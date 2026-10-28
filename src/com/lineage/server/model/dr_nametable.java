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

public class dr_nametable {

    private static Logger _log;
    private static dr_nametable _instance;
    private final Map<Integer, dr_name_message> _itemIdIndex;

    static {
        _log = Logger.getLogger(dr_nametable.class.getName());
    }

    public static dr_nametable getInstance() {
        if (_instance == null) {
            _instance = new dr_nametable();
        }
        return _instance;
    }

    private dr_nametable() {
        this._itemIdIndex = Maps.newHashMap();
        this.loadSystemMessage();
    }

    private void loadSystemMessage() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 炫色一_名稱");
            rs = pstm.executeQuery();
            this.fillSystemMessage(rs);
        } catch (SQLException e) {
            _log.log(Level.SEVERE, "error while creating 炫色一_名稱 table", e);
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }

    private void fillSystemMessage(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int Id = rs.getInt("type");
            String Message = rs.getString("type_name");
            dr_name_message System_Message = new dr_name_message(Id, Message);
            this._itemIdIndex.put(Id, System_Message);
        }
    }

    public dr_name_message getTemplate(int Id) {
        return this._itemIdIndex.get(Id);
    }
}
