package com.lineage.server.datatables.sql;

import com.lineage.config.Config;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.utils.PerformanceTimer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.lineage.config.ConfigIpCheck;
import org.apache.commons.logging.LogFactory;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.IpStorage;

public class IpTable implements IpStorage
{
    private static final Log _log;
    private Timestamp _UnbanTime;
    
    static {
        _log = LogFactory.getLog(IpTable.class);
    }
    
    public Timestamp getUnbanTime() {
        return this._UnbanTime;
    }
    
    @Override
    public void setUnbanTime(Timestamp time) {
        this._UnbanTime = time;
    }
    
    private void ufwDeny(String key) {
        try {
            if (ConfigIpCheck.UFW) {
                String command = "sudo ufw deny from " + key;
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                while ((line = input.readLine()) != null) {
                    IpTable._log.info("Linux 系統命令執行: 防火牆" + line);
                }
            }
        }
        catch (IOException e) {
            IpTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void ufwDeleteDeny(String key) {
        try {
            String command = "sudo ufw delete deny from " + key;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                IpTable._log.info("Linux 系統命令執行: 防火牆" + line);
            }
        }
        catch (IOException e) {
            IpTable._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `ban_ip`");
            rs = ps.executeQuery();
            while (rs.next()) {
                String key = rs.getString("ip");
                if (key.lastIndexOf(".") != -1) {
                    if (!LanSecurityManager.BANIPMAP.containsKey(key)) {
                        LanSecurityManager.BANIPMAP.put(key, 100);
                        IpReading.get().checktime(key);
                    }
                    else {
                        continue;
                    }
                }
                else if (!LanSecurityManager.BANNAMEMAP.containsKey(key)) {
                    LanSecurityManager.BANNAMEMAP.put(key, 100);
                    IpReading.get().checktime(key);
                }
                else {
                    continue;
                }
            }
        }
        catch (SQLException e) {
            IpTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        IpTable._log.info("載入禁止登入IP資料數量: " + LanSecurityManager.BANIPMAP.size() + "(" + timer.get() + "ms)");
        IpTable._log.info("載入禁止登入NAME資料數量: " + LanSecurityManager.BANNAMEMAP.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public void add(String key, String info) {
        boolean isBan = false;
        if (key.lastIndexOf(".") != -1) {
            if (!LanSecurityManager.BANIPMAP.containsKey(key)) {
                LanSecurityManager.BANIPMAP.put(key, 100);
                isBan = true;
                if (Config.ISUBUNTU) {
                    this.ufwDeny(key);
                }
            }
        }
        else if (!LanSecurityManager.BANNAMEMAP.containsKey(key)) {
            LanSecurityManager.BANNAMEMAP.put(key, 100);
            isBan = true;
        }
        if (this.check(key)) {
            isBan = false;
        }
        if (isBan) {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactoryLogin.get().getConnection();
                pstm = con.prepareStatement("INSERT INTO `ban_ip` SET `ip`=?,`info`=?,`datetime`=SYSDATE(), UnbanTime=?");
                int i = 0;
                pstm.setString(++i, key);
                pstm.setString(++i, info);
                pstm.setTimestamp(++i, this.getUnbanTime());
                pstm.execute();
            }
            catch (SQLException e) {
                IpTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
    }
    
    @Override
    public void remove(String key) {
        boolean isBan = false;
        if (key.lastIndexOf(".") != -1) {
            if (LanSecurityManager.BANIPMAP.containsKey(key)) {
                LanSecurityManager.BANIPMAP.remove(key);
                isBan = true;
                if (Config.ISUBUNTU) {
                    IpTable._log.info("******Linux 系統命令執行**************************");
                    this.ufwDeleteDeny(key);
                    IpTable._log.info("******Linux 系統命令完成**************************");
                }
            }
        }
        else if (LanSecurityManager.BANNAMEMAP.containsKey(key)) {
            LanSecurityManager.BANNAMEMAP.remove(key);
            isBan = true;
        }
        if (!this.check(key)) {
            isBan = false;
        }
        if (isBan) {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactoryLogin.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM `ban_ip` WHERE `ip`=?");
                pstm.setString(1, key);
                pstm.execute();
            }
            catch (SQLException e) {
                IpTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
        }
    }
    
    private boolean check(String key) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `ban_ip` WHERE `ip` ='" + key + "'";
            ps = cn.prepareStatement(sqlstr);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            IpTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        return false;
    }
    
    @Override
    public void checktime(String key) {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `ban_ip` WHERE `ip` ='" + key + "'";
            ps = cn.prepareStatement(sqlstr);
            rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("UnbanTime");
                if (ts != null && ts.getTime() < System.currentTimeMillis()) {
                    IpReading.get().remove(key);
                    System.out.println("解除封鎖：" + key);
                }
            }
        }
        catch (SQLException e) {
            IpTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
