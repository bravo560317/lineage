package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.data.EventClass;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Event;
import java.util.Map;
import org.apache.commons.logging.Log;

public class EventTable
{
    private static final Log _log;
    private static EventTable _instance;
    private static final Map<Integer, L1Event> _eventList;
    
    static {
        _log = LogFactory.getLog(EventTable.class);
        _eventList = new HashMap();
    }
    
    public static EventTable get() {
        if (EventTable._instance == null) {
            EventTable._instance = new EventTable();
        }
        return EventTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_event` ORDER BY `id`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String eventname = rs.getString("eventname");
                String eventclass = rs.getString("eventclass");
                boolean eventstart = rs.getBoolean("eventstart");
                String eventother = rs.getString("eventother").replaceAll(" ", "");
                String eventother2 = rs.getString("eventother2").replaceAll(" ", "");
                if (eventstart) {
                    L1Event event = new L1Event();
                    event.set_eventid(id);
                    event.set_eventname(eventname);
                    event.set_eventclass(eventclass);
                    event.set_eventstart(eventstart);
                    event.set_eventother(eventother);
                    event.set_eventother2(eventother2);
                    event.set_next_time(rs.getTimestamp("next_time"));
                    EventClass.get().addList(id, eventclass);
                    EventTable._eventList.put(new Integer(id), event);
                    EventClass.get().startEvent(event);
                }
            }
            EventTable._log.info("載入活動設置資料數量: " + EventTable._eventList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            EventTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public void loadIplimit() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_event` WHERE id=24");
            rs = pstm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String eventname = rs.getString("eventname");
                String eventclass = rs.getString("eventclass");
                boolean eventstart = rs.getBoolean("eventstart");
                String eventother = rs.getString("eventother").replaceAll(" ", "");
                String eventother2 = rs.getString("eventother2").replaceAll(" ", "");
                if (eventstart) {
                    L1Event event = new L1Event();
                    event.set_eventid(id);
                    event.set_eventname(eventname);
                    event.set_eventclass(eventclass);
                    event.set_eventstart(eventstart);
                    event.set_eventother(eventother);
                    event.set_eventother2(eventother2);
                    EventClass.get().addList(id, eventclass);
                    EventTable._eventList.put(new Integer(id), event);
                    EventClass.get().startEvent(event);
                }
            }
            EventTable._log.info("載入網咖IP設置資料數量: " + EventTable._eventList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            EventTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    public L1Event getTemplate(int id) {
        return EventTable._eventList.get(new Integer(id));
    }
    
    public Map<Integer, L1Event> getList() {
        return EventTable._eventList;
    }
    
    public int size() {
        return EventTable._eventList.size();
    }
}
