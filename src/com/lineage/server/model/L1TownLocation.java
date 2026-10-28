package com.lineage.server.model;

import com.lineage.server.templates.L1Town;
import com.lineage.server.datatables.lock.TownReading;
import java.util.Random;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.types.Point;

public class L1TownLocation
{
    public static final int TOWNID_TALKING_ISLAND = 1;
    public static final int TOWNID_SILVER_KNIGHT_TOWN = 2;
    public static final int TOWNID_GLUDIO = 3;
    public static final int TOWNID_ORCISH_FOREST = 4;
    public static final int TOWNID_WINDAWOOD = 5;
    public static final int TOWNID_KENT = 6;
    public static final int TOWNID_GIRAN = 7;
    public static final int TOWNID_HEINE = 8;
    public static final int TOWNID_WERLDAN = 9;
    public static final int TOWNID_OREN = 10;
    public static final int TOWNID_ELVEN_FOREST = 11;
    public static final int TOWNID_ADEN = 12;
    public static final int TOWNID_SILENT_CAVERN = 13;
    public static final int TOWNID_OUM_DUNGEON = 14;
    public static final int TOWNID_RESISTANCE = 15;
    public static final int TOWNID_PIRATE_ISLAND = 16;
    public static final int TOWNID_RECLUSE_VILLAGE = 17;
    private static final short GETBACK_MAP_TALKING_ISLAND = 0;
    private static final Point[] GETBACK_LOC_TALKING_ISLAND;
    private static final short GETBACK_MAP_SILVER_KNIGHT_TOWN = 4;
    private static final Point[] GETBACK_LOC_SILVER_KNIGHT_TOWN;
    private static final short GETBACK_MAP_GLUDIO = 4;
    private static final Point[] GETBACK_LOC_GLUDIO;
    private static final short GETBACK_MAP_ORCISH_FOREST = 4;
    private static final Point[] GETBACK_LOC_ORCISH_FOREST;
    private static final short GETBACK_MAP_WINDAWOOD = 4;
    private static final Point[] GETBACK_LOC_WINDAWOOD;
    private static final short GETBACK_MAP_KENT = 4;
    private static final Point[] GETBACK_LOC_KENT;
    private static final short GETBACK_MAP_GIRAN = 4;
    private static final Point[] GETBACK_LOC_GIRAN;
    private static final short GETBACK_MAP_HEINE = 4;
    private static final Point[] GETBACK_LOC_HEINE;
    private static final short GETBACK_MAP_WERLDAN = 4;
    private static final Point[] GETBACK_LOC_WERLDAN;
    private static final short GETBACK_MAP_OREN = 4;
    private static final Point[] GETBACK_LOC_OREN;
    private static final short GETBACK_MAP_ELVEN_FOREST = 4;
    private static final Point[] GETBACK_LOC_ELVEN_FOREST;
    private static final short GETBACK_MAP_ADEN = 4;
    private static final Point[] GETBACK_LOC_ADEN;
    private static final short GETBACK_MAP_SILENT_CAVERN = 304;
    private static final Point[] GETBACK_LOC_SILENT_CAVERN;
    private static final short GETBACK_MAP_OUM_DUNGEON = 310;
    private static final Point[] GETBACK_LOC_OUM_DUNGEON;
    private static final short GETBACK_MAP_RESISTANCE = 400;
    private static final Point[] GETBACK_LOC_RESISTANCE;
    private static final short GETBACK_MAP_PIRATE_ISLAND = 440;
    private static final Point[] GETBACK_LOC_PIRATE_ISLAND;
    private static final short GETBACK_MAP_RECLUSE_VILLAGE = 400;
    private static final Point[] GETBACK_LOC_RECLUSE_VILLAGE;
    
