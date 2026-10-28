package com.lineage.server.timecontroller.pet;

import com.lineage.server.serverpackets.S_DoActionGFX;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.world.WorldDoll;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class DollTimer extends TimerTask {

    private static final Log _log;
    private static Random _random;
    private ScheduledFuture<?> _timer;

    static {
        _log = LogFactory.getLog(DollTimer.class);
        _random = new Random();
    }

    public void start() {
        int timeMillis = 30000; // 每 30 秒掃描一次
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long) timeMillis, (long) timeMillis);
    }

    @Override
    public void run() {
        try {
            Collection<L1DollInstance> allDoll = WorldDoll.get().all();
            if (allDoll.isEmpty()) {
                return;
            }

            for (L1DollInstance doll : allDoll) {
                try {
                    // ★ 重要：time = 0 → 永久存在（不倒數、不刪除）
                    if (doll.get_time() == 0) {
                        if (!doll.isDead()) {
                            this.checkAction(doll); // 照常做待機動作
                        }
                        continue;
                    }

                    int time = doll.get_time() - 30; // 每回合扣 30 秒
                    if (time <= 0) {
                        outDoll(doll);
                    } else {
                        doll.set_time(time);
                        if (!doll.isDead()) {
                            this.checkAction(doll);
                        }
                    }

                    Thread.sleep(50L); // 稍作間隔，避免一次送太多封包
                } catch (Exception singleEx) {
                    _log.error("處理單一娃娃時發生錯誤", singleEx);
                }
            }
        } catch (Exception e) {
            _log.error("魔法娃娃處理時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            DollTimer dollTimer = new DollTimer();
            dollTimer.start();
        }
    }

    private static void outDoll(L1DollInstance doll) {
        try {
            if (doll != null) {
                if (doll.destroyed()) {
                    return;
                }
                doll.deleteDoll();
            }
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    private void checkAction(L1DollInstance doll) {
        try {
            if (doll.getX() == doll.get_olX() && doll.getY() == doll.get_olY()) {
                int actionCode = _random.nextBoolean() ? 66 : 67;
                doll.broadcastPacketAll(new S_DoActionGFX(doll.getId(), actionCode));
            }
            doll.set_olX(doll.getX());
            doll.set_olY(doll.getY());
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }
}
