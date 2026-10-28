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

public class SprTable
{
    private static final Log _log;
    private static final Map<Integer, SprTable.Spr> _dataMap;
    private static SprTable _instance;
    
    static {
        _log = LogFactory.getLog(SprTable.class);
        _dataMap = new HashMap();
    }
    
    public static SprTable get() {
        if (SprTable._instance == null) {
            SprTable._instance = new SprTable();
        }
        return SprTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        SprTable.Spr spr = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `spr_action`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("spr_id");
                if (!SprTable._dataMap.containsKey((Integer)key)) {
                    spr = new SprTable.Spr();
                    SprTable._dataMap.put(key, spr);
                }
                else {
                    spr = SprTable._dataMap.get((Integer)key);
                }
                int actid = rs.getInt("act_id");
                int frameCount = rs.getInt("framecount");
                if (frameCount < 0) {
                    frameCount = 0;
                }
                int frameRate = rs.getInt("framerate");
                if (frameRate < 0) {
                    frameRate = 0;
                }
                int speed = this.calcActionSpeed(frameCount, frameRate);
                switch (actid) {
                    case 0:
                    case 4:
                    case 11:
                    case 20:
                    case 24:
                    case 40:
                    case 46:
                    case 50:
                    case 54:
                    case 58:
                    case 62: {
                        spr._moveSpeed.put((Integer)actid, (Integer)speed);
                        continue;
                    }
                    case 1:
                    case 5:
                    case 12:
                    case 21:
                    case 25:
                    case 41:
                    case 47:
                    case 51:
                    case 55:
                    case 59:
                    case 63: {
                        spr._attackSpeed.put((Integer)actid, (Integer)speed);
                        continue;
                    }
                    case 2: {
                        spr._dmg = speed;
                        continue;
                    }
                    case 18: {
                        spr._dirSpellSpeed = speed;
                        continue;
                    }
                    case 19: {
                        spr._nodirSpellSpeed = speed;
                        continue;
                    }
                    case 30: {
                        spr._dirSpellSpeed30 = speed;
                        continue;
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        catch (SQLException e) {
            SprTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SprTable._log.info("載入圖形影格資料數量: " + SprTable._dataMap.size() + "(" + timer.get() + "ms)");
    }
    
    private int calcActionSpeed(int frameCount, int frameRate) {
        return (int)((double)(frameCount * 40) * (24.0 / (double)frameRate));
    }
    
    public int getAttackSpeed(int sprid, int actid) {
        if (SprTable._dataMap.containsKey((Integer)sprid)) {
            if ((SprTable._dataMap.get((Integer)sprid))._attackSpeed.containsKey((Integer)actid)) {
                return (SprTable._dataMap.get((Integer)sprid))._attackSpeed.get((Integer)actid);
            }
            else if (actid == 1) {
                return 0;
            }
            else {
                return (SprTable._dataMap.get((Integer)sprid))._attackSpeed.get(1);
            }
        }
        else {
            return 0;
        }
    }
    
    public int getMoveSpeed(int sprid, int actid) {
        if (SprTable._dataMap.containsKey((Integer)sprid)) {
            if ((SprTable._dataMap.get((Integer)sprid))._moveSpeed.containsKey((Integer)actid)) {
                return (SprTable._dataMap.get((Integer)sprid))._moveSpeed.get((Integer)actid);
            }
            else if (actid == 0) {
                return 0;
            }
            else {
                return (SprTable._dataMap.get((Integer)sprid))._moveSpeed.get(0);
            }
        }
        else {
            return 0;
        }
    }
    
    public int getDirSpellSpeed(int sprid) {
        if (SprTable._dataMap.containsKey((Integer)sprid)) {
            return (SprTable._dataMap.get((Integer)sprid))._dirSpellSpeed;
        }
        else {
            return 0;
        }
    }
    
    public int getNodirSpellSpeed(int sprid) {
        if (SprTable._dataMap.containsKey((Integer)sprid)) {
            return (SprTable._dataMap.get((Integer)sprid))._nodirSpellSpeed;
        }
        else {
            return 0;
        }
    }
    
    public int getDirSpellSpeed30(int sprid) {
        if (SprTable._dataMap.containsKey((Integer)sprid)) {
            return (SprTable._dataMap.get((Integer)sprid))._dirSpellSpeed30;
        }
        else {
            return 0;
        }
    }
    
    public int getDmg(int sprid) {
        if (SprTable._dataMap.containsKey((Integer)sprid)) {
            return (SprTable._dataMap.get((Integer)sprid))._dmg;
        }
        else {
            return 0;
        }
    }
    
    public long spr_move_speed(int tempCharGfx) {
        return 200L;
    }
    
    public long spr_attack_speed(int tempCharGfx) {
        return 200L;
    }
    
    public long spr_skill_speed(int tempCharGfx) {
        return 200L;
    }
    
    private static class Spr
    {
        private final Map<Integer, Integer> _moveSpeed;
        private final Map<Integer, Integer> _attackSpeed;
        private int _nodirSpellSpeed;
        private int _dirSpellSpeed;
        private int _dirSpellSpeed30;
        private int _dmg;
        
        private Spr() {
            this._moveSpeed = new HashMap();
            this._attackSpeed = new HashMap();
            this._nodirSpellSpeed = 0;
            this._dirSpellSpeed = 0;
            this._dirSpellSpeed30 = 0;
            this._dmg = 0;
        }
        
        /*
        static void access$2(SprTable.Spr spr, int dmg) {
            spr._dmg = dmg;
        }
        */
        
        /*
        static void access$3(SprTable.Spr spr, int dirSpellSpeed) {
            spr._dirSpellSpeed = dirSpellSpeed;
        }
        */
        
        /*
        static void access$4(SprTable.Spr spr, int nodirSpellSpeed) {
            spr._nodirSpellSpeed = nodirSpellSpeed;
        }
        */
        
        /*
        static void access$5(SprTable.Spr spr, int dirSpellSpeed30) {
            spr._dirSpellSpeed30 = dirSpellSpeed30;
        }
        */
    }
}
