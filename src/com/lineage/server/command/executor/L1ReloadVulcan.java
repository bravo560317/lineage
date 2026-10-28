package com.lineage.server.command.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.add.system.L1BlendTable;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
 * GM重讀資料表
 * 
 * @author shinoame
 *
 */
public class L1ReloadVulcan implements L1CommandExecutor {

	private static final Log _log = LogFactory.getLog(L1ReloadVulcan.class);

	private L1ReloadVulcan() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1ReloadVulcan();
	}

	@Override
	public void execute(final L1PcInstance pc, final String cmdName, final String arg) {
        if (pc == null) {
            L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入w_npc火神裝備製作資料數量。");
        }
        else {
            pc.sendPackets(new S_SystemMessage("\\aH重讀w_npc火神裝備製作-資料庫更新。"));
        }
        L1BlendTable.getInstance().loadBlendTable();
    }
	}
