package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
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
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class CastleOriginal
{
    private static final Log _logx;
    private static ArrayList<ArrayList<Object>> aData1;
    public static final String TOKEN = ",";
    private static CastleOriginal _instance;
    
    static {
        _logx = LogFactory.getLog(CastleOriginal.class);
        CastleOriginal.aData1 = new ArrayList();
    }
    
    public static CastleOriginal getInstance() {
        if (CastleOriginal._instance == null) {
            CastleOriginal._instance = new CastleOriginal();
        }
        return CastleOriginal._instance;
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
    
    public static void forCastleOriginal(L1PcInstance pc) {
        ArrayList<?> aTempData = null;
        int castle_id = 0;
        if (pc.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                castle_id = clan.getCastleId();
            }
        }
        for (int i = 0; i < CastleOriginal.aData1.size(); ++i) {
            aTempData = CastleOriginal.aData1.get(i);
            if ((Integer)aTempData.get(0) == castle_id) {
                String castle = "";
                if (castle_id == 1) {
                    castle = "肯特城";
                }
                else if (castle_id == 2) {
                    castle = "妖魔城";
                }
                else if (castle_id == 3) {
                    castle = "風木城";
                }
                else if (castle_id == 4) {
                    castle = "奇岩城";
                }
                else if (castle_id == 5) {
                    castle = "海音城";
                }
                else if (castle_id == 6) {
                    castle = "侏儒城";
                }
                else if (castle_id == 7) {
                    castle = "亞丁城";
                }
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
                if ((Integer)aTempData.get(1) <= pc.getLevel()) {
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
                            pc.addother_ReductionDmg((Integer)aTempData.get(21));
                            add20 = "(減免傷害+ " + (int)(Integer)aTempData.get(21) + ")";
                        }
                        if ((Integer)aTempData.get(24) != 0) {
                            pc.addHpr((Integer)aTempData.get(24));
                            add22 = "(回血+ " + (int)(Integer)aTempData.get(24) + ")";
                        }
                        if ((Integer)aTempData.get(25) != 0) {
                            pc.addMpr((Integer)aTempData.get(25));
                            add23 = "(回魔+ " + (int)(Integer)aTempData.get(25) + ")";
                        }
                        if ((Integer)aTempData.get(26) != 0) {
                            pc.addWeightReduction((Integer)aTempData.get(26));
                            add24 = "(負重+ " + (int)(Integer)aTempData.get(26) + ")";
                        }
                        pc.sendPackets(new S_SystemMessage("你所屬的城堡為：" + castle));
                        pc.sendPackets(new S_SystemMessage(add1 + add2 + add3 + add4 + add5 + add6 + add7 + add8 + add9 + add10 + add11 + add12 + add13 + add14 + add15 + add16 + add17 + add18 + add19 + add20 + add21 + add22 + add23 + add24));
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
    
    private CastleOriginal() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_城堡賦予能力");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("checkCastle")));
                    aReturn.add(1, new Integer(rset.getInt("checkLevel")));
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
                    aReturn.add(23, new Integer(rset.getInt("checkReincarnation")));
                    aReturn.add(24, new Integer(rset.getInt("AddHpr")));
                    aReturn.add(25, new Integer(rset.getInt("AddMpr")));
                    aReturn.add(26, new Integer(rset.getInt("AddWeight")));
                    CastleOriginal.aData1.add(aReturn);
                }
            }
            CastleOriginal._logx.info("載入城堡成員能力加成:(" + this.getClass().getSimpleName() + "): " + aReturn.size() + "(" + timer.get() + "ms)");
            stat.close();
            rset.close();
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
}
