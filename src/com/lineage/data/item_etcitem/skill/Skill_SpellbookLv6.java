package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv6 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv6();
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
            int magicLv = 6;
            if (nameId.equalsIgnoreCase("$542")) {
                skillid = 41;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$543")) {
                skillid = 42;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$544")) {
                skillid = 43;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$545")) {
                skillid = 44;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$546")) {
                skillid = 45;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1588")) {
                skillid = 46;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1864")) {
                skillid = 47;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$1865")) {
                skillid = 48;
                attribute = 0;
            }
            Skill_Check.check(pc, item, skillid, 6, attribute);
            return;
        }
    }
}
