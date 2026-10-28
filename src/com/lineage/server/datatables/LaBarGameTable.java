package com.lineage.server.datatables;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.world.World;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.william.L1WilliamSystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class LaBarGameTable
{
    private static final Log _log;
    private static ArrayList<ArrayList<Object>> _array;
    private static final String TOKEN = ",";
    private static LaBarGameTable _instance;
    
    static {
        _log = LogFactory.getLog(LaBarGameTable.class);
        LaBarGameTable._array = new ArrayList();
    }
    
    public static LaBarGameTable get() {
        if (LaBarGameTable._instance == null) {
            LaBarGameTable._instance = new LaBarGameTable();
        }
        return LaBarGameTable._instance;
    }
    
    private LaBarGameTable() {
        PerformanceTimer timer = new PerformanceTimer();
        this.getData();
        LaBarGameTable._log.info("載入拉霸遊戲數據設置資料數量: " + LaBarGameTable._array.size() + "(" + timer.get() + "ms)");
        if (LaBarGameTable._array.size() <= 0) {
            LaBarGameTable._array.clear();
            LaBarGameTable._array = null;
        }
    }
    
    public static boolean forLaBarGame(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        if (LaBarGameTable._array.size() <= 0) {
            return false;
        }
        else {
            ArrayList<?> aTempData = null;
            for (int i = 0; i < LaBarGameTable._array.size(); ++i) {
                aTempData = LaBarGameTable._array.get(i);
                if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s)) {
                    if (pc.hasSkillEffect(123456)) {
                        pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(5)));
                        return false;
                    }
                    else {
                        boolean isCreate = true;
                        if ((Integer)aTempData.get(5) != 0 && (Integer)aTempData.get(6) != 0 && !pc.getInventory().checkItem((Integer)aTempData.get(5), (long)(int)(Integer)aTempData.get(6))) {
                            L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(5));
                            pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)(int)(Integer)aTempData.get(6) - pc.getInventory().countItems(temp.getItemId())) + ")"));
                            isCreate = false;
                        }
                        if (isCreate) {
                            pc.getInventory().consumeItem((Integer)aTempData.get(5), (long)(int)(Integer)aTempData.get(6));
                        }
                        if ((int[])(Object)aTempData.get(2) != null && (int[])(Object)aTempData.get(3) != null && isCreate) {
                            int[] giveItem = (int[])(Object)aTempData.get(2);
                            for (int j = 0; j < giveItem.length; ++j) {
                                try {
                                    L1ItemInstance item1 = ItemTable.get().createItem(giveItem[j]);
                                    pc.sendPackets(new S_BlueMessage(166, "\\fH" + item1.getName()));
                                    Thread.sleep(500L);
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (RandomArrayList.getInc(100, 1) >= 100 - (Integer)aTempData.get(7)) {
                                int[] giveCount = (int[])(Object)aTempData.get(3);
                                int[] giveEnchantlvl = (int[])(Object)aTempData.get(4);
                                int rndItem = RandomArrayList.getInt(giveItem.length);
                                int rndCount = RandomArrayList.getInt(giveCount.length);
                                int rndEnchantlvl = RandomArrayList.getInt(giveEnchantlvl.length);
                                int giveItemGet = giveItem[rndItem];
                                int giveCountGet = giveCount[rndCount];
                                int giveEnchantlvlGet = giveEnchantlvl[rndEnchantlvl];
                                L1ItemInstance item2 = ItemTable.get().createItem(giveItemGet);
                                if (item2.isStackable()) {
                                    item2.setCount((long)giveCountGet);
                                }
                                else {
                                    item2.setCount(1L);
                                }
                                if (item2.getItem().getType2() == 1 || item2.getItem().getType2() == 2) {
                                    item2.setEnchantLevel(giveEnchantlvlGet);
                                }
                                else {
                                    item2.setEnchantLevel(0);
                                }
                                item2.setIdentified(true);
                                if (item2 != null) {
                                    if (pc.getInventory().checkAddItem(item2, (long)giveCountGet) == 0) {
                                        pc.getInventory().storeItem(item2);
                                    }
                                    else {
                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item2);
                                    }
                                    pc.sendPackets(new S_PacketBoxGree(166, L1WilliamSystemMessage.ShowMessage(6) + item2.getName() + " " + giveCountGet + " 個"));
                                    pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(6) + item2.getName() + " " + giveCountGet + " 個。"));
                                }
                            }
                            else {
                                pc.sendPackets(new S_BlueMessage(166, L1WilliamSystemMessage.ShowMessage(7)));
                                pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(7)));
                            }
                            pc.setSkillEffect(123456, 10000);
                        }
                    }
                }
            }
            return false;
        }
    }
    
    private void getData() {
        Connection cn = null;
        Statement ps = null;
        ResultSet rset = null;
        String sTemp = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.createStatement();
            rset = ps.executeQuery("SELECT * FROM w_npc拉霸");
            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList();
                aReturn.add(0, new Integer(rset.getInt("npcid")));
                sTemp = rset.getString("action");
                aReturn.add(1, sTemp);
                if (rset.getString("GiveItem") != null && !rset.getString("GiveItem").equals("") && !rset.getString("GiveItem").equals("0")) {
                    aReturn.add(2, getArray(rset.getString("GiveItem"), ",", 1));
                }
                else {
                    aReturn.add(2, null);
                }
                if (rset.getString("GiveItemCount") != null && !rset.getString("GiveItemCount").equals("") && !rset.getString("GiveItemCount").equals("0")) {
                    aReturn.add(3, getArray(rset.getString("GiveItemCount"), ",", 1));
                }
                else {
                    aReturn.add(3, null);
                }
                if (rset.getString("GiveItemEnchantlvl") != null && !rset.getString("GiveItemEnchantlvl").equals("")) {
                    aReturn.add(4, getArray(rset.getString("GiveItemEnchantlvl"), ",", 1));
                }
                else {
                    aReturn.add(4, null);
                }
                aReturn.add(5, new Integer(rset.getInt("checkMoney")));
                aReturn.add(6, new Integer(rset.getInt("checkMoneyCount")));
                aReturn.add(7, new Integer(rset.getInt("random")));
                LaBarGameTable._array.add(aReturn);
            }
        }
        catch (SQLException e) {
            LaBarGameTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rset);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
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
        return LaBarGameTable._array;
    }
}
