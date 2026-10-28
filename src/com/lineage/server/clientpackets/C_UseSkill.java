package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.world.World;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.PolyTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_UseSkill extends ClientBasePacket
{
    private static final Log _log;
    private static final int[] _cast_with_invis;
    
    static {
        _log = LogFactory.getLog(C_UseSkill.class);
        _cast_with_invis = new int[] { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 68, 69, 72, 73, 75, 78, 79, 88, 89, 90, 91, 97, 98, 99, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 133, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159, 163, 164, 165, 166, 168, 169, 170, 171, 175, 176, 181, 185, 190, 195, 201, 204, 209, 211, 214, 216, 219 };
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
if (pc == null) {
    _log.error("C_UseSkill: pc is null (maybe disconnected or packet too early)");
    return;
}
            if (!pc.isDead()) {
                if (!pc.isTeleport()) {
                    if (!pc.isPrivateShop()) {
                        if (!pc.hasSkillEffect(4017)) {
                            if (pc.get_MOVE_STOP()) {
                                pc.setTeleport(false);
                                pc.sendPackets(new S_Paralysis(7, false));
                            }
                            else if (!pc.getMap().isUsableSkill()) {
                                pc.sendPackets(new S_ServerMessage(563));
                            }
                            else {
                                boolean isError = false;
                                boolean isError2 = false;
                                boolean isError3 = false;
                                int polyId = pc.getTempCharGfx();
                                L1PolyMorph poly = PolyTable.get().getTemplate(polyId);
                                if (pc.getInventory().getWeight240() >= 197) {
                                    isError3 = true;
                                }
                                if (poly != null && !poly.canUseSkill()) {
                                    isError = true;
                                }
                                if (pc.isParalyzed() && !isError) {
                                    isError = true;
                                }
                                if (pc.hasSkillEffect(64) && !isError) {
                                    isError = true;
                                }
                                if (pc.hasSkillEffect(161) && !isError) {
                                    isError = true;
                                }
                                if (pc.hasSkillEffect(1007) && !isError) {
                                    isError = true;
                                }
                                if (pc.isParalyzedX() && !isError) {
                                    isError = true;
                                }
                                if (isError) {
                                    pc.sendPackets(new S_Paralysis(7, false));
                                    pc.sendPackets(new S_ServerMessage(285));
                                    return;
                                }
                                else if (isError3) {
                                    pc.sendPackets(new S_Paralysis(7, false));
                                    pc.sendPackets(new S_ServerMessage(316));
                                    return;
                                }
                                else if (isError2) {
                                    pc.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法使用魔法。"));
                                    return;
                                }
                                else {
                                    int row = this.readC();
                                    int column = this.readC();
                                    int skillId = (row << 3) + column + 1;
                                    int[] MAP_IDSKILL = ConfigOther.MAP_IDSKILL;
                                    int[] MAP_SKILL = ConfigOther.MAP_SKILL;
                                    int[] array;
                                    for (int length = (array = MAP_IDSKILL).length, i = 0; i < length; ++i) {
                                        int mapid = array[i];
                                        int[] array2;
                                        int length2 = (array2 = MAP_SKILL).length;
                                        int j = 0;
                                        while (j < length2) {
                                            int mapskill = array2[j];
                                            if (pc.getMapId() == mapid && skillId == mapskill) {
                                                pc.sendPackets(new S_ServerMessage("此地圖無法使用此魔法"));
                                                pc.sendPackets(new S_Paralysis(7, false));
                                                return;
                                            }
                                            else {
                                                ++j;
                                            }
                                        }
                                    }
                                    int[] bow_GFX_Arrow = ConfigGuaji.Guaji_map_stopskill;
                                    int[] array3;
                                    int length3 = (array3 = bow_GFX_Arrow).length;
                                    int k = 0;
                                    while (k < length3) {
                                        int gfx = array3[k];
                                        if (skillId == gfx && pc.isActived()) {
                                            pc.sendPackets(new S_ServerMessage("內掛中禁止施放此魔法"));
                                            return;
                                        }
                                        else {
                                            ++k;
                                        }
                                    }
                                    boolean castle_area = L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId());
                                    int[] bow_GFX_Arrow2 = ConfigOther.WAR_DISABLE_SKILLS;
                                    int[] array4;
                                    int length4 = (array4 = bow_GFX_Arrow2).length;
                                    int l = 0;
                                    while (l < length4) {
                                        int gfx2 = array4[l];
                                        if (skillId == gfx2 && castle_area && pc.castleWarResult()) {
                                            pc.sendPackets(new S_ServerMessage("戰爭旗幟內禁止使用此魔法"));
                                            return;
                                        }
                                        else {
                                            ++l;
                                        }
                                    }
                                    if (skillId == 11 && pc.getMap().isSafetyZone(pc.getLocation())) {
                                        pc.sendPackets(new S_ServerMessage("安全區無法施放此魔法"));
                                        return;
                                    }
                                    else if (skillId > 239) {
                                        return;
                                    }
                                    else if (skillId < 0) {
                                        return;
                                    }
                                    else if ((pc.isInvisble() || pc.isInvisDelay()) && !this.isInvisUsableSkill(skillId)) {
                                        pc.sendPackets(new S_ServerMessage(1003));
                                        return;
                                    }
                                    else if (!pc.isSkillMastery(skillId)) {
                                        return;
                                    }
                                    else {
                                        long now_time = System.currentTimeMillis();
                                        L1Skills _skill = SkillsTable.get().getTemplate(skillId);
                                        if (_skill.getdetect_no_delay() != 0 && now_time < pc.getskill_timeDelay() + (long)_skill.getReuseDelay()) {
                                            return;
                                        }
                                        else {
                                            CheckUtil.isUserMap(pc);
                                            String charName = new String();
                                            int targetId = 0;
                                            int targetX = 0;
                                            int targetY = 0;
                                            int result;
                                            if (SkillsTable.get().getTemplate(skillId).getActionId() == 18) {
                                                result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.SPELL_DIR);
                                            }
                                            else {
                                                result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.SPELL_NODIR);
                                            }
                                            if (result == 2) {
                                                C_UseSkill._log.error("要求角色技能攻擊:速度異常(" + pc.getName() + ")");
                                                return;
                                            }
                                            else {
                                                if (skillId == 18 && pc.hasSkillEffect(6931)) {
                                                    pc.killSkillEffectTimer(6931);
                                                    pc.setSkillEffect(6932, 2000);
                                                }
                                                if (decrypt.length > 4) {
                                                    try {
                                                        if (skillId == 116 || skillId == 118) {
                                                            String[] tempName = this.readS().split("\\[");
                                                            charName = tempName[0];
                                                        }
                                                        else if (skillId == 113) {
                                                            targetId = this.readD();
                                                            targetX = this.readH();
                                                            targetY = this.readH();
                                                            pc.setText(this.readS());
                                                        }
                                                        else if (skillId == 5 || skillId == 69) {
                                                            this.readH();
                                                            targetId = this.readD();
                                                        }
                                                        else if (skillId == 58 || skillId == 63) {
                                                            targetX = this.readH();
                                                            targetY = this.readH();
                                                        }
                                                        else if (skillId == 51) {
                                                            if (pc.getInventory().checkEquipped(20284) || pc.getInventory().checkEquipped(120284)) {
                                                                int summonId = this.readD();
                                                                pc.setSummonId(summonId);
                                                            }
                                                            else {
                                                                targetId = this.readD();
                                                            }
                                                        }
                                                        else {
                                                            targetId = this.readD();
                                                            targetX = this.readH();
                                                            targetY = this.readH();
                                                        }
                                                    }
                                                    catch (Exception ex) {}
                                                }
                                                if (pc.hasSkillEffect(78)) {
                                                    pc.killSkillEffectTimer(78);
                                                    pc.startHpRegeneration();
                                                    pc.startMpRegeneration();
                                                }
                                                pc.killSkillEffectTimer(32);
                                                try {
                                                    if (skillId == 116 || skillId == 118) {
                                                        if (charName.isEmpty()) {
                                                            return;
                                                        }
                                                        else {
                                                            L1PcInstance target = World.get().getPlayer(charName);
                                                            if (target == null) {
                                                                pc.sendPackets(new S_ServerMessage(73, charName));
                                                                return;
                                                            }
                                                            else if (pc.getClanid() != target.getClanid()) {
                                                                pc.sendPackets(new S_ServerMessage(414));
                                                                return;
                                                            }
                                                            else {
                                                                targetId = target.getId();
                                                                if (skillId == 116) {
                                                                    int callClanId = pc.getCallClanId();
                                                                    if (callClanId == 0 || callClanId != targetId) {
                                                                        pc.setCallClanId(targetId);
                                                                        pc.setCallClanHeading(pc.getHeading());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (pc.isWizard() && pc.getlogpcpower_SkillFor5() != 0 && _skill.getTarget().equals("attack")) {
                                                        if (pc.hasSkillEffect(5198)) {
                                                            if (pc.getEsotericSkill() == skillId) {
                                                                if (pc.getEsotericCount() < 4) {
                                                                    pc.setEsotericCount(pc.getEsotericCount() + 1);
                                                                    if (pc.getEsotericCount() == 2) {
                                                                        pc.sendPackets(new S_SystemMessage("魔擊累積 階段二已發動！"));
                                                                    }
                                                                    else if (pc.getEsotericCount() == 3) {
                                                                        pc.sendPackets(new S_SystemMessage("魔擊累積 階段三已發動！"));
                                                                    }
                                                                    else if (pc.getEsotericCount() == 4) {
                                                                        pc.sendPackets(new S_SystemMessage("魔擊累積 階段四已發動！"));
                                                                    }
                                                                }
                                                                else {
                                                                    pc.sendPackets(new S_SystemMessage("魔擊累積維持"));
                                                                }
                                                            }
                                                            else {
                                                                pc.setEsotericSkill(skillId);
                                                                pc.setEsotericCount(1);
                                                                pc.sendPackets(new S_SystemMessage("魔擊累積 階段一已發動！"));
                                                            }
                                                            pc.setSkillEffect(5198, 2000);
                                                        }
                                                        else {
                                                            pc.setEsotericSkill(skillId);
                                                            pc.setEsotericCount(1);
                                                            pc.setSkillEffect(5198, 2000);
                                                            pc.sendPackets(new S_SystemMessage("魔擊累積 階段一 已發動！"));
                                                        }
                                                    }
                                                    L1SkillUse l1skilluse = new L1SkillUse();
                                                    l1skilluse.handleCommands(pc, skillId, targetId, targetX, targetY, 0, 0);
                                                    if (_skill.getdetect_no_delay() != 0) {
                                                        pc.setskill_timeDelay(now_time);
                                                    }
                                                }
                                                catch (Exception ex2) {}
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        catch (Exception e) {
            C_UseSkill._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this.over();
        }
    }
    
    private boolean isInvisUsableSkill(int useSkillid) {
        int[] cast_with_invis;
        int length = (cast_with_invis = C_UseSkill._cast_with_invis).length;
        int i = 0;
        while (i < length) {
            int skillId = cast_with_invis[i];
            if (skillId == useSkillid) {
                return true;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
