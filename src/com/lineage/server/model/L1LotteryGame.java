package com.lineage.server.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigOther;
import com.lineage.server.Shutdown;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.world.World;

public class L1LotteryGame {
    private static L1LotteryGame _instance;
    private static final Log _log = LogFactory.getLog(L1LotteryGame.class);
    private static final Random _random = new Random();

    private static final int MAX_TICKETS = 100;
    private static final int TICKET_PRICE = 50000;
    private static final long WINNER_REWARD = 4950000L;

    private final List<LotteryBuyer> _ticketPool = new ArrayList<>();
    private final HashMap<Integer, Integer> _onlemembers = new HashMap<>();
    
    private long _startTime = 0;

    public static L1LotteryGame getInstance() {
        if (_instance == null) {
            _instance = new L1LotteryGame();
        }
        return _instance;
    }

    public static class LotteryBuyer {
        public L1PcInstance pc;
        public LotteryBuyer(L1PcInstance pc) {
            this.pc = pc;
        }
    }

    public String[] getLotteryData(L1PcInstance pc) {
        int soldCount = _ticketPool.size();
        int remainCount = MAX_TICKETS - soldCount;
        String poolStr = insertComma((int)(soldCount * TICKET_PRICE));

        return new String[] {
            String.valueOf(soldCount),
            String.valueOf(remainCount),
            poolStr,
            insertComma((int)WINNER_REWARD)
        };
    }

    private String insertComma(int s) {
        final NumberFormat formater = new DecimalFormat("###,###,###,###");
        return formater.format(s);
    }

    public synchronized void buyTickets(L1PcInstance pc, int count) {
        if (ServerRestartTimer.isRtartTime() || Shutdown.SHUTDOWN) {
            pc.sendPackets(new S_SystemMessage("伺服器即將重啟或關機，暫時無法購買彩券！"));
            return;
        }

        if (count <= 0) {
            pc.sendPackets(new S_SystemMessage("購買張數錯誤！"));
            return;
        }

        if (_ticketPool.isEmpty()) {
            _startTime = System.currentTimeMillis();
            GeneralThreadPool.get().execute(new LotteryTimeoutTask());
        }

        int currentSold = _ticketPool.size();
        int remain = MAX_TICKETS - currentSold;

        if (remain <= 0) {
            pc.sendPackets(new S_SystemMessage("本期彩券已全部售完，請等待開獎！"));
            return;
        }

        if (count > remain) {
            count = remain;
        }

        long totalCost = (long)count * TICKET_PRICE;
        final L1ItemInstance deaItem = pc.getInventory().findItemId(ConfigOther.Dubo);
        if (deaItem == null || deaItem.getCount() < totalCost) {
            pc.sendPackets(new S_SystemMessage(String.format("你的籌碼不足！購買 %d 張需要 %s 籌碼。", count, insertComma((int)totalCost))));
            return;
        }

        if (pc.getInventory().removeItem(deaItem, totalCost) != totalCost) {
            pc.sendPackets(new S_SystemMessage("扣除籌碼失敗！"));
            return;
        }

        for (int i = 0; i < count; i++) {
            _ticketPool.add(new LotteryBuyer(pc));
        }

        pc.sendPackets(new S_SystemMessage(String.format("成功購買了 %d 張彩券，花費 %s 籌碼！", count, insertComma((int)totalCost))));
        World.get().broadcastServerMessage(String.format("【彩券】玩家 [%s] 購買了 %d 張彩券！目前剩餘 %d 張 / 100張。", pc.getName(), count, (MAX_TICKETS - _ticketPool.size())));

        WriteLogTxt.Recording("彩券記錄", String.format("玩家:%s 購買 %d 張彩券，花費:%d", pc.getName(), count, totalCost));

        if (_ticketPool.size() >= MAX_TICKETS) {
            GeneralThreadPool.get().execute(new LotteryDrawTask());
        }
    }

    public int getOnlineCount(final L1PcInstance pc) {
        int count = 0;
        if (_onlemembers.containsKey(pc.getId())) {
            count = _onlemembers.get(pc.getId());
            _onlemembers.remove(pc.getId());
        }
        return count;
    }

    private class LotteryTimeoutTask implements Runnable {
        private final long savedStartTime = _startTime;

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(30000);
                    synchronized (L1LotteryGame.this) {
                        if (_ticketPool.isEmpty() || _startTime != savedStartTime) {
                            return;
                        }
                    }
                }

                synchronized (L1LotteryGame.this) {
                    if (_ticketPool.isEmpty() || _startTime != savedStartTime) {
                        return;
                    }

                    World.get().broadcastServerMessage("【彩券逾時】100張彩券在 10 分鐘內未售罄，本期彩券自動取消，全數退還已購籌碼！");
                    
                    HashMap<L1PcInstance, Integer> refundMap = new HashMap<>();
                    for (LotteryBuyer buyer : _ticketPool) {
                        refundMap.put(buyer.pc, refundMap.getOrDefault(buyer.pc, 0) + 1);
                    }

                    for (Map.Entry<L1PcInstance, Integer> entry : refundMap.entrySet()) {
                        L1PcInstance pc = entry.getKey();
                        int count = entry.getValue();
                        int refundAmount = count * TICKET_PRICE;

                        if (pc.getOnlineStatus() == 1) {
                            pc.getInventory().storeItem(ConfigOther.Dubo, refundAmount);
                            pc.sendPackets(new S_SystemMessage(String.format("【彩券退還】因逾時未開獎，退還您購買彩券的 %s 籌碼。", insertComma(refundAmount))));
                        } else {
                            _onlemembers.put(pc.getId(), _onlemembers.getOrDefault(pc.getId(), 0) + refundAmount);
                        }
                        WriteLogTxt.Recording("彩券記錄", String.format("逾時退還玩家:%s 金額:%d", pc.getName(), refundAmount));
                    }

                    _ticketPool.clear();
                }
            } catch (Exception e) {
                _log.error("LotteryTimeoutTask error", e);
            }
        }
    }

    private class LotteryDrawTask implements Runnable {
        @Override
        public void run() {
            try {
                World.get().broadcastServerMessage("【彩券開獎】100張彩券已全數售完！準備進行公開隨機抽獎...");
                Thread.sleep(2000);

                if (_ticketPool.isEmpty()) {
                    return;
                }

                int luckyIndex = _random.nextInt(_ticketPool.size());
                LotteryBuyer winnerBuyer = _ticketPool.get(luckyIndex);
                final L1PcInstance winnerPc = winnerBuyer.pc;

                _ticketPool.clear();

                World.get().broadcastServerMessage(String.format("【彩券中獎】恭喜 [%s] 從 100 張彩券中脫穎而出，獨得 4,950,000 籌碼大獎！", winnerPc.getName()));

                if (winnerPc.getOnlineStatus() == 1) {
                    winnerPc.getInventory().storeItem(ConfigOther.Dubo, WINNER_REWARD);
                    winnerPc.sendPackets(new S_ServerMessage(143, "彩券大師", "籌碼(" + WINNER_REWARD + ")"));
                    WriteLogTxt.Recording("彩券記錄", String.format("中獎玩家:%s 獲得獎金(%d)在線", winnerPc.getName(), WINNER_REWARD));
                } else {
                    _onlemembers.put(winnerPc.getId(), (int) WINNER_REWARD);
                    WriteLogTxt.Recording("彩券記錄", String.format("中獎玩家:%s 獲得獎金(%d)離線", winnerPc.getName(), WINNER_REWARD));
                }

            } catch (Exception e) {
                _log.error("LotteryDrawTask error", e);
            }
        }
    }
}