package com.lineage.echo;

import java.util.Calendar;

public class PacketWc
{
    public static int Packey() {
        Calendar r = Calendar.getInstance();
        int y = r.get(1);
        return y;
    }
    
    public static int Packem() {
        Calendar r = Calendar.getInstance();
        int m = r.get(2);
        return m;
    }
    
    public static int Packed() {
        Calendar r = Calendar.getInstance();
        int d = r.get(5);
        return d;
    }
    
    public static int Packeh() {
        Calendar r = Calendar.getInstance();
        int h = r.get(11);
        return h;
    }
    
    public static int Packen() {
        Calendar r = Calendar.getInstance();
        int n = r.get(12);
        return n;
    }
}
