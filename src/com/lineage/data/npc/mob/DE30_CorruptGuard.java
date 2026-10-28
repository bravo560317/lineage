package com.lineage.data.npc.mob;

import com.lineage.data.quest.DarkElfLv30_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DE30_CorruptGuard extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(DE30_CorruptGuard.class);
        DE30_CorruptGuard._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new DE30_CorruptGuard();
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
                if (pc.getQuest().isEnd(DarkElfLv30_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().get_step(DarkElfLv30_1.QUEST.get_id()) == 1) {
                    if (pc.getInventory().checkItem(40554)) {
                        return pc;
                    }
                    else {
                        DE30_CorruptGuard._random.nextInt(100);
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DE30_CorruptGuard._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
