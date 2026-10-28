package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.L1Character;
import com.lineage.server.world.World;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_AttackBow extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_AttackBow.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            pc.setFoeSlayer(false);
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isPrivateShop()) {
                            if (!pc.isFreezeAtion()) {
                                if (pc.getInventory().getWeight240() >= 197) {
                                    pc.sendPackets(new S_ServerMessage(110));
                                }
                                else {
                                    int result = pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.ATTACK);
                                    if (result == 2) {
                                        C_AttackBow._log.error("要求角色攻擊:速度異常(" + pc.getName() + ")");
                                    }
                                    if (pc.isInvisble()) {
                                        return;
                                    }
                                    else if (pc.isInvisDelay()) {
                                        return;
                                    }
                                    else if (pc.isParalyzedX()) {
                                        return;
                                    }
                                    else {
                                        if (pc.get_weaknss() != 0) {
                                            long h_time = Calendar.getInstance().getTimeInMillis() / 1000L;
                                            if (h_time - pc.get_weaknss_t() > 16L) {
                                                pc.set_weaknss(0, 0L);
                                            }
                                        }
                                        int targetId = 0;
                                        int locx = 0;
                                        int locy = 0;
                                        int targetX = 0;
                                        int targetY = 0;
                                        try {
                                            targetId = this.readD();
                                            locx = this.readH();
                                            locy = this.readH();
                                        }
                                        catch (Exception e) {
                                            this.over();
                                            return;
                                        }
                                        if (locx == 0) {
                                            return;
                                        }
                                        else if (locy == 0) {
                                            return;
                                        }
                                        else {
                                            targetX = locx;
                                            targetY = locy;
                                            L1Object target = World.get().findObject(targetId);
                                            if (target instanceof L1Character && target.getMapId() != pc.getMapId()) {
                                                return;
                                            }
                                            else {
                                                CheckUtil.isUserMap(pc);
                                                if (target instanceof L1NpcInstance) {
                                                    int hiddenStatus = ((L1NpcInstance)target).getHiddenStatus();
                                                    if (hiddenStatus == 1) {
                                                        return;
                                                    }
                                                    else if (hiddenStatus == 2) {
                                                        return;
                                                    }
                                                }
                                                if (pc.hasSkillEffect(78)) {
                                                    pc.killSkillEffectTimer(78);
                                                    pc.startHpRegeneration();
                                                    pc.startMpRegeneration();
                                                }
                                                pc.killSkillEffectTimer(32);
                                                pc.delInvis();
                                                pc.setRegenState(1);
                                                if (target != null && !((L1Character)target).isDead()) {
                                                    if (target instanceof L1PcInstance) {
                                                        L1PcInstance tg = (L1PcInstance)target;
                                                        pc.setNowTarget(tg);
                                                    }
                                                    target.onAction(pc);
                                                    return;
                                                }
                                                else {
                                                    pc.setHeading(pc.targetDirection(locx, locy));
                                                    L1ItemInstance weapon = pc.getWeapon();
                                                    if (weapon != null) {
                                                        int weaponType = weapon.getItem().getType1();
                                                        switch (weaponType) {
                                                            case 20: {
                                                                L1ItemInstance arrow = pc.getInventory().getArrow();
                                                                if (arrow != null) {
                                                                    int arrowGfxid = 66;
                                                                    switch (pc.getTempCharGfx()) {
                                                                        case 7959:
                                                                        case 7967:
                                                                        case 7968:
                                                                        case 7969:
                                                                        case 7970: {
                                                                            arrowGfxid = 7972;
                                                                            break;
                                                                        }
                                                                        case 8842:
                                                                        case 8900: {
                                                                            arrowGfxid = 8904;
                                                                            break;
                                                                        }
                                                                        case 8845:
                                                                        case 8913: {
                                                                            arrowGfxid = 8916;
                                                                            break;
                                                                        }
                                                                        case 13631: {
                                                                            arrowGfxid = 13656;
                                                                            break;
                                                                        }
                                                                        case 13635: {
                                                                            arrowGfxid = 13658;
                                                                            break;
                                                                        }
                                                                    }
                                                                    if (pc.getTempCharGfx() >= 13715 && pc.getTempCharGfx() <= 13745) {
                                                                        arrowGfxid = 11762;
                                                                    }
                                                                    this.arrowAction(pc, arrow, arrowGfxid, targetX, targetY);
                                                                    break;
                                                                }
                                                                else if (weapon.getName().equals("$1821")) {
                                                                    this.arrowAction(pc, null, 2349, targetX, targetY);
                                                                    break;
                                                                }
                                                                else {
                                                                    this.nullAction(pc);
                                                                    break;
                                                                }
                                                            }
                                                            case 62: {
                                                                L1ItemInstance sting = pc.getInventory().getSting();
                                                                if (sting != null) {
                                                                    int stingGfxid = 2989;
                                                                    switch (pc.getTempCharGfx()) {
                                                                        case 7959:
                                                                        case 7967:
                                                                        case 7968:
                                                                        case 7969:
                                                                        case 7970: {
                                                                            stingGfxid = 7972;
                                                                            break;
                                                                        }
                                                                        case 8842:
                                                                        case 8900: {
                                                                            stingGfxid = 8904;
                                                                            break;
                                                                        }
                                                                        case 8845:
                                                                        case 8913: {
                                                                            stingGfxid = 8916;
                                                                            break;
                                                                        }
                                                                        case 13631: {
                                                                            stingGfxid = 13656;
                                                                            break;
                                                                        }
                                                                        case 13635: {
                                                                            stingGfxid = 13658;
                                                                            break;
                                                                        }
                                                                    }
                                                                    if (pc.getTempCharGfx() >= 13715 && pc.getTempCharGfx() <= 13745) {
                                                                        stingGfxid = 11762;
                                                                    }
                                                                    this.arrowAction(pc, sting, stingGfxid, targetX, targetY);
                                                                    break;
                                                                }
                                                                else {
                                                                    this.nullAction(pc);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        return;
                                                    }
                                                    else {
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
                }
            }
            return;
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private void arrowAction(L1PcInstance pc, L1ItemInstance item, int gfx, int targetX, int targetY) {
        pc.sendPacketsAll(new S_UseArrowSkill(pc, gfx, targetX, targetY, 1));
        if (item != null) {
            pc.getInventory().removeItem(item, 1L);
        }
    }
    
    private void nullAction(L1PcInstance pc) {
        pc.sendPacketsAll(new S_UseArrowSkill(pc));
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
