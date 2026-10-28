package com.lineage.william;

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
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class drop_type_count
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean BUILD_DATA;
    public static final String TOKEN = ",";
    
    static {
        drop_type_count.aData = new ArrayList();
        drop_type_count.BUILD_DATA = false;
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
        if (!drop_type_count.BUILD_DATA) {
            drop_type_count.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < drop_type_count.aData.size(); ++i) {
            aTempData = drop_type_count.aData.get(i);
            if (pc.getArmorCount1() == (Integer)aTempData.get(0)) {
                if ((Integer)aTempData.get(1) != 0) {
                    pc.addStr((Integer)aTempData.get(1));
                }
                if ((Integer)aTempData.get(2) != 0) {
                    pc.addDex((Integer)aTempData.get(2));
                }
                if ((Integer)aTempData.get(3) != 0) {
                    pc.addInt((Integer)aTempData.get(3));
                }
                if ((Integer)aTempData.get(4) != 0) {
                    pc.addCon((Integer)aTempData.get(4));
                }
                if ((Integer)aTempData.get(5) != 0) {
                    pc.addWis((Integer)aTempData.get(5));
                }
                if ((Integer)aTempData.get(6) != 0) {
                    pc.addCha((Integer)aTempData.get(6));
                }
                if ((Integer)aTempData.get(7) != 0) {
                    pc.addSp((Integer)aTempData.get(7));
                }
                if ((Integer)aTempData.get(8) != 0) {
                    pc.addMaxHp((Integer)aTempData.get(8));
                    pc.setCurrentHp(pc.getCurrentHp() + (Integer)aTempData.get(8));
                }
                if ((Integer)aTempData.get(9) != 0) {
                    pc.addMaxMp((Integer)aTempData.get(9));
                    pc.setCurrentMp(pc.getCurrentMp() + (Integer)aTempData.get(9));
                }
                if ((Integer)aTempData.get(10) != 0) {
                    pc.addother_ReductionDmg((Integer)aTempData.get(10));
                }
                if ((Integer)aTempData.get(11) != 0) {
                    pc.addMr((Integer)aTempData.get(11));
                }
                if ((Integer)aTempData.get(12) != 0) {
                    pc.addAc((Integer)aTempData.get(12));
                }
                if ((Integer)aTempData.get(13) != 0) {
                    pc.addDmgup((Integer)aTempData.get(13));
                }
                if ((Integer)aTempData.get(14) != 0) {
                    pc.addBowDmgup((Integer)aTempData.get(14));
                }
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
        if (!drop_type_count.BUILD_DATA) {
            drop_type_count.BUILD_DATA = true;
            getData();
        }
        for (int i = 0; i < drop_type_count.aData.size(); ++i) {
            aTempData = drop_type_count.aData.get(i);
            if (pc.getArmorCount1() == (Integer)aTempData.get(0)) {
                if ((Integer)aTempData.get(1) != 0) {
                    pc.addStr(-(int)(Integer)aTempData.get(1));
                }
                if ((Integer)aTempData.get(2) != 0) {
                    pc.addDex(-(int)(Integer)aTempData.get(2));
                }
                if ((Integer)aTempData.get(3) != 0) {
                    pc.addInt(-(int)(Integer)aTempData.get(3));
                }
                if ((Integer)aTempData.get(4) != 0) {
                    pc.addCon(-(int)(Integer)aTempData.get(4));
                }
                if ((Integer)aTempData.get(5) != 0) {
                    pc.addWis(-(int)(Integer)aTempData.get(5));
                }
                if ((Integer)aTempData.get(6) != 0) {
                    pc.addCha(-(int)(Integer)aTempData.get(6));
                }
                if ((Integer)aTempData.get(7) != 0) {
                    pc.addSp(-(int)(Integer)aTempData.get(7));
                }
                if ((Integer)aTempData.get(8) != 0) {
                    pc.addMaxHp(-(int)(Integer)aTempData.get(8));
                    pc.setCurrentHp(pc.getCurrentHp() - (Integer)aTempData.get(8));
                }
                if ((Integer)aTempData.get(9) != 0) {
                    pc.addMaxMp(-(int)(Integer)aTempData.get(9));
                    pc.setCurrentMp(pc.getCurrentMp() - (Integer)aTempData.get(9));
                }
                if ((Integer)aTempData.get(10) != 0) {
                    pc.addother_ReductionDmg(-(int)(Integer)aTempData.get(10));
                }
                if ((Integer)aTempData.get(11) != 0) {
                    pc.addMr(-(int)(Integer)aTempData.get(11));
                }
                if ((Integer)aTempData.get(12) != 0) {
                    pc.addAc(-(int)(Integer)aTempData.get(12));
                }
                if ((Integer)aTempData.get(13) != 0) {
                    pc.addDmgup(-(int)(Integer)aTempData.get(13));
                }
                if ((Integer)aTempData.get(14) != 0) {
                    pc.addBowDmgup(-(int)(Integer)aTempData.get(14));
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
            pstmt = conn.prepareStatement("SELECT * FROM william_droptype_count");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rs.getInt("type_count")));
                    aReturn.add(1, new Integer(rs.getInt("str")));
                    aReturn.add(2, new Integer(rs.getInt("dex")));
                    aReturn.add(3, new Integer(rs.getInt("int")));
                    aReturn.add(4, new Integer(rs.getInt("con")));
                    aReturn.add(5, new Integer(rs.getInt("wis")));
                    aReturn.add(6, new Integer(rs.getInt("cha")));
                    aReturn.add(7, new Integer(rs.getInt("sp")));
                    aReturn.add(8, new Integer(rs.getInt("hp")));
                    aReturn.add(9, new Integer(rs.getInt("mp")));
                    aReturn.add(10, new Integer(rs.getInt("ReductionDmg")));
                    aReturn.add(11, new Integer(rs.getInt("Mr")));
                    aReturn.add(12, new Integer(rs.getInt("Ac")));
                    aReturn.add(13, new Integer(rs.getInt("attack")));
                    aReturn.add(14, new Integer(rs.getInt("bowattack")));
                    drop_type_count.aData.add(aReturn);
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
