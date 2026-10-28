package com.lineage.server.utils;

import java.sql.ResultSet;
import java.sql.Connection;
import com.lineage.DatabaseFactory;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class L1QueryUtil
{
    private static void setupPrepareStatement(PreparedStatement pstm, Object[] args) throws SQLException {
        for (int i = 0; i < args.length; ++i) {
            pstm.setObject(i + 1, args[i]);
        }
    }
    
    public static <T> T selectFirst(L1QueryUtil.EntityFactory<T> factory, String sql, Object... args) {
        List<T> result = selectAll(factory, sql, args);
        return result.isEmpty() ? null : result.get(0);
    }
    
    public static <T> List<T> selectAll(L1QueryUtil.EntityFactory<T> factory, String sql, Object... args) {
        ArrayList<T> result = new ArrayList();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement(sql);
            setupPrepareStatement(pstm, args);
            rs = pstm.executeQuery();
            while (rs.next()) {
                T entity = factory.fromResultSet(rs);
                if (entity == null) {
                    throw new NullPointerException(factory.getClass().getSimpleName() + " returned null.");
                }
                else {
                    result.add(entity);
                }
            }
        }
        catch (SQLException e) {
            throw new SecurityException(e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return result;
    }
    
    public static boolean execute(Connection con, String sql, Object[] args) {
        PreparedStatement pstm = null;
        try {
            pstm = con.prepareStatement(sql);
            setupPrepareStatement(pstm, args);
            boolean bool = pstm.execute();
            return bool;
        }
        catch (SQLException e) {
            throw new SecurityException(e);
        }
        finally {
            SQLUtil.close(pstm);
        }
    }
    
    public static boolean execute(String sql, Object[] args) {
        Connection con = null;
        try {
            con = DatabaseFactory.get().getConnection();
            boolean bool = execute(con, sql, args);
            return bool;
        }
        catch (SQLException e) {
            throw new SecurityException(e);
        }
        finally {
            SQLUtil.close(con);
        }
    }
    
    public interface EntityFactory<T>
    {
        public abstract T fromResultSet(ResultSet p0) throws SQLException;
    }
}
