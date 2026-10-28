package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.server.templates.L1ShopS;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.lock.DwarfShopReading;
import java.util.HashMap;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_CnSShopSellList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CnSShopSellList(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        this.buildPacket(pc, npc.getId());
    }
    
    private void buildPacket(L1PcInstance pc, int tgObjid) {
        Map<L1ShopS, L1ItemInstance> shopItems = new HashMap();
        Map<Integer, L1ItemInstance> srcMap = DwarfShopReading.get().allItems();
        for (Integer key : srcMap.keySet()) {
            L1ShopS info = DwarfShopReading.get().getShopS(key);
            if (info == null) {
                continue;
            }
            else if (info.get_end() != 0) {
                continue;
            }
            else if (info.get_item() == null) {
                continue;
            }
            else {
                L1ItemInstance item = srcMap.get(key);
                if (item.getItem().getUseType() != 1) {
                    continue;
                }
                else {
                    shopItems.put(info, item);
                }
            }
        }
        this.writeC(70);
        this.writeD(tgObjid);
        if (shopItems.size() <= 0) {
            this.writeH(0);
            return;
        }
        else {
            this.writeH(shopItems.size());
            int i = 0;
            for (L1ShopS key2 : shopItems.keySet()) {
                ++i;
                L1ItemInstance item = shopItems.get(key2);
                pc.get_otherList().add_cnSList(item, i);
                this.writeD(i);
                this.writeH(item.getItem().getGfxId());
                this.writeD(key2.get_adena());
                this.writeS(item.getViewName());// 可改成 getViewName() 看你需求//getLogName
                L1ItemStatus itemInfo = new L1ItemStatus(item);
                byte[] status = itemInfo.getStatusBytes(true).getBytes();
                this.writeC(status.length);
                byte[] array;
                for (int length = (array = status).length, j = 0; j < length; ++j) {
                    byte b = array[j];
                    this.writeC((int)b);
                }
            }
            this.writeH(7);
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
