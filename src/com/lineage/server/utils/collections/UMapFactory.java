package com.lineage.server.utils.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.WeakHashMap;
import java.util.Collections;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;

public class UMapFactory
{
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }
    
    public static <K, V> Map<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }
    
    public static <K, V> Map<K, V> newTreeMap() {
        return new TreeMap();
    }
    
    public static <K, V> Map<K, V> newReverseTreeMap() {
        return new TreeMap(Collections.reverseOrder());
    }
    
    public static <K, V> Map<K, V> newWeakMap() {
        return new WeakHashMap();
    }
    
    public static <K, V> Map<K, V> newWeakMap(Map<K, V> from) {
        return new WeakHashMap(from);
    }
    
    public static <K, V> Map<K, V> newConcurrentMap() {
        return new ConcurrentHashMap();
    }
    
    public static <K, V> Map<K, V> newConcurrentMap(Map<K, V> from) {
        return new ConcurrentHashMap(from);
    }
    
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }
}
