package com.lineage.server.model.Instance;

import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1RequestInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1RequestInstance.class);
    }
    
    public L1RequestInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance player) {
        try {
            int objid = this.getId();
            L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
            if (talking != null) {
                if (player.getLawful() < -1000) {
                    player.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
                }
                else {
                    player.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
                }
            }
        }
        catch (Exception e) {
            L1RequestInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
    }
    
    public void doFinalAction(L1PcInstance player) {
    }
}
