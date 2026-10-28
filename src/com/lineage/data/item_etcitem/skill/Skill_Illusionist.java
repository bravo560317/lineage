package com.lineage.data.item_etcitem.skill;

import com.lineage.data.cmd.Skill_Check;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Skill_Illusionist extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Skill_Illusionist();
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
            if (!pc.isIllusionist()) {
                S_ServerMessage msg = new S_ServerMessage(79);
                pc.sendPackets(msg);
            }
            else {
                String nameId = item.getItem().getNameId();
                int skillid = 0;
                int attribute = 6;
                int magicLv = 0;
                if (nameId.equalsIgnoreCase("$5681")) {
                    skillid = 201;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5682")) {
                    skillid = 202;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("記憶水晶(粉碎能量)")) {
                    skillid = 203;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5684")) {
                    skillid = 204;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5685")) {
                    skillid = 205;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5686")) {
                    skillid = 206;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5687")) {
                    skillid = 207;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5688")) {
                    skillid = 208;
                    magicLv = 61;
                }
                else if (nameId.equalsIgnoreCase("$5689")) {
                    skillid = 209;
                    magicLv = 62;
                }
                else if (nameId.equalsIgnoreCase("$5690")) {
                    skillid = 210;
                    magicLv = 62;
                }
                else if (nameId.equalsIgnoreCase("$5691")) {
                    skillid = 211;
                    magicLv = 62;
                }
                else if (nameId.equalsIgnoreCase("$5692")) {
                    skillid = 212;
                    magicLv = 62;
                }
                else if (nameId.equalsIgnoreCase("記憶水晶(隱身破壞者)")) {
                    skillid = 213;
                    magicLv = 63;
                }
                else if (nameId.equalsIgnoreCase("$5694")) {
                    skillid = 214;
                    magicLv = 63;
                }
                else if (nameId.equalsIgnoreCase("$5695")) {
                    skillid = 215;
                    magicLv = 63;
                }
                else if (nameId.equalsIgnoreCase("$5696")) {
                    skillid = 216;
                    magicLv = 63;
                }
                else if (nameId.equalsIgnoreCase("$5697")) {
                    skillid = 217;
                    magicLv = 64;
                }
                else if (nameId.equalsIgnoreCase("記憶水晶(疼痛的歡愉)")) {
                    skillid = 218;
                    magicLv = 64;
                }
                else if (nameId.equalsIgnoreCase("$5699")) {
                    skillid = 219;
                    magicLv = 64;
                }
                else if (nameId.equalsIgnoreCase("$5700")) {
                    skillid = 220;
                    magicLv = 64;
                }
                Skill_Check.check(pc, item, skillid, magicLv, 6);
            }
            return;
        }
    }
}
