package com.lineage.data.npc.quest;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.quest.DragonKnightLv50_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_RedSoul extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_RedSoul.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_RedSoul();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (!pc.isCrown()) {
                if (pc.isKnight()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                }
                else if (pc.isElf()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                }
                else if (pc.isWizard()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                }
                else if (pc.isDarkelf()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                }
                else if (pc.isDragonKnight()) {
                    if (pc.getQuest().isStart(DragonKnightLv50_1.QUEST.get_id())) {
                        switch (pc.getQuest().get_step(DragonKnightLv50_1.QUEST.get_id())) {
                            case 2: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f1"));
                                break;
                            }
                            case 3: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f2"));
                                break;
                            }
                            default: {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                                break;
                            }
                        }
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                    }
                }
                else if (pc.isIllusionist()) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f4"));
                }
            }
        }
        catch (Exception e) {
            Npc_RedSoul._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (pc.isDragonKnight()) {
            if (pc.getQuest().isStart(DragonKnightLv50_1.QUEST.get_id())) {
                switch (pc.getQuest().get_step(DragonKnightLv50_1.QUEST.get_id())) {
                    case 2:
                    case 3: {
                        if (!cmd.equalsIgnoreCase("a")) {
                            break;
                        }
                        else {
                            L1ItemInstance item = pc.getInventory().checkItemX(49229, 10L);
                            if (item != null) {
                                pc.getInventory().removeItem(item, 10L);
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f2"));
                                pc.getQuest().set_step(DragonKnightLv50_1.QUEST.get_id(), 3);
                                CreateNewItem.getQuestItem(pc, npc, 49207, 1L);
                                CreateNewItem.getQuestItem(pc, npc, 49227, 1L);
                                break;
                            }
                            else {
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "redsoul_f3"));
                                break;
                            }
                        }
                    }
                    default: {
                        pc.sendPackets(new S_CloseList(pc.getId()));
                        break;
                    }
                }
            }
            else {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
    }
}
