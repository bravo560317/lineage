package com.lineage.server;

import java.io.IOException;
import com.lineage.server.command.GMCommands;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.lineage.server.utils.SystemUtil;
import com.lineage.list.OnlineUser;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class CmdEcho
{
    private static final Log _log;
    private static final boolean _msg = false;
    private static final String _t1 = "\n\r--------------------------------------------------";
    private static final String _t2 = "\n\r--------------------------------------------------";
    
    static {
        _log = LogFactory.getLog(CmdEcho.class);
    }
    
    public CmdEcho(long timer) {
        String info = "\n\r--------------------------------------------------" + ((timer != 0L) ? ("\n       啟動指令視窗監聽器!!遊戲伺服器啟動完成!!\n       服務器啟動耗用時間: " + timer + "ms") : ("\n       目前連線帳號: " + OnlineUser.get().size())) + "\n\r--------------------------------------------------";
        CmdEcho._log.warn(info);
        SystemUtil.printMemoryUsage(CmdEcho._log);
    }
    
    public void runCmd() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str = br.readLine();
            GMCommands.getInstance().handleCommands(str);
            this.reRunCmd();
        }
        catch (IOException e) {
            CmdEcho._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void reRunCmd() {
        CmdEcho cmdEcho = new CmdEcho(0L);
        cmdEcho.runCmd();
    }
}
