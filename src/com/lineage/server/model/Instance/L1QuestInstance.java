package com.lineage.server.model.Instance;

import com.lineage.server.datatables.NpcTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1QuestInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1QuestInstance.class);
    }
    
    public L1QuestInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        else {
            int npcId = this.getNpcTemplate().get_npcId();
            switch (npcId) {
                case 70957:
                case 71075:
                case 80012:
                case 81209: {
                    break;
                }
                default: {
                    this.setActived(false);
                    this.startAI();
                    break;
                }
            }
            return;
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
            attack.commit();
        }
        catch (Exception e) {
            L1QuestInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance pc) {
        int npcId = this.getNpcTemplate().get_npcId();
        this.setHeading(this.targetDirection(pc.getX(), pc.getY()));
        this.broadcastPacketAll(new S_ChangeHeading(this));
        if (npcId == 71062) {
            if (pc.getQuest().get_step(31) == 2) {
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), "kamit1b"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), "kamit1"));
            }
        }
        else if (npcId == 71075) {
            if (pc.getQuest().get_step(34) == 1) {
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), "llizard1b"));
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), "llizard1a"));
            }
        }
        this.set_stop_time(10);
        this.setRest(true);
    }
    
    @Override
    public void onFinalAction(L1PcInstance pc, String action) {
        if (action.equalsIgnoreCase("start")) {
            int npcId = this.getNpcTemplate().get_npcId();
            if (npcId == 71062 && pc.getQuest().get_step(31) == 2) {
                L1Npc l1npc = NpcTable.get().getTemplate(71062);
                L1FollowerInstance follow = new L1FollowerInstance(l1npc, this, pc);
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), ""));
            }
            else if (npcId == 71075 && pc.getQuest().get_step(34) == 1) {
                L1Npc l1npc = NpcTable.get().getTemplate(71075);
                L1FollowerInstance follow = new L1FollowerInstance(l1npc, this, pc);
                pc.sendPackets(new S_NPCTalkReturn(this.getId(), ""));
            }
        }
    }
}
