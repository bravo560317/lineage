package com.lineage.data.cmd;

import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.Instance.L1ItemPower;
import com.lineage.server.datatables.lock.DwarfForVIPReading;
import com.lineage.server.datatables.sql.DwarfForVIPTable;
import com.lineage.server.world.World;
import com.lineage.config.Configtype;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class EnchantAccessory_Acc extends EnchantExecutor_Acc
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(EnchantAccessory_Acc.class);
    }
    
    @Override
    public void failureEnchant(L1PcInstance pc, L1ItemInstance item) {
        if (pc.get_other().get_item() != null) {
            pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.get_other().set_item(null);
            return;
        }
        else {
            StringBuilder s = new StringBuilder();
            if (!item.isIdentified()) {
                s.append(item.getName());
            }
            else {
                s.append(item.getLogName());
            }
            pc.sendPackets(new S_ServerMessage(164, s.toString(), "$252"));
            if (!pc.isGm() && Configtype.Accessoryfalse == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() > Configtype.Accessory_savebroad) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg4_fail, pc.getName(), s.toString())));
            }
            DwarfForVIPTable.savepcid.add(pc.getId());
            DwarfForVIPTable.saveweapon.add(item);
            DwarfForVIPTable.savename.add(item.getNumberedViewName(1L));
            DwarfForVIPReading.get().insertItem(pc.getName(), item);
            pc.getInventory().removeItem(item, item.getCount());
            EnchantAccessory_Acc._log.info("人物:" + pc.getName() + "點爆物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
            return;
        }
    }
    
    @Override
    public void successEnchant(L1PcInstance pc, L1ItemInstance item, int randomELevel) {
        if (pc.get_other().get_item() != null) {
            pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.get_other().set_item(null);
            return;
        }
        else {
            StringBuilder s = new StringBuilder();
            StringBuilder sa = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            if (!item.isIdentified()) {
                s.append(item.getName());
            }
            else if (item.getEnchantLevel() > 0) {
                s.append("+" + item.getEnchantLevel() + " " + item.getName());
            }
            else if (item.getEnchantLevel() < 0) {
                s.append(String.valueOf(item.getEnchantLevel()) + " " + item.getName());
            }
            else {
                s.append(item.getName());
            }
            switch (randomELevel) {
                case -1: {
                    sa.append("$246");
                    sb.append("$247");
                    break;
                }
                case 0: {
                    pc.sendPackets(new S_ServerMessage(160, s.toString(), "$252", "$248"));
                    pc.setcheck_lv(true);
                    return;
                }
                case 1: {
                    sa.append("$252");
                    sb.append("$247");
                    break;
                }
                case 2:
                case 3: {
                    sa.append("$252");
                    sb.append("$248");
                    break;
                }
            }
            pc.sendPackets(new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));
            int oldEnchantLvl = item.getEnchantLevel();
            int newEnchantLvl = oldEnchantLvl + randomELevel;
            if (oldEnchantLvl != newEnchantLvl) {
                item.setEnchantLevel(newEnchantLvl);
                pc.getInventory().updateItem(item, 4);
                pc.getInventory().saveItem(item, 4);
                if (!pc.isGm() && Configtype.Accessorytrue == 1 && item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.Accessory_savebroad) {
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(Configtype.msg4_true, pc.getName(), item.getLogName())));
                }
                if (item.isEquipped()) {
                    int use_type = item.getItem().getUseType();
                    switch (use_type) {
                        case 2:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 25:
                        case 47: {
                            pc.addAc(-randomELevel);
                            Integer attack = L1ItemPower.Attack.get((Integer)item.getItemId());
                            if (attack != null) {
                                pc.addMr(randomELevel * attack);
                            }
                            Integer bowattack = L1ItemPower.BowAttack.get((Integer)item.getItemId());
                            if (bowattack != null) {
                                pc.addMr(randomELevel * bowattack);
                            }
                            Integer mr = L1ItemPower.MR.get((Integer)item.getItemId());
                            if (mr != null) {
                                pc.addMr(randomELevel * mr);
                            }
                            Integer sp = L1ItemPower.SP.get((Integer)item.getItemId());
                            if (sp != null) {
                                pc.addSp(randomELevel * sp);
                            }
                            Integer weight = L1ItemPower.weightReductionByEnchant.get((Integer)item.getItemId());
                            if (weight != null) {
                                pc.addWeightReduction(randomELevel * weight);
                            }
                            Integer hp = L1ItemPower.HP.get((Integer)item.getItemId());
                            if (hp != null) {
                                if (item.getItemId() == 300429) {
                                    int enchantlvl = item.getEnchantLevel();
                                    if (randomELevel == -1) {
                                        ++enchantlvl;
                                    }
                                    switch (enchantlvl) {
                                        case 5:
                                        case 7:
                                        case 9: {
                                            pc.addMaxHp(randomELevel * hp);
                                            break;
                                        }
                                    }
                                    break;
                                }
                                else {
                                    pc.addMaxHp(randomELevel * hp);
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        case 23:
                        case 24:
                        case 37:
                        case 40: {
                            if (item.getItem().get_greater() != 3) {
                                item.GreaterAtEnchant(pc, randomELevel);
                                break;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    pc.sendPackets(new S_SPMR(pc));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                }
            }
            return;
        }
    }
}
