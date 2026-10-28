package com.lineage.server.datatables.sql;

import java.sql.SQLException;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import java.util.ArrayList;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1Alliance;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.ClanAllianceStorage;

public final class ClanAllianceTable implements ClanAllianceStorage
{
    private static final Log _log;
    private final HashMap<Integer, L1Alliance> _allianceList;
    
    static {
        _log = LogFactory.getLog(ClanAllianceTable.class);
    }
    
    public ClanAllianceTable() {
        this._allianceList = new HashMap();
    }
    
    @Override
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM character_alliance ORDER BY order_id");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int alliance_id1 = rs.getInt("alliance_id1");
                int alliance_id2 = rs.getInt("alliance_id2");
                int alliance_id3 = rs.getInt("alliance_id3");
                int alliance_id4 = rs.getInt("alliance_id4");
                ArrayList<L1Clan> totalList = new ArrayList(4);
                for (L1Clan clan : WorldClan.get().getAllClans()) {
                    if (clan.getClanId() == alliance_id1 || clan.getClanId() == alliance_id2 || clan.getClanId() == alliance_id3 || clan.getClanId() == alliance_id4) {
                        totalList.add(clan);
                    }
                }
                if (totalList.size() < 2) {
                    this.deleteAlliance(order_id);
                }
                else {
                    L1Alliance l1alliance = new L1Alliance(order_id, (L1Clan[])totalList.toArray(new L1Clan[totalList.size()]));
                    this._allianceList.put(order_id, l1alliance);
                }
            }
        }
        catch (Exception e) {
            ClanAllianceTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ClanAllianceTable._log.info("讀取->師徒系統資料數量: " + this._allianceList.size() + "(" + timer.get() + "ms)");
    }
    
    @Override
    public final void insertAlliance(L1Alliance alliance) {
        if (alliance.getTotalList().size() < 2) {
            return;
        }
        else {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("INSERT INTO character_alliance SET order_id=?, alliance_id1=?, alliance_id2=?");
                pstm.setInt(1, alliance.getOrderId());
                pstm.setInt(2, (alliance.getTotalList().get(0)).getClanId());
                pstm.setInt(3, (alliance.getTotalList().get(1)).getClanId());
                pstm.execute();
                this._allianceList.put(alliance.getOrderId(), alliance);
            }
            catch (SQLException e) {
                ClanAllianceTable._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
            return;
        }
    }
    
    @Override
    public final void updateAlliance(int order_id, ArrayList<L1Clan> totalList) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE character_alliance SET alliance_id1=?, alliance_id2=?, alliance_id3=?, alliance_id4=? WHERE order_id=?");
            pstm.setInt(1, (totalList.size() > 0) ? (totalList.get(0)).getClanId() : 0);
            pstm.setInt(2, (totalList.size() > 1) ? (totalList.get(1)).getClanId() : 0);
            pstm.setInt(3, (totalList.size() > 2) ? (totalList.get(2)).getClanId() : 0);
            pstm.setInt(4, (totalList.size() > 3) ? (totalList.get(3)).getClanId() : 0);
            pstm.setInt(5, order_id);
            pstm.execute();
        }
        catch (SQLException e) {
            ClanAllianceTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public final void deleteAlliance(int order_id) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM character_alliance WHERE order_id=?");
            pstm.setInt(1, order_id);
            pstm.execute();
            this._allianceList.remove((Integer)order_id);
        }
        catch (SQLException e) {
            ClanAllianceTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public final L1Alliance getAlliance(int clan_id) {
        for (L1Alliance alliance : this._allianceList.values()) {
            for (L1Clan clan : alliance.getTotalList()) {
                if (clan.getClanId() == clan_id) {
                    return alliance;
                }
            }
        }
        return null;
    }
}
