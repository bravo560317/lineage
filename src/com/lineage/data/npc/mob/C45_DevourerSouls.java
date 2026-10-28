package com.lineage.data.npc.mob;

import com.lineage.data.quest.CrownLv45_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class C45_DevourerSouls extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(C45_DevourerSouls.class);
        C45_DevourerSouls._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new C45_DevourerSouls();
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
                if (pc.getQuest().isEnd(CrownLv45_1.QUEST.get_id())) {
                    return pc;
                }
                else if (pc.getQuest().isStart(CrownLv45_1.QUEST.get_id())) {
                    if (pc.getInventory().checkItem(41422)) {
                        return pc;
                    }
                    else {
                        C45_DevourerSouls._random.nextInt(100);
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            C45_DevourerSouls._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
