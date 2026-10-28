package com.lineage.server.templates;

import java.io.Serializable;

public abstract class L1Item implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int _type2;
    private int _itemId;
    private String _name;
    private String _classname;
    private String _nameId;
    private int _type;
    private int _type1;
    private int _material;
    private int _weight;
    private int _gfxId;
    private int _groundGfxId;
    private int _minLevel;
    private int _itemDescId;
    private int _maxLevel;
    private int _bless;
    private boolean _tradable;
    private boolean _cantDelete;
    private boolean _save_at_once;
    private int _maxUseTime;
    private int _foodVolume;
    private int _useType;
    private int _dmgSmall;
    private int _dmgLarge;
    private int[] _mode;
    private int _safeEnchant;
    private boolean _useRoyal;
    private boolean _useKnight;
    private boolean _useElf;
    private boolean _useMage;
    private boolean _useDarkelf;
    private boolean _useDragonknight;
    private boolean _useIllusionist;
    private byte _addstr;
    private byte _adddex;
    private byte _addcon;
    private byte _addint;
    private byte _addwis;
    private byte _addcha;
    private int _addhp;
    private int _addattack;
    private int _addbowattack;
    private int _addmp;
    private int _addhpr;
    private int _addmpr;
    private int _addsp;
    private int _mdef;
    private boolean _isHasteItem;
    private int _delay_effect;
    private int _expPoint;
    private int _minlogpcpower;
    private int _maxlogpcpower;
    private int _PVPdmg;
    private int _PVPdmgReduction;
    private int _weaponskillpro;
    private int _weaponskilldmg;
    private int _penetrate;
    private int _NoweaponRedmg;
    private int _addStunLevel;
    private boolean _dropcolor;
    private boolean _givebless;
    private int _charcount;
    private int _acccount;
    private int _newbless;
    private int _adenapoint;
    
    public L1Item() {
        this._maxUseTime = 0;
        this._dmgSmall = 0;
        this._dmgLarge = 0;
        this._mode = null;
        this._safeEnchant = 0;
        this._useRoyal = false;
        this._useKnight = false;
        this._useElf = false;
        this._useMage = false;
        this._useDarkelf = false;
        this._useDragonknight = false;
        this._useIllusionist = false;
        this._addstr = 0;
        this._adddex = 0;
        this._addcon = 0;
        this._addint = 0;
        this._addwis = 0;
        this._addcha = 0;
        this._addhp = 0;
        this._addattack = 0;
        this._addbowattack = 0;
        this._addmp = 0;
        this._addhpr = 0;
        this._addmpr = 0;
        this._addsp = 0;
        this._mdef = 0;
        this._isHasteItem = false;
        this._dropcolor = false;
        this._givebless = false;
    }
    
    public int getType2() {
        return this._type2;
    }
    
    public void setType2(int type) {
        this._type2 = type;
    }
    
    public int getItemId() {
        return this._itemId;
    }
    
    public void setItemId(int itemId) {
        this._itemId = itemId;
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(String name) {
        this._name = name;
    }
    
    public String getclassname() {
        return this._classname;
    }
    
    public void setClassname(String classname) {
        this._classname = classname;
    }
    
    public String getNameId() {
        return this._nameId;
    }
    
    public void setNameId(String nameid) {
        this._nameId = nameid;
    }
    
    public int getType() {
        return this._type;
    }
    
    public void setType(int type) {
        this._type = type;
    }
    
    public int getType1() {
        return this._type1;
    }
    
    public void setType1(int type1) {
        this._type1 = type1;
    }
    
    public int getMaterial() {
        return this._material;
    }
    
    public void setMaterial(int material) {
        this._material = material;
    }
    
    public int getWeight() {
        return this._weight;
    }
    
    public void setWeight(int weight) {
        this._weight = weight;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    public void setGfxId(int gfxId) {
        this._gfxId = gfxId;
    }
    
    public int getGroundGfxId() {
        return this._groundGfxId;
    }
    
    public void setGroundGfxId(int groundGfxId) {
        this._groundGfxId = groundGfxId;
    }
    
    public int getItemDescId() {
        return this._itemDescId;
    }
    
    public void setItemDescId(int descId) {
        this._itemDescId = descId;
    }
    
    public int getMinLevel() {
        return this._minLevel;
    }
    
    public void setMinLevel(int level) {
        this._minLevel = level;
    }
    
    public int getMaxLevel() {
        return this._maxLevel;
    }
    
    public void setMaxLevel(int maxlvl) {
        this._maxLevel = maxlvl;
    }
    
    public int getBless() {
        return this._bless;
    }
    
    public void setBless(int i) {
        this._bless = i;
    }
    
    public boolean isTradable() {
        return this._tradable;
    }
    
    public void setTradable(boolean flag) {
        this._tradable = flag;
    }
    
    public boolean isCantDelete() {
        return this._cantDelete;
    }
    
    public void setCantDelete(boolean flag) {
        this._cantDelete = flag;
    }
    
    public boolean isToBeSavedAtOnce() {
        return this._save_at_once;
    }
    
    public void setToBeSavedAtOnce(boolean flag) {
        this._save_at_once = flag;
    }
    
    public int getMaxUseTime() {
        return this._maxUseTime;
    }
    
    public void setMaxUseTime(int i) {
        this._maxUseTime = i;
    }
    
    public int getFoodVolume() {
        return this._foodVolume;
    }
    
    public void setFoodVolume(int volume) {
        this._foodVolume = volume;
    }
    
    public int getLightRange() {
        int light = 0;
        switch (this._itemId) {
            case 40001: {
                light = 11;
                break;
            }
            case 40002: {
                light = 14;
                break;
            }
            case 40004: {
                light = 22;
                break;
            }
            case 40005: {
                light = 8;
                break;
            }
        }
        return light;
    }
    
    public int getLightFuel() {
        int time = 0;
        switch (this._itemId) {
            case 40001: {
                time = 6000;
                break;
            }
            case 40002: {
                time = 12000;
                break;
            }
            case 40003: {
                time = 12000;
                break;
            }
            case 40004: {
                time = 0;
                break;
            }
            case 40005: {
                time = 600;
                break;
            }
        }
        return time;
    }
    
    public int getUseType() {
        return this._useType;
    }
    
    public void setUseType(int useType) {
        this._useType = useType;
    }
    
    public int getDmgSmall() {
        return this._dmgSmall;
    }
    
    public void setDmgSmall(int dmgSmall) {
        this._dmgSmall = dmgSmall;
    }
    
    public int getDmgLarge() {
        return this._dmgLarge;
    }
    
    public void setDmgLarge(int dmgLarge) {
        this._dmgLarge = dmgLarge;
    }
    
    public int[] get_mode() {
        return this._mode;
    }
    
    public void set_mode(int[] mode) {
        this._mode = mode;
    }
    
    public int get_safeenchant() {
        return this._safeEnchant;
    }
    
    public void set_safeenchant(int safeenchant) {
        this._safeEnchant = safeenchant;
    }
    
    public boolean isUseRoyal() {
        return this._useRoyal;
    }
    
    public void setUseRoyal(boolean flag) {
        this._useRoyal = flag;
    }
    
    public boolean isUseKnight() {
        return this._useKnight;
    }
    
    public void setUseKnight(boolean flag) {
        this._useKnight = flag;
    }
    
    public boolean isUseElf() {
        return this._useElf;
    }
    
    public void setUseElf(boolean flag) {
        this._useElf = flag;
    }
    
    public boolean isUseMage() {
        return this._useMage;
    }
    
    public void setUseMage(boolean flag) {
        this._useMage = flag;
    }
    
    public boolean isUseDarkelf() {
        return this._useDarkelf;
    }
    
    public void setUseDarkelf(boolean flag) {
        this._useDarkelf = flag;
    }
    
    public boolean isUseDragonknight() {
        return this._useDragonknight;
    }
    
    public void setUseDragonknight(boolean flag) {
        this._useDragonknight = flag;
    }
    
    public boolean isUseIllusionist() {
        return this._useIllusionist;
    }
    
    public void setUseIllusionist(boolean flag) {
        this._useIllusionist = flag;
    }
    
    public byte get_addstr() {
        return this._addstr;
    }
    
    public void set_addstr(byte addstr) {
        this._addstr = addstr;
    }
    
    public byte get_adddex() {
        return this._adddex;
    }
    
    public void set_adddex(byte adddex) {
        this._adddex = adddex;
    }
    
    public byte get_addcon() {
        return this._addcon;
    }
    
    public void set_addcon(byte addcon) {
        this._addcon = addcon;
    }
    
    public byte get_addint() {
        return this._addint;
    }
    
    public void set_addint(byte addint) {
        this._addint = addint;
    }
    
    public byte get_addwis() {
        return this._addwis;
    }
    
    public void set_addwis(byte addwis) {
        this._addwis = addwis;
    }
    
    public byte get_addcha() {
        return this._addcha;
    }
    
    public void set_addcha(byte addcha) {
        this._addcha = addcha;
    }
    
    public int get_addhp() {
        return this._addhp;
    }
    
    public void set_addhp(int addhp) {
        this._addhp = addhp;
    }
    
    public int get_addattack() {
        return this._addattack;
    }
    
    public void set_addattack(int addattack) {
        this._addattack = addattack;
    }
    
    public int get_addbowattack() {
        return this._addbowattack;
    }
    
    public void set_addbowattack(int addbowattack) {
        this._addbowattack = addbowattack;
    }
    
    public int get_addmp() {
        return this._addmp;
    }
    
    public void set_addmp(int addmp) {
        this._addmp = addmp;
    }
    
    public int get_addhpr() {
        return this._addhpr;
    }
    
    public void set_addhpr(int addhpr) {
        this._addhpr = addhpr;
    }
    
    public int get_addmpr() {
        return this._addmpr;
    }
    
    public void set_addmpr(int addmpr) {
        this._addmpr = addmpr;
    }
    
    public int get_addsp() {
        return this._addsp;
    }
    
    public void set_addsp(int addsp) {
        this._addsp = addsp;
    }
    
    public int get_mdef() {
        return this._mdef;
    }
    
    public void set_mdef(int i) {
        this._mdef = i;
    }
    
    public boolean isHasteItem() {
        return this._isHasteItem;
    }
    
    public void setHasteItem(boolean flag) {
        this._isHasteItem = flag;
    }
    
    public boolean isStackable() {
        return false;
    }
    
    public int get_delayid() {
        return 0;
    }
    
    public int get_delaytime() {
        return 0;
    }
    
    public int getMaxChargeCount() {
        return 0;
    }
    
    public void set_delayEffect(int delay_effect) {
        this._delay_effect = delay_effect;
    }
    
    public int get_delayEffect() {
        return this._delay_effect;
    }
    
    public int get_add_dmg() {
        return 0;
    }
    
    public int getRange() {
        return 0;
    }
    
    public int getHitModifier() {
        return 0;
    }
    
    public int getDmgModifier() {
        return 0;
    }
    
    public int getDoubleDmgChance() {
        return 0;
    }
    
    public int get_canbedmg() {
        return 0;
    }
    
    public boolean isTwohandedWeapon() {
        return false;
    }
    
    public int get_ac() {
        return 0;
    }
    
    public int getDamageReduction() {
        return 0;
    }
    
    public int getWeightReduction() {
        return 0;
    }
    
    public int getHitModifierByArmor() {
        return 0;
    }
    
    public int getDmgModifierByArmor() {
        return 0;
    }
    
    public int getBowHitModifierByArmor() {
        return 0;
    }
    
    public int getBowDmgModifierByArmor() {
        return 0;
    }
    
    public int getMagicHitModifierByArmor() {
        return 0;
    }
    
    public int getMagicDmgModifier() {
        return 0;
    }
    
    public int get_defense_water() {
        return 0;
    }
    
    public int get_defense_fire() {
        return 0;
    }
    
    public int get_defense_earth() {
        return 0;
    }
    
    public int get_defense_wind() {
        return 0;
    }
    
    public int get_regist_stun() {
        return 0;
    }
    
    public int get_regist_stone() {
        return 0;
    }
    
    public int get_regist_sleep() {
        return 0;
    }
    
    public int get_regist_freeze() {
        return 0;
    }
    
    public int get_regist_sustain() {
        return 0;
    }
    
    public int get_regist_blind() {
        return 0;
    }
    
    public int get_greater() {
        return 3;
    }
    
    public int getExpPoint() {
        return this._expPoint;
    }
    
    public void setExpPoint(int i) {
        this._expPoint = i;
    }
    
    public int get_up_hp_potion() {
        return 0;
    }
    
    public int get_uhp_number() {
        return 0;
    }
    
    public int getminlogpcpower() {
        return this._minlogpcpower;
    }
    
    public void setminlogpcpower(int i) {
        this._minlogpcpower = i;
    }
    
    public int getmaxlogpcpower() {
        return this._maxlogpcpower;
    }
    
    public void setmaxlogpcpower(int i) {
        this._maxlogpcpower = i;
    }
    
    public int getPVPdmg() {
        return this._PVPdmg;
    }
    
    public void setPVPdmg(int i) {
        this._PVPdmg = i;
    }
    
    public int getPVPdmgReduction() {
        return this._PVPdmgReduction;
    }
    
    public void setPVPdmgReduction(int i) {
        this._PVPdmgReduction = i;
    }
    
    public int getweaponskillpro() {
        return this._weaponskillpro;
    }
    
    public void setweaponskillpro(int i) {
        this._weaponskillpro = i;
    }
    
    public void addweaponskillpro(int i) {
        this._weaponskillpro += i;
    }
    
    public int getweaponskilldmg() {
        return this._weaponskilldmg;
    }
    
    public void setweaponskilldmg(int i) {
        this._weaponskilldmg = i;
    }
    
    public void addweaponskilldmg(int i) {
        this._weaponskilldmg += i;
    }
    
    public int getpenetrate() {
        return this._penetrate;
    }
    
    public void setpenetrate(int i) {
        this._penetrate = i;
    }
    
    public int getNoweaponRedmg() {
        return this._NoweaponRedmg;
    }
    
    public void setNoweaponRedmg(int i) {
        this._NoweaponRedmg = i;
    }
    
    public int getaddStunLevel() {
        return this._addStunLevel;
    }
    
    public void setaddStunLevel(int i) {
        this._addStunLevel = i;
    }
    
    public boolean isdropcolor() {
        return this._dropcolor;
    }
    
    public void setdropcolor(boolean flag) {
        this._dropcolor = flag;
    }
    
    public boolean isgivebless() {
        return this._givebless;
    }
    
    public void setgivebless(boolean flag) {
        this._givebless = flag;
    }
    
    public int getcharcount() {
        return this._charcount;
    }
    
    public void setcharcount(int i) {
        this._charcount = i;
    }
    
    public int getacccount() {
        return this._acccount;
    }
    
    public void setacccount(int i) {
        this._acccount = i;
    }
    
    public int getnewbless() {
        return this._newbless;
    }
    
    public void setnewbless(int i) {
        this._newbless = i;
    }
    
    public int getadenapoint() {
        return this._adenapoint;
    }
    
    public void setadenapoint(int i) {
        this._adenapoint = i;
    }
    
    public void addadenapoint(int i) {
        this._adenapoint += i;
    }
}
