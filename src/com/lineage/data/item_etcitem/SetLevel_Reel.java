package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class SetLevel_Reel extends ItemExecutor
{
    private static final Log _log;
    private int _levelupto;
    
    static {
        _log = LogFactory.getLog(SetLevel_Reel.class);
    }
    
    public SetLevel_Reel() {
        this._levelupto = 1;
    }
    
    public static ItemExecutor get() {
        return new SetLevel_Reel();
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
                if (this._levelupto > pc.getLevel()) {
                    pc.setExp_Direct(ExpTable.getExpByLevel(this._levelupto));
                }
                else {
                    pc.sendPackets(new S_ServerMessage("你目前等級已經夠了"));
                    return;
                }
            }
        }
        catch (Exception e) {
            SetLevel_Reel._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._levelupto = Integer.parseInt(set[1]);
            if (this._levelupto <= 0) {
                SetLevel_Reel._log.error("UserHpr 設置錯誤:最小恢復質小於等於0! 使用預設1");
                this._levelupto = 50;
            }
        }
        catch (Exception ex) {}
    }
}
