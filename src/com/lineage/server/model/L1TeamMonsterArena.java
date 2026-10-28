package com.lineage.server.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;
import com.lineage.server.Shutdown;
import com.lineage.server.WriteLogTxt;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.world.World;

public class L1TeamMonsterArena {
    private static L1TeamMonsterArena _instance;
    private static final Random _random = new Random();
    private static final org.apache.commons.logging.Log _log = org.apache.commons.logging.LogFactory.getLog(L1TeamMonsterArena.class);

    public static final int MAP_ID = 5136; // 3v3 專屬地圖
    private static final int FEE_ITEM_ID = 40308; // 金幣
    private static final int MIN_BET_AMOUNT = 50000;
    private static final long QUEUE_TIMEOUT = 10 * 60 * 1000L;

private static final int[] MONSTER_LIST = {
        1045041, 1045019, 1045009, 1045008, 1045016, 1097025, 1058007, 1058005, 1058002, 1097024,
        1045697, 1045695, 1045712, 1045710, 1058004, 1045696, 1045693, 1045694, 1045690, 1045691,
        1045686, 1045687, 1045688, 1045692, 1045689, 1058006, 1058001, 1058017, 1097022, 1097023,
        1058003, 1045044, 1045313, 1045711, 1045049, 1046130, 1046125, 1046133, 1046134, 1046131,
        1045269, 1045349, 1046129, 1045322, 1046128, 1045286, 1045270, 1045365, 1045359, 1045341,
        1045451, 1045257, 1045203, 1045206, 1045291, 1045393, 1045420, 1045381, 1045248, 1045284,
        1045361, 1045373, 1045216, 1045221, 1045224, 1045276, 1045289, 1045298, 1045324, 1045346,
        1045278, 1045280, 1045823, 1045819, 1045155, 1045264, 1045329, 1045265, 1045822, 1045812,
        1045047, 1045048, 1045039, 1045054, 1045040, 1045241, 1045162, 1045141, 1045816, 1045806,
        1045213, 1045223, 1045215, 1045387, 1045809, 1045190, 1046132, 1045222, 1045817, 1045925,
        1045184, 1045171, 1045370, 1045818, 1045810, 1045173, 1045161, 1045149, 1045144, 1045146,
        1045147, 1045143, 1045136, 1045140, 1045804, 1045131, 1045130, 1045127, 1045126, 1045196,
        1045121, 1045122, 1045125, 1045115, 1045088, 1045107, 1045109, 1045098, 1045082, 1045092,
        1045024, 1045551, 1045079, 1045083, 1045033, 1045043, 1045046, 1045042, 1045034, 1045053
    };

    private WaitingPlayer _waitingPlayer;
    private long _queueStartTime;
    private final HashMap<Integer, Long> _offlineRewards = new HashMap<>();

    public static class WaitingPlayer {
        public final L1PcInstance pc;
        public final int betAmount;

        public WaitingPlayer(final L1PcInstance pc, final int betAmount) {
            this.pc = pc;
            this.betAmount = betAmount;
        }
    }

    public static L1TeamMonsterArena getInstance() {
        if (_instance == null) {
            _instance = new L1TeamMonsterArena();
        }
        return _instance;
    }

    public synchronized String[] getArenaData(L1PcInstance pc) {
        String hostName = "無人";
        String bet = "0";
        String status = "請下注成為紅方";
        if (_waitingPlayer != null) {
            hostName = _waitingPlayer.pc.getName();
            bet = insertComma(_waitingPlayer.betAmount);
            status = _waitingPlayer.pc.getId() == pc.getId()
                    ? "您是紅方贊助，等待藍方應戰中..."
                    : String.format("已有紅方贊助，點擊參賽即可用 %s 金幣藍方應戰！", bet);
        }
        return new String[] {
            hostName,
            bet,
            status
        };
    }

    private String insertComma(int s) {
        final NumberFormat formater = new DecimalFormat("###,###,###,###");
        return formater.format(s);
    }

