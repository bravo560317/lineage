package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_TechnicalDocument extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_TechnicalDocument();
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
            if (!pc.isKnight()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 0;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$3259")) {
                    skillid = 87;
                    attribute = 5;
                    magicLv = 31;
                }
                else if (nameId.equalsIgnoreCase("$4007")) {
                    skillid = 88;
                    attribute = 5;
                    magicLv = 31;
                }
                else if (nameId.equalsIgnoreCase("$4008")) {
                    skillid = 89;
                    attribute = 5;
                    magicLv = 32;
                }
                else if (nameId.equalsIgnoreCase("$4712")) {
                    skillid = 90;
                    attribute = 5;
                    magicLv = 31;
                }
                else if (nameId.equalsIgnoreCase("$4713")) {
                    skillid = 91;
                    attribute = 5;
                    magicLv = 31;
                }
                Skill_Check.check(pc, item, skillid, magicLv, attribute);
            }
            return;
        }
    }
}
