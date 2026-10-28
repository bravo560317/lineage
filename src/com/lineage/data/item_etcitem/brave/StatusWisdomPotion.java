package com.lineage.data.item_etcitem.brave;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBoxWisdomPotion;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class StatusWisdomPotion extends ItemExecutor
{
    private static final Log _log;
    private int _time;
    private int _gfxid;
    private static final int _int7 = 64;
    private static final int _int6 = 32;
    private static final int _int5 = 16;
    private static final int _int4 = 8;
    private static final int _int3 = 4;
    private static final int _int2 = 2;
    private static final int _int1 = 1;
    private boolean _isCrown;
    private boolean _isKnight;
    private boolean _isElf;
    private boolean _isWizard;
    private boolean _isDarkelf;
    private boolean _isDragonKnight;
    private boolean _isIllusionist;
    
    static {
        _log = LogFactory.getLog(StatusWisdomPotion.class);
    }
    
    public StatusWisdomPotion() {
        this._time = 300;
        this._gfxid = 0;
    }
    
    public static ItemExecutor get() {
        return new StatusWisdomPotion();
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
                    pc.getInventory().removeItem(item, 1L);
                    L1BuffUtil.cancelAbsoluteBarrier(pc);
                    if (!pc.hasSkillEffect(1004)) {
                        pc.addSp(2);
                        pc.addMpr(2);
                    }
                    if (this._gfxid > 0) {
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                    }
                    pc.sendPackets(new S_PacketBoxWisdomPotion(this._time));
                    pc.setSkillEffect(1004, this._time * 1000);
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
            StatusWisdomPotion._log.error(e.getLocalizedMessage(), e);
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
                StatusWisdomPotion._log.error("StatusWisdomPotion 可執行職業設定錯誤:餘數大於0");
            }
        }
        catch (Exception e) {
            StatusWisdomPotion._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._time = Integer.parseInt(set[1]);
            if (this._time <= 0) {
                StatusWisdomPotion._log.error("StatusWisdomPotion 設置錯誤:技能效果時間小於等於0! 使用預設300秒");
                this._time = 300;
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
    }
}