    public synchronized void playArena(final L1PcInstance pc, final int betAmount) {
        if (ServerRestartTimer.isRtartTime() || Shutdown.SHUTDOWN) {
            pc.sendPackets(new S_SystemMessage("伺服器即將重啟或關機，暫時無法參與 怪物團戰！"));
            return;
        }

        if (betAmount < MIN_BET_AMOUNT) {
            pc.sendPackets(new S_SystemMessage(String.format("最低下注金額為 %d 金幣！", MIN_BET_AMOUNT)));
            return;
        }

        if (_waitingPlayer != null && _waitingPlayer.pc.getId() == pc.getId()) {
            pc.sendPackets(new S_SystemMessage("您已經是紅方，請等待其他玩家應戰。"));
            return;
        }

        final L1ItemInstance feeItem = pc.getInventory().findItemId(FEE_ITEM_ID);
        if (feeItem == null || feeItem.getCount() < betAmount) {
            pc.sendPackets(new S_SystemMessage(String.format("金幣不足，無法參與擂台（需要 %d 金幣）。", betAmount)));
            return;
        }

        if (!pc.getInventory().consumeItem(FEE_ITEM_ID, betAmount)) {
            pc.sendPackets(new S_SystemMessage("扣除金幣失敗！"));
            return;
        }

        if (_waitingPlayer == null) {
            _queueStartTime = System.currentTimeMillis();
            _waitingPlayer = new WaitingPlayer(pc, betAmount);
            pc.sendPackets(new S_SystemMessage(String.format("成功發起 3v3 擂台，下注 %d 金幣，等待藍方應戰...", betAmount)));
            World.get().broadcastServerMessage(String.format("【怪物團戰】玩家 [%s] 發起 %d 金幣 3v3 擂台，歡迎前往應戰！", pc.getName(), betAmount));
            GeneralThreadPool.get().execute(new ArenaTimeoutTask(_queueStartTime));
            return;
        }

        final WaitingPlayer host = _waitingPlayer;
        _waitingPlayer = null;
        pc.sendPackets(new S_SystemMessage(String.format("已用 %d 金幣藍方應戰，3v3 擂台即將開始！", betAmount)));
        GeneralThreadPool.get().execute(new ArenaFightTask(host.pc, pc, betAmount));
    }

    public synchronized WaitingPlayer getWaitingPlayer() {
        return _waitingPlayer;
    }

    private class ArenaTimeoutTask implements Runnable {
        private final long queueTime;

        private ArenaTimeoutTask(final long queueTime) {
            this.queueTime = queueTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(QUEUE_TIMEOUT);
                synchronized (L1TeamMonsterArena.this) {
                    if (_waitingPlayer == null || _queueStartTime != queueTime) {
                        return;
                    }
                    final WaitingPlayer waiting = _waitingPlayer;
                    _waitingPlayer = null;
                    refund(waiting.pc, waiting.betAmount, "10 分鐘內無人應戰，3v3 下注金幣已退還。");
                    World.get().broadcastServerMessage(String.format("【怪物團戰】玩家 [%s] 的 3v3 擂台逾時取消。", waiting.pc.getName()));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                _log.error("ArenaTimeoutTask error", e);
            }
        }
    }

    private class ArenaFightTask implements Runnable {
        private final L1PcInstance host;
        private final L1PcInstance challenger;
        private final int betAmount;

        public ArenaFightTask(final L1PcInstance host, final L1PcInstance challenger, final int betAmount) {
            this.host = host;
            this.challenger = challenger;
            this.betAmount = betAmount;
        }

