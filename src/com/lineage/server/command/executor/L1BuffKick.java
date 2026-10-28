package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1BuffKick implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1BuffKick.class);
    }
    
    private L1BuffKick() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1BuffKick();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            try {
                int objid = Integer.parseInt(arg);
                CharBuffReading.get().deleteBuff(objid);
                if (pc == null) {
                    L1BuffKick._log.warn("系統命令執行: 指定角色objid Buff清除!");
                    return;
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, String.valueOf(objid) + " Buff清除!"));
                    return;
                }
            }
            catch (Exception e) {
                int mode = 0;
                if (pc != null) {
                    pc.sendPackets(new S_PacketBoxGm(pc, 0));
                    return;
                }
                else {
                    return;
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1BuffKick._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1BuffKick._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
