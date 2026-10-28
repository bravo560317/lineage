package com.lineage.server.templates;

import com.lineage.server.datatables.sql.L1MonTable;

public class L1Mon
{
    private int _npcmid;
    private int _dorpid;
    private int _dorplostid;
    private int _x;
    private int _y;
    private int _map;
    private int _x1;
    private int _y1;
    private int _map1;
    private int _pcdmg;
    
    public int getNpcmid() {
        return this._npcmid;
    }
    
    public void setNpcmid(int Npcmid) {
        this._npcmid = Npcmid;
    }
    
    public int getDorpid() {
        return this._dorpid;
    }
    
    public void setDorpid(int Dorpid) {
        this._dorpid = Dorpid;
    }
    
    public int getDorplostid() {
        return this._dorplostid;
    }
    
    public void setDorplostid(int Dorplostid) {
        this._dorplostid = Dorplostid;
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
    
    public int getMap() {
        return this._map;
    }
    
    public void setMap(int map) {
        this._map = map;
    }
    
    public int getX1() {
        return this._x1;
    }
    
    public void setX1(int x) {
        this._x1 = x;
    }
    
    public int getY1() {
        return this._y1;
    }
    
    public void setY1(int y) {
        this._y1 = y;
    }
    
    public int getMap1() {
        return this._map1;
    }
    
    public void setMap1(int map) {
        this._map1 = map;
    }
    
    public static int CheckNpcMid(int Npc_Mid) {
        L1Mon mon = L1MonTable.get().getMon(Npc_Mid);
        if (mon == null) {
            return 0;
        }
        else {
            int npcmid = mon._npcmid;
            return npcmid;
        }
    }
    
    public int getpcdmg() {
        return this._pcdmg;
    }
    
    public void setpcdmg(int pcdmg) {
        this._pcdmg = pcdmg;
    }
}
