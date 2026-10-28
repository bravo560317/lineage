package com.lineage.server.model.Instance;

import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GamInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GamInstance.class);
    }
    
    public L1GamInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
        }
        catch (Exception e) {
            L1GamInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onNpcAI() {
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
    }
    
    public void doFinalAction(L1PcInstance player) {
    }
}
