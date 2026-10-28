package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv10 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv10();
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
                int magicLv = 10;
                if (nameId.equalsIgnoreCase("$562")) {
                    skillid = 73;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$563")) {
                    skillid = 74;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$564")) {
                    skillid = 75;
                    attribute = 1;
                }
                else if (nameId.equalsIgnoreCase("$565")) {
                    skillid = 76;
                    attribute = 2;
                }
                else if (nameId.equalsIgnoreCase("$566")) {
                    skillid = 77;
                    attribute = 1;
                }
                else if (nameId.equalsIgnoreCase("$1872")) {
                    skillid = 78;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$1873")) {
                    skillid = 79;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("魔法書 (冰雪颶風)")) {
                    skillid = 80;
                    attribute = 2;
                }
                Skill_Check.check(pc, item, skillid, 10, attribute);
            }
            return;
        }
    }
}
