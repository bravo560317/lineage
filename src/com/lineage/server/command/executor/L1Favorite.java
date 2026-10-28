package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.command.GMCommands;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1Favorite implements L1CommandExecutor
{
    private static final Log _log;
    private static final Map<Integer, String> _faviCom;
    
    static {
        _log = LogFactory.getLog(L1Favorite.class);
        _faviCom = new HashMap();
    }
    
    private L1Favorite() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Favorite();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (!L1Favorite._faviCom.containsKey((Integer)pc.getId())) {
                L1Favorite._faviCom.put(pc.getId(), "");
            }
            String faviCom = L1Favorite._faviCom.get((Integer)pc.getId());
            if (arg.startsWith("set")) {
                StringTokenizer st = new StringTokenizer(arg);
                st.nextToken();
                if (!st.hasMoreTokens()) {
                    pc.sendPackets(new S_SystemMessage("紀錄質不能為空白"));
                    return;
                }
                else {
                    StringBuilder cmd = new StringBuilder();
                    String temp = st.nextToken();
                    if (temp.equalsIgnoreCase(cmdName)) {
                        pc.sendPackets(new S_SystemMessage(cmdName + " 紀錄質異常。"));
                        return;
                    }
                    else {
                        cmd.append(temp + " ");
                        while (st.hasMoreTokens()) {
                            cmd.append(st.nextToken() + " ");
                        }
                        faviCom = cmd.toString().trim();
                        L1Favorite._faviCom.put(pc.getId(), faviCom);
                        pc.sendPackets(new S_SystemMessage(faviCom + " 指令紀錄完成!"));
                    }
                }
            }
            else if (arg.startsWith("show")) {
                pc.sendPackets(new S_SystemMessage("目前紀錄的指令: " + faviCom));
            }
            else if (faviCom.isEmpty()) {
                pc.sendPackets(new S_SystemMessage("目前無紀錄指令!"));
            }
            else {
                StringBuilder cmd2 = new StringBuilder();
                StringTokenizer st2 = new StringTokenizer(arg);
                StringTokenizer st3 = new StringTokenizer(faviCom);
                while (st3.hasMoreTokens()) {
                    String temp2 = st3.nextToken();
                    if (temp2.startsWith("%")) {
                        cmd2.append(st2.nextToken() + " ");
                    }
                    else {
                        cmd2.append(temp2 + " ");
                    }
                }
                while (st2.hasMoreTokens()) {
                    cmd2.append(st2.nextToken() + " ");
                }
                pc.sendPackets(new S_SystemMessage(cmd2 + " 指令執行。"));
                GMCommands.getInstance().handleCommands(pc, cmd2.toString());
            }
        }
        catch (Exception e) {
            L1Favorite._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
