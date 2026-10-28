package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class ClanOriginal
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    private static ClanOriginal _instance;
    public static final String TOKEN = ",";
    
    static {
        ClanOriginal.aData = new ArrayList();
        ClanOriginal.BUILD_DATA = false;
    }
    
    public static ClanOriginal getInstance() {
        if (ClanOriginal._instance == null) {
            ClanOriginal._instance = new ClanOriginal();
        }
        return ClanOriginal._instance;
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
    
    public static void forIntensifyArmor(L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!ClanOriginal.BUILD_DATA) {
            ClanOriginal.BUILD_DATA = true;
            getData();
        }
        int clan_level = 0;
        if (pc.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                clan_level = clan.getClanLevel();
            }
        }
        int i = 0;
        while (i < ClanOriginal.aData.size()) {
            aTempData = ClanOriginal.aData.get(i);
            if (clan_level == (Integer)aTempData.get(0)) {
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
                pc.sendPackets(new S_SystemMessage("你所屬的" + pc.getClanname() + "血盟等級為： " + clan_level + " 級"));
                pc.sendPackets(new S_SystemMessage(add1 + add2 + add3 + add4 + add5 + add6 + add7 + add8 + add9 + add10 + add11 + add12 + add13 + add14 + add15 + add16 + add17 + add18 + add19 + add20 + add21 + add22 + add23 + add24 + add25));
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
    
    private static void getData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM w_血盟等級");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("ClanLevel")));
                    aReturn.add(1, new Integer(rs.getInt("Contribution")));
                    aReturn.add(2, new Integer(rs.getInt("AddMaxHp")));
                    aReturn.add(3, new Integer(rs.getInt("AddMaxMp")));
                    aReturn.add(4, new Integer(rs.getInt("AddDmg")));
                    aReturn.add(5, new Integer(rs.getInt("AddBowDmg")));
                    aReturn.add(6, new Integer(rs.getInt("AddHit")));
                    aReturn.add(7, new Integer(rs.getInt("AddBowHit")));
                    aReturn.add(8, new Integer(rs.getInt("AddMr")));
                    aReturn.add(9, new Integer(rs.getInt("AddSp")));
                    aReturn.add(10, new Integer(rs.getInt("AddAc")));
                    aReturn.add(11, new Integer(rs.getInt("AddFire")));
                    aReturn.add(12, new Integer(rs.getInt("AddWind")));
                    aReturn.add(13, new Integer(rs.getInt("AddEarth")));
                    aReturn.add(14, new Integer(rs.getInt("AddWater")));
                    aReturn.add(15, new Integer(rs.getInt("AddStr")));
                    aReturn.add(16, new Integer(rs.getInt("AddDex")));
                    aReturn.add(17, new Integer(rs.getInt("AddCon")));
                    aReturn.add(18, new Integer(rs.getInt("AddWis")));
                    aReturn.add(19, new Integer(rs.getInt("AddInt")));
                    aReturn.add(20, new Integer(rs.getInt("AddCha")));
                    aReturn.add(21, new Integer(rs.getInt("reduction_dmg")));
                    aReturn.add(22, new Integer(rs.getInt("reduction_magic_dmg")));
                    aReturn.add(23, new Double(rs.getDouble("ExpRate")));
                    aReturn.add(24, new Integer(rs.getInt("AddHpr")));
                    aReturn.add(25, new Integer(rs.getInt("AddMpr")));
                    aReturn.add(26, new Integer(rs.getInt("AddWeight")));
                    ClanOriginal.aData.add(aReturn);
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
