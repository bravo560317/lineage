package com.lineage.server.model.weaponskill;

import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.datatables.WeaponSkillPowerTable;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;



public class WeaponSkillStart
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(WeaponSkillStart.class);
        _random = new Random();
    }
    
    public static double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            if (weapon == null) {
                return 0.0;
            }
            else {
                ArrayList<L1WeaponSkillType> list = WeaponSkillPowerTable.get().getTemplate(weapon.getItemId());
                if (list != null) {
                    L1WeaponSkillType tmp = list.get(WeaponSkillStart._random.nextInt(list.size()));
                    if (tmp != null) {
                        if (tmp.get_boss_holdout() && target instanceof L1NpcInstance) {
                            L1NpcInstance npc = (L1NpcInstance)target;
                            if (npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
                                return 0.0;
                            }
                        }
                        return tmp.start_weapon_skill(pc, target, weapon, srcdmg);
                    }
                }
                return 0.0;
            }
        }
        catch (Exception e) {
            WeaponSkillStart._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }

    /**
     * 取得武器的所有技能實例 (Get all skill instances for a weapon)
     * @param weapon 武器實例 (Weapon instance)
     * @return 武器技能實例列表 (List of weapon skill instances)
     */
    public static ArrayList<L1WeaponSkillType> getWeaponSkillsForWeapon(L1ItemInstance weapon) {
        if (weapon == null) {
            return null;
        }
        // WeaponSkillPowerTable 儲存了 weapon.getItemId() 對應的 L1WeaponSkillType 列表
        // (WeaponSkillPowerTable stores the list of L1WeaponSkillType corresponding to weapon.getItemId())
        // 這裡直接返回該列表 (Return the list directly here)
        return WeaponSkillPowerTable.get().getTemplate(weapon.getItemId());
    }

}
