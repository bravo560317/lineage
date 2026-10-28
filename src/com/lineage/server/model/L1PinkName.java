package com.lineage.server.model;

import com.lineage.server.types.Point;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_PinkName;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1PinkName
{
    public static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1PinkName.class);
    }
    
    private L1PinkName() {
    }
    
    public static void onAction(L1PcInstance tgpc, L1Character atk) {
        if (tgpc == null || atk == null) {
            return;
        }
        else if (!(atk instanceof L1PcInstance)) {
            return;
        }
        else {
            L1PcInstance attacker = (L1PcInstance)atk;
            if (tgpc.getId() == attacker.getId()) {
                return;
            }
            else if (attacker.getFightId() == tgpc.getId()) {
                return;
            }
            else if (tgpc.getZoneType() != 0) {
                return;
            }
            else if (attacker.getZoneType() != 0) {
                return;
            }
            else {
                boolean isNowWar = false;
                int castleId = L1CastleLocation.getCastleIdByArea(tgpc);
                if (castleId != 0) {
                    isNowWar = ServerWarExecutor.get().isNowWar(castleId);
                }
                if (isNowWar) {
                    return;
                }
                else if (tgpc.getLawful() < 0) {
                    return;
                }
                else if (tgpc.isPinkName()) {
                    return;
                }
                else {
                    attacker.sendPacketsAll(new S_PinkName(attacker.getId(), 15));
                    if (!attacker.isPinkName()) {
                        attacker.setPinkName(true);
                        attacker.setSkillEffect(5122, 15000);
                        L1PinkName.PinkNameTimer pink = new PinkNameTimer(attacker);
                        GeneralThreadPool.get().execute(pink);
                    }
                    return;
                }
            }
        }
    }
    
    public static void runPinkNameTimer(L1PcInstance pc) {
        if (!pc.isPinkName()) {
            pc.sendPacketsAll(new S_PinkName(pc.getId(), 15));
            pc.setPinkName(true);
            pc.setSkillEffect(5122, 15000);
            L1PinkName.PinkNameTimer pink = new PinkNameTimer(pc);
            GeneralThreadPool.get().execute(pink);
        }
    }
    
    private static class PinkNameTimer implements Runnable
    {
        private L1PcInstance _attacker;
        private Point _point;
        private int _mapid;
        
        public PinkNameTimer(L1PcInstance attacker) {
            this._attacker = null;
            this._point = null;
            this._mapid = -1;
            this._attacker = attacker;
            this._point = new Point(attacker.getX(), attacker.getY());
            this._mapid = attacker.getMapId();
        }
        
        @Override
        public void run() {
            int i = 0;
            while (i < 180) {
                try {
                    Thread.sleep(1000L);
                }
                catch (Exception e) {
                    L1PinkName._log.error(e.getLocalizedMessage(), e);
                }
                if (this._mapid != this._attacker.getMapId()) {
                    break;
                }
                else if (!this._attacker.getLocation().isInScreen(this._point)) {
                    break;
                }
                else if (this._attacker.isDead()) {
                    break;
                }
                else {
                    ++i;
                }
            }
            this.stopPinkName();
        }
        
        private void stopPinkName() {
            this._attacker.sendPacketsAll(new S_PinkName(this._attacker.getId(), 0));
            this._attacker.setPinkName(false);
        }
    }
}
