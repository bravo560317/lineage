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

public class PcMrDmg
{
    private static Logger _log;
    private static PcMrDmg _instance;
    private final HashMap<Integer, PcMr> _itemIdIndex;
    
    static {
        PcMrDmg._log = Logger.getLogger(PcMrDmg.class.getName());
    }
    
    public static PcMrDmg getInstance() {
        if (PcMrDmg._instance == null) {
            PcMrDmg._instance = new PcMrDmg();
        }
        return PcMrDmg._instance;
    }
    
    private PcMrDmg() {
        this._itemIdIndex = new HashMap();
        this.loadPcMr();
    }
    
    private void loadPcMr() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_能力抗魔設置");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            PcMrDmg._log.log(Level.SEVERE, "error while creating 系統_能力抗魔設置 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int PcMr = rs.getInt("抗魔");
            int sp = rs.getInt("減負面機率");
            int mgs = rs.getInt("減魔法傷害");
            PcMr PcMrSkill = new PcMr(PcMr, sp, mgs);
            this._itemIdIndex.put(PcMr, PcMrSkill);
        }
    }
    
    public PcMr getTemplate(int PcMr) {
        return this._itemIdIndex.get((Integer)PcMr);
    }
}
