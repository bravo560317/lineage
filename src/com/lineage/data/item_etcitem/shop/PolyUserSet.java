package com.lineage.data.item_etcitem.shop;

import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class PolyUserSet extends ItemExecutor
{
    private static final Log _log;
    private int _polyid;
    private int _time;
    
    static {
        _log = LogFactory.getLog(PolyUserSet.class);
    }
    
    public PolyUserSet() {
        this._polyid = -1;
        this._time = 1800;
    }
    
    public static ItemExecutor get() {
        return new PolyUserSet();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int awakeSkillId = pc.getAwakeSkillId();
        if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
            pc.sendPackets(new S_ServerMessage(1384));
            return;
        }
        else if (this._polyid == -1) {
            int itemId = item.getItemId();
            PolyUserSet._log.error("自定義變身捲軸 設定錯誤: " + itemId + " 沒有變身代號!");
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            L1PolyMorph.doPoly(pc, this._polyid, this._time, 1);
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._polyid = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._time = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
