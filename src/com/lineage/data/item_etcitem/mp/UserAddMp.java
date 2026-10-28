package com.lineage.data.item_etcitem.mp;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class UserAddMp extends ItemExecutor
{
    private static final Log _log;
    private int _min_mp;
    private int _max_addmp;
    private int _gfxid;
    private int _consumeItemId;
    private int _consumeItemCount;
    
    static {
        _log = LogFactory.getLog(UserAddMp.class);
    }
    
    public static ItemExecutor get() {
        return new UserAddMp();
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
                    }
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                }
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                if (this._gfxid > 0) {
                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                }
                int addmp = this._min_mp;
                if (this._max_addmp > 0) {
                    addmp += (int)(Math.random() * (double)this._max_addmp);
                }
                if (addmp > 0) {
                    pc.sendPackets(new S_ServerMessage(338, "$1084"));
                }
                pc.setCurrentMp(pc.getCurrentMp() + addmp);
            }
        }
        catch (Exception e) {
            UserAddMp._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._min_mp = Integer.parseInt(set[1]);
            if (this._min_mp <= 0) {
                this._min_mp = 1;
                UserAddMp._log.error("UserMpr 設置錯誤:最小恢復值小於等於0! 使用預設1");
            }
        }
        catch (Exception ex) {}
        try {
            int max_hp = Integer.parseInt(set[2]);
            if (max_hp >= this._min_mp) {
                this._max_addmp = max_hp - this._min_mp + 1;
            }
            else {
                this._max_addmp = 0;
                UserAddMp._log.error("UserMpr 設置錯誤:最大恢復值小於最小恢復值!(" + this._min_mp + " " + max_hp + ")");
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
