package com.lineage.server.model.poison;

import com.lineage.server.model.L1Character;

public class L1SilencePoison extends L1Poison
{
    private final L1Character _target;
    
    public static boolean doInfection(L1Character cha) {
        if (!L1Poison.isValidTarget(cha)) {
            return false;
        }
        else {
            cha.setPoison(new L1SilencePoison(cha));
            return true;
        }
    }
    
    private L1SilencePoison(L1Character cha) {
        this._target = cha;
        this.doInfection();
    }
    
    private void doInfection() {
        this._target.setPoisonEffect(1);
        L1Poison.sendMessageIfPlayer(this._target, 310);
        this._target.setSkillEffect(1007, 0);
    }
    
    @Override
    public int getEffectId() {
        return 1;
    }
    
    @Override
    public void cure() {
        this._target.setPoisonEffect(0);
        L1Poison.sendMessageIfPlayer(this._target, 311);
        this._target.killSkillEffectTimer(1007);
        this._target.setPoison(null);
    }
}
