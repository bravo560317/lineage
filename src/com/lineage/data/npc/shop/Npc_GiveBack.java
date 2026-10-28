package com.lineage.data.npc.shop;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.sql.DwarfForVIPTable;
import com.lineage.server.datatables.lock.DwarfForVIPReading;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1Item;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

import java.util.ArrayList;
import java.util.List;

public class Npc_GiveBack extends NpcExecutor {

    public static NpcExecutor get() {
        return new Npc_GiveBack();
    }

    @Override
    public int type() {
        return 3;
    }

    /**
     * 計算爆裝贖回費用
     * 武器：安定 = 6
     * 飾品：安定 = 0
     * 防具：安定 = item.get_safeenchant()
     */
    private int[] getPriceByItem(L1ItemInstance item) {

        int type2 = item.getItem().getType2();      // 1 武器、2 防具、0/其他 = 飾品
        int enchant = item.getEnchantLevel();
        int useType = item.getItem().getUseType();

        int safe = item.getItem().get_safeenchant(); // 防具用
        int broken = 0; // 爆掉層數

        // 💥 武器（安定永遠 6）
        if (type2 == 1) {
            broken = Math.max(0, enchant - 6);

            if (broken == 0) return ConfigToPrice(ConfigOther.weapon_Item_Price6);
            if (broken == 1) return ConfigToPrice(ConfigOther.weapon_Item_Price7);
            if (broken == 2) return ConfigToPrice(ConfigOther.weapon_Item_Price8);
            if (broken == 3) return ConfigToPrice(ConfigOther.weapon_Item_Price9);
            if (broken >= 4) return ConfigToPrice(ConfigOther.weapon_Item_Price10);
        }

        // 💥 飾品（UseType 23/24/37/40）安定 = 0
        if (useType == 23 || useType == 24 || useType == 37 || useType == 40) {

            broken = Math.max(0, enchant - 0);

            // 飾品全部吃 armor_Item_Price10
            return ConfigToPrice(ConfigOther.armor_Item_Price10);
        }

        // 💥 防具 type2=2
        if (type2 == 2) {

            // 但有些防具 safe=0、有些 safe=4、有些 safe=6
            broken = Math.max(0, enchant - safe);

            if (broken == 0) return ConfigToPrice(ConfigOther.armor_Item_Price4);
            if (broken == 1) return ConfigToPrice(ConfigOther.armor_Item_Price5);
            if (broken == 2) return ConfigToPrice(ConfigOther.armor_Item_Price6);
            if (broken == 3) return ConfigToPrice(ConfigOther.armor_Item_Price7);
            if (broken == 4) return ConfigToPrice(ConfigOther.armor_Item_Price8);
            if (broken >= 5) return ConfigToPrice(ConfigOther.armor_Item_Price9);
        }

        // 其他類型物品（不應該發生，但保險）
        return new int[]{0, 0};
    }


    /** 把 "20001,3" → int[]{20001,3} */
    private int[] ConfigToPrice(String cfg) {
        try {
            String[] p = cfg.split(",");
            return new int[]{ Integer.valueOf(p[0]), Integer.valueOf(p[1]) };
        } catch (Exception e) {
            return new int[]{0, 0};
        }
    }


    /** 顯示贖回清單 */
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {

        try {
            List<Integer> idxList = new ArrayList<>();

            // 找出該玩家的所有暫存物品
            for (int i = 0; i < DwarfForVIPTable.savepcid.size(); i++) {
                if (DwarfForVIPTable.savepcid.get(i) == pc.getId()) {
                    idxList.add(i);
                }
            }

            if (idxList.isEmpty()) {
                String[] msg = { " ", "0", "0", " " };
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "nogiveback", msg));
                return;
            }

            int total = idxList.size();
            if (pc.get_backpage() < 1) pc.set_backpage(1);
            if (pc.get_backpage() > total) pc.set_backpage(total);

            int realIndex = idxList.get(pc.get_backpage() - 1);
            L1ItemInstance item = DwarfForVIPTable.saveweapon.get(realIndex);

            int[] price = getPriceByItem(item);

