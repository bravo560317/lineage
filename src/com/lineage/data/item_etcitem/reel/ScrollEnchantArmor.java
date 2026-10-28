package com.lineage.data.item_etcitem.reel;

import com.lineage.data.cmd.EnchantExecutor;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.L1ItemUpdata;
import com.lineage.data.cmd.EnchantArmor;
import com.lineage.config.Configtype;
import com.lineage.server.datatables.ItemNoENTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.serverpackets.S_SkillSound;


public class ScrollEnchantArmor extends ItemExecutor
{
    final Random random;
    
    private ScrollEnchantArmor() {
        this.random = new Random();
    }
    
    public static ItemExecutor get() {
        return new ScrollEnchantArmor();
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
        else if (ItemNoENTable.RESTRICTIONS.contains((Integer)tgItem.getItemId())) {
            pc.sendPackets(new S_ServerMessage("該物品禁止強化。"));
            return;
        }
        else {
            int safe_enchant = tgItem.getItem().get_safeenchant();
            boolean isErr = false;
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
            int armorId = tgItem.getItem().getItemId();
            if (armorId >= 401004 && armorId <= 401007) {
                isErr = true;
            }
            if (armorId >= 120444 && armorId <= 120448) {
                isErr = true;
            }
            if (armorId >= 120477 && armorId <= 120479) {
                isErr = true;
            }
            if (tgItem.getBless() >= 128) {
                isErr = true;
            }
            if (isErr) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else if (tgItem.getEnchantLevel() == Configtype.armorlv) {
                pc.sendPackets(new S_ServerMessage("您的防具已達上限值。"));
                return;
            }
            else {
                int enchant_level = tgItem.getEnchantLevel();
                EnchantExecutor enchantExecutor = new EnchantArmor();
                int randomELevel = enchantExecutor.randomELevel(tgItem, item.getBless());
                pc.getInventory().removeItem(item, 1L);
                boolean isEnchant = true;
                boolean isEnchant2 = true;
                if (enchant_level < -6) {
                    isEnchant = false;
                }
                else if (enchant_level < safe_enchant) {
                    isEnchant = true;
                }
                else {
                    Random random = new Random();
                    int rnd = random.nextInt(100) + 1;
                    int enchant_level_tmp;
                    if (safe_enchant == 0) {
                        enchant_level_tmp = enchant_level + 2;
                    }
                    else {
                        enchant_level_tmp = enchant_level;
                    }
                    int enchant_chance_armor;
                    
                    // ==========================================
                    // ★ 修改處：從防具自身的「安定值」開始，每 +1 機率變成 7 成 (乘以 0.7)
                    // ==========================================
                    if (enchant_level < safe_enchant) {
                        // 未滿安定值時維持預設計算
                        if (enchant_level >= 9) {
                            enchant_chance_armor = (int)L1ItemUpdata.enchant_armor_up9((double)enchant_level_tmp);
                        }
                        else {
                            enchant_chance_armor = (int)L1ItemUpdata.enchant_armor_dn9((double)enchant_level_tmp);
                        }
                    }
                    else {
                        // 達到或超過安定值時：以「安定值」當作基礎機率，每超過 1 級就乘以 0.7
                        double baseChance = L1ItemUpdata.enchant_armor_dn9((double)safe_enchant);
                        int diff = enchant_level - safe_enchant;
                        enchant_chance_armor = (int)(baseChance * Math.pow(0.7, diff));
                    }
                    // ==========================================

                    if (item.getItemId() == 44065) {
                        enchant_chance_armor *= 2;
                    }
                    if (rnd < enchant_chance_armor) {
                        isEnchant = true;
                    }
                    else if (enchant_level >= 9 && rnd < enchant_chance_armor * 2) {
                        randomELevel = 0;
                        isEnchant2 = true;
                    }
                    else {
                        isEnchant = false;
                    }
                }
                if (randomELevel <= 0 && enchant_level > -6) {
                    isEnchant = true;
                }
                if (isEnchant) {
                    // ★ 防具強化成功特效
                    S_SkillSound success = new S_SkillSound(pc.getId(), 19026);
                    pc.sendPackets(success);
                    pc.broadcastPacket(success);

                    pc.setoldAllName(tgItem.getAllName());
                    enchantExecutor.successEnchant(pc, tgItem, randomELevel);
                    if (!pc.isGm() && Configtype.armorbroadtrue == 1 && !isEnchant2 && tgItem.getEnchantLevel() - tgItem.getItem().get_safeenchant() >= Configtype.armor_savebroad) {
                        if (isEnchant2) {
                            isEnchant2 = false;
                        }
                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg1_true, pc.getName(), pc.getoldAllName(), tgItem.getAllName())));
                        pc.setoldAllName(null);
                    }
                    tgItem.setproctect(false);
                    tgItem.setproctect1(false);
                    tgItem.setproctect2(false);
                    tgItem.setproctect3(false);
                    tgItem.setproctect4(false);
                    tgItem.setproctect5(false);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.setcheck_lv(false);
                }
                else if (tgItem.getproctect()) {
                    tgItem.setproctect(false);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().updateItem(tgItem, 4);
                    pc.getInventory().saveItem(tgItem, 4);
                    RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到高級卷的保護", pc.getIp());
                }
                else if (tgItem.getproctect1()) {
                    tgItem.setproctect1(false);
                    tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().updateItem(tgItem, 4);
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.sendPackets(new S_ServerMessage("受到保護卷軸保護,失敗後物品倒扣1。"));
                    RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到中級卷的保護", pc.getIp());
                }
                else if (tgItem.getproctect2()) {
                    tgItem.setproctect2(false);
                    tgItem.setEnchantLevel(0);
                    pc.sendPackets(new S_ItemStatus(tgItem));
                    pc.getInventory().updateItem(tgItem, 4);
                    pc.getInventory().saveItem(tgItem, 4);
                    pc.sendPackets(new S_ServerMessage("受到保護卷軸保護,失敗後物品歸0。"));
                    RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到低級卷的保護", pc.getIp());
                }
                else if (tgItem.getproctect3()) {
                    if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
                        tgItem.setproctect3(false);
                        pc.setproctctran(0);
                        tgItem.setEnchantLevel(0);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().updateItem(tgItem, 4);
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("受到保護卷軸保護,失敗後物品歸0。"));
                        RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率初卷的保護", pc.getIp());
                    }
                    else {
                        pc.setproctctran(0);
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                }
                else if (tgItem.getproctect4()) {
                    if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
                        tgItem.setproctect4(false);
                        pc.setproctctran(0);
                        tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().updateItem(tgItem, 4);
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("受到保護卷軸保護,失敗後物品倒扣1。"));
                        RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率中卷的保護", pc.getIp());
                    }
                    else {
                        pc.setproctctran(0);
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                }
                else if (tgItem.getproctect5()) {
                    if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
                        tgItem.setproctect5(false);
                        pc.setproctctran(0);
                        pc.sendPackets(new S_ItemStatus(tgItem));
                        pc.getInventory().updateItem(tgItem, 4);
                        pc.getInventory().saveItem(tgItem, 4);
                        pc.sendPackets(new S_ServerMessage("受到保護卷軸保護,失敗後物品無變化。"));
                        RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率高卷的保護", pc.getIp());
                    }
                    else {
                        pc.setproctctran(0);
                        enchantExecutor.failureEnchant(pc, tgItem);
                    }
                }
                else {
                    // ★ 防具強化失敗特效
                    S_SkillSound fail = new S_SkillSound(pc.getId(), 19025);
                    pc.sendPackets(fail);
                    pc.broadcastPacket(fail);
                    enchantExecutor.failureEnchant(pc, tgItem);
                    RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "強化失敗", pc.getIp());
                    if (!tgItem.getproctect() || !tgItem.getproctect1() || !tgItem.getproctect2()) {
                        RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "未受保護爆了", pc.getIp());
                    }
                }
                return;
            }
        }
    }
}