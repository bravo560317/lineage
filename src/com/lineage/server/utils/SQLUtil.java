package com.lineage.server.utils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public class SQLUtil
{
    public static SQLException close(Connection cn) {
        try {
            if (cn != null) {
                cn.close();
            }
        }
        catch (SQLException e) {
            return e;
        }
        return null;
    }
    
    public static SQLException close(Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        }
        catch (SQLException e) {
            return e;
        }
        return null;
    }
    
    public static SQLException close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (SQLException e) {
            return e;
        }
        return null;
    }
    
    public static void close(ResultSet rs, Statement ps, Connection cn) {
        close(rs);
        close(ps);
        close(cn);
    }
}
