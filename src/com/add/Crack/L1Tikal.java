package com.add.Crack;

import java.util.Timer;
import java.util.TimerTask;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.add.L1Config_other;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.server.utils.collections.Lists;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import java.util.logging.Logger;

public class L1Tikal
{
    private static Logger _log;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_AGAIN = 3;
    public static final int STATUS_END = 4;
    private static List<L1PcInstance> playerList;
    public static int _status;
    private static final int END_STATUS_WINNER = 1;
    private static final int END_STATUS_NOWINNER = 2;
    public static int _type;
    
    static {
        L1Tikal._log = Logger.getLogger(L1Tikal.class.getName());
        L1Tikal.playerList = (List)Lists.newArrayList();
        L1Tikal._status = 0;
        L1Tikal._type = 0;
    }
    
    public static void enterGame(L1PcInstance pc, int npcid) {
        L1Object obj = World.get().findObject(npcid);
        L1NpcInstance npc = (L1NpcInstance)obj;
        if (!L1CrackTime.getStart().getGateOpen()) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tikalgate2"));
            return;
        }
        else if (!pc.getInventory().checkItem(49273, 1L)) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tikalgate3"));
            return;
        }
        else if (getGameStatus() == 2) {
            if (L1Tikal.playerList.contains(pc)) {
                L1Teleport.teleport(pc, 32730, 32866, (short)784, 2, true);
            }
            else {
                pc.sendPackets(new S_SystemMessage("副本已開始，無法進入。"));
            }
            return;
        }
        else if (getGameStatus() == 3) {
            pc.sendPackets(new S_SystemMessage("副本正在重置中。"));
            return;
        }
        else if (getGameStatus() == 4) {
            pc.sendPackets(new S_SystemMessage("BOSS已死亡，無法進入。"));
            return;
        }
        else if (L1Tikal.playerList.size() >= L1Config_other._2213) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tikalgate4"));
            return;
        }
        else {
            if (getGameStatus() == 0) {
                L1Teleport.teleport(pc, 32730, 32866, (short)784, 2, true);
                pc.getInventory().consumeItem(49273, 1L);
                addPlayerList(pc);
            }
            if (getGameStatus() == 1 && L1Tikal.playerList.size() < L1Config_other._2213) {
                L1Teleport.teleport(pc, 32730, 32866, (short)784, 2, true);
                pc.getInventory().consumeItem(49273, 1L);
                addPlayerList(pc);
            }
            return;
        }
    }
    
    private static void addPlayerList(L1PcInstance pc) {
        if (!L1Tikal.playerList.contains(pc)) {
            L1Tikal.playerList.add(pc);
        }
        if (L1Tikal.playerList.size() == L1Config_other._2211) {
            if (getGameStatus() == 0) {
                setGameStatus(1);
                for (L1Object object7 : World.get().getVisibleObjects(784).values()) {
                    Object obj = object7;
                    if (obj instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)obj;
                        tgpc.sendPackets(new S_SystemMessage("時空裂痕副本將在5秒後開始。"));
                    }
                }
                OverTime(5);
            }
        }
        else if (L1Tikal.playerList.size() < L1Config_other._2211) {
            pc.sendPackets(new S_SystemMessage("需要有至少 " + L1Config_other._2211 + " 人以上參加者才可能開始副本。"));
        }
    }
    
    public static void removePlayerList(L1PcInstance pc) {
        if (L1Tikal.playerList.contains(pc)) {
            L1Tikal.playerList.remove(pc);
            L1Teleport.teleport(pc, 33442, 32797, (short)4, 5, true);
        }
    }
    
    public static void setGameStatus(int i) {
        L1Tikal._status = i;
    }
    
    private static int getGameStatus() {
        return L1Tikal._status;
    }
    
    private static void setGameEnd(int type) {
        switch (type) {
            case 1: {
                sendEndMessage(type);
                break;
            }
            case 2: {
                sendEndMessage(type);
                break;
            }
        }
        L1Tikal._type = type;
    }
    
    public static int getGameEnd() {
        return L1Tikal._type;
    }
    
    private static void startAgain() {
        new L1Tikal.Again().begin();
    }
    
    private static void startAgain2() {
        new L1Tikal.Again2().begin();
    }
    
    private static void sendEndMessage(int type) {
        if (type == 1) {
            for (L1PcInstance pc : L1Tikal.playerList) {
                pc.sendPackets(new S_ServerMessage(1488));
            }
        }
        else if (type == 2) {
            for (L1PcInstance pc : L1Tikal.playerList) {
                pc.sendPackets(new S_ServerMessage(1490));
            }
        }
        Overtime2(type);
    }
    
    private static void Overtime2(int type) {
        int time = 40;
        try {
            while (time >= 0) {
                Thread.sleep(1000L);
                for (L1PcInstance pc : L1Tikal.playerList) {
                    if (time == 35 && type == 1) {
                        pc.sendPackets(new S_ServerMessage(1489));
                    }
                    else if (time == 30) {
                        pc.sendPackets(new S_ServerMessage(1476));
                    }
                    else if (time == 20) {
                        pc.sendPackets(new S_ServerMessage(1477));
                    }
                    else if (time == 10) {
                        pc.sendPackets(new S_ServerMessage(1478));
                    }
                    else if (time == 5) {
                        pc.sendPackets(new S_ServerMessage(1480));
                    }
                    else if (time == 4) {
                        pc.sendPackets(new S_ServerMessage(1481));
                    }
                    else if (time == 3) {
                        pc.sendPackets(new S_ServerMessage(1482));
                    }
                    else if (time == 2) {
                        pc.sendPackets(new S_ServerMessage(1483));
                    }
                    else if (time == 1) {
                        pc.sendPackets(new S_ServerMessage(1484));
                    }
                    else if (time == 0 && type == 2) {
                        setGameStatus(3);
                        L1Tikal.playerList.clear();
                        startAgain();
                    }
                    else {
                        continue;
                    }
                }
                --time;
            }
        }
        catch (Exception e) {
            L1Tikal._log.warning(e.getMessage());
        }
    }
    
    public static void OverTime(int time) {
        try {
            while (time >= 0) {
                Thread.sleep(1000L);
                --time;
                for (L1PcInstance pc : L1Tikal.playerList) {
                    if (time == 4) {
                        pc.sendPackets(new S_ServerMessage(1485));
                    }
                    else if (time == 3) {
                        pc.sendPackets(new S_ServerMessage(1486));
                    }
                    else if (time == 2) {
                        pc.sendPackets(new S_ServerMessage(1487));
                    }
                    else {
                        continue;
                    }
                }
                if (time == 1) {
                    L1SpawnUtil.spawn(92000, 32751, 32871, (short)784, 0);
                    L1SpawnUtil.spawn(92001, 32750, 32860, (short)784, 0);
                    startSeekBossSecond();
                }
            }
        }
        catch (Exception e) {
            L1Tikal._log.warning(e.getMessage());
        }
    }
    
    private static void startSeekBossSecond() {
        new L1Tikal.SeekBossSecond().begin();
    }
    
    private static int MobCount(short mapId) {
        int MobCount = 0;
        for (L1Object object8 : World.get().getVisibleObjects((int)mapId).values()) {
            Object obj = object8;
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (mob != null && !mob.isDead()) {
                    ++MobCount;
                }
                else {
                    continue;
                }
            }
        }
        return MobCount;
    }
    
    private static int PcCount(short mapId) {
        int PcCount = 0;
        for (L1Object object9 : World.get().getVisibleObjects((int)mapId).values()) {
            Object obj = object9;
            if (obj instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)obj;
                if (pc != null && !pc.isDead()) {
                    ++PcCount;
                }
                else {
                    continue;
                }
            }
        }
        return PcCount;
    }
    
    private static class Again extends TimerTask
    {
        @Override
        public void run() {
            L1Tikal.setGameStatus(0);
            for (L1Object object10 : World.get().getVisibleObjects(784).values()) {
                Object obj = object10;
                if (obj instanceof L1NpcInstance) {
                    L1NpcInstance npc = (L1NpcInstance)obj;
                    npc.deleteMe();
                }
            }
            this.cancel();
        }
        
        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, (long)(L1Config_other._2212 * 60 * 1000));
        }
    }
    
    private static class Again2 extends TimerTask
    {
        @Override
        public void run() {
            L1Tikal.setGameStatus(0);
            for (L1Object object11 : World.get().getVisibleObjects(784).values()) {
                Object obj = object11;
                if (obj instanceof L1NpcInstance) {
                    L1NpcInstance npc = (L1NpcInstance)obj;
                    npc.deleteMe();
                }
            }
            this.cancel();
        }
        
        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, (long)(L1Config_other._2218 * 60 * 1000));
        }
    }
    
    private static class SeekBossSecond extends TimerTask
    {
        @Override
        public void run() {
            if (MobCount((short)784) == 0) {
                setGameEnd(1);
            }
            else if (PcCount((short)784) == 0) {
                setGameEnd(2);
            }
            else {
                startSeekBossSecond();
            }
            this.cancel();
        }
        
        public void begin() {
            Timer timer = new Timer();
            timer.schedule(this, 5000L);
        }
    }
}
