package com.lineage.server.timecontroller;

import com.lineage.server.timecontroller.event.T_Special;
import com.lineage.data.event.RandomGiftSet;
import com.lineage.server.timecontroller.server.ServerItemUserTimer;
import com.lineage.server.timecontroller.server.ServerSpawnMob;
import com.lineage.data.event.NowTimeSpawn;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.timecontroller.server.ServerDeleteItemTimer;
import com.lineage.server.timecontroller.server.ServerElementalStoneTimer;
import com.lineage.config.ConfigAlt;
import com.lineage.server.timecontroller.server.ServerLightTimer;
import com.lineage.server.timecontroller.server.ServerHouseTaxTimer;
import com.lineage.server.timecontroller.server.ServerAuctionTimer;
import com.lineage.server.timecontroller.server.ServerWarTimer;
import com.lineage.server.timecontroller.server.ServerTrapTimer;

public class StartTimer_Server
{
    public void start() throws InterruptedException {
        ServerTrapTimer trapTimer = new ServerTrapTimer();
        trapTimer.start();
        Thread.sleep(50L);
        ServerWarTimer warTimer = new ServerWarTimer();
        warTimer.start();
        Thread.sleep(50L);
        ServerAuctionTimer auctionTimeController = new ServerAuctionTimer();
        auctionTimeController.start();
        Thread.sleep(50L);
        ServerHouseTaxTimer houseTaxTimeController = new ServerHouseTaxTimer();
        houseTaxTimeController.start();
        Thread.sleep(50L);
        ServerLightTimer lightTimeController = new ServerLightTimer();
        lightTimeController.start();
        Thread.sleep(50L);
        if (ConfigAlt.ELEMENTAL_STONE_AMOUNT > 0) {
            ServerElementalStoneTimer elementalStoneGenerator = new ServerElementalStoneTimer();
            elementalStoneGenerator.start();
            Thread.sleep(50L);
        }
        if (ConfigAlt.ALT_ITEM_DELETION_TIME > 0) {
            ServerDeleteItemTimer deleteitem = new ServerDeleteItemTimer();
            deleteitem.start();
            Thread.sleep(50L);
        }
        ServerRestartTimer autoRestart = new ServerRestartTimer();
        autoRestart.start();
        Thread.sleep(50L);
        ServerUseMapTimer useMapTimer = new ServerUseMapTimer();
        useMapTimer.start();
        Thread.sleep(50L);
        if (NowTimeSpawn.START) {
            ServerSpawnMob SpawnMob = new ServerSpawnMob();
            SpawnMob.start();
            Thread.sleep(50L);
        }
        ServerItemUserTimer userTimer = new ServerItemUserTimer();
        userTimer.start();
        if (RandomGiftSet.START) {
            T_Special.getStart();
        }
    }
}
