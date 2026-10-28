package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.L1Location;
import com.lineage.server.world.World;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.ItemExecutor;

public class newskillbook extends ItemExecutor
{
    private static final Log _log;
    private Random randon;
    private int _range;
    private int _count;
    private int _dmgMin;
    private int _dmgMax;
    private int _gfxId;
    private int _itemId;
    private int _itemCount;
    
    static {
        _log = LogFactory.getLog(newskillbook.class);
    }
    
    public newskillbook() {
        this.randon = new Random();
        this._range = 0;
        this._count = 0;
        this._dmgMin = 0;
        this._dmgMax = 0;
        this._gfxId = 0;
        this._itemId = 0;
        this._itemCount = 0;
    }
    
    public static ItemExecutor get() {
        return new newskillbook();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        int spellsc_x = data[1];
        int spellsc_y = data[2];
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        L1Object target = World.get().findObject(spellsc_objid);
        if (target != null || this._count > 1) {
            this.doAction(pc, target);
        }
        else {
            pc.sendPacketsXR(new S_EffectLocation(new L1Location(spellsc_x, spellsc_y, (int)pc.getMapId()), 10), 7);
        }
    }
    
    private boolean check(L1PcInstance user, L1Object target) {
        if (user.getId() == target.getId()) {
            return false;
        }
        else if (!user.glanceCheck(target.getX(), target.getY())) {
            return false;
        }
        else {
            int location = target.getLocation().getTileLineDistance(user.getLocation());
            if (location > this._range) {
                user.sendPackets(new S_SystemMessage("距離為:" + this._range));
                newskillbook._log.info("距離為:" + this._range);
                return false;
            }
            else {
                return true;
            }
        }
    }
    
    private void doAction(L1PcInstance user, L1Object target) {
        Random _random = new Random();
        if (this._count == 1 && !this.check(user, target)) {
            return;
        }
        else {
            int dmg = 0;
            if (this._dmgMax - this._dmgMin <= 0) {
                dmg = this._dmgMin;
            }
            else {
                dmg = _random.nextInt(this._dmgMax - this._dmgMin) + this._dmgMin;
            }
            if (this._itemId != 0 && !user.getInventory().consumeItem(this._itemId, (long)this._itemCount)) {
                user.sendPackets(new S_SystemMessage("施法材料不足"));
                return;
            }
            else {
                if (this._count > 1) {
                    ArrayList<L1Object> list = World.get().getVisibleObjects(user, this._count);
                    if (list != null) {
                        for (L1Object object : list) {
                            if (object instanceof L1PcInstance) {
                                L1PcInstance pc = (L1PcInstance)object;
                                if (pc.getMap().isSafetyZone(pc.getLocation()) || pc.checkNonPvP(user, pc)) {
                                    return;
                                }
                                else if (pc.hasSkillEffect(50) || user.hasSkillEffect(78) || pc.hasSkillEffect(157)) {
                                    return;
                                }
                                else {
                                    if (pc.hasSkillEffect(68)) {
                                        dmg /= 2;
                                    }
                                    int newHp = pc.getCurrentHp() - dmg;
                                    if (newHp <= 0 && !pc.isGm()) {
                                        pc.death(user);
                                    }
                                    pc.setCurrentHp(newHp);
                                    pc.sendPacketsX10(new S_DoActionGFX(pc.getId(), 2));
                                }
                            }
                            else if (object instanceof L1MonsterInstance) {
                                L1MonsterInstance mob = (L1MonsterInstance)object;
                                mob.receiveDamage(user, dmg);
                                mob.broadcastPacketX10(new S_DoActionGFX(mob.getId(), 2));
                            }
                            else {
                                continue;
                            }
                        }
                        user.sendPacketsX10(new S_SkillSound(user.getId(), this._gfxId));
                    }
                }
                else {
                    if (target instanceof L1PcInstance) {
                        L1PcInstance pc2 = (L1PcInstance)target;
                        if (pc2.getMap().isSafetyZone(pc2.getLocation()) || user.checkNonPvP(user, pc2)) {
                            return;
                        }
                        else if (pc2.hasSkillEffect(50) || pc2.hasSkillEffect(78) || pc2.hasSkillEffect(157)) {
                            return;
                        }
                        else {
                            if (pc2.hasSkillEffect(68)) {
                                dmg /= 2;
                            }
                            int newHp2 = pc2.getCurrentHp() - dmg;
                            if (newHp2 <= 0 && !pc2.isGm()) {
                                pc2.death(user);
                            }
                            pc2.setCurrentHp(newHp2);
                        }
                    }
                    else if (target instanceof L1MonsterInstance) {
                        L1MonsterInstance mob2 = (L1MonsterInstance)target;
                        mob2.receiveDamage(user, dmg);
                    }
                    user.setHeading(user.targetDirection(target.getX(), target.getY()));
                    user.sendPacketsX10(new S_ChangeHeading(user));
                    user.sendPacketsX10(new S_DoActionGFX(user.getId(), 17));
                    if (target instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)target;
                        tgpc.sendPacketsX10(new S_SkillSound(tgpc.getId(), this._gfxId));
                        tgpc.sendPacketsX10(new S_DoActionGFX(tgpc.getId(), 2));
                    }
                    else if (target instanceof L1MonsterInstance) {
                        L1MonsterInstance mob2 = (L1MonsterInstance)target;
                        mob2.broadcastPacketX10(new S_SkillSound(mob2.getId(), this._gfxId));
                        mob2.broadcastPacketX10(new S_DoActionGFX(mob2.getId(), 2));
                    }
                    else {
                        user.sendPacketsXR(new S_EffectLocation(new L1Location(target.getX(), target.getY(), (int)user.getMapId()), this._gfxId), 7);
                    }
                }
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._range = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._count = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._dmgMin = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
        try {
            this._dmgMax = Integer.parseInt(set[4]);
        }
        catch (Exception ex4) {}
        try {
            this._gfxId = Integer.parseInt(set[5]);
        }
        catch (Exception ex5) {}
        try {
            this._itemId = Integer.parseInt(set[6]);
        }
        catch (Exception ex6) {}
        try {
            this._itemCount = Integer.parseInt(set[7]);
        }
        catch (Exception ex7) {}
    }
}
