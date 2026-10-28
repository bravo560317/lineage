package com.lineage.server.model.weaponskill;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK003 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK003.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK003();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            int chance = W_SK003._random.nextInt(1000);
            int random = this.random(weapon);
            if (random >= chance) {
                int hpadd = Math.max((int)(srcdmg * (double)this._type1 / (double)this._type2), 1);
                short newHp = (short)(pc.getCurrentHp() + hpadd);
                pc.setCurrentHp((int)newHp);
                return 0.0;
            }
            else {
                return 0.0;
            }
        }
        catch (Exception e) {
            W_SK003._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
