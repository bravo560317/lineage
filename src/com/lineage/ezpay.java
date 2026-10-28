package com.lineage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class ezpay
{
    public static void getItem(String ip) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM 版本驗證");
            if (rs != null) {
                while (rs.next()) {
                    int money = rs.getInt("ip");
                    int checklock = rs.getInt("安全鎖");
                    rs.getInt("端口");
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (Exception ex) {}
    }
}
