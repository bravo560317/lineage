package com.lineage.data.item_etcitem.wand;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.Instance.L1MonsterInstance;
import java.util.Random;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Location;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Lightning_Magic_Wand extends ItemExecutor
{
    private static int _gfxid;
    
    static {
        Lightning_Magic_Wand._gfxid = 11736;
    }
    
    private Lightning_Magic_Wand() {
    }
    
    public static ItemExecutor get() {
        return new Lightning_Magic_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        int spellsc_x = data[1];
        int spellsc_y = data[2];
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        int chargeCount = item.getChargeCount();
        if (chargeCount <= 0) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            L1Object target = World.get().findObject(spellsc_objid);
            if (target != null) {
                this.doWandAction(pc, target);
            }
            else {
                pc.sendPacketsXR(new S_EffectLocation(new L1Location(spellsc_x, spellsc_y, (int)pc.getMapId()), Lightning_Magic_Wand._gfxid), 7);
            }
            item.setChargeCount(item.getChargeCount() - 1);
            pc.getInventory().updateItem(item, 128);
            return;
        }
    }
    
    private void doWandAction(L1PcInstance user, L1Object target) {
        Random _random = new Random();
        if (user.getId() == target.getId()) {
            return;
        }
        else if (!user.glanceCheck(target.getX(), target.getY())) {
            return;
        }
        else {
            int dmg = _random.nextInt(11) - 5 + user.getInt();
            dmg = Math.max(1, dmg);
            if (target instanceof L1PcInstance) {
                L1PcInstance pc = (L1PcInstance)target;
                if (pc.getMap().isSafetyZone(pc.getLocation()) || user.checkNonPvP(user, pc)) {
                    return;
                }
                else if (pc.hasSkillEffect(50) || pc.hasSkillEffect(78) || pc.hasSkillEffect(157)) {
                    return;
                }
                else {
                    int newHp = pc.getCurrentHp() - dmg;
                    if (newHp <= 0 && !pc.isGm()) {
                        pc.death(user);
                    }
                    pc.setCurrentHp(newHp);
                }
            }
            else if (target instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)target;
                switch (mob.getNpcId()) {
                    case 71100:
                    case 91072: {
                        user.sendPacketsXR(new S_EffectLocation(new L1Location(target.getX(), target.getY(), (int)user.getMapId()), Lightning_Magic_Wand._gfxid), 7);
                        return;
                    }
                    default: {
                        mob.receiveDamage(user, dmg);
                        break;
                    }
                }
            }
            user.setHeading(user.targetDirection(target.getX(), target.getY()));
            user.sendPacketsX10(new S_ChangeHeading(user));
            user.sendPacketsX10(new S_DoActionGFX(user.getId(), 17));
            if (target instanceof L1PcInstance) {
                L1PcInstance tgpc = (L1PcInstance)target;
                tgpc.sendPacketsX10(new S_SkillSound(tgpc.getId(), Lightning_Magic_Wand._gfxid));
                tgpc.sendPacketsX10(new S_DoActionGFX(tgpc.getId(), 2));
            }
            else if (target instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)target;
                mob.broadcastPacketX10(new S_SkillSound(mob.getId(), Lightning_Magic_Wand._gfxid));
                mob.broadcastPacketX10(new S_DoActionGFX(mob.getId(), 2));
            }
            else {
                user.sendPacketsXR(new S_EffectLocation(new L1Location(target.getX(), target.getY(), (int)user.getMapId()), Lightning_Magic_Wand._gfxid), 7);
            }
            return;
        }
    }
}
