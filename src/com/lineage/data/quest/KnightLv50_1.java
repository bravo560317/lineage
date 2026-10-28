package com.lineage.data.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.QuestExecutor;

public class KnightLv50_1 extends QuestExecutor
{
    private static final Log _log;
    public static L1Quest QUEST;
    private static final String _html = "y_q_k50_1";
    
    static {
        _log = LogFactory.getLog(KnightLv50_1.class);
    }
    
    public static QuestExecutor get() {
        return new KnightLv50_1();
    }
    
    @Override
    public void execute(L1Quest quest) {
        try {
            KnightLv50_1.QUEST = quest;
        }
        catch (Exception e) {
            KnightLv50_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void startQuest(L1PcInstance pc) {
        try {
            if (KnightLv50_1.QUEST.check(pc)) {
                if (pc.getLevel() >= KnightLv50_1.QUEST.get_questlevel()) {
                    if (pc.getQuest().get_step(KnightLv50_1.QUEST.get_id()) != 1) {
                        pc.getQuest().set_step(KnightLv50_1.QUEST.get_id(), 1);
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
            KnightLv50_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void endQuest(L1PcInstance pc) {
        try {
            if (!pc.getQuest().isEnd(KnightLv50_1.QUEST.get_id())) {
                pc.getQuest().set_end(KnightLv50_1.QUEST.get_id());
                String questName = KnightLv50_1.QUEST.get_questname();
                pc.sendPackets(new S_ServerMessage("\\fT" + questName + "任務完成！"));
                if (KnightLv50_1.QUEST.is_del()) {
                    pc.sendPackets(new S_ServerMessage("\\fT請注意這個任務可以重複執行，需要重複任務，請在任務管理員中執行解除。"));
                }
                else {
                    new S_ServerMessage("\\fR請注意這個任務不能重複執行，無法在任務管理員中解除執行。");
                }
            }
        }
        catch (Exception e) {
            KnightLv50_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void showQuest(L1PcInstance pc) {
        try {
            if ("y_q_k50_1" != null) {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_k50_1"));
            }
        }
        catch (Exception e) {
            KnightLv50_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void stopQuest(L1PcInstance pc) {
    }
}
