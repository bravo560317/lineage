package com.lineage.server.datatables;

import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Scenery;
import java.util.Map;
import org.apache.commons.logging.Log;

public class SceneryTable
{
    private static final Log _log;
    private static SceneryTable _instance;
    private static final Map<Integer, L1Scenery> _sceneryList;
    private static final Map<Integer, L1Scenery> _fieldList;
    
    static {
        _log = LogFactory.getLog(SceneryTable.class);
        _sceneryList = new HashMap();
        _fieldList = new HashMap();
    }
    
    public static SceneryTable get() {
        if (SceneryTable._instance == null) {
            SceneryTable._instance = new SceneryTable();
        }
        return SceneryTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `spawnlist_scenery`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int gfxid = rs.getInt("gfxid");
                int locx = rs.getInt("locx");
                int locy = rs.getInt("locy");
                int heading = rs.getInt("heading");
                int mapid = rs.getInt("mapid");
                String html = rs.getString("html");
                L1Scenery scenery = new L1Scenery();
                scenery.set_gfxid(gfxid);
                scenery.set_locx(locx);
                scenery.set_locy(locy);
                scenery.set_heading(heading);
                scenery.set_mapid(mapid);
                scenery.set_html(html);
                SceneryTable._sceneryList.put(new Integer(id), scenery);
            }
        }
        catch (SQLException e) {
            SceneryTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        SceneryTable._log.info("載入景觀設置資料數量: " + SceneryTable._sceneryList.size() + "(" + timer.get() + "ms)");
        this.satrt();
    }
    
    private void satrt() {
        for (L1Scenery scenery : SceneryTable._sceneryList.values()) {
            L1FieldObjectInstance field = (L1FieldObjectInstance)NpcTable.get().newNpcInstance(71081);
            if (field != null) {
                field.setId(IdFactoryNpc.get().nextId());
                field.setGfxId(scenery.get_gfxid());
                field.setTempCharGfx(scenery.get_gfxid());
                field.setMap((short)scenery.get_mapid());
                field.setX(scenery.get_locx());
                field.setY(scenery.get_locy());
                field.setHomeX(scenery.get_locx());
                field.setHomeY(scenery.get_locy());
                field.setHeading(scenery.get_heading());
                World.get().storeObject(field);
                World.get().addVisibleObject(field);
                SceneryTable._fieldList.put(new Integer(field.getId()), scenery);
            }
        }
    }
    
    public String get_sceneryHtml(int objid) {
        L1Scenery scenery = SceneryTable._fieldList.get(new Integer(objid));
        if (scenery != null && !scenery.get_html().equals("0")) {
            return scenery.get_html();
        }
        else {
            return null;
        }
    }
    
    public void storeScenery(String note, int gfxid, int locx, int locy, int heading, int mapid, String html) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO `spawnlist_scenery` SET `note`=?,`gfxid`=?,`locx`=?,`locy`=?,`heading`=?,`mapid`=?,`html`=?");
            int i = 0;
            pstm.setString(++i, note);
            pstm.setInt(++i, gfxid);
            pstm.setInt(++i, locx);
            pstm.setInt(++i, locy);
            pstm.setInt(++i, heading);
            pstm.setInt(++i, mapid);
            pstm.setString(++i, html);
            pstm.execute();
        }
        catch (Exception e) {
            SceneryTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
