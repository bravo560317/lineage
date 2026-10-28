package com.lineage.data.item_etcitem.brave;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class StatusBraveCake_1 extends ItemExecutor
{
    private static final Log _log;
    private int _time;
    private int _gfxid;
    private boolean _isCrown;
    private boolean _isKnight;
    private boolean _isElf;
    private boolean _isWizard;
    private boolean _isDarkelf;
    private boolean _isDragonKnight;
    private boolean _isIllusionist;
    private int _consumeItemId;
    private int _consumeItemCount;
    
    static {
        _log = LogFactory.getLog(StatusBraveCake_1.class);
    }
    
    public StatusBraveCake_1() {
        this._time = 600;
    }
    
    public static ItemExecutor get() {
        return new StatusBraveCake_1();
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
                if (this.check(pc)) {
                    if (pc.hasSkillEffect(998)) {
                        pc.killSkillEffectTimer(998);
                    }
                    if (this._consumeItemId > 0) {
                        if (!pc.getInventory().consumeItem(this._consumeItemId, (long)this._consumeItemCount)) {
                            pc.sendPackets(new S_SystemMessage("所需道具不足"));
                            return;
                        }
                    }
                    else {
                        pc.getInventory().removeItem(item, 1L);
                    }
                    L1BuffUtil.cancelAbsoluteBarrier(pc);
                    pc.sendPacketsAll(new S_Liquor(pc.getId(), 8));
                    pc.sendPackets(new S_ServerMessage(1065));
                    if (this._gfxid > 0) {
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                    }
                    pc.setSkillEffect(998, this._time * 1000);

                    // 發送「第三段加速(包含移動+攻擊速度)」封包 (Mode 6 代表龍之珍珠效果)
                    pc.sendPackets(new S_SkillBrave(pc.getId(), 6, this._time));
                    pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 6, 0));
                    pc.setBraveSpeed(6); // 記錄伺服器狀態為三段加速
                }
                else {
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
            return;
        }
    }
    
    private boolean check(L1PcInstance pc) {
        try {
            if (pc.isCrown() && this._isCrown) {
                return true;
            }
            else if (pc.isKnight() && this._isKnight) {
                return true;
            }
            else if (pc.isElf() && this._isElf) {
                return true;
            }
            else if (pc.isWizard() && this._isWizard) {
                return true;
            }
            else if (pc.isDarkelf() && this._isDarkelf) {
                return true;
            }
            else if (pc.isDragonKnight() && this._isDragonKnight) {
                return true;
            }
            else if (pc.isIllusionist() && this._isIllusionist) {
                return true;
            }
        }
        catch (Exception e) {
            StatusBraveCake_1._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    private void set_use_type(int use_type) {
        try {
            if (use_type >= 64) {
                use_type -= 64;
                this._isIllusionist = true;
            }
            if (use_type >= 32) {
                use_type -= 32;
                this._isDragonKnight = true;
            }
            if (use_type >= 16) {
                use_type -= 16;
                this._isDarkelf = true;
            }
            if (use_type >= 8) {
                use_type -= 8;
                this._isWizard = true;
            }
            if (use_type >= 4) {
                use_type -= 4;
                this._isElf = true;
            }
            if (use_type >= 2) {
                use_type -= 2;
                this._isKnight = true;
            }
            if (use_type >= 1) {
                --use_type;
                this._isCrown = true;
            }
            if (use_type > 0) {
                StatusBraveCake_1._log.error("StatusBraveCake 可執行職業設定錯誤:餘數大於0");
            }
        }
        catch (Exception e) {
            StatusBraveCake_1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._time = Integer.parseInt(set[1]);
            if (this._time <= 0) {
                StatusBraveCake_1._log.error("StatusBraveCake 設置錯誤:技能效果時間小於等於0! 使用預設600秒");
                this._time = 600;
            }
        }
        catch (Exception ex) {}
        try {
            this._gfxid = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            int user_type = Integer.parseInt(set[3]);
            this.set_use_type(user_type);
        }
        catch (Exception ex3) {}
        try {
            this._consumeItemId = Integer.parseInt(set[4]);
            this._consumeItemCount = Integer.parseInt(set[5]);
        }
        catch (Exception ex4) {}
    }
}
