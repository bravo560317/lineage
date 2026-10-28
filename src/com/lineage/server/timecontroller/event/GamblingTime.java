package com.lineage.server.timecontroller.event;

import java.util.Collection;
import com.lineage.server.serverpackets.S_NpcChatShouting;
import com.lineage.server.model.Instance.L1GamblingInstance;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.model.Instance.L1GamInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1DoorInstance;
import java.util.Iterator;
import com.lineage.server.templates.L1Gambling;
import com.lineage.data.event.gambling.GamblingNpc;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.data.event.GamblingSet;
import com.lineage.server.datatables.lock.GamblingReading;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.Random;
import com.lineage.data.event.gambling.Gambling;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class GamblingTime extends TimerTask
{
    private static final Log _log;
    private static Gambling _gambling;
    private static double rate;
    private static boolean _issystem;
    private static boolean _isStart;
    private static int _gamblingNo;
    private static Random _random;
    private ScheduledFuture<?> _timer;
    private static final String[] _msg;
    
    static {
        _log = LogFactory.getLog(GamblingTime.class);
        GamblingTime.rate = 0.0;
        GamblingTime._issystem = true;
        GamblingTime._isStart = false;
        GamblingTime._gamblingNo = 100;
        GamblingTime._random = new Random();
        _msg = new String[] { "剛剛喝到的是綠水嗎?等等跑就知道~^^~", "隔壁跑道聽說昨天踩到釘子.....", "買我啦!!看我的臉就知道我贏!!", "快點跑完我也想去打一下副本~~", "你在看我嗎??你可以在靠近一點...", "飛龍都不一定跑贏我!看我強壯的雞腿!", "那個誰誰誰!!等等不要跑超過我黑...", "有沒騎士在場阿?給瓶勇水喝喝~~", "地球是很危險的...", "誰給我來一下祝福!加持!加持~", "咦~~有一個參賽者是傳說中的跑道之王...", "沒事!沒事!!哥只是個傳說~~", "隔壁的~你剛剛喝什麼?你是不是作弊??", "肚子好餓...沒吃飯能贏嗎??", "哇靠~~今天感覺精力充沛耶!!", "隔壁的!!你控制一下不要一直放屁!!", "嗯......嗯......其他幾個是憋三,我會贏....", "我剛剛好像喝多了...頭還在暈...", "昨晚的妞真正丫，喝綠水算三小。", "肚子餓死了，跑不動了。", "輸贏都行啦，娛樂而已。", "小賭怡情，大賭傷身。", "我要放點水。經常贏都有點不好意思了。", "【強化勇氣的藥水】是幹嘛的？我這有一罐。", "昨晚被吵死了，現在都覺得好累。", "阿幹....不要看我啦!!會影響我心情!!", "說什麼呢~~你們不想我贏阿!!!", "小賭可以養家活口!!大賭可以興邦建國!!", "賭是不好的....不賭是萬萬不行的...." };
    }
    
    public void start() {
        GamblingTime._gamblingNo = GamblingReading.get().maxId();
        int timeMillis = GamblingSet.GAMADENATIME * 60 * 1000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    public static int get_gamblingNo() {
        return GamblingTime._gamblingNo;
    }
    
    public static Gambling get_gambling() {
        return GamblingTime._gambling;
    }
    
    public static boolean isStart() {
        return GamblingTime._isStart;
    }
    
    public static void set_status(boolean b) {
        GamblingTime._issystem = b;
    }
    
    @Override
    public void run() {
        Label_0750: {
            try {
                if (GamblingTime._issystem) {
                    if (GamblingTime._gambling == null) {
                        if (!ServerRestartTimer.isRtartTime()) {
                            this.doorOpen(false);
                            long previous = 0L;
                            for (L1PcInstance listner : World.get().getAllPlayers()) {
                                if (!listner.isShowWorldChat()) {
                                    continue;
                                }
                                else {
                                    listner.sendPackets(new S_ServerMessage("奇岩賭場比賽即將開始"));
                                }
                            }
                            (GamblingTime._gambling = new Gambling()).set_gmaNpc(previous);
                            boolean is5m = true;
                            int timeS = 300;
                            while (is5m) {
                                Thread.sleep(1000L);
                                switch (timeS) {
                                    case 0: {
                                        this.doorOpen(true);
                                        this.npcChat(0, 3, null);
                                        GamblingTime._gambling.startGam();
                                        is5m = false;
                                        break;
                                    }
                                    case 1: {
                                        GamblingTime._isStart = true;
                                        this.npcChat(0, 2, null);
                                        break;
                                    }
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                    case 7:
                                    case 8:
                                    case 9:
                                    case 10: {
                                        this.npcChat(timeS, 1, null);
                                        break;
                                    }
                                    case 60:
                                    case 120:
                                    case 180:
                                    case 240:
                                    case 300: {
                                        this.npcChat(timeS / 60, 0, null);
                                        break;
                                    }
                                }
                                --timeS;
                            }
                            GamblingTime._gambling.set_allRate();
                            Thread.sleep(2000L);
                            for (GamblingNpc gamblingNpc : GamblingTime._gambling.get_allNpc().values()) {
                                this.npcChat(0, 4, gamblingNpc);
                                Thread.sleep(1000L);
                            }
                            while (GamblingTime._gambling.get_oneNpc() == null) {
                                Thread.sleep(100L);
                            }
                            if (GamblingTime._gambling.get_oneNpc() != null) {
                                GamblingNpc one = GamblingTime._gambling.get_oneNpc();
                                this.npcChat(0, 5, one);
                                int npcid = GamblingTime._gambling.get_oneNpc().get_npc().getNpcId();
                                double rate = GamblingTime._gambling.get_oneNpc().get_rate();
                                long adena = GamblingTime._gambling.get_allAdena();
                                int outcount = (int)(GamblingTime._gambling.get_oneNpc().get_adena() / (long)GamblingSet.GAMADENA);
                                L1Gambling gambling = new L1Gambling();
                                gambling.set_id(GamblingTime._gamblingNo);
                                gambling.set_adena(adena);
                                gambling.set_rate(rate);
                                gambling.set_gamblingno(String.valueOf(GamblingTime._gamblingNo) + "-" + npcid);
                                gambling.set_outcount(outcount);
                                GamblingReading.get().add(gambling);
                            }
                            synchronized (this) {
                                ++GamblingTime._gamblingNo;
                            }
                            Thread.sleep(20000L);
                            break Label_0750;
                        }
                    }
                }
                return;
            }
            catch (Exception e) {
                GamblingTime._log.error("奇岩賭場時間軸異常重啟", e);
                GeneralThreadPool.get().cancel(this._timer, false);
                GamblingTime timerTask = new GamblingTime();
                timerTask.start();
                return;
            }
            finally {
                if (GamblingTime._gambling == null) {
                    return;
                }
                else {
                    GamblingTime._gambling.delAllNpc();
                    GamblingTime._gambling.clear();
                    GamblingTime._gambling = null;
                    if (!GamblingTime._isStart) {
                        return;
                    }
                    else {
                        GamblingTime._isStart = false;
                    }
                }
            }
        }
    }
    
    private void doorOpen(boolean isOpen) {
        L1DoorInstance.openGam(isOpen);
    }
    
    private void npcChat(int i, int mode, GamblingNpc gamblingNpc) {
        Collection<L1Object> allObj = World.get().getObject();
        for (L1Object obj : allObj) {
            if (!(obj instanceof L1GamInstance)) {
                continue;
            }
            else {
                L1GamInstance npc = (L1GamInstance)obj;
                switch (mode) {
                    case 0: {
                        if (GamblingTime._random.nextInt(100) + 1 < 80) {
                            String msg = GamblingTime._msg[GamblingTime._random.nextInt(GamblingTime._msg.length)];
                            npc.broadcastPacketX10(new S_NpcChat(npc, msg));
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        for (L1Object obj : allObj) {
            if (!(obj instanceof L1GamblingInstance)) {
                continue;
            }
            else {
                L1GamblingInstance npc2 = (L1GamblingInstance)obj;
                int npcId = npc2.getNpcId();
                switch (mode) {
                    case 0: {
                        if (npcId == 70041) {
                            npc2.broadcastPacketX10(new S_NpcChatShouting(npc2, "$376 " + i + "$377"));
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    case 1: {
                        if (npcId == 70041) {
                            npc2.broadcastPacketX10(new S_NpcChatShouting(npc2, String.valueOf(i)));
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    case 2: {
                        if (npcId == 70041) {
                            npc2.broadcastPacketX10(new S_NpcChatShouting(npc2, "$363"));
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    case 3: {
                        if (npcId == 70041) {
                            npc2.broadcastPacketX10(new S_NpcChatShouting(npc2, "$364"));
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    case 4: {
                        if (npcId == 70041) {
                            gamblingNpc.get_npc().getNameId();
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    case 5: {
                        if (npcId == 70041) {
                            String onename = gamblingNpc.get_npc().getNameId();
                            npc2.broadcastPacketX10(new S_NpcChatShouting(npc2, "$375 " + GamblingTime._gamblingNo + "優勝者: " + onename + " [賠率]:" + GamblingTime._gambling.get_oneNpc().get_rate()));
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
    }
}
