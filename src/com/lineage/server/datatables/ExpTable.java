package com.lineage.server.datatables;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Exp;
import java.util.Map;
import org.apache.commons.logging.Log;

public final class ExpTable
{
    private static final Log _log;
    public static int MAX_LEVEL;
    public static long MAX_EXP;
    private static ExpTable _instance;
    private static final Map<Integer, L1Exp> _expList;
    
    static {
        _log = LogFactory.getLog(ExpTable.class);
        ExpTable.MAX_LEVEL = 0;
        ExpTable.MAX_EXP = 0L;
        _expList = new HashMap();
    }
    
    public static ExpTable get() {
        if (ExpTable._instance == null) {
            ExpTable._instance = new ExpTable();
        }
        return ExpTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `exp`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int level = rs.getInt("level");
                long exp = rs.getLong("exp");
                double expPenalty = rs.getDouble("expPenalty");
                double deathexp = rs.getDouble("扣除經驗");
                double deathexph = rs.getDouble("恢復經驗");
                if (level > ExpTable.MAX_LEVEL) {
                    ExpTable.MAX_LEVEL = level;
                }
                if (exp > ExpTable.MAX_EXP) {
                    ExpTable.MAX_EXP = exp;
                }
                L1Exp l1exp = new L1Exp();
                l1exp.set_level(level);
                l1exp.set_exp(exp);
                l1exp.set_expPenalty(expPenalty);
                l1exp.setdexp(deathexp);
                l1exp.setdexph(deathexph);
                ExpTable._expList.put(new Integer(level), l1exp);
            }
            ExpTable._log.info("載入經驗質設置資料數量: " + ExpTable._expList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            ExpTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        --ExpTable.MAX_LEVEL;
    }
    
    public static long getExpByLevel(int level) {
        L1Exp l1exp = ExpTable._expList.get(new Integer(level - 1));
        long exp = 0L;
        if (l1exp != null) {
            exp = l1exp.get_exp();
        }
        return exp;
    }
    
    public static long getNeedExpNextLevel(int level) {
        return getExpByLevel(level + 1) - getExpByLevel(level);
    }
    
    public static int getLevelByExp(long exp) {
        int level = 1;
        for (Integer integer : ExpTable._expList.keySet()) {
            if (integer == 0) {
                continue;
            }
            else {
                L1Exp up = ExpTable._expList.get(integer);
                L1Exp dn = ExpTable._expList.get((Integer)(integer - 1));
                long upe = up.get_exp();
                long dne = dn.get_exp();
                if (exp >= dne && exp < upe) {
                    level = integer;
                }
                else {
                    continue;
                }
            }
        }
        return Math.min(level, ExpTable.MAX_LEVEL);
    }
    
    public static int getExpPercentage(int level, long exp) {
        return (int)(100.0 * ((double)(exp - getExpByLevel(level)) / (double)getNeedExpNextLevel(level)));
    }
    
    public static double getPenaltyRate(int level, int levelmet) {
        L1Exp l1exp = ExpTable._expList.get(new Integer(level));
        double expPenalty = 1.0;
        if (l1exp != null) {
            expPenalty = 1.0 / l1exp.get_expPenalty();
        }
        return expPenalty;
    }
    
    public static double calcPercentageExp(int level, int percentage) {
        long needExp = getNeedExpNextLevel(level);
        double rate = (double)percentage / 100.0;
        return (double)needExp * rate;
    }
    
    public static double getPenaltyRate(int level) {
        L1Exp l1exp = ExpTable._expList.get(new Integer(level));
        double expPenalty = 1.0;
        if (l1exp != null) {
            expPenalty = 1.0 / l1exp.get_expPenalty();
        }
        return expPenalty;
    }
    
    public static double getDeathExprates(int level) {
        double rate = 0.0;
        L1Exp l1exp = ExpTable._expList.get(new Integer(level));
        if (l1exp != null) {
            rate = l1exp.getdexp();
        }
        return rate;
    }
    
    public static double getHfExprates(int level) {
        double rate = 0.0;
        L1Exp l1exp = ExpTable._expList.get(new Integer(level));
        if (l1exp != null) {
            rate = l1exp.getdexph();
        }
        return rate;
    }
}
