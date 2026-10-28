package com.lineage.server.model;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class L1Cube extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _future;
    private int _timeCounter;
    private final L1Character _effect;
    private final L1Character _cha;
    private final int _skillId;
    
    static {
        _log = LogFactory.getLog(L1Cube.class);
    }
    
    public L1Cube(L1Character effect, L1Character cha, int skillId) {
        this._future = null;
        this._timeCounter = 0;
        this._effect = effect;
        this._cha = cha;
        this._skillId = skillId;
    }
    
    @Override
    public void run() {
        try {
            if (this._cha.isDead()) {
                this.stop();
                return;
            }
            else if (!this._cha.hasSkillEffect(this._skillId)) {
                this.stop();
                return;
            }
            else {
                ++this._timeCounter;
                this.giveEffect();
            }
        }
        catch (Throwable e) {
            L1Cube._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void begin() {
        this._future = GeneralThreadPool.get().scheduleAtFixedRate(this, 900L, 1000L);
    }
    
    public void stop() {
        if (this._future != null) {
            this._future.cancel(false);
        }
    }
    
    public void giveEffect() {
        switch (this._skillId) {
            case 1019: {
                if (this._timeCounter % 4 != 0) {
                    return;
                }
                else if (this._cha.hasSkillEffect(4000)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(78)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(50)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(157)) {
                    return;
                }
                else if (this._cha instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)this._cha;
                    pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 2));
                    pc.receiveDamage(this._effect, 10.0, false, true);
                    break;
                }
                else if (this._cha instanceof L1MonsterInstance) {
                    L1MonsterInstance mob = (L1MonsterInstance)this._cha;
                    mob.broadcastPacketX10(new S_DoActionGFX(mob.getId(), 2));
                    mob.receiveDamage(this._effect, 10);
                    break;
                }
                else {
                    break;
                }
            }
            case 1021: {
                if (this._timeCounter % 4 != 0) {
                    return;
                }
                else if (this._cha.hasSkillEffect(4000)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(78)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(50)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(157)) {
                    return;
                }
                else if (this._cha instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)this._cha;
                    pc.setSkillEffect(4000, 1000);
                    pc.sendPackets(new S_Paralysis(6, true));
                    break;
                }
                else if (this._cha instanceof L1MonsterInstance) {
                    L1MonsterInstance mob = (L1MonsterInstance)this._cha;
                    mob.setSkillEffect(4000, 1000);
                    mob.setPassispeed(0);
                    break;
                }
                else {
                    break;
                }
            }
            case 1023: {
                this._cha.setSkillEffect(1024, 4000);
                break;
            }
            case 1025: {
                if (this._timeCounter % 4 == 0) {
                    int newMp = this._cha.getCurrentMp() + 5;
                    if (newMp < 0) {
                        newMp = 0;
                    }
                    this._cha.setCurrentMp(newMp);
                }
                if (this._timeCounter % 5 == 0) {
                    if (this._cha instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)this._cha;
                        pc.receiveDamage(this._effect, 25.0, false, true);
                        break;
                    }
                    else if (this._cha instanceof L1MonsterInstance) {
                        L1MonsterInstance mob = (L1MonsterInstance)this._cha;
                        mob.receiveDamage(this._effect, 25);
                        break;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }
}
