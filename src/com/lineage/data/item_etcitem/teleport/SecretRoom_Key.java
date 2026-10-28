package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.model.L1Teleport;
import com.lineage.data.quest.KnightLv30_1;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class SecretRoom_Key extends ItemExecutor
{
    public static ItemExecutor get() {
        return new SecretRoom_Key();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            short mapid = 813;
            if (pc.isKnight() && pc.getX() >= 32806 && pc.getX() <= 32814 && pc.getY() >= 32798 && pc.getY() <= 32807 && pc.getMapId() == 813) {
                if (pc.isKnight()) {
                    if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_ServerMessage(79));
                        return;
                    }
                    else if (!pc.getQuest().isStart(KnightLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_ServerMessage(79));
                    }
                    else {
                        L1Teleport.teleport(pc, 32727, 32789, (short)237, 5, false);
                        if (pc.isActived()) {
                            pc.setActived(false);
                            pc.killSkillEffectTimer(8853);
                            pc.sendPackets(new S_ServerMessage("掛機中請勿使用手動卷軸。"));
                            pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                            if (pc.getAutoX() > 0) {
                                pc.setAutoX(0);
                                pc.setAutoY(0);
                                pc.setAutoMap(0);
                            }
                        }
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
            }
            return;
        }
    }
}
