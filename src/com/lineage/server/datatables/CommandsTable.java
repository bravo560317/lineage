package com.lineage.server.datatables;

import java.util.Collection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Command;
import java.util.Map;
import org.apache.commons.logging.Log;

public class CommandsTable
{
    private static final Log _log;
    private static final Map<String, L1Command> _commandList;
    private static CommandsTable _instance;
    
    static {
        _log = LogFactory.getLog(CommandsTable.class);
        _commandList = new HashMap();
    }
    
    public static CommandsTable get() {
        if (CommandsTable._instance == null) {
            CommandsTable._instance = new CommandsTable();
        }
        return CommandsTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `commands`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int access_level = rs.getInt("access_level");
                String class_name = rs.getString("class_name");
                String note = rs.getString("note");
                boolean system = rs.getBoolean("system");
                L1Command command = new L1Command(name, system, access_level, class_name, note);
                CommandsTable._commandList.put(name.toLowerCase(), command);
            }
        }
        catch (SQLException e) {
            CommandsTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        CommandsTable._log.info("載入管理者命令數量: " + CommandsTable._commandList.size() + "(" + timer.get() + "ms)");
    }
    
    public L1Command get(String name) {
        return CommandsTable._commandList.get(name);
    }
    
    public Collection<L1Command> getList() {
        return (Collection)CommandsTable._commandList.values();
    }
}
