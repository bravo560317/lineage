package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1SkillItem;
import java.util.Map;
import org.apache.commons.logging.Log;

public class SkillsItemTable
{
    private static final Log _log;
    private static SkillsItemTable _instance;
    private static final Map<Integer, L1SkillItem> _skills;
    
    static {
        _log = LogFactory.getLog(SkillsItemTable.class);
        _skills = new HashMap();
    }
    
    public static SkillsItemTable get() {
        if (SkillsItemTable._instance == null) {
            SkillsItemTable._instance = new SkillsItemTable();
        }
        return SkillsItemTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `skills_item`");
            rs = pstm.executeQuery();
            this.itemTable(rs);
        }
        catch (SQLException e) {
            SkillsItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SkillsItemTable._log.info("載入購買技能 材料 設置資料數量: " + SkillsItemTable._skills.size() + "(" + timer.get() + "ms)");
    }
    
    private void itemTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            L1SkillItem skillItem = new L1SkillItem();
            int skill_id = rs.getInt("skill_id");
            skillItem.set_skill_id(skill_id);
            String name = rs.getString("name");
            skillItem.set_name(name);
            String items = rs.getString("itemids");
            if (items == null) {
                skillItem.set_items((int[])null);
            }
            else {
                String[] itemsX = items.split(",");
                int[] items_x = new int[itemsX.length];
                for (int i = 0; i < itemsX.length; ++i) {
                    items_x[i] = Integer.parseInt(itemsX[i]);
                }
                skillItem.set_items(items_x);
            }
            String counts = rs.getString("counts");
            if (counts == null) {
                skillItem.set_counts((int[])null);
            }
            else {
                String[] countsX = counts.split(",");
                int[] counts_x = new int[countsX.length];
                for (int j = 0; j < countsX.length; ++j) {
                    counts_x[j] = Integer.parseInt(countsX[j]);
                }
                skillItem.set_counts(counts_x);
            }
            SkillsItemTable._skills.put(new Integer(skill_id), skillItem);
            if (skillItem.get_items() != null) {
                if (skillItem.get_items().length != skillItem.get_counts().length) {
                    SkillsItemTable._log.error("購買技能 材料 設置資料異常 技能編號: " + skill_id);
                    SkillsItemTable._skills.remove(new Integer(skill_id));
                }
                else {
                    continue;
                }
            }
            else {
                SkillsItemTable._skills.remove(new Integer(skill_id));
            }
        }
    }
    
    public L1SkillItem getTemplate(int i) {
        return SkillsItemTable._skills.get(new Integer(i));
    }
}
