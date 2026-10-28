package com.lineage.server.datatables.sql;

import com.lineage.config.Config;
import com.lineage.DatabaseFactory;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.lineage.server.templates.L1Account;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.AccountStorage;

public class AccountTable implements AccountStorage
{
    private static final Log _log;
    private final Map<String, String> _loginNameList;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(AccountTable.class);
        AccountTable._random = new Random();
    }
    
    public AccountTable() {
        this._loginNameList = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `accounts`";
            ps = co.prepareStatement("SELECT * FROM `accounts`");
            rs = ps.executeQuery();
            while (rs.next()) {
                String login = rs.getString("login").toLowerCase();
                this._loginNameList.put(login, login);
            }
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        AccountTable._log.info("載入已有帳戶名稱資料數量: " + this._loginNameList.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public boolean isAccountUT(String loginName) {
        return this._loginNameList.get(loginName) != null;
    }
    
    @Override
    public L1Account create(String loginName, String pwd, String ip, String host, String spwd) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            L1Account value = new L1Account();
            value.set_login(loginName.toLowerCase());
            value.set_password(pwd);
            value.set_lastactive(lastactive);
            value.set_access_level(0);
            value.set_ip(ip);
            value.set_mac(host);
            value.set_character_slot(0);
            value.set_spw(spwd);
            value.set_countCharacters(0);
            value.set_isLoad(false);
            value.set_server_no(0);
            value.set_first_pay(0);
            value.set_logincheck(0);
            value.set_id_card(AccountTable._random.nextInt(100000000) + 1);
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `accounts` SET `login`=?,`password`=?,`lastactive`=?,`access_level`=?,`ip`=?,`host`=?,`character_slot`=?,`spw`=?,`server_no`=?,`first_pay`=?,`logincheck`=?,`id_card`=?");
            int i = 0;
            ps.setString(++i, value.get_login().toLowerCase());
            ps.setString(++i, value.get_password());
            ps.setTimestamp(++i, value.get_lastactive());
            ps.setInt(++i, 0);
            ps.setString(++i, value.get_ip());
            ps.setString(++i, value.get_mac());
            ps.setInt(++i, 0);
            ps.setString(++i, value.get_spw());
            ps.setInt(++i, value.get_server_no());
            ps.setInt(++i, value.get_first_pay());
            ps.setInt(++i, value.get_logincheck());
            ps.setInt(++i, value.get_id_card());
            ps.execute();
            AccountTable._log.info("新帳號建立: " + value.get_login());
            return value;
        }
        catch (SQLException e) {}
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        return null;
    }
    
    @Override
    public boolean isAccount(String loginName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `accounts` WHERE `login`=?";
            ps = co.prepareStatement("SELECT * FROM `accounts` WHERE `login`=?");
            ps.setString(1, loginName.toLowerCase());
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return false;
    }
    
    @Override
    public L1Account getAccount(String loginName) {
        return this.getAccountInfo(loginName);
    }
    
    private L1Account getAccountInfo(String loginName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `accounts` WHERE `login`=?";
            ps = co.prepareStatement("SELECT * FROM `accounts` WHERE `login`=?");
            ps.setString(1, loginName.toLowerCase());
            rs = ps.executeQuery();
            if (rs.next()) {
                String login = rs.getString("login").toLowerCase();
                String password = rs.getString("password");
                Timestamp lastactive = rs.getTimestamp("lastactive");
                int access_level = rs.getInt("access_level");
                String ip = rs.getString("ip");
                String host = rs.getString("host");
                int character_slot = rs.getInt("character_slot");
                String spw = rs.getString("spw");
                int warehouse = rs.getInt("warehouse");
                int server_no = rs.getInt("server_no");
                int first_pay = rs.getInt("first_pay");
                int logincheck = rs.getInt("logincheck");
                int id_card = rs.getInt("id_card");
                int countCharacters = getPlayers(login);
                L1Account value = new L1Account();
                value.set_login(login);
                value.set_password(password);
                value.set_lastactive(lastactive);
                value.set_access_level(access_level);
                value.set_ip(ip);
                value.set_mac(host);
                value.set_character_slot(character_slot);
                value.set_spw(spw);
                value.set_warehouse(warehouse);
                value.set_countCharacters(countCharacters);
                value.set_server_no(server_no);
                value.set_first_pay(first_pay);
                value.set_logincheck(logincheck);
                value.set_id_card(id_card);
                return value;
            }
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return null;
    }
    
    private static int getPlayers(String loginName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            String sqlstr = "SELECT * FROM `characters` WHERE `account_name`=?";
            ps = co.prepareStatement("SELECT * FROM `characters` WHERE `account_name`=?");
            ps.setString(1, loginName.toLowerCase());
            rs = ps.executeQuery();
            while (rs.next()) {
                ++i;
            }
            return i;
        }
        catch (Exception e) {}
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return 0;
    }
    
    @Override
    public void updateWarehouse(String loginName, int pwd) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "UPDATE `accounts` SET `warehouse`=? WHERE `login`=?";
            pstm = con.prepareStatement("UPDATE `accounts` SET `warehouse`=? WHERE `login`=?");
            pstm.setInt(1, pwd);
            pstm.setString(2, loginName);
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateLastActive(L1Account account) {
        if (account != null) {
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            account.set_lastactive(lastactive);
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactoryLogin.get().getConnection();
                pstm = con.prepareStatement("UPDATE `accounts` SET `lastactive`=?,`ip`=?,`host`=? WHERE `login`=?");
                pstm.setTimestamp(1, lastactive);
                pstm.setString(2, account.get_ip());
                pstm.setString(3, account.get_mac());
                pstm.setString(4, account.get_login());
                pstm.execute();
            }
            catch (Exception e) {
                AccountTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
    }
    
    @Override
    public void updateCharacterSlot(String loginName, int count) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("UPDATE `accounts` SET `character_slot`=? WHERE `login`=?");
            pstm.setInt(1, count);
            pstm.setString(2, loginName);
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updatePwd(String loginName, String newpwd) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("UPDATE `accounts` SET `password`=? WHERE `login`=?");
            pstm.setString(1, newpwd);
            pstm.setString(2, loginName);
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateLan(String loginName, boolean islan) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "UPDATE `accounts` SET `server_no`=? WHERE `login`=?";
            pstm = con.prepareStatement("UPDATE `accounts` SET `server_no`=? WHERE `login`=?");
            if (islan) {
                pstm.setInt(1, Config.SERVERNO);
            }
            else {
                pstm.setInt(1, 0);
            }
            pstm.setString(2, loginName);
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateLan() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "UPDATE `accounts` SET `server_no`=0";
            pstm = con.prepareStatement("UPDATE `accounts` SET `server_no`=0");
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public String getPalyer(String PlayerName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String name = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT `account_name` FROM `characters` WHERE `char_name`=?");
            ps.setString(1, PlayerName);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("account_name");
            }
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return name;
    }
    
    @Override
    public void updatefp(String loginName, int fp) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlfp = "UPDATE `accounts` SET `first_pay`=? WHERE `login`=?";
            pstm = con.prepareStatement("UPDATE `accounts` SET `first_pay`=? WHERE `login`=?");
            pstm.setInt(1, fp);
            pstm.setString(2, loginName);
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void update_idcard(String loginName, int fp) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlfp = "UPDATE `accounts` SET `id_card`=? WHERE `login`=?";
            pstm = con.prepareStatement("UPDATE `accounts` SET `id_card`=? WHERE `login`=?");
            pstm.setInt(1, fp);
            pstm.setString(2, loginName);
            pstm.execute();
        }
        catch (Exception e) {
            AccountTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
