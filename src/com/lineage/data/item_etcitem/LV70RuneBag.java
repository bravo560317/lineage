package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class LV70RuneBag extends ItemExecutor
{
    public static ItemExecutor get() {
        return new LV70RuneBag();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        if (itemId == 49871) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600041, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600042, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600043, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600044, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600045, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600046, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600047, 1L);
            }
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49872) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600049, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600050, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600051, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600052, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600053, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600054, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600055, 1L);
            }
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49873) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600057, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600058, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600059, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600060, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600061, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600062, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600063, 1L);
            }
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49874) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600065, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600066, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600067, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600068, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600069, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600070, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600071, 1L);
            }
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49875) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600073, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600074, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600075, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600076, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600077, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600078, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600079, 1L);
            }
            pc.getInventory().removeItem(item, 1L);
        }
    }
}
