package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Shenter extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Shenter.class);
    }
    
    private Npc_Shenter() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Shenter();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.getInventory().checkItem(95308) && !pc.getInventory().checkItem(95309)) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "shenter1"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "shenter2"));
            }
        }
        catch (Exception e) {
            Npc_Shenter._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        try {
            if (cmd.equalsIgnoreCase("r")) {
                pc.sendPackets(new S_SystemMessage("尚未開放排名"));
            }
            else if (cmd.equalsIgnoreCase("a")) {
                if (pc.getInventory().checkItem(95309)) {
                    pc.sendPackets(new S_SystemMessage("屍魂水晶還在封印中"));
                    return;
                }
                else if (pc.getInventory().checkItem(95308)) {
                    pc.sendPackets(new S_SystemMessage("你已經領取過了"));
                    return;
                }
                else if (pc.getInventory().getSize() > 170) {
                    pc.sendPackets(new S_SystemMessage("你身上持有道具過多，領取失敗"));
                    return;
                }
                else {
                    CreateNewItem.createNewItem(pc, 95308, 1L);
                    pc.sendPackets(new S_CloseList(pc.getId()));
                }
            }
        }
        catch (Exception e) {
            Npc_Shenter._log.error(e.getLocalizedMessage(), e);
        }
    }
}
