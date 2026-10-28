package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class Npc_BuyPet
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        Npc_BuyPet.aData = new ArrayList();
        Npc_BuyPet.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        ArrayList aTempData = null;
        if (!Npc_BuyPet.NO_GET_DATA) {
            Npc_BuyPet.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < Npc_BuyPet.aData.size(); ++i) {
            aTempData = Npc_BuyPet.aData.get(i);
            if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s)) {
                if (!pc.getInventory().checkItem((Integer)aTempData.get(2), (long)(int)(Integer)aTempData.get(3))) {
                    L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(2));
                    pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)(int)(Integer)aTempData.get(3) - pc.getInventory().countItems(temp.getItemId())) + ")"));
                    return false;
                }
                else if ((Integer)aTempData.get(4) == 0) {
                    pc.sendPackets(new S_ServerMessage("寵物編號尚未設定"));
                    return false;
                }
                else if (pc.getInventory().getSize() > 170) {
                    pc.sendPackets(new S_ServerMessage(337, "身上空間"));
                    return false;
                }
                else {
                    pc.getInventory().consumeItem((Integer)aTempData.get(2), (long)(int)(Integer)aTempData.get(3));
                    L1PcInventory inv = pc.getInventory();
                    L1ItemInstance petamu = inv.storeItem(40314, 1L);
                    if (petamu != null) {
                        PetReading.get().buyNewPet((Integer)aTempData.get(4), petamu.getId() + 1, petamu.getId(), (Integer)aTempData.get(5), ExpTable.getExpByLevel((Integer)aTempData.get(5)));
                        pc.sendPackets(new S_ItemName(petamu));
                        pc.sendPackets(new S_ServerMessage(403, petamu.getName()));
                    }
                }
            }
        }
        return false;
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_npc購買寵物項圈");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("npcid")));
                    sTemp = rset.getString("action");
                    aReturn.add(1, sTemp);
                    aReturn.add(2, new Integer(rset.getInt("確認道具")));
                    aReturn.add(3, new Integer(rset.getInt("確認道具數量")));
                    aReturn.add(4, new Integer(rset.getInt("購買寵物編號")));
                    aReturn.add(5, new Integer(rset.getInt("購買寵物等級")));
                    Npc_BuyPet.aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {}
    }
    
    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        String sTemp = null;
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                iReturn[i] = Integer.parseInt(sTemp);
            }
            return iReturn;
        }
        else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i = 0; i < iSize; ++i) {
                sTemp = st.nextToken();
                sReturn[i] = sTemp;
            }
            return sReturn;
        }
        else if (iType == 3) {
            String sReturn2 = null;
            for (int i = 0; i < iSize; ++i) {
                sTemp = (sReturn2 = st.nextToken());
            }
            return sReturn2;
        }
        else {
            return null;
        }
    }
}
