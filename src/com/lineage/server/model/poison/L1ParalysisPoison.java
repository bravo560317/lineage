package com.lineage.server.model.poison;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.ModelError;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.Log;

public class L1ParalysisPoison extends L1Poison
{
    private static final Log _log;
    private final L1Character _target;
    private Thread _timer;
    private final int _delay;
    private final int _time;
    private int _effectId;
    
    static {
        _log = LogFactory.getLog(L1ParalysisPoison.class);
    }
    
    private L1ParalysisPoison(L1Character cha, int delay, int time) {
        this._effectId = 1;
        this._target = cha;
        this._delay = delay;
        this._time = time;
        this.doInfection();
    }
    
    public static boolean doInfection(L1Character cha, int delay, int time) {
        if (!L1Poison.isValidTarget(cha)) {
            return false;
        }
        else {
            cha.setPoison(new L1ParalysisPoison(cha, delay, time));
            return true;
        }
    }
    
    private void doInfection() {
        L1Poison.sendMessageIfPlayer(this._target, 212);
        this._target.setPoisonEffect(1);
        if (this._target instanceof L1PcInstance) {
            this._timer = new L1ParalysisPoison.ParalysisPoisonTimer();
            GeneralThreadPool.get().execute(this._timer);
        }
    }
    
    @Override
    public int getEffectId() {
        return this._effectId;
    }
    
    @Override
    public void cure() {
        this._target.setPoisonEffect(0);
        this._target.setPoison(null);
        if (this._timer != null) {
            this._timer.interrupt();
        }
    }
    
    /*
    static void access$3(L1ParalysisPoison l1ParalysisPoison, int effectId) {
        l1ParalysisPoison._effectId = effectId;
    }
    */
    
    /*
    static void access$4(L1ParalysisPoison l1ParalysisPoison, Thread timer) {
        l1ParalysisPoison._timer = timer;
    }
    */
    
    private class ParalysisPoisonTimer extends Thread
    {
        @Override
        public void run() {
            L1ParalysisPoison.this._target.setSkillEffect(1008, 0);
            try {
                Thread.sleep((long)L1ParalysisPoison.this._delay);
            }
            catch (InterruptedException e) {
                ModelError.isError(L1ParalysisPoison._log, e.getLocalizedMessage(), e);
                return;
            }
            L1ParalysisPoison.this._effectId = 2;
            L1ParalysisPoison.this._target.setPoisonEffect(2);
            if (L1ParalysisPoison.this._target instanceof L1PcInstance) {
                L1PcInstance player = (L1PcInstance)L1ParalysisPoison.this._target;
                if (!player.isDead()) {
                    player.sendPackets(new S_Paralysis(1, true));
                    L1ParalysisPoison.this._timer = new L1ParalysisPoison.ParalysisTimer();
                    GeneralThreadPool.get().execute(L1ParalysisPoison.this._timer);
                    if (this.isInterrupted()) {
                        L1ParalysisPoison.this._timer.interrupt();
                    }
                }
            }
        }
    }
    
    private class ParalysisTimer extends Thread
    {
        @Override
        public void run() {
            L1ParalysisPoison.this._target.killSkillEffectTimer(1008);
            L1ParalysisPoison.this._target.setSkillEffect(1009, 0);
            try {
                Thread.sleep((long)L1ParalysisPoison.this._time);
            }
            catch (InterruptedException e) {
                ModelError.isError(L1ParalysisPoison._log, e.getLocalizedMessage(), e);
            }
            L1ParalysisPoison.this._target.killSkillEffectTimer(1009);
            if (L1ParalysisPoison.this._target instanceof L1PcInstance) {
                L1PcInstance player = (L1PcInstance)L1ParalysisPoison.this._target;
                if (!player.isDead()) {
                    player.sendPackets(new S_Paralysis(1, false));
                    L1ParalysisPoison.this.cure();
                }
            }
        }
    }
}
