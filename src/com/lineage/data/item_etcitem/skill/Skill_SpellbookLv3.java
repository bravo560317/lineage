package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv3 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv3();
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
            int magicLv = 3;
            if (nameId.equalsIgnoreCase("$527")) {
                skillid = 17;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$528")) {
                skillid = 18;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$529")) {
                skillid = 19;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$530")) {
                skillid = 20;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$531")) {
                skillid = 21;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1585")) {
                skillid = 22;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1859")) {
                skillid = 23;
                attribute = 0;
            }
            Skill_Check.check(pc, item, skillid, 3, attribute);
            return;
        }
    }
}
