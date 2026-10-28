package com.lineage.server.timecontroller.pc;

import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.L1Object;
import java.util.Iterator;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1HouseLocation;
import com.lineage.server.datatables.MapHprMprTable;
import com.lineage.server.datatables.RevertHpMp;
import java.util.ConcurrentModificationException;
import java.util.Random;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class HprExecutor
{
    private static final Log _log;
    private static final Map<Integer, Integer> _skill;
    private static final Map<Integer, Integer> _mapIdU;
    private static final Map<Integer, Integer> _mapIdD;
    private static HprExecutor _instance;
    
    static {
        _log = LogFactory.getLog(HprExecutor.class);
        _skill = new HashMap();
        _mapIdU = new HashMap();
        _mapIdD = new HashMap();
    }
    
    protected static HprExecutor get() {
        if (HprExecutor._instance == null) {
            HprExecutor._instance = new HprExecutor();
        }
        return HprExecutor._instance;
    }
    
    private HprExecutor() {
        HprExecutor._skill.put(158, 15);
        HprExecutor._skill.put(3005, 3);
        HprExecutor._skill.put(3013, 3);
        HprExecutor._skill.put(3020, 2);
        HprExecutor._skill.put(3028, 2);
        HprExecutor._skill.put(3038, 2);
        HprExecutor._skill.put(3046, 2);
        HprExecutor._mapIdD.put(410, (Integer)(-10));
        HprExecutor._mapIdD.put(2000, (Integer)(-10));
        HprExecutor._mapIdD.put(306, (Integer)(-10));
    }
    
    protected boolean check(L1PcInstance tgpc) {
        try {
            if (tgpc == null) {
                return false;
            }
            else if (tgpc.getOnlineStatus() == 0) {
                return false;
            }
            else if (tgpc.getNetConnection() == null) {
                return false;
            }
            else if (tgpc.isDead()) {
                return false;
            }
            else if (tgpc.isTeleport()) {
                return false;
            }
            else {
                Integer dhp = HprExecutor._mapIdD.get(new Integer((int)tgpc.getMapId()));
                if (dhp != null) {
                    return true;
                }
                else if (isUnderwater(tgpc)) {
                    return true;
                }
                else if (tgpc.getCurrentHp() >= tgpc.getMaxHp()) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            HprExecutor._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
    
    protected void checkRegenHp(L1PcInstance tgpc) {
        try {
            tgpc.set_hpRegenType(tgpc.hpRegenType() + tgpc.getHpRegenState());
            tgpc.setRegenState(4);
            if (tgpc.isRegenHp()) {
                regenHp(tgpc);
            }
        }
        catch (Exception e) {
            HprExecutor._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void regenHp(L1PcInstance tgpc) {
        tgpc.set_hpRegenType(0);
        int maxBonus = 1;
        if (tgpc.getLevel() > 11 && tgpc.getCon() >= 14) {
            maxBonus = Math.min(tgpc.getCon() - 12, 14);
        }
        int equipHpr = tgpc.getInventory().hpRegenPerTick();
        equipHpr += tgpc.getHpr();
        Random random = new Random();
        int bonus = random.nextInt(maxBonus) + 1;
        if (!tgpc.getSkillisEmpty() && tgpc.getSkillEffect().size() > 0) {
            try {
                for (Integer skillid : HprExecutor._skill.keySet()) {
                    if (tgpc.hasSkillEffect(skillid)) {
                        Integer integer = HprExecutor._skill.get(skillid);
                        if (integer != null) {
                            bonus += integer;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            catch (ConcurrentModificationException ex) {}
            catch (Exception e) {
                HprExecutor._log.error(e.getLocalizedMessage(), e);
            }
        }
        for (int i = 0; i <= RevertHpMp.get().RevertSize(); ++i) {
            RevertHpMp r = RevertHpMp.get().getRevert(i);
            if (r != null && tgpc.getMapId() == r.getMapId()) {
                if (r.getSx() == 0) {
                    bonus += r.getHpr();
                    break;
                }
                else if (tgpc.getX() >= r.getSx() && tgpc.getX() <= r.getEx() && tgpc.getY() >= r.getSy() && tgpc.getY() <= r.getEy()) {
                    bonus += r.getHpr();
                    break;
                }
            }
        }
        short mapid = tgpc.getMapId();
        int maphp = MapHprMprTable.get().getMapHpr(tgpc, (int)mapid);
        int maphp2 = MapHprMprTable.get().getMapHpr(tgpc, 32767);
        if (maphp != 0) {
            bonus += maphp;
        }
        if (L1HouseLocation.isInHouse(tgpc.getX(), tgpc.getY(), tgpc.getMapId())) {
            bonus += maphp2;
        }
        Integer rhp = HprExecutor._mapIdU.get(new Integer((int)tgpc.getMapId()));
        if (rhp != null) {
            bonus += rhp;
        }
        if (tgpc.isElf() && tgpc.getMapId() == 4 && tgpc.getLocation().isInScreen(new Point(33055, 32336))) {
            bonus += 5;
        }
        if (tgpc.getOriginalHpr() > 0) {
            bonus += tgpc.getOriginalHpr();
        }
        boolean inLifeStream = false;
        if (isPlayerInLifeStream(tgpc)) {
            inLifeStream = true;
            bonus += 3;
        }
        int newHp = tgpc.getCurrentHp();
        newHp += bonus + equipHpr;
        newHp = Math.max(newHp, 1);
        if (isUnderwater(tgpc)) {
            newHp -= 20;
        }
        Integer dhp = HprExecutor._mapIdD.get(new Integer((int)tgpc.getMapId()));
        if (dhp != null && !inLifeStream) {
            bonus += dhp;
        }
        newHp = Math.max(newHp, 0);
        tgpc.setCurrentHp(newHp);
    }
    
    private static boolean isUnderwater(L1PcInstance pc) {
        return !pc.getInventory().checkEquipped(20207) && !pc.hasSkillEffect(1003) && (!pc.getInventory().checkEquipped(21048) || !pc.getInventory().checkEquipped(21049) || !pc.getInventory().checkEquipped(21050)) && pc.getMap().isUnderwater();
    }
    
    private static boolean isPlayerInLifeStream(L1PcInstance pc) {
        for (L1Object object : pc.getKnownObjects()) {
            if (object instanceof L1EffectInstance) {
                L1EffectInstance effect = (L1EffectInstance)object;
                if (effect.getNpcId() == 81169 && effect.getLocation().getTileLineDistance(pc.getLocation()) < 4) {
                    return true;
                }
                else {
                    continue;
                }
            }
        }
        return false;
    }
}
