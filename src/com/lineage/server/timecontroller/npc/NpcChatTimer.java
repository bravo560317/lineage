package com.lineage.server.timecontroller.npc;

import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.templates.L1NpcChat;
import com.lineage.server.datatables.NpcChatTable;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class NpcChatTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(NpcChatTimer.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1NpcChat> allChat = NpcChatTable.get().all();
            if (allChat.isEmpty()) {
                return;
            }
            else {
                for (L1NpcChat npcChat : allChat) {
                    if (this.isChatTime(npcChat.getGameTime())) {
                        int npcId = npcChat.getNpcId();
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == npcId) {
                                npc.startChat(3);
                            }
                        }
                    }
                    Thread.sleep(50L);
                }
            }
        }
        catch (Exception e) {
            NpcChatTimer._log.error("NPC對話時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            NpcChatTimer npcChatTimer = new NpcChatTimer();
            npcChatTimer.start();
        }
    }
    
    private boolean isChatTime(int chatTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Calendar realTime = getRealTime();
        int nowTime = Integer.valueOf(sdf.format(realTime.getTime()));
        return nowTime == chatTime;
    }
    
    private static Calendar getRealTime() {
        TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(_tz);
        return cal;
    }
}
