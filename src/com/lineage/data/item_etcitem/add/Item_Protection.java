package com.lineage.data.item_etcitem.add;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.templates.L1ItemPowerUpdate;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Item_Protection extends ItemExecutor
{
    private Item_Protection() {
    }
    
    public static ItemExecutor get() {
        return new Item_Protection();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int itemobj = data[0];
        L1ItemInstance item2 = pc.getInventory().getItem(itemobj);
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (item2 == null) {
            return;
        }
        else if (item2.getproctect6()) {
            pc.sendPackets(new S_ServerMessage(1300));
            return;
        }
        else {
            switch (itemId) {
                case 92076: {
                    L1ItemPowerUpdate info = ItemPowerUpdateTable.get().get(item2.getItemId());
                    if (info == null) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else {
                        L1Doll doll = DollPowerTable.get().get_type(item2.getItemId());
                        if (doll == null) {
                            pc.sendPackets(new S_ServerMessage(2477));
                            return;
                        }
                        else if (pc.getDoll(item2.getId()) != null) {
                            pc.sendPackets(new S_ServerMessage(1181));
                            return;
                        }
                        else {
                            item2.setproctect6(true);
                            pc.sendPackets(new S_ItemStatus(tgItem));
                            pc.sendPackets(new S_ServerMessage(1308, item2.getLogName()));
                            pc.getInventory().removeItem(item, 1L);
                            娃娃保護("IP(" + pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "在" + "【 + " + tgItem.getEnchantLevel() + " " + tgItem.getName() + "】- 附加娃娃保護成功," + "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
                            break;
                        }
                    }
                }
            }
            return;
        }
    }
    
    public static void 娃娃保護(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[娃娃保護].txt", true));
            out.write(info + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
