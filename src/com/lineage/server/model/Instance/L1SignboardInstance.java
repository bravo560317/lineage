package com.lineage.server.model.Instance;

import com.lineage.server.serverpackets.S_NPCPack_Signboard;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SignboardInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SignboardInstance.class);
    }
    
    public L1SignboardInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack_Signboard(this));
        }
        catch (Exception e) {
            L1SignboardInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
}
