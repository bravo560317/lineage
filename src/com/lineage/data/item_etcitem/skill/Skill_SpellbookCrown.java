package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_SpellbookCrown extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_SpellbookCrown();
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
            if (!pc.isCrown()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 4;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("魔法書 (精準目標)")) {
                    skillid = 113;
                    magicLv = 21;
                }
                else if (nameId.equalsIgnoreCase("魔法書 (呼喚盟友)")) {
                    skillid = 116;
                    magicLv = 22;
                }
                else if (nameId.equalsIgnoreCase("魔法書 (激勵士氣)")) {
                    skillid = 114;
                    magicLv = 23;
                }
                else if (nameId.equalsIgnoreCase("魔法書 (援護盟友)")) {
                    skillid = 118;
                    magicLv = 24;
                }
                else if (nameId.equalsIgnoreCase("魔法書 (衝擊士氣)")) {
                    skillid = 117;
                    magicLv = 25;
                }
                else if (nameId.equalsIgnoreCase("魔法書 (鋼鐵士氣)")) {
                    skillid = 115;
                    magicLv = 26;
                }
                else if (nameId.equalsIgnoreCase("魔法書(王者加護)")) {
                    skillid = 119;
                    magicLv = 26;
                }
                Skill_Check.check(pc, item, skillid, magicLv, attribute);
            }
            return;
        }
    }
}