            String msg4;
            if (price[0] > 0) {
                L1Item temp = ItemTable.get().getTemplate(price[0]);
                msg4 = price[1] + "個" + temp.getName();
            } else {
                msg4 = "此物品無法贖回";
            }

            String[] msg = {
                    DwarfForVIPTable.savename.get(realIndex),
                    String.valueOf(pc.get_backpage()),
                    String.valueOf(total),
                    msg4
            };

            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giveback", msg));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** 贖回動作 */
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {

        // 重新整理該玩家的暫存物品列表
        List<Integer> idxList = new ArrayList<>();
        for (int i = 0; i < DwarfForVIPTable.savepcid.size(); i++) {
            if (DwarfForVIPTable.savepcid.get(i) == pc.getId()) {
                idxList.add(i);
            }
        }

        if (idxList.isEmpty()) {
            pc.sendPackets(new S_ServerMessage("沒有可贖回的物品。"));
            pc.set_backpage(1);
            return;
        }

        int total = idxList.size();
        if (pc.get_backpage() < 1) pc.set_backpage(1);
        if (pc.get_backpage() > total) pc.set_backpage(total);

        // ================================
        // 上一頁
        // ================================
        if (cmd.equals("prev")) {

            if (pc.get_backpage() > 1)
                pc.set_backpage(pc.get_backpage() - 1);

            int realIndex = idxList.get(pc.get_backpage() - 1);
            L1ItemInstance item = DwarfForVIPTable.saveweapon.get(realIndex);

            int[] price = getPriceByItem(item);

            String msg4;
            if (price[0] > 0) {
                L1Item tmp = ItemTable.get().getTemplate(price[0]);
                msg4 = price[1] + "個" + tmp.getName();
            } else {
                msg4 = "此物品無法贖回";
            }

            String[] msg = {
                    DwarfForVIPTable.savename.get(realIndex),
                    String.valueOf(pc.get_backpage()),
                    String.valueOf(total),
                    msg4
            };

            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giveback", msg));
            return;
        }

        // ================================
        // 下一頁
        // ================================
        if (cmd.equals("next")) {

            if (pc.get_backpage() < total)
                pc.set_backpage(pc.get_backpage() + 1);

            int realIndex = idxList.get(pc.get_backpage() - 1);
            L1ItemInstance item = DwarfForVIPTable.saveweapon.get(realIndex);

            int[] price = getPriceByItem(item);

            String msg4;
            if (price[0] > 0) {
                L1Item tmp = ItemTable.get().getTemplate(price[0]);
                msg4 = price[1] + "個" + tmp.getName();
            } else {
                msg4 = "此物品無法贖回";
            }

            String[] msg = {
                    DwarfForVIPTable.savename.get(realIndex),
                    String.valueOf(pc.get_backpage()),
                    String.valueOf(total),
                    msg4
            };

            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giveback", msg));
            return;
        }


        // ================================
        // 確認贖回
        // ================================
        if (cmd.equals("accept")) {

            int realIndex = idxList.get(pc.get_backpage() - 1);
            L1ItemInstance item = DwarfForVIPTable.saveweapon.get(realIndex);

            int[] price = getPriceByItem(item);

            // 無法贖回
            if (price[0] == 0 || price[1] <= 0) {
                pc.sendPackets(new S_ServerMessage("此物品無法贖回。"));
                return;
            }

            // 材料不足
            if (!pc.getInventory().checkItem(price[0], price[1])) {
                L1Item t = ItemTable.get().getTemplate(price[0]);
                pc.sendPackets(new S_ServerMessage(t.getName() + "不足:" + price[1]));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }

            // 扣材料
            pc.getInventory().consumeItem(price[0], price[1]);

            // 給玩家物品
            pc.getInventory().storeItem1(item);
            pc.sendPackets(new S_ServerMessage("\\fV 您的 " + item.getLogName() + " 已成功贖回！"));

            // 刪除資料
            DwarfForVIPReading.get().deleteItem(pc.getName(), item);

            DwarfForVIPTable.savepcid.remove(realIndex);
            DwarfForVIPTable.saveweapon.remove(realIndex);
            DwarfForVIPTable.savename.remove(realIndex);

            pc.set_backpage(1);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
