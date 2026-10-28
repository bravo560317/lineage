package com.lineage.data;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.sql.Timestamp;
import com.lineage.server.templates.L1Event;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.data.executor.EventExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;

public class EventClass
{
    private static final Log _log;
    private static final Map<Integer, EventExecutor> _classList;
    private static EventClass _instance;
    
    static {
        _log = LogFactory.getLog(EventClass.class);
        _classList = new HashMap();
    }
    
    public static EventClass get() {
        if (EventClass._instance == null) {
            EventClass._instance = new EventClass();
        }
        return EventClass._instance;
    }
    
    public void addList(int eventid, String className) {
        if (className.equals("0")) {
            return;
        }
        else {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("com.lineage.data.event.");
                stringBuilder.append(className);
                Class cls = Class.forName(stringBuilder.toString());
                EventExecutor exe = (EventExecutor)cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
                EventClass._classList.put(new Integer(eventid), exe);
            }
            catch (ClassNotFoundException e) {
                String error = "發生[Event(活動設置)檔案]錯誤, 檢查檔案是否存在:" + className + " EventId:" + eventid;
                EventClass._log.error(error);
                DataError.isError(EventClass._log, error, e);
            }
            catch (IllegalArgumentException e2) {
                EventClass._log.error(e2.getLocalizedMessage(), e2);
            }
            catch (IllegalAccessException e3) {
                EventClass._log.error(e3.getLocalizedMessage(), e3);
            }
            catch (InvocationTargetException e4) {
                EventClass._log.error(e4.getLocalizedMessage(), e4);
            }
            catch (SecurityException e5) {
                EventClass._log.error(e5.getLocalizedMessage(), e5);
            }
            catch (NoSuchMethodException e6) {
                EventClass._log.error(e6.getLocalizedMessage(), e6);
            }
            return;
        }
    }
    
    public void startEvent(L1Event event) {
        try {
            EventExecutor exe = EventClass._classList.get(new Integer(event.get_eventid()));
            if (exe != null) {
                exe.execute(event);
            }
        }
        catch (Exception e) {
            EventClass._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public final void updateEventNextTime(int event_id, Timestamp next_time) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE server_event SET next_time=? WHERE id=?");
            pstm.setTimestamp(1, next_time);
            pstm.setInt(2, event_id);
            pstm.execute();
        }
        catch (SQLException e) {
            EventClass._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
