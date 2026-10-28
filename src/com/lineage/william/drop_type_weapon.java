package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.sql.CharItemsTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;

public class drop_type_weapon
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        drop_type_weapon.aData = new ArrayList();
        drop_type_weapon.BUILD_DATA = false;
        drop_type_weapon._random = new Random();
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
        if (!drop_type_weapon.BUILD_DATA) {
            drop_type_weapon.BUILD_DATA = true;
            getData();
        }
        int i = 0;
        while (i < drop_type_weapon.aData.size()) {
            aTempData = drop_type_weapon.aData.get(i);
            if (pc.getMapId() == (int)(Integer)aTempData.get(0) && (Integer)aTempData.get(1) == 1 && drop_type_weapon._random.nextInt(1000) + 1 <= (Integer)aTempData.get(2)) {
                switch (drop_type_weapon._random.nextInt(15) + 1) {
                    case 1: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemStr(item.getItemStr() + drop_type_weapon._random.nextInt((Integer)aTempData.get(3)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:力量+" + item.getItemStr()));
                            break;
                        }
                        else {
                            item.setItemStr(item.getItemStr() + (Integer)aTempData.get(3));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:力量+" + (int)(Integer)aTempData.get(3)));
                            break;
                        }
                    }
                    case 2: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemDex(item.getItemDex() + drop_type_weapon._random.nextInt((Integer)aTempData.get(4)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:敏捷+" + item.getItemDex()));
                            break;
                        }
                        else {
                            item.setItemDex(item.getItemDex() + (Integer)aTempData.get(4));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:敏捷+" + (int)(Integer)aTempData.get(4)));
                            break;
                        }
                    }
                    case 3: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemInt(item.getItemInt() + drop_type_weapon._random.nextInt((Integer)aTempData.get(5)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:智力+" + item.getItemInt()));
                            break;
                        }
                        else {
                            item.setItemInt(item.getItemInt() + (Integer)aTempData.get(5));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:智力+" + (int)(Integer)aTempData.get(5)));
                            break;
                        }
                    }
                    case 4: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemCon(item.getItemCon() + drop_type_weapon._random.nextInt((Integer)aTempData.get(6)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:體質+" + item.getItemCon()));
                            break;
                        }
                        else {
                            item.setItemCon(item.getItemCon() + (Integer)aTempData.get(6));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:體質+" + (int)(Integer)aTempData.get(6)));
                            break;
                        }
                    }
                    case 5: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemWis(item.getItemWis() + drop_type_weapon._random.nextInt((Integer)aTempData.get(7)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:精神+" + item.getItemWis()));
                            break;
                        }
                        else {
                            item.setItemWis(item.getItemWis() + (Integer)aTempData.get(7));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:精神+" + (int)(Integer)aTempData.get(7)));
                            break;
                        }
                    }
                    case 6: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemCha(item.getItemCha() + drop_type_weapon._random.nextInt((Integer)aTempData.get(8)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:魅力+" + item.getItemCha()));
                            break;
                        }
                        else {
                            item.setItemCha(item.getItemCha() + (Integer)aTempData.get(8));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:魅力+" + (int)(Integer)aTempData.get(8)));
                            break;
                        }
                    }
                    case 7: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemSp(item.getItemSp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(9)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:魔攻+" + item.getItemSp()));
                            break;
                        }
                        else {
                            item.setItemSp(item.getItemSp() + (Integer)aTempData.get(9));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:魔攻+" + (int)(Integer)aTempData.get(9)));
                            break;
                        }
                    }
                    case 8: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemHp(item.getItemHp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(10)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:血量+" + item.getItemHp()));
                            break;
                        }
                        else {
                            item.setItemHp(item.getItemHp() + (Integer)aTempData.get(10));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:血量+" + (int)(Integer)aTempData.get(10)));
                            break;
                        }
                    }
                    case 9: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemMp(item.getItemMp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(11)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:魔力+" + item.getItemMp()));
                            break;
                        }
                        else {
                            item.setItemMp(item.getItemMp() + (Integer)aTempData.get(11));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:魔力+" + (int)(Integer)aTempData.get(11)));
                            break;
                        }
                    }
                    case 10: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemAttack(item.getItemAttack() + drop_type_weapon._random.nextInt((Integer)aTempData.get(12)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:近攻+" + item.getItemAttack()));
                            break;
                        }
                        else {
                            item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(12));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:近攻+" + (int)(Integer)aTempData.get(12)));
                            break;
                        }
                    }
                    case 11: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemBowAttack(item.getItemBowAttack() + drop_type_weapon._random.nextInt((Integer)aTempData.get(13)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:遠攻+" + item.getItemBowAttack()));
                            break;
                        }
                        else {
                            item.setItemBowAttack(item.getItemBowAttack() + (Integer)aTempData.get(13));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:遠攻+" + (int)(Integer)aTempData.get(13)));
                            break;
                        }
                    }
                    case 12: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemprobability(item.getItemprobability() + drop_type_weapon._random.nextInt((Integer)aTempData.get(14)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:武器發動率+" + item.getItemprobability() + "%"));
                            break;
                        }
                        else {
                            item.setItemprobability(item.getItemprobability() + (Integer)aTempData.get(14));
                            pc.sendPackets(new S_ServerMessage("獲得裝備單屬性:武器發動率+" + (int)(Integer)aTempData.get(14) + "%"));
                            break;
                        }
                    }
                    case 13: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemHit(item.getItemHit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(15)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近命中+" + item.getItemHit()));
                            break;
                        }
                        else {
                            item.setItemHit(item.getItemHit() + (Integer)aTempData.get(15));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近命中+" + (int)(Integer)aTempData.get(15)));
                            break;
                        }
                    }
                    case 14: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemBowHit(item.getItemBowHit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(16)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠命中+" + item.getItemBowHit()));
                            break;
                        }
                        else {
                            item.setItemBowHit(item.getItemBowHit() + (Integer)aTempData.get(16));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠命中+" + (int)(Integer)aTempData.get(16)));
                            break;
                        }
                    }
                    case 15: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemMag_Hit(item.getItemMag_Hit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(17)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔法命中+" + item.getItemMag_Hit()));
                            break;
                        }
                        else {
                            item.setItemMag_Hit(item.getItemMag_Hit() + (Integer)aTempData.get(17));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔法命中+" + (int)(Integer)aTempData.get(17)));
                            break;
                        }
                    }
                }
                item.setItemArmorType(1);
                pc.sendPackets(new S_ItemStatus(item));
                CharItemsTable cit = new CharItemsTable();
                try {
                    cit.updateItemStr(item);
                    cit.updateItemDex(item);
                    cit.updateItemInt(item);
                    cit.updateItemCon(item);
                    cit.updateItemWis(item);
                    cit.updateItemCha(item);
                    cit.updateItemHp(item);
                    cit.updateItemMp(item);
                    cit.updateItemSp(item);
                    cit.updateItemAttack(item);
                    cit.updateItemBowAttack(item);
                    cit.updateItemprobability(item);
                    cit.updateItemReductionDmg(item);
                    cit.updateItemMr(item);
                    cit.updateItemAc(item);
                    cit.updateItemMag_Red(item);
                    cit.updateItemDg(item);
                    cit.updateItemistSustain(item);
                    cit.updateItemistStun(item);
                    cit.updateItemistStone(item);
                    cit.updateItemistSleep(item);
                    cit.updateItemistFreeze(item);
                    cit.updateItemistBlind(item);
                    cit.updateItemArmorType(item);
                    pc.save();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            else if (pc.getMapId() == (int)(Integer)aTempData.get(0) && (Integer)aTempData.get(1) == 2 && drop_type_weapon._random.nextInt(1000) + 1 <= (Integer)aTempData.get(2)) {
                switch (drop_type_weapon._random.nextInt(15) + 1) {
                    case 1: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemStr(item.getItemStr() + drop_type_weapon._random.nextInt((Integer)aTempData.get(3)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:力量+" + item.getItemStr()));
                            break;
                        }
                        else {
                            item.setItemStr(item.getItemStr() + (Integer)aTempData.get(3));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:力量+" + (int)(Integer)aTempData.get(3)));
                            break;
                        }
                    }
                    case 2: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemDex(item.getItemDex() + drop_type_weapon._random.nextInt((Integer)aTempData.get(4)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:敏捷+" + item.getItemDex()));
                            break;
                        }
                        else {
                            item.setItemDex(item.getItemDex() + (Integer)aTempData.get(4));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:敏捷+" + (int)(Integer)aTempData.get(4)));
                            break;
                        }
                    }
                    case 3: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemInt(item.getItemInt() + drop_type_weapon._random.nextInt((Integer)aTempData.get(5)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:智力+" + item.getItemInt()));
                            break;
                        }
                        else {
                            item.setItemInt(item.getItemInt() + (Integer)aTempData.get(5));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:智力+" + (int)(Integer)aTempData.get(5)));
                            break;
                        }
                    }
                    case 4: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemCon(item.getItemCon() + drop_type_weapon._random.nextInt((Integer)aTempData.get(6)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:體質+" + item.getItemCon()));
                            break;
                        }
                        else {
                            item.setItemCon(item.getItemCon() + (Integer)aTempData.get(6));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:體質+" + (int)(Integer)aTempData.get(6)));
                            break;
                        }
                    }
                    case 5: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemWis(item.getItemWis() + drop_type_weapon._random.nextInt((Integer)aTempData.get(7)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:精神+" + item.getItemWis()));
                            break;
                        }
                        else {
                            item.setItemWis(item.getItemWis() + (Integer)aTempData.get(7));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:精神+" + (int)(Integer)aTempData.get(7)));
                            break;
                        }
                    }
                    case 6: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemCha(item.getItemCha() + drop_type_weapon._random.nextInt((Integer)aTempData.get(8)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魅力+" + item.getItemCha()));
                            break;
                        }
                        else {
                            item.setItemCha(item.getItemCha() + (Integer)aTempData.get(8));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魅力+" + (int)(Integer)aTempData.get(8)));
                            break;
                        }
                    }
                    case 7: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemSp(item.getItemSp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(9)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔攻+" + item.getItemSp()));
                            break;
                        }
                        else {
                            item.setItemSp(item.getItemSp() + (Integer)aTempData.get(9));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔攻+" + (int)(Integer)aTempData.get(9)));
                            break;
                        }
                    }
                    case 8: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemHp(item.getItemHp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(10)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:血量+" + item.getItemHp()));
                            break;
                        }
                        else {
                            item.setItemHp(item.getItemHp() + (Integer)aTempData.get(10));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:血量+" + (int)(Integer)aTempData.get(10)));
                            break;
                        }
                    }
                    case 9: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemMp(item.getItemMp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(11)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔力+" + item.getItemMp()));
                            break;
                        }
                        else {
                            item.setItemMp(item.getItemMp() + (Integer)aTempData.get(11));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔力+" + (int)(Integer)aTempData.get(11)));
                            break;
                        }
                    }
                    case 10: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemAttack(item.getItemAttack() + drop_type_weapon._random.nextInt((Integer)aTempData.get(12)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近攻+" + item.getItemAttack()));
                            break;
                        }
                        else {
                            item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(12));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近攻+" + (int)(Integer)aTempData.get(12)));
                            break;
                        }
                    }
                    case 11: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemBowAttack(item.getItemBowAttack() + drop_type_weapon._random.nextInt((Integer)aTempData.get(13)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠攻+" + item.getItemBowAttack()));
                            break;
                        }
                        else {
                            item.setItemBowAttack(item.getItemBowAttack() + (Integer)aTempData.get(13));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠攻+" + (int)(Integer)aTempData.get(13)));
                            break;
                        }
                    }
                    case 12: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemprobability(item.getItemprobability() + drop_type_weapon._random.nextInt((Integer)aTempData.get(14)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:武器發動率+" + item.getItemprobability() + "%"));
                            break;
                        }
                        else {
                            item.setItemprobability(item.getItemprobability() + (Integer)aTempData.get(14));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:武器發動率+" + (int)(Integer)aTempData.get(14) + "%"));
                            break;
                        }
                    }
                    case 13: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemHit(item.getItemHit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(15)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近命中+" + item.getItemHit()));
                            break;
                        }
                        else {
                            item.setItemHit(item.getItemHit() + (Integer)aTempData.get(15));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近命中+" + (int)(Integer)aTempData.get(15)));
                            break;
                        }
                    }
                    case 14: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemBowHit(item.getItemBowHit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(16)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠命中+" + item.getItemBowHit()));
                            break;
                        }
                        else {
                            item.setItemBowHit(item.getItemBowHit() + (Integer)aTempData.get(16));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠命中+" + (int)(Integer)aTempData.get(16)));
                            break;
                        }
                    }
                    case 15: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemMag_Hit(item.getItemMag_Hit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(17)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔法命中+" + item.getItemMag_Hit()));
                            break;
                        }
                        else {
                            item.setItemMag_Hit(item.getItemMag_Hit() + (Integer)aTempData.get(17));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔法命中+" + (int)(Integer)aTempData.get(17)));
                            break;
                        }
                    }
                }
                switch (drop_type_weapon._random.nextInt(15) + 1) {
                    case 1: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemStr(item.getItemStr() + drop_type_weapon._random.nextInt((Integer)aTempData.get(3)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:力量+" + item.getItemStr()));
                            break;
                        }
                        else {
                            item.setItemStr(item.getItemStr() + (Integer)aTempData.get(3));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:力量+" + (int)(Integer)aTempData.get(3)));
                            break;
                        }
                    }
                    case 2: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemDex(item.getItemDex() + drop_type_weapon._random.nextInt((Integer)aTempData.get(4)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:敏捷+" + item.getItemDex()));
                            break;
                        }
                        else {
                            item.setItemDex(item.getItemDex() + (Integer)aTempData.get(4));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:敏捷+" + (int)(Integer)aTempData.get(4)));
                            break;
                        }
                    }
                    case 3: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemInt(item.getItemInt() + drop_type_weapon._random.nextInt((Integer)aTempData.get(5)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:智力+" + item.getItemInt()));
                            break;
                        }
                        else {
                            item.setItemInt(item.getItemInt() + (Integer)aTempData.get(5));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:智力+" + (int)(Integer)aTempData.get(5)));
                            break;
                        }
                    }
                    case 4: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemCon(item.getItemCon() + drop_type_weapon._random.nextInt((Integer)aTempData.get(6)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:體質+" + item.getItemCon()));
                            break;
                        }
                        else {
                            item.setItemCon(item.getItemCon() + (Integer)aTempData.get(6));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:體質+" + (int)(Integer)aTempData.get(6)));
                            break;
                        }
                    }
                    case 5: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemWis(item.getItemWis() + drop_type_weapon._random.nextInt((Integer)aTempData.get(7)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:精神+" + item.getItemWis()));
                            break;
                        }
                        else {
                            item.setItemWis(item.getItemWis() + (Integer)aTempData.get(7));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:精神+" + (int)(Integer)aTempData.get(7)));
                            break;
                        }
                    }
                    case 6: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemCha(item.getItemCha() + drop_type_weapon._random.nextInt((Integer)aTempData.get(8)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魅力+" + item.getItemCha()));
                            break;
                        }
                        else {
                            item.setItemCha(item.getItemCha() + (Integer)aTempData.get(8));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魅力+" + (int)(Integer)aTempData.get(8)));
                            break;
                        }
                    }
                    case 7: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemSp(item.getItemSp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(9)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔攻+" + item.getItemSp()));
                            break;
                        }
                        else {
                            item.setItemSp(item.getItemSp() + (Integer)aTempData.get(9));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔攻+" + (int)(Integer)aTempData.get(9)));
                            break;
                        }
                    }
                    case 8: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemHp(item.getItemHp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(10)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:血量+" + item.getItemHp()));
                            break;
                        }
                        else {
                            item.setItemHp(item.getItemHp() + (Integer)aTempData.get(10));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:血量+" + (int)(Integer)aTempData.get(10)));
                            break;
                        }
                    }
                    case 9: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemMp(item.getItemMp() + drop_type_weapon._random.nextInt((Integer)aTempData.get(11)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔力+" + item.getItemMp()));
                            break;
                        }
                        else {
                            item.setItemMp(item.getItemMp() + (Integer)aTempData.get(11));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔力+" + (int)(Integer)aTempData.get(11)));
                            break;
                        }
                    }
                    case 10: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemAttack(item.getItemAttack() + drop_type_weapon._random.nextInt((Integer)aTempData.get(12)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近攻+" + item.getItemAttack()));
                            break;
                        }
                        else {
                            item.setItemAttack(item.getItemAttack() + (Integer)aTempData.get(12));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近攻+" + (int)(Integer)aTempData.get(12)));
                            break;
                        }
                    }
                    case 11: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemBowAttack(item.getItemBowAttack() + drop_type_weapon._random.nextInt((Integer)aTempData.get(13)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠攻+" + item.getItemBowAttack()));
                            break;
                        }
                        else {
                            item.setItemBowAttack(item.getItemBowAttack() + (Integer)aTempData.get(13));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠攻+" + (int)(Integer)aTempData.get(13)));
                            break;
                        }
                    }
                    case 12: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemprobability(item.getItemprobability() + drop_type_weapon._random.nextInt((Integer)aTempData.get(14)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:武器發動率+" + item.getItemprobability() + "%"));
                            break;
                        }
                        else {
                            item.setItemprobability(item.getItemprobability() + (Integer)aTempData.get(14));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:武器發動率+" + (int)(Integer)aTempData.get(14) + "%"));
                            break;
                        }
                    }
                    case 13: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemHit(item.getItemHit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(15)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近命中+" + item.getItemHit()));
                            break;
                        }
                        else {
                            item.setItemHit(item.getItemHit() + (Integer)aTempData.get(15));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:近命中+" + (int)(Integer)aTempData.get(15)));
                            break;
                        }
                    }
                    case 14: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemBowHit(item.getItemBowHit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(16)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠命中+" + item.getItemBowHit()));
                            break;
                        }
                        else {
                            item.setItemBowHit(item.getItemBowHit() + (Integer)aTempData.get(16));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:遠命中+" + (int)(Integer)aTempData.get(16)));
                            break;
                        }
                    }
                    case 15: {
                        if ((Integer)aTempData.get(18) == 1) {
                            item.setItemMag_Hit(item.getItemMag_Hit() + drop_type_weapon._random.nextInt((Integer)aTempData.get(17)) + 1);
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔法命中+" + item.getItemMag_Hit()));
                            break;
                        }
                        else {
                            item.setItemMag_Hit(item.getItemMag_Hit() + (Integer)aTempData.get(17));
                            pc.sendPackets(new S_ServerMessage("獲得裝備雙屬性:魔法命中+" + (int)(Integer)aTempData.get(17)));
                            break;
                        }
                    }
                }
                item.setItemArmorType(2);
                pc.sendPackets(new S_ItemStatus(item));
                CharItemsTable cit = new CharItemsTable();
                try {
                    cit.updateItemStr(item);
                    cit.updateItemDex(item);
                    cit.updateItemInt(item);
                    cit.updateItemCon(item);
                    cit.updateItemWis(item);
                    cit.updateItemCha(item);
                    cit.updateItemHp(item);
                    cit.updateItemMp(item);
                    cit.updateItemSp(item);
                    cit.updateItemAttack(item);
                    cit.updateItemBowAttack(item);
                    cit.updateItemHit(item);
                    cit.updateItemBowHit(item);
                    cit.updateItemprobability(item);
                    cit.updateItemReductionDmg(item);
                    cit.updateItemMag_Hit(item);
                    cit.updateItemArmorType(item);
                    pc.save();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            else if (pc.getMapId() == (int)(Integer)aTempData.get(0) && (Integer)aTempData.get(1) == 3 && drop_type_weapon._random.nextInt(1000) + 1 <= (Integer)aTempData.get(2)) {
                switch (drop_type_weapon._random.nextInt(11) + 1) {
                    case 1: {
                        item.setItemArmorType(16);
                        break;
                    }
                    case 2: {
                        item.setItemArmorType(17);
                        break;
                    }
                    case 3: {
                        item.setItemArmorType(18);
                        break;
                    }
                    case 4: {
                        item.setItemArmorType(19);
                        break;
                    }
                    case 5: {
                        item.setItemArmorType(20);
                        break;
                    }
                    case 6: {
                        item.setItemArmorType(21);
                        break;
                    }
                    case 7: {
                        item.setItemArmorType(22);
                        break;
                    }
                    case 8: {
                        item.setItemArmorType(23);
                        break;
                    }
                    case 9: {
                        item.setItemArmorType(24);
                        break;
                    }
                    case 10: {
                        item.setItemArmorType(25);
                        break;
                    }
                    case 11: {
                        item.setItemArmorType(26);
                        break;
                    }
                }
                pc.sendPackets(new S_ItemStatus(item));
                CharItemsTable cit = new CharItemsTable();
                try {
                    cit.updateItemArmorType(item);
                    pc.save();
                }
                catch (Exception e) {
                    e.printStackTrace();
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
            pstmt = conn.prepareStatement("SELECT * FROM william_droptype_weapon");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("mapid")));
                    aReturn.add(1, new Integer(rs.getInt("check_type")));
                    aReturn.add(2, new Integer(rs.getInt("ran")));
                    aReturn.add(3, new Integer(rs.getInt("str")));
                    aReturn.add(4, new Integer(rs.getInt("dex")));
                    aReturn.add(5, new Integer(rs.getInt("int")));
                    aReturn.add(6, new Integer(rs.getInt("con")));
                    aReturn.add(7, new Integer(rs.getInt("wis")));
                    aReturn.add(8, new Integer(rs.getInt("cha")));
                    aReturn.add(9, new Integer(rs.getInt("sp")));
                    aReturn.add(10, new Integer(rs.getInt("hp")));
                    aReturn.add(11, new Integer(rs.getInt("mp")));
                    aReturn.add(12, new Integer(rs.getInt("attack")));
                    aReturn.add(13, new Integer(rs.getInt("bowattack")));
                    aReturn.add(14, new Integer(rs.getInt("probability")));
                    aReturn.add(15, new Integer(rs.getInt("hit")));
                    aReturn.add(16, new Integer(rs.getInt("bowhit")));
                    aReturn.add(17, new Integer(rs.getInt("mag_hit")));
                    aReturn.add(18, new Integer(rs.getInt("check_ran")));
                    drop_type_weapon.aData.add(aReturn);
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
