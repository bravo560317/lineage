package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.templates.L1ShopItem;
import com.lineage.server.datatables.ShopCnTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopSellListCnX extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopSellListCnX(L1PcInstance pc, int tgObjid) {
        this._byte = null;
        this.buildPacket(pc, tgObjid);
    }
    
    private void buildPacket(L1PcInstance pc, int tgObjid) {
        this.writeC(70);
        this.writeD(tgObjid);
        ArrayList<L1ShopItem> shopItems = ShopCnTable.get().get(1);
        if (shopItems.size() <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(shopItems.size());
            int i = 0;
            for (L1ShopItem shopItem : shopItems) {
                ++i;
                pc.get_otherList().add_cnList(i, shopItem);
                L1Item item = shopItem.getItem();
                this.writeD(i);
                this.writeH(item.getGfxId());
                this.writeD(shopItem.getPrice());
                if (shopItem.getPackCount() > 1) {
                    this.writeS(item.getNameId() + " (" + shopItem.getPackCount() + ")");
                }
                else {
                    this.writeS(item.getNameId());
                }
                L1ItemStatus itemInfo = new L1ItemStatus(item);
                byte[] status = itemInfo.getStatusBytes(true).getBytes();
                this.writeC(status.length);
                byte[] array;
                for (int length = (array = status).length, j = 0; j < length; ++j) {
                    byte b = array[j];
                    this.writeC((int)b);
                }
            }
            this.writeH(6100);
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
