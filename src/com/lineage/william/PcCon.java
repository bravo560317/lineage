package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import java.util.logging.Logger;

public class PcCon
{
    private static Logger _log;
    private static PcCon _instance;
    private final HashMap<Integer, ConDmg> _itemIdIndex;
    
    static {
        PcCon._log = Logger.getLogger(PcCon.class.getName());
    }
    
    public static PcCon getInstance() {
        if (PcCon._instance == null) {
            PcCon._instance = new PcCon();
        }
        return PcCon._instance;
    }
    
    private PcCon() {
        this._itemIdIndex = new HashMap();
        this.loadConDmg();
    }
    
    private void loadConDmg() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_能力體質設置");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            PcCon._log.log(Level.SEVERE, "error while creating 系統_能立體植設置 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int ConDmg = rs.getInt("Con");
            int AddDmgR = rs.getInt("減傷");
            int AddHP = rs.getInt("永久加血");
            ConDmg ConDmgSkill = new ConDmg(ConDmg, AddDmgR, AddHP);
            this._itemIdIndex.put(ConDmg, ConDmgSkill);
        }
    }
    
    public ConDmg getTemplate(int ConDmg) {
        return this._itemIdIndex.get((Integer)ConDmg);
    }
}
