package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class Npc_Rush
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    
    static {
        Npc_Rush.aData = new ArrayList();
        Npc_Rush.NO_GET_DATA = false;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        ArrayList aTempData = null;
        if (!Npc_Rush.NO_GET_DATA) {
            Npc_Rush.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < Npc_Rush.aData.size(); ++i) {
            aTempData = Npc_Rush.aData.get(i);
            boolean success = false;
            if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s)) {
                int[] checkitem = (int[])aTempData.get(2);
                int chekitem_count = (Integer)aTempData.get(3);
                int chekitem_en = (Integer)aTempData.get(4);
                int newitem1 = (Integer)aTempData.get(7);
                int chekitem_en2 = (Integer)aTempData.get(8);
                int[] materials = (int[])aTempData.get(5);
                int[] counts = (int[])aTempData.get(6);
                if (materials != null) {
                    for (int j = 0; j < materials.length; ++j) {
                        if (!pc.getInventory().checkItem(materials[j], (long)counts[j])) {
                            L1Item temp = ItemTable.get().getTemplate(materials[j]);
                            pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
                            success = false;
                        }
                    }
                }
                int q = 0;
                while (q < checkitem.length) {
                    if (pc.getInventory().checkEnchantItem(checkitem[q], chekitem_en, (long)chekitem_count) && pc.getInventory().checkItem(materials, counts)) {
                        pc.getInventory().consumeEnchantItem(checkitem[q], chekitem_en, (long)chekitem_count);
                        for (int k = 0; k < materials.length; ++k) {
                            pc.getInventory().consumeItem(materials[k], (long)counts[k]);
                        }
                        L1ItemInstance item = ItemTable.get().createItem(newitem1);
                        item.setEnchantLevel(chekitem_en2);
                        item.setIdentified(true);
                        pc.getInventory().storeItem(item);
                        pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                        success = true;
                        pc.sendPackets(new S_CloseList(pc.getId()));
                        break;
                    }
                    else {
                        ++q;
                    }
                }
                if (!success && aTempData.get(9) != null) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), (String)aTempData.get(9)));
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
            ResultSet rset = stat.executeQuery("SELECT * FROM w_npc製作");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("npcid")));
                    sTemp = rset.getString("action");
                    aReturn.add(1, sTemp);
                    aReturn.add(2, getArray(rset.getString("偵測身上物品"), ",", 1));
                    aReturn.add(3, rset.getInt("偵測身上物品數量"));
                    aReturn.add(4, rset.getInt("偵測物品加成"));
                    aReturn.add(5, getArray(rset.getString("檢查身上材料"), ",", 1));
                    aReturn.add(6, getArray(rset.getString("檢查身上材料數量"), ",", 1));
                    aReturn.add(7, rset.getInt("給予物品"));
                    aReturn.add(8, rset.getInt("給予物品加成"));
                    aReturn.add(9, new String(rset.getString("不足顯示對話")));
                    Npc_Rush.aData.add(aReturn);
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
