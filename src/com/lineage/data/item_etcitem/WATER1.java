package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class WATER1 extends ItemExecutor
{
    private WATER1() {
    }
    
    public static ItemExecutor get() {
        return new WATER1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.hasSkillEffect(85501)) {
            int time = pc.getSkillEffectTimeSec(85501);
            pc.sendPackets(new S_ServerMessage("真 死亡騎士之心效果剩餘時間(秒):" + time));
            return;
        }
        else {
            int time = 600;
            pc.setSkillEffect(85501, 600000);
            pc.sendPacketsAll(new S_SkillSound(pc.getId(), 245));
            pc.sendPacketsAll(new S_Liquor(pc.getId(), 8));
            pc.sendPackets(new S_ServerMessage(1065));
            pc.setSkillEffect(998, 600000);
            pc.addMaxHp(100);
            pc.addMaxMp(100);
            pc.addDmgup(5);
            pc.addHitup(10);
            pc.addBowDmgup(5);
            pc.addBowHitup(10);
            pc.addSp(5);
            pc.addAc(-10);
            pc.addMr(10);
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharAttrDef(pc));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
            pc.sendPackets(new S_OwnCharStatus2(pc));
            pc.sendPackets(new S_ServerMessage(4699));
            return;
        }
    }
}
