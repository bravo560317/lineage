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

public class NpcClanContribution
{
    private static final Log _log;
    private static NpcClanContribution _instance;
    private static Random _random;
    private static final Map<Integer, Integer> _scoremaxList;
    private static final Map<Integer, Integer> _scoreminList;
    private static final ArrayList<Integer> _scorenpcList;
    
    static {
        _log = LogFactory.getLog(NpcClanContribution.class);
        NpcClanContribution._random = new Random();
        _scoremaxList = new TreeMap();
        _scoreminList = new TreeMap();
        _scorenpcList = new ArrayList();
    }
    
    public static NpcClanContribution get() {
        if (NpcClanContribution._instance == null) {
            NpcClanContribution._instance = new NpcClanContribution();
        }
        return NpcClanContribution._instance;
    }
    
    public void reload() {
        NpcClanContribution._scoremaxList.clear();
        NpcClanContribution._scoreminList.clear();
        NpcClanContribution._scorenpcList.clear();
        this.load();
    }
    
    public void load() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `w_血盟能量怪物`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int npcId = rs.getInt("npcid");
                int score_min = rs.getInt("min_血盟能量");
                int score_max = rs.getInt("max_血盟能量");
                NpcClanContribution._scoremaxList.put(npcId, score_max);
                NpcClanContribution._scoreminList.put(npcId, score_min);
                NpcClanContribution._scorenpcList.add(npcId);
            }
        }
        catch (SQLException e) {
            NpcClanContribution._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public ArrayList<Integer> get_scoreList() {
        return NpcClanContribution._scorenpcList;
    }
    
    public int get_score(int npcid) {
        if (NpcClanContribution._scoreminList.containsKey((Integer)npcid)) {
            int max = NpcClanContribution._scoremaxList.get((Integer)npcid);
            int min = NpcClanContribution._scoreminList.get((Integer)npcid);
            int sum = min + NpcClanContribution._random.nextInt(max);
            return sum;
        }
        else {
            return 0;
        }
    }
}
