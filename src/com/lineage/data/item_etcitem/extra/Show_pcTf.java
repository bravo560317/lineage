package com.lineage.data.item_etcitem.extra;

import com.add.TFUpdate;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Show_pcTf extends ItemExecutor
{
    private static Show_pcTf _instance;
    private static L1PcInstance tgpc;
    
    public static Show_pcTf get() {
        if (Show_pcTf._instance == null) {
            Show_pcTf._instance = new Show_pcTf();
        }
        return Show_pcTf._instance;
    }
    
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance useItem) {
        int spellsc_objid = data[0];
        L1Object target = World.get().findObject(spellsc_objid);
        if (pc.getMapId() == 5153) {
            pc.sendPackets(new S_ServerMessage("此地圖無法使用"));
            return;
        }
        else {
            if (target != null) {
                if (target instanceof L1PcInstance) {
                    L1PcInstance target_pc = Show_pcTf.tgpc = (L1PcInstance)target;
                    // 顯示其他玩家天賦資料時，也必須使用完整 payload，否則 HTML 只會拿到前幾個欄位。
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tfcount", TFUpdate.getInstance().buildTalentInfo(target_pc)));
                    pc.getInventory().removeItem(useItem, 1L);
                }
                else {
                    pc.sendPackets(new S_SystemMessage("只能使用在玩家身上"));
                }
            }
            return;
        }
    }
}
