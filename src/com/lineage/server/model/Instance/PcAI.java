package com.lineage.server.model.Instance;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.datatables.SprTable;
import java.util.Iterator;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.clientpackets.AcceleratorChecker;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.types.Point;
import com.lineage.config.ConfigGuaji;
import java.util.Calendar;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.thread.NpcAiThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.server.model.L1Character;

public class PcAI implements Runnable
{
    private static final Log _log;
    private static Random _random;
    private final L1PcInstance _pc;
    private long _simpleNoTargetSince;
    protected int[][] DIR_TABLE;
    
    static {
        _log = LogFactory.getLog(PcAI.class);
        PcAI._random = new Random();
    }
    
    public PcAI(L1PcInstance pc) {
        this.DIR_TABLE = new int[][] { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };
        this._pc = pc;
        this._simpleNoTargetSince = System.currentTimeMillis();
    }
    
    public void startAI() {
        NpcAiThreadPool.get().execute(this);
    }
    
    @Override
    public void run() {
        try {
            while (this._pc.getMaxHp() > 0) {
                if (this.AIProcess()) {
                    break;
                }
                else {
                    try {
                        Thread.sleep((long)this.getRightInterval(2));
                    }
                    catch (Exception e) {
                        break;
                    }
                }
            }
            do {
                try {
                    Thread.sleep((long)(this.getRightInterval(1) + 15));
                }
                catch (Exception e) {
                    break;
                }
            } while (this._pc.isDead());
            this._pc.allTargetClear();
            this._pc.setAiRunning(false);
            this._pc.setActived(false);
            Thread.sleep(10L);
        }
        catch (Exception e) {
            PcAI._log.error("pcAI發生例外狀況: " + this._pc.getName(), e);
        }
    }
    
