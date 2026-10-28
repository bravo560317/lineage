package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_DragonKnigh extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_DragonKnigh();
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
            if (!pc.isDragonKnight()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 6;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$5778")) {
                    skillid = 181;
                    magicLv = 51;
                }
                else if (nameId.equalsIgnoreCase("$5701")) {
                    skillid = 182;
                    magicLv = 51;
                }
                else if (nameId.equalsIgnoreCase("$5702")) {
                    skillid = 183;
                    magicLv = 51;
                }
                else if (nameId.equalsIgnoreCase("$5703")) {
                    skillid = 184;
                    magicLv = 51;
                }
                else if (nameId.equalsIgnoreCase("$5704")) {
                    skillid = 185;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5705")) {
                    skillid = 186;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5706")) {
                    skillid = 187;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5707")) {
                    skillid = 188;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("龍騎士書版(岩漿之箭)")) {
                    skillid = 189;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5709")) {
                    skillid = 190;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5710")) {
                    skillid = 191;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5711")) {
                    skillid = 192;
                    magicLv = 52;
                }
                else if (nameId.equalsIgnoreCase("$5712")) {
                    skillid = 193;
                    magicLv = 53;
                }
                else if (nameId.equalsIgnoreCase("龍騎士書版(暴龍之眼)")) {
                    skillid = 194;
                    magicLv = 53;
                }
                else if (nameId.equalsIgnoreCase("$5714")) {
                    skillid = 195;
                    magicLv = 53;
                }
                Skill_Check.check(pc, item, skillid, magicLv, 6);
            }
            return;
        }
    }
}
