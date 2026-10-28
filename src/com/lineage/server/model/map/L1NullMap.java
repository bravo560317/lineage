package com.lineage.server.model.map;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.types.Point;

class L1NullMap extends L1Map
{
    public L1NullMap() {
    }
    
    @Override
    public int getId() {
        return 0;
    }
    
    @Override
    public int getX() {
        return 0;
    }
    
    @Override
    public int getY() {
        return 0;
    }
    
    @Override
    public int getWidth() {
        return 0;
    }
    
    @Override
    public int getHeight() {
        return 0;
    }
    
    @Override
    public int getTile(int x, int y) {
        return 0;
    }
    
    @Override
    public int getOriginalTile(int x, int y) {
        return 0;
    }
    
    @Override
    public boolean isInMap(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isInMap(Point pt) {
        return false;
    }
    
    @Override
    public void setPassable(int x, int y, boolean isPassable) {
    }
    
    @Override
    public boolean isPassable(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isPassable(int x, int y, int heading) {
        return false;
    }
    
    @Override
    public boolean isPassable(int x, int y, L1Character cha) {
        return false;
    }
    
    @Override
    public boolean isPassable(Point pt, L1Character cha) {
        return false;
    }
    
    @Override
    public boolean isPassable(int x, int y, int heading, L1Character cha) {
        return false;
    }
    
    @Override
    public boolean isPassableDna(int x, int y, int heading) {
        return false;
    }
    
    @Override
    public boolean isPassable(Point pt, int heading, L1Character cha) {
        return false;
    }
    
    @Override
    public boolean isDoorPassable(int x, int y, int heading, L1NpcInstance npc) {
        return false;
    }
    
    @Override
    public void setPassable(int x, int y, boolean isPassable, int door) {
    }
    
    @Override
    public void setPassable(Point pt, boolean isPassable) {
    }
    
    @Override
    public boolean isSafetyZone(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isSafetyZone(Point pt) {
        return false;
    }
    
    @Override
    public boolean isCombatZone(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isCombatZone(Point pt) {
        return false;
    }
    
    @Override
    public boolean isNormalZone(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isNormalZone(Point pt) {
        return false;
    }
    
    @Override
    public boolean isArrowPassable(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isArrowPassable(Point pt) {
        return false;
    }
    
    @Override
    public boolean isArrowPassable(int x, int y, int heading) {
        return false;
    }
    
    @Override
    public boolean isArrowPassable(Point pt, int heading) {
        return false;
    }
    
    @Override
    public boolean isUnderwater() {
        return false;
    }
    
    @Override
    public boolean isMarkable() {
        return false;
    }
    
    @Override
    public boolean isTeleportable() {
        return false;
    }
    
    @Override
    public boolean isEscapable() {
        return false;
    }
    
    @Override
    public boolean isUseResurrection() {
        return false;
    }
    
    @Override
    public boolean isUsePainwand() {
        return false;
    }
    
    @Override
    public boolean isEnabledDeathPenalty() {
        return false;
    }
    
    @Override
    public boolean isTakePets() {
        return false;
    }
    
    @Override
    public boolean isRecallPets() {
        return false;
    }
    
    @Override
    public boolean isUsableItem() {
        return false;
    }
    
    @Override
    public boolean isUsableSkill() {
        return false;
    }
    
    @Override
    public boolean isGuaji() {
        return false;
    }
    
    @Override
    public boolean isClanPc() {
        return false;
    }
    
    @Override
    public boolean isPartyPc() {
        return false;
    }
    
    @Override
    public int isUsableShop() {
        return 0;
    }
    
    @Override
    public int isStart_time() {
        return 0;
    }
    
    @Override
    public int isweek() {
        return 0;
    }
    
    @Override
    public int isEnd_time() {
        return 0;
    }
    
    @Override
    public int isStart_time2() {
        return 0;
    }
    
    @Override
    public int isEnd_time2() {
        return 0;
    }
    
    @Override
    public boolean isdropitem() {
        return false;
    }
    
    @Override
    public boolean isFishingZone(int x, int y) {
        return false;
    }
    
    @Override
    public int isExistDoor(int x, int y) {
        return 3;
    }
    
    @Override
    public String toString(Point pt) {
        return "null";
    }
    
    @Override
    public L1V1Map copyMap(int paramInt) {
        return null;
    }
    
    @Override
    public boolean isOriginalTile(int x, int y) {
        return false;
    }
    
    @Override
    public boolean isNull() {
        return true;
    }
    
    @Override
    public void setTestTile(int x, int y, int tile) {
    }
}
