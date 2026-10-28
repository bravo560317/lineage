package com.lineage.server.timecontroller.quest;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.world.World;
import java.util.HashMap;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.data.quest.WizardLv30_1;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class W30_Timer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private int _qid;
    
    static {
        _log = LogFactory.getLog(W30_Timer.class);
    }
    
    public W30_Timer() {
        this._qid = -1;
    }
    
    public void start() {
        this._qid = WizardLv30_1.QUEST.get_id();
        int timeMillis = 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            HashMap<Integer, L1Object> mapList = new HashMap();
            mapList.putAll(World.get().getVisibleObjects(201));
            ArrayList<L1QuestUser> questList = WorldQuest.get().getQuests(this._qid);
            Object[] array;
            for (int length = (array = questList.toArray()).length, j = 0; j < length; ++j) {
                Object object = array[j];
                L1QuestUser quest = (L1QuestUser)object;
                int i = 0;
                for (L1Object obj : mapList.values()) {
                    if (obj instanceof L1MerchantInstance) {
                        continue;
                    }
                    else if (obj.get_showId() == quest.get_id()) {
                        if (obj.getX() == 32867 && obj.getY() == 32912) {
                            ++i;
                        }
                        if (obj.getX() == 32867 && obj.getY() == 32927) {
                            ++i;
                        }
                        if (obj.getX() == 32860 && obj.getY() == 32920) {
                            ++i;
                        }
                        if (obj.getX() == 32875 && obj.getY() == 32920) {
                            ++i;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
                if (i >= 4) {
                    for (L1NpcInstance npc : quest.npcList()) {
                        if (npc instanceof L1DoorInstance) {
                            L1DoorInstance door = (L1DoorInstance)npc;
                            if (door.getDoorId() == 10003) {
                                door.open();
                                Thread.sleep(50L);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    for (L1PcInstance pc : quest.pcList()) {
                        if (pc.getX() == 32868 && pc.getY() == 32919) {
                            L1Teleport.teleport(pc, 32929, 32798, (short)201, 5, true);
                            Thread.sleep(50L);
                        }
                    }
                }
                else {
                    for (L1NpcInstance npc : quest.npcList()) {
                        if (npc instanceof L1DoorInstance) {
                            L1DoorInstance door = (L1DoorInstance)npc;
                            if (door.getDoorId() == 10003) {
                                door.close();
                                Thread.sleep(50L);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            mapList.clear();
            questList.clear();
        }
        catch (Exception e) {
            W30_Timer._log.error("不死族的叛徒 (法師30級以上官方任務)時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            W30_Timer w30Timer = new W30_Timer();
            w30Timer.start();
        }
    }
}
