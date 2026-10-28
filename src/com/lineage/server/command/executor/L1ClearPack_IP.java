package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.commons.system.LanSecurityManager;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ClearPack_IP implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1ClearPack_IP();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(arg);
            String name = stringtokenizer.nextToken();
            if (name.equals("all")) {
                LanSecurityManager.BANIPPACK.clear();
                pc.sendPackets(new S_SystemMessage("所有驗證封鎖IP已清除"));
            }
            else if (LanSecurityManager.BANIPPACK.containsKey(name)) {
                LanSecurityManager.BANIPPACK.remove(name);
                pc.sendPackets(new S_SystemMessage("此IP已被移除驗證封鎖清單"));
            }
            else {
                pc.sendPackets(new S_SystemMessage("此IP並沒有被驗證封鎖"));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " ip 請輸入(all，全部清除)。"));
            pc.sendPackets(new S_SystemMessage("驗證封鎖數量：" + LanSecurityManager.BANIPPACK.size() + " 筆"));
        }
    }
}
