package com.lineage.data.item_etcitem.doll;

import com.lineage.server.templates.L1Doll;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.utils.Random;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Update_gem extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Update_gem.class);
    }
    
    public static ItemExecutor get() {
        return new Update_gem();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
            if (tgItem == null) {
                return;
            }
            else {
                L1Doll doll = DollPowerTable.get().get_type(tgItem.getItemId());
                if (doll == null) {
                    pc.sendPackets(new S_ServerMessage(2477));
                    return;
                }
                else if (pc.getDoll(tgItem.getId()) != null) {
                    pc.sendPackets(new S_ServerMessage(1181));
                    return;
                }
                else {
                    int[] firegirls = { 80290, 80291, 80292, 80293, 80294, 80295, 80296, 80297, 80298, 80299 };
                    int givegirl = firegirls[Random.getInt(firegirls.length)];
                    int[] firegirls2 = { 80300, 80301, 80302, 80303, 80304 };
                    int givegirl2 = firegirls2[Random.getInt(firegirls2.length)];
                    int[] fireboys = { 80305, 80306, 80307, 80308, 80309, 80310, 80311, 80312, 80313, 80314 };
                    int giveboy = fireboys[Random.getInt(fireboys.length)];
                    int[] fireboys2 = { 80315, 80316, 80317, 80318, 80319 };
                    int giveboy2 = fireboys2[Random.getInt(fireboys2.length)];
                    if (tgItem.getItemId() == 80283 || (tgItem.getItemId() >= 80290 && tgItem.getItemId() <= 80299)) {
                        pc.getInventory().removeItem(item, 1L);
                        pc.getInventory().removeItem(tgItem, 1L);
                        if (Random.nextInt(100) + 1 <= 95) {
                            L1ItemInstance giveitem = ItemTable.get().createItem(givegirl);
                            giveitem.setRemainingTime(90000);
                            giveitem.setIdentified(true);
                            giveitem.setCount(1L);
                            pc.getInventory().storeItem(giveitem);
                            return;
                        }
                        else {
                            L1ItemInstance giveitem = ItemTable.get().createItem(givegirl2);
                            giveitem.setRemainingTime(90000);
                            giveitem.setIdentified(true);
                            giveitem.setCount(1L);
                            pc.getInventory().storeItem(giveitem);
                            return;
                        }
                    }
                    else if (tgItem.getItemId() == 80288 || (tgItem.getItemId() >= 80305 && tgItem.getItemId() <= 80314)) {
                        pc.getInventory().removeItem(item, 1L);
                        pc.getInventory().removeItem(tgItem, 1L);
                        if (Random.nextInt(100) + 1 <= 95) {
                            L1ItemInstance giveitem = ItemTable.get().createItem(giveboy);
                            giveitem.setRemainingTime(90000);
                            giveitem.setIdentified(true);
                            giveitem.setCount(1L);
                            pc.getInventory().storeItem(giveitem);
                            return;
                        }
                        else {
                            L1ItemInstance giveitem = ItemTable.get().createItem(giveboy2);
                            giveitem.setRemainingTime(90000);
                            giveitem.setIdentified(true);
                            giveitem.setCount(1L);
                            pc.getInventory().storeItem(giveitem);
                            return;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("精靈的寶石只可使用於火焰衝擊系列"));
                    }
                }
            }
        }
        catch (Exception e) {
            Update_gem._log.error(e.getLocalizedMessage(), e);
        }
    }
}
