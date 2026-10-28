package com.lineage.data.npc.quest2;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.L1SpawnUtil;
import java.util.Random;
import java.util.TimerTask;
import java.util.Iterator;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Location;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_SystemMessage;

public class Npc_DragonB2 extends NpcExecutor
{
    private static final Log _log;
    public static final Map<Integer, Npc_DragonB2.checkDragonTimer2> _timer;
    
    static {
        _log = LogFactory.getLog(Npc_DragonB2.class);
        _timer = new HashMap();
    }
    
    private Npc_DragonB2() {
    }
    
    public static NpcExecutor get() {
        return new Npc_DragonB2();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
@Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            L1QuestUser quest = WorldQuest.get().get(pc.get_showId());
            if (quest != null) {
                // 1. 檢查是否已經在計時器流程中
                if (Npc_DragonB2._timer.get((Integer)pc.get_showId()) == null) {
                    
                    int normalMonsterCount = 0; // 普通小怪數量
                    boolean isBossExists = false;     // Boss 是否已存在

                    // 2. 遍歷副本內所有 NPC
                    if (!quest.npcList().isEmpty()) {
                        for (L1NpcInstance find_npc : quest.npcList()) {
                            // 檢查是否為法利昂系列的 Boss ID
                            if (find_npc != null && (find_npc.getNpcId() == 71026 || find_npc.getNpcId() == 71027 || find_npc.getNpcId() == 71028)) {
                                isBossExists = true;
                                break;
                            }
                            
                            // 檢查是否還有活著的普通怪物 (排除掉門或其他非戰鬥 NPC)
                            if (find_npc instanceof L1MonsterInstance && !find_npc.isDead() && find_npc.getNpcId() != 71026 && find_npc.getNpcId() != 71027 && find_npc.getNpcId() != 71028) {
                                normalMonsterCount++;
                            }
                        }
                    }

                    // 3. 判斷邏輯
                    if (isBossExists) {
                        // Boss 已經在場，直接傳送
                        L1Location loc = new L1Location(32990, 32842, (int)npc.getMapId()).randomLocation(5, false);
                        L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                    } 
                    else if (normalMonsterCount >= 10) {
                        // 還有小怪沒清完
                        pc.sendPackets(new S_SystemMessage("必須消滅區域內的所有怪物，才能挑戰法利昂。"));
                    } 
                    else {
                        // 條件達成：沒有 Boss 且小怪清空 -> 啟動召喚倒數
                        Npc_DragonB2.checkDragonTimer2 timer = new Npc_DragonB2.checkDragonTimer2((int)npc.getMapId(), quest);
                        timer.begin();
                        Npc_DragonB2._timer.put(pc.get_showId(), timer);
                        
                        // 執行傳送
                        L1Location loc = new L1Location(32990, 32842, (int)npc.getMapId()).randomLocation(5, false);
                        L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                    }
                } else {
                    // 計時器已在執行，直接傳送
                    L1Location loc = new L1Location(32990, 32842, (int)npc.getMapId()).randomLocation(5, false);
                    L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                }
            }
        }
        catch (Exception e) {
            Npc_DragonB2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
    }
    
    private class checkDragonTimer2 extends TimerTask
    {
        private final int mapId;
        private final L1QuestUser quest;
        
        public checkDragonTimer2(int mapId, L1QuestUser quest) {
            this.mapId = mapId;
            this.quest = quest;
        }
        
        @Override
        public void run() {
            this.cancel();
            try {
                this.sendServerMessage(1657);
                Thread.sleep(3000L);
                this.sendServerMessage(1658);
                Thread.sleep(4000L);
                this.sendServerMessage(1659);
                Thread.sleep(4000L);
                this.sendServerMessage(1660);
                L1Location loc = new L1Location(32958, 32835, this.mapId).randomLocation(5, true);
                L1SpawnUtil.spawn(71026, loc, new Random().nextInt(8), this.quest.get_id());
            }
            catch (Exception ex) {}
            finally {
                Npc_DragonB2._timer.remove((Integer)this.quest.get_id());
            }
        }
        
        private final void sendServerMessage(int msgid) {
            if (!this.quest.pcList().isEmpty()) {
                for (L1PcInstance pc : this.quest.pcList()) {
                    pc.sendPackets(new S_ServerMessage(msgid));
                }
            }
        }
        
        public final void begin() {
            GeneralThreadPool.get().schedule(this, 10000L);
        }
    }
}
