package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class Addsetvip
{
    private static ArrayList<ArrayList<Object>> aData15c;
    private static boolean NO_MORE_GET_DATA15c;
    public static final String TOKEN = ",";
    
    static {
        Addsetvip.aData15c = new ArrayList();
        Addsetvip.NO_MORE_GET_DATA15c = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void forArmorSkillSound(L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!Addsetvip.NO_MORE_GET_DATA15c) {
            Addsetvip.NO_MORE_GET_DATA15c = true;
            getData15c();
        }
        for (int i = 0; i < Addsetvip.aData15c.size(); ++i) {
            aTempData = Addsetvip.aData15c.get(i);
            if ((int[])aTempData.get(0) != null) {
                if (pc.getInventory().checkEquipped((int[])aTempData.get(0))) {
                    if ((Integer)aTempData.get(1) <= 0 || pc.getLevel() >= (Integer)aTempData.get(1)) {
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
                        if ((Integer)aTempData.get(2) != 0) {
                            pc.addMaxHp((Integer)aTempData.get(2));
                            pc.setCurrentHp(pc.getCurrentHp() + (Integer)aTempData.get(2));
                            add1 = "(HP+ " + (int)(Integer)aTempData.get(2) + ")";
                        }
                        if ((Integer)aTempData.get(3) != 0) {
                            pc.addMaxMp((Integer)aTempData.get(3));
                            pc.setCurrentMp(pc.getCurrentMp() + (Integer)aTempData.get(3));
                            add2 = "(MP+ " + (int)(Integer)aTempData.get(3) + ")";
                        }
                        if ((Integer)aTempData.get(4) != 0) {
                            pc.addDmgup((Integer)aTempData.get(4));
                            add3 = "(近距攻擊+ " + (int)(Integer)aTempData.get(4) + ")";
                        }
                        if ((Integer)aTempData.get(5) != 0) {
                            pc.addBowDmgup((Integer)aTempData.get(5));
                            add4 = "(遠距攻擊+ " + (int)(Integer)aTempData.get(5) + ")";
                        }
                        if ((Integer)aTempData.get(6) != 0) {
                            pc.addHitup((Integer)aTempData.get(6));
                            add5 = "(近距命中+ " + (int)(Integer)aTempData.get(6) + ")";
                        }
                        if ((Integer)aTempData.get(7) != 0) {
                            pc.addBowHitup((Integer)aTempData.get(7));
                            add6 = "(遠距命中+ " + (int)(Integer)aTempData.get(7) + ")";
                        }
                        if ((Integer)aTempData.get(8) != 0) {
                            pc.addMr((Integer)aTempData.get(8));
                            add7 = "(抗魔+ " + (int)(Integer)aTempData.get(8) + ")";
                        }
                        if ((Integer)aTempData.get(9) != 0) {
                            pc.addSp((Integer)aTempData.get(9));
                            add8 = "(魔攻+ " + (int)(Integer)aTempData.get(9) + ")";
                        }
                        if ((Integer)aTempData.get(10) != 0) {
                            pc.addAc(-(int)(Integer)aTempData.get(10));
                            add9 = "(防禦- " + (int)(Integer)aTempData.get(10) + ")";
                        }
                        if ((Integer)aTempData.get(11) != 0) {
                            pc.addFire((Integer)aTempData.get(11));
                            add10 = "(火屬性+ " + (int)(Integer)aTempData.get(11) + ")";
                        }
                        if ((Integer)aTempData.get(12) != 0) {
                            pc.addWind((Integer)aTempData.get(12));
                            add11 = "(風屬性+ " + (int)(Integer)aTempData.get(12) + ")";
                        }
                        if ((Integer)aTempData.get(13) != 0) {
                            pc.addEarth((Integer)aTempData.get(13));
                            add12 = "(地屬性+ " + (int)(Integer)aTempData.get(13) + ")";
                        }
                        if ((Integer)aTempData.get(14) != 0) {
                            pc.addWater((Integer)aTempData.get(14));
                            add13 = "(水屬性+ " + (int)(Integer)aTempData.get(14) + ")";
                        }
                        if ((Integer)aTempData.get(15) != 0) {
                            pc.addStr((Integer)aTempData.get(15));
                            add14 = "(力量+ " + (int)(Integer)aTempData.get(15) + ")";
                        }
                        if ((Integer)aTempData.get(16) != 0) {
                            pc.addDex((Integer)aTempData.get(16));
                            add15 = "(敏捷+ " + (int)(Integer)aTempData.get(16) + ")";
                        }
                        if ((Integer)aTempData.get(17) != 0) {
                            pc.addCon((Integer)aTempData.get(17));
                            add16 = "(體質+ " + (int)(Integer)aTempData.get(17) + ")";
                        }
                        if ((Integer)aTempData.get(18) != 0) {
                            pc.addWis((Integer)aTempData.get(18));
                            add17 = "(精神+ " + (int)(Integer)aTempData.get(18) + ")";
                        }
                        if ((Integer)aTempData.get(19) != 0) {
                            pc.addInt((Integer)aTempData.get(19));
                            add18 = "(智力+ " + (int)(Integer)aTempData.get(19) + ")";
                        }
                        if ((Integer)aTempData.get(20) != 0) {
                            pc.addCha((Integer)aTempData.get(20));
                            add19 = "(魅力+ " + (int)(Integer)aTempData.get(20) + ")";
                        }
                        if ((Integer)aTempData.get(21) != 0) {
                            pc.addClan_ReductionDmg((Integer)aTempData.get(21));
                            add20 = "(減免傷害+ " + (int)(Integer)aTempData.get(21) + ")";
                        }
                        if ((Integer)aTempData.get(22) != 0) {
                            pc.add_Clanmagic_reduction_dmg((Integer)aTempData.get(22));
                            add21 = "(減免魔法傷害+ " + (int)(Integer)aTempData.get(22) + ")";
                        }
                        if ((Double)aTempData.get(23) > 0.0) {
                            pc.addExpByArmor((Double)aTempData.get(23));
                            add22 = "(經驗增加" + (double)(Double)aTempData.get(23) + "倍)";
                        }
                        if ((Integer)aTempData.get(24) != 0) {
                            pc.addHpr((Integer)aTempData.get(24));
                            add23 = "(回血+ " + (int)(Integer)aTempData.get(24) + ")";
                        }
                        if ((Integer)aTempData.get(25) != 0) {
                            pc.addMpr((Integer)aTempData.get(25));
                            add24 = "(回魔+ " + (int)(Integer)aTempData.get(25) + ")";
                        }
                        if ((Integer)aTempData.get(26) != 0) {
                            pc.addWeightReduction((Integer)aTempData.get(26));
                            add25 = "(負重+ " + (int)(Integer)aTempData.get(26) + ")";
                        }
                        pc.sendPackets(new S_SystemMessage("\\fT賦予套裝能力如下:"));
                        pc.sendPackets(new S_SystemMessage(add1 + add2 + add3 + add4 + add5 + add6 + add7 + add8 + add9 + add10 + add11 + add12 + add13 + add14 + add15 + add16 + add17 + add18 + add19 + add20 + add21 + add22 + add23 + add24 + add25));
                        pc.sendPackets(new S_SPMR(pc));
                        pc.sendPackets(new S_OwnCharStatus(pc));
                        pc.sendPackets(new S_OwnCharStatus2(pc));
                        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                        pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                        break;
                    }
                }
            }
        }
    }
    
    public static void forArmorSkillSound1(L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!Addsetvip.NO_MORE_GET_DATA15c) {
            Addsetvip.NO_MORE_GET_DATA15c = true;
            getData15c();
        }
        int i = 0;
        while (i < Addsetvip.aData15c.size()) {
            aTempData = Addsetvip.aData15c.get(i);
            if ((int[])aTempData.get(0) != null && pc.getInventory().checkEquipped((int[])aTempData.get(0))) {
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
                if ((Integer)aTempData.get(2) != 0) {
                    pc.addMaxHp(-(int)(Integer)aTempData.get(2));
                    pc.setCurrentHp(pc.getCurrentHp() + (Integer)aTempData.get(2));
                }
                if ((Integer)aTempData.get(3) != 0) {
                    pc.addMaxMp(-(int)(Integer)aTempData.get(3));
                    pc.setCurrentMp(pc.getCurrentMp() + (Integer)aTempData.get(3));
                }
                if ((Integer)aTempData.get(4) != 0) {
                    pc.addDmgup(-(int)(Integer)aTempData.get(4));
                }
                if ((Integer)aTempData.get(5) != 0) {
                    pc.addBowDmgup(-(int)(Integer)aTempData.get(5));
                }
                if ((Integer)aTempData.get(6) != 0) {
                    pc.addHitup(-(int)(Integer)aTempData.get(6));
                }
                if ((Integer)aTempData.get(7) != 0) {
                    pc.addBowHitup(-(int)(Integer)aTempData.get(7));
                }
                if ((Integer)aTempData.get(8) != 0) {
                    pc.addMr(-(int)(Integer)aTempData.get(8));
                }
                if ((Integer)aTempData.get(9) != 0) {
                    pc.addSp(-(int)(Integer)aTempData.get(9));
                }
                if ((Integer)aTempData.get(10) != 0) {
                    pc.addAc((Integer)aTempData.get(10));
                }
                if ((Integer)aTempData.get(11) != 0) {
                    pc.addFire(-(int)(Integer)aTempData.get(11));
                }
                if ((Integer)aTempData.get(12) != 0) {
                    pc.addWind(-(int)(Integer)aTempData.get(12));
                }
                if ((Integer)aTempData.get(13) != 0) {
                    pc.addEarth(-(int)(Integer)aTempData.get(13));
                }
                if ((Integer)aTempData.get(14) != 0) {
                    pc.addWater(-(int)(Integer)aTempData.get(14));
                }
                if ((Integer)aTempData.get(15) != 0) {
                    pc.addStr(-(int)(Integer)aTempData.get(15));
                }
                if ((Integer)aTempData.get(16) != 0) {
                    pc.addDex(-(int)(Integer)aTempData.get(16));
                }
                if ((Integer)aTempData.get(17) != 0) {
                    pc.addCon(-(int)(Integer)aTempData.get(17));
                }
                if ((Integer)aTempData.get(18) != 0) {
                    pc.addWis(-(int)(Integer)aTempData.get(18));
                }
                if ((Integer)aTempData.get(19) != 0) {
                    pc.addInt(-(int)(Integer)aTempData.get(19));
                }
                if ((Integer)aTempData.get(20) != 0) {
                    pc.addCha(-(int)(Integer)aTempData.get(20));
                }
                if ((Integer)aTempData.get(21) != 0) {
                    pc.addClan_ReductionDmg(-(int)(Integer)aTempData.get(21));
                }
                if ((Integer)aTempData.get(22) != 0) {
                    pc.add_Clanmagic_reduction_dmg(-(int)(Integer)aTempData.get(22));
                }
                if ((Double)aTempData.get(23) > 0.0) {
                    pc.addExpByArmor(-(double)(Double)aTempData.get(23));
                }
                if ((Integer)aTempData.get(24) != 0) {
                    pc.addHpr(-(int)(Integer)aTempData.get(24));
                }
                if ((Integer)aTempData.get(25) != 0) {
                    pc.addMpr(-(int)(Integer)aTempData.get(25));
                }
                if ((Integer)aTempData.get(26) != 0) {
                    pc.addWeightReduction(-(int)(Integer)aTempData.get(26));
                }
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharStatus(pc));
                pc.sendPackets(new S_OwnCharStatus2(pc));
                pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                break;
            }
            else {
                ++i;
            }
        }
    }
    
    private static void getData15c() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具脫穿套裝能力");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    if (rset.getString("裝備編號") != null && !rset.getString("裝備編號").equals("") && !rset.getString("裝備編號").equals("0")) {
                        aReturn.add(0, convert(rset.getString("裝備編號").split(",")));
                    }
                    else {
                        aReturn.add(0, null);
                    }
                    aReturn.add(1, new Integer(rset.getInt("等級")));
                    aReturn.add(2, new Integer(rset.getInt("AddMaxHp")));
                    aReturn.add(3, new Integer(rset.getInt("AddMaxMp")));
                    aReturn.add(4, new Integer(rset.getInt("AddDmg")));
                    aReturn.add(5, new Integer(rset.getInt("AddBowDmg")));
                    aReturn.add(6, new Integer(rset.getInt("AddHit")));
                    aReturn.add(7, new Integer(rset.getInt("AddBowHit")));
                    aReturn.add(8, new Integer(rset.getInt("AddMr")));
                    aReturn.add(9, new Integer(rset.getInt("AddSp")));
                    aReturn.add(10, new Integer(rset.getInt("AddAc")));
                    aReturn.add(11, new Integer(rset.getInt("AddFire")));
                    aReturn.add(12, new Integer(rset.getInt("AddWind")));
                    aReturn.add(13, new Integer(rset.getInt("AddEarth")));
                    aReturn.add(14, new Integer(rset.getInt("AddWater")));
                    aReturn.add(15, new Integer(rset.getInt("AddStr")));
                    aReturn.add(16, new Integer(rset.getInt("AddDex")));
                    aReturn.add(17, new Integer(rset.getInt("AddCon")));
                    aReturn.add(18, new Integer(rset.getInt("AddWis")));
                    aReturn.add(19, new Integer(rset.getInt("AddInt")));
                    aReturn.add(20, new Integer(rset.getInt("AddCha")));
                    aReturn.add(21, new Integer(rset.getInt("reduction_dmg")));
                    aReturn.add(22, new Integer(rset.getInt("reduction_magic_dmg")));
                    aReturn.add(23, new Double(rset.getDouble("ExpRate")));
                    aReturn.add(24, new Integer(rset.getInt("AddHpr")));
                    aReturn.add(25, new Integer(rset.getInt("AddMpr")));
                    aReturn.add(26, new Integer(rset.getInt("AddWeight")));
                    Addsetvip.aData15c.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
    
    private static int[] convert(String[] args) {
        int[] i32 = new int[args.length];
        for (int j = 0; j < args.length; ++j) {
            i32[j] = Integer.parseInt(args[j]);
        }
        return i32;
    }
}
