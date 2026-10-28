package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.templates.L1ItemVIP;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.william.ConDmg;
import com.lineage.william.Addsetvip;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.datatables.ItemVIPTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class VIP extends ItemExecutor
{
    public static ItemExecutor get() {
        return new VIP();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int item_id = item.getItemId();
        L1ItemVIP vip = ItemVIPTable.get().getVIP(item_id);
        if (vip == null) {
            return;
        }
        else {
            if (item.isEquipped()) {
                if (pc.get_eqconitem() > 0) {
                    pc.addMaxHp(-pc.get_eqconitem());
                    pc.set_eqconitem(0);
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                }
                Addsetvip.forArmorSkillSound1(pc);
                item.setEquipped(false);
                ItemVIPTable.get().deleItemVIP(pc, item_id);
                pc.getInventory().updateItem(item, 8);
                pc.getInventory().saveItem(item, 8);
                int condmg = ConDmg.getConHitSkill(pc, (int)pc.getCon());
                if (condmg != 0) {
                    pc.add_eqconitem(condmg);
                    pc.addMaxHp(pc.get_eqconitem());
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                }
            }
            else {
                boolean isvip = false;
                List<L1ItemInstance> items = pc.getInventory().getItems();
                for (L1ItemInstance tgitem : items) {
                    if (!tgitem.isEquipped()) {
                        continue;
                    }
                    else {
                        int tgitem_id = tgitem.getItemId();
                        L1ItemVIP tgvip = ItemVIPTable.get().getVIP(tgitem_id);
                        if (tgvip == null) {
                            continue;
                        }
                        else if (tgvip.get_type() == vip.get_type()) {
                            isvip = true;
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
                if (isvip) {
                    pc.sendPackets(new S_ServerMessage(66));
                }
                else {
                    if (pc.get_eqconitem() > 0) {
                        pc.addMaxHp(-pc.get_eqconitem());
                        pc.set_eqconitem(0);
                        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    }
                    item.setEquipped(true);
                    Addsetvip.forArmorSkillSound(pc);
                    ItemVIPTable.get().addItemVIP(pc, item_id);
                    pc.getInventory().updateItem(item, 8);
                    pc.getInventory().saveItem(item, 8);
                    int condmg2 = ConDmg.getConHitSkill(pc, (int)pc.getCon());
                    if (condmg2 != 0) {
                        pc.add_eqconitem(condmg2);
                        pc.addMaxHp(pc.get_eqconitem());
                        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    }
                }
            }
            return;
        }
    }
}
