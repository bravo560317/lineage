package com.lineage.data.npc.mob;

import com.lineage.data.quest.ElfLv50_1;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class E50_GiantAntSoldier extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(E50_GiantAntSoldier.class);
        E50_GiantAntSoldier._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new E50_GiantAntSoldier();
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
                switch (pc.getMapId()) {
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                    case 51: {
                        if (pc.getQuest().isEnd(ElfLv50_1.QUEST.get_id())) {
                            return pc;
                        }
                        else if (pc.getQuest().isStart(ElfLv50_1.QUEST.get_id())) {
                            if (pc.getInventory().checkItem(49162)) {
                                return pc;
                            }
                            else {
                                switch (pc.getQuest().get_step(ElfLv50_1.QUEST.get_id())) {
                                    case 1: {
                                        E50_GiantAntSoldier._random.nextInt(100);
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            return pc;
        }
        catch (Exception e) {
            E50_GiantAntSoldier._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
