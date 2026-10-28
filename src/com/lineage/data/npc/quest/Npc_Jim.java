package com.lineage.data.npc.quest;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.KnightLv15_1;
import com.lineage.data.quest.KnightLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Jim extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Jim.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Jim();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.getTempCharGfx() != 2374) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim1"));
                return;
            }
            else if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
            else if (pc.isKnight()) {
                if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jimcg"));
                    return;
                }
                else if (pc.getInventory().checkItem(40529)) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jimcg"));
                    return;
                }
                else if (!pc.getQuest().isStart(KnightLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
                }
                else {
                    if (pc.getQuest().get_step(KnightLv30_1.QUEST.get_id()) < 2) {
                        pc.getQuest().set_step(KnightLv30_1.QUEST.get_id(), 2);
                    }
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim2"));
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jim4"));
            }
        }
        catch (Exception e) {
            Npc_Jim._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isKnight()) {
            if (!pc.getQuest().isEnd(KnightLv15_1.QUEST.get_id())) {
                return;
            }
            else if (pc.getQuest().isEnd(KnightLv30_1.QUEST.get_id())) {
                return;
            }
            else if (!pc.getQuest().isStart(KnightLv30_1.QUEST.get_id())) {
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request letter of gratitude")) {
                if (CreateNewItem.checkNewItem(pc, new int[] { 40607 }, new int[] { 1 }) < 1L) {
                    isCloseList = true;
                }
                else {
                    CreateNewItem.createNewItem(pc, new int[] { 40607 }, new int[] { 1 }, new int[] { 40529 }, 1L, new int[] { 1 });
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jimcg"));
                }
            }
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
