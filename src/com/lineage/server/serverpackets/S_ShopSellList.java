package com.lineage.server.serverpackets;

import java.util.Iterator;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Item;
import java.util.List;
import com.lineage.server.model.shop.L1Shop;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRate;
import com.lineage.server.templates.L1ShopItem;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;

public class S_ShopSellList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopSellList(int objId) {
        this._byte = null;
        this.writeC(70);
        this.writeD(objId);
        L1Object npcObj = World.get().findObject(objId);
        if (!(npcObj instanceof L1NpcInstance)) {
            this.writeH(0);
            return;
        }
        else {
            int npcId = ((L1NpcInstance)npcObj).getNpcTemplate().get_npcId();
            L1Shop shop = ShopTable.get().get(npcId);
            List<?> shopItems = shop.getSellingItems();
            if (shopItems.size() <= 0) {
                this.writeH(0);
                return;
            }
            else {
                this.writeH(shopItems.size());
                for (int i = 0; i < shopItems.size(); ++i) {
                    L1ShopItem shopItem = (L1ShopItem)shopItems.get(i);
                    L1Item item = shopItem.getItem();
                    int price = (int)((double)shopItem.getPrice() * ConfigRate.RATE_SHOP_SELLING_PRICE);
                    this.writeD(i);
                    this.writeH(shopItem.getItem().getGfxId());
                    this.writeD(price);
                    int enchant = shopItem.getEnchantLevel();
                    String s1 = "";
                    if (enchant != 0) {
                        s1 = "+" + enchant + " ";
                    }
                    if (shopItem.getPackCount() > 1) {
                        this.writeS(s1 + item.getNameId() + " (" + shopItem.getPackCount() + ")");
                    }
                    else {
                        this.writeS(s1 + item.getNameId());
                    }
                    if (ConfigOther.SHOPINFO) {
                        L1ItemStatus itemInfo = new L1ItemStatus(item);
                        byte[] status = itemInfo.getStatusBytes(true).getBytes();
                        this.writeC(status.length);
                        byte[] array;
                        for (int length = (array = status).length, j = 0; j < length; ++j) {
                            byte b = array[j];
                            this.writeC((int)b);
                        }
                    }
                    else {
                        this.writeC(0);
                    }
                }
                this.writeH(7);
                return;
            }
        }
    }
    
    public S_ShopSellList(L1NpcInstance npc) {
        this._byte = null;
        this.writeC(70);
        this.writeD(npc.getId());
        int npcId = npc.getNpcTemplate().get_npcId();
        L1Shop shop = ShopTable.get().get(npcId);
        List<?> shopItems = shop.getSellingItems();
        if (shopItems.size() <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(shopItems.size());
            for (int i = 0; i < shopItems.size(); ++i) {
                L1ShopItem shopItem = (L1ShopItem)shopItems.get(i);
                L1Item item = shopItem.getItem();
                int price = shopItem.getPrice();
                this.writeD(i);
                this.writeH(shopItem.getItem().getGfxId());
                this.writeD(price);
                if (shopItem.getPackCount() > 1) {
                    this.writeS(item.getNameId() + " (" + shopItem.getPackCount() + ")");
                }
                else {
                    this.writeS(item.getNameId());
                }
                this.writeC(0);
            }
            this.writeH(7);
            return;
        }
    }
    
    public S_ShopSellList(L1PcInstance pc, boolean info) {
        this._byte = null;
        this.writeC(70);
        this.writeD(0);
        int itemsize = 0;
        for (L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.isEquipped()) {
                ++itemsize;
            }
        }
        if (itemsize <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(itemsize);
            int i = 0;
            for (L1ItemInstance item2 : pc.getInventory().getItems()) {
                if (item2.isEquipped()) {
                    this.writeD(i);
                    ++i;
                    this.writeH(item2.getItem().getGfxId());
                    this.writeD(0);
                    this.writeS(item2.getLogName());
                    L1Item template = ItemTable.get().getTemplate(item2.getItemId());
                    L1ItemStatus itemInfo = new L1ItemStatus(template);
                    byte[] status = itemInfo.getStatusBytes(true).getBytes();
                    this.writeC(status.length);
                    byte[] array;
                    for (int length = (array = status).length, j = 0; j < length; ++j) {
                        byte b = array[j];
                        this.writeC((int)b);
                    }
                }
            }
            this.writeH(0);
            return;
        }
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
