package com.lineage.data.item_etcitem;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class AddExpPotion extends ItemExecutor
{
    private static final Log _log;
    private int _add_exp;
    
    static {
        _log = LogFactory.getLog(AddExpPotion.class);
    }
    
    public AddExpPotion() {
        this._add_exp = 1;
    }
    
    public static ItemExecutor get() {
        return new AddExpPotion();
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
            else {
                pc.getInventory().removeItem(item, 1L);
                long exp = (long)this._add_exp;
                if (exp > 0L) {
                    pc.addExp(exp);
                }
            }
        }
        catch (Exception e) {
            AddExpPotion._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._add_exp = Integer.parseInt(set[1]);
            if (this._add_exp <= 0) {
                AddExpPotion._log.error("UserHpr 設置錯誤:最小恢復質小於等於0! 使用預設1");
                this._add_exp = 1;
            }
        }
        catch (Exception ex) {}
    }
}
