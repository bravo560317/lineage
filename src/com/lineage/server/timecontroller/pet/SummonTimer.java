package com.lineage.server.timecontroller.pet;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.world.WorldSummons;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class SummonTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(SummonTimer.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1SummonInstance> allPet = WorldSummons.get().all();
            if (allPet.isEmpty()) {
                return;
            }
            else {
                for (L1SummonInstance summon : allPet) {
                    int time = summon.get_time() - 60;
                    if (time <= 0) {
                        outSummon(summon);
                    }
                    else {
                        summon.set_time(time);
                    }
                    Thread.sleep(50L);
                }
            }
        }
        catch (Exception e) {
            SummonTimer._log.error("召喚獸處理時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            SummonTimer summon_Timer = new SummonTimer();
            summon_Timer.start();
        }
    }
    
    private static void outSummon(L1SummonInstance summon) {
        try {
            if (summon != null) {
                if (summon.destroyed()) {
                    return;
                }
                else {
                    summon.Death(null);
                }
            }
        }
        catch (Exception e) {
            SummonTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void Summon(L1PcInstance pc) {
        int[] summons = { 81210, 81213, 81216, 81219, 81222, 81225, 81228 };
        int summonid = 0;
        int summoncost = 8;
        int levelRange = 32;
        int i = 0;
        while (i < summons.length) {
            if (i == summons.length - 1) {
                summonid = summons[i];
                break;
            }
            else {
                levelRange += 4;
                ++i;
            }
        }
        int petcost = 0;
        Object[] petlist = pc.getPetList().values().toArray();
        Object[] array;
        for (int length = (array = petlist).length, k = 0; k < length; ++k) {
            Object pet = array[k];
            petcost += ((L1NpcInstance)pet).getPetcost();
        }
        int pcCha = pc.getCha();
        if (pcCha > 34) {
            pcCha = 34;
        }
        int charisma = pcCha + 6 - petcost;
        int summoncount = charisma / summoncost;
        L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
        for (int j = 0; j < summoncount; ++j) {
            L1SummonInstance summon1 = new L1SummonInstance(npcTemp, pc);
            summon1.setPetcost(summoncost);
        }
        pc.setSummonMonster(false);
    }
}
