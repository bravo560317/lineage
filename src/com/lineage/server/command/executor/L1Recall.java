package com.lineage.server.command.executor;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import java.util.ArrayList;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Recall implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Recall.class);
    }
    
    private L1Recall() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Recall();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            Collection<L1PcInstance> targets = null;
            if (arg.equalsIgnoreCase("all")) {
                targets = World.get().getAllPlayers();
            }
            else {
                targets = new ArrayList();
                L1PcInstance tg = World.get().getPlayer(arg);
                if (tg == null) {
                    int mode = 2;
                    pc.sendPackets(new S_PacketBoxGm(pc, 2));
                    return;
                }
                else {
                    targets.add(tg);
                }
            }
            for (L1PcInstance target : targets) {
                if (target.isGm()) {
                    continue;
                }
                else {
                    L1Teleport.teleportToTargetFront(target, pc, 2);
                    target.sendPackets(new S_SystemMessage("管理者召喚。"));
                }
            }
        }
        catch (Exception e) {
            L1Recall._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
