package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.data.cmd.EnchantArmor;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Enc_Percentage_Hundred extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Enc_Percentage_Hundred();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
            return;
        }
        else {
            int safe_enchant = tgItem.getItem().get_safeenchant();
            boolean isErr = false;
            int use_type = tgItem.getItem().getUseType();
            switch (use_type) {
                case 23:
                case 24:
                case 37:
                case 40: {
                    if (tgItem.getItem().get_greater() == 3) {
                        isErr = true;
                    }
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
                int enchant_level = tgItem.getEnchantLevel();
                if (enchant_level >= 9) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    EnchantExecutor enchantExecutor = new EnchantArmor();
                    pc.getInventory().removeItem(item, 1L);
                    enchantExecutor.successEnchant(pc, tgItem, 1);
                    return;
                }
            }
        }
    }
}
