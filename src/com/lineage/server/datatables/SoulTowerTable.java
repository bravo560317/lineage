package com.lineage.server.datatables;

import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SoulTowerTable
{
    private static Logger _log;
    private static SoulTowerTable _instance;
    private final ArrayList<SoulTowerTable.SoulTowerRank> list;
    
    static {
        SoulTowerTable._log = Logger.getLogger(SoulTowerTable.class.getName());
    }
    
    public static SoulTowerTable getInstance() {
        if (SoulTowerTable._instance == null) {
            SoulTowerTable._instance = new SoulTowerTable();
        }
        return SoulTowerTable._instance;
    }
    
    private SoulTowerTable() {
        this.list = new ArrayList();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT *FROM soul_tower_rank");
            rs = pstm.executeQuery();
            while (rs.next()) {
                SoulTowerTable.SoulTowerRank rank = new SoulTowerTable.SoulTowerRank();
                rank.name = rs.getString("name");
                rank.classType = rs.getInt("class");
                rank.time = rs.getInt("time");
                rank.date = rs.getDate("date").getTime();
                this.list.add(rank);
            }
        }
        catch (SQLException e) {
            SoulTowerTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
    }
    
    public void updateRank(L1PcInstance pc, int time) {
        boolean isBestter = false;
        for (SoulTowerTable.SoulTowerRank rank : this.list) {
            if (time < rank.time || this.list.size() < 10) {
                isBestter = true;
                break;
            }
        }
        if (isBestter) {
            SoulTowerTable.SoulTowerRank rank = new SoulTowerTable.SoulTowerRank();
            rank.name = pc.getName();
            rank.classType = pc.getType();
            rank.time = time;
            Date now = new Date();
            java.sql.Date sqlDate = new java.sql.Date(now.getTime());
            rank.date = sqlDate.getTime();
            this.list.add(rank);
            Collections.sort(this.list, new Comparator<SoulTowerTable.SoulTowerRank>() {
                @Override
                public int compare(SoulTowerTable.SoulTowerRank r1, SoulTowerTable.SoulTowerRank r2) {
                    return r1.time - r2.time;
                }
            });
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM soul_tower_rank WHERE rank >0");
                pstm.execute();
                int i = 0;
                while (i < this.list.size()) {
                    if (i >= 10) {
                        break;
                    }
                    else {
                        SoulTowerTable.SoulTowerRank str = this.list.get(i);
                        pstm = con.prepareStatement("INSERT INTO soul_tower_rank  SET rank=?,name=?,class=?,time=?,date=?");
                        pstm.setInt(1, i + 1);
                        pstm.setString(2, str.name);
                        pstm.setInt(3, str.classType);
                        pstm.setInt(4, str.time);
                        Date utilDate = new Date();
                        utilDate.setTime(str.date);
                        pstm.setDate(5, new java.sql.Date(utilDate.getTime()));
                        pstm.execute();
                        ++i;
                    }
                }
            }
            catch (SQLException e) {
                SoulTowerTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
    }
    
    public class SoulTowerRank
    {
        public String name;
        public int classType;
        public int time;
        public long date;
    }
}
