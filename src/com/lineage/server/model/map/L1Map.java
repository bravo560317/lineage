package com.lineage.server.model.map;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.types.Point;

public abstract class L1Map
{
    private static L1NullMap _nullMap;
    private static final int[] NUMBER;
    
    static {
        L1Map._nullMap = new L1NullMap();
        NUMBER = new int[] { 0, 12, 16, 24, 28, 32, 44 };
    }
    
    public abstract L1V1Map copyMap(int p0);
    
    protected L1Map() {
    }
    
    public abstract int getId();
    
    public abstract int getX();
    
    public abstract int getY();
    
    public abstract int getWidth();
    
    public abstract int getHeight();
    
    public abstract int getTile(int p0, int p1);
    
    public boolean isOriginalTile(int x, int y) {
        int number = this.getOriginalTile(x, y);
        int[] number2;
        int length = (number2 = L1Map.NUMBER).length;
        int j = 0;
        while (j < length) {
            int i = number2[j];
            if (number == i) {
                return false;
            }
            else {
                ++j;
            }
        }
        return true;
    }
    
    public abstract int getOriginalTile(int p0, int p1);
    
    public abstract boolean isInMap(Point p0);
    
    public abstract boolean isInMap(int p0, int p1);
    
    public abstract boolean isPassable(Point p0, L1Character p1);
    
    public abstract boolean isPassable(int p0, int p1, L1Character p2);
    
    public abstract boolean isPassable(Point p0, int p1, L1Character p2);
    
    public abstract boolean isPassable(int p0, int p1, int p2, L1Character p3);
    
    public abstract boolean isPassableDna(int p0, int p1, int p2);
    
    public abstract boolean isDoorPassable(int p0, int p1, int p2, L1NpcInstance p3);
    
    public abstract void setPassable(Point p0, boolean p1);
    
    public abstract void setPassable(int p0, int p1, boolean p2, int p3);
    
    public abstract void setPassable(int p0, int p1, boolean p2);
    
    public abstract boolean isPassable(int p0, int p1);
    
    public abstract boolean isSafetyZone(Point p0);
    
    public abstract boolean isSafetyZone(int p0, int p1);
    
    public abstract boolean isCombatZone(Point p0);
    
    public abstract boolean isCombatZone(int p0, int p1);
    
    public abstract boolean isNormalZone(Point p0);
    
    public abstract boolean isNormalZone(int p0, int p1);
    
    public abstract boolean isArrowPassable(Point p0);
    
    public abstract boolean isArrowPassable(int p0, int p1);
    
    public abstract boolean isArrowPassable(Point p0, int p1);
    
    public abstract boolean isArrowPassable(int p0, int p1, int p2);
    
    public abstract boolean isUnderwater();
    
    public abstract boolean isMarkable();
    
    public abstract boolean isTeleportable();
    
    public abstract boolean isEscapable();
    
    public abstract boolean isUseResurrection();
    
    public abstract boolean isUsePainwand();
    
    public abstract boolean isEnabledDeathPenalty();
    
    public abstract boolean isTakePets();
    
    public abstract boolean isRecallPets();
    
    public abstract boolean isUsableItem();
    
    public abstract boolean isUsableSkill();
    
    public abstract boolean isGuaji();
    
    public abstract int isUsableShop();
    
    public abstract int isStart_time();
    
    public abstract int isEnd_time();
    
    public abstract int isStart_time2();
    
    public abstract int isEnd_time2();
    
    public abstract boolean isdropitem();
    
    public abstract int isweek();
    
    public abstract boolean isClanPc();
    
    public abstract boolean isPartyPc();
    
    public abstract boolean isFishingZone(int p0, int p1);
    
    public abstract int isExistDoor(int p0, int p1);
    
    public static L1Map newNull() {
        return L1Map._nullMap;
    }
    
    public abstract String toString(Point p0);
    
    public boolean isNull() {
        return false;
    }
    
    public abstract boolean isPassable(int p0, int p1, int p2);
    
    public abstract void setTestTile(int p0, int p1, int p2);
}