        @Override
        public void run() {
            L1MonsterInstance[] team1 = new L1MonsterInstance[3];
            L1MonsterInstance[] team2 = new L1MonsterInstance[3];
            try {
                // 紅方 3 隻怪座標
                int[] x1 = { 32797, 32797, 32797 };
                int[] y1 = { 32861, 32863, 32865 };
                // 藍方 3 隻怪座標
                int[] x2 = { 32802, 32802, 32802 };
                int[] y2 = { 32861, 32863, 32865 };

                // 勢均力敵配對機制：限制雙方總血量差距不超過 50%
                int maxRetry = 100;
                boolean balanced = false;
                L1Npc[] t1List = new L1Npc[3];
                L1Npc[] t2List = new L1Npc[3];

                while (!balanced && maxRetry > 0) {
                    maxRetry--;
                    long hp1Total = 0;
                    long hp2Total = 0;

                    for (int i = 0; i < 3; i++) {
                        int id1 = MONSTER_LIST[_random.nextInt(MONSTER_LIST.length)];
                        int id2 = MONSTER_LIST[_random.nextInt(MONSTER_LIST.length)];

                        t1List[i] = NpcTable.get().getTemplate(id1);
                        t2List[i] = NpcTable.get().getTemplate(id2);

                        if (t1List[i] == null || t2List[i] == null) {
                            refund(host, betAmount, "3v3 系統異常，已退還贊助金。");
                            refund(challenger, betAmount, "3v3 系統異常，已退還應戰金。");
                            return;
                        }

                        hp1Total += t1List[i].get_hp();
                        hp2Total += t2List[i].get_hp();
                    }

                    if (hp1Total > 0 && hp2Total > 0) {
                        double minHp = Math.min(hp1Total, hp2Total);
                        double maxHp = Math.max(hp1Total, hp2Total);
                        if (minHp >= maxHp * 0.5) { // 雙方總血量落差小於 50%
                            balanced = true;
                        }
                    }
                }

                // 若超過 100 次沒篩選到，直接用最後一次
                for (int i = 0; i < 3; i++) {
                    team1[i] = new L1MonsterInstance(t1List[i]);
                    team2[i] = new L1MonsterInstance(t2List[i]);

                    team1[i].setId(IdFactoryNpc.get().nextId());
                    team2[i].setId(IdFactoryNpc.get().nextId());
                    team1[i].setMaster(null);
                    team2[i].setMaster(null);

                    team1[i].setMap((short) MAP_ID);
                    team1[i].setX(x1[i]);
                    team1[i].setY(y1[i]);
                    team1[i].setHeading(2);

                    team2[i].setMap((short) MAP_ID);
                    team2[i].setX(x2[i]);
                    team2[i].setY(y2[i]);
                    team2[i].setHeading(6);

                    // 讓怪物一生成就進入沉睡狀態（留在原地不動）
                    team1[i].setSleeped(true);
                    team2[i].setSleeped(true);

                    World.get().storeObject(team1[i]);
                    World.get().addVisibleObject(team1[i]);
                    World.get().storeObject(team2[i]);
                    World.get().addVisibleObject(team2[i]);
                }

                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                    sb1.append(team1[i].getName()).append(i < 2 ? "、" : "");
                    sb2.append(team2[i].getName()).append(i < 2 ? "、" : "");
                }

                World.get().broadcastServerMessage(String.format("【怪物團戰】紅方 [%s] (%s) VS 藍方 [%s] (%s)，5 秒後混戰開打！",
                    host.getName(), sb1.toString(), challenger.getName(), sb2.toString()));
                
                // 等待 5 秒
                Thread.sleep(5000L);

                // 解除沉睡狀態
                for (int i = 0; i < 3; i++) {
                    team1[i].setSleeped(false);
                    team2[i].setSleeped(false);
                }

                int timeout = 0;
                boolean isTimeout = false;
                while (true) {
                    Thread.sleep(1000L);
                    timeout++;
                    if (timeout > 90) { // 90 秒未分出勝負則平手退款
                        isTimeout = true;
                        break;
                    }

                    boolean team1Alive = false;
                    boolean team2Alive = false;

                    for (int i = 0; i < 3; i++) {
                        if (team1[i] != null && !team1[i].isDead() && team1[i].getCurrentHp() > 0) team1Alive = true;
                        if (team2[i] != null && !team2[i].isDead() && team2[i].getCurrentHp() > 0) team2Alive = true;
                    }

                    if (!team1Alive || !team2Alive) break;

                    // 參照 1v1 完美會走過去的邏輯：給予仇恨並驅動攻擊
                    for (int i = 0; i < 3; i++) {
                        L1MonsterInstance target1 = findAlive(team2);
                        if (isAlive(team1[i]) && target1 != null) {
                            team1[i].setHate(target1, 1000);
                            team1[i].arenaAttackTarget(target1);
                        }
                        L1MonsterInstance target2 = findAlive(team1);
                        if (isAlive(team2[i]) && target2 != null) {
                            team2[i].setHate(target2, 1000);
                            team2[i].arenaAttackTarget(target2);
                        }
                    }
                }

                for (int i = 0; i < 3; i++) {
                    if (team1[i] != null) team1[i].deleteMe();
                    if (team2[i] != null) team2[i].deleteMe();
                }

                if (isTimeout) {
                    refund(host, betAmount, "3v3 擂台超時未分勝負，已退還下注金幣。");
                    refund(challenger, betAmount, "3v3 擂台超時未分勝負，已退還應戰金幣。");
                    World.get().broadcastServerMessage("【怪物團戰】雙方激戰 90 秒平手未分勝負，已各自退還報名金幣。");
                } else {
                    boolean team1Alive = false;
                    for (int i = 0; i < 3; i++) {
                        if (team1[i] != null && !team1[i].isDead() && team1[i].getCurrentHp() > 0) team1Alive = true;
                    }

                    final L1PcInstance winner = team1Alive ? host : challenger;
                    final long totalPot = (long) betAmount * 2;
                    final long tax = totalPot * 2 / 100; // 抽 2% 水錢
                    final long reward = totalPot - tax;
                    
                    giveReward(winner, reward);
                    World.get().broadcastServerMessage(String.format("【怪物團戰】激戰結束！恭喜 [%s] 贏得 3v3 總獎金 %d 金幣（系統扣除 2%% 水錢）。",
                        winner.getName(), reward));
                }

            } catch (Exception e) {
                _log.error("ArenaFightTask error", e);
                try {
                    refund(host, betAmount, "3v3 擂台異常，已退還金幣。");
                    refund(challenger, betAmount, "3v3 擂台異常，已退還金幣。");
                } catch (Exception ex) {}
            } finally {
                for (int i = 0; i < 3; i++) {
                    if (team1[i] != null) team1[i].deleteMe();
                    if (team2[i] != null) team2[i].deleteMe();
                }
            }
        }

