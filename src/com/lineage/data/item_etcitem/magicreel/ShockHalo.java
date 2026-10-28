package com.lineage.data.item_etcitem.magicreel;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class ShockHalo extends ItemExecutor
{
    private ShockHalo() {
    }
    
    public static ItemExecutor get() {
        return new ShockHalo();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else {
            int targetID = data[0];
            if (targetID == 0) {
                pc.sendPackets(new S_ServerMessage(281));
                return;
            }
            else {
                L1Object target = World.get().findObject(targetID);
                if (target == null) {
                    return;
                }
                else if (pc.getWeapon() == null) {
                    pc.sendPackets(new S_ServerMessage("此狀態無法施法。"));
                    return;
                }
                else if (pc.getWeapon().getItem().getType() != 3) {
                    pc.sendPackets(new S_ServerMessage("此狀態無法施法。"));
                    return;
                }
                else if (pc.getCurrentMp() <= 15) {
                    pc.sendPackets(new S_ServerMessage(278));
                    return;
                }
                else {
                    int spellsc_x = target.getX();
                    int spellsc_y = target.getY();
                    pc.setCurrentMp((int)(short)(pc.getCurrentMp() - 15));
                    L1BuffUtil.cancelAbsoluteBarrier(pc);
                    int skillid = 87;
                    L1SkillUse l1skilluse = new L1SkillUse();
                    l1skilluse.handleCommands(pc, 87, targetID, spellsc_x, spellsc_y, 0, 2);
                    return;
                }
            }
        }
    }
}
