package com.lineage.server.datatables;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.collections.Lists;
import com.lineage.server.model.L1UbSupplie;
import java.util.List;
import java.util.logging.Logger;

public class UbSupplies
{
    private static UbSupplies _instance;
    private static Logger _log;
    private final List<L1UbSupplie> _ubSupplies;
    
    static {
        UbSupplies._log = Logger.getLogger(UbSupplies.class.getName());
    }
    
    private UbSupplies() {
        this._ubSupplies = (List)Lists.newArrayList();
        this.load();
    }
    
    public static UbSupplies getInstance() {
        if (UbSupplies._instance == null) {
            UbSupplies._instance = new UbSupplies();
        }
        return UbSupplies._instance;
    }
    
    public void load() {
        L1UbSupplie us = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select * from ub_supplies ORDER BY ub_id,ub_round,ub_item_id");
            rs = pstm.executeQuery();
            while (rs.next()) {
                us = new L1UbSupplie();
                us.setUbId(rs.getInt("ub_id"));
                us.setUbName(rs.getString("ub_name"));
                us.setUbRound(rs.getInt("ub_round"));
                us.setUbItemId(rs.getInt("ub_item_id"));
                us.setUbItemStackCont(rs.getInt("ub_item_stackcont"));
                us.setUbItemCont(rs.getInt("ub_item_cont"));
                us.setUbItemBless(rs.getInt("ub_item_bless"));
                this._ubSupplies.add(us);
            }
        }
        catch (SQLException e) {
            UbSupplies._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public List<L1UbSupplie> getUbSupplies(int id) {
        List<L1UbSupplie> temp = (List)Lists.newArrayList();
        for (L1UbSupplie t : this._ubSupplies) {
            if (t.getUbId() == id) {
                temp.add(t);
            }
        }
        return temp;
    }
}
