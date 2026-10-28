package com.lineage.data.item_etcitem.hp;

import com.lineage.server.serverpackets.S_SkillSound;
import java.util.Random;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserAddHp_1 extends ItemExecutor
{
    private static final Log _log;
    private int _min_hp;
    private int _gfxid;
    private int _consumeItemId;
    private int _consumeItemCount;
    
    static {
        _log = LogFactory.getLog(UserAddHp_1.class);
    }
    
    public static ItemExecutor get() {
        return new UserAddHp_1();
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
            if (L1BuffUtil.stopPotion(pc)) {
                if (pc.getInventory().consumeItem(this._consumeItemId, (long)this._consumeItemCount)) {
                    UseHeallingPotion(pc, this._min_hp, this._gfxid);
                }
                else {
                    pc.sendPackets(new S_SystemMessage("所需道具不足"));
                }
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
        if (pc.get_other().get_hppotion() > 0) {
            healHp += healHp * pc.get_other().get_hppotion() / 100;
        }
        if (pc.get_other2().get_hppotion() > 0) {
            healHp += healHp * pc.get_other2().get_hppotion() / 100;
        }
        if (pc.get_potion_heal() > 0) {
            healHp += healHp * pc.get_potion_heal() / 100;
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
        if (pc.getCurrentHp() != pc.getMaxHp()) {
            pc.setCurrentHp(pc.getCurrentHp() + healHp);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._min_hp = Integer.parseInt(set[1]);
            if (this._min_hp <= 0) {
                this._min_hp = 1;
                UserAddHp_1._log.error("UserHpr 設置錯誤:最小恢復值小於等於0! 使用預設1");
            }
        }
        catch (Exception ex) {}
        try {
            this._gfxid = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._consumeItemId = Integer.parseInt(set[3]);
            this._consumeItemCount = Integer.parseInt(set[4]);
        }
        catch (Exception ex3) {}
    }
}
