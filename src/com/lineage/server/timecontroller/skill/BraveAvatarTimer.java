package com.lineage.server.timecontroller.skill;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class BraveAvatarTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(BraveAvatarTimer.class);
    }
    
    public void start() {
        int timeMillis = 5000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            for (L1PcInstance pc : World.get().getAllPlayers()) {
                if (pc != null) {
                    if (pc.getNetConnection() == null) {
                        continue;
                    }
                    else if (pc.getParty() != null) {
                        L1PcInstance leader = pc.getParty().getLeader();
                        if (leader.isCrown() && leader.isSkillMastery(119) && leader.getLocation().getTileLineDistance(pc.getLocation()) <= 16) {
                            if (pc.getParty().getNumOfMembers() >= 2 && !pc.hasSkillEffect(8065)) {
                                pc.setSkillEffect(8065, 0);
                                pc.addStr(1);
                                pc.addDex(1);
                                pc.addInt(1);
                                pc.addMr(10);
                                pc.addRegistStun(2);
                                pc.addRegistSustain(2);
                                pc.sendPackets(new S_SPMR(pc));
                                pc.sendPackets(new S_OwnCharStatus2(pc));
                                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 9009));
                                pc.sendPackets(new S_PacketBox(180, 1, 479));
                            }
                            else {
                                continue;
                            }
                        }
                        else if (leader.getLocation().getTileLineDistance(pc.getLocation()) > 16 && pc.hasSkillEffect(8065)) {
                            pc.removeNoTimerSkillEffect(8065);
                        }
                        else {
                            continue;
                        }
                    }
                    else if (pc.hasSkillEffect(8065)) {
                        pc.removeNoTimerSkillEffect(8065);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception e) {
            BraveAvatarTimer._log.error("王者加護時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            BraveAvatarTimer BraveAvatarTimer = new BraveAvatarTimer();
            BraveAvatarTimer.start();
        }
    }
}
