package com.lineage.data.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.QuestExecutor;

public class ElfLv45_2 extends QuestExecutor
{
    private static final Log _log;
    public static L1Quest QUEST;
    private static final String _html = "y_q_e45_2";
    public static final int _npcId = 91123;
    
    static {
        _log = LogFactory.getLog(ElfLv45_2.class);
    }
    
    public static QuestExecutor get() {
        return new ElfLv45_2();
    }
    
    @Override
    public void execute(L1Quest quest) {
        try {
            ElfLv45_2.QUEST = quest;
        }
        catch (Exception e) {
            ElfLv45_2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void startQuest(L1PcInstance pc) {
        try {
            if (ElfLv45_2.QUEST.check(pc)) {
                if (pc.getLevel() >= ElfLv45_2.QUEST.get_questlevel()) {
                    if (pc.getQuest().get_step(ElfLv45_2.QUEST.get_id()) != 1) {
                        pc.getQuest().set_step(ElfLv45_2.QUEST.get_id(), 1);
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_not1"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_not2"));
            }
        }
        catch (Exception e) {
            ElfLv45_2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void endQuest(L1PcInstance pc) {
        try {
            if (!pc.getQuest().isEnd(ElfLv45_2.QUEST.get_id())) {
                pc.getQuest().set_end(ElfLv45_2.QUEST.get_id());
                String questName = ElfLv45_2.QUEST.get_questname();
                pc.sendPackets(new S_ServerMessage("\\fT" + questName + "任務完成！"));
                if (ElfLv45_2.QUEST.is_del()) {
                    pc.sendPackets(new S_ServerMessage("\\fT請注意這個任務可以重複執行，需要重複任務，請在任務管理員中執行解除。"));
                }
                else {
                    new S_ServerMessage("\\fR請注意這個任務不能重複執行，無法在任務管理員中解除執行。");
                }
            }
        }
        catch (Exception e) {
            ElfLv45_2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void showQuest(L1PcInstance pc) {
        try {
            if ("y_q_e45_2" != null) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_e45_2"));
            }
        }
        catch (Exception e) {
            ElfLv45_2._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void stopQuest(L1PcInstance pc) {
        try {
            int[] delItemIds = { 41348, 41346, 41347, 41354, 41353, 41349, 41351, 41350 };
            int[] array;
            for (int length = (array = delItemIds).length, i = 0; i < length; ++i) {
                int delItemId = array[i];
                pc.getInventory().delQuestItem(delItemId);
            }
        }
        catch (Exception e) {
            ElfLv45_2._log.error(e.getLocalizedMessage(), e);
        }
    }
}
