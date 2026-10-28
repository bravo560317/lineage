package com.lineage.data.npc.mob;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class MobSpawn extends NpcExecutor
{
    private static final Log _log;
    private int _npcid;
    
    static {
        _log = LogFactory.getLog(MobSpawn.class);
    }
    
    public static NpcExecutor get() {
        return new MobSpawn();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && this._npcid != 0) {
                L1Npc l1npc = NpcTable.get().getTemplate(this._npcid);
                if (l1npc == null) {
                    MobSpawn._log.error("召喚NPC編號: " + this._npcid + " 不存在!(mob.MobSpawn)");
                    return pc;
                }
                else {
                    L1NpcInstance newnpc = L1SpawnUtil.spawnT(this._npcid, npc.getX(), npc.getY(), npc.getMapId(), npc.getHeading(), 300);
                    newnpc.onNpcAI();
                    newnpc.startChat(0);
                }
            }
            return pc;
        }
        catch (Exception e) {
            MobSpawn._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._npcid = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
