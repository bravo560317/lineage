package com.lineage.data.item_etcitem.event;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Item_Mazu extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Item_Mazu.class);
    }
    
    public static ItemExecutor get() {
        return new Item_Mazu();
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
            else if (pc.hasSkillEffect(8591)) {
                int time = pc.getSkillEffectTimeSec(8591);
                pc.sendPackets(new S_ServerMessage("媽祖的祝福效果剩餘時間(秒):" + time));
                return;
            }
            else {
                int time = 1800;
                pc.setSkillEffect(8591, time * 1000);
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_ServerMessage("感受到媽祖的祝福"));
            }
        }
        catch (Exception e) {
            Item_Mazu._log.error(e.getLocalizedMessage(), e);
        }
    }
}
