package com.lineage.server.templates;

public class L1NewMap
{
    public static final int MOVE = 1;
    public static final int SAFE_ZONE = 4;
    public static final int FIGHT_ZONE = 8;
    public static final int JUSTICE_SHRINE = 32;
    public static final int EVIL_SHRINE = 64;
    private final int x;
    private final int y;
    private final byte[] data;
    
    public L1NewMap(int x, int y, byte[] data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }
    
    public final int getTileType(int x, int y) {
        if (y >= 64 || x >= 64 || x < 0 || y < 0) {
            return 0;
        }
        else {
            return this.data[y * 64 + x] & 0xFF;
        }
    }
    
    public final boolean getTileType(int x, int y, int zone) {
        return y < 64 && x < 64 && x >= 0 && y >= 0 && (this.data[y * 64 + x] & 0xFF & zone) == zone;
    }
    
    public final int getX() {
        return this.x;
    }
    
    public final int getY() {
        return this.y;
    }
}
