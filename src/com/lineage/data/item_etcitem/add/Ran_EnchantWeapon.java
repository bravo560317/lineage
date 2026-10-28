package com.lineage.data.item_etcitem.add;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.data.cmd.EnchantWeapon;
import java.util.Random;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ran_EnchantWeapon extends ItemExecutor
{
    private int _chance;
    
    private Ran_EnchantWeapon() {
    }
    
    public static ItemExecutor get() {
        return new Ran_EnchantWeapon();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.getItem().getType2() != 1) {
            pc.sendPackets(new S_SystemMessage("物品必須是武器系列"));
            return;
        }
        else if (tgItem.getItem().get_safeenchant() < 0 || tgItem.getBless() >= 128) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            int use_type = tgItem.getItem().getUseType();
            if (use_type == 23 || use_type == 24 || use_type == 37 || use_type == 40) {
                pc.sendPackets(new S_SystemMessage("飾品無法使用。"));
                return;
            }
            else if (tgItem.getEnchantLevel() >= Configtype.weaponlv) {
                pc.sendPackets(new S_SystemMessage("已經超過最高強化上限。"));
                return;
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                Random random = new Random();
                EnchantExecutor enchantExecutor = new EnchantWeapon();
                if (this._chance > random.nextInt(100) + 1) {
                    enchantExecutor.successEnchant(pc, tgItem, 1);
                }
                else {
                    enchantExecutor.successEnchant(pc, tgItem, 0);
                }
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._chance = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
