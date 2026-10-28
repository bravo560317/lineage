package com.lineage.server.datatables;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class LightSpawnTable
{
    private static final Log _log;
    private static LightSpawnTable _instance;
    
    static {
        _log = LogFactory.getLog(LightSpawnTable.class);
    }
    
    public static LightSpawnTable getInstance() {
        if (LightSpawnTable._instance == null) {
            LightSpawnTable._instance = new LightSpawnTable();
        }
        return LightSpawnTable._instance;
    }
    
    private LightSpawnTable() {
        this.FillLightSpawnTable();
    }
    
    private void FillLightSpawnTable() {
    }
}
