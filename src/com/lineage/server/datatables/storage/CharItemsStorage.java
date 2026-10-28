package com.lineage.server.datatables.storage;

import java.util.Map;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;

public interface CharItemsStorage
{
    public abstract void load();
    
    public abstract CopyOnWriteArrayList<L1ItemInstance> loadItems(Integer p0);
    
    public abstract void delUserItems(Integer p0);
    
    public abstract boolean getUserItems(Integer p0, int p1, long p2);
    
    public abstract boolean getUserItem(int p0);
    
    public abstract void del_item(int p0);
    
    public abstract void storeItem(int p0, L1ItemInstance p1) throws Exception;
    
    public abstract void deleteItem(int p0, L1ItemInstance p1) throws Exception;
    
    public abstract void updateItemId_Name(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemId(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemCount(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemDurability(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemChargeCount(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemRemainingTime(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemEnchantLevel(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemEquipped(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemIdentified(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemBless(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemAttrEnchantKind(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemAttrEnchantLevel(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemAttack(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemBowAttack(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemHit(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemBowHit(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemReductionDmg(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemSp(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemprobability(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemStr(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemDex(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemInt(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemCon(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemCha(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemWis(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemHp(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemMp(L1ItemInstance p0) throws Exception;
    
    public abstract void updateskilltype(L1ItemInstance p0) throws Exception;
    
    public abstract void updateskilltypelv(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemMr(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemAc(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemMag_Red(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemMag_Hit(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemDg(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemistSustain(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemistStun(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemistStone(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemistSleep(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemistFreeze(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemistBlind(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemArmorLv(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemArmorType(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemType(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemHpr(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemMpr(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemhppotion(L1ItemInstance p0) throws Exception;
    
    public abstract void updateBless(L1ItemInstance p0) throws Exception;
    
    public abstract void updateItemDelayEffect(L1ItemInstance p0) throws Exception;
    
    public abstract int getItemCount(int p0) throws Exception;
    
    public abstract void getAdenaCount(int p0, long p1) throws Exception;
    
    public abstract void updateGamNo(L1ItemInstance p0) throws Exception;
    
    public abstract void updateGamNpcId(L1ItemInstance p0) throws Exception;
    
    public abstract void updateStarNpcId(L1ItemInstance p0) throws Exception;
    
    public abstract Map<Integer, L1ItemInstance> getUserItems(int p0);
    
    public abstract int checkItemId(int p0);
}
