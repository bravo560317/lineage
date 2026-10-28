package com.eric;

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

public final class StartCheckWarTime
{
    private static Logger _log;
    private static StartCheckWarTime _instance;
    private final Map<Integer, StartCheckWarTime.Data> _check;
    
    static {
        StartCheckWarTime._log = Logger.getLogger(StartCheckWarTime.class.getName());
    }
    
    private StartCheckWarTime() {
        this._check = (Map)Maps.newHashMap();
        this.loadStartCheckWarTimeFromDatabase();
    }
    
    private void loadStartCheckWarTimeFromDatabase() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM eric_startcheckwartime");
            rs = pstm.executeQuery();
            while (rs.next()) {
                StartCheckWarTime.Data data = new StartCheckWarTime.Data();
                int id = rs.getInt("castle_id");
                rs.getString("name");
                data._isActive = rs.getBoolean("isActive");
                this._check.put(new Integer(id), data);
            }
            StartCheckWarTime._log.config("StartCheckWarTime " + this._check.size());
        }
        catch (SQLException e) {
            e.printStackTrace();
            StartCheckWarTime._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public static StartCheckWarTime getInstance() {
        if (StartCheckWarTime._instance == null) {
            StartCheckWarTime._instance = new StartCheckWarTime();
        }
        return StartCheckWarTime._instance;
    }
    
    public boolean isActive(int castleId) {
        StartCheckWarTime.Data data = this._check.get((Integer)castleId);
        return data == null || (this._check.get((Integer)castleId))._isActive;
    }
    
    private class Data
    {
        public boolean _isActive;
        
        private Data() {
            this._isActive = true;
        }
    }
}
