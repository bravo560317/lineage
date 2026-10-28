package com.lineage.server;

import java.util.Iterator;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.config.Config;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.echo.ServerExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class EchoServerTimer extends TimerTask
{
    private static final Log _log;
    private static EchoServerTimer _instance;
    private static final Map<Integer, ServerExecutor> _echoList;
    
    static {
        _log = LogFactory.getLog(EchoServerTimer.class);
        _echoList = new HashMap();
    }
    
    public static EchoServerTimer get() {
        if (EchoServerTimer._instance == null) {
            EchoServerTimer._instance = new EchoServerTimer();
        }
        return EchoServerTimer._instance;
    }
    
    public void start() {
        try {
            if (EchoServerTimer._echoList.isEmpty()) {
                this.startEcho();
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            if (Config.RESTART_LOGIN > 0) {
                EchoServerTimer._log.warn("監聽端口重置作業啟動 間隔時間:" + Config.RESTART_LOGIN + "分鐘。");
                int timeMillis = Config.RESTART_LOGIN * 60 * 1000;
                GeneralThreadPool.get().aiScheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
            }
        }
    }
    
    @Override
    public void run() {
        try {
            EchoServerTimer._log.warn("監聽端口重置作業!");
            try {
                this.stopEcho();
                this.startEcho();
            }
            catch (Exception e) {
                EchoServerTimer._log.error("重新啟動端口作業失敗!!", e);
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error("監聽端口重置作業失敗!!", e);
        }
        finally {
            EchoServerTimer._log.warn("監聽端口重置作業完成!!");
        }
    }
    
    public void reStart() {
        try {
            if (Shutdown.SHUTDOWN) {
                return;
            }
            else if (!EchoServerTimer._echoList.isEmpty()) {
                this.stopEcho();
                Thread.sleep(2000L);
                this.startEcho();
            }
            else {
                EchoServerTimer._log.error("監聽端口重置作業失敗(目前無任何監聽線程)!!");
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void startEcho() {
        try {
            String[] portList = Config.GAME_SERVER_PORT.split("-");
            String[] array;
            for (int length = (array = portList).length, i = 0; i < length; ++i) {
                String ports = array[i];
                int key = Integer.parseInt(ports);
                ServerExecutor echoServer = new ServerExecutor(key);
                if (echoServer != null) {
                    EchoServerTimer._echoList.put(new Integer(key), echoServer);
                    echoServer.stsrtEcho();
                }
                Thread.sleep(100L);
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void stopEcho() {
        try {
            if (!EchoServerTimer._echoList.isEmpty()) {
                for (Integer key : EchoServerTimer._echoList.keySet()) {
                    ServerExecutor echoServer = EchoServerTimer._echoList.get(key);
                    if (echoServer != null) {
                        echoServer.stopEcho();
                    }
                    Thread.sleep(100L);
                }
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean isPort(int key) {
        try {
            return EchoServerTimer._echoList.get(new Integer(key)) != null;
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    
    public void stopPort(int key) {
        try {
            ServerExecutor echoServer = EchoServerTimer._echoList.get(new Integer(key));
            if (echoServer != null) {
                echoServer.stopEcho();
                EchoServerTimer._echoList.remove(new Integer(key));
            }
            else {
                EchoServerTimer._log.warn("關閉指定監聽端口 作業失敗:該端口未在作用中!");
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void startPort(int key) {
        try {
            ServerExecutor echoServer = EchoServerTimer._echoList.get(new Integer(key));
            if (echoServer == null) {
                echoServer = new ServerExecutor(key);
                EchoServerTimer._echoList.put(new Integer(key), echoServer);
                echoServer.stsrtEcho();
            }
            else {
                EchoServerTimer._log.warn("啟用指定監聽端口 作業失敗:該端口已在作用中!");
            }
        }
        catch (Exception e) {
            EchoServerTimer._log.error(e.getLocalizedMessage(), e);
        }
    }
}
