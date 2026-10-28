package com.lineage.server.serverpackets;

import java.util.ArrayList;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.templates.L1PrivateShopSellList;
import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.HashMap;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_PrivateShop extends ServerBasePacket
{
    public S_PrivateShop(L1PcInstance pc, int objectId, int type) {
        L1Object shopObj = World.get().findObject(objectId);
        if (shopObj instanceof L1PcInstance) {
            this.isPc(pc, objectId, type);
        }
        else if (shopObj instanceof L1DeInstance) {
            this.isDe(pc, objectId, type);
        }
    }
    
    private void isDe(L1PcInstance pc, int objectId, int type) {
        L1DeInstance shopDe = (L1DeInstance)World.get().findObject(objectId);
        if (shopDe == null) {
            return;
        }
        else {
            this.writeC(140);
            this.writeC(type);
            this.writeD(objectId);
            if (type == 0) {
                Map<Integer, L1ItemInstance> sellList = new HashMap();
                Map<L1ItemInstance, Integer> map = shopDe.get_sellList();
                if (map.isEmpty()) {
                    this.writeH(0);
                    return;
                }
                else if (map.size() <= 0) {
                    this.writeH(0);
                    return;
                }
                else {
                    this.writeH(map.size());
                    int i = 0;
                    for (L1ItemInstance item : map.keySet()) {
                        this.writeC(i);
                        this.writeC(item.getBless());
                        this.writeH(item.getItem().getGfxId());
                        this.writeD((int)item.getCount());
                        int price = map.get(item);
                        this.writeD(price);
                        String name = item.getNumberedViewName(item.getCount());
                        this.writeS(name);
                        this.writeC(0);
                        sellList.put(i, item);
                        ++i;
                    }
                    pc.get_otherList().DELIST.clear();
                    pc.get_otherList().DELIST.putAll(sellList);
                }
            }
            else if (type == 1) {
                Map<Integer, int[]> list = shopDe.get_buyList();
                if (list.isEmpty()) {
                    this.writeH(0);
                    return;
                }
                else if (list.size() <= 0) {
                    this.writeH(0);
                    return;
                }
                else {
                    this.writeH(list.size());
                    Iterator<Integer> iterator = (Iterator)list.keySet().iterator();
                    while (iterator.hasNext()) {
                        int key = iterator.next();
                        int[] buyitem = list.get((Integer)key);
                        int count = buyitem[2];
                        int level = buyitem[1];
                        int price2 = buyitem[0];
                        int j = 0;
                        for (L1ItemInstance pcItem : pc.getInventory().getItems()) {
                            if (pcItem.getItemId() == key && pcItem.getEnchantLevel() == level) {
                                this.writeC(j);
                                this.writeD(pcItem.getId());
                                this.writeD(count);
                                this.writeD(price2);
                                ++j;
                            }
                        }
                    }
                }
            }
            return;
        }
    }
    
    private void isPc(L1PcInstance pc, int objectId, int type) {
        L1PcInstance shopPc = (L1PcInstance)World.get().findObject(objectId);
        if (shopPc == null) {
            return;
        }
        else {
            this.writeC(140);
            this.writeC(type);
            this.writeD(objectId);
            if (type == 0) {
                ArrayList<?> list = shopPc.getSellList();
                if (list.isEmpty()) {
                    this.writeH(0);
                    return;
                }
                else {
                    int size = list.size();
                    if (size <= 0) {
                        this.writeH(0);
                        return;
                    }
                    else {
                        pc.setPartnersPrivateShopItemCount(size);
                        this.writeH(size);
                        for (int i = 0; i < size; ++i) {
                            L1PrivateShopSellList pssl = (L1PrivateShopSellList)list.get(i);
                            int itemObjectId = pssl.getItemObjectId();
                            int count = pssl.getSellTotalCount() - pssl.getSellCount();
                            int price = pssl.getSellPrice();
                            L1ItemInstance item = shopPc.getInventory().getItem(itemObjectId);
                            if (item != null) {
                                this.writeC(i);
                                this.writeC(item.getBless());
                                this.writeH(item.getItem().getGfxId());
                                this.writeD(count);
                                this.writeD(price);
                                String name = item.getNumberedViewName_shop((long)count);
                                this.writeS(name);
                                this.writeC(0);
                            }
                        }
                    }
                }
            }
            else if (type == 1) {
                ArrayList<?> list = shopPc.getBuyList();
                if (list.isEmpty()) {
                    this.writeH(0);
                    return;
                }
                else {
                    int size = list.size();
                    if (size <= 0) {
                        this.writeH(0);
                        return;
                    }
                    else {
                        this.writeH(size);
                        for (int i = 0; i < size; ++i) {
                            L1PrivateShopBuyList psbl = (L1PrivateShopBuyList)list.get(i);
                            int itemObjectId = psbl.getItemObjectId();
                            int count = psbl.getBuyTotalCount();
                            int price = psbl.getBuyPrice();
                            L1ItemInstance item = shopPc.getInventory().getItem(itemObjectId);
                            for (L1ItemInstance pcItem : pc.getInventory().getItems()) {
                                if (item.getItemId() == pcItem.getItemId() && item.getEnchantLevel() == pcItem.getEnchantLevel()) {
                                    this.writeC(i);
                                    this.writeD(pcItem.getId());
                                    this.writeD(count);
                                    this.writeD(price);
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}
