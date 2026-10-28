package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Marbin extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Marbin.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Marbin();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getLevel() < 52) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest8"));
                return;
            }
            else if (pc.getInventory().checkItem(92340)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest1"));
            }
        }
        catch (Exception e) {
            Npc_Marbin._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        long needExp = ExpTable.getNeedExpNextLevel(49);
        if (cmd.equalsIgnoreCase("a")) {
            L1PcInventory inv = pc.getInventory();
            if (inv != null) {
                if (inv.checkItem(92124) && inv.checkItem(92120, 100L) && inv.checkItem(92118)) {
                    inv.consumeItem(92124, 1L);
                    inv.consumeItem(92118, 1L);
                    L1ItemInstance pcItem = pc.getInventory().findItemId(92120);
                    inv.removeItem(pcItem);
                    L1ItemInstance item = ItemTable.get().createItem(92125);
                    if (item != null) {
                        item.setCount(5L);
                        item.setIdentified(true);
                        if (pc.getInventory().checkAddItem(item, 5L) == 0) {
                            pc.getInventory().storeItem(item);
                            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest2"));
                        }
                        double addExp = 720000.0;
                        double exppenalty = ExpTable.getPenaltyRate(pc.getLevel());
                        pc.addExp((long)(addExp * exppenalty));
                    }
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest4"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest5"));
                }
            }
        }
        else if (cmd.equalsIgnoreCase("b")) {
            if (pc.getLevel() >= 52) {
                L1PcInventory inv = pc.getInventory();
                if (inv != null && !inv.checkItem(92340)) {
                    L1ItemInstance item2 = ItemTable.get().createItem(92340);
                    if (item2 != null) {
                        item2.setCount(1L);
                        item2.setIdentified(true);
                        if (pc.getInventory().checkAddItem(item2, 1L) == 0) {
                            pc.getInventory().storeItem(item2);
                            pc.sendPackets(new S_ServerMessage(403, item2.getLogName()));
                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest2"));
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_SystemMessage("你已經領取過袋子"));
                    return;
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest8"));
            }
        }
        else if (cmd.equalsIgnoreCase("c")) {
            L1PcInventory inv = pc.getInventory();
            if (inv.checkItem(92119) && inv.checkItem(92120, 100L) && inv.checkItem(92124)) {
                inv.consumeItem(92119, 1L);
                inv.consumeItem(92124, 1L);
                L1ItemInstance pcItem = pc.getInventory().findItemId(92120);
                inv.removeItem(pcItem);
                needExp = (long)(int)((double)needExp * 0.06);
                pc.addExp((long)(int)needExp);
                CreateNewItem.createNewItem_NPC(pc, npc.getName(), 92126, 1);
                pc.sendPackets(new S_SkillSound(pc.getId(), 3944));
                pc.broadcastPacketAll(new S_SkillSound(pc.getId(), 3944));
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest6"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "marbinquest5"));
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
