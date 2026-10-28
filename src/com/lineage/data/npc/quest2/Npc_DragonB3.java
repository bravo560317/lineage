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

public class Npc_DragonB3 extends NpcExecutor
{
    private static final Log _log;
    public static final Map<Integer, Npc_DragonB3.checkDragonTimer3> _timer;
    
    static {
        _log = LogFactory.getLog(Npc_DragonB3.class);
        _timer = new HashMap();
    }
    
    private Npc_DragonB3() {
    }
    
    public static NpcExecutor get() {
        return new Npc_DragonB3();
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
                // 1. 檢查是否已經在召喚流程中
                if (Npc_DragonB3._timer.get((Integer)pc.get_showId()) == null) {

                    int normalMonsterCount = 0; // 普通小怪數量
                    boolean isBossExists = false;     // Boss 是否已存在

                    // 2. 遍歷副本內所有 NPC
                    if (!quest.npcList().isEmpty()) {
                        for (L1NpcInstance find_npc : quest.npcList()) {
                            // 檢查是否為林德拜爾系列的 Boss ID (97204 - 97209)
                            if (find_npc != null && (find_npc.getNpcId() >= 97204 && find_npc.getNpcId() <= 97209)) {
                                isBossExists = true;
                                break;
                            }
                            
                            // 檢查是否有活著的普通怪物 (排除掉 Boss ID 以外的 L1MonsterInstance)
                            if (find_npc instanceof L1MonsterInstance && !find_npc.isDead() && !(find_npc.getNpcId() >= 97204 && find_npc.getNpcId() <= 97209)) {
                                normalMonsterCount++;
                            }
                        }
                    }

                    // 3. 判斷邏輯
                    if (isBossExists) {
                        // Boss 已經在場，直接傳送
                        L1Location loc = new L1Location(32844, 32887, (int)npc.getMapId()).randomLocation(5, false);
                        L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                    } 
                    else if (normalMonsterCount >= 10) {
                        // 還有小怪沒清完，發送系統提示
                        pc.sendPackets(new S_SystemMessage("必須消滅區域內的所有怪物，才能挑戰林德拜爾。"));
                    } 
                    else {
                        // 小怪已清空且 Boss 不存在 -> 啟動召喚倒數
                        Npc_DragonB3.checkDragonTimer3 timer = new Npc_DragonB3.checkDragonTimer3((int)npc.getMapId(), quest);
                        timer.begin();
                        Npc_DragonB3._timer.put(pc.get_showId(), timer);
                        
                        // 傳送至準備區域
                        L1Location loc = new L1Location(32844, 32887, (int)npc.getMapId()).randomLocation(5, false);
                        L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                    }
                } else {
                    // 計時器已在運作，直接傳送
                    L1Location loc = new L1Location(32844, 32887, (int)npc.getMapId()).randomLocation(5, false);
                    L1Teleport.teleport(pc, loc.getX(), loc.getY(), npc.getMapId(), 4, true);
                }
            }
        }
        catch (Exception e) {
            Npc_DragonB3._log.error(e.getLocalizedMessage(), e);
        }
    }
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
    }
    
    private class checkDragonTimer3 extends TimerTask
    {
        private final int mapId;
        private final L1QuestUser quest;
        
        public checkDragonTimer3(int mapId, L1QuestUser quest) {
            this.mapId = mapId;
            this.quest = quest;
        }
        
        @Override
        public void run() {
            this.cancel();
            try {
                this.sendServerMessage(1755);
                Thread.sleep(3000L);
                this.sendServerMessage(1756);
                Thread.sleep(4000L);
                this.sendServerMessage(1757);
                Thread.sleep(4000L);
                this.sendServerMessage(1758);
                L1Location loc = new L1Location(32846, 32877, this.mapId).randomLocation(5, true);
                L1SpawnUtil.spawn(97204, loc, new Random().nextInt(8), this.quest.get_id());
            }
            catch (Exception ex) {}
            finally {
                Npc_DragonB3._timer.remove((Integer)this.quest.get_id());
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
