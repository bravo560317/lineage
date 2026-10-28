package com.lineage.data.event.gambling;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class Gambling extends GamblingConfig
{
    private GamblingNpc _onenpc;
    private long _adena;
    private long _upadena;
    public int WIN;
    private final Map<Integer, GamblingNpc> _npcidMap;
    private Random _random;
    
    public Gambling() {
        this._npcidMap = new HashMap();
        this._random = new Random();
    }
    
    public void clear() {
        this._npcidMap.clear();
        this._onenpc = null;
        this._adena = 0L;
    }
    
    public long get_allAdena() {
        return this._adena;
    }
    
    public void set_gmaNpc(long previous) {
        if (GamblingConfig.ISGFX) {
            GamblingConfig.ISGFX = false;
        }
        else {
            GamblingConfig.ISGFX = true;
        }
        this.WIN = this._random.nextInt(5);
        int i = 0;
        while (this._npcidMap.size() < 5) {
            int npcid = Gambling.NPCID[this._random.nextInt(Gambling.NPCID.length)];
            if (this._npcidMap.get(new Integer(npcid)) == null) {
                GamblingNpc gamnpc = new GamblingNpc(this);
                gamnpc.showNpc(npcid, i++);
                this._npcidMap.put(new Integer(npcid), gamnpc);
            }
        }
        this._upadena = previous;
    }
    
    public long get_allRate() {
        long adena = 0L;
        for (GamblingNpc gamblingNpc : this._npcidMap.values()) {
            adena += gamblingNpc.get_adena();
        }
        return adena + this._upadena;
    }
    
    public void set_allRate() {
        long adena = this._upadena;
        for (GamblingNpc gamblingNpc : this._npcidMap.values()) {
            adena += gamblingNpc.get_adena();
        }
        this._adena = adena;
        for (GamblingNpc gamblingNpc : this._npcidMap.values()) {
            this.set_npcRate(gamblingNpc, adena);
        }
    }
    
    private void set_npcRate(GamblingNpc npc, long adena) {
        double rate = (double)(10 + this._random.nextInt(40));
        npc.set_rate(rate / 10.0);
    }
    
    public Map<Integer, GamblingNpc> get_allNpc() {
        return this._npcidMap;
    }
    
    public GamblingNpc get_oneNpc() {
        return this._onenpc;
    }
    
    public void set_oneNpc(GamblingNpc onenpc) {
        this._onenpc = onenpc;
    }
    
    public void startGam() {
        for (GamblingNpc gamblingNpc : this._npcidMap.values()) {
            gamblingNpc.getStart();
        }
    }
    
    public void delAllNpc() {
        for (GamblingNpc gamblingNpc : this._npcidMap.values()) {
            gamblingNpc.delNpc();
        }
    }
}
