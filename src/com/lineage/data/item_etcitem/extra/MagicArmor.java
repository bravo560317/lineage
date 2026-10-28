package com.lineage.data.item_etcitem.extra;

import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.datatables.lock.CharWeaponTimeReading;
import java.sql.Timestamp;
import com.lineage.server.datatables.ExtraMagicWeaponTable;
import com.lineage.server.datatables.sql.CharWeaponTimeTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class MagicArmor extends ItemExecutor
{
    private static final Random _random;
    
    static {
        _random = new Random();
    }
    
    private MagicArmor() {
    }
    
    public static ItemExecutor get() {
        return new MagicArmor();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.isEquipped()) {
            pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
            return;
        }
        else if (tgItem.getItem().getType2() != 2) {
            pc.sendPackets(new S_SystemMessage("\\aD注意！只能對防具附加，並且再次強化可覆蓋或更換其他附魔技能。"));
            return;
        }
        else {
            int steps = CharWeaponTimeTable.get_steps(targObjId);
            if (steps >= 0) {
                ++steps;
            }
            if (steps == -1) {
                steps = 0;
            }
            if (tgItem.get_magic_weapon() != null && tgItem.get_magic_weapon().getItemId() != item.getItemId()) {
                steps = 0;
            }
            L1MagicWeapon magicWeapon = ExtraMagicWeaponTable.getInstance().get(item.getItemId(), steps);
            if (magicWeapon == null && steps >= 0) {
                pc.sendPackets(new S_SystemMessage("\\aD請注意！已達該屬性最高階級。"));
                return;
            }
            else if (magicWeapon == null && steps == -1) {
                return;
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                if (MagicArmor._random.nextInt(1000) >= magicWeapon.getSuccessRandom()) {
                    pc.sendPackets(new S_SystemMessage(magicWeapon.getFailureMsg()));
                    return;
                }
                else {
                    boolean update = false;
                    update = (tgItem.get_magic_weapon() != null);
                    tgItem.set_magic_weapon(magicWeapon);
                    if (magicWeapon.getMaxUseTime() > 0) {
                        Timestamp ts = new Timestamp(System.currentTimeMillis() + (long)(magicWeapon.getMaxUseTime() * 1000));
                        tgItem.set_time(ts);
                    }
                    else {
                        tgItem.set_time(null);
                    }
                    if (update) {
                        if (tgItem.get_magic_weapon().getItemId() == item.getItemId()) {
                            CharWeaponTimeReading.get().updateTime(tgItem.getId(), tgItem.get_time(), tgItem.get_magic_weapon().getItemId(), steps, 1);
                        }
                        else {
                            CharWeaponTimeReading.get().updateTime(tgItem.getId(), tgItem.get_time(), tgItem.get_magic_weapon().getItemId(), steps, 0);
                        }
                    }
                    else {
                        CharWeaponTimeReading.get().addTime(tgItem.getId(), tgItem.get_time(), tgItem.get_magic_weapon().getItemId());
                    }
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.sendPackets(new S_SystemMessage(magicWeapon.getSuccessMsg()));
                    return;
                }
            }
        }
    }
}
