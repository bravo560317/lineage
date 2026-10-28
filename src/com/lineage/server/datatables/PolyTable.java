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
import com.lineage.server.model.L1PolyMorph;
import java.util.Map;
import org.apache.commons.logging.Log;

public class PolyTable
{
    private static final Log _log;
    private static PolyTable _instance;
    private static final Map<String, L1PolyMorph> _polymorphs;
    private static final Map<Integer, L1PolyMorph> _polyIdIndex;
    
    static {
        _log = LogFactory.getLog(PolyTable.class);
        _polymorphs = new HashMap();
        _polyIdIndex = new HashMap();
    }
    
    public static PolyTable get() {
        if (PolyTable._instance == null) {
            PolyTable._instance = new PolyTable();
        }
        return PolyTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM polymorphs");
            rs = ps.executeQuery();
            this.fillPolyTable(rs);
        }
        catch (SQLException e) {
            PolyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        PolyTable._log.info("載入人物變身資料數量: " + PolyTable._polymorphs.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillPolyTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int polyId = rs.getInt("polyid");
            int minLevel = rs.getInt("minlevel");
            int weaponEquipFlg = rs.getInt("weaponequip");
            int armorEquipFlg = rs.getInt("armorequip");
            boolean canUseSkill = rs.getBoolean("isSkillUse");
            int causeFlg = rs.getInt("cause");
            int arrowgifx = rs.getInt("卡冊編號");
            L1PolyMorph poly = new L1PolyMorph(id, name, polyId, minLevel, weaponEquipFlg, armorEquipFlg, canUseSkill, causeFlg, arrowgifx);
            PolyTable._polymorphs.put(name, poly);
            PolyTable._polyIdIndex.put(polyId, poly);
        }
    }
    
    public L1PolyMorph getTemplate(String name) {
        return PolyTable._polymorphs.get(name);
    }
    
    public L1PolyMorph getTemplate(int polyId) {
        return PolyTable._polyIdIndex.get((Integer)polyId);
    }
}
