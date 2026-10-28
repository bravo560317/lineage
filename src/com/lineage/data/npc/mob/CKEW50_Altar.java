package com.lineage.data.npc.mob;

import java.util.Iterator;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.CKEWLv50_1;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.HashMap;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class CKEW50_Altar extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CKEW50_Altar.class);
    }
    
    private CKEW50_Altar() {
    }
    
    public static NpcExecutor get() {
        return new CKEW50_Altar();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null) {
                HashMap<Integer, L1Object> mapList = new HashMap();
                mapList.putAll(World.get().getVisibleObjects(2000));
                for (L1Object tgobj : mapList.values()) {
                    if (tgobj instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)tgobj;
                        if (tgpc.get_showId() == pc.get_showId()) {
                            tgpc.getQuest().set_step(CKEWLv50_1.QUEST.get_id(), 3);
                            if (tgpc.getInventory().checkItem(49241)) {
                                continue;
                            }
                            else {
                                CreateNewItem.getQuestItem(tgpc, npc, 49241, 1L);
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                mapList.clear();
            }
            return pc;
        }
        catch (Exception e) {
            CKEW50_Altar._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
