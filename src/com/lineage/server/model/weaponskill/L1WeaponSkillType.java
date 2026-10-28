package com.lineage.server.model.weaponskill;

import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Location;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public abstract class L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    private double _level;
    protected int _type1;
    protected int _type2;
    protected int _type3;
    private int _attr;
    protected int _ac_mr;
    private int _gfxid1;
    private int _gfxid2;
    private int _gfxid3;
    private int _gfxid4;
    private int _gfxid4_count;
    protected int _power;
    private int _srcdmg;
    private int _addsrcdmg;
    protected int _random1;
    protected int _random2;
    protected boolean _boss_holdout;
    
    
    static {
        _log = LogFactory.getLog(L1WeaponSkillType.class);
        _random = new Random();
    }
    
    public L1WeaponSkillType() {
        this._level = 1.0;
    }
    
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        return 0.0;
    }
    
    public double get_level() {
        return this._level;
    }
    
    public void set_level(int level) {
        this._level = (double)level / 100.0;
    }
    
    public int get_type1() {
        return this._type1;
    }
    
    public void set_type1(int _type1) {
        this._type1 = _type1;
    }
    
    public int get_type2() {
        return this._type2;
    }
    
    public void set_type2(int _type2) {
        this._type2 = _type2;
    }
    
    public int get_type3() {
        return this._type3;
    }
    
    public void set_type3(int _type3) {
        this._type3 = _type3;
    }
    
    public int get_attr() {
        return this._attr;
    }
    
    public void set_attr(int _attr) {
        this._attr = _attr;
    }
    
    public int get_ac_mr() {
        return this._ac_mr;
    }
    
    public void set_ac_mr(int _ac_mr) {
        this._ac_mr = _ac_mr;
    }
    
    public int get_gfxid1() {
        return this._gfxid1;
    }
    
    public void set_gfxid1(int _gfxid1) {
        this._gfxid1 = _gfxid1;
    }
    
    public int get_gfxid2() {
        return this._gfxid2;
    }
    
    public void set_gfxid2(int _gfxid2) {
        this._gfxid2 = _gfxid2;
    }
    
    public int get_gfxid3() {
        return this._gfxid3;
    }
    
    public void set_gfxid3(int _gfxid3) {
        this._gfxid3 = _gfxid3;
    }
    
    public int get_gfxid4() {
        return this._gfxid4;
    }
    
    public void set_gfxid4(int _gfxid4) {
        this._gfxid4 = _gfxid4;
    }
    
    public int get_gfxid4_count() {
        return this._gfxid4_count;
    }
    
    public void set_gfxid4_count(int _gfxid4_count) {
        this._gfxid4_count = _gfxid4_count;
    }
    
    public int get_power() {
        return this._power;
    }
    
    public void set_power(int _power) {
        this._power = _power;
    }
    
    public int get_srcdmg() {
        return this._srcdmg;
    }
    
    public void set_srcdmg(int _srcdmg) {
        this._srcdmg = _srcdmg;
    }
    
    public int get_addsrcdmg() {
        return this._addsrcdmg;
    }
    
    public void set_addsrcdmg(int _addsrcdmg) {
        if (_addsrcdmg > 100) {
            _addsrcdmg = 100;
        }
        this._addsrcdmg = _addsrcdmg;
    }
    
    public int get_random1() {
        return this._random1;
    }
    
    public void set_random1(int _random1) {
        if (_random1 > 1000) {
            _random1 = 1000;
        }
        this._random1 = _random1;
    }
    
    public int get_random2() {
        return this._random2;
    }
    
    public void set_random2(int _random2) {
        if (_random2 > 1000) {
            _random2 = 1000;
        }
        this._random2 = _random2;
    }
    
    public void set_boss_holdout(boolean boss_holdout) {
        this._boss_holdout = boss_holdout;
    }
    
    public boolean get_boss_holdout() {
        return this._boss_holdout;
    }
    
    protected int random(L1ItemInstance weapon) {
        try {
            int int1 = this._random1;
            if (this._random2 == 0 || weapon.getEnchantLevel() > 0) {}
            return int1 + weapon.getEnchantLevel() * this._random2;
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
            return 0;
        }
    }
    
    protected int dmg1() {
        try {
            if (this._srcdmg != 0) {
                return this._srcdmg;
            }
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }
    
    protected double dmg2(double srcdmg) {
        try {
            if (this._addsrcdmg != 0) {
                return srcdmg * (double)this._addsrcdmg / 100.0;
            }
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
        }
        return 0.0;
    }
    
    protected int dmg3(L1PcInstance pc) {
        try {
            if (this._power > 0) {
                int int1 = 0;
                int tmp = this._power;
                int i = 0;
                if (tmp >= 32) {
                    ++i;
                    tmp -= 32;
                    int1 += pc.getCon();
                }
                if (tmp >= 16) {
                    ++i;
                    tmp -= 16;
                    int1 += pc.getCha();
                }
                if (tmp >= 8) {
                    ++i;
                    tmp -= 8;
                    int1 += pc.getWis();
                }
                if (tmp >= 4) {
                    ++i;
                    tmp -= 4;
                    int1 += pc.getInt();
                }
                if (tmp >= 2) {
                    ++i;
                    tmp -= 2;
                    int1 += pc.getDex();
                }
                if (tmp >= 1) {
                    ++i;
                    --tmp;
                    int1 += pc.getStr();
                }
                return int1 / i >> 1;
            }
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }
    
    protected double calc_dmg(L1PcInstance pc, L1Character target, double srcdmg, L1ItemInstance weapon) {
        if (target.hasSkillEffect(68)) {
            srcdmg /= 2.0;
        }
        try {
            switch (this._ac_mr) {
                case 1: {
                    srcdmg -= (double)DmgAcMr.calcDefense(target);
                    break;
                }
                case 2: {
                    srcdmg = DmgAcMr.calcMrDefense(pc, target, srcdmg, weapon);
                    break;
                }
                case 3: {
                    srcdmg -= (double)DmgAcMr.calcDefense(target);
                    srcdmg = DmgAcMr.calcMrDefense(pc, target, srcdmg, weapon);
                    break;
                }
            }
            int resist = 0;
            switch (this._attr) {
                case 1: {
                    resist = target.getEarth();
                    break;
                }
                case 2: {
                    resist = target.getFire();
                    break;
                }
                case 4: {
                    resist = target.getWater();
                    break;
                }
                case 8: {
                    resist = target.getWind();
                    break;
                }
            }
            int resistFloor = (int)(0.16 * (double)Math.abs(resist));
            if (resist >= 0) {
                resistFloor *= 1;
            }
            else {
                resistFloor *= -1;
            }
            double attrDeffence = (double)resistFloor / 32.0;
            srcdmg *= 1.0 - attrDeffence;
            if (target.getMr() >= 50 && target.getMr() <= 99 && L1WeaponSkillType._random.nextInt(100) <= 5) {
                srcdmg /= 2.0;
            }
            else if (target.getMr() >= 100 && target.getMr() <= 149 && L1WeaponSkillType._random.nextInt(100) <= 15) {
                srcdmg /= 2.0;
            }
            else if (target.getMr() >= 150 && target.getMr() <= 179 && L1WeaponSkillType._random.nextInt(100) <= 20) {
                srcdmg /= 2.0;
            }
            else if (target.getMr() >= 180 && target.getMr() <= 209 && L1WeaponSkillType._random.nextInt(100) <= 30) {
                srcdmg /= 2.0;
            }
            else if (target.getMr() >= 210 && target.getMr() <= 229 && L1WeaponSkillType._random.nextInt(100) <= 40) {
                srcdmg /= 2.0;
            }
            else if (target.getMr() >= 230 && target.getMr() <= 249 && L1WeaponSkillType._random.nextInt(100) <= 60) {
                srcdmg /= 2.0;
            }
            else if (target.getMr() >= 250 && L1WeaponSkillType._random.nextInt(100) <= 80) {
                srcdmg /= 2.0;
            }
            return srcdmg * this._level;
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
    
    protected void show(L1PcInstance pc, L1Character target) {
        try {
            if (this._gfxid1 != 0) {
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), this._gfxid1));
            }
            if (this._gfxid2 != 0) {
                target.broadcastPacketAll(new S_SkillSound(target.getId(), this._gfxid2));
                if (target instanceof L1PcInstance) {
                    L1PcInstance targetPc = (L1PcInstance)target;
                    targetPc.sendPackets(new S_SkillSound(target.getId(), this._gfxid2));
                }
            }
            if (this._gfxid3 != 0) {
                int[] data = { 1, 0, this._gfxid3, 6 };
                S_UseAttackSkill packet = new S_UseAttackSkill(pc, target.getId(), target.getX(), target.getY(), data, false);
                pc.sendPackets(packet);
                pc.broadcastPacketAll(packet);
            }
            if (this._gfxid4 != 0 && this._gfxid4_count > 0) {
                L1WeaponSkillType.WanponSkill wanponSkill = new L1WeaponSkillType.WanponSkill(pc);
                wanponSkill.start_skill();
            }
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    protected int calc_resist(L1Character target) {
        try {
            int resist = 0;
            switch (this._attr) {
                case 1: {
                    resist = target.getEarth();
                    break;
                }
                case 2: {
                    resist = target.getFire();
                    break;
                }
                case 4: {
                    resist = target.getWater();
                    break;
                }
                case 8: {
                    resist = target.getWind();
                    break;
                }
            }
            int resistFloor = 0;
            if (resist <= 3) {
                resistFloor = 0;
            }
            else if (resist > 3 && resist <= 8) {
                resistFloor = 1;
            }
            else if (resist == 9) {
                resistFloor = 2;
            }
            else if (resist > 9 && resist <= 14) {
                resistFloor = 3;
            }
            else if (resist > 14 && resist <= 17) {
                resistFloor = 4;
            }
            else if (resist > 17 && resist <= 20) {
                resistFloor = 5;
            }
            else if (resist > 20 && resist <= 24) {
                resistFloor = 6;
            }
            else if (resist > 24 && resist <= 27) {
                resistFloor = 7;
            }
            else if (resist > 27 && resist <= 29) {
                resistFloor = 8;
            }
            else if (resist > 29 && resist <= 30) {
                resistFloor = 9;
            }
            else if (resist > 30 && resist <= 35) {
                resistFloor = 10;
            }
            else if (resist > 35 && resist <= 40) {
                resistFloor = 11;
            }
            else if (resist > 40 && resist <= 44) {
                resistFloor = 12;
            }
            else if (resist > 44 && resist <= 48) {
                resistFloor = 13;
            }
            else if (resist > 48 && resist <= 52) {
                resistFloor = 14;
            }
            else if (resist > 52 && resist <= 54) {
                resistFloor = 15;
            }
            else if (resist > 54 && resist <= 56) {
                resistFloor = 16;
            }
            else if (resist > 56 && resist <= 58) {
                resistFloor = 17;
            }
            else if (resist > 58 && resist <= 60) {
                resistFloor = 18;
            }
            else if (resist > 60 && resist <= 64) {
                resistFloor = 19;
            }
            else if (resist > 64 && resist <= 68) {
                resistFloor = 20;
            }
            else if (resist > 68 && resist <= 72) {
                resistFloor = 21;
            }
            else if (resist > 72 && resist <= 76) {
                resistFloor = 22;
            }
            else if (resist > 76 && resist <= 80) {
                resistFloor = 23;
            }
            else if (resist > 80 && resist <= 84) {
                resistFloor = 24;
            }
            else if (resist > 84 && resist <= 88) {
                resistFloor = 25;
            }
            else if (resist > 92 && resist <= 96) {
                resistFloor = 26;
            }
            else if (resist > 100 && resist <= 104) {
                resistFloor = 27;
            }
            else if (resist > 104 && resist <= 108) {
                resistFloor = 28;
            }
            else if (resist > 108 && resist <= 112) {
                resistFloor = 29;
            }
            else if (resist > 112 && resist <= 120) {
                resistFloor = 30;
            }
            else if (resist > 120 && resist <= 129) {
                resistFloor = 31;
            }
            else if (resist > 129) {
                resistFloor = 32;
            }
            return resistFloor;
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
            return 0;
        }
    }
    
    protected double bon() {
        try {
            if (this._addsrcdmg != 0) {
                return (double)this._addsrcdmg / 100.0;
            }
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
        }
        return 1.0;
    }
    
    protected double calc_mr(L1PcInstance pc, L1Character target, double srcdmg, L1ItemInstance weapon) {
        try {
            switch (this._ac_mr) {
                case 1: {
                    srcdmg -= (double)DmgAcMr.calcDefense(target);
                    break;
                }
                case 2: {
                    srcdmg = DmgAcMr.calcMrDefense(pc, target, srcdmg, weapon);
                    break;
                }
                case 3: {
                    srcdmg -= (double)DmgAcMr.calcDefense(target);
                    srcdmg = DmgAcMr.calcMrDefense(pc, target, srcdmg, weapon);
                    break;
                }
                case 4: {
                    srcdmg = DmgAcMr.calcMrDefense2(pc, target, srcdmg, weapon);
                    break;
                }
            }
            return srcdmg;
        }
        catch (Exception e) {
            L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
    
    private class WanponSkill implements Runnable
    {
        private L1PcInstance _pc;
        
        private WanponSkill(L1PcInstance pc) {
            this._pc = pc;
        }
        
        public void start_skill() {
            PcOtherThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            try {
                int x = this._pc.getX();
                int y = this._pc.getY();
                int mapId = this._pc.getMapId();
                for (int i = 0; i < L1WeaponSkillType.this._gfxid4_count; ++i) {
                    Thread.sleep(30L);
                    int i2 = L1WeaponSkillType._random.nextInt(4);
                    int i3 = L1WeaponSkillType._random.nextInt(4);
                    switch (L1WeaponSkillType._random.nextInt(4)) {
                        case 0: {
                            this._pc.sendPacketsAll(new S_EffectLocation(new L1Location(x + i2, y - i3, mapId), L1WeaponSkillType.this._gfxid4));
                            break;
                        }
                        case 1: {
                            this._pc.sendPacketsAll(new S_EffectLocation(new L1Location(x - i2, y + i3, mapId), L1WeaponSkillType.this._gfxid4));
                            break;
                        }
                        case 2: {
                            this._pc.sendPacketsAll(new S_EffectLocation(new L1Location(x + i2, y + i3, mapId), L1WeaponSkillType.this._gfxid4));
                            break;
                        }
                        case 3: {
                            this._pc.sendPacketsAll(new S_EffectLocation(new L1Location(x - i2, y - i3, mapId), L1WeaponSkillType.this._gfxid4));
                            break;
                        }
                    }
                }
            }
            catch (Exception e) {
                L1WeaponSkillType._log.error(e.getLocalizedMessage(), e);
            }
        }
    }

}
