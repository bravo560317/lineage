package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Taketreasure;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public final class Taketreasure
{
    private static final Log _log;
    private static Taketreasure _instance;
    private static final ArrayList<L1Taketreasure> _stealList;
    
    static {
        _log = LogFactory.getLog(Taketreasure.class);
        _stealList = new ArrayList();
    }
    
    public static Taketreasure getInstance() {
        if (Taketreasure._instance == null) {
            Taketreasure._instance = new Taketreasure();
        }
        return Taketreasure._instance;
    }
    
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_敵人死亡奪寶");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("道具");
                L1Item temp = ItemTable.get().getTemplate(item_id);
                if (temp == null) {
                    this.del_box(item_id);
                }
                else {
                    int level = rs.getInt("等級");
                    int steal_chance = rs.getInt("奪取機率");
                    int min_steal_count = rs.getInt("奪取最低數量");
                    int max_steal_count = rs.getInt("奪取最高數量");
                    int drop_on_floor = rs.getInt("奪取類型");
                    int anti_steal_item_id = rs.getInt("防止奪取道具編號");
                    String dropmsg = rs.getString("廣播1");
                    String dropmsg2 = rs.getString("廣播2");
                    String dropmsg3 = rs.getString("廣播3");
                    int itemid = rs.getInt("額外獲得道具");
                    int itemcount = rs.getInt("額外獲得道具數量");
                    int deaditemid = rs.getInt("額外噴道具");
                    int deaditemcount = rs.getInt("額外噴道具數量");
                    L1Taketreasure itemSteal = new L1Taketreasure(item_id, level, steal_chance, min_steal_count, max_steal_count, drop_on_floor, anti_steal_item_id, dropmsg, dropmsg2, dropmsg3, itemid, itemcount, deaditemid, deaditemcount);
                    Taketreasure._stealList.add(itemSteal);
                }
            }
        }
        catch (SQLException e) {
            Taketreasure._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void del_box(int id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `系統_敵人死亡奪寶` WHERE `道具`=?");
            ps.setInt(1, id);
            ps.execute();
        }
        catch (SQLException e) {
            Taketreasure._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public final ArrayList<L1Taketreasure> getList() {
        return Taketreasure._stealList;
    }
}
