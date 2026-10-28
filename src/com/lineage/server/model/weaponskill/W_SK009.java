package com.lineage.server.model.weaponskill;

import com.lineage.server.model.L1Magic;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK009 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK009.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK009();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            int chance = W_SK009._random.nextInt(1000);
            if (this.random(weapon) >= chance) {
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
                    if (target.getCurrentMp() > 0) {
                        int mana = this._type1 + W_SK009._random.nextInt(this._type2);
                        int drainMana = mana + pc.getInt() / 2;
                        if (target.getCurrentMp() < drainMana) {
                            drainMana = target.getCurrentMp();
                        }
                        L1Magic magic = new L1Magic(pc, target);
                        magic.commit(0, drainMana);
                        this.show(pc, target);
                    }
                }
            }
            return 0.0;
        }
        catch (Exception e) {
            W_SK009._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
