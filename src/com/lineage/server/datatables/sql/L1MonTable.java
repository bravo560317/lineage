package com.lineage.server.datatables.sql;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Mon;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1MonTable
{
    private static final Log _log;
    private static final Map<Integer, L1Mon> _mons;
    private static L1MonTable _instance;
    
    static {
        _log = LogFactory.getLog(L1MonTable.class);
        _mons = new HashMap();
    }
    
    public static L1MonTable get() {
        if (L1MonTable._instance == null) {
            L1MonTable._instance = new L1MonTable();
        }
        return L1MonTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_怪物擊殺系統`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int Npc_Mid = rs.getInt("Npc編號");
                int Dorp_Id = rs.getInt("共同攻擊獎勵");
                int Dorp_Lost_Id = rs.getInt("尾刀攻擊者獎勵");
                int x = rs.getInt("共同攻擊者傳送X軸");
                int y = rs.getInt("共同攻擊者傳送Y軸");
                int map = rs.getInt("共同攻擊者傳送地圖編號");
                int x2 = rs.getInt("尾刀攻擊者傳送X軸");
                int y2 = rs.getInt("尾刀攻擊者傳送Y軸");
                int map2 = rs.getInt("尾刀攻擊者傳送地圖編號");
                int pcdmg = rs.getInt("打擊怪物總傷害最低值");
                L1Mon Mon = new L1Mon();
                Mon.setNpcmid(Npc_Mid);
                Mon.setDorpid(Dorp_Id);
                Mon.setDorplostid(Dorp_Lost_Id);
                Mon.setX(x);
                Mon.setY(y);
                Mon.setMap(map);
                Mon.setX1(x2);
                Mon.setY1(y2);
                Mon.setMap1(map2);
                Mon.setpcdmg(pcdmg);
                L1MonTable._mons.put(Npc_Mid, Mon);
            }
        }
        catch (SQLException e) {
            L1MonTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        L1MonTable._log.info("讀取->w_怪物擊殺系統資料: " + L1MonTable._mons.size() + "(" + timer.get() + "ms)");
    }
    
    public L1Mon getMon(int Npc_Mid) {
        return L1MonTable._mons.get((Integer)Npc_Mid);
    }
}
