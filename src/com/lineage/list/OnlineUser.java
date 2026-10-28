package com.lineage.list;

import java.util.Iterator;
import java.util.Collections;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.config.Config;
import com.lineage.server.templates.L1Account;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.lineage.echo.ClientExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;

public class OnlineUser
{
    private static final Log _log;
    private static OnlineUser _instance;
    private final Map<String, ClientExecutor> _clientList;
    private Collection<String> _allValues;
    private Collection<ClientExecutor> _allClient;
    
    static {
        _log = LogFactory.getLog(OnlineUser.class);
    }
    
    public static OnlineUser get() {
        if (OnlineUser._instance == null) {
            OnlineUser._instance = new OnlineUser();
        }
        return OnlineUser._instance;
    }
    
    private OnlineUser() {
        this._clientList = new ConcurrentHashMap();
    }
    
    public boolean addClient(L1Account value, ClientExecutor client) {
        String accountName = value.get_login();
        ClientExecutor xclient = this._clientList.get(accountName);
        if (xclient == null) {
            client.setAccount(value);
            value.set_isLoad(true);
            value.set_server_no(Config.SERVERNO);
            AccountReading.get().updateLan(accountName, true);
            this._clientList.put(accountName, client);
            OnlineUser._log.info("帳號登入: " + value.get_login() + " 目前連線帳號: " + this._clientList.size());
            return true;
        }
        else {
            xclient.kick();
            client.kick();
            OnlineUser._log.error("連線列表中重複資料: " + value.get_login() + "\n");
            return false;
        }
    }
    
    public boolean isLan(String accountName) {
        ClientExecutor client = this._clientList.get(accountName);
        return client != null;
    }
    
    public void remove(String accountName) {
        ClientExecutor xclient = this._clientList.get(accountName);
        if (xclient != null) {
            L1Account value = xclient.getAccount();
            value.set_isLoad(false);
            value.set_server_no(0);
            AccountReading.get().updateLan(accountName, false);
            this._clientList.remove(accountName);
            OnlineUser._log.info("\n       目前連線帳號: " + this._clientList.size() + "\n");
        }
    }
    
    public ClientExecutor get(String accountName) {
        ClientExecutor client = this._clientList.get(accountName);
        return client;
    }
    
    public Map<String, ClientExecutor> map() {
        return this._clientList;
    }
    
    public Collection<ClientExecutor> all() {
        try {
            Collection<ClientExecutor> vs = this._allClient;
            return (vs != null) ? vs : (this._allClient = (Collection)Collections.unmodifiableCollection(this._clientList.values()));
        }
        catch (Exception e) {
            OnlineUser._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public Collection<String> getObject() {
        Collection<String> vs = this._allValues;
        return (vs != null) ? vs : (this._allValues = (Collection)Collections.unmodifiableCollection(this._clientList.keySet()));
    }
    
    public boolean isMax() {
        return this._clientList.size() >= Config.MAX_ONLINE_USERS;
    }
    
    public int size() {
        return this._clientList.size();
    }
    
    public void kickAll() {
        for (String acc : this._clientList.keySet()) {
            this.remove(acc);
        }
    }
}
