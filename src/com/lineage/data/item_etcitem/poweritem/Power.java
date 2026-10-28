package com.lineage.data.item_etcitem.poweritem;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.data.event.PowerItemSet;
import com.lineage.server.datatables.PowerItemTable;
import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.william.L1PowerItem;

public class Power extends ItemExecutor {

    private static final Log _log = LogFactory.getLog(Power.class);
    private static final Random _random = new Random();

    public static ItemExecutor get() {
        return new Power();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {

            if (data == null || data.length <= 0) {
                return;
            }

            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);

            if (tgItem == null) {
                return;
            }

            if (tgItem.get_power_name() == null) {
                pc.sendPackets(new S_ServerMessage("\\fT這個物品沒有凹槽!"));
                return;
            }

            if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
                return;
            }

            int random = PowerItemSet.HOLER;
            if (pc.isGm()) {
                random = 1000;
            }

            L1ItemPower_name power = tgItem.get_power_name();
            int use_type = tgItem.getItem().getUseType();
            int item_type = tgItem.getItem().getType();

            L1PowerItem power_item = PowerItemTable.get().getItem(item.getItemId());

            // =========================
            // 類型檢查
            // =========================
            if (power_item != null) {

                boolean isErr = false;

                if (power_item.type.equalsIgnoreCase("Armor")) {

                    // 脛甲 type=17 特判
                    if (item_type == 17) {
                        isErr = true;
                    } else {
                        switch (use_type) {
                            case 2:
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 25:
                                isErr = true;
                                break;
                        }
                    }

                } else if (power_item.type.equalsIgnoreCase("Weapon")) {

                    if (use_type == 1) {
                        isErr = true;
                    }

                } else if (power_item.type.equalsIgnoreCase("All")) {

                    isErr = true;
                }

                if (!isErr) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
            }

            // =========================
            // 相同寶石數量限制
            // =========================
            if (power_item != null) {
                int powercount = 0;

                for (int i = 0; i < power.get_hole_count(); i++) {
                    if (item.getItemId() == power.get_hole(i)) {
                        powercount++;
                    }
                }

                if (powercount >= power_item.powercount) {
                    pc.sendPackets(new S_ServerMessage("\\fT同一道具允許置入" +
                            item.getItem().getName() + "(" + power_item.powercount + ")個!"));
                    return;
                }
            }

            // =========================
            // 先找空洞 (重要修正點)
            // =========================
            int emptyHole = 0;

            if (power.get_hole_1() == 0 && power.get_hole_count() >= 1) {
                emptyHole = 1;
            } else if (power.get_hole_2() == 0 && power.get_hole_count() >= 2) {
                emptyHole = 2;
            } else if (power.get_hole_3() == 0 && power.get_hole_count() >= 3) {
                emptyHole = 3;
            } else if (power.get_hole_4() == 0 && power.get_hole_count() >= 4) {
                emptyHole = 4;
            } else if (power.get_hole_5() == 0 && power.get_hole_count() >= 5) {
                emptyHole = 5;
            }

            if (emptyHole == 0) {
                pc.sendPackets(new S_ServerMessage("\\fT這個物品沒有足夠凹槽!"));
                return;
            }

            // =========================
            // 成功率判定
            // =========================
            boolean success = (_random.nextInt(1000) <= random);

            // 扣寶石 (成功或失敗都扣)
            pc.getInventory().removeItem(item, 1L);

            if (!success) {
                pc.sendPackets(new S_ServerMessage("\\fT凹槽置入魔法物品失敗!"));
                return;
            }

            // =========================
            // 寫入洞位
            // =========================
            switch (emptyHole) {
                case 1:
                    power.set_hole_1(item.getItemId());
                    break;
                case 2:
                    power.set_hole_2(item.getItemId());
                    break;
                case 3:
                    power.set_hole_3(item.getItemId());
                    break;
                case 4:
                    power.set_hole_4(item.getItemId());
                    break;
                case 5:
                    power.set_hole_5(item.getItemId());
                    break;
            }

            CharItemPowerReading.get().updateItem(tgItem.getId(), power);

            pc.sendPackets(new S_ItemStatus(tgItem));
            pc.sendPackets(new S_ItemName(tgItem));

            Timestamp ts = new Timestamp(System.currentTimeMillis());

            打洞水晶放入成功("玩家 :" + pc.getName() + "||" +
                    item.getName() + "成功放入.第" + emptyHole + "洞 ,時間:" + ts + ")");

        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    public static void 打洞水晶放入成功(String info) {
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter("./玩家紀錄/打洞水晶放入成功.txt", true))) {

            out.write(info + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
