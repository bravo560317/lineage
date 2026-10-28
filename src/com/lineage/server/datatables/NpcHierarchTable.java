package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Hierarch;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public class NpcHierarchTable
{
    private static final Log _log;
    private static NpcHierarchTable _instance;
    private static final HashMap<Integer, L1Hierarch> _powerMap;
    
    static {
        _log = LogFactory.getLog(NpcHierarchTable.class);
        _powerMap = new HashMap();
    }
    
    public static NpcHierarchTable get() {
        if (NpcHierarchTable._instance == null) {
            NpcHierarchTable._instance = new NpcHierarchTable();
        }
        return NpcHierarchTable._instance;
    }
    
    private NpcHierarchTable() {
        this.load();
    }
    
    private void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_技師補助跟隨`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int npc_id = rs.getInt("npc_id");
                String skill_id = rs.getString("skill_id").replaceAll(" ", "");
                String skill_mp = rs.getString("skill_mp").replaceAll(" ", "");
                int[] SkillId = (int[])null;
                if (skill_id != null && !skill_id.equals("")) {
                    String[] set2 = skill_id.split(",");
                    SkillId = new int[set2.length];
                    for (int i = 0; i < set2.length; ++i) {
                        SkillId[i] = Integer.parseInt(set2[i]);
                    }
                }
                int[] SkillMp = (int[])null;
                if (skill_mp != null && !skill_mp.equals("")) {
                    String[] set3 = skill_mp.split(",");
                    SkillMp = new int[set3.length];
                    for (int j = 0; j < set3.length; ++j) {
                        SkillMp[j] = Integer.parseInt(set3[j]);
                    }
                }
                L1Hierarch hierarch = new L1Hierarch();
                hierarch.setSkillId(SkillId);
                hierarch.setSkillMp(SkillMp);
                NpcHierarchTable._powerMap.put(npc_id, hierarch);
            }
        }
        catch (SQLException e) {
            NpcHierarchTable._log.error(e.getLocalizedMessage(), e);
        }
        catch (Exception e2) {
            NpcHierarchTable._log.error(e2.getLocalizedMessage(), e2);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        NpcHierarchTable._log.info("載入祭司能力資料數量: " + NpcHierarchTable._powerMap.size() + "(" + timer.get() + "ms)");
    }
    
    public L1Hierarch getHierarch(int key) {
        if (NpcHierarchTable._powerMap.containsKey((Integer)key)) {
            return NpcHierarchTable._powerMap.get((Integer)key);
        }
        else {
            return null;
        }
    }
}
