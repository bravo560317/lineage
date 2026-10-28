package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv5 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv5();
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
            int magicLv = 5;
            if (nameId.equalsIgnoreCase("$537")) {
                skillid = 33;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$538")) {
                skillid = 34;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$539")) {
                skillid = 35;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$540")) {
                skillid = 36;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$541")) {
                skillid = 37;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$1587")) {
                skillid = 38;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1862")) {
                skillid = 39;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1863")) {
                skillid = 40;
                attribute = 0;
            }
            Skill_Check.check(pc, item, skillid, 5, attribute);
            return;
        }
    }
}
