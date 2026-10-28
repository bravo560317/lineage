package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.shop.L1Shop;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.shop.L1AssessedItem;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_ShopBuyList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShopBuyList(int objid, L1PcInstance pc) {
        this._byte = null;
        L1Object object = World.get().findObject(objid);
        if (!(object instanceof L1NpcInstance)) {
            return;
        }
        else {
            L1NpcInstance npc = (L1NpcInstance)object;
            int npcId = npc.getNpcTemplate().get_npcId();
            L1Shop shop = ShopTable.get().get(npcId);
            if (shop == null) {
                pc.sendPackets(new S_NoSell(npc));
                return;
            }
            else {
                List<L1AssessedItem> assessedItems = shop.assessItems(pc.getInventory());
                if (assessedItems.isEmpty()) {
                    pc.sendPackets(new S_NoSell(npc));
                    return;
                }
                else if (assessedItems.size() <= 0) {
                    pc.sendPackets(new S_NoSell(npc));
                    return;
                }
                else {
                    this.writeC(65);
                    this.writeD(objid);
                    this.writeH(assessedItems.size());
                    for (L1AssessedItem item : assessedItems) {
                        this.writeD(item.getTargetId());
                        this.writeD(item.getAssessedPrice());
                    }
                    this.writeH(7);
                    return;
                }
            }
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
