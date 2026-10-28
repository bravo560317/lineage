package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Loc implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Loc.class);
    }
    
    private L1Loc() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Loc();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int locx = pc.getX();
            int locy = pc.getY();
            short mapid = pc.getMapId();
            int gab = pc.getMap().getOriginalTile(locx, locy);
            int h = pc.getHeading();
            String msg = String.format("座標 (%d, %d, %d, %d) %d %s", (Integer)locx, (Integer)locy, (Short)mapid, (Integer)h, (Integer)gab, String.valueOf(pc.getMap().isCombatZone(locx, locy) ? "戰鬥區域" : "") + (pc.getMap().isSafetyZone(locx, locy) ? "安全區域" : "") + (pc.getMap().isNormalZone(locx, locy) ? "一般區域" : ""));
            pc.sendPackets(new S_SystemMessage(msg));
        }
        catch (Exception e) {
            L1Loc._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
