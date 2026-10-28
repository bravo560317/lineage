package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpiritCrystal_Earth extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpiritCrystal_Earth();
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
            else if (pc.getElfAttr() != 1) {
                S_ServerMessage msg = new S_ServerMessage(684);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 3;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$1840")) {
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
                Skill_Check.check(pc, item, skillid, magicLv, 3);
            }
            return;
        }
    }
}
