package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Doett extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Doett.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Doett();
    }
    
    @Override
    public int type() {
        return 17;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            npc.setHeading(npc.targetDirection(pc.getX(), pc.getY()));
            npc.broadcastPacketAll(new S_ChangeHeading(npc));
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM1"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM1"));
            }
            else if (pc.isElf()) {
                if (pc.getLawful() < 0) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettec1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doette1"));
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM1"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM2"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM3"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "doettM4"));
            }
        }
        catch (Exception e) {
            Npc_Doett._log.error(e.getLocalizedMessage(), e);
        }
    }
}
