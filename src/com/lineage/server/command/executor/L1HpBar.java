package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1HpBar implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1HpBar.class);
    }
    
    private L1HpBar() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1HpBar();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        if (arg.equalsIgnoreCase("pc")) {
            pc.setSkillEffect(2003, 0);
        }
        else if (arg.equalsIgnoreCase("on")) {
            pc.setSkillEffect(2001, 0);
        }
        else if (arg.equalsIgnoreCase("off")) {
            pc.removeSkillEffect(2001);
            for (L1Object obj : pc.getKnownObjects()) {
                if (pc.isGmHpBarTarget(obj)) {
                    pc.sendPackets(new S_HPMeter(obj.getId(), 255));
                }
            }
        }
        else {
            L1HpBar._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
    
    public static boolean isHpBarTarget(L1Object obj) {
        return obj instanceof L1MonsterInstance || obj instanceof L1PcInstance || obj instanceof L1SummonInstance || obj instanceof L1PetInstance;
    }
}
