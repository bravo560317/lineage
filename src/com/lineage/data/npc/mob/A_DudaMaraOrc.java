package com.lineage.data.npc.mob;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.ALv15_1;
import com.lineage.data.quest.ElfLv15_2;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class A_DudaMaraOrc extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(A_DudaMaraOrc.class);
        A_DudaMaraOrc._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new A_DudaMaraOrc();
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
                if (pc.getQuest().isStart(ElfLv15_2.QUEST.get_id())) {
                    A_DudaMaraOrc._random.nextInt(100);
                }
                if (pc.getQuest().isStart(ALv15_1.QUEST.get_id()) && A_DudaMaraOrc._random.nextInt(100) < 30) {
                    CreateNewItem.getQuestItem(pc, npc, 40133, 1L);
                }
            }
            return pc;
        }
        catch (Exception e) {
            A_DudaMaraOrc._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
