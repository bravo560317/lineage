package com.lineage.data.item_etcitem;

import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ring extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ring();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1PcInstance partner = null;
        boolean partner_stat = false;
        if (QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
            return;
        }
        else if (pc.getPartnerId() != 0) {
            partner = (L1PcInstance)World.get().findObject(pc.getPartnerId());
            if (partner != null && partner.getPartnerId() != 0 && pc.getPartnerId() == partner.getId() && partner.getPartnerId() == pc.getId()) {
                partner_stat = true;
            }
            if (item.getChargeCount() <= 0) {
                pc.sendPackets(new S_ServerMessage(791));
                return;
            }
            else {
                if (partner_stat) {
                    boolean castle_area = L1CastleLocation.checkInAllWarArea(partner.getX(), partner.getY(), partner.getMapId());
                    if ((partner.getMapId() == 0 || partner.getMapId() == 4 || partner.getMapId() == 304) && !castle_area) {
                        L1Teleport.teleport(pc, partner.getX(), partner.getY(), partner.getMapId(), 5, true);
                        if (item.getItemId() >= 40903 && item.getItemId() <= 40908) {
                            item.setChargeCount(item.getChargeCount() - 1);
                            pc.getInventory().updateItem(item, 128);
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(547));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(546));
                }
                return;
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(662));
            return;
        }
    }
}
