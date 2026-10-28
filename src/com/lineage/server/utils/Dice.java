package com.lineage.server.utils;

import java.util.Random;

public class Dice
{
    private static final Random _rnd;
    private final int _faces;
    
    static {
        _rnd = new Random();
    }
    
    public Dice(int faces) {
        this._faces = faces;
    }
    
    public int getFaces() {
        return this._faces;
    }
    
    public int roll() {
        return Dice._rnd.nextInt(this._faces) + 1;
    }
    
    public int roll(int count) {
        int n = 0;
        for (int i = 0; i < count; ++i) {
            n += this.roll();
        }
        return n;
    }
}
