package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_MapID;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1MoveX implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1MoveX.class);
    }
    
    private L1MoveX() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1MoveX();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int locx = Integer.parseInt(st.nextToken());
            int locy = Integer.parseInt(st.nextToken());
            short mapid;
            if (st.hasMoreTokens()) {
                mapid = Short.parseShort(st.nextToken());
            }
            else {
                mapid = pc.getMapId();
            }
            L1MoveX._log.info("測試未知地圖 移動至指定座標邊(參數:LOCX - LOCY - MAPID) 執行的GM:" + pc.getName());
            pc.setX(locx);
            pc.setY(locy);
            pc.setMap(mapid);
            pc.setTempID((int)mapid);
            pc.setHeading(5);
            pc.sendPackets(new S_MapID(pc.getTempID()));
            pc.sendPackets(new S_OwnCharPack(pc));
            pc.sendPackets(new S_CharVisualUpdate(pc));
        }
        catch (Exception e) {
            L1MoveX._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
