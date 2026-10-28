package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;

public class drop_type_weapon_item
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        drop_type_weapon_item.aData = new ArrayList();
        drop_type_weapon_item.BUILD_DATA = false;
        drop_type_weapon_item._random = new Random();
    }
    
    public static void main(String[] a) {
        while (true) {
            try {
                Server.main((String[])null);
                continue;
            }
            catch (Exception ex) {}
        }
    }
    
public static void forIntensifyArmor(L1PcInstance pc, L1ItemInstance item) {
        if (!drop_type_weapon_item.BUILD_DATA) {
            drop_type_weapon_item.BUILD_DATA = true;
            getData();
        }
        // 如果資料庫沒資料，直接中斷
        if (drop_type_weapon_item.aData.isEmpty()) return;

        // 1. 從你資料庫的 100 種設定中，隨機抽出一種 (解開 1~7 的封印)
        int index = drop_type_weapon_item._random.nextInt(drop_type_weapon_item.aData.size());
        ArrayList<Object> aTempData = drop_type_weapon_item.aData.get(index);

        int check_type = (Integer) aTempData.get(0);
        int prob = (Integer) aTempData.get(1); // 讀取你資料庫裡的 ran (機率欄位)

        // 2. 進行機率判定：產生 1~100 的隨機數，若小於等於 prob，才算中獎！
        if (drop_type_weapon_item._random.nextInt(1000) + 1 <= prob) {
            
            // 中獎了！寫入炫色種類與能力
            item.setItemArmorType(check_type);
            item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(2));
            item.setItemHit(item.getItemHit() + (Integer)aTempData.get(3));
            item.setItemSp(item.getItemSp() + (Integer)aTempData.get(4));
            item.setItemStr(item.getItemStr() + (Integer)aTempData.get(5));
            item.setItemDex(item.getItemDex() + (Integer)aTempData.get(6));
            item.setItemInt(item.getItemInt() + (Integer)aTempData.get(7));
            item.setItemCon(item.getItemCon() + (Integer)aTempData.get(8));
            item.setItemWis(item.getItemWis() + (Integer)aTempData.get(9));
            item.setItemCha(item.getItemCha() + (Integer)aTempData.get(10));
            item.setItemHp(item.getItemHp() + (Integer)aTempData.get(11));
            item.setItemMp(item.getItemMp() + (Integer)aTempData.get(12));
            item.setItemMr(item.getItemMr() + (Integer)aTempData.get(13));
            item.setItemReductionDmg(item.getItemReductionDmg() + (Integer)aTempData.get(14));
            item.setItemHpr(item.getItemHpr() + (Integer)aTempData.get(15));
            item.setItemMpr(item.getItemMpr() + (Integer)aTempData.get(16));
            item.setItemhppotion(item.getItemhppotion() + (Integer)aTempData.get(17));

            // 將能力更新到資料庫中保存
            CharItemsTable cit = new CharItemsTable();
            try {
                cit.updateItemAttack(item);
                cit.updateItemHit(item);
                cit.updateItemSp(item);
                cit.updateItemStr(item);
                cit.updateItemDex(item);
                cit.updateItemInt(item);
                cit.updateItemCon(item);
                cit.updateItemWis(item);
                cit.updateItemCha(item);
                cit.updateItemHp(item);
                cit.updateItemMp(item);
                cit.updateItemMr(item);
                cit.updateItemReductionDmg(item);
                cit.updateItemHpr(item);
                cit.updateItemMpr(item);
                cit.updateItemhppotion(item);
                cit.updateItemArmorType(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 👉 如果機率沒過 (else)，就會什麼都不做。裝備就會是你想要的【純白板】！
    }
    
    private static void getData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM 炫色一_武器");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("check_type")));
                    aReturn.add(1, new Integer(rs.getInt("ran")));
                    aReturn.add(2, new Integer(rs.getInt("Attack")));
                    aReturn.add(3, new Integer(rs.getInt("Hit")));
                    aReturn.add(4, new Integer(rs.getInt("Sp")));
                    aReturn.add(5, new Integer(rs.getInt("Str")));
                    aReturn.add(6, new Integer(rs.getInt("Dex")));
                    aReturn.add(7, new Integer(rs.getInt("Int")));
                    aReturn.add(8, new Integer(rs.getInt("Con")));
                    aReturn.add(9, new Integer(rs.getInt("Wis")));
                    aReturn.add(10, new Integer(rs.getInt("Cha")));
                    aReturn.add(11, new Integer(rs.getInt("Hp")));
                    aReturn.add(12, new Integer(rs.getInt("Mp")));
                    aReturn.add(13, new Integer(rs.getInt("Mr")));
                    aReturn.add(14, new Integer(rs.getInt("ReductionDmg")));
                    aReturn.add(15, new Integer(rs.getInt("Hpr")));
                    aReturn.add(16, new Integer(rs.getInt("Mpr")));
                    aReturn.add(17, new Integer(rs.getInt("hppotion")));
                    drop_type_weapon_item.aData.add(aReturn);
                }
            }
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstmt);
            SQLUtil.close(conn);
        }
    }
}
