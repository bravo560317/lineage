package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.Instance.L1ItemStatus;
import com.lineage.config.ConfigOther;
import com.lineage.server.templates.L1ShopS;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.lock.DwarfShopReading;
import java.util.HashMap;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_CnSShopSellListarmor extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CnSShopSellListarmor(L1PcInstance pc, L1NpcInstance npc, int type) {
        this._byte = null;
        this.buildPacket(pc, npc.getId(), type);
    }
    
    private void buildPacket(L1PcInstance pc, int tgObjid, int type) {
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
                switch (type) {
                    case 2: {
                        if (item.getItem().getUseType() != 2) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 18: {
                        if (item.getItem().getUseType() != 18) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 19: {
                        if (item.getItem().getUseType() != 19) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 20: {
                        if (item.getItem().getUseType() != 20) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 21: {
                        if (item.getItem().getUseType() != 21) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 22: {
                        if (item.getItem().getUseType() != 22) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 23: {
                        if (item.getItem().getUseType() != 23) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 24: {
                        if (item.getItem().getUseType() != 24) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 25: {
                        if (item.getItem().getUseType() != 25) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 37: {
                        if (item.getItem().getUseType() != 37) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 40: {
                        if (item.getItem().getUseType() != 40) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    case 50: {
                        if (item.getItem().getUseType() == 2) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 18) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 19) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 20) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 21) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 22) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 25) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 37) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 23) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 24) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 40) {
                            continue;
                        }
                        else if (item.getItem().getUseType() == 1) {
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                }
                shopItems.put(info, item);
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
             // ★補上名稱，這樣物品顯示才正常
                this.writeS(item.getViewName()); // 可改成 getViewName() 看你需求
                if (ConfigOther.SHOPINFO) {//一般商店是否顯示詳細訊息         	
                    // 顯示詳細裝備信息
                    L1ItemStatus itemInfo = new L1ItemStatus(item);
                    byte[] status = itemInfo.getStatusBytes(true).getBytes();
                    this.writeC(status.length); // 寫入狀態字節數
                    for (byte b : status) {
                        this.writeC(b); // 寫入每個狀態字節
                    }
                }
                else {
                    this.writeC(0);
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
