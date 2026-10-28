package com.lineage.william;

import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.templates.L1Item;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.world.World;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ItemIntegration
{
    private static final Log _logx;
    private static ArrayList<ArrayList<Object>> aData20;
    public static final String TOKEN = ",";
    private static ItemIntegration _instance;
    
    static {
        _logx = LogFactory.getLog(ItemIntegration.class);
        ItemIntegration.aData20 = new ArrayList();
    }
    
    public static ItemIntegration getInstance() {
        if (ItemIntegration._instance == null) {
            ItemIntegration._instance = new ItemIntegration();
        }
        return ItemIntegration._instance;
    }
    
    public static void reload() {
        ItemIntegration.aData20.clear();
        ItemIntegration._instance = null;
        getInstance();
    }
    
    public static void forItemIntegration(L1PcInstance pc, L1ItemInstance item, L1ItemInstance itemG) {
        int itemid = item.getItem().getItemId();
        ArrayList<?> aTempData = null;
        int i = 0;
        while (i < ItemIntegration.aData20.size()) {
            aTempData = ItemIntegration.aData20.get(i);
            if ((Integer)aTempData.get(0) == itemid && itemG.getItem().getItemId() == (Integer)aTempData.get(4)) {
                if (pc.getInventory().getSize() >= 170) {
                    pc.sendPackets(new S_ServerMessage("身上物品太多,無法操作"));
                    return;
                }
                else if ((double)pc.getInventory().getWeight() / pc.getMaxWeight() * 100.0 > 90.0) {
                    pc.sendPackets(new S_ServerMessage("身上物品負重,無法操作"));
                    return;
                }
                else {
                    if ((Integer)aTempData.get(1) != 0) {
                        byte class_id = 0;
                        String msg = "";
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
                        switch ((Integer)aTempData.get(1)) {
                            case 1: {
                                msg = "王族";
                                break;
                            }
                            case 2: {
                                msg = "騎士";
                                break;
                            }
                            case 3: {
                                msg = "法師";
                                break;
                            }
                            case 4: {
                                msg = "妖精";
                                break;
                            }
                            case 5: {
                                msg = "黑暗妖精";
                                break;
                            }
                        }
                        if ((int)(Integer)aTempData.get(1) != class_id && !pc.isGm()) {
                            pc.sendPackets(new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
                            return;
                        }
                    }
                    if ((Integer)aTempData.get(2) != 0 && !pc.isGm() && pc.getLevel() < (Integer)aTempData.get(2)) {
                        pc.sendPackets(new S_SystemMessage("等級" + (int)(Integer)aTempData.get(2) + "以上才可使用此道具。"));
                        return;
                    }
                    else if (item.getCount() >= (long)(int)(Integer)aTempData.get(3) || (Integer)aTempData.get(3) == 0) {
                        if (itemG.getItem().getItemId() == (Integer)aTempData.get(4)) {
                            if (itemG.getCount() >= (long)(int)(Integer)aTempData.get(5)) {
                                if (itemG.getEnchantLevel() >= (Integer)aTempData.get(19)) {
                                    if (((int[])(Object)aTempData.get(7) != null && (int[])(Object)aTempData.get(8) != null) || ((int[])(Object)aTempData.get(7) == null && (int[])(Object)aTempData.get(8) == null)) {
                                        int[] materials = (int[])(Object)aTempData.get(7);
                                        int[] counts = (int[])(Object)aTempData.get(8);
                                        boolean isCreate = true;
                                        if ((int[])(Object)aTempData.get(7) != null && (int[])(Object)aTempData.get(8) != null) {
                                            for (int j = 0; j < materials.length; ++j) {
                                                if (!pc.getInventory().checkItem(materials[j], (long)counts[j])) {
                                                    L1Item temp = ItemTable.get().getTemplate(materials[j]);
                                                    pc.sendPackets(new S_ServerMessage(337, temp.getName() + "(" + ((long)counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
                                                    isCreate = false;
                                                }
                                            }
                                        }
                                        if (isCreate) {
                                            if ((int[])(Object)aTempData.get(7) != null && (int[])(Object)aTempData.get(8) != null) {
                                                for (int k = 0; k < materials.length; ++k) {
                                                    pc.getInventory().consumeItem(materials[k], (long)counts[k]);
                                                }
                                            }
                                            if ((Integer)aTempData.get(3) != 0) {
                                                pc.getInventory().removeItem(item, (long)(int)(Integer)aTempData.get(3));
                                            }
                                            if ((int[])(Object)aTempData.get(9) != null && (int[])(Object)aTempData.get(10) != null) {
                                                int[] giveMaterials = (int[])(Object)aTempData.get(9);
                                                int[] giveCounts = (int[])(Object)aTempData.get(10);
                                                int rnd = (Integer)aTempData.get(6);
                                                if (RandomArrayList.getInc(100, 1) <= rnd) {
                                                    for (int l = 0; l < giveMaterials.length; ++l) {
                                                        L1ItemInstance item2 = ItemTable.get().createItem(giveMaterials[l]);
                                                        if (item2.isStackable()) {
                                                            item2.setCount((long)giveCounts[l]);
                                                        }
                                                        else {
                                                            item2.setCount(1L);
                                                        }
                                                        if (item2 != null) {
                                                            if (itemG.getItem().getType2() == 0 && item2.getItem().getType2() == 0) {
                                                                if (itemG.getItem().getType() == 32) {
                                                                    強化道具(pc, itemG, giveMaterials[l]);
                                                                }
                                                                else {
                                                                    pc.getInventory().removeItem(itemG, (long)(int)(Integer)aTempData.get(5));
                                                                    if (pc.getInventory().checkAddItem(item2, (long)giveCounts[l]) == 0) {
                                                                        pc.getInventory().storeItem(item2);
                                                                    }
                                                                    else {
                                                                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item2);
                                                                    }
                                                                }
                                                            }
                                                            else if (itemG.getItem().getType2() == 1 && item2.getItem().getType2() == 1) {
                                                                強化武器(pc, itemG, giveMaterials[l]);
                                                            }
                                                            else if (itemG.getItem().getType2() == 2 && item2.getItem().getType2() == 2) {
                                                                強化裝備(pc, itemG, giveMaterials[l]);
                                                            }
                                                            if ((String)aTempData.get(11) != null) {
                                                                pc.sendPackets(new S_SystemMessage((String)aTempData.get(11)));
                                                            }
                                                            else {
                                                                pc.sendPackets(new S_ServerMessage(403, item2.getLogName()));
                                                            }
                                                            if ((String)aTempData.get(18) != null) {
                                                                World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(18), pc.getName(), "+" + itemG.getEnchantLevel() + item2.getViewName())));
                                                            }
                                                            if ((Integer)aTempData.get(12) != 0) {
                                                                pc.sendPackets(new S_SkillSound(pc.getId(), (Integer)aTempData.get(12)));
                                                                pc.broadcastPacketAll(new S_SkillSound(pc.getId(), (Integer)aTempData.get(12)));
                                                            }
                                                            祝福升級成功("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (+" + itemG.getEnchantLevel() + item2.getViewName() + ")升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                                                        }
                                                    }
                                                    break;
                                                }
                                                else {
                                                    if ((Integer)aTempData.get(13) != 0) {
                                                        pc.getInventory().removeItem(itemG, (long)(int)(Integer)aTempData.get(5));
                                                    }
                                                    if ((String)aTempData.get(14) != null) {
                                                        pc.sendPackets(new S_SystemMessage((String)aTempData.get(14)));
                                                    }
                                                    祝福升級失敗("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (+" + itemG.getViewName() + ")升級(失敗) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                                                    if ((Integer)aTempData.get(15) == 0) {
                                                        pc.getInventory().removeItem(itemG, 1L);
                                                        break;
                                                    }
                                                    else {
                                                        break;
                                                    }
                                                }
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    pc.sendPackets(new S_SystemMessage("該物品強化值需求+" + (int)(Integer)aTempData.get(19)));
                                    break;
                                }
                            }
                            else {
                                L1Item temp2 = ItemTable.get().getTemplate((Integer)aTempData.get(4));
                                pc.sendPackets(new S_ServerMessage(337, temp2.getName() + "(" + ((long)(int)(Integer)aTempData.get(5) - pc.getInventory().countItems(temp2.getItemId())) + ")"));
                                break;
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage("沒發生什麼事情"));
                            break;
                        }
                    }
                    else {
                        L1Item temp3 = ItemTable.get().getTemplate(itemid);
                        pc.sendPackets(new S_ServerMessage(337, temp3.getName() + "(" + ((long)(int)(Integer)aTempData.get(3) - pc.getInventory().countItems(temp3.getItemId())) + ")"));
                        break;
                    }
                }
            }
            else {
                ++i;
            }
        }
    }
    
    public static void 祝福升級成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[祝福升級成功].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void 祝福升級失敗(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[祝福升級失敗].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private ItemIntegration() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            Statement stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT * FROM william_item_integration");
            ArrayList<Object> aReturn = null;
            if (rset != null) {
                while (rset.next()) {
                    aReturn = new ArrayList();
                    aReturn.add(0, new Integer(rset.getInt("道具")));
                    aReturn.add(1, new Integer(rset.getInt("職業")));
                    aReturn.add(2, new Integer(rset.getInt("等級")));
                    aReturn.add(3, new Integer(rset.getInt("道具數量")));
                    aReturn.add(4, new Integer(rset.getInt("Integration_ID")));
                    aReturn.add(5, new Integer(rset.getInt("Integration_count")));
                    aReturn.add(6, new Integer(rset.getInt("random")));
                    if (rset.getString("materials") != null && !rset.getString("materials").equals("") && !rset.getString("materials").equals("0")) {
                        aReturn.add(7, getArray(rset.getString("materials"), ",", 1));
                    }
                    else {
                        aReturn.add(7, null);
                    }
                    if (rset.getString("counts") != null && !rset.getString("counts").equals("") && !rset.getString("counts").equals("0")) {
                        aReturn.add(8, getArray(rset.getString("counts"), ",", 1));
                    }
                    else {
                        aReturn.add(8, null);
                    }
                    if (rset.getString("new_item") != null && !rset.getString("new_item").equals("") && !rset.getString("new_item").equals("0")) {
                        aReturn.add(9, getArray(rset.getString("new_item"), ",", 1));
                    }
                    else {
                        aReturn.add(9, null);
                    }
                    if (rset.getString("new_item_counts") != null && !rset.getString("new_item_counts").equals("") && !rset.getString("new_item_counts").equals("0")) {
                        aReturn.add(10, getArray(rset.getString("new_item_counts"), ",", 1));
                    }
                    else {
                        aReturn.add(10, null);
                    }
                    aReturn.add(11, rset.getString("msg"));
                    aReturn.add(12, new Integer(rset.getInt("gfxId")));
                    aReturn.add(13, new Integer(rset.getInt("失敗是否刪除材料")));
                    aReturn.add(14, rset.getString("失敗顯示"));
                    aReturn.add(15, new Integer(rset.getInt("失敗是否保留")));
                    aReturn.add(16, new Integer(rset.getInt("預留欄位1")));
                    aReturn.add(17, new Integer(rset.getInt("預留欄位2")));
                    aReturn.add(18, rset.getString("All_message"));
                    aReturn.add(19, new Integer(rset.getInt("EnchantItem")));
                    ItemIntegration.aData20.add(aReturn);
                }
            }
            stat.close();
            rset.close();
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
    
    private static void 強化裝備(L1PcInstance pc, L1ItemInstance item, int new_ItemId) {
        L1Item l1item = ItemTable.get().getTemplate(new_ItemId);
        if (item.getBless() == 0) {
            New_BlessItem.cleanall(pc, item);
        }
        pc.sendPackets(new S_DeleteInventoryItem(item.getId()));
        item.setItemId(new_ItemId);
        item.setItem(l1item);
        item.setBless(l1item.getBless());
        try {
            CharItemsReading.get().updateItemId_Name(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        pc.sendPackets(new S_AddItem(item));
        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
    }
    
    private static void 強化武器(L1PcInstance pc, L1ItemInstance item, int new_ItemId) {
        L1Item l1item = ItemTable.get().getTemplate(new_ItemId);
        if (item.getBless() == 0) {
            New_BlessItem.cleanall(pc, item);
        }
        pc.sendPackets(new S_DeleteInventoryItem(item.getId()));
        item.setItemId(new_ItemId);
        item.setItem(l1item);
        item.setBless(l1item.getBless());
        try {
            CharItemsReading.get().updateItemId_Name(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        pc.sendPackets(new S_AddItem(item));
        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
    }
    
    private static void 強化道具(L1PcInstance pc, L1ItemInstance item, int new_ItemId) {
        L1Item l1item = ItemTable.get().getTemplate(new_ItemId);
        if (item.getBless() == 0) {
            New_BlessItem.cleanall(pc, item);
        }
        pc.sendPackets(new S_DeleteInventoryItem(item.getId()));
        item.setItemId(new_ItemId);
        item.setItem(l1item);
        item.setBless(l1item.getBless());
        try {
            CharItemsReading.get().updateItemId_Name(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        pc.sendPackets(new S_AddItem(item));
        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
    }
}
