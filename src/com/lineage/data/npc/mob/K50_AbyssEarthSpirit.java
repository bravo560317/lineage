package com.lineage.data.npc.mob;

import com.lineage.data.quest.KnightLv50_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class K50_AbyssEarthSpirit extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(K50_AbyssEarthSpirit.class);
        K50_AbyssEarthSpirit._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new K50_AbyssEarthSpirit();
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
                if (pc.getQuest().isEnd(KnightLv50_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(KnightLv50_1.QUEST.get_id())) {
                    switch (pc.getQuest().get_step(KnightLv50_1.QUEST.get_id())) {
                        case 3: {
                            K50_AbyssEarthSpirit._random.nextInt(100);
                            break;
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            K50_AbyssEarthSpirit._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
