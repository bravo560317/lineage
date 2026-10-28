package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Boss_TrackingTechnique extends ItemExecutor {

    private Boss_TrackingTechnique() {
    }

    public static ItemExecutor get() {
        return new Boss_TrackingTechnique();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {

        pc.sendPackets(new S_SystemMessage("正在找尋目標中......"));

        L1MonsterInstance nearestBoss = null;
        double minDistance = Double.MAX_VALUE;

        // 取得全部怪物
        for (L1MonsterInstance boss : WorldMob.get().getAllMonster()) {

            // 同地圖、未死亡、且為 BOSS
            if (boss.getMapId() == pc.getMapId() &&
                !boss.isDead() &&
                boss.getNpcTemplate().is_boss()) {

                // 計算距離（使用歐式距離）
                double dx = boss.getX() - pc.getX();
                double dy = boss.getY() - pc.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                // 找到更近的 BOSS
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestBoss = boss;
                }
            }
        }

        if (nearestBoss != null) {
            L1Teleport.teleport(pc,
                    nearestBoss.getX(),
                    nearestBoss.getY(),
                    nearestBoss.getMapId(),
                    pc.getHeading(),
                    true);

            pc.sendPackets(new S_SystemMessage("傳送至最近的 Boss：「" + nearestBoss.getName() + "」"));
        } else {
            pc.sendPackets(new S_SystemMessage("這張地圖已經沒有 Boss 的蹤跡..."));
        }

        pc.getInventory().removeItem(item, 1L);
    }
}
