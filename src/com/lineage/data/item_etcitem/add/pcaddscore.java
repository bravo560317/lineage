package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.C1_Name_Type_Table;
import com.lineage.data.event.CampSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class pcaddscore extends ItemExecutor
{
    private static final Log _log;
    private int _value;
    
    static {
        _log = LogFactory.getLog(pcaddscore.class);
    }
    
    private pcaddscore() {
    }
    
    public static ItemExecutor get() {
        return new pcaddscore();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            pc.sendPackets(new S_ServerMessage("\\fX你得到了" + this._value + "積分"));
            pc.get_other().add_score(this._value);
            if (CampSet.CAMPSTART && pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0) {
                int lv = C1_Name_Type_Table.get().getLv(pc.get_c_power().get_c1_type(), pc.get_other().get_score());
                if (lv != pc.get_c_power().get_power().get_c1_id()) {
                    pc.get_c_power().set_power(pc, false);
                    pc.sendPackets(new S_ServerMessage("\\fR階級變更:" + pc.get_c_power().get_power().get_c1_name_type()));
                    pc.sendPacketsAll(new S_ChangeName(pc, true));
                }
            }
            try {
                pc.save();
            }
            catch (Exception e) {
                pcaddscore._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._value = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
