package com.lineage.data.item_etcitem.reel;

import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ScrollEnchantTrue extends ItemExecutor
{
    private ScrollEnchantTrue() {
    }
    
    public static ItemExecutor get() {
        return new ScrollEnchantTrue();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(data[0]);
        int itemId = item.getItem().getItemId();
        int useType = l1iteminstance1.getItem().getType2();
        if (itemId == 44069) {
            if (useType == 2) {
                this.scrollOfEnchantArmor100(pc, item, l1iteminstance1);
            }
            else {
                pc.sendPackets(new S_SystemMessage("請選擇一種防具"));
            }
        }
        else if (itemId == 44068) {
            if (useType == 1) {
                this.scrollOfEnchantWeapon100(pc, item, l1iteminstance1);
            }
            else {
                pc.sendPackets(new S_SystemMessage("請選擇一種武器"));
            }
        }
    }
    
    public void scrollOfEnchantArmor100(L1PcInstance pc, L1ItemInstance l1iteminstance, L1ItemInstance l1iteminstance1) {
        int itemId = l1iteminstance.getItem().getItemId();
        int safe_enchant = l1iteminstance1.getItem().get_safeenchant();
        int armorId = l1iteminstance1.getItem().getItemId();
        if (l1iteminstance1 == null || l1iteminstance1.getItem().getType2() != 2 || safe_enchant < 0 || l1iteminstance1.getEnchantLevel() >= Configtype.armorlv) {
            pc.sendPackets(new S_ServerMessage("您的防具已達上限值。"));
            return;
        }
        else if (l1iteminstance1.getItem().getItemId() >= 120477 && l1iteminstance1.getItem().getItemId() <= 120479 && l1iteminstance1.getEnchantLevel() >= 9) {
            pc.sendPackets(new S_ServerMessage("您的防具已達上限值。"));
            return;
        }
        else if (armorId == 20028 || armorId == 20082 || armorId == 20126 || armorId == 20173 || armorId == 20206 || armorId == 20232 || armorId == 21138 || armorId == 30009 || armorId == 21051 || armorId == 21052 || armorId == 21053 || armorId == 30012 || armorId == 21054 || armorId == 21055 || armorId == 21056 || armorId == 21140 || armorId == 21141) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (armorId >= 401004 && armorId <= 401007) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (armorId >= 120444 && armorId <= 120448) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if ((armorId == 20161 || (armorId >= 21035 && armorId <= 21038)) && itemId != 40127) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (itemId == 40127 && armorId != 20161 && (armorId < 21035 || armorId > 21038)) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            pc.getInventory().removeItem(l1iteminstance, 1L);
            this.SuccessEnchant(pc, l1iteminstance1, pc.getNetConnection(), 1);
            pc.sendPackets(new S_ItemStatus(l1iteminstance1));
            pc.getInventory().updateItem(l1iteminstance1, 4);
            pc.getInventory().saveItem(l1iteminstance1, 4);
            return;
        }
    }
    
    public void scrollOfEnchantWeapon100(L1PcInstance pc, L1ItemInstance l1iteminstance, L1ItemInstance l1iteminstance1) {
        int itemId = l1iteminstance.getItem().getItemId();
        int safe_enchant = l1iteminstance1.getItem().get_safeenchant();
        int weaponId = l1iteminstance1.getItem().getItemId();
        if (l1iteminstance1 == null || l1iteminstance1.getItem().getType2() != 1 || safe_enchant < 0 || l1iteminstance1.getEnchantLevel() == Configtype.weaponlv) {
            pc.sendPackets(new S_ServerMessage("您的武器已達上限值。"));
            return;
        }
        else if (weaponId >= 301 && weaponId <= 310) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (weaponId >= 100213 && weaponId <= 100217) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (weaponId >= 1040 && weaponId <= 1043) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (weaponId >= 100213 && weaponId <= 100217) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (weaponId == 2 || weaponId == 7 || weaponId == 35 || weaponId == 48 || weaponId == 73 || weaponId == 105 || weaponId == 120 || weaponId == 147 || weaponId == 156 || weaponId == 174 || weaponId == 175 || weaponId == 224) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (weaponId >= 246 && weaponId <= 249 && itemId != 40660) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (itemId == 40660 && (weaponId < 246 || weaponId > 249)) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if ((weaponId == 36 || weaponId == 183 || (weaponId >= 250 && weaponId <= 255)) && itemId != 40128) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (itemId == 40128 && weaponId != 36 && weaponId != 183 && (weaponId < 250 || weaponId > 255)) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            pc.getInventory().removeItem(l1iteminstance, 1L);
            this.SuccessEnchant(pc, l1iteminstance1, pc.getNetConnection(), 1);
            pc.sendPackets(new S_ItemStatus(l1iteminstance1));
            pc.getInventory().updateItem(l1iteminstance1, 4);
            pc.getInventory().saveItem(l1iteminstance1, 4);
            return;
        }
    }
    
    public void SuccessEnchant(L1PcInstance pc, L1ItemInstance item, ClientExecutor client, int i) {
        StringBuilder s = new StringBuilder();
        StringBuilder sa = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        if (!item.isIdentified()) {
            s.append(item.getName());
        }
        else {
            s.append(item.getLogName());
        }
        switch (i) {
            case -1: {
                sa.append("$246");
                sb.append("$247");
                break;
            }
            case 0: {
                pc.sendPackets(new S_ServerMessage(160, s.toString(), "$252", "$248"));
                return;
            }
            case 1: {
                sa.append("$245");
                sb.append("$247");
                break;
            }
            case 2:
            case 3: {
                sa.append("$245");
                sb.append("$248");
                break;
            }
        }
        pc.sendPackets(new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));
        if (item.getItem().getType2() == 1 && !pc.isGm() && Configtype.weaponbroadtrue == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.weapon_savebroad - 1) {
            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_true, pc.getName(), s.toString())));
        }
        if (item.getItem().getType2() == 2 && !pc.isGm() && Configtype.armorbroadtrue == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.armor_savebroad - 1) {
            World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_true, pc.getName(), s.toString())));
        }
        int oldEnchantLvl = item.getEnchantLevel();
        int newEnchantLvl = oldEnchantLvl + i;
        int safe_enchant = item.getItem().get_safeenchant();
        item.setEnchantLevel(newEnchantLvl);
        client.getActiveChar().getInventory().updateItem(item, 4);
        if (newEnchantLvl > safe_enchant) {
            client.getActiveChar().getInventory().saveItem(item, 4);
        }
        if (item.getItem().getType2() == 2) {
            if (item.isEquipped()) {
                if (item.getItem().getType() < 8 || item.getItem().getType() > 12) {
                    pc.addAc(-i);
                }
                int armorId = item.getItem().getItemId();
                int[] i2 = { 20011, 20110, 21123, 21124, 21125, 21126, 120011 };
                int j = 0;
                while (j < i2.length) {
                    if (armorId == i2[j]) {
                        pc.addMr(i);
                        pc.sendPackets(new S_SPMR(pc));
                        break;
                    }
                    else {
                        ++j;
                    }
                }
                int[] i3 = { 20056, 120056, 220056 };
                int k = 0;
                while (k < i3.length) {
                    if (armorId == i3[k]) {
                        pc.addMr(i * 2);
                        pc.sendPackets(new S_SPMR(pc));
                        break;
                    }
                    else {
                        ++k;
                    }
                }
                if (armorId == 21535) {
                    pc.addMr(i * 3);
                    pc.sendPackets(new S_SPMR(pc));
                }
                if (armorId == 71100) {
                    pc.addMr(i * 4);
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            pc.sendPackets(new S_OwnCharAttrDef(pc));
        }
    }
}
