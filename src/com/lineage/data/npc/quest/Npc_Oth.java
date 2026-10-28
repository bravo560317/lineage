package com.lineage.data.npc.quest;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.data.quest.ElfLv15_2;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Oth extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Oth.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Oth();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
            else if (pc.isElf()) {
                if (pc.getQuest().isEnd(ElfLv15_2.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth5"));
                }
                else if (pc.getLevel() >= ElfLv15_2.QUEST.get_questlevel()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth1"));
                    QuestClass.get().startQuest(pc, ElfLv15_2.QUEST.get_id());
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth6"));
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "oth2"));
            }
        }
        catch (Exception e) {
            Npc_Oth._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isElf()) {
            if (pc.getQuest().isEnd(ElfLv15_2.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("request dex helmet of elven")) {
                this.getItem(pc, 20021);
            }
            else if (cmd.equalsIgnoreCase("request con helmet of elven")) {
                this.getItem(pc, 20039);
            }
        }
        else {
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void getItem(L1PcInstance pc, int getid) {
        if (CreateNewItem.checkNewItem(pc, new int[] { 40609, 40610, 40611, 40612 }, new int[] { 1, 1, 1, 1 }) < 1L) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
        else {
            CreateNewItem.createNewItem(pc, new int[] { 40609, 40610, 40611, 40612 }, new int[] { 1, 1, 1, 1 }, new int[] { getid }, 1L, new int[] { 1 });
            QuestClass.get().endQuest(pc, ElfLv15_2.QUEST.get_id());
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
