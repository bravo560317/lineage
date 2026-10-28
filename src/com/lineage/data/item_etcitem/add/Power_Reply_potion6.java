package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SkillSound;
import java.util.Random;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Power_Reply_potion6 extends ItemExecutor
{
    private int _type;
    private int _type1;
    private int _type2;
    private int _type3;
    
    private Power_Reply_potion6() {
    }
    
    public static ItemExecutor get() {
        return new Power_Reply_potion6();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else if (pc.hasSkillEffect(71)) {
            pc.sendPackets(new S_ServerMessage(698));
            return;
        }
        else {
            boolean isError = false;
            if (pc.getInventory().checkItem(this._type2, (long)this._type3)) {
                pc.getInventory().consumeItem(this._type2, (long)this._type3);
            }
            else {
                isError = true;
            }
            if (isError) {
                pc.sendPackets(new S_ServerMessage(189));
                return;
            }
            else {
                if (L1BuffUtil.stopPotion(pc)) {
                    UseHeallingPotion(pc, this._type, this._type1);
                }
                return;
            }
        }
    }
    
    private static void UseHeallingPotion(L1PcInstance pc, int healHp, int gfxid) {
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        Random random = new Random();
        pc.sendPacketsX8(new S_SkillSound(pc.getId(), gfxid));
        healHp = (int)((double)healHp * (random.nextGaussian() / 5.0 + 1.0));
        if (pc.get_up_hp_potion() > 0) {
            healHp += healHp * pc.get_up_hp_potion() / 100;
            healHp += pc.get_uhp_number();
        }
        if (pc.get_potion_heal() > 0) {
            healHp += healHp * pc.get_potion_heal() / 100;
        }
        if (pc.get_potion_healling() > 0) {
            healHp += pc.get_potion_healling();
        }
        if (pc.hasSkillEffect(173)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(4012)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(4011)) {
            healHp *= -1;
        }
        if (pc.isGm() && healHp > 0) {
            pc.sendPackets(new S_ServerMessage("回復:" + healHp));
        }
        pc.setCurrentHp(pc.getCurrentHp() + healHp);
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
            if (this._type <= 0) {
                this._type = 1;
            }
        }
        catch (Exception ex) {}
        try {
            this._type1 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._type2 = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._type3 = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
    }
}
