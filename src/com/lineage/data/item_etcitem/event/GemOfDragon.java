package com.lineage.data.item_etcitem.event;

import com.lineage.server.serverpackets.S_PacketBoxExp;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.event.LeavesSet;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.data.item_etcitem.brave.StatusBrave;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class GemOfDragon extends ItemExecutor {

    private static final Log _log;
    private int _percentage;
    private int _gfxid;
    private boolean _isCrown;
    private boolean _isKnight;
    private boolean _isElf;
    private boolean _isWizard;
    private boolean _isDarkelf;
    private boolean _isDragonKnight;
    private boolean _isIllusionist;

    static {
        _log = LogFactory.getLog(StatusBrave.class);
    }

    public GemOfDragon() {
        this._percentage = 100;
        this._gfxid = 0;
    }

    public static ItemExecutor get() {
        return new GemOfDragon();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        if (pc == null) {
            return;
        }
        if (!LeavesSet.START) {
            pc.sendPackets(new S_ServerMessage("\\fT尚未開放"));
            return;
        }
        if (pc.getlogintime() >= 960000) {
            pc.sendPackets(new S_ServerMessage("\\fT殷海薩的祝福:已達飽和.無法在使用"));
            return;
        }

        int itemId = item.getItemId();
        if (this.check(pc)) {

            pc.getInventory().removeItem(item, 1L);

            int exp = pc.getlogintime();
            int addexp = exp + this._percentage * LeavesSet.EXP;
            pc.setlogintime(addexp);

            if (pc.getlogintime() > 960000) {
                pc.setlogintime(960000);
            }

            if (this._gfxid > 0) {
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
            }

            if (exp != pc.getlogintime()) {
                pc.sendPackets(new S_PacketBoxExp(pc.getlogintime() / LeavesSet.EXP));
            }

            // ★★★★★ 你要的啟動中提示（純顯示，無效果）★★★★★
            pc.sendPackets(new S_ServerMessage("\\fY殷海薩的祝福效果：經驗提升至 2 倍 、掉寶提升至 1.5 倍（啟動中）"));
        }
    }

    private boolean check(L1PcInstance pc) {
        try {
            if (pc.isCrown() && this._isCrown) return true;
            if (pc.isKnight() && this._isKnight) return true;
            if (pc.isElf() && this._isElf) return true;
            if (pc.isWizard() && this._isWizard) return true;
            if (pc.isDarkelf() && this._isDarkelf) return true;
            if (pc.isDragonKnight() && this._isDragonKnight) return true;
            if (pc.isIllusionist() && this._isIllusionist) return true;
        } catch (Exception e) {
            GemOfDragon._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    private void set_use_type(int use_type) {
        try {
            if (use_type >= 64) { use_type -= 64; this._isIllusionist = true; }
            if (use_type >= 32) { use_type -= 32; this._isDragonKnight = true; }
            if (use_type >= 16) { use_type -= 16; this._isDarkelf = true; }
            if (use_type >= 8) { use_type -= 8; this._isWizard = true; }
            if (use_type >= 4) { use_type -= 4; this._isElf = true; }
            if (use_type >= 2) { use_type -= 2; this._isKnight = true; }
            if (use_type >= 1) { use_type -= 1; this._isCrown = true; }

            if (use_type > 0) {
                GemOfDragon._log.error("GemOfDragon 可執行職業設定錯誤:餘數大於0");
            }
        } catch (Exception e) {
            GemOfDragon._log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void set_set(String[] set) {
        try {
            this._percentage = Integer.parseInt(set[1]);
            if (this._percentage <= 0) {
                GemOfDragon._log.error("GemOfDragon 設置錯誤:恢復百分比小於等於0! 使用預設100");
                this._percentage = 100;
            }
        } catch (Exception ex) {}

        try {
            this._gfxid = Integer.parseInt(set[2]);
        } catch (Exception ex2) {}

        try {
            int user_type = Integer.parseInt(set[3]);
            this.set_use_type(user_type);
        } catch (Exception ex3) {}
    }
}
