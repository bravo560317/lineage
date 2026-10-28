package com.lineage.server.timecontroller;

import com.lineage.server.timecontroller.pc.PcHellTimer;
import com.lineage.config.ConfigAlt;
import com.add.new_AutoBuyTime;
import com.lineage.server.timecontroller.pc.EzpayHero;
import com.lineage.server.timecontroller.pc.Quburcount;
import com.lineage.server.timecontroller.pc.Pc_Ai_Hp_Type2;
import com.lineage.server.timecontroller.pc.Pc_Ai_Hp;
import com.lineage.server.timecontroller.pc.LawfulTimer;
import com.lineage.server.timecontroller.pc.GfxTimer;
import com.lineage.server.timecontroller.pc.PartyTimer;
import com.lineage.server.timecontroller.pc.UnfreezingTimer;
import com.lineage.server.timecontroller.pc.PcGhostTimer;
import com.lineage.server.timecontroller.pc.PcDeleteTimer;
import com.lineage.server.timecontroller.pc.MprTimerWizard;
import com.lineage.server.timecontroller.pc.MprTimerKnight;
import com.lineage.server.timecontroller.pc.MprTimerIllusionist;
import com.lineage.server.timecontroller.pc.MprTimerElf;
import com.lineage.server.timecontroller.pc.MprTimerDragonKnight;
import com.lineage.server.timecontroller.pc.MprTimerDarkElf;
import com.lineage.server.timecontroller.pc.MprTimerCrown;
import com.lineage.server.timecontroller.pc.HprTimerWizard;
import com.lineage.server.timecontroller.pc.HprTimerKnight;
import com.lineage.server.timecontroller.pc.HprTimerIllusionist;
import com.lineage.server.timecontroller.pc.HprTimerElf;
import com.lineage.server.timecontroller.pc.HprTimerDragonKnight;
import com.lineage.server.timecontroller.pc.HprTimerDarkElf;
import com.lineage.server.timecontroller.pc.HprTimerCrown;
import com.lineage.server.timecontroller.pc.UpdateObjectWTimer;
import com.lineage.server.timecontroller.pc.UpdateObjectKTimer;
import com.lineage.server.timecontroller.pc.UpdateObjectITimer;
import com.lineage.server.timecontroller.pc.UpdateObjectETimer;
import com.lineage.server.timecontroller.pc.UpdateObjectDTimer;
import com.lineage.server.timecontroller.pc.UpdateObjectDKTimer;
import com.lineage.server.timecontroller.pc.UpdateObjectCTimer;
import com.lineage.server.timecontroller.pc.ExpTimer;
import com.lineage.server.timecontroller.pc.PcFishingTimer;
import com.lineage.server.timecontroller.pc.PcAutoSaveInventoryTimer;
import com.lineage.server.timecontroller.pc.PcAutoSaveTimer;
import com.lineage.config.Config;

public class StartTimer_Pc
{
    public void start() throws InterruptedException {
        if (Config.AUTOSAVE_INTERVAL > 0) {
            PcAutoSaveTimer save = new PcAutoSaveTimer();
            save.start();
        }
        if (Config.AUTOSAVE_INTERVAL_INVENTORY > 0) {
            PcAutoSaveInventoryTimer save2 = new PcAutoSaveInventoryTimer();
            save2.start();
        }
        PcFishingTimer fishingTimeController = new PcFishingTimer();
        fishingTimeController.start();
        Thread.sleep(50L);
        ExpTimer expTimer = new ExpTimer();
        expTimer.start();
        UpdateObjectCTimer objectCTimer = new UpdateObjectCTimer();
        objectCTimer.start();
        UpdateObjectDKTimer objectDKTimer = new UpdateObjectDKTimer();
        objectDKTimer.start();
        UpdateObjectDTimer objectDTimer = new UpdateObjectDTimer();
        objectDTimer.start();
        UpdateObjectETimer objectETimer = new UpdateObjectETimer();
        objectETimer.start();
        UpdateObjectITimer objectITimer = new UpdateObjectITimer();
        objectITimer.start();
        UpdateObjectKTimer objectKTimer = new UpdateObjectKTimer();
        objectKTimer.start();
        UpdateObjectWTimer objectWTimer = new UpdateObjectWTimer();
        objectWTimer.start();
        Thread.sleep(50L);
        HprTimerCrown hprCrown = new HprTimerCrown();
        hprCrown.start();
        HprTimerDarkElf hprDarkElf = new HprTimerDarkElf();
        hprDarkElf.start();
        HprTimerDragonKnight hprDK = new HprTimerDragonKnight();
        hprDK.start();
        HprTimerElf hprElf = new HprTimerElf();
        hprElf.start();
        HprTimerIllusionist hprIllusionist = new HprTimerIllusionist();
        hprIllusionist.start();
        HprTimerKnight hprKnight = new HprTimerKnight();
        hprKnight.start();
        HprTimerWizard hprWizard = new HprTimerWizard();
        hprWizard.start();
        Thread.sleep(50L);
        MprTimerCrown mprCrown = new MprTimerCrown();
        mprCrown.start();
        MprTimerDarkElf mprDarkElf = new MprTimerDarkElf();
        mprDarkElf.start();
        MprTimerDragonKnight mprDragonKnight = new MprTimerDragonKnight();
        mprDragonKnight.start();
        MprTimerElf mprElf = new MprTimerElf();
        mprElf.start();
        MprTimerIllusionist mprIllusionist = new MprTimerIllusionist();
        mprIllusionist.start();
        MprTimerKnight mprKnight = new MprTimerKnight();
        mprKnight.start();
        MprTimerWizard mprWizard = new MprTimerWizard();
        mprWizard.start();
        Thread.sleep(50L);
        PcDeleteTimer deleteTimer = new PcDeleteTimer();
        deleteTimer.start();
        PcGhostTimer ghostTimer = new PcGhostTimer();
        ghostTimer.start();
        UnfreezingTimer unfreezingTimer = new UnfreezingTimer();
        unfreezingTimer.start();
        PartyTimer partyTimer = new PartyTimer();
        partyTimer.start();
        GfxTimer GfxTimer = new GfxTimer();
        GfxTimer.start();
        Thread.sleep(50L);
        LawfulTimer lawfulTimer = new LawfulTimer();
        lawfulTimer.start();
        Pc_Ai_Hp Pc_Ai_Hp = new Pc_Ai_Hp();
        Pc_Ai_Hp.start();
        Thread.sleep(50L);
        Pc_Ai_Hp_Type2 SkillSoundHp2 = new Pc_Ai_Hp_Type2();
        SkillSoundHp2.start();
        Thread.sleep(50L);
        Quburcount Quburcount = new Quburcount();
        Quburcount.start();
        Thread.sleep(50L);
        EzpayHero EzpayHero = new EzpayHero();
        EzpayHero.start();
        Thread.sleep(50L);
        new_AutoBuyTime abi = new new_AutoBuyTime();
        abi.start();
        if (ConfigAlt.ALT_PUNISHMENT) {
            PcHellTimer hellTimer = new PcHellTimer();
            hellTimer.start();
        }
    }
}
