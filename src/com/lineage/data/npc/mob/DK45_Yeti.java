package com.lineage.data.npc.mob;

import com.lineage.data.quest.DragonKnightLv45_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DK45_Yeti extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(DK45_Yeti.class);
        DK45_Yeti._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new DK45_Yeti();
    }
    
    @Override
    public int type() {
        return 8;
    }
    
    @Override
    public L1PcInstance death(L1Character lastAttacker, L1NpcInstance npc) {
        try {
            L1PcInstance pc = CheckUtil.checkAtkPc(lastAttacker);
            if (pc != null && pc.getQuest().isStart(DragonKnightLv45_1.QUEST.get_id())) {
                switch (pc.getQuest().get_step(DragonKnightLv45_1.QUEST.get_id())) {
                    case 3: {
                        DK45_Yeti._random.nextInt(100);
                        break;
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DK45_Yeti._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
