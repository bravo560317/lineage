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

public class L1BlendTable_1
{
    private static final Log _log;
    private static L1BlendTable_1 _instance;
    private final Map<String, L1Blend1> _CraftIndex;
    private final Map<String, String> _CraftList;
    
    static {
        _log = LogFactory.getLog(L1BlendTable_1.class);
    }
    
    public L1BlendTable_1() {
        this._CraftIndex = new HashMap();
        this._CraftList = new HashMap();
    }
    
    public static L1BlendTable_1 getInstance() {
        if (L1BlendTable_1._instance == null) {
            L1BlendTable_1._instance = new L1BlendTable_1();
        }
        return L1BlendTable_1._instance;
    }
    
    public void loadBlendTable() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_npc成就圖鑑收集");
            rs = pstm.executeQuery();
            this.fillBlendTable(rs);
        }
        catch (SQLException e) {
            L1BlendTable_1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        L1BlendTable_1._log.info("載入成就收集資料數量: " + this._CraftIndex.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillBlendTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int npcid = rs.getInt("npcid");
            String action = rs.getString("action");
            String note = rs.getString("note");
            int checkLevel = rs.getInt("checkLevel");
            int checkClass = rs.getInt("checkClass");
            String materials = rs.getString("materials").replaceAll(" ", "");
            String[] needids_tmp = materials.split(",");
            int[] needids = new int[needids_tmp.length];
            for (int i = 0; i < needids_tmp.length; ++i) {
                needids[i] = Integer.parseInt(needids_tmp[i]);
            }
            String materials_count = rs.getString("materials_count").replaceAll(" ", "");
            String[] needcounts_tmp = materials_count.split(",");
            int[] needcounts = new int[needcounts_tmp.length];
            for (int j = 0; j < needcounts_tmp.length; ++j) {
                needcounts[j] = Integer.parseInt(needcounts_tmp[j]);
            }
            String materials_enchants = rs.getString("materials_enchants").replaceAll(" ", "");
            String[] needenchants_tmp = materials_enchants.split(",");
            int[] needenchants = new int[needenchants_tmp.length];
            for (int k = 0; k < needenchants_tmp.length; ++k) {
                needenchants[k] = Integer.parseInt(needenchants_tmp[k]);
            }
            String sucesshtml = rs.getString("sucess_html");
            String failhtml = rs.getString("fail_html");
            String Allmessage = rs.getString("Allmessage");
            int quest = rs.getInt("quest");
            String givebuff = rs.getString("能力說明");
            L1Blend1 Item_Blend1 = new L1Blend1();
            Item_Blend1.set_npcid(npcid);
            Item_Blend1.set_action(action);
            Item_Blend1.set_note(note);
            Item_Blend1.setCheckLevel(checkLevel);
            Item_Blend1.setCheckClass(checkClass);
            Item_Blend1.setMaterials(needids);
            Item_Blend1.setMaterials_count(needcounts);
            Item_Blend1.set_materials_enchants(needenchants);
            Item_Blend1.set_sucesshtml(sucesshtml);
            Item_Blend1.set_failhtml(failhtml);
            Item_Blend1.set_Allmessage(Allmessage);
            Item_Blend1.setquest(quest);
            Item_Blend1.set_givebuff(givebuff);
            String key = String.valueOf(npcid) + action;
            this._CraftIndex.put(key, Item_Blend1);
            this.loadCraftList(npcid);
        }
    }
    
    private void loadCraftList(int npcid) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_npc成就圖鑑收集` WHERE `npcid` =?");
            pstm.setInt(1, npcid);
            rs = pstm.executeQuery();
            while (rs.next()) {
                String action = rs.getString("action");
                String note = rs.getString("note");
                String key = String.valueOf(npcid) + action;
                this._CraftList.put(key, note);
            }
        }
        catch (SQLException e) {
            L1BlendTable_1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1Blend1 getTemplate(String craftkey) {
        return this._CraftIndex.get(craftkey);
    }
    
    public Map<String, String> get_craftlist() {
        return this._CraftList;
    }
}
