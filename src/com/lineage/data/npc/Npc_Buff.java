package com.lineage.data.npc;

import java.util.Iterator;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.L1Clan;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.world.WorldClan;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Buff extends NpcExecutor
{
    private Npc_Buff() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Buff();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_npcbuff"));
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("npcclanbuff")) {
            if (pc.hasSkillEffect(95413)) {
                return;
            }
            else if (pc.getClan() == null) {
                pc.sendPackets(new S_SystemMessage("必須先擁有血盟。"));
                return;
            }
            else if (pc.getInventory().checkItem(ConfigOther.NeedItem, (long)ConfigOther.NeedItemCount)) {
                pc.getInventory().consumeItem(ConfigOther.NeedItem, (long)ConfigOther.NeedItemCount);
                pc.setSkillEffect(95413, 10000);
                int[] skills = ConfigOther.Give_skill;
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                for (int i = 0; i < skills.length; ++i) {
                    L1PcInstance[] array;
                    for (int length = (array = clanMembers).length, j = 0; j < length; ++j) {
                        L1PcInstance clanMember1 = array[j];
                        L1Skills skill = SkillsTable.get().getTemplate(skills[i]);
                        L1SkillUse skillUse = new L1SkillUse();
                        skillUse.handleCommands(clanMember1, skills[i], clanMember1.getId(), clanMember1.getX(), clanMember1.getY(), skill.getBuffDuration(), 4);
                    }
                }
                L1PcInstance[] onlineClanMember;
                for (int length2 = (onlineClanMember = clan.getOnlineClanMember()).length, k = 0; k < length2; ++k) {
                    L1PcInstance clanMembers2 = onlineClanMember[k];
                    clanMembers2.sendPackets(new S_SystemMessage(String.format(ConfigOther.Msg, pc.getName())));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(ConfigOther.ItemMsg));
            }
        }
        if (cmd.equalsIgnoreCase("npcallbuff")) {
            if (pc.hasSkillEffect(95413)) {
                return;
            }
            else {
                pc.setSkillEffect(95413, 10000);
                if (pc.getInventory().checkItem(ConfigOther.NeedItem1, (long)ConfigOther.NeedItemCount1)) {
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(ConfigOther.Msg1, pc.getName())));
                    World.get().broadcastPacketToAll(new S_BlueMessage(166, "\\f2" + String.format(ConfigOther.Msg1, pc.getName())));
                    pc.getInventory().consumeItem(ConfigOther.NeedItem1, (long)ConfigOther.NeedItemCount1);
                    Npc_Buff.AllBuffRunnable allBuffRunnable = new Npc_Buff.AllBuffRunnable();
                    GeneralThreadPool.get().execute(allBuffRunnable);
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(ConfigOther.ItemMsg1));
                }
            }
        }
    }
    
    private class AllBuffRunnable implements Runnable
    {
        @Override
        public void run() {
            try {
                for (L1PcInstance tgpc : World.get().getAllPlayers()) {
                    this.startPc(tgpc);
                    Thread.sleep(1L);
                }
            }
            catch (Exception ex) {}
        }
        
        public void startPc(L1PcInstance target) {
            int[] allBuffSkill = ConfigOther.Give_skill1;
            for (int i = 0; i < allBuffSkill.length; ++i) {
                L1Skills skill = SkillsTable.get().getTemplate(allBuffSkill[i]);
                L1SkillUse skillUse = new L1SkillUse();
                skillUse.handleCommands(target, allBuffSkill[i], target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 4);
            }
        }
    }
}
