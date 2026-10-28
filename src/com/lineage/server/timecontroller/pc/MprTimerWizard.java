package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.WorldWizard;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class MprTimerWizard extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(MprTimerWizard.class);
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> allPc = WorldWizard.get().all();
            if (allPc.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : allPc) {
                    MprExecutor mpr = MprExecutor.get();
                    if (mpr.check(tgpc)) {
                        mpr.checkRegenMp(tgpc);
                        Thread.sleep(1L);
                    }
                    if (tgpc.isEsoteric()) {
                        int addMp = 0;
                        if (tgpc.getlogpcpower_SkillFor4() >= 1) {
                            addMp += tgpc.getlogpcpower_SkillFor4() * 10;
                        }
                        if (tgpc.getCurrentMp() > addMp) {
                            tgpc.setCurrentMp(tgpc.getCurrentMp() - addMp);
                            tgpc.sendPackets(new S_SkillSound(tgpc.getId(), 215));
                            tgpc.broadcastPacketAll(new S_SkillSound(tgpc.getId(), 215));
                        }
                        else {
                            tgpc.setEsoteric(false);
                            tgpc.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(粉碎爆發)"));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            MprTimerWizard._log.error("Pc(法師) MP自然回復時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            MprTimerWizard mprWizard = new MprTimerWizard();
            mprWizard.start();
        }
    }
}
