package com.lineage.data.cmd;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.lock.CharShiftingReading;
import com.lineage.server.serverpackets.S_HelpMessage;
import com.lineage.server.model.L1Character;
import com.lineage.server.world.World;
import com.lineage.server.utils.DigitalUtil;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class CreateNewItem
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CreateNewItem.class);
    }
    
    public static boolean delItems(L1PcInstance pc, int[] srcItemIds, int[] counts, long amount) {
        try {
            if (pc == null) {
                return false;
            }
            else if (amount <= 0L) {
                return false;
            }
            else if (srcItemIds.length <= 0) {
                return false;
            }
            else if (counts.length <= 0) {
                return false;
            }
            else if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error("道具交換物品與數量陣列設置異常!");
                return false;
            }
            else {
                int i = 0;
                while (i < srcItemIds.length) {
                    long itemCount = (long)counts[i] * amount;
                    L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount);
                    if (item == null) {
                        return false;
                    }
                    else {
                        ++i;
                    }
                }
                i = 0;
                while (i < srcItemIds.length) {
                    long itemCount2 = (long)counts[i] * amount;
                    L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount2);
                    if (item != null) {
                        pc.getInventory().removeItem(item, itemCount2);
                        ++i;
                    }
                    else {
                        return false;
                    }
                }
                return true;
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    
    public static boolean getItem(L1PcInstance pc, L1NpcInstance npc, String cmd, int[] items, int[] counts, int[] gitems, int[] gcounts, long amount) {
        long xcount = checkNewItem(pc, items, counts);
        if (xcount > 0L) {
            if (amount == 0L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, cmd));
                return false;
            }
            else {
                if (xcount >= amount) {
                    createNewItem(pc, items, counts, gitems, amount, gcounts);
                }
                return true;
            }
        }
        else {
            return true;
        }
    }
    
    public static long checkNewItem(L1PcInstance pc, int srcItemId, int count) {
        try {
            if (pc == null) {
                return -1L;
            }
            else {
                L1ItemInstance item = pc.getInventory().findItemIdNoEq(srcItemId);
                long itemCount = -1L;
                if (item != null) {
                    itemCount = item.getCount() / (long)count;
                }
                if (itemCount < 1L) {
                    L1Item tgItem = ItemTable.get().getTemplate(srcItemId);
                    if (tgItem != null) {
                        pc.sendPackets(new S_ServerMessage(337, tgItem.getNameId() + "(" + ((long)count - ((item == null) ? 0L : item.getCount())) + ")"));
                    } else {
                        CreateNewItem._log.error("所需物品編號不存在於資料庫: " + srcItemId);
                        pc.sendPackets(new S_ServerMessage(337, "未知物品(" + srcItemId + ") (" + ((long)count - ((item == null) ? 0L : item.getCount())) + ")"));
                    }
                    return -1L;
                }
                else {
                    return itemCount;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
            return -1L;
        }
    }
    
    public static long checkNewItem(L1PcInstance pc, int[] srcItemIds, int[] counts) {
        try {
            if (pc == null) {
                return -1L;
            }
            else if (srcItemIds.length <= 0) {
                return -1L;
            }
            else if (counts.length <= 0) {
                return -1L;
            }
            else if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error("道具交換物品與數量陣列設置異常!");
                return -1L;
            }
            else {
                long[] checkCount = new long[srcItemIds.length];
                boolean error = false;
                for (int i = 0; i < srcItemIds.length; ++i) {
                    int itemid = srcItemIds[i];
                    int count = counts[i];
                    L1ItemInstance item = pc.getInventory().findItemIdNoEq(itemid);
                    if (item != null) {
                        long itemCount = item.getCount() / (long)count;
                        checkCount[i] = itemCount;
                        if (itemCount < 1L) {
                            pc.sendPackets(new S_ServerMessage(337, item.getName() + "(" + ((long)count - item.getCount()) + ")"));
                            error = true;
                        }
                    }
                    else {
                        L1Item tgItem = ItemTable.get().getTemplate(itemid);
                        if (tgItem != null) {
                            pc.sendPackets(new S_ServerMessage(337, tgItem.getNameId() + "(" + count + ")"));
                        } else {
                            CreateNewItem._log.error("所需物品編號不存在於資料庫: " + itemid);
                            pc.sendPackets(new S_ServerMessage(337, "未知物品(" + itemid + ") (" + count + ")"));
                        }
                        error = true;
                    }
                }
                if (!error) {
                    long count2 = DigitalUtil.returnMin(checkCount);
                    return count2;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
        return -1L;
    }
    
    public static void createNewItem(L1PcInstance pc, int[] srcItemIds, int[] counts, int getItemId, long amount, int getCount) {
        try {
            if (pc == null) {
                return;
            }
            else if (amount <= 0L) {
                return;
            }
            else if (srcItemIds.length <= 0) {
                return;
            }
            else if (counts.length <= 0) {
                return;
            }
            else if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error("道具交換物品與數量陣列設置異常!");
                return;
            }
            else if (getItemId == 0) {
                return;
            }
            else if (getCount == 0) {
                return;
            }
            else {
                boolean error = false;
                for (int i = 0; i < srcItemIds.length; ++i) {
                    long itemCount = (long)counts[i] * amount;
                    L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount);
                    if (item == null) {
                        error = true;
                    }
                }
                if (!error && !getItemIsOk(pc, getItemId, amount, getCount)) {
                    error = true;
                }
                if (!error) {
                    for (int i = 0; i < srcItemIds.length; ++i) {
                        long itemCount2 = (long)counts[i] * amount;
                        L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount2);
                        if (item != null) {
                            pc.getInventory().removeItem(item, itemCount2);
                        }
                        else {
                            error = true;
                        }
                    }
                }
                if (!error) {
                    giveItem(pc, getItemId, amount, getCount);
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void createNewItem(L1PcInstance pc, int[] srcItemIds, int[] counts, int[] getItemIds, long amount, int[] getCounts) {
        try {
            if (pc == null) {
                return;
            }
            else if (amount <= 0L) {
                return;
            }
            else if (srcItemIds.length <= 0) {
                return;
            }
            else if (counts.length <= 0) {
                return;
            }
            else if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error("道具交換物品與數量陣列設置異常!");
                return;
            }
            else if (getItemIds.length <= 0) {
                return;
            }
            else if (getCounts.length <= 0) {
                return;
            }
            else if (getItemIds.length != getCounts.length) {
                CreateNewItem._log.error("道具交換物品與數量陣列設置異常!");
                return;
            }
            else {
                boolean error = false;
                for (int i = 0; i < srcItemIds.length; ++i) {
                    long itemCount = (long)counts[i] * amount;
                    L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount);
                    if (item == null) {
                        error = true;
                    }
                }
                if (!error) {
                    for (int i = 0; i < getItemIds.length; ++i) {
                        if (!getItemIsOk(pc, getItemIds[i], amount, getCounts[i])) {
                            error = true;
                        }
                    }
                }
                if (!error) {
                    for (int i = 0; i < srcItemIds.length; ++i) {
                        long itemCount2 = (long)counts[i] * amount;
                        L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount2);
                        if (item != null) {
                            pc.getInventory().removeItem(item, itemCount2);
                        }
                        else {
                            error = true;
                        }
                    }
                }
                if (!error) {
                    for (int i = 0; i < getItemIds.length; ++i) {
                        giveItem(pc, getItemIds[i], amount, getCounts[i]);
                    }
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static boolean getItemIsOk(L1PcInstance pc, int getItemId, long amount, int getCount) {
        try {
            if (pc == null) {
                return false;
            }
            else {
                L1Item tgItem = ItemTable.get().getTemplate(getItemId);
                if (pc.getInventory().checkAddItem(tgItem, amount * (long)getCount) != 0) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
        return true;
    }
    
    private static void giveItem(L1PcInstance pc, int getItemId, long amount, int getCount) {
        try {
            if (pc == null) {
                return;
            }
            else {
                L1Item tgItem = ItemTable.get().getTemplate(getItemId);
                if (tgItem.isStackable()) {
                    L1ItemInstance tgItemX = ItemTable.get().createItem(getItemId);
                    tgItemX.setCount(amount * (long)getCount);
                    createNewItem(pc, tgItemX);
                }
                else {
                    for (int get = 0; (long)get < amount * (long)getCount; ++get) {
                        L1ItemInstance tgItemX2 = ItemTable.get().createItem(getItemId);
                        tgItemX2.setCount(1L);
                        createNewItem(pc, tgItemX2);
                    }
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void createNewItem(L1PcInstance pc, int srcItemId, int count, int getItemId, int getCount) {
        createNewItem(pc, srcItemId, count, getItemId, 1L, getCount);
    }
    
    public static void createNewItem(L1PcInstance pc, int srcItemId, int count, int getItemId, long amount, int getCount) {
        long itemCount1 = (long)count * amount;
        L1ItemInstance item1 = pc.getInventory().checkItemX(srcItemId, itemCount1);
        if (item1 != null) {
            L1ItemInstance tgItem = ItemTable.get().createItem(getItemId);
            if (pc.getInventory().checkAddItem(tgItem, amount * (long)getCount) == 0) {
                pc.getInventory().removeItem(item1, itemCount1);
                if (tgItem.isStackable()) {
                    tgItem.setCount(amount * (long)getCount);
                    createNewItem(pc, tgItem);
                }
                else {
                    for (int get = 0; (long)get < amount * (long)getCount; ++get) {
                        L1ItemInstance tgItemX = ItemTable.get().createItem(getItemId);
                        tgItemX.setCount(1L);
                        createNewItem(pc, tgItemX);
                    }
                }
            }
            else {
                World.get().removeObject(tgItem);
            }
        }
    }
    
    public static void createNewItem(L1PcInstance pc, int srcItemId, int count, int getItemId, long amount) {
        long itemCount1 = (long)count * amount;
        L1ItemInstance item1 = pc.getInventory().checkItemX(srcItemId, itemCount1);
        if (item1 != null) {
            L1ItemInstance tgItem = ItemTable.get().createItem(getItemId);
            if (pc.getInventory().checkAddItem(tgItem, amount) == 0) {
                pc.getInventory().removeItem(item1, itemCount1);
                if (tgItem.isStackable()) {
                    tgItem.setCount(amount);
                    createNewItem(pc, tgItem);
                }
                else {
                    for (int get = 0; (long)get < amount; ++get) {
                        L1ItemInstance tgItemX = ItemTable.get().createItem(getItemId);
                        tgItemX.setCount(1L);
                        createNewItem(pc, tgItemX);
                    }
                }
            }
        }
    }
    
    public static void createNewItem(L1PcInstance pc, L1ItemInstance item) {
        try {
            if (pc == null) {
                return;
            }
            else if (item == null) {
                return;
            }
            else {
                pc.getInventory().storeItem(item);
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void createNewItem(L1PcInstance pc, L1ItemInstance item, long count) {
        try {
            if (pc == null) {
                return;
            }
            else if (item == null) {
                return;
            }
            else {
                item.setCount(count);
                if (pc.getInventory().checkAddItem(item, count) == 0) {
                    pc.getInventory().storeItem(item);
                }
                else {
                    item.set_showId(pc.get_showId());
                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                }
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static boolean createNewItem(L1PcInstance pc, int item_id, long count) {
        try {
            if (pc == null) {
                return false;
            }
            else {
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    item.setCount(count);
                    if (pc.getInventory().checkAddItem(item, count) == 0) {
                        pc.getInventory().storeItem(item);
                    }
                    else {
                        item.set_showId(pc.get_showId());
                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                    }
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                    return true;
                }
                else {
                    CreateNewItem._log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                    return false;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    
    public static boolean createNewItemAi(L1PcInstance pc, int item_id, long count) {
        try {
            if (pc == null) {
                return false;
            }
            else {
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    item.setCount(count);
                    if (pc.getInventory().checkAddItem(item, count) == 0) {
                        pc.getInventory().storeItem(item);
                    }
                    else {
                        item.set_showId(pc.get_showId());
                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                    }
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                    return true;
                }
                else {
                    CreateNewItem._log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                    return false;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    
    public static void getQuestItem(L1Character atk, L1NpcInstance npc, int item_id, long count) {
        try {
            if (atk == null) {
                return;
            }
            else {
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    item.setCount(count);
                    if (atk.getInventory().checkAddItem(item, count) == 0) {
                        atk.getInventory().storeItem(item);
                    }
                    else {
                        item.set_showId(atk.get_showId());
                        World.get().getInventory(atk.getX(), atk.getY(), atk.getMapId()).storeItem(item);
                    }
                    if (atk instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)atk;
                        if (npc != null) {
                            pc.sendPackets(new S_ServerMessage(143, npc.getNameId(), item.getLogName()));
                            pc.sendPackets(new S_HelpMessage("\\fW" + npc.getNameId() + "給你" + item.getLogName()));
                        }
                        else {
                            pc.sendPackets(new S_HelpMessage("\\fW給你", item.getLogName()));
                        }
                    }
                }
                else {
                    CreateNewItem._log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void updateA(L1PcInstance pc, L1ItemInstance srcItem, L1ItemInstance newItem, int enchant, int down, int mode) {
        try {
            if (pc == null) {
                return;
            }
            else if (srcItem == null) {
                return;
            }
            else if (newItem == null) {
                return;
            }
            else {
                newItem.setCount(1L);
                if (srcItem.getEnchantLevel() > enchant) {
                    newItem.setEnchantLevel(srcItem.getEnchantLevel() - down);
                }
                else {
                    newItem.setEnchantLevel(srcItem.getEnchantLevel());
                }
                newItem.setAttrEnchantKind(srcItem.getAttrEnchantKind());
                newItem.setAttrEnchantLevel(srcItem.getAttrEnchantLevel());
                newItem.setIdentified(true);
                int srcObjid = srcItem.getId();
                L1Item srcItemX = srcItem.getItem();
                if (pc.getInventory().removeItem(srcItem) == 1L) {
                    pc.getInventory().storeItem(newItem);
                    pc.sendPackets(new S_ServerMessage(403, newItem.getLogName()));
                    CharShiftingReading.get().newShifting(pc, 0, null, srcObjid, srcItemX, newItem, mode);
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void updateB(L1PcInstance pc, L1ItemInstance srcItem, int newid) {
        try {
            if (pc == null) {
                return;
            }
            else if (srcItem == null) {
                return;
            }
            else {
                L1ItemInstance newItem = ItemTable.get().createItem(newid);
                if (newItem != null) {
                    if (pc.getInventory().removeItem(srcItem) == 1L) {
                        pc.getInventory().storeItem(newItem);
                    }
                }
                else {
                    CreateNewItem._log.error("給予物件失敗 原因: 指定編號物品不存在(" + newid + ")");
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void createNewItem_LV(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
        L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount((long)count);
        item.setEnchantLevel(EnchantLevel);
        item.setIdentified(true);
        if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
            pc.getInventory().storeItem(item);
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
        else {
            World.get().removeObject(item);
        }
    }
    
    public static boolean createNewItem(L1PcInstance pc, int item_id, int count, int en) {
        try {
            if (pc == null) {
                return false;
            }
            else {
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    if (item.isStackable()) {
                        item.setCount((long)count);
                    }
                    else {
                        item.setCount(1L);
                    }
                    item.setEnchantLevel(en);
                    if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                        pc.getInventory().storeItem(item);
                    }
                    else {
                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                    }
                    pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                    return true;
                }
                else {
                    CreateNewItem._log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                    return false;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    
    public static void createNewItem_NPC(L1PcInstance pc, String npcName, int item_id, int count) {
        L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount((long)count);
        if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
            pc.getInventory().storeItem(item);
        }
        else {
            World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
        }
        pc.sendPackets(new S_ServerMessage(143, npcName, item.getLogName()));
    }
    
    public static boolean createNewItemRandomGifts(L1PcInstance pc, int item_id, int count) {
        try {
            if (pc == null) {
                return false;
            }
            else {
                L1ItemInstance item = ItemTable.get().createItem(item_id);
                if (item != null) {
                    if (item.isStackable()) {
                        item.setCount((long)count);
                    }
                    else {
                        item.setCount(1L);
                    }
                    if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
                        pc.getInventory().storeItem(item);
                    }
                    else {
                        World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                    }
                    pc.sendPackets(new S_SystemMessage(String.format("恭喜你獲得系統線上抽獎大禮：", item.getLogName())));
                    return true;
                }
                else {
                    CreateNewItem._log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
                    return false;
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
}
