package com.lineage.data.item_weapon;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class KenRauhelBaton extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(KenRauhelBaton.class);
    }
    
    public static ItemExecutor get() {
        return new KenRauhelBaton();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        try {
            switch (data[0]) {
                case 0: {
                    if (pc.hasSkillEffect(4008)) {
                        pc.removeSkillEffect(4008);
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    pc.setSkillEffect(4008, -1);
                    break;
                }
            }
        }
        catch (Exception e) {
            KenRauhelBaton._log.error(e.getLocalizedMessage(), e);
        }
    }
}
