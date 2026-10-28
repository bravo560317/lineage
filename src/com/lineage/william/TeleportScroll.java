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

public class TeleportScroll
{
    private static Logger _log;
    private static TeleportScroll _instance;
    private final HashMap<Integer, L1WilliamTeleportScroll> _itemIdIndex;
    
    static {
        TeleportScroll._log = Logger.getLogger(TeleportScroll.class.getName());
    }
    
    public static TeleportScroll getInstance() {
        if (TeleportScroll._instance == null) {
            TeleportScroll._instance = new TeleportScroll();
        }
        return TeleportScroll._instance;
    }
    
    private TeleportScroll() {
        this._itemIdIndex = new HashMap();
        this.loadTeleportScroll();
    }
    
    private void loadTeleportScroll() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM william_teleport_scroll");
            rs = pstm.executeQuery();
            this.fillTeleportScroll(rs);
        }
        catch (SQLException e) {
            TeleportScroll._log.log(Level.SEVERE, "error while creating william_teleport_scroll table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillTeleportScroll(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int itemId = rs.getInt("item_id");
            int tpLocX = rs.getInt("tpLocX");
            int tpLocY = rs.getInt("tpLocY");
            short tpMapId = rs.getShort("tpMapId");
            int check_minLocX = rs.getInt("check_minLocX");
            int check_minLocY = rs.getInt("check_minLocY");
            int check_maxLocX = rs.getInt("check_maxLocX");
            int check_maxLocY = rs.getInt("check_maxLocY");
            short check_MapId = rs.getShort("check_MapId");
            int removeItem = rs.getInt("removeItem");
            L1WilliamTeleportScroll teleport_scroll = new L1WilliamTeleportScroll(itemId, tpLocX, tpLocY, tpMapId, check_minLocX, check_minLocY, check_maxLocX, check_maxLocY, check_MapId, removeItem);
            this._itemIdIndex.put(itemId, teleport_scroll);
        }
    }
    
    public L1WilliamTeleportScroll getTemplate(int itemId) {
        return this._itemIdIndex.get((Integer)itemId);
    }
}
