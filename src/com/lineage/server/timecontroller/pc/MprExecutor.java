package com.lineage.server.timecontroller.pc;

import java.util.Iterator;
import com.lineage.config.ConfigOther;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1HouseLocation;
import com.lineage.server.datatables.MapHprMprTable;
import com.lineage.server.datatables.RevertHpMp;
import java.util.ConcurrentModificationException;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class MprExecutor
{
    private static final Log _log;
    private static final Map<Integer, Integer> _skill;
    private static final Map<Integer, Integer> _mapId;
    private static MprExecutor _instance;
    
    static {
        _log = LogFactory.getLog(MprExecutor.class);
        _skill = new HashMap();
        _mapId = new HashMap();
    }
    
    protected static MprExecutor get() {
        if (MprExecutor._instance == null) {
            MprExecutor._instance = new MprExecutor();
        }
        return MprExecutor._instance;
    }
    
    private MprExecutor() {
        MprExecutor._skill.put(32, 5);
        MprExecutor._skill.put(206, 2);
        MprExecutor._skill.put(3002, 3);
        MprExecutor._skill.put(3010, 3);
        MprExecutor._skill.put(3020, 2);
        MprExecutor._skill.put(3028, 2);
        MprExecutor._skill.put(3037, 2);
        MprExecutor._skill.put(3045, 2);
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
            else if (tgpc.getCurrentMp() >= tgpc.getMaxMp()) {
                return false;
            }
        }
        catch (Exception e) {
            MprExecutor._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }
    
    protected void checkRegenMp(L1PcInstance tgpc) {
        try {
            tgpc.set_mpRegenType(tgpc.mpRegenType() + tgpc.getMpRegenState());
            tgpc.setRegenState(4);
            if (tgpc.isRegenMp()) {
                regenMp(tgpc);
            }
        }
        catch (Exception e) {
            MprExecutor._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void regenMp(L1PcInstance tgpc) {
        tgpc.set_mpRegenType(0);
        if (tgpc.getMapId() == 201) {
            return;
        }
        else {
            int baseMpr = 1;
            switch (tgpc.getWis()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: {
                    baseMpr = 1;
                    break;
                }
                case 15:
                case 16: {
                    baseMpr = 2;
                    break;
                }
                default: {
                    baseMpr = 3;
                    break;
                }
            }
            if (!tgpc.getSkillisEmpty() && tgpc.getSkillEffect().size() > 0) {
                try {
                    for (Integer key : tgpc.getSkillEffect()) {
                        if (key.equals(32)) {
                            if (tgpc.getHpRegenState() != 4) {
                                tgpc.killSkillEffectTimer(32);
                                continue;
                            }
                            else {
                                int sec = tgpc.getSkillEffectTimeSec(32);
                                if (!tgpc.hasSkillEffect(1002)) {
                                    baseMpr += 5;
                                }
                                else {
                                    baseMpr += 5 + 2 * Math.min((640 - sec) / 32, 8);
                                }
                            }
                        }
                        if (key.equals(1002)) {
                            baseMpr += Math.max(tgpc.getWis() - 10, 1);
                        }
                        else {
                            Integer integer = MprExecutor._skill.get(key);
                            if (integer != null) {
                                baseMpr += integer;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                catch (ConcurrentModificationException ex) {}
                catch (Exception e) {
                    MprExecutor._log.error(e.getLocalizedMessage(), e);
                }
            }
            for (int i = 0; i <= RevertHpMp.get().RevertSize(); ++i) {
                RevertHpMp r = RevertHpMp.get().getRevert(i);
                if (r != null && tgpc.getMapId() == r.getMapId()) {
                    if (r.getSx() == 0) {
                        baseMpr += r.getMpr();
                        break;
                    }
                    else if (tgpc.getX() >= r.getSx() && tgpc.getX() <= r.getEx() && tgpc.getY() >= r.getSy() && tgpc.getY() <= r.getEy()) {
                        baseMpr += r.getMpr();
                        break;
                    }
                }
            }
            short mapid = tgpc.getMapId();
            int mapmp = MapHprMprTable.get().getMapMpr((int)mapid);
            int maphp1 = MapHprMprTable.get().getMapMpr(32767);
            if (mapmp != 0) {
                baseMpr += mapmp;
            }
            if (L1HouseLocation.isInHouse(tgpc.getX(), tgpc.getY(), tgpc.getMapId())) {
                baseMpr += maphp1;
            }
            Integer rmp = MprExecutor._mapId.get((Short)tgpc.getMapId());
            if (rmp != null) {
                baseMpr += rmp;
            }
            if (tgpc.isElf() && tgpc.getMapId() == 4 && tgpc.getLocation().isInScreen(new Point(33055, 32336))) {
                baseMpr += ConfigOther.FORESTMPR;
            }
            if (tgpc.getOriginalMpr() > 0) {
                baseMpr += tgpc.getOriginalMpr();
            }
            int itemMpr = tgpc.getInventory().mpRegenPerTick();
            itemMpr += tgpc.getMpr();
            int mpr = baseMpr + itemMpr;
            int newMp = tgpc.getCurrentMp() + mpr;
            newMp = Math.max(newMp, 0);
            tgpc.setCurrentMp(newMp);
            return;
        }
    }
}
