package com.lineage.server.model;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.datatables.NpcTable;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1WarSpawn
{
    private static final Log _log;
    private static L1WarSpawn _instance;
    
    static {
        _log = LogFactory.getLog(L1WarSpawn.class);
    }
    
    public static L1WarSpawn getInstance() {
        if (L1WarSpawn._instance == null) {
            L1WarSpawn._instance = new L1WarSpawn();
        }
        return L1WarSpawn._instance;
    }
    
    public void SpawnCatapult(int castleId) {
        if (castleId == 1) {
            this.spawnCatapultK();
        }
        else if (castleId == 4) {
            this.spawnCatapultG();
        }
        else if (castleId == 2) {
            this.spawnCatapultO();
        }
    }
    
    public void spawnTower(int castleId) {
        int npcId = 81111;
        if (castleId == 7) {
            npcId = 81189;
        }
        L1Npc l1npc = NpcTable.get().getTemplate(npcId);
        int[] loc = new int[3];
        loc = L1CastleLocation.getTowerLoc(castleId);
        this.SpawnWarObject(l1npc, loc[0], loc[1], (short)loc[2]);
        if (castleId == 7) {
            this.spawnSubTower();
        }
    }
    
    private void spawnSubTower() {
        int[] loc = new int[3];
        for (int i = 1; i <= 4; ++i) {
            L1Npc l1npc = NpcTable.get().getTemplate(81189 + i);
            loc = L1CastleLocation.getSubTowerLoc(i);
            this.SpawnWarObject(l1npc, loc[0], loc[1], (short)loc[2]);
        }
    }
    
    public void SpawnCrown(int castleId) {
        L1Npc l1npc = NpcTable.get().getTemplate(81125);
        int[] loc = new int[3];
        loc = L1CastleLocation.getTowerLoc(castleId);
        this.SpawnWarObject(l1npc, loc[0], loc[1], (short)loc[2]);
    }
    
    public void SpawnFlag(int castleId) {
        L1Npc l1npc = NpcTable.get().getTemplate(81122);
        int[] loc = new int[5];
        loc = L1CastleLocation.getWarArea(castleId);
        int x = 0;
        int y = 0;
        int locx1 = loc[0];
        int locx2 = loc[1];
        int locy1 = loc[2];
        int locy2 = loc[3];
        short mapid = (short)loc[4];
        x = locx1;
        y = locy1;
        while (x <= locx2) {
            this.SpawnWarObject(l1npc, x, y, mapid);
            x += 8;
        }
        x = locx2;
        for (y = locy1; y <= locy2; y += 8) {
            this.SpawnWarObject(l1npc, x, y, mapid);
        }
        x = locx2;
        y = locy2;
        while (x >= locx1) {
            this.SpawnWarObject(l1npc, x, y, mapid);
            x -= 8;
        }
        x = locx1;
        for (y = locy2; y >= locy1; y -= 8) {
            this.SpawnWarObject(l1npc, x, y, mapid);
        }
        switch (castleId) {
            default: {
                return;
            }
        }
    }
    
    private void spawnCatapultG() {
        int[] loc = new int[3];
        for (int i = 1; i <= 4; ++i) {
            L1Npc l1npc = NpcTable.get().getTemplate(90330 + i);
            loc = L1CastleLocation.getCatapultGLoc(i);
            this.SpawnWarObject(l1npc, loc[0], loc[1], (short)loc[2]);
        }
    }
    
    private void spawnCatapultK() {
        int[] loc = new int[3];
        for (int i = 1; i <= 4; ++i) {
            L1Npc l1npc = NpcTable.get().getTemplate(90326 + i);
            loc = L1CastleLocation.getCatapultKLoc(i);
            this.SpawnWarObject(l1npc, loc[0], loc[1], (short)loc[2]);
        }
    }
    
    private void spawnCatapultO() {
        int[] loc = new int[3];
        for (int i = 1; i <= 3; ++i) {
            L1Npc l1npc = NpcTable.get().getTemplate(90334 + i);
            loc = L1CastleLocation.getCatapultOLoc(i);
            this.SpawnWarObject(l1npc, loc[0], loc[1], (short)loc[2]);
        }
    }
    
    private void SpawnWarObject(L1Npc l1npc, int locx, int locy, short mapid) {
        try {
            if (l1npc != null) {
                L1NpcInstance npc = NpcTable.get().newNpcInstance(l1npc);
                npc.setId(IdFactoryNpc.get().nextId());
                npc.setX(locx);
                npc.setY(locy);
                npc.setHomeX(locx);
                npc.setHomeY(locy);
                npc.setHeading(0);
                npc.setMap(mapid);
                World.get().storeObject(npc);
                World.get().addVisibleObject(npc);
                for (L1PcInstance pc : World.get().getAllPlayers()) {
                    npc.addKnownObject(pc);
                    pc.addKnownObject(npc);
                    pc.sendPacketsAll(new S_NPCPack(npc));
                }
            }
        }
        catch (Exception e) {
            L1WarSpawn._log.error(e.getLocalizedMessage(), e);
        }
    }
}
