package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_GmMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigRate;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Db implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Db.class);
    }
    
    private L1Db() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Db();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int rateXp = Integer.parseInt(st.nextToken(), 10);
            String msgid = null;
            if (pc == null) {
                L1Db._log.warn("系統命令執行: " + cmdName + " 變更經驗值倍率" + arg);
            }
            if (ConfigRate.RATE_XP == (double)rateXp) {
                if (pc == null) {
                    L1Db._log.warn("目前經驗倍率已經是: " + rateXp);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, "目前經驗倍率已經是: " + rateXp));
                }
            }
            else {
                ConfigRate.RATE_XP = (double)rateXp;
                World.get().broadcastPacketToAll(new S_GmMessage("經驗倍率變更為 " + ConfigRate.RATE_XP + " 倍。", "\\aE"));
                if (pc == null) {
                    L1Db._log.warn("目前經驗倍率變更為: " + rateXp);
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1Db._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Db._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
