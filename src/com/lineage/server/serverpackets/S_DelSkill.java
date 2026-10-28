package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_DelSkill extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_DelSkill(L1PcInstance pc, int skillid) {
        this._byte = null;
        byte skill_list_values = 32;
        int[] skillList = new int[32];
        L1Skills skill = SkillsTable.get().getTemplate(skillid);
        if (skill.getSkillLevel() > 0) {
            int[] array = skillList;
            int n = skill.getSkillLevel() - 1;
            array[n] += skill.getId();
            this.writeC(160);
            this.writeC(32);
            int[] array2;
            for (int length = (array2 = skillList).length, i = 0; i < length; ++i) {
                int element = array2[i];
                this.writeC(element);
            }
            if (pc != null) {
                pc.removeSkillMastery(skillid);
            }
        }
    }
    
    public S_DelSkill(L1PcInstance pc, int level1, int level2, int level3, int level4, int level5, int level6, int level7, int level8, int level9, int level10, int knight1, int knight2, int de1, int de2, int royal, int un, int elf1, int elf2, int elf3, int elf4, int elf5, int elf6, int k1, int k2, int k3, int l1, int l2, int l3) {
        this._byte = null;
        int i6 = level5 + level6 + level7 + level8;
        int j6 = level9 + level10;
        this.writeC(160);
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
                        pc.removeSkillMastery(skillid);
                    }
                }
                ++rt;
                rtType >>= 1;
            }
        }
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
