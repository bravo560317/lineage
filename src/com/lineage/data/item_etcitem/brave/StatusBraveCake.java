package com.lineage.data.item_etcitem.brave;

import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class StatusBraveCake extends ItemExecutor
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
    private boolean _isCrown; // 是否允許王族使用
    private boolean _isKnight; // 是否允許騎士使用
    private boolean _isElf; // 是否允許妖精使用
    private boolean _isWizard; // 是否允許法師使用
    private boolean _isDarkelf; // 是否允許黑暗妖精使用
    private boolean _isDragonKnight; // 是否允許龍騎士使用
    private boolean _isIllusionist; // 是否允許幻術師使用
    private boolean _notConsume; // 是否不消耗原本的道具
    private int _need; // 額外需要的消耗道具ID
    private int _count; // 額外需要的消耗道具數量
    
    static {
        _log = LogFactory.getLog(StatusBraveCake.class);
    }
    
    public StatusBraveCake() {
        this._time = 600; // 預設效果時間為600秒
    }
    
    public static ItemExecutor get() {
        return new StatusBraveCake();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1Item needitem = ItemTable.get().getTemplate(this._need);
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            // 檢查玩家是否處於可以喝藥水的狀態
            if (L1BuffUtil.stopPotion(pc)) {
                // 檢查玩家職業是否允許使用該道具
                if (this.check(pc)) {
                    // 若有設定額外道具需求，檢查背包內是否有足夠的數量，並確認 needitem 存在防止報錯
                    if (this._need > 0 && needitem != null && !pc.getInventory().checkItem(this._need, (long)this._count)) {
                        pc.sendPackets(new S_SystemMessage(needitem.getName() + "不足，需要" + this._count + "個才能使用"));
                        return;
                    }
                    else {
                        // 若玩家已經有 998 (勇敢蛋糕) 狀態，則先移除舊的狀態
                        if (pc.hasSkillEffect(998)) {
                            pc.killSkillEffectTimer(998);
                        }
                        // 如果未設定不消耗原道具，則將使用的道具移除
                        if (!this._notConsume) {
                            pc.getInventory().removeItem(item, 1L);
                        }
                        // 如果有額外的消耗道具需求，則將其扣除
                        if (this._need > 0) {
                            pc.getInventory().consumeItem(this._need, (long)this._count);
                        }
                        // 取消絕對屏障狀態
                        L1BuffUtil.cancelAbsoluteBarrier(pc);
                        // 廣播特效封包 (8代表勇敢藥水類特效)
                        pc.sendPacketsAll(new S_Liquor(pc.getId(), 8));
                        // 1065: 系統訊息「吃了勇敢蛋糕」
                        pc.sendPackets(new S_ServerMessage(1065));
                        // 若有設定技能特效ID，則播放該特效
                        if (this._gfxid > 0) {
                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), this._gfxid));
                        }
                        // 給予玩家技能效果，時間從秒數轉換為毫秒
                        pc.setSkillEffect(998, this._time * 1000);
                    }
                }
                else {
                    // 職業不符時，送出系統訊息：79 沒有任何事情發生。
                    pc.sendPackets(new S_ServerMessage(79));
                }
            }
            return;
        }
    }
    
    // 檢查玩家的職業是否在允許使用的範圍內
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
            StatusBraveCake._log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    // 透過傳入的數值解析允許使用的職業 (利用數值遞減的方式解析位元組合)
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
                StatusBraveCake._log.error("StatusBraveCake 可執行職業設定錯誤:餘數大於0");
            }
        }
        catch (Exception e) {
            StatusBraveCake._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    // 設定道具的相關參數陣列 (通常是從資料庫或外部設定檔傳入)
    @Override
    public void set_set(String[] set) {
        try {
            // set[1]: 效果持續時間 (秒)
            this._time = Integer.parseInt(set[1]);
            if (this._time <= 0) {
                StatusBraveCake._log.error("StatusBraveCake 設置錯誤:技能效果時間小於等於0! 使用預設600秒");
                this._time = 600;
            }
        }
        catch (Exception ex) {}
        try {
            // set[2]: 施放特效 ID
            this._gfxid = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            // set[3]: 允許職業的數值組合
            int user_type = Integer.parseInt(set[3]);
            this.set_use_type(user_type);
        }
        catch (Exception ex3) {}
        try {
            // set[4]: (非必填) 是否不消耗原本使用的道具
            if (set.length > 4) {
                this._notConsume = Boolean.parseBoolean(set[4]);
            }
        }
        catch (Exception ex4) {}
        try {
            // set[5]: 額外需求道具 ID
            this._need = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
        try {
            // set[6]: 額外需求道具數量
            this._count = Integer.parseInt(set[6]);
        }
        catch (Exception ex6) {}
    }
}
