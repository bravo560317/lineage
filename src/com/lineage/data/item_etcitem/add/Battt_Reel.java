package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Battt_Reel extends ItemExecutor
{
    private Battt_Reel() {
    }
    
    public static ItemExecutor get() {
        return new Battt_Reel();
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
                if (!pc.hasSkillEffect(8907)) {
                    pc.setSkillEffect(8907, 600000);
                    pc.getInventory().removeItem(item, 1L);
                    pc.addAc(-50);
                    pc.sendPackets(new S_OwnCharAttrDef(pc));
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 11101));
                }
                else {
                    int time = pc.getSkillEffectTimeSec(8907);
                    pc.sendPackets(new S_SystemMessage("下層防禦強化卷軸 剩餘時間(秒):" + time));
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("只能在屍魂塔副本使用。"));
            }
            return;
        }
    }
}
