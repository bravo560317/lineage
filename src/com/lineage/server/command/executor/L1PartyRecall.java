package com.lineage.server.command.executor;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import com.lineage.server.model.L1Party;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1PartyRecall implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1PartyRecall.class);
    }
    
    private L1PartyRecall() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1PartyRecall();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1PcInstance target = World.get().getPlayer(arg);
            if (target != null) {
                L1Party party = target.getParty();
                if (party != null) {
                    int x = pc.getX();
                    int y = pc.getY() + 2;
                    short map = pc.getMapId();
                    ConcurrentHashMap<Integer, L1PcInstance> pcs = party.partyUsers();
                    if (pcs.isEmpty()) {
                        return;
                    }
                    else if (pcs.size() <= 0) {
                        return;
                    }
                    else {
                        for (L1PcInstance pc2 : pcs.values()) {
                            try {
                                L1Teleport.teleport(pc2, x, y, map, 5, true);
                                pc2.sendPackets(new S_SystemMessage("管理員召喚!"));
                            }
                            catch (Exception e) {
                                L1PartyRecall._log.error("隊伍召喚異常", e);
                            }
                        }
                    }
                }
                else {
                    pc.sendPackets(new S_SystemMessage(arg + " 不是一個隊伍成員!"));
                }
            }
            else {
                int mode = 3;
                pc.sendPackets(new S_PacketBoxGm(pc, 3));
            }
        }
        catch (Exception e2) {
            L1PartyRecall._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
