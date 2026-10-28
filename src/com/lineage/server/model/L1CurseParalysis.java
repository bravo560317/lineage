package com.lineage.server.model;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1CurseParalysis extends L1Paralysis
{
    private static final Log _log;
    private final L1Character _target;
    private final int _delay;
    private final int _time;
    private Thread _timer;
    
    static {
        _log = LogFactory.getLog(L1CurseParalysis.class);
    }
    
    private L1CurseParalysis(L1Character cha, int delay, int time, int mode) {
        this._target = cha;
        this._delay = delay;
        this._time = time;
        this.curse(mode);
    }
    
    private void curse(int mode) {
        if (this._target instanceof L1PcInstance) {
            L1PcInstance player = (L1PcInstance)this._target;
            switch (mode) {
                case 1: {
                    player.sendPackets(new S_ServerMessage(212));
                    break;
                }
                case 2: {
                    player.sendPackets(new S_ServerMessage(291));
                    break;
                }
            }
        }
        this._target.setPoisonEffect(2);
        this._timer = new L1CurseParalysis.ParalysisDelayTimer();
        GeneralThreadPool.get().execute(this._timer);
    }
    
    public static boolean curse(L1Character cha, int delay, int time, int mode) {
        if (!(cha instanceof L1PcInstance) && !(cha instanceof L1MonsterInstance)) {
            return false;
        }
        else if (cha.hasSkillEffect(1010) || cha.hasSkillEffect(1011)) {
            return false;
        }
        else {
            cha.setParalaysis(new L1CurseParalysis(cha, delay, time, mode));
            return true;
        }
    }
    
    @Override
    public int getEffectId() {
        return 2;
    }
    
    @Override
    public void cure() {
        this._target.setPoisonEffect(0);
        this._target.setParalaysis(null);
        if (this._timer != null) {
            this._timer.interrupt();
        }
    }
    
    /*
    static void access$3(L1CurseParalysis l1CurseParalysis, Thread timer) {
        l1CurseParalysis._timer = timer;
    }
    */
    
    private class ParalysisDelayTimer extends Thread
    {
        @Override
        public void run() {
            L1CurseParalysis.this._target.setSkillEffect(1010, 0);
            try {
                Thread.sleep((long)L1CurseParalysis.this._delay);
            }
            catch (InterruptedException e) {
                L1CurseParalysis.this._target.killSkillEffectTimer(1010);
                ModelError.isError(L1CurseParalysis._log, e.getLocalizedMessage(), e);
                return;
            }
            if (L1CurseParalysis.this._target instanceof L1PcInstance) {
                L1PcInstance player = (L1PcInstance)L1CurseParalysis.this._target;
                if (!player.isDead()) {
                    player.sendPackets(new S_Paralysis(1, true));
                }
            }
            L1CurseParalysis.this._target.setParalyzed(true);
            L1CurseParalysis.this._timer = new L1CurseParalysis.ParalysisTimer();
            GeneralThreadPool.get().execute(L1CurseParalysis.this._timer);
            if (this.isInterrupted()) {
                L1CurseParalysis.this._timer.interrupt();
            }
        }
    }
    
    private class ParalysisTimer extends Thread
    {
        @Override
        public void run() {
            L1CurseParalysis.this._target.killSkillEffectTimer(1010);
            L1CurseParalysis.this._target.setSkillEffect(1011, 0);
            try {
                Thread.sleep((long)L1CurseParalysis.this._time);
            }
            catch (InterruptedException e) {
                ModelError.isError(L1CurseParalysis._log, e.getLocalizedMessage(), e);
            }
            L1CurseParalysis.this._target.killSkillEffectTimer(1011);
            if (L1CurseParalysis.this._target instanceof L1PcInstance) {
                L1PcInstance player = (L1PcInstance)L1CurseParalysis.this._target;
                if (!player.isDead()) {
                    player.sendPackets(new S_Paralysis(1, false));
                }
            }
            L1CurseParalysis.this._target.setParalyzed(false);
            L1CurseParalysis.this.cure();
        }
    }
}
