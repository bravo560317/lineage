package com.lineage.server.datatables;

import com.lineage.server.utils.Random;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1DropEnchant;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public final class DropItemEnchantTable
{
    private static final Log _log;
    private static DropItemEnchantTable _instance;
    private static final HashMap<Integer, ArrayList<L1DropEnchant>> _dropItemEnchant;
    
    static {
        _log = LogFactory.getLog(DropItemEnchantTable.class);
        _dropItemEnchant = new HashMap();
    }
    
    public static DropItemEnchantTable get() {
        if (DropItemEnchantTable._instance == null) {
            DropItemEnchantTable._instance = new DropItemEnchantTable();
        }
        return DropItemEnchantTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_掉落物品強化值`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int npcid = rs.getInt("npc_id");
                int itemid = rs.getInt("item_id");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    DropItemEnchantTable._log.error("掉落物品機率資料錯誤: 沒有這個編號的道具:" + itemid);
                    errorItem(itemid);
                }
                else {
                    String dropenchants = rs.getString("drop_enchants").replaceAll(" ", "");
                    String[] enchants_tmp = dropenchants.split(",");
                    int[] enchants = new int[enchants_tmp.length];
                    for (int i = 0; i < enchants_tmp.length; ++i) {
                        enchants[i] = Integer.parseInt(enchants_tmp[i]);
                    }
                    L1DropEnchant data = new L1DropEnchant(itemid, enchants);
                    ArrayList<L1DropEnchant> datalist = DropItemEnchantTable._dropItemEnchant.get((Integer)npcid);
                    if (datalist == null) {
                        datalist = new ArrayList();
                    }
                    datalist.add(data);
                    DropItemEnchantTable._dropItemEnchant.put(npcid, datalist);
                }
            }
        }
        catch (SQLException e) {
            DropItemEnchantTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DropItemEnchantTable._log.info("載入掉落物品強化值資料數量: " + DropItemEnchantTable._dropItemEnchant.size() + "(" + timer.get() + "ms)");
    }
    
    public ArrayList<L1DropEnchant> getDatalist(int npcid) {
        ArrayList<L1DropEnchant> datalist = DropItemEnchantTable._dropItemEnchant.get((Integer)npcid);
        if (datalist == null) {
            return null;
        }
        else {
            return datalist;
        }
    }
    
    public int getEnchant(L1DropEnchant data) {
        int[] enchants = data.getEnchants();
        int level = enchants[Random.nextInt(enchants.length)];
        return level;
    }
    
    private static void errorItem(int itemid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `系統_掉落物品強化值` WHERE `item_id`=?");
            pstm.setInt(1, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropItemEnchantTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
