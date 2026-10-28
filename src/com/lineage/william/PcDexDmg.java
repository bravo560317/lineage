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

public class PcDexDmg
{
    private static Logger _log;
    private static PcDexDmg _instance;
    private final HashMap<Integer, DexDmg> _itemIdIndex;
    
    static {
        PcDexDmg._log = Logger.getLogger(PcDexDmg.class.getName());
    }
    
    public static PcDexDmg getInstance() {
        if (PcDexDmg._instance == null) {
            PcDexDmg._instance = new PcDexDmg();
        }
        return PcDexDmg._instance;
    }
    
    private PcDexDmg() {
        this._itemIdIndex = new HashMap();
        this.loadDexDmg();
    }
    
    private void loadDexDmg() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_能力敏捷設置");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            PcDexDmg._log.log(Level.SEVERE, "error while creating 系統_能力敏捷設置 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int DexDmg = rs.getInt("Dex");
            int AddDmg = rs.getInt("AddDmg");
            int AddHit = rs.getInt("AddHit");
            DexDmg DexDmgSkill = new DexDmg(DexDmg, AddDmg, AddHit);
            this._itemIdIndex.put(DexDmg, DexDmgSkill);
        }
    }
    
    public DexDmg getTemplate(int DexDmg) {
        return this._itemIdIndex.get((Integer)DexDmg);
    }
}
