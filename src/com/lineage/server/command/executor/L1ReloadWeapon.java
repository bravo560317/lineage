package com.lineage.server.command.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.WeaponSkillTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
 * GM重讀資料表
 * 
 * @author dexc
 *
 */
public class L1ReloadWeapon implements L1CommandExecutor {

	private static final Log _log = LogFactory.getLog(L1ReloadWeapon.class);

	private L1ReloadWeapon() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1ReloadWeapon();
	}

	@Override
	public void execute(final L1PcInstance pc, final String cmdName, final String arg) {
        if (pc == null) {
            L1Reload._log.warn("系統命令執行: " + cmdName + "重新載入武器資料。");
        }
        else {
            pc.sendPackets(new S_SystemMessage("\\aH重新載入武器資料。"));
        }
        ItemTable.get().loadweapons();
    }
	}

