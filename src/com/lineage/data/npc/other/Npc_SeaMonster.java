package com.lineage.data.npc.other;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.data.cmd.NpcWorkMove;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.types.Point;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_SeaMonster extends NpcExecutor
{
    private static final Log _log;
    private static final Point[][] _point;
    
    static {
        _log = LogFactory.getLog(Npc_SeaMonster.class);
        _point = new Point[][] { { new Point(32787, 32807), new Point(32828, 32807) }, { new Point(32791, 32794), new Point(32791, 32828) }, { new Point(32776, 32821) }, { new Point(32772, 32823) }, { new Point(32791, 32807), new Point(32789, 32828) }, { new Point(32801, 32802), new Point(32802, 32801), new Point(32804, 32801), new Point(32805, 32802), new Point(32805, 32804), new Point(32809, 32808), new Point(32828, 32808) } };
    }
    
    public static NpcExecutor get() {
        return new Npc_SeaMonster();
    }
    
    @Override
    public int type() {
        return 16;
    }
    
    @Override
    public void work(L1NpcInstance npc) {
        Npc_SeaMonster.Work work = new Npc_SeaMonster.Work(npc);
        work.getStart();
    }
    
    private class Work implements Runnable
    {
        private L1NpcInstance _npc;
        private int _spr;
        private final int _type;
        private NpcWorkMove _npcMove;
        private final L1QuestUser quest;
        
        private Work(L1NpcInstance npc) {
            this._npc = npc;
            this._spr = SprTable.get().getMoveSpeed(npc.getTempCharGfx(), 0);
            this._npcMove = new NpcWorkMove(npc);
            this.quest = WorldQuest.get().get(npc.get_showId());
            this._type = npc.get_quest_id();
        }
        
        public void getStart() {
            GeneralThreadPool.get().schedule(this, 300L);
        }
        
        @Override
        public void run() {
            try {
                if (this.quest == null || this.quest.get_orimR() == null) {
                    return;
                }
                else {
                    int counter = 0;
                    Point point = Npc_SeaMonster._point[this._type][counter];
                    boolean isWork = true;
                    while (isWork) {
                        Thread.sleep((long)this._spr);
                        this._npcMove.actionStart(point);
                        if (this._npc.getLocation().isSamePoint(point)) {
                            if (counter + 1 >= Npc_SeaMonster._point[this._type].length) {
                                isWork = false;
                                this._npc.setreSpawn(false);
                                this._npc.deleteMe();
                            }
                            else {
                                point = Npc_SeaMonster._point[this._type][++counter];
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                Npc_SeaMonster._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
