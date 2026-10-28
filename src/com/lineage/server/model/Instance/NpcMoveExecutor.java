package com.lineage.server.model.Instance;

public abstract class NpcMoveExecutor
{
    protected static final byte[] HEADING_TABLE_X;
    protected static final byte[] HEADING_TABLE_Y;
    protected static final byte[] HEADING_TABLE_XR;
    protected static final byte[] HEADING_TABLE_YR;
    protected static final byte[] HEADING_RD;
    protected static final int[][] _ary1;
    protected static final int[] _heading2;
    protected static final int[] _heading3;
    
    static {
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
        HEADING_TABLE_XR = new byte[] { 0, -1, -1, -1, 0, 1, 1, 1 };
        HEADING_TABLE_YR = new byte[] { 1, 1, 0, -1, -1, -1, 0, 1 };
        HEADING_RD = new byte[] { 4, 5, 6, 7, 0, 1, 2, 3 };
        _ary1 = new int[][] { { 7, 1, 0, 6, 2 }, { 7, 3, 1, 0, 2 }, { 3, 1, 0, 4, 2 }, { 5, 3, 1, 4, 2 }, { 5, 3, 6, 4, 2 }, { 7, 5, 3, 6, 4 }, { 7, 5, 0, 6, 4 }, { 7, 5, 1, 0, 6 } };
        _heading2 = new int[] { 7, 0, 1, 2, 3, 4, 5, 6 };
        _heading3 = new int[] { 1, 2, 3, 4, 5, 6, 7, 0 };
    }
    
    public abstract void setDirectionMove(int p0);
    
    public abstract int moveDirection(int p0, int p1);
    
    public abstract int targetReverseDirection(int p0, int p1);
    
    public abstract int checkObject(int p0);
    
    public abstract int openDoor(int p0);
    
    public abstract void clear();
}
