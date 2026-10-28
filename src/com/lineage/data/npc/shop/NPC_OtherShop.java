package com.lineage.data.npc.shop;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class NPC_OtherShop extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(NPC_OtherShop.class);
        NPC_OtherShop._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new NPC_OtherShop();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            int htmlidr = NPC_OtherShop._random.nextInt(6);
            String htmlid = "yiwei_Shop" + htmlidr;
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), htmlid));
        }
        catch (Exception e) {
            NPC_OtherShop._log.error(e.getLocalizedMessage(), e);
        }
    }
}
