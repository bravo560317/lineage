package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpiritCrystal extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpiritCrystal();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            if (!pc.isElf()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 3;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$1829")) {
                    skillid = 129;
                    magicLv = 11;
                }
                else if (nameId.equalsIgnoreCase("$1830")) {
                    skillid = 130;
                    magicLv = 11;
                }
                else if (nameId.equalsIgnoreCase("$1831")) {
                    skillid = 131;
                    magicLv = 11;
                }
                else if (nameId.equalsIgnoreCase("$1832")) {
                    skillid = 137;
                    magicLv = 12;
                }
                else if (nameId.equalsIgnoreCase("$1833")) {
                    skillid = 138;
                    magicLv = 12;
                }
                else if (nameId.equalsIgnoreCase("$3261")) {
                    skillid = 132;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1834")) {
                    skillid = 145;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1835")) {
                    skillid = 146;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1836")) {
                    skillid = 147;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$3262")) {
                    skillid = 133;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1842")) {
                    skillid = 153;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1843")) {
                    skillid = 154;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$3263")) {
                    skillid = 134;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1849")) {
                    skillid = 161;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1850")) {
                    skillid = 162;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1856")) {
                    skillid = 168;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$4716")) {
                    skillid = 160;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1840")) {
                    skillid = 151;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1841")) {
                    skillid = 152;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1846")) {
                    skillid = 157;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("精靈水晶(大地的祝福)")) {
                    skillid = 159;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1856")) {
                    skillid = 168;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$3265")) {
                    skillid = 169;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1837")) {
                    skillid = 148;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("精靈水晶(烈炎氣息)")) {
                    skillid = 155;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1851")) {
                    skillid = 163;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$3267")) {
                    skillid = 171;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$4714")) {
                    skillid = 175;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$4715")) {
                    skillid = 176;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$3266")) {
                    skillid = 170;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1847")) {
                    skillid = 158;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$4716")) {
                    skillid = 160;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1852")) {
                    skillid = 164;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1853")) {
                    skillid = 165;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$4717")) {
                    skillid = 173;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1838")) {
                    skillid = 149;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1839")) {
                    skillid = 150;
                    magicLv = 13;
                }
                else if (nameId.equalsIgnoreCase("$1845")) {
                    skillid = 156;
                    magicLv = 14;
                }
                else if (nameId.equalsIgnoreCase("$1854")) {
                    skillid = 166;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$1855")) {
                    skillid = 167;
                    magicLv = 15;
                }
                else if (nameId.equalsIgnoreCase("$4718")) {
                    skillid = 174;
                    magicLv = 15;
                }
                Skill_Check.check(pc, item, skillid, magicLv, 3);
            }
            return;
        }
    }
}
