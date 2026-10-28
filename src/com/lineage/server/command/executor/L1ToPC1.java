package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ToPC1 implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ToPC2.class);
    }
    
    private L1ToPC1() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ToPC1();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1PcInstance target = World.get().getPlayer(arg);
            if (target != null) {
                pc.set_showId(target.get_showId());
                L1Teleport.teleport(pc, target.getX(), target.getY(), target.getMapId(), 5, false);
                pc.sendPackets(new S_ServerMessage(166, "移動座標至指定人物身邊: " + arg));
            }
            else {
                int mode = 1;
                pc.sendPackets(new S_PacketBoxGm(pc, 1));
            }
        }
        catch (Exception e) {
            L1ToPC1._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
