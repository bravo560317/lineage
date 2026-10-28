package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_NPCPack_D;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1DwarfInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1DwarfInstance.class);
    }
    
    public L1DwarfInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack_D(this));
        }
        catch (Exception e) {
            L1DwarfInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
        }
        catch (Exception e) {
            L1DwarfInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        int npcId = this.getNpcTemplate().get_npcId();
        String htmlid = null;
        if (talking != null) {
            if (npcId == 60028 && !pc.isElf()) {
                htmlid = "elCE1";
            }
            if (htmlid != null) {
                pc.sendPackets(new S_NPCTalkReturn(objid, htmlid));
            }
            else if (pc.getLevel() < 5) {
                pc.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance pc, String Action) {
        if (Action.equalsIgnoreCase("retrieve-pledge") && pc.getClanname().equalsIgnoreCase(" ")) {
            pc.sendPackets(new S_ServerMessage(208));
        }
    }
}
