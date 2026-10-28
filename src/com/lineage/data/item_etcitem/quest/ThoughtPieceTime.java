package com.lineage.data.item_etcitem.quest;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.data.quest.IllusionistLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.quest.DragonKnightLv50_1;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class ThoughtPieceTime extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ThoughtPieceTime.class);
    }
    
    public static ItemExecutor get() {
        return new ThoughtPieceTime();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.isDragonKnight()) {
            if (pc.getQuest().isStart(DragonKnightLv50_1.QUEST.get_id())) {
                pc.getInventory().removeItem(item, 1L);
                this.staraQuest(pc, DragonKnightLv50_1.QUEST.get_id(), 2004);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cot_ep1st"));
            }
        }
        else if (pc.isIllusionist()) {
            if (pc.getQuest().isStart(IllusionistLv50_1.QUEST.get_id())) {
                pc.getInventory().removeItem(item, 1L);
                this.staraQuest(pc, IllusionistLv50_1.QUEST.get_id(), 2004);
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cot_ep1st"));
            }
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cot_ep1st"));
        }
    }
    
    private void staraQuest(L1PcInstance pc, int questid, int mapid) {
        try {
            int showId = WorldQuest.get().nextId();
            int users = QuestMapTable.get().getTemplate(mapid);
            if (users == -1) {
                users = 127;
            }
            L1QuestUser quest = WorldQuest.get().put(showId, mapid, questid, pc);
            if (quest == null) {
                ThoughtPieceTime._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                Integer time = QuestMapTable.get().getTime(mapid);
                if (time != null) {
                    quest.set_time(time);
                }
                L1Teleport.teleport(pc, 32729, 32831, (short)mapid, 2, true);
            }
        }
        catch (Exception e) {
            ThoughtPieceTime._log.error(e.getLocalizedMessage(), e);
        }
    }
}
