package com.lineage.server.model.skill;

import com.lineage.server.model.L1Location;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import java.util.Random;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.model.L1Magic;
import com.lineage.server.datatables.Skill_type1;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.L1PinkName;
import com.lineage.server.serverpackets.S_RangeSkill;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.S_Sound;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_PacketBoxWindShackle;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.L1Clan;
import java.util.List;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1TowerInstance;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.Instance.L1TeleporterInstance;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.model.Instance.L1HousekeeperInstance;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.Instance.L1DwarfInstance;
import com.lineage.server.model.Instance.L1CrownInstance;
import com.lineage.server.model.Instance.L1GuardInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Object;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.World;
import com.lineage.server.datatables.SkillsTable;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.templates.L1Skills;
import org.apache.commons.logging.Log;

public class L1SkillUse
{
    private static final Log _log;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_SPELLSC = 2;
    public static final int TYPE_NPCBUFF = 3;
    public static final int TYPE_GMBUFF = 4;
    private L1Skills _skill;
    private int _skillId;
    private int _getBuffDuration;
    private int _shockStunDuration;
    private int _getBuffIconDuration;
    private int _targetID;
    private int _mpConsume;
    private int _hpConsume;
    private int _targetX;
    private int _targetY;
    private int _dmg;
    private int _skillTime;
    private int _type;
    private int _actid;
    private int _gfxid;
    private int _heal;
    private boolean _isPK;
    private int _bookmarkId;
    private int _itemobjid;
    private boolean _checkedUseSkill;
    private int _leverage;
    private boolean _isFreeze;
    private boolean _isCounterMagic;
    private boolean _isGlanceCheckFail;
    private L1Character _user;
    private L1PcInstance _player;
    private L1NpcInstance _npc;
    private L1Character _target;
    private L1NpcInstance _targetNpc;
    private int _calcType;
    private static final int PC_PC = 1;
    private static final int PC_NPC = 2;
    private static final int NPC_PC = 3;
    private static final int NPC_NPC = 4;
    private ArrayList<TargetStatus> _targetList;
    private static final int[] EXCEPT_COUNTER_MAGIC;
    private static final int[][] REPEATEDSKILLS;
    
    static {
        _log = LogFactory.getLog(L1SkillUse.class);
        EXCEPT_COUNTER_MAGIC = new int[] { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, 87, 88, 89, 90, 91, 97, 98, 99, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 132, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159, 161, 163, 164, 165, 166, 168, 169, 170, 171, 187, 194, 213, 175, 176, 181, 185, 190, 195, 201, 204, 209, 211, 214, 216, 219, 208, 10026, 10027, 10028, 10029, 41472 };
        REPEATEDSKILLS = new int[][] { { 148, 149, 155, 156, 163, 166 }, { 151, 168, 3, 99, 159 }, { 52, 101, 150, 1000, 1016, 1017, 186 }, { 43, 54, 1001 }, { 26, 110 }, { 42, 109 }, { 114, 115 }, { 201, 106 }, { 185, 190, 195 }, { 14 } };
    }
    
    public L1SkillUse() {
        this._mpConsume = 0;
        this._hpConsume = 0;
        this._targetX = 0;
        this._targetY = 0;
        this._dmg = 0;
        this._skillTime = 0;
        this._type = 0;
        this._actid = 0;
        this._gfxid = 0;
        this._isPK = false;
        this._bookmarkId = 0;
        this._itemobjid = 0;
        this._checkedUseSkill = false;
        this._leverage = 10;
        this._isFreeze = false;
        this._isCounterMagic = true;
        this._isGlanceCheckFail = false;
        this._user = null;
        this._player = null;
        this._npc = null;
        this._target = null;
        this._targetNpc = null;
    }
    
    public void setLeverage(int i) {
        this._leverage = i;
    }
    
    public int getLeverage() {
        return this._leverage;
    }
    
    private boolean isCheckedUseSkill() {
        return this._checkedUseSkill;
    }
    
    private void setCheckedUseSkill(boolean flg) {
        this._checkedUseSkill = flg;
    }
    
    public boolean checkUseSkill(L1PcInstance player, int skillid, int target_id, int x, int y, int time, int type, L1Character attacker) {
        return this.checkUseSkill(player, skillid, target_id, x, y, time, type, attacker, 0, 0);
    }
    
