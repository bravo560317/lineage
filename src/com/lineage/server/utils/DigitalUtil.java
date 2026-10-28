package com.lineage.server.utils;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DigitalUtil
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(DigitalUtil.class);
    }
    
    public static long returnMin(long[] longs) {
        long i = -1L;
        try {
            for (long count : longs) {
                if (i == -1L) {
                    i = count;
                }
                i = Math.min(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMin(int[] ints) {
        long i = -1L;
        try {
            for (int length = ints.length, j = 0; j < length; ++j) {
                long count = (long)ints[j];
                if (i == -1L) {
                    i = count;
                }
                i = Math.min(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMin(short[] shorts) {
        long i = -1L;
        try {
            for (int length = shorts.length, j = 0; j < length; ++j) {
                long count = (long)shorts[j];
                if (i == -1L) {
                    i = count;
                }
                i = Math.min(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMin(byte[] bytes) {
        long i = -1L;
        try {
            for (int length = bytes.length, j = 0; j < length; ++j) {
                long count = (long)bytes[j];
                if (i == -1L) {
                    i = count;
                }
                i = Math.min(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMax(long[] longs) {
        long i = -1L;
        try {
            for (long count : longs) {
                i = Math.max(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMax(int[] ints) {
        long i = -1L;
        try {
            for (int length = ints.length, j = 0; j < length; ++j) {
                long count = (long)ints[j];
                i = Math.max(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMax(short[] shorts) {
        long i = -1L;
        try {
            for (int length = shorts.length, j = 0; j < length; ++j) {
                long count = (long)shorts[j];
                i = Math.max(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
    
    public static long returnMax(byte[] bytes) {
        long i = -1L;
        try {
            for (int length = bytes.length, j = 0; j < length; ++j) {
                long count = (long)bytes[j];
                i = Math.max(i, count);
            }
        }
        catch (Exception e) {
            DigitalUtil._log.error(e.getLocalizedMessage(), e);
        }
        return i;
    }
}
