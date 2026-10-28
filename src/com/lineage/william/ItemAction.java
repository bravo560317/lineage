package com.lineage.william;

import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import java.util.Random;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import java.util.ArrayList;

public class ItemAction
{
    private static ArrayList<ArrayList<Object>> aData;
    public static final HashMap<Integer, ItemAction> _questionIdIndex;
    private static boolean NO_GET_DATA;
    public static final String TOKEN = ",";
    private static ItemAction _instance;
    
    static {
        ItemAction.aData = new ArrayList();
        _questionIdIndex = new HashMap();
        ItemAction.NO_GET_DATA = false;
    }
    
    public static ItemAction getInstance() {
        if (ItemAction._instance == null) {
            ItemAction._instance = new ItemAction();
        }
        return ItemAction._instance;
    }
    
    public static void reload() {
        ItemAction._questionIdIndex.clear();
        ItemAction._instance = null;
        getInstance();
    }
    
    private ItemAction() {
        getData();
    }
    
    public static boolean forNpcQuest(String s, L1PcInstance pc, L1ItemInstance item) {
        ArrayList aTempData = null;
        if (!ItemAction.NO_GET_DATA) {
            ItemAction.NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < ItemAction.aData.size(); ++i) {
            aTempData = ItemAction.aData.get(i);
            if (((String)aTempData.get(0)).equals(s)) {
                if (pc.getInventory().getSize() >= 170) {
                    pc.sendPackets(new S_ServerMessage(166, "身上太多東西,請先去清理"));
                    return false;
                }
                else {
                    String msg0 = "";
                    String msg2 = "";
                    String msg3 = "";
                    String msg4 = "";
                    String msg5 = "";
                    String msg6 = "";
                    String msg7 = "";
                    String msg8 = "";
                    String msg9 = "";
                    String msg10 = "";
                    String msg11 = "";
                    String msg12 = "";
                    String msg13 = "";
                    String msg14 = "";
                    String msg15 = "";
                    String msg16 = "";
                    String msg17 = "";
                    String msg18 = "";
                    String msg19 = "";
                    String msg20 = "";
                    String msg21 = "";
                    String msg22 = "";
                    if ((Integer)aTempData.get(1) != 0) {
                        msg0 = " " + (int)(Integer)aTempData.get(1) + "級以上。 ";
                    }
                    else {
                        msg0 = " 無限制 ";
                    }
                    if ((Integer)aTempData.get(2) == 1) {
                        msg2 = " 王族";
                    }
                    else if ((Integer)aTempData.get(2) == 2) {
                        msg2 = " 騎士";
                    }
                    else if ((Integer)aTempData.get(2) == 3) {
                        msg2 = " 法師";
                    }
                    else if ((Integer)aTempData.get(2) == 4) {
                        msg2 = " 妖精";
                    }
                    else if ((Integer)aTempData.get(2) == 5) {
                        msg2 = " 黑妖";
                    }
                    else if ((Integer)aTempData.get(2) == 0) {
                        msg2 = " 所有職業";
                    }
                    if ((Integer)aTempData.get(3) != 0) {
                        if ((Integer)aTempData.get(17) != 0) {
                            msg3 = new StringBuilder().append((Integer)aTempData.get(17)).toString();
                        }
                        else {
                            msg3 = new StringBuilder().append((Integer)aTempData.get(3)).toString();
                        }
                    }
                    else {
                        msg3 = "無限制";
                    }
                    if ((String)aTempData.get(5) != null) {
                        msg4 = new StringBuilder().append((String)aTempData.get(5)).toString();
                    }
                    else {
                        msg4 = "無標題";
                    }
                    int HammerCount1 = 0;
                    L1ItemInstance firehammer1 = pc.getInventory().findItemId((Integer)aTempData.get(18));
                    if (firehammer1 != null) {
                        HammerCount1 = (int)firehammer1.getCount();
                        if (HammerCount1 > (Integer)aTempData.get(20)) {
                            HammerCount1 = (Integer)aTempData.get(20);
                        }
                        if ((String)aTempData.get(19) != null && HammerCount1 > 0) {
                            msg22 = "[" + (String)aTempData.get(19) + "]成功機率+: " + HammerCount1 + " %";
                        }
                    }
                    int[] materials = (int[])aTempData.get(6);
                    int[] counts = (int[])aTempData.get(7);
                    int[] enchants = (int[])aTempData.get(8);
                    if (materials != null) {
                        for (int j = 0; j < materials.length; ++j) {
                            L1ItemInstance temp = ItemTable.get().createItem(materials[j]);
                            temp.setEnchantLevel(enchants[j]);
                            temp.setIdentified(true);
                            switch (j) {
                                case 0: {
                                    msg5 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 1: {
                                    msg6 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 2: {
                                    msg7 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 3: {
                                    msg8 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 4: {
                                    msg9 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 5: {
                                    msg10 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 6: {
                                    msg11 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 7: {
                                    msg12 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 8: {
                                    msg13 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 9: {
                                    msg14 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 10: {
                                    msg15 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 11: {
                                    msg16 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 12: {
                                    msg17 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 13: {
                                    msg18 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 14: {
                                    msg19 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 15: {
                                    msg19 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                                case 16: {
                                    msg21 = temp.getLogName() + " (" + counts[j] + ") 個";
                                    break;
                                }
                            }
                        }
                    }
                    if ((Integer)aTempData.get(4) == 1) {
                        pc.getInventory().removeItem(item, 1L);
                    }
                    String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22 };
                    pc.setitemactionhtml((String)aTempData.get(0));
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ItemBlend3", msgs));
                }
            }
            if (pc.getitemactionhtml() == (String)aTempData.get(0) && ((String)aTempData.get(9)).equals(s)) {
                int[] materials2 = (int[])aTempData.get(6);
                int[] counts2 = (int[])aTempData.get(7);
                int[] enchants2 = (int[])aTempData.get(8);
                if ((Integer)aTempData.get(1) != 0 && pc.getLevel() < (Integer)aTempData.get(1)) {
                    pc.sendPackets(new S_ServerMessage("等級必須為" + (int)(Integer)aTempData.get(1) + "以上。"));
                    return false;
                }
                else {
                    if ((Integer)aTempData.get(2) != 0) {
                        byte class_id = 0;
                        String Classmsg = "";
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
                        switch ((Integer)aTempData.get(2)) {
                            case 1: {
                                Classmsg = "王族";
                                break;
                            }
                            case 2: {
                                Classmsg = "騎士";
                                break;
                            }
                            case 3: {
                                Classmsg = "法師";
                                break;
                            }
                            case 4: {
                                Classmsg = "妖精";
                                break;
                            }
                            case 5: {
                                Classmsg = "黑暗妖精";
                                break;
                            }
                        }
                        if ((int)(Integer)aTempData.get(2) != class_id) {
                            pc.sendPackets(new S_ServerMessage(166, "職業必須是", Classmsg, "才能製造此道具"));
                            return false;
                        }
                    }
                    boolean enough = true;
                    int num = 0;
                    for (int k = 0; k < materials2.length; ++k) {
                        if (!pc.getInventory().checkEnchantItem(materials2[k], enchants2[k], (long)counts2[k])) {
                            L1Item temp2 = ItemTable.get().getTemplate(materials2[k]);
                            pc.sendPackets(new S_ServerMessage(337, temp2.getName() + "(" + ((long)counts2[k] - pc.getInventory().countItems(temp2.getItemId())) + ")"));
                            enough = false;
                        }
                    }
                    if (enough) {
                        if (++num == materials2.length) {
                            enough = true;
                        }
                        if (enough) {
                            int[] newcounts = new int[counts2.length];
                            for (int l = 0; l < counts2.length; ++l) {
                                newcounts[l] = counts2[l];
                                pc.getInventory().consumeEnchantItem(materials2[l], enchants2[l], (long)newcounts[l]);
                            }
                            int HammerCount2 = 0;
                            L1ItemInstance firehammer2 = pc.getInventory().findItemId((Integer)aTempData.get(18));
                            if (firehammer2 != null) {
                                HammerCount2 = (int)firehammer2.getCount();
                                if (HammerCount2 > (Integer)aTempData.get(20)) {
                                    HammerCount2 = (Integer)aTempData.get(20);
                                }
                            }
                            int TotalChance = (Integer)aTempData.get(3) + HammerCount2;
                            Random _random = new Random();
                            int New_itemid = (Integer)aTempData.get(10);
                            int newcounts2;
                            int New_item_counts = newcounts2 = (Integer)aTempData.get(11);
                            if (_random.nextInt(100) < TotalChance) {
                                L1ItemInstance newitem = ItemTable.get().createItem(New_itemid);
                                if (newitem != null) {
                                    if (newitem.isStackable()) {
                                        newitem.setEnchantLevel((Integer)aTempData.get(12));
                                        newitem.setIdentified(true);
                                        newitem.setCount((long)newcounts2);
                                        pc.getInventory().storeItem(newitem);
                                        pc.sendPackets(new S_ServerMessage(143, newitem.getLogName()));
                                        if ((String)aTempData.get(13) != null && (Integer)aTempData.get(15) == 1) {
                                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(13), pc.getName(), newitem.getLogName())));
                                        }
                                        if ((Integer)aTempData.get(16) == 1) {
                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                        }
                                        enough = false;
                                    }
                                    else {
                                        L1ItemInstance newitem2 = ItemTable.get().createItem(New_itemid);
                                        newitem2.setEnchantLevel((Integer)aTempData.get(12));
                                        newitem2.setIdentified(true);
                                        newitem2.setCount(1L);
                                        pc.getInventory().storeItem(newitem2);
                                        pc.sendPackets(new S_ServerMessage(143, newitem2.getLogName()));
                                        if ((String)aTempData.get(13) != null && (Integer)aTempData.get(15) == 1) {
                                            World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(13), pc.getName(), newitem.getLogName())));
                                        }
                                        if ((Integer)aTempData.get(16) == 1) {
                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                        }
                                        enough = false;
                                    }
                                    return false;
                                }
                            }
                            else if ((Integer)aTempData.get(21) > 0 && (Integer)aTempData.get(22) > 0) {
                                L1ItemInstance residueitem = ItemTable.get().createItem((Integer)aTempData.get(21));
                                residueitem.setEnchantLevel((Integer)aTempData.get(23));
                                residueitem.setIdentified(true);
                                residueitem.setCount((long)(int)(Integer)aTempData.get(22));
                                pc.getInventory().storeItem(residueitem);
                                pc.sendPackets(new S_ServerMessage("退還物品:" + residueitem.getLogName()));
                                pc.sendPackets(new S_ServerMessage((String)aTempData.get(14)));
                                if ((Integer)aTempData.get(16) == 1) {
                                    pc.sendPackets(new S_CloseList(pc.getId()));
                                }
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage((String)aTempData.get(14)));
                                if ((Integer)aTempData.get(16) == 1) {
                                    pc.sendPackets(new S_CloseList(pc.getId()));
                                }
                                enough = false;
                                return false;
                            }
                        }
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
            ResultSet rset = stat.executeQuery("SELECT * FROM w_道具火神製作");
            ArrayList<Object> aReturn = null;
            String sTemp = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    sTemp = rset.getString("action");
                    aReturn.add(0, sTemp);
                    aReturn.add(1, rset.getInt("checkLevel_min"));
                    aReturn.add(2, rset.getInt("classid"));
                    aReturn.add(3, new Integer(rset.getInt("成功機率")));
                    aReturn.add(4, new Integer(rset.getInt("是否扣除道具")));
                    aReturn.add(5, rset.getString("製造清單名稱"));
                    if (rset.getString("需求物品") != null && !rset.getString("需求物品").equals("") && !rset.getString("需求物品").equals("0")) {
                        aReturn.add(6, getArray(rset.getString("需求物品"), ",", 1));
                    }
                    else {
                        aReturn.add(6, null);
                    }
                    if (rset.getString("需求物品數量") != null && !rset.getString("需求物品數量").equals("") && !rset.getString("需求物品數量").equals("0")) {
                        aReturn.add(7, getArray(rset.getString("需求物品數量"), ",", 1));
                    }
                    else {
                        aReturn.add(7, null);
                    }
                    aReturn.add(8, getArray(rset.getString("需求物品強化值"), ",", 1));
                    aReturn.add(9, rset.getString("確認製作action"));
                    aReturn.add(10, rset.getInt("給予道具"));
                    aReturn.add(11, rset.getInt("給予道具數量"));
                    aReturn.add(12, rset.getInt("給予道具強化值"));
                    aReturn.add(13, rset.getString("製作成功"));
                    aReturn.add(14, rset.getString("製作失敗"));
                    aReturn.add(15, new Integer(rset.getInt("世界廣播")));
                    aReturn.add(16, new Integer(rset.getInt("是否關閉對話視窗")));
                    aReturn.add(17, rset.getInt("顯示假機率"));
                    aReturn.add(18, new Integer(rset.getInt("自訂機率道具")));
                    aReturn.add(19, rset.getString("自訂機率道具名稱"));
                    aReturn.add(20, new Integer(rset.getInt("自訂機率數量上限")));
                    aReturn.add(21, new Integer(rset.getInt("失敗退還道具")));
                    aReturn.add(22, new Integer(rset.getInt("失敗退還道具數量")));
                    aReturn.add(23, new Integer(rset.getInt("失敗退還強化值")));
                    ItemAction.aData.add(aReturn);
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
