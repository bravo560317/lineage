package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1AddSkill implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1AddSkill.class);
    }
    
    private L1AddSkill() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1AddSkill();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int cnt = 0;
            String skill_name = "";
            int skill_id = 0;
            int object_id = pc.getId();
            pc.sendPacketsX8(new S_SkillSound(object_id, 227));
            if (pc.isCrown()) {
                pc.sendPackets(new S_AddSkill(pc, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                for (cnt = 1; cnt <= 16; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
                for (cnt = 113; cnt <= 120; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
            else if (pc.isKnight()) {
                pc.sendPackets(new S_AddSkill(pc, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 192, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                for (cnt = 1; cnt <= 8; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
                for (cnt = 87; cnt <= 91; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
            else if (pc.isElf()) {
                pc.sendPackets(new S_AddSkill(pc, 255, 255, 127, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 127, 3, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0));
                for (cnt = 1; cnt <= 48; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
                for (cnt = 129; cnt <= 176; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
            else if (pc.isWizard()) {
                pc.sendPackets(new S_AddSkill(pc, 255, 255, 127, 255, 255, 255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                for (cnt = 1; cnt <= 80; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
            else if (pc.isDarkelf()) {
                pc.sendPackets(new S_AddSkill(pc, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 127, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                for (cnt = 1; cnt <= 16; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
                for (cnt = 97; cnt <= 111; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
            else if (pc.isDragonKnight()) {
                pc.sendPackets(new S_AddSkill(pc, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 240, 255, 7, 0, 0, 0));
                for (cnt = 181; cnt <= 195; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
            else if (pc.isIllusionist()) {
                pc.sendPackets(new S_AddSkill(pc, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 15));
                for (cnt = 201; cnt <= 220; ++cnt) {
                    L1Skills l1skills = SkillsTable.get().getTemplate(cnt);
                    skill_name = l1skills.getName();
                    skill_id = l1skills.getSkillId();
                    CharSkillReading.get().spellMastery(object_id, skill_id, skill_name, 0, 0);
                }
            }
        }
        catch (Exception e) {
            L1AddSkill._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
