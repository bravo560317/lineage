package com.lineage.data.item_etcitem.add;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.data.cmd.EnchantArmor;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Zel100_ScrollEnchantArmor extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Zel100_ScrollEnchantArmor();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else {
            boolean isErr = false;
            int safe_enchant = tgItem.getItem().get_safeenchant();
            int use_type = tgItem.getItem().getUseType();
            switch (use_type) {
                case 2:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 25:
                case 47: {
                    if (safe_enchant < 0) {
                        isErr = true;
                        break;
                    }
                    else {
                        break;
                    }
                }
                default: {
                    isErr = true;
                    break;
                }
            }
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                if (tgItem.getEnchantLevel() < Configtype.armorlv) {
                    pc.getInventory().removeItem(item, 1L);
                    EnchantExecutor enchantExecutor = new EnchantArmor();
                    int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                    enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                }
                else {
                    pc.sendPackets(new S_ServerMessage("已達到防具最高強化階段。"));
                }
                return;
            }
        }
    }
}
