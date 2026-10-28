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

public class Phantom_gem extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Phantom_gem.class);
    }
    
    public static ItemExecutor get() {
        return new Phantom_gem();
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
                    int[] firegirls2 = { 80300, 80301, 80302, 80303, 80304 };
                    int givegirl2 = firegirls2[Random.getInt(firegirls2.length)];
                    int[] fireboys2 = { 80315, 80316, 80317, 80318, 80319 };
                    int giveboy2 = fireboys2[Random.getInt(fireboys2.length)];
                    if (tgItem.getItemId() >= 80300 && tgItem.getItemId() <= 80304) {
                        pc.getInventory().removeItem(item, 1L);
                        pc.getInventory().removeItem(tgItem, 1L);
                        L1ItemInstance giveitem = ItemTable.get().createItem(givegirl2);
                        giveitem.setRemainingTime(90000);
                        giveitem.setIdentified(true);
                        giveitem.setCount(1L);
                        pc.getInventory().storeItem(giveitem);
                        return;
                    }
                    else if (tgItem.getItemId() >= 80315 && tgItem.getItemId() <= 80319) {
                        pc.getInventory().removeItem(item, 1L);
                        pc.getInventory().removeItem(tgItem, 1L);
                        L1ItemInstance giveitem = ItemTable.get().createItem(giveboy2);
                        giveitem.setRemainingTime(90000);
                        giveitem.setIdentified(true);
                        giveitem.setCount(1L);
                        pc.getInventory().storeItem(giveitem);
                        return;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("魔幻的寶石只可使用於爆擊火焰衝擊系列"));
                    }
                }
            }
        }
        catch (Exception e) {
            Phantom_gem._log.error(e.getLocalizedMessage(), e);
        }
    }
}
