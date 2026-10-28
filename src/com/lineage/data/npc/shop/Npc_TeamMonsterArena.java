package com.lineage.data.npc.shop;

import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1TeamMonsterArena;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HowManyMake;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

public class Npc_TeamMonsterArena extends NpcExecutor {

    private Npc_TeamMonsterArena() {}

    @Override
    public int type() {
        return 3;
    }

    public static NpcExecutor get() {
        return new Npc_TeamMonsterArena();
    }

    @Override
    public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
        String[] arenaData = L1TeamMonsterArena.getInstance().getArenaData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "team_arena", arenaData));
    }

    @Override
    public void action(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final long amount) {
        if (cmd.equalsIgnoreCase("join_arena")) {
            L1TeamMonsterArena.WaitingPlayer waiting = L1TeamMonsterArena.getInstance().getWaitingPlayer();
            if (waiting != null && waiting.pc.getId() != pc.getId()) {
                L1TeamMonsterArena.getInstance().playArena(pc, waiting.betAmount);
                return;
            }
            L1ItemInstance gold = pc.getInventory().findItemId(40308);
            int maxGold = (gold != null) ? (int) Math.min(gold.getCount(), Integer.MAX_VALUE) : 0;
            pc.sendPackets(new S_HowManyMake(npc.getId(), maxGold, "do_play_arena"));
            return;
        } else if (cmd.equalsIgnoreCase("do_play_arena")) {
            L1TeamMonsterArena.getInstance().playArena(pc, (int) amount);
        } else if (cmd.equalsIgnoreCase("watch_arena")) {
            // 傳送到 3v3 地圖 5136 觀戰點
            L1Teleport.teleport(pc, 32799, 32870, (short) 5136, 0, true);
            return;
        } else if (cmd.equalsIgnoreCase("online")) {
            long count = L1TeamMonsterArena.getInstance().getOnlineCount(pc);
            if (count > 0) {
                L1ItemInstance item = ItemTable.get().createItem(40308);
                if (item != null) {
                    item.setCount(count);
                    pc.getInventory().storeItem(item);
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                }
            } else {
                pc.sendPackets(new S_SystemMessage("您沒有可領取的 3v3 擂台獎金。"));
            }
        }
        
        String[] arenaData = L1TeamMonsterArena.getInstance().getArenaData(pc);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "team_arena", arenaData));
    }
}