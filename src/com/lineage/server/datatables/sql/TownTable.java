package com.lineage.server.datatables.sql;

import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Town;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.TownStorage;

public class TownTable implements TownStorage
{
    private static final Log _log;
    private static final Map<Integer, L1Town> _towns;
    private static TownTable _instance;
    
    static {
        _log = LogFactory.getLog(TownTable.class);
        _towns = new HashMap();
    }
    
    public static TownTable getInstance() {
        if (TownTable._instance == null) {
            TownTable._instance = new TownTable();
        }
        return TownTable._instance;
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        TownTable._towns.clear();
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_town`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1Town town = new L1Town();
                int townid = rs.getInt("town_id");
                town.set_townid(townid);
                town.set_name(rs.getString("name"));
                town.set_leader_id(rs.getInt("leader_id"));
                town.set_leader_name(rs.getString("leader_name"));
                town.set_tax_rate(rs.getInt("tax_rate"));
                town.set_tax_rate_reserved(rs.getInt("tax_rate_reserved"));
                town.set_sales_money(rs.getInt("sales_money"));
                town.set_sales_money_yesterday(rs.getInt("sales_money_yesterday"));
                town.set_town_tax(rs.getInt("town_tax"));
                town.set_town_fix_tax(rs.getInt("town_fix_tax"));
                TownTable._towns.put(new Integer(townid), town);
            }
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        TownTable._log.info("載入村莊資料數量: " + TownTable._towns.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public L1Town[] getTownTableList() {
        return (L1Town[])TownTable._towns.values().toArray(new L1Town[TownTable._towns.size()]);
    }
    
    @Override
    public L1Town getTownTable(int id) {
        return TownTable._towns.get((Integer)id);
    }
    
    @Override
    public boolean isLeader(L1PcInstance pc, int town_id) {
        L1Town town = this.getTownTable(town_id);
        return town.get_leader_id() == pc.getId();
    }
    
    @Override
    public synchronized void addSalesMoney(int town_id, int salesMoney) {
        Connection con = null;
        PreparedStatement pstm = null;
        L1Town town = this.getTownTable(town_id);
        int townTaxRate = town.get_tax_rate();
        int townTax = salesMoney / 100 * townTaxRate;
        int townFixTax = salesMoney / 100 * 2;
        if (townTax <= 0 && townTaxRate > 0) {
            townTax = 1;
        }
        if (townFixTax <= 0 && townTaxRate > 0) {
            townFixTax = 1;
        }
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `server_town` SET `sales_money`=`sales_money`+?,`town_tax`=`town_tax`+?,`town_fix_tax`=`town_fix_tax`+? WHERE `town_id`=?");
            pstm.setInt(1, salesMoney);
            pstm.setInt(2, townTax);
            pstm.setInt(3, townFixTax);
            pstm.setInt(4, town_id);
            pstm.execute();
            town.set_sales_money(town.get_sales_money() + salesMoney);
            town.set_town_tax(town.get_town_tax() + townTax);
            town.set_town_fix_tax(town.get_town_fix_tax() + townFixTax);
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateTaxRate() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `server_town` SET `tax_rate`=`tax_rate_reserved`");
            pstm.execute();
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void updateSalesMoneyYesterday() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `server_town` SET `sales_money_yesterday`=`sales_money`,`sales_money`=0");
            pstm.execute();
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public String totalContribution(int townId) {
        Connection con = null;
        PreparedStatement pstm1 = null;
        ResultSet rs1 = null;
        PreparedStatement pstm2 = null;
        ResultSet rs2 = null;
        PreparedStatement pstm3 = null;
        ResultSet rs3 = null;
        PreparedStatement pstm4 = null;
        PreparedStatement pstm5 = null;
        int leaderId = 0;
        String leaderName = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement("SELECT `objid`, `char_name` FROM `characters` WHERE `HomeTownID`=? ORDER BY `Contribution` DESC");
            pstm1.setInt(1, townId);
            rs1 = pstm1.executeQuery();
            if (rs1.next()) {
                leaderId = rs1.getInt("objid");
                leaderName = rs1.getString("char_name");
            }
            double totalContribution = 0.0;
            pstm2 = con.prepareStatement("SELECT SUM(`Contribution`) AS TotalContribution FROM `characters` WHERE `HomeTownID`=?");
            pstm2.setInt(1, townId);
            rs2 = pstm2.executeQuery();
            if (rs2.next()) {
                totalContribution = (double)rs2.getInt("TotalContribution");
            }
            double townFixTax = 0.0;
            pstm3 = con.prepareStatement("SELECT `town_fix_tax` FROM `server_town` WHERE `town_id`=?");
            pstm3.setInt(1, townId);
            rs3 = pstm3.executeQuery();
            if (rs3.next()) {
                townFixTax = (double)rs3.getInt("town_fix_tax");
            }
            double contributionUnit = 0.0;
            if (totalContribution != 0.0) {
                contributionUnit = Math.floor(townFixTax / totalContribution * 100.0) / 100.0;
            }
            pstm4 = con.prepareStatement("UPDATE `characters` SET `Contribution`=0,`Pay`=`Contribution` * ? WHERE `HomeTownID`=?");
            pstm4.setDouble(1, contributionUnit);
            pstm4.setInt(2, townId);
            pstm4.execute();
            pstm5 = con.prepareStatement("UPDATE `server_town` SET `leader_id`=?,`leader_name`=?,`tax_rate`= 0,`tax_rate_reserved`=0,`sales_money`=0,`sales_money_yesterday`=`sales_money`,`town_tax`=0,`town_fix_tax`=0 WHERE `town_id`=?");
            pstm5.setInt(1, leaderId);
            pstm5.setString(2, leaderName);
            pstm5.setInt(3, townId);
            pstm5.execute();
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs1);
            SQLUtil.close(rs2);
            SQLUtil.close(rs3);
            SQLUtil.close(pstm1);
            SQLUtil.close(pstm2);
            SQLUtil.close(pstm3);
            SQLUtil.close(pstm4);
            SQLUtil.close(pstm5);
            SQLUtil.close(con);
        }
        return leaderName;
    }
    
    @Override
    public void clearHomeTownID() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `characters` SET `HomeTownID`=0 WHERE `HomeTownID`=-1");
            pstm.execute();
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public int getPay(int objid) {
        Connection con = null;
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        ResultSet rs1 = null;
        int pay = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement("SELECT `Pay` FROM `characters` WHERE `objid`=? FOR UPDATE");
            pstm1.setInt(1, objid);
            rs1 = pstm1.executeQuery();
            if (rs1.next()) {
                pay = rs1.getInt("Pay");
            }
            pstm2 = con.prepareStatement("UPDATE `characters` SET `Pay`=0 WHERE `objid`=?");
            pstm2.setInt(1, objid);
            pstm2.execute();
        }
        catch (SQLException e) {
            TownTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs1);
            SQLUtil.close(pstm1);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
        return pay;
    }
}
