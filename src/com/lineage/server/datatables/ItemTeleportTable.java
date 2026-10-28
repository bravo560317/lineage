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

public class ItemTeleportTable
{
    private static final Log _log;
    private static final Map<Integer, ItemTeleportTable.TeleportList> _teleportList;
    public static boolean START;
    private static ItemTeleportTable _instance;
    
    static {
        _log = LogFactory.getLog(ItemTeleportTable.class);
        _teleportList = new HashMap();
        ItemTeleportTable.START = false;
    }
    
    public static ItemTeleportTable get() {
        if (ItemTeleportTable._instance == null) {
            ItemTeleportTable._instance = new ItemTeleportTable();
        }
        return ItemTeleportTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `etcitem_teleport`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                int locx = rs.getInt("locx");
                int locy = rs.getInt("locy");
                short mapid = rs.getShort("mapid");
                int time = rs.getInt("time");
                int week = rs.getInt("限制星期");
                int starttime = rs.getInt("幾點開始");
                int unitytime = rs.getInt("幾點結束");
                ItemTeleportTable.TeleportList list = new ItemTeleportTable.TeleportList();
                list._locx = locx;
                list._locy = locy;
                list._mapid = mapid;
                list._time = time;
                list._week = week;
                list._starttime = starttime;
                list._unitytime = unitytime;
                ItemTeleportTable._teleportList.put(item_id, list);
            }
        }
        catch (SQLException e) {
            ItemTeleportTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemTeleportTable._log.info("讀取=>傳送捲軸傳送點定義數量: " + ItemTeleportTable._teleportList.size() + "(" + timer.get() + "ms)");
        if (ItemTeleportTable._teleportList.size() <= 0) {
            ItemTeleportTable._teleportList.clear();
        }
        else {
            ItemTeleportTable.START = true;
        }
    }
    
    public ItemTeleportTable.TeleportList getLoc(int item_id) {
        ItemTeleportTable.TeleportList teleport = null;
        if (ItemTeleportTable._teleportList.containsKey((Integer)item_id)) {
            teleport = ItemTeleportTable._teleportList.get((Integer)item_id);
        }
        return teleport;
    }
    
    public class TeleportList
    {
        private int _locx;
        private int _locy;
        private short _mapid;
        private int _time;
        private int _week;
        private int _starttime;
        private int _unitytime;
        
        public int getLocX() {
            return this._locx;
        }
        
        public int getLocY() {
            return this._locy;
        }
        
        public short getMapId() {
            return this._mapid;
        }
        
        public int getTime() {
            return this._time;
        }
        
        public int getWeek() {
            return this._week;
        }
        
        public int getStarttime() {
            return this._starttime;
        }
        
        public int getUnitytime() {
            return this._unitytime;
        }
        
        /*
        static void access$0(ItemTeleportTable.TeleportList list, int locx) {
            list._locx = locx;
        }
        */
        
        /*
        static void access$1(ItemTeleportTable.TeleportList list, int locy) {
            list._locy = locy;
        }
        */
        
        /*
        static void access$2(ItemTeleportTable.TeleportList list, short mapid) {
            list._mapid = mapid;
        }
        */
        
        /*
        static void access$3(ItemTeleportTable.TeleportList list, int time) {
            list._time = time;
        }
        */
        
        /*
        static void access$4(ItemTeleportTable.TeleportList list, int week) {
            list._week = week;
        }
        */
        
        /*
        static void access$5(ItemTeleportTable.TeleportList list, int starttime) {
            list._starttime = starttime;
        }
        */
        
        /*
        static void access$6(ItemTeleportTable.TeleportList list, int unitytime) {
            list._unitytime = unitytime;
        }
        */
    }
}
