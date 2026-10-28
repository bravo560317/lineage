package com.lineage.server.utils;

import java.util.Random;

public class RangeInt
{
    private static final Random _rnd;
    private int _low;
    private int _high;
    
    static {
        _rnd = new Random();
    }
    
    public RangeInt(int low, int high) {
        this._low = low;
        this._high = high;
    }
    
    public RangeInt(RangeInt range) {
        this(range._low, range._high);
    }
    
    public boolean includes(int i) {
        return this._low <= i && i <= this._high;
    }
    
    public static boolean includes(int i, int low, int high) {
        return low <= i && i <= high;
    }
    
    public int ensure(int i) {
        int r = i;
        r = ((this._low <= r) ? r : this._low);
        r = ((r <= this._high) ? r : this._high);
        return r;
    }
    
    public static int ensure(int n, int low, int high) {
        int r = n;
        r = ((low <= r) ? r : low);
        r = ((r <= high) ? r : high);
        return r;
    }
    
    public int randomValue() {
        return RangeInt._rnd.nextInt(this.getWidth() + 1) + this._low;
    }
    
    public int getLow() {
        return this._low;
    }
    
    public int getHigh() {
        return this._high;
    }
    
    public int getWidth() {
        return this._high - this._low;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RangeInt)) {
            return false;
        }
        else {
            RangeInt range = (RangeInt)obj;
            return this._low == range._low && this._high == range._high;
        }
    }
    
    @Override
    public String toString() {
        return "low=" + this._low + ", high=" + this._high;
    }
}
