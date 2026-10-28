package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ChangeWeather implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ChangeWeather.class);
    }
    
    private L1ChangeWeather() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ChangeWeather();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1ChangeWeather._log.warn("系統命令執行: " + cmdName + " " + arg + " 遊戲天氣控制。");
                L1ChangeWeather._log.info("說明: 0 終止氣候。");
                L1ChangeWeather._log.info("說明: 1~3 雪控制。");
                L1ChangeWeather._log.info("說明: 17~19 雨控制");
            }
            StringTokenizer tok = new StringTokenizer(arg);
            int weather = Integer.parseInt(tok.nextToken());
            World.get().setWeather(weather);
            World.get().broadcastPacketToAll(new S_Weather(weather));
        }
        catch (Exception e) {
            if (pc == null) {
                L1ChangeWeather._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1ChangeWeather._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
