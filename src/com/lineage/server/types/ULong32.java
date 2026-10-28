package com.lineage.server.types;

public class ULong32
{
    public static final long MAX_UNSIGNEDLONG_VALUE = 2147483647L;
    
    public static long fromArray(char[] buff) {
        return fromLong64((long)((buff[3] & 'ÿ') << 24 | (buff[2] & 'ÿ') << 16 | (buff[1] & 'ÿ') << 8 | (buff[0] & 'ÿ')));
    }
    
    public static long fromArray(byte[] buff) {
        return fromLong64((long)((buff[3] & 0xFF) << 24 | (buff[2] & 0xFF) << 16 | (buff[1] & 0xFF) << 8 | (buff[0] & 0xFF)));
    }
    
    public static long fromLong64(long l) {
        return l << 32 >>> 32 & -1L;
    }
    
    public static long fromInt32(int i) {
        return (long)(i << 32 >>> 32 & -1);
    }
    
    public static long add(long l1, long l2) {
        return fromInt32((int)l1 + (int)l2);
    }
    
    public static long sub(long l1, long l2) {
        return fromInt32((int)l1 - (int)l2);
    }
}
