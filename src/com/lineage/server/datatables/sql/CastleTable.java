package com.lineage.server.datatables.sql;

import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Castle;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CastleStorage;

public class CastleTable implements CastleStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Castle> _castles;
    
    static {
        _log = LogFactory.getLog(CastleTable.class);
        _castles = new HashMap();
    }
    
    private Calendar timestampToCalendar(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_castle`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int castle_id = rs.getInt("castle_id");
                String name = rs.getString("name");
                Calendar war_time = this.timestampToCalendar(rs.getTimestamp("war_time"));
                int tax_rate = rs.getInt("tax_rate");
                long public_money = rs.getLong("public_money");
                L1Castle castle = new L1Castle(castle_id, name);
                castle.setWarTime(war_time);
                castle.setTaxRate(tax_rate);
                castle.setPublicMoney(public_money);
                CastleTable._castles.put(castle_id, castle);
            }
        }
        catch (SQLException e) {
            CastleTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        CastleTable._log.info("載入城堡資料數量: " + CastleTable._castles.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public Map<Integer, L1Castle> getCastleMap() {
        return CastleTable._castles;
    }
    
    @Override
    public L1Castle[] getCastleTableList() {
        return (L1Castle[])CastleTable._castles.values().toArray(new L1Castle[CastleTable._castles.size()]);
    }
    
    @Override
    public L1Castle getCastleTable(int id) {
        return CastleTable._castles.get((Integer)id);
    }
    
    @Override
    public void updateCastle(L1Castle castle) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `server_castle` SET `name`=?,`war_time`=?,`tax_rate`=?,`public_money`=? WHERE `castle_id`=?");
            int i = 0;
            pstm.setString(++i, castle.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm = sdf.format(castle.getWarTime().getTime());
            pstm.setString(++i, fm);
            pstm.setInt(++i, castle.getTaxRate());
            pstm.setLong(++i, castle.getPublicMoney());
            pstm.setInt(++i, castle.getId());
            pstm.execute();
            CastleTable._castles.put(castle.getId(), castle);
        }
        catch (SQLException e) {
            CastleTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
