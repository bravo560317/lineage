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

public class PcMapNpcmdg
{
    private static Logger _log;
    private static PcMapNpcmdg _instance;
    private final HashMap<Integer, MapNpcDmg> _itemIdIndex;
    
    static {
        PcMapNpcmdg._log = Logger.getLogger(PcMapNpcmdg.class.getName());
    }
    
    public static PcMapNpcmdg getInstance() {
        if (PcMapNpcmdg._instance == null) {
            PcMapNpcmdg._instance = new PcMapNpcmdg();
        }
        return PcMapNpcmdg._instance;
    }
    
    private PcMapNpcmdg() {
        this._itemIdIndex = new HashMap();
        this.loadStrDmg();
    }
    
    private void loadStrDmg() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_地圖怪物強化");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            PcMapNpcmdg._log.log(Level.SEVERE, "error while creating 系統_地圖怪物強化 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            short StrDmg = rs.getShort("地圖");
            double AddDuble = (double)rs.getInt("強化倍率");
            MapNpcDmg StrDmgSkill = new MapNpcDmg(StrDmg, AddDuble);
            this._itemIdIndex.put((int)StrDmg, StrDmgSkill);
        }
    }
    
    public MapNpcDmg getTemplate(int MapNpcDmg) {
        return this._itemIdIndex.get((Integer)MapNpcDmg);
    }
}
