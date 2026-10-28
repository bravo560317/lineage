package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.clientpackets.AcceleratorChecker;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SpeedCheck implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SpeedCheck.class);
    }
    
    private L1SpeedCheck() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1SpeedCheck();
    }
    
    public Calendar getRealTime() {
        TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(_tz);
        return cal;
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            double set = AcceleratorChecker.CHECK_STRICTNESS = Double.parseDouble(arg);
            if (pc == null) {
                L1SpeedCheck._log.warn("系統命令執行: " + cmdName + "設置防加速允許範圍值" + set + "倍。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("設置防加速允許範圍值" + set + "倍。"));
            }
        }
        catch (Exception e) {
            L1SpeedCheck._log.error(e.getLocalizedMessage(), e);
            if (pc == null) {
                L1SpeedCheck._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1SpeedCheck._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
