package com.lineage.data.item_etcitem.hp;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import java.util.Random;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class UserAddHp1 extends ItemExecutor
{
    private int _type;
    private int _type1;
    
    public static ItemExecutor get() {
        return new UserAddHp1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            System.out.println("[物件為空值]");
            return;
        }
        else if (pc == null) {
            System.out.println("[人物為空值]");
            return;
        }
        else {
            if (L1BuffUtil.stopPotion(pc) && pc.getMapId() >= 2101 && pc.getMapId() <= 2151) {
                UseHeallingPotion(pc, this._type, this._type1);
                pc.getInventory().removeItem(item, 1L);
            }
            return;
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
        if (pc.get_other().get_hppotion() > 0) {
            healHp += healHp * pc.get_other().get_hppotion() / 100;
        }
        if (pc.get_other2().get_hppotion() > 0) {
            healHp += healHp * pc.get_other2().get_hppotion() / 100;
        }
        if (pc.getattr_potion_heal() > 0) {
            healHp += pc.getattr_potion_heal();
        }
        if (pc.hasSkillEffect(173)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(230)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(4012)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(4011)) {
            healHp *= -1;
        }
        if (pc.isGm()) {
            pc.sendPackets(new S_SystemMessage("GM顯示當下喝水量:" + healHp));
        }
        pc.setCurrentHp(pc.getCurrentHp() + healHp);
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._type1 = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
