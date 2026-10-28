package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.Random;
import java.util.ArrayList;



public class weapon_lv_gfx

{

    private static ArrayList<ArrayList<Object>> aData;

    private static boolean BUILD_DATA;

    private static Random _random;

    public static final String TOKEN = ",";

   

    static {

        weapon_lv_gfx.aData = new ArrayList();

        weapon_lv_gfx.BUILD_DATA = false;

        weapon_lv_gfx._random = new Random();

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

   

    public static void forWeapon(L1PcInstance pc, L1ItemInstance item, int weapon_level) {

        ArrayList<Object> aTempData = null;

        if (!weapon_lv_gfx.BUILD_DATA) {

            weapon_lv_gfx.BUILD_DATA = true;

            getData();

        }

        for (int i = 0; i < weapon_lv_gfx.aData.size(); ++i) {

            aTempData = weapon_lv_gfx.aData.get(i);

            if (weapon_level == (Integer)aTempData.get(0)) {

                if ((Integer)aTempData.get(1) != 0) {

                    pc.addDmgup((Integer)aTempData.get(1));

                    pc.sendPackets(new S_SystemMessage("\\aL近距離傷害+ " + (int)(Integer)aTempData.get(1)));

                }

                if ((Integer)aTempData.get(2) != 0) {

                    pc.addBowDmgup((Integer)aTempData.get(2));

                    pc.sendPackets(new S_SystemMessage("\\aL遠距離傷害+ " + (int)(Integer)aTempData.get(2)));

                }

                if ((Integer)aTempData.get(3) != 0) {

                    pc.addHitup((Integer)aTempData.get(3));

                    pc.sendPackets(new S_SystemMessage("\\aL近距離命中+ " + (int)(Integer)aTempData.get(3)));

                }

                if ((Integer)aTempData.get(4) != 0) {

                    pc.addBowHitup((Integer)aTempData.get(4));

                    pc.sendPackets(new S_SystemMessage("\\aL遠距離命中+ " + (int)(Integer)aTempData.get(4)));

                }

                if ((Integer)aTempData.get(5) != 0) {

                    pc.setmagicdmg((Integer)aTempData.get(5));

                    pc.sendPackets(new S_SystemMessage("\\aL魔法增加傷害+ " + (int)(Integer)aTempData.get(5)));

                }

                if ((Double)aTempData.get(6) > 0.0) {

                    pc.addDmgdouble((Double)aTempData.get(6));

                    pc.sendPackets(new S_SystemMessage("\\aL物理爆傷增加* " + (double)(Double)aTempData.get(6)));

                }

                if ((Integer)aTempData.get(7) != 0) {

                    pc.addSp((Integer)aTempData.get(7));

                    pc.sendPackets(new S_SystemMessage("\\aL魔攻+ " + (int)(Integer)aTempData.get(7)));

                }

                if ((Integer)aTempData.get(8) != 0) {

                    pc.setweaponran((Integer)aTempData.get(8));

                    if ((Integer)aTempData.get(9) != 0 && (Integer)aTempData.get(10) != 0) {

                        int type = (Integer)aTempData.get(10) - (Integer)aTempData.get(9);

                        pc.setweapondmg(type);

                        pc.sendPackets(new S_SystemMessage("\\aL" + (int)(Integer)aTempData.get(8) + "%機率傷害+" + (int)(Integer)aTempData.get(9) + "~" + (int)(Integer)aTempData.get(10)));

                    }

                    if ((Integer)aTempData.get(11) != 0) {

                        pc.setweaponchp((Integer)aTempData.get(11));

                        pc.sendPackets(new S_SystemMessage("\\aL" + (int)(Integer)aTempData.get(8) + "%機率吸血+" + (int)(Integer)aTempData.get(11) + "滴"));

                    }

                    if ((Integer)aTempData.get(12) != 0) {

                        pc.setweaponcmp((Integer)aTempData.get(12));

                        pc.sendPackets(new S_SystemMessage("\\aL" + (int)(Integer)aTempData.get(8) + "%機率吸血+" + (int)(Integer)aTempData.get(12) + "滴"));

                    }

                    if ((Integer)aTempData.get(13) != 0) {

                        pc.setweapongfx((Integer)aTempData.get(13));

                    }

                }

            }

        }

    }

   

    public static void forWeapon1(L1PcInstance pc, L1ItemInstance item, int weapon_level) {

        ArrayList<Object> aTempData = null;

        if (!weapon_lv_gfx.BUILD_DATA) {

            weapon_lv_gfx.BUILD_DATA = true;

            getData();

        }

        for (int i = 0; i < weapon_lv_gfx.aData.size(); ++i) {

            aTempData = weapon_lv_gfx.aData.get(i);

            if (weapon_level == (Integer)aTempData.get(0)) {

                if ((Integer)aTempData.get(1) != 0) {

                    pc.addDmgup(-(int)(Integer)aTempData.get(1));

                }

                if ((Integer)aTempData.get(2) != 0) {

                    pc.addBowDmgup(-(int)(Integer)aTempData.get(2));

                }

                if ((Integer)aTempData.get(3) != 0) {

                    pc.addHitup(-(int)(Integer)aTempData.get(3));

                }

                if ((Integer)aTempData.get(4) != 0) {

                    pc.addBowHitup(-(int)(Integer)aTempData.get(4));

                }

                if ((Integer)aTempData.get(5) != 0) {

                    pc.setmagicdmg(0);

                }

                if ((Double)aTempData.get(6) > 0.0) {

                    pc.addDmgdouble(-(double)(Double)aTempData.get(6));

                }

                if ((Integer)aTempData.get(7) != 0) {

                    pc.addSp(-(int)(Integer)aTempData.get(7));

                }

                if ((Integer)aTempData.get(8) != 0) {

                    pc.setweaponran(0);

                    if ((Integer)aTempData.get(9) != 0 && (Integer)aTempData.get(10) != 0) {

                        pc.setweapondmg(0);

                    }

                    if ((Integer)aTempData.get(11) != 0) {

                        pc.setweaponchp(0);

                    }

                    if ((Integer)aTempData.get(12) != 0) {

                        pc.setweaponcmp(0);

                    }

                    if ((Integer)aTempData.get(13) != 0) {

                        pc.setweapongfx(0);

                    }

                }

            }

        }

    }

   

    private static void getData() {

        Connection conn = null;

        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {

            conn = DatabaseFactory.get().getConnection();

            pstmt = conn.prepareStatement("SELECT * FROM w_過安定武器");

            rs = pstmt.executeQuery();

            ArrayList<Object> aReturn = null;

            if (rs != null) {

                while (rs.next()) {

                    aReturn = new ArrayList();

                    aReturn.add(0, new Integer(rs.getInt("Level")));

                    aReturn.add(1, new Integer(rs.getInt("近距離攻擊")));

                    aReturn.add(2, new Integer(rs.getInt("遠距離攻擊")));

                    aReturn.add(3, new Integer(rs.getInt("近距離命中")));

                    aReturn.add(4, new Integer(rs.getInt("遠距離命中")));

                    aReturn.add(5, new Integer(rs.getInt("魔法增傷")));

                    aReturn.add(6, new Double(rs.getDouble("物理爆擊")));

                    aReturn.add(7, new Integer(rs.getInt("魔攻")));

                    aReturn.add(8, new Integer(rs.getInt("機率")));

                    aReturn.add(9, new Integer(rs.getInt("最小攻擊")));

                    aReturn.add(10, new Integer(rs.getInt("最大攻擊")));

                    aReturn.add(11, new Integer(rs.getInt("吸血")));

                    aReturn.add(12, new Integer(rs.getInt("吸魔")));

                    aReturn.add(13, new Integer(rs.getInt("特效")));

                    weapon_lv_gfx.aData.add(aReturn);

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

