package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Item;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.data.item_armor.set.ArmorSet;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CreateItemSet implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1CreateItemSet.class);
    }
    
    private L1CreateItemSet() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1CreateItemSet();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int key = Integer.parseInt(arg);
            HashMap<Integer, ArmorSet> map = ArmorSet.getAllSet();
            ArmorSet armorSet = map.get((Integer)key);
            int[] get_ids;
            for (int length = (get_ids = armorSet.get_ids()).length, i = 0; i < length; ++i) {
                int itemid = get_ids[i];
                L1Item temp = ItemTable.get().getTemplate(itemid);
                if (temp != null) {
                    pc.getInventory().storeItem(itemid, 1L);
                    pc.sendPackets(new S_ServerMessage(403, temp.getNameId() + "(ID:" + itemid + ")"));
                }
                else {
                    L1CreateItemSet._log.error("找不到指定編號物品:" + itemid + " 套裝編號:" + arg + " 執行的GM:" + pc.getName());
                }
            }
        }
        catch (Exception e) {
            L1CreateItemSet._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
