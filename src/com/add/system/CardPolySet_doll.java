package com.add.system;

public class CardPolySet_doll
{
    private int _id;
    private String _msg1;
    private int[] _needids;
    private int[] _needquest;
    private String[] _needname;
    private int _questid;
    private int _addstr;
    private int _adddex;
    private int _addcon;
    private int _addint;
    private int _addwis;
    private int _addcha;
    private int _ac;
    private int _addhp;
    private int _addmp;
    private int _addhpr;
    private int _addmpr;
    private int _adddmg;
    private int _addbowdmg;
    private int _addhit;
    private int _addbowhit;
    private int _adddmgr;
    private int _addmdmgr;
    private int _addsp;
    private int _addmhit;
    private int _addmr;
    private int _addfire;
    private int _addwater;
    private int _addwind;
    private int _addearth;
    
    public int getId() {
        return this._id;
    }
    
    public String getMsg1() {
        return this._msg1;
    }
    
    public final int[] getNeedids() {
        return this._needids;
    }
    
    public final int[] getNeedQuest() {
        return this._needquest;
    }
    
    public final String[] getNeedName() {
        return this._needname;
    }
    
    public int getQuestId() {
        return this._questid;
    }
    
    public int getAddStr() {
        return this._addstr;
    }
    
    public int getAddDex() {
        return this._adddex;
    }
    
    public int getAddCon() {
        return this._addcon;
    }
    
    public int getAddInt() {
        return this._addint;
    }
    
    public int getAddWis() {
        return this._addwis;
    }
    
    public int getAddCha() {
        return this._addcha;
    }
    
    public int getAddAc() {
        return this._ac;
    }
    
    public int getAddHp() {
        return this._addhp;
    }
    
    public int getAddMp() {
        return this._addmp;
    }
    
    public int getAddHpr() {
        return this._addhpr;
    }
    
    public int getAddMpr() {
        return this._addmpr;
    }
    
    public int getAddDmg() {
        return this._adddmg;
    }
    
    public int getAddBowDmg() {
        return this._addbowdmg;
    }
    
    public int getAddHit() {
        return this._addhit;
    }
    
    public int getAddBowHit() {
        return this._addbowhit;
    }
    
    public int getAddDmgR() {
        return this._adddmgr;
    }
    
    public int getAddMagicDmgR() {
        return this._addmdmgr;
    }
    
    public int getAddSp() {
        return this._addsp;
    }
    
    public int getAddMagicHit() {
        return this._addmhit;
    }
    
    public int getAddMr() {
        return this._addmr;
    }
    
    public int getAddFire() {
        return this._addfire;
    }
    
    public int getAddWater() {
        return this._addwater;
    }
    
    public int getAddWind() {
        return this._addwind;
    }
    
    public int getAddEarth() {
        return this._addearth;
    }
    
    public CardPolySet_doll(int Id, String msg1, int[] needids, int[] needquest, String[] needname, int QuestId, int AddStr, int AddDex, int AddCon, int AddInt, int AddWis, int AddCha, int Ac, int AddHp, int AddMp, int AddHpr, int AddMpr, int Dmg, int BowDmg, int Hit, int BowHit, int DmgR, int MagicDmgR, int Sp, int MagicHit, int Mr, int Fire, int Water, int Wind, int Earth) {
        this._id = Id;
        this._msg1 = msg1;
        this._needids = needids;
        this._needquest = needquest;
        this._needname = needname;
        this._questid = QuestId;
        this._addstr = AddStr;
        this._adddex = AddDex;
        this._addcon = AddCon;
        this._addint = AddInt;
        this._addwis = AddWis;
        this._addcha = AddCha;
        this._ac = Ac;
        this._addhp = AddHp;
        this._addmp = AddMp;
        this._addhpr = AddHpr;
        this._addmpr = AddMpr;
        this._adddmg = Dmg;
        this._addbowdmg = BowDmg;
        this._addhit = Hit;
        this._addbowhit = BowHit;
        this._adddmgr = DmgR;
        this._addmdmgr = MagicDmgR;
        this._addsp = Sp;
        this._addmhit = MagicHit;
        this._addmr = Mr;
        this._addfire = Fire;
        this._addwater = Water;
        this._addwind = Wind;
        this._addearth = Earth;
    }
}
