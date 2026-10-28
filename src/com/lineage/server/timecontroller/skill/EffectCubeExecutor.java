package com.lineage.server.timecontroller.skill;

import com.lineage.server.model.L1Cube;
import com.lineage.server.model.Instance.L1EffectType;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1Character;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1EffectInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class EffectCubeExecutor
{
    private static final Log _log;
    private static EffectCubeExecutor _instance;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType;
    
    static {
        _log = LogFactory.getLog(EffectCubeExecutor.class);
    }
    
    protected static EffectCubeExecutor get() {
        if (EffectCubeExecutor._instance == null) {
            EffectCubeExecutor._instance = new EffectCubeExecutor();
        }
        return EffectCubeExecutor._instance;
    }
    
    protected void cubeBurn(L1EffectInstance effect) {
        try {
            for (L1Object objects : World.get().getVisibleObjects(effect, 3)) {
                if (objects == null) {
                    continue;
                }
                else if (objects instanceof L1PcInstance) {
                    L1PcInstance pc = (L1PcInstance)objects;
                    if (pc.isDead()) {
                        continue;
                    }
                    else if (effect.get_showId() != pc.get_showId()) {
                        continue;
                    }
                    else if (effect.getMaster() == null) {
                        continue;
                    }
                    else {
                        L1PcInstance user = (L1PcInstance)effect.getMaster();
                        if (pc.getId() == user.getId()) {
                            cubeToAlly(pc, effect);
                        }
                        else if (pc.getClanid() != 0 && user.getClanid() == pc.getClanid()) {
                            cubeToAlly(pc, effect);
                        }
                        else if (pc.isInParty() && pc.getParty().isMember(user)) {
                            cubeToAlly(pc, effect);
                        }
                        else if (pc.isSafetyZone()) {
                            boolean isNowWar = false;
                            int castleId = L1CastleLocation.getCastleIdByArea(pc);
                            if (castleId > 0) {
                                isNowWar = ServerWarExecutor.get().isNowWar(castleId);
                            }
                            if (!isNowWar) {
                                continue;
                            }
                            else {
                                cubeToEnemy(pc, effect);
                            }
                        }
                        else {
                            cubeToEnemy(pc, effect);
                        }
                    }
                }
                else if (objects instanceof L1MonsterInstance) {
                    L1MonsterInstance mob = (L1MonsterInstance)objects;
                    if (mob.isDead()) {
                        continue;
                    }
                    else {
                        cubeToEnemy(mob, effect);
                    }
                }
                else {
                    continue;
                }
            }
        }
        catch (Exception e) {
            EffectCubeExecutor._log.error("Npc L1Effect幻術師技能(立方)狀態送出時間軸發生異常", e);
            effect.deleteMe();
        }
    }
    
    private static void cubeToAlly(L1Character cha, L1EffectInstance effect) {
        int castGfx = SkillsTable.get().getTemplate(effect.getSkillId()).getCastGfx();
        L1PcInstance pc = null;
        int skillid = cubeToAllyId(effect.effectType());
        if (!cha.hasSkillEffect(skillid)) {
            switch ($SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType()[effect.effectType().ordinal()]) {
                case 2: {
                    cha.addFire(30);
                    break;
                }
                case 3: {
                    cha.addEarth(30);
                    break;
                }
                case 4: {
                    cha.addWind(30);
                    break;
                }
            }
            if (cha instanceof L1PcInstance) {
                pc = (L1PcInstance)cha;
                pc.sendPackets(new S_OwnCharAttrDef(pc));
                pc.sendPackets(new S_SkillSound(pc.getId(), castGfx));
            }
            cha.broadcastPacketX10(new S_SkillSound(cha.getId(), castGfx));
            cha.setSkillEffect(skillid, 8000);
            if (effect.effectType() == L1EffectType.isCubeHarmonize) {
                L1Cube cube = new L1Cube(effect, cha, 1025);
                cube.begin();
            }
        }
    }
    
    private static void cubeToEnemy(L1Character cha, L1EffectInstance effect) {
        int castGfx2 = SkillsTable.get().getTemplate(effect.getSkillId()).getCastGfx2();
        L1PcInstance pc = null;
        int skillid = cubeToEnemyId(effect.effectType());
        if (!cha.hasSkillEffect(skillid)) {
            if (cha instanceof L1PcInstance) {
                pc = (L1PcInstance)cha;
                pc.sendPackets(new S_SkillSound(pc.getId(), castGfx2));
            }
            cha.broadcastPacketX10(new S_SkillSound(cha.getId(), castGfx2));
            cha.setSkillEffect(skillid, 8000);
            L1Cube cube = new L1Cube(effect, cha, skillid);
            cube.begin();
        }
    }
    
    private static int cubeToAllyId(L1EffectType effectType) {
        int cubeToAllyId = 0;
        switch ($SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType()[effectType.ordinal()]) {
            case 2: {
                cubeToAllyId = 1018;
                break;
            }
            case 3: {
                cubeToAllyId = 1020;
                break;
            }
            case 4: {
                cubeToAllyId = 1022;
                break;
            }
            case 5: {
                cubeToAllyId = 1025;
                break;
            }
        }
        return cubeToAllyId;
    }
    
    private static int cubeToEnemyId(L1EffectType effectType) {
        int cubeToEnemyId = 0;
        switch ($SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType()[effectType.ordinal()]) {
            case 2: {
                cubeToEnemyId = 1019;
                break;
            }
            case 3: {
                cubeToEnemyId = 1021;
                break;
            }
            case 4: {
                cubeToEnemyId = 1023;
                break;
            }
            case 5: {
                cubeToEnemyId = 1025;
                break;
            }
        }
        return cubeToEnemyId;
    }
    
    static /* synthetic */ int[] $SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType() {
        int[] $switch_TABLE$com$lineage$server$model$Instance$L1EffectType = EffectCubeExecutor.$SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType;
        if ($switch_TABLE$com$lineage$server$model$Instance$L1EffectType != null) {
            return $switch_TABLE$com$lineage$server$model$Instance$L1EffectType;
        }
        else {
            int[] $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2 = new int[L1EffectType.values().length];
            try {
                $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2[L1EffectType.isCubeBurn.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2[L1EffectType.isCubeEruption.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2[L1EffectType.isCubeHarmonize.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2[L1EffectType.isCubeShock.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2[L1EffectType.isFirewall.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2[L1EffectType.isOther.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return EffectCubeExecutor.$SWITCH_TABLE$com$lineage$server$model$Instance$L1EffectType = $switch_TABLE$com$lineage$server$model$Instance$L1EffectType2;
        }
    }
}
