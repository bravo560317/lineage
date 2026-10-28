package com.lineage.server.model.SoulTower;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.MapData;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.world.World;
import com.lineage.server.model.map.L1WorldMap;

public class L1SoulTower
{
    boolean[] mapStat;
    static L1SoulTower _soulTower;
    
    private L1SoulTower() {
        this.mapStat = new boolean[50];
        for (int i = 1; i < 50; ++i) {
            L1WorldMap.get().cloneMap(4001, 4001 + i);
            World.get().addMap(4001 + i);
            MapData mapdata = MapsTable.get().getMapData(4001);
            MapsTable.get().setMaps(4001 + i, mapdata);
        }
    }
    
    public static L1SoulTower get() {
        if (L1SoulTower._soulTower == null) {
            L1SoulTower._soulTower = new L1SoulTower();
        }
        return L1SoulTower._soulTower;
    }
    
    public void soulTowerStart(L1PcInstance pc) {
        int i = 0;
        while (i < this.mapStat.length) {
            if (!this.mapStat[i]) {
                this.mapStat[i] = true;
                SoulTowerThread thread = new SoulTowerThread(4001 + i, pc);
                pc.setTempThread(thread);
                GeneralThreadPool.get().execute(thread);
                return;
            }
            else {
                ++i;
            }
        }
    }
}
