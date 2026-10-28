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

public class PcIntSp
{
    private static Logger _log;
    private static PcIntSp _instance;
    private final HashMap<Integer, IntSp> _itemIdIndex;
    
    static {
        PcIntSp._log = Logger.getLogger(PcIntSp.class.getName());
    }
    
    public static PcIntSp getInstance() {
        if (PcIntSp._instance == null) {
            PcIntSp._instance = new PcIntSp();
        }
        return PcIntSp._instance;
    }
    
    private PcIntSp() {
        this._itemIdIndex = new HashMap();
        this.loadIntSp();
    }
    
    private void loadIntSp() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_能力智力設置");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            PcIntSp._log.log(Level.SEVERE, "error while creating 系統_能力智力設置 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int IntSp = rs.getInt("int");
            int sp = rs.getInt("Sp");
            int mgs = rs.getInt("魔法穿透率");
            int mgs2 = rs.getInt("忽略魔防");
            IntSp IntSpSkill = new IntSp(IntSp, sp, mgs, mgs2);
            this._itemIdIndex.put(IntSp, IntSpSkill);
        }
    }
    
    public IntSp getTemplate(int IntSp) {
        return this._itemIdIndex.get((Integer)IntSp);
    }
}
