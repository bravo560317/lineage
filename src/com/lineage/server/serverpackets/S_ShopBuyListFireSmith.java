package com.lineage.server.serverpackets;

import com.add.system.L1FireCrystal;
import com.add.system.L1FireSmithCrystalTable;
import java.util.HashMap;
import com.lineage.server.model.L1PcInventory;
import java.util.Iterator;
import java.util.Map;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopBuyListFireSmith extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopBuyListFireSmith(L1PcInstance pc, L1NpcInstance npc) {
        this._byte = null;
        Map<L1ItemInstance, Integer> assessedItems = this.assessItems(pc.getInventory());
        if (assessedItems.isEmpty()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "smithitem3"));
            return;
        }
        else if (assessedItems.size() <= 0) {
            pc.sendPackets(new S_NoSell(npc));
            return;
        }
        else {
            this.writeC(65);
            this.writeD(npc.getId());
            this.writeH(assessedItems.size());
            for (L1ItemInstance key : assessedItems.keySet()) {
                this.writeD(key.getId());
                this.writeD(assessedItems.get(key));
            }
            this.writeH(7);
            return;
        }
    }
    
    private Map<L1ItemInstance, Integer> assessItems(L1PcInventory inv) {
        Map<L1ItemInstance, Integer> result = new HashMap();
        for (L1ItemInstance item : inv.getItems()) {
            switch (item.getItem().getItemId()) {
                case 40308:
                case 40314:
                case 40316:
                case 41246:
                case 44070:
                case 80033:
                case 83000:
                case 83022: {
                    continue;
                }
                default: {
                    if (!item.isEquipped() && item.getItem().getType2() != 0) {
                        int key = item.getItemId();
                        if (item.getBless() == 0) {
                            key = item.getItemId() - 100000;
                        }
                        else if (item.getBless() == 2) {
                            key = item.getItemId() - 200000;
                        }
                        L1FireCrystal firecrystal = L1FireSmithCrystalTable.get().getTemplate(key);
                        if (firecrystal != null) {
                            int crystalcount = firecrystal.get_CrystalCount(item);
                            if (crystalcount > 0) {
                                result.put(item, crystalcount);
                                continue;
                            }
                            else {
                                continue;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return result;
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
