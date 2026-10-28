package com.lineage.data.npc.mob;

import com.lineage.data.quest.DarkElfLv15_2;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DE15_OrcElder extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(DE15_OrcElder.class);
        DE15_OrcElder._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new DE15_OrcElder();
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
                if (pc.getQuest().isEnd(DarkElfLv15_2.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(DarkElfLv15_2.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(40585)) {
                        return pc;
                    }
                    else {
                        DE15_OrcElder._random.nextInt(100);
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            DE15_OrcElder._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
