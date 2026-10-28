package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_DarkSpiritCrystal extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_DarkSpiritCrystal();
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
            if (!pc.isDarkelf()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 6;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$2518")) {
                    skillid = 97;
                    magicLv = 41;
                }
                else if (nameId.equalsIgnoreCase("$2519")) {
                    skillid = 98;
                    magicLv = 41;
                }
                else if (nameId.equalsIgnoreCase("$2520")) {
                    skillid = 99;
                    magicLv = 41;
                }
                else if (nameId.equalsIgnoreCase("$2521")) {
                    skillid = 100;
                    magicLv = 41;
                }
                else if (nameId.equalsIgnoreCase("$3172")) {
                    skillid = 109;
                    magicLv = 41;
                }
                else if (nameId.equalsIgnoreCase("$2522")) {
                    skillid = 101;
                    magicLv = 42;
                }
                else if (nameId.equalsIgnoreCase("$2523")) {
                    skillid = 102;
                    magicLv = 42;
                }
                else if (nameId.equalsIgnoreCase("$2524")) {
                    skillid = 103;
                    magicLv = 42;
                }
                else if (nameId.equalsIgnoreCase("$2525")) {
                    skillid = 104;
                    magicLv = 42;
                }
                else if (nameId.equalsIgnoreCase("$3173")) {
                    skillid = 110;
                    magicLv = 42;
                }
                else if (nameId.equalsIgnoreCase("$2526")) {
                    skillid = 105;
                    magicLv = 43;
                }
                else if (nameId.equalsIgnoreCase("$2527")) {
                    skillid = 106;
                    magicLv = 43;
                }
                else if (nameId.equalsIgnoreCase("$2528")) {
                    skillid = 107;
                    magicLv = 43;
                }
                else if (nameId.equalsIgnoreCase("$2529")) {
                    skillid = 108;
                    magicLv = 43;
                }
                else if (nameId.equalsIgnoreCase("$3174")) {
                    skillid = 111;
                    magicLv = 43;
                }
                else if (nameId.equalsIgnoreCase("黑暗精靈水晶(破壞盔甲)")) {
                    skillid = 112;
                    magicLv = 44;
                }
                Skill_Check.check(pc, item, skillid, magicLv, attribute);
            }
            return;
        }
    }
}
