package com.lineage.server.types;

public class UByte8
{
    public static byte[] fromArray(long[] buff) {
        byte[] byteBuff = new byte[buff.length * 4];
        for (int i = 0; i < buff.length; ++i) {
            byteBuff[i * 4 + 0] = (byte)(int)(buff[i] & 0xFFL);
            byteBuff[i * 4 + 1] = (byte)(int)(buff[i] >> 8 & 0xFFL);
            byteBuff[i * 4 + 2] = (byte)(int)(buff[i] >> 16 & 0xFFL);
            byteBuff[i * 4 + 3] = (byte)(int)(buff[i] >> 24 & 0xFFL);
        }
        return byteBuff;
    }
    
    public static byte[] fromArray(char[] buff) {
        byte[] byteBuff = new byte[buff.length];
        for (int i = 0; i < buff.length; ++i) {
            byteBuff[i] = (byte)(buff[i] & 'ÿ');
        }
        return byteBuff;
    }
    
    public static byte fromUChar8(char c) {
        return (byte)(c & 'ÿ');
    }
    
    public static byte[] fromULong32(long l) {
        byte[] byteBuff = { (byte)(int)(l & 0xFFL), (byte)(int)(l >> 8 & 0xFFL), (byte)(int)(l >> 16 & 0xFFL), (byte)(int)(l >> 24 & 0xFFL) };
        return byteBuff;
    }
}
