package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;

public class drop_type_armor_npc
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        drop_type_armor_npc.aData = new ArrayList();
        drop_type_armor_npc.BUILD_DATA = false;
        drop_type_armor_npc._random = new Random();
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

    if (item == null || item.getItemArmorType() != 0) {
        return;
    }

    if (!BUILD_DATA) {
        BUILD_DATA = true;
        getData();
    }

    // 1~1000 抽一個號碼（決定抽哪個 check_type）
    int ran = _random.nextInt(1000) + 1;

    for (int i = 0; i < aData.size(); i++) {
        ArrayList<Object> data = aData.get(i);

        int checkType = (Integer) data.get(0); // check_type
        int chance    = (Integer) data.get(1); // ran（成功率）

        // 抽到的號碼不是這筆資料，跳過
        if (ran != checkType) {
            continue;
        }

        // 成功率判斷
        if (_random.nextInt(1000) + 1 > chance) {
            return;
        }

        // 命中，設定防具類型
        item.setItemArmorType(checkType);

        // 套用能力
        item.setItemAttack(item.getItemAttack() + (Integer)data.get(2));
        item.setItemHit(item.getItemHit() + (Integer)data.get(3));
        item.setItemSp(item.getItemSp() + (Integer)data.get(4));
        item.setItemStr(item.getItemStr() + (Integer)data.get(5));
        item.setItemDex(item.getItemDex() + (Integer)data.get(6));
        item.setItemInt(item.getItemInt() + (Integer)data.get(7));
        item.setItemCon(item.getItemCon() + (Integer)data.get(8));
        item.setItemWis(item.getItemWis() + (Integer)data.get(9));
        item.setItemCha(item.getItemCha() + (Integer)data.get(10));
        item.setItemHp(item.getItemHp() + (Integer)data.get(11));
        item.setItemMp(item.getItemMp() + (Integer)data.get(12));
        item.setItemMr(item.getItemMr() + (Integer)data.get(13));
        item.setItemReductionDmg(item.getItemReductionDmg() + (Integer)data.get(14));
        item.setItemHpr(item.getItemHpr() + (Integer)data.get(15));
        item.setItemMpr(item.getItemMpr() + (Integer)data.get(16));
        item.setItemhppotion(item.getItemhppotion() + (Integer)data.get(17));

        // 寫回資料庫
        try {
            CharItemsTable cit = new CharItemsTable();
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

        // 成功一次就結束
        return;
    }
}

    public static void forIntensifyArmor_npc(L1ItemInstance item) {
		
		

    // ✅ 防止洗裝
    if (item == null || item.getItemArmorType() != 0) {
        return;
    }

    if (!BUILD_DATA) {
        BUILD_DATA = true;
        getData();
    }

    int ran = _random.nextInt(1000) + 1;

    for (int i = 0; i < aData.size(); i++) {
        ArrayList<Object> data = aData.get(i);

        int checkType = (Integer) data.get(0);
        int chance    = (Integer) data.get(1);

        if (ran != checkType) {
            continue;
        }

        if (_random.nextInt(1000) + 1 > chance) {
            return;
        }

// ⭐ 套用炫色
item.setItemArmorType(checkType);

// ✅ 先加能力（這段要在前面）
item.setItemAttack(item.getItemAttack() + (Integer)data.get(2));
item.setItemHit(item.getItemHit() + (Integer)data.get(3));
item.setItemSp(item.getItemSp() + (Integer)data.get(4));
item.setItemStr(item.getItemStr() + (Integer)data.get(5));
item.setItemDex(item.getItemDex() + (Integer)data.get(6));
item.setItemInt(item.getItemInt() + (Integer)data.get(7));
item.setItemCon(item.getItemCon() + (Integer)data.get(8));
item.setItemWis(item.getItemWis() + (Integer)data.get(9));
item.setItemCha(item.getItemCha() + (Integer)data.get(10));
item.setItemHp(item.getItemHp() + (Integer)data.get(11));
item.setItemMp(item.getItemMp() + (Integer)data.get(12));
item.setItemMr(item.getItemMr() + (Integer)data.get(13));
item.setItemReductionDmg(item.getItemReductionDmg() + (Integer)data.get(14));
item.setItemHpr(item.getItemHpr() + (Integer)data.get(15));
item.setItemMpr(item.getItemMpr() + (Integer)data.get(16));
item.setItemhppotion(item.getItemhppotion() + (Integer)data.get(17));

// ✅ 再寫入DB（這裡才對）
try {
    CharItemsTable cit = new CharItemsTable();
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
        return;
    }
}
    private static void getData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM 炫色一_防具");
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
                    drop_type_armor_npc.aData.add(aReturn);
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
