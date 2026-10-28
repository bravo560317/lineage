package com.lineage.data.item_etcitem.teleport;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.skill.L1BuffUtil;
import java.util.Calendar;
import com.lineage.server.datatables.ItemTeleportTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class SOR_UserSet extends ItemExecutor
{
    private int type1;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(SOR_UserSet.class);
    }
    
    public static ItemExecutor get() {
        return new SOR_UserSet();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.hasSkillEffect(230)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else if (pc.hasSkillEffect(4017)) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else if (this.type1 > 0 && pc.getMapId() != this.type1) {
            pc.sendPackets(new S_ServerMessage(1413));
            return;
        }
        else {
            ItemTeleportTable.TeleportList list = ItemTeleportTable.get().getLoc(item.getItemId());
            if (list != null) {
                int week = list.getWeek();
                int starttime = list.getStarttime();
                int unitytime = list.getUnitytime();
                Calendar date = Calendar.getInstance();
                int nowWeek = date.get(7) - 1;
                int nowHour = date.get(11);
                if (nowWeek == 0) {
                    nowWeek += 7;
                }
                if (week > 0 && starttime < 0 && unitytime < 0) {
                    if (week != nowWeek) {
                        pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【" + week + " 】"));
                        return;
                    }
                }
                else if (week > 0 && starttime >= 0 && unitytime >= 0) {
                    if (week != nowWeek || nowHour < starttime || nowHour >= unitytime) {
                        pc.sendPackets(new S_ServerMessage(166, "使用時間限制:星期【 " + week + " 】時間為【 " + starttime + " 】點，至【 " + unitytime + "】點"));
                        return;
                    }
                }
                else if (week < 0 && starttime >= 0 && unitytime >= 0 && (nowHour < starttime || nowHour >= unitytime)) {
                    pc.sendPackets(new S_ServerMessage(166, "使用時間限制:時間為【 " + starttime + " 】點，至【 " + unitytime + "】點"));
                    return;
                }
                int locX = list.getLocX();
                int locY = list.getLocY();
                short mapId = list.getMapId();
                if (pc.getMap().isEscapable()) {
                    if (pc.isActived()) {
                        pc.setActived(false);
                        pc.killSkillEffectTimer(8853);
                        pc.sendPackets(new S_ServerMessage("掛機中請勿使用手動卷軸。"));
                        pc.sendPackets(new S_ServerMessage("自動狩獵已停止。"));
                        if (pc.getAutoX() > 0) {
                            pc.setAutoX(0);
                            pc.setAutoY(0);
                            pc.setAutoMap(0);
                        }
                    }
                    pc.getInventory().removeItem(item, 1L);
                    L1BuffUtil.cancelAbsoluteBarrier(pc);
                    SOR_UserSet.TeleportRunnable runnable = new SOR_UserSet.TeleportRunnable(pc, locX, locY, (int)mapId);
                    GeneralThreadPool.get().schedule(runnable, 0L);
                }
                else {
                    pc.sendPackets(new S_ServerMessage(276));
                    pc.sendPackets(new S_Paralysis(7, false));
                }
                try {
                    Thread.sleep(1500L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int time = list.getTime();
                if (time > 0) {
                    pc.get_other().set_usemap((int)mapId);
                    ServerUseMapTimer.put(pc, time);
                    pc.sendPackets(new S_ServerMessage("使用時間限制:" + time + "秒"));
                }
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this.type1 = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
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
            catch (InterruptedException e) {
                SOR_UserSet._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
