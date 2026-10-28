package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.ArrayList;

public class ItemActionTele
{
    private static ArrayList<ArrayList<Object>> aData;
    private static boolean NO_GET_DATA;
    private static boolean tele;
    public static final String TOKEN = ",";
    
    static {
        ItemActionTele.aData = new ArrayList<ArrayList<Object>>();
        ItemActionTele.NO_GET_DATA = false;
        ItemActionTele.tele = true;
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc) {
        ArrayList<Object> aTempData = null;
        if (!ItemActionTele.NO_GET_DATA) {
            ItemActionTele.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemActionTele.aData.size(); ++i) {
            aTempData = ItemActionTele.aData.get(i);
            if (((String)aTempData.get(0)).equals(s)) {
                
                // 1. 原本的消耗道具檢查
                if ((Integer)aTempData.get(2) > 0 && !pc.getInventory().checkItem((Integer)aTempData.get(2), (long)(int)(Integer)aTempData.get(3))) {
                    L1Item temp = ItemTable.get().getTemplate((Integer)aTempData.get(2));
                    pc.sendPackets(new S_SystemMessage((temp != null ? temp.getNameId() : "道具") + "不足( " + (int)(Integer)aTempData.get(3) + ")，無法使用。"));
                    return false;
                }
                
                // 2. 新增：持有道具判定 (當「持有道具編號」大時 0 觸發)
                int holdItemId = (Integer) aTempData.get(9);
                int holdItemCount = (Integer) aTempData.get(10);
                if (holdItemId > 0 && !pc.getInventory().checkItem(holdItemId, (long) holdItemCount)) {
                    L1Item holdTemp = ItemTable.get().getTemplate(holdItemId);
                    pc.sendPackets(new S_SystemMessage("身上需持有 " + (holdTemp != null ? holdTemp.getNameId() : "指定道具") + " (" + holdItemCount + ") 個以上，才能傳送。"));
                    return false;
                }

                // 3. 等級不足判定
                else if (pc.getLevel() < (Integer)aTempData.get(1)) {
                    pc.sendPackets(new S_SystemMessage("等級不足( " + (int)(Integer)aTempData.get(1) + ")，無法傳送。"));
                    return false;
                }
                else if (pc.isParalyzed_guaji()) {
                    pc.sendPackets(new S_ServerMessage("負面狀態中無法使用"));
                    return false;
                }
                else {
                    boolean isTeleport = pc.getMap().isTeleportable();
                    if (!isTeleport) {
                        pc.sendPackets(new S_ServerMessage("該地圖無法使用此功能"));
                        return false;
                    }
                    else {
                        if ((Integer)aTempData.get(4) > 0) {
                            int time = (Integer)aTempData.get(7);
                            int locX = (Integer)aTempData.get(4);
                            int locY = (Integer)aTempData.get(5);
                            int mapId = (Integer)aTempData.get(6);
                            L1Map map = L1WorldMap.get().getMap((short)mapId);
                            int r = 10;
                            int tryCount = 0;
                            int newX = locX;
                            int newY = locY;
                            do {
                                ++tryCount;
                                newX = locX + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                                newY = locY + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                                if (map.isPassable(newX, newY, pc)) {
                                    break;
                                }
                            } while (tryCount < 5);
                            if (tryCount >= 5) {
                                L1Teleport.teleport(pc, locX, locY, (short)mapId, pc.getHeading(), true);
                            }
                            else {
                                L1Teleport.teleport(pc, newX, newY, (short)mapId, pc.getHeading(), true);
                            }
                            
                            // 扣除消耗道具
                            pc.getInventory().consumeItem((Integer)aTempData.get(2), (long)(int)(Integer)aTempData.get(3));
                            
                            if (time > 0) {
                                pc.get_other().set_usemap((int)pc.getMapId());
                                ServerUseMapTimer.put(pc, time);
                                pc.sendPackets(new S_ServerMessage("地圖時間限制:" + time + "秒"));
                            }
                            if ((String)aTempData.get(8) != null) {
                                pc.sendPackets(new S_SystemMessage((String)aTempData.get(8)));
                            }
                        }
                        pc.sendPackets(new S_CloseList(pc.getId()));
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
            ResultSet rset = stat.executeQuery("SELECT * FROM w_Action對話傳送");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList<Object>();
                    sTemp = rset.getString("action");
                    aReturn.add(0, sTemp);
                    aReturn.add(1, rset.getInt("等級"));
                    aReturn.add(2, Integer.valueOf(rset.getInt("消耗道具編號")));
                    aReturn.add(3, Integer.valueOf(rset.getInt("消耗道具數量")));
                    aReturn.add(4, Integer.valueOf(rset.getInt("傳送座標X")));
                    aReturn.add(5, Integer.valueOf(rset.getInt("傳送座標Y")));
                    aReturn.add(6, Integer.valueOf(rset.getInt("傳送座標M")));
                    aReturn.add(7, Integer.valueOf(rset.getInt("限制時間")));
                    aReturn.add(8, rset.getString("傳送顯示文字"));
                    // 新增讀取欄位
                    aReturn.add(9, Integer.valueOf(rset.getInt("持有道具編號")));
                    aReturn.add(10, Integer.valueOf(rset.getInt("持有道具數量")));
                    
                    ItemActionTele.aData.add(aReturn);
                }
            }
            if (rset != null) {
                rset.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
}