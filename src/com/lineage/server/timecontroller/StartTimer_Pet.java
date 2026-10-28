package com.lineage.server.timecontroller;

import com.lineage.server.timecontroller.pet.HierarchMprTimer;
import com.lineage.server.timecontroller.pet.DollAidTimer;
import com.lineage.server.timecontroller.pet.DollGetTimer;
import com.lineage.server.timecontroller.pet.DollMprTimer;
import com.lineage.server.timecontroller.pet.DollHprTimer;
import com.lineage.server.timecontroller.pet.DollTimer;
import com.lineage.server.timecontroller.pet.SummonTimer;
import com.lineage.server.timecontroller.pet.SummonMprTimer;
import com.lineage.server.timecontroller.pet.SummonHprTimer;
import com.lineage.server.timecontroller.pet.PetMprTimer;
import com.lineage.server.timecontroller.pet.PetHprTimer;

public class StartTimer_Pet
{
    public void start() throws InterruptedException {
        PetHprTimer petHprTimer = new PetHprTimer();
        petHprTimer.start();
        Thread.sleep(50L);
        PetMprTimer petMprTimer = new PetMprTimer();
        petMprTimer.start();
        Thread.sleep(50L);
        SummonHprTimer summonHprTimer = new SummonHprTimer();
        summonHprTimer.start();
        Thread.sleep(50L);
        SummonMprTimer summonMprTimer = new SummonMprTimer();
        summonMprTimer.start();
        Thread.sleep(50L);
        SummonTimer summon_Timer = new SummonTimer();
        summon_Timer.start();
        Thread.sleep(50L);
        DollTimer dollTimer = new DollTimer();
        dollTimer.start();
        DollHprTimer dollHpTimer = new DollHprTimer();
        dollHpTimer.start();
        DollMprTimer dollMpTimer = new DollMprTimer();
        dollMpTimer.start();
        DollGetTimer dollGetTimer = new DollGetTimer();
        dollGetTimer.start();
        DollAidTimer dollAidTimer = new DollAidTimer();
        dollAidTimer.start();
        HierarchMprTimer hierarchMprTimer = new HierarchMprTimer();
        hierarchMprTimer.start();
        Thread.sleep(50L);
    }
}
