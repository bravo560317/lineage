package com.lineage.server.timecontroller;

import com.lineage.server.timecontroller.npc.NpcBowTimer;
import com.lineage.server.timecontroller.npc.NpcWorkTimer;
import com.lineage.server.timecontroller.npc.NpcRestTimer;
import com.lineage.server.timecontroller.npc.NpcShopTimer;
import com.lineage.server.timecontroller.npc.NpcSpawnBossTimer;
import com.lineage.server.timecontroller.npc.NpcDigestItemTimer;
import com.lineage.server.timecontroller.npc.NpcDeadTimer;
import com.lineage.server.timecontroller.npc.NpcDeleteTimer;
import com.lineage.server.timecontroller.npc.NpcMprTimer;
import com.lineage.server.timecontroller.npc.NpcHprTimer;
import com.lineage.server.timecontroller.npc.NpcChatTimer;

public class StartTimer_Npc
{
    public void start() throws InterruptedException {
        NpcChatTimer npcChatTimeController = new NpcChatTimer();
        npcChatTimeController.start();
        Thread.sleep(50L);
        NpcHprTimer npcHprTimer = new NpcHprTimer();
        npcHprTimer.start();
        Thread.sleep(50L);
        NpcMprTimer npcMprTimer = new NpcMprTimer();
        npcMprTimer.start();
        Thread.sleep(50L);
        NpcDeleteTimer npcDeleteTimer = new NpcDeleteTimer();
        npcDeleteTimer.start();
        Thread.sleep(50L);
        NpcDeadTimer npcDeadTimer = new NpcDeadTimer();
        npcDeadTimer.start();
        Thread.sleep(50L);
        NpcDigestItemTimer digestItemTimer = new NpcDigestItemTimer();
        digestItemTimer.start();
        Thread.sleep(50L);
        NpcSpawnBossTimer bossTimer = new NpcSpawnBossTimer();
        bossTimer.start();
        Thread.sleep(50L);
        NpcShopTimer shopTimer = new NpcShopTimer();
        shopTimer.start();
        Thread.sleep(50L);
        NpcRestTimer restTimer = new NpcRestTimer();
        restTimer.start();
        Thread.sleep(50L);
        NpcWorkTimer workTimer = new NpcWorkTimer();
        workTimer.start();
        Thread.sleep(50L);
        NpcBowTimer bow = new NpcBowTimer();
        bow.start();
    }
}
