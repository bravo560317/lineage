package com.lineage.server.model.weaponskill;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 武器技能(編號4): 吸取魔力<br>
 * 攻擊時，機率性發動，吸取目標的MP，並轉移給攻擊者。
 */
public class W_SK004 extends L1WeaponSkillType
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(W_SK004.class);
        _random = new Random();
    }
    
    public static L1WeaponSkillType get() {
        return new W_SK004();
    }
    
    /**
     * 啟動武器技能
     * @param pc 攻擊者(玩家)
     * @param target 目標
     * @param weapon 觸發技能的武器
     * @param srcdmg 原始傷害 (此技能未使用)
     * @return double 額外增加的傷害 (此技能為0.0，因為是特殊效果，不直接增加傷害)
     */
    @Override
    public double start_weapon_skill(L1PcInstance pc, L1Character target, L1ItemInstance weapon, double srcdmg) {
        try {
            // 檢查目標是否還有魔力，如果MP小於等於0，則不發動技能
            if (target.getCurrentMp() <= 0) {
                return 0.0;
            }

            // 技能發動機率檢查，此處的 random(weapon) 方法已包含武器強化等級的加成
            // W_SK004._random.nextInt(1000) 產生一個 0-999 的隨機數作為門檻
            if (this.random(weapon) < W_SK004._random.nextInt(1000)) {
                return 0.0; // 未達到發動門檻，直接返回
            }

            // --- 技能發動成功，開始計算吸魔量 ---

            // 基礎吸魔量，由 _type1 決定 (通常在資料庫 weapon_skill_power 中設定)
            int mpDrained = 0;
            if (this._type1 > 0) {
                mpDrained = W_SK004._random.nextInt(this._type1) + 1;
            }

            // 根據 _type3 的設定，決定是否受武器強化等級影響
            // 如果 _type3 為 1 且武器強化等級 > 0，則額外增加吸魔量
            if (this._type3 == 1 && weapon.getEnchantLevel() > 0) {
                mpDrained += W_SK004._random.nextInt(weapon.getEnchantLevel()) + 1;
            }

            // 最終吸魔量不能超過目標現有MP，且不能超過技能設定的最大值 _type2
            mpDrained = Math.min(mpDrained, target.getCurrentMp());
            mpDrained = Math.min(mpDrained, this._type2);

            // 更新目標和攻擊者的MP
            target.setCurrentMp(target.getCurrentMp() - mpDrained);
            pc.setCurrentMp(pc.getCurrentMp() + mpDrained);

            return 0.0; // 吸魔效果不產生額外傷害
        }
        catch (Exception e) {
            W_SK004._log.error(e.getLocalizedMessage(), e);
            return 0.0;
        }
    }
}
