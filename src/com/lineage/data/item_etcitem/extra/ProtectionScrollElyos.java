package com.lineage.data.item_etcitem.extra;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.EnchantWeapon;
import java.util.Random;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ProtectionScrollElyos extends ItemExecutor
{
    private int a;
    
    public static ItemExecutor get() {
        return new ProtectionScrollElyos();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.getItem().getType2() != 1 || tgItem.getItem().get_safeenchant() <= -1) {
            pc.sendPackets(new S_SystemMessage("使用對象錯誤，請確認清楚。"));
            return;
        }
        else {
            if (tgItem.getItem().getItemId() >= 339 && tgItem.getItem().getItemId() <= 344) {
                if (tgItem.getEnchantLevel() >= 12) {
                    pc.sendPackets(new S_SystemMessage("已經超過最高強化上限。"));
                    return;
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    Random random = new Random();
                    EnchantExecutor enchantExecutor = new EnchantWeapon();
                    if (random.nextInt(100) <= this.a) {
                        enchantExecutor.successEnchant(pc, tgItem, 1);
                    }
                    else {
                        enchantExecutor.successEnchant(pc, tgItem, -tgItem.getEnchantLevel());
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        this.a = Integer.parseInt(set[1]);
    }
}
