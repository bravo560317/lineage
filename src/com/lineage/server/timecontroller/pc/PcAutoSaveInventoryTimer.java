package com.lineage.server.timecontroller.pc;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.Config;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.echo.ClientExecutor;
import com.lineage.list.OnlineUser;
import com.lineage.server.thread.PcOtherThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class PcAutoSaveInventoryTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(PcAutoSaveInventoryTimer.class);
    }
    
    public void start() {
        int timeMillis = 60000;
        this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 60000L, 60000L);
    }
    
    @Override
    public void run() {
        try {
            Collection<ClientExecutor> allClien = OnlineUser.get().all();
            if (allClien.isEmpty()) {
                return;
            }
            else {
                for (ClientExecutor client : allClien) {
                    int time = client.get_saveInventory();
                    if (time == -1) {
                        continue;
                    }
                    else {
                        --time;
                        this.save(client, time);
                    }
                }
            }
        }
        catch (Exception e) {
            PcAutoSaveInventoryTimer._log.error("背包物品自動保存時間軸異常重啟", e);
            PcOtherThreadPool.get().cancel(this._timer, false);
            PcAutoSaveInventoryTimer restart = new PcAutoSaveInventoryTimer();
            restart.start();
        }
    }
    
    private void save(ClientExecutor client, Integer time) {
        try {
            if (client.get_socket() == null) {
                return;
            }
            else if (time > 0) {
                client.set_saveInventory(time);
            }
            else {
                client.set_saveInventory(Config.AUTOSAVE_INTERVAL_INVENTORY);
                L1PcInstance pc = client.getActiveChar();
                if (pc != null) {
                    pc.saveInventory();
                }
            }
        }
        catch (Exception e) {
            PcAutoSaveInventoryTimer._log.debug("執行背包資料存檔處理異常 帳號:" + client.getAccountName());
        }
    }
}
