package com.lineage.server.model;

import com.lineage.server.model.gametime.L1GameTime;
import com.lineage.server.model.gametime.L1GameTimeAdapter;
import com.lineage.server.templates.L1Castle;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.datatables.lock.CastleReading;
import java.util.Random;
import java.util.Iterator;
import java.util.HashMap;
import com.lineage.config.ConfigClastle;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1CastleLocation
{
    private static final Log _log;
    public static final int KENT_CASTLE_ID = 1;
    public static final int OT_CASTLE_ID = 2;
    public static final int WW_CASTLE_ID = 3;
    public static final int GIRAN_CASTLE_ID = 4;
    public static final int HEINE_CASTLE_ID = 5;
    public static final int DOWA_CASTLE_ID = 6;
    public static final int ADEN_CASTLE_ID = 7;
    public static final int DIAD_CASTLE_ID = 8;
    private static final int KENT_TOWER_X;
    private static final int KENT_TOWER_Y;
    private static final short KENT_TOWER_MAP;
    private static final int KENT_X1 = 33089;
    private static final int KENT_X2 = 33219;
    private static final int KENT_Y1 = 32717;
    private static final int KENT_Y2 = 32827;
    private static final short KENT_MAP = 4;
    private static final short KENT_INNER_CASTLE_MAP = 15;
    private static final int KENT_CATAPULT_ATTACK_X1 = 33093;
    private static final int KENT_CATAPULT_ATTACK_Y1 = 32780;
    private static final int KENT_CATAPULT_ATTACK_X2 = 33093;
    private static final int KENT_CATAPULT_ATTACK_Y2 = 32755;
    private static final int KENT_CATAPULT_DEFENCE_X1 = 33130;
    private static final int KENT_CATAPULT_DEFENCE_Y1 = 32779;
    private static final int KENT_CATAPULT_DEFENCE_X2 = 33130;
    private static final int KENT_CATAPULT_DEFENCE_Y2 = 32748;
    private static final int KENT_CATAPULT_MAP = 4;
    private static final int OT_TOWER_X;
    private static final int OT_TOWER_Y;
    private static final short OT_TOWER_MAP;
    private static final int OT_X1 = 32750;
    private static final int OT_X2 = 32850;
    private static final int OT_Y1 = 32220;
    private static final int OT_Y2 = 32350;
    private static final short OT_MAP = 4;
    private static final short OT_INNER_CASTLE_MAP = 260;
    private static final int OT_CATAPULT_ATTACK_X1 = 32778;
    private static final int OT_CATAPULT_ATTACK_Y1 = 32331;
    private static final int OT_CATAPULT_ATTACK_X2 = 32804;
    private static final int OT_CATAPULT_ATTACK_Y2 = 32331;
    private static final int OT_CATAPULT_DEFENCE_X1 = 32798;
    private static final int OT_CATAPULT_DEFENCE_Y1 = 32298;
    private static final int OT_CATAPULT_MAP = 4;
    private static final int WW_TOWER_X;
    private static final int WW_TOWER_Y;
    private static final short WW_TOWER_MAP;
    private static final int WW_X1 = 32571;
    private static final int WW_X2 = 32721;
    private static final int WW_Y1 = 33350;
    private static final int WW_Y2 = 33460;
    private static final short WW_MAP = 4;
    private static final short WW_INNER_CASTLE_MAP = 29;
    private static final int GIRAN_TOWER_X = 33631;
    private static final int GIRAN_TOWER_Y = 32678;
    private static final short GIRAN_TOWER_MAP = 4;
    private static final int GIRAN_X1 = 33559;
    private static final int GIRAN_X2 = 33686;
    private static final int GIRAN_Y1 = 32615;
    private static final int GIRAN_Y2 = 32755;
    private static final short GIRAN_MAP = 4;
    private static final short GIRAN_INNER_CASTLE_MAP = 52;
    private static final int GIRAN_CATAPULT_ATTACK_X1 = 33642;
    private static final int GIRAN_CATAPULT_ATTACK_Y1 = 32748;
    private static final int GIRAN_CATAPULT_ATTACK_X2 = 33619;
    private static final int GIRAN_CATAPULT_ATTACK_Y2 = 32748;
    private static final int GIRAN_CATAPULT_DEFENCE_X1 = 33652;
    private static final int GIRAN_CATAPULT_DEFENCE_Y1 = 32715;
    private static final int GIRAN_CATAPULT_DEFENCE_X2 = 33621;
    private static final int GIRAN_CATAPULT_DEFENCE_Y2 = 32715;
    private static final int GIRAN_CATAPULT_MAP = 4;
    private static final int HEINE_TOWER_X;
    private static final int HEINE_TOWER_Y;
    private static final short HEINE_TOWER_MAP;
    private static final int HEINE_X1 = 33458;
    private static final int HEINE_X2 = 33583;
    private static final int HEINE_Y1 = 33315;
    private static final int HEINE_Y2 = 33490;
    private static final short HEINE_MAP = 4;
    private static final short HEINE_INNER_CASTLE_MAP = 64;
    private static final int DOWA_TOWER_X;
    private static final int DOWA_TOWER_Y;
    private static final short DOWA_TOWER_MAP;
    private static final int DOWA_X1 = 32755;
    private static final int DOWA_X2 = 32870;
    private static final int DOWA_Y1 = 32790;
    private static final int DOWA_Y2 = 32920;
    private static final short DOWA_MAP = 66;
    private static final int ADEN_TOWER_X;
    private static final int ADEN_TOWER_Y;
    private static final short ADEN_TOWER_MAP;
    private static final int ADEN_X1 = 34007;
    private static final int ADEN_X2 = 34162;
    private static final int ADEN_Y1 = 33172;
    private static final int ADEN_Y2 = 33332;
    private static final short ADEN_MAP = 4;
    private static final short ADEN_INNER_CASTLE_MAP = 300;
    private static final int ADEN_SUB_TOWER1_X = 34057;
    private static final int ADEN_SUB_TOWER1_Y = 33291;
    private static final int ADEN_SUB_TOWER2_X = 34123;
    private static final int ADEN_SUB_TOWER2_Y = 33291;
    private static final int ADEN_SUB_TOWER3_X = 34057;
    private static final int ADEN_SUB_TOWER3_Y = 33230;
    private static final int ADEN_SUB_TOWER4_X = 34123;
    private static final int ADEN_SUB_TOWER4_Y = 33230;
    private static final int DIAD_TOWER_X;
    private static final int DIAD_TOWER_Y;
    private static final short DIAD_TOWER_MAP;
    private static final int DIAD_X1 = 32888;
    private static final int DIAD_X2 = 33070;
    private static final int DIAD_Y1 = 32839;
    private static final int DIAD_Y2 = 32953;
    private static final short DIAD_MAP = 320;
    private static final short DIAD_INNER_CASTLE_MAP = 330;
    private static final Map<Integer, L1Location> _towers;
    private static final Map<Integer, L1MapArea> _areas;
    private static final Map<Integer, Integer> _innerTowerMaps;
    private static final Map<Integer, L1Location> _subTowers;
    private static final Map<Integer, L1Clan> _isCastle;
    private static final Map<Integer, L1Location> _catapultG;
    private static final Map<Integer, L1Location> _catapultK;
    private static final Map<Integer, L1Location> _catapultO;
    private static Map<Integer, Integer> _castleTaxRate;
    private static L1CastleLocation.L1CastleTaxRateListener _listener;
    
    static {
        _log = LogFactory.getLog(L1CastleLocation.class);
        KENT_TOWER_X = ConfigClastle.KENT_TOWER_X;
        KENT_TOWER_Y = ConfigClastle.KENT_TOWER_Y;
        KENT_TOWER_MAP = (short)ConfigClastle.KENT_TOWER_MAP;
        OT_TOWER_X = ConfigClastle.OT_TOWER_X;
        OT_TOWER_Y = ConfigClastle.OT_TOWER_Y;
        OT_TOWER_MAP = (short)ConfigClastle.OT_TOWER_MAP;
        WW_TOWER_X = ConfigClastle.WW_TOWER_X;
        WW_TOWER_Y = ConfigClastle.WW_TOWER_Y;
        WW_TOWER_MAP = (short)ConfigClastle.WW_TOWER_MAP;
        HEINE_TOWER_X = ConfigClastle.HEINE_TOWER_X;
        HEINE_TOWER_Y = ConfigClastle.HEINE_TOWER_Y;
        HEINE_TOWER_MAP = (short)ConfigClastle.HEINE_TOWER_MAP;
        DOWA_TOWER_X = ConfigClastle.DOWA_TOWER_X;
        DOWA_TOWER_Y = ConfigClastle.DOWA_TOWER_Y;
        DOWA_TOWER_MAP = (short)ConfigClastle.DOWA_TOWER_MAP;
        ADEN_TOWER_X = ConfigClastle.ADEN_TOWER_X;
        ADEN_TOWER_Y = ConfigClastle.ADEN_TOWER_Y;
        ADEN_TOWER_MAP = (short)ConfigClastle.ADEN_TOWER_MAP;
        DIAD_TOWER_X = ConfigClastle.DIAD_TOWER_X;
        DIAD_TOWER_Y = ConfigClastle.DIAD_TOWER_Y;
        DIAD_TOWER_MAP = (short)ConfigClastle.DIAD_TOWER_MAP;
        _towers = new HashMap();
        _areas = new HashMap();
        _innerTowerMaps = new HashMap();
        _subTowers = new HashMap();
        _isCastle = new HashMap();
        L1CastleLocation._towers.put(1, new L1Location(L1CastleLocation.KENT_TOWER_X, L1CastleLocation.KENT_TOWER_Y, (int)L1CastleLocation.KENT_TOWER_MAP));
        L1CastleLocation._towers.put(2, new L1Location(L1CastleLocation.OT_TOWER_X, L1CastleLocation.OT_TOWER_Y, (int)L1CastleLocation.OT_TOWER_MAP));
        L1CastleLocation._towers.put(3, new L1Location(L1CastleLocation.WW_TOWER_X, L1CastleLocation.WW_TOWER_Y, (int)L1CastleLocation.WW_TOWER_MAP));
        L1CastleLocation._towers.put(4, new L1Location(33631, 32678, 4));
        L1CastleLocation._towers.put(5, new L1Location(L1CastleLocation.HEINE_TOWER_X, L1CastleLocation.HEINE_TOWER_Y, (int)L1CastleLocation.HEINE_TOWER_MAP));
        L1CastleLocation._towers.put(6, new L1Location(L1CastleLocation.DOWA_TOWER_X, L1CastleLocation.DOWA_TOWER_Y, (int)L1CastleLocation.DOWA_TOWER_MAP));
        L1CastleLocation._towers.put(7, new L1Location(L1CastleLocation.ADEN_TOWER_X, L1CastleLocation.ADEN_TOWER_Y, (int)L1CastleLocation.ADEN_TOWER_MAP));
        L1CastleLocation._towers.put(8, new L1Location(L1CastleLocation.DIAD_TOWER_X, L1CastleLocation.DIAD_TOWER_Y, (int)L1CastleLocation.DIAD_TOWER_MAP));
        L1CastleLocation._areas.put(1, new L1MapArea(33089, 32717, 33219, 32827, 4));
        L1CastleLocation._areas.put(2, new L1MapArea(32750, 32220, 32850, 32350, 4));
        L1CastleLocation._areas.put(3, new L1MapArea(32571, 33350, 32721, 33460, 4));
        L1CastleLocation._areas.put(4, new L1MapArea(33559, 32615, 33686, 32755, 4));
        L1CastleLocation._areas.put(5, new L1MapArea(33458, 33315, 33583, 33490, 4));
        L1CastleLocation._areas.put(6, new L1MapArea(32755, 32790, 32870, 32920, 66));
        L1CastleLocation._areas.put(7, new L1MapArea(34007, 33172, 34162, 33332, 4));
        L1CastleLocation._areas.put(8, new L1MapArea(32888, 32839, 33070, 32953, 320));
        L1CastleLocation._innerTowerMaps.put(1, 15);
        L1CastleLocation._innerTowerMaps.put(2, 260);
        L1CastleLocation._innerTowerMaps.put(3, 29);
        L1CastleLocation._innerTowerMaps.put(4, 52);
        L1CastleLocation._innerTowerMaps.put(5, 64);
        L1CastleLocation._innerTowerMaps.put(7, 300);
        L1CastleLocation._innerTowerMaps.put(8, 330);
        L1CastleLocation._subTowers.put(1, new L1Location(34057, 33291, (int)L1CastleLocation.ADEN_TOWER_MAP));
        L1CastleLocation._subTowers.put(2, new L1Location(34123, 33291, (int)L1CastleLocation.ADEN_TOWER_MAP));
        L1CastleLocation._subTowers.put(3, new L1Location(34057, 33230, (int)L1CastleLocation.ADEN_TOWER_MAP));
        L1CastleLocation._subTowers.put(4, new L1Location(34123, 33230, (int)L1CastleLocation.ADEN_TOWER_MAP));
        (_catapultG = new HashMap()).put(1, new L1Location(33642, 32748, 4));
        L1CastleLocation._catapultG.put(2, new L1Location(33619, 32748, 4));
        L1CastleLocation._catapultG.put(3, new L1Location(33652, 32715, 4));
        L1CastleLocation._catapultG.put(4, new L1Location(33621, 32715, 4));
        (_catapultK = new HashMap()).put(1, new L1Location(33093, 32780, 4));
        L1CastleLocation._catapultK.put(2, new L1Location(33093, 32755, 4));
        L1CastleLocation._catapultK.put(3, new L1Location(33130, 32779, 4));
        L1CastleLocation._catapultK.put(4, new L1Location(33130, 32748, 4));
        (_catapultO = new HashMap()).put(1, new L1Location(32778, 32331, 4));
        L1CastleLocation._catapultO.put(2, new L1Location(32804, 32331, 4));
        L1CastleLocation._catapultO.put(3, new L1Location(32798, 32298, 4));
        L1CastleLocation._castleTaxRate = new HashMap();
    }
    
    private L1CastleLocation() {
    }
    
    public static Map<Integer, L1Clan> mapCastle() {
        return L1CastleLocation._isCastle;
    }
    
    public static L1Clan castleClan(Integer key) {
        return L1CastleLocation._isCastle.get(key);
    }
    
    public static void putCastle(Integer key, L1Clan value) {
        try {
            L1CastleLocation._isCastle.put(key, value);
        }
        catch (Exception e) {
            L1CastleLocation._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void removeCastle(Integer key) {
        try {
            L1CastleLocation._isCastle.remove(key);
        }
        catch (Exception e) {
            L1CastleLocation._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static int getCastleId(L1Location loc) {
        for (Map.Entry<Integer, L1Location> entry : L1CastleLocation._towers.entrySet()) {
            if ((entry.getValue()).equals(loc)) {
                return entry.getKey();
            }
        }
        return 0;
    }
    
    public static int getCastleId(int locx, int locy, short mapid) {
        return getCastleId(new L1Location(locx, locy, (int)mapid));
    }
    
    public static int getCastleIdByArea(L1Location loc) {
        for (Map.Entry<Integer, L1MapArea> entry : L1CastleLocation._areas.entrySet()) {
            if ((entry.getValue()).contains(loc)) {
                return entry.getKey();
            }
        }
        for (Map.Entry<Integer, Integer> entry2 : L1CastleLocation._innerTowerMaps.entrySet()) {
            if (entry2.getValue() == loc.getMapId()) {
                return entry2.getKey();
            }
        }
        return 0;
    }
    
    public static int getCastleIdByArea(L1Character cha) {
        return getCastleIdByArea(cha.getLocation());
    }
    
    public static boolean checkInWarArea(int castleId, L1Location loc) {
        return castleId == getCastleIdByArea(loc);
    }
    
    public static boolean checkInWarArea(int castleId, L1Character cha) {
        return checkInWarArea(castleId, cha.getLocation());
    }
    
    public static boolean checkInAllWarArea(L1Location loc) {
        return getCastleIdByArea(loc) != 0;
    }
    
    public static boolean checkInAllWarArea(int locx, int locy, short mapid) {
        return checkInAllWarArea(new L1Location(locx, locy, (int)mapid));
    }
    
    public static int[] getTowerLoc(int castleId) {
        int[] result = new int[3];
        L1Location loc = L1CastleLocation._towers.get((Integer)castleId);
        if (loc != null) {
            result[0] = loc.getX();
            result[1] = loc.getY();
            result[2] = loc.getMapId();
        }
        return result;
    }
    
    public static int[] getWarArea(int castleId) {
        int[] loc = new int[5];
        switch (castleId) {
            case 1: {
                loc[0] = 33089;
                loc[1] = 33219;
                loc[2] = 32717;
                loc[3] = 32827;
                loc[4] = 4;
                break;
            }
            case 2: {
                loc[0] = 32750;
                loc[1] = 32850;
                loc[2] = 32220;
                loc[3] = 32350;
                loc[4] = 4;
                break;
            }
            case 3: {
                loc[0] = 32571;
                loc[1] = 32721;
                loc[2] = 33350;
                loc[3] = 33460;
                loc[4] = 4;
                break;
            }
            case 4: {
                loc[0] = 33559;
                loc[1] = 33686;
                loc[2] = 32615;
                loc[3] = 32755;
                loc[4] = 4;
                break;
            }
            case 5: {
                loc[0] = 33458;
                loc[1] = 33583;
                loc[2] = 33315;
                loc[3] = 33490;
                loc[4] = 4;
                break;
            }
            case 6: {
                loc[0] = 32755;
                loc[1] = 32870;
                loc[2] = 32790;
                loc[3] = 32920;
                loc[4] = 66;
                break;
            }
            case 7: {
                loc[0] = 34007;
                loc[1] = 34162;
                loc[2] = 33172;
                loc[3] = 33332;
                loc[4] = 4;
                break;
            }
            case 8: {
                loc[0] = 32888;
                loc[1] = 33070;
                loc[2] = 32839;
                loc[3] = 32953;
                loc[4] = 320;
                break;
            }
        }
        return loc;
    }
    
    public static int[] getCastleLoc(int castle_id) {
        int[] loc = new int[3];
        switch (castle_id) {
            case 1: {
                loc[0] = 32731;
                loc[1] = 32810;
                loc[2] = 15;
                break;
            }
            case 2: {
                loc[0] = 32802;
                loc[1] = 32270;
                loc[2] = 4;
                break;
            }
            case 3: {
                loc[0] = 32730;
                loc[1] = 32814;
                loc[2] = 29;
                break;
            }
            case 4: {
                loc[0] = 32724;
                loc[1] = 32827;
                loc[2] = 52;
                break;
            }
            case 5: {
                loc[0] = 32568;
                loc[1] = 32855;
                loc[2] = 64;
                break;
            }
            case 6: {
                loc[0] = 32853;
                loc[1] = 32810;
                loc[2] = 66;
                break;
            }
            case 7: {
                loc[0] = 32892;
                loc[1] = 32572;
                loc[2] = 300;
                break;
            }
            case 8: {
                loc[0] = 32733;
                loc[1] = 32985;
                loc[2] = 330;
                break;
            }
        }
        return loc;
    }
    
    public static int[] getGetBackLoc(int castle_id) {
        int[] loc = null;
        switch (castle_id) {
            case 1: {
                loc = L1TownLocation.getGetBackLoc(6);
                break;
            }
            case 2: {
                loc = L1TownLocation.getGetBackLoc(4);
                break;
            }
            case 3: {
                loc = L1TownLocation.getGetBackLoc(5);
                break;
            }
            case 4: {
                loc = L1TownLocation.getGetBackLoc(7);
                break;
            }
            case 5: {
                loc = L1TownLocation.getGetBackLoc(8);
                break;
            }
            case 6: {
                loc = L1TownLocation.getGetBackLoc(9);
                break;
            }
            case 7: {
                loc = L1TownLocation.getGetBackLoc(12);
                break;
            }
            case 8: {
                Random random = new Random();
                int rnd = random.nextInt(3);
                int[][] loctmp = { { 32792, 32807, 310 }, { 32816, 32820, 310 }, { 32823, 32797, 310 } };
                loc = loctmp[rnd];
                break;
            }
            default: {
                loc = L1TownLocation.getGetBackLoc(2);
                break;
            }
        }
        return loc;
    }
    
    public static int getCastleIdByNpcid(int npcid) {
        int castle_id = 0;
        int town_id = L1TownLocation.getTownIdByNpcid(npcid);
        switch (town_id) {
            case 1:
            case 7: {
                castle_id = 4;
                break;
            }
            case 2:
            case 5: {
                castle_id = 3;
                break;
            }
            case 3:
            case 6: {
                castle_id = 1;
                break;
            }
            case 4: {
                castle_id = 2;
                break;
            }
            case 8: {
                castle_id = 5;
                break;
            }
            case 9:
            case 10: {
                castle_id = 6;
                break;
            }
            case 12: {
                castle_id = 7;
                break;
            }
            case 14: {
                castle_id = 8;
                break;
            }
        }
        return castle_id;
    }
    
    public static int getCastleTaxRateByNpcId(int npcId) {
        int castleId = getCastleIdByNpcid(npcId);
        if (castleId != 0) {
            return L1CastleLocation._castleTaxRate.get((Integer)castleId);
        }
        else {
            return 0;
        }
    }
    
    public static void setCastleTaxRate() {
        try {
            L1Castle[] castleTableList;
            for (int length = (castleTableList = CastleReading.get().getCastleTableList()).length, i = 0; i < length; ++i) {
                L1Castle castle = castleTableList[i];
                L1CastleLocation._castleTaxRate.put(castle.getId(), castle.getTaxRate());
            }
            if (L1CastleLocation._listener == null) {
                L1CastleLocation._listener = new L1CastleLocation.L1CastleTaxRateListener();
                L1GameTimeClock.getInstance().addListener(L1CastleLocation._listener);
            }
        }
        catch (Exception e) {
            L1CastleLocation._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static int[] getSubTowerLoc(int no) {
        int[] result = new int[3];
        L1Location loc = L1CastleLocation._subTowers.get((Integer)no);
        if (loc != null) {
            result[0] = loc.getX();
            result[1] = loc.getY();
            result[2] = loc.getMapId();
        }
        return result;
    }
    
    public static int[] getCatapultGLoc(int num) {
        int[] result = new int[3];
        L1Location loc = L1CastleLocation._catapultG.get((Integer)num);
        if (loc != null) {
            result[0] = loc.getX();
            result[1] = loc.getY();
            result[2] = loc.getMapId();
        }
        return result;
    }
    
    public static int[] getCatapultKLoc(int num) {
        int[] result = new int[3];
        L1Location loc = L1CastleLocation._catapultK.get((Integer)num);
        if (loc != null) {
            result[0] = loc.getX();
            result[1] = loc.getY();
            result[2] = loc.getMapId();
        }
        return result;
    }
    
    public static int[] getCatapultOLoc(int num) {
        int[] result = new int[3];
        L1Location loc = L1CastleLocation._catapultO.get((Integer)num);
        if (loc != null) {
            result[0] = loc.getX();
            result[1] = loc.getY();
            result[2] = loc.getMapId();
        }
        return result;
    }
    
    private static class L1CastleTaxRateListener extends L1GameTimeAdapter
    {
        @Override
        public void onDayChanged(L1GameTime time) {
            try {
                L1CastleLocation.setCastleTaxRate();
            }
            catch (Exception e) {
                L1CastleLocation._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
