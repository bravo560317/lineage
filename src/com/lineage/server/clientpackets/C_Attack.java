package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_AttackPacketPc;
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

public class C_Attack extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Attack.class);
    }
    
    public C_Attack() {
    }
    
    public C_Attack(byte[] abyte0, ClientExecutor client) {
        try {
            this.start(abyte0, client);
        }
        catch (Exception e) {
            C_Attack._log.error(e.getLocalizedMessage(), e);
        }
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
                                        C_Attack._log.error("要求角色攻擊:速度異常(" + pc.getName() + ")");
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
                                    else if (pc.hasSkillEffect(8007)) {
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
                                                    pc.sendPacketsAll(new S_AttackPacketPc(pc));
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
            return;
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
