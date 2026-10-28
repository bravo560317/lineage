package com.lineage.william;

import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Item;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import java.util.ArrayList;

public class Itemup
{
    private static ArrayList aData17;
    private static Itemup _instance;
    private static boolean NO_MORE_GET_DATA17;
    private static Random _random;
    public static final String TOKEN = ",";
    
    static {
        Itemup.aData17 = new ArrayList();
        Itemup.NO_MORE_GET_DATA17 = false;
        Itemup._random = new Random();
    }
    
    public static Itemup get() {
        if (Itemup._instance == null) {
            Itemup._instance = new Itemup();
        }
        return Itemup._instance;
    }
    
    public static void forresolvent(L1PcInstance pc, L1ItemInstance item, int l) {
        int itemid = item.getItemId();
        L1ItemInstance tgitem = pc.getInventory().getItem(l);
        ArrayList aTempData = null;
        int[] iTemp = (int[])null;
        if (!Itemup.NO_MORE_GET_DATA17) {
            Itemup.NO_MORE_GET_DATA17 = true;
            getData17();
        }
        for (int i = 0; i < Itemup.aData17.size(); ++i) {
            aTempData = (ArrayList)Itemup.aData17.get(i);
            if ((Integer)aTempData.get(0) == itemid) {
                if ((Integer)aTempData.get(2) != 0 && pc.getLevel() < (Integer)aTempData.get(2)) {
                    pc.sendPackets(new S_SystemMessage("等級" + (int)(Integer)aTempData.get(2) + "以上才可使用此道具。"));
                    return;
                }
                else if ((Integer)aTempData.get(0) == item.getItemId() && (Integer)aTempData.get(5) == tgitem.getItemId()) {
                    if (tgitem.getBless() >= 4) {
                        pc.sendPackets(new S_SystemMessage("該裝備受到封印卷軸的影響。"));
                        return;
                    }
                    else if (pc.getDoll(tgitem.getId()) != null) {
                        pc.sendPackets(new S_ServerMessage(1181));
                        return;
                    }
                    else if (tgitem.isEquipped()) {
                        pc.sendPackets(new S_SystemMessage("請先把裝備脫掉在使用。"));
                        return;
                    }
                    else if (!pc.getDolls().isEmpty()) {
                        pc.sendPackets(new S_ServerMessage("請先解除魔法娃娃。"));
                        return;
                    }
                    else if (pc.getInventory().getSize() >= 170) {
                        pc.sendPackets(new S_SystemMessage("身上物品太多無法進行升級"));
                        return;
                    }
                    else if ((double)pc.getInventory().getWeight() / pc.getMaxWeight() * 100.0 > 90.0) {
                        pc.sendPackets(new S_SystemMessage("身上負重限制無法進行升級"));
                        return;
                    }
                    else {
                        int[] materials = (int[])aTempData.get(3);
                        int[] counts = (int[])aTempData.get(4);
                        boolean isCreate = true;
                        for (int j = 0; j < materials.length; ++j) {
                            if (!pc.getInventory().checkItem(materials[j], (long)counts[j])) {
                                L1Item temp = ItemTable.get().getTemplate(materials[j]);
                                pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
                                isCreate = false;
                            }
                        }
                        if (isCreate) {
                            for (int k = 0; k < materials.length; ++k) {
                                pc.getInventory().consumeItem(materials[k], (long)counts[k]);
                            }
                            int ran = 0;
                            if (pc.isGm()) {
                                ran = 99;
                            }
                            else {
                                ran = (Integer)aTempData.get(6);
                            }
                            if (ran >= Itemup._random.nextInt(100) + 1) {
                                L1Item l1item = ItemTable.get().getTemplate((Integer)aTempData.get(7));
                                if (tgitem.getBless() == 0) {
                                    New_BlessItem.cleanall(pc, tgitem);
                                }
                                pc.sendPackets(new S_DeleteInventoryItem(tgitem.getId()));
                                tgitem.setItemId((Integer)aTempData.get(7));
                                tgitem.setItem(l1item);
                                tgitem.setBless(l1item.getBless());
                                try {
                                    CharItemsReading.get().updateItemId_Name(tgitem);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                pc.sendPackets(new S_AddItem(tgitem));
                                pc.sendPackets(new S_ServerMessage(403, tgitem.getLogName()));
                                pc.sendPackets(new S_SystemMessage("\\fY" + (String)aTempData.get(9)));
                                升級成功("玩家 :" + pc.getName() + "使用 " + item.getName() + "對" + tgitem.getName() + "升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                                isCreate = false;
                                L1ItemInstance item2 = pc.getInventory().findItemId((Integer)aTempData.get(0));
                                pc.getInventory().removeItem(item2.getId(), 1L);
                                break;
                            }
                            else {
                                isCreate = false;
                                pc.sendPackets(new S_SystemMessage((String)aTempData.get(10)));
                                if ((Integer)aTempData.get(11) == 1) {
                                    pc.getInventory().removeItem(tgitem.getId(), 1L);
                                }
                                L1ItemInstance item3 = pc.getInventory().findItemId((Integer)aTempData.get(0));
                                pc.getInventory().removeItem(item3.getId(), 1L);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void getData17() {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM w_物品升級系統_1");
            ArrayList aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("item")));
                    aReturn.add(1, rset.getString("note"));
                    aReturn.add(2, new Integer(rset.getInt("CheckLevel")));
                    if (rset.getString("CheckItem") != null && !rset.getString("CheckItem").equals("") && !rset.getString("CheckItem").equals("0")) {
                        aReturn.add(3, getArray(rset.getString("CheckItem"), ",", 1));
                    }
                    else {
                        aReturn.add(3, null);
                    }
                    if (rset.getString("Checkcount") != null && !rset.getString("Checkcount").equals("") && !rset.getString("Checkcount").equals("0")) {
                        aReturn.add(4, getArray(rset.getString("Checkcount"), ",", 1));
                    }
                    else {
                        aReturn.add(4, null);
                    }
                    aReturn.add(5, new Integer(rset.getInt("ne_item")));
                    aReturn.add(6, new Integer(rset.getInt("random")));
                    aReturn.add(7, new Integer(rset.getInt("giveitem")));
                    aReturn.add(8, new Integer(rset.getInt("givecount")));
                    aReturn.add(9, rset.getString("Success"));
                    aReturn.add(10, rset.getString("Fail"));
                    aReturn.add(11, new Integer(rset.getInt("save_type")));
                    Itemup.aData17.add(aReturn);
                }
            }
            SQLUtil.close(rset);
            SQLUtil.close(stat);
            SQLUtil.close(con);
        }
        catch (Exception ex) {}
    }
    
    public static void 升級成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[升級成功].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
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
