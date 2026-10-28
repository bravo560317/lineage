package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Account;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ChangPassword implements L1CommandExecutor
{
    private L1ChangPassword() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ChangPassword();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            String password = st.nextToken();
            L1PcInstance target = World.get().getPlayer(name);
            String newPassword = "";
            newPassword = password;
            if (target != null) {
                L1Account acc = target.getNetConnection().getAccount();
                acc.set_password(newPassword);
                AccountReading.get().updatePwd(acc.get_login(), newPassword);
                pc.sendPackets(new S_SystemMessage("玩家【" + target.getName() + "】的密碼以被修改為【" + password + "】"));
                target.sendPackets(new S_SystemMessage("你的密碼以被修改GM為【" + password + "】"));
            }
            else {
                pc.sendPackets(new S_SystemMessage(name + "不在線上。"));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " 請輸入玩家名稱 密碼 ←請輸入。"));
        }
    }
}
