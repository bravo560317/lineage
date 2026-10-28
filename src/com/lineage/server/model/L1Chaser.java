package com.lineage.server.model;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class L1Chaser extends TimerTask
{
    private static final Log _log;
    private static final Random _random;
    private ScheduledFuture<?> _future;
    private int _timeCounter;
    private final L1PcInstance _pc;
    private final L1Character _cha;
    
    static {
        _log = LogFactory.getLog(L1Chaser.class);
        _random = new Random();
    }
    
    public L1Chaser(L1PcInstance pc, L1Character cha) {
        this._future = null;
        this._timeCounter = 0;
        this._cha = cha;
        this._pc = pc;
    }
    
    @Override
    public void run() {
        try {
            if (this._cha == null || this._cha.isDead()) {
                this.stop();
                return;
            }
            else {
                this.attack();
                ++this._timeCounter;
                if (this._timeCounter >= 3) {
                    this.stop();
                    return;
                }
            }
        }
        catch (Throwable e) {
            L1Chaser._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void begin() {
        this._future = GeneralThreadPool.get().scheduleAtFixedRate(this, 0L, 1000L);
    }
    
    public void stop() {
        if (this._future != null) {
            this._future.cancel(false);
        }
    }
    
    private void attack() {
        double damage = this.getDamage(this._pc, this._cha);
        if (this._cha.getCurrentHp() - (int)damage <= 0 && this._cha.getCurrentHp() != 1) {
            damage = (double)(this._cha.getCurrentHp() - 1);
        }
        else if (this._cha.getCurrentHp() == 1) {
            damage = 0.0;
        }
        //this._pc.sendPacketsAll(new S_EffectLocation(this._cha.getX(), this._cha.getY(), 7025));
        if (this._cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this._cha;
            pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 2));
            pc.receiveDamage(this._pc, damage, false, false);
        }
        else if (this._cha instanceof L1NpcInstance) {
            L1NpcInstance npc = (L1NpcInstance)this._cha;
            npc.broadcastPacketX10(new S_DoActionGFX(npc.getId(), 2));
            npc.receiveDamage(this._pc, (int)damage);
        }
    }
    
    private double getDamage(L1PcInstance pc, L1Character cha) {
        double dmg = 0.0;
        int spByItem = pc.getSp() - pc.getTrueSp();
        int intel = pc.getInt();
        int charaIntelligence = pc.getInt() + spByItem - 12;
        double coefficientA = 1.0 + 0.09375 * (double)charaIntelligence;
        if (coefficientA < 1.0) {
            coefficientA = 1.0;
        }
        double coefficientB = 0.0;
        if (intel > 25) {
            coefficientB = 1.625;
        }
        else if (intel <= 12) {
            coefficientB = 0.78;
        }
        else {
            coefficientB = (double)intel * 0.065;
        }
        double coefficientC = 0.0;
        if (intel > 25) {
            coefficientC = 25.0;
        }
        else if (intel <= 12) {
            coefficientC = 12.0;
        }
        else {
            coefficientC = (double)intel;
        }
        double bsk = 0.0;
        if (pc.hasSkillEffect(55)) {
            bsk = 0.1;
        }
        dmg = (double)(L1Chaser._random.nextInt(6) + 1 + 7) * (1.0 + bsk) * coefficientA * coefficientB / 10.5 * coefficientC * 2.0;
        return L1WeaponSkill.calcDamageReduction(pc, cha, dmg, 0) * 0.66;
    }
}
