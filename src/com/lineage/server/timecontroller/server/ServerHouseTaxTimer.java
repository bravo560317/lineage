package com.lineage.server.timecontroller.server;

import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.lock.AuctionBoardReading;
import com.lineage.server.templates.L1AuctionBoardTmp;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.L1House;
import com.lineage.server.datatables.lock.HouseReading;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerHouseTaxTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    
    static {
        _log = LogFactory.getLog(ServerHouseTaxTimer.class);
    }
    
    public void start() {
        int timeMillis = 600000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 600000L, 600000L);
    }
    
    @Override
    public void run() {
        try {
            checkTaxDeadline();
        }
        catch (Exception e) {
            ServerHouseTaxTimer._log.error("血盟小屋稅收時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerHouseTaxTimer houseTaxTimer = new ServerHouseTaxTimer();
            houseTaxTimer.start();
        }
    }
    
    private static Calendar getRealTime() {
        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(tz);
        return cal;
    }
    
    private static void checkTaxDeadline() {
        try {
            Collection<L1House> houseList = (Collection)HouseReading.get().getHouseTableList().values();
            if (!houseList.isEmpty()) {
                for (L1House house : houseList) {
                    if (!house.isOnSale() && house.getTaxDeadline().before(getRealTime())) {
                        sellHouse(house);
                    }
                }
            }
        }
        catch (Exception e) {
            ServerHouseTaxTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void sellHouse(L1House house) {
        L1AuctionBoardTmp board = new L1AuctionBoardTmp();
        if (board != null) {
            int houseId = house.getHouseId();
            board.setHouseId(houseId);
            board.setHouseName(house.getHouseName());
            board.setHouseArea(house.getHouseArea());
            TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
            Calendar cal = Calendar.getInstance(tz);
            cal.add(5, 5);
            cal.set(12, 0);
            cal.set(13, 0);
            board.setDeadline(cal);
            board.setPrice(100000L);
            board.setLocation(house.getLocation());
            board.setOldOwner("");
            board.setOldOwnerId(0);
            board.setBidder("");
            board.setBidderId(0);
            AuctionBoardReading.get().insertAuctionBoard(board);
            house.setOnSale(true);
            house.setPurchaseBasement(true);
            cal.add(5, ConfigAlt.HOUSE_TAX_INTERVAL);
            house.setTaxDeadline(cal);
            HouseReading.get().updateHouse(house);
            Collection<L1Clan> allClans = WorldClan.get().getAllClans();
            for (L1Clan clan : allClans) {
                if (clan.getHouseId() == houseId) {
                    clan.setHouseId(0);
                    ClanReading.get().updateClan(clan);
                }
            }
        }
    }
}
