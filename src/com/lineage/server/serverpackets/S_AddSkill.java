package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_AddSkill extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_AddSkill(L1PcInstance pc, int skillid) {
        this._byte = null;
        byte skill_list_values = 32;
        int[] skillList = new int[32];
        L1Skills skill = SkillsTable.get().getTemplate(skillid);
        int[] array = skillList;
        int n = skill.getSkillLevel() - 1;
        array[n] += skill.getId();
        this.writeC(164);
        this.writeC(32);
        int[] array2;
        for (int length = (array2 = skillList).length, i = 0; i < length; ++i) {
            int element = array2[i];
            this.writeC(element);
        }
        if (pc != null) {
            pc.setSkillMastery(skillid);
        }
    }
    
    public S_AddSkill(L1PcInstance pc, int[] skills) {
        this._byte = null;
        int i6 = skills[4] + skills[5] + skills[6] + skills[7];
        int j6 = skills[8] + skills[9];
        this.writeC(164);
        if (i6 > 0 && j6 == 0) {
            this.writeC(50);
        }
        else if (j6 > 0) {
            this.writeC(100);
        }
        else {
            this.writeC(32);
        }
        this.writeC(skills[0]);
        this.writeC(skills[1]);
        this.writeC(skills[2]);
        this.writeC(skills[3]);
        this.writeC(skills[4]);
        this.writeC(skills[5]);
        this.writeC(skills[6]);
        this.writeC(skills[7]);
        this.writeC(skills[8]);
        this.writeC(skills[9]);
        this.writeC(skills[10]);
        this.writeC(skills[11]);
        this.writeC(skills[12]);
        this.writeC(skills[13]);
        this.writeC(skills[14]);
        this.writeC(skills[15]);
        this.writeC(skills[16]);
        this.writeC(skills[17]);
        this.writeC(skills[18]);
        this.writeC(skills[19]);
        this.writeC(skills[20]);
        this.writeC(skills[21]);
        this.writeC(skills[22]);
        this.writeC(skills[23]);
        this.writeC(skills[24]);
        this.writeC(skills[25]);
        this.writeC(skills[26]);
        this.writeC(skills[27]);
        int[] ix = { skills[0], skills[1], skills[2], skills[3], skills[4], skills[5], skills[6], skills[7], skills[8], skills[9], skills[10], skills[11], skills[12], skills[13], skills[14], skills[15], skills[16], skills[17], skills[18], skills[19], skills[20], skills[21], skills[22], skills[23], skills[24], skills[25], skills[26], skills[27] };
        for (int k = 0; k < ix.length; ++k) {
            int type = ix[k];
            int rtType = 128;
            int rt = 0;
            int skillid = -1;
            while (rt < 8) {
                if (type - rtType >= 0) {
                    type -= rtType;
                    switch (rtType) {
                        case 1: {
                            skillid = (k << 3) + 1;
                            break;
                        }
                        case 2: {
                            skillid = (k << 3) + 2;
                            break;
                        }
                        case 4: {
                            skillid = (k << 3) + 3;
                            break;
                        }
                        case 8: {
                            skillid = (k << 3) + 4;
                            break;
                        }
                        case 16: {
                            skillid = (k << 3) + 5;
                            break;
                        }
                        case 32: {
                            skillid = (k << 3) + 6;
                            break;
                        }
                        case 64: {
                            skillid = (k << 3) + 7;
                            break;
                        }
                        case 128: {
                            skillid = (k << 3) + 8;
                            break;
                        }
                    }
                    if (skillid != -1 && pc != null) {
                        pc.setSkillMastery(skillid);
                    }
                }
                ++rt;
                rtType >>= 1;
            }
        }
    }
    
    public S_AddSkill(L1PcInstance pc, int level1, int level2, int level3, int level4, int level5, int level6, int level7, int level8, int level9, int level10, int knight1, int knight2, int de1, int de2, int royal, int un, int elf1, int elf2, int elf3, int elf4, int elf5, int elf6, int k1, int k2, int k3, int l1, int l2, int l3) {
        this._byte = null;
        int i6 = level5 + level6 + level7 + level8;
        int j6 = level9 + level10;
        this.writeC(164);
        if (i6 > 0 && j6 == 0) {
            this.writeC(50);
        }
        else if (j6 > 0) {
            this.writeC(100);
        }
        else {
            this.writeC(32);
        }
        this.writeC(level1);
        this.writeC(level2);
        this.writeC(level3);
        this.writeC(level4);
        this.writeC(level5);
        this.writeC(level6);
        this.writeC(level7);
        this.writeC(level8);
        this.writeC(level9);
        this.writeC(level10);
        this.writeC(knight1);
        this.writeC(knight2);
        this.writeC(de1);
        this.writeC(de2);
        this.writeC(royal);
        this.writeC(un);
        this.writeC(elf1);
        this.writeC(elf2);
        this.writeC(elf3);
        this.writeC(elf4);
        this.writeC(elf5);
        this.writeC(elf6);
        this.writeC(k1);
        this.writeC(k2);
        this.writeC(k3);
        this.writeC(l1);
        this.writeC(l2);
        this.writeC(l3);
        int[] ix = { level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, knight1, knight2, de1, de2, royal, un, elf1, elf2, elf3, elf4, elf5, elf6, k1, k2, k3, l1, l2, l3 };
        for (int m = 0; m < ix.length; ++m) {
            int type = ix[m];
            int rtType = 128;
            int rt = 0;
            int skillid = -1;
            while (rt < 8) {
                if (type - rtType >= 0) {
                    type -= rtType;
                    switch (rtType) {
                        case 1: {
                            skillid = (m << 3) + 1;
                            break;
                        }
                        case 2: {
                            skillid = (m << 3) + 2;
                            break;
                        }
                        case 4: {
                            skillid = (m << 3) + 3;
                            break;
                        }
                        case 8: {
                            skillid = (m << 3) + 4;
                            break;
                        }
                        case 16: {
                            skillid = (m << 3) + 5;
                            break;
                        }
                        case 32: {
                            skillid = (m << 3) + 6;
                            break;
                        }
                        case 64: {
                            skillid = (m << 3) + 7;
                            break;
                        }
                        case 128: {
                            skillid = (m << 3) + 8;
                            break;
                        }
                    }
                    if (skillid != -1 && pc != null) {
                        pc.setSkillMastery(skillid);
                    }
                }
                ++rt;
                rtType >>= 1;
            }
        }
    }
    
    public S_AddSkill(L1PcInstance pc, int mode, int dx) {
        this._byte = null;
        byte skill_list_values = 32;
        this.writeC(164);
        this.writeC(32);
        for (int i = 0; i < 28; ++i) {
            if (i == mode) {
                this.writeC(dx);
            }
            else {
                this.writeC(0);
            }
        }
    }
    
    public S_AddSkill(int level1, int level2, int level3, int level4, int level5, int level6, int level7, int level8, int level9, int level10, int knight, int l2, int de1, int de2, int royal, int l3, int elf1, int elf2, int elf3, int elf4, int elf5, int elf6, int k5, int l5, int m5, int n5, int o5, int p5) {
        this._byte = null;
        int i6 = level5 + level6 + level7 + level8;
        int j6 = level9 + level10;
        this.writeC(164);
        if (i6 > 0 && j6 == 0) {
            this.writeC(50);
        }
        else if (j6 > 0) {
            this.writeC(100);
        }
        else {
            this.writeC(32);
        }
        this.writeC(level1);
        this.writeC(level2);
        this.writeC(level3);
        this.writeC(level4);
        this.writeC(level5);
        this.writeC(level6);
        this.writeC(level7);
        this.writeC(level8);
        this.writeC(level9);
        this.writeC(level10);
        this.writeC(knight);
        this.writeC(l2);
        this.writeC(de1);
        this.writeC(de2);
        this.writeC(royal);
        this.writeC(l3);
        this.writeC(elf1);
        this.writeC(elf2);
        this.writeC(elf3);
        this.writeC(elf4);
        this.writeC(elf5);
        this.writeC(elf6);
        this.writeC(k5);
        this.writeC(l5);
        this.writeC(m5);
        this.writeC(n5);
        this.writeC(o5);
        this.writeC(p5);
        this.writeD(0);
        this.writeD(0);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
