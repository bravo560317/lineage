package com.lineage.data.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.QuestExecutor;

public class EWLv40_1 extends QuestExecutor
{
    private static final Log _log;
    public static L1Quest QUEST;
    private static final String _html = "y_q_ew40_1";
    public static final int _roiid = 81209;
    public static final int _roi2id = 70957;
    public static final int _baschid = 70964;
    
    static {
        _log = LogFactory.getLog(EWLv40_1.class);
    }
    
    public static QuestExecutor get() {
        return new EWLv40_1();
    }
    
    @Override
    public void execute(L1Quest quest) {
        try {
            EWLv40_1.QUEST = quest;
        }
        catch (Exception e) {
            EWLv40_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void startQuest(L1PcInstance pc) {
        try {
            if (EWLv40_1.QUEST.check(pc)) {
                if (pc.getLevel() >= EWLv40_1.QUEST.get_questlevel()) {
                    if (pc.getQuest().get_step(EWLv40_1.QUEST.get_id()) != 1) {
                        pc.getQuest().set_step(EWLv40_1.QUEST.get_id(), 1);
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
            EWLv40_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void endQuest(L1PcInstance pc) {
        try {
            if (!pc.getQuest().isEnd(EWLv40_1.QUEST.get_id())) {
                pc.getQuest().set_end(EWLv40_1.QUEST.get_id());
                String questName = EWLv40_1.QUEST.get_questname();
                pc.sendPackets(new S_ServerMessage("\\fT" + questName + "任務完成！"));
                if (EWLv40_1.QUEST.is_del()) {
                    pc.sendPackets(new S_ServerMessage("\\fT請注意這個任務可以重複執行，需要重複任務，請在任務管理員中執行解除。"));
                }
                else {
                    new S_ServerMessage("\\fR請注意這個任務不能重複執行，無法在任務管理員中解除執行。");
                }
            }
        }
        catch (Exception e) {
            EWLv40_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void showQuest(L1PcInstance pc) {
        try {
            if ("y_q_ew40_1" != null) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_ew40_1"));
            }
        }
        catch (Exception e) {
            EWLv40_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void stopQuest(L1PcInstance pc) {
    }
}
