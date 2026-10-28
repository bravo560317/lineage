package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookLv4 extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookLv4();
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
            int magicLv = 4;
            if (nameId.equalsIgnoreCase("$532")) {
                skillid = 25;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$533")) {
                skillid = 26;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$534")) {
                skillid = 27;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$535")) {
                skillid = 28;
                attribute = 2;
            }
            else if (nameId.equalsIgnoreCase("$536")) {
                skillid = 29;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1586")) {
                skillid = 30;
                attribute = 0;
            }
            else if (nameId.equalsIgnoreCase("$1860")) {
                skillid = 31;
                attribute = 1;
            }
            else if (nameId.equalsIgnoreCase("$1861")) {
                skillid = 32;
                attribute = 0;
            }
            Skill_Check.check(pc, item, skillid, 4, attribute);
            return;
        }
    }
}
