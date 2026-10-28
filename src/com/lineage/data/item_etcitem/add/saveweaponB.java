package com.lineage.data.item_etcitem.add;

import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class saveweaponB extends ItemExecutor
{
    private saveweaponB() {
    }
    
    public static ItemExecutor get() {
        return new saveweaponB();
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
        else if (tgItem.getItem().getType2() == 1 && tgItem.getEnchantLevel() >= 15) {
            pc.sendPackets(new S_ServerMessage("武器已達到裝備保護卷的最高保護階段。"));
            return;
        }
        else if (tgItem.getItem().getType2() == 2 && tgItem.getEnchantLevel() >= 11) {
            pc.sendPackets(new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
            return;
        }
        else if (tgItem.getItem().getItemId() >= 120477 && tgItem.getItem().getItemId() <= 120479 && tgItem.getEnchantLevel() >= 9) {
            pc.sendPackets(new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
            return;
        }
        else {
            int safe_enchant = tgItem.getItem().get_safeenchant();
            boolean isErr = false;
            int use_type = tgItem.getItem().getUseType();
            switch (use_type) {
                case 1:
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
            if (tgItem.getItem().getItemId() >= 301 && tgItem.getItem().getItemId() <= 305) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                if (tgItem != null) {
                    if (tgItem.getItem().get_safeenchant() <= -1) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else if (tgItem.getproctect() || tgItem.getproctect1() || tgItem.getproctect2()) {
                        pc.sendPackets(new S_ServerMessage("目前該裝備已經受到保護中"));
                        return;
                    }
                    else {
                        tgItem.setproctect1(true);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("中級裝備保護卷軸的力量附著的物品中。"));
                        pc.getInventory().removeItem(item, 1L);
                        RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予中階防爆", pc.getIp());
                    }
                }
                return;
            }
        }
    }
}
