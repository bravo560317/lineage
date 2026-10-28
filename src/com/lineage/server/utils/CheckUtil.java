package com.lineage.server.utils;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.templates.L1Item;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.NpcTeleportTable;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class CheckUtil
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(CheckUtil.class);
    }
    
    private CheckUtil() {
    }
    
    public static L1PcInstance checkAtkPc(L1Character lastAttacker) {
        try {
            L1PcInstance pc = null;
            if (lastAttacker instanceof L1PcInstance) {
                pc = (L1PcInstance)lastAttacker;
            }
            else if (lastAttacker instanceof L1PetInstance) {
                L1PetInstance atk = (L1PetInstance)lastAttacker;
                if (atk.getMaster() != null && atk.getMaster() instanceof L1PcInstance) {
                    pc = (L1PcInstance)atk.getMaster();
                }
            }
            else if (lastAttacker instanceof L1SummonInstance) {
                L1SummonInstance atk2 = (L1SummonInstance)lastAttacker;
                if (atk2.getMaster() != null && atk2.getMaster() instanceof L1PcInstance) {
                    pc = (L1PcInstance)atk2.getMaster();
                }
            }
            else if (lastAttacker instanceof L1IllusoryInstance) {
                L1IllusoryInstance atk3 = (L1IllusoryInstance)lastAttacker;
                if (atk3.getMaster() != null && atk3.getMaster() instanceof L1PcInstance) {
                    pc = (L1PcInstance)atk3.getMaster();
                }
            }
            else if (lastAttacker instanceof L1EffectInstance) {
                L1EffectInstance atk4 = (L1EffectInstance)lastAttacker;
                if (atk4.getMaster() != null && atk4.getMaster() instanceof L1PcInstance) {
                    pc = (L1PcInstance)atk4.getMaster();
                }
            }
            else {
                return null;
            }
            return pc;
        }
        catch (Exception e) {
            CheckUtil._log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    
    public static void isUserMap(L1PcInstance pc) {
        try {
            if (!pc.isGm()) {
                isTimeMap(pc);
                isPartyMap(pc);
            }
        }
        catch (Exception e) {
            CheckUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void isPartyMap(L1PcInstance pc) {
        try {
            short mapid = pc.getMapId();
            Integer userCount = NpcTeleportTable.get().isPartyMap((int)mapid);
            if (userCount != null) {
                if (!pc.isInParty()) {
                    pc.sendPackets(new S_ServerMessage(425));
                    L1Teleport.teleport(pc, 33080, 33392, (short)4, 5, true);
                    return;
                }
                else {
                    int partyCount = pc.getParty().partyUserInMap(mapid);
                    if (partyCount < userCount) {
                        L1Teleport.teleport(pc, 33080, 33392, (short)4, 5, true);
                    }
                }
            }
        }
        catch (Exception e) {
            CheckUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void isTimeMap(L1PcInstance pc) {
        try {
            short mapid = pc.getMapId();
            if (NpcTeleportTable.get().isTimeMap((int)mapid)) {
                Integer time = ServerUseMapTimer.MAP.get(pc);
                if (time == null) {
                    L1Teleport.teleport(pc, 33080, 33392, (short)4, 5, true);
                }
            }
        }
        catch (Exception e) {
            CheckUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static boolean getUseItem(L1PcInstance pc, L1Item item) {
        if (item == null) {
            return false;
        }
        else if (pc.hasSkillEffect(71) && item.getType() == 6) {
            pc.sendPackets(new S_ServerMessage(698));
            return false;
        }
        else {
            return !pc.hasSkillEffect(157) && !pc.hasSkillEffect(50);
        }
    }
    
    public static boolean getUseItemAll(L1PcInstance pc) {
        return !pc.hasSkillEffect(1011) && !pc.hasSkillEffect(1009) && !pc.hasSkillEffect(66) && !pc.hasSkillEffect(87) && !pc.hasSkillEffect(212) && !pc.hasSkillEffect(50) && !pc.hasSkillEffect(157) && !pc.hasSkillEffect(103) && !pc.hasSkillEffect(208);
    }
    
    public static boolean getUseItem(L1PcInstance pc) {
        if (pc.hasSkillEffect(71)) {
            pc.sendPackets(new S_ServerMessage(698));
            return false;
        }
        else {
            return !pc.hasSkillEffect(1011) && !pc.hasSkillEffect(1009) && !pc.hasSkillEffect(66) && !pc.hasSkillEffect(87) && !pc.hasSkillEffect(212) && !pc.hasSkillEffect(50) && !pc.hasSkillEffect(157) && !pc.hasSkillEffect(103) && !pc.hasSkillEffect(208);
        }
    }
    
    public static boolean checkPassable(L1PcInstance pc, int locx, int locy, short mapid) {
        if (QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
            return false;
        }
        else {
            Collection<L1Object> allObj = (Collection)World.get().getVisibleObjects(pc, 1);
            for (L1Object obj : allObj) {
                if (obj instanceof L1ItemInstance) {
                    continue;
                }
                else if (!(obj instanceof L1Character)) {
                    continue;
                }
                else {
                    L1Character character = (L1Character)obj;
                    if (character.isInvisble()) {
                        continue;
                    }
                    else if (character instanceof L1NpcInstance) {
                        continue;
                    }
                    else if (character instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)character;
                        if (pc.isGmInvis()) {
                            continue;
                        }
                        else if (pc.isGhost()) {
                            continue;
                        }
                        else if (tgpc.getX() == locx && tgpc.getY() == locy && tgpc.getMapId() == mapid) {
                            return true;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
            return false;
        }
    }
    
    public static boolean checkAttackSkill(L1Character cha) {
        try {
            if (cha instanceof L1EffectInstance) {
                return false;
            }
            else if (cha instanceof L1IllusoryInstance) {
                return false;
            }
        }
        catch (Exception e) {
            CheckUtil._log.error(e.getLocalizedMessage(), e);
        }
        return true;
    }
    
    public static boolean checkPassablePc(L1PcInstance pc, int locx, int locy, short mapid) {
        Collection<L1Object> allObj = (Collection)World.get().getVisibleObjects(pc, 1);
        for (L1Object obj : allObj) {
            if (obj instanceof L1ItemInstance) {
                continue;
            }
            else if (!(obj instanceof L1Character)) {
                continue;
            }
            else {
                L1Character character = (L1Character)obj;
                if (character.isInvisble()) {
                    continue;
                }
                else if (character instanceof L1PcInstance) {
                    L1PcInstance tgpc = (L1PcInstance)character;
                    if (tgpc.isGmInvis()) {
                        continue;
                    }
                    else if (tgpc.isGhost()) {
                        continue;
                    }
                    else if (tgpc.isDead()) {
                        continue;
                    }
                    else if (tgpc.getX() == locx && tgpc.getY() == locy && tgpc.getMapId() == mapid) {
                        return true;
                    }
                    else {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }
        }
        return false;
    }
}
