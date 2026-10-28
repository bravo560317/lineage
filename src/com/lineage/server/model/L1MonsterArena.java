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

public class L1MonsterArena {
    private static L1MonsterArena _instance;
    private static final Random _random = new Random();
    private static final org.apache.commons.logging.Log _log = org.apache.commons.logging.LogFactory.getLog(L1MonsterArena.class);

    public static final int MAP_ID = 5135;
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

    public static L1MonsterArena getInstance() {
        if (_instance == null) {
            _instance = new L1MonsterArena();
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
                    ? "您是紅方，等待藍方中..."
                    : String.format("已有紅方，點擊參賽即可用 %s 金幣應戰！", bet);
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
            pc.sendPackets(new S_SystemMessage("伺服器即將重啟或關機，暫時無法參與怪物對決！"));
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
            pc.sendPackets(new S_SystemMessage(String.format("發起擂台，下注 %d 金幣，等待藍方中...", betAmount)));
            World.get().broadcastServerMessage(String.format("【怪物對決】玩家 [%s] 發起 %d 金幣擂台，歡迎前往應戰！", pc.getName(), betAmount));
            GeneralThreadPool.get().execute(new ArenaTimeoutTask(_queueStartTime));
            return;
        }

        final WaitingPlayer host = _waitingPlayer;
        _waitingPlayer = null;
        pc.sendPackets(new S_SystemMessage(String.format("已用 %d 金幣應戰，擂台即將開始！", betAmount)));
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
                synchronized (L1MonsterArena.this) {
                    if (_waitingPlayer == null || _queueStartTime != queueTime) {
                        return;
                    }
                    final WaitingPlayer waiting = _waitingPlayer;
                    _waitingPlayer = null;
                    refund(waiting.pc, waiting.betAmount, "10 分鐘內無人應戰，下注金幣已退還。");
                    World.get().broadcastServerMessage(String.format("【怪物對決】玩家 [%s] 的擂台逾時取消。", waiting.pc.getName()));
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
            L1MonsterInstance mob1 = null;
            L1MonsterInstance mob2 = null;
            try {
                // 1v1 勢均力敵配對機制：限制雙方血量差距不超過 50%
                int maxRetry = 100;
                boolean balanced = false;
                L1Npc template1 = null;
                L1Npc template2 = null;

                while (!balanced && maxRetry > 0) {
                    maxRetry--;
                    int mobId1 = MONSTER_LIST[_random.nextInt(MONSTER_LIST.length)];
                    int mobId2 = MONSTER_LIST[_random.nextInt(MONSTER_LIST.length)];

                    template1 = NpcTable.get().getTemplate(mobId1);
                    template2 = NpcTable.get().getTemplate(mobId2);

                    if (template1 == null || template2 == null) {
                        refund(host, betAmount, "擂台系統異常，已將贊助金退還。");
                        refund(challenger, betAmount, "擂台系統異常，已將應戰金退還。");
                        return;
                    }

                    double hp1 = template1.get_hp();
                    double hp2 = template2.get_hp();

                    if (hp1 > 0 && hp2 > 0) {
                        double minHp = Math.min(hp1, hp2);
                        double maxHp = Math.max(hp1, hp2);
                        if (minHp >= maxHp * 0.5) { // 兩隻怪血量落差小於 50%
                            balanced = true;
                        }
                    }
                }

                mob1 = new L1MonsterInstance(template1);
                mob2 = new L1MonsterInstance(template2);

                mob1.setId(IdFactoryNpc.get().nextId());
                mob2.setId(IdFactoryNpc.get().nextId());
                mob1.setMap((short) MAP_ID);
                mob1.setX(32797);
                mob1.setY(32865);
                mob1.setHeading(2);

                mob2.setMap((short) MAP_ID);
                mob2.setX(32802);
                mob2.setY(32865);
                mob2.setHeading(6);

                // 一生成即設定為沉睡狀態（留在原地不動、不攻擊）
                mob1.setSleeped(true);
                mob2.setSleeped(true);

                World.get().storeObject(mob1);
                World.get().addVisibleObject(mob1);
                World.get().storeObject(mob2);
                World.get().addVisibleObject(mob2);

                World.get().broadcastServerMessage(String.format("【怪物對決】[%s] 的 %s VS [%s] 的 %s，5 秒後開戰！",
                    host.getName(), mob1.getName(), challenger.getName(), mob2.getName()));

                // 等待 5 秒
                Thread.sleep(5000L);

                // 5 秒後解除沉睡狀態，並賦予仇恨與啟動 AI
                mob1.setSleeped(false);
                mob2.setSleeped(false);
                mob1.setHate(mob2, 1000);
                mob2.setHate(mob1, 1000);
                mob1.onNpcAI();
                mob2.onNpcAI();

                int timeout = 0;
                while (mob1 != null && mob2 != null && !mob1.isDead() && !mob2.isDead()) {
                    Thread.sleep(1000L);
                    timeout++;
                    if (timeout > 60) {
                        break;
                    }
                    if (mob1.getCurrentHp() > 0 && mob2.getCurrentHp() > 0) {
                        mob1.setHate(mob2, 1000);
                        mob2.setHate(mob1, 1000);
                        mob1.arenaAttackTarget(mob2);
                        mob2.arenaAttackTarget(mob1);
                    }
                }

                final L1PcInstance winner = mob1.isDead() && !mob2.isDead() ? challenger
                        : mob2.isDead() && !mob1.isDead() ? host
                        : mob1.getCurrentHp() >= mob2.getCurrentHp() ? host : challenger;
                final String winnerMonster = winner == host ? mob1.getName() : mob2.getName();
                if (mob1 != null) mob1.deleteMe();
                if (mob2 != null) mob2.deleteMe();
                final long totalPot = (long) betAmount * 2;
                final long tax = totalPot * 2 / 100;
                final long reward = totalPot - tax;
                giveReward(winner, reward);
                World.get().broadcastServerMessage(String.format("【怪物對決】怪物 [%s] 獲勝，玩家 [%s] 贏得 %d 金幣（傭金 %d）。",
                    winnerMonster, winner.getName(), reward, tax));

            } catch (Exception e) {
                _log.error("ArenaFightTask error", e);
                try {
                    refund(host, betAmount, "擂台發生異常，已將贊助金退還。");
                    refund(challenger, betAmount, "擂台發生異常，已將應戰金退還。");
                } catch (Exception ex) {}
            } finally {
                if (mob1 != null) mob1.deleteMe();
                if (mob2 != null) mob2.deleteMe();
            }
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
                pc.sendPackets(new S_SystemMessage(String.format("【怪物對決】恭喜您贏得獎金 %d 金幣！", amount)));
            }
        } else {
            _offlineRewards.put(pc.getId(), _offlineRewards.getOrDefault(pc.getId(), 0L) + amount);
        }
        WriteLogTxt.Recording("怪物對決", String.format("玩家:%s 獲得金幣:%d", pc.getName(), amount));
    }

    public synchronized long getOnlineCount(final L1PcInstance pc) {
        final Long amount = _offlineRewards.remove(pc.getId());
        return amount == null ? 0L : amount;
    }
}