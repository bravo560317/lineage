package com.lineage.server.timecontroller.npc;

import com.lineage.data.npc.quest2.Npc_DragonB3;
import com.lineage.data.npc.quest2.Npc_DragonB2;
import com.lineage.data.npc.quest2.Npc_DragonB1;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcDeleteTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcDeleteTimer.class);
    }
    
    public void start() {
        int timeMillis = 2000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 2000L, 2000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1NpcInstance> allNpc = WorldNpc.get().all();
            if (allNpc.isEmpty()) {
                return;
            }
            else {
                for (L1NpcInstance npc : allNpc) {
                    if (!npc.is_spawnTime()) {
                        continue;
                    }
                    else {
                        int time = npc.get_spawnTime();
                        time -= 2;
                        if (time > 0) {
                            npc.set_spawnTime(time);
                        }
                        else {
                            remove(npc);
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            NpcDeleteTimer._log.error("NPC存在時間時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcDeleteTimer npcDeleteTimer = new NpcDeleteTimer();
            npcDeleteTimer.start();
        }
    }
    
    private static void remove(L1NpcInstance tgnpc) {
        try {
            boolean isRemove = false;
            if (tgnpc instanceof L1MonsterInstance) {
                if (tgnpc.getNpcId() == 80034) {
                    tgnpc.outParty(tgnpc);
                }
                isRemove = true;
            }
            if (tgnpc instanceof L1IllusoryInstance) {
                isRemove = true;
            }
            if (isRemove) {
                tgnpc.setCurrentHpDirect(0);
                tgnpc.setDead(true);
                tgnpc.getMap().setPassable(tgnpc.getLocation(), true);
                tgnpc.setExp(0L);
                tgnpc.setKarma(0);
                tgnpc.allTargetClear();
            }
            if (tgnpc.getNpcId() == 70932) {
                tgnpc.broadcastPacketAll(new S_DoActionGFX(tgnpc.getId(), 8));
                if (Npc_DragonB1._timer.containsKey((Integer)tgnpc.get_quest_id())) {
                    Npc_DragonB1._timer.remove((Integer)tgnpc.get_quest_id());
                }
            }
            else if (tgnpc.getNpcId() == 70937) {
                tgnpc.broadcastPacketAll(new S_DoActionGFX(tgnpc.getId(), 8));
                if (Npc_DragonB2._timer.containsKey((Integer)tgnpc.get_quest_id())) {
                    Npc_DragonB2._timer.remove((Integer)tgnpc.get_quest_id());
                }
            }
            else if (tgnpc.getNpcId() == 70934) {
                tgnpc.broadcastPacketAll(new S_DoActionGFX(tgnpc.getId(), 8));
                if (Npc_DragonB3._timer.containsKey((Integer)tgnpc.get_quest_id())) {
                    Npc_DragonB3._timer.remove((Integer)tgnpc.get_quest_id());
                }
            }
            else if (tgnpc.getNpcId() == 70933) {
                tgnpc.broadcastPacketAll(new S_DoActionGFX(tgnpc.getId(), 8));
            }
            tgnpc.deleteMe();
        }
        catch (Exception e) {
            NpcDeleteTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
