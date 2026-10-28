package com.lineage.echo;

import java.net.SocketException;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.utils.SystemUtil;
import com.lineage.server.utils.StreamUtil;
import com.lineage.list.OnlineUser;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.server.thread.GeneralThreadPool;
import java.io.IOException;
import java.math.BigInteger;
import com.lineage.config.Config;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.LogFactory;
import com.lineage.echo.encryptions.Encryption;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Account;
import java.net.Socket;
import org.apache.commons.logging.Log;

public class ClientExecutor extends OpcodesClient implements Runnable
{
    private static final Log _log;
    private Socket _csocket;
    private L1Account _account;
    private L1PcInstance _activeChar;
    private StringBuilder _ip;
    private StringBuilder _mac;
    private int _kick;
    private String _kick_reason; // 斷線原因
    private boolean _isStrat;
    private EncryptExecutor _encrypt;
    private DecryptExecutor _decrypt;
    private PacketHandlerExecutor _handler;
    private Encryption _keys;
    private int _error;
    private static final int M = 3;
    private static final int O = 2;
    private int _saveInventory;
    private int _savePc;
    public int _xorByte;
    public long _authdata;
    private PacketHc o;
    private PacketHc m;
    private byte _loginStatus;
    
    static {
        _log = LogFactory.getLog(ClientExecutor.class);
    }
    
    public ClientExecutor(Socket socket) throws IOException {
        this._csocket = null;
        this._account = null;
        this._activeChar = null;
        this._ip = null;
        this._mac = null;
        this._kick = 0;
        this._kick_reason = "未知原因"; // 預設為未知
        this._isStrat = true;
        this._error = -1;
        this._saveInventory = 0;
        this._savePc = 0;
        this._xorByte = -16;
        this._loginStatus = 2;
        this._csocket = socket;
        if (Config.LOGINS_TO_AUTOENTICATION) {
            int randomNumber = (int)(Math.random() * 9.0E8) + 255;
            this._xorByte = randomNumber % 255 + 1;
            this._authdata = new BigInteger(Integer.toString(randomNumber)).modPow(new BigInteger(Config.RSA_KEY_E), new BigInteger(Config.RSA_KEY_N)).longValue();
        }
        this._ip = new StringBuilder().append(socket.getInetAddress().getHostAddress());
        this._handler = new PacketHandler(this);
        this._keys = new Encryption();
        this._decrypt = new DecryptExecutor(this, socket.getInputStream());
        this._encrypt = new EncryptExecutor(this, socket.getOutputStream());
    }
    
    public void start() {
    }
    
