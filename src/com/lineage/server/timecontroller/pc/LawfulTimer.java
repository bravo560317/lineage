package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;
import com.lineage.server.datatables.MapsTable;

public class LawfulTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(LawfulTimer.class);
    }
    
    public void start() {
        int timeMillis = 500;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 500L, 500L);
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
                    if (check(tgpc)) {
                        if (tgpc.geteffect_gfxid() > 0 && tgpc.geteffect_time() > 0 && !tgpc.hasSkillEffect(91350)) {
                            tgpc.setSkillEffect(91350, tgpc.geteffect_time() * 1000);
                            tgpc.sendPackets(new S_SkillSound(tgpc.getId(), tgpc.geteffect_gfxid()));
                            tgpc.broadcastPacketAll(new S_SkillSound(tgpc.getId(), tgpc.geteffect_gfxid()));
                        }
                        if (tgpc.gethpr_time() > 0 && tgpc.gethprr() > 0 && tgpc.gethpr_gfxid() > 0 && !tgpc.hasSkillEffect(91351)) {
                            tgpc.setSkillEffect(91351, tgpc.gethpr_time() * 1000);
                            tgpc.setCurrentHp((int)(short)(tgpc.getCurrentHp() + tgpc.gethprr()));
                            tgpc.sendPackets(new S_SkillSound(tgpc.getId(), tgpc.gethpr_gfxid()));
                            tgpc.broadcastPacketAll(new S_SkillSound(tgpc.getId(), tgpc.gethpr_gfxid()));
                        }
                        if (tgpc.getmpr_time() > 0 && tgpc.getmprr() > 0 && tgpc.getmpr_gfxid() > 0 && !tgpc.hasSkillEffect(91352)) {
                            tgpc.setSkillEffect(91352, tgpc.getmpr_time() * 1000);
                            tgpc.setCurrentMp((int)(short)(tgpc.getCurrentMp() + tgpc.getmprr()));
                            tgpc.sendPackets(new S_SkillSound(tgpc.getId(), tgpc.getmpr_gfxid()));
                            tgpc.broadcastPacketAll(new S_SkillSound(tgpc.getId(), tgpc.getmpr_gfxid()));
                        }
                        int week = tgpc.getMap().isweek();
                        Calendar date = Calendar.getInstance();
                        int nowWeek = date.get(7) - 1;
                        if (nowWeek == 0) {
                            nowWeek += 7;
                        }
                        // 取得地圖的兩個時間區段設定
                        int starttime1 = tgpc.getMap().isStart_time();
                        int endtime1 = tgpc.getMap().isEnd_time();
                        int starttime2 = tgpc.getMap().isStart_time2();
                        int endtime2 = tgpc.getMap().isEnd_time2();
                        int nowHour = date.get(11);
                        // 判斷是否在第一個時間區段內
                        boolean time1_OK = (nowHour >= starttime1 && nowHour < endtime1);
                        // 判斷是否在第二個時間區段內
                        boolean time2_OK = (nowHour >= starttime2 && nowHour < endtime2);

                        // 檢查星期限制
                        if (week != -1 && week != nowWeek) {
                            tgpc.sendPackets(new S_ServerMessage(166, "★地圖限制星期【" + week + "】才可進入"));
                            L1Teleport.teleport(tgpc, 33440, 32802, (short)4, 0, true);
                            continue;
                        }

                        // 檢查時間區段限制 (當地圖有設定任一時間區段時)
                        if (starttime1 > 0 || endtime1 > 0 || starttime2 > 0 || endtime2 > 0) {
                            // 如果兩個時間區段都不符合，則傳送出去
                            if (!time1_OK && !time2_OK) {
                                String msg = "★地圖限制時間【" + starttime1 + "】到【" + endtime1 + "】";
                                if (starttime2 > 0 || endtime2 > 0) {
                                    msg += " 及 【" + starttime2 + "】到【" + endtime2 + "】";
                                }
                                tgpc.sendPackets(new S_ServerMessage(166, msg));
                                L1Teleport.teleport(tgpc, 33440, 32802, (short)4, 0, true);
                            }
                        }

                        tgpc.onChangeLawful();
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            LawfulTimer._log.error("Lawful更新處理時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            LawfulTimer lawfulTimer = new LawfulTimer();
            lawfulTimer.start();
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
            else if (tgpc.isTeleport()) {
                return false;
            }
            else if (tgpc.isDead()) {
                return false;
            }
            else if (tgpc.getCurrentHp() <= 0) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
