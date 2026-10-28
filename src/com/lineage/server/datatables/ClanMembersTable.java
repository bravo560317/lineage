package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import com.lineage.server.IdFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class ClanMembersTable
{
    private static Logger _log;
    private static ClanMembersTable _instance;
    
    static {
        ClanMembersTable._log = Logger.getLogger(ClanMembersTable.class.getName());
    }
    
    public static ClanMembersTable getInstance() {
        if (ClanMembersTable._instance == null) {
            ClanMembersTable._instance = new ClanMembersTable();
        }
        return ClanMembersTable._instance;
    }
    
    public void newMember(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm1 = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        int nextId = IdFactory.get().nextId();
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement("SELECT * FROM clan_members ORDER BY clan_id");
            rs = pstm1.executeQuery();
            pstm2 = con.prepareStatement("INSERT INTO clan_members SET clan_id=?, index_id=?, char_id=?, char_name=?, notes=?");
            pstm2.setInt(1, pc.getClanid());
            pstm2.setInt(2, nextId);
            pstm2.setInt(3, pc.getId());
            pstm2.setString(4, pc.getName());
            pstm2.setString(5, "");
            pstm2.execute();
        }
        catch (SQLException e) {
            ClanMembersTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm1);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
        pc.setClanMemberId(nextId);
    }
    
    public void updateMember(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE clan_members SET clan_id=?, index_id=?, char_id=?, char_name=?");
            pstm.setInt(1, pc.getClanid());
            pstm.setInt(2, IdFactory.get().nextId());
            pstm.setInt(3, pc.getId());
            pstm.setString(4, pc.getName());
            pstm.execute();
        }
        catch (SQLException e) {
            ClanMembersTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void updateMemberNotes(L1PcInstance pc, String notes) {
        Connection con = null;
        PreparedStatement pstm1 = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement("UPDATE clan_members SET notes=? WHERE char_id=?");
            pstm1.setString(1, notes);
            pstm1.setInt(2, pc.getId());
            pstm1.execute();
        }
        catch (SQLException e) {
            ClanMembersTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm1);
            SQLUtil.close(con);
        }
    }
    
    public void deleteMember(int charId) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_members WHERE char_id=?");
            pstm.setInt(1, charId);
            pstm.execute();
        }
        catch (SQLException e) {
            ClanMembersTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void deleteAllMember(int clanId) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM clan_members WHERE clan_id=?");
            pstm.setInt(1, clanId);
            pstm.execute();
        }
        catch (SQLException e) {
            ClanMembersTable._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
