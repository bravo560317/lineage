package com.add;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.collections.Maps;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1SystemMessageTable
{
    private static final Log _log;
    private static L1SystemMessageTable _instance;
    private final Map<Integer, L1SystemMessage> _ConfigIndex;
    
    static {
        _log = LogFactory.getLog(L1SystemMessageTable.class);
    }
    
    public L1SystemMessageTable() {
        this._ConfigIndex = (Map)Maps.newHashMap();
    }
    
    public static L1SystemMessageTable get() {
        if (L1SystemMessageTable._instance == null) {
            L1SystemMessageTable._instance = new L1SystemMessageTable();
        }
        return L1SystemMessageTable._instance;
    }
    
    public void loadSystemMessage() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM config_other");
            rs = pstm.executeQuery();
            this.fillSystemMessage(rs);
        }
        catch (SQLException e) {
            L1SystemMessageTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        L1SystemMessageTable._log.info("載入DB化系統設定檔資料數量: " + this._ConfigIndex.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillSystemMessage(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int Id = rs.getInt("id");
            String Message = rs.getString("message");
            Timestamp time = rs.getTimestamp("resetMaptime");
            Calendar resetmaptime = null;
            if (time != null) {
                resetmaptime = this.timestampToCalendar(rs.getTimestamp("resetMaptime"));
            }
            L1SystemMessage System_Message = new L1SystemMessage(Id, Message, resetmaptime);
            this._ConfigIndex.put(Id, System_Message);
        }
    }
    
    public L1SystemMessage getTemplate(int Id) {
        return this._ConfigIndex.get((Integer)Id);
    }
    
    private Calendar timestampToCalendar(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
    
    public void updateResetTime(int id, Calendar reset_cal) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `config_other` SET `resetMaptime`=? WHERE `id`=?");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm = sdf.format(reset_cal.getTime());
            int i = 0;
            pstm.setString(++i, fm);
            pstm.setInt(++i, id);
            pstm.execute();
        }
        catch (Exception e) {
            L1SystemMessageTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
