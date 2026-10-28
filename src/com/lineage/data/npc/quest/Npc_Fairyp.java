package com.lineage.data.npc.quest;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.quest.ElfLv30_1;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Fairyp extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Fairyp.class);
        Npc_Fairyp._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Fairyp();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            if (pc.isCrown()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
            else if (pc.isElf()) {
                if (pc.getQuest().isEnd(ElfLv30_1.QUEST.get_id())) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
                    return;
                }
                else if (pc.getLevel() >= ElfLv30_1.QUEST.get_questlevel()) {
                    if (!pc.getQuest().isStart(ElfLv30_1.QUEST.get_id())) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
                    }
                    else if (Npc_Fairyp._random.nextInt(100) < 40) {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp1"));
                    }
                    else {
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp2"));
                    }
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "fairyp3"));
            }
        }
        catch (Exception e) {
            Npc_Fairyp._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (pc.isElf()) {
            if (pc.getQuest().isEnd(ElfLv30_1.QUEST.get_id())) {
                return;
            }
            else if (cmd.equalsIgnoreCase("teleport darkmar-dungen")) {
                staraQuest(pc, 213);
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("teleport dark-elf-dungen")) {
                staraQuest(pc, 211);
                isCloseList = true;
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    public static void staraQuest(L1PcInstance pc, int mapid) {
        try {
            int questid = ElfLv30_1.QUEST.get_id();
            int showId = WorldQuest.get().nextId();
            L1QuestUser quest = WorldQuest.get().put(showId, mapid, questid, pc);
            if (quest == null) {
                Npc_Fairyp._log.error("副本設置過程發生異常!!");
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                Integer time = QuestMapTable.get().getTime(mapid);
                if (time != null) {
                    quest.set_time(time);
                }
                L1Teleport.teleport(pc, 32744, 32794, (short)mapid, 5, true);
            }
        }
        catch (Exception e) {
            Npc_Fairyp._log.error(e.getLocalizedMessage(), e);
        }
    }
}
