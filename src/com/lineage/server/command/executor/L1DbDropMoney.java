package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_GmMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigRate;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1DbDropMoney implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1DbDropMoney.class);
    }
    
    private L1DbDropMoney() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1DbDropMoney();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int rateDrop = Integer.parseInt(st.nextToken(), 10);
            String msgid = null;
            if (pc == null) {
                L1DbDropMoney._log.warn("系統命令執行: " + cmdName + " 變更掉錢率" + arg);
            }
            if ((int)ConfigRate.RATE_DROP_ADENA == rateDrop) {
                if (pc == null) {
                    L1DbDropMoney._log.warn("目前掉錢率已經是: " + rateDrop);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, "目前掉錢率已經是:" + rateDrop));
                }
                return;
            }
            else {
                if (ConfigRate.RATE_DROP_ADENA < (double)rateDrop) {
                    ConfigRate.RATE_DROP_ADENA = (double)rateDrop;
                    msgid = "金幣倍率變更為" + ConfigRate.RATE_DROP_ADENA + "倍。";
                }
                else if (ConfigRate.RATE_DROP_ADENA > (double)rateDrop) {
                    ConfigRate.RATE_DROP_ADENA = (double)rateDrop;
                    msgid = "金幣倍率恢復為" + ConfigRate.RATE_DROP_ADENA + "倍。";
                }
                if (msgid != null) {
                    World.get().broadcastPacketToAll(new S_GmMessage(msgid, "\\aE"));
                }
                if (pc == null) {
                    L1DbDropMoney._log.warn("目前掉錢率變更為: " + rateDrop);
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1DbDropMoney._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1DbDropMoney._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
