package com.lineage.server.model.weaponskill;

import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK0015 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK0015.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK0015();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            if (target.getCurrentMp() <= 0) {
                return 0.0;
            }
            else {
                int som_lvl = weapon.getEnchantLevel() + 3;
                if (som_lvl < 0) {
                    som_lvl = 0;
                }
                if (target instanceof L1MonsterInstance) {
                    L1MonsterInstance targetNpc = (L1MonsterInstance)target;
                    if (targetNpc.getCurrentMp() > 0) {
                        L1Magic magic = new L1Magic(pc, targetNpc);
                        magic.commit(0, this._type1 + W_SK0015._random.nextInt(this._type2));
                    }
                }
                else if (target instanceof L1PcInstance) {
                    L1PcInstance targetpc = (L1PcInstance)target;
                    int mana = this._type1 + W_SK0015._random.nextInt(this._type2);
                    if (targetpc.getCurrentMp() > 0) {
                        pc.setCurrentMp((int)(short)(pc.getCurrentMp() + mana));
                        targetpc.setCurrentMp((int)(short)(targetpc.getCurrentMp() - mana));
                    }
                }
                return 0.0;
            }
        }
        catch (Exception e) {
            W_SK0015._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
