package com.lineage.data.item_etcitem.wand;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_DoActionGFX;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Create_Monster_Magic_Wand extends ItemExecutor
{
    private Create_Monster_Magic_Wand() {
    }
    
    public static ItemExecutor get() {
        return new Create_Monster_Magic_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        Random random = new Random();
        boolean isUse = true;
        pc.broadcastPacketAll(new S_DoActionGFX(pc.getId(), 17));
        if (pc.getMap().isUsePainwand()) {
            int chargeCount = item.getChargeCount();
            if (chargeCount <= 0) {
                isUse = false;
            }
        }
        else {
            isUse = false;
        }
        if (isUse) {
            int[] mobArray = { 45008, 45140, 45016, 45021, 45025, 45033, 45099, 45147, 45123, 45130, 45046, 45092, 45138, 45098, 45127, 45143, 45149, 45171, 45040, 45155, 45192, 45173, 45213, 45079, 45144 };
            int rnd = random.nextInt(mobArray.length);
            L1SpawnUtil.spawn(pc, mobArray[rnd], 0, 300);
            int newchargecount = item.getChargeCount() - 1;
            item.setChargeCount(newchargecount);
            pc.getInventory().updateItem(item, 128);
            if (newchargecount <= 0) {
                pc.getInventory().deleteItem(item);
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
