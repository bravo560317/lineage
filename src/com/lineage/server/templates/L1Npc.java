package com.lineage.server.templates;

import org.apache.commons.logging.LogFactory;
import com.lineage.data.executor.NpcExecutor;
import org.apache.commons.logging.Log;
import com.lineage.server.model.L1Object;

public class L1Npc extends L1Object implements Cloneable
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    private int _npcid;
    private String _name;
    private String _impl;
    private int _level;
    private int _hp;
    private int _mp;
    private int _ac;
    private int _str;
    private int _con;
    private int _dex;
    private int _wis;
    private int _int;
    private int _mr;
    private int _exp;
    private int _lawful;
    private String _size;
    private int _weakAttr;
    private int _ranged;
    private boolean _agrososc;
    private boolean _Atkpc;
    private boolean _Atkpc_red;
    private boolean _agrocoi;
    private boolean _tameable;
    private int _passispeed;
    private int _atkspeed;
    private boolean _agro;
    private int _gfxid;
    private String _nameid;
    private int _undead;
    private int _poisonatk;
    private int _paralysisatk;
    private int _family;
    private int _agrofamily;
    private int _agrogfxid1;
    private int _agrogfxid2;
    private boolean _picupitem;
    private int _digestitem;
    private boolean _bravespeed;
    private int _hprinterval;
    private int _hpr;
    private int _mprinterval;
    private int _mpr;
    private boolean _teleport;
    private int _randomlevel;
    private int _randomhp;
    private int _randommp;
    private int _randomac;
    private int _randomexp;
    private int _randomlawful;
    private int _damagereduction;
    private boolean _hard;
    private boolean _doppel;
    private boolean _tu;
    private boolean _erase;
    private int bowActId;
    private int _karma;
    private int _transformId;
    private int _transformGfxId;
    private int _atkMagicSpeed;
    private int _subMagicSpeed;
    private int _lightSize;
    private boolean _amountFixed;
    private boolean _changeHead;
    private boolean _isCantResurrect;
    private String _classname;
    private NpcExecutor _class;
    private boolean _talk;
    private boolean _action;
    private boolean _attack;
    private boolean _death;
    private boolean _work;
    private boolean _spawn;
    private boolean _boss;
    private int _quest_score;
    private int _npcbroad;
    
    static {
        _log = LogFactory.getLog(L1Npc.class);
    }
    
    public L1Npc() {
        this.bowActId = 0;
        this._talk = false;
        this._action = false;
        this._attack = false;
        this._death = false;
        this._work = false;
        this._spawn = false;
        this._boss = false;
    }
    
    public L1Npc clone() {
        try {
            return (L1Npc)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }
    
    public int get_npcId() {
        return this._npcid;
    }
    
    public void set_npcId(int i) {
        this._npcid = i;
    }
    
    public String get_name() {
        return this._name;
    }
    
    public void set_name(String s) {
        this._name = s;
    }
    
    public String getImpl() {
        return this._impl;
    }
    
    public void setImpl(String s) {
        this._impl = s;
    }
    
    public int get_level() {
        return this._level;
    }
    
    public void set_level(int i) {
        this._level = i;
    }
    
    public int get_hp() {
        return this._hp;
    }
    
    public void set_hp(int i) {
        this._hp = i;
    }
    
    public int get_mp() {
        return this._mp;
    }
    
    public void set_mp(int i) {
        this._mp = i;
    }
    
    public int get_ac() {
        return this._ac;
    }
    
    public void set_ac(int i) {
        this._ac = i;
    }
    
    public int get_str() {
        return this._str;
    }
    
    public void set_str(int i) {
        this._str = i;
    }
    
    public int get_con() {
        return this._con;
    }
    
    public void set_con(int i) {
        this._con = i;
    }
    
    public int get_dex() {
        return this._dex;
    }
    
    public void set_dex(int i) {
        this._dex = i;
    }
    
    public int get_wis() {
        return this._wis;
    }
    
    public void set_wis(int i) {
        this._wis = i;
    }
    
    public int get_int() {
        return this._int;
    }
    
    public void set_int(int i) {
        this._int = i;
    }
    
    public int get_mr() {
        return this._mr;
    }
    
    public void set_mr(int i) {
        this._mr = i;
    }
    
    public int get_exp() {
        return this._exp;
    }
    
    public void set_exp(int i) {
        this._exp = i;
    }
    
    public int get_lawful() {
        return this._lawful;
    }
    
    public void set_lawful(int i) {
        this._lawful = i;
    }
    
    public String get_size() {
        return this._size;
    }
    
    public boolean isSmall() {
        return this._size.equalsIgnoreCase("small");
    }
    
    public boolean isLarge() {
        return this._size.equalsIgnoreCase("large");
    }
    
    public void set_size(String s) {
        this._size = s;
    }
    
    public int get_weakAttr() {
        return this._weakAttr;
    }
    
    public void set_weakAttr(int i) {
        this._weakAttr = i;
    }
    
    public int get_ranged() {
        return this._ranged;
    }
    
    public void set_ranged(int i) {
        this._ranged = i;
    }
    
    public boolean is_agrososc() {
        return this._agrososc;
    }
    
    public void set_agrososc(boolean flag) {
        this._agrososc = flag;
    }
    
    public boolean is_Atkpc() {
        return this._Atkpc;
    }
    
    public void set_Atkpc(boolean flag) {
        this._Atkpc = flag;
    }
    
    public boolean is_Atkpc_red() {
        return this._Atkpc_red;
    }
    
    public void set_Atkpc_red(boolean flag) {
        this._Atkpc_red = flag;
    }
    
    public int get_npcbroad() {
        return this._npcbroad;
    }
    
    public void set_npcbroad(int i) {
        this._npcbroad = i;
    }
    
    public boolean is_agrocoi() {
        return this._agrocoi;
    }
    
    public void set_agrocoi(boolean flag) {
        this._agrocoi = flag;
    }
    
    public boolean isTamable() {
        return this._tameable;
    }
    
    public void setTamable(boolean flag) {
        this._tameable = flag;
    }
    
    public int get_passispeed() {
        return this._passispeed;
    }
    
    public void set_passispeed(int i) {
        this._passispeed = i;
    }
    
    public int get_atkspeed() {
        return this._atkspeed;
    }
    
    public void set_atkspeed(int i) {
        this._atkspeed = i;
    }
    
    public boolean is_agro() {
        return this._agro;
    }
    
    public void set_agro(boolean flag) {
        this._agro = flag;
    }
    
    public int get_gfxid() {
        return this._gfxid;
    }
    
    public void set_gfxid(int i) {
        this._gfxid = i;
    }
    
    public String get_nameid() {
        return this._nameid;
    }
    
    public void set_nameid(String s) {
        this._nameid = s;
    }
    
    public int get_undead() {
        return this._undead;
    }
    
    public void set_undead(int i) {
        this._undead = i;
    }
    
    public int get_poisonatk() {
        return this._poisonatk;
    }
    
    public void set_poisonatk(int i) {
        this._poisonatk = i;
    }
    
    public int get_paralysisatk() {
        return this._paralysisatk;
    }
    
    public void set_paralysisatk(int i) {
        this._paralysisatk = i;
    }
    
    public int get_family() {
        return this._family;
    }
    
    public void set_family(int i) {
        this._family = i;
    }
    
    public int get_agrofamily() {
        return this._agrofamily;
    }
    
    public void set_agrofamily(int i) {
        this._agrofamily = i;
    }
    
    public int is_agrogfxid1() {
        return this._agrogfxid1;
    }
    
    public void set_agrogfxid1(int i) {
        this._agrogfxid1 = i;
    }
    
    public int is_agrogfxid2() {
        return this._agrogfxid2;
    }
    
    public void set_agrogfxid2(int i) {
        this._agrogfxid2 = i;
    }
    
    public boolean is_picupitem() {
        return this._picupitem;
    }
    
    public void set_picupitem(boolean flag) {
        this._picupitem = flag;
    }
    
    public int get_digestitem() {
        return this._digestitem;
    }
    
    public void set_digestitem(int i) {
        this._digestitem = i;
    }
    
    public boolean is_bravespeed() {
        return this._bravespeed;
    }
    
    public void set_bravespeed(boolean flag) {
        this._bravespeed = flag;
    }
    
    public int get_hprinterval() {
        return this._hprinterval;
    }
    
    public void set_hprinterval(int i) {
        this._hprinterval = i / 1000;
    }
    
    public int get_hpr() {
        return this._hpr;
    }
    
    public void set_hpr(int i) {
        this._hpr = i;
    }
    
    public int get_mprinterval() {
        return this._mprinterval;
    }
    
    public void set_mprinterval(int i) {
        this._mprinterval = i / 1000;
    }
    
    public int get_mpr() {
        return this._mpr;
    }
    
    public void set_mpr(int i) {
        this._mpr = i;
    }
    
    public boolean is_teleport() {
        return this._teleport;
    }
    
    public void set_teleport(boolean flag) {
        this._teleport = flag;
    }
    
    public int get_randomlevel() {
        return this._randomlevel;
    }
    
    public void set_randomlevel(int i) {
        this._randomlevel = i;
    }
    
    public int get_randomhp() {
        return this._randomhp;
    }
    
    public void set_randomhp(int i) {
        this._randomhp = i;
    }
    
    public int get_randommp() {
        return this._randommp;
    }
    
    public void set_randommp(int i) {
        this._randommp = i;
    }
    
    public int get_randomac() {
        return this._randomac;
    }
    
    public void set_randomac(int i) {
        this._randomac = i;
    }
    
    public int get_randomexp() {
        return this._randomexp;
    }
    
    public void set_randomexp(int i) {
        this._randomexp = i;
    }
    
    public int get_randomlawful() {
        return this._randomlawful;
    }
    
    public void set_randomlawful(int i) {
        this._randomlawful = i;
    }
    
    public int get_damagereduction() {
        return this._damagereduction;
    }
    
    public void set_damagereduction(int i) {
        this._damagereduction = i;
    }
    
    public boolean is_hard() {
        return this._hard;
    }
    
    public void set_hard(boolean flag) {
        this._hard = flag;
    }
    
    public boolean is_doppel() {
        return this._doppel;
    }
    
    public void set_doppel(boolean flag) {
        this._doppel = flag;
    }
    
    public void set_IsTU(boolean i) {
        this._tu = i;
    }
    
    public boolean get_IsTU() {
        return this._tu;
    }
    
    public void set_IsErase(boolean i) {
        this._erase = i;
    }
    
    public boolean get_IsErase() {
        return this._erase;
    }
    
    public int getBowActId() {
        return this.bowActId;
    }
    
    public void setBowActId(int i) {
        this.bowActId = i;
    }
    
    public int getKarma() {
        return this._karma;
    }
    
    public void setKarma(int i) {
        this._karma = i;
    }
    
    public int getTransformId() {
        return this._transformId;
    }
    
    public void setTransformId(int transformId) {
        this._transformId = transformId;
    }
    
    public int getTransformGfxId() {
        return this._transformGfxId;
    }
    
    public void setTransformGfxId(int i) {
        this._transformGfxId = i;
    }
    
    public int getAtkMagicSpeed() {
        return this._atkMagicSpeed;
    }
    
    public void setAtkMagicSpeed(int atkMagicSpeed) {
        this._atkMagicSpeed = atkMagicSpeed;
    }
    
    public int getSubMagicSpeed() {
        return this._subMagicSpeed;
    }
    
    public void setSubMagicSpeed(int subMagicSpeed) {
        this._subMagicSpeed = subMagicSpeed;
    }
    
    public int getLightSize() {
        return this._lightSize;
    }
    
    public void setLightSize(int lightSize) {
        this._lightSize = lightSize;
    }
    
    public boolean isAmountFixed() {
        return this._amountFixed;
    }
    
    public void setAmountFixed(boolean fixed) {
        this._amountFixed = fixed;
    }
    
    public boolean getChangeHead() {
        return this._changeHead;
    }
    
    public void setChangeHead(boolean changeHead) {
        this._changeHead = changeHead;
    }
    
    public boolean isCantResurrect() {
        return this._isCantResurrect;
    }
    
    public void setCantResurrect(boolean isCantResurrect) {
        this._isCantResurrect = isCantResurrect;
    }
    
    public void set_classname(String classname) {
        this._classname = classname;
    }
    
    public String get_classname() {
        return this._classname;
    }
    
    public NpcExecutor getNpcExecutor() {
        return this._class;
    }
    
    public void setNpcExecutor(NpcExecutor _class) {
        try {
            if (_class == null) {
                return;
            }
            else {
                this._class = _class;
                int type = _class.type();
                if (type >= 32) {
                    this._spawn = true;
                    type -= 32;
                }
                if (type >= 16) {
                    this._work = true;
                    type -= 16;
                }
                if (type >= 8) {
                    this._death = true;
                    type -= 8;
                }
                if (type >= 4) {
                    this._attack = true;
                    type -= 4;
                }
                if (type >= 2) {
                    this._action = true;
                    type -= 2;
                }
                if (type >= 1) {
                    this._talk = true;
                    --type;
                }
                if (type > 0) {
                    L1Npc._log.error("獨立判斷項數組設定錯誤:餘數大於0 NpcId: " + this._npcid);
                }
            }
        }
        catch (Exception e) {
            L1Npc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public boolean talk() {
        return this._talk;
    }
    
    public boolean action() {
        return this._action;
    }
    
    public boolean attack() {
        return this._attack;
    }
    
    public boolean death() {
        return this._death;
    }
    
    public boolean work() {
        return this._work;
    }
    
    public boolean spawn() {
        return this._spawn;
    }
    
    public void set_boss(boolean boss) {
        this._boss = boss;
    }
    
    public boolean is_boss() {
        return this._boss;
    }
    
    public int get_quest_score() {
        return this._quest_score;
    }
    
    public void set_quest_score(int quest_score) {
        this._quest_score = quest_score;
    }
    
    public int getresurreId() {
        return this._quest_score;
    }
}
