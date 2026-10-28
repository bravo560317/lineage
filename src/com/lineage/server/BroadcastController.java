package com.lineage.server;

import java.util.Iterator;
import com.lineage.server.serverpackets.ServerBasePacket;
import java.util.Collection;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.Queue;
import org.apache.commons.logging.Log;

public final class BroadcastController implements Runnable
{
    private static final Log _log;
    private static BroadcastController _instance;
    private static boolean _isStop;
    private static final int MAX_MSG_SIZE = 20;
    private static final Queue<String> _msgQueue;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(BroadcastController.class);
        _msgQueue = new ConcurrentLinkedQueue();
    }
    
    public static BroadcastController getInstance() {
        if (BroadcastController._instance == null) {
            BroadcastController._instance = new BroadcastController();
        }
        return BroadcastController._instance;
    }
    
    public final int getMsgSize() {
        return BroadcastController._msgQueue.size();
    }
    
    public final boolean requestWork(String data) {
        return BroadcastController._msgQueue.size() < 20 && BroadcastController._msgQueue.offer(data);
    }
    
    public final boolean isStop() {
        return BroadcastController._isStop;
    }
    
    public final void setStop(boolean stop) {
        BroadcastController._isStop = stop;
    }
    
    @Override
    public void run() {
        try {
            if (BroadcastController._isStop) {
                return;
            }
            else if (BroadcastController._msgQueue.isEmpty()) {
                return;
            }
            else {
                Collection<L1PcInstance> all = World.get().getAllPlayers();
                if (all.isEmpty()) {
                    return;
                }
                else {
                    String message = BroadcastController._msgQueue.poll();
                    ServerBasePacket packet1 = new S_ServerMessage(message);
                    ServerBasePacket packet2 = new S_PacketBoxGree(2, "\\f=" + message);
                    for (L1PcInstance pc : all) {
                        if (check(pc)) {
                            pc.sendPackets(packet1);
                            pc.sendPackets(packet2);
                        }
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            BroadcastController._log.error("廣播系統時間軸異常重啟");
            GeneralThreadPool.get().cancel(this._timer, false);
            (BroadcastController._instance = new BroadcastController()).start();
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
        }
        catch (Exception e) {
            BroadcastController._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
    
    public final void start() {
        int timeMillis = 3000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 3000L, 3000L);
    }
}
