package com.lineage.data.item_etcitem.shop;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class Up_Score_02 extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Up_Score_02.class);
    }
    
    public static ItemExecutor get() {
        return new Up_Score_02();
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
            else if (pc.hasSkillEffect(7002)) {
                int time = pc.getSkillEffectTimeSec(7002);
                pc.sendPackets(new S_ServerMessage("\\fX陣營積分2倍卷軸使用中 剩餘時間(秒):" + time));
                return;
            }
            else if (pc.hasSkillEffect(7003)) {
                int time = pc.getSkillEffectTimeSec(7003);
                pc.sendPackets(new S_ServerMessage("\\fX陣營積分3倍卷軸使用中 剩餘時間(秒):" + time));
                return;
            }
            else if (pc.getInventory().removeItem(item, 1L) != 1L) {
                return;
            }
            else {
                int time = 600;
                pc.setSkillEffect(7002, time * 1000);
                pc.sendPackets(new S_ServerMessage("陣營積分2倍藥水使用中(600秒)"));
                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 9714));
            }
        }
        catch (Exception e) {
            Up_Score_02._log.error(e.getLocalizedMessage(), e);
        }
    }
}
