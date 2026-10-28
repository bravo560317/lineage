package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.william.ItemOutburst;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldItem;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class Quburcount extends TimerTask
{
    private static final Log _log;
    private static final Random _random;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(Quburcount.class);
        _random = new Random();
    }
    
    public void start() {
        int timeMillis = 1000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1ItemInstance> items = WorldItem.get().all();
            Collection<L1PcInstance> all = World.get().getAllPlayers();
            if (all.isEmpty()) {
                return;
            }
            else {
                for (L1PcInstance tgpc : all) {
                    if (check(tgpc) && tgpc.getQuburcount() > 0) {
                        tgpc.setQuburcount(tgpc.getQuburcount() - 1);
                        switch (tgpc.getQuburcount()) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            case 20: {
                                tgpc.sendPackets(new S_ServerMessage("目前爆氣值剩餘:" + tgpc.getQuburcount()));
                                break;
                            }
                        }
                        if (tgpc.getQuburcount() == 0) {
                            for (L1ItemInstance item : items) {
                                ItemOutburst.falseOutburst(tgpc, item);
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Quburcount._log.error("爆氣時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            Quburcount partyTimer = new Quburcount();
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
            else if (!tgpc.getcheckOutbur()) {
                return false;
            }
            else if (!tgpc.getOutbur()) {
                return false;
            }
        }
        catch (Exception e) {
            Quburcount._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
}
