package com.lineage.server.command.executor;

import java.util.TimeZone;
import com.lineage.config.Config;
import java.util.Iterator;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1AuctionBoardTmp;
import com.lineage.server.datatables.lock.AuctionBoardReading;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1ReBoard implements L1CommandExecutor
{
    public static L1CommandExecutor getInstance() {
        return new L1ReBoard();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            Calendar DeadTime = this.getRealTime();
            DeadTime.add(12, Integer.parseInt(arg));
            for (L1AuctionBoardTmp board : AuctionBoardReading.get().getAuctionBoardTableList().values()) {
                board.setDeadline(DeadTime);
                AuctionBoardReading.get().updateAuctionBoard(board);
            }
            World.get().broadcastPacketToAll(new S_SystemMessage("血盟小屋將在" + arg + "分鐘後結標，需要購買的請盡快下標。"));
        }
        catch (Exception e) {
            pc.sendPackets(new S_SystemMessage("請輸入 " + cmdName + " 分鐘數"));
        }
    }
    
    private Calendar getRealTime() {
        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
        Calendar cal = Calendar.getInstance(tz);
        return cal;
    }
}
