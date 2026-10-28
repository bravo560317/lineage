package com.lineage.server.command.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.ItemPowerUpdateTable;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
 * GM重讀資料表
 * 
 * @author dexc
 *
 */
public class L1ReLoad_Update implements L1CommandExecutor {

	private static final Log _log = LogFactory.getLog(L1ReLoad_Update.class);

	private L1ReLoad_Update() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1ReLoad_Update();
	}

	@Override
	public void execute(final L1PcInstance pc, final String cmdName, final String arg) {
		try {
			// PolyTable.getInstance().load();
			ItemPowerUpdateTable.get().load();
			// ServerAIEffectTable.reload();
			pc.sendPackets(new S_SystemMessage("\\aH[server_item_power_update]重讀完畢。"));

		} catch (final Exception e) {
			_log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
			// 261 \f1指令錯誤。
			pc.sendPackets(new S_ServerMessage(261));
		}
	}
}
