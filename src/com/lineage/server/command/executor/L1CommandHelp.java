package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Command;
import com.lineage.server.datatables.CommandsTable;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CommandHelp implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CommandHelp.class);
    }
    
    private L1CommandHelp() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1CommandHelp();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        ArrayList<L1Command> list = new ArrayList();
        for (L1Command command : CommandsTable.get().getList()) {
            if (pc == null) {
                if (command.isSystem()) {
                    list.add(command);
                }
                else {
                    continue;
                }
            }
            else if (command.getLevel() <= pc.getAccessLevel()) {
                list.add(command);
            }
            else {
                continue;
            }
        }
        for (L1Command command : list) {
            if (pc == null) {
                L1CommandHelp._log.info("可用命令: " + command.getName() + ": " + command.get_note());
            }
            else {
                pc.sendPackets(new S_ServerMessage(166, command.getName() + ": " + command.get_note()));
            }
        }
        if (pc == null) {
            L1CommandHelp._log.info("可用命令: c: 對遊戲中玩家廣播公告.");
        }
    }
}
