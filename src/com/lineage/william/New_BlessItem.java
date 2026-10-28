package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;

public class New_BlessItem
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        New_BlessItem.aData = new ArrayList();
        New_BlessItem.BUILD_DATA = false;
        New_BlessItem._random = new Random();
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
        if (!New_BlessItem.BUILD_DATA) {
            New_BlessItem.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < New_BlessItem.aData.size(); ++i) {
            aTempData = New_BlessItem.aData.get(i);
            if ((Integer)aTempData.get(17) == 2 && item.getItem().getType2() == 2 && (((Integer)aTempData.get(0) == 2 && item.getItem().getUseType() == 2) || ((Integer)aTempData.get(0) == 25 && item.getItem().getUseType() == 25) || ((Integer)aTempData.get(0) == 18 && item.getItem().getUseType() == 18) || ((Integer)aTempData.get(0) == 19 && item.getItem().getUseType() == 19) || ((Integer)aTempData.get(0) == 20 && item.getItem().getUseType() == 20) || ((Integer)aTempData.get(0) == 21 && item.getItem().getUseType() == 21) || ((Integer)aTempData.get(0) == 22 && item.getItem().getUseType() == 22) || ((Integer)aTempData.get(0) == 23 && item.getItem().getUseType() == 23) || ((Integer)aTempData.get(0) == 23 && item.getItem().getUseType() == 23) || ((Integer)aTempData.get(0) == 24 && item.getItem().getUseType() == 24) || ((Integer)aTempData.get(0) == 37 && item.getItem().getUseType() == 37) || ((Integer)aTempData.get(0) == 40 && item.getItem().getUseType() == 40) || ((Integer)aTempData.get(0) == 47 && item.getItem().getUseType() == 47))) {
                item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(1));
                item.setItemHit(item.getItemHit() + (Integer)aTempData.get(2));
                item.setItemSp(item.getItemSp() + (Integer)aTempData.get(3));
                item.setItemStr(item.getItemStr() + (Integer)aTempData.get(4));
                item.setItemDex(item.getItemDex() + (Integer)aTempData.get(5));
                item.setItemInt(item.getItemInt() + (Integer)aTempData.get(6));
                item.setItemCon(item.getItemCon() + (Integer)aTempData.get(7));
                item.setItemWis(item.getItemWis() + (Integer)aTempData.get(8));
                item.setItemCha(item.getItemCha() + (Integer)aTempData.get(9));
                item.setItemHp(item.getItemHp() + (Integer)aTempData.get(10));
                item.setItemMp(item.getItemMp() + (Integer)aTempData.get(11));
                item.setItemMr(item.getItemMr() + (Integer)aTempData.get(12));
                item.setItemReductionDmg(item.getItemReductionDmg() + (Integer)aTempData.get(13));
                item.setItemHpr(item.getItemHpr() + (Integer)aTempData.get(14));
                item.setItemMpr(item.getItemMpr() + (Integer)aTempData.get(15));
                item.setItemhppotion(item.getItemhppotion() + (Integer)aTempData.get(16));
                item.setItemAc(item.getItemAc() + (Integer)aTempData.get(19));
                if (!pc.isGm()) {
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(18), pc.getName(), item.getLogName())));
                }
                CharItemsTable cit = new CharItemsTable();
                try {
                    cit.updateItemAttack(item);
                    cit.updateItemHit(item);
                    cit.updateItemAc(item);
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
                    cit.updateBless(item);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void forIntensifyweapon(L1PcInstance pc, L1ItemInstance item) {
        ArrayList<Object> aTempData = null;
        if (!New_BlessItem.BUILD_DATA) {
            New_BlessItem.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < New_BlessItem.aData.size(); ++i) {
            aTempData = New_BlessItem.aData.get(i);
            if ((Integer)aTempData.get(17) == 1 && item.getItem().getType2() == 1 && (((Integer)aTempData.get(0) == 1 && item.getItem().getType() == 1) || ((Integer)aTempData.get(17) == 1 && (Integer)aTempData.get(0) == 2 && item.getItem().getType() == 2) || ((Integer)aTempData.get(0) == 3 && item.getItem().getType() == 3) || ((Integer)aTempData.get(0) == 15 && item.getItem().getType() == 15) || ((Integer)aTempData.get(0) == 5 && item.getItem().getType() == 5) || ((Integer)aTempData.get(0) == 6 && item.getItem().getType() == 6) || ((Integer)aTempData.get(0) == 14 && item.getItem().getType() == 14) || ((Integer)aTempData.get(0) == 7 && item.getItem().getType() == 7) || ((Integer)aTempData.get(0) == 16 && item.getItem().getType() == 16) || ((Integer)aTempData.get(0) == 11 && item.getItem().getType() == 11) || ((Integer)aTempData.get(0) == 12 && item.getItem().getType() == 12) || ((Integer)aTempData.get(0) == 4 && item.getItem().getType() == 4) || ((Integer)aTempData.get(0) == 13 && item.getItem().getType() == 13) || ((Integer)aTempData.get(0) == 11 && item.getItem().getType() == 11) || ((Integer)aTempData.get(0) == 12 && item.getItem().getType() == 12) || ((Integer)aTempData.get(0) == 17 && item.getItem().getType() == 17) || ((Integer)aTempData.get(17) == 1 && (Integer)aTempData.get(0) == 18 && item.getItem().getType() == 18))) {
                item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(1));
                item.setItemHit(item.getItemHit() + (Integer)aTempData.get(2));
                item.setItemSp(item.getItemSp() + (Integer)aTempData.get(3));
                item.setItemStr(item.getItemStr() + (Integer)aTempData.get(4));
                item.setItemDex(item.getItemDex() + (Integer)aTempData.get(5));
                item.setItemInt(item.getItemInt() + (Integer)aTempData.get(6));
                item.setItemCon(item.getItemCon() + (Integer)aTempData.get(7));
                item.setItemWis(item.getItemWis() + (Integer)aTempData.get(8));
                item.setItemCha(item.getItemCha() + (Integer)aTempData.get(9));
                item.setItemHp(item.getItemHp() + (Integer)aTempData.get(10));
                item.setItemMp(item.getItemMp() + (Integer)aTempData.get(11));
                item.setItemMr(item.getItemMr() + (Integer)aTempData.get(12));
                item.setItemReductionDmg(item.getItemReductionDmg() + (Integer)aTempData.get(13));
                item.setItemHpr(item.getItemHpr() + (Integer)aTempData.get(14));
                item.setItemMpr(item.getItemMpr() + (Integer)aTempData.get(15));
                item.setItemAc(item.getItemAc() + (Integer)aTempData.get(19));
                item.setItemhppotion(item.getItemhppotion() + (Integer)aTempData.get(16));
                if (!pc.isGm()) {
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(18), pc.getName(), item.getLogName())));
                }
                CharItemsTable cit = new CharItemsTable();
                try {
                    cit.updateItemAttack(item);
                    cit.updateItemHit(item);
                    cit.updateItemSp(item);
                    cit.updateItemStr(item);
                    cit.updateItemDex(item);
                    cit.updateItemInt(item);
                    cit.updateItemCon(item);
                    cit.updateItemAc(item);
                    cit.updateItemWis(item);
                    cit.updateItemCha(item);
                    cit.updateItemHp(item);
                    cit.updateItemMp(item);
                    cit.updateItemMr(item);
                    cit.updateItemReductionDmg(item);
                    cit.updateItemHpr(item);
                    cit.updateItemMpr(item);
                    cit.updateBless(item);
                    cit.updateItemhppotion(item);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void cleanall(L1PcInstance pc, L1ItemInstance item) {
        ArrayList<Object> aTempData = null;
        if (!New_BlessItem.BUILD_DATA) {
            New_BlessItem.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < New_BlessItem.aData.size(); ++i) {
            aTempData = New_BlessItem.aData.get(i);
            item.setItemAttack(0);
            item.setItemHit(0);
            item.setItemSp(0);
            item.setItemStr(0);
            item.setItemDex(0);
            item.setItemInt(0);
            item.setItemCon(0);
            item.setItemWis(0);
            item.setItemCha(0);
            item.setItemHp(0);
            item.setItemMp(0);
            item.setItemMr(0);
            item.setItemReductionDmg(0);
            item.setItemHpr(0);
            item.setItemMpr(0);
            item.setItemhppotion(0);
            item.setItemAc(0);
            CharItemsTable cit = new CharItemsTable();
            try {
                cit.updateItemAttack(item);
                cit.updateItemHit(item);
                cit.updateItemAc(item);
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
                cit.updateBless(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM w_物品祝福系統");
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("item_type")));
                    aReturn.add(1, new Integer(rs.getInt("Attack")));
                    aReturn.add(2, new Integer(rs.getInt("Hit")));
                    aReturn.add(3, new Integer(rs.getInt("Sp")));
                    aReturn.add(4, new Integer(rs.getInt("Str")));
                    aReturn.add(5, new Integer(rs.getInt("Dex")));
                    aReturn.add(6, new Integer(rs.getInt("Int")));
                    aReturn.add(7, new Integer(rs.getInt("Con")));
                    aReturn.add(8, new Integer(rs.getInt("Wis")));
                    aReturn.add(9, new Integer(rs.getInt("Cha")));
                    aReturn.add(10, new Integer(rs.getInt("Hp")));
                    aReturn.add(11, new Integer(rs.getInt("Mp")));
                    aReturn.add(12, new Integer(rs.getInt("Mr")));
                    aReturn.add(13, new Integer(rs.getInt("ReductionDmg")));
                    aReturn.add(14, new Integer(rs.getInt("Hpr")));
                    aReturn.add(15, new Integer(rs.getInt("Mpr")));
                    aReturn.add(16, new Integer(rs.getInt("hppotion")));
                    aReturn.add(17, new Integer(rs.getInt("type")));
                    aReturn.add(18, rs.getString("All_message"));
                    aReturn.add(19, new Integer(rs.getInt("ac")));
                    New_BlessItem.aData.add(aReturn);
                }
            }
            stat.close();
            rs.close();
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (SQLException ex) {}
    }
}
