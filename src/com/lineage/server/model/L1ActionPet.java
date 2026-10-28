package com.lineage.server.model;

import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_SelectTarget;
import com.lineage.server.model.Instance.L1PetInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1ActionPet
{
    private static final Log _log;
    private final L1PcInstance _pc;
    
    static {
        _log = LogFactory.getLog(L1ActionPet.class);
    }
    
    public L1ActionPet(L1PcInstance pc) {
        this._pc = pc;
    }
    
    public L1PcInstance get_pc() {
        return this._pc;
    }
    
    public void action(L1PetInstance npc, String action) {
        try {
            String status = null;
            if (action.equalsIgnoreCase("attackchr")) {
                int currentPetStatus = npc.getCurrentPetStatus();
                String type = "0";
                switch (currentPetStatus) {
                    case 0:
                    case 4:
                    case 6:
                    case 7: {
                        type = "5";
                        break;
                    }
                    case 1:
                    case 2:
                    case 3:
                    case 5: {
                        type = String.valueOf(currentPetStatus);
                        break;
                    }
                }
                npc.onFinalAction(this._pc, type);
                this._pc.sendPackets(new S_SelectTarget(npc.getId()));
            }
            else if (action.equalsIgnoreCase("aggressive")) {
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
            else if (action.equalsIgnoreCase("getitem")) {
                npc.collect(false);
            }
            else if (action.equalsIgnoreCase("changename")) {
                this._pc.rename(false);
                this._pc.setTempID(npc.getId());
                S_Message_YN pack = new S_Message_YN(325);
                this._pc.sendPackets(pack);
            }
            if (status != null) {
                npc.onFinalAction(this._pc, status);
            }
        }
        catch (Exception e) {
            L1ActionPet._log.error(e.getLocalizedMessage(), e);
        }
    }
}
