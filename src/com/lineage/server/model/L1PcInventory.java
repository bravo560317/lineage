package com.lineage.server.model;

import java.util.Arrays;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ItemRestrictionsTable;
import java.util.Random;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.data.item_armor.set.ArmorSet;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.william.ConDmg;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.clientpackets.C_LoginToServer;
import com.lineage.server.world.World;
import com.lineage.data.event.ProtectorSet;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemColor;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.CheckItemPowerTable;
import com.lineage.data.event.checkpcitem;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_AddItem;
import java.util.List;
import com.lineage.server.datatables.ItemVIPTable;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.CharItemsReading;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.text.DecimalFormat;
import com.lineage.config.ConfigRate;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1PcInventory extends L1Inventory
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    private static final int MAX_SIZE = 240;
    private final L1PcInstance _owner;
    private int _arrowId;
    private int _stingId;
    public static final int COL_ATTR_ENCHANT_LEVEL = 2048;
    public static final int COL_ATTR_ENCHANT_KIND = 1024;
    public static final int COL_BLESS = 512;
    public static final int COL_REMAINING_TIME = 256;
    public static final int COL_CHARGE_COUNT = 128;
    public static final int COL_ITEMID = 64;
    public static final int COL_DELAY_EFFECT = 32;
    public static final int COL_COUNT = 16;
    public static final int COL_EQUIPPED = 8;
    public static final int COL_ENCHANTLVL = 4;
    public static final int COL_IS_ID = 2;
    public static final int COL_DURABILITY = 1;
    
    static {
        _log = LogFactory.getLog(L1PcInventory.class);
    }
    
    public L1PcInventory(L1PcInstance owner) {
        this._owner = owner;
        this._arrowId = 0;
        this._stingId = 0;
    }
    
    public L1PcInstance getOwner() {
        return this._owner;
    }
    
    public int getWeight240() {
        return this.calcWeight240((long)this.getWeight());
    }
    
    public int calcWeight240(long weight) {
        int weight2 = 0;
        if (ConfigRate.RATE_WEIGHT_LIMIT != 0.0) {
            double maxWeight = this._owner.getMaxWeight();
            if ((double)weight > maxWeight) {
                weight2 = 240;
            }
            else {
                double wpTemp = (double)(weight * 100L) / maxWeight * 240.0 / 100.0;
                DecimalFormat df = new DecimalFormat("00.##");
                df.format(wpTemp);
                wpTemp = (double)Math.round(wpTemp);
                weight2 = (int)wpTemp;
            }
        }
        else {
            weight2 = 0;
        }
        return weight2;
    }
    
    @Override
    public int checkAddItem(L1ItemInstance item, long count) {
        return this.checkAddItem(item, count, true);
    }
    
    public int checkAddItem(L1Item item, long count) {
        if (item == null) {
            return -1;
        }
        else {
            boolean isMaxSize = false;
            boolean isWeightOver = false;
            if (item.isStackable()) {
                if (!this.checkItem(item.getItemId()) && this.getSize() + 1 >= MAX_SIZE) {
                    isMaxSize = true;
                }
            }
            else if (this.getSize() + 1 >= MAX_SIZE) {
                isMaxSize = true;
            }
            if (isMaxSize) {
                this.sendOverMessage(263);
                return 1;
            }
            else {
                long weight = (long)this.getWeight() + (long)item.getWeight() * count / 1000L + 1L;
                if (weight < 0L || (long)item.getWeight() * count / 1000L < 0L) {
                    isWeightOver = true;
                }
                if (this.calcWeight240(weight) >= 240 && !isWeightOver) {
                    isWeightOver = true;
                }
                if (isWeightOver) {
                    this.sendOverMessage(82);
                    return 2;
                }
                else {
                    return 0;
                }
            }
        }
    }
    
    public int checkAddItem(L1ItemInstance item, long count, boolean message) {
        if (item == null) {
            return -1;
        }
        else if (count <= 0L) {
            return -1;
        }
        else {
            boolean isMaxSize = false;
            boolean isWeightOver = false;
            if (item.isStackable()) {
                if (!this.checkItem(item.getItem().getItemId()) && this.getSize() + 1 >= MAX_SIZE) {
                    isMaxSize = true;
                }
            }
            else if (this.getSize() + 1 >= MAX_SIZE) {
                isMaxSize = true;
            }
            if (isMaxSize) {
                if (message) {
                    this.sendOverMessage(263);
                }
                return 1;
            }
            else {
                long weight = (long)this.getWeight() + (long)item.getItem().getWeight() * count / 1000L + 1L;
                if (weight < 0L || (long)item.getItem().getWeight() * count / 1000L < 0L) {
                    isWeightOver = true;
                }
                if (this.calcWeight240(weight) >= 240 && !isWeightOver) {
                    isWeightOver = true;
                }
                if (isWeightOver) {
                    if (message) {
                        this.sendOverMessage(82);
                    }
                    return 2;
                }
                else {
                    return 0;
                }
            }
        }
    }
    
    public void sendOverMessage(int message_id) {
        this._owner.sendPackets(new S_ServerMessage(message_id));
    }
    
    public int getTypeAndItemIdEquipped(int type2, int type, int itemId) {
        int equipeCount = 0;
        L1ItemInstance item = null;
        for (Object itemObject : this._items) {
            item = (L1ItemInstance)itemObject;
            if (item.getItem().getType2() == type2 && item.getItem().getType() == type && item.getItemId() == itemId && item.isEquipped()) {
                ++equipeCount;
            }
        }
        return equipeCount;
    }
    
    @Override
    public void loadItems() {
        try {
            CopyOnWriteArrayList<L1ItemInstance> items = CharItemsReading.get().loadItems(this._owner.getId());
            if (items != null) {
                this._items = (List)items;
                List<L1ItemInstance> equipped = new CopyOnWriteArrayList();
                for (L1ItemInstance item : items) {
                    int itemId = item.getItemId();
                    if (item.isEquipped()) {
                        if (ItemVIPTable.get().checkVIP(itemId)) {
                            ItemVIPTable.get().addItemVIP(this._owner, itemId);
                        }
                        equipped.add(item);
                    }
                    item.setEquipped(false);
                }
                for (L1ItemInstance item : equipped) {
                    this.setEquipped(item, true, true, false);
                }
            }
        }
        catch (Exception e) {
            L1PcInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void insertItem(L1ItemInstance item) {
        int itemId = item.getItemId();
        if (item.getCount() <= 0L) {
            return;
        }
        else {
            item.set_char_objid(this._owner.getId());
            this._owner.sendPackets(new S_AddItem(item));
            if (item.getItem().getWeight() != 0) {
                this._owner.sendPackets(new S_PacketBox(10, this.getWeight240()));
            }
            if (checkpcitem.START && CheckItemPowerTable.get().checkItem(item.getItemId())) {
                CheckItemPowerTable.get().givepower(this._owner, item.getItemId());
            }
            try {
                CharItemsReading.get().storeItem(this._owner.getId(), item);
            }
            catch (Exception e) {
                L1PcInventory._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        this.updateItem(item, 16);
        if (item.getItem().isToBeSavedAtOnce()) {
            this.saveItem(item, 16);
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item, int column) {
        if (column >= 2048) {
            this._owner.sendPackets(new S_ItemStatus(item));
            column -= 2048;
        }
        if (column >= 1024) {
            this._owner.sendPackets(new S_ItemStatus(item));
            column -= 1024;
        }
        if (column >= 512) {
            this._owner.sendPackets(new S_ItemColor(item));
            column -= 512;
        }
        if (column >= 256) {
            this._owner.sendPackets(new S_ItemName(item));
            column -= 256;
        }
        if (column >= 128) {
            this._owner.sendPackets(new S_ItemName(item));
            column -= 128;
        }
        if (column >= 64) {
            this._owner.sendPackets(new S_ItemStatus(item));
            this._owner.sendPackets(new S_ItemColor(item));
            this._owner.sendPackets(new S_PacketBox(10, this.getWeight240()));
            column -= 64;
        }
        if (column >= 32) {
            column -= 32;
        }
        if (column >= 16) {
            this._owner.sendPackets(new S_ItemStatus(item));
            int weight = item.getWeight();
            if (weight != item.getLastWeight()) {
                item.setLastWeight(weight);
                this._owner.sendPackets(new S_ItemStatus(item));
            }
            else {
                this._owner.sendPackets(new S_ItemName(item));
            }
            if (item.getItem().getWeight() != 0) {
                this._owner.sendPackets(new S_PacketBox(10, this.getWeight240()));
            }
            column -= 16;
        }
        if (column >= 8) {
            this._owner.sendPackets(new S_ItemName(item));
            column -= 8;
        }
        if (column >= 4) {
            this._owner.sendPackets(new S_ItemStatus(item));
            column -= 4;
        }
        if (column >= 2) {
            this._owner.sendPackets(new S_ItemStatus(item));
            this._owner.sendPackets(new S_ItemColor(item));
            column -= 2;
        }
        if (column >= 1) {
            this._owner.sendPackets(new S_ItemStatus(item));
            --column;
        }
    }
    
    public void saveItem(L1ItemInstance item, int column) {
        if (column == 0) {
            return;
        }
        else {
            try {
                if (column >= 2048) {
                    CharItemsReading.get().updateItemAttrEnchantLevel(item);
                    column -= 2048;
                }
                if (column >= 1024) {
                    CharItemsReading.get().updateItemAttrEnchantKind(item);
                    column -= 1024;
                }
                if (column >= 512) {
                    CharItemsReading.get().updateItemBless(item);
                    column -= 512;
                }
                if (column >= 256) {
                    CharItemsReading.get().updateItemRemainingTime(item);
                    column -= 256;
                }
                if (column >= 128) {
                    CharItemsReading.get().updateItemChargeCount(item);
                    column -= 128;
                }
                if (column >= 64) {
                    CharItemsReading.get().updateItemId(item);
                    column -= 64;
                }
                if (column >= 32) {
                    CharItemsReading.get().updateItemDelayEffect(item);
                    column -= 32;
                }
                if (column >= 16) {
                    CharItemsReading.get().updateItemCount(item);
                    column -= 16;
                }
                if (column >= 8) {
                    CharItemsReading.get().updateItemEquipped(item);
                    column -= 8;
                }
                if (column >= 4) {
                    CharItemsReading.get().updateItemEnchantLevel(item);
                    column -= 4;
                }
                if (column >= 2) {
                    CharItemsReading.get().updateItemIdentified(item);
                    column -= 2;
                }
                if (column >= 1) {
                    CharItemsReading.get().updateItemDurability(item);
                    --column;
                }
            }
            catch (Exception e) {
                L1PcInventory._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void deleteItem(L1ItemInstance item) {
        if (checkpcitem.START && CheckItemPowerTable.get().checkItem(item.getItemId())) {
            CheckItemPowerTable.get().delepower(this._owner, item.getItemId());
        }
        try {
            CharItemsReading.get().deleteItem(this._owner.getId(), item);
        }
        catch (Exception e) {
            L1PcInventory._log.error(e.getLocalizedMessage(), e);
        }
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
        if (itemid == ProtectorSet.ITEM_ID && this instanceof L1PcInventory) {
            L1PcInventory pc_inv = this;
            L1PcInstance pc = pc_inv.getOwner();
            if (pc.isProtector()) {
                pc.setProtector(false);
                World.get().broadcastPacketToAll(new S_ServerMessage(2925));
            }
        }
        if (itemid == 56148 && this instanceof L1PcInventory) {
            L1PcInventory pc_inv = this;
            L1PcInstance pc = pc_inv.getOwner();
            if (pc.isEffectGS()) {
                pc.setGS(false);
                C_LoginToServer.checkforDADISStone(pc);
            }
        }
        if (itemid == 56147 && this instanceof L1PcInventory) {
            L1PcInventory pc_inv = this;
            L1PcInstance pc = pc_inv.getOwner();
            if (pc.isEffectDADIS()) {
                pc.setDADIS(false);
                C_LoginToServer.checkforDADISStone(pc);
            }
        }
        if (itemid == 44216 && this instanceof L1PcInventory) {
            L1PcInventory pc_inv = this;
            L1PcInstance pc = pc_inv.getOwner();
            if (pc._isCraftsmanHeirloom()) {
                pc.setCraftsmanHeirloom(false);
                C_LoginToServer.checkforSouls(pc);
            }
        }
        if (itemid == 44217 && this instanceof L1PcInventory) {
            L1PcInventory pc_inv = this;
            L1PcInstance pc = pc_inv.getOwner();
            if (pc._isMarsSoul()) {
                pc.setMarsSoul(false);
                C_LoginToServer.checkforSouls(pc);
            }
        }
        if (item.isEquipped()) {
            this.setEquipped(item, false);
        }
        if (item != null) {
            this._owner.sendPackets(new S_DeleteInventoryItem(item));
            this._items.remove(item);
            if (item.getItem().getWeight() != 0) {
                this._owner.sendPackets(new S_PacketBox(10, this.getWeight240()));
            }
        }
    }
    
    public void setEquipped(L1ItemInstance item, boolean equipped) {
        this.setEquipped(item, equipped, false, false);
    }
    
    public void setEquipped(L1ItemInstance item, boolean equipped, boolean loaded, boolean changeWeapon) {
        if (item.isEquipped() != equipped) {
            if (equipped) {
                if (this._owner.get_eqconitem() > 0) {
                    this._owner.addMaxHp(-this._owner.get_eqconitem());
                    this._owner.set_eqconitem(0);
                    this._owner.sendPackets(new S_HPUpdate(this._owner.getCurrentHp(), this._owner.getMaxHp()));
                }
                item.setEquipped(true);
                this._owner.getEquipSlot().set(item);
                int condmg = ConDmg.getConHitSkill(this._owner, (int)this._owner.getCon());
                if (condmg != 0) {
                    this._owner.add_eqconitem(condmg);
                    this._owner.addMaxHp(this._owner.get_eqconitem());
                    this._owner.sendPackets(new S_HPUpdate(this._owner.getCurrentHp(), this._owner.getMaxHp()));
                }
            }
            else if (!loaded && (item.getItemId() == 20077 || item.getItemId() == 20062 || item.getItemId() == 120077) && this._owner.isInvisble()) {
                this._owner.delInvis();
                return;
            }
            else {
                if (this._owner.get_eqconitem() > 0) {
                    this._owner.addMaxHp(-this._owner.get_eqconitem());
                    this._owner.set_eqconitem(0);
                    this._owner.sendPackets(new S_HPUpdate(this._owner.getCurrentHp(), this._owner.getMaxHp()));
                }
                item.setEquipped(false);
                this._owner.getEquipSlot().remove(item);
                int condmg = ConDmg.getConHitSkill(this._owner, (int)this._owner.getCon());
                if (condmg != 0) {
                    this._owner.add_eqconitem(condmg);
                    this._owner.addMaxHp(this._owner.get_eqconitem());
                    this._owner.sendPackets(new S_HPUpdate(this._owner.getCurrentHp(), this._owner.getMaxHp()));
                }
            }
            if (!loaded) {
                this._owner.getInventory().toSlotPacket(this._owner, item);
                this._owner.setCurrentHp(this._owner.getCurrentHp());
                this._owner.setCurrentMp(this._owner.getCurrentMp());
                this.updateItem(item, 8);
                this._owner.sendPackets(new S_OwnCharStatus(this._owner));
                if (item.getItem().getType2() == 1 && !changeWeapon) {
                    this._owner.sendPacketsAll(new S_CharVisualUpdate(this._owner));
                }
            }
        }
    }
    
    public L1ItemInstance checkEquippedItem(int id) {
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getItemId() == id && item.isEquipped()) {
                    return item;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return null;
    }
    
    public boolean checkEquipped(int id) {
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getItemId() == id && item.isEquipped()) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return false;
    }
    
    public boolean checkSkillType(int i) {
        for (L1ItemInstance item : this._items) {
            item.getskilltype();
            if (item.getskilltype() == i && item.isEquipped()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkSkillTypelv(int i) {
        for (L1ItemInstance item : this._items) {
            item.getskilltypelv();
            if (item.getskilltypelv() == i && item.isEquipped()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkSkillTypebless(int i) {
        for (L1ItemInstance item : this._items) {
            item.getBless();
            if (item.getBless() == i && item.isEquipped()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkCardEquipped(int itemid) {
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getItemId() == itemid && item.get_card_use() == 1) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return false;
    }
    
    public boolean checkEquipped(String nameid) {
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getName().equals(nameid) && item.isEquipped()) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return false;
    }
    
    public boolean checkEquipped(int[] ids) {
        try {
            int length = ids.length;
            int i = 0;
            while (i < length) {
                int id = ids[i];
                if (!this.checkEquipped(id)) {
                    return false;
                }
                else {
                    ++i;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return true;
    }
    
    public boolean checkEquipped(String[] names) {
        try {
            int length = names.length;
            int i = 0;
            while (i < length) {
                String name = names[i];
                if (!this.checkEquipped(name)) {
                    return false;
                }
                else {
                    ++i;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return true;
    }
    
    public int getTypeEquipped(int type2, int type) {
        int equipeCount = 0;
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getType2() == type2 && item.getItem().getType() == type && item.isEquipped()) {
                    ++equipeCount;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return equipeCount;
    }
    
    public L1ItemInstance getItemEquipped(int type2, int type) {
        L1ItemInstance equipeitem = null;
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getType2() == type2 && item.getItem().getType() == type && item.isEquipped()) {
                    equipeitem = item;
                    break;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return equipeitem;
    }
    
    public L1ItemInstance getItemEquipped1(int type2) {
        L1ItemInstance equipeitem = null;
        try {
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getType2() == type2 && item.isEquipped()) {
                    equipeitem = item;
                    break;
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return equipeitem;
    }
    
    public void setPartMode(ArmorSet armorSet, boolean isMode) {
        int[] tgItemId = armorSet.get_ids();
        for (int len = tgItemId.length, i = 0; i < len; ++i) {
            L1ItemInstance[] tgItems = this.findItemsId(tgItemId[i]);
            L1ItemInstance[] array;
            for (int length = (array = tgItems).length, j = 0; j < length; ++j) {
                L1ItemInstance tgItem = array[j];
                tgItem.setIsMatch(isMode);
                this._owner.sendPackets(new S_ItemStatus(tgItem));
            }
        }
    }
    
    public L1ItemInstance[] getRingEquipped() {
        L1ItemInstance[] equipeItem = new L1ItemInstance[4];
        try {
            int equipeCount = 0;
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getUseType() == 23 && !item.isEquipped()) {
                    equipeItem[equipeCount] = item;
                    if (++equipeCount == 4) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return equipeItem;
    }
    
    public L1ItemInstance[] getEarRingEquipped() {
        L1ItemInstance[] equipeItem = new L1ItemInstance[2];
        try {
            int equipeCount = 0;
            for (L1ItemInstance item : this._items) {
                if (item.getItem().getUseType() == 40 && item.isEquipped()) {
                    equipeItem[equipeCount] = item;
                    if (++equipeCount == 2) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception ex) {
            L1PcInventory._log.error(ex.getLocalizedMessage(), ex);
        }
        return equipeItem;
    }
    
    public void takeoffEquip(int polyid) {
        this.takeoffWeapon(polyid);
        this.takeoffArmor(polyid);
    }
    
    private void takeoffWeapon(int polyid) {
        if (this._owner.getWeapon() == null) {
            return;
        }
        else {
            boolean takeoff = false;
            int weapon_type = this._owner.getWeapon().getItem().getType();
            takeoff = !L1PolyMorph.isEquipableWeapon(polyid, weapon_type);
            if (takeoff) {
                this.setEquipped(this._owner.getWeapon(), false, false, false);
            }
            return;
        }
    }
    
    private void takeoffArmor(int polyid) {
        L1ItemInstance armor = null;
        for (int type = 0; type <= 13; ++type) {
            if (this.getTypeEquipped(2, type) != 0 && !L1PolyMorph.isEquipableArmor(polyid, type)) {
                if (type == 9) {
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                }
                else if (type == 12) {
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                }
                else {
                    armor = this.getItemEquipped(2, type);
                    if (armor != null) {
                        this.setEquipped(armor, false, false, false);
                    }
                }
            }
        }
    }
    
    public L1ItemInstance getArrow() {
        return this.getBullet(-2);
    }
    
    public L1ItemInstance getSting() {
        return this.getBullet(-3);
    }
    
    private L1ItemInstance getBullet(int useType) {
        int priorityId = 0;
        if (useType == -2) {
            if (this._owner.getWeapon().getItemId() == 192) {
                L1ItemInstance bullet = this.findItemId(40742);
                if (bullet == null) {
                    this._owner.sendPackets(new S_ServerMessage(329, "$2377"));
                }
                return bullet;
            }
            else {
                priorityId = this._arrowId;
            }
        }
        if (useType == -3) {
            priorityId = this._stingId;
        }
        if (priorityId > 0) {
            L1ItemInstance bullet = this.findItemId(priorityId);
            if (bullet != null) {
                return bullet;
            }
            else {
                if (useType == -2) {
                    this._arrowId = 0;
                }
                if (useType == -3) {
                    this._stingId = 0;
                }
            }
        }
        for (Object itemObject : this._items) {
            L1ItemInstance bullet = (L1ItemInstance)itemObject;
            if (bullet.getItem().getUseType() == useType) {
                if (useType == -2) {
                    this._arrowId = bullet.getItem().getItemId();
                }
                if (useType == -3) {
                    this._stingId = bullet.getItem().getItemId();
                }
                return bullet;
            }
        }
        return null;
    }
    
    public void setArrow(int id) {
        this._arrowId = id;
    }
    
    public void setSting(int id) {
        this._stingId = id;
    }
    
    public int hpRegenPerTick() {
        int hpr = 0;
        try {
            for (Object itemObject : this._items) {
                L1ItemInstance item = (L1ItemInstance)itemObject;
                if (item.isEquipped()) {
                    L1ItemSpecialAttributeChar attr_char = item.get_ItemAttrName();
                    if (attr_char != null) {
                        L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
                        hpr += attr.get_add_hpr() + item.getItem().get_addhpr() + item.getItemHpr();
                    }
                    else {
                        hpr += item.getItem().get_addhpr() + item.getItemHpr();
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcInventory._log.error(e.getLocalizedMessage(), e);
        }
        return hpr;
    }
    
    public int mpRegenPerTick() {
        int mpr = 0;
        try {
            for (Object itemObject : this._items) {
                L1ItemInstance item = (L1ItemInstance)itemObject;
                if (item.isEquipped()) {
                    L1ItemSpecialAttributeChar attr_char = item.get_ItemAttrName();
                    if (attr_char != null) {
                        L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
                        mpr += attr.get_add_mpr() + item.getMpr() + item.getItemMpr();
                    }
                    else {
                        mpr += item.getMpr() + item.getItemMpr();
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcInventory._log.error(e.getLocalizedMessage(), e);
        }
        return mpr;
    }
    
    public L1ItemInstance caoPenalty() {
        try {
            Random random = new Random();
            int rnd = random.nextInt(this._items.size());
            L1ItemInstance penaltyItem = this._items.get(rnd);
            if (penaltyItem.getItem().getItemId() == 44070) {
                return null;
            }
            else if (penaltyItem.getItem().getItemId() == 40308) {
                return null;
            }
            else if (penaltyItem.getItem().getItemId() == 92088) {
                return null;
            }
            else if (penaltyItem.getItem().getItemId() == 83000) {
                return null;
            }
            else if (penaltyItem.getItem().getItemId() == 83022) {
                return null;
            }
            else if (penaltyItem.getItem().isCantDelete()) {
                return null;
            }
            else if (!penaltyItem.getItem().isTradable()) {
                return null;
            }
            else if (penaltyItem.get_time() != null) {
                return null;
            }
            else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)penaltyItem.getItemId())) {
                return null;
            }
            else {
                Object[] petlist = this._owner.getPetList().values().toArray();
                Object[] array;
                for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                    Object petObject = array[i];
                    if (petObject instanceof L1PetInstance) {
                        L1PetInstance pet = (L1PetInstance)petObject;
                        if (penaltyItem.getId() == pet.getItemObjId()) {
                            return null;
                        }
                    }
                }
                if (this._owner.getDoll(penaltyItem.getId()) != null) {
                    return null;
                }
                else {
                    this.setEquipped(penaltyItem, false);
                    return penaltyItem;
                }
            }
        }
        catch (Exception e) {
            L1PcInventory._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public void delQuestItem(int itemId) {
        try {
            Random random = new Random();
            for (L1ItemInstance item : this._items) {
                if (item.getItemId() == itemId) {
                    this.removeItem(item);
                    this._owner.sendPackets(new S_ServerMessage(random.nextInt(4) + 445, item.getName()));
                }
            }
        }
        catch (Exception e) {
            L1PcInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int checkAddItem_LV(L1ItemInstance item, int count, int EnchantLevel) {
        return this.checkAddItem_LV(item, count, EnchantLevel, true);
    }
    
    public int checkAddItem_LV(L1ItemInstance item, int count, int EnchantLevel, boolean message) {
        if (item == null) {
            return -1;
        }
        else if (this.getSize() > MAX_SIZE || (this.getSize() == MAX_SIZE && (!item.isStackable() || !this.checkItem(item.getItem().getItemId())))) {
            if (message) {
                this.sendOverMessage(263);
            }
            return 1;
        }
        else {
            int weight = this.getWeight() + item.getItem().getWeight() * count / 1000 + 1;
            if (weight < 0 || item.getItem().getWeight() * count / 1000 < 0) {
                if (message) {
                    this.sendOverMessage(82);
                }
                return 2;
            }
            else if (this.calcWeight240((long)weight) >= 240) {
                if (message) {
                    this.sendOverMessage(82);
                }
                return 2;
            }
            else {
                L1ItemInstance itemExist = this.findItemId(item.getItemId());
                if (itemExist != null && itemExist.getCount() + (long)count > Long.MAX_VALUE) {
                    if (message) {
                        this.getOwner().sendPackets(new S_ServerMessage(166, "所持有的金幣", "超過了2,000,000,000上限"));
                    }
                    return 3;
                }
                else {
                    return 0;
                }
            }
        }
    }
    
    public L1ItemInstance checkItemX_Lv(int itemid, int lv, long count) {
        if (count <= 0L) {
            return null;
        }
        else {
            if (ItemTable.get().getTemplate(itemid) != null) {
                L1ItemInstance item = this.findItemIdNoEq(itemid);
                if (item != null && item.getCount() >= count && item.getEnchantLevel() == lv) {
                    return item;
                }
            }
            return null;
        }
    }
    
    public synchronized boolean checkDBItemCount(int pcId, int itemId, long count) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long actualcount = 0L;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM character_items WHERE char_id =? AND item_id = ?");
            pstm.setInt(1, pcId);
            pstm.setInt(2, itemId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                actualcount = rs.getLong("count");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        return actualcount >= count;
    }
    
    public synchronized boolean checkDBResultItemCount(int pcId, int id, int count, int xyz) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int actualcount = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            if (xyz == 1) {
                pstm = con.prepareStatement("SELECT * FROM character_warehouse WHERE account_name = ? AND id = ?");
                pstm.setInt(1, pcId);
                pstm.setInt(2, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    actualcount = rs.getInt("count");
                }
            }
            else if (xyz == 2) {
                pstm = con.prepareStatement("SELECT * FROM character_elf_warehouse WHERE account_name = ? AND id = ?");
                pstm.setInt(1, pcId);
                pstm.setInt(2, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    actualcount = rs.getInt("count");
                }
            }
            else if (xyz == 3) {
                pstm = con.prepareStatement("SELECT * FROM clan_warehouse WHERE clan_name = ? AND id = ?");
                pstm.setInt(1, pcId);
                pstm.setInt(2, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    actualcount = rs.getInt("count");
                }
            }
            else if (xyz == 4) {
                pstm = con.prepareStatement("SELECT * FROM character_char_warehouse WHERE char_name = ? AND id = ?");
                pstm.setInt(1, pcId);
                pstm.setInt(2, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    actualcount = rs.getInt("count");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        return actualcount >= count;
    }
    
    public boolean consumeItem(int itemid) {
        if (ItemTable.get().getTemplate(itemid).isStackable()) {
            L1ItemInstance item = this.findItemId(itemid);
            if (item != null) {
                this.removeItem(item, item.getCount());
                return true;
            }
        }
        else {
            L1ItemInstance[] items = this.findItemsId(itemid);
            if (items != null) {
                L1ItemInstance[] array;
                for (int length = (array = items).length, i = 0; i < length; ++i) {
                    L1ItemInstance item2 = array[i];
                    this.removeItem(item2);
                }
                return true;
            }
        }
        return false;
    }
    
    public void takeoffWeaponFor2600() {
        L1ItemInstance item = this._owner.getInventory().findItemId(1054);
        if (item != null) {
            if (this._owner.getWeapon() != null) {
                this.setEquipped(this._owner.getWeapon(), false, false, false);
            }
            this._owner.getInventory().setEquipped(item, true);
            L1PolyMorph.doPoly(this._owner, 12232, 3600, 1);
        }
    }
    
    public boolean consumeItemsIdArray(ArrayList<Integer> itemsidarray) {
        int[] itemsid = new int[itemsidarray.size()];
        long[] counts = new long[itemsidarray.size()];
        for (int i = 0; i < itemsidarray.size(); ++i) {
            itemsid[i] = itemsidarray.get(i);
            counts[i] = 1L;
        }
        return this.consumeItemsId(itemsid, counts);
    }
    
    public boolean consumeItemsId(int[] itemsid, long[] counts) {
        if (itemsid.length != counts.length) {
            return false;
        }
        else {
            int removecount = 0;
            for (int i = 0; i < itemsid.length; ++i) {
                if (ItemTable.get().getTemplate(itemsid[i]).isStackable()) {
                    L1ItemInstance item = this.findItemId(itemsid[i]);
                    if (item != null && item.getCount() >= counts[i]) {
                        this.removeItem(item, counts[i]);
                        ++removecount;
                    }
                    if (removecount == itemsid.length) {
                        return true;
                    }
                }
                else {
                    L1ItemInstance[] itemList = this.findItemsId(itemsid[i]);
                    if ((long)itemList.length == counts[i]) {
                        for (int c = 0; (long)c < counts[i]; ++c) {
                            this.removeItem(itemList[c], 1L);
                        }
                        if (++removecount == itemsid.length) {
                            return true;
                        }
                    }
                    if ((long)itemList.length > counts[i]) {
                        L1Inventory.DataComparator dc = new L1Inventory.DataComparator();
                        Arrays.sort(itemList, dc);
                        for (int c2 = 0; (long)c2 < counts[i]; ++c2) {
                            this.removeItem(itemList[c2], 1L);
                        }
                        if (++removecount == itemsid.length) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