    public boolean checkUseSkill(L1PcInstance player, int skillid, int target_id, int x, int y, int time, int type, L1Character attacker, int actid, int gfxid) {
        this.setCheckedUseSkill(true);
        this._targetList = new ArrayList();
        this._skill = SkillsTable.get().getTemplate(skillid);
        if (this._skill == null) {
            return false;
        }
        else {
            this._skillId = skillid;
            this._targetX = x;
            this._targetY = y;
            this._skillTime = time;
            this._type = type;
            this._actid = actid;
            this._gfxid = gfxid;
            boolean checkedResult = true;
            if (attacker == null) {
                this._player = player;
                this._user = this._player;
            }
            else {
                this._npc = (L1NpcInstance)attacker;
                this._user = this._npc;
            }
            if (this._skill.getTarget().equals("none")) {
                this._targetID = this._user.getId();
                this._targetX = this._user.getX();
                this._targetY = this._user.getY();
            }
            else {
                this._targetID = target_id;
            }
            switch (type) {
                case 0: {
                    checkedResult = this.isNormalSkillUsable();
                    break;
                }
                case 2:
                case 3: {
                    checkedResult = true;
                    break;
                }
            }
            if (!checkedResult) {
                return false;
            }
            else if (this._skillId == 58 || this._skillId == 63) {
                return true;
            }
            else {
                L1Object object = World.get().findObject(this._targetID);
                if (object instanceof L1ItemInstance) {
                    return false;
                }
                else {
                    if (this._user instanceof L1PcInstance) {
                        if (object instanceof L1PcInstance) {
                            this._calcType = 1;
                        }
                        else {
                            this._calcType = 2;
                            this._targetNpc = (L1NpcInstance)object;
                        }
                    }
                    else if (this._user instanceof L1NpcInstance) {
                        if (object instanceof L1PcInstance) {
                            this._calcType = 3;
                        }
                        else {
                            this._calcType = 4;
                            this._targetNpc = (L1NpcInstance)object;
                        }
                    }
                    switch (this._skillId) {
                        case 5:
                        case 69: {
                            this._bookmarkId = target_id;
                            break;
                        }
                        case 12:
                        case 73:
                        case 100:
                        case 107: {
                            this._itemobjid = target_id;
                            break;
                        }
                        case 21: {
                            this._itemobjid = target_id;
                            this._user.setuseitemobjid(this._itemobjid);
                            break;
                        }
                    }
                    this._target = (L1Character)object;
                    if (!(this._target instanceof L1MonsterInstance) && this._skill.getTarget().equals("attack") && this._user.getId() != target_id) {
                        this._isPK = true;
                    }
                    if (!(object instanceof L1Character)) {
                        checkedResult = false;
                    }
                    this.makeTargetList();
                    if (this._user instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)this._user;
                        if (!ConfigAlt.NO_CD.equals("null")) {
                            boolean nocd = false;
                            String[] nocds = ConfigAlt.NO_CD.split(",");
                            int i = 0;
                            while (i < nocds.length) {
                                if (Integer.valueOf(nocds[i]) == this._skillId) {
                                    nocd = true;
                                    break;
                                }
                                else {
                                    ++i;
                                }
                            }
                            if (nocd && this._type != 4) {
                                if (pc.hasSkillEffect(40000)) {
                                    return false;
                                }
                                else if (!pc.hasSkillEffect(40000) && this._skill.getReuseDelay() > 0) {
                                    pc.setSkillEffect(40000, this._skill.getReuseDelay());
                                }
                            }
                        }
                    }
                    if (this._targetList.size() == 0 && this._user instanceof L1NpcInstance) {
                        checkedResult = false;
                    }
                    return checkedResult;
                }
            }
        }
    }
    
    private boolean isNormalSkillUsable() {
        if (this._user instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)this._user;
            if (!this.isAttrAgrees()) {
                return false;
            }
            else if (this._skillId == 147 && pc.getElfAttr() == 0) {
                pc.sendPackets(new S_ServerMessage(280));
                return false;
            }
            else {
                if (this._skillId == 155) {
                    if (pc.getWeapon() == null) {
                        pc.sendPackets(new S_SystemMessage("需裝備匕首或長劍才能施放"));
                        return false;
                    }
                    else if (pc.getWeapon().getItem().getType() != 1 && pc.getWeapon().getItem().getType() != 2) {
                        pc.sendPackets(new S_SystemMessage("需裝備匕首或長劍才能施放"));
                        return false;
                    }
                }
                boolean castle_area = L1CastleLocation.checkInAllWarArea(this._player.getX(), this._player.getY(), this._player.getMapId());
                int[] bow_GFX_Arrow = ConfigOther.WAR_DISABLE_SKILLS;
                int[] array;
                int length = (array = bow_GFX_Arrow).length;
                int i = 0;
                while (i < length) {
                    int gfx = array[i];
                    if (this._skillId == gfx && castle_area) {
                        this._player.sendPackets(new S_ServerMessage("1戰爭旗幟內禁止使用此魔法"));
                        return false;
                    }
                    else {
                        ++i;
                    }
                }
                if (this._skillId == 205 || this._skillId == 210 || this._skillId == 215 || this._skillId == 220) {
                    boolean isNearSameCube = false;
                    int gfxId = 0;
                    for (L1Object obj : World.get().getVisibleObjects(pc, 3)) {
                        if (obj instanceof L1EffectInstance) {
                            L1EffectInstance effect = (L1EffectInstance)obj;
                            gfxId = effect.getGfxId();
                            if ((this._skillId == 205 && gfxId == 6706) || (this._skillId == 210 && gfxId == 6712) || (this._skillId == 215 && gfxId == 6718) || (this._skillId == 220 && gfxId == 6724)) {
                                isNearSameCube = true;
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    if (isNearSameCube) {
                        pc.sendPackets(new S_ServerMessage(1412));
                        return false;
                    }
                }
                if (!this.isItemConsume() && !this._player.isGm()) {
                    this._player.sendPackets(new S_ServerMessage(299));
                    return false;
                }
            }
        }
        else if (this._user instanceof L1NpcInstance && this._user.hasSkillEffect(64)) {
            this._user.removeSkillEffect(64);
            return false;
        }
        return this.isHPMPConsume();
    }
    
    public void handleCommands(L1PcInstance player, int skillId, int targetId, int x, int y, int timeSecs, int type) {
        this.handleCommands(player, skillId, targetId, x, y, timeSecs, type, null);
    }
    
    public void handleCommands1(L1PcInstance player, int skillId, int targetId, int x, int y, int timeSecs, int type) {
        this.handleCommands1(player, skillId, targetId, x, y, timeSecs, type, null);
    }
    
    public void handleCommands(L1PcInstance player, int skillId, int targetId, int x, int y, int timeSecs, int type, L1Character attacker) {
        try {
            if (!this.isCheckedUseSkill()) {
                boolean isUseSkill = this.checkUseSkill(player, skillId, targetId, x, y, timeSecs, type, attacker);
                if (!isUseSkill) {
                    this.failSkill();
                    return;
                }
            }
            switch (type) {
                case 0: {
                    if (!this._isGlanceCheckFail || this._skill.getArea() > 0 || this._skill.getTarget().equals("none")) {
                        this.runSkill();
                        this.useConsume();
                        this.sendGrfx(true);
                        this.sendFailMessageHandle();
                        this.setDelay();
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    this.runSkill();
                    break;
                }
                case 2: {
                    this.runSkill();
                    this.sendGrfx(true);
                    this.setDelay();
                    break;
                }
                case 3: {
                    this.runSkill();
                    this.sendGrfx(true);
                    break;
                }
                case 4: {
                    this.runSkill();
                    this.sendGrfx(false);
                    break;
                }
                case 5: {
                    this.runSkill();
                    this.sendGrfx(true);
                    break;
                }
            }
            this.setCheckedUseSkill(false);
        }
        catch (Exception e) {
            L1SkillUse._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void handleCommands1(L1PcInstance player, int skillId, int targetId, int x, int y, int timeSecs, int type, L1Character attacker) {
        try {
            if (!this.isCheckedUseSkill()) {
                boolean isUseSkill = this.checkUseSkill(player, skillId, targetId, x, y, timeSecs, type, attacker);
                if (!isUseSkill) {
                    this.failSkill();
                    return;
                }
            }
            switch (type) {
                case 0: {
                    if (!this._isGlanceCheckFail || this._skill.getArea() > 0 || this._skill.getTarget().equals("none")) {
                        this.runSkill();
                        this.useConsume();
                        this.sendGrfx(false);
                        this.sendFailMessageHandle();
                        this.setDelay();
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 1: {
                    this.runSkill();
                    break;
                }
                case 2: {
                    this.runSkill();
                    this.sendGrfx(false);
                    this.setDelay();
                    break;
                }
                case 3: {
                    this.runSkill();
                    this.sendGrfx(false);
                    break;
                }
                case 4: {
                    this.runSkill();
                    this.sendGrfx(false);
                    break;
                }
                case 5: {
                    this.runSkill();
                    this.sendGrfx(false);
                    break;
                }
            }
            this.setCheckedUseSkill(false);
        }
        catch (Exception e) {
            L1SkillUse._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void failSkill() {
        this.setCheckedUseSkill(false);
        switch (this._skillId) {
            case 5:
            case 69:
            case 131: {
                this._player.sendPackets(new S_Paralysis(7, false));
                break;
            }
        }
    }
    
    private boolean isTarget(L1Character cha) throws Exception {
        if (cha == null) {
            return false;
        }
        else {
            if (this._npc != null) {
                if (this._npc.isHate(cha)) {
                    return true;
                }
                else if (this._npc instanceof L1PetInstance && cha instanceof L1MonsterInstance) {
                    return true;
                }
                else if (this._npc instanceof L1SummonInstance && cha instanceof L1MonsterInstance) {
                    return true;
                }
            }
            if (!CheckUtil.checkAttackSkill(cha)) {
                return false;
            }
            else {
                boolean flg = false;
                if (cha instanceof L1DoorInstance && (cha.getMaxHp() == 0 || cha.getMaxHp() == 1)) {
                    return false;
                }
                else if (cha instanceof L1DollInstance) {
                    return false;
                }
                else if (cha instanceof L1HierarchInstance) {
                    return false;
                }
                else {
                    if (cha instanceof L1PcInstance) {
                        L1PcInstance pc = (L1PcInstance)cha;
                        if (pc.isGhost() || pc.isGmInvis()) {
                            return false;
                        }
                    }
                    if (this._calcType == 3) {
                        if (cha instanceof L1PcInstance || cha instanceof L1PetInstance || cha instanceof L1DeInstance || cha instanceof L1SummonInstance) {
                            flg = true;
                        }
                        if (cha instanceof L1DeInstance && cha.isSafetyZone()) {
                            return false;
                        }
                    }
                    if (this._calcType == 2 && cha instanceof L1PcInstance && cha.isSafetyZone()) {
                        return false;
                    }
                    else if (this._calcType == 2 && this._target instanceof L1NpcInstance && !(this._target instanceof L1PetInstance) && !(this._target instanceof L1SummonInstance) && (cha instanceof L1PetInstance || cha instanceof L1SummonInstance || cha instanceof L1PcInstance)) {
                        return false;
                    }
                    else if (this._calcType == 2 && this._target instanceof L1NpcInstance && !(this._target instanceof L1GuardInstance) && cha instanceof L1GuardInstance) {
                        return false;
                    }
                    else if ((this._skill.getTarget().equals("attack") || this._skill.getType() == 64) && this._calcType == 3 && !(cha instanceof L1PetInstance) && !(cha instanceof L1SummonInstance) && !(cha instanceof L1PcInstance)) {
                        return false;
                    }
                    else if ((this._skill.getTarget().equals("attack") || this._skill.getType() == 64) && this._calcType == 4 && this._user instanceof L1MonsterInstance && cha instanceof L1MonsterInstance) {
                        return false;
                    }
                    else if (this._skill.getTarget().equals("none") && this._skill.getType() == 64 && (cha instanceof L1CrownInstance || cha instanceof L1DwarfInstance || cha instanceof L1EffectInstance || cha instanceof L1FieldObjectInstance || cha instanceof L1FurnitureInstance || cha instanceof L1HousekeeperInstance || cha instanceof L1MerchantInstance || cha instanceof L1TeleporterInstance)) {
                        return false;
                    }
                    else if (this._skill.getType() == 64 && cha.getId() == this._user.getId()) {
                        return false;
                    }
                    else if (cha.getId() == this._user.getId() && this._skillId == 49) {
                        return false;
                    }
                    else if (((this._skill.getTargetTo() & 0x1) == 0x1 || (this._skill.getTargetTo() & 0x4) == 0x4 || (this._skill.getTargetTo() & 0x8) == 0x8) && cha.getId() == this._user.getId() && this._skillId != 49) {
                        return true;
                    }
                    else {
                        if (this._user instanceof L1PcInstance && (this._skill.getTarget().equals("attack") || this._skill.getType() == 64) && !this._isPK) {
                            if (cha instanceof L1SummonInstance) {
                                L1SummonInstance summon = (L1SummonInstance)cha;
                                if (this._player.getId() == summon.getMaster().getId()) {
                                    return false;
                                }
                            }
                            else if (cha instanceof L1PetInstance) {
                                L1PetInstance pet = (L1PetInstance)cha;
                                if (this._player.getId() == pet.getMaster().getId()) {
                                    return false;
                                }
                            }
                        }
                        if ((this._skill.getTarget().equals("attack") || this._skill.getType() == 64) && !(cha instanceof L1MonsterInstance) && !this._isPK && this._target instanceof L1PcInstance) {
                            L1PcInstance enemy = null;
                            try {
                                enemy = (L1PcInstance)cha;
                            }
                            catch (Exception e) {
                                return false;
                            }
                            if (this._skillId == 72 && enemy.getZoneType() != 1 && (cha.hasSkillEffect(60) || cha.hasSkillEffect(97))) {
                                return true;
                            }
                            else {
                                if (this._player.getClanid() != 0 && enemy.getClanid() != 0) {
                                    for (L1War war : WorldWar.get().getWarList()) {
                                        if (war.checkClanInWar(this._player.getClanname()) && war.checkClanInSameWar(this._player.getClanname(), enemy.getClanname()) && L1CastleLocation.checkInAllWarArea(enemy.getX(), enemy.getY(), enemy.getMapId())) {
                                            return true;
                                        }
                                    }
                                }
                                return false;
                            }
                        }
                        else if (!this._user.glanceCheck(cha.getX(), cha.getY()) && !this._skill.isThrough() && this._skill.getType() != 2 && this._skill.getType() != 32) {
                            this._isGlanceCheckFail = true;
                            return false;
                        }
                        else if (cha.hasSkillEffect(50) && this._skillId == 50) {
                            return false;
                        }
                        else if (cha.hasSkillEffect(157) && this._skillId == 157) {
                            return false;
                        }
                        else if (!(cha instanceof L1MonsterInstance) && (this._skillId == 36 || this._skillId == 41)) {
                            return false;
                        }
                        else if (cha.isDead() && this._skillId != 41 && this._skillId != 61 && this._skillId != 75 && this._skillId != 165) {
                            return false;
                        }
                        else if (!cha.isDead() && (this._skillId == 41 || this._skillId == 61 || this._skillId == 75 || this._skillId == 165)) {
                            return false;
                        }
                        else if ((cha instanceof L1TowerInstance || cha instanceof L1DoorInstance) && (this._skillId == 41 || this._skillId == 61 || this._skillId == 75 || this._skillId == 165)) {
                            return false;
                        }
                        else {
                            if (cha instanceof L1PcInstance) {
                                L1PcInstance pc = (L1PcInstance)cha;
                                if (pc.hasSkillEffect(78)) {
                                    switch (this._skillId) {
                                        case 13:
                                        case 20:
                                        case 26:
                                        case 27:
                                        case 29:
                                        case 37:
                                        case 40:
                                        case 42:
                                        case 44:
                                        case 47:
                                        case 48:
                                        case 56:
                                        case 64:
                                        case 66:
                                        case 68:
                                        case 69:
                                        case 71:
                                        case 72:
                                        case 76:
                                        case 103:
                                        case 151:
                                        case 152:
                                        case 153:
                                        case 212: {
                                            return true;
                                        }
                                        default: {
                                            return false;
                                        }
                                    }
                                }
                            }
                            if (cha instanceof L1NpcInstance) {
                                int hiddenStatus = ((L1NpcInstance)cha).getHiddenStatus();
                                switch (hiddenStatus) {
                                    case 1: {
                                        switch (this._skillId) {
                                            case 13:
                                            case 72:
                                            case 194:
                                            case 213: {
                                                return true;
                                            }
                                            default: {
                                                return false;
                                            }
                                        }
                                    }
                                    case 2: {
                                        return false;
                                    }
                                }
                            }
                            if ((this._skill.getTargetTo() & 0x1) == 0x1 && cha instanceof L1PcInstance) {
                                flg = true;
                            }
                            else if ((this._skill.getTargetTo() & 0x2) == 0x2 && (cha instanceof L1MonsterInstance || cha instanceof L1NpcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance)) {
                                flg = true;
                            }
                            else if ((this._skill.getTargetTo() & 0x10) == 0x10 && this._user instanceof L1PcInstance) {
                                if (cha instanceof L1SummonInstance) {
                                    L1SummonInstance summon = (L1SummonInstance)cha;
                                    if (summon.getMaster() != null && this._player.getId() == summon.getMaster().getId()) {
                                        flg = true;
                                    }
                                }
                                if (cha instanceof L1PetInstance) {
                                    L1PetInstance pet = (L1PetInstance)cha;
                                    if (pet.getMaster() != null && this._player.getId() == pet.getMaster().getId()) {
                                        flg = true;
                                    }
                                }
                            }
                            if (this._calcType == 1 && cha instanceof L1PcInstance) {
                                L1PcInstance xpc = (L1PcInstance)cha;
                                if ((this._skill.getTargetTo() & 0x4) == 0x4 && ((this._player.getClanid() != 0 && this._player.getClanid() == xpc.getClanid()) || this._player.isGm())) {
                                    return true;
                                }
                                else if ((this._skill.getTargetTo() & 0x8) == 0x8 && (this._player.getParty().isMember(xpc) || this._player.isGm())) {
                                    return true;
                                }
                            }
                            return flg;
                        }
                    }
                }
            }
        }
    }
    
    private boolean isParty(L1NpcInstance npc, L1Character cha) {
        if (npc.getMaster() == null) {
            return false;
        }
        else if (npc.isHate(cha)) {
            return false;
        }
        else {
            int masterId = npc.getMaster().getId();
            if (cha instanceof L1PcInstance) {
                return cha.getId() == masterId;
            }
            else if (cha instanceof L1PetInstance) {
                L1PetInstance tgPet = (L1PetInstance)cha;
                return tgPet.getMaster() != null && tgPet.getMaster().getId() == masterId;
            }
            else if (cha instanceof L1SummonInstance) {
                L1SummonInstance tgSu = (L1SummonInstance)cha;
                return tgSu.getMaster() != null && tgSu.getMaster().getId() == masterId;
            }
            else {
                return false;
            }
        }
    }
    
    private void makeTargetList() {
        try {
            if (this._type == 1) {
                this._targetList.add(new TargetStatus(this._user));
                return;
            }
            else if (this._skill.getTargetTo() == 0 && (this._skill.getType() & 0x40) != 0x40) {
                this._targetList.add(new TargetStatus(this._user));
                return;
            }
            else {
                if (this._skill.getRanged() != -1) {
                    if (this._user.getLocation().getTileLineDistance(this._target.getLocation()) > this._skill.getRanged()) {
                        return;
                    }
                }
                else if (!this._user.getLocation().isInScreen(this._target.getLocation())) {
                    return;
                }
                if (!this.isTarget(this._target) && !this._skill.getTarget().equals("none")) {
                    return;
                }
                else {
                    switch (this._skillId) {
                        case 17: {
                            ArrayList<L1Object> al1object = World.get().getVisibleLineObjects(this._user, this._target);
                            for (L1Object tgobj : al1object) {
                                if (tgobj == null) {
                                    continue;
                                }
                                else if (!(tgobj instanceof L1Character)) {
                                    continue;
                                }
                                else {
                                    L1Character cha = (L1Character)tgobj;
                                    if (!this.isTarget(cha)) {
                                        continue;
                                    }
                                    else {
                                        this._targetList.add(new TargetStatus(cha));
                                    }
                                }
                            }
                            al1object.clear();
                            return;
                        }
                        default: {
                            if (this._skill.getArea() == 0) {
                                if (!this._user.glanceCheck(this._target.getX(), this._target.getY()) && (this._skill.getType() & 0x40) == 0x40 && this._skillId != 10026 && this._skillId != 10027 && this._skillId != 10028 && this._skillId != 10029) {
                                    this._targetList.add(new TargetStatus(this._target, false));
                                    return;
                                }
                                else {
                                    this._targetList.add(new TargetStatus(this._target));
                                    break;
                                }
                            }
                            else {
                                if (!this._skill.getTarget().equals("none")) {
                                    this._targetList.add(new TargetStatus(this._target));
                                }
                                if (this._skillId != 49 && !this._skill.getTarget().equals("attack") && this._skill.getType() != 64) {
                                    this._targetList.add(new TargetStatus(this._user));
                                }
                                List<L1Object> objects;
                                if (this._skill.getArea() == -1) {
                                    objects = (List)World.get().getVisibleObjects(this._user);
                                }
                                else {
                                    objects = (List)World.get().getVisibleObjects(this._target, this._skill.getArea());
                                }
                                for (L1Object tgobj : objects) {
                                    if (tgobj == null) {
                                        continue;
                                    }
                                    else if (!(tgobj instanceof L1Character)) {
                                        continue;
                                    }
                                    else {
                                        if (tgobj instanceof L1MonsterInstance) {
                                            L1MonsterInstance mob = (L1MonsterInstance)tgobj;
                                            if (mob.getNpcId() == 45166) {
                                                continue;
                                            }
                                            else if (mob.getNpcId() == 45167) {
                                                continue;
                                            }
                                        }
                                        else if (tgobj instanceof L1PcInstance) {
                                            L1PcInstance tgpc = (L1PcInstance)tgobj;
                                            if ((this._skillId == 114 || this._skillId == 115 || this._skillId == 117) && this._user instanceof L1PcInstance) {
                                                L1PcInstance pc = (L1PcInstance)this._user;
                                                if (pc.getClanid() != tgpc.getClanid()) {
                                                    if (pc.isInParty()) {
                                                        for (L1Object object : World.get().getVisibleObjects(pc, 14)) {
                                                            if (object instanceof L1PcInstance) {
                                                                L1PcInstance red = (L1PcInstance)object;
                                                                if (pc.isInParty() && pc.getParty().isMember(red)) {
                                                                    int castgfx = this._skill.getCastGfx();
                                                                    L1SkillUse skillUse = new L1SkillUse();
                                                                    skillUse.handleCommands(red, castgfx, red.getId(), red.getX(), red.getY(), 1800, 4);
                                                                    red.sendPacketsAll(new S_SkillSound(red.getId(), castgfx));
                                                                }
                                                                else {
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                        continue;
                                                    }
                                                    else {
                                                        continue;
                                                    }
                                                }
                                                else {
                                                    L1Clan clan = pc.getClan();
                                                    L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                                                    L1PcInstance[] array;
                                                    for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                                                        L1PcInstance clanMember1 = array[i];
                                                        if (pc.getLocation().isInScreen(pc.getLocation())) {
                                                            int castgfx2 = this._skill.getCastGfx();
                                                            L1SkillUse skillUse2 = new L1SkillUse();
                                                            skillUse2.handleCommands(clanMember1, castgfx2, clanMember1.getId(), clanMember1.getX(), clanMember1.getY(), 1800, 4);
                                                            clanMember1.sendPacketsAll(new S_SkillSound(clanMember1.getId(), castgfx2));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        L1Character cha = (L1Character)tgobj;
                                        if (!this.isTarget(cha)) {
                                            continue;
                                        }
                                        else {
                                            this._targetList.add(new TargetStatus(cha));
                                        }
                                    }
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private void sendHappenMessage(L1PcInstance pc) {
        int msgID = this._skill.getSysmsgIdHappen();
        if (msgID > 0) {
            pc.sendPackets(new S_ServerMessage(msgID));
        }
    }
    
    private void sendFailMessageHandle() {
        if (this._skill.getType() != 64 && !this._skill.getTarget().equals("none") && this._targetList.size() == 0) {
            this.sendFailMessage();
        }
    }
    
    private void sendFailMessage() {
        int msgID = this._skill.getSysmsgIdFail();
        if (msgID > 0 && this._user instanceof L1PcInstance) {
            this._player.sendPackets(new S_ServerMessage(msgID));
        }
    }
    
    private boolean isAttrAgrees() {
        int magicattr = this._skill.getAttr();
        return this._user instanceof L1NpcInstance || this._skill.getSkillLevel() < 17 || this._skill.getSkillLevel() > 22 || magicattr == 0 || magicattr == this._player.getElfAttr() || this._player.isGm();
    }
    
    private boolean isHPMPConsume() {
        this._mpConsume = this._skill.getMpConsume();
        this._hpConsume = this._skill.getHpConsume();
        int currentMp = 0;
        int currentHp = 0;
        if (this._user instanceof L1NpcInstance) {
            currentMp = this._npc.getCurrentMp();
            currentHp = this._npc.getCurrentHp();
        }
        else {
            currentMp = this._player.getCurrentMp();
            currentHp = this._player.getCurrentHp();
            if (this._player.getOriginalMagicConsumeReduction() > 0) {
                this._mpConsume -= this._player.getOriginalMagicConsumeReduction();
            }
            if (this._player.getInt() > 12 && this._skillId > 8 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 13 && this._skillId > 16 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 14 && this._skillId > 23 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 15 && this._skillId > 32 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 16 && this._skillId > 40 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 17 && this._skillId > 48 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 18 && this._skillId > 56 && this._skillId <= 80) {
                --this._mpConsume;
            }
            if (this._player.getInt() > 12 && this._skillId >= 87 && this._skillId <= 91) {
                this._mpConsume -= this._player.getInt() - 12;
            }
            if (this._skillId == 26 && this._player.getInventory().checkEquipped(20013)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 43 && this._player.getInventory().checkEquipped(20013)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 1 && this._player.getInventory().checkEquipped(20014)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 19 && this._player.getInventory().checkEquipped(20014)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 12 && this._player.getInventory().checkEquipped(20015)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 13 && this._player.getInventory().checkEquipped(20015)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 42 && this._player.getInventory().checkEquipped(20015)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 43 && this._player.getInventory().checkEquipped(20008)) {
                this._mpConsume >>= 1;
            }
            if (this._skillId == 54 && this._player.getInventory().checkEquipped(20023)) {
                this._mpConsume >>= 1;
            }
            if (this._skill.getMpConsume() == 0) {
                this._mpConsume = 0;
            }
            else if (this._mpConsume <= 0) {
                this._mpConsume = 1;
            }
        }
        if (currentHp < this._hpConsume + 1) {
            if (this._user instanceof L1PcInstance) {
                this._player.sendPackets(new S_ServerMessage(279));
                if (this._player.isGm()) {
                    this._player.setCurrentHp(this._player.getMaxHp());
                }
            }
            return false;
        }
        else if (this._skillId == 108 && currentHp <= 100) {
            if (this._user instanceof L1PcInstance) {
                this._player.sendPackets(new S_ServerMessage(279));
            }
            return false;
        }
        else if (currentMp < this._mpConsume) {
            if (this._user instanceof L1PcInstance) {
                this._player.sendPackets(new S_ServerMessage(278));
                if (this._player.isGm()) {
                    this._player.setCurrentMp(this._player.getMaxMp());
                }
            }
            return false;
        }
        else {
            return true;
        }
    }
    
    private boolean isItemConsume() {
        int itemConsume = this._skill.getItemConsumeId();
        int itemConsumeCount = this._skill.getItemConsumeCount();
        return itemConsume == 0 || this._player.isGm() || this._player.getInventory().checkItem(itemConsume, (long)itemConsumeCount);
    }
    
    private void useConsume() {
        if (this._user instanceof L1NpcInstance) {
            int current_hp = this._npc.getCurrentHp() - this._hpConsume;
            this._npc.setCurrentHp(current_hp);
            int current_mp = this._npc.getCurrentMp() - this._mpConsume;
            this._npc.setCurrentMp(current_mp);
            return;
        }
        else {
            if (this.isHPMPConsume()) {
                if (this._skillId == 108) {
                    if (this._player.getlogpcpower_SkillFor4() != 0) {
                        int hp = (int)((double)this._player.getCurrentHp() * (0.05 * (double)this._player.getlogpcpower_SkillFor4()));
                        this._player.setCurrentHp(hp);
                        this._player.setCurrentMp(1);
                    }
                    else {
                        this._player.setCurrentHp(100);
                        this._player.setCurrentMp(1);
                    }
                }
                else {
                    int current_hp = this._player.getCurrentHp() - this._hpConsume;
                    this._player.setCurrentHp(current_hp);
                    int current_mp = this._player.getCurrentMp() - this._mpConsume;
                    this._player.setCurrentMp(current_mp);
                }
            }
            int lawful = this._player.getLawful() + this._skill.getLawful();
            if (lawful > 32767) {
                lawful = 32767;
            }
            if (lawful < -32767) {
                lawful = -32767;
            }
            this._player.setLawful(lawful);
            int itemConsume = this._skill.getItemConsumeId();
            int itemConsumeCount = this._skill.getItemConsumeCount();
            if (itemConsume == 0 || this._player.isGm()) {
                return;
            }
            else {
                this._player.getInventory().consumeItem(itemConsume, (long)itemConsumeCount);
                return;
            }
        }
    }
    
    private void addMagicList(L1Character cha, boolean repetition) {
        if (this._skillTime == 0) {
            this._getBuffDuration = this._skill.getBuffDuration() * 1000;
            if (this._skill.getBuffDuration() == 0) {
                if (this._skillId == 60) {
                    cha.setSkillEffect(60, 0);
                }
                return;
            }
        }
        else {
            this._getBuffDuration = this._skillTime * 1000;
        }
        if (this._skillId == 87 || this._skillId == 208) {
            this._getBuffDuration = this._shockStunDuration;
        }
        if (this._skillId == 11) {
            return;
        }
        else if (this._skillId == 33 || this._skillId == 4001) {
            return;
        }
        else if (this._skillId == 67) {
            return;
        }
        else if (this._skillId == 21 || this._skillId == 8 || this._skillId == 12 || this._skillId == 48 || this._skillId == 107) {
            return;
        }
        else if ((this._skillId == 50 || this._skillId == 80 || this._skillId == 194) && !this._isFreeze) {
            return;
        }
        else {
            SkillMode mode = L1SkillMode.get().getSkill(this._skillId);
            if (mode == null) {
                cha.setSkillEffect(this._skillId, this._getBuffDuration);
            }
            if (cha instanceof L1PcInstance && repetition) {
                L1PcInstance pc = (L1PcInstance)cha;
                this.sendIcon(pc);
            }
            return;
        }
    }
    
    private void sendIcon(L1PcInstance pc) {
        if (this._skillTime == 0) {
            this._getBuffIconDuration = this._skill.getBuffDuration();
        }
        else {
            this._getBuffIconDuration = this._skillTime;
        }
        switch (this._skillId) {
            case 3: {
                pc.sendPackets(new S_SkillIconShield(5, this._getBuffIconDuration));
                break;
            }
            case 26: {
                pc.sendPackets(new S_Dexup(pc, 5, this._getBuffIconDuration));
                break;
            }
            case 29:
            case 76:
            case 152: {
                pc.sendPackets(new S_SkillHaste(pc.getId(), 2, this._getBuffIconDuration));
                pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 2, 0));
                break;
            }
            case 42: {
                pc.sendPackets(new S_Strup(pc, 5, this._getBuffIconDuration));
                break;
            }
            case 43:
            case 54: {
                pc.sendPackets(new S_SkillHaste(pc.getId(), 1, this._getBuffIconDuration));
                pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
                break;
            }
            case 52:
            case 101:
            case 150: {
                pc.sendPackets(new S_SkillBrave(pc.getId(), 4, this._getBuffIconDuration));
                pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 4, 0));
                break;
            }
            case 68:
            case 119: { // 119: 王者加護 (同步顯示聖結界圖示)
                pc.sendPackets(new S_PacketBox(40, this._getBuffIconDuration));
                break;
            }
            case 99: {
                pc.sendPackets(new S_SkillIconShield(3, this._getBuffIconDuration));
                break;
            }
            case 109: {
                pc.sendPackets(new S_Strup(pc, 3, this._getBuffIconDuration));
                break;
            }
            case 110: {
                pc.sendPackets(new S_Dexup(pc, 3, this._getBuffIconDuration));
                break;
            }
            case 114: {
                pc.sendPackets(new S_PacketBoxIconAura(113, this._getBuffIconDuration));
                break;
            }
            case 115: {
                pc.sendPackets(new S_PacketBoxIconAura(114, this._getBuffIconDuration));
                break;
            }
            case 117: {
                pc.sendPackets(new S_PacketBoxIconAura(116, this._getBuffIconDuration));
                break;
            }
            case 148: {
                pc.sendPackets(new S_PacketBoxIconAura(147, this._getBuffIconDuration));
                break;
            }
            case 149: {
                pc.sendPackets(new S_PacketBoxIconAura(148, this._getBuffIconDuration));
                break;
            }
            case 151: {
                pc.sendPackets(new S_SkillIconShield(6, this._getBuffIconDuration));
                break;
            }
            case 155: {
                pc.sendPackets(new S_PacketBoxIconAura(154, this._getBuffIconDuration));
                break;
            }
            case 156: {
                pc.sendPackets(new S_PacketBoxIconAura(155, this._getBuffIconDuration));
                break;
            }
            case 159: {
                pc.sendPackets(new S_SkillIconShield(7, this._getBuffIconDuration));
                break;
            }
            case 163: {
                pc.sendPackets(new S_PacketBoxIconAura(162, this._getBuffIconDuration));
                break;
            }
            case 166: {
                pc.sendPackets(new S_PacketBoxIconAura(165, this._getBuffIconDuration));
                break;
            }
            case 167: {
                pc.sendPackets(new S_PacketBoxWindShackle(pc.getId(), this._getBuffIconDuration));
                break;
            }
            case 168: {
                pc.sendPackets(new S_SkillIconShield(10, this._getBuffIconDuration));
                break;
            }
            case 186: {
                pc.sendPackets(new S_SkillBrave(pc.getId(), 6, this._getBuffIconDuration));
                pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 6, 0));
                break;
            }
        }
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_OwnCharStatus(pc));
        pc.sendPackets(new S_PacketBox(132, pc.getEr()));
    }
    
    private void sendGrfx(boolean isSkillAction) {
        if (this._actid == 0) {
            this._actid = this._skill.getActionId();
        }
        if (this._gfxid == 0) {
            this._gfxid = this._skill.getCastGfx();
            if (this._gfxid == 0) {
                return;
            }
        }
        if (this._user instanceof L1PcInstance) {
            if (this._player.isMagicCritical()) {
                int gfxid2 = this._skill.getCastGfx2();
                if (gfxid2 != 0) {
                    this._gfxid = gfxid2;
                }
            }
            if (this._skillId == 58 || this._skillId == 63) {
                L1PcInstance pc = (L1PcInstance)this._user;
                if (this._skillId == 58) {
                    pc.setHeading(pc.targetDirection(this._targetX, this._targetY));
                    pc.sendPacketsAll(new S_ChangeHeading(pc));
                }
                pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), this._actid));
                return;
            }
            else {
                int targetid = this._target.getId();
                if (this._skillId == 87 || this._skillId == 208) {
                    if (this._targetList.size() == 0) {
                        return;
                    }
                    else {
                        if (this._target instanceof L1PcInstance) {
                            L1PcInstance pc2 = (L1PcInstance)this._target;
                            pc2.sendPacketsAll(new S_SkillSound(pc2.getId(), 4434));
                        }
                        else if (this._target instanceof L1NpcInstance) {
                            this._target.broadcastPacketAll(new S_SkillSound(this._target.getId(), 4434));
                        }
                        return;
                    }
                }
                else {
                    if (this._skillId == 2) {
                        L1PcInstance pc2 = (L1PcInstance)this._target;
                        pc2.sendPackets(new S_Sound(145));
                        if (pc2.isKnight() && pc2.getlogpcpower_SkillFor3() != 0 && pc2.getMeteLevel() > 0) {
                            if (pc2.isEsoteric()) {
                                pc2.setEsoteric(false);
                                pc2.sendPackets(new S_SystemMessage("\\fU關閉轉身技能(聖法之盾)"));
                            }
                            else {
                                pc2.setEsoteric(true);
                                pc2.sendPackets(new S_SystemMessage("\\fU開啟轉身技能(聖法之盾)"));
                            }
                        }
                        else if (pc2.isDarkelf() && pc2.getlogpcpower_SkillFor5() != 0 && pc2.getMeteLevel() > 0) {
                            if (pc2.isEsoteric()) {
                                pc2.setEsoteric(false);
                                pc2.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(刀劍之影)"));
                            }
                            else {
                                pc2.setEsoteric(true);
                                pc2.sendPackets(new S_SystemMessage("\\fU開啟轉生技能(刀劍之影)"));
                            }
                        }
                        else if (pc2.isWizard() && pc2.getlogpcpower_SkillFor4() != 0 && pc2.getMeteLevel() > 0) {
                            if (pc2.isEsoteric()) {
                                pc2.setEsoteric(false);
                                pc2.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(粉碎爆發)"));
                            }
                            else {
                                pc2.setEsoteric(true);
                                int addMp = 0;
                                if (pc2.getlogpcpower_SkillFor4() >= 1) {
                                    addMp += pc2.getlogpcpower_SkillFor4() * 10;
                                }
                                pc2.sendPackets(new S_SystemMessage("\\fU開啟轉生技能(粉碎爆發)，每秒消耗 " + addMp + " MP"));
                            }
                        }
                        else if (pc2.isCrown() && pc2.getlogpcpower_SkillFor2() != 0 && pc2.getMeteLevel() > 0) {
                            if (pc2.isEsoteric()) {
                                pc2.setEsoteric(false);
                                pc2.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(狂暴之氣)"));
                            }
                            else {
                                pc2.setEsoteric(true);
                                pc2.sendPackets(new S_SystemMessage("\\fU開啟轉生技能(狂暴之氣)"));
                            }
                        }
                    }
                    if (this._targetList.size() == 0 && !this._skill.getTarget().equals("none")) {
                        int tempchargfx = this._player.getTempCharGfx();
                        switch (tempchargfx) {
                            case 5727:
                            case 5730: {
                                this._actid = 19;
                                break;
                            }
                            case 5733:
                            case 5736: {
                                this._actid = 1;
                                break;
                            }
                        }
                        if (isSkillAction) {
                            this._player.sendPacketsAll(new S_DoActionGFX(this._player.getId(), this._actid));
                        }
                        return;
                    }
                    else {
                        if (this._skill.getTarget().equals("attack") && this._skillId != 18) {
                            if (this.isPcSummonPet(this._target) && (this._player.isSafetyZone() || this._target.isSafetyZone() || this._player.checkNonPvP(this._player, this._target))) {
                                this._player.sendPacketsAll(new S_UseAttackSkill(this._player, 0, this._gfxid, this._targetX, this._targetY, this._actid, this._dmg));
                                return;
                            }
                            else if (this._skill.getArea() == 0) {
                                this._player.sendPacketsAll(new S_UseAttackSkill(this._player, targetid, this._gfxid, this._targetX, this._targetY, this._actid, this._dmg));
                            }
                            else {
                                this._player.sendPacketsAll(new S_RangeSkill(this._player, this._targetList, this._gfxid, this._actid, 8));
                            }
                        }
                        else if (this._skill.getTarget().equals("none") && this._skill.getType() == 64) {
                            this._player.sendPacketsAll(new S_RangeSkill(this._player, this._targetList, this._gfxid, this._actid, 0));
                        }
                        else {
                            if (this._skillId != 5 && this._skillId != 69 && this._skillId != 131) {
                                if (isSkillAction) {
                                    this._player.sendPacketsAll(new S_DoActionGFX(this._player.getId(), this._actid));
                                }
                                if (this._skillId == 31 || this._skillId == 91 || this._skillId == 112 || this._skillId == 134) {
                                    this._player.sendPackets(new S_SkillSound(targetid, this._gfxid));
                                    this._player.broadcastPacketAll(new S_SkillSound(targetid, this._gfxid));
                                }
                                else if (this._skillId == 113) {
                                    return;
                                }
                                else if (this._skillId == 1 || this._skillId == 19 || this._skillId == 35 || this._skillId == 57) {
                                    this._player.sendPacketsAllUnderInvis(new S_SkillSound(targetid, this._gfxid));
                                }
                                else if (this._skillId == 68) {
                                    this._player.sendPacketsAllUnderInvis(new S_SkillSound(targetid, this._gfxid));
                                    if (this._calcType == 1 && this._player.getId() != this._target.getId() && this._target.hasSkillEffect(5122)) {
                                        L1PinkName.runPinkNameTimer(this._player);
                                    }
                                }
                                else if (this._skillId == 185 || this._skillId == 190 || this._skillId == 195) {
                                    if (this._skillId == this._player.getAwakeSkillId()) {
                                        this._player.sendPacketsAll(new S_SkillSound(targetid, this._gfxid));
                                    }
                                    else {
                                        return;
                                    }
                                }
                                else if (this._skillId == 106 && this._player.getAc() <= -150) {
                                    this._gfxid = this._skill.getCastGfx2();
                                    this._player.sendPacketsAll(new S_SkillSound(targetid, this._gfxid));
                                }
                                else {
                                    this._player.sendPacketsAll(new S_SkillSound(targetid, this._gfxid));
                                }
                            }
                            for (TargetStatus ts : this._targetList) {
                                L1Character cha = ts.getTarget();
                                if (cha instanceof L1PcInstance) {
                                    L1PcInstance pc3 = (L1PcInstance)cha;
                                    pc3.sendPackets(new S_OwnCharStatus(pc3));
                                }
                            }
                        }
                        this._player.setMagicCritical(false);
                    }
                }
            }
        }
        else if (this._user instanceof L1NpcInstance) {
            int targetid = this._target.getId();
            if (this._user instanceof L1MerchantInstance) {
                this._user.broadcastPacketAll(new S_SkillSound(targetid, this._gfxid));
                return;
            }
            else if (this._targetList.size() == 0 && !this._skill.getTarget().equals("none")) {
                this._user.broadcastPacketAll(new S_DoActionGFX(this._user.getId(), this._actid));
                return;
            }
            else if (this._skill.getTarget().equals("attack") && this._skillId != 18) {
                if (this._skill.getArea() == 0) {
                    this._user.broadcastPacketAll(new S_UseAttackSkill(this._user, targetid, this._gfxid, this._targetX, this._targetY, this._actid, this._dmg));
                }
                else {
                    this._user.broadcastPacketAll(new S_RangeSkill(this._user, this._targetList, this._gfxid, this._actid, 8));
                }
            }
            else if (this._skill.getTarget().equals("none") && this._skill.getType() == 64) {
                this._user.broadcastPacketAll(new S_RangeSkill(this._user, this._targetList, this._gfxid, this._actid, 0));
            }
            else if (this._skillId != 5 && this._skillId != 69 && this._skillId != 131) {
                this._user.broadcastPacketAll(new S_DoActionGFX(this._user.getId(), this._actid));
                this._user.broadcastPacketAll(new S_SkillSound(targetid, this._gfxid));
            }
        }
    }
    
    private void deleteRepeatedSkills(L1Character cha) {
        int[][] repeatedskills;
        for (int length = (repeatedskills = L1SkillUse.REPEATEDSKILLS).length, i = 0; i < length; ++i) {
            int[] skills = repeatedskills[i];
            int[] array;
            for (int length2 = (array = skills).length, j = 0; j < length2; ++j) {
                int id = array[j];
                if (id == this._skillId) {
                    this.stopSkillList(cha, skills);
                }
            }
        }
    }
    
    private void stopSkillList(L1Character cha, int[] repeat_skill) {
        for (int skillId : repeat_skill) {
            if (skillId != this._skillId) {
                cha.removeSkillEffect(skillId);
            }
        }
    }
    
    private void setDelay() {
        if (this._skill.getReuseDelay() > 0) {
            L1SkillDelay.onSkillUse(this._user, this._skill.getReuseDelay());
        }
    }
    
    private void runSkill() {
        switch (this._skillId) {
            case 58: {
                L1SpawnUtil.doSpawnFireWall(this._user, this._targetX, this._targetY);
                return;
            }
            case 63: {
                L1SpawnUtil.spawnEffect(81169, this._skill.getBuffDuration(), this._targetX, this._targetY, this._user.getMapId(), this._user, 0);
                return;
            }
            case 205: {
                L1SpawnUtil.spawnEffect(80149, this._skill.getBuffDuration(), this._targetX, this._targetY, this._user.getMapId(), this._user, this._skillId);
                return;
            }
            case 210: {
                L1SpawnUtil.spawnEffect(80150, this._skill.getBuffDuration(), this._targetX, this._targetY, this._user.getMapId(), this._user, this._skillId);
                return;
            }
            case 215: {
                L1SpawnUtil.spawnEffect(80151, this._skill.getBuffDuration(), this._targetX, this._targetY, this._user.getMapId(), this._user, this._skillId);
                return;
            }
            case 220: {
                L1SpawnUtil.spawnEffect(80152, this._skill.getBuffDuration(), this._targetX, this._targetY, this._user.getMapId(), this._user, this._skillId);
                return;
            }
            default: {
                if (Skill_type1.RESTRICTIONS.contains((Integer)this._skillId)) {
                    this._isCounterMagic = false;
                }
                if (this._skillId == 87 && this._user instanceof L1PcInstance) {
                    this._target.onAction(this._player);
                }
                if (this._skillId == 208 && this._user instanceof L1PcInstance) {
                    this._target.onAction(this._player);
                }
                if (!this.isTargetCalc(this._target)) {
                    return;
                }
                else {
                    try {
                        TargetStatus ts = null;
                        L1Character cha = null;
                        int drainMana = 0;
                        boolean isSuccess = false;
                        int undeadType = 0;
                        int heal = 0;
                        Iterator<TargetStatus> iter = (Iterator)this._targetList.iterator();
                        while (iter.hasNext()) {
                            ts = null;
                            cha = null;
                            isSuccess = false;
                            undeadType = 0;
                            ts = iter.next();
                            cha = ts.getTarget();
                            if (this._npc != null) {
                                if (this._npc instanceof L1PetInstance && this.isParty(this._npc, cha)) {
                                    ts.isCalc(false);
                                    this._dmg = 0;
                                    continue;
                                }
                                else if (this._npc instanceof L1SummonInstance && this.isParty(this._npc, cha)) {
                                    ts.isCalc(false);
                                    this._dmg = 0;
                                    continue;
                                }
                            }
                            if (!ts.isCalc() || !this.isTargetCalc(cha)) {
                                ts.isCalc(false);
                            }
                            else {
                                L1Magic magic = new L1Magic(this._user, cha);
                                magic.setLeverage(this.getLeverage());
                                if (cha instanceof L1MonsterInstance) {
                                    undeadType = ((L1MonsterInstance)cha).getNpcTemplate().get_undead();
                                }
                                if ((this._skill.getType() == 4 || this._skill.getType() == 1) && this.isTargetFailure(cha)) {
                                    iter.remove();
                                }
                                else {
                                    if (cha instanceof L1PcInstance || cha instanceof L1NpcInstance) {
                                        if (this._skillTime == 0) {
                                            this._getBuffIconDuration = this._skill.getBuffDuration();
                                        }
                                        else {
                                            this._getBuffIconDuration = this._skillTime;
                                        }
                                    }
                                    this.deleteRepeatedSkills(cha);
                                    switch (this._skill.getType()) {
                                        case 1:
                                        case 4: {
                                            isSuccess = magic.calcProbabilityMagic(this._skillId);
											

											// ⭐ 新增：如果是玩家施放技能 18，顯示成功率
if (this._skillId == 18 && this._user instanceof L1PcInstance) {
    L1PcInstance pc = (L1PcInstance) this._user;
    int prob = magic.getShowProbability(); // 取得剛才存好的機率
    
    // 限制範圍並顯示
    int finalShowProb = Math.min(Math.max(prob, 0), 100);
    pc.sendPackets(new S_ServerMessage(166, "法術成功率：" + finalShowProb + "%"));
}


                                            if (this._type == 4) {
                                                isSuccess = true;
                                            }
                                            if (cha.hasSkillEffect(153) && this._skillId != 153) {
                                                cha.removeSkillEffect(153);
                                            }
                                            if (this._skillId != 66 || this._skillId != 212 || this._skillId != 103) {
                                                cha.removeSkillEffect(66);
                                                cha.removeSkillEffect(212);
                                                cha.removeSkillEffect(103);
                                            }
                                            if (isSuccess) {
                                                if (this.isUseCounterMagic(cha)) {
                                                    iter.remove();
                                                    continue;
                                                }
                                                else if (this._skillId == 174 && cha instanceof L1PcInstance) {
                                                    L1PcInstance pc = (L1PcInstance)cha;
                                                    pc.sendPackets(new S_PacketBox(132, 0));
                                                    break;
                                                }
                                                else {
                                                    break;
                                                }
                                            }
                                            else {
                                                if ((this._skillId == 66 || this._skillId == 212 || this._skillId == 103) && cha instanceof L1PcInstance) {
                                                    L1PcInstance pc = (L1PcInstance)cha;
                                                    pc.sendPackets(new S_ServerMessage(297));
                                                }
                                                iter.remove();
                                                continue;
                                            }
                                        }
                                        case 16: {
                                            this._heal = magic.calcHealing(this._skillId);
                                            if (cha.hasSkillEffect(170)) {
                                                this._heal <<= 1;
                                            }
                                            if (cha.hasSkillEffect(173)) {
                                                this._heal >>= 1;
                                            }
                                            if (cha.hasSkillEffect(4012)) {
                                                this._heal >>= 1;
                                            }
                                            if (cha.hasSkillEffect(4013)) {
                                                this._heal *= -1;
                                            }
                                            if (this._skillId == 164 && this._player.getlogpcpower_SkillFor3() != 0 && this._player.getlogpcpower_SkillFor3() >= 1 && RandomArrayList.getInc(100, 1) <= this._player.getlogpcpower_SkillFor3()) {
                                                this._heal *= 2;
                                                this._player.sendPackets(new S_SkillSound(this._player.getId(), 5377));
                                            }
                                            if (cha.isPinkName()) {
                                                L1PinkName.runPinkNameTimer(this._player);
                                            }
                                            double con1 = 1.0;
                                            if (this._calcType == 1) {
                                                for (int Int = 1; Int < this._player.getInt() / 15; ++Int) {
                                                    con1 += 0.1;
                                                }
                                            }
                                            this._heal = (int)((double)this._heal * con1);
                                            if (this._calcType == 1 && this._player.isGm()) {
                                                this._player.sendPackets(new S_SystemMessage("治癒:" + this._heal));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                        case 64: {
                                            if (this._user.getId() != cha.getId()) {
                                                if (this.isUseCounterMagic(cha)) {
                                                    iter.remove();
                                                    continue;
                                                }
                                                else {
                                                    this._dmg = magic.calcMagicDamage(this._skillId);
                                                    if (cha.hasSkillEffect(153)) {
                                                        cha.removeSkillEffect(153);
                                                        break;
                                                    }
                                                    else {
                                                        break;
                                                    }
                                                }
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                    }
                                    SkillMode mode = L1SkillMode.get().getSkill(this._skillId);
                                    if (mode != null) {
                                        if (this._user instanceof L1PcInstance) {
                                            switch (this._skillId) {
                                                case 5:
                                                case 69: {
                                                    this._dmg = mode.start(this._player, cha, magic, this._bookmarkId);
                                                    break;
                                                }
                                                case 116:
                                                case 118: {
                                                    this._dmg = mode.start(this._player, cha, magic, this._targetID);
                                                    break;
                                                }
                                                default: {
                                                    this._dmg = mode.start(this._player, cha, magic, this._getBuffIconDuration);
                                                    break;
                                                }
                                            }
                                        }
                                        if (this._user instanceof L1NpcInstance) {
                                            this._dmg = mode.start(this._npc, cha, magic, this._getBuffIconDuration);
                                        }
                                    }
                                    else if (cha.hasSkillEffect(this._skillId)) {
                                        this.addMagicList(cha, true);
                                        continue;
                                    }
                                    if (this._skillId == 13 || this._skillId == 194 || this._skillId == 213) {
                                        if (cha instanceof L1NpcInstance) {
                                            L1NpcInstance npc = (L1NpcInstance)cha;
                                            int hiddenStatus = npc.getHiddenStatus();
                                            if (hiddenStatus == 1) {
                                                npc.appearOnGround(this._player);
                                            }
                                        }
                                    }
                                    else if (this._skillId == 72) {
                                        if (cha instanceof L1PcInstance) {
                                            this._dmg = magic.calcMagicDamage(this._skillId);
                                        }
                                        else if (cha instanceof L1NpcInstance) {
                                            L1NpcInstance npc = (L1NpcInstance)cha;
                                            int hiddenStatus = npc.getHiddenStatus();
                                            if (hiddenStatus == 1) {
                                                npc.appearOnGround(this._player);
                                            }
                                            else {
                                                this._dmg = 0;
                                            }
                                        }
                                        else {
                                            this._dmg = 0;
                                        }
                                    }
                                    else if (this._skillId == 1 || this._skillId == 19 || this._skillId == 35 || this._skillId == 57 || this._skillId == 49 || this._skillId == 158 || this._skillId == 164) {
                                        if (this._user instanceof L1PcInstance) {
                                            cha.removeSkillEffect(170);
                                        }
                                    }
                                    else if (this._skillId == 10 || this._skillId == 28) {
                                        heal = this._dmg;
                                    }
                                    else if (this._skillId == 10026 || this._skillId == 10027 || this._skillId == 10028 || this._skillId == 10029) {
                                        if (this._user instanceof L1NpcInstance) {
                                            this._user.broadcastPacketAll(new S_NpcChat(this._npc, (L1PcInstance)this._target, "$3717"));
                                        }
                                        else {
                                            this._player.broadcastPacketAll(new S_Chat(this._player, "$3717"));
                                        }
                                    }
                                    else if (this._skillId == 10057) {
                                        L1Teleport.teleportToTargetFront(cha, this._user, 1);
                                    }
                                    else if (this._skillId == 10058) {
                                        if (this._calcType == 3) {
                                            List<L1Object> objects = (List)World.get().getVisibleObjects(this._user, 12);
                                            for (L1Object tgobj : objects) {
                                                if (tgobj == null) {
                                                    continue;
                                                }
                                                else if (tgobj == this._user) {
                                                    continue;
                                                }
                                                else if (tgobj instanceof L1PcInstance) {
                                                    L1PcInstance cha2 = (L1PcInstance)tgobj;
                                                    L1Teleport.teleportToTargetFront(cha2, this._user, 1);
                                                }
                                                else {
                                                    continue;
                                                }
                                            }
                                        }
                                    }
                                    else if (this._skillId == 29 || this._skillId == 76 || this._skillId == 152) {
                                        if (cha instanceof L1PcInstance) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            if (pc2.getHasteItemEquipped() > 0) {
                                                continue;
                                            }
                                        }
                                        if (cha.getBraveSpeed() == 5) {
                                            continue;
                                        }
                                        else {
                                            switch (cha.getMoveSpeed()) {
                                                case 0: {
                                                    if (cha instanceof L1PcInstance) {
                                                        L1PcInstance pc2 = (L1PcInstance)cha;
                                                        pc2.sendPackets(new S_SkillHaste(pc2.getId(), 2, this._getBuffIconDuration));
                                                    }
                                                    cha.broadcastPacketAll(new S_SkillHaste(cha.getId(), 2, this._getBuffIconDuration));
                                                    cha.setMoveSpeed(2);
                                                    break;
                                                }
                                                case 1: {
                                                    int skillNum = 0;
                                                    if (cha.hasSkillEffect(43)) {
                                                        skillNum = 43;
                                                    }
                                                    else if (cha.hasSkillEffect(54)) {
                                                        skillNum = 54;
                                                    }
                                                    else if (cha.hasSkillEffect(1001)) {
                                                        skillNum = 1001;
                                                    }
                                                    if (skillNum != 0) {
                                                        cha.removeSkillEffect(skillNum);
                                                        cha.removeSkillEffect(this._skillId);
                                                        cha.setMoveSpeed(0);
                                                        continue;
                                                    }
                                                    else {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if (this._skillId == 11) {
                                        L1DamagePoison.doInfection(this._user, cha, 3000, 5);
                                    }
                                    else if (this._skillId == 47) {
                                        if (cha instanceof L1PcInstance) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addDmgup(-5);
                                            pc2.addHitup(-1);
                                        }
                                    }
                                    else if (this._skillId == 56) {
                                        if (cha instanceof L1PcInstance) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addHitup(-6);
                                            pc2.addAc(12);
                                        }
                                    }
                                    else if (this._skillId == 157) {
                                        if (cha instanceof L1PcInstance) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            boolean castle_area = L1CastleLocation.checkInAllWarArea(pc2.getX(), pc2.getY(), pc2.getMapId());
                                            if (castle_area) {
                                                return;
                                            }
                                            else {
                                                pc2.sendPacketsAll(new S_Poison(pc2.getId(), 2));
                                                pc2.sendPackets(new S_Paralysis(4, true));
                                                if (pc2.hasSkillEffect(1010) || pc2.hasSkillEffect(1011)) {
                                                    pc2.cureParalaysis();
                                                }
                                            }
                                        }
                                        else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                                            L1NpcInstance npc = (L1NpcInstance)cha;
                                            npc.broadcastPacketAll(new S_Poison(npc.getId(), 2));
                                            npc.setParalyzed(true);
                                        }
                                    }
                                    else if (this._skillId == 50) {
                                        this._isFreeze = magic.calcProbabilityMagic(this._skillId);
                                        if (this._isFreeze) {
                                            int time = this._skill.getBuffDuration() + 1;
                                            if (cha instanceof L1PcInstance) {
                                                L1PcInstance pc3 = (L1PcInstance)cha;
                                                boolean castle_area2 = L1CastleLocation.checkInAllWarArea(pc3.getX(), pc3.getY(), pc3.getMapId());
                                                if (castle_area2 && pc3.castleWarResult()) {
                                                    return;
                                                }
                                                else {
                                                    L1SpawnUtil.spawnEffect(81168, time, pc3.getX(), pc3.getY(), this._user.getMapId(), this._user, 0);
                                                    pc3.sendPacketsAll(new S_Poison(pc3.getId(), 2));
                                                    pc3.sendPackets(new S_Paralysis(4, true));
                                                }
                                            }
                                            else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                                                L1NpcInstance npc2 = (L1NpcInstance)cha;
                                                L1SpawnUtil.spawnEffect(81168, time, npc2.getX(), npc2.getY(), this._user.getMapId(), this._user, 0);
                                                npc2.broadcastPacketAll(new S_Poison(npc2.getId(), 2));
                                                npc2.setParalyzed(true);
                                            }
                                        }
                                    }
                                    else if (this._skillId == 80) {
                                        this._isFreeze = magic.calcProbabilityMagic(this._skillId);
                                        if (this._isFreeze) {
                                            int time = this._skill.getBuffDuration() + 1;
                                            if (cha instanceof L1PcInstance) {
                                                L1PcInstance l1PcInstance = (L1PcInstance)cha;
                                            }
                                            else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                                                L1NpcInstance npc2 = (L1NpcInstance)cha;
                                                L1SpawnUtil.spawnEffect(81168, time, npc2.getX(), npc2.getY(), this._user.getMapId(), this._user, 0);
                                                npc2.broadcastPacketAll(new S_Poison(npc2.getId(), 2));
                                                npc2.setParalyzed(true);
                                            }
                                        }
                                    }
                                    else if (this._skillId == 18) {
                                        if (undeadType == 1 || undeadType == 3) {
                                            this._dmg = cha.getCurrentHp();
                                        }
                                    }
                                    else if (this._skillId == 39) {
                                        Random random = new Random();
                                        int chance = random.nextInt(10) + 5;
                                        drainMana = chance + this._user.getInt() / 2;
                                        if (cha.getCurrentMp() < drainMana) {
                                            drainMana = cha.getCurrentMp();
                                        }
                                    }
                                    else if (this._skillId == 27) {
                                        if (this._calcType == 1 || this._calcType == 3) {
                                            if (cha instanceof L1PcInstance) {
                                                L1PcInstance pc2 = (L1PcInstance)cha;
                                                L1ItemInstance weapon = pc2.getWeapon();
                                                if (weapon != null) {
                                                    Random random2 = new Random();
                                                    int weaponDamage = random2.nextInt(this._user.getInt() / 3) + 1;
                                                    pc2.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
                                                    pc2.getInventory().receiveDamage(weapon, weaponDamage);
                                                }
                                            }
                                        }
                                        else {
                                            ((L1NpcInstance)cha).setWeaponBreaked(true);
                                        }
                                    }
                                    else if (this._skillId == 66) {
                                        if (cha instanceof L1PcInstance) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.sendPackets(new S_Paralysis(3, true));
                                        }
                                        cha.setSleeped(true);
                                    }
                                    else if (this._skillId == 183) {
                                        if (cha instanceof L1PcInstance) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(10);
                                        }
                                    }
                                    else if (this._skillId == 193 && cha instanceof L1PcInstance) {
                                        L1PcInstance pc2 = (L1PcInstance)cha;
                                        pc2.addStr(-3);
                                        pc2.addInt(-3);
                                    }
                                    if (this._calcType == 1 || this._calcType == 3) {
                                        if (this._skillId == 73) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            L1ItemInstance item = pc2.getInventory().getItem(this._itemobjid);
                                            if (item != null && item.getItem().getType2() == 1) {
                                                int item_type = item.getItem().getType2();
                                                int safe_enchant = item.getItem().get_safeenchant();
                                                int enchant_level = item.getEnchantLevel();
                                                String item_name = item.getName();
                                                if (safe_enchant < 0) {
                                                    pc2.sendPackets(new S_ServerMessage(79));
                                                }
                                                else if (safe_enchant == 0) {
                                                    pc2.sendPackets(new S_ServerMessage(79));
                                                }
                                                else if (item_type == 1 && enchant_level == 0) {
                                                    if (!item.isIdentified()) {
                                                        pc2.sendPackets(new S_ServerMessage(161, item_name, "$245", "$247"));
                                                    }
                                                    else {
                                                        item_name = "+0 " + item_name;
                                                        pc2.sendPackets(new S_ServerMessage(161, "+0 " + item_name, "$245", "$247"));
                                                    }
                                                    item.setEnchantLevel(1);
                                                    pc2.getInventory().updateItem(item, 4);
                                                }
                                                else {
                                                    pc2.sendPackets(new S_ServerMessage(79));
                                                }
                                            }
                                            else {
                                                pc2.sendPackets(new S_ServerMessage(79));
                                            }
                                        }
                                        else if (this._skillId == 100) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            Random random3 = new Random();
                                            L1ItemInstance item2 = pc2.getInventory().getItem(this._itemobjid);
                                            if (item2 != null) {
                                                int dark = (int)(10.0 + (double)pc2.getLevel() * 0.8 + (double)(pc2.getWis() - 6) * 1.2);
                                                int brave = (int)((double)dark / 2.1);
                                                int wise = (int)((double)brave / 2.0);
                                                int kayser = (int)((double)wise / 1.9);
                                                int chance2 = random3.nextInt(100) + 1;
                                                if (item2.getItem().getItemId() == 40320) {
                                                    pc2.getInventory().removeItem(item2, 1L);
                                                    if (dark >= chance2) {
                                                        pc2.getInventory().storeItem(40321, 1L);
                                                        pc2.sendPackets(new S_ServerMessage(403, "$2475"));
                                                    }
                                                    else {
                                                        pc2.sendPackets(new S_ServerMessage(280));
                                                    }
                                                }
                                                else if (item2.getItem().getItemId() == 40321) {
                                                    pc2.getInventory().removeItem(item2, 1L);
                                                    if (brave >= chance2) {
                                                        pc2.getInventory().storeItem(40322, 1L);
                                                        pc2.sendPackets(new S_ServerMessage(403, "$2476"));
                                                    }
                                                    else {
                                                        pc2.sendPackets(new S_ServerMessage(280));
                                                    }
                                                }
                                                else if (item2.getItem().getItemId() == 40322) {
                                                    pc2.getInventory().removeItem(item2, 1L);
                                                    if (wise >= chance2) {
                                                        pc2.getInventory().storeItem(40323, 1L);
                                                        pc2.sendPackets(new S_ServerMessage(403, "$2477"));
                                                    }
                                                    else {
                                                        pc2.sendPackets(new S_ServerMessage(280));
                                                    }
                                                }
                                                else if (item2.getItem().getItemId() == 40323) {
                                                    pc2.getInventory().removeItem(item2, 1L);
                                                    if (kayser >= chance2) {
                                                        pc2.getInventory().storeItem(40324, 1L);
                                                        pc2.sendPackets(new S_ServerMessage(403, "$2478"));
                                                    }
                                                    else {
                                                        pc2.sendPackets(new S_ServerMessage(280));
                                                    }
                                                }
                                            }
                                        }
                                        else if (this._skillId == 78) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.stopHpRegeneration();
                                            pc2.stopMpRegeneration();
                                        }
                                        if (this._skillId == 2) {
                                            if (this._skillId == 2) {
                                                L1PcInstance pc2 = (L1PcInstance)this._target;
                                                pc2.sendPackets(new S_Sound(145));
                                            }
                                        }
                                    else if (this._skillId == 114) { // 114: 激勵士氣
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                        pc2.addHitup(5);     // 增加 近戰命中率 5
                                        pc2.addBowHitup(5);  // 增加 遠距離命中率 5
                                        pc2.addMr(20);       // 增加 魔法防禦(MR) 20
                                            pc2.sendPackets(new S_SPMR(pc2));
                                            pc2.sendPackets(new S_PacketBoxIconAura(113, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 161) {
                                            this._isFreeze = magic.calcProbabilityMagic(161);
                                            if (this._isFreeze && cha instanceof L1PcInstance) {
                                                L1PcInstance pc2 = (L1PcInstance)cha;
                                                for (L1Object object : World.get().getVisibleObjects(pc2, 3)) {
                                                    if (object instanceof L1PcInstance) {
                                                        L1PcInstance red = (L1PcInstance)object;
                                                        L1SkillUse l1skilluse = new L1SkillUse();
                                                        pc2.sendPacketsAll(new S_SkillSound(red.getId(), 10708));
                                                        l1skilluse.handleCommands(pc2, 161, pc2.getId(), pc2.getX(), pc2.getY(), 0, 4);
                                                    }
                                                }
                                            }
                                        }
                                    else if (this._skillId == 115) { // 115: 鋼鐵士氣
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                        pc2.addAc(-8);       // 增加 防禦力 8 (數值越低防禦越高)
                                            pc2.sendPackets(new S_PacketBoxIconAura(114, this._getBuffIconDuration));
                                        }
                                    else if (this._skillId == 117) { // 117: 衝擊士氣
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                        pc2.addDmgup(10);    // 增加 近戰攻擊力 10
                                        pc2.addHitup(5);     // 增加 近戰命中率 5
                                            pc2.sendPackets(new S_PacketBoxIconAura(116, this._getBuffIconDuration));
                                        }
                                    else if (this._skillId == 119) { // 119: 王者加護
                                        L1PcInstance pc2 = (L1PcInstance)cha;
                                        pc2.setSkillEffect(68, this._getBuffDuration); // 在背景賦予聖結界(68)的實際減傷狀態
                                        pc2.sendPackets(new S_PacketBox(40, this._getBuffIconDuration)); // 發送聖結界的UI圖示
                                    }
                                        else if (this._skillId == 3) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(-2);
                                            pc2.sendPackets(new S_SkillIconShield(5, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 110) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addDex(3);
                                            pc2.sendPackets(new S_Dexup(pc2, 2, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 109) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addStr(3);
                                            pc2.sendPackets(new S_Strup(pc2, 2, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 107) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            L1ItemInstance item = pc2.getInventory().getItem(this._itemobjid);
                                            if (item != null && item.getItem().getType2() == 1) {
                                                item.setSkillWeaponEnchant(pc2, this._skillId, this._skill.getBuffDuration() * 1000);
                                                pc2.sendPackets(new S_PacketBox(154, this._skill.getCastGfx(), this._skill.getBuffDuration()));
                                            }
                                            else {
                                                pc2.sendPackets(new S_ServerMessage(79));
                                            }
                                        }
                                        else if (this._skillId == 12) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            L1ItemInstance item = pc2.getInventory().getItem(this._itemobjid);
                                            if (item != null && item.getItem().getType2() == 1) {
                                                pc2.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
                                                item.setSkillWeaponEnchant(pc2, this._skillId, this._skill.getBuffDuration() * 1000);
                                            }
                                            else {
                                                pc2.sendPackets(new S_ServerMessage(79));
                                            }
                                        }
                                        else if (this._skillId == 8 || this._skillId == 48) {
                                            if (!(cha instanceof L1PcInstance)) {
                                                return;
                                            }
                                            else {
                                                L1PcInstance pc2 = (L1PcInstance)cha;
                                                if (pc2.getWeapon() == null) {
                                                    pc2.sendPackets(new S_ServerMessage(79));
                                                    return;
                                                }
                                                else {
                                                    for (L1ItemInstance item : pc2.getInventory().getItems()) {
                                                        if (pc2.getWeapon().equals(item)) {
                                                            pc2.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
                                                            item.setSkillWeaponEnchant(pc2, this._skillId, this._skill.getBuffDuration() * 1000);
                                                            pc2.setSkillEffect(48, this._skill.getBuffDuration() * 1000);
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if (this._skillId == 160) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.setSkillEffect(160, 960000);
                                            pc2.sendPackets(new S_PacketBox(132, pc2.getEr()));
                                        }
                                        else if (this._skillId == 159) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(-7);
                                            pc2.sendPackets(new S_SkillIconShield(7, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 129) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addMr(10);
                                            pc2.sendPackets(new S_SPMR(pc2));
                                        }
                                        else if (this._skillId == 137) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addWis(3);
                                            pc2.resetBaseMr();
                                        }
                                        else if (this._skillId == 138) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addWind(10);
                                            pc2.addWater(10);
                                            pc2.addFire(10);
                                            pc2.addEarth(10);
                                            pc2.sendPackets(new S_OwnCharAttrDef(pc2));
                                        }
                                        else if (this._skillId == 147) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            int attr = pc2.getElfAttr();
                                            if (attr == 1) {
                                                pc2.addEarth(50);
                                            }
                                            else if (attr == 2) {
                                                pc2.addFire(50);
                                            }
                                            else if (attr == 4) {
                                                pc2.addWater(50);
                                            }
                                            else if (attr == 8) {
                                                pc2.addWind(50);
                                            }
                                        }
                                        else if (this._skillId == 60 || this._skillId == 97) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.sendPackets(new S_Invis(pc2.getId(), 1));
                                            pc2.broadcastPacketAll(new S_RemoveObject(pc2));
                                        }
                                        else if (this._skillId == 168) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(-10);
                                            pc2.sendPackets(new S_SkillIconShield(10, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 151) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(-6);
                                            pc2.sendPackets(new S_SkillIconShield(6, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 42) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addStr(5);
                                            pc2.sendPackets(new S_Strup(pc2, 5, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 26) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addDex(5);
                                            pc2.sendPackets(new S_Dexup(pc2, 5, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 148) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.sendPackets(new S_PacketBoxIconAura(147, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 155) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addDmgup(4);
                                            pc2.sendPackets(new S_PacketBoxIconAura(154, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 163) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.sendPackets(new S_PacketBoxIconAura(162, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 149) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addBowHitup(6);
                                            pc2.sendPackets(new S_PacketBoxIconAura(148, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 156) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addBowHitup(2);
                                            pc2.addBowDmgup(3);
                                            pc2.sendPackets(new S_PacketBoxIconAura(155, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 166) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addBowDmgup(6);
                                            pc2.addBowHitup(-1);
                                            pc2.sendPackets(new S_PacketBoxIconAura(165, this._getBuffIconDuration));
                                        }
                                        else if (this._skillId == 55) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(10);
                                            pc2.stopHpRegeneration();
                                        }
                                        else if (this._skillId == 54) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            if (pc2.getHasteItemEquipped() > 0) {
                                                continue;
                                            }
                                            else if (pc2.getMoveSpeed() != 2) {
                                                pc2.setDrink(false);
                                                pc2.setMoveSpeed(1);
                                                pc2.sendPackets(new S_SkillHaste(pc2.getId(), 1, this._getBuffIconDuration));
                                                pc2.broadcastPacketAll(new S_SkillHaste(pc2.getId(), 1, 0));
                                            }
                                            else {
                                                int skillNum2 = 0;
                                                if (pc2.hasSkillEffect(29)) {
                                                    skillNum2 = 29;
                                                }
                                                else if (pc2.hasSkillEffect(76)) {
                                                    skillNum2 = 76;
                                                }
                                                else if (pc2.hasSkillEffect(152)) {
                                                    skillNum2 = 152;
                                                }
                                                if (skillNum2 != 0) {
                                                    pc2.removeSkillEffect(skillNum2);
                                                    pc2.removeSkillEffect(54);
                                                    pc2.setMoveSpeed(0);
                                                    continue;
                                                }
                                            }
                                        }
                                        else if (this._skillId == 52 || this._skillId == 101 || this._skillId == 150) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.setBraveSpeed(4);
                                            pc2.sendPackets(new S_SkillBrave(pc2.getId(), 4, this._getBuffIconDuration));
                                            pc2.broadcastPacketAll(new S_SkillBrave(pc2.getId(), 4, 0));
                                        }
                                        else if (this._skillId == 204) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addDmgup(4);
                                            pc2.addHitup(4);
                                        }
                                        else if (this._skillId == 214) {
                                            L1PcInstance pc2 = (L1PcInstance)cha;
                                            pc2.addAc(-20);
                                        }
                                    }
                                    if (this._calcType == 2 || this._calcType == 4) {
                                        if (this._skillId == 36 && ((L1MonsterInstance)cha).getNpcTemplate().isTamable()) {
                                            if (ConfigOther.petcountcha) {
                                                int petcost = 0;
                                                int petCount = 0;
                                                Object[] petlist = this._user.getPetList().values().toArray();
                                                if (petlist.length > ConfigOther.tamingmonstercount) {
                                                    this._player.sendPackets(new S_ServerMessage(489));
                                                    return;
                                                }
                                                else {
                                                    Object[] array;
                                                    for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                                                        Object pet = array[i];
                                                        petcost += ((L1NpcInstance)pet).getPetcost();
                                                    }
                                                    int charisma = this._user.getCha();
                                                    charisma -= petcost;
                                                    charisma /= 2;
                                                    if (charisma <= 0) {
                                                        this._player.sendPackets(new S_ServerMessage(489));
                                                        return;
                                                    }
                                                    else {
                                                        L1SummonInstance summon = new L1SummonInstance(this._targetNpc, this._user, false);
                                                        ((L1NpcInstance)(this._target = summon)).setPetcost(2);
                                                    }
                                                }
                                            }
                                            else {
                                                int petcost = 0;
                                                Object[] petlist2 = this._user.getPetList().values().toArray();
                                                if (petlist2.length > ConfigOther.tamingmonstercount) {
                                                    this._player.sendPackets(new S_ServerMessage(489));
                                                    return;
                                                }
                                                else {
                                                    Object[] array2;
                                                    for (int length2 = (array2 = petlist2).length, j = 0; j < length2; ++j) {
                                                        Object pet2 = array2[j];
                                                        petcost += ((L1NpcInstance)pet2).getPetcost();
                                                    }
                                                    int charisma2 = this._user.getCha();
                                                    if (this._player.isElf()) {
                                                        charisma2 += 12;
                                                    }
                                                    else if (this._player.isWizard()) {
                                                        charisma2 += 6;
                                                    }
                                                    charisma2 -= petcost;
                                                    if (charisma2 >= 6) {
                                                        L1SummonInstance summon2 = new L1SummonInstance(this._targetNpc, this._user, false);
                                                        this._target = summon2;
                                                    }
                                                    else {
                                                        this._player.sendPackets(new S_ServerMessage(319));
                                                    }
                                                }
                                            }
                                        }
                                        else if (this._skillId == 41) {
                                            int petcost = 0;
                                            Object[] petlist2 = this._user.getPetList().values().toArray();
                                            Object[] array3;
                                            for (int length3 = (array3 = petlist2).length, k = 0; k < length3; ++k) {
                                                Object pet2 = array3[k];
                                                petcost += ((L1NpcInstance)pet2).getPetcost();
                                            }
                                            int charisma2 = this._user.getCha();
                                            if (this._player.isElf()) {
                                                charisma2 += 12;
                                            }
                                            else if (this._player.isWizard()) {
                                                charisma2 += 6;
                                            }
                                            charisma2 -= petcost;
                                            if (charisma2 >= 6) {
                                                L1SummonInstance summon2 = new L1SummonInstance(this._targetNpc, this._user, true);
                                                this._target = summon2;
                                            }
                                            else {
                                                this._player.sendPackets(new S_ServerMessage(319));
                                            }
                                        }
                                        else if (this._skillId == 23) {
                                            if (cha instanceof L1MonsterInstance) {
                                                L1Npc npcTemp = ((L1MonsterInstance)cha).getNpcTemplate();
                                                int weakAttr = npcTemp.get_weakAttr();
                                                if ((weakAttr & 0x1) == 0x1) {
                                                    cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2169));
                                                }
                                                if ((weakAttr & 0x2) == 0x2) {
                                                    cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2167));
                                                }
                                                if ((weakAttr & 0x4) == 0x4) {
                                                    cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2166));
                                                }
                                                if ((weakAttr & 0x8) == 0x8) {
                                                    cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2168));
                                                }
                                            }
                                        }
                                        else if (this._skillId == 145) {
                                            if (cha instanceof L1SummonInstance) {
                                                L1SummonInstance summon3 = (L1SummonInstance)cha;
                                                summon3.broadcastPacketAll(new S_SkillSound(summon3.getId(), 2245));
                                                summon3.returnToNature();
                                            }
                                            else if (this._user instanceof L1PcInstance) {
                                                this._player.sendPackets(new S_ServerMessage(79));
                                            }
                                        }
                                    }
                                    if (this._skill.getType() == 16 && this._calcType == 2 && undeadType == 1) {
                                        this._dmg = this._heal;
                                    }
                                    if (this._skill.getType() == 16 && this._calcType == 2 && undeadType == 4) {
                                        this._heal = 0;
                                    }
                                    if ((cha instanceof L1TowerInstance || cha instanceof L1DoorInstance) && this._heal < 0) {
                                        this._heal = 0;
                                    }
                                    if (this._dmg != 0 || drainMana != 0) {
                                        magic.commit(this._dmg, drainMana);
                                    }
                                    if (this._skill.getType() == 16 && this._heal != 0 && undeadType != 1) {
                                        cha.setCurrentHp(this._heal + cha.getCurrentHp());
                                    }
                                    if (heal > 0) {
                                        this._user.setCurrentHp(heal + this._user.getCurrentHp());
                                    }
                                    if (cha instanceof L1PcInstance) {
                                        L1PcInstance pc2 = (L1PcInstance)cha;
                                        pc2.turnOnOffLight();
                                        pc2.sendPackets(new S_OwnCharAttrDef(pc2));
                                        pc2.sendPackets(new S_OwnCharStatus(pc2));
                                        this.sendHappenMessage(pc2);
                                    }
                                    this.addMagicList(cha, false);
                                }
                            }
                        }
                        if (this._skillId == 13 || this._skillId == 72 || this._skillId == 194 || this._skillId == 213) {
                            this.detection(this._player);
                        }
                    }
                    catch (Exception e) {
                        L1SkillUse._log.error(e.getLocalizedMessage(), e);
                    }
                    return;
                }
            }
        }
    }
    
    private static void summonMonster(L1PcInstance pc, String s) {
        int summonid = 0;
        int levelrange = 0;
        int summoncost = 0;
        String[] summonstr_list = { "7", "263", "519", "8", "264", "520", "9", "265", "521", "10", "266", "522", "11", "267", "523", "12", "268", "524", "13", "269", "525", "14", "270", "526", "15", "271", "527", "16", "17", "18", "274" };
        int[] summonid_list = { 81210, 81211, 81212, 81213, 81214, 81215, 81216, 81217, 81218, 81219, 81220, 81221, 81222, 81223, 81224, 81225, 81226, 81227, 81228, 81229, 81230, 81231, 81232, 81233, 81234, 81235, 81236, 81237, 81238, 81239, 81240 };
        int[] summonlvl_list = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
        int[] summoncha_list = { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 14, 36, 36, 44 };
        int loop = 0;
        while (loop < summonstr_list.length) {
            if (s.equalsIgnoreCase(summonstr_list[loop])) {
                summonid = summonid_list[loop];
                levelrange = summonlvl_list[loop];
                summoncost = summoncha_list[loop];
                break;
            }
            else {
                ++loop;
            }
        }
        if (pc.isWizard() && pc.getlogpcpower_SkillFor2() != 0 && pc.getlogpcpower_SkillFor2() >= 1 && summonid == 81240) {
            summonid = 93142 + pc.getlogpcpower_SkillFor2();
        }
        if (pc.getLevel() < levelrange) {
            pc.sendPackets(new S_ServerMessage(743));
            return;
        }
        else if (pc.getCha() < summoncost) {
            pc.sendPackets(new S_ServerMessage(3039));
            return;
        }
        else {
            int petcost = 0;
            Object[] petlist = pc.getPetList().values().toArray();
            Object[] array;
            for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                Object pet = array[i];
                petcost += ((L1NpcInstance)pet).getPetcost();
            }
            int pcCha = pc.getCha();
            int charisma = 0;
            int summoncount = 0;
            if (levelrange <= 56 || levelrange == 64) {
                if (pcCha > 34) {
                    pcCha = 34;
                }
            }
            else if (levelrange == 60) {
                if (pcCha > 30) {
                    pcCha = 30;
                }
            }
            else if (levelrange > 64 && pcCha > 44) {
                pcCha = 44;
            }
            charisma = pc.getCha() + 6 - petcost;
            summoncount = charisma / summoncost;
            L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
            for (int cnt = 0; cnt < summoncount; ++cnt) {
                L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
                summon.setPetcost(summoncost);
            }
            return;
        }
    }
    
    private void detection(L1PcInstance pc) {
        if (!pc.isGmInvis() && pc.isInvisble()) {
            pc.delInvis();
            pc.beginInvisTimer();
        }
        for (L1PcInstance tgt : World.get().getVisiblePlayer(pc)) {
            if (!tgt.isGmInvis() && tgt.isInvisble()) {
                tgt.delInvis();
            }
        }
        WorldTrap.get().onDetection(pc);
    }
    
    private boolean isTargetCalc(L1Character cha) {
        if (this._skill.getTarget().equals("attack") && this._skillId != 18 && this.isPcSummonPet(cha) && (this._player.isSafetyZone() || cha.isSafetyZone() || this._player.checkNonPvP(this._player, cha))) {
            return false;
        }
        else {
            switch (this._skillId) {
                case 66: {
                    if (this._user.getId() == cha.getId()) {
                        return false;
                    }
                    else {
                        break;
                    }
                }
                case 69: {
                    if (this._user.getId() != cha.getId()) {
                        return false;
                    }
                    else {
                        break;
                    }
                }
                case 76: {
                    if (this._user.getId() == cha.getId()) {
                        return false;
                    }
                    else if (cha instanceof L1SummonInstance) {
                        L1SummonInstance summon = (L1SummonInstance)cha;
                        if (this._user.getId() == summon.getMaster().getId()) {
                            return false;
                        }
                        else {
                            break;
                        }
                    }
                    else if (cha instanceof L1PetInstance) {
                        L1PetInstance pet = (L1PetInstance)cha;
                        if (this._user.getId() == pet.getMaster().getId()) {
                            return false;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            return true;
        }
    }
    
    private boolean isPcSummonPet(L1Character cha) {
        switch (this._calcType) {
            case 1: {
                return true;
            }
            case 2: {
                if (cha instanceof L1SummonInstance) {
                    L1SummonInstance summon = (L1SummonInstance)cha;
                    if (summon.isExsistMaster()) {
                        return true;
                    }
                }
                return cha instanceof L1PetInstance || cha instanceof L1DeInstance;
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean isTargetFailure(L1Character cha) {
        boolean isTU = false;
        boolean isErase = false;
        boolean isManaDrain = false;
        int undeadType = 0;
        if (cha instanceof L1TowerInstance || cha instanceof L1DoorInstance) {
            return true;
        }
        else if (cha instanceof L1PcInstance) {
            if (this._calcType == 1 && this._player.checkNonPvP(this._player, cha)) {
                L1PcInstance pc = (L1PcInstance)cha;
                return this._player.getId() != pc.getId() && (pc.getClanid() == 0 || this._player.getClanid() != pc.getClanid());
            }
            else {
                return false;
            }
        }
        else {
            if (cha instanceof L1MonsterInstance) {
                isTU = ((L1MonsterInstance)cha).getNpcTemplate().get_IsTU();
            }
            if (cha instanceof L1MonsterInstance) {
                isErase = ((L1MonsterInstance)cha).getNpcTemplate().get_IsErase();
            }
            if (cha instanceof L1MonsterInstance) {
                undeadType = ((L1MonsterInstance)cha).getNpcTemplate().get_undead();
            }
            if (cha instanceof L1MonsterInstance) {
                isManaDrain = true;
            }
            return (this._skillId == 18 && (undeadType == 0 || undeadType == 2)) || (this._skillId == 18 && !isTU) || ((this._skillId == 153 || this._skillId == 29 || this._skillId == 39 || this._skillId == 76 || this._skillId == 152 || this._skillId == 167) && !isErase) || (this._skillId == 39 && !isManaDrain);
        }
    }
    
    private boolean isUseCounterMagic(L1Character cha) {
        if (this._isCounterMagic && cha.hasSkillEffect(31)) {
            cha.removeSkillEffect(31);
            int castgfx2 = SkillsTable.get().getTemplate(31).getCastGfx2();
            cha.broadcastPacketAll(new S_SkillSound(cha.getId(), castgfx2));
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPackets(new S_SkillSound(pc.getId(), castgfx2));
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    public static boolean isUseCounterMagic1(L1Character cha) {
        if (cha.hasSkillEffect(31)) {
            cha.removeSkillEffect(31);
            int castgfx2 = SkillsTable.get().getTemplate(31).getCastGfx2();
            cha.broadcastPacketAll(new S_SkillSound(cha.getId(), castgfx2));
            if (cha instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)cha;
                pc.sendPackets(new S_SkillSound(pc.getId(), castgfx2));
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    private void SpawnPoisonArea(L1Location baseloc) {
        int locX = 0;
        int locY = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                switch (this._user.getHeading()) {
                    case 0: {
                        locX = -1 + j;
                        locY = -1 * (-3 + i);
                        break;
                    }
                    case 1: {
                        locX = -1 * (2 + j - i);
                        locY = -1 * (-4 + j + i);
                        break;
                    }
                    case 2: {
                        locX = -1 * (3 - i);
                        locY = -1 + j;
                        break;
                    }
                    case 3: {
                        locX = -1 * (4 - j - i);
                        locY = -1 * (2 + j - i);
                        break;
                    }
                    case 4: {
                        locX = 1 - j;
                        locY = -1 * (3 - i);
                        break;
                    }
                    case 5: {
                        locX = -1 * (-2 - j + i);
                        locY = -1 * (4 - j - i);
                        break;
                    }
                    case 6: {
                        locX = -1 * (-3 + i);
                        locY = 1 - j;
                        break;
                    }
                    case 7: {
                        locX = -1 * (-4 + j + i);
                        locY = -1 * (-2 - j + i);
                        break;
                    }
                }
                L1EffectInstance effect = L1SpawnUtil.spawnEffect(86125, 3, baseloc.getX() - locX, baseloc.getY() - locY, this._user.getMapId(), this._user, 20011);
                effect.broadcastPacketAll(new S_SkillSound(effect.getId(), 1263));
            }
        }
    }
    
    public static void DoMySkillByMonster(L1PcInstance pc, int id) {
        if (pc.isDead()) {
            return;
        }
        else if (pc.hasSkillEffect(id)) {
            return;
        }
        else {
            L1SkillUse l1skilluse = new L1SkillUse();
            l1skilluse.handleCommands(pc, id, pc.getId(), pc.getX(), pc.getY(), 0, 4);
            return;
        }
    }
}
