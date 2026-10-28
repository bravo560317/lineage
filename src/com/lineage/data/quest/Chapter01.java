package com.lineage.data.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.QuestExecutor;

public class Chapter01 extends QuestExecutor
{
    private static final Log _log;
    public static L1Quest QUEST;
    public static final int MAPID = 9000;
    private static final String _html = "q_cha1_1";
    
    static {
        _log = LogFactory.getLog(Chapter01.class);
    }
    
    public static QuestExecutor get() {
        return new Chapter01();
    }
    
    @Override
    public void execute(L1Quest quest) {
        try {
            Chapter01.QUEST = quest;
        }
        catch (Exception e) {
            Chapter01._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void startQuest(L1PcInstance pc) {
        try {
            if (Chapter01.QUEST.check(pc)) {
                if (pc.getLevel() < Chapter01.QUEST.get_questlevel()) {
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_not1"));
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_not2"));
            }
        }
        catch (Exception e) {
            Chapter01._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void endQuest(L1PcInstance pc) {
        try {
            String questName = Chapter01.QUEST.get_questname();
            pc.sendPackets(new S_ServerMessage("\\fT" + questName + "任務結束！"));
        }
        catch (Exception e) {
            Chapter01._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void showQuest(L1PcInstance pc) {
        try {
            if ("q_cha1_1" != null) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "q_cha1_1"));
            }
        }
        catch (Exception e) {
            Chapter01._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void stopQuest(L1PcInstance pc) {
    }
}
