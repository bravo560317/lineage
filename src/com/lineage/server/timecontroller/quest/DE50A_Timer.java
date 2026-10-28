package com.lineage.server.timecontroller.quest;

import com.lineage.server.model.L1Location;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.data.quest.DarkElfLv50_1;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class DE50A_Timer extends TimerTask
{
    private static final Log _log;
    private static Random _random;
    private ScheduledFuture<?> _timer;
    private int _qid;
    
    static {
        _log = LogFactory.getLog(DE50A_Timer.class);
        DE50A_Timer._random = new Random();
    }
    
    public DE50A_Timer() {
        this._qid = -1;
    }
    
    public void start() {
        this._qid = DarkElfLv50_1.QUEST.get_id();
        int timeMillis = 1500;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1500L, 1500L);
    }
    
    @Override
    public void run() {
        try {
            ArrayList<L1QuestUser> questList = WorldQuest.get().getQuests(this._qid);
            if (questList.isEmpty()) {
                return;
            }
            else {
                Object[] array;
                for (int length = (array = questList.toArray()).length, i = 0; i < length; ++i) {
                    Object object = array[i];
                    L1QuestUser quest = (L1QuestUser)object;
                    for (L1PcInstance tgpc : quest.pcList()) {
                        if (!tgpc.isDead() && DE50A_Timer._random.nextInt(100) < 10) {
                            quest.addNpc(spawn(tgpc));
                            Thread.sleep(50L);
                        }
                    }
                }
                questList.clear();
            }
        }
        catch (Exception e) {
            DE50A_Timer._log.error("尋找黑暗之星 (黑暗妖精50級以上官方任務)A時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            DE50A_Timer de50ATimer = new DE50A_Timer();
            de50ATimer.start();
        }
    }
    
    private static L1MonsterInstance spawn(L1PcInstance tgpc) {
        try {
            L1Location loc = tgpc.getLocation().randomLocation(4, false);
            tgpc.sendPackets(new S_EffectLocation(loc, 3992));
            L1MonsterInstance mob = null;
            if (DE50A_Timer._random.nextBoolean()) {
                mob = L1SpawnUtil.spawnX(45582, loc, tgpc.get_showId());
            }
            else {
                mob = L1SpawnUtil.spawnX(45587, loc, tgpc.get_showId());
            }
            mob.setExp(1L);
            return mob;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
