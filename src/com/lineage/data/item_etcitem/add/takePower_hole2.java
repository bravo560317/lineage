package com.lineage.data.item_etcitem.add;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.templates.L1ItemPower_name;
import java.sql.Timestamp;
import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class takePower_hole2 extends ItemExecutor
{
    private int type;
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(takePower_hole2.class);
        _random = new Random();
    }
    
    public static ItemExecutor get() {
        return new takePower_hole2();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            int targObjId = data[0];
            L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
            if (tgItem == null) {
                return;
            }
            else if (tgItem.get_power_name() == null) {
                pc.sendPackets(new S_ServerMessage("\\fT這個物品沒有凹槽!"));
                return;
            }
            else if (tgItem.isEquipped()) {
                pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
                return;
            }
            else {
                L1ItemPower_name power = tgItem.get_power_name();
                int use_type = tgItem.getItem().getUseType();
                boolean isErr = false;
                boolean isErr2 = false;
                if (power.get_hole_2() == 0) {
                    switch (use_type) {
                        case 2:
                        case 19:
                        case 20:
                        case 21:
                        case 22: {
                            isErr = true;
                            break;
                        }
                    }
                    switch (use_type) {
                        case 1: {
                            isErr2 = true;
                            break;
                        }
                    }
                    if (isErr) {
                        pc.sendPackets(new S_ServerMessage("該防具第2洞尚未插入卡片"));
                    }
                    if (isErr2) {
                        pc.sendPackets(new S_ServerMessage("該武器第2洞尚未插入卡片"));
                    }
                    return;
                }
                else if (power.get_hole_2() > 0 && power.get_hole_count() >= 1) {
                    if (takePower_hole2._random.nextInt(100) + 1 <= this.type) {
                        pc.getInventory().storeItem(power.get_hole_2(), 1L);
                        power.set_hole_2(0);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.sendPackets(new S_ItemName(tgItem));
                        CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
                        打洞水晶拔出成功("玩家 :" + pc.getName() + "||" + item.getName() + "成功拔出.第1洞 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("取回失敗"));
                    }
                    pc.getInventory().removeItem(item, 1L);
                }
            }
        }
        catch (Exception e) {
            takePower_hole2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
    
    public static void 打洞水晶拔出成功(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/打洞水晶拔出成功.txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
