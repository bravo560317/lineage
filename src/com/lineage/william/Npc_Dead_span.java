package com.lineage.william;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.Server;
import java.util.ArrayList;

public class Npc_Dead_span
{
    private static ArrayList aData17;
    private static boolean NO_MORE_GET_DATA17;
    public static final String TOKEN = ",";
    
    static {
        Npc_Dead_span.aData17 = new ArrayList();
        Npc_Dead_span.NO_MORE_GET_DATA17 = false;
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
    
    public static void forresolvent(L1PcInstance pc, L1MonsterInstance mob) {
        ArrayList aTempData = null;
        int[] iTemp = (int[])null;
        if (!Npc_Dead_span.NO_MORE_GET_DATA17) {
            Npc_Dead_span.NO_MORE_GET_DATA17 = true;
            getData17();
        }
        for (int i = 0; i < Npc_Dead_span.aData17.size(); ++i) {
            aTempData = (ArrayList)Npc_Dead_span.aData17.get(i);
            if ((Integer)aTempData.get(0) == mob.getNpcId() && mob.isDead()) {
                World.get().broadcastPacketToAll(new S_SystemMessage((String)aTempData.get(3)));
                if ((Integer)aTempData.get(1) > 0) {
                    L1SpawnUtil.spawn(pc, (Integer)aTempData.get(1), 0, (Integer)aTempData.get(2) * 60);
                }
                if ((Integer)aTempData.get(4) > 0) {
                    L1Teleport.teleport(pc, (Integer)aTempData.get(4), (Integer)aTempData.get(5), (short)(int)(Integer)aTempData.get(6), 5, true);
                }
            }
        }
    }
    
    private static void getData17() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_怪物死亡召喚");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("死亡NPC編號")));
                    aReturn.add(1, new Integer(rset.getInt("召喚NPC編號")));
                    aReturn.add(2, new Integer(rset.getInt("召喚NPC時間")));
                    aReturn.add(3, rset.getString("死亡NPC說話"));
                    aReturn.add(4, new Integer(rset.getInt("tele_x")));
                    aReturn.add(5, new Integer(rset.getInt("tele_y")));
                    aReturn.add(6, new Integer(rset.getInt("tele_mapid")));
                    Npc_Dead_span.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
}
