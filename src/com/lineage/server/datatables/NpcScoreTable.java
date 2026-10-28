package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.TreeMap;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;

public class NpcScoreTable
{
    private static final Log _log;
    private static NpcScoreTable _instance;
    private static Random _random;
    private static final Map<Integer, Integer> _scoremaxList;
    private static final Map<Integer, Integer> _scoreminList;
    private static final ArrayList<Integer> _scorenpcList;
    
    static {
        _log = LogFactory.getLog(NpcScoreTable.class);
        NpcScoreTable._random = new Random();
        _scoremaxList = new TreeMap();
        _scoreminList = new TreeMap();
        _scorenpcList = new ArrayList();
    }
    
    public static NpcScoreTable get() {
        if (NpcScoreTable._instance == null) {
            NpcScoreTable._instance = new NpcScoreTable();
        }
        return NpcScoreTable._instance;
    }
    
    public void reload() {
        NpcScoreTable._scoremaxList.clear();
        NpcScoreTable._scoreminList.clear();
        NpcScoreTable._scorenpcList.clear();
        this.load();
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `系統_血盟_陣營怪物`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int npcId = rs.getInt("npcid");
                int score_min = rs.getInt("min_score");
                int score_max = rs.getInt("max_score");
                NpcScoreTable._scoremaxList.put(npcId, score_max);
                NpcScoreTable._scoreminList.put(npcId, score_min);
                NpcScoreTable._scorenpcList.add(npcId);
            }
        }
        catch (SQLException e) {
            NpcScoreTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        NpcScoreTable._log.info("載入NPC積分設置資料數量: " + NpcScoreTable._scoremaxList.size() + "(" + timer.get() + "ms)");
    }
    
    public ArrayList<Integer> get_scoreList() {
        return NpcScoreTable._scorenpcList;
    }
    
    public int get_score(int npcid) {
        if (NpcScoreTable._scoreminList.containsKey((Integer)npcid)) {
            int max = NpcScoreTable._scoremaxList.get((Integer)npcid);
            int min = NpcScoreTable._scoreminList.get((Integer)npcid);
            int sum = min + NpcScoreTable._random.nextInt(max);
            return sum;
        }
        else {
            return 0;
        }
    }
}
