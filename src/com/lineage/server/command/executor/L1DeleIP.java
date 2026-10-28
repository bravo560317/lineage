package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.commons.system.LanSecurityManager;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1DeleIP implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1DeleIP();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(arg);
            String name = stringtokenizer.nextToken();
            if (name.equals("all")) {
                LanSecurityManager.BANIPPACK.clear();
                pc.sendPackets(new S_SystemMessage("清除驗證封鎖IP"));
            }
            else if (LanSecurityManager.BANIPPACK.containsKey(name)) {
                LanSecurityManager.BANIPPACK.remove(name);
                pc.sendPackets(new S_SystemMessage("IP已被移除封鎖名單(驗正內)"));
            }
            else {
                pc.sendPackets(new S_SystemMessage("此IP目前沒被封鎖"));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage(cmdName + " ip 輸入(all，全部清除)。"));
            pc.sendPackets(new S_SystemMessage("目前-驗證封鎖量：" + LanSecurityManager.BANIPPACK.size() + " 筆"));
        }
    }
}
