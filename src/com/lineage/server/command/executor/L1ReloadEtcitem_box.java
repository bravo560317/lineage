package com.lineage.server.command.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.ItemBoxTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
 * GM重讀寶箱資料表
 * 
 * 指令用途：重新載入 etcitem_box / etcitem_box_key / etcitem_boxs 三張表。
 */
public class L1ReloadEtcitem_box implements L1CommandExecutor {

    private static final Log _log = LogFactory.getLog(L1ReloadEtcitem_box.class);

    private L1ReloadEtcitem_box() {}

    public static L1CommandExecutor getInstance() {
        return new L1ReloadEtcitem_box();
    }

    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            ItemBoxTable.reload();

            if (pc == null) {
                _log.info("系統命令執行: " + cmdName + " 重新載入寶箱資料表。");
            } else {
                pc.sendPackets(new S_SystemMessage("\\aH更新 [etcitem_box][etcitem_box_key][etcitem_boxs] 資料表。"));
            }

        } catch (Exception e) {
            _log.error("執行 " + getClass().getSimpleName() + " 發生錯誤。", e);
            if (pc != null) {
                pc.sendPackets(new S_ServerMessage(261)); // 錯誤指令格式
            }
        }
    }
}
