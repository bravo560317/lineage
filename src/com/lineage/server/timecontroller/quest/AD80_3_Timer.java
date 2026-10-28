package com.lineage.server.timecontroller.quest;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.data.quest.ADLv80_3;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Location;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public final class AD80_3_Timer extends TimerTask
{
    private static final Log _log;
    private static final Random _random;
    private ScheduledFuture<?> _timer;
    private int _qid;
    private int _counter;
    private L1Location _loc;
    
    static {
        _log = LogFactory.getLog(AD80_3_Timer.class);
        _random = new Random();
    }
    
    public AD80_3_Timer() {
        this._qid = -1;
    }
    
    public void start() {
        this._qid = ADLv80_3.QUEST.get_id();
        this._loc = new L1Location(32848, 32877, 1017);
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 2000L, 2000L);
    }
    
    @Override
    public void run() {
        try {
            ArrayList<?> questList = WorldQuest.get().getQuests(this._qid);
            if (questList.isEmpty()) {
                return;
            }
            else {
                Object[] array;
                for (int length = (array = questList.toArray()).length, i = 0; i < length; ++i) {
                    Object object = array[i];
                    L1QuestUser quest = (L1QuestUser)object;
                    if (quest == null) {
                        continue;
                    }
                    for (L1NpcInstance npc : quest.npcList()) {
                        if (!npc.isDead()) {
                            L1Location loc = npc.getLocation().randomLocation(12, true);
                            if (npc.getNpcId() >= 97204 && npc.getNpcId() <= 97209 && ++this._counter >= 45) {
                                this._counter = 0;
                                boolean check_spawn = false;
                                for (L1NpcInstance check_npc : quest.npcList()) {
                                    if (check_npc.getNpcId() == 97210) {
                                        check_spawn = true;
                                        break;
                                    }
                                }
                                if (!check_spawn) {
                                    loc = this._loc.randomLocation(12, true);
                                    L1SpawnUtil.spawn(97210, loc, 0, quest.get_id());
                                }
                            }
                            if (npc.getNpcId() >= 97204 && npc.getNpcId() <= 97206 && AD80_3_Timer._random.nextInt(100) < 5) {
                                for (L1PcInstance tgpc : quest.pcList()) {
                                    switch (AD80_3_Timer._random.nextInt(3)) {
                                        case 0: {
                                            npc.getNpcTemplate().set_weakAttr(4);
                                            tgpc.sendPackets(new S_PacketBoxGree(6));
                                            continue;
                                        }
                                        case 1: {
                                            npc.getNpcTemplate().set_weakAttr(1);
                                            tgpc.sendPackets(new S_PacketBoxGree(7));
                                            continue;
                                        }
                                        case 2: {
                                            npc.getNpcTemplate().set_weakAttr(8);
                                            tgpc.sendPackets(new S_PacketBoxGree(8));
                                            continue;
                                        }
                                        default: {
                                            continue;
                                        }
                                    }
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                questList.clear();
            }
        }
        catch (Exception e) {
            AD80_3_Timer._log.error("風龍副本 傷害計能施放 時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            AD80_3_Timer ad80_3Timer = new AD80_3_Timer();
            ad80_3Timer.start();
        }
    }
}
