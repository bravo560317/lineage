package com.lineage.data.item_etcitem.hp;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserAddHp_New extends ItemExecutor
{
    private static final Log _log;
    private int _min_hp;
    private int _max_addhp;
    private int _gfxid;
    private int _consumeItemId;
    private int _consumeItemCount;
    private boolean _notConsume;
    
    static {
        _log = LogFactory.getLog(UserAddHp_New.class);
    }
    
    public UserAddHp_New() {
        this._notConsume = true;
    }
    
    public static ItemExecutor get() {
        return new UserAddHp_New();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            else if (pc == null) {
                return;
            }
            else if (L1BuffUtil.stopPotion(pc)) {
                if (this._consumeItemId > 0) {
                    if (!pc.getInventory().consumeItem(this._consumeItemId, (long)this._consumeItemCount)) {
                        pc.sendPackets(new S_SystemMessage("所需道具不足"));
                        return;
                    }
                }
                else if (!this._notConsume) {
                    pc.getInventory().removeItem(item, 1L);
                }
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                if (this._gfxid > 0) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), this._gfxid));
                }
                int addhp = this._min_hp;
                if (this._max_addhp > 0) {
                    addhp += (int)(Math.random() * (double)this._max_addhp);
                }
                if (pc.get_up_hp_potion() > 0) {
                    addhp += addhp * pc.get_up_hp_potion() / 100;
                    addhp += pc.get_uhp_number();
                }
                if (pc.get_potion_heal() > 0) {
                    addhp += addhp * pc.get_potion_heal() / 100;
                }
                if (pc.get_other().get_hppotion() > 0) {
                    addhp += addhp * pc.get_other().get_hppotion() / 100;
                }
                if (pc.get_other2().get_hppotion() > 0) {
                    addhp += addhp * pc.get_other2().get_hppotion() / 100;
                }
                if (pc.getattr_potion_heal() > 0) {
                    addhp += pc.getattr_potion_heal();
                }
                if (pc.hasSkillEffect(173)) {
                    addhp >>= 1;
                }
                if (pc.hasSkillEffect(230)) {
                    addhp >>= 1;
                }
                if (pc.hasSkillEffect(4012)) {
                    addhp >>= 1;
                }
                if (pc.hasSkillEffect(4011)) {
                    addhp *= -1;
                }
                if (pc.isGm()) {
                    pc.sendPackets(new S_SystemMessage("GM顯示當下喝水量:" + addhp));
                }
                pc.setCurrentHp(pc.getCurrentHp() + addhp);
            }
        }
        catch (Exception e) {
            UserAddHp_New._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._min_hp = Integer.parseInt(set[1]);
            if (this._min_hp <= 0) {
                this._min_hp = 1;
                UserAddHp_New._log.error("UserHpr 設置錯誤:最小恢復值小於等於0! 使用預設1");
            }
        }
        catch (Exception ex) {}
        try {
            int max_hp = Integer.parseInt(set[2]);
            if (max_hp >= this._min_hp) {
                this._max_addhp = max_hp - this._min_hp + 1;
            }
            else {
                this._max_addhp = 0;
                UserAddHp_New._log.error("UserHpr 設置錯誤:最大恢復值小於最小恢復值!(" + this._min_hp + " " + max_hp + ")");
            }
        }
        catch (Exception ex2) {}
        try {
            this._gfxid = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._consumeItemId = Integer.parseInt(set[4]);
            this._consumeItemCount = Integer.parseInt(set[5]);
        }
        catch (Exception ex4) {}
    }
}
