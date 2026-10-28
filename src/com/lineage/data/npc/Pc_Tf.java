package com.lineage.data.npc;

import com.add.TFUpdate;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Pc_Tf extends NpcExecutor
{
    private Pc_Tf() {
    }
    
    public static NpcExecutor get() {
        return new Pc_Tf();
    }
    
    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        // 開啟天賦對話時，必須送完整資料，不能只送 11 個欄位。
        // 否則 HTML 會拿不到 #101 / #201 / #301 等設定值，
        // 只能等第一次點擊 button 後才 refresh 成功，造成畫面看起來像「要先點一下才跳出來」。
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tfcount", TFUpdate.getInstance().buildTalentInfo(pc)));
    }
}
