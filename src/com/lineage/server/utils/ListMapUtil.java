package com.lineage.server.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ListMapUtil
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(ListMapUtil.class);
    }
    
    public static void clear(Queue<?> queue) {
        try {
            if (queue != null) {
                queue.clear();
            }
        }
        catch (Exception e) {
            ListMapUtil._log.error("清空Queue發生異常", e);
        }
    }
    
    public static void clear(Map<?, ?> map) {
        try {
            if (map != null) {
                map.clear();
            }
        }
        catch (Exception e) {
            ListMapUtil._log.error("清空Map發生異常", e);
        }
    }
    
    public static void clear(ArrayList<?> list) {
        try {
            if (list != null) {
                list.clear();
            }
        }
        catch (Exception e) {
            ListMapUtil._log.error("清空ArrayList發生異常", e);
        }
    }
    
    public static void clear(List<?> list) {
        try {
            if (list != null) {
                list.clear();
            }
        }
        catch (Exception e) {
            ListMapUtil._log.error("清空List發生異常", e);
        }
    }
}
