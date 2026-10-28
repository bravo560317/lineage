package com.lineage.server.timecontroller.pc;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1Party;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_PacketBoxParty;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class PartyTimer extends TimerTask
{
    private static final Log _log;
    private static final Random _random;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(PartyTimer.class);
        _random = new Random();
    }
    
    public void start() {
        int timeMillis = 5000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 5000L, 5000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (tgpc.hasSkillEffect(6933)) {
                        newai(tgpc);
                    }
                    if (check(tgpc)) {
                        tgpc.sendPackets(new S_PacketBoxParty(tgpc.getParty(), tgpc));
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            PartyTimer._log.error("隊伍更新時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            PartyTimer partyTimer = new PartyTimer();
            partyTimer.start();
        }
    }
    
    private static boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else {
                L1Party party = tgpc.getParty();
                if (party == null) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            PartyTimer._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
    
    private static void newai(L1PcInstance pc) {
        String[] info = new String[34];
        info[1] = String.valueOf(pc.getnewai1());
        info[2] = String.valueOf(pc.getnewai2());
        info[3] = String.valueOf(pc.getnewai3());
        info[4] = String.valueOf(pc.getnewai4());
        info[5] = String.valueOf(pc.getnewai5());
        info[6] = String.valueOf(pc.getnewai6());
        switch (PartyTimer._random.nextInt(10) + 1) {
            case 1: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai", info));
                break;
            }
            case 2: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai1", info));
                break;
            }
            case 3: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai2", info));
                break;
            }
            case 4: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai3", info));
                break;
            }
            case 5: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai4", info));
                break;
            }
            case 6: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai5", info));
                break;
            }
            case 7: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai6", info));
                break;
            }
            case 8: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai7", info));
                break;
            }
            case 9: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai8", info));
                break;
            }
            case 10: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai9", info));
                break;
            }
        }
    }
}
