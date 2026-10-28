package com.lineage.data.quest;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.timecontroller.quest.AD80_3_Timer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Quest;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.QuestExecutor;

public class ADLv80_3 extends QuestExecutor
{
    private static final Log _log;
    public static L1Quest QUEST;
    public static final int MAPID = 1017;
    
    static {
        _log = LogFactory.getLog(ADLv80_3.class);
    }
    
    public static QuestExecutor get() {
        return new ADLv80_3();
    }
    
    @Override
    public void execute(L1Quest quest) {
        try {
            ADLv80_3.QUEST = quest;
            AD80_3_Timer ad80_3Timer = new AD80_3_Timer();
            ad80_3Timer.start();
        }
        catch (Exception e) {
            ADLv80_3._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void startQuest(L1PcInstance pc) {
        try {
            if (ADLv80_3.QUEST.check(pc)) {
                if (pc.getLevel() >= ADLv80_3.QUEST.get_questlevel()) {
                    if (pc.getQuest().get_step(ADLv80_3.QUEST.get_id()) != 1) {
                        pc.getQuest().set_step(ADLv80_3.QUEST.get_id(), 1);
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
            ADLv80_3._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void endQuest(L1PcInstance pc) {
        try {
            if (!pc.getQuest().isEnd(ADLv80_3.QUEST.get_id())) {
                pc.getQuest().set_end(ADLv80_3.QUEST.get_id());
            }
        }
        catch (Exception e) {
            ADLv80_3._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void showQuest(L1PcInstance pc) {
    }
    
    @Override
    public void stopQuest(L1PcInstance pc) {
    }
}
