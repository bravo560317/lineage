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

public class PcStrDmg
{
    private static Logger _log;
    private static PcStrDmg _instance;
    private final HashMap<Integer, StrDmg> _itemIdIndex;
    
    static {
        PcStrDmg._log = Logger.getLogger(PcStrDmg.class.getName());
    }
    
    public static PcStrDmg getInstance() {
        if (PcStrDmg._instance == null) {
            PcStrDmg._instance = new PcStrDmg();
        }
        return PcStrDmg._instance;
    }
    
    private PcStrDmg() {
        this._itemIdIndex = new HashMap();
        this.loadStrDmg();
    }
    
    private void loadStrDmg() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_能力力量設置");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            PcStrDmg._log.log(Level.SEVERE, "error while creating 系統_能力力量設置 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int StrDmg = rs.getInt("Str");
            int AddDmg = rs.getInt("AddDmg");
            int AddHit = rs.getInt("AddHit");
            StrDmg StrDmgSkill = new StrDmg(StrDmg, AddDmg, AddHit);
            this._itemIdIndex.put(StrDmg, StrDmgSkill);
        }
    }
    
    public StrDmg getTemplate(int StrDmg) {
        return this._itemIdIndex.get((Integer)StrDmg);
    }
}
