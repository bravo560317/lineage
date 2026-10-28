package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.ALv15_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class A_RovaOrc extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(A_RovaOrc.class);
        A_RovaOrc._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new A_RovaOrc();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.getQuest().isStart(ALv15_1.QUEST.get_id()) && A_RovaOrc._random.nextInt(100) < 30) {
                CreateNewItem.getQuestItem(pc, npc, 40134, 1L);
            }
            return pc;
        }
        catch (Exception e) {
            A_RovaOrc._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
