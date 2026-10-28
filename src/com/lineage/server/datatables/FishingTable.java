package com.lineage.server.datatables;

import java.util.Iterator;
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
import java.util.Random;
import com.lineage.server.templates.L1Fishing;
import java.util.Map;
import org.apache.commons.logging.Log;

public class FishingTable
{
    public static final Log _log;
    private static final Map<Integer, L1Fishing> _fishingMap;
    private static Random _random;
    private static FishingTable _instance;
    
    static {
        _log = LogFactory.getLog(FishingTable.class);
        _fishingMap = new HashMap();
        FishingTable._random = new Random();
    }
    
    public static FishingTable get() {
        if (FishingTable._instance == null) {
            FishingTable._instance = new FishingTable();
        }
        return FishingTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `server_fishing`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("itemid");
                int random = rs.getInt("random");
                int fishingpole = rs.getInt("fishingpole");
                int fishingpole2 = rs.getInt("fishingpole2");
                if (ItemTable.get().getTemplate(key) == null) {
                    FishingTable._log.error("漁獲資料錯誤: 沒有這個編號的道具:" + key);
                    delete(key);
                }
                else if (fishingpole > 0 || fishingpole2 > 0) {
                    L1Fishing value = new L1Fishing();
                    value.set_itemid(key);
                    value.set_random(random);
                    value.set_fishingpole(fishingpole);
                    value.set_fishingpole2(fishingpole2);
                    FishingTable._fishingMap.put(key, value);
                }
                else {
                    continue;
                }
            }
        }
        catch (SQLException e) {
            FishingTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        FishingTable._log.info("載入漁獲資料數量: " + FishingTable._fishingMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `server_fishing` WHERE `itemid`=?");
            ps.setInt(1, item_id);
            ps.execute();
        }
        catch (SQLException e) {
            FishingTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public L1Fishing get_fish(int fishingpoleid) {
        try {
            Collection<L1Fishing> fishlist = (Collection)FishingTable._fishingMap.values();
            int comparechance = 1000000;
            for (L1Fishing fish : fishlist) {
                if (fish.get_fishingpole() == fishingpoleid || fish.get_fishingpole2() == fishingpoleid) {
                    int chance = fish.get_random();
                    if (FishingTable._random.nextInt(comparechance) < chance) {
                        return fish;
                    }
                    else {
                        comparechance -= chance;
                    }
                }
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
