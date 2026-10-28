package com.lineage.server.command.executor;
import com.lineage.server.datatables.ShopCnTable;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ReloadShop implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ReloadShop.class);
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ReloadShop();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        if (pc == null) {
            L1ReloadShop._log.warn("系統命令執行: " + cmdName + "重新讀取[shop]和[shop_cn]資料表。");
        }
        else {
            pc.sendPackets(new S_SystemMessage("\\aH重新讀取[shop]和[shop_cn]資料表"));
        }
        ShopTable.get().load();
        ShopCnTable.get().restshopCn();
    }
}
