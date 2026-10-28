package com.lineage.data.npc.quest;

import java.util.Iterator;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.QuestClass;
import com.lineage.server.model.Instance.L1FollowerInstance;
import com.lineage.server.datatables.NpcTable;
import com.lineage.data.quest.EWLv40_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Roi extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Roi.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Roi();
    }
    
    @Override
    public int type() {
        return 7;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (npc.getMaster() != null) {
                if (npc.getMaster().equals(pc)) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi2"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi"));
                }
                return;
            }
            else if (pc.getQuest().isEnd(EWLv40_1.QUEST.get_id())) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi"));
                return;
            }
            else if (pc.getLevel() >= EWLv40_1.QUEST.get_questlevel()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi1"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi"));
            }
        }
        catch (Exception e) {
            Npc_Roi._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (npc.getMaster() != null) {
            return;
        }
        else if (pc.getQuest().isEnd(EWLv40_1.QUEST.get_id())) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi"));
            return;
        }
        else {
            if (pc.getLevel() >= EWLv40_1.QUEST.get_questlevel()) {
                if (cmd.equalsIgnoreCase("start")) {
                    L1Npc l1npc = NpcTable.get().getTemplate(70957);
                    new L1FollowerInstance(l1npc, npc, pc);
                    QuestClass.get().startQuest(pc, EWLv40_1.QUEST.get_id());
                    isCloseList = true;
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "roi"));
            }
            if (isCloseList) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            return;
        }
    }
    
    @Override
    public void attack(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (npc.getNpcId() != 70957) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else {
                for (L1Object object : World.get().getVisibleObjects(npc)) {
                    if (object instanceof L1NpcInstance) {
                        L1NpcInstance tgnpc = (L1NpcInstance)object;
                        if (tgnpc.getNpcTemplate().get_npcId() == 70964 && npc.getLocation().getTileLineDistance(pc.getLocation()) < 3 && tgnpc.getLocation().getTileLineDistance(pc.getLocation()) < 3) {
                            pc.getQuest().set_end(EWLv40_1.QUEST.get_id());
                            CreateNewItem.getQuestItem(pc, npc, 41003, 1L);
                            npc.setParalyzed(true);
                            npc.deleteMe();
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Npc_Roi._log.error(e.getLocalizedMessage(), e);
        }
    }
}