        private boolean isAlive(final L1MonsterInstance monster) {
            return monster != null && !monster.isDead() && monster.getCurrentHp() > 0;
        }

        private L1MonsterInstance findAlive(final L1MonsterInstance[] team) {
            for (L1MonsterInstance monster : team) {
                if (isAlive(monster)) {
                    return monster;
                }
            }
            return null;
        }
    }

    private void refund(final L1PcInstance pc, final long amount, final String message) {
        if (pc.getOnlineStatus() == 1) {
            pc.getInventory().storeItem(FEE_ITEM_ID, amount);
            pc.sendPackets(new S_SystemMessage(message));
        } else {
            _offlineRewards.put(pc.getId(), _offlineRewards.getOrDefault(pc.getId(), 0L) + amount);
        }
    }

    private void giveReward(final L1PcInstance pc, final long amount) {
        if (pc.getOnlineStatus() == 1) {
            L1ItemInstance item = ItemTable.get().createItem(FEE_ITEM_ID);
            if (item != null) {
                item.setCount(amount);
                pc.getInventory().storeItem(item);
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                pc.sendPackets(new S_SystemMessage(String.format("【怪物團戰】恭喜您贏得獎金 %d 金幣！", amount)));
            }
        } else {
            _offlineRewards.put(pc.getId(), _offlineRewards.getOrDefault(pc.getId(), 0L) + amount);
        }
        WriteLogTxt.Recording("3v3怪物擂台", String.format("玩家:%s 獲得金幣:%d", pc.getName(), amount));
    }

    public synchronized long getOnlineCount(final L1PcInstance pc) {
        final Long amount = _offlineRewards.remove(pc.getId());
        return amount == null ? 0L : amount;
    }
}