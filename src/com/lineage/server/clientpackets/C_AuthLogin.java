package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.Random;
import java.util.Iterator;
import com.lineage.server.templates.L1Account;
import com.lineage.server.serverpackets.S_CommonNews;
import com.lineage.config.Config;
import com.lineage.list.OnlineUser;
import com.lineage.config.ConfigIpCheck;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.server.serverpackets.S_LoginResult;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_AuthLogin extends ClientBasePacket
{
    private static final Log _log;
    private static final String _check_accname = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String _check_pwd = "abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#";
    
    static {
        _log = LogFactory.getLog(C_AuthLogin.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            boolean iserror = false;
            String loginName = this.readS().toLowerCase();
            if (loginName.length() > 12) {
                C_AuthLogin._log.warn("不合法的帳號長度:" + client.getIp().toString());
                client.set_error(client.get_error() + 1);
                return;
            }
            else {
                int i = 0;
                while (i < loginName.length()) {
                    String ch = loginName.substring(i, i + 1);
                    if (!"abcdefghijklmnopqrstuvwxyz0123456789".contains(ch)) {
                        C_AuthLogin._log.warn("不被允許的帳號字元!");
                        iserror = true;
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                String password = this.readS();
                if (password.length() > 13) {
                    C_AuthLogin._log.warn("不合法的密碼長度:" + client.getIp().toString());
                    client.set_error(client.get_error() + 1);
                    return;
                }
                else {
                    int j = 0;
                    while (j < password.length()) {
                        String ch2 = password.substring(j, j + 1);
                        if (!"abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#".contains(ch2.toLowerCase())) {
                            C_AuthLogin._log.warn("不被允許的密碼字元!");
                            iserror = true;
                            break;
                        }
                        else {
                            ++j;
                        }
                    }
                    if (!iserror) {
                        this.checkLogin(client, loginName, password, false);
                    }
                    else {
                        client.out().encrypt(new S_LoginResult(10));
                    }
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    public void checkLogin(ClientExecutor client, String loginName, String password, boolean auto) {
        try {
            if (loginName == null || loginName.equals("") || password == null || password.equals("")) {
                return;
            }

            StringBuilder ip = client.getIp();
            StringBuilder mac = client.getMac();

            // 檢查是否在封鎖名單
            if (LanSecurityManager.BANNAMEMAP.containsKey(loginName)) {
                C_AuthLogin._log.warn("禁止登入帳號位置: account=" + loginName + " host=" + client.getIp());
                client.out().encrypt(new S_LoginResult(26));
                C_AuthLogin.KickTimeController kickTime = new C_AuthLogin.KickTimeController(client, null);
                kickTime.schedule();
                return;
            }

            boolean isError = false;
            L1Account account = AccountReading.get().getAccount(loginName);

            // ★ IP 限制：不管帳號存不存在，都要檢查
            int tongip = ConfigIpCheck.ipcount;
            if (tongip != 0) {
                int k = 0;
                for (ClientExecutor tempClient : OnlineUser.get().all()) {
                    if (ip.toString().equalsIgnoreCase(tempClient.getIp().toString()) && ++k > tongip - 1) {
                        client.out().encrypt(new S_LoginResult(39)); // 連線數超過
                        return;
                    }
                }
            }

            if (account == null) {
                if (Config.AUTO_CREATE_ACCOUNTS) {
                    if (mac == null) {
                        mac = ip;
                    }
                    account = AccountReading.get().create(loginName, password, ip.toString(), mac.toString(), "未建立超級密碼");
                } else if (auto) {
                    client.out().encrypt(new S_LoginResult(155));
                    return;
                } else {
                    client.out().encrypt(new S_LoginResult(8));
                    isError = true;
                }
            }

            if (!isError && !account.get_password().equals(password)) {
                if (auto) {
                    client.out().encrypt(new S_LoginResult(149));
                } else {
                    client.out().encrypt(new S_LoginResult(8));
                }
                isError = true;
            }

            if (!isError && OnlineUser.get().isMax()) {
                C_AuthLogin._log.info("人數已達上限");
                client.out().encrypt(new S_LoginResult(39));
                isError = true;
            }

            if (isError) {
                client.set_error(client.get_error() + 1);
                return;
            }

            ClientExecutor inGame = OnlineUser.get().get(loginName);
            if (inGame != null) {
                C_AuthLogin._log.info("相同帳號重複登入: account=" + loginName + " host=" + ip);
                client.out().encrypt(new S_LoginResult(22));
                C_AuthLogin.KickTimeController kickTime2 = new C_AuthLogin.KickTimeController(client, inGame);
                kickTime2.schedule();
            } else if (account.get_server_no() != 0 && account.get_server_no() != Config.SERVERNO) {
                C_AuthLogin._log.info("帳號登入其他服務器: account=" + loginName + " host=" + ip + " 已經登入:" + account.get_server_no() + "伺服器");
                client.out().encrypt(new S_LoginResult(22));
                C_AuthLogin.KickTimeController kickTime2 = new C_AuthLogin.KickTimeController(client, null);
                kickTime2.schedule();
                return;
            } else if (account.is_isLoad()) {
                C_AuthLogin._log.info("相同帳號重複登入: account=" + loginName + " host=" + ip);
                client.out().encrypt(new S_LoginResult(22));
                C_AuthLogin.KickTimeController kickTime2 = new C_AuthLogin.KickTimeController(client, null);
                kickTime2.schedule();
                return;
            } else if (OnlineUser.get().addClient(account, client)) {
                account.set_ip(ip.toString());
                if (mac != null) {
                    account.set_mac(mac.toString());
                }
                AccountReading.get().updateLastActive(account);
                client.setAccount(account);
                client.out().encrypt(new S_LoginResult(0));

                if (Config.NEWS) {
                    this.delay(1000, 1000);
                    client.out().encrypt(new S_CommonNews());
                } else {
                    this.delay(1000, 1000);
                    C_CommonClick common = new C_CommonClick();
                    common.start((byte[]) null, client);
                }
            }

        } catch (Exception ex) {
            C_AuthLogin._log.error("checkLogin 錯誤", ex);
        }
    }

    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    public void delay(int i, int j) {
        try {
            int rnd = new Random().nextInt(i) + j;
            Thread.sleep((long)rnd);
        }
        catch (Exception e) {
            C_AuthLogin._log.info("角色延遲登入系統出錯");
        }
    }
    
    private class KickTimeController implements Runnable
    {
        private ClientExecutor _kick1;
        private ClientExecutor _kick2;
        
        private KickTimeController(ClientExecutor kick1, ClientExecutor kick2) {
            this._kick1 = null;
            this._kick2 = null;
            this._kick1 = kick1;
            this._kick2 = kick2;
        }
        
        private void schedule() {
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
                this._kick1.out().encrypt(new S_Disconnect());
                Thread.sleep(1000L);
                this._kick1.set_error(10);
                if (this._kick2 != null) {
                    this._kick2.set_error(10);
                }
            }
            catch (InterruptedException e) {
                C_AuthLogin._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
