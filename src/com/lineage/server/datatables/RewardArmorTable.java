package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class RewardArmorTable
{
    private static ArrayList<ArrayList<Object>> aData15b;
    private static boolean NO_MORE_GET_DATA15b;
    private static RewardArmorTable _instance;
    
    static {
        RewardArmorTable.aData15b = new ArrayList();
        RewardArmorTable.NO_MORE_GET_DATA15b = false;
    }
    
    public static RewardArmorTable getInstance() {
        if (RewardArmorTable._instance == null) {
            RewardArmorTable._instance = new RewardArmorTable();
        }
        return RewardArmorTable._instance;
    }
    
    public static void forArmor(L1PcInstance pc, L1ItemInstance item, int weapon_level) {
        ArrayList<?> aTempData = null;
        if (!RewardArmorTable.NO_MORE_GET_DATA15b) {
            RewardArmorTable.NO_MORE_GET_DATA15b = true;
            getData15b();
        }
        for (int i = 0; i < RewardArmorTable.aData15b.size(); ++i) {
            aTempData = RewardArmorTable.aData15b.get(i);
            if (weapon_level == (Integer)aTempData.get(0)) {
                if ((Integer)aTempData.get(1) != 0) {
                    pc.sendPackets(new S_ServerMessage("\\aL物理減傷增加:" + (int)(Integer)aTempData.get(1)));
                    pc.add_reduction_dmg((Integer)aTempData.get(1));
                }
                if ((Integer)aTempData.get(2) != 0) {
                    pc.sendPackets(new S_ServerMessage("\\aL魔法減傷增加:" + (int)(Integer)aTempData.get(2)));
                    pc.add_magic_reduction_dmg((Integer)aTempData.get(2));
                }
                if ((Integer)aTempData.get(3) != 0) {
                    pc.addMaxHp((Integer)aTempData.get(3));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_ServerMessage("\\aL血量增加+" + (int)(Integer)aTempData.get(3)));
                    if (pc.isInParty()) {
                        pc.getParty().updateMiniHP(pc);
                    }
                }
                if ((Integer)aTempData.get(4) != 0) {
                    pc.addMaxMp((Integer)aTempData.get(4));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_ServerMessage("\\aL魔量增加+" + (int)(Integer)aTempData.get(4)));
                }
            }
        }
    }
    
    public static void forArmor1(L1PcInstance pc, L1ItemInstance item, int weapon_level) {
        ArrayList<?> aTempData = null;
        if (!RewardArmorTable.NO_MORE_GET_DATA15b) {
            RewardArmorTable.NO_MORE_GET_DATA15b = true;
            getData15b();
        }
        for (int i = 0; i < RewardArmorTable.aData15b.size(); ++i) {
            aTempData = RewardArmorTable.aData15b.get(i);
            if (weapon_level == (Integer)aTempData.get(0)) {
                if ((Integer)aTempData.get(1) != 0) {
                    pc.sendPackets(new S_ServerMessage("\\aL物理減傷減少:" + (int)(Integer)aTempData.get(1)));
                    pc.add_reduction_dmg(-(int)(Integer)aTempData.get(1));
                }
                if ((Integer)aTempData.get(2) != 0) {
                    pc.sendPackets(new S_ServerMessage("\\aL魔法減傷減少:" + (int)(Integer)aTempData.get(2)));
                    pc.add_magic_reduction_dmg(-(int)(Integer)aTempData.get(2));
                }
                if ((Integer)aTempData.get(3) != 0) {
                    pc.addMaxHp(-(int)(Integer)aTempData.get(3));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_ServerMessage("\\aL血量減少:" + (int)(Integer)aTempData.get(3)));
                    if (pc.isInParty()) {
                        pc.getParty().updateMiniHP(pc);
                    }
                }
                if ((Integer)aTempData.get(4) != 0) {
                    pc.addMaxMp(-(int)(Integer)aTempData.get(4));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_ServerMessage("\\aL魔量減少:" + (int)(Integer)aTempData.get(4)));
                }
            }
        }
    }
    
    private static void getData15b() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_過安定防具");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("Level")));
                    aReturn.add(1, new Integer(rset.getInt("Dmg_r")));
                    aReturn.add(2, new Integer(rset.getInt("Dmg_Magic_r")));
                    aReturn.add(3, new Integer(rset.getInt("Hp")));
                    aReturn.add(4, new Integer(rset.getInt("Mp")));
                    RewardArmorTable.aData15b.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
}
