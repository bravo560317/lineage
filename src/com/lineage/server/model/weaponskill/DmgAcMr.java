package com.lineage.server.model.weaponskill;

import com.lineage.server.model.L1AttackList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class DmgAcMr
{
    private static final Log _log;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(DmgAcMr.class);
        _random = new Random();
    }
    
    public static int calcDefense(L1Character target) {
        try {
            if (target instanceof L1PcInstance) {
                L1PcInstance targetPc = (L1PcInstance)target;
                int ac = Math.max(0, 10 - targetPc.getAc());
                int acDefMax = targetPc.getClassFeature().getAcDefenseMax(ac);
                if (acDefMax != 0) {
                    int srcacd = Math.max(1, acDefMax >> 3);
                    return DmgAcMr._random.nextInt(acDefMax) + srcacd;
                }
            }
            else if (target instanceof L1MonsterInstance) {
                L1MonsterInstance targetNpc = (L1MonsterInstance)target;
                int damagereduction = targetNpc.getNpcTemplate().get_damagereduction();
                int srcac = targetNpc.getAc();
                int ac2 = Math.max(0, 10 - srcac);
                int acDefMax2 = ac2 / 7;
                if (acDefMax2 != 0) {
                    int srcacd2 = Math.max(1, acDefMax2);
                    return DmgAcMr._random.nextInt(acDefMax2) + srcacd2 + damagereduction;
                }
            }
        }
        catch (Exception e) {
            DmgAcMr._log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }
    
    public static double calcMrDefense(L1PcInstance pc, L1Character target, double dmg, L1ItemInstance weapon) {
        if (weapon.getItem().getType1() == 20) {
            return dmg;
        }
        else {
            int TotalMagicHit = 0;
            Integer magichit = L1AttackList.INTH.get((Integer)(int)pc.getInt());
            if (magichit != null) {
                TotalMagicHit += magichit;
            }
            if (pc.getBaseInt() >= 25 && pc.getBaseInt() <= 44) {
                TotalMagicHit += (pc.getBaseInt() - 15) / 10;
            }
            else if (pc.getBaseInt() >= 45) {
                TotalMagicHit += 5;
            }
            TotalMagicHit += pc.getOriginalMagicHit();
            int mr = getTargetMr(target);
            double mrFloor = 0.0;
            if (mr < 100) {
                mrFloor = Math.floor((double)((mr - TotalMagicHit) / 2));
            }
            else if (mr >= 100) {
                mrFloor = Math.floor((double)((mr - TotalMagicHit) / 10));
            }
            double mrCoefficient = 0.0;
            if (mr < 100) {
                mrCoefficient = 1.0 - 0.01 * mrFloor;
            }
            else if (mr >= 100) {
                mrCoefficient = 0.6 - 0.01 * mrFloor;
            }
            dmg *= mrCoefficient;
            return dmg;
        }
    }
    
    private static int getTargetMr(L1Character target) {
        int mr = 0;
        if (target instanceof L1PcInstance) {
            L1PcInstance targetPc = (L1PcInstance)target;
            mr = targetPc.getMr();
            switch (targetPc.guardianEncounter()) {
                case 0: {
                    mr += 3;
                    break;
                }
                case 1: {
                    mr += 6;
                    break;
                }
                case 2: {
                    mr += 9;
                    break;
                }
            }
        }
        else if (target instanceof L1MonsterInstance) {
            L1MonsterInstance targetNpc = (L1MonsterInstance)target;
            mr = targetNpc.getMr();
        }
        return mr;
    }
    
    public static double getDamage(L1PcInstance pc) {
        double dmg = 0.0;
        int spByItem = pc.getSp() - pc.getTrueSp();
        int intel = pc.getInt();
        int charaIntelligence = pc.getInt() + spByItem - 12;
        double coefficientA = 1.0 + 0.09375 * (double)charaIntelligence;
        if (coefficientA < 1.0) {
            coefficientA = 1.0;
        }
        double coefficientB = 0.0;
        if (intel > 25) {
            coefficientB = 1.625;
        }
        else if (intel <= 12) {
            coefficientB = 0.78;
        }
        else {
            coefficientB = (double)intel * 0.065;
        }
        double coefficientC = 0.0;
        if (intel > 25) {
            coefficientC = 25.0;
        }
        else if (intel <= 12) {
            coefficientC = 12.0;
        }
        else {
            coefficientC = (double)intel;
        }
        double bsk = 0.0;
        if (pc.hasSkillEffect(55)) {
            bsk = 0.1;
        }
        dmg = (double)(DmgAcMr._random.nextInt(6) + 1 + 7) * (1.0 + bsk) * coefficientA * coefficientB / 10.5 * coefficientC;
        return dmg;
    }
    
    public static double getSpInt(L1PcInstance pc) {
        int spByItem = pc.getSp() - pc.getTrueSp();
        int intel = pc.getInt();
        return (double)((spByItem + intel) * 3);
    }
    
    public static double calcMrDefense2(L1PcInstance pc, L1Character target, double dmg, L1ItemInstance weapon) {
        int mr = getTargetMr(target);
        double mrFloor = 0.0;
        if (mr < 100) {
            mrFloor = Math.floor((double)((mr - pc.getOriginalMagicHit()) / 2));
        }
        else if (mr >= 100) {
            mrFloor = Math.floor((double)((mr - pc.getOriginalMagicHit()) / 10));
        }
        double mrCoefficient = 0.0;
        if (mr < 100) {
            mrCoefficient = 1.0 - 0.01 * mrFloor;
        }
        else if (mr >= 100) {
            mrCoefficient = 0.6 - 0.01 * mrFloor;
        }
        dmg *= mrCoefficient;
        return dmg;
    }
    
    public static double getDamageStr(L1PcInstance pc) {
        double dmg = 0.0;
        int intel = pc.getStr();
        int charaIntelligence = pc.getStr();
        double coefficientA = 1.0 + 0.09375 * (double)charaIntelligence;
        if (coefficientA < 1.0) {
            coefficientA = 1.0;
        }
        double coefficientB = 0.0;
        if (intel > 25) {
            coefficientB = 1.625;
        }
        else if (intel <= 12) {
            coefficientB = 0.78;
        }
        else {
            coefficientB = (double)intel * 0.065;
        }
        double coefficientC = 0.0;
        if (intel > 25) {
            coefficientC = 25.0;
        }
        else if (intel <= 12) {
            coefficientC = 12.0;
        }
        else {
            coefficientC = (double)intel;
        }
        double bsk = 0.0;
        if (pc.hasSkillEffect(55)) {
            bsk = 0.1;
        }
        dmg = (double)(DmgAcMr._random.nextInt(6) + 1 + 7) * (1.0 + bsk) * coefficientA * coefficientB / 10.5 * coefficientC;
        return dmg;
    }
    
    public static double getDamageDex(L1PcInstance pc) {
        double dmg = 0.0;
        int intel = pc.getDex();
        int charaIntelligence = pc.getDex();
        double coefficientA = 1.0 + 0.09375 * (double)charaIntelligence;
        if (coefficientA < 1.0) {
            coefficientA = 1.0;
        }
        double coefficientB = 0.0;
        if (intel > 25) {
            coefficientB = 1.625;
        }
        else if (intel <= 12) {
            coefficientB = 0.78;
        }
        else {
            coefficientB = (double)intel * 0.065;
        }
        double coefficientC = 0.0;
        if (intel > 25) {
            coefficientC = 25.0;
        }
        else if (intel <= 12) {
            coefficientC = 12.0;
        }
        else {
            coefficientC = (double)intel;
        }
        double bsk = 0.0;
        if (pc.hasSkillEffect(55)) {
            bsk = 0.1;
        }
        dmg = (double)(DmgAcMr._random.nextInt(6) + 1 + 7) * (1.0 + bsk) * coefficientA * coefficientB / 10.5 * coefficientC;
        return dmg;
    }
    
    public static double getDamageInt(L1PcInstance pc) {
        double dmg = 0.0;
        int intel = pc.getInt();
        int charaIntelligence = pc.getInt();
        double coefficientA = 1.0 + 0.09375 * (double)charaIntelligence;
        if (coefficientA < 1.0) {
            coefficientA = 1.0;
        }
        double coefficientB = 0.0;
        if (intel > 25) {
            coefficientB = 1.625;
        }
        else if (intel <= 12) {
            coefficientB = 0.78;
        }
        else {
            coefficientB = (double)intel * 0.065;
        }
        double coefficientC = 0.0;
        if (intel > 25) {
            coefficientC = 25.0;
        }
        else if (intel <= 12) {
            coefficientC = 12.0;
        }
        else {
            coefficientC = (double)intel;
        }
        double bsk = 0.0;
        if (pc.hasSkillEffect(55)) {
            bsk = 0.1;
        }
        dmg = (double)(DmgAcMr._random.nextInt(6) + 1 + 7) * (1.0 + bsk) * coefficientA * coefficientB / 10.5 * coefficientC;
        return dmg;
    }
}
