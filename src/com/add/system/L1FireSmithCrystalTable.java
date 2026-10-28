package com.add.system;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1FireSmithCrystalTable
{
    private static final Log _log;
    private static L1FireSmithCrystalTable _instance;
    private final Map<Integer, L1FireCrystal> _FireCrystalIndex;
    
    static {
        _log = LogFactory.getLog(L1FireSmithCrystalTable.class);
    }
    
    public L1FireSmithCrystalTable() {
        this._FireCrystalIndex = new HashMap();
    }
    
    public static L1FireSmithCrystalTable get() {
        if (L1FireSmithCrystalTable._instance == null) {
            L1FireSmithCrystalTable._instance = new L1FireSmithCrystalTable();
        }
        return L1FireSmithCrystalTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM system_firesmith_crystal");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                int enchant_lv0 = rs.getInt("enchant_lv0");
                int enchant_lv2 = rs.getInt("enchant_lv1");
                int enchant_lv3 = rs.getInt("enchant_lv2");
                int enchant_lv4 = rs.getInt("enchant_lv3");
                int enchant_lv5 = rs.getInt("enchant_lv4");
                int enchant_lv6 = rs.getInt("enchant_lv5");
                int enchant_lv7 = rs.getInt("enchant_lv6");
                int enchant_lv8 = rs.getInt("enchant_lv7");
                int enchant_lv9 = rs.getInt("enchant_lv8");
                int enchant_lv10 = rs.getInt("enchant_lv9");
                int enchant_lv11 = rs.getInt("enchant_lv10");
                int enchant_lv12 = rs.getInt("enchant_lv11");
                int enchant_lv13 = rs.getInt("enchant_lv12");
                int enchant_lv14 = rs.getInt("enchant_lv13");
                int enchant_lv15 = rs.getInt("enchant_lv14");
                L1FireCrystal fireCrystal = new L1FireCrystal(itemid, enchant_lv0, enchant_lv2, enchant_lv3, enchant_lv4, enchant_lv5, enchant_lv6, enchant_lv7, enchant_lv8, enchant_lv9, enchant_lv10, enchant_lv11, enchant_lv12, enchant_lv13, enchant_lv14, enchant_lv15);
                this._FireCrystalIndex.put(itemid, fireCrystal);
            }
        }
        catch (SQLException e) {
            L1FireSmithCrystalTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        L1FireSmithCrystalTable._log.info("載入火神融煉道具資料數量: " + this._FireCrystalIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public L1FireCrystal getTemplate(int itemid) {
        return this._FireCrystalIndex.get((Integer)itemid);
    }
}
