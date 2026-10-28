package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import java.util.logging.Logger;

public class SystemMessage
{
    private static Logger _log;
    private static SystemMessage _instance;
    private final HashMap<Integer, L1WilliamSystemMessage> _itemIdIndex;
    
    static {
        SystemMessage._log = Logger.getLogger(SystemMessage.class.getName());
    }
    
    public static SystemMessage getInstance() {
        if (SystemMessage._instance == null) {
            SystemMessage._instance = new SystemMessage();
        }
        return SystemMessage._instance;
    }
    
    private SystemMessage() {
        this._itemIdIndex = new HashMap();
        this.loadSystemMessage();
    }
    
    private void loadSystemMessage() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_遊戲內文字控制");
            rs = pstm.executeQuery();
            this.fillSystemMessage(rs);
        }
        catch (SQLException e) {
            SystemMessage._log.log(Level.SEVERE, "error while creating 系統_遊戲內文字控制 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillSystemMessage(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int Id = rs.getInt("id");
            String Message = rs.getString("message");
            L1WilliamSystemMessage System_Message = new L1WilliamSystemMessage(Id, Message);
            this._itemIdIndex.put(Id, System_Message);
        }
    }
    
    public L1WilliamSystemMessage getTemplate(int Id) {
        return this._itemIdIndex.get((Integer)Id);
    }
}
