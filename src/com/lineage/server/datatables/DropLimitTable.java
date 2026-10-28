package com.lineage.server.datatables;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class DropLimitTable
{
    private static final Logger _log;
    private static DropLimitTable INS;
    private Timestamp ts;
    private final Map<Integer, Object[]> _data;
    
    static {
        _log = Logger.getLogger(DropLimitTable.class.getName());
    }
    
    public static DropLimitTable getInstance() {
        if (DropLimitTable.INS == null) {
            DropLimitTable.INS = new DropLimitTable();
        }
        return DropLimitTable.INS;
    }
    
    private DropLimitTable() {
        this._data = new ConcurrentHashMap();
        this.reload();
    }
    
    public final void reload() {
        this._data.clear();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Label_0205: {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("SELECT * FROM 系統_掉落限制_2");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    Object[] data = { this.ts = rs.getTimestamp(6), rs.getInt(3), rs.getInt(4), rs.getInt(5) };
                    if (data[0] != null) {
                        data[0] = this.ts;
                    }
                    this._data.put(id, data);
                }
            }
            catch (SQLException e) {
                DropLimitTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
                break Label_0205;
            }
            finally {
                SQLUtil.close(rs, pstm, con);
            }
        }
        this.ts = new Timestamp(System.currentTimeMillis());
        Iterator<Integer> iterator = this._data.keySet().iterator();
        while (iterator.hasNext()) {
            int itemid = (Integer)iterator.next();
            this.updateTime1(itemid, this._data.get((Integer)itemid), this.ts);
        }
    }
    
    public boolean giveCheck(int i, int j) {
        this.ts.setTime(System.currentTimeMillis());
        boolean flag = false;
        if (this._data.containsKey((Integer)i)) {
            synchronized (DropLimitTable.class) {
                if (this._data.containsKey((Integer)i)) {
                    Object[] aobj = this._data.get((Integer)i);
                    if ((Integer)aobj[2] >= (Integer)aobj[1]) {
                        this.updateTime2(i, aobj, this.ts);
                        flag = true;
                    }
                    else {
                        aobj[2] = (Integer)((Integer)aobj[2] + j);
                        this.updateTime(i, aobj, this.ts);
                        if ((Integer)aobj[2] == (Integer)aobj[1]) {
                            this.updateTime3(i, aobj, this.ts);
                        }
                    }
                }
            }
        }
        return flag;
    }
    
    private void updateTime(int itemid, Object[] info, Timestamp now) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE 系統_掉落限制_2 SET give_count=?, time=? WHERE item_id=?");
            pstm.setObject(1, info[2]);
            pstm.setObject(2, info[0]);
            pstm.setInt(3, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropLimitTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void updateTime2(int itemid, Object[] info, Timestamp now) {
        Timestamp ts = (Timestamp)info[0];
        if (now.after(ts)) {
            info[0] = new Timestamp(System.currentTimeMillis());
            info[2] = 0;
        }
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE 系統_掉落限制_2 SET give_count=?, time=? WHERE item_id=?");
            pstm.setObject(1, info[2]);
            pstm.setObject(2, info[0]);
            pstm.setInt(3, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropLimitTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void updateTime3(int itemid, Object[] info, Timestamp now) {
        int day = (Integer)info[3];
        info[0] = new Timestamp(System.currentTimeMillis() + (long)day * 3600000L);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE 系統_掉落限制_2 SET give_count=?, time=? WHERE item_id=?");
            pstm.setObject(1, info[2]);
            pstm.setObject(2, info[0]);
            pstm.setInt(3, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropLimitTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void updateTime1(int itemid, Object[] info, Timestamp now) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE 系統_掉落限制_2 SET give_count=?, time=? WHERE item_id=?");
            pstm.setObject(1, info[2]);
            pstm.setObject(2, info[0]);
            pstm.setInt(3, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropLimitTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
