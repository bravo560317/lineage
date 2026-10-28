package com.lineage.server.command.executor;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SKick implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SKick.class);
    }
    
    private L1SKick() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1SKick();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1SKick._log.warn("系統命令執行: " + cmdName + " " + arg + " 解除卡點。");
            }
            L1PcInstance target = World.get().getPlayer(arg);
            if (target != null) {
                String info = "進行 人物:" + target.getName() + " 解除卡點作業。";
                if (pc == null) {
                    L1SKick._log.warn(info);
                }
                else {
                    pc.sendPackets(new S_SystemMessage(info));
                }
                target.setX(33080);
                target.setY(33392);
                target.setMap((short)4);
                ClientExecutor targetClient = target.getNetConnection();
                targetClient.kick();
            }
            else if (pc == null) {
                L1SKick._log.warn(arg + " 不在線上。");
            }
            else {
                pc.sendPackets(new S_ServerMessage(73, arg));
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1SKick._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1SKick._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
