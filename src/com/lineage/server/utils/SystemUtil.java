package com.lineage.server.utils;

import java.lang.management.MemoryUsage;
import java.lang.management.ManagementFactory;
import org.apache.commons.logging.Log;
import java.nio.ByteBuffer;

public class SystemUtil
{
    public static String toHex(ByteBuffer data) {
        StringBuilder result = new StringBuilder();
        int counter = 0;
        while (data.hasRemaining()) {
            if (counter % 16 == 0) {
                result.append(String.format("%04X: ", (Integer)counter));
            }
            int b = data.get() & 0xFF;
            result.append(String.format("%02X ", (Integer)b));
            if (++counter % 16 == 0) {
                result.append("  ");
                toText(data, result, 16);
                result.append("\n");
            }
        }
        int rest = counter % 16;
        if (rest > 0) {
            for (int i = 0; i < 17 - rest; ++i) {
                result.append("   ");
            }
            toText(data, result, rest);
        }
        return result.toString();
    }
    
    private static void toText(ByteBuffer data, StringBuilder result, int cnt) {
        int charPos = data.position() - cnt;
        for (int a = 0; a < cnt; ++a) {
            int c = data.get(charPos++);
            if (c > 31 && c < 128) {
                result.append((char)c);
            }
            else {
                result.append('.');
            }
        }
    }
    
    public static long getUsedMemoryMB() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L / 1024L;
    }
    
    public static void printMemoryUsage(Log log) {
        MemoryUsage hm = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        MemoryUsage nhm = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        String s1 = String.valueOf(hm.getUsed() / 1048576L) + "/" + hm.getMax() / 1048576L + "mb";
        String s2 = String.valueOf(nhm.getUsed() / 1048576L) + "/" + nhm.getMax() / 1048576L + "mb";
        if (log != null) {
            log.info("已分配內存使用量: " + s1);
            log.info("非分配內存使用量: " + s2);
        }
    }
}
