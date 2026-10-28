package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.TreeMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;

public class NpcPrestigeTable
{
    private static final Log _log;
    private static NpcPrestigeTable _instance;
    private static Random _random;
    private static final Map<Integer, Integer> _scoremaxList;
    private static final Map<Integer, Integer> _scoreminList;
    private static final ArrayList<Integer> _scorenpcList;
    
    static {
        _log = LogFactory.getLog(NpcPrestigeTable.class);
        NpcPrestigeTable._random = new Random();
        _scoremaxList = new TreeMap();
        _scoreminList = new TreeMap();
        _scorenpcList = new ArrayList();
    }
    
    public static NpcPrestigeTable get() {
        if (NpcPrestigeTable._instance == null) {
            NpcPrestigeTable._instance = new NpcPrestigeTable();
        }
        return NpcPrestigeTable._instance;
    }
    
    public void reload() {
        NpcPrestigeTable._scoremaxList.clear();
        NpcPrestigeTable._scoreminList.clear();
        NpcPrestigeTable._scorenpcList.clear();
        this.load();
    }
    
    public void load() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `w_威望怪物`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int npcId = rs.getInt("npcid");
                int score_min = rs.getInt("min_威望");
                int score_max = rs.getInt("max_威望");
                NpcPrestigeTable._scoremaxList.put(npcId, score_max);
                NpcPrestigeTable._scoreminList.put(npcId, score_min);
                NpcPrestigeTable._scorenpcList.add(npcId);
            }
        }
        catch (SQLException e) {
            NpcPrestigeTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public ArrayList<Integer> get_scoreList() {
        return NpcPrestigeTable._scorenpcList;
    }
    
    public int get_score(int npcid) {
        if (NpcPrestigeTable._scoreminList.containsKey((Integer)npcid)) {
            int max = NpcPrestigeTable._scoremaxList.get((Integer)npcid);
            int min = NpcPrestigeTable._scoreminList.get((Integer)npcid);
            int sum = min + NpcPrestigeTable._random.nextInt(max);
            return sum;
        }
        else {
            return 0;
        }
    }
}
