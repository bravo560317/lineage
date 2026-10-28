package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class L1Save implements L1CommandExecutor
{
    private static Logger _log;
    
    static {
        L1Save._log = Logger.getLogger(L1Save.class.getName());
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Save();
    }
    
    @Override
    public void execute(L1PcInstance paramL1PcInstance, String paramString1, String paramString2) {
        try {
            for (L1PcInstance localL1PcInstance : World.get().getAllPlayers()) {
                localL1PcInstance.save();
                localL1PcInstance.saveInventory();
                localL1PcInstance.sendPackets(new S_SystemMessage("您的資料已經受到儲存保護。"));
                paramL1PcInstance.sendPackets(new S_SystemMessage("伺服器資料儲存完畢。"));
                System.out.println("伺服器上的人物資料已儲存到資料庫中。 剩餘記憶體:" + Runtime.getRuntime().freeMemory() / 1024L / 1024L + "MB");
            }
        }
        catch (Exception ex) {}
    }
}
