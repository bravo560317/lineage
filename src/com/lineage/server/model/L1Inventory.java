package com.lineage.server.model;

import java.util.Comparator;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import com.lineage.server.datatables.ItemTimeTable;
import java.util.Collections;
import java.util.ArrayList;
import com.lineage.server.datatables.lock.FurnitureSpawnReading;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.datatables.sql.LetterTable;
import java.util.Arrays;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import com.add.ItemTimeTableadd;
import com.add.L1ItemTimeadd;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.clientpackets.C_LoginToServer;
import com.lineage.data.event.ProtectorSet;
import java.util.Random;
import com.lineage.server.templates.L1Item;
import com.lineage.server.world.World;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.ItemTable;
import com.lineage.config.ConfigRate;
import com.lineage.config.ConfigAlt;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_EquipmentWindow;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.LogFactory;
import java.text.SimpleDateFormat;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.List;
import org.apache.commons.logging.Log;

public class L1Inventory extends L1Object
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    protected List<L1ItemInstance> _items;
    public static final int MAX_WEIGHT = 1500;
    public static final int OK = 0;
    public static final int SIZE_OVER = 1;
    public static final int WEIGHT_OVER = 2;
    public static final int AMOUNT_OVER = 3;
    public static final int WAREHOUSE_TYPE_PERSONAL = 0;
    public static final int WAREHOUSE_TYPE_CLAN = 1;
    public int[] slot_ring;
    public int[] slot_earring;
    private static final SimpleDateFormat sdf;
    
    static {
        _log = LogFactory.getLog(L1Inventory.class);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    public L1Inventory() {
        this._items = new CopyOnWriteArrayList();
        this.slot_ring = new int[4];
        this.slot_earring = new int[2];
    }
    
    public void toSlotPacket(L1PcInstance pc, L1ItemInstance item) {
        int select_idx = -1;
        int idx = 0;
        if (item.getItem().getType2() == 2) {
            switch (item.getItem().getType()) {
                case 1:
                case 2:
                case 3:
                case 4: {
                    idx = item.getItem().getType();
                    break;
                }
                case 5: {
                    idx = 6;
                    break;
                }
                case 6: {
                    idx = 5;
                    break;
                }
                case 7:
                case 13: {
                    idx = 7;
                    break;
                }
                case 8: {
                    idx = 10;
                    break;
                }
                case 9: {
                    for (int i = 0; i < this.slot_ring.length; ++i) {
                        if (this.slot_ring[i] == item.getId()) {
                            select_idx = i;
                        }
                    }
                    if (item.isEquipped() && select_idx == -1) {
                        int i = 0;
                        while (i < this.slot_ring.length) {
                            if (this.slot_ring[i] == 0) {
                                this.slot_ring[i] = item.getId();
                                idx = 18 + i;
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    if (!item.isEquipped() && select_idx != -1) {
                        this.slot_ring[select_idx] = 0;
                        idx = 18 + select_idx;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 10: {
                    idx = 11;
                    break;
                }
                case 12: {
                    for (int i = 0; i < this.slot_earring.length; ++i) {
                        if (this.slot_earring[i] == item.getId()) {
                            select_idx = i;
                        }
                    }
                    if (item.isEquipped() && select_idx == -1) {
                        int i = 0;
                        while (i < this.slot_earring.length) {
                            if (this.slot_earring[i] == 0) {
                                this.slot_earring[i] = item.getId();
                                idx = 12 + i;
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    if (!item.isEquipped() && select_idx != -1) {
                        this.slot_earring[select_idx] = 0;
                        idx = 12 + select_idx;
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 14: {
                    idx = 22;
                    break;
                }
                case 15: {
                    idx = 23;
                    break;
                }
                case 16: {
                    idx = 24;
                    break;
                }
                case 17: {
                    idx = 14;
                    break;
                }
                case 18: {
                    idx = 25;
                    break;
                }
                case 19: {
                    idx = 26;
                    break;
                }
                case 20: {
                    idx = 27;
                    break;
                }
            }
        }
        else if (item.getItem().getType2() == 1) {
            idx = 8;
        }
        if (idx != 0) {
            pc.sendPackets(new S_EquipmentWindow(pc, item.getId(), idx, item.isEquipped()));
        }
    }
    
    public int getSize() {
        if (this._items.isEmpty()) {
            return 0;
        }
        else {
            return this._items.size();
        }
    }
    
    public List<L1ItemInstance> getItems() {
        return this._items;
    }
    
    public int getWeight() {
        int weight = 0;
        for (L1ItemInstance item : this._items) {
            weight += item.getWeight();
        }
        return weight;
    }
    
    public int checkAddItem(L1ItemInstance item, long count) {
        if (item == null) {
            return -1;
        }
        else if (item.getCount() <= 0L || count <= 0L) {
            return -1;
        }
        else if (this.getSize() > ConfigAlt.MAX_NPC_ITEM || (this.getSize() == ConfigAlt.MAX_NPC_ITEM && (!item.isStackable() || !this.checkItem(item.getItem().getItemId())))) {
            return 1;
        }
        else {
            long weight = (long)this.getWeight() + (long)item.getItem().getWeight() * count / 1000L + 1L;
            if (weight < 0L || (long)item.getItem().getWeight() * count / 1000L < 0L) {
                return 2;
            }
            else if ((double)weight > 1500.0 * ConfigRate.RATE_WEIGHT_LIMIT_PET) {
                return 2;
            }
            else {
                L1ItemInstance itemExist = this.findItemId(item.getItemId());
                if (itemExist != null && itemExist.getCount() + count > Long.MAX_VALUE) {
                    return 3;
                }
                else {
                    return 0;
                }
            }
        }
    }
    
    public int checkAddItemToWarehouse(L1ItemInstance item, long count, int type) {
        if (item == null) {
            return -1;
        }
        else if (item.getCount() <= 0L || count <= 0L) {
            return -1;
        }
        else {
            int maxSize = 100;
            if (type == 0) {
                maxSize = ConfigAlt.MAX_PERSONAL_WAREHOUSE_ITEM;
            }
            else if (type == 1) {
                maxSize = ConfigAlt.MAX_CLAN_WAREHOUSE_ITEM;
            }
            if (this.getSize() > maxSize || (this.getSize() == maxSize && (!item.isStackable() || !this.checkItem(item.getItem().getItemId())))) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
    
    public synchronized L1ItemInstance storeItem(int itemid, long count) {
        try {
            if (count <= 0L) {
                return null;
            }
            else {
                L1Item temp = ItemTable.get().getTemplate(itemid);
                if (temp == null) {
                    return null;
                }
                else if (temp.isStackable()) {
                    L1ItemInstance item = new L1ItemInstance(temp, count);
                    if (this.findItemId(itemid) == null) {
                        item.setId(IdFactory.get().nextId());
                        World.get().storeObject(item);
                    }
                    return this.storeItem(item);
                }
                else {
                    L1ItemInstance result = null;
                    for (int i = 0; (long)i < count; ++i) {
                        L1ItemInstance item2 = new L1ItemInstance(temp, 1L);
                        item2.setId(IdFactory.get().nextId());
                        World.get().storeObject(item2);
                        this.storeItem(item2);
                        result = item2;
                    }
                    return result;
                }
            }
        }
        catch (Exception e) {
            L1Inventory._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public synchronized L1ItemInstance storeItem(L1ItemInstance item) {
        try {
            if (item == null) {
                return null;
            }
            else if (item.getCount() <= 0L) {
                return null;
            }
            else {
                int itemId = item.getItem().getItemId();
                if (item.isStackable()) {
                    if (itemId == 40309) {
                        L1ItemInstance[] items = this.findItemsId(itemId);
                        L1ItemInstance[] array;
                        int length = (array = items).length;
                        int i = 0;
                        while (i < length) {
                            L1ItemInstance tgitem = array[i];
                            String racegamNo = tgitem.getraceGamNo();
                            if (item.getraceGamNo().equals(racegamNo)) {
                                tgitem.setCount(tgitem.getCount() + item.getCount());
                                this.updateItem(tgitem);
                                return tgitem;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else {
                        L1ItemInstance findItem = this.findItemId(item.getItem().getItemId());
                        if (findItem != null) {
                            if (findItem.getId() == item.getId()) {
                                if (this instanceof L1PcInventory) {
                                    L1PcInventory pc_inv = (L1PcInventory)this;
                                    L1PcInstance pc = pc_inv.getOwner();
                                    if (pc != null) {
                                        L1Inventory._log.info("增加物品時發生重複ID異常: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + ", Objid:" + item.getId() + ")");
                                    }
                                }
                                return null;
                            }
                            else {
                                findItem.setCount(findItem.getCount() + item.getCount());
                                this.updateItem(findItem);
                                return findItem;
                            }
                        }
                    }
                    if (itemId == 40312) {
                        L1ItemInstance findItem = this.findKeyId(item.getKeyId());
                        if (findItem != null) {
                            findItem.setCount(findItem.getCount() + item.getCount());
                            this.updateItem(findItem);
                            return findItem;
                        }
                    }
                    if (itemId == 82503) {
                        L1ItemInstance findItem = this.findKeyId(item.getKeyId());
                        if (findItem != null) {
                            findItem.setCount(findItem.getCount() + item.getCount());
                            this.updateItem(findItem);
                            return findItem;
                        }
                    }
                    if (itemId == 82504) {
                        L1ItemInstance findItem = this.findKeyId(item.getKeyId());
                        if (findItem != null) {
                            findItem.setCount(findItem.getCount() + item.getCount());
                            this.updateItem(findItem);
                            return findItem;
                        }
                    }
                }
                item.setX(this.getX());
                item.setY(this.getY());
                item.setMap(this.getMapId());
                int maxchargeCount = item.getItem().getMaxChargeCount();
                switch (itemId) {
                    case 20383: {
                        maxchargeCount = 50;
                        break;
                    }
                    case 40006:
                    case 40007:
                    case 40008:
                    case 40009:
                    case 140006:
                    case 140008: {
                        Random random1 = new Random();
                        maxchargeCount -= random1.nextInt(5);
                        break;
                    }
                }
                if (item.getChargeCount() > 0) {
                    item.setChargeCount(item.getChargeCount());
                }
                else {
                    item.setChargeCount(maxchargeCount);
                }
                if (item.getRemainingTime() > 0) {
                    item.setRemainingTime(item.getRemainingTime());
                }
                item.setBless(item.getItem().getBless());
                if (itemId == ProtectorSet.ITEM_ID && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc.isProtector()) {
                        pc.setProtector(true);
                        C_LoginToServer.checkforProtector(pc);
                        World.get().broadcastPacketToAll(new S_ServerMessage(2924));
                    }
                }
                if (itemId == 56148 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc.isEffectGS() && !pc.isEffectDADIS()) {
                        pc.setGS(true);
                    }
                }
                if (itemId == 56147 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc.isEffectGS() && !pc.isEffectDADIS()) {
                        pc.setDADIS(true);
                    }
                }
                if (itemId == 44216 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc._isCraftsmanHeirloom() && !pc._isMarsSoul()) {
                        pc.setCraftsmanHeirloom(true);
                    }
                }
                if (itemId == 44217 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc._isMarsSoul() && !pc._isCraftsmanHeirloom()) {
                        pc.setMarsSoul(true);
                    }
                }
                if (itemId == 40312 && !InnKeyTable.checkey(item)) {
                    item.setIdentified(true);
                    InnKeyTable.StoreKey(item);
                }
                if (itemId == 82503 && !InnKeyTable.checkey(item)) {
                    item.setIdentified(true);
                    InnKeyTable.StoreKey(item);
                }
                if (itemId == 82504 && !InnKeyTable.checkey(item)) {
                    item.setIdentified(true);
                    InnKeyTable.StoreKey(item);
                }
                if (this instanceof L1PcInventory) {
                    this.set_time_item(item);
                    this.set_time_itemadd(item);
                }
                this._items.add(item);
                this.insertItem(item);
                return item;
            }
        }
        catch (Exception e) {
            L1Inventory._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    private void set_time_itemadd(L1ItemInstance item) {
        L1ItemTimeadd itemTime = ItemTimeTableadd.TIME.get((Integer)item.getItemId());
        if (itemTime != null && itemTime != null) {
            item.set_time(itemTime.get_remain_time());
            CharItemsTimeReading.get().addTime(item.getId(), itemTime.get_remain_time());
            if (this instanceof L1PcInventory) {
                L1PcInventory pc_inv = (L1PcInventory)this;
                L1PcInstance pc = pc_inv.getOwner();
                pc.sendPackets(new S_ItemName(item));
            }
        }
    }
    
    public synchronized L1ItemInstance storeItem1(L1ItemInstance item) {
        try {
            if (item == null) {
                return null;
            }
            else if (item.getCount() <= 0L) {
                return null;
            }
            else {
                int itemId = item.getItem().getItemId();
                if (item.isStackable()) {
                    if (itemId == 40309) {
                        L1ItemInstance[] items = this.findItemsId(itemId);
                        L1ItemInstance[] array;
                        int length = (array = items).length;
                        int i = 0;
                        while (i < length) {
                            L1ItemInstance tgitem = array[i];
                            String racegamNo = tgitem.getraceGamNo();
                            if (item.getraceGamNo().equals(racegamNo)) {
                                tgitem.setCount(tgitem.getCount() + item.getCount());
                                this.updateItem(tgitem);
                                return tgitem;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else {
                        L1ItemInstance findItem = this.findItemId(item.getItem().getItemId());
                        if (findItem != null) {
                            if (findItem.getId() == item.getId()) {
                                if (this instanceof L1PcInventory) {
                                    L1PcInventory pc_inv = (L1PcInventory)this;
                                    L1PcInstance pc = pc_inv.getOwner();
                                    if (pc != null) {
                                        L1Inventory._log.info("增加物品時發生重複ID異常: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + ", Objid:" + item.getId() + ")");
                                    }
                                }
                                return null;
                            }
                            else {
                                findItem.setCount(findItem.getCount() + item.getCount());
                                this.updateItem(findItem);
                                return findItem;
                            }
                        }
                    }
                }
                item.setX(this.getX());
                item.setY(this.getY());
                item.setMap(this.getMapId());
                int maxchargeCount = item.getItem().getMaxChargeCount();
                switch (itemId) {
                    case 20383: {
                        maxchargeCount = 50;
                        break;
                    }
                    case 40006:
                    case 40007:
                    case 40008:
                    case 40009:
                    case 140006:
                    case 140008: {
                        Random random1 = new Random();
                        maxchargeCount -= random1.nextInt(5);
                        break;
                    }
                }
                if (item.getChargeCount() > 0) {
                    item.setChargeCount(item.getChargeCount());
                }
                else {
                    item.setChargeCount(maxchargeCount);
                }
                if (item.getRemainingTime() > 0) {
                    item.setRemainingTime(item.getRemainingTime());
                }
                if (itemId == ProtectorSet.ITEM_ID && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc.isProtector()) {
                        pc.setProtector(true);
                        C_LoginToServer.checkforProtector(pc);
                        World.get().broadcastPacketToAll(new S_ServerMessage(2924));
                    }
                }
                if (itemId == 56148 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc.isEffectGS() && !pc.isEffectDADIS()) {
                        pc.setGS(true);
                    }
                }
                if (itemId == 56147 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc.isEffectGS() && !pc.isEffectDADIS()) {
                        pc.setDADIS(true);
                    }
                }
                if (itemId == 44216 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc._isCraftsmanHeirloom() && !pc._isMarsSoul()) {
                        pc.setCraftsmanHeirloom(true);
                    }
                }
                if (itemId == 44217 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv = (L1PcInventory)this;
                    L1PcInstance pc = pc_inv.getOwner();
                    if (pc != null && !pc._isMarsSoul() && !pc._isCraftsmanHeirloom()) {
                        pc.setMarsSoul(true);
                    }
                }
                if (itemId == 40312 && !InnKeyTable.checkey(item)) {
                    item.setIdentified(true);
                    InnKeyTable.StoreKey(item);
                }
                if (itemId == 82503 && !InnKeyTable.checkey(item)) {
                    item.setIdentified(true);
                    InnKeyTable.StoreKey(item);
                }
                if (itemId == 82504 && !InnKeyTable.checkey(item)) {
                    item.setIdentified(true);
                    InnKeyTable.StoreKey(item);
                }
                if (this instanceof L1PcInventory) {
                    this.set_time_item(item);
                    this.set_time_itemadd(item);
                }
                this._items.add(item);
                this.insertItem(item);
                return item;
            }
        }
        catch (Exception e) {
            L1Inventory._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public synchronized L1ItemInstance storeTradeItem(L1ItemInstance item) {
        try {
            if (item == null) {
                return null;
            }
            else if (item.getCount() <= 0L) {
                return null;
            }
            else {
                int itemId = item.getItem().getItemId();
                if (item.isStackable()) {
                    if (itemId == 40309) {
                        L1ItemInstance[] items = this.findItemsId(itemId);
                        L1ItemInstance[] array;
                        int length = (array = items).length;
                        int i = 0;
                        while (i < length) {
                            L1ItemInstance tgitem = array[i];
                            String racegamNo = tgitem.getraceGamNo();
                            if (item.getraceGamNo().equals(racegamNo)) {
                                tgitem.setCount(tgitem.getCount() + item.getCount());
                                this.updateItem(tgitem);
                                return tgitem;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    else {
                        L1ItemInstance findItem = this.findItemId(item.getItem().getItemId());
                        if (findItem != null) {
                            if (findItem.getId() == item.getId()) {
                                if (this instanceof L1PcInventory) {
                                    L1PcInventory pc_inv = (L1PcInventory)this;
                                    L1PcInstance pc = pc_inv.getOwner();
                                    if (pc != null) {
                                        L1Inventory._log.info("增加物品時發生重複ID異常[刪除3]: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + ", Objid:" + item.getId() + ")");
                                        this.deleteItem(item);
                                        result("增加物品時發生重複ID異常[刪除3]: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + "(" + item.getCount() + ")" + ", Objid:" + item.getId() + ")", 13);
                                    }
                                }
                                return null;
                            }
                            else {
                                findItem.setCount(findItem.getCount() + item.getCount());
                                this.updateItem(findItem);
                                return findItem;
                            }
                        }
                    }
                    if (itemId == 40312) {
                        L1ItemInstance findItem = this.findKeyId(item.getKeyId());
                        if (findItem != null) {
                            findItem.setCount(findItem.getCount() + item.getCount());
                            this.updateItem(findItem);
                            return findItem;
                        }
                    }
                    if (itemId == 82503) {
                        L1ItemInstance findItem = this.findKeyId(item.getKeyId());
                        if (findItem != null) {
                            findItem.setCount(findItem.getCount() + item.getCount());
                            this.updateItem(findItem);
                            return findItem;
                        }
                    }
                    if (itemId == 82504) {
                        L1ItemInstance findItem = this.findKeyId(item.getKeyId());
                        if (findItem != null) {
                            findItem.setCount(findItem.getCount() + item.getCount());
                            this.updateItem(findItem);
                            return findItem;
                        }
                    }
                    boolean SKIP = false;
                    switch (itemId) {
                        case 40309:
                        case 40312:
                        case 82503:
                        case 82504: {
                            SKIP = true;
                            break;
                        }
                    }
                }
                item.setX(this.getX());
                item.setY(this.getY());
                item.setMap(this.getMapId());
                if (itemId == ProtectorSet.ITEM_ID && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv2 = (L1PcInventory)this;
                    L1PcInstance pc2 = pc_inv2.getOwner();
                    if (pc2 != null && !pc2.isProtector()) {
                        pc2.setProtector(true);
                        World.get().broadcastPacketToAll(new S_ServerMessage(2924));
                    }
                }
                if (itemId == 56148 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv2 = (L1PcInventory)this;
                    L1PcInstance pc2 = pc_inv2.getOwner();
                    if (pc2 != null && !pc2.isEffectGS() && !pc2.isEffectDADIS()) {
                        pc2.setGS(true);
                    }
                }
                if (itemId == 56147 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv2 = (L1PcInventory)this;
                    L1PcInstance pc2 = pc_inv2.getOwner();
                    if (pc2 != null && !pc2.isEffectGS() && !pc2.isEffectDADIS()) {
                        pc2.setDADIS(true);
                    }
                }
                if (itemId == 56146 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv2 = (L1PcInventory)this;
                    L1PcInstance pc2 = pc_inv2.getOwner();
                    if (pc2 != null && !pc2._isCraftsmanHeirloom() && !pc2._isMarsSoul()) {
                        pc2.setCraftsmanHeirloom(true);
                    }
                }
                if (itemId == 56145 && this instanceof L1PcInventory) {
                    L1PcInventory pc_inv2 = (L1PcInventory)this;
                    L1PcInstance pc2 = pc_inv2.getOwner();
                    if (pc2 != null && !pc2._isMarsSoul() && !pc2._isCraftsmanHeirloom()) {
                        pc2.setMarsSoul(true);
                    }
                }
                if (itemId == 40312 && !InnKeyTable.checkey(item)) {
                    InnKeyTable.StoreKey(item);
                }
                if (itemId == 82503 && !InnKeyTable.checkey(item)) {
                    InnKeyTable.StoreKey(item);
                }
                if (itemId == 82504 && !InnKeyTable.checkey(item)) {
                    InnKeyTable.StoreKey(item);
                }
                if (this instanceof L1PcInventory) {
                    this.set_time_item(item);
                    this.set_time_itemadd(item);
                }
                this._items.add(item);
                this.insertItem(item);
                return item;
            }
        }
        catch (Exception e) {
            L1Inventory._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public boolean consumeItem(int itemid, long count) {
        if (count <= 0L) {
            return false;
        }
        else {
            if (ItemTable.get().getTemplate(itemid).isStackable()) {
                L1ItemInstance item = this.findItemId(itemid);
                if (item != null && item.getCount() >= count) {
                    this.removeItem(item, count);
                    return true;
                }
            }
            else {
                L1ItemInstance[] itemList = this.findItemsId(itemid);
                if ((long)itemList.length == count) {
                    for (int i = 0; (long)i < count; ++i) {
                        this.removeItem(itemList[i], 1L);
                    }
                    return true;
                }
                else if ((long)itemList.length > count) {
                    L1Inventory.DataComparator dc = new L1Inventory.DataComparator();
                    Arrays.sort(itemList, dc);
                    for (int j = 0; (long)j < count; ++j) {
                        this.removeItem(itemList[j], 1L);
                    }
                    return true;
                }
            }
            return false;
        }
    }
    
    public L1ItemInstance shiftingItem(int objectId, long count) {
        L1ItemInstance item = this.getItem(objectId);
        if (item == null) {
            return null;
        }
        else if (item.getCount() <= 0L || count <= 0L) {
            return null;
        }
        else if (item.getCount() < count) {
            return null;
        }
        else if (item.getCount() == count && !item.isEquipped()) {
            this.deleteItem(item);
            return item;
        }
        else {
            return null;
        }
    }
    
    public long removeItem(int objectId, long count) {
        L1ItemInstance item = this.getItem(objectId);
        return this.removeItem(item, count);
    }
    
    public void removeItem(int itemid) {
        for (L1ItemInstance item : this._items) {
            if (item.getItemId() == itemid) {
                this.removeItem(item);
            }
        }
    }
    
    public long removeItem(L1ItemInstance item) {
        return this.removeItem(item, item.getCount());
    }
    
    public long removeItem(L1ItemInstance item, long count) {
        if (item == null) {
            return 0L;
        }
        else if (item.getCount() <= 0L || count <= 0L) {
            return 0L;
        }
        else {
            if (item.getCount() < count) {
                count = item.getCount();
            }
            if (item.getCount() == count) {
                int itemId = item.getItem().getItemId();
                if (itemId >= 49016 && itemId <= 49025) {
                    LetterTable lettertable = new LetterTable();
                    lettertable.deleteLetter(item.getId());
                }
                else if (itemId >= 41383 && itemId <= 41400) {
                    for (L1Object l1object : World.get().getObject()) {
                        if (l1object instanceof L1FurnitureInstance) {
                            L1FurnitureInstance furniture = (L1FurnitureInstance)l1object;
                            if (furniture.getItemObjId() == item.getId()) {
                                FurnitureSpawnReading.get().deleteFurniture(furniture);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                this.onRemoveItem(item);
                this.deleteItem(item);
                World.get().removeObject(item);
            }
            else {
                item.setCount(item.getCount() - count);
                this.updateItem(item);
            }
            return count;
        }
    }
    
    public void deleteItem(L1ItemInstance item) {
        int itemid = item.getItemId();
        if (itemid == 40312) {
            InnKeyTable.DeleteKey(item);
        }
        if (itemid == 82503) {
            InnKeyTable.DeleteKey(item);
        }
        if (itemid == 82504) {
            InnKeyTable.DeleteKey(item);
        }
        this._items.remove(item);
    }
    
    public synchronized L1ItemInstance tradeItem(int objectId, long count, L1Inventory inventory) {
        L1ItemInstance item = this.getItem(objectId);
        return this.tradeItem(item, count, inventory);
    }
    
    public synchronized L1ItemInstance tradeItem1(int objectId, long count, L1Inventory inventory) {
        L1ItemInstance item = this.getItem1(objectId);
        return this.tradeItem(item, count, inventory);
    }
    
    public synchronized L1ItemInstance tradeItem(L1ItemInstance item, int count, int showId, L1GroundInventory inventory) {
        if (item == null) {
            return null;
        }
        else if (item.getCount() <= 0L || count <= 0) {
            return null;
        }
        else if (item.isEquipped()) {
            return null;
        }
        else if (item.getCount() < (long)count) {
            return null;
        }
        else if (this.getItem(item.getId()) == null || inventory.getItem(item.getId()) != null) {
            if (this instanceof L1PcInventory) {
                L1PcInventory pc_inv = (L1PcInventory)this;
                L1PcInstance pc = pc_inv.getOwner();
                if (pc != null) {
                    L1Inventory._log.info("轉移物品時發生重複ID異常[To地板]: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + ", Objid:" + item.getId() + ")");
                }
            }
            return null;
        }
        else {
            L1ItemInstance carryItem;
            if (item.getCount() == (long)count) {
                this.onRemoveItem(item);
                this.deleteItem(item);
                carryItem = item;
                carryItem.set_showId(showId);
            }
            else {
                item.setCount(item.getCount() - (long)count);
                this.updateItem(item);
                carryItem = ItemTable.get().createItem(item.getItem().getItemId());
                carryItem.set_showId(showId);
                carryItem.setCount((long)count);
                carryItem.setEnchantLevel(item.getEnchantLevel());
                carryItem.setIdentified(item.isIdentified());
                carryItem.set_durability(item.get_durability());
                carryItem.setChargeCount(item.getChargeCount());
                carryItem.setRemainingTime(item.getRemainingTime());
                carryItem.setLastUsed(item.getLastUsed());
                carryItem.setBless(item.getBless());
                if (carryItem.getItem().getItemId() == 40312 || carryItem.getItem().getItemId() == 82503 || carryItem.getItem().getItemId() == 82504) {
                    carryItem.setInnNpcId(item.getInnNpcId());
                    carryItem.setKeyId(item.getKeyId());
                    carryItem.setHall(item.checkRoomOrHall());
                    carryItem.setDueTime(item.getDueTime());
                }
            }
            return inventory.storeTradeItem(carryItem);
        }
    }
    
    public synchronized L1ItemInstance tradeItem(L1ItemInstance item, long count, L1Inventory inventory) {
        if (item == null) {
            return null;
        }
        else if (item.getCount() <= 0L || count <= 0L) {
            return null;
        }
        else if (item.isEquipped()) {
            return null;
        }
        else if (item.getCount() < count) {
            return null;
        }
        else if (this.getItem(item.getId()) == null || inventory.getItem(item.getId()) != null) {
            if (this instanceof L1PcInventory) {
                L1PcInventory pc_inv = (L1PcInventory)this;
                L1PcInstance pc = pc_inv.getOwner();
                if (pc != null) {
                    L1Inventory._log.info("轉移物品時發生重複ID異常[刪除5]: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + ", Objid:" + item.getId() + ")");
                    this.deleteItem(item);
                    result("增加物品時發生重複ID異常[刪除1]: (人物:" + pc.getName() + ", 道具:" + item.getItem().getName() + "(" + item.getCount() + ")" + ", Objid:" + item.getId() + ")", 13);
                }
            }
            return null;
        }
        else {
            L1ItemInstance carryItem;
            if (item.getCount() == count) {
                this.onRemoveItem(item);
                this.deleteItem(item);
                carryItem = item;
            }
            else {
                item.setCount(item.getCount() - count);
                this.updateItem(item);
                carryItem = ItemTable.get().createItem(item.getItem().getItemId());
                carryItem.setCount(count);
                carryItem.setEnchantLevel(item.getEnchantLevel());
                carryItem.setIdentified(item.isIdentified());
                carryItem.set_durability(item.get_durability());
                carryItem.setChargeCount(item.getChargeCount());
                carryItem.setRemainingTime(item.getRemainingTime());
                carryItem.setLastUsed(item.getLastUsed());
                carryItem.setBless(item.getBless());
                if (carryItem.getItem().getItemId() == 40312 || carryItem.getItem().getItemId() == 82503 || carryItem.getItem().getItemId() == 82504) {
                    carryItem.setInnNpcId(item.getInnNpcId());
                    carryItem.setKeyId(item.getKeyId());
                    carryItem.setHall(item.checkRoomOrHall());
                    carryItem.setDueTime(item.getDueTime());
                }
            }
            return inventory.storeTradeItem(carryItem);
        }
    }
    
    public L1ItemInstance receiveDamage(int objectId) {
        L1ItemInstance item = this.getItem(objectId);
        return this.receiveDamage(item);
    }
    
    public L1ItemInstance receiveDamage(L1ItemInstance item) {
        return this.receiveDamage(item, 1);
    }
    
    public L1ItemInstance receiveDamage(L1ItemInstance item, int count) {
        if (item == null) {
            return null;
        }
        else {
            int itemType = item.getItem().getType2();
            int currentDurability = item.get_durability();
            if ((currentDurability == 0 && itemType == 0) || currentDurability < 0) {
                item.set_durability(0);
                return null;
            }
            else {
                if (itemType == 0) {
                    int minDurability = (item.getEnchantLevel() + 5) * -1;
                    int durability = currentDurability - count;
                    if (durability < minDurability) {
                        durability = minDurability;
                    }
                    if (currentDurability > durability) {
                        item.set_durability(durability);
                    }
                }
                else {
                    int maxDurability = item.getEnchantLevel() + 5;
                    int durability = currentDurability + count;
                    if (durability > maxDurability) {
                        durability = maxDurability;
                    }
                    if (currentDurability < durability) {
                        item.set_durability(durability);
                    }
                }
                this.updateItem(item, 1);
                return item;
            }
        }
    }
    
    public L1ItemInstance recoveryDamage(L1ItemInstance item) {
        if (item == null) {
            return null;
        }
        else {
            int itemType = item.getItem().getType2();
            int durability = item.get_durability();
            if ((durability == 0 && itemType != 0) || durability < 0) {
                item.set_durability(0);
                return null;
            }
            else {
                if (itemType == 0) {
                    item.set_durability(durability + 1);
                }
                else {
                    item.set_durability(durability - 1);
                }
                this.updateItem(item, 1);
                return item;
            }
        }
    }
    
    public L1ItemInstance findItemIdNoEq(int itemId) {
        for (L1ItemInstance item : this._items) {
            if (item.getItem().getItemId() == itemId && !item.isEquipped()) {
                return item;
            }
        }
        return null;
    }
    
    public L1ItemInstance findItemId(int itemId) {
        for (L1ItemInstance item : this._items) {
            if (item.getItem().getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }
    
    public L1ItemInstance findItemId(String nameid) {
        for (L1ItemInstance item : this._items) {
            if (item.getName().equals(nameid)) {
                return item;
            }
        }
        return null;
    }
    
    public L1ItemInstance findKeyId(int keyid) {
        for (L1ItemInstance item : this._items) {
            if (item.getKeyId() == keyid) {
                return item;
            }
        }
        return null;
    }
    
    public L1ItemInstance[] findItemsId(int itemId) {
        ArrayList<L1ItemInstance> itemList = new ArrayList();
        for (L1ItemInstance item : this._items) {
            if (item.getItemId() == itemId && item.get_time() == null) {
                itemList.add(item);
            }
        }
        return (L1ItemInstance[])itemList.toArray(new L1ItemInstance[0]);
    }
    
    public L1ItemInstance[] findItemsId2(int itemId) {
        ArrayList<L1ItemInstance> itemList = new ArrayList();
        for (L1ItemInstance item : this._items) {
            if (item.getItemId() == itemId) {
                itemList.add(item);
            }
        }
        return (L1ItemInstance[])itemList.toArray(new L1ItemInstance[0]);
    }
    
    public L1ItemInstance[] findItemsId1(int itemId) {
        ArrayList<L1ItemInstance> itemList = new ArrayList();
        for (L1ItemInstance item : this._items) {
            if (item.getItemId() == itemId && !item.isEquipped() && item.get_time() == null && !item.isEquipped()) {
                itemList.add(item);
            }
        }
        return (L1ItemInstance[])itemList.toArray(new L1ItemInstance[0]);
    }
    
    public L1ItemInstance[] findItemsIdNotEquipped(int itemId) {
        ArrayList<L1ItemInstance> itemList = new ArrayList();
        for (L1ItemInstance item : this._items) {
            if (item.getItemId() == itemId && !item.isEquipped()) {
                itemList.add(item);
            }
        }
        return (L1ItemInstance[])itemList.toArray(new L1ItemInstance[0]);
    }
    
    public L1ItemInstance[] findItemsIdNoEqWithEnchant(int itemId, int enchant) {
        ArrayList<L1ItemInstance> itemList = new ArrayList();
        for (L1ItemInstance item : this._items) {
            if (item.getItemId() == itemId && item.getEnchantLevel() == enchant && !item.isEquipped()) {
                itemList.add(item);
            }
        }
        return (L1ItemInstance[])itemList.toArray(new L1ItemInstance[0]);
    }
    
    public L1ItemInstance[] findItemsIdNotEquipped(String nameid) {
        ArrayList<L1ItemInstance> itemList = new ArrayList();
        for (L1ItemInstance item : this._items) {
            if (item.getName().equals(nameid) && !item.isEquipped()) {
                itemList.add(item);
            }
        }
        return (L1ItemInstance[])itemList.toArray(new L1ItemInstance[0]);
    }
    
    public L1ItemInstance getItem(int objectId) {
        for (Object itemObject : this._items) {
            L1ItemInstance item = (L1ItemInstance)itemObject;
            if (item.getId() == objectId) {
                return item;
            }
        }
        return null;
    }
    
    public L1ItemInstance getItem1(int objectId) {
        for (Object itemObject : this._items) {
            L1ItemInstance item = (L1ItemInstance)itemObject;
            if (item.getItemId() == objectId) {
                return item;
            }
        }
        return null;
    }
    
    public L1ItemInstance getItem_doll(int objectId) {
        for (Object itemObject : this._items) {
            L1ItemInstance item = (L1ItemInstance)itemObject;
            if (item.getItemId() == objectId) {
                return item;
            }
        }
        return null;
    }
    
    public boolean checkItem(int itemid) {
        return this.checkItem(itemid, 1L);
    }
    
    public boolean checkItem(int itemId, long count) {
        if (count <= 0L) {
            return true;
        }
        else {
            if (ItemTable.get().getTemplate(itemId).isStackable()) {
                L1ItemInstance item = this.findItemId(itemId);
                if (item != null && item.getCount() >= count) {
                    return true;
                }
            }
            else {
                Object[] itemList = this.findItemsId(itemId);
                if ((long)itemList.length >= count) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public boolean checkItem(L1ItemInstance item, long count) {
        return count <= 0L || item.getCount() >= count;
    }
    
    public L1ItemInstance checkItemX(int itemid, long count) {
        if (count <= 0L) {
            return null;
        }
        else {
            if (ItemTable.get().getTemplate(itemid) != null) {
                L1ItemInstance item = this.findItemIdNoEq(itemid);
                if (item != null && item.getCount() >= count) {
                    return item;
                }
            }
            return null;
        }
    }
    
    public boolean checkEnchantItem(int itemid, int enchant, long count) {
        if (ItemTable.get().getTemplate(itemid).isStackable()) {
            L1ItemInstance item = this.findItemIdNoEq(itemid);
            if (item != null && item.getEnchantLevel() == enchant && item.getCount() >= count) {
                return true;
            }
        }
        else {
            int num = 0;
            for (L1ItemInstance item2 : this._items) {
                if (item2.isEquipped()) {
                    continue;
                }
                else if (item2.getItemId() == itemid && item2.getEnchantLevel() == enchant && (long)(++num) == count) {
                    return true;
                }
                else {
                    continue;
                }
            }
        }
        return false;
    }
    
    public boolean consumeEnchantItem(int itemid, int enchant, long count) {
        if (ItemTable.get().getTemplate(itemid).isStackable()) {
            L1ItemInstance item = this.findItemIdNoEq(itemid);
            if (item != null && item.getEnchantLevel() == enchant && item.getCount() >= count) {
                this.removeItem(item, count);
                return true;
            }
        }
        else {
            L1ItemInstance[] itemList = this.findItemsIdNoEqWithEnchant(itemid, enchant);
            if ((long)itemList.length == count) {
                for (int i = 0; (long)i < count; ++i) {
                    this.removeItem(itemList[i], 1L);
                }
                return true;
            }
            else if ((long)itemList.length > count) {
                L1Inventory.DataComparator dc = new L1Inventory.DataComparator();
                Arrays.sort(itemList, dc);
                for (int j = 0; (long)j < count; ++j) {
                    this.removeItem(itemList[j], 1L);
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean checkItemNotEquipped(String nameid, long count) {
        return count == 0L || count <= this.countItems(nameid);
    }
    
    public boolean checkItemNotEquipped(int itemid, long count) {
        return count == 0L || count <= this.countItems(itemid);
    }
    
    public boolean checkItem(int[] ids) {
        int len = ids.length;
        int[] counts = new int[len];
        for (int i = 0; i < len; ++i) {
            counts[i] = 1;
        }
        return this.checkItem(ids, counts);
    }
    
    public boolean checkItem(int[] ids, int[] counts) {
        int i = 0;
        while (i < ids.length) {
            if (!this.checkItem(ids[i], (long)counts[i])) {
                return false;
            }
            else {
                ++i;
            }
        }
        return true;
    }
    
    public long countItems(int itemId) {
        if (ItemTable.get().getTemplate(itemId).isStackable()) {
            L1ItemInstance item = this.findItemId(itemId);
            if (item != null) {
                return item.getCount();
            }
            else {
                return 0L;
            }
        }
        else {
            Object[] itemList = this.findItemsIdNotEquipped(itemId);
            return (long)itemList.length;
        }
    }
    
    public long countItems(String nameid) {
        if (ItemTable.get().getTemplate(nameid).isStackable()) {
            L1ItemInstance item = this.findItemId(nameid);
            if (item != null) {
                return item.getCount();
            }
            else {
                return 0L;
            }
        }
        else {
            Object[] itemList = this.findItemsIdNotEquipped(nameid);
            return (long)itemList.length;
        }
    }
    
    public void shuffle() {
        Collections.shuffle(this._items);
    }
    
    public void clearItems() {
        for (Object itemObject : this._items) {
            L1ItemInstance item = (L1ItemInstance)itemObject;
            World.get().removeObject(item);
        }
    }
    
    public void loadItems() {
    }
    
    public void insertItem(L1ItemInstance item) {
    }
    
    public void updateItem(L1ItemInstance item) {
    }
    
    public void onRemoveItem(L1ItemInstance item) {
        this._items.remove(item);
    }
    
    public void updateItem(L1ItemInstance item, int colmn) {
    }
    
    private void set_time_item(L1ItemInstance item) {
        if (item.get_time() == null) {
            int[] date = null;
            if (ItemTimeTable.TIME.get((Integer)item.getItemId()) != null) {
                date = ItemTimeTable.TIME.get((Integer)item.getItemId());
            }
            if (date != null) {
                Calendar cal = Calendar.getInstance();
                if (date[0] != 0) {
                    cal.add(5, date[0]);
                }
                if (date[1] != 0) {
                    cal.add(11, date[1]);
                }
                if (date[2] != 0) {
                    cal.add(12, date[2]);
                }
                Timestamp ts = new Timestamp(cal.getTimeInMillis());
                item.set_time(ts);
                CharItemsTimeReading.get().addTime(item.getId(), ts);
            }
        }
    }
    
    public static final void recordToFiles(String filename, String info, Timestamp timestamp) {
        try {
     
                BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/" + filename + "/" + L1Inventory.sdf.format(timestamp) + ".txt", true));
                try {
                    out.write(info);
                    out.newLine();
                    out.flush();
                }
                finally {
                    if (out != null) {
                        out.close();
                    }
                }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void result(String info, int x) {
        String y = "";
        if (x == 1) {
            y = "玩家紀錄/個人倉庫存放紀錄.txt";
        }
        else if (x == 2) {
            y = "玩家紀錄/個人倉庫領取紀錄.txt";
        }
        else if (x == 3) {
            y = "玩家紀錄/妖精倉庫存放紀錄.txt";
        }
        else if (x == 4) {
            y = "玩家紀錄/妖精倉庫領取紀錄.txt";
        }
        else if (x == 5) {
            y = "玩家紀錄/血盟倉庫存放紀錄.txt";
        }
        else if (x == 6) {
            y = "玩家紀錄/血盟倉庫領取紀錄.txt";
        }
        else if (x == 7) {
            y = "玩家紀錄/倉庫領取異常記錄.txt";
        }
        else if (x == 8) {
            y = "玩家紀錄/個人商店掛賣紀錄.txt";
        }
        else if (x == 9) {
            y = "玩家紀錄/個人商店掛收紀錄.txt";
        }
        else if (x == 10) {
            y = "玩家紀錄/個人與NPC商店異常記錄.txt";
        }
        else if (x == 11) {
            y = "玩家紀錄/個人商店元寶掛賣紀錄.txt";
        }
        else if (x == 12) {
            y = "玩家紀錄/個人商店元寶掛收紀錄.txt";
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(y, true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public class DataComparator implements Comparator<Object>
    {
        @Override
        public int compare(Object item1, Object item2) {
            return ((L1ItemInstance)item1).getEnchantLevel() - ((L1ItemInstance)item2).getEnchantLevel();
        }
    }
}
