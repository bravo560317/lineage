package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv8 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv8();
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
                int magicLv = 8;
                if (nameId.equalsIgnoreCase("$552")) {
                    skillid = 57;
                    attribute = 1;
                }
                else if (nameId.equalsIgnoreCase("$553")) {
                    skillid = 58;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$554")) {
                    skillid = 59;
                    attribute = 2;
                }
                else if (nameId.equalsIgnoreCase("$555")) {
                    skillid = 60;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$556")) {
                    skillid = 61;
                    attribute = 1;
                }
                else if (nameId.equalsIgnoreCase("$1589")) {
                    skillid = 62;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$1868")) {
                    skillid = 63;
                    attribute = 0;
                }
                else if (nameId.equalsIgnoreCase("$1869")) {
                    skillid = 64;
                    attribute = 0;
                }
                Skill_Check.check(pc, item, skillid, 8, attribute);
            }
            return;
        }
    }
}
