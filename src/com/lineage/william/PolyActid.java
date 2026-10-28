package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;

public class PolyActid
{
    private static PolyActid _instance;
    private final HashMap<Integer, L1WilliamPolyActid> _gfxIdIndex;
    
    public static PolyActid getInstance() {
        if (PolyActid._instance == null) {
            PolyActid._instance = new PolyActid();
        }
        return PolyActid._instance;
    }
    
    private PolyActid() {
        this._gfxIdIndex = new HashMap();
        this.loadGfxIdOrginal();
    }
    
    private void loadGfxIdOrginal() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_變身actid設定");
            rs = pstm.executeQuery();
            this.fillWeaponSkill(rs);
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillWeaponSkill(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int gfxId = rs.getInt("變身編號");
            int ran = rs.getInt("機率");
            int actid = rs.getInt("actid");
            int dmg = rs.getInt("固定傷害");
            int dmg2 = rs.getInt("隨機傷害");
            L1WilliamPolyActid gfxIdOrginal = new L1WilliamPolyActid(gfxId, ran, actid, dmg, dmg2);
            this._gfxIdIndex.put(gfxId, gfxIdOrginal);
        }
    }
    
    public L1WilliamPolyActid getTemplate(int gfxId) {
        return this._gfxIdIndex.get((Integer)gfxId);
    }
    
    public L1WilliamPolyActid[] getGfxIdList() {
        return (L1WilliamPolyActid[])this._gfxIdIndex.values().toArray(new L1WilliamPolyActid[this._gfxIdIndex.size()]);
    }
}
