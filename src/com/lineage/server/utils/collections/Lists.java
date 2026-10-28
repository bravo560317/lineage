package com.lineage.server.utils.collections;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;

public class Lists
{
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }
    
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList();
    }
}
