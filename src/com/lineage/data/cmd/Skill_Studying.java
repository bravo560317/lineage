package com.lineage.data.cmd;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class Skill_Studying implements Skill_StudyingExecutor
{
    @Override
    public void magic(L1PcInstance pc, int skillId, int magicAtt, int attribute, int itemObj) {
        int pclvl = pc.getLevel();
        boolean isUse = true;
        if (pc.isCrown()) {
            switch (magicAtt) {
                case 1: {
                    if (pclvl >= 10) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 2: {
                    if (pclvl >= 20) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 21: {
                    if (pclvl >= 15) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 22: {
                    if (pclvl >= 30) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 23: {
                    if (pclvl >= 40) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 24: {
                    if (pclvl >= 45) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 25: {
                    if (pclvl >= 50) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 26: {
                    if (pclvl >= 55) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        else if (pc.isKnight()) {
            switch (magicAtt) {
                case 1: {
                    if (pclvl >= 50) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 31: {
                    if (pclvl >= 50) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 32: {
                    if (pclvl >= 60) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        else if (pc.isWizard()) {
            switch (magicAtt) {
                case 1: {
                    if (pclvl >= 4) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 2: {
                    if (pclvl >= 8) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 3: {
                    if (pclvl >= 12) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 4: {
                    if (pclvl >= 16) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 5: {
                    if (pclvl >= 20) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 6: {
                    if (pclvl >= 24) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 7: {
                    if (pclvl >= 28) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 8: {
                    if (pclvl >= 32) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 9: {
                    if (pclvl >= 36) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 10: {
                    if (pclvl >= 40) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        else if (pc.isElf()) {
            switch (magicAtt) {
                case 1: {
                    if (pclvl >= 8) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 2: {
                    if (pclvl >= 16) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 3: {
                    if (pclvl >= 24) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 4: {
                    if (pclvl >= 32) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 5: {
                    if (pclvl >= 40) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 6: {
                    if (pclvl >= 48) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 11: {
                    if (pclvl >= 10) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 12: {
                    if (pclvl >= 20) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 13: {
                    if (pclvl >= 30) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 14: {
                    if (pclvl >= 40) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 15: {
                    if (pclvl >= 50) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        else if (pc.isDarkelf()) {
            switch (magicAtt) {
                case 1: {
                    if (pclvl >= 12) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 2: {
                    if (pclvl >= 24) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 41: {
                    if (pclvl >= 15) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 42: {
                    if (pclvl >= 30) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 43: {
                    if (pclvl >= 45) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 44: {
                    if (pclvl >= 60) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        else if (pc.isDragonKnight()) {
            switch (magicAtt) {
                case 51: {
                    if (pclvl >= 15) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 52: {
                    if (pclvl >= 30) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 53: {
                    if (pclvl >= 45) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        else if (pc.isIllusionist()) {
            switch (magicAtt) {
                case 61: {
                    if (pclvl >= 10) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 62: {
                    if (pclvl >= 20) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 63: {
                    if (pclvl >= 30) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                case 64: {
                    if (pclvl >= 40) {
                        this.mapPosition(pc, skillId, attribute, itemObj);
                        break;
                    }
                    else {
                        isUse = false;
                        break;
                    }
                }
                default: {
                    S_ServerMessage msg = new S_ServerMessage(79);
                    pc.sendPackets(msg);
                    break;
                }
            }
        }
        if (!isUse) {
            S_ServerMessage msg = new S_ServerMessage(312);
            pc.sendPackets(msg);
        }
    }
    
    private void mapPosition(L1PcInstance pc, int skillId, int attribute, int itemObj) {
        boolean isOk = true;
        isOk = false;
        if (isOk) {
            int x = pc.getX();
            int y = pc.getY();
            int m = pc.getMapId();
            switch (attribute) {
                case 0: {
                    if ((x > 32880 && x < 32892 && y > 32646 && y < 32658 && m == 4) || (x > 32662 && x < 32674 && y > 32297 && y < 32309 && m == 4) || (x > 33135 && x < 33146 && y > 32232 && y < 32249 && m == 4) || (x > 33116 && x < 33128 && y > 32930 && y < 32942 && m == 4) || (x > 32791 && x < 32796 && y > 32842 && y < 32848 && m == 76)) {
                        this.spellBook(pc, skillId, itemObj);
                        break;
                    }
                    else {
                        S_ServerMessage msg = new S_ServerMessage(79);
                        pc.sendPackets(msg);
                        break;
                    }
                }
                case 1: {
                    if ((x > 33116 && x < 33128 && y > 32930 && y < 32942 && m == 4) || (x > 33135 && x < 33146 && y > 32232 && y < 32249 && m == 4) || (x > 32791 && x < 32796 && y > 32842 && y < 32848 && m == 76)) {
                        this.spellBook(pc, skillId, itemObj);
                        break;
                    }
                    else if ((x > 32880 && x < 32892 && y > 32646 && y < 32658 && m == 4) || (x > 32662 && x < 32674 && y > 32297 && y < 32309 && m == 4)) {
                        pc.getInventory().removeItem(pc.getInventory().getItem(itemObj), 1L);
                        short dmg = (short)(int)(Math.random() * 50.0 + 30.0);
                        pc.receiveDamage(pc, (double)dmg, false, true);
                        if (pc.isInvisble()) {
                            pc.delInvis();
                        }
                        S_SkillSound sound = new S_SkillSound(pc.getId(), 11736);
                        pc.sendPacketsX8(sound);
                        S_DoActionGFX pack = new S_DoActionGFX(pc.getId(), 2);
                        pc.sendPacketsX8(pack);
                        S_HPUpdate newHp = new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp());
                        pc.sendPackets(newHp);
                        if (pc.isInParty()) {
                            pc.getParty().updateMiniHP(pc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        S_ServerMessage msg = new S_ServerMessage(79);
                        pc.sendPackets(msg);
                        break;
                    }
                }
                case 2: {
                    if ((x > 32880 && x < 32892 && y > 32646 && y < 32658 && m == 4) || (x > 32662 && x < 32674 && y > 32297 && y < 32309 && m == 4)) {
                        this.spellBook(pc, skillId, itemObj);
                        break;
                    }
                    else if ((x > 33116 && x < 33128 && y > 32930 && y < 32942 && m == 4) || (x > 33135 && x < 33146 && y > 32232 && y < 32249 && m == 4) || (x > 32791 && x < 32796 && y > 32842 && y < 32848 && m == 76)) {
                        pc.getInventory().removeItem(pc.getInventory().getItem(itemObj), 1L);
                        short dmg = (short)(int)(Math.random() * 50.0 + 30.0);
                        pc.receiveDamage(pc, (double)dmg, false, true);
                        if (pc.isInvisble()) {
                            pc.delInvis();
                        }
                        S_SkillSound sound = new S_SkillSound(pc.getId(), 11736);
                        pc.sendPacketsX8(sound);
                        S_DoActionGFX pack = new S_DoActionGFX(pc.getId(), 2);
                        pc.sendPacketsX8(pack);
                        S_HPUpdate newHp = new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp());
                        pc.sendPackets(newHp);
                        if (pc.isInParty()) {
                            pc.getParty().updateMiniHP(pc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        S_ServerMessage msg = new S_ServerMessage(79);
                        pc.sendPackets(msg);
                        break;
                    }
                }
                case 3: {
                    if ((x > 33049 && x < 33061 && y > 32330 && y < 32343 && m == 4) || (x > 32788 && x < 32794 && y > 32847 && y < 32853 && m == 75)) {
                        this.spellBook(pc, skillId, itemObj);
                        break;
                    }
                    else {
                        S_ServerMessage msg = new S_ServerMessage(79);
                        pc.sendPackets(msg);
                        break;
                    }
                }
            }
        }
        else {
            this.spellBook(pc, skillId, itemObj);
        }
    }
    
    private void spellBook(L1PcInstance pc, int skillId, int itemObj) {
        pc.getInventory().removeItem(pc.getInventory().getItem(itemObj), 1L);
        pc.sendPackets(new S_AddSkill(pc, skillId));
        char c = 'ã';
        S_SkillSound sound = new S_SkillSound(pc.getId(), 227);
        pc.sendPacketsX8(sound);
        L1Skills skill = SkillsTable.get().getTemplate(skillId);
        String skillName = skill.getName();
        CharSkillReading.get().spellMastery(pc.getId(), skillId, skillName, 0, 0);
    }
}
