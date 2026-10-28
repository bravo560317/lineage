package com.lineage.server.model.weaponskill;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK0014 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK0014.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK0014();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            int chance = W_SK0014._random.nextInt(1000);
            if (this.random(weapon) + pc.getWeaponSkillChance() * 10 >= chance) {
                if (target.hasSkillEffect(31)) {
                    target.removeSkillEffect(31);
                    int castgfx2 = SkillsTable.get().getTemplate(31).getCastGfx2();
                    target.broadcastPacketAll(new S_SkillSound(target.getId(), castgfx2));
                    if (target instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)target;
                        tgpc.sendPacketsAll(new S_SkillSound(tgpc.getId(), castgfx2));
                    }
                    return 0.0;
                }
                else {
                    if (target.hasSkillEffect(153)) {
                        target.removeSkillEffect(153);
                    }
                    double damage = DmgAcMr.getDamage(pc);
                    double SPINT = DmgAcMr.getSpInt(pc);
                    int dice = this._type1 + W_SK0014._random.nextInt(this._type2);
                    int def = this.calc_resist(target);
                    double outdmg;
                    damage = (outdmg = this.calc_mr(pc, target, (SPINT - 4.0 - (double)def) * (double)dice / 32.0 * this.bon(), weapon));
                    if (this._type3 > 0) {
                        outdmg *= (double)this._type3 / 100.0;
                    }
                    if (pc.getWeaponSkillDmg() != 0.0) {
                        outdmg += outdmg * pc.getWeaponSkillDmg();
                    }
                    this.show(pc, target);
                    return outdmg;
                }
            }
            else {
                return 0.0;
            }
        }
        catch (Exception e) {
            W_SK0014._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