    static {
        GETBACK_LOC_TALKING_ISLAND = new Point[] { new Point(32600, 32942), new Point(32574, 32944), new Point(32580, 32923), new Point(32557, 32975), new Point(32597, 32914), new Point(32580, 32974) };
        GETBACK_LOC_SILVER_KNIGHT_TOWN = new Point[] { new Point(33071, 33402), new Point(33088, 33398), new Point(33085, 33402), new Point(33097, 33366), new Point(33110, 33365), new Point(33072, 33392) };
        GETBACK_LOC_GLUDIO = new Point[] { new Point(32601, 32757), new Point(32625, 32809), new Point(32611, 32726), new Point(32612, 32781), new Point(32605, 32761), new Point(32614, 32739), new Point(32612, 32775) };
        GETBACK_LOC_ORCISH_FOREST = new Point[] { new Point(32750, 32435), new Point(32745, 32447), new Point(32738, 32452), new Point(32741, 32436), new Point(32749, 32446) };
        GETBACK_LOC_WINDAWOOD = new Point[] { new Point(32608, 33178), new Point(32626, 33185), new Point(32630, 33179), new Point(32625, 33207), new Point(32638, 33203), new Point(32621, 33179) };
        GETBACK_LOC_KENT = new Point[] { new Point(33048, 32750), new Point(33059, 32768), new Point(33047, 32761), new Point(33059, 32759), new Point(33051, 32775), new Point(33048, 32778), new Point(33064, 32773), new Point(33057, 32748) };
        GETBACK_LOC_GIRAN = new Point[] { new Point(33435, 32803), new Point(33439, 32817), new Point(33440, 32809), new Point(33419, 32810), new Point(33426, 32823), new Point(33418, 32818), new Point(33432, 32824) };
        GETBACK_LOC_HEINE = new Point[] { new Point(33593, 33242), new Point(33593, 33248), new Point(33604, 33236), new Point(33599, 33236), new Point(33610, 33247), new Point(33610, 33241), new Point(33599, 33252), new Point(33605, 33252) };
        GETBACK_LOC_WERLDAN = new Point[] { new Point(33702, 32492), new Point(33747, 32508), new Point(33696, 32498), new Point(33723, 32512), new Point(33710, 32521), new Point(33724, 32488), new Point(33693, 32513) };
        GETBACK_LOC_OREN = new Point[] { new Point(34086, 32280), new Point(34037, 32230), new Point(34022, 32254), new Point(34021, 32269), new Point(34044, 32290), new Point(34049, 32316), new Point(34081, 32249), new Point(34074, 32313), new Point(34064, 32230) };
        GETBACK_LOC_ELVEN_FOREST = new Point[] { new Point(33065, 32358), new Point(33052, 32313), new Point(33030, 32342), new Point(33068, 32320), new Point(33071, 32314), new Point(33030, 32370), new Point(33076, 32324), new Point(33068, 32336) };
        GETBACK_LOC_ADEN = new Point[] { new Point(33915, 33114), new Point(34061, 33115), new Point(34090, 33168), new Point(34011, 33136), new Point(34093, 33117), new Point(33959, 33156), new Point(33992, 33120), new Point(34047, 33156) };
        GETBACK_LOC_SILENT_CAVERN = new Point[] { new Point(32856, 32898), new Point(32860, 32916), new Point(32868, 32893), new Point(32875, 32903), new Point(32855, 32898) };
        GETBACK_LOC_OUM_DUNGEON = new Point[] { new Point(32818, 32805), new Point(32800, 32798), new Point(32815, 32819), new Point(32823, 32811), new Point(32817, 32828) };
        GETBACK_LOC_RESISTANCE = new Point[] { new Point(32570, 32667), new Point(32559, 32678), new Point(32564, 32683), new Point(32574, 32661), new Point(32576, 32669), new Point(32572, 32662) };
        GETBACK_LOC_PIRATE_ISLAND = new Point[] { new Point(32431, 33058), new Point(32407, 33054) };
        GETBACK_LOC_RECLUSE_VILLAGE = new Point[] { new Point(32599, 32916), new Point(32599, 32923), new Point(32603, 32908), new Point(32595, 32908), new Point(32591, 32918) };
    }
    
    public static final boolean isGambling(L1PcInstance pc) {
        int x = pc.getX();
        int y = pc.getY();
        short m = pc.getMapId();
        return isGambling(x, y, m);
    }
    
    public static final boolean isGambling(int x, int y, short map) {
        if (map == 4) {
            if (x > 33469 && x < 33528 && y > 32837 && y < 32848) {
                return true;
            }
            else if (x > 33466 && x < 33532 && y > 32858 && y < 32870) {
                return true;
            }
            else if (x > 33528 && x < 33427 && y > 32848 && y < 32871) {
                return true;
            }
            else if (x > 33259 && x < 33266 && y > 32397 && y < 32405) {
                return true;
            }
            else if (x > 33389 && x < 33396 && y > 32340 && y < 32348) {
                return true;
            }
            else if (x > 33332 && x < 33339 && y > 32432 && y < 32440) {
                return true;
            }
            else if (x >= 34225 && x <= 34300 && y >= 33095 && y <= 33280) {
                return true;
            }
            else if (x >= 34200 && x <= 34300 && y >= 33280 && y <= 33515) {
                return true;
            }
        }
        return false;
    }
    
