package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import java.util.ArrayList;

public class ItemBox
{
    private static ArrayList<ArrayList<Object>> aData;
    public static final HashMap<Integer, ItemBox> _questionIdIndex;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    private static ItemBox _instance;
    
    static {
        ItemBox.aData = new ArrayList();
        _questionIdIndex = new HashMap();
        ItemBox.NO_GET_DATA = false;
    }
    
    public static ItemBox getInstance() {
        if (ItemBox._instance == null) {
            ItemBox._instance = new ItemBox();
        }
        return ItemBox._instance;
    }
    
    public static void reload() {
        ItemBox._questionIdIndex.clear();
        ItemBox._instance = null;
        getInstance();
    }
    
    private ItemBox() {
        getData();
    }
    
    public static void trueOutburst(L1PcInstance pc, L1ItemInstance item) {
        ArrayList aTempData = null;
        if (!ItemBox.NO_GET_DATA) {
            ItemBox.NO_GET_DATA = true;
            getData();
        }
        int itemid = item.getItemId();
        for (int i = 0; i < ItemBox.aData.size(); ++i) {
            aTempData = ItemBox.aData.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(0) == itemid) {
                    if ((Integer)aTempData.get(1) != 0 && pc.getLevel() < (Integer)aTempData.get(1)) {
                        pc.sendPackets(new S_SystemMessage("等級" + (int)(Integer)aTempData.get(1) + "以上才可使用此道具。"));
                        return;
                    }
                    else {
                        int[] giveItem = (int[])aTempData.get(2);
                        int[] giveCount = (int[])aTempData.get(3);
                        int rndItem = RandomArrayList.getInt(giveItem.length);
                        int rndCount = RandomArrayList.getInt(giveCount.length);
                        int giveItemGet = giveItem[rndItem];
                        int giveCountGet = giveCount[rndCount];
                        L1ItemInstance item2 = ItemTable.get().createItem(giveItemGet);
                        if (item2.isStackable()) {
                            item2.setCount((long)giveCountGet);
                        }
                        else {
                            item2.setCount(1L);
                        }
                        item2.setIdentified(true);
                        if (item2 != null) {
                            if (pc.getInventory().checkAddItem(item2, (long)giveCountGet) != 0) {
                                return;
                            }
                            else {
                                pc.getInventory().storeItem(item2);
                                pc.sendPackets(new S_ServerMessage(403, item2.getName() + "(" + giveCountGet + ")"));
                            }
                        }
                        if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 0) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 1);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 1) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 2);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 2) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 3);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 3) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 4);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 4) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 5);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 5) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 6);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 6) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 7);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 7) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 8);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 8) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 9);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 9) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 10);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 10) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 11);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 11) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 12);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 12) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 13);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 13) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 14);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 14) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 15);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 15) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 16);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 16) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 17);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 17) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 18);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 18) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 19);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 19) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 20);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 20) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 21);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 21) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 22);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 22) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 23);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 23) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 24);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 24) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 25);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 25) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 26);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 26) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 27);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 27) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 28);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 28) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 29);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 29) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 30);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 30) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 31);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 31) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 32);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 32) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 33);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 33) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 34);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 34) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 35);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 35) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 36);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 36) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 37);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 37) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 38);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 38) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 39);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 39) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 40);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 40) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 41);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 41) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 42);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 42) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 43);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 43) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 44);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 44) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 45);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 45) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 46);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 46) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 47);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 47) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 48);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 48) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 49);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 49) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 50);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 50) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 51);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 51) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 52);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 52) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 53);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 53) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 54);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 54) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 55);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 55) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 56);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 56) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 57);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 57) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 58);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 58) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 59);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 59) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 60);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        else if (pc.getQuest().get_step((Integer)aTempData.get(0)) == 60) {
                            pc.getQuest().set_step((Integer)aTempData.get(0), 61);
                            checkboxcount(pc, item);
                            pc.getInventory().removeItem(item, 1L);
                        }
                        return;
                    }
                }
            }
        }
    }
    
    public static void checkboxcount(L1PcInstance pc, L1ItemInstance item) {
        ArrayList aTempData = null;
        if (!ItemBox.NO_GET_DATA) {
            ItemBox.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemBox.aData.size(); ++i) {
            aTempData = ItemBox.aData.get(i);
            if (pc.getQuest().get_step((Integer)aTempData.get(0)) == (Integer)aTempData.get(4)) {
                int[] giveItem = (int[])aTempData.get(5);
                int rndItem = RandomArrayList.getInt(giveItem.length);
                int giveItemGet = giveItem[rndItem];
                L1ItemInstance item2 = ItemTable.get().createItem(giveItemGet);
                item2.setCount(1L);
                item2.setIdentified(true);
                if (item2 != null) {
                    if (pc.getInventory().checkAddItem(item2, 1L) != 0) {
                        return;
                    }
                    else {
                        pc.getInventory().storeItem(item2);
                        pc.getQuest().set_step((Integer)aTempData.get(0), 0);
                        pc.sendPackets(new S_ServerMessage(403, item2.getName() + "(1)"));
                        if ((Integer)aTempData.get(6) == 1 && !pc.isGm()) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(7), pc.getName(), item2.getLogName())));
                        }
                    }
                }
            }
        }
    }
    
    private static void getData() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具寶相次數");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("道具編號")));
                    aReturn.add(1, new Integer(rset.getInt("等級限制")));
                    aReturn.add(2, getArray(rset.getString("隨機獲取道具"), ",", 1));
                    aReturn.add(3, getArray(rset.getString("隨機獲取數量"), ",", 1));
                    aReturn.add(4, new Integer(rset.getInt("達滿此開箱次數")));
                    aReturn.add(5, getArray(rset.getString("達滿給予物品"), ",", 1));
                    aReturn.add(6, new Integer(rset.getInt("是否世界廣播")));
                    aReturn.add(7, rset.getString("世界廣播文字"));
                    ItemBox.aData.add(aReturn);
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
