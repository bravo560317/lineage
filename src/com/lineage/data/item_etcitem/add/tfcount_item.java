package com.lineage.data.item_etcitem.add;

import com.add.TFUpdate;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class tfcount_item extends ItemExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(tfcount_item.class);
    }
    
public static tfcount_item get() {
    return new tfcount_item();
}
    
	public void showTfCount(L1PcInstance pc) {
    // 這條舊入口曾經只送 11 欄位，會把完整天賦資料覆蓋掉；
    // 一旦點擊天賦按鈕或其他 tfcount 觸發器再次進來，就只剩原本幾項顯示。
    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tfcount", TFUpdate.getInstance().buildTalentInfo(pc)));
}
@Override
public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
    showTfCount(pc);
}
}
