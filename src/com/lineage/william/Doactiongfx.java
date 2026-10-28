package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class Doactiongfx
{
    private static ArrayList aData17;
    private static boolean NO_MORE_GET_DATA17;
    public static final String TOKEN = ",";
    
    static {
        Doactiongfx.aData17 = new ArrayList();
        Doactiongfx.NO_MORE_GET_DATA17 = false;
    }
    
    public static void main(String[] a) {
        try {
            Server.main((String[])null);
        }
        catch (Exception ex) {}
    }
    
    public static void forItemUSe(L1NpcInstance npc, int npc1, int npcspan) {
        ArrayList aTempData = null;
        if (!Doactiongfx.NO_MORE_GET_DATA17) {
            Doactiongfx.NO_MORE_GET_DATA17 = true;
            getData15b();
        }
        for (int i = 0; i < Doactiongfx.aData17.size(); ++i) {
            aTempData = (ArrayList)Doactiongfx.aData17.get(i);
            if (npc1 == (Integer)aTempData.get(0)) {
                npc.broadcastPacketAll(new S_NPCPack(npc));
                npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), npcspan));
            }
        }
    }
    
    private static void getData15b() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 測試");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("npc_gfx")));
                    aReturn.add(1, new Integer(rset.getInt("DoActionGFX")));
                    Doactiongfx.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
}
