package com.add;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import java.util.Calendar;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1DayPresent
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<L1DayPresent>> _buddyMap;
    private static ArrayList array;
    private static boolean NO_MORE_GET_DATA15d;
    public static final String TOKEN = ",";
    
    static {
        _log = LogFactory.getLog(L1DayPresent.class);
        _buddyMap = new HashMap();
        L1DayPresent.array = new ArrayList();
        L1DayPresent.NO_MORE_GET_DATA15d = false;
    }
    
    public static void forTimeMessage(L1PcInstance pc) {
        ArrayList aTempData = null;
        if (!L1DayPresent.NO_MORE_GET_DATA15d) {
            L1DayPresent.NO_MORE_GET_DATA15d = true;
            getData15d();
        }
        for (int i = 0; i < L1DayPresent.array.size(); ++i) {
            aTempData = (ArrayList)L1DayPresent.array.get(i);
            Calendar nowTime = Calendar.getInstance();
            if ((Integer)aTempData.get(0) != 0 && (Integer)aTempData.get(1) != 0) {
                if (nowTime.get(2) + 1 == (Integer)aTempData.get(0)) {
                    if (nowTime.get(5) == (Integer)aTempData.get(1)) {
                        if ((Integer)aTempData.get(3) != 0 && pc.getLevel() < (Integer)aTempData.get(3)) {
                            return;
                        }
                        else {
                            if ((Integer)aTempData.get(2) != 0) {
                                byte class_id = 0;
                                if (pc.isCrown()) {
                                    class_id = 1;
                                }
                                else if (pc.isKnight()) {
                                    class_id = 2;
                                }
                                else if (pc.isWizard()) {
                                    class_id = 3;
                                }
                                else if (pc.isElf()) {
                                    class_id = 4;
                                }
                                else if (pc.isDarkelf()) {
                                    class_id = 5;
                                }
                                else if (pc.isDragonKnight()) {
                                    class_id = 6;
                                }
                                else if (pc.isIllusionist()) {
                                    class_id = 7;
                                }
                                if ((int)(Integer)aTempData.get(2) != class_id) {
                                    return;
                                }
                            }
                            if ((int[])aTempData.get(5) != null && (int[])aTempData.get(6) != null) {
                                int[] giveMaterials = (int[])aTempData.get(5);
                                int[] giveCounts = (int[])aTempData.get(6);
                                for (int l = 0; l < giveMaterials.length; ++l) {
                                    L1ItemInstance item = ItemTable.get().createItem(giveMaterials[l]);
                                    if (item.isStackable()) {
                                        item.setCount((long)giveCounts[l]);
                                    }
                                    else {
                                        item.setCount(1L);
                                    }
                                    if (item != null) {
                                        if (pc.getInventory().checkAddItem(item, (long)giveCounts[l]) == 0) {
                                            pc.getInventory().storeItem(item);
                                        }
                                        else {
                                            World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                        }
                                        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                                    }
                                }
                            }
                            if ((String)aTempData.get(4) != null) {
                                pc.sendPackets(new S_SystemMessage((String)aTempData.get(4)));
                            }
                        }
                    }
                }
            }
            else if ((Integer)aTempData.get(0) == 0) {
                if ((Integer)aTempData.get(1) == 0) {
                    if ((Integer)aTempData.get(3) != 0 && pc.getLevel() < (Integer)aTempData.get(3)) {
                        return;
                    }
                    else {
                        if ((Integer)aTempData.get(2) != 0) {
                            byte class_id = 0;
                            if (pc.isCrown()) {
                                class_id = 1;
                            }
                            else if (pc.isKnight()) {
                                class_id = 2;
                            }
                            else if (pc.isWizard()) {
                                class_id = 3;
                            }
                            else if (pc.isElf()) {
                                class_id = 4;
                            }
                            else if (pc.isDarkelf()) {
                                class_id = 5;
                            }
                            else if (pc.isDragonKnight()) {
                                class_id = 6;
                            }
                            else if (pc.isIllusionist()) {
                                class_id = 7;
                            }
                            if ((int)(Integer)aTempData.get(2) != class_id) {
                                return;
                            }
                        }
                        if ((int[])aTempData.get(5) != null && (int[])aTempData.get(6) != null) {
                            int[] giveMaterials = (int[])aTempData.get(5);
                            int[] giveCounts = (int[])aTempData.get(6);
                            for (int l = 0; l < giveMaterials.length; ++l) {
                                L1ItemInstance item = ItemTable.get().createItem(giveMaterials[l]);
                                if (item.isStackable()) {
                                    item.setCount((long)giveCounts[l]);
                                }
                                else {
                                    item.setCount(1L);
                                }
                                if (item != null) {
                                    if (pc.getInventory().checkAddItem(item, (long)giveCounts[l]) == 0) {
                                        pc.getInventory().storeItem(item);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                    }
                                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                                }
                            }
                        }
                        if ((String)aTempData.get(4) != null) {
                            pc.sendPackets(new S_SystemMessage((String)aTempData.get(4)));
                        }
                    }
                }
            }
        }
    }
    
    private static void getData15d() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection conn = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM 系統_節日自動送禮");
            ArrayList arraylist = null;
            if (rset != null) {
                while (rset.next()) {
                    arraylist = new ArrayList();
                    arraylist.add(0, new Integer(rset.getInt("month")));
                    arraylist.add(1, new Integer(rset.getInt("day")));
                    arraylist.add(2, new Integer(rset.getInt("checkClass")));
                    arraylist.add(3, new Integer(rset.getInt("level")));
                    arraylist.add(4, rset.getString("message"));
                    if (rset.getString("new_item") != null && !rset.getString("new_item").equals("") && !rset.getString("new_item").equals("0")) {
                        arraylist.add(5, getArray(rset.getString("new_item"), ",", 1));
                    }
                    else {
                        arraylist.add(5, null);
                    }
                    if (rset.getString("new_item_counts") != null && !rset.getString("new_item_counts").equals("") && !rset.getString("new_item_counts").equals("0")) {
                        arraylist.add(6, getArray(rset.getString("new_item_counts"), ",", 1));
                    }
                    else {
                        arraylist.add(6, null);
                    }
                    L1DayPresent.array.add(arraylist);
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
        L1DayPresent._log.info("載入節日系統自動送禮: " + L1DayPresent._buddyMap.size() + "(" + timer.get() + "ms)");
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