    public static int[] getGetBackLoc(int town_id) {
        Random random = new Random();
        int[] loc = new int[3];
        int rnd = 0;
        switch (town_id) {
            case 1: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_TALKING_ISLAND.length);
                loc[0] = L1TownLocation.GETBACK_LOC_TALKING_ISLAND[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_TALKING_ISLAND[rnd].getY();
                loc[2] = 0;
                break;
            }
            case 2: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_SILVER_KNIGHT_TOWN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_SILVER_KNIGHT_TOWN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_SILVER_KNIGHT_TOWN[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 3: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_GLUDIO.length);
                loc[0] = L1TownLocation.GETBACK_LOC_GLUDIO[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_GLUDIO[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 4: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_ORCISH_FOREST.length);
                loc[0] = L1TownLocation.GETBACK_LOC_ORCISH_FOREST[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_ORCISH_FOREST[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 5: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_WINDAWOOD.length);
                loc[0] = L1TownLocation.GETBACK_LOC_WINDAWOOD[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_WINDAWOOD[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 6: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_KENT.length);
                loc[0] = L1TownLocation.GETBACK_LOC_KENT[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_KENT[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 7: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_GIRAN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_GIRAN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_GIRAN[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 8: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_HEINE.length);
                loc[0] = L1TownLocation.GETBACK_LOC_HEINE[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_HEINE[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 9: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_WERLDAN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_WERLDAN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_WERLDAN[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 10: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_OREN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_OREN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_OREN[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 11: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_ELVEN_FOREST.length);
                loc[0] = L1TownLocation.GETBACK_LOC_ELVEN_FOREST[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_ELVEN_FOREST[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 12: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_ADEN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_ADEN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_ADEN[rnd].getY();
                loc[2] = 4;
                break;
            }
            case 13: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_SILENT_CAVERN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_SILENT_CAVERN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_SILENT_CAVERN[rnd].getY();
                loc[2] = 304;
                break;
            }
            case 14: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_OUM_DUNGEON.length);
                loc[0] = L1TownLocation.GETBACK_LOC_OUM_DUNGEON[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_OUM_DUNGEON[rnd].getY();
                loc[2] = 310;
                break;
            }
            case 15: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_RESISTANCE.length);
                loc[0] = L1TownLocation.GETBACK_LOC_RESISTANCE[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_RESISTANCE[rnd].getY();
                loc[2] = 400;
                break;
            }
            case 16: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_PIRATE_ISLAND.length);
                loc[0] = L1TownLocation.GETBACK_LOC_PIRATE_ISLAND[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_PIRATE_ISLAND[rnd].getY();
                loc[2] = 440;
                break;
            }
            case 17: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_RECLUSE_VILLAGE.length);
                loc[0] = L1TownLocation.GETBACK_LOC_RECLUSE_VILLAGE[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_RECLUSE_VILLAGE[rnd].getY();
                loc[2] = 400;
                break;
            }
            default: {
                rnd = random.nextInt(L1TownLocation.GETBACK_LOC_SILVER_KNIGHT_TOWN.length);
                loc[0] = L1TownLocation.GETBACK_LOC_SILVER_KNIGHT_TOWN[rnd].getX();
                loc[1] = L1TownLocation.GETBACK_LOC_SILVER_KNIGHT_TOWN[rnd].getY();
                loc[2] = 4;
                break;
            }
        }
        return loc;
    }
    
    public static int getTownTaxRateByNpcid(int npcid) {
        int tax_rate = 0;
        int town_id = getTownIdByNpcid(npcid);
        if (town_id >= 1 && town_id <= 10) {
            L1Town town = TownReading.get().getTownTable(town_id);
            tax_rate = town.get_tax_rate() + 2;
        }
        return tax_rate;
    }
    
    public static int getTownIdByNpcid(int npcid) {
        int town_id = 0;
        switch (npcid) {
            case 50015:
            case 70010:
            case 70011:
            case 70012:
            case 70014:
            case 70528:
            case 70532:
            case 70536: {
                town_id = 1;
                break;
            }
            case 50020:
            case 70016:
            case 70018:
            case 70544:
            case 70546: {
                town_id = 6;
                break;
            }
            case 50024:
            case 70019:
            case 70020:
            case 70021:
            case 70022:
            case 70024:
            case 70567: {
                town_id = 3;
                break;
            }
            case 50036:
            case 70026:
            case 70028:
            case 70029:
            case 70030:
            case 70031:
            case 70032:
            case 70033:
            case 70038:
            case 70039:
            case 70043:
            case 70594:
            case 70617:
            case 70632: {
                town_id = 7;
                break;
            }
            case 50039:
            case 70044:
            case 70045:
            case 70654:
            case 70664: {
                town_id = 9;
                break;
            }
            case 50044:
            case 70047:
            case 70048:
            case 70049:
            case 70051:
            case 70052:
            case 70053:
            case 70054:
            case 70055:
            case 70056:
            case 70057:
            case 70058: {
                town_id = 12;
                break;
            }
            case 50051:
            case 70059:
            case 70060:
            case 70061:
            case 70062:
            case 70063:
            case 70065:
            case 70066:
            case 70067:
            case 70068:
            case 70748:
            case 70749: {
                town_id = 10;
                break;
            }
            case 50054:
            case 70070:
            case 70071:
            case 70072:
            case 70773:
            case 70774: {
                town_id = 5;
                break;
            }
            case 50056:
            case 70073:
            case 70074:
            case 70075:
            case 70799: {
                town_id = 2;
                break;
            }
            case 50066:
            case 70082:
            case 70083:
            case 70084:
            case 70860:
            case 70873: {
                town_id = 8;
                break;
            }
            case 70079:
            case 70815:
            case 70836: {
                town_id = 4;
                break;
            }
            case 70092:
            case 70093: {
                town_id = 14;
                break;
            }
        }
        return town_id;
    }
}
