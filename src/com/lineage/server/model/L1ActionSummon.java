package com.lineage.server.model;

import com.lineage.server.model.Instance.L1SummonInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1ActionSummon
{
    private static final Log _log;
    private final L1PcInstance _pc;
    
    static {
        _log = LogFactory.getLog(L1ActionSummon.class);
    }
    
    public L1ActionSummon(L1PcInstance pc) {
        this._pc = pc;
    }
    
    public L1PcInstance get_pc() {
        return this._pc;
    }
    
    public void action(L1SummonInstance npc, String action) {
        try {
            String status = null;
            if (action.equalsIgnoreCase("aggressive")) {
                status = "1";
            }
            else if (action.equalsIgnoreCase("defensive")) {
                status = "2";
            }
            else if (action.equalsIgnoreCase("stay")) {
                status = "3";
            }
            else if (action.equalsIgnoreCase("extend")) {
                status = "4";
            }
            else if (action.equalsIgnoreCase("alert")) {
                status = "5";
            }
            else if (action.equalsIgnoreCase("dismiss")) {
                status = "6";
            }
            if (status != null) {
                npc.onFinalAction(this._pc, status);
            }
        }
        catch (Exception e) {
            L1ActionSummon._log.error(e.getLocalizedMessage(), e);
        }
    }
}
