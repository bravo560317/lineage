package com.lineage.data.item_etcitem.brave;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class StatusHaste extends ItemExecutor
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
    private int dele;
    
    static {
        _log = LogFactory.getLog(StatusHaste.class);
    }
    
    public StatusHaste() {
        this._time = 300;
        this._gfxid = 0;
        this.dele = 1;
    }
    
    public static ItemExecutor get() {
        return new StatusHaste();
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
                    if (this._gfxid > 0) {
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                    }
                    if (pc.getHasteItemEquipped() > 0) {
                        return;
                    }
                    else {
                        if (this.dele == 1) {
                            pc.getInventory().removeItem(item, 1L);
                        }
                        pc.setDrink(false);
                        L1BuffUtil.hasteStart(pc);
                        L1BuffUtil.cancelAbsoluteBarrier(pc);
                        if (pc.hasSkillEffect(29)) {
                            pc.killSkillEffectTimer(29);
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                        }
                        else if (pc.hasSkillEffect(76)) {
                            pc.killSkillEffectTimer(76);
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                        }
                        else if (pc.hasSkillEffect(152)) {
                            pc.killSkillEffectTimer(152);
                            pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                        }
                        else {
                            pc.sendPackets(new S_SkillHaste(pc.getId(), 1, this._time));
                            pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
                            pc.setMoveSpeed(1);
                            pc.setSkillEffect(1001, this._time * 1000);
                        }
                    }
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
            StatusHaste._log.error(e.getLocalizedMessage(), e);
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
                StatusHaste._log.error("StatusHaste 可執行職業設定錯誤:餘數大於0");
            }
        }
        catch (Exception e) {
            StatusHaste._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._time = Integer.parseInt(set[1]);
            if (this._time <= 0) {
                StatusHaste._log.error("StatusHaste 設置錯誤:技能效果時間小於等於0! 使用預設300秒");
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
        try {
            this.dele = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
    }
}
