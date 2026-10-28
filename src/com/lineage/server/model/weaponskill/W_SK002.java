package com.lineage.server.model.weaponskill;

import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK002 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK002.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK002();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            if (target.getPoison() != null) {
                return 0.0;
            }
            else {
                int chance = W_SK002._random.nextInt(1000);
                int random = this.random(weapon);
                if (random >= chance) {
                    L1DamagePoison.doInfection(pc, target, this._type1 * 1000, this._type2);
                    return 0.0;
                }
                else {
                    return 0.0;
                }
            }
        }
        catch (Exception e) {
            W_SK002._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
