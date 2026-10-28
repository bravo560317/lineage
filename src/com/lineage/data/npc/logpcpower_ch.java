package com.lineage.data.npc;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class logpcpower_ch extends NpcExecutor
{
    private logpcpower_ch() {
    }
    
    public static NpcExecutor get() {
        return new logpcpower_ch();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        String[] info = new String[6];
        if (pc.getlogpcpower_SkillCount() > 0) {
            info[0] = String.valueOf(pc.getlogpcpower_SkillCount());
        }
        else {
            info[0] = "0";
        }
        if (pc.getlogpcpower_SkillFor1() > 0) {
            info[1] = String.valueOf(pc.getlogpcpower_SkillFor1());
        }
        else {
            info[1] = "0";
        }
        if (pc.getlogpcpower_SkillFor2() > 0) {
            info[2] = String.valueOf(pc.getlogpcpower_SkillFor2());
        }
        else {
            info[2] = "0";
        }
        if (pc.getlogpcpower_SkillFor3() > 0) {
            info[3] = String.valueOf(pc.getlogpcpower_SkillFor3());
        }
        else {
            info[3] = "0";
        }
        if (pc.getlogpcpower_SkillFor4() > 0) {
            info[4] = String.valueOf(pc.getlogpcpower_SkillFor4());
        }
        else {
            info[4] = "0";
        }
        if (pc.getlogpcpower_SkillFor5() > 0) {
            info[5] = String.valueOf(pc.getlogpcpower_SkillFor5());
        }
        else {
            info[5] = "0";
        }
        if (pc.isCrown()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talCrown", info));
        }
        else if (pc.isKnight()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talKnight", info));
        }
        else if (pc.isWizard()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talWizard", info));
        }
        else if (pc.isElf()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talElf", info));
        }
        else if (pc.isDarkelf()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talDarkelf", info));
        }
        else if (pc.isDragonKnight()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talDragonK", info));
        }
        else if (pc.isIllusionist()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "talIllusi", info));
        }
    }
}
