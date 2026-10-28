package com.lineage.data.npc.mob;

import com.lineage.data.quest.KnightLv30_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class K30_ArachnevilElder extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(K30_ArachnevilElder.class);
        K30_ArachnevilElder._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new K30_ArachnevilElder();
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
                if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(KnightLv30_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(40590)) {
                        return pc;
                    }
                    else {
                        K30_ArachnevilElder._random.nextInt(100);
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            K30_ArachnevilElder._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
