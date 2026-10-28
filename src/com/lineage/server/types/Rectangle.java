package com.lineage.server.types;

public class Rectangle
{
    private int _left;
    private int _top;
    private int _right;
    private int _bottom;
    
    public Rectangle(Rectangle rect) {
        this.set(rect);
    }
    
    public Rectangle(int left, int top, int right, int bottom) {
        this.set(left, top, right, bottom);
    }
    
    public Rectangle() {
        this(0, 0, 0, 0);
    }
    
    public void set(Rectangle rect) {
        this.set(rect.getLeft(), rect.getTop(), rect.getWidth(), rect.getHeight());
    }
    
    public void set(int left, int top, int right, int bottom) {
        this._left = left;
        this._top = top;
        this._right = right;
        this._bottom = bottom;
    }
    
    public int getLeft() {
        return this._left;
    }
    
    public int getTop() {
        return this._top;
    }
    
    public int getRight() {
        return this._right;
    }
    
    public int getBottom() {
        return this._bottom;
    }
    
    public int getWidth() {
        return this._right - this._left;
    }
    
    public int getHeight() {
        return this._bottom - this._top;
    }
    
    public boolean contains(int x, int y) {
        return this._left <= x && x <= this._right && this._top <= y && y <= this._bottom;
    }
    
    public boolean contains(Point pt) {
        return this.contains(pt.getX(), pt.getY());
    }
}
