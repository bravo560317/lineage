package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.add.PeepCard;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1WeaponSkill;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class MagicReel_SpecialMagice extends ItemExecutor
{
    private static final Random _random;
    private int _type;
    
    static {
        _random = new Random();
    }
    
    private MagicReel_SpecialMagice() {
    }
    
    public static ItemExecutor get() {
        return new MagicReel_SpecialMagice();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null) {
            return;
        }
        else if (item == null) {
            return;
        }
        else if (pc.isInvisble() || pc.isInvisDelay()) {
            pc.sendPackets(new S_ServerMessage(281));
            return;
        }
        else {
            L1BuffUtil.cancelAbsoluteBarrier(pc);
            switch (this._type) {
                case 1: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 4; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 6509));
                        break;
                    }
                }
                case 2: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 5; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 6509));
                        break;
                    }
                }
                case 3: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 6; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 6509));
                        break;
                    }
                }
                case 4: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 7; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 6509));
                        break;
                    }
                }
                case 5: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 8; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 6509));
                        break;
                    }
                }
                case 6: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 4; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 4394));
                        break;
                    }
                }
                case 7: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 5; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 4394));
                        break;
                    }
                }
                case 8: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 6; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 4394));
                        break;
                    }
                }
                case 9: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 7; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 4394));
                        break;
                    }
                }
                case 10: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        for (int i = 0; i < 8; ++i) {
                            cha.onAction(pc);
                        }
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 4394));
                        break;
                    }
                }
                case 11: {
                    if (pc.getInventory().checkItem(40308, 5000000L)) {
                        pc.sendPackets(new S_SkillSound(pc.getId(), 2244));
                        pc.broadcastPacketAll(new S_SkillSound(pc.getId(), 2244));
                        pc.setCurrentHp(pc.getCurrentHp() + 300);
                        pc.getInventory().consumeItem(40308, 5000000L);
                        pc.sendPackets(new S_SystemMessage("\\fY生命力恢復了300點，並收取了5000000現金"));
                        break;
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("\\fY金幣不足500W"));
                        break;
                    }
                }
                case 12: {
                    if (pc.getInventory().checkItem(40308, 5000000L)) {
                        pc.sendPackets(new S_SkillSound(pc.getId(), 2244));
                        pc.broadcastPacketAll(new S_SkillSound(pc.getId(), 2244));
                        pc.setCurrentMp(pc.getCurrentMp() + 150);
                        pc.getInventory().consumeItem(40308, 5000000L);
                        pc.sendPackets(new S_SystemMessage("\\fY魔力恢復了150點，並收取了5000000現金"));
                        break;
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("\\fY金幣不足500W"));
                        break;
                    }
                }
                case 13: {
                    pc.setSkillEffect(8895, 15000);
                    pc.sendPackets(new S_SystemMessage("\\fY環繞著氣息擊退敵人能力持續15秒"));
                    break;
                }
                case 14: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else if (MagicReel_SpecialMagice._random.nextInt(100) < 20) {
                        if (!L1WeaponSkill.isFreeze(cha)) {
                            cha.setSkillEffect(4000, 3000);
                            cha.broadcastPacketX8(new S_SkillSound(cha.getId(), 4184));
                            if (cha instanceof L1PcInstance) {
                                L1PcInstance targetPc = (L1PcInstance)cha;
                                targetPc.sendPackets(new S_SkillSound(cha.getId(), 4184));
                                targetPc.sendPackets(new S_Paralysis(6, true));
                                targetPc.setSkillEffect(4017, 3000);
                                break;
                            }
                            else if (cha instanceof L1NpcInstance) {
                                L1NpcInstance targetNpc = (L1NpcInstance)cha;
                                if (!targetNpc.getNpcTemplate().is_boss()) {
                                    targetNpc.setParalyzed(true);
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(280));
                        return;
                    }
                }
                case 15: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else if (MagicReel_SpecialMagice._random.nextInt(100) < 20) {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance targetPc = (L1PcInstance)cha;
                            PeepCard.TeleportPc(pc, targetPc);
                            break;
                        }
                        else if (cha instanceof L1NpcInstance) {
                            L1NpcInstance targetNpc = (L1NpcInstance)cha;
                            PeepCard.TeleportPc(pc, targetNpc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(280));
                        break;
                    }
                }
                case 16: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else if (MagicReel_SpecialMagice._random.nextInt(100) < 30) {
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance targetPc = (L1PcInstance)cha;
                            PeepCard.TakePc(pc, targetPc);
                            break;
                        }
                        else if (cha instanceof L1NpcInstance) {
                            L1NpcInstance targetNpc = (L1NpcInstance)cha;
                            PeepCard.TakePc(pc, targetNpc);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("\\fY運氣不好 沒偷到!!"));
                        break;
                    }
                }
                case 17: {
                    if (pc.hasSkillEffect(8863)) {
                        pc.sendPackets(new S_SystemMessage("\\fY目前逆流時間還持續著"));
                        return;
                    }
                    else {
                        pc.setSkillEffect(8863, 1800000);
                        pc.sendPackets(new S_SystemMessage("\\fY受到逆流的攻擊.強力暴發"));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 5010));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 5010));
                        break;
                    }
                }
                case 18: {
                    pc.setSkillEffect(8864, 60000);
                    pc.sendPackets(new S_SystemMessage("\\fY武器附加隱毒效果持續60秒"));
                    break;
                }
                case 19: {
                    if (pc.hasSkillEffect(8865)) {
                        pc.sendPackets(new S_SystemMessage("\\fY該技能時間尚未消失"));
                        return;
                    }
                    else {
                        pc.setSkillEffect(8865, 1800000);
                        pc.sendPackets(new S_SystemMessage("\\fY體能似乎湧出來了"));
                        pc.addMaxHp(500);
                        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 4848));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 4848));
                        break;
                    }
                }
                case 20: {
                    if (pc.hasSkillEffect(8866)) {
                        pc.sendPackets(new S_SystemMessage("\\fY該技能時間尚未消失"));
                        return;
                    }
                    else {
                        pc.setSkillEffect(8866, 900000);
                        pc.sendPackets(new S_SystemMessage("\\fY魔法增壓似乎讓我魔法增傷不少"));
                        pc.addSp(2);
                        pc.sendPackets(new S_SPMR(pc));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 4601));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 4601));
                        break;
                    }
                }
                case 21: {
                    pc.setSkillEffect(8867, 4000);
                    pc.sendPackets(new S_SystemMessage("\\fY強烈的防護罩保護著你"));
                    pc.sendPackets(new S_SkillSound(pc.getId(), 5010));
                    pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 5010));
                    break;
                }
                case 22: {
                    if (pc.hasSkillEffect(8868)) {
                        pc.sendPackets(new S_SystemMessage("\\fY該技能祝福時間尚未消失"));
                        return;
                    }
                    else {
                        pc.setSkillEffect(8868, 900000);
                        pc.sendPackets(new S_SystemMessage("\\fY敏捷祝福 環繞全身"));
                        pc.addDex(4);
                        pc.sendPackets(new S_OwnCharStatus2(pc));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 4899));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 4899));
                        break;
                    }
                }
                case 23: {
                    if (pc.hasSkillEffect(8869)) {
                        pc.sendPackets(new S_SystemMessage("\\fY該技能祝福時間尚未消失"));
                        return;
                    }
                    else {
                        pc.setSkillEffect(8869, 900000);
                        pc.sendPackets(new S_SystemMessage("\\fY力量祝福 環繞全身"));
                        pc.addStr(4);
                        pc.sendPackets(new S_OwnCharStatus2(pc));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 4895));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 4895));
                        break;
                    }
                }
                case 24: {
                    if (pc.hasSkillEffect(8870)) {
                        pc.sendPackets(new S_SystemMessage("\\fY該技能祝福時間尚未消失"));
                        return;
                    }
                    else {
                        pc.setSkillEffect(8870, 900000);
                        pc.sendPackets(new S_SystemMessage("\\fY智力祝福 環繞全身"));
                        pc.addInt(4);
                        pc.sendPackets(new S_OwnCharStatus2(pc));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 4891));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 4891));
                        break;
                    }
                }
                case 25: {
                    pc.setSkillEffect(8871, 5000);
                    pc.sendPackets(new S_SystemMessage("\\fY魯夫的霸王氣.環繞全身"));
                    break;
                }
                case 26: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else if (cha instanceof L1PcInstance) {
                        L1PcInstance targetPc = (L1PcInstance)cha;
                        targetPc.setSkillEffect(8872, 5000);
                        targetPc.sendPackets(new S_SystemMessage("\\fY敵人受到白息之光的詛咒"));
                        targetPc.sendPackets(new S_SystemMessage("\\fY受到白息之光的逞罰無法使用物品"));
                        pc.sendPackets(new S_SkillSound(pc.getId(), 227));
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 227));
                        break;
                    }
                    else {
                        break;
                    }
                }
                case 27: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    if (cha == null) {
                        return;
                    }
                    else {
                        int range = 5;
                        int dmg = 150 + MagicReel_SpecialMagice._random.nextInt(200) + 1;
                        ArrayList<L1Object> array_list = World.get().getVisibleObjects(cha, 5);
                        array_list.add(cha);
                        if (cha instanceof L1PcInstance) {
                            for (L1Object tgobj : array_list) {
                                if (tgobj instanceof L1PcInstance) {
                                    L1PcInstance tgpc = (L1PcInstance)tgobj;
                                    if (tgpc.isDead()) {
                                        continue;
                                    }
                                    else if (tgpc.getClanid() == pc.getClanid() && tgpc.getClanid() != 0) {
                                        continue;
                                    }
                                    else if (tgpc.getMap().isSafetyZone(tgpc.getLocation())) {
                                        continue;
                                    }
                                    else {
                                        tgpc.receiveDamage(pc, (double)dmg, false, false);
                                        tgpc.broadcastPacketAll(new S_SkillSound(tgpc.getId(), 5653));
                                    }
                                }
                            }
                            break;
                        }
                        else if (cha instanceof L1NpcInstance) {
                            for (L1Object tgobj : array_list) {
                                if (tgobj instanceof L1MonsterInstance) {
                                    L1MonsterInstance tgmob = (L1MonsterInstance)tgobj;
                                    if (tgmob.isDead()) {
                                        continue;
                                    }
                                    else {
                                        tgmob.receiveDamage(pc, dmg);
                                        tgmob.broadcastPacketAll(new S_SkillSound(tgmob.getId(), 5653));
                                    }
                                }
                            }
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
                case 28: {
                    pc.setSkillEffect(8873, 5000);
                    pc.sendPackets(new S_SystemMessage("\\fY重擊暈眩的能量附加在武器上了"));
                    break;
                }
                case 30: {
                    L1Character cha = this.getTarget(pc, data[0]);
                    int dmg2 = 200 + MagicReel_SpecialMagice._random.nextInt(400) + 1;
                    if (cha == null) {
                        return;
                    }
                    else if (cha instanceof L1PcInstance) {
                        L1PcInstance targetPc2 = (L1PcInstance)cha;
                        pc.setCurrentHp((int)(short)(pc.getCurrentHp() / 5));
                        targetPc2.receiveDamage(pc, (double)dmg2, false, false);
                        pc.sendPackets(new S_SystemMessage("\\fY自殺性 犧牲給予強大傷害"));
                        targetPc2.sendPackets(new S_SkillSound(targetPc2.getId(), 4594));
                        targetPc2.broadcastPacketX10(new S_SkillSound(targetPc2.getId(), 4594));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            return;
        }
    }
    
    private final L1Character getTarget(L1PcInstance pc, int targetID) {
        if (targetID == pc.getId() || targetID == 0) {
            pc.sendPackets(new S_ServerMessage(281));
            return null;
        }
        else {
            L1Object target = World.get().findObject(targetID);
            if (target == null || !(target instanceof L1Character)) {
                pc.sendPackets(new S_ServerMessage(281));
                return null;
            }
            else {
                return (L1Character)target;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._type = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
