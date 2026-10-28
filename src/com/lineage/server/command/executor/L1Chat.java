package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Chat implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Chat.class);
    }
    
    private L1Chat() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Chat();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            if (st.hasMoreTokens()) {
                String flag = st.nextToken();
                String msg;
                if (flag.compareToIgnoreCase("on") == 0) {
                    World.get().set_worldChatElabled(true);
                    msg = "取消廣播限制。";
                }
                else if (flag.compareToIgnoreCase("off") == 0) {
                    World.get().set_worldChatElabled(false);
                    msg = "設置廣播限制。";
                }
                else {
                    throw new Exception();
                }
                if (pc == null) {
                    L1Chat._log.warn("系統命令執行: " + cmdName + " " + arg + " " + msg);
                }
                else {
                    pc.sendPackets(new S_SystemMessage(msg));
                }
            }
            else {
                String msg2;
                if (World.get().isWorldChatElabled()) {
                    msg2 = "目前未暫停廣播使用。.chat off 可以設置廣播限制。";
                }
                else {
                    msg2 = "目前暫停廣播使用。.chat on 可以取消廣播限制。";
                }
                if (pc == null) {
                    L1Chat._log.warn("指令異常: " + msg2);
                }
                else {
                    pc.sendPackets(new S_SystemMessage(msg2));
                }
            }
        }
        catch (Exception e) {
            if (pc == null) {
                L1Chat._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Chat._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
