package com.lineage.server.model.weaponskill;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK005 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK005.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK005();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            int chance = W_SK005._random.nextInt(1000);
            if (this.random(weapon) + pc.getWeaponSkillChance() * 10 >= chance) {
                if (target.getCurrentMp() > 0) {
                    int evel = weapon.getEnchantLevel() * 2;
                    int mpadd = this._type1 + evel;
                    L1Magic magic = new L1Magic(pc, target);
                    magic.commit(0, mpadd);
                }
                W_SK005.DmgSet dmgSet = new W_SK005.DmgSet(pc, target, weapon, srcdmg);
                dmgSet.begin();
                return 0.0;
            }
            else {
                return 0.0;
            }
        }
        catch (Exception e) {
            W_SK005._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
    
    private class DmgSet implements Runnable
    {
        private int _timeCounter;
        private double _srcdmg;
        private final L1PcInstance _pc;
        private final L1Character _cha;
        private final L1ItemInstance _weapon;
        
        private DmgSet(L1PcInstance pc, L1Character cha, L1ItemInstance weapon, double srcdmg) {
            this._timeCounter = 0;
            this._srcdmg = 0.0;
            this._cha = cha;
            this._pc = pc;
            this._srcdmg = srcdmg;
            this._weapon = weapon;
        }
        
        @Override
        public void run() {
            try {
                if (this._cha.hasSkillEffect(8050)) {
                    return;
                }
                else if (this._cha.hasSkillEffect(31)) {
                    this._cha.removeSkillEffect(31);
                    int castgfx2 = SkillsTable.get().getTemplate(31).getCastGfx2();
                    this._cha.broadcastPacketAll(new S_SkillSound(this._cha.getId(), castgfx2));
                    if (this._cha instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)this._cha;
                        tgpc.sendPacketsAll(new S_SkillSound(tgpc.getId(), castgfx2));
                    }
                    return;
                }
                else {
                    if (this._cha.hasSkillEffect(153)) {
                        this._cha.removeSkillEffect(153);
                    }
                    W_SK005.this.show(this._pc, this._cha);
                    while (this._timeCounter < W_SK005.this._type2) {
                        if (this._cha != null) {
                            if (this._cha.isDead()) {
                                break;
                            }
                            else {
                                this.attack();
                                ++this._timeCounter;
                                Thread.sleep(1000L);
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            catch (Throwable e) {
                W_SK005._log.error(e.getLocalizedMessage(), e);
            }
        }
        
        private void begin() {
            GeneralThreadPool.get().schedule(this, 100L);
        }
        
        private void attack() {
            this._cha.setSkillEffect(8050, W_SK005.this._type2 * 1000);
            double damage = DmgAcMr.getDamage(this._pc);
            if ((double)this._cha.getCurrentHp() - damage <= 0.0 && this._cha.getCurrentHp() != 1) {
                damage = (double)(this._cha.getCurrentHp() - 1);
            }
            else if (this._cha.getCurrentHp() == 1) {
                damage = 0.0;
            }
            double outdmg = (double)W_SK005.this.dmg1() + W_SK005.this.dmg2(this._srcdmg) + (double)W_SK005.this.dmg3(this._pc) + damage;
            outdmg = W_SK005.this.calc_dmg(this._pc, this._cha, outdmg, this._weapon);
            if (W_SK005.this._type3 > 0) {
                outdmg *= (double)W_SK005.this._type3 / 100.0;
            }
            if (this._pc.getWeaponSkillDmg() != 1.0) {
                outdmg += outdmg * this._pc.getWeaponSkillDmg();
            }
            if (this._cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)this._cha;
                pc.receiveDamage(this._pc, outdmg, true, true);
            }
            else if (this._cha instanceof L1NpcInstance) {
                L1NpcInstance npc = (L1NpcInstance)this._cha;
                npc.receiveDamage(this._pc, (int)outdmg);
            }
        }
    }
}
