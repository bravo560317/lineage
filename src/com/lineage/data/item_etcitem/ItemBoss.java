package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.lineage.server.datatables.lock.SpawnBossReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

/**
 * BOSS重生查詢道具 (一次顯示前200隻)
 */
public class ItemBoss extends ItemExecutor {

    private static ItemBoss _instance;

    public static ItemBoss get() {
        if (_instance == null) {
            _instance = new ItemBoss();
        }
		
        return _instance;
    }
public void showBossList(L1PcInstance pc) {
    List<Integer> bossList = SpawnBossReading.get().bossreid();
    String[] info = new String[200];
    int num = 0;

    for (int i = 0; i < bossList.size() && num < 200; i++) {
        int bossId = bossList.get(i);
        String name = SpawnBossReading.get().getTemplate(bossId).getName();

        long nextTime = 0L;
        if (SpawnBossReading.get().getTemplate(bossId).get_nextSpawnTime() != null) {
            nextTime = SpawnBossReading.get().getTemplate(bossId).get_nextSpawnTime().getTimeInMillis();
        }

        if (nextTime == 0L || nextTime < System.currentTimeMillis()) {
            info[num] = "【存在】" + name;
        } else {
            Date d = new Date(nextTime);
            String time = new SimpleDateFormat("HH:mm").format(d);
            info[num] = "【" + time + "】" + name;
        }
        num++;
    }

    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bosslist", info));
}
@Override
public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
    showBossList(pc);
}
}
