package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv9 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv9();
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
            if (!pc.isWizard()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 0;
                int magicLv = 9;
                if (nameId.equalsIgnoreCase("$557")) {
                    skillid = 65;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$558")) {
                    skillid = 66;
                    attribute = 2;
                }
                else if (nameId.equalsIgnoreCase("$559")) {
                    skillid = 67;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$560")) {
                    skillid = 68;
                    attribute = 1;
                }
                else if (nameId.equalsIgnoreCase("$561")) {
                    skillid = 69;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$1590")) {
                    skillid = 70;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$1870")) {
                    skillid = 71;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$1871")) {
                    skillid = 72;
                    attribute = 0;
                }
                Skill_Check.check(pc, item, skillid, 9, attribute);
            }
            return;
        }
    }
}
