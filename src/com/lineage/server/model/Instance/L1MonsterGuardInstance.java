package com.lineage.server.model.Instance;

import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.L1Character;
import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_NPCPack;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1MonsterGuardInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1MonsterGuardInstance.class);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                perceivedFrom.sendPackets(new S_NPCPack(this));
                if (this.getCurrentHp() > 0) {
                    this.onNpcAI();
                }
            }
        }
        catch (Exception e) {
            L1MonsterGuardInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void searchTarget() {
        L1PcInstance targetPlayer = this.searchTarget(this);
        if (targetPlayer != null) {
            this._hateList.add(targetPlayer, 0);
            this._target = targetPlayer;
        }
        else {
            this.ISASCAPE = false;
        }
    }
    
    private L1PcInstance searchTarget(L1MonsterGuardInstance npc) {
        L1PcInstance targetPlayer = null;
        for (L1PcInstance pc : World.get().getVisiblePlayer(npc)) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                L1MonsterGuardInstance._log.error(e.getLocalizedMessage(), e);
            }
            if (pc.getCurrentHp() > 0 && !pc.isDead() && !pc.isGhost() && !pc.isGm() && npc.get_showId() == pc.get_showId() && (pc.getClan() == null || pc.getClan().getCastleId() != 2)) {
                boolean isCheck = false;
                if (!pc.isInvisble()) {
                    isCheck = true;
                }
                if (npc.getNpcTemplate().is_agrocoi()) {
                    isCheck = true;
                }
                if (isCheck) {
                    if (pc.hasSkillEffect(67) && npc.getNpcTemplate().is_agrososc()) {
                        targetPlayer = pc;
                        return targetPlayer;
                    }
                    else if (npc.getNpcTemplate().is_agro()) {
                        targetPlayer = pc;
                        return targetPlayer;
                    }
                    else if (npc.getNpcTemplate().is_agrogfxid1() >= 0 && pc.getGfxId() == npc.getNpcTemplate().is_agrogfxid1()) {
                        targetPlayer = pc;
                        return targetPlayer;
                    }
                    else if (npc.getNpcTemplate().is_agrogfxid2() >= 0 && pc.getGfxId() == npc.getNpcTemplate().is_agrogfxid2()) {
                        targetPlayer = pc;
                        return targetPlayer;
                    }
                    else {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }
        }
        return targetPlayer;
    }
    
    @Override
    public void setLink(L1Character cha) {
        if (this.get_showId() != cha.get_showId()) {
            return;
        }
        else {
            if (cha != null && this._hateList.isEmpty()) {
                this._hateList.add(cha, 0);
                this.checkTarget();
            }
            return;
        }
    }
    
    public L1MonsterGuardInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        else {
            this.setActived(false);
            this.startAI();
            return;
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        if (this.ATTACK != null) {
            this.ATTACK.attack(pc, this);
        }
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            L1AttackMode attack = new L1AttackPc(pc, this);
            if (attack.calcHit()) {
                attack.calcDamage();
            }
            attack.action();
            attack.commit();
        }
    }
    
    @Override
    public void ReceiveManaDamage(L1Character attacker, int mpDamage) {
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
    }
    
    @Override
    public void setCurrentHp(int i) {
    }
    
    @Override
    public void setCurrentMp(int i) {
    }
}
