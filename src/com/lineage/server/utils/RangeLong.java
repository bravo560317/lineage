package com.lineage.server.utils;

public class RangeLong
{
    public static StringBuilder scount(long count) {
        String xcount = String.valueOf(count);
        StringBuilder newCount = new StringBuilder();
        newCount.append(xcount);
        int x = xcount.length();
        int index = xcount.length() / 3;
        if (x % 3 == 0) {
            --index;
        }
        for (int i = 0; i < index; ++i) {
            x -= 3;
            newCount.insert(x, ",");
        }
        return newCount;
    }
}
