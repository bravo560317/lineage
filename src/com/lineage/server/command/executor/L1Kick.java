package com.lineage.server.command.executor;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Kick implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Kick.class);
    }
    
    private L1Kick() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Kick();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1PcInstance target = World.get().getPlayer(arg);
            if (target != null) {
                if (pc == null) {
                    L1Kick._log.warn("系統命令執行: " + cmdName + " 踢除:" + arg + " 下線。");
                }
                else {
                    pc.sendPackets(new S_SystemMessage("踢除:" + arg + " 下線。"));
                }
                this.start(target);
            }
            else if (pc == null) {
                L1Kick._log.error("指令異常: 指定人物不在線上，這個命令必須輸入正確人物名稱才能執行。");
            }
            else {
                int mode = 5;
                pc.sendPackets(new S_PacketBoxGm(pc, 5));
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1Kick._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Kick._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
    
    public void start(L1PcInstance target) {
        ClientExecutor targetClient = target.getNetConnection();
        targetClient.kick();
    }
}
