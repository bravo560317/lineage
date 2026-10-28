package com.lineage.data.npc.shop;

import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1MonsterArena;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HowManyMake;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

public class Npc_MonsterArena extends NpcExecutor {

    private Npc_MonsterArena() {}

    @Override
    public int type() {
        return 3;
    }

    public static NpcExecutor get() {
        return new Npc_MonsterArena();
    }

    @Override
    public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
        String[] arenaData = L1MonsterArena.getInstance().getArenaData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "arena", arenaData));
    }

    @Override
    public void action(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final long amount) {
        if (cmd.equalsIgnoreCase("join_arena")) {
            L1MonsterArena.WaitingPlayer waiting = L1MonsterArena.getInstance().getWaitingPlayer();
            if (waiting != null && waiting.pc.getId() != pc.getId()) {
                L1MonsterArena.getInstance().playArena(pc, waiting.betAmount);
                return;
            }
            L1ItemInstance gold = pc.getInventory().findItemId(40308);
            int maxGold = (gold != null) ? (int) Math.min(gold.getCount(), Integer.MAX_VALUE) : 0;
            pc.sendPackets(new S_HowManyMake(npc.getId(), maxGold, "do_play_arena"));
            return;
        } else if (cmd.equalsIgnoreCase("do_play_arena")) {
            L1MonsterArena.getInstance().playArena(pc, (int) amount);
        } else if (cmd.equalsIgnoreCase("watch_arena")) {
            L1Teleport.teleport(pc, 32799, 32870, (short) 5135, 0, true);
            return;
        } else if (cmd.equalsIgnoreCase("online")) {
            long count = L1MonsterArena.getInstance().getOnlineCount(pc);
            if (count > 0) {
                L1ItemInstance item = ItemTable.get().createItem(40308);
                if (item != null) {
                    item.setCount(count);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                }
            } else {
                pc.sendPackets(new S_SystemMessage("您沒有可領取的擂台獎金。"));
            }
        }
        
        String[] arenaData = L1MonsterArena.getInstance().getArenaData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "arena", arenaData));
    }
}