package com.lineage.data.event;

import java.util.Iterator;
import java.util.Map;
import com.lineage.server.templates.L1Bank;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.ScheduledFuture;
import java.util.TimerTask;
import com.lineage.server.datatables.lock.AccountBankReading;
import com.lineage.server.utils.RangeLong;
import com.lineage.server.templates.L1Event;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.EventExecutor;

public class BankSet extends EventExecutor
{
    private static final Log _log;
    public static long BANKMAX;
    public static long BANK_INTEREST_OVER;
    public static int BANK_ITEMID;
    public static int BANK_TIME;
    public static double BANK_INTEREST;
    
    static {
        _log = LogFactory.getLog(BankSet.class);
        BankSet.BANK_TIME = 1;
        BankSet.BANK_INTEREST = 0.01;
    }
    
    private BankSet() {
    }
    
    public static EventExecutor get() {
        return new BankSet();
    }
    
    @Override
    public void execute(L1Event event) {
        try {
            String[] set = event.get_eventother().split(",");
            try {
                BankSet.BANKMAX = Long.parseLong(set[0]);
            }
            catch (Exception e) {
                BankSet.BANKMAX = 1900000000L;
                BankSet._log.error("未設定銀行儲蓄上限(使用預設19億)");
            }
            try {
                BankSet.BANK_INTEREST_OVER = Long.parseLong(set[1]);
            }
            catch (Exception e) {
                BankSet.BANK_INTEREST_OVER = 2000000000L;
                BankSet._log.error("未設定利息及現存款總額(使用預設20億)");
            }
            try {
                BankSet.BANK_ITEMID = Integer.parseInt(set[2]);
            }
            catch (Exception e) {
                BankSet.BANK_ITEMID = 40308;
                BankSet._log.error("未設定存款物品編號(使用預設40308)");
            }
            try {
                BankSet.BANK_TIME = Integer.parseInt(set[3]);
            }
            catch (Exception e) {
                BankSet.BANK_TIME = 60;
                BankSet._log.error("未設銀行計算利息時間(使用預設60分鐘)");
            }
            try {
                BankSet.BANK_INTEREST = Double.parseDouble(set[4]);
            }
            catch (Exception e) {
                BankSet.BANK_INTEREST = 0.01;
                BankSet._log.error("未設銀行利息比率(使用預設0.01%)");
            }
            BankSet._log.info("銀行設置\n 帳戶儲蓄上限: " + RangeLong.scount(BankSet.BANKMAX) + " \n 存款總額限制: " + RangeLong.scount(BankSet.BANK_INTEREST_OVER) + "\n 存款物品編號: " + RangeLong.scount(BankSet.BANK_INTEREST_OVER) + "\n 利息計算時間: " + BankSet.BANK_TIME + "\n 利息比率: " + BankSet.BANK_INTEREST);
            AccountBankReading.get().load();
            BankSet.BankTimer bankTime = new BankSet.BankTimer();
            bankTime.start();
        }
        catch (Exception e2) {
            BankSet._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private class BankTimer extends TimerTask
    {
        private ScheduledFuture<?> _timer;
        
        public void start() {
            int timeMillis = BankSet.BANK_TIME * 60 * 1000;
            this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
        }
        
        @Override
        public void run() {
            try {
                Map<String, L1Bank> map = AccountBankReading.get().map();
                if (map.isEmpty()) {
                    return;
                }
                else {
                    for (String key : map.keySet()) {
                        L1Bank bank = map.get(key);
                        if (bank.isLan() && !bank.isEmpty()) {
                            long nwecount = bank.get_adena_count() + Math.round((double)bank.get_adena_count() * BankSet.BANK_INTEREST);
                            bank.set_adena_count(nwecount);
                            AccountBankReading.get().updateAdena(bank.get_account_name(), nwecount);
                            Thread.sleep(5L);
                        }
                    }
                }
            }
            catch (Exception e) {
                BankSet._log.error("銀行管理員時間軸異常重啟", e);
                GeneralThreadPool.get().cancel(this._timer, false);
                BankSet.BankTimer bankTime = new BankSet.BankTimer();
                bankTime.start();
            }
        }
    }
}
