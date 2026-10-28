package com.lineage.server.command.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
 * GM重讀資料表
 * 
 * @author dexc
 *
 */
public class L1ReloadNpc implements L1CommandExecutor {

	private static final Log _log = LogFactory.getLog(L1ReloadNpc.class);

	private L1ReloadNpc() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1ReloadNpc();
	}

	@Override
	public void execute(final L1PcInstance pc, final String cmdName, final String arg) {
        if (pc == null) {
            L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入NPC設置資料數量。");
        }
        else {
            pc.sendPackets(new S_SystemMessage("\\aH重新載入NPC設置資料數量。"));
        }
        NpcTable.get().load();
    }	
}
