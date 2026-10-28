package com.lineage.william;

import com.lineage.server.utils.PerformanceTimer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import java.util.logging.Logger;

public class NowTimeSpawn
{
    private static Logger _log;
    private static final Log _logx;
    private static NowTimeSpawn _instance;
    private static final String TOKEN = ",";
    public static final HashMap<Integer, NowTimeSpawn> _ItemIdIndex;
    private int _id;
    private int _hh;
    private int _mm;
    private int _npcid;
    private int _isCount;
    private int _x;
    private int _y;
    private int _mapid;
    private String _note;
    private String _note1;
    private int[] _Special;
    private int _istp;
    private int _tpx;
    private int _tpy;
    private int _tpmapid;
    private int _tptime;
    private int _week;
    private int _xyran;
    
    static {
        NowTimeSpawn._log = Logger.getLogger(NowTimeSpawn.class.getName());
        _logx = LogFactory.getLog(NowTimeSpawn.class.getName());
        _ItemIdIndex = new HashMap();
    }
    
    public static NowTimeSpawn get() {
        if (NowTimeSpawn._instance == null) {
            NowTimeSpawn._instance = new NowTimeSpawn();
        }
        return NowTimeSpawn._instance;
    }
    
    private NowTimeSpawn() {
        this._isCount = 0;
        this._x = 0;
        this._y = 0;
        this._mapid = 0;
        this.loadChackDrop();
    }
    
    private void loadChackDrop() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM 系統_鐘點怪物施放");
            rs = pstm.executeQuery();
            this.fillChackDrop(rs);
        }
        catch (SQLException e) {
            NowTimeSpawn._log.log(Level.SEVERE, "error while creating 系統_鐘點怪物施放 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillChackDrop(ResultSet rs) throws SQLException {
        PerformanceTimer timer = new PerformanceTimer();
        while (rs.next()) {
            int id = rs.getInt("sn");
            int hh = rs.getInt("時");
            int mm = rs.getInt("分");
            int npcid = rs.getInt("NPC編號");
            int x = rs.getInt("X座標");
            int y = rs.getInt("Y座標");
            int mapid = rs.getInt("地圖編號");
            int isCount = rs.getInt("數量");
            String note = rs.getString("公告內容");
            String note2 = rs.getString("特殊公告");
            String QQ = null;
            QQ = rs.getString("畫面特效");
            int[] Special = null;
            if (QQ != null) {
                String[] value1 = QQ.split(",");
                Special = new int[value1.length];
                for (int i = 0; i < value1.length; ++i) {
                    int temp = Integer.parseInt(value1[i]);
                    int[] array = Special;
                    int n = i;
                    array[n] += temp;
                }
            }
            int istp = rs.getInt("是否為傳送門");
            int tpx = rs.getInt("x");
            int tpy = rs.getInt("y");
            int tpmapid = rs.getInt("m");
            int tptime = rs.getInt("存在時間");
            int week = rs.getInt("限制星期");
            int xyran = rs.getInt("隨機中心生怪");
            NowTimeSpawn RndValue = new NowTimeSpawn(id, hh, mm, npcid, isCount, x, y, mapid, note, note2, Special, istp, tpx, tpy, tpmapid, tptime, week, xyran);
            NowTimeSpawn._ItemIdIndex.put(id, RndValue);
        }
        NowTimeSpawn._logx.info("載入設置固定時間放怪(" + this.getClass().getSimpleName() + "): " + NowTimeSpawn._ItemIdIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public NowTimeSpawn getTemplate(int id) {
        return NowTimeSpawn._ItemIdIndex.get((Integer)id);
    }
    
    public NowTimeSpawn[] getList() {
        return (NowTimeSpawn[])NowTimeSpawn._ItemIdIndex.values().toArray(new NowTimeSpawn[NowTimeSpawn._ItemIdIndex.size()]);
    }
    
    public NowTimeSpawn(int id, int hh, int mm, int npcid, int isCount, int x, int y, int mapid, String note, String note1, int[] Special, int istp, int tpx, int tpy, int tpmapid, int tptime, int week, int xyran) {
        this._isCount = 0;
        this._x = 0;
        this._y = 0;
        this._mapid = 0;
        this._id = id;
        this._hh = hh;
        this._mm = mm;
        this._npcid = npcid;
        this._isCount = isCount;
        this._x = x;
        this._y = y;
        this._mapid = mapid;
        this._note = note;
        this._note1 = note1;
        this._Special = Special;
        this._istp = istp;
        this._tpx = tpx;
        this._tpy = tpy;
        this._tpmapid = tpmapid;
        this._tptime = tptime;
        this._week = week;
        this._xyran = xyran;
    }
    
    public int getId() {
        return this._id;
    }
    
    public int getHH() {
        return this._hh;
    }
    
    public int getMM() {
        return this._mm;
    }
    
    public int getNpcId() {
        return this._npcid;
    }
    
    public int getIsCount() {
        return this._isCount;
    }
    
    public int getX() {
        return this._x;
    }
    
    public int getY() {
        return this._y;
    }
    
    public int getMapId() {
        return this._mapid;
    }
    
    public String getNote() {
        return this._note;
    }
    
    public String getNote1() {
        return this._note1;
    }
    
    public int[] getSpecial() {
        return this._Special;
    }
    
    public int getistp() {
        return this._istp;
    }
    
    public int gettpx() {
        return this._tpx;
    }
    
    public int gettpy() {
        return this._tpy;
    }
    
    public int gettpmapid() {
        return this._tpmapid;
    }
    
    public int gettptime() {
        return this._tptime;
    }
    
    public int getweek() {
        return this._week;
    }
    
    public int getxyran() {
        return this._xyran;
    }
}
