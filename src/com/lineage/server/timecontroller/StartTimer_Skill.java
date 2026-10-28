package com.lineage.server.timecontroller;

import com.lineage.server.timecontroller.skill.BraveAvatarTimer;
import com.lineage.server.timecontroller.skill.EffectCubeHarmonizeTimer;
import com.lineage.server.timecontroller.skill.EffectCubeShockTimer;
import com.lineage.server.timecontroller.skill.EffectCubeEruptionTimer;
import com.lineage.server.timecontroller.skill.EffectCubeBurnTimer;
import com.lineage.server.timecontroller.skill.EffectFirewallTimer;
import com.lineage.server.timecontroller.skill.Skill_Awake_Timer;

public class StartTimer_Skill
{
    public void start() {
        Skill_Awake_Timer awake_Timer = new Skill_Awake_Timer();
        awake_Timer.start();
        EffectFirewallTimer firewall = new EffectFirewallTimer();
        firewall.start();
        EffectCubeBurnTimer cubeBurn = new EffectCubeBurnTimer();
        cubeBurn.start();
        EffectCubeEruptionTimer cubeEruption = new EffectCubeEruptionTimer();
        cubeEruption.start();
        EffectCubeShockTimer cubeShock = new EffectCubeShockTimer();
        cubeShock.start();
        EffectCubeHarmonizeTimer cubeHarmonize = new EffectCubeHarmonizeTimer();
        cubeHarmonize.start();
        BraveAvatarTimer braveavatar = new BraveAvatarTimer();
        braveavatar.start();
    }
}
