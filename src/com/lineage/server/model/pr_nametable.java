package com.lineage.server.model;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.collections.Maps;
import java.util.Map;
import java.util.logging.Logger;

public class pr_nametable
{
    private static Logger _log;
    private static pr_nametable _instance;
    private final Map<Integer, pr_name_message> _itemIdIndex;
    
    static {
        pr_nametable._log = Logger.getLogger(pr_nametable.class.getName());
    }
    
    public static pr_nametable getInstance() {
        if (pr_nametable._instance == null) {
            pr_nametable._instance = new pr_nametable();
        }
        return pr_nametable._instance;
    }
    
    private pr_nametable() {
        this._itemIdIndex = (Map)Maps.newHashMap();
        this.loadSystemMessage();
    }
    
    private void loadSystemMessage() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_威望名稱自訂");
            rs = pstm.executeQuery();
            this.fillSystemMessage(rs);
        }
        catch (SQLException e) {
            pr_nametable._log.log(Level.SEVERE, "error while creating w_威望名稱自訂 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillSystemMessage(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int Id = rs.getInt("type");
            String Message = rs.getString("type_name");
            pr_name_message System_Message = new pr_name_message(Id, Message);
            this._itemIdIndex.put(Id, System_Message);
        }
    }
    
    public pr_name_message getTemplate(int Id) {
        return this._itemIdIndex.get((Integer)Id);
    }
}
