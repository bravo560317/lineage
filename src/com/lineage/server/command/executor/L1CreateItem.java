package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CreateItem implements L1CommandExecutor {
    private static final Log _log = LogFactory.getLog(L1CreateItem.class);

    private L1CreateItem() {
    }

    public static L1CommandExecutor getInstance() {
        return new L1CreateItem();
    }

    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            
            // **如果沒有輸入任何參數，則顯示用法提示**
            if (!st.hasMoreTokens()) {
                pc.sendPackets(new S_SystemMessage("請使用正確格式: ." + cmdName + " 物品ID/名稱 [數量] [強化等級] [祝福狀態]"));
                return;
            }
            
            String nameid = st.nextToken();

            // 設定數量，預設為1
            long count = 1L;
            if (st.hasMoreTokens()) {
                count = Long.parseLong(st.nextToken());
            }

            // 設定強化等級，預設為0
            int enchant = 0;
            if (st.hasMoreTokens()) {
                enchant = Integer.parseInt(st.nextToken());
            }

            // 設定祝福狀態，預設為一般 (1)
            int bless = 1; // 祝福:0, 一般:1, 詛咒:2
            if (st.hasMoreTokens()) {
                bless = Integer.parseInt(st.nextToken());
                if (bless < 0 || bless > 2) {
                    pc.sendPackets(new S_SystemMessage("祝福狀態只能是 0 (祝福), 1 (一般), 或 2 (詛咒)"));
                    return;
                }
            }

            // 獲取物品ID
            int itemid = 0;
            try {
                itemid = Integer.parseInt(nameid);
            } catch (NumberFormatException e) {
                itemid = ItemTable.get().findItemIdByNameWithoutSpace(nameid);
                if (itemid == 0) {
                    pc.sendPackets(new S_SystemMessage("沒有找到條件吻合的物品。"));
                    return;
                }
            }

            // 獲取物品模板
            L1Item temp = ItemTable.get().getTemplate(itemid);
            if (temp != null) {
                if (temp.isStackable()) {
                    // 可以堆疊的物品
                    L1ItemInstance item = ItemTable.get().createItem(itemid);
                    item.setEnchantLevel(enchant); // 設置強化等級
                    temp.setBless(bless); // 設置祝福狀態
                    item.setCount(count);
                    item.setIdentified(true);

                    if (pc.getInventory().checkAddItem(item, count) == 0) {
                        pc.getInventory().storeItem(item);
                        pc.sendPackets(new S_ServerMessage(403, item.getLogName() + "(ID:" + itemid + ")"));
                    }
                } else if (count > 10L) {
                    // 如果是不可以堆疊的物品，限制一次創造的數量最多為10
                    pc.sendPackets(new S_SystemMessage("不可以堆疊的物品一次創造數量禁止超過10"));
                    return;
                } else {
                    // 不可以堆疊的物品
                    L1ItemInstance item = null;
                    int createCount = 0;
                    while ((long) createCount < count) {
                        item = ItemTable.get().createItem(itemid);
                        item.setEnchantLevel(enchant); // 設置強化等級
                        temp.setBless(bless); // 設置祝福狀態
                        item.setIdentified(true);

                        if (pc.getInventory().checkAddItem(item, 1L) == 0) {
                            pc.getInventory().storeItem(item);
                            createCount++;
                        } else {
                            break;
                        }
                    }
                    if (createCount > 0) {
                        pc.sendPackets(new S_ServerMessage(403, item.getLogName() + "(ID:" + itemid + ")"));
                    }
                }
            } else {
                pc.sendPackets(new S_SystemMessage("指定ID不存在"));
            }
        } catch (Exception e) {
            _log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261)); // 261 \f1指令錯誤。
        }
    }
}
