package com.lineage.server.model.weaponskill;

import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK001 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK001.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK001();
    }
    
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            int chance = W_SK001._random.nextInt(1000);
            int random = this.random(weapon);
            if (random >= chance) {
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
                    double dmg = (double)(target.getCurrentHp() * this._type1 / this._type2);
                    pc.sendPackets(new S_ServerMessage(158, weapon.getLogName()));
                    pc.getInventory().removeItem(weapon, 1L);
                    double outdmg = (double)this.dmg1() + this.dmg2(srcdmg) + (double)this.dmg3(pc) + dmg;
                    if ((double)target.getCurrentHp() - outdmg < 0.0 && target.getCurrentHp() != 1) {
                        outdmg = (double)(target.getCurrentHp() - 1);
                    }
                    if (target instanceof L1PcInstance) {
                        L1PcInstance tgpc2 = (L1PcInstance)target;
                        tgpc2.sendPacketsAll(new S_DoActionGFX(tgpc2.getId(), 2));
                        tgpc2.receiveDamage(pc, outdmg, false, true);
                    }
                }
            }
            return 0.0;
        }
        catch (Exception e) {
            W_SK001._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
