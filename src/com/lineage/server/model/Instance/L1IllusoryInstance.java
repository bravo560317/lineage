package com.lineage.server.model.Instance;

import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_NPCPack_Ill;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1IllusoryInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1IllusoryInstance.class);
    }
    
    public L1IllusoryInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            else {
                perceivedFrom.addKnownObject(this);
                if (this.getCurrentHp() > 0) {
                    perceivedFrom.sendPackets(new S_NPCPack_Ill(this));
                    this.onNpcAI();
                    if (this.getBraveSpeed() > 0) {
                        perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), this.getBraveSpeed(), 600000));
                    }
                }
                else {
                    perceivedFrom.sendPackets(new S_NPCPack_Ill(this));
                }
            }
        }
        catch (Exception e) {
            L1IllusoryInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void setLink(L1Character cha) {
        if (this.get_showId() != cha.get_showId()) {
            return;
        }
        else if (cha instanceof L1PcInstance && cha.getMap().isSafetyZone(cha.getLocation())) {
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
    public void onTalkAction(L1PcInstance pc) {
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
        }
        catch (Exception e) {
            L1IllusoryInstance._log.error(e.getLocalizedMessage(), e);
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
