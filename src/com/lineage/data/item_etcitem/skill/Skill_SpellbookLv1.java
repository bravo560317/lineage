package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv1 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv1();
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
            int magicLv = 1;
            if (nameId.equalsIgnoreCase("$517")) {
                skillid = 1;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$518")) {
                skillid = 2;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$519")) {
                skillid = 3;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$520")) {
                skillid = 4;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$521")) {
                skillid = 5;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1581")) {
                skillid = 6;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1582")) {
                skillid = 7;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1857")) {
                skillid = 8;
                attribute = 0;
            }
            Skill_Check.check(pc, item, skillid, 1, attribute);
            return;
        }
    }
}
