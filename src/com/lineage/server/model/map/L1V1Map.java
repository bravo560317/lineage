package com.lineage.server.model.map;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1GuardInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.types.Point;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1V1Map extends L1Map
{
    private static final Log _log;
    private int _mapId;
    private int _worldTopLeftX;
    private int _worldTopLeftY;
    private int _worldBottomRightX;
    private int _worldBottomRightY;
    private byte[][] _map;
    private boolean _isUnderwater;
    private boolean _isMarkable;
    private boolean _isTeleportable;
    private boolean _isEscapable;
    private boolean _isUseResurrection;
    private boolean _isUsePainwand;
    private boolean _isEnabledDeathPenalty;
    private boolean _isTakePets;
    private boolean _isRecallPets;
    private boolean _isUsableItem;
    private boolean _isUsableSkill;
    private boolean _isGuaji;
    private boolean _isClanPc;
    private boolean _isPartyPc;
    private boolean _isdropitem;
    private int _isUsableShop;
    private int _isStart_time;
    private int _isEnd_time;
    private int _isStart_time2;
    private int _isEnd_time2;
    private int _isweek;
    private static final byte BITFLAG_IS_IMPASSABLE = Byte.MIN_VALUE;
    private static final byte[] HEADING_TABLE_X;
    private static final byte[] HEADING_TABLE_Y;
    
    static {
        _log = LogFactory.getLog(L1V1Map.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    protected L1V1Map() {
    }
    
    public L1V1Map(int mapId, byte[][] map, int worldTopLeftX, int worldTopLeftY, boolean underwater, boolean markable, boolean teleportable, boolean escapable, boolean useResurrection, boolean usePainwand, boolean enabledDeathPenalty, boolean takePets, boolean recallPets, boolean usableItem, boolean usableSkill, boolean Guaji, boolean isClanPc, boolean isPartyPc, boolean isdropitem, int usableShop, int Start_time, int End_time, int Start_time2, int End_time2, int week) {
        this._mapId = mapId;
        this._map = map;
        this._worldTopLeftX = worldTopLeftX;
        this._worldTopLeftY = worldTopLeftY;
        this._worldBottomRightX = worldTopLeftX + map.length - 1;
        this._worldBottomRightY = worldTopLeftY + map[0].length - 1;
        this._isUnderwater = underwater;
        this._isMarkable = markable;
        this._isTeleportable = teleportable;
        this._isEscapable = escapable;
        this._isUseResurrection = useResurrection;
        this._isUsePainwand = usePainwand;
        this._isEnabledDeathPenalty = enabledDeathPenalty;
        this._isTakePets = takePets;
        this._isRecallPets = recallPets;
        this._isUsableItem = usableItem;
        this._isUsableSkill = usableSkill;
        this._isGuaji = Guaji;
        this._isClanPc = isClanPc;
        this._isPartyPc = isPartyPc;
        this._isdropitem = isdropitem;
        this._isUsableShop = usableShop;
        this._isStart_time = Start_time;
        this._isEnd_time = End_time;
        this._isStart_time2 = Start_time2;
        this._isEnd_time2 = End_time2;
        this._isweek = week;
    }
    
    public L1V1Map(L1V1Map map) {
        this._mapId = map._mapId;
        this._map = new byte[map._map.length][];
        for (int i = 0; i < map._map.length; ++i) {
            this._map[i] = map._map[i].clone();
        }
        this._worldTopLeftX = map._worldTopLeftX;
        this._worldTopLeftY = map._worldTopLeftY;
        this._worldBottomRightX = map._worldBottomRightX;
        this._worldBottomRightY = map._worldBottomRightY;
    }
    
    @Override
    public void setTestTile(int x, int y, int tile) {
        this.setTile(x, y, tile);
    }
    
    private int accessTile(int x, int y) {
        if (!this.isInMap(x, y)) {
            return 0;
        }
        else {
            return this._map[x - this._worldTopLeftX][y - this._worldTopLeftY];
        }
    }
    
    private int accessOriginalTile(int x, int y) {
        return this.accessTile(x, y) & 0x7F;
    }
    
    public void setTile(int x, int y, int tile) {
        if (!this.isInMap(x, y)) {
            return;
        }
        else {
            this._map[x - this._worldTopLeftX][y - this._worldTopLeftY] = (byte)tile;
            return;
        }
    }
    
    public byte[][] getRawTiles() {
        return this._map;
    }
    
    @Override
    public int getId() {
        return this._mapId;
    }
    
    @Override
    public int getX() {
        return this._worldTopLeftX;
    }
    
    @Override
    public int getY() {
        return this._worldTopLeftY;
    }
    
    @Override
    public int getWidth() {
        return this._worldBottomRightX - this._worldTopLeftX + 1;
    }
    
    @Override
    public int getHeight() {
        return this._worldBottomRightY - this._worldTopLeftY + 1;
    }
    
    @Override
    public int getTile(int x, int y) {
        short tile = this._map[x - this._worldTopLeftX][y - this._worldTopLeftY];
        if ((tile & 0xFFFFFF80) != 0x0) {
            return 300;
        }
        else {
            return this.accessOriginalTile(x, y);
        }
    }
    
    @Override
    public int getOriginalTile(int x, int y) {
        return this.accessOriginalTile(x, y);
    }
    
    @Override
    public boolean isInMap(Point pt) {
        return this.isInMap(pt.getX(), pt.getY());
    }
    
    @Override
    public boolean isInMap(int x, int y) {
        return (this._mapId != 4 || (x >= 32520 && y >= 32070 && (y >= 32190 || x >= 33950))) && (this._worldTopLeftX <= x && x <= this._worldBottomRightX && this._worldTopLeftY <= y && y <= this._worldBottomRightY);
    }
    
    @Override
    public boolean isPassable(int x, int y) {
        return this.isPassable(x, y - 1, 4) || this.isPassable(x + 1, y, 6) || this.isPassable(x, y + 1, 0) || this.isPassable(x - 1, y, 2);
    }
    
    @Override
    public boolean isPassable(Point pt, L1Character cha) {
        return this.isPassable(pt.getX(), pt.getY(), cha);
    }
    
    @Override
    public boolean isPassable(int x, int y, L1Character cha) {
        return this.isPassable(x, y - 1, 4, cha) || this.isPassable(x + 1, y, 6, cha) || this.isPassable(x, y + 1, 0, cha) || this.isPassable(x - 1, y, 2, cha);
    }
    
    @Override
    public boolean isPassable(Point pt, int heading, L1Character cha) {
        return this.isPassable(pt.getX(), pt.getY(), heading, cha);
    }
    
    @Override
    public boolean isPassable(int x, int y, int heading) {
        int tile1 = this.accessTile(x, y);
        int tile2;
        if (heading == 0) {
            tile2 = this.accessTile(x, y - 1);
            int newX = x;
            int newY = y - 1;
        }
        else if (heading == 1) {
            tile2 = this.accessTile(x + 1, y - 1);
            int newX = x + 1;
            int newY = y - 1;
        }
        else if (heading == 2) {
            tile2 = this.accessTile(x + 1, y);
            int newX = x + 1;
            int newY = y;
        }
        else if (heading == 3) {
            tile2 = this.accessTile(x + 1, y + 1);
            int newX = x + 1;
            int newY = y + 1;
        }
        else if (heading == 4) {
            tile2 = this.accessTile(x, y + 1);
            int newX = x;
            int newY = y + 1;
        }
        else if (heading == 5) {
            tile2 = this.accessTile(x - 1, y + 1);
            int newX = x - 1;
            int newY = y + 1;
        }
        else if (heading == 6) {
            tile2 = this.accessTile(x - 1, y);
            int newX = x - 1;
            int newY = y;
        }
        else if (heading == 7) {
            tile2 = this.accessTile(x - 1, y - 1);
            int newX = x - 1;
            int newY = y - 1;
        }
        else {
            return false;
        }
        if ((tile2 & 0xFFFFFF80) == 0xFFFFFF80) {
            return false;
        }
        else if ((tile2 & 0x2) != 0x2 && (tile2 & 0x1) != 0x1) {
            return false;
        }
        else if (heading == 0) {
            return (tile1 & 0x2) == 0x2;
        }
        else if (heading == 1) {
            int tile3 = this.accessTile(x, y - 1);
            int tile4 = this.accessTile(x + 1, y);
            return (tile3 & 0x1) == 0x1 || (tile4 & 0x2) == 0x2;
        }
        else if (heading == 2) {
            return (tile1 & 0x1) == 0x1;
        }
        else if (heading == 3) {
            int tile3 = this.accessTile(x, y + 1);
            return (tile3 & 0x1) == 0x1;
        }
        else if (heading == 4) {
            return (tile2 & 0x2) == 0x2;
        }
        else if (heading == 5) {
            return (tile2 & 0x1) == 0x1 || (tile2 & 0x2) == 0x2;
        }
        else if (heading == 6) {
            return (tile2 & 0x1) == 0x1;
        }
        else if (heading == 7) {
            int tile3 = this.accessTile(x - 1, y);
            return (tile3 & 0x2) == 0x2;
        }
        else {
            return false;
        }
    }
    
    @Override
    public boolean isPassable(int x, int y, int heading, L1Character cha) {
        try {
            int tile1 = this.accessTile(x, y);
            int locx = x + L1V1Map.HEADING_TABLE_X[heading];
            int locy = y + L1V1Map.HEADING_TABLE_Y[heading];
            int tile2 = this.accessTile(locx, locy);
            if (tile2 == 0) {
                return false;
            }
            else if ((tile2 & 0xFFFFFF80) == 0xFFFFFF80) {
                return false;
            }
            else if (cha != null) {
                switch (this._mapId) {
                    case 0: {
                        return this.set_map(tile2, 1);
                    }
                    case 4:
                    case 57:
                    case 58:
                    case 68:
                    case 69:
                    case 70:
                    case 303:
                    case 430:
                    case 440:
                    case 445:
                    case 480:
                    case 613:
                    case 630: {
                        switch (tile2) {
                            case 12: {
                                return false;
                            }
                            case 21: {
                                return false;
                            }
                            case 26: {
                                return false;
                            }
                            case 28: {
                                return false;
                            }
                            case 29: {
                                return false;
                            }
                            case 44: {
                                return false;
                            }
                            default: {
                                return this.set_map(tile2, 8);
                            }
                        }
                    }
                    default: {
                        return this.set_map(tile2, 3);
                    }
                }
            }
            else {
                switch (heading) {
                    case 0: {
                        return (tile1 & 0x2) == 0x2;
                    }
                    case 1: {
                        int tile3 = this.accessTile(x, y - 1);
                        int tile4 = this.accessTile(x + 1, y);
                        return (tile3 & 0x1) == 0x1 || (tile4 & 0x2) == 0x2;
                    }
                    case 2: {
                        return (tile1 & 0x1) == 0x1;
                    }
                    case 3: {
                        int tile3 = this.accessTile(x, y + 1);
                        return (tile3 & 0x1) == 0x1;
                    }
                    case 4: {
                        return (tile2 & 0x2) == 0x2;
                    }
                    case 5: {
                        return (tile2 & 0x1) == 0x1 || (tile2 & 0x2) == 0x2;
                    }
                    case 6: {
                        return (tile2 & 0x1) == 0x1;
                    }
                    case 7: {
                        int tile3 = this.accessTile(x - 1, y);
                        return (tile3 & 0x2) == 0x2;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    @Override
    public boolean isPassableDna(int x, int y, int heading) {
        try {
            int locx = x + L1V1Map.HEADING_TABLE_X[heading];
            int locy = y + L1V1Map.HEADING_TABLE_Y[heading];
            int tile2 = this.accessTile(locx, locy);
            if (tile2 == 0) {
                return false;
            }
            else {
                switch (this._mapId) {
                    case 0: {
                        return this.set_map(tile2, 1);
                    }
                    case 4:
                    case 57:
                    case 58:
                    case 68:
                    case 69:
                    case 70:
                    case 303:
                    case 430:
                    case 440:
                    case 445:
                    case 480:
                    case 613:
                    case 630: {
                        switch (tile2) {
                            case 12: {
                                return false;
                            }
                            case 21: {
                                return false;
                            }
                            case 26: {
                                return false;
                            }
                            case 28: {
                                return false;
                            }
                            case 29: {
                                return false;
                            }
                            case 44: {
                                return false;
                            }
                            default: {
                                return this.set_map(tile2, 8);
                            }
                        }
                    }
                    default: {
                        return this.set_map(tile2, 3);
                    }
                }
            }
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    private boolean set_map(int tile2, int i) {
        return (tile2 & i) != 0x0;
    }
    
    @Override
    public boolean isDoorPassable(int x, int y, int heading, L1NpcInstance npc) {
        try {
            if (heading == -1) {
                return false;
            }
            else {
                int locx = x + L1V1Map.HEADING_TABLE_X[heading];
                int locy = y + L1V1Map.HEADING_TABLE_Y[heading];
                int tile2 = this.accessTile(locx, locy);
                if (npc != null) {
                    if (tile2 == 3) {
                        if (npc.is_now_target() == null) {
                            return false;
                        }
                        else {
                            for (L1Object object : World.get().getVisibleObjects(npc, 2)) {
                                if (object instanceof L1DoorInstance) {
                                    L1DoorInstance door = (L1DoorInstance)object;
                                    switch (door.getDoorId()) {
                                        case 6006:
                                        case 6007:
                                        case 10000:
                                        case 10001:
                                        case 10002:
                                        case 10003:
                                        case 10004:
                                        case 10005:
                                        case 10006:
                                        case 10007:
                                        case 10008:
                                        case 10009:
                                        case 10010:
                                        case 10011:
                                        case 10012:
                                        case 10013:
                                        case 10015:
                                        case 10016:
                                        case 10017:
                                        case 10019:
                                        case 10020:
                                        case 10036: {
                                            return false;
                                        }
                                        default: {
                                            if (door.getOpenStatus() == 29) {
                                                if (npc instanceof L1GuardInstance) {
                                                    door.open();
                                                    return true;
                                                }
                                                else if (door.getKeeperId() == 0) {
                                                    door.open();
                                                    return true;
                                                }
                                                else {
                                                    continue;
                                                }
                                            }
                                            else {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                            return false;
                        }
                    }
                    else {
                        return true;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return true;
    }
    
    @Override
    public void setPassable(Point pt, boolean isPassable) {
        this.setPassable(pt.getX(), pt.getY(), isPassable, 2);
    }
    
    @Override
    public void setPassable(int x, int y, boolean isPassable, int door) {
        switch (door) {
            case 0: {
                this.set_door_0(x, y, isPassable);
                break;
            }
            case 1: {
                this.set_door_1(x, y, isPassable);
                break;
            }
            default: {
                if (isPassable) {
                    this.setTile(x, y, (int)(short)(this.accessTile(x, y) & 0x7F));
                    break;
                }
                else {
                    this.setTile(x, y, (int)(short)(this.accessTile(x, y) | 0xFFFFFF80));
                    break;
                }
            }
        }
    }
    
    private void set_door_0(int x, int y, boolean isPassable) {
        try {
            if (isPassable) {
                this._map[x - this._worldTopLeftX][y - this._worldTopLeftY] = 47;
            }
            else {
                this._map[x - this._worldTopLeftX][y - this._worldTopLeftY] = 3;
                this._map[x - 1 - this._worldTopLeftX][y - this._worldTopLeftY] = 3;
                this._map[x + 1 - this._worldTopLeftX][y - this._worldTopLeftY] = 3;
            }
        }
        catch (Exception e) {
            L1V1Map._log.error("X:" + x + " Y:" + y + " MAP:" + this._mapId, e);
        }
    }
    
    private void set_door_1(int x, int y, boolean isPassable) {
        try {
            if (isPassable) {
                this._map[x - this._worldTopLeftX][y - this._worldTopLeftY] = 47;
            }
            else {
                this._map[x - this._worldTopLeftX][y - this._worldTopLeftY] = 3;
                this._map[x - this._worldTopLeftX][y - 1 - this._worldTopLeftY] = 3;
                this._map[x - this._worldTopLeftX][y + 1 - this._worldTopLeftY] = 3;
            }
        }
        catch (Exception e) {
            L1V1Map._log.error("X:" + x + " Y:" + y + " MAP:" + this._mapId, e);
        }
    }
    
    @Override
    public boolean isSafetyZone(Point pt) {
        return this.isSafetyZone(pt.getX(), pt.getY());
    }
    
    @Override
    public boolean isSafetyZone(int x, int y) {
        int tile = this.accessOriginalTile(x, y);
        return (tile & 0x30) == 0x10;
    }
    
    @Override
    public boolean isCombatZone(Point pt) {
        return this.isCombatZone(pt.getX(), pt.getY());
    }
    
    @Override
    public boolean isCombatZone(int x, int y) {
        int tile = this.accessOriginalTile(x, y);
        return (tile & 0x30) == 0x20;
    }
    
    @Override
    public boolean isNormalZone(Point pt) {
        return this.isNormalZone(pt.getX(), pt.getY());
    }
    
    @Override
    public boolean isNormalZone(int x, int y) {
        int tile = this.accessOriginalTile(x, y);
        return (tile & 0x30) == 0x0;
    }
    
    @Override
    public boolean isArrowPassable(Point pt) {
        return this.isArrowPassable(pt.getX(), pt.getY());
    }
    
    @Override
    public boolean isArrowPassable(int x, int y) {
        return (this.accessOriginalTile(x, y) & 0xE) != 0x0;
    }
    
    @Override
    public boolean isArrowPassable(Point pt, int heading) {
        return this.isArrowPassable(pt.getX(), pt.getY(), heading);
    }
    
    @Override
    public boolean isArrowPassable(int x, int y, int heading) {
        try {
            int newX = x + L1V1Map.HEADING_TABLE_X[heading];
            int newY = y + L1V1Map.HEADING_TABLE_Y[heading];
            int tile2 = this.accessTile(newX, newY);
            switch (tile2) {
                case 0:
                case 3: {
                    return false;
                }
                default: {
                    return (tile2 & 0xC) != 0x0;
                }
            }
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public boolean isUnderwater() {
        return this._isUnderwater;
    }
    
    @Override
    public boolean isMarkable() {
        return this._isMarkable;
    }
    
    @Override
    public boolean isTeleportable() {
        return this._isTeleportable;
    }
    
    @Override
    public boolean isEscapable() {
        return this._isEscapable;
    }
    
    @Override
    public boolean isUseResurrection() {
        return this._isUseResurrection;
    }
    
    @Override
    public boolean isUsePainwand() {
        return this._isUsePainwand;
    }
    
    @Override
    public boolean isEnabledDeathPenalty() {
        return this._isEnabledDeathPenalty;
    }
    
    @Override
    public boolean isTakePets() {
        return this._isTakePets;
    }
    
    @Override
    public boolean isRecallPets() {
        return this._isRecallPets;
    }
    
    @Override
    public boolean isUsableItem() {
        return this._isUsableItem;
    }
    
    @Override
    public boolean isUsableSkill() {
        return this._isUsableSkill;
    }
    
    @Override
    public boolean isGuaji() {
        return this._isGuaji;
    }
    
    @Override
    public boolean isClanPc() {
        return this._isClanPc;
    }
    
    @Override
    public boolean isPartyPc() {
        return this._isPartyPc;
    }
    
    @Override
    public int isUsableShop() {
        return this._isUsableShop;
    }
    
    @Override
    public int isStart_time() {
        return this._isStart_time;
    }
    
    @Override
    public int isweek() {
        return this._isweek;
    }
    
    @Override
    public int isEnd_time() {
        return this._isEnd_time;
    }
    
    public int isStart_time2() {
        return this._isStart_time2;
    }
    
    public int isEnd_time2() {
        return this._isEnd_time2;
    }
    
    @Override
    public boolean isdropitem() {
        return this._isdropitem;
    }
    
    @Override
    public boolean isFishingZone(int x, int y) {
        return this.accessOriginalTile(x, y) == 28;
    }
    
    @Override
    public int isExistDoor(int x, int y) {
        try {
            return this._map[x - this._worldTopLeftX][y - this._worldTopLeftY];
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    @Override
    public String toString(Point pt) {
        return new StringBuilder().append(this.getOriginalTile(pt.getX(), pt.getY())).toString();
    }
    
    @Override
    public L1V1Map copyMap(int newMapId) {
        return this.clone(newMapId);
    }
    
    private L1V1Map clone(int newMapId) {
        L1V1Map map = new L1V1Map(this);
        map._mapId = newMapId;
        map._isUnderwater = this._isUnderwater;
        map._isMarkable = this._isMarkable;
        map._isTeleportable = this._isTeleportable;
        map._isEscapable = this._isEscapable;
        map._isUseResurrection = this._isUseResurrection;
        map._isUsePainwand = this._isUsePainwand;
        map._isEnabledDeathPenalty = this._isEnabledDeathPenalty;
        map._isTakePets = this._isTakePets;
        map._isRecallPets = this._isRecallPets;
        map._isUsableItem = this._isUsableItem;
        map._isUsableSkill = this._isUsableSkill;
        map._isGuaji = this._isGuaji;
        map._isdropitem = this._isdropitem;
        return map;
    }
    
    @Override
    public void setPassable(int x, int y, boolean isPassable) {
    }
}
