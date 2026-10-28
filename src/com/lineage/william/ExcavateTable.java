package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ExcavateTable
{
    private static final Log _log;
    private static ArrayList<ArrayList<Object>> _array;
    private static final String TOKEN = ",";
    private static ExcavateTable _instance;
    
    static {
        _log = LogFactory.getLog(ExcavateTable.class);
        ExcavateTable._array = new ArrayList();
    }
    
    public static ExcavateTable get() {
        if (ExcavateTable._instance == null) {
            ExcavateTable._instance = new ExcavateTable();
        }
        return ExcavateTable._instance;
    }
    
    private ExcavateTable() {
        PerformanceTimer timer = new PerformanceTimer();
        this.getData();
        ExcavateTable._log.info("載入挖礦遊戲設置資料數量: " + ExcavateTable._array.size() + "(" + timer.get() + "ms)");
        if (ExcavateTable._array.size() <= 0) {
            ExcavateTable._array.clear();
            ExcavateTable._array = null;
        }
    }
    
    public static void forExcavate(L1PcInstance pc) {
        if (ExcavateTable._array.size() <= 0) {
            return;
        }
        else {
            int MapId = pc.getMapId();
            ArrayList<?> aTempData = null;
            for (int i = 0; i < ExcavateTable._array.size(); ++i) {
                aTempData = ExcavateTable._array.get(i);
                if (check(pc) && (Integer)aTempData.get(0) == MapId) {
                    if ((Integer)aTempData.get(1) != 0 && pc.getLevel() < (Integer)aTempData.get(1)) {
                        pc.sendPackets(new S_SystemMessage("你的等級不足" + (int)(Integer)aTempData.get(1) + "，以至於挖不到任何東西"));
                        return;
                    }
                    else if ((Integer)aTempData.get(2) != 0 && !pc.getInventory().checkItem((Integer)aTempData.get(2))) {
                        L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(2));
                        pc.sendPackets(new S_SystemMessage("必需要有(" + temp.getName() + ")才挖的到東西。"));
                        return;
                    }
                    else {
                        int[] giveItem = (int[])(Object)aTempData.get(4);
                        int[] giveCount = (int[])(Object)aTempData.get(5);
                        int rndItem = RandomArrayList.getInt(giveItem.length);
                        int rndCount = RandomArrayList.getInt(giveCount.length);
                        int giveItemGet = giveItem[rndItem];
                        int giveCountGet = giveCount[rndCount];
                        int[] giveItem2 = (int[])(Object)aTempData.get(7);
                        int[] giveCount2 = (int[])(Object)aTempData.get(8);
                        int rndItem2 = RandomArrayList.getInt(giveItem2.length);
                        int rndCount2 = RandomArrayList.getInt(giveCount2.length);
                        int giveItemGet2 = giveItem2[rndItem2];
                        int giveCountGet2 = giveCount2[rndCount2];
                        int[] giveItem3 = (int[])(Object)aTempData.get(10);
                        int[] giveCount3 = (int[])(Object)aTempData.get(11);
                        int rndItem3 = RandomArrayList.getInt(giveItem3.length);
                        int rndCount3 = RandomArrayList.getInt(giveCount3.length);
                        int giveItemGet3 = giveItem3[rndItem3];
                        int giveCountGet3 = giveCount3[rndCount3];
                        int[] giveItem4 = (int[])(Object)aTempData.get(13);
                        int[] giveCount4 = (int[])(Object)aTempData.get(14);
                        int rndItem4 = RandomArrayList.getInt(giveItem4.length);
                        int rndCount4 = RandomArrayList.getInt(giveCount4.length);
                        int giveItemGet4 = giveItem4[rndItem4];
                        int giveCountGet4 = giveCount4[rndCount4];
                        int[] giveItem5 = (int[])(Object)aTempData.get(16);
                        int[] giveCount5 = (int[])(Object)aTempData.get(17);
                        int rndItem5 = RandomArrayList.getInt(giveItem5.length);
                        int rndCount5 = RandomArrayList.getInt(giveCount5.length);
                        int giveItemGet5 = giveItem5[rndItem5];
                        int giveCountGet5 = giveCount5[rndCount5];
                        if ((Integer)aTempData.get(2) != 0) {
                            L1PolyMorph.doPoly(pc, 3642, 8, 2);
                            pc.sendPackets(new S_DoActionGFX(pc.getId(), 7));
                            try {
                                Thread.sleep(10000L);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (RandomArrayList.getInc(100, 1) <= (Integer)aTempData.get(3)) {
                                L1ItemInstance item = ItemTable.get().createItem(giveItemGet);
                                if (item.isStackable()) {
                                    item.setCount((long)giveCountGet);
                                }
                                else {
                                    item.setCount(1L);
                                }
                                if (item != null) {
                                    if (pc.getInventory().checkAddItem(item, (long)giveCountGet) == 0) {
                                        pc.getInventory().storeItem(item);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    }
                                    pc.sendPackets(new S_SystemMessage("\\aE鏗、鏗.似乎挖到礦石[" + item.getName() + " " + giveCountGet + "]"));
                                }
                            }
                            else if (RandomArrayList.getInc(100, 1) <= (Integer)aTempData.get(6)) {
                                L1ItemInstance item = ItemTable.get().createItem(giveItemGet2);
                                if (item.isStackable()) {
                                    item.setCount((long)giveCountGet2);
                                }
                                else {
                                    item.setCount(1L);
                                }
                                if (item != null) {
                                    if (pc.getInventory().checkAddItem(item, (long)giveCountGet2) == 0) {
                                        pc.getInventory().storeItem(item);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    }
                                    pc.sendPackets(new S_SystemMessage("\\aI鏗、鏗.挖到不錯的礦石[" + item.getName() + " " + giveCountGet2 + "]"));
                                }
                            }
                            else if (RandomArrayList.getInc(100, 1) <= (Integer)aTempData.get(9)) {
                                L1ItemInstance item = ItemTable.get().createItem(giveItemGet3);
                                if (item.isStackable()) {
                                    item.setCount((long)giveCountGet3);
                                }
                                else {
                                    item.setCount(1L);
                                }
                                if (item != null) {
                                    if (pc.getInventory().checkAddItem(item, (long)giveCountGet3) == 0) {
                                        pc.getInventory().storeItem(item);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    }
                                    pc.sendPackets(new S_SystemMessage("\\aD鏗、挖到有點價值的礦石[" + item.getName() + " " + giveCountGet3 + "]"));
                                }
                            }
                            else if (RandomArrayList.getInc(100, 1) <= (Integer)aTempData.get(12)) {
                                L1ItemInstance item = ItemTable.get().createItem(giveItemGet4);
                                if (item.isStackable()) {
                                    item.setCount((long)giveCountGet4);
                                }
                                else {
                                    item.setCount(1L);
                                }
                                if (item != null) {
                                    if (pc.getInventory().checkAddItem(item, (long)giveCountGet4) == 0) {
                                        pc.getInventory().storeItem(item);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    }
                                    pc.sendPackets(new S_SystemMessage("\\aD鏗、挖到價值不斐的礦石[" + item.getName() + " " + giveCountGet4 + "]"));
                                }
                            }
                            else if (RandomArrayList.getInc(100, 1) <= (Integer)aTempData.get(15)) {
                                L1ItemInstance item = ItemTable.get().createItem(giveItemGet5);
                                if (item.isStackable()) {
                                    item.setCount((long)giveCountGet5);
                                }
                                else {
                                    item.setCount(1L);
                                }
                                if (item != null) {
                                    if (pc.getInventory().checkAddItem(item, (long)giveCountGet5) == 0) {
                                        pc.getInventory().storeItem(item);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    }
                                    pc.sendPackets(new S_SystemMessage("\\aD鏗、挖到黃金礦石[" + item.getName() + " " + giveCountGet5 + "]"));
                                }
                            }
                            else {
                                pc.sendPackets(new S_SystemMessage("\\fW什麼都沒有挖到。"));
                            }
                        }
                        pc.setSkillEffect(10010, 3000);
                    }
                }
            }
            return;
        }
    }
    
    public static void ExcavateKnow(L1PcInstance pc) {
        ArrayList<?> aTempData = null;
        pc.sendPackets(new S_SystemMessage("目前可以挖掘的地區有："));
        for (int i = 0; i < ExcavateTable._array.size(); ++i) {
            aTempData = ExcavateTable._array.get(i);
            pc.sendPackets(new S_SystemMessage(MapsTable.get().getLoaction((Integer)aTempData.get(0))));
        }
    }
    
    private void getData() {
        Connection cn = null;
        Statement ps = null;
        ResultSet rset = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.createStatement();
            rset = ps.executeQuery("SELECT * FROM w_道具挖礦");
            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList();
                aReturn.add(0, new Integer(rset.getInt("Map_Id")));
                aReturn.add(1, new Integer(rset.getInt("Level")));
                aReturn.add(2, new Integer(rset.getInt("Check_Item")));
                aReturn.add(3, new Integer(rset.getInt("Random")));
                aReturn.add(4, getArray(rset.getString("Give_Item"), ",", 1));
                aReturn.add(5, getArray(rset.getString("Give_Item_Count"), ",", 1));
                aReturn.add(6, new Integer(rset.getInt("Random1")));
                aReturn.add(7, getArray(rset.getString("Give_Item1"), ",", 1));
                aReturn.add(8, getArray(rset.getString("Give_Item_Count1"), ",", 1));
                aReturn.add(9, new Integer(rset.getInt("Random2")));
                aReturn.add(10, getArray(rset.getString("Give_Item2"), ",", 1));
                aReturn.add(11, getArray(rset.getString("Give_Item_Count2"), ",", 1));
                aReturn.add(12, new Integer(rset.getInt("Random3")));
                aReturn.add(13, getArray(rset.getString("Give_Item3"), ",", 1));
                aReturn.add(14, getArray(rset.getString("Give_Item_Count3"), ",", 1));
                aReturn.add(15, new Integer(rset.getInt("Random4")));
                aReturn.add(16, getArray(rset.getString("Give_Item4"), ",", 1));
                aReturn.add(17, getArray(rset.getString("Give_Item_Count4"), ",", 1));
                ExcavateTable._array.add(aReturn);
            }
        }
        catch (SQLException e) {
            ExcavateTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rset);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
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
    
    public static ArrayList<ArrayList<Object>> getSetList() {
        return ExcavateTable._array;
    }
}
