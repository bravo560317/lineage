package com.lineage.data.item_etcitem.add;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.lock.CharItemPowerReading;
import java.sql.Timestamp;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.data.event.PowerItemSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class hole_armor extends ItemExecutor {

    private int _type;
    private int a;
    private static final Random _random = new Random();

    public static ItemExecutor get() {
        return new hole_armor();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);

        if (tgItem == null) {
            return;
        }

        // 裝備中不可使用
        if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
            return;
        }

        // 讀出物品 type / useType
        int type = tgItem.getItem().getType();        // 用於偵測脛甲
        int useType = tgItem.getItem().getUseType();  // 用於原本白名單判定

        // ================================
        // 新增：脛甲(type=17) 允許打洞
        // ================================
        boolean isLegging = (type == 17);

        // 你的原本允許打洞的 useType 白名單
        boolean isAllowedUseType =
                (useType == 2 || useType == 13 || useType == 18 ||
                 useType == 19 || useType == 20 || useType == 21 ||
                 useType == 22 || useType == 25);

        // 最終能不能打洞
        if (!isAllowedUseType && !isLegging) {
            pc.sendPackets(new S_ServerMessage("\\fR該物品無法擴充"));
            return;
        }

        L1ItemPower_name power;
        boolean update = false;

        if (tgItem.get_power_name() != null) {
            power = tgItem.get_power_name();
            update = true;
        } else {
            power = new L1ItemPower_name();
        }

        // 已滿洞
        if (power.get_hole_count() >= PowerItemSet.ARMORHOLE) {
            pc.sendPackets(new S_ServerMessage("\\fT擴充數量已經滿了"));
            return;
        }

        // -------------------------------
        // 開始打洞
        // this._type = 1~5 (你的設定)
        // -------------------------------
        if (power != null) {

            if (_random.nextInt(100) + 1 <= this.a) {

                power.set_item_obj_id(tgItem.getId());
                power.set_hole_count(power.get_hole_count() + 1);
                tgItem.set_power_name(power);

                pc.sendPackets(new S_ServerMessage(
                        "\\fR成功增加了洞數! 最高 " + PowerItemSet.ARMORHOLE + " 洞"
                ));

                打洞成功("玩家:" + pc.getName() + " 使用 " + item.getName() +
                        " 打出洞數 +1, 時間:" + new Timestamp(System.currentTimeMillis()));

                if (update) {
                    CharItemPowerReading.get().updateItem(tgItem.getId(), power);
                } else {
                    CharItemPowerReading.get().storeItem(tgItem.getId(), power);
                }

                pc.sendPackets(new S_ItemStatus(tgItem));
                pc.sendPackets(new S_ItemName(tgItem));

            } else {
                pc.sendPackets(new S_ServerMessage("\\fR強化失敗..運氣不好"));
            }
        }

        // 移除材料
        pc.getInventory().removeItem(item, 1L);
    }

    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        } catch (Exception ignored) {}

        try {
            this.a = Integer.parseInt(set[2]);
        } catch (Exception ignored) {}
    }

    public static void 打洞成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("./玩家紀錄/打洞成功.txt", true)
            );
            out.write(info + "\r\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
