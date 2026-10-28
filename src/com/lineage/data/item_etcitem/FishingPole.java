package com.lineage.data.item_etcitem;

import com.lineage.server.timecontroller.pc.PcFishingTimer;
import com.lineage.server.serverpackets.S_Fishing;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class FishingPole extends ItemExecutor
{
    private FishingPole() {
    }
    
    public static ItemExecutor get() {
        return new FishingPole();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int fishX = data[0];
        int fishY = data[1];
        int itemId = item.getItemId();
        this.startFishing(pc, itemId, fishX, fishY);
    }
    
    private void startFishing(L1PcInstance pc, int itemId, int fishX, int fishY) {
        if (pc.getMapId() != 5300) {
            pc.sendPackets(new S_ServerMessage(1138));
            return;
        }
        else {
            int rodLength = 0;
            if (itemId == 83001) {
                rodLength = 14;
            }
            else {
                rodLength = 15;
            }
            if (pc.getMap().isFishingZone(fishX, fishY)) {
                if (pc.getMap().isFishingZone(fishX + 1, fishY) && pc.getMap().isFishingZone(fishX - 1, fishY) && pc.getMap().isFishingZone(fishX, fishY + 1) && pc.getMap().isFishingZone(fishX, fishY - 1)) {
                    if (fishX > pc.getX() + rodLength || fishX < pc.getX() - rodLength) {
                        pc.sendPackets(new S_ServerMessage(1138));
                    }
                    else if (fishY > pc.getY() + rodLength || fishY < pc.getY() - rodLength) {
                        pc.sendPackets(new S_ServerMessage(1138));
                    }
                    else if (pc.getInventory().checkItem(83002, 1L)) {
                        pc.sendPacketsAll(new S_Fishing(pc.getId(), 71, fishX, fishY));
                        pc.setFishing(true, fishX, fishY);
                        pc.setFishingPoleId(itemId);
                        PcFishingTimer.addMember(pc);
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(1137));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(1138));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(1138));
            }
            return;
        }
    }
}
