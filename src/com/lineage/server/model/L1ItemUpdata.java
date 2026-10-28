package com.lineage.server.model;

import com.lineage.config.ConfigRate;

/**
 * 強化成功率計算（武器 / 防具）
 */
public class L1ItemUpdata {

    /* =========================
     * 武器強化
     * ========================= */

    // 武器：高階強化（+9 以上）
    public static double enchant_wepon_up9(double enchantLevel) {
        if (enchantLevel <= 0.0) {
            enchantLevel = 1.0;
        }

        return (100.0 + enchantLevel * ConfigRate.ENCHANT_CHANCE_WEAPON)
                / (enchantLevel * 1.2);
    }

    // 武器：低階強化（+0 ~ +8）
    public static double enchant_wepon_dn9(double enchantLevel) {
        if (enchantLevel <= 0.0) {
            enchantLevel = 1.0;
        }

        return (100.0 + enchantLevel * ConfigRate.ENCHANT_CHANCE_WEAPON)
                / enchantLevel;
    }

    /* =========================
     * 防具強化
     * ========================= */

    // 防具：高階強化（+9 以上）
    public static double enchant_armor_up9(double enchantLevel) {
        if (enchantLevel <= 0.0) {
            enchantLevel = 1.0;
        }

        // +9 以上懲罰倍率（比武器更嚴格）
        return (100.0 + enchantLevel * ConfigRate.ENCHANT_CHANCE_ARMOR)
                / (enchantLevel * 2.5);
    }

    // 防具：低階強化（+0 ~ +8）
    public static double enchant_armor_dn9(double enchantLevel) {
        if (enchantLevel <= 0.0) {
            enchantLevel = 1.0;
        }

        // +7 開始加重懲罰
        if (enchantLevel >= 7) {
            return (100.0 + enchantLevel * ConfigRate.ENCHANT_CHANCE_ARMOR)
                    / (enchantLevel * 2.0);
        }

        // +0 ~ +6
        return (100.0 + enchantLevel * ConfigRate.ENCHANT_CHANCE_ARMOR)
                / enchantLevel;
    }
}
