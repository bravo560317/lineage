package com.lineage.data.npc.mob;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.DragonKnightLv30_1;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class DK30_OrcEmissaryA extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DK30_OrcEmissaryA.class);
    }
    
    public static NpcExecutor get() {
        return new DK30_OrcEmissaryA();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            boolean isTak = false;
            if (pc.getTempCharGfx() == 6984) {
                isTak = true;
            }
            if (!isTak) {
                return;
            }
            else if (!pc.isCrown() && !pc.isKnight() && !pc.isElf() && !pc.isWizard() && !pc.isDarkelf()) {
                if (pc.isDragonKnight()) {
                    if (pc.getQuest().isStart(DragonKnightLv30_1.QUEST.get_id())) {
                        npc.onTalkAction(pc);
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "spy_orc1"));
                    }
                }
                else {
                    pc.isIllusionist();
                }
            }
        }
        catch (Exception e) {
            DK30_OrcEmissaryA._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isDragonKnight()) {
            if (pc.getQuest().isStart(DragonKnightLv30_1.QUEST.get_id()) && cmd.equalsIgnoreCase("request flute of spy")) {
                L1ItemInstance item = pc.getInventory().checkItemX(49223, 1L);
                if (item != null) {
                    pc.getInventory().removeItem(item, 1L);
                    CreateNewItem.getQuestItem(pc, npc, 49222, 1L);
                }
            }
            isCloseList = true;
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
