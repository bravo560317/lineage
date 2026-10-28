package com.lineage.server.timecontroller.server;

import com.lineage.server.templates.L1House;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.sql.AuctionBoardTable;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.L1AuctionBoardTmp;
import com.lineage.server.datatables.lock.AuctionBoardReading;
import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Calendar;
import com.lineage.server.utils.ListMapUtil;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.logging.LogFactory;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class ServerAuctionTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static final Queue<Integer> _removeList;
    
    static {
        _log = LogFactory.getLog(ServerAuctionTimer.class);
        _removeList = new ConcurrentLinkedQueue();
    }
    
    public void start() {
        int timeMillis = 300000;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 300000L, 300000L);
    }
    
    @Override
    public void run() {
        try {
            checkAuctionDeadline();
        }
        catch (Exception e) {
            ServerAuctionTimer._log.error("小屋拍賣公告欄時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            ServerAuctionTimer restart = new ServerAuctionTimer();
            restart.start();
        }
        finally {
            ListMapUtil.clear(ServerAuctionTimer._removeList);
        }
    }
    
    private static Calendar getRealTime() {
        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(tz);
        return cal;
    }
    
    private static void checkAuctionDeadline() {
        try {
            Collection<L1AuctionBoardTmp> boardList = (Collection)AuctionBoardReading.get().getAuctionBoardTableList().values();
            for (L1AuctionBoardTmp board : boardList) {
                if (board.getDeadline().before(getRealTime())) {
                    endAuction(board);
                }
            }
            if (!ServerAuctionTimer._removeList.isEmpty()) {
                remove();
            }
        }
        catch (Exception e) {
            ServerAuctionTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void endAuction(L1AuctionBoardTmp board) {
        try {
            int houseId = board.getHouseId();
            long price = board.getPrice();
            int oldOwnerId = board.getOldOwnerId();
            String bidder = board.getBidder();
            int bidderId = board.getBidderId();
            if (oldOwnerId != 0 && bidderId != 0) {
                L1PcInstance oldOwnerPc = (L1PcInstance)World.get().findObject(oldOwnerId);
                long payPrice = (long)(int)((double)price * 0.9);
                if (oldOwnerPc != null) {
                    oldOwnerPc.getInventory().storeItem(40308, payPrice);
                    oldOwnerPc.sendPackets(new S_ServerMessage(527, String.valueOf(payPrice)));
                }
                else {
                    try {
                        CharItemsReading.get().getAdenaCount(oldOwnerId, price);
                    }
                    catch (Exception e) {
                        ServerAuctionTimer._log.error(e.getLocalizedMessage(), e);
                    }
                }
                L1PcInstance bidderPc = (L1PcInstance)World.get().findObject(bidderId);
                if (bidderPc != null) {
                    bidderPc.sendPackets(new S_ServerMessage(524, String.valueOf(price), bidder));
                }
                deleteHouseInfo(houseId);
                setHouseInfo(houseId, bidderId);
                ServerAuctionTimer._removeList.add(houseId);
            }
            else if (oldOwnerId == 0 && bidderId != 0) {
                L1PcInstance bidderPc2 = (L1PcInstance)World.get().findObject(bidderId);
                if (bidderPc2 != null) {
                    bidderPc2.sendPackets(new S_ServerMessage(524, String.valueOf(price), bidder));
                }
                setHouseInfo(houseId, bidderId);
                ServerAuctionTimer._removeList.add(houseId);
            }
            else if (oldOwnerId != 0 && bidderId == 0) {
                L1PcInstance oldOwnerPc = (L1PcInstance)World.get().findObject(oldOwnerId);
                if (oldOwnerPc != null) {
                    oldOwnerPc.sendPackets(new S_ServerMessage(528));
                }
                ServerAuctionTimer._removeList.add(houseId);
            }
            else if (oldOwnerId == 0 && bidderId == 0) {
                Calendar cal = getRealTime();
                cal.add(5, 5);
                cal.set(12, 0);
                cal.set(13, 0);
                board.setDeadline(cal);
                AuctionBoardTable boardTable = new AuctionBoardTable();
                boardTable.updateAuctionBoard(board);
            }
        }
        catch (Exception e2) {
            ServerAuctionTimer._log.error(e2.getLocalizedMessage(), e2);
        }
    }
    
    private static void deleteHouseInfo(int houseId) {
        try {
            Collection<L1Clan> allClans = WorldClan.get().getAllClans();
            for (L1Clan clan : allClans) {
                if (clan.getHouseId() == houseId) {
                    clan.setHouseId(0);
                    ClanReading.get().updateClan(clan);
                }
            }
        }
        catch (Exception e) {
            ServerAuctionTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void setHouseInfo(int houseId, int bidderId) {
        try {
            Collection<L1Clan> allClans = WorldClan.get().getAllClans();
            for (L1Clan clan : allClans) {
                if (clan.getLeaderId() == bidderId) {
                    clan.setHouseId(houseId);
                    ClanReading.get().updateClan(clan);
                    break;
                }
            }
        }
        catch (Exception e) {
            ServerAuctionTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void remove() {
        try {
            Iterator<Integer> iter = (Iterator)ServerAuctionTimer._removeList.iterator();
            while (iter.hasNext()) {
                Integer houseId = iter.next();
                iter.remove();
                deleteNote(houseId);
            }
        }
        catch (Exception e) {
            ServerAuctionTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void deleteNote(int houseId) {
        try {
            L1House house = HouseReading.get().getHouseTable(houseId);
            house.setOnSale(false);
            Calendar cal = getRealTime();
            cal.add(5, ConfigAlt.HOUSE_TAX_INTERVAL);
            cal.set(12, 0);
            cal.set(13, 0);
            house.setTaxDeadline(cal);
            HouseReading.get().updateHouse(house);
            AuctionBoardReading.get().deleteAuctionBoard(houseId);
        }
        catch (Exception e) {
            ServerAuctionTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
