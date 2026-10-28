package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class ChangClassid extends ItemExecutor
{
    private int a;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ChangClassid.class);
    }
    
    private ChangClassid() {
    }
    
    public static ItemExecutor get() {
        return new ChangClassid();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPackets(new S_DelSkill(pc, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        for (L1ItemInstance item2 : pc.getInventory().getItems()) {
            pc.getInventory().setEquipped(item2, false, false, false);
            pc.setClassId(this.a);
            pc.setTempCharGfx(this.a);
            pc.setGfxId(this.a);
            pc.setElfAttr(0);
            try {
                pc.save();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        this.a = Integer.parseInt(set[1]);
    }
}
