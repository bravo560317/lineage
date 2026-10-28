package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.william.L1Blend_buff;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Quest_blend extends ItemExecutor
{
    private int a;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Quest_blend.class);
    }
    
    private Quest_blend() {
    }
    
    public static ItemExecutor get() {
        return new Quest_blend();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getQuest().get_step(this.a) == 2) {
            pc.sendPackets(new S_SystemMessage("無法重複使用此道具"));
            return;
        }
        else {
            pc.getQuest().set_step(this.a, 1);
            L1Blend_buff.forIntensifyArmor(pc);
            pc.getQuest().set_step(this.a, 2);
            pc.getInventory().removeItem(item, 1L);
            pc.sendPackets(new S_ServerMessage("\\fY完成任務"));
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        this.a = Integer.parseInt(set[1]);
    }
}
