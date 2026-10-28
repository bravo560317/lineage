package com.lineage.server.model;

import com.lineage.server.utils.RangeInt;

public class L1Karma
{
    private static final int[] KARMA_POINT;
    private static RangeInt KARMA_RANGE;
    private int _karma;
    
    static {
        KARMA_POINT = new int[] { 10000, 20000, 100000, 500000, 1500000, 3000000, 5000000, 10000000, 15500000 };
        L1Karma.KARMA_RANGE = new RangeInt(-15500000, 15500000);
    }
    
    public L1Karma() {
        this._karma = 0;
    }
    
    public int get() {
        return this._karma;
    }
    
    public void set(int i) {
        this._karma = L1Karma.KARMA_RANGE.ensure(i);
    }
    
    public void add(int i) {
        this.set(this._karma + i);
    }
    
    public int getLevel() {
        boolean isMinus = false;
        int karmaLevel = 0;
        int karma = this.get();
        if (karma < 0) {
            isMinus = true;
            karma *= -1;
        }
        int[] karma_POINT;
        int length = (karma_POINT = L1Karma.KARMA_POINT).length;
        int i = 0;
        while (i < length) {
            int point = karma_POINT[i];
            if (karma < point) {
                break;
            }
            else if (++karmaLevel >= 8) {
                break;
            }
            else {
                ++i;
            }
        }
        if (isMinus) {
            karmaLevel *= -1;
        }
        return karmaLevel;
    }
    
    public int getPercent() {
        int karma = this.get();
        int karmaLevel = this.getLevel();
        if (karmaLevel == 0) {
            return 0;
        }
        else {
            if (karma < 0) {
                karma *= -1;
                karmaLevel *= -1;
            }
            return 100 * (karma - L1Karma.KARMA_POINT[karmaLevel - 1]) / (L1Karma.KARMA_POINT[karmaLevel] - L1Karma.KARMA_POINT[karmaLevel - 1]);
        }
    }
}
