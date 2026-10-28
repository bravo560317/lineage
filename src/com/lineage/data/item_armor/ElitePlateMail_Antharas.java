package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class ElitePlateMail_Antharas extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ElitePlateMail_Antharas.class);
    }
    
    public static ItemExecutor get() {
        return new ElitePlateMail_Antharas();
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
                int reduceDmg = 0;
                if (item.getEnchantLevel() >= 7 && item.getEnchantLevel() <= 9) {
                    reduceDmg += item.getEnchantLevel() - 6;
                }
                switch (data[0]) {
                    case 0: {
                        pc.addDamageReductionByArmor(-reduceDmg);
                        pc.set_venom_resist(-1);
                        break;
                    }
                    case 1: {
                        pc.addDamageReductionByArmor(reduceDmg);
                        pc.set_venom_resist(1);
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            ElitePlateMail_Antharas._log.error(e.getLocalizedMessage(), e);
        }
    }
}
