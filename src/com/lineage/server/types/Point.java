package com.lineage.server.types;

public class Point
{
    protected int _x;
    protected int _y;
    private static final int[] HEADING_TABLE_X;
    private static final int[] HEADING_TABLE_Y;
    
    static {
        HEADING_TABLE_X = new int[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new int[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    public Point() {
        this._x = 0;
        this._y = 0;
    }
    
    public Point(int x, int y) {
        this._x = 0;
        this._y = 0;
        this._x = x;
        this._y = y;
    }
    
    public Point(Point pt) {
        this._x = 0;
        this._y = 0;
        this._x = pt._x;
        this._y = pt._y;
    }
    
    public int getX() {
        return this._x;
    }
    
    public void setX(int x) {
        this._x = x;
    }
    
    public int getY() {
        return this._y;
    }
    
    public void setY(int y) {
        this._y = y;
    }
    
    public void set(Point pt) {
        this._x = pt._x;
        this._y = pt._y;
    }
    
    public void set(int x, int y) {
        this._x = x;
        this._y = y;
    }
    
    public void forward(int heading) {
        this._x += Point.HEADING_TABLE_X[heading];
        this._y += Point.HEADING_TABLE_Y[heading];
    }
    
    public void backward(int heading) {
        this._x -= Point.HEADING_TABLE_X[heading];
        this._y -= Point.HEADING_TABLE_Y[heading];
    }
    
    public double getLineDistance(Point pt) {
        long diffX = (long)(pt.getX() - this.getX());
        long diffY = (long)(pt.getY() - this.getY());
        return Math.sqrt((double)(diffX * diffX + diffY * diffY));
    }
    
    public double getLineDistance(int x, int y) {
        long diffX = (long)(x - this.getX());
        long diffY = (long)(y - this.getY());
        return Math.sqrt((double)(diffX * diffX + diffY * diffY));
    }
    
    public int getTileLineDistance(Point pt) {
        return Math.max(Math.abs(pt.getX() - this.getX()), Math.abs(pt.getY() - this.getY()));
    }
    
    public int getTileDistance(Point pt) {
        return Math.abs(pt.getX() - this.getX()) + Math.abs(pt.getY() - this.getY());
    }
    
    public boolean isInScreen(Point pt) {
        int dist = this.getTileDistance(pt);
        if (dist > 19) {
            return false;
        }
        else if (dist <= 18) {
            return true;
        }
        else {
            int dist2 = Math.abs(pt.getX() - (this.getX() - 18)) + Math.abs(pt.getY() - (this.getY() - 18));
            return 19 <= dist2 && dist2 <= 52;
        }
    }
    
    public boolean isSamePoint(Point pt) {
        return pt.getX() == this.getX() && pt.getY() == this.getY();
    }
    
    @Override
    public int hashCode() {
        return 7 * this.getX() + this.getY();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        else {
            Point pt = (Point)obj;
            return this.getX() == pt.getX() && this.getY() == pt.getY();
        }
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", (Integer)this._x, (Integer)this._y);
    }
}
