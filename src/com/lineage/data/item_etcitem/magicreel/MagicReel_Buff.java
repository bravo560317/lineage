package com.lineage.data.item_etcitem.magicreel;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class MagicReel_Buff extends ItemExecutor
{
    private int _skillid;
    private int _consume;
    
    public MagicReel_Buff() {
        this._skillid = 0;
        this._consume = 1;
    }
    
    public static ItemExecutor get() {
        return new MagicReel_Buff();
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
                int spellsc_x = target.getX();
                int spellsc_y = target.getY();
                pc.getInventory().removeItem(item, (long)this._consume);
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                L1SkillUse l1skilluse = new L1SkillUse();
                l1skilluse.handleCommands(pc, this._skillid, targetID, spellsc_x, spellsc_y, 0, 2);
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
