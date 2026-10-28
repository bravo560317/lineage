package com.lineage.data.item_etcitem.wand;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Wand_Spell extends ItemExecutor
{
    private static final Log _log;
    private boolean _isCrown;
    private boolean _isKnight;
    private boolean _isElf;
    private boolean _isWizard;
    private boolean _isDarkelf;
    private boolean _isDragonKnight;
    private boolean _isIllusionist;
    private int _skillid;
    
    static {
        _log = LogFactory.getLog(Wand_Spell.class);
    }
    
    public Wand_Spell() {
        this._skillid = 0;
    }
    
    public static ItemExecutor get() {
        return new Wand_Spell();
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
            if (this.check(pc)) {
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                L1SkillUse l1skilluse = new L1SkillUse();
                l1skilluse.handleCommands(pc, this._skillid, pc.getId(), 0, 0, 0, 2);
            }
            else {
                pc.sendPackets(new S_ServerMessage(264));
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
            Wand_Spell._log.error(e.getLocalizedMessage(), e);
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
                Wand_Spell._log.error("Wand_Spell 可執行職業設定錯誤:餘數大於0");
            }
        }
        catch (Exception e) {
            Wand_Spell._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._skillid = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            int user_type = Integer.parseInt(set[2]);
            this.set_use_type(user_type);
        }
        catch (Exception ex2) {}
    }
}
