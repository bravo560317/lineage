package com.lineage.server.model.poison;

import com.lineage.server.model.ModelError;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.Log;

public class L1DamagePoison extends L1Poison
{
    private static final Log _log;
    private Thread _timer;
    private final L1Character _attacker;
    private final L1Character _target;
    private final int _damageSpan;
    private final int _damage;
    
    static {
        _log = LogFactory.getLog(L1DamagePoison.class);
    }
    
    private L1DamagePoison(L1Character attacker, L1Character cha, int damageSpan, int damage) {
        this._attacker = attacker;
        this._target = cha;
        this._damageSpan = damageSpan;
        this._damage = damage;
        this.doInfection();
    }
    
    boolean isDamageTarget(L1Character cha) {
        return cha instanceof L1PcInstance || cha instanceof L1MonsterInstance;
    }
    
    private void doInfection() {
        this._target.setSkillEffect(1006, 30000);
        this._target.setPoisonEffect(1);
        if (this.isDamageTarget(this._target)) {
            this._timer = new L1DamagePoison.NormalPoisonTimer();
            GeneralThreadPool.get().execute(this._timer);
        }
    }
    
    public static boolean doInfection(L1Character attacker, L1Character cha, int damageSpan, int damage) {
        if (!L1Poison.isValidTarget(cha)) {
            return false;
        }
        else {
            cha.setPoison(new L1DamagePoison(attacker, cha, damageSpan, damage));
            return true;
        }
    }
    
    @Override
    public int getEffectId() {
        return 1;
    }
    
    @Override
    public void cure() {
        this._target.setPoisonEffect(0);
        this._target.killSkillEffectTimer(1006);
        this._target.setPoison(null);
        if (this._timer != null) {
            this._timer.interrupt();
        }
    }
    
    private class NormalPoisonTimer extends Thread
    {
        @Override
        public void run() {
            try {
                while (L1DamagePoison.this._target.hasSkillEffect(1006)) {
                    Thread.sleep((long)L1DamagePoison.this._damageSpan);
                    if (!L1DamagePoison.this._target.hasSkillEffect(1006)) {
                        break;
                    }
                    else if (L1DamagePoison.this._target instanceof L1PcInstance) {
                        L1PcInstance player = (L1PcInstance)L1DamagePoison.this._target;
                        player.receiveDamage(L1DamagePoison.this._attacker, (double)L1DamagePoison.this._damage, false, true);
                        if (player.isDead()) {
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                    else if (L1DamagePoison.this._target instanceof L1MonsterInstance) {
                        L1MonsterInstance mob = (L1MonsterInstance)L1DamagePoison.this._target;
                        mob.receiveDamage(L1DamagePoison.this._attacker, L1DamagePoison.this._damage);
                        if (mob.isDead()) {
                            break;
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
            catch (InterruptedException e) {
                ModelError.isError(L1DamagePoison._log, e.getLocalizedMessage(), e);
            }
            L1DamagePoison.this.cure();
        }
    }
}
