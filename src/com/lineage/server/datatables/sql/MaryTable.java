package com.lineage.server.datatables.sql;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.data.npc.gam.Npc_Mary;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.MaryStorage;

public class MaryTable implements MaryStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(MaryTable.class);
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `w_npc博弈小瑪莉`");
            rs = ps.executeQuery();
            while (rs.next()) {
                long all_stake = rs.getLong("all_stake");
                Npc_Mary.set_all_stake(all_stake);
                long all_user_prize = rs.getLong("all_user_prize");
                Npc_Mary.set_all_user_prize(all_user_prize);
                int out_prize = rs.getInt("out_prize");
                Npc_Mary.set_out_prize(out_prize);
                int item_id = rs.getInt("item_id");
                Npc_Mary.set_itemid(item_id);
                int count = rs.getInt("count");
                Npc_Mary.set_count(count);
                int x_a1 = rs.getInt("x_a1");
                Npc_Mary.set_x_a1(x_a1);
                int x_a2 = rs.getInt("x_a2");
                Npc_Mary.set_x_a2(x_a2);
                int x_b1 = rs.getInt("x_b1");
                Npc_Mary.set_x_b1(x_b1);
                int x_b2 = rs.getInt("x_b2");
                Npc_Mary.set_x_b2(x_b2);
                int x_c1 = rs.getInt("x_c1");
                Npc_Mary.set_x_c1(x_c1);
                int x_c2 = rs.getInt("x_c2");
                Npc_Mary.set_x_c2(x_c2);
                int x_d1 = rs.getInt("x_d1");
                Npc_Mary.set_x_d1(x_d1);
                int x_d2 = rs.getInt("x_d2");
                Npc_Mary.set_x_d2(x_d2);
                int x_e1 = rs.getInt("x_e1");
                Npc_Mary.set_x_e1(x_e1);
                int x_e2 = rs.getInt("x_e2");
                Npc_Mary.set_x_e2(x_e2);
                int x_f1 = rs.getInt("x_f1");
                Npc_Mary.set_x_f1(x_f1);
                int x_f2 = rs.getInt("x_f2");
                Npc_Mary.set_x_f2(x_f2);
                int x_g1 = rs.getInt("x_g1");
                Npc_Mary.set_x_g1(x_g1);
                int x_g2 = rs.getInt("x_g2");
                Npc_Mary.set_x_g2(x_g2);
            }
        }
        catch (SQLException e) {
            MaryTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        MaryTable._log.info("載入小瑪莉設置資料 (" + timer.get() + "ms)");
    }
    
    @Override
    public void update(long all_stake, long all_user_prize, int count) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("UPDATE `w_npc博弈小瑪莉` SET `all_stake`=?,`all_user_prize`=?,`count`=? WHERE `id`=1");
            pm.setLong(1, all_stake);
            pm.setLong(2, all_user_prize);
            pm.setInt(3, count);
            pm.execute();
        }
        catch (SQLException e) {
            MaryTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
}
