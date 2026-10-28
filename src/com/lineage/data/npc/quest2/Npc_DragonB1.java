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

public class Npc_DragonB1 extends NpcExecutor
{
    private static final Log _log;
    public static final Map<Integer, Npc_DragonB1.checkDragonTimer1> _timer;
    
    static {
        _log = LogFactory.getLog(Npc_DragonB1.class);
        _timer = new HashMap();
    }
    
    private Npc_DragonB1() {
    }
    
    public static NpcExecutor get() {
        return new Npc_DragonB1();
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
                // 1. 檢查是否已經在召喚倒數中
                if (Npc_DragonB1._timer.get((Integer)pc.get_showId()) == null) {
                    
                    int normalMonsterCount = 0; // 普通小怪數量
                    boolean isBossExists = false;     // Boss 是否已存在

                    // 2. 遍歷副本內所有 NPC 進行檢查
                    if (!quest.npcList().isEmpty()) {
                        for (L1NpcInstance find_npc : quest.npcList()) {
                            // 如果是 Boss (71014, 71015, 71016)
                            if (find_npc != null && (find_npc.getNpcId() == 71014 || find_npc.getNpcId() == 71015 || find_npc.getNpcId() == 71016)) {
                                isBossExists = true;
                                break;
                            }
                            
                            // 如果是怪物且不是門、不是隱形 NPC、且目前還活著
                            if (find_npc instanceof L1MonsterInstance && !find_npc.isDead() && find_npc.getNpcId() != 71014 && find_npc.getNpcId() != 71015 && find_npc.getNpcId() != 71016) {
                                normalMonsterCount++;
                            }
                        }
                    }

                    // 3. 判斷邏輯
                    if (isBossExists) {
                        // Boss 已經在場，不需要重新召喚，直接傳送
                        L1Location loc = new L1Location(32795, 32662, (int)npc.getMapId()).randomLocation(5, false);
                        L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                    } 
                    else if (normalMonsterCount >= 10) {
                        // 還有小怪沒清完，發送系統訊息並攔截
                        pc.sendPackets(new S_SystemMessage("必須消滅區域內的所有怪物，才能挑戰安塔瑞斯。"));
                    } 
                    else {
                        // 小怪已清空且 Boss 不存在 -> 開始召喚流程
                        Npc_DragonB1.checkDragonTimer1 timer = new Npc_DragonB1.checkDragonTimer1((int)npc.getMapId(), quest);
                        timer.begin();
                        Npc_DragonB1._timer.put(pc.get_showId(), timer);
                        
                        // 執行傳送
                        L1Location loc = new L1Location(32795, 32662, (int)npc.getMapId()).randomLocation(5, false);
                        L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                    }
                } else {
                    // 已經在計時器跑動中，直接傳送
                    L1Location loc = new L1Location(32795, 32662, (int)npc.getMapId()).randomLocation(5, false);
                    L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                }
            }
        }
        catch (Exception e) {
            Npc_DragonB1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
    }
    
    private class checkDragonTimer1 extends TimerTask
    {
        private final int mapId;
        private final L1QuestUser quest;
        
        public checkDragonTimer1(int mapId, L1QuestUser quest) {
            this.mapId = mapId;
            this.quest = quest;
        }
        
        @Override
        public void run() {
            this.cancel();
            try {
                this.sendServerMessage(1570);
                Thread.sleep(5000L);
                this.sendServerMessage(1571);
                Thread.sleep(5000L);
                this.sendServerMessage(1572);
                Thread.sleep(5000L);
                L1Location loc = new L1Location(32786, 32689, this.mapId).randomLocation(5, true);
                L1SpawnUtil.spawn(71014, loc, new Random().nextInt(8), this.quest.get_id());
            }
            catch (Exception ex) {}
            finally {
                Npc_DragonB1._timer.remove((Integer)this.quest.get_id());
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
