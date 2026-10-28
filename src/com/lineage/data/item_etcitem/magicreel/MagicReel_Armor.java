package com.lineage.data.item_etcitem.magicreel;

import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class MagicReel_Armor extends ItemExecutor
{
    private int _skillid;
    private int _consume;
    
    public MagicReel_Armor() {
        this._skillid = 0;
        this._consume = 1;
    }
    
    public static ItemExecutor get() {
        return new MagicReel_Armor();
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
            L1ItemInstance tgItem = pc.getInventory().getItem(targetID);
            if (tgItem == null) {
                return;
            }
            else if (tgItem.getItem().getType2() != 2) {
                pc.sendPackets(new S_ServerMessage(281));
                return;
            }
            else if (tgItem.getItem().getType() != 2) {
                pc.sendPackets(new S_ServerMessage(281));
                return;
            }
            else {
                pc.getInventory().removeItem(item, (long)this._consume);
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                L1SkillUse l1skilluse = new L1SkillUse();
                l1skilluse.handleCommands(pc, this._skillid, targetID, 0, 0, 0, 2);
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._skillid = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._consume = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
