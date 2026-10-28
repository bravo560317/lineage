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

public class L1BlendTable
{
    private static final Log _log;
    private static L1BlendTable _instance;
    private final Map<String, L1Blend> _CraftIndex;
    private final Map<String, String> _CraftList;
    
    static {
        _log = LogFactory.getLog(L1BlendTable.class);
    }
    
    public L1BlendTable() {
        this._CraftIndex = new HashMap();
        this._CraftList = new HashMap();
    }
    
    public static L1BlendTable getInstance() {
        if (L1BlendTable._instance == null) {
            L1BlendTable._instance = new L1BlendTable();
        }
        return L1BlendTable._instance;
    }
    
    public void loadBlendTable() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_npc火神裝備製作");
            rs = pstm.executeQuery();
            this.fillBlendTable(rs);
        }
        catch (SQLException e) {
            L1BlendTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        L1BlendTable._log.info("載入道具製造資料數量: " + this._CraftIndex.size() + "(" + timer.get() + "ms)");
    }
    
    private void fillBlendTable(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int npcid = rs.getInt("npcid");
            String action = rs.getString("action");
            String note = rs.getString("note");
            int checkLevel = rs.getInt("checkLevel");
            int checkClass = rs.getInt("checkClass");
            int rnd = rs.getInt("rnd");
            int hpConsume = rs.getInt("hpConsume");
            int mpConsume = rs.getInt("mpConsume");
            int bonusitem = rs.getInt("bonus_item");
            int bonusitemcount = rs.getInt("bonus_item_count");
            int bonusitemenchant = rs.getInt("bonus_item_enchant");
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
            int new_item = rs.getInt("new_item");
            int new_item_counts = rs.getInt("new_item_counts");
            int new_Enchantlvl_SW = rs.getInt("new_Enchantlvl_SW");
            int new_item_Enchantlvl = rs.getInt("new_item_Enchantlvl");
            int new_item_bless = rs.getInt("new_item_Bless");
            int residueitem = rs.getInt("失敗退還");
            int residuecount = rs.getInt("失敗退還數量");
            int residueLv = rs.getInt("失敗退還強化");
            int replacement_count = rs.getInt("replacement_count");
            boolean inputamount = rs.getBoolean("input_amount");
            boolean all_in_once = rs.getBoolean("all_in_once");
            String sucesshtml = rs.getString("sucess_html");
            String failhtml = rs.getString("fail_html");
            String Allmessage = rs.getString("Allmessage");
            String runmessage = rs.getString("顯示假機率");
            int itemran = rs.getInt("自訂機率道具");
            int itemranup = rs.getInt("自訂機率數量上限");
            String othermessage = rs.getString("自訂機率道具名稱");
            int itembroad = rs.getInt("公告");
            L1Blend Item_Blend = new L1Blend();
            Item_Blend.set_npcid(npcid);
            Item_Blend.set_action(action);
            Item_Blend.set_note(note);
            Item_Blend.setCheckLevel(checkLevel);
            Item_Blend.setCheckClass(checkClass);
            Item_Blend.setRandom(rnd);
            Item_Blend.setHpConsume(hpConsume);
            Item_Blend.setMpConsume(mpConsume);
            Item_Blend.setMaterials(needids);
            Item_Blend.setMaterials_count(needcounts);
            Item_Blend.set_materials_enchants(needenchants);
            Item_Blend.setNew_item(new_item);
            Item_Blend.setNew_item_counts(new_item_counts);
            Item_Blend.setNew_Enchantlvl_SW(new_Enchantlvl_SW);
            Item_Blend.setNew_item_Enchantlvl(new_item_Enchantlvl);
            Item_Blend.setNew_item_Bless(new_item_bless);
            Item_Blend.setResidue_Item(residueitem);
            Item_Blend.setResidue_Count(residuecount);
            Item_Blend.setResidue_Lv(residueLv);
            Item_Blend.setReplacement_count(replacement_count);
            Item_Blend.setInputAmount(inputamount);
            Item_Blend.setAll_In_Once(all_in_once);
            Item_Blend.setBonus_item(bonusitem);
            Item_Blend.setBonus_item_count(bonusitemcount);
            Item_Blend.setBonus_item_enchant(bonusitemenchant);
            Item_Blend.set_sucesshtml(sucesshtml);
            Item_Blend.set_failhtml(failhtml);
            Item_Blend.set_Allmessage(Allmessage);
            Item_Blend.set_runmessage(runmessage);
            Item_Blend.setitembroad(itembroad);
            Item_Blend.setitemran(itemran);
            Item_Blend.setitemranup(itemranup);
            Item_Blend.set_othermessage(othermessage);
            String key = String.valueOf(npcid) + action;
            this._CraftIndex.put(key, Item_Blend);
            this.loadCraftList(npcid);
        }
    }
    
    private void loadCraftList(int npcid) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `w_npc火神裝備製作` WHERE `npcid` =?");
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
            L1BlendTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1Blend getTemplate(String craftkey) {
        return this._CraftIndex.get(craftkey);
    }
    
    public Map<String, String> get_craftlist() {
        return this._CraftList;
    }
}
