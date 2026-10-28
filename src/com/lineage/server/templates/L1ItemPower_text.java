package com.lineage.server.templates;

import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ItemPower_text
{
    private static final Log _log;
    private int _id;
    private int _ac;
    private int _hp;
    private int _mp;
    private int _hpr;
    private int _mpr;
    private int _mr;
    private int _sp;
    private int _hit;
    private int _dmgup;
    private int _bowhit;
    private int _bowdmgup;
    private int _dice_dmg;
    private int _dmg;
    private int _er;
    private int _dodge;
    private int _dice_hp;
    private int _sucking_hp;
    private int _dice_mp;
    private int _sucking_mp;
    private int _double_dmg;
    private int _lift;
    private int _defense_water;
    private int _defense_wind;
    private int _defense_fire;
    private int _defense_earth;
    private int _regist_stun;
    private int _regist_stone;
    private int _regist_sleep;
    private int _regist_freeze;
    private int _regist_sustain;
    private int _regist_blind;
    private int[] _gfx;
    private String _msg;
    
    static {
        _log = LogFactory.getLog(L1ItemPower_text.class);
    }
    
    public void add_pc_power(L1PcInstance pc) {
        try {
            if (this._ac != 0) {
                pc.addAc(this._ac);
            }
            if (this._hp != 0) {
                pc.addMaxHp(this._hp);
            }
            if (this._mp != 0) {
                pc.addMaxMp(this._mp);
            }
            if (this._hpr != 0) {
                pc.addHpr(this._hpr);
            }
            if (this._mpr != 0) {
                pc.addMpr(this._mpr);
            }
            if (this._mr != 0) {
                pc.addMr(this._mr);
                pc.sendPackets(new S_SPMR(pc));
            }
            if (this._sp != 0) {
                pc.addSp(this._sp);
                pc.sendPackets(new S_SPMR(pc));
            }
            if (this._hit != 0) {
                pc.addHitup(this._hit);
            }
            if (this._dmgup != 0) {
                pc.addDmgup(this._dmgup);
            }
            if (this._bowhit != 0) {
                pc.addBowHitup(this._bowhit);
            }
            if (this._bowdmgup != 0) {
                pc.addBowDmgup(this._bowdmgup);
            }
            if (this._dice_dmg != 0 && this._dmg != 0) {
                pc.set_dmgAdd(this._dmg, this._dice_dmg);
            }
            if (this._er != 0) {
                pc.addOriginalEr(this._er);
                pc.sendPackets(new S_PacketBox(132, pc.getEr()));
            }
            if (this._dodge != 0) {
                pc.add_dodge(this._dodge);
                pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
            }
            if (this._dice_hp != 0 && this._sucking_hp != 0) {
                pc.add_dice_hp(this._dice_hp, this._sucking_hp);
            }
            if (this._dice_mp != 0 && this._sucking_mp != 0) {
                pc.add_dice_mp(this._dice_mp, this._sucking_mp);
            }
            if (this._double_dmg != 0) {
                pc.add_double_dmg(this._double_dmg);
            }
            if (this._lift != 0) {
                pc.add_lift(this._lift);
            }
            if (this._defense_water != 0) {
                pc.addWater(this._defense_wind);
            }
            if (this._defense_wind != 0) {
                pc.addWind(this._defense_wind);
            }
            if (this._defense_fire != 0) {
                pc.addFire(this._defense_fire);
            }
            if (this._defense_earth != 0) {
                pc.addEarth(this._defense_earth);
            }
            if (this._regist_stun != 0) {
                pc.addRegistStun(this._regist_stun);
            }
            if (this._regist_stone != 0) {
                pc.addRegistStone(this._regist_stone);
            }
            if (this._regist_sleep != 0) {
                pc.addRegistSleep(this._regist_sleep);
            }
            if (this._regist_freeze != 0) {
                pc.add_regist_freeze(this._regist_freeze);
            }
            if (this._regist_sustain != 0) {
                pc.addRegistSustain(this._regist_sustain);
            }
            if (this._regist_blind != 0) {
                pc.addRegistBlind(this._regist_blind);
            }
        }
        catch (Exception e) {
            L1ItemPower_text._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void remove_pc_power(L1PcInstance pc) {
        try {
            if (this._ac != 0) {
                pc.addAc(-this._ac);
            }
            if (this._hp != 0) {
                pc.addMaxHp(-this._hp);
            }
            if (this._mp != 0) {
                pc.addMaxMp(-this._mp);
            }
            if (this._hpr != 0) {
                pc.addHpr(-this._hpr);
            }
            if (this._mpr != 0) {
                pc.addMpr(-this._mpr);
            }
            if (this._mr != 0) {
                pc.addMr(-this._mr);
                pc.sendPackets(new S_SPMR(pc));
            }
            if (this._sp != 0) {
                pc.addSp(-this._sp);
                pc.sendPackets(new S_SPMR(pc));
            }
            if (this._hit != 0) {
                pc.addHitup(-this._hit);
            }
            if (this._dmgup != 0) {
                pc.addDmgup(-this._dmgup);
            }
            if (this._bowhit != 0) {
                pc.addBowHitup(-this._bowhit);
            }
            if (this._bowdmgup != 0) {
                pc.addBowDmgup(-this._bowdmgup);
            }
            if (this._dice_dmg != 0 && this._dmg != 0) {
                pc.set_dmgAdd(-this._dice_dmg, -this._dmg);
            }
            if (this._er != 0) {
                pc.addOriginalEr(-this._er);
                pc.sendPackets(new S_PacketBox(132, pc.getEr()));
            }
            if (this._dodge != 0) {
                pc.add_dodge(-this._dodge);
                pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
            }
            if (this._dice_hp != 0 && this._sucking_hp != 0) {
                pc.add_dice_hp(-this._dice_hp, -this._sucking_hp);
            }
            if (this._dice_mp != 0 && this._sucking_mp != 0) {
                pc.add_dice_mp(-this._dice_mp, -this._sucking_mp);
            }
            if (this._double_dmg != 0) {
                pc.add_double_dmg(-this._double_dmg);
            }
            if (this._lift != 0) {
                pc.add_lift(-this._lift);
            }
            if (this._defense_water != 0) {
                pc.addWater(-this._defense_wind);
            }
            if (this._defense_wind != 0) {
                pc.addWind(-this._defense_wind);
            }
            if (this._defense_fire != 0) {
                pc.addFire(-this._defense_fire);
            }
            if (this._defense_earth != 0) {
                pc.addEarth(-this._defense_earth);
            }
            if (this._regist_stun != 0) {
                pc.addRegistStun(-this._regist_stun);
            }
            if (this._regist_stone != 0) {
                pc.addRegistStone(-this._regist_stone);
            }
            if (this._regist_sleep != 0) {
                pc.addRegistSleep(-this._regist_sleep);
            }
            if (this._regist_freeze != 0) {
                pc.add_regist_freeze(-this._regist_freeze);
            }
            if (this._regist_sustain != 0) {
                pc.addRegistSustain(-this._regist_sustain);
            }
            if (this._regist_blind != 0) {
                pc.addRegistBlind(-this._regist_blind);
            }
        }
        catch (Exception e) {
            L1ItemPower_text._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getAc() {
        return this._ac;
    }
    
    public void setAc(int ac) {
        this._ac = ac;
    }
    
    public int getHp() {
        return this._hp;
    }
    
    public void setHp(int hp) {
        this._hp = hp;
    }
    
    public int getMp() {
        return this._mp;
    }
    
    public void setMp(int mp) {
        this._mp = mp;
    }
    
    public int getHpr() {
        return this._hpr;
    }
    
    public void setHpr(int hpr) {
        this._hpr = hpr;
    }
    
    public int getMpr() {
        return this._mpr;
    }
    
    public void setMpr(int mpr) {
        this._mpr = mpr;
    }
    
    public int getMr() {
        return this._mr;
    }
    
    public void setMr(int mr) {
        this._mr = mr;
    }
    
    public int getSp() {
        return this._sp;
    }
    
    public void setSp(int sp) {
        this._sp = sp;
    }
    
    public int getHit() {
        return this._hit;
    }
    
    public void setHit(int hit) {
        this._hit = hit;
    }
    
    public int getDmgup() {
        return this._dmgup;
    }
    
    public void setDmgup(int dmgup) {
        this._dmgup = dmgup;
    }
    
    public int getBowhit() {
        return this._bowhit;
    }
    
    public void setBowhit(int bowhit) {
        this._bowhit = bowhit;
    }
    
    public int getBowdmgup() {
        return this._bowdmgup;
    }
    
    public void setBowdmgup(int bowdmgup) {
        this._bowdmgup = bowdmgup;
    }
    
    public int getDice_dmg() {
        return this._dice_dmg;
    }
    
    public void setDice_dmg(int dice_dmg) {
        this._dice_dmg = dice_dmg;
    }
    
    public int getDmg() {
        return this._dmg;
    }
    
    public void setDmg(int dmg) {
        this._dmg = dmg;
    }
    
    public int getEr() {
        return this._er;
    }
    
    public void setEr(int er) {
        this._er = er;
    }
    
    public int getDodge() {
        return this._dodge;
    }
    
    public void setDodge(int dodge) {
        this._dodge = dodge;
    }
    
    public int getDice_hp() {
        return this._dice_hp;
    }
    
    public void setDice_hp(int dice_hp) {
        this._dice_hp = dice_hp;
    }
    
    public int getSucking_hp() {
        return this._sucking_hp;
    }
    
    public void setSucking_hp(int sucking_hp) {
        this._sucking_hp = sucking_hp;
    }
    
    public int getDice_mp() {
        return this._dice_mp;
    }
    
    public void setDice_mp(int dice_mp) {
        this._dice_mp = dice_mp;
    }
    
    public int getSucking_mp() {
        return this._sucking_mp;
    }
    
    public void setSucking_mp(int sucking_mp) {
        this._sucking_mp = sucking_mp;
    }
    
    public int getDouble_dmg() {
        return this._double_dmg;
    }
    
    public void setDouble_dmg(int double_dmg) {
        this._double_dmg = double_dmg;
    }
    
    public int getLift() {
        return this._lift;
    }
    
    public void setLift(int lift) {
        this._lift = lift;
    }
    
    public int getDefense_water() {
        return this._defense_water;
    }
    
    public void setDefense_water(int defense_water) {
        this._defense_water = defense_water;
    }
    
    public int getDefense_wind() {
        return this._defense_wind;
    }
    
    public void setDefense_wind(int defense_wind) {
        this._defense_wind = defense_wind;
    }
    
    public int getDefense_fire() {
        return this._defense_fire;
    }
    
    public void setDefense_fire(int defense_fire) {
        this._defense_fire = defense_fire;
    }
    
    public int getDefense_earth() {
        return this._defense_earth;
    }
    
    public void setDefense_earth(int defense_earth) {
        this._defense_earth = defense_earth;
    }
    
    public int getRegist_stun() {
        return this._regist_stun;
    }
    
    public void setRegist_stun(int regist_stun) {
        this._regist_stun = regist_stun;
    }
    
    public int getRegist_stone() {
        return this._regist_stone;
    }
    
    public void setRegist_stone(int regist_stone) {
        this._regist_stone = regist_stone;
    }
    
    public int getRegist_sleep() {
        return this._regist_sleep;
    }
    
    public void setRegist_sleep(int regist_sleep) {
        this._regist_sleep = regist_sleep;
    }
    
    public int getRegist_freeze() {
        return this._regist_freeze;
    }
    
    public void setRegist_freeze(int regist_freeze) {
        this._regist_freeze = regist_freeze;
    }
    
    public int getRegist_sustain() {
        return this._regist_sustain;
    }
    
    public void setRegist_sustain(int regist_sustain) {
        this._regist_sustain = regist_sustain;
    }
    
    public int getRegist_blind() {
        return this._regist_blind;
    }
    
    public void setRegist_blind(int regist_blind) {
        this._regist_blind = regist_blind;
    }
    
    public int[] getGfx() {
        return this._gfx;
    }
    
    public void setGfx(int[] out) {
        this._gfx = out;
    }
    
    public String getMsg() {
        return this._msg;
    }
    
    public void setMsg(String msg) {
        this._msg = msg;
    }
    
    public int get_id() {
        return this._id;
    }
    
    public void set_id(int _id) {
        this._id = _id;
    }
}
