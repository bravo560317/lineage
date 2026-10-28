package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1AccountBanKick implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1AccountBanKick.class);
    }
    
    private L1AccountBanKick() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1AccountBanKick();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            String gmName = "";
            if (pc == null) {
                L1AccountBanKick._log.warn("系統命令執行: " + cmdName + " 帳號封鎖:" + arg);
                gmName = "系統命令";
            }
            else {
                gmName = pc.getName() + "命令";
            }
            L1PcInstance target = World.get().getPlayer(arg);
            if (target != null) {
                String info = target.getName() + " 該人物帳號完成封鎖。";
                if (pc == null) {
                    L1AccountBanKick._log.warn(info);
                }
                else {
                    pc.sendPackets(new S_SystemMessage(info));
                }
                this.start(target, gmName + ":L1AccountBanKick 封鎖帳號");
            }
            else {
                boolean account = AccountReading.get().isAccount(arg);
                if (account) {
                    IpReading.get().add(arg, gmName + ":L1AccountBanKick 封鎖帳號");
                    return;
                }
                else if (pc == null) {
                    L1AccountBanKick._log.error("指令異常: 這個命令必須輸入正確帳號名稱才能執行。");
                }
                else {
                    int mode = 7;
                    pc.sendPackets(new S_PacketBoxGm(pc, 7));
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1AccountBanKick._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1AccountBanKick._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
    
    private void start(L1PcInstance target, String info) {
        IpReading.get().add(target.getAccountName(), info);
        target.getNetConnection().kick();
    }
}
