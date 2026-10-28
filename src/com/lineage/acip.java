package com.lineage;

import java.sql.PreparedStatement;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.config.Config;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.net.InetAddress;

public class acip
{
    public static void getItem() {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_kor版本驗證");
            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            Timestamp now_time = new Timestamp(System.currentTimeMillis());
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    int checklock = rs.getInt("安全鎖");
                    Timestamp end_time = rs.getTimestamp("限制結束時間");
                    int ver = rs.getInt("版本編號");
                    if (ver == 10001 && checklock == 1 && now_time.before(end_time)) {
                        isGet = true;
                    }
                }
                if (!isGet) {
                    System.exit(-1);
                    return;
                }
                else if (isGet) {
                    return;
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
    
    public static void getItem(int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_kor版本驗證");
            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("安全鎖");
                    int checkport = rs.getInt("端口");
                    int ezpaycount = rs.getInt("pay");
                    int port = rs.getInt("port");
                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        String[] array;
                        for (int length = (array = portList).length, i = 0; i < length; ++i) {
                            String ports = array[i];
                            int key = Integer.parseInt(ports);
                            if (key == port) {
                                ezpaycount += count;
                                limitedRewardToList(checkip, ezpaycount, port);
                            }
                        }
                    }
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
    
    public static void getItem_people(int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_kor版本驗證");
            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("安全鎖");
                    int checkport = rs.getInt("端口");
                    int people = rs.getInt("目前連線");
                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        String[] array;
                        for (int length = (array = portList).length, i = 0; i < length; ++i) {
                            String ports = array[i];
                            int key = Integer.parseInt(ports);
                            if (key == checkport) {
                                people = count;
                                limitedRewardToList_people(checkip, people, checkport);
                            }
                        }
                    }
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
    
    public static boolean getItem_people1(int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_kor版本驗證");
            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("安全鎖");
                    int checkport = rs.getInt("端口");
                    int people_up = rs.getInt("限制人數");
                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        String[] array;
                        int length = (array = portList).length;
                        int i = 0;
                        while (i < length) {
                            String ports = array[i];
                            int key = Integer.parseInt(ports);
                            if (key == checkport && count >= people_up) {
                                return true;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static void getItemgame() {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_kor版本驗證");
            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            Timestamp now_time = new Timestamp(System.currentTimeMillis());
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("安全鎖");
                    int checkport = rs.getInt("端口");
                    int ezpaycount = rs.getInt("pay");
                    Timestamp end_time = rs.getTimestamp("限制結束時間");
                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        String[] array;
                        for (int length = (array = portList).length, i = 0; i < length; ++i) {
                            String ports = array[i];
                            int key = Integer.parseInt(ports);
                            if (key == checkport && now_time.before(end_time)) {
                                isGet = true;
                            }
                        }
                    }
                }
                if (!isGet) {
                    Thread.sleep(5000L);
                    System.exit(-1);
                    return;
                }
                else if (isGet) {
                    return;
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
    
    public static void limitedRewardToList(String ip, int pay, int port) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryIp.get().getConnection();
            ps = cn.prepareStatement("UPDATE acc_kor版本驗證 SET pay=? WHERE ip=? AND `port`=?");
            int i = 0;
            ps.setInt(++i, pay);
            ps.setString(++i, ip);
            ps.setInt(++i, port);
            ps.execute();
        }
        catch (SQLException e) {
            e.getLocalizedMessage();
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public static void limitedRewardToList_people(String ip, int people, int port) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryIp.get().getConnection();
            ps = cn.prepareStatement("UPDATE acc_kor版本驗證 SET 目前連線=? WHERE ip=? AND `端口`=?");
            int i = 0;
            ps.setInt(++i, people);
            ps.setString(++i, ip);
            ps.setInt(++i, port);
            ps.execute();
        }
        catch (SQLException e) {
            e.getLocalizedMessage();
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
