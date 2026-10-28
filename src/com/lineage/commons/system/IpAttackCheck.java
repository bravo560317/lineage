package com.lineage.commons.system;

import java.util.Iterator;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.config.ConfigIpCheck;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import com.lineage.echo.ClientExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;

public class IpAttackCheck
{
    private static final Log _log;
    public static final Map<ClientExecutor, String> SOCKETLIST;
    private static final HashMap<String, IpAttackCheck.IpTemp> _ipList;
    private static IpAttackCheck _instance;
    
    static {
        _log = LogFactory.getLog(IpAttackCheck.class);
        SOCKETLIST = new ConcurrentHashMap();
        _ipList = new HashMap();
    }
    
    public static IpAttackCheck get() {
        if (IpAttackCheck._instance == null) {
            IpAttackCheck._instance = new IpAttackCheck();
        }
        return IpAttackCheck._instance;
    }
    
    private IpAttackCheck() {
        IpAttackCheck._ipList.clear();
    }
    
    public boolean check(String key) {
        try {
            long nowtime = System.currentTimeMillis();
            IpAttackCheck.IpTemp value = IpAttackCheck._ipList.get(key);
            if (value == null) {
                value = new IpAttackCheck.IpTemp();
                value._time = nowtime;
                value._count = 1;
                IpAttackCheck._ipList.put(key, value);
                IpAttackCheck._log.warn("初始連接 錯誤次數為: " + value._count);
            }
            else if (nowtime - value._time <= (long)ConfigIpCheck.TIMEMILLIS) {
                IpAttackCheck.IpTemp ipTemp = value;
                ++ipTemp._count;
                IpAttackCheck._log.warn("IP: " + key + " 連接間隔不正常 錯誤次數為: " + value._count);
                if (value._count >= ConfigIpCheck.COUNT) {
                    IpAttackCheck._log.warn("連接間隔不正常 錯誤次數為: " + value._count + " 封鎖IP: " + key);
                    this.kick(key);
                    if (ConfigIpCheck.SETDB) {
                        IpReading.get().add(key, "IP類似攻擊行為");
                        System.out.println("IP類似攻擊行為" + key);
                        return false;
                    }
                }
            }
            else {
                value._time = nowtime;
                value._count = 1;
            }
        }
        catch (Exception e) {
            IpAttackCheck._log.error(e.getLocalizedMessage(), e);
        }
        return true;
    }
    
    private void kick(String key) {
        try {
            for (ClientExecutor socket : IpAttackCheck.SOCKETLIST.keySet()) {
                String ip = IpAttackCheck.SOCKETLIST.get(socket);
                if (ip != null && ip.equals(key)) {
                    socket.close();
                }
            }
        }
        catch (Exception e) {
            IpAttackCheck._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private class IpTemp
    {
        long _time;
        int _count;
    }
}
