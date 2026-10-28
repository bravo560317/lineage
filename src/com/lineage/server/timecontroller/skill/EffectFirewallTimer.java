package com.lineage.server.timecontroller.skill;

import java.util.ConcurrentModificationException;
import com.lineage.server.model.L1AttackList;
import com.lineage.server.serverpackets.S_DoActionGFX;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1EffectType;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.world.WorldEffect;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class EffectFirewallTimer extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(EffectFirewallTimer.class);
        EffectFirewallTimer._random = new Random();
    }
    
    public void start() {
        int timeMillis = 1650;
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, (long)timeMillis, (long)timeMillis);
    }
    
    @Override
    public void run() {
        try {
            Collection<L1EffectInstance> allNpc = WorldEffect.get().all();
            if (allNpc.isEmpty()) {
                return;
            }
            else {
                for (L1EffectInstance effect : allNpc) {
                    if (effect.effectType() == L1EffectType.isFirewall) {
                        firewall(effect);
                        Thread.sleep(1L);
                    }
                }
            }
        }
        catch (Exception e) {
            EffectFirewallTimer._log.error("Npc L1Effect法師技能(火牢)狀態送出時間軸異常重啟", e);
            GeneralThreadPool.get().cancel(this._timer, false);
            EffectFirewallTimer firewallTimer = new EffectFirewallTimer();
            firewallTimer.start();
        }
    }
    
    private static void firewall(L1EffectInstance effect) {
        try {
            L1PcInstance user = (L1PcInstance)effect.getMaster();
            ArrayList<L1Character> list = WorldEffect.get().getFirewall(effect);
            for (L1Character object : list) {
                if (effect.get_showId() == object.get_showId()) {
                    if (object instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)object;
                        topc(user, tgpc);
                    }
                    else if (object instanceof L1MonsterInstance) {
                        L1MonsterInstance tgmob = (L1MonsterInstance)object;
                        tonpc(user, tgmob);
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        catch (Exception e) {
            EffectFirewallTimer._log.error("Npc L1Effect法師技能(火牢)狀態送出時間軸發生異常", e);
            effect.deleteMe();
        }
    }
    
    private static void tonpc(L1PcInstance user, L1MonsterInstance tgmob) {
        if (dmg0(tgmob)) {
            return;
        }
        else {
            double attrDeffence = 0.0;
            int weakAttr = tgmob.getFire();
            if (weakAttr > 0) {
                attrDeffence = calcAttrResistance(weakAttr);
            }
            int srcDmg = 19 + EffectFirewallTimer._random.nextInt(Math.max(user.getInt() / 2, 1));
            int damage = (int)((1.0 - attrDeffence) * (double)srcDmg);
            damage = Math.max(damage, 0);
            if (damage <= 0) {
                return;
            }
            else {
                tgmob.broadcastPacketX10(new S_DoActionGFX(tgmob.getId(), 2));
                tgmob.receiveDamage(user, damage);
                return;
            }
        }
    }
    
    private static void topc(L1PcInstance user, L1PcInstance tgpc) {
        if (user.getClanid() != 0 && tgpc.getClanid() == user.getClanid()) {
            return;
        }
        else if (tgpc.isSafetyZone()) {
            return;
        }
        else if (dmg0(tgpc)) {
            return;
        }
        else {
            double attrDeffence = 0.0;
            int weakAttr = tgpc.getFire();
            if (weakAttr > 0) {
                attrDeffence = calcAttrResistance(weakAttr);
            }
            int srcDmg = 19 + EffectFirewallTimer._random.nextInt(Math.max(user.getInt() / 2, 1));
            int damage = (int)((1.0 - attrDeffence) * (double)srcDmg);
            damage = Math.max(damage, 0);
            boolean dmgX2 = false;
            if (!tgpc.getSkillisEmpty() && tgpc.getSkillEffect().size() > 0) {
                try {
                    for (Integer skillid : L1AttackList.SKD3.keySet()) {
                        if (tgpc.hasSkillEffect(skillid)) {
                            Integer integer = L1AttackList.SKD3.get(skillid);
                            if (integer != null) {
                                if (integer.equals(skillid)) {
                                    dmgX2 = true;
                                }
                                else {
                                    damage += integer;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                catch (ConcurrentModificationException ex) {}
                catch (Exception e) {
                    EffectFirewallTimer._log.error(e.getLocalizedMessage(), e);
                }
            }
            if (dmgX2) {
                damage /= 2;
            }
            if (damage <= 0) {
                return;
            }
            else {
                tgpc.sendPacketsAll(new S_DoActionGFX(tgpc.getId(), 2));
                tgpc.receiveDamage(user, (double)damage, false, true);
                return;
            }
        }
    }
    
    private static double calcAttrResistance(int resist) {
        int resistFloor = (int)(0.16 * (double)Math.abs(resist));
        if (resist >= 0) {
            resistFloor *= 1;
        }
        else {
            resistFloor *= -1;
        }
        double attrDeffence = (double)resistFloor / 32.0;
        return attrDeffence;
    }
    
    private static boolean dmg0(L1Character character) {
        try {
            if (character == null) {
                return false;
            }
            else if (character.getSkillisEmpty()) {
                return false;
            }
            else if (character.getSkillEffect().size() <= 0) {
                return false;
            }
            else {
                for (Integer key : character.getSkillEffect()) {
                    Integer integer = L1AttackList.SKM0.get(key);
                    if (integer != null) {
                        return true;
                    }
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
        catch (Exception e) {
            EffectFirewallTimer._log.error(e.getLocalizedMessage(), e);
            return false;
        }
        return false;
    }
}
