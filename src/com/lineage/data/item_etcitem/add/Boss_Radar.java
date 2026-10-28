package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Boss_Radar extends ItemExecutor
{
    private Boss_Radar() {
    }
    
    public static ItemExecutor get() {
        return new Boss_Radar();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPackets(new S_SystemMessage("使用野狼的嗅覺，找尋目標......"));
        L1MonsterInstance find_npc = null;
        for (L1MonsterInstance boss : WorldMob.get().getAllMonster()) {
            if (boss.getNpcTemplate().is_boss() && boss.getMapId() == pc.getMapId() && !boss.isDead()) {
                find_npc = boss;
                if (find_npc != null) {
                    pc.sendPackets(new S_SystemMessage("從 " + find_npc.getX() + "," + find_npc.getY() + " 處，傳來 " + find_npc.getName() + " 的氣味~~~"));
                }
                else {
                    pc.sendPackets(new S_SystemMessage("這裡已經沒有BOSS的氣味，好像晚了一步..."));
                    break;
                }
            }
        }
    }
}
