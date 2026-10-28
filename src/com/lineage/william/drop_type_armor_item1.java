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

public class drop_type_armor_item1
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        drop_type_armor_item1.aData = new ArrayList();
        drop_type_armor_item1.BUILD_DATA = false;
        drop_type_armor_item1._random = new Random();
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
        boolean isStop2 = false;
        int ran = drop_type_armor_item1._random.nextInt(25) + 1;
        if (!drop_type_armor_item1.BUILD_DATA) {
            drop_type_armor_item1.BUILD_DATA = true;
            getData();
        }
        int i = 0;
        while (i < drop_type_armor_item1.aData.size()) {
            aTempData = drop_type_armor_item1.aData.get(i);
            switch (ran) {
                case 1: {
                    if ((Integer)aTempData.get(0) == 1) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(1);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 2: {
                    if ((Integer)aTempData.get(0) == 2) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(2);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 3: {
                    if ((Integer)aTempData.get(0) == 3) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(3);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 4: {
                    if ((Integer)aTempData.get(0) == 4) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(4);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 5: {
                    if ((Integer)aTempData.get(0) == 5) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(5);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 6: {
                    if ((Integer)aTempData.get(0) == 6) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(6);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 7: {
                    if ((Integer)aTempData.get(0) == 7) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(7);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 8: {
                    if ((Integer)aTempData.get(0) == 8) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(8);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 9: {
                    if ((Integer)aTempData.get(0) == 9) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(9);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 10: {
                    if ((Integer)aTempData.get(0) == 10) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(10);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 11: {
                    if ((Integer)aTempData.get(0) == 11) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(11);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 12: {
                    if ((Integer)aTempData.get(0) == 12) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(12);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 13: {
                    if ((Integer)aTempData.get(0) == 13) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(13);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 14: {
                    if ((Integer)aTempData.get(0) == 14) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(14);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 15: {
                    if ((Integer)aTempData.get(0) == 15) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(15);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 16: {
                    if ((Integer)aTempData.get(0) == 16) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(16);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 17: {
                    if ((Integer)aTempData.get(0) == 17) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(17);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 18: {
                    if ((Integer)aTempData.get(0) == 18) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(18);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 19: {
                    if ((Integer)aTempData.get(0) == 19) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(19);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 20: {
                    if ((Integer)aTempData.get(0) == 20) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(20);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 21: {
                    if ((Integer)aTempData.get(0) == 21) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(21);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 22: {
                    if ((Integer)aTempData.get(0) == 22) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(22);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 23: {
                    if ((Integer)aTempData.get(0) == 23) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(23);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 24: {
                    if ((Integer)aTempData.get(0) == 24) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(24);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
                case 25: {
                    if ((Integer)aTempData.get(0) == 25) {
                        if (drop_type_armor_item1._random.nextInt(1000) + 1 <= (Integer)aTempData.get(1)) {
                            pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(1)));
                            item.setItemArmorType(25);
                            CharItemsTable cit = new CharItemsTable();
                            try {
                                cit.updateItemArmorType(item);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            isStop = true;
                            break;
                        }
                        else {
                            isStop2 = true;
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            if (isStop2) {
                isStop2 = false;
                pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(2)));
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
                return;
            }
            else if (isStop) {
                if (item.getItemArmorType() == (Integer)aTempData.get(0)) {
                    isStop = false;
                    if ((Integer)aTempData.get(2) > 0) {
                        item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(2));
                    }
                    if ((Integer)aTempData.get(3) > 0) {
                        item.setItemHit(item.getItemHit() + (Integer)aTempData.get(3));
                    }
                    if ((Integer)aTempData.get(4) > 0) {
                        item.setItemSp(item.getItemSp() + (Integer)aTempData.get(4));
                    }
                    if ((Integer)aTempData.get(5) > 0) {
                        item.setItemStr(item.getItemStr() + (Integer)aTempData.get(5));
                    }
                    if ((Integer)aTempData.get(6) > 0) {
                        item.setItemDex(item.getItemDex() + (Integer)aTempData.get(6));
                    }
                    if ((Integer)aTempData.get(7) > 0) {
                        item.setItemInt(item.getItemInt() + (Integer)aTempData.get(7));
                    }
                    if ((Integer)aTempData.get(8) > 0) {
                        item.setItemCon(item.getItemCon() + (Integer)aTempData.get(8));
                    }
                    if ((Integer)aTempData.get(9) > 0) {
                        item.setItemWis(item.getItemWis() + (Integer)aTempData.get(9));
                    }
                    if ((Integer)aTempData.get(10) > 0) {
                        item.setItemCha(item.getItemCha() + (Integer)aTempData.get(10));
                    }
                    if ((Integer)aTempData.get(11) > 0) {
                        item.setItemHp(item.getItemHp() + (Integer)aTempData.get(11));
                    }
                    if ((Integer)aTempData.get(12) > 0) {
                        item.setItemMp(item.getItemMp() + (Integer)aTempData.get(12));
                    }
                    if ((Integer)aTempData.get(13) > 0) {
                        item.setItemMr(item.getItemMr() + (Integer)aTempData.get(13));
                    }
                    if ((Integer)aTempData.get(14) > 0) {
                        item.setItemReductionDmg(item.getItemReductionDmg() + (Integer)aTempData.get(14));
                    }
                    if ((Integer)aTempData.get(15) > 0) {
                        item.setItemHpr(item.getItemHpr() + (Integer)aTempData.get(15));
                    }
                    if ((Integer)aTempData.get(16) > 0) {
                        item.setItemMpr(item.getItemMpr() + (Integer)aTempData.get(16));
                    }
                    if ((Integer)aTempData.get(17) > 0) {
                        item.setItemhppotion(item.getItemhppotion() + (Integer)aTempData.get(17));
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
                        cit.updateItemHpr(item);
                        cit.updateItemMpr(item);
                        cit.updateItemhppotion(item);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            else {
                ++i;
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
                    drop_type_armor_item1.aData.add(aReturn);
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
