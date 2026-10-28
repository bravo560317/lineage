package com.lineage.server.types;

public class UChar8
{
    public static char[] fromArray(long[] buff) {
        char[] charBuff = new char[buff.length * 4];
        for (int i = 0; i < buff.length; ++i) {
            charBuff[i * 4 + 0] = (char)(int)(buff[i] & 0xFFL);
            charBuff[i * 4 + 1] = (char)(int)(buff[i] >> 8 & 0xFFL);
            charBuff[i * 4 + 2] = (char)(int)(buff[i] >> 16 & 0xFFL);
            charBuff[i * 4 + 3] = (char)(int)(buff[i] >> 24 & 0xFFL);
        }
        return charBuff;
    }
    
    public static char[] fromArray(byte[] buff) {
        char[] charBuff = new char[buff.length];
        for (int i = 0; i < buff.length; ++i) {
            charBuff[i] = (char)(buff[i] & 0xFF);
        }
        return charBuff;
    }
    
    public static char[] fromArray(byte[] buff, int length) {
        char[] charBuff = new char[length];
        for (int i = 0; i < length; ++i) {
            charBuff[i] = (char)(buff[i] & 0xFF);
        }
        return charBuff;
    }
    
    public static char fromUByte8(byte b) {
        return (char)(b & 0xFF);
    }
    
    public static char[] fromULong32(long l) {
        char[] charBuff = { (char)(int)(l & 0xFFL), (char)(int)(l >> 8 & 0xFFL), (char)(int)(l >> 16 & 0xFFL), (char)(int)(l >> 24 & 0xFFL) };
        return charBuff;
    }
}
