package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Account;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.AccountReading;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Acc implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Acc.class);
    }
    
    private L1Acc() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Acc();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (pc == null) {
                L1Acc._log.warn("系統命令執行: " + cmdName + " 取回指定帳號資料:" + arg);
            }
            StringTokenizer st = new StringTokenizer(arg);
            String accname = st.nextToken();
            L1Account acc = AccountReading.get().getAccount(accname.toLowerCase());
            if (acc != null) {
                String ip = null;
                String mac = null;
                if (acc.get_ip() != null) {
                    ip = acc.get_ip();
                }
                if (acc.get_mac() != null) {
                    mac = acc.get_mac();
                }
                StringBuilder info = new StringBuilder();
                info.append("IP位置:" + ip);
                info.append(" MAC位置:" + mac);
                info.append(" 最後登入時間:" + acc.get_lastactive().toString());
                if (pc == null) {
                    L1Acc._log.warn(info.toString());
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, info.toString()));
                }
                StringBuilder info2 = new StringBuilder();
                info2.append("密碼:" + acc.get_password());
                info2.append(" 超級密碼:" + acc.get_spw());
                info2.append(" 倉庫密碼:" + acc.get_warehouse());
                if (pc == null) {
                    L1Acc._log.warn(info2.toString());
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, info2.toString()));
                }
            }
            else {
                String e = "指令異常: 沒有該帳號(" + accname + ")!!";
                if (pc == null) {
                    L1Acc._log.warn(e);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(166, e));
                }
            }
        }
        catch (Exception e2) {
            if (pc == null) {
                L1Acc._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Acc._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
