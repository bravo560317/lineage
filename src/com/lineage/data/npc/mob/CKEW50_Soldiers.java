package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class CKEW50_Soldiers extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(CKEW50_Soldiers.class);
        CKEW50_Soldiers._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new CKEW50_Soldiers();
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
                if (pc.hasSkillEffect(4007)) {
                    return pc;
                }
                else if (pc.getInventory().checkItem(49165)) {
                    return pc;
                }
                else if (CKEW50_Soldiers._random.nextInt(100) < 40) {
                    CreateNewItem.getQuestItem(pc, npc, 49165, 1L);
                }
            }
            return pc;
        }
        catch (Exception e) {
            CKEW50_Soldiers._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
