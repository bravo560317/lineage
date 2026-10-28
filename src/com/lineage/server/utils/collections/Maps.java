package com.lineage.server.utils.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

public class Maps
{
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }
    
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }
}
