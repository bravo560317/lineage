package com.lineage;

import com.lineage.server.model.map.L1Map;
import java.util.Map;

public abstract class MapReader
{
    public abstract Map<Integer, L1Map> read();
}
