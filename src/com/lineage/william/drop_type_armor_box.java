package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;

public class drop_type_armor_box
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        drop_type_armor_box.aData = new ArrayList();
        drop_type_armor_box.BUILD_DATA = false;
        drop_type_armor_box._random = new Random();
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
        ArrayList<Object> aTempData = null;
        boolean isStop = false;
        if (!drop_type_armor_box.BUILD_DATA) {
            drop_type_armor_box.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < drop_type_armor_box.aData.size(); ++i) {
            aTempData = drop_type_armor_box.aData.get(i);
            switch (drop_type_armor_box._random.nextInt(7) + 1) {
                case 1: {
                    if ((Integer)aTempData.get(0) == 1 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(1);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if ((Integer)aTempData.get(0) == 2 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(2);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 3: {
                    if ((Integer)aTempData.get(0) == 3 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(3);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 4: {
                    if ((Integer)aTempData.get(0) == 4 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(4);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 5: {
                    if ((Integer)aTempData.get(0) == 5 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(5);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 6: {
                    if ((Integer)aTempData.get(0) == 6 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(6);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 7: {
                    if ((Integer)aTempData.get(0) == 7 && drop_type_armor_box._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1) && item.getItemArmorType() == 0) {
                        item.setItemArmorType(7);
                        isStop = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            if (isStop && item.getItemArmorType() == (Integer)aTempData.get(0)) {
                isStop = false;
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                pc.sendPackets(new S_ItemStatus(item));
                pc.getInventory().updateItem(item, 4);
                pc.getInventory().saveItem(item, 4);
            }
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
                    drop_type_armor_box.aData.add(aReturn);
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
