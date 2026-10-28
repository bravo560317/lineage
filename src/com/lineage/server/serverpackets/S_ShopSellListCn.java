package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Item;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.templates.L1ShopItem;
import com.lineage.server.datatables.ShopCnTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopSellListCn extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopSellListCn(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        this.buildPacket(pc, npc.getId(), npc.getNpcId());
    }
    
    private void buildPacket(L1PcInstance pc, int tgObjid, int npcid) {
        this.writeC(70);
        this.writeD(tgObjid);
        ArrayList<L1ShopItem> shopItems = ShopCnTable.get().get(npcid);
        if (shopItems.size() <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(shopItems.size());
            int i = 0;
            for (L1ShopItem shopItem : shopItems) {
                ++i;
                pc.get_otherList().add_cnList(shopItem, i);
                L1Item item = shopItem.getItem();
                String nameString = "";
                if (shopItem.getEnchantLevel() > 1) {
                    nameString = "+" + shopItem.getEnchantLevel() + " ";
                }
                this.writeD(i);
                this.writeH(item.getGfxId());
                this.writeD(shopItem.getPrice());
                int AlreadyBoughtCount = pc.getQuest().get_step(item.getItemId());
                int daily_count = shopItem.getDailyBuyingCount() - AlreadyBoughtCount;
                if (shopItem.getPackCount() > 1) {
                    if (shopItem.getDailyBuyingCount() > 0) {
                        this.writeS(nameString + item.getNameId() + "(" + shopItem.getPackCount() + ")-剩餘(" + daily_count + ")");
                    }
                    else {
                        this.writeS(nameString + item.getNameId() + " (" + shopItem.getPackCount() + "}");
                    }
                }
                else if (shopItem.getDailyBuyingCount() > 0) {
                    this.writeS(nameString + item.getNameId() + "-剩餘(" + daily_count + ")");
                }
                else {
                    this.writeS(nameString + item.getNameId());
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
    
    public S_ShopSellListCn(L1PcInstance pc, int npcId) {
        this._byte = null;
        this.buildPacket(pc, pc.getId(), npcId);
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
