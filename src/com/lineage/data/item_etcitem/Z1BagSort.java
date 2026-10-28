package com.lineage.data.item_etcitem;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.serverpackets.S_SystemMessage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Z1BagSort extends ItemExecutor {

    private Z1BagSort() {}

    public static ItemExecutor get() {
        return new Z1BagSort();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            List<L1ItemInstance> allItems = pc.getInventory().getItems();
            if (allItems == null || allItems.isEmpty()) {
                pc.sendPackets(new S_SystemMessage("\\aF背包是空的"));
                return;
            }

            List<L1ItemInstance> equippedItems = new ArrayList<>();
            List<L1ItemInstance> unequippedItems = new ArrayList<>();

            for (L1ItemInstance i : allItems) {
                if (i == null) {
                    continue;
                }
                if (i.isEquipped()) {
                    equippedItems.add(i);
                } else {
                    unequippedItems.add(i);
                }
            }

            for (L1ItemInstance i : unequippedItems) {
                pc.sendPackets(new S_DeleteInventoryItem(i));
            }

            unequippedItems.sort(Comparator.comparingInt(i -> i.getItem().getItemId()));
            for (L1ItemInstance i : unequippedItems) {
                pc.sendPackets(new S_AddItem(i));
            }

            pc.sendPackets(new S_SystemMessage("\\aF背包整理完畢"));
            pc.saveInventory();
            pc.save();
        } catch (Exception e) {
            pc.sendPackets(new S_SystemMessage("\\aF背包整理發生錯誤"));
            e.printStackTrace();
        }
    }
}
