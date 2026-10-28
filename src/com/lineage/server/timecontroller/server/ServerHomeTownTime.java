package com.lineage.server.timecontroller.server;

import com.lineage.server.model.gametime.L1GameTimeAdapter;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.TownReading;
import java.util.Calendar;
import com.lineage.server.model.gametime.L1GameTime;
import com.lineage.server.model.gametime.L1GameTimeClock;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ServerHomeTownTime
{
    private static final Log _log;
    private static ServerHomeTownTime _instance;
    private static ServerHomeTownTime.L1TownFixedProcListener _listener;
    
    static {
        _log = LogFactory.getLog(ServerHomeTownTime.class);
    }
    
    public static ServerHomeTownTime getInstance() {
        if (ServerHomeTownTime._instance == null) {
            ServerHomeTownTime._instance = new ServerHomeTownTime();
        }
        return ServerHomeTownTime._instance;
    }
    
    private ServerHomeTownTime() {
        this.startListener();
    }
    
    private void startListener() {
        if (ServerHomeTownTime._listener == null) {
            ServerHomeTownTime._listener = new ServerHomeTownTime.L1TownFixedProcListener();
            L1GameTimeClock.getInstance().addListener(ServerHomeTownTime._listener);
        }
    }
    
    private void fixedProc(L1GameTime time) {
        Calendar cal = time.getCalendar();
        int day = cal.get(5);
        if (day == 25) {
            this.monthlyProc();
        }
        else {
            this.dailyProc();
        }
    }
    
    public void dailyProc() {
        ServerHomeTownTime._log.info("村莊系統：日處理啟動");
        TownReading.get().updateTaxRate();
        TownReading.get().updateSalesMoneyYesterday();
        TownReading.get().load();
    }
    
    public void monthlyProc() {
        ServerHomeTownTime._log.info("村莊系統：月處理啟動");
        World.get().setProcessingContributionTotal(true);
        Collection<L1PcInstance> players = World.get().getAllPlayers();
        for (L1PcInstance pc : players) {
            try {
                pc.save();
            }
            catch (Exception e) {
                ServerHomeTownTime._log.error(e.getLocalizedMessage(), e);
            }
        }
        for (int townId = 1; townId <= 10; ++townId) {
            String leaderName = TownReading.get().totalContribution(townId);
            if (leaderName != null) {
                S_PacketBox packet = new S_PacketBox(23, leaderName);
                for (L1PcInstance pc2 : players) {
                    if (pc2.getHomeTownId() == townId) {
                        pc2.setContribution(0);
                        pc2.sendPackets(packet);
                    }
                }
            }
        }
        TownReading.get().load();
        for (L1PcInstance pc : players) {
            if (pc.getHomeTownId() == -1) {
                pc.setHomeTownId(0);
            }
            pc.setContribution(0);
            try {
                pc.save();
            }
            catch (Exception e) {
                ServerHomeTownTime._log.error(e.getLocalizedMessage(), e);
            }
        }
        TownReading.get().clearHomeTownID();
        World.get().setProcessingContributionTotal(false);
    }
    
    private class L1TownFixedProcListener extends L1GameTimeAdapter
    {
        @Override
        public void onDayChanged(L1GameTime time) {
            ServerHomeTownTime.this.fixedProc(time);
        }
    }
}
