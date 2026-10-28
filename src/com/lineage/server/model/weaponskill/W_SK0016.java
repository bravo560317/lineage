package com.lineage.server.model.weaponskill;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class W_SK0016 extends L1WeaponSkillType {

    private static final Log _log = LogFactory.getLog(W_SK0016.class);
    private static final Random _random = new Random();

    public static L1WeaponSkillType get() {
        return new W_SK0016();
    }

    public W_SK0016() {
    }

    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        // W_SK0016 技能本身不執行任何特殊攻擊邏輯，僅用於標記武器為免箭矢並判斷強化等級。
        // 這裡不需要額外的邏輯，因為免箭矢的判斷已經移到 L1AttackPc 中。
        return 0.0;
    }

    
    @Override
    public double get_level() {
        // 該技能沒有等級發動百分比，直接返回0.0
        return 0.0;
    }
}