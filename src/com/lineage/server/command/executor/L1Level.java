package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.RangeInt;
import com.lineage.server.datatables.ExpTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Level implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Level.class);
    }
    
    private L1Level() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Level();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            int level = Integer.parseInt(tok.nextToken());
            if (level == pc.getLevel()) {
                return;
            }
            else if (!RangeInt.includes(level, 1, ExpTable.MAX_LEVEL)) {
                pc.sendPackets(new S_SystemMessage("範圍限制 1~" + ExpTable.MAX_LEVEL));
                return;
            }
            else {
                long nowexp = pc.getExp();
                long levelexp = ExpTable.getExpByLevel(level);
                long add = levelexp - nowexp;
                pc.addExp(add);
                pc.onChangeExp();
            }
        }
        catch (Exception e) {
            L1Level._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
