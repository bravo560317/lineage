package com.lineage.server.timecontroller.pc;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_MagicEquipment;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.ScheduledFuture;
import java.util.TimerTask;

public class ItemTimer extends TimerTask
{
    private ScheduledFuture<?> _timer;
    private L1ItemInstance _item;
    private int _skillid;
    private int _time;
    private boolean _isend;
    
    public ItemTimer() {
        this._isend = false;
    }
    
    private void checkIcon(L1ItemInstance item, int skillid, int time) {
        int gfx = 0;
        switch (skillid) {
            case 21: {
                gfx = 748;
                break;
            }
        }
        if (gfx == 0) {
            return;
        }
        else {
            L1PcInstance pc = World.get().getPlayer(item.get_char_objid());
            if (pc != null) {
                S_MagicEquipment packet = new S_MagicEquipment(time, gfx);
                pc.sendPacketsAll(packet);
            }
            return;
        }
    }
    
    public void start(L1ItemInstance item, int skillid, int time) {
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
        this._item = item;
        this._skillid = skillid;
        this._time = time;
        this._item.addskill(this._skillid, this._time);
        this.checkIcon(this._item, this._skillid, this._time);
    }
    
    @Override
    public void run() {
        try {
            if (this._item == null) {
                this._isend = true;
                return;
            }
            else {
                --this._time;
                if (this._time <= 0) {
                    this.checkIcon(this._item, this._skillid, 0);
                    this._item.removeskill(this._skillid);
                    this._isend = true;
                }
            }
        }
        catch (Exception e) {
            this._item.removeskill(this._skillid);
            GeneralThreadPool.get().cancel(this._timer, false);
            ItemTimer itemTimer = new ItemTimer();
            itemTimer.start(this._item, this._skillid, this._time);
        }
        finally {
            if (this._isend) {
                GeneralThreadPool.get().cancel(this._timer, false);
            }
        }
    }
}
