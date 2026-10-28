package com.lineage.server.datatables;

import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.model.drop.SetDropExecutor;
import com.lineage.server.templates.L1DropMap;
import java.util.ArrayList;
import java.util.Map;
import com.lineage.server.model.drop.SetDrop;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DropMapTable
{
    private static final Log _log;
    private static DropMapTable _instance;
    
    static {
        _log = LogFactory.getLog(DropMapTable.class);
    }
    
    public static DropMapTable get() {
        if (DropMapTable._instance == null) {
            DropMapTable._instance = new DropMapTable();
        }
        return DropMapTable._instance;
    }
    
    public void load() {
        Map<Integer, ArrayList<L1DropMap>> droplists = this.allDropList();
        SetDropExecutor setDropExecutor = new SetDrop();
        setDropExecutor.addDropMapX(droplists);
    }
    
    private Map<Integer, ArrayList<L1DropMap>> allDropList() {
        PerformanceTimer timer = new PerformanceTimer();
        Map<Integer, ArrayList<L1DropMap>> droplistMap = new HashMap();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_指定地圖掉落`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int mapid = rs.getInt("mapid");
                int itemId = rs.getInt("itemid");
                int min = rs.getInt("min");
                int max = rs.getInt("max");
                int chance = rs.getInt("chance");
                if (this.check_item(itemId)) {
                    L1DropMap drop = new L1DropMap(mapid, itemId, min, max, chance);
                    ArrayList<L1DropMap> dropList = droplistMap.get((Integer)drop.get_mapid());
                    if (dropList == null) {
                        dropList = new ArrayList();
                        droplistMap.put(new Integer(drop.get_mapid()), dropList);
                    }
                    dropList.add(drop);
                }
                String note = rs.getString("note");
                if (!note.contains("=>")) {
                    updata_name(mapid, itemId);
                }
            }
        }
        catch (SQLException e) {
            DropMapTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        DropMapTable._log.info("載入掉落物品資料數量(指定地圖): " + droplistMap.size() + "(" + timer.get() + "ms)");
        return droplistMap;
    }
    
    private boolean check_item(int itemId) {
        L1Item itemTemplate = ItemTable.get().getTemplate(itemId);
        if (itemTemplate == null) {
            errorItem(itemId);
            return false;
        }
        else {
            return true;
        }
    }
    
    private static void updata_name(int mapid, int itemId) {
        Connection cn = null;
        PreparedStatement ps = null;
        String mapname = MapsTable.get().getMapName(mapid);
        String itemname = ItemTable.get().getTemplate(itemId).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `系統_指定地圖掉落` SET `note`=? WHERE `mapid`=? AND `itemid`=?");
            int i = 0;
            ps.setString(++i, mapname + "=>" + itemname);
            ps.setInt(++i, mapid);
            ps.setInt(++i, itemId);
            ps.execute();
        }
        catch (SQLException e) {
            DropMapTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void errorItem(int itemid) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `系統_指定地圖掉落` WHERE `itemid`=?");
            pstm.setInt(1, itemid);
            pstm.execute();
        }
        catch (SQLException e) {
            DropMapTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
