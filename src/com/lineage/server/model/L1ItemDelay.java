package com.lineage.server.model;

import com.lineage.server.templates.L1EtcItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ItemDelay
{
    private static final Log _log;
    public static final int WEAPON = 500;
    public static final int ARMOR = 501;
    public static final int ITEM = 502;
    public static final int POLY = 503;
    
    static {
        _log = LogFactory.getLog(L1ItemDelay.class);
    }
    
    public static void onItemUse(L1PcInstance pc, int delayId, int delayTime) {
        try {
            if (delayId != 0 && delayTime != 0) {
                L1ItemDelay.ItemDelayTimer timer = new L1ItemDelay.ItemDelayTimer(pc, delayId, delayTime);
                pc.addItemDelay(delayId, timer);
                GeneralThreadPool.get().schedule(timer, (long)delayTime);
            }
        }
        catch (Exception e) {
            L1ItemDelay._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void onItemUse(ClientExecutor client, L1ItemInstance item) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (pc != null) {
                onItemUse(pc, item);
            }
        }
        catch (Exception e) {
            L1ItemDelay._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void onItemUse(L1PcInstance pc, L1ItemInstance item) {
        try {
            int delayId = 0;
            int delayTime = 0;
            switch (item.getItem().getType2()) {
                case 0: {
                    delayId = ((L1EtcItem)item.getItem()).get_delayid();
                    delayTime = ((L1EtcItem)item.getItem()).get_delaytime();
                    break;
                }
                case 1: {
                    return;
                }
                case 2: {
                    switch (item.getItemId()) {
                        case 20062:
                        case 20077:
                        case 120077: {
                            if (item.isEquipped() && !pc.isInvisble()) {
                                pc.beginInvisTimer();
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        default: {
                            return;
                        }
                    }
                }
            }
            if (delayId != 0 && delayTime != 0) {
                L1ItemDelay.ItemDelayTimer timer = new L1ItemDelay.ItemDelayTimer(pc, delayId, delayTime);
                pc.addItemDelay(delayId, timer);
                GeneralThreadPool.get().schedule(timer, (long)delayTime);
            }
        }
        catch (Exception e) {
            L1ItemDelay._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    static class ItemDelayTimer implements Runnable
    {
        private int _delayId;
        private int _delayTime;
        private L1Character _cha;
        
        public ItemDelayTimer(L1Character cha, int id, int time) {
            this._cha = cha;
            this._delayId = id;
            this._delayTime = time;
        }
        
        @Override
        public void run() {
            this.stopDelayTimer(this._delayId);
        }
        
        public int get_delayTime() {
            return this._delayTime;
        }
        
        public void stopDelayTimer(int delayId) {
            this._cha.removeItemDelay(delayId);
        }
    }
}
