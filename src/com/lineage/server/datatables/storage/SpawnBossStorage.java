package com.lineage.server.datatables.storage;

import java.util.Map;
import java.util.List;
import com.lineage.server.model.L1Spawn;
import java.util.Calendar;

public interface SpawnBossStorage
{
    public abstract void load();
    
    public abstract void upDateNextSpawnTime(int p0, Calendar p1);
    
    public abstract L1Spawn getTemplate(int p0);
    
    public abstract List<Integer> bossIds();
    
    public abstract Map<Integer, L1Spawn> get_bossSpawnTable();
    
    public abstract List<Integer> bossreid();
}
