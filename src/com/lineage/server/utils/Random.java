package com.lineage.server.utils;

public class Random
{
    private static final int _max = 32767;
    private static int _idx;
    private static double[] _value;
    private static int _nIndex;
    private static double[] _nArray;
    
    static {
        Random._idx = 0;
        Random._value = new double[32768];
        Random._idx = 0;
        while (Random._idx < 32768) {
            Random._value[Random._idx] = (Math.random() + Math.random() + Math.random() + Math.random() + Math.random()) % 1.0;
            ++Random._idx;
        }
        Random._nIndex = 0;
        Random._nArray = new double[32768];
        do {
            Random._nArray[Random._nIndex] = Math.random();
        } while (getIndex() != 0);
    }
    
    public static int nextInt(int n) {
        Random._idx &= 0x7FFF;
        return (int)(Random._value[Random._idx++] * (double)n);
    }
    
    public static int nextInt(int n, int offset) {
        Random._idx &= 0x7FFF;
        return offset + (int)(Random._value[Random._idx++] * (double)n);
    }
    
    public static boolean nextBoolean() {
        return nextInt(2) == 1;
    }
    
    public static byte nextByte() {
        return (byte)nextInt(256);
    }
    
    public static long nextLong() {
        long l = (long)(nextInt(Integer.MAX_VALUE) << 32 + nextInt(Integer.MAX_VALUE));
        return l;
    }
    
    public static int getInt(int rang) {
        return (int)(getValue() * (double)rang);
    }
    
    private static double getValue() {
        return Random._nArray[getIndex()];
    }
    
    private static int getIndex() {
        return Random._nIndex = (0x7FFF & ++Random._nIndex);
    }
}
