package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpiritCrystal_Water extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpiritCrystal_Water();
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
            else if (pc.getElfAttr() != 4) {
                S_ServerMessage msg = new S_ServerMessage(684);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 3;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$3266")) {
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
                Skill_Check.check(pc, item, skillid, magicLv, 3);
            }
            return;
        }
    }
}
