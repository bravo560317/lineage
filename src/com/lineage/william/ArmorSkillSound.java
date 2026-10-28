package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class ArmorSkillSound
{
    private static ArrayList<ArrayList<Object>> aData15c;
    private static boolean NO_MORE_GET_DATA15c;
    public static final String TOKEN = ",";
    
    static {
        ArmorSkillSound.aData15c = new ArrayList();
        ArmorSkillSound.NO_MORE_GET_DATA15c = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void forArmorSkillSound(L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!ArmorSkillSound.NO_MORE_GET_DATA15c) {
            ArmorSkillSound.NO_MORE_GET_DATA15c = true;
            getData15c();
        }
        for (int i = 0; i < ArmorSkillSound.aData15c.size(); ++i) {
            aTempData = ArmorSkillSound.aData15c.get(i);
            if ((int[])aTempData.get(0) != null) {
                if (pc.getInventory().checkEquipped((int[])aTempData.get(0))) {
                    pc.sendPackets(new S_SkillSound(pc.getId(), (Integer)aTempData.get(1)));
                    pc.broadcastPacketAll(new S_SkillSound(pc.getId(), (Integer)aTempData.get(1)));
                }
            }
        }
    }
    
    private static void getData15c() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 系統_裝備穿著特效");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    if (rset.getString("armor_id") != null && !rset.getString("armor_id").equals("") && !rset.getString("armor_id").equals("0")) {
                        aReturn.add(0, convert(rset.getString("armor_id").split(",")));
                    }
                    else {
                        aReturn.add(0, null);
                    }
                    aReturn.add(1, new Integer(rset.getInt("gfxId")));
                    ArmorSkillSound.aData15c.add(aReturn);
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
