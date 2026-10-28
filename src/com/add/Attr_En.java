package com.add;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class Attr_En
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    public static final String TOKEN = ",";
    
    static {
        Attr_En.aData = new ArrayList();
        Attr_En.BUILD_DATA = false;
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
        if (!Attr_En.BUILD_DATA) {
            Attr_En.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < Attr_En.aData.size(); ++i) {
            aTempData = Attr_En.aData.get(i);
            if (item.getAttrEnchantKind() == (Integer)aTempData.get(0) && item.getAttrEnchantLevel() == (Integer)aTempData.get(1)) {
                String add1 = "";
                String add2 = "";
                String add3 = "";
                String add4 = "";
                String add5 = "";
                String add6 = "";
                String add7 = "";
                String add8 = "";
                String add9 = "";
                String add10 = "";
                String add11 = "";
                String add12 = "";
                String add13 = "";
                String add14 = "";
                String add15 = "";
                String add16 = "";
                String add17 = "";
                String add18 = "";
                String add19 = "";
                String add20 = "";
                String add21 = "";
                String add22 = "";
                String add23 = "";
                String add24 = "";
                String add25 = "";
                String add26 = "";
                String add27 = "";
                String add28 = "";
                String add29 = "";
                String add30 = "";
                String add31 = "";
                String add32 = "";
                String add33 = "";
                String add34 = "";
                String add35 = "";
                String add36 = "";
                String add37 = "";
                String add38 = "";
                String add39 = "";
                String add40 = "";
                pc.sendPackets(new S_SystemMessage("----------屬性卷軸額外增加----------"));
                if ((Integer)aTempData.get(2) != 0 && (Integer)aTempData.get(3) != 0) {
                    pc.seteffect_gfxid((Integer)aTempData.get(2));
                    pc.seteffect_time((Integer)aTempData.get(3));
                }
                if ((Integer)aTempData.get(10) != 0) {
                    pc.addStr((Integer)aTempData.get(10));
                    add3 = "(力量+ " + (int)(Integer)aTempData.get(10) + ")";
                }
                if ((Integer)aTempData.get(11) != 0) {
                    pc.addDex((Integer)aTempData.get(11));
                    add4 = "(敏捷+ " + (int)(Integer)aTempData.get(11) + ")";
                }
                if ((Integer)aTempData.get(12) != 0) {
                    pc.addCon((Integer)aTempData.get(12));
                    add5 = "(體質+ " + (int)(Integer)aTempData.get(12) + ")";
                }
                if ((Integer)aTempData.get(13) != 0) {
                    pc.addInt((Integer)aTempData.get(13));
                    add6 = "(智力+ " + (int)(Integer)aTempData.get(13) + ")";
                }
                if ((Integer)aTempData.get(14) != 0) {
                    pc.addWis((Integer)aTempData.get(14));
                    add7 = "(精神+ " + (int)(Integer)aTempData.get(14) + ")";
                }
                if ((Integer)aTempData.get(15) != 0) {
                    pc.addCha((Integer)aTempData.get(15));
                    add8 = "(魅力+ " + (int)(Integer)aTempData.get(15) + ")";
                }
                if ((Integer)aTempData.get(16) != 0) {
                    pc.addAc(-(int)(Integer)aTempData.get(16));
                    add9 = "(防禦+ " + (int)(Integer)aTempData.get(16) + ")";
                }
                if ((Integer)aTempData.get(17) != 0) {
                    pc.addMaxHp((Integer)aTempData.get(17));
                    add10 = "(血量+ " + (int)(Integer)aTempData.get(17) + ")";
                }
                if ((Integer)aTempData.get(18) != 0) {
                    pc.addMaxMp((Integer)aTempData.get(18));
                    add11 = "(魔量+ " + (int)(Integer)aTempData.get(18) + ")";
                }
                if ((Integer)aTempData.get(19) != 0) {
                    pc.addHpr((Integer)aTempData.get(19));
                    add12 = "(回血+ " + (int)(Integer)aTempData.get(19) + ")";
                }
                if ((Integer)aTempData.get(20) != 0) {
                    pc.addMpr((Integer)aTempData.get(20));
                    add13 = "(回魔+ " + (int)(Integer)aTempData.get(20) + ")";
                }
                if ((Integer)aTempData.get(21) != 0) {
                    pc.addDmgup((Integer)aTempData.get(21));
                    add14 = "(近戰攻擊+ " + (int)(Integer)aTempData.get(21) + ")";
                }
                if ((Integer)aTempData.get(22) != 0) {
                    pc.addBowDmgup((Integer)aTempData.get(22));
                    add15 = "(遠戰攻擊+ " + (int)(Integer)aTempData.get(22) + ")";
                }
                if ((Integer)aTempData.get(23) != 0) {
                    pc.addHitup((Integer)aTempData.get(23));
                    add16 = "(近戰命中+ " + (int)(Integer)aTempData.get(23) + ")";
                }
                if ((Integer)aTempData.get(24) != 0) {
                    pc.addBowHitup((Integer)aTempData.get(24));
                    add17 = "(遠戰命中+ " + (int)(Integer)aTempData.get(24) + ")";
                }
                if ((Integer)aTempData.get(25) != 0) {
                    pc.addother_ReductionDmg((Integer)aTempData.get(25));
                    add18 = "(減傷+ " + (int)(Integer)aTempData.get(25) + ")";
                }
                if ((Integer)aTempData.get(26) != 0) {
                    pc.addMr((Integer)aTempData.get(26));
                    add19 = "(抗魔+ " + (int)(Integer)aTempData.get(26) + ")";
                }
                if ((Integer)aTempData.get(27) != 0) {
                    pc.addSp((Integer)aTempData.get(27));
                    add20 = "(魔攻+ " + (int)(Integer)aTempData.get(27) + ")";
                }
                if ((Integer)aTempData.get(28) != 0) {
                    pc.addFire((Integer)aTempData.get(28));
                    add21 = "(火屬+ " + (int)(Integer)aTempData.get(28) + ")";
                }
                if ((Integer)aTempData.get(29) != 0) {
                    pc.addWind((Integer)aTempData.get(29));
                    add22 = "(風屬+ " + (int)(Integer)aTempData.get(29) + ")";
                }
                if ((Integer)aTempData.get(30) != 0) {
                    pc.addEarth((Integer)aTempData.get(30));
                    add23 = "(地屬+ " + (int)(Integer)aTempData.get(30) + ")";
                }
                if ((Integer)aTempData.get(31) != 0) {
                    pc.addWater((Integer)aTempData.get(31));
                    add24 = "(水屬+ " + (int)(Integer)aTempData.get(31) + ")";
                }
                if ((Integer)aTempData.get(34) != 0) {
                    pc.addPVPdmg((Integer)aTempData.get(34));
                    add26 = "(pvp攻擊+ " + (int)(Integer)aTempData.get(34) + ")";
                }
                if ((Integer)aTempData.get(35) != 0) {
                    pc.addPVPdmgReduction((Integer)aTempData.get(35));
                    add27 = "(pvp減傷+ " + (int)(Integer)aTempData.get(35) + ")";
                }
                if ((Integer)aTempData.get(42) != 0) {
                    pc.addRegistStun((Integer)aTempData.get(42));
                    add31 = "(暈眩耐性+ " + (int)(Integer)aTempData.get(42) + ")";
                }
                if ((Integer)aTempData.get(43) != 0) {
                    pc.addRegistStone((Integer)aTempData.get(43));
                    add32 = "(石化耐性+ " + (int)(Integer)aTempData.get(43) + ")";
                }
                if ((Integer)aTempData.get(44) != 0) {
                    pc.addRegistSleep((Integer)aTempData.get(44));
                    add33 = "(睡眠耐性+ " + (int)(Integer)aTempData.get(44) + ")";
                }
                if ((Integer)aTempData.get(45) != 0) {
                    pc.add_regist_freeze((Integer)aTempData.get(45));
                    add34 = "(凍結耐性+ " + (int)(Integer)aTempData.get(45) + ")";
                }
                if ((Integer)aTempData.get(46) != 0) {
                    pc.addRegistSustain((Integer)aTempData.get(46));
                    add35 = "(支撐耐性+ " + (int)(Integer)aTempData.get(46) + ")";
                }
                if ((Integer)aTempData.get(47) != 0) {
                    pc.addRegistBlind((Integer)aTempData.get(47));
                    add36 = "(闇黑耐性+ " + (int)(Integer)aTempData.get(47) + ")";
                }
                if ((Double)aTempData.get(48) > 0.0) {
                    pc.addExpByArmor((Double)aTempData.get(48));
                    add22 = "(經驗增加" + (double)(Double)aTempData.get(48) + "倍)";
                }
                if ((Integer)aTempData.get(61) != 0) {
                    pc.addattr_potion_heal((Integer)aTempData.get(61));
                    add40 = "(喝水量增加" + (int)(Integer)aTempData.get(61) + ")";
                }
                pc.sendPackets(new S_SystemMessage(add1 + add2 + add3 + add4 + add5 + add6 + add7 + add8 + add9 + add10 + add11 + add12 + add13 + add14 + add15 + add16 + add17 + add18 + add19 + add20 + add21 + add22 + add23 + add24 + add25 + add26 + add27 + add28 + add29 + add30 + add31 + add32 + add33 + add34 + add35 + add36 + add37 + add38 + add39 + add40));
                if ((Integer)aTempData.get(32) != 0 && (Integer)aTempData.get(33) != 0) {
                    pc.setDmgReductionChance((Integer)aTempData.get(32));
                    pc.setDmgReductionDmg((Integer)aTempData.get(33));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(32) + "%機率觸發減傷:" + (int)(Integer)aTempData.get(33) + "滴}"));
                }
                if ((Integer)aTempData.get(36) != 0 && (Double)aTempData.get(37) != 0.0) {
                    pc.set近戰爆擊發動率((Integer)aTempData.get(36));
                    pc.set近戰爆擊倍率((Double)aTempData.get(37));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(36) + "%機率觸發近戰爆擊:" + (double)(Double)aTempData.get(37) + "%}"));
                }
                if ((Integer)aTempData.get(38) != 0 && (Double)aTempData.get(39) != 0.0) {
                    pc.set遠攻爆擊發動率((Integer)aTempData.get(38));
                    pc.set遠攻爆擊倍率((Double)aTempData.get(39));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(38) + "%機率觸發遠弓爆擊:" + (double)(Double)aTempData.get(39) + "%}"));
                }
                if ((Integer)aTempData.get(40) != 0 && (Double)aTempData.get(41) != 0.0) {
                    pc.set魔法爆擊發動率((Integer)aTempData.get(40));
                    pc.set魔法爆擊倍率((Double)aTempData.get(41));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(40) + "%機率觸發魔法爆擊:" + (double)(Double)aTempData.get(41) + "%}"));
                }
                if ((Integer)aTempData.get(4) != 0 && (Integer)aTempData.get(5) != 0 && (Integer)aTempData.get(6) != 0) {
                    pc.sethpr_time((Integer)aTempData.get(4));
                    pc.sethprr((Integer)aTempData.get(5));
                    pc.sethpr_gfxid((Integer)aTempData.get(6));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(4) + "秒回復血量:" + (int)(Integer)aTempData.get(5) + "滴)"));
                }
                if ((Integer)aTempData.get(7) != 0 && (Integer)aTempData.get(8) != 0 && (Integer)aTempData.get(9) != 0) {
                    pc.setmpr_time((Integer)aTempData.get(7));
                    pc.setmprr((Integer)aTempData.get(8));
                    pc.setmpr_gfxid((Integer)aTempData.get(9));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(7) + "秒回復魔量:" + (int)(Integer)aTempData.get(8) + "滴)"));
                }
                if ((Integer)aTempData.get(49) != 0 && (Integer)aTempData.get(50) != 0 && (Integer)aTempData.get(51) != 0 && (Integer)aTempData.get(52) != 0) {
                    pc.set吸取HP機率((Integer)aTempData.get(49));
                    pc.set吸取HP固定值((Integer)aTempData.get(50));
                    pc.set吸取HP隨機值((Integer)aTempData.get(51));
                    pc.set吸取HP動畫值((Integer)aTempData.get(52));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(49) + "%機率吸血:" + (int)(Integer)aTempData.get(50) + "+隨機" + (int)(Integer)aTempData.get(51) + "滴}"));
                }
                if ((Integer)aTempData.get(53) != 0 && (Integer)aTempData.get(54) != 0 && (Integer)aTempData.get(55) != 0 && (Integer)aTempData.get(56) != 0) {
                    pc.set吸取MP機率((Integer)aTempData.get(53));
                    pc.set吸取MP固定值((Integer)aTempData.get(54));
                    pc.set吸取MP隨機值((Integer)aTempData.get(55));
                    pc.set吸取MP動畫值((Integer)aTempData.get(56));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(53) + "%機率吸魔:" + (int)(Integer)aTempData.get(54) + "+隨機" + (int)(Integer)aTempData.get(55) + "滴}"));
                }
                if ((Integer)aTempData.get(57) != 0 && (Integer)aTempData.get(58) != 0 && (Integer)aTempData.get(59) != 0 && (Integer)aTempData.get(60) != 0) {
                    pc.set攻擊力發動機率((Integer)aTempData.get(57));
                    pc.set攻擊力固定值((Integer)aTempData.get(58));
                    pc.set攻擊力隨機值((Integer)aTempData.get(59));
                    pc.set攻擊力動畫((Integer)aTempData.get(60));
                    pc.sendPackets(new S_SystemMessage("(" + (int)(Integer)aTempData.get(57) + "%機率攻擊:" + (int)(Integer)aTempData.get(58) + "+隨機" + (int)(Integer)aTempData.get(59) + "滴}"));
                }
                pc.sendPackets(new S_SystemMessage("----------------------------------"));
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharStatus(pc));
                pc.sendPackets(new S_OwnCharStatus2(pc));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
        }
    }
    
    public static void forIntensifyArmor1(L1PcInstance pc, L1ItemInstance item) {
        ArrayList<Object> aTempData = null;
        if (!Attr_En.BUILD_DATA) {
            Attr_En.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < Attr_En.aData.size(); ++i) {
            aTempData = Attr_En.aData.get(i);
            if (item.getAttrEnchantKind() == (Integer)aTempData.get(0) && item.getAttrEnchantLevel() == (Integer)aTempData.get(1)) {
                if ((Integer)aTempData.get(2) != 0 && (Integer)aTempData.get(3) != 0) {
                    pc.seteffect_gfxid(-(int)(Integer)aTempData.get(2));
                    pc.seteffect_time(-(int)(Integer)aTempData.get(3));
                }
                if ((Integer)aTempData.get(10) != 0) {
                    pc.addStr(-(int)(Integer)aTempData.get(10));
                }
                if ((Integer)aTempData.get(11) != 0) {
                    pc.addDex(-(int)(Integer)aTempData.get(11));
                }
                if ((Integer)aTempData.get(12) != 0) {
                    pc.addCon(-(int)(Integer)aTempData.get(12));
                }
                if ((Integer)aTempData.get(13) != 0) {
                    pc.addInt(-(int)(Integer)aTempData.get(13));
                }
                if ((Integer)aTempData.get(14) != 0) {
                    pc.addWis(-(int)(Integer)aTempData.get(14));
                }
                if ((Integer)aTempData.get(15) != 0) {
                    pc.addCha(-(int)(Integer)aTempData.get(15));
                }
                if ((Integer)aTempData.get(16) != 0) {
                    pc.addAc((Integer)aTempData.get(16));
                }
                if ((Integer)aTempData.get(17) != 0) {
                    pc.addMaxHp(-(int)(Integer)aTempData.get(17));
                }
                if ((Integer)aTempData.get(18) != 0) {
                    pc.addMaxMp(-(int)(Integer)aTempData.get(18));
                }
                if ((Integer)aTempData.get(19) != 0) {
                    pc.addHpr(-(int)(Integer)aTempData.get(19));
                }
                if ((Integer)aTempData.get(20) != 0) {
                    pc.addMpr(-(int)(Integer)aTempData.get(20));
                }
                if ((Integer)aTempData.get(21) != 0) {
                    pc.addDmgup(-(int)(Integer)aTempData.get(21));
                }
                if ((Integer)aTempData.get(22) != 0) {
                    pc.addBowDmgup(-(int)(Integer)aTempData.get(22));
                }
                if ((Integer)aTempData.get(23) != 0) {
                    pc.addHitup(-(int)(Integer)aTempData.get(23));
                }
                if ((Integer)aTempData.get(24) != 0) {
                    pc.addBowHitup(-(int)(Integer)aTempData.get(24));
                }
                if ((Integer)aTempData.get(25) != 0) {
                    pc.addother_ReductionDmg(-(int)(Integer)aTempData.get(25));
                }
                if ((Integer)aTempData.get(26) != 0) {
                    pc.addMr(-(int)(Integer)aTempData.get(26));
                }
                if ((Integer)aTempData.get(27) != 0) {
                    pc.addSp(-(int)(Integer)aTempData.get(27));
                }
                if ((Integer)aTempData.get(28) != 0) {
                    pc.addFire(-(int)(Integer)aTempData.get(28));
                }
                if ((Integer)aTempData.get(29) != 0) {
                    pc.addWind(-(int)(Integer)aTempData.get(29));
                }
                if ((Integer)aTempData.get(30) != 0) {
                    pc.addEarth(-(int)(Integer)aTempData.get(30));
                }
                if ((Integer)aTempData.get(31) != 0) {
                    pc.addWater(-(int)(Integer)aTempData.get(31));
                }
                if ((Integer)aTempData.get(34) != 0) {
                    pc.addPVPdmg(-(int)(Integer)aTempData.get(34));
                }
                if ((Integer)aTempData.get(35) != 0) {
                    pc.addPVPdmgReduction(-(int)(Integer)aTempData.get(35));
                }
                if ((Integer)aTempData.get(42) != 0) {
                    pc.addRegistStun(-(int)(Integer)aTempData.get(42));
                }
                if ((Integer)aTempData.get(43) != 0) {
                    pc.addRegistStone(-(int)(Integer)aTempData.get(43));
                }
                if ((Integer)aTempData.get(44) != 0) {
                    pc.addRegistSleep(-(int)(Integer)aTempData.get(44));
                }
                if ((Integer)aTempData.get(45) != 0) {
                    pc.add_regist_freeze(-(int)(Integer)aTempData.get(45));
                }
                if ((Integer)aTempData.get(46) != 0) {
                    pc.addRegistSustain(-(int)(Integer)aTempData.get(46));
                }
                if ((Integer)aTempData.get(47) != 0) {
                    pc.addRegistBlind(-(int)(Integer)aTempData.get(47));
                }
                if ((Double)aTempData.get(48) > 0.0) {
                    pc.addExpByArmor(-(double)(Double)aTempData.get(48));
                }
                if ((Integer)aTempData.get(4) != 0 && (Integer)aTempData.get(5) != 0 && (Integer)aTempData.get(6) != 0) {
                    pc.sethpr_time(-(int)(Integer)aTempData.get(4));
                    pc.sethprr(-(int)(Integer)aTempData.get(5));
                    pc.sethpr_gfxid(-(int)(Integer)aTempData.get(6));
                }
                if ((Integer)aTempData.get(7) != 0 && (Integer)aTempData.get(8) != 0 && (Integer)aTempData.get(9) != 0) {
                    pc.setmpr_time(-(int)(Integer)aTempData.get(7));
                    pc.setmprr(-(int)(Integer)aTempData.get(8));
                    pc.setmpr_gfxid(-(int)(Integer)aTempData.get(9));
                }
                if ((Integer)aTempData.get(32) != 0 && (Integer)aTempData.get(33) != 0) {
                    pc.setDmgReductionChance(-(int)(Integer)aTempData.get(32));
                    pc.setDmgReductionDmg(-(int)(Integer)aTempData.get(33));
                }
                if ((Integer)aTempData.get(36) != 0 && (Double)aTempData.get(37) != 0.0) {
                    pc.set近戰爆擊發動率(-(int)(Integer)aTempData.get(36));
                    pc.set近戰爆擊倍率(-(double)(Double)aTempData.get(37));
                }
                if ((Integer)aTempData.get(38) != 0 && (Double)aTempData.get(39) != 0.0) {
                    pc.set遠攻爆擊發動率(-(int)(Integer)aTempData.get(38));
                    pc.set遠攻爆擊倍率(-(double)(Double)aTempData.get(39));
                }
                if ((Integer)aTempData.get(40) != 0 && (Double)aTempData.get(41) != 0.0) {
                    pc.set魔法爆擊發動率(-(int)(Integer)aTempData.get(40));
                    pc.set魔法爆擊倍率(-(double)(Double)aTempData.get(41));
                }
                if ((Integer)aTempData.get(49) != 0 && (Integer)aTempData.get(50) != 0 && (Integer)aTempData.get(51) != 0 && (Integer)aTempData.get(52) != 0) {
                    pc.set吸取HP機率(-(int)(Integer)aTempData.get(49));
                    pc.set吸取HP固定值(-(int)(Integer)aTempData.get(50));
                    pc.set吸取HP隨機值(-(int)(Integer)aTempData.get(51));
                    pc.set吸取HP動畫值(-(int)(Integer)aTempData.get(52));
                }
                if ((Integer)aTempData.get(53) != 0 && (Integer)aTempData.get(54) != 0 && (Integer)aTempData.get(55) != 0 && (Integer)aTempData.get(56) != 0) {
                    pc.set吸取MP機率(-(int)(Integer)aTempData.get(53));
                    pc.set吸取MP固定值(-(int)(Integer)aTempData.get(54));
                    pc.set吸取MP隨機值(-(int)(Integer)aTempData.get(55));
                    pc.set吸取MP動畫值(-(int)(Integer)aTempData.get(56));
                }
                if ((Integer)aTempData.get(57) != 0 && (Integer)aTempData.get(58) != 0 && (Integer)aTempData.get(59) != 0 && (Integer)aTempData.get(60) != 0) {
                    pc.set攻擊力發動機率(-(int)(Integer)aTempData.get(57));
                    pc.set攻擊力固定值(-(int)(Integer)aTempData.get(58));
                    pc.set攻擊力隨機值(-(int)(Integer)aTempData.get(59));
                    pc.set攻擊力動畫(-(int)(Integer)aTempData.get(60));
                }
                if ((Integer)aTempData.get(61) != 0) {
                    pc.addattr_potion_heal(-(int)(Integer)aTempData.get(61));
                }
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharStatus(pc));
                pc.sendPackets(new S_OwnCharStatus2(pc));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            }
        }
    }
    
    private static void getData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM w_屬性卷軸附屬能力");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("AttrEnchantKind")));
                    aReturn.add(1, new Integer(rs.getInt("AttrEnchantLevel")));
                    aReturn.add(2, new Integer(rs.getInt("effect_gfxid")));
                    aReturn.add(3, new Integer(rs.getInt("effect_time")));
                    aReturn.add(4, new Integer(rs.getInt("hpr_time")));
                    aReturn.add(5, new Integer(rs.getInt("hpr")));
                    aReturn.add(6, new Integer(rs.getInt("hpr_gfxid")));
                    aReturn.add(7, new Integer(rs.getInt("mpr_time")));
                    aReturn.add(8, new Integer(rs.getInt("mpr")));
                    aReturn.add(9, new Integer(rs.getInt("mpr_gfxid")));
                    aReturn.add(10, new Integer(rs.getInt("addStr")));
                    aReturn.add(11, new Integer(rs.getInt("addDex")));
                    aReturn.add(12, new Integer(rs.getInt("addCon")));
                    aReturn.add(13, new Integer(rs.getInt("addInt")));
                    aReturn.add(14, new Integer(rs.getInt("addWis")));
                    aReturn.add(15, new Integer(rs.getInt("addCha")));
                    aReturn.add(16, new Integer(rs.getInt("addAc")));
                    aReturn.add(17, new Integer(rs.getInt("addMaxHp")));
                    aReturn.add(18, new Integer(rs.getInt("addMaxMp")));
                    aReturn.add(19, new Integer(rs.getInt("addHpr")));
                    aReturn.add(20, new Integer(rs.getInt("addMpr")));
                    aReturn.add(21, new Integer(rs.getInt("addDmg")));
                    aReturn.add(22, new Integer(rs.getInt("addBowDmg")));
                    aReturn.add(23, new Integer(rs.getInt("addHit")));
                    aReturn.add(24, new Integer(rs.getInt("addBowHit")));
                    aReturn.add(25, new Integer(rs.getInt("reduction_dmg")));
                    aReturn.add(26, new Integer(rs.getInt("addMr")));
                    aReturn.add(27, new Integer(rs.getInt("addSp")));
                    aReturn.add(28, new Integer(rs.getInt("addFire")));
                    aReturn.add(29, new Integer(rs.getInt("addWind")));
                    aReturn.add(30, new Integer(rs.getInt("addEarth")));
                    aReturn.add(31, new Integer(rs.getInt("addWater")));
                    aReturn.add(32, new Integer(rs.getInt("DmgReductionChance")));
                    aReturn.add(33, new Integer(rs.getInt("DmgReductionDmg")));
                    aReturn.add(34, new Integer(rs.getInt("PVPdmg")));
                    aReturn.add(35, new Integer(rs.getInt("PVPdmgReduction")));
                    aReturn.add(36, new Integer(rs.getInt("近戰爆擊發動率")));
                    aReturn.add(37, new Double(rs.getDouble("近戰爆擊倍率")));
                    aReturn.add(38, new Integer(rs.getInt("遠攻爆擊發動率")));
                    aReturn.add(39, new Double(rs.getDouble("遠攻爆擊倍率")));
                    aReturn.add(40, new Integer(rs.getInt("魔法爆擊發動率")));
                    aReturn.add(41, new Double(rs.getDouble("魔法爆擊倍率")));
                    aReturn.add(42, new Integer(rs.getInt("暈眩耐性")));
                    aReturn.add(43, new Integer(rs.getInt("石化耐性")));
                    aReturn.add(44, new Integer(rs.getInt("睡眠耐性")));
                    aReturn.add(45, new Integer(rs.getInt("凍結耐性")));
                    aReturn.add(46, new Integer(rs.getInt("支撐耐性")));
                    aReturn.add(47, new Integer(rs.getInt("闇黑耐性")));
                    aReturn.add(48, new Double(rs.getDouble("addExp")));
                    aReturn.add(49, new Integer(rs.getInt("吸取HP機率")));
                    aReturn.add(50, new Integer(rs.getInt("吸取HP固定值")));
                    aReturn.add(51, new Integer(rs.getInt("吸取HP隨機值")));
                    aReturn.add(52, new Integer(rs.getInt("吸取HP動畫")));
                    aReturn.add(53, new Integer(rs.getInt("吸取MP機率")));
                    aReturn.add(54, new Integer(rs.getInt("吸取MP固定值")));
                    aReturn.add(55, new Integer(rs.getInt("吸取MP隨機值")));
                    aReturn.add(56, new Integer(rs.getInt("吸取MP動畫")));
                    aReturn.add(57, new Integer(rs.getInt("攻擊力發動機率")));
                    aReturn.add(58, new Integer(rs.getInt("攻擊力固定值")));
                    aReturn.add(59, new Integer(rs.getInt("攻擊力隨機值")));
                    aReturn.add(60, new Integer(rs.getInt("攻擊力動畫")));
                    aReturn.add(61, new Integer(rs.getInt("藥水恢復量")));
                    Attr_En.aData.add(aReturn);
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