    @Override
    public void run() {
        this.o = new PacketHc(this, 2);
        GeneralThreadPool.get().schedule(this.o, 0L);
        try {
            this._encrypt.satrt();
            this._encrypt.outStart();
            boolean isEcho = false;
            while (this._isStrat) {
                byte[] decrypt = null;
                try {
                    decrypt = this.readPacket();
                }
                catch (Exception e) {
                    this._kick_reason = "讀取客戶端封包失敗";
                    ClientExecutor._log.error("讀取客戶端封包失敗: " + this._ip, e);
                    break;
                }
                if (decrypt == null || decrypt.length == 0) {
                    this._kick_reason = "客戶端連線中斷";
                    ClientExecutor._log.warn("客戶端封包為空，結束連線: " + this._ip);
                    break;
                }
                else if (decrypt.length > 1440) {
                    ClientExecutor._log.warn("客戶端送出長度異常封包:" + this._ip.toString() + " 帳號:" + ((this._account != null) ? this._account.get_login() : "未登入"));
                    LanSecurityManager.BANIPMAP.put(this._ip.toString(), 100);
                    // 設置斷線原因
                    this._kick_reason = "封包長度異常";
                    break;
                }
                else {
                    if (this._account != null) {
                        if (!OnlineUser.get().isLan(this._account.get_login())) {
                            break;
                        }
                        else if (!this._account.is_isLoad()) { 
                            // 設置斷線原因
                            this._kick_reason = "帳號未載入/重複登入";
                            break;
                        }
                    }
                    int opcode = decrypt[0] & 0xFF;
                    if (this._activeChar == null) {
                        if (opcode == 14) {
                            isEcho = true;
                        }
                        else if (opcode == 119) {
                            this.m = new PacketHc(this, 3);
                            GeneralThreadPool.get().schedule(this.m, 0L);
                            this.set_savePc(Config.AUTOSAVE_INTERVAL);
                            this.set_saveInventory(Config.AUTOSAVE_INTERVAL_INVENTORY);
                        } else if (opcode == 122) { // C_Disconnect
                            this._kick_reason = "正常播放離開畫面";
                        }
                        if (isEcho) {
                            this._handler.handlePacket(decrypt);
                        }
                        else {
                            continue;
                        }
                    }
                    else if (isEcho) {
                        if (this.m == null) {
                            continue;
                        }
                        else {
                            switch (opcode) {
                                case 7:
                                case 25:
                                case 138: {
                                    this._handler.handlePacket(decrypt);
                                    continue;
                                }
                                case 29: {
                                    this.m.requestWork(decrypt);
                                    continue;
                                }
                                case 122: {
                                    // 設置斷線原因
                                    this._kick_reason = "正常播放離開畫面";
                                    this._isStrat = false;
                                    continue;
                                }
                                default: {
                                    this.o.requestWork(decrypt);
                                    continue;
                                }
                            }
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            // 設置斷線原因
            this._kick_reason = "連線異常 (" + e.getMessage() + ")";
        }
        finally {
            this.set_savePc(-1);
            this.set_saveInventory(-1);
            this.close();
        }
    }
    
    public void close() {
        try {
            String mac = null;
            if (this._mac != null) {
                mac = this._mac.toString();
            }
            if (this._csocket == null) {
                return;
            }
            else {
                this._kick = 0;
                if (this._account != null) {
                    OnlineUser.get().remove(this._account.get_login());
                }
                if (this._activeChar != null) {
                    this.quitGame();
                }
                String ipAddr = this._ip.toString();
                String account = null;
                if (this._kick < 1 && this._account != null) {
                    account = this._account.get_login();
                }
                this._decrypt.stop();
                this._encrypt.stop();
                StreamUtil.close(this._csocket);
                this._handler = null;
                this._mac = null;
                this._ip = null;
                this._activeChar = null;
                this._account = null;
                this._decrypt = null;
                this._encrypt = null;
                this._csocket = null;
                this._keys = null;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\n--------------------------------------------------");
                stringBuilder.append("\n       客戶端 離線: (");
                if (account != null) {
                    stringBuilder.append(account + " ");
                }
                if (mac != null) {
                    stringBuilder.append(" " + mac + " / ");
                }
                // 增加斷線原因
                stringBuilder.append(ipAddr + ") 原因: " + this._kick_reason);
                String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                stringBuilder.append(ipAddr + ") 完成連線中斷!! (" + timeStamp + ")");
                stringBuilder.append("\n--------------------------------------------------");
                ClientExecutor._log.info(stringBuilder.toString());
                SystemUtil.printMemoryUsage(ClientExecutor._log);
            }
        }
        catch (Exception ex) {}
    }
    
    public L1Account getAccount() {
        return this._account;
    }
    
    public void setAccount(L1Account account) {
        this._account = account;
    }
    
    public String getAccountName() {
        if (this._account == null) {
            return null;
        }
        else {
            return this._account.get_login();
        }
    }
    
    public L1PcInstance getActiveChar() {
        return this._activeChar;
    }
    
    public void setActiveChar(L1PcInstance pc) {
        this._activeChar = pc;
    }
    
    public StringBuilder getIp() {
        return this._ip;
    }
    
    public StringBuilder getMac() {
        return this._mac;
    }
    
    public boolean setMac(StringBuilder mac) {
        this._mac = mac;
        return true;
    }
    
    public Socket get_socket() {
        return this._csocket;
    }
    
    public boolean kick() {
        return kick("GM踢除");
    }

    public boolean kick(String reason) {
        try {
            this._encrypt.encrypt(new S_Disconnect());
        }
        catch (Exception ex) {}
        this.quitGame();
        this._kick = 1;
        this._kick_reason = reason; // 設置斷線原因
        this._isStrat = false;
        this.close();
        return true;
    }
    
    public void quitGame() {
        try {
            if (this._activeChar == null) {
                return;
            }
            else {
                synchronized (this._activeChar) {
                    QuitGame.quitGame(this._activeChar);
                    this._activeChar = null;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private byte[] readPacket() {
        try {
            byte[] data = null;
            data = this._decrypt.decrypt();
            return data;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public EncryptExecutor out() {
        return this._encrypt;
    }
    
    public void set_keys(Encryption keys) {
        this._keys = keys;
    }
    
    public Encryption get_keys() {
        return this._keys;
    }
    
    public int get_error() {
        return this._error;
    }
    
    public void set_error(int error) {
        this._error = error;
        if (this._error >= 2) {
            this.kick("密碼錯誤次數過多");
        }
    }
    
    public void set_saveInventory(int saveInventory) {
        this._saveInventory = saveInventory;
    }
    
    public int get_saveInventory() {
        return this._saveInventory;
    }
    
    public void set_savePc(int savePc) {
        this._savePc = savePc;
    }
    
    public int get_savePc() {
        return this._savePc;
    }
    
    public void setLoginStatus(byte i) {
        this._loginStatus = i;
    }
    
    public byte getLoginStatus() {
        return this._loginStatus;
    }
    
    public void RemoveSocket() {
        try {
            this._csocket.setSoTimeout(0);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    public void SetSocket(int time) {
        try {
            this._csocket.setSoTimeout(time * 1000);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
