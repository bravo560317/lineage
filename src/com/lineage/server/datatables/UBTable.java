package com.lineage.server.datatables;

import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.L1UltimateBattle;
import java.util.Map;
import org.apache.commons.logging.Log;

public class UBTable
{
    private static final Log _log;
    private static UBTable _instance;
    private static final Map<Integer, L1UltimateBattle> _ub;
    
    static {
        _log = LogFactory.getLog(UBTable.class);
        _ub = new HashMap();
    }
    
    public static UBTable getInstance() {
        if (UBTable._instance == null) {
            UBTable._instance = new UBTable();
        }
        return UBTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `ub_settings`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1UltimateBattle ub = new L1UltimateBattle();
                ub.setUbId(rs.getInt("ub_id"));
                ub.setName(rs.getString("ub_name"));
                ub.setMapId(rs.getShort("ub_mapid"));
                ub.setLocX1(rs.getInt("ub_area_x1"));
                ub.setLocY1(rs.getInt("ub_area_y1"));
                ub.setLocX2(rs.getInt("ub_area_x2"));
                ub.setLocY2(rs.getInt("ub_area_y2"));
                ub.setMinLevel(rs.getInt("min_lvl"));
                ub.setMaxLevel(rs.getInt("max_lvl"));
                ub.setMaxPlayer(rs.getInt("max_player"));
                ub.setEnterRoyal(rs.getBoolean("enter_royal"));
                ub.setEnterKnight(rs.getBoolean("enter_knight"));
                ub.setEnterMage(rs.getBoolean("enter_mage"));
                ub.setEnterElf(rs.getBoolean("enter_elf"));
                ub.setEnterDarkelf(rs.getBoolean("enter_darkelf"));
                ub.setEnterDragonKnight(rs.getBoolean("enter_dragonknight"));
                ub.setEnterIllusionist(rs.getBoolean("enter_illusionist"));
                ub.setEnterMale(rs.getBoolean("enter_male"));
                ub.setEnterFemale(rs.getBoolean("enter_female"));
                ub.setUsePot(rs.getBoolean("use_pot"));
                ub.setHpr(rs.getInt("hpr_bonus"));
                ub.setMpr(rs.getInt("mpr_bonus"));
                ub.resetLoc();
                UBTable._ub.put(ub.getUbId(), ub);
            }
        }
        catch (SQLException e) {
            UBTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
        }
        try {
            pstm = con.prepareStatement("SELECT * FROM `ub_managers`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1UltimateBattle ub = this.getUb(rs.getInt("ub_id"));
                if (ub != null) {
                    ub.addManager(rs.getInt("ub_manager_npc_id"));
                }
            }
        }
        catch (SQLException e) {
            UBTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
        }
        try {
            pstm = con.prepareStatement("SELECT * FROM `ub_times`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                L1UltimateBattle ub = this.getUb(rs.getInt("ub_id"));
                if (ub != null) {
                    ub.addUbTime(rs.getInt("ub_time"));
                }
            }
        }
        catch (SQLException e) {
            UBTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs, pstm, con);
        }
        UBTable._log.info("載入無線大賽設置資料數量: " + UBTable._ub.size() + "(" + timer.get() + "ms)");
    }
    
    public L1UltimateBattle getUb(int ubId) {
        return UBTable._ub.get((Integer)ubId);
    }
    
    public Collection<L1UltimateBattle> getAllUb() {
        return (Collection)Collections.unmodifiableCollection(UBTable._ub.values());
    }
    
    public L1UltimateBattle getUbForNpcId(int npcId) {
        for (L1UltimateBattle ub : UBTable._ub.values()) {
            if (ub.containsManager(npcId)) {
                return ub;
            }
        }
        return null;
    }
    
    public int getMaxPattern(int ubId) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT MAX(pattern) FROM `spawnlist_ub` WHERE `ub_id`=?");
            pstm.setInt(1, ubId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            UBTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return n;
    }
    
    public void writeUbScore(int ubId, L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm1 = null;
        PreparedStatement pstm2 = null;
        ResultSet rs = null;
        int score = 0;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement("SELECT * FROM ub_rank WHERE ub_id=? AND char_name=?");
            pstm1.setInt(1, ubId);
            pstm1.setString(2, pc.getName());
            rs = pstm1.executeQuery();
            if (rs.next()) {
                score = rs.getInt("score");
                pstm2 = con.prepareStatement("UPDATE ub_rank SET score=? WHERE ub_id=? AND char_name=?");
                pstm2.setInt(1, score + pc.getUbScore());
                pstm2.setInt(2, ubId);
                pstm2.setString(3, pc.getName());
                pstm2.execute();
            }
            else {
                pstm2 = con.prepareStatement("INSERT INTO ub_rank SET ub_id=?, char_name=?, score=?");
                pstm2.setInt(1, ubId);
                pstm2.setString(2, pc.getName());
                pstm2.setInt(3, pc.getUbScore());
                pstm2.execute();
            }
            pc.setUbScore(0);
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm1);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
    }
}
