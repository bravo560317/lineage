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
import com.lineage.server.world.World;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class LaBarGameTable1
{
    private static final Log _log;
    private static ArrayList<ArrayList<Object>> _array;
    private static final String TOKEN = ",";
    private static LaBarGameTable1 _instance;
    
    static {
        _log = LogFactory.getLog(LaBarGameTable1.class);
        LaBarGameTable1._array = new ArrayList();
    }
    
    public static LaBarGameTable1 get() {
        if (LaBarGameTable1._instance == null) {
            LaBarGameTable1._instance = new LaBarGameTable1();
        }
        return LaBarGameTable1._instance;
    }
    
    private LaBarGameTable1() {
        PerformanceTimer timer = new PerformanceTimer();
        this.getData();
        if (LaBarGameTable1._array.size() <= 0) {
            LaBarGameTable1._array.clear();
            LaBarGameTable1._array = null;
        }
    }
    
    public static boolean forLaBarGame(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        if (LaBarGameTable1._array.size() <= 0) {
            return false;
        }
        else {
            ArrayList<?> aTempData = null;
            for (int i = 0; i < LaBarGameTable1._array.size(); ++i) {
                aTempData = LaBarGameTable1._array.get(i);
                if (aTempData.get(0) != null && (Integer)aTempData.get(0) == npcid && ((String)aTempData.get(1)).equals(s)) {
                    if (pc.hasSkillEffect(123456)) {
                        pc.sendPackets(new S_SystemMessage("延遲中請稍後在操作"));
                        return false;
                    }
                    else {
                        boolean isCreate = true;
                        if ((Integer)aTempData.get(8) != 0 && (Integer)aTempData.get(9) != 0 && !pc.getInventory().checkItem((Integer)aTempData.get(8), (long)(int)(Integer)aTempData.get(9))) {
                            L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(8));
                            pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)(int)(Integer)aTempData.get(9) - pc.getInventory().countItems(temp.getItemId())) + ")"));
                            isCreate = false;
                        }
                        if (isCreate) {
                            pc.getInventory().consumeItem((Integer)aTempData.get(8), (long)(int)(Integer)aTempData.get(9));
                        }
                        if (isCreate) {
                            int[] spr = (int[])(Object)aTempData.get(2);
                            for (int j = 0; j < spr.length; ++j) {
                                try {
                                    pc.sendPackets(new S_SkillSound(pc.getId(), spr[j]));
                                    Thread.sleep((long)(int)(Integer)aTempData.get(10));
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            int[] 抽卡道具編號 = (int[])(Object)aTempData.get(3);
                            int rndItem = RandomArrayList.getInt(抽卡道具編號.length);
                            int giveItemGet = 抽卡道具編號[rndItem];
                            L1ItemInstance item = ItemTable.get().createItem(giveItemGet);
                            if (item.isStackable()) {
                                item.setCount((long)(int)(Integer)aTempData.get(4));
                            }
                            else {
                                item.setCount(1L);
                            }
                            if (item != null) {
                                if (pc.getInventory().checkAddItem(item, (long)(int)(Integer)aTempData.get(4)) == 0) {
                                    pc.getInventory().storeItem(item);
                                }
                                else {
                                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                                }
                                pc.sendPackets(new S_SystemMessage((String)aTempData.get(5)));
                                if ((Integer)aTempData.get(6) == 1) {
                                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(7), pc.getName(), item.getLogName())));
                                }
                                pc.setSkillEffect(123456, 10000);
                            }
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
            rset = ps.executeQuery("SELECT * FROM w_npc跑圖補丁spr");
            while (rset.next()) {
                ArrayList<Object> aReturn = new ArrayList();
                aReturn.add(0, new Integer(rset.getInt("npcid")));
                sTemp = rset.getString("action");
                aReturn.add(1, sTemp);
                if (rset.getString("spr編號") != null && !rset.getString("spr編號").equals("") && !rset.getString("spr編號").equals("0")) {
                    aReturn.add(2, getArray(rset.getString("spr編號"), ",", 1));
                }
                else {
                    aReturn.add(2, null);
                }
                if (rset.getString("抽卡道具編號") != null && !rset.getString("抽卡道具編號").equals("") && !rset.getString("抽卡道具編號").equals("0")) {
                    aReturn.add(3, getArray(rset.getString("抽卡道具編號"), ",", 1));
                }
                else {
                    aReturn.add(3, null);
                }
                aReturn.add(4, new Integer(rset.getInt("抽中數量")));
                aReturn.add(5, rset.getString("顯示文字"));
                aReturn.add(6, new Integer(rset.getInt("是否廣播")));
                aReturn.add(7, rset.getString("廣播文字"));
                aReturn.add(8, new Integer(rset.getInt("確認道具")));
                aReturn.add(9, new Integer(rset.getInt("確認道具數量")));
                aReturn.add(10, new Integer(rset.getInt("跑圖延遲毫秒")));
                LaBarGameTable1._array.add(aReturn);
            }
        }
        catch (SQLException e) {
            LaBarGameTable1._log.error(e.getLocalizedMessage(), e);
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
        return LaBarGameTable1._array;
    }
}
