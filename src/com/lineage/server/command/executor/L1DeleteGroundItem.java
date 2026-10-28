package com.lineage.server.command.executor;

import com.lineage.server.model.L1Inventory;
import java.util.ArrayList;
import java.util.Iterator;
import com.lineage.server.datatables.lock.FurnitureSpawnReading;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.datatables.sql.LetterTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1DeleteGroundItem implements L1CommandExecutor
{
    private L1DeleteGroundItem() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1DeleteGroundItem();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        for (L1Object l1object : World.get().getObject()) {
            if (l1object instanceof L1ItemInstance) {
                L1ItemInstance item = (L1ItemInstance)l1object;
                if (item.getX() == 0 && item.getY() == 0) {
                    continue;
                }
                else {
                    ArrayList<L1PcInstance> tpc = World.get().getVisiblePlayer(item, 0);
                    if (tpc.size() == 0) {
                        L1Inventory inv = World.get().getInventory(item.getX(), item.getY(), item.getMapId());
                        int itemId = item.getItem().getItemId();
                        if (itemId >= 49016 && itemId <= 49025) {
                            LetterTable lettertable = new LetterTable();
                            lettertable.deleteLetter(item.getId());
                        }
                        else if (itemId >= 41383 && itemId <= 41400 && l1object instanceof L1FurnitureInstance) {
                            L1FurnitureInstance furniture = (L1FurnitureInstance)l1object;
                            if (furniture.getItemObjId() == item.getId()) {
                                FurnitureSpawnReading.get().deleteFurniture(furniture);
                            }
                        }
                        inv.deleteItem(item);
                        World.get().removeVisibleObject(item);
                        World.get().removeObject(item);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        World.get().broadcastServerMessage("刪除地面物品。");
    }
}
