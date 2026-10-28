package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class ADLv80_Koma2 extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ADLv80_Koma2.class);
    }
    
    public static NpcExecutor get() {
        return new ADLv80_Koma2();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.getMapId() == 1005) {
                CreateNewItem.getQuestItem(pc, npc, 42511, 1L);
            }
            return pc;
        }
        catch (Exception e) {
            ADLv80_Koma2._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