    private boolean AIProcess() {
        try {
			
			        // ===== 地圖切換停止掛機 =====
        if (this._pc.getAutoMap() > 0 && this._pc.getMapId() != this._pc.getAutoMap()) {
            this._pc.setActived(false);
            this._pc.sendPackets(new S_ServerMessage("地圖切換，自動停止掛機。"));
            return true;
        }

            if (this._pc.isDead()) {
                return true;
            }
            else if (this._pc.getOnlineStatus() == 0) {
                return true;
            }
            else if (this._pc.getCurrentHp() <= 0) {
                return true;
            }
            else if (!this._pc.isActived()) {
                return true;
            }
            else if (this.ConfirmTheEnemy(this._pc)) {
                return true;
            }
            else if (this._pc.isInvisble()) {
                this._pc.setActived(false);
                this._pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                this._pc.setAutoX(0);
                this._pc.setAutoY(0);
                this._pc.setAutoMap(0);
                this._pc.killSkillEffectTimer(8853);
                PcAI.TeleportRunnable runnable = new PcAI.TeleportRunnable(this._pc, 33440, 32802, 4);
                GeneralThreadPool.get().schedule(runnable, 0L);
                return true;
            }
            else if (this._pc.getInventory().getWeight240() >= 197) {
                this._pc.sendPackets(new S_ServerMessage(110));
                this._pc.setActived(false);
                this._pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                this._pc.setAutoX(0);
                this._pc.setAutoY(0);
                this._pc.setAutoMap(0);
                this._pc.killSkillEffectTimer(8853);
                PcAI.TeleportRunnable runnable = new PcAI.TeleportRunnable(this._pc, 33440, 32802, 4);
                GeneralThreadPool.get().schedule(runnable, 0L);
                return true;
            }
            else if (this._pc.getWeapon() == null) {
                this._pc.sendPackets(new S_ServerMessage("\\fU沒拿武器，自動關閉掛機！"));
                this._pc.setActived(false);
                this._pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                this._pc.setAutoX(0);
                this._pc.setAutoY(0);
                this._pc.setAutoMap(0);
                this._pc.killSkillEffectTimer(8853);
                return true;
            }
            else {
                Calendar date = Calendar.getInstance();
                int nowHour = date.get(11);
                if (ConfigGuaji.checktimeguaji) {
                    int[] GUAJI_TIME = ConfigGuaji.GUAJI_ITEM;
                    int[] array;
                    int length = (array = GUAJI_TIME).length;
                    int i = 0;
                    while (i < length) {
                        int guajitimme = array[i];
                        if (nowHour == guajitimme) {
                            this._pc.setActived(false);
                            this._pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                            PcAI.TeleportRunnable runnable2 = new PcAI.TeleportRunnable(this._pc, 33440, 32802, 4);
                            GeneralThreadPool.get().schedule(runnable2, 0L);
                            this._pc.setAutoX(0);
                            this._pc.setAutoY(0);
                            this._pc.setAutoMap(0);
                            this._pc.killSkillEffectTimer(8853);
                            return true;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                if (!this._pc.getMap().isGuaji() && this._pc.isActived()) {
                    this._pc.setActived(false);
                    this._pc.setAutoX(0);
                    this._pc.setAutoY(0);
                    this._pc.setAutoMap(0);
                    this._pc.sendPackets(new S_ServerMessage("此地圖不能掛機"));
                    this._pc.killSkillEffectTimer(8853);
                    return true;
                }
                else {
                    if (!this._pc.isSimpleAutoHunt() && this._pc.getAu_AutoSet(0) > 0 && this._pc.getAu_AutoSet(1) > 0 && this._pc.get_followmaster() == null && this._pc.getAutoX() > 0 && this._pc.getAutoY() > 0 && this._pc.getLocation().getTileLineDistance(new Point(this._pc.getAutoX(), this._pc.getAutoY())) > this._pc.getAu_AutoSet(1)) {
                        L1Teleport.teleport(this._pc, this._pc.getAutoX(), this._pc.getAutoY(), this._pc.getMapId(), 0, false);
                        if (this._pc.isPathfinding()) {
                            this._pc.setrandomMoveDirection(PcAI._random.nextInt(7));
                        }
                        this._pc.targetClear();
                    }
                    int result = this._pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.MOVE);
                    if (result == 2) {
                        PcAI._log.error("要求角色移動:速度異常(" + this._pc.getName() + ")");
                    }
this._pc.checkTarget();
boolean searchTarget = true;

// ==========================================
// [新增] 高寵優先鎖定主人目標 (終極雷達修正版)
// ==========================================
if (this._pc.get_followmaster() != null && this._pc.getfollowatk()) {
    L1PcInstance master = this._pc.get_followmaster();
    
    if (master != null) {
        // 1. 先嘗試讀取主人的內掛目標
        L1Character masterTarget = master.is_now_target();
        
        // 2. 如果主人沒開內掛，啟動雷達掃描附近怪物
        if (masterTarget == null) {
            for (L1Object obj : World.get().getVisibleObjects(master, 10)) { 
                if (obj instanceof com.lineage.server.model.Instance.L1NpcInstance) {
                    com.lineage.server.model.Instance.L1NpcInstance npc = (com.lineage.server.model.Instance.L1NpcInstance) obj;
                    
                    // 【關鍵修正】取得這隻怪物的「最高仇恨對象」
                    L1Character hateTarget = npc.getHateList().getMaxHateCharacter();
                    
                    // 如果這隻怪最恨的人是「主人」，高寵立刻把這隻怪設為目標！
                    if (hateTarget != null && hateTarget.getId() == master.getId()) {
                        masterTarget = npc;
                        break; // 找到就跳出迴圈
                    }
                }
            }
        }
        
        // 3. 執行目標綁定
        if (masterTarget != null && !masterTarget.isDead()) {
            this._pc.set_now_target(masterTarget); 
        } else {
            this._pc.allTargetClear();
            searchTarget = false; 
        }
    }
}
// ==========================================

if (this._pc.is_now_target() != null) {
    searchTarget = false;
}
if (searchTarget) {
    this._pc.searchTarget();
}

if (this._pc.isSimpleAutoHunt()) {
    if (this._pc.is_now_target() != null) {
        this._simpleNoTargetSince = System.currentTimeMillis();
    }
    else if (System.currentTimeMillis() - this._simpleNoTargetSince >= 30000L) {
        L1Teleport.randomTeleportai(this._pc);
        this._simpleNoTargetSince = System.currentTimeMillis();
    }
}

                    if (this._pc.is_now_target() == null) {
                        if (!this._pc.isPathfinding()) {
                            this._pc.setrandomMoveDirection(PcAI._random.nextInt(8));
                        }
                        this._pc.noTarget();
                        Thread.sleep(50L);
                        return false;
                    }
                    else {
                        this._pc.onTarget();
                        if (this._pc.isPathfinding()) {
                            this._pc.setPathfinding(false);
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (Exception e) {
            PcAI._log.error("pcAI發生例外狀況: " + this._pc.getName(), e);
        }
        return false;
    }
    
    private boolean ConfirmTheEnemy(L1PcInstance pc) {
        try {
            if (!pc.isSimpleAutoHunt() && pc.getAu_OtherSet(2) > 0 && pc.getAu_AutoSet(0) == 0 && pc.getMap().isTeleportable()) {
                for (L1Object objid : World.get().getVisibleObjects(pc, 5)) {
                    if (objid instanceof L1PcInstance) {
                        L1PcInstance _pc1 = (L1PcInstance)objid;
                        if (pc.isInEnemyList(_pc1.getName())) {
                            L1Teleport.randomTeleportai(pc);
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            PcAI._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private int getRightInterval(int type) {
        int interval = 0;
        switch (type) {
            case 1: {
                interval = SprTable.get().getAttackSpeed(this._pc.getTempCharGfx(), this._pc.getCurrentWeapon() + 1);
                break;
            }
            case 2: {
                interval = SprTable.get().getMoveSpeed(this._pc.getTempCharGfx(), this._pc.getCurrentWeapon());
                break;
            }
            default: {
                return 0;
            }
        }
        return this.intervalR(type, interval);
    }
    
    private int intervalR(int type, int interval) {
        try {
            if (this._pc.isHaste()) {
                interval = (int)((double)interval * 0.755);
            }
            if (type == 2 && this._pc.isFastMovable()) {
                interval = (int)((double)interval * 0.755);
            }
            if (this._pc.isBrave()) {
                interval = (int)((double)interval * 0.755);
            }
            if (this._pc.isElfBrave()) {
                interval = (int)((double)interval * 0.855);
            }
            if (type == 1 && this._pc.isElfBrave()) {
                interval = (int)((double)interval * 0.9);
            }
            if (this._pc.isBraveX()) {
                interval = (int)((double)interval * 0.755);
            }
        }
        catch (Exception e) {
            PcAI._log.error(e.getLocalizedMessage(), e);
        }
        return interval;
    }
    
    private class TeleportRunnable implements Runnable
    {
        private final L1PcInstance _pc;
        private int _locX;
        private int _locY;
        private int _mapid;
        
        public TeleportRunnable(L1PcInstance pc, int x, int y, int mapid) {
            this._locX = 0;
            this._locY = 0;
            this._mapid = 0;
            this._pc = pc;
            this._locX = x;
            this._locY = y;
            this._mapid = mapid;
        }
        
        @Override
        public void run() {
            try {
                L1Map map = L1WorldMap.get().getMap((short)this._mapid);
                int r = 10;
                int tryCount = 0;
                int newX = this._locX;
                int newY = this._locY;
                do {
                    ++tryCount;
                    newX = this._locX + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                    newY = this._locY + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                    if (map.isPassable(newX, newY, this._pc)) {
                        break;
                    }
                    else {
                        Thread.sleep(1L);
                    }
                } while (tryCount < 5);
                if (tryCount >= 5) {
                    L1Teleport.teleport(this._pc, this._locX, this._locY, (short)this._mapid, this._pc.getHeading(), true);
                }
                else {
                    L1Teleport.teleport(this._pc, newX, newY, (short)this._mapid, this._pc.getHeading(), true);
                }
            }
            catch (InterruptedException ex) {}
        }
    }
}
