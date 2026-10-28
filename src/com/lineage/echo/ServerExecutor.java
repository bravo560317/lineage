package com.lineage.echo;

import com.lineage.server.utils.StreamUtil;
import java.net.Socket;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.config.ConfigIpCheck;
import com.lineage.server.thread.GeneralThreadPool;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.InetAddress;
import com.lineage.config.Config;
import org.apache.commons.logging.LogFactory;
import java.net.ServerSocket;
import org.apache.commons.logging.Log;

public class ServerExecutor extends Thread
{
    private static final Log _log;
    private ServerSocket _server;
    private int _port;
    private static final String _t1 = "\n\r--------------------------------------------------";
    private static final String _t2 = "\n\r--------------------------------------------------";
    
    static {
        _log = LogFactory.getLog(ServerExecutor.class);
    }
    
    public ServerExecutor(int port) {
        this._port = 0;
        try {
            this._port = port;
            if (!"*".equals(Config.GAME_SERVER_HOST_NAME)) {
                InetAddress inetaddress = InetAddress.getByName(Config.GAME_SERVER_HOST_NAME);
                this._server = new ServerSocket(this._port, 50, inetaddress);
                return;
            }
            else {
                this._server = new ServerSocket(this._port);
                return;
            }
        }
        catch (SocketTimeoutException e) {
            ServerExecutor._log.fatal("連線超時:(" + this._port + ")", e);
        }
        catch (IOException e2) {
            ServerExecutor._log.fatal("IP位置加載錯誤 或 端口位置已被佔用:(" + this._port + ")", e2);
        }
        finally {
            ServerExecutor._log.info("[D] " + this.getClass().getSimpleName() + " 開始監聽服務端口:(" + this._port + ")");
        }
    }
    
    public void stsrtEcho() throws IOException {
        GeneralThreadPool.get().execute(this);
    }
    
    @Override
    public void run() {
        try {
            while (this._server != null) {
                Socket socket = null;
                try {
                    socket = this._server.accept();
                    if (socket != null) {
                        String ipaddr = socket.getInetAddress().getHostAddress();
                        if (ConfigIpCheck.IPCHECKPACK) {
                            socket.setSoTimeout(120000);
                            if (ConfigIpCheck.IPCHECKPACK) {
                                socket.setSoTimeout(1000);
                                LanSecurityManager.BANIPPACK.put(ipaddr, 30);
                            }
                        }
                        String info = "\n\r--------------------------------------------------\n       客戶端 連線遊戲伺服器 服務端口:(" + this._port + ")" + "\n       " + ipaddr + "\n\r--------------------------------------------------";
                        ServerExecutor._log.info(info);
                        ClientExecutor client = new ClientExecutor(socket);
                        GeneralThreadPool.get().execute(client);
                    }
                    else {
                        continue;
                    }
                }
                catch (SecurityException ex) {}
            }
        }
        catch (IOException ex2) {}
        finally {
            String lanInfo = "[D] " + this.getClass().getSimpleName() + " 服務器核心關閉監聽端口(" + this._port + ")";
            ServerExecutor._log.warn(lanInfo);
        }
    }
    
    public void stopEcho() {
        try {
            if (this._server != null) {
                StreamUtil.close(this._server);
                StreamUtil.interrupt(this);
                this._server = null;
            }
        }
        catch (Exception e) {
            ServerExecutor._log.error(e.getLocalizedMessage(), e);
        }
    }
}
