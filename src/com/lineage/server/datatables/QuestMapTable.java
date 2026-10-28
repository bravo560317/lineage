package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class QuestMapTable
{
    private static final Log _log;
    private static QuestMapTable _instance;
    private static final Map<Integer, Integer> _mapList;
    private static final Map<Integer, Integer> _timeList;
    
    static {
        _log = LogFactory.getLog(QuestMapTable.class);
        _mapList = new HashMap();
        _timeList = new HashMap();
    }
    
    public static QuestMapTable get() {
        if (QuestMapTable._instance == null) {
            QuestMapTable._instance = new QuestMapTable();
        }
        return QuestMapTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_quest_maps`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int mapid = rs.getInt("mapid");
                int time = rs.getInt("time");
                int users = rs.getInt("users");
                QuestMapTable._mapList.put(new Integer(mapid), new Integer(users));
                if (time > 0) {
                    QuestMapTable._timeList.put(new Integer(mapid), new Integer(time));
                }
            }
            QuestMapTable._log.info("載入Quest(副本)地圖設置資料數量: " + QuestMapTable._mapList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            QuestMapTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public boolean isQuestMap(int mapid) {
        return QuestMapTable._mapList.get(new Integer(mapid)) != null;
    }
    
    public int getTemplate(int mapid) {
        if (QuestMapTable._mapList.get(new Integer(mapid)) != null) {
            return QuestMapTable._mapList.get(new Integer(mapid));
        }
        else {
            return -1;
        }
    }
    
    public Integer getTime(int mapid) {
        return QuestMapTable._timeList.get(new Integer(mapid));
    }
    
    public Map<Integer, Integer> getList() {
        return QuestMapTable._mapList;
    }
}
