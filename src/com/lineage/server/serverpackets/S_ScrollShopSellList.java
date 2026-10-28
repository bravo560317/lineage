package com.lineage.server.serverpackets;

import java.io.IOException;
import com.lineage.server.templates.L1Item;
import java.util.List;
import com.lineage.server.model.shop.L1Shop;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ShopItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.model.L1TaxCalculator;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;

public class S_ScrollShopSellList extends ServerBasePacket
{
    public S_ScrollShopSellList(L1Npc npc) {
        this.writeC(70);
        int objId = World.get().getObjId(npc);
        L1Object npcObj = World.get().findObject(objId);
        this.writeD(objId);
        int npcId = ((L1NpcInstance)npcObj).getNpcTemplate().get_npcId();
        L1TaxCalculator calc = new L1TaxCalculator(npcId);
        L1Shop shop = ShopTable.get().get(npcId);
        List<L1ShopItem> shopItems = shop.getSellingItems();
        this.writeH(shopItems.size());
        L1ItemInstance dummy = new L1ItemInstance();
        for (int i = 0; i < shopItems.size(); ++i) {
            L1ShopItem shopItem = shopItems.get(i);
            L1Item item = shopItem.getItem();
            int price = calc.layTax(shopItem.getPrice());
            this.writeD(i);
            this.writeH(shopItem.getItem().getGfxId());
            this.writeD(price);
            if (shopItem.getPackCount() > 1) {
                this.writeS(item.getName() + " (" + shopItem.getPackCount() + ")");
            }
            else {
                this.writeS(item.getName());
            }
            L1Item template = ItemTable.get().getTemplate(item.getItemId());
            if (template == null) {
                this.writeC(0);
            }
            else {
                L1ItemStatus itemInfo = new L1ItemStatus(item);
                byte[] status = itemInfo.getStatusBytes(true).getBytes();
                this.writeC(status.length);
                byte[] array;
                for (int length = (array = status).length, j = 0; j < length; ++j) {
                    byte b = array[j];
                    this.writeC((int)b);
                }
            }
        }
        this.writeH(7);
    }
    
    @Override
    public byte[] getContent() throws IOException {
        return this._bao.toByteArray();
    }
}
