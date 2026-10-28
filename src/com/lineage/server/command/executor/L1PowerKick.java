package com.lineage.server.command.executor;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.commons.system.LanSecurityManager;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1PowerKick implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1PowerKick.class);
    }
    
    private L1PowerKick() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1PowerKick();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            String gmName = "";
            if (pc == null) {
                L1PowerKick._log.warn("系統命令執行: " + cmdName + " " + arg + " 封鎖IP/MAC。");
                gmName = "系統命令";
            }
            else {
                gmName = pc.getName() + "命令";
            }
            if (arg.indexOf("remove") != -1) {
                StringTokenizer st = new StringTokenizer(arg);
                st.nextToken();
                String ipaddr = st.nextToken();
                boolean isBan = false;
                if (LanSecurityManager.BANIPMAP.containsKey(arg)) {
                    isBan = true;
                }
                if (!isBan) {
                    IpReading.get().remove(ipaddr);
                    L1PowerKick._log.warn("系統命令執行: " + cmdName + " " + ipaddr + " 解除封鎖IP/MAC。");
                }
                return;
            }
            else if (arg.lastIndexOf(".") != -1) {
                if (!LanSecurityManager.BANIPMAP.containsKey(arg)) {
                    IpReading.get().add(arg.toString(), gmName + ":L1PowerKick 封鎖IP");
                }
                return;
            }
            else {
                L1PcInstance target = World.get().getPlayer(arg);
                if (target != null) {
                    ClientExecutor targetClient = target.getNetConnection();
                    String ipaddr2 = targetClient.getIp().toString();
                    if (ipaddr2 != null && !LanSecurityManager.BANIPMAP.containsKey(ipaddr2)) {
                        IpReading.get().add(ipaddr2.toString(), gmName + ":L1PowerKick 封鎖IP");
                    }
                    if (pc != null) {
                        pc.sendPackets(new S_SystemMessage(target.getName() + " 封鎖IP/MAC。"));
                    }
                    targetClient.kick();
                }
                else if (pc == null) {
                    L1PowerKick._log.error("指令異常: 這個命令必須輸入正確人物名稱 或是 IP/MAC位置才能執行。");
                }
                else {
                    int mode = 6;
                    pc.sendPackets(new S_PacketBoxGm(pc, 6));
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1PowerKick._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1PowerKick._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
