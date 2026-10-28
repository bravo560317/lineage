package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv2 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv2();
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
            String nameId = item.getItem().getNameId();
            int skillid = 0;
            int attribute = 0;
            int magicLv = 2;
            if (nameId.equalsIgnoreCase("$522")) {
                skillid = 9;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$523")) {
                skillid = 10;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$524")) {
                skillid = 11;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$525")) {
                skillid = 12;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$526")) {
                skillid = 13;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1858")) {
                skillid = 14;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1583")) {
                skillid = 15;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1584")) {
                skillid = 16;
                attribute = 0;
            }
            Skill_Check.check(pc, item, skillid, 2, attribute);
            return;
        }
    }
}
