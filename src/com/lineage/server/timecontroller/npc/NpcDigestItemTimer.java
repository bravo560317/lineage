package com.lineage.server.timecontroller.npc;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.WorldMob;
import com.lineage.server.thread.GeneralThreadPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

public class NpcDigestItemTimer extends TimerTask {

    private static final Log _log = LogFactory.getLog(NpcDigestItemTimer.class);

    private ScheduledFuture<?> _timer;

    public void start() {
        // 每5秒執行一次
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 5000L, 5000L);
    }

    @Override
    public void run() {
        try {
            Collection<L1MonsterInstance> allMob = WorldMob.get().all();

            if (allMob == null || allMob.isEmpty()) {
                return;
            }

            for (L1MonsterInstance mob : allMob) {

                if (mob == null) {
                    continue;
                }

                // 基本防呆
                if (mob.isDead() || mob.getMaxHp() <= 0) {
                    continue;
                }

                if (mob.getNpcTemplate() == null) {
                    continue;
                }

                if (mob.getNpcTemplate().get_digestitem() <= 0) {
                    continue;
                }

                if (mob.getDigestItemEmpty()) {
                    continue;
                }

                checkDelItem(mob);
            }

        } catch (Exception e) {
            // ❗不要重開Timer，避免爆線程
            _log.error("NpcDigestItemTimer error", e);
        }
    }

    private void checkDelItem(L1MonsterInstance mob) {
        try {

            if (mob == null || mob.isDead()) {
                return;
            }

            HashMap<L1ItemInstance, L1NpcInstance.DelItemTime> allItem = mob.getDigestItem();

            if (allItem == null || allItem.isEmpty()) {
                return;
            }

            if (mob.getInventory() == null) {
                return;
            }

            Iterator<L1ItemInstance> it = allItem.keySet().iterator();

            while (it.hasNext()) {

                L1ItemInstance key = it.next();

                // item 不存在
                if (key == null) {
                    it.remove();
                    continue;
                }

                L1NpcInstance.DelItemTime value = allItem.get(key);

                // 時間資料不存在
                if (value == null) {
                    it.remove();
                    continue;
                }

                // 倒數時間減少（每5秒跑一次）
                value._del_item_time -= 5;

                if (value._del_item_time <= 0) {

                    // 安全刪除物品
                    try {
                        if (mob.getInventory() != null) {
                            mob.getInventory().removeItem(key, key.getCount());
                        }
                    } catch (Exception e) {
                        _log.error("removeItem error", e);
                    }

                    // 從Map移除
                    it.remove();
                }
            }

            // 全部清空
            if (allItem.isEmpty()) {
                mob.getDigestItemClear();
            }

        } catch (Exception e) {
            _log.error("checkDelItem error", e);
        }
    }
}