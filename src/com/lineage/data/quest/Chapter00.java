package com.lineage.data.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.CharacterQuestReading;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.QuestExecutor;

public class Chapter00 extends QuestExecutor
{
    private static final Log _log;
    public static L1Quest QUEST;
    private static final String _html = "q_cha0_1";
    
    static {
        _log = LogFactory.getLog(Chapter00.class);
    }
    
    public static QuestExecutor get() {
        return new Chapter00();
    }
    
    @Override
    public void execute(L1Quest quest) {
        try {
            Chapter00.QUEST = quest;
        }
        catch (Exception e) {
            Chapter00._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void startQuest(L1PcInstance pc) {
        try {
            if (Chapter00.QUEST.check(pc)) {
                if (pc.getLevel() >= Chapter00.QUEST.get_questlevel()) {
                    if (pc.getQuest().get_step(Chapter00.QUEST.get_id()) != 1) {
                        pc.getQuest().set_step(Chapter00.QUEST.get_id(), 1);
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
            Chapter00._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void endQuest(L1PcInstance pc) {
        try {
            if (!pc.getQuest().isEnd(Chapter00.QUEST.get_id())) {
                pc.getQuest().set_end(Chapter00.QUEST.get_id());
                CharacterQuestReading.get().delQuest(pc.getId(), Chapter00.QUEST.get_id());
                String questName = Chapter00.QUEST.get_questname();
                pc.sendPackets(new S_ServerMessage("\\fT" + questName + "任務完成！"));
            }
        }
        catch (Exception e) {
            Chapter00._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void showQuest(L1PcInstance pc) {
        try {
            if ("q_cha0_1" != null) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "q_cha0_1"));
            }
        }
        catch (Exception e) {
            Chapter00._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void stopQuest(L1PcInstance pc) {
    }
}
