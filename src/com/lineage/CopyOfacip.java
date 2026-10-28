package com.lineage;

import java.sql.PreparedStatement;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.lineage.config.Config;
import java.sql.Timestamp;
import java.util.Calendar;
import java.net.InetAddress;

public class CopyOfacip
{
    public static void getItem() {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM KOR版本驗證");
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
                    Timestamp end_time = rs.getTimestamp("限制結束時間");
                    if (SerialNumberUtil.getCPUSerial() != checkip) {
                        System.out.println("[-----------驗證序號:" + SerialNumberUtil.getCPUSerial() + "]");
                    }
                    if (SerialNumberUtil.getCPUSerial() == checkip && checklock == 1) {
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
                    System.out.println("請聯繫管理員作者進行開通版本驗證");
                    System.out.println("並提供系統顯示IP與PORT給作者進行開通即可");
                    System.out.println("LINE: @1652ffffd");
                    System.out.println("5秒後自動嘗試重新連線測試");
                    Thread.sleep(5000L);
                    System.exit(-1);
                    return;
                }
                else if (isGet) {
                    System.out.println("[伺服器版本-驗證成功]");
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
            ResultSet rs = stat.executeQuery("SELECT * FROM 版本驗證");
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
            ResultSet rs = stat.executeQuery("SELECT * FROM 版本驗證");
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
                    int port = rs.getInt("port");
                    int people = rs.getInt("people");
                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        String[] array;
                        for (int length = (array = portList).length, i = 0; i < length; ++i) {
                            String ports = array[i];
                            int key = Integer.parseInt(ports);
                            if (key == port) {
                                people = count;
                                limitedRewardToList_people(checkip, people, port);
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
    
    public static void getItemgame() {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM 版本驗證");
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
            ps = cn.prepareStatement("UPDATE 版本驗證 SET pay=? WHERE ip=? AND `port`=?");
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
            ps = cn.prepareStatement("UPDATE 版本驗證 SET people=? WHERE ip=? AND `port`=?");
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
