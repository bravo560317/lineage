package com.lineage.data.npc.other;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.types.Point;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.data.cmd.NpcWorkMove;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Ship extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Ship.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Ship();
    }
    
    @Override
    public int type() {
        return 16;
    }
    
    @Override
    public void work(L1NpcInstance npc) {
        Npc_Ship.Work work = new Npc_Ship.Work(npc);
        work.getStart();
    }
    
    private class Work implements Runnable
    {
        private L1NpcInstance _npc;
        private int _spr;
        private NpcWorkMove _npcMove;
        private final L1QuestUser quest;
        private final Point[] _point;
        
        private Work(L1NpcInstance npc) {
            this._npc = npc;
            this._spr = SprTable.get().getMoveSpeed(npc.getTempCharGfx(), 0);
            this._npcMove = new NpcWorkMove(npc);
            this.quest = WorldQuest.get().get(npc.get_showId());
            this._point = new Point[] { new Point(this._npc.getX(), 32811), new Point(this._npc.getX(), 32813), new Point(this._npc.getX(), 32825) };
        }
        
        public void getStart() {
            GeneralThreadPool.get().schedule(this, 10L);
        }
        
        @Override
        public void run() {
            try {
                if (this.quest == null || this.quest.get_orimR() == null) {
                    return;
                }
                else {
                    int counter = 0;
                    Point point = this._point[counter];
                    boolean isWork = true;
                    while (isWork) {
                        Thread.sleep((long)this._spr);
                        this._npcMove.actionStart(point);
                        if (this._npc.getLocation().isSamePoint(point)) {
                            this.quest.get_orimR().shipReturnStep(counter);
                            if (counter == 0) {
                                Thread.sleep(2000L);
                                point = this._point[Math.min(++counter, this._point.length)];
                            }
                            else if (counter == 1) {
                                Thread.sleep(120000L);
                                this.quest.get_orimR().shipReturnStep(counter);
                                point = this._point[Math.min(++counter, this._point.length)];
                            }
                            else if (counter == 2) {
                                isWork = false;
                                this._npc.setreSpawn(false);
                                this._npc.deleteMe();
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                Npc_Ship._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
