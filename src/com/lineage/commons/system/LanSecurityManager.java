package com.lineage.commons.system;

import java.util.Iterator;
import java.io.IOException;
import com.lineage.server.thread.GeneralThreadPool;
import java.security.Permission;
import com.lineage.config.ConfigIpCheck;
import com.lineage.server.datatables.lock.IpReading;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class LanSecurityManager extends SecurityManager
{
    private static final Log _log;
    public static final Map<String, Integer> BANIPPACK;
    public static final Map<String, Integer> BANIPMAP;
    public static final Map<String, Integer> BANNAMEMAP;
    public static final Map<String, Integer> ONEIPMAP;
    public static final Map<String, Long> ONETIMEMILLISMAP;
    public static final Map<String, Integer> Loginer;
    
    static {
        _log = LogFactory.getLog(LanSecurityManager.class);
        BANIPPACK = new ConcurrentHashMap();
        BANIPMAP = new HashMap();
        BANNAMEMAP = new HashMap();
        ONEIPMAP = new ConcurrentHashMap();
        ONETIMEMILLISMAP = new ConcurrentHashMap();
        Loginer = new HashMap();
    }
    
    @Override
    public void checkAccept(String host, int port) {
        IpReading.get().checktime(host);
        if (ConfigIpCheck.IPCHECKPACK) {
            if (LanSecurityManager.BANIPMAP.containsKey(host)) {
                throw new SecurityException();
            }
            else if (LanSecurityManager.BANIPPACK.containsKey(host)) {
                throw new SecurityException();
            }
        }
        else if (LanSecurityManager.BANIPMAP.containsKey(host)) {
            throw new SecurityException();
        }
        else if (LanSecurityManager.ONEIPMAP.containsKey(host)) {
            throw new SecurityException();
        }
        else if (LanSecurityManager.ONETIMEMILLISMAP.containsKey(host)) {
            throw new SecurityException();
        }
        else {
            if (ConfigIpCheck.ONETIMEMILLIS != 0) {
                LanSecurityManager.ONETIMEMILLISMAP.put(host, System.currentTimeMillis());
            }
            if (ConfigIpCheck.ISONEIP) {
                LanSecurityManager.ONEIPMAP.put(host, port);
            }
            if (ConfigIpCheck.IPCHECK && !IpAttackCheck.get().check(host)) {
                throw new SecurityException();
            }
        }
    }
    
    @Override
    public void checkAccess(Thread t) {
    }
    
    @Override
    public void checkPermission(Permission perm) {
    }
    
    public void stsrt_cmd() throws IOException {
        LanSecurityManager.RemoveIp removeIp = new LanSecurityManager.RemoveIp(ConfigIpCheck.ONETIMEMILLIS);
        GeneralThreadPool.get().execute(removeIp);
    }
    
    public void stsrt_cmd_tmp() throws IOException {
        LanSecurityManager.RemoveTmpIp removeIp = new LanSecurityManager.RemoveTmpIp();
        GeneralThreadPool.get().execute(removeIp);
    }
    
    private class RemoveTmpIp implements Runnable
    {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000L);
                    if (!LanSecurityManager.BANIPPACK.isEmpty()) {
                        for (String ip : LanSecurityManager.BANIPPACK.keySet()) {
                            int time = LanSecurityManager.BANIPPACK.get(ip) - 1;
                            if (time <= 0) {
                                LanSecurityManager.BANIPPACK.remove(ip);
                            }
                            else {
                                LanSecurityManager.BANIPPACK.put(ip, time);
                            }
                        }
                        continue;
                    }
                    else {
                        continue;
                    }
                }
                catch (Exception e) {
                    LanSecurityManager._log.error(e.getLocalizedMessage(), e);
                    return;
                }
            }
        }
    }
    
    private class RemoveIp implements Runnable
    {
        public int _time;
        
        public RemoveIp(int oNETIMEMILLIS) {
            this._time = 60000;
            this._time = oNETIMEMILLIS;
        }
        
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000L);
                    if (!LanSecurityManager.ONETIMEMILLISMAP.isEmpty()) {
                        for (String ip : LanSecurityManager.ONETIMEMILLISMAP.keySet()) {
                            long time = LanSecurityManager.ONETIMEMILLISMAP.get(ip);
                            if (System.currentTimeMillis() - time >= (long)this._time) {
                                LanSecurityManager.ONETIMEMILLISMAP.remove(ip);
                            }
                        }
                        continue;
                    }
                    else {
                        continue;
                    }
                }
                catch (Exception e) {
                    LanSecurityManager._log.error(e.getLocalizedMessage(), e);
                    return;
                }
            }
        }
    }
}
