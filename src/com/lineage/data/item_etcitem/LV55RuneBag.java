package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class LV55RuneBag extends ItemExecutor
{
    public static ItemExecutor get() {
        return new LV55RuneBag();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        if (itemId == 49866) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600000, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600001, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600002, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600003, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600004, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600005, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600006, 1L);
            }
            CreateNewItem.createNewItem(pc, 49477, 1L);
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49867) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600008, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600009, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600010, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600011, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600012, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600013, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600014, 1L);
            }
            CreateNewItem.createNewItem(pc, 49477, 1L);
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49868) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600016, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600017, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600018, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600019, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600020, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600021, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600022, 1L);
            }
            CreateNewItem.createNewItem(pc, 49477, 1L);
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49869) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600024, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600025, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600026, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600027, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600028, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600029, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600030, 1L);
            }
            CreateNewItem.createNewItem(pc, 49477, 1L);
            pc.getInventory().removeItem(item, 1L);
        }
        else if (itemId == 49870) {
            if (pc.isCrown()) {
                CreateNewItem.createNewItem(pc, 600032, 1L);
            }
            else if (pc.isKnight()) {
                CreateNewItem.createNewItem(pc, 600033, 1L);
            }
            else if (pc.isElf()) {
                CreateNewItem.createNewItem(pc, 600034, 1L);
            }
            else if (pc.isWizard()) {
                CreateNewItem.createNewItem(pc, 600035, 1L);
            }
            else if (pc.isDarkelf()) {
                CreateNewItem.createNewItem(pc, 600036, 1L);
            }
            else if (pc.isDragonKnight()) {
                CreateNewItem.createNewItem(pc, 600037, 1L);
            }
            else if (pc.isIllusionist()) {
                CreateNewItem.createNewItem(pc, 600038, 1L);
            }
            CreateNewItem.createNewItem(pc, 49477, 1L);
            pc.getInventory().removeItem(item, 1L);
        }
    }
}
