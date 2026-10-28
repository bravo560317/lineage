package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Food2 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Food2();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        this.useGreenPotion(pc, itemId);
        pc.getInventory().removeItem(item, 1L);
    }
    
    private void useGreenPotion(L1PcInstance pc, int itemId) {
        if (pc.hasSkillEffect(71)) {
            pc.sendPackets(new S_ServerMessage(698));
            return;
        }
        else {
            L1BuffUtil.cancelAbsoluteBarrier(pc);
            int time = 30;
            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 191));
            if (pc.getHasteItemEquipped() > 0) {
                return;
            }
            else {
                pc.setDrink(false);
                if (pc.hasSkillEffect(43)) {
                    pc.killSkillEffectTimer(43);
                    pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.setMoveSpeed(0);
                }
                else if (pc.hasSkillEffect(54)) {
                    pc.killSkillEffectTimer(54);
                    pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.setMoveSpeed(0);
                }
                else if (pc.hasSkillEffect(1001)) {
                    pc.killSkillEffectTimer(1001);
                    pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                    pc.setMoveSpeed(0);
                }
                if (pc.hasSkillEffect(29)) {
                    pc.killSkillEffectTimer(29);
                    pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                }
                else if (pc.hasSkillEffect(76)) {
                    pc.killSkillEffectTimer(76);
                    pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                }
                else if (pc.hasSkillEffect(152)) {
                    pc.killSkillEffectTimer(152);
                    pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                }
                else {
                    pc.sendPackets(new S_SkillHaste(pc.getId(), 1, 30));
                    pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
                    pc.setMoveSpeed(1);
                    pc.setSkillEffect(1001, 30000);
                }
                return;
            }
        }
    }
}
