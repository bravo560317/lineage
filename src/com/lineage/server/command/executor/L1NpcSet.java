package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.NpcSpawnTable;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1NpcSet implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1NpcSet.class);
    }
    
    private L1NpcSet() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1NpcSet();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            int npcid = Integer.parseInt(arg);
            L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                pc.sendPackets(new S_SystemMessage("找不到該npc。"));
                return;
            }
            else {
                npc.setId(IdFactoryNpc.get().nextId());
                npc.setMap(pc.getMap());
                npc.setX(pc.getX());
                npc.setY(pc.getY());
                npc.setHomeX(npc.getX());
                npc.setHomeY(npc.getY());
                npc.setHeading(pc.getHeading());
                npc.set_showId(pc.get_showId());
                World.get().storeObject(npc);
                World.get().addVisibleObject(npc);
                npc.turnOnOffLight();
                NpcSpawnTable.get().storeSpawn(pc, npc.getNpcTemplate());
            }
        }
        catch (Exception e) {
            L1NpcSet._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
