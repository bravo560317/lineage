package com.lineage.data.item_etcitem.add;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.data.cmd.EnchantWeapon;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Dai100_ScrollEnchantWeapon extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Dai100_ScrollEnchantWeapon();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else {
            int safe_enchant = tgItem.getItem().get_safeenchant();
            boolean isErr = false;
            int use_type = tgItem.getItem().getUseType();
            switch (use_type) {
                case 1: {
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
            int weaponId = tgItem.getItem().getItemId();
            if (weaponId >= 246 && weaponId <= 255) {
                isErr = true;
            }
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                if (tgItem.getEnchantLevel() < Configtype.weaponlv) {
                    pc.getInventory().removeItem(item, 1L);
                    EnchantExecutor enchantExecutor = new EnchantWeapon();
                    int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                    enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                }
                else {
                    pc.sendPackets(new S_ServerMessage("已達到武器最高強化階段。"));
                }
                return;
            }
        }
    }
}
