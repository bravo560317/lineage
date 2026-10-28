package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Battl_Reel extends ItemExecutor
{
    private Battl_Reel() {
    }
    
    public static ItemExecutor get() {
        return new Battl_Reel();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            if (pc.getMapId() >= 4001 && pc.getMapId() <= 4050) {
                if (!pc.hasSkillEffect(8906)) {
                    pc.setSkillEffect(8906, 600000);
                    pc.getInventory().removeItem(item, 1L);
                    pc.addHitup(30);
                    pc.addDmgup(30);
                    pc.addBowHitup(30);
                    pc.addBowDmgup(30);
                    pc.addSp(30);
                    pc.sendPackets(new S_SPMR(pc));
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 11101));
                }
                else {
                    int time = pc.getSkillEffectTimeSec(8906);
                    pc.sendPackets(new S_SystemMessage("下層戰鬥強化卷軸 剩餘時間(秒):" + time));
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("只能在屍魂塔副本使用。"));
            }
            return;
        }
    }
}
