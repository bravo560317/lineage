package com.lineage.data.npc.quest;

import java.util.Iterator;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.model.Instance.L1FollowerInstance;
import com.lineage.server.datatables.NpcTable;
import com.lineage.data.quest.KnightLv45_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Searcher extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Searcher.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Searcher();
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
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk2"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                return;
            }
            else {
                npc.onTalkAction(pc);
                if (pc.isCrown()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                else if (pc.isKnight()) {
                    if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                        return;
                    }
                    else if (pc.getLevel() >= KnightLv45_1.QUEST.get_questlevel()) {
                        switch (pc.getQuest().get_step(KnightLv45_1.QUEST.get_id())) {
                            case 4:
                            case 5: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk1"));
                                break;
                            }
                            default: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                                break;
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                    }
                }
                else if (pc.isElf()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                else if (pc.isWizard()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                else if (pc.isDarkelf()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                else if (pc.isDragonKnight()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                else if (pc.isIllusionist()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk4"));
                }
            }
        }
        catch (Exception e) {
            Npc_Searcher._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (npc.getMaster() != null) {
            return;
        }
        else {
            if (pc.isKnight()) {
                if (pc.getQuest().isEnd(KnightLv45_1.QUEST.get_id())) {
                    return;
                }
                else if (cmd.equalsIgnoreCase("start")) {
                    L1Npc l1npc = NpcTable.get().getTemplate(71093);
                    new L1FollowerInstance(l1npc, npc, pc);
                    pc.getQuest().set_step(KnightLv45_1.QUEST.get_id(), 5);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "searcherk2"));
                }
            }
            else {
                isCloseList = true;
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
            if (npc.getNpcId() != 71093) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else {
                for (L1Object object : World.get().getVisibleObjects(npc)) {
                    if (object instanceof L1NpcInstance) {
                        L1NpcInstance tgnpc = (L1NpcInstance)object;
                        if (tgnpc.getNpcTemplate().get_npcId() == 70740 && npc.getLocation().getTileLineDistance(pc.getLocation()) < 3 && tgnpc.getLocation().getTileLineDistance(pc.getLocation()) < 3) {
                            pc.getQuest().set_step(KnightLv45_1.QUEST.get_id(), 6);
                            if (!pc.getInventory().checkItem(40593)) {
                                CreateNewItem.getQuestItem(pc, npc, 40593, 1L);
                            }
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
            Npc_Searcher._log.error(e.getLocalizedMessage(), e);
        }
    }
}
