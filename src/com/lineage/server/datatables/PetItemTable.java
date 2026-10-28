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
import com.lineage.server.templates.L1PetItem;
import java.util.Map;
import org.apache.commons.logging.Log;

public class PetItemTable
{
    private static final Log _log;
    private static PetItemTable _instance;
    private static final Map<Integer, L1PetItem> _petItemIdIndex;
    
    static {
        _log = LogFactory.getLog(PetItemTable.class);
        _petItemIdIndex = new HashMap();
    }
    
    public static PetItemTable get() {
        if (PetItemTable._instance == null) {
            PetItemTable._instance = new PetItemTable();
        }
        return PetItemTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM petitem");
            rs = pstm.executeQuery();
            this.fillPetItemTable(rs);
        }
        catch (SQLException e) {
            PetItemTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        PetItemTable._log.info("載入寵物道具資料數量: " + PetItemTable._petItemIdIndex.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillPetItemTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            L1PetItem petItem = new L1PetItem();
            petItem.setItemId(rs.getInt("item_id"));
            petItem.setHitModifier(rs.getInt("hitmodifier"));
            petItem.setDamageModifier(rs.getInt("dmgmodifier"));
            petItem.setAddAc(rs.getInt("ac"));
            petItem.setAddStr(rs.getInt("add_str"));
            petItem.setAddCon(rs.getInt("add_con"));
            petItem.setAddDex(rs.getInt("add_dex"));
            petItem.setAddInt(rs.getInt("add_int"));
            petItem.setAddWis(rs.getInt("add_wis"));
            petItem.setAddHp(rs.getInt("add_hp"));
            petItem.setAddMp(rs.getInt("add_mp"));
            petItem.setAddSp(rs.getInt("add_sp"));
            petItem.setAddMr(rs.getInt("m_def"));
            petItem.setWeapom(rs.getBoolean("isweapon"));
            petItem.setHigher(rs.getBoolean("ishigher"));
            PetItemTable._petItemIdIndex.put(petItem.getItemId(), petItem);
        }
    }
    
    public L1PetItem getTemplate(int itemId) {
        return PetItemTable._petItemIdIndex.get((Integer)itemId);
    }
}
