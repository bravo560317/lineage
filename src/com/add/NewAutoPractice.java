package com.add;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.text.SimpleDateFormat;
import com.lineage.server.model.L1Character;
import java.sql.SQLException;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class NewAutoPractice
{
    private static final Log _log;
    private static NewAutoPractice _instance;
    
    static {
        _log = LogFactory.getLog(NewAutoPractice.class);
    }
    
    public static NewAutoPractice get() {
        if (NewAutoPractice._instance == null) {
            NewAutoPractice._instance = new NewAutoPractice();
        }
        return NewAutoPractice._instance;
    }
    
    public void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `內掛仇人清單`");
            rs = pm.executeQuery();
            while (rs.next()) {}
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public void load3() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `character_bad_buddy`");
            rs = pm.executeQuery();
            while (rs.next()) {}
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public void load2() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `內掛被殺清單`");
            rs = pm.executeQuery();
            while (rs.next()) {}
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public void addEnemy(L1PcInstance pc) {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `內掛仇人清單`");
            rs = pm.executeQuery();
            while (rs.next()) {
                if (pc.getId() == rs.getInt("CharObjId")) {
                    pc.setInEnemyList(rs.getString("EnemyObjId"));
                }
            }
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public void AddEnemyList(L1PcInstance pc, String name) {
        int id = this.LoadMaxId() + 1;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `內掛仇人清單` SET `Id`=?,`CharObjId`=?,`EnemyObjId`=?");
            pstm.setInt(1, id);
            pstm.setInt(2, pc.getId());
            pstm.setString(3, name);
            pstm.execute();
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        pc.setInEnemyList(name);
    }
    
    public int LoadMaxId() {
        int id = 1;
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT MAX(Id) FROM 內掛仇人清單");
            rs = pm.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        return id;
    }
    
    public void DeleteEnemyList(L1PcInstance pc, String name) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `內掛仇人清單` WHERE `CharObjId`=? AND `EnemyObjId`=?");
            ps.setInt(1, pc.getId());
            ps.setString(2, name);
            ps.execute();
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        pc.removeInEnemyList(name);
    }
    
    public void AddAutoList(L1PcInstance pc, L1Character srpc) {
        int id = this.LoadMaxId2() + 1;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `內掛被殺清單` SET `Id`=?,`CharObjId`=?,`EnemyName`=?,`Time`=?");
            pstm.setInt(1, id);
            pstm.setInt(2, pc.getId());
            pstm.setString(3, srpc.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String times = sdf.format((Long)System.currentTimeMillis());
            pstm.setString(4, times);
            pstm.execute();
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public int LoadMaxId2() {
        int id = 1;
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            String sql = "SELECT MAX(Id) FROM 內掛被殺清單";
            pm = co.prepareStatement(sql);
            rs = pm.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        return id;
    }
    
    public void SearchAutoLog(L1PcInstance pc) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM 內掛被殺清單");
            rs = pstm.executeQuery();
            if (rs != null) {
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                while (rs.next()) {
                    int objid = rs.getInt("CharObjId");
                    String name = rs.getString("EnemyName");
                    String time = rs.getString("Time");
                    if (objid == pc.getId()) {
                        stringBuilder.append(name + " [" + time + "],");
                        ++i;
                    }
                }
                if (i == 0) {
                    stringBuilder.append("目前沒有任何被殺相關紀錄");
                }
                String[] clientStrAry = stringBuilder.toString().split(",");
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "x_autolist", clientStrAry));
            }
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
            SQLUtil.close(rs);
        }
    }
    
    public void ClearAutoLog(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `內掛被殺清單` WHERE `CharObjId`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public void addBadEnemy(L1PcInstance pc) {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `character_bad_buddy`");
            rs = pm.executeQuery();
            while (rs.next()) {
                if (pc.getId() == rs.getInt("CharObjId")) {
                    pc.setBadInEnemyList(rs.getString("EnemyObjId"));
                }
            }
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
    }
    
    public void AddBadEnemyList(L1PcInstance pc, String name) {
        int id = this.LoadBadMaxId() + 1;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `character_bad_buddy` SET `Id`=?,`CharObjId`=?,`EnemyObjId`=?");
            pstm.setInt(1, id);
            pstm.setInt(2, pc.getId());
            pstm.setString(3, name);
            pstm.execute();
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        pc.setBadInEnemyList(name);
    }
    
    public int LoadBadMaxId() {
        int id = 1;
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT MAX(Id) FROM character_bad_buddy");
            rs = pm.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch (Exception e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        return id;
    }
    
    public void DeleteBadEnemyList(L1PcInstance pc, String name) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_bad_buddy` WHERE `CharObjId`=? AND `EnemyObjId`=?");
            ps.setInt(1, pc.getId());
            ps.setString(2, name);
            ps.execute();
        }
        catch (SQLException e) {
            NewAutoPractice._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        pc.removeBadInEnemyList(name);
    }
}
