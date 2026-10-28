package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.model.Instance.L1TrapInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ShowTrap implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ShowTrap.class);
    }
    
    private L1ShowTrap() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ShowTrap();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc.hasSkillEffect(2002)) {
                pc.removeSkillEffect(2002);
                for (L1Object obj : pc.getKnownObjects()) {
                    if (obj instanceof L1TrapInstance) {
                        pc.removeKnownObject(obj);
                        pc.sendPackets(new S_RemoveObject(obj));
                    }
                }
            }
            else {
                pc.setSkillEffect(2002, 0);
            }
        }
        catch (Exception e) {
            L1ShowTrap._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
