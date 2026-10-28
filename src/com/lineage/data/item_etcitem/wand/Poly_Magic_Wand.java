package com.lineage.data.item_etcitem.wand;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_ShowPolyList;
import java.util.Random;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.L1Character;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Poly_Magic_Wand extends ItemExecutor
{
    private Poly_Magic_Wand() {
    }
    
    public static ItemExecutor get() {
        return new Poly_Magic_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int itemId = item.getItemId();
        int spellsc_objid = data[0];
        if (pc.getMap().isSafetyZone(pc.getLocation())) {
            return;
        }
        else {
            if (pc.getMapId() == 63 || pc.getMapId() == 552 || pc.getMapId() == 555 || pc.getMapId() == 557 || pc.getMapId() == 558 || pc.getMapId() == 779 || pc.getMapId() == 340 || pc.getMapId() == 350) {
                pc.sendPackets(new S_ServerMessage(563));
            }
            else {
                pc.sendPacketsX8(new S_DoActionGFX(pc.getId(), 17));
                int chargeCount = item.getChargeCount();
                if ((chargeCount <= 0 && itemId != 40410) || pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    L1Object target = World.get().findObject(spellsc_objid);
                    if (target != null) {
                        L1Character cha = (L1Character)target;
                        this.polyAction(pc, cha);
                        L1BuffUtil.cancelAbsoluteBarrier(pc);
                        if (itemId == 40008 || itemId == 140008) {
                            item.setChargeCount(item.getChargeCount() - 1);
                            pc.getInventory().updateItem(item, 128);
                        }
                        else {
                            pc.getInventory().removeItem(item, 1L);
                        }
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                    }
                }
            }
            return;
        }
    }
    
    private void polyAction(L1PcInstance attacker, L1Character cha) {
        boolean isSameClan = false;
        Random _random = new Random();
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc = (L1PcInstance)cha;
            if (pc.getClanid() != 0 && attacker.getClanid() == pc.getClanid()) {
                isSameClan = true;
            }
        }
        if (attacker.getId() != cha.getId() && !isSameClan) {
            int probability = 3 * (attacker.getLevel() - cha.getLevel()) + 100 - cha.getMr();
            int rnd = _random.nextInt(100) + 1;
            if (rnd > probability) {
                return;
            }
        }
        int[] polyArray = { 29, 945, 947, 979, 1037, 1039, 3860, 3861, 3862, 3863, 3864, 3865, 3904, 3906, 95, 146, 2374, 2376, 2377, 2378, 3866, 3867, 3868, 3869, 3870, 3871, 3872, 3873, 3874, 3875, 3876 };
        int pid = _random.nextInt(polyArray.length);
        int polyId = polyArray[pid];
        if (cha instanceof L1PcInstance) {
            L1PcInstance pc2 = (L1PcInstance)cha;
            if (pc2.getInventory().checkEquipped(20281) || pc2.getInventory().checkEquipped(600280) || pc2.getInventory().checkEquipped(28006) || pc2.getInventory().checkEquipped(571279) || pc2.getInventory().checkEquipped(280235)) {
                pc2.sendPackets(new S_ShowPolyList(pc2.getId()));
                if (!pc2.isShapeChange()) {
                    pc2.setSummonMonster(false);
                    pc2.setShapeChange(true);
                }
                pc2.sendPackets(new S_ServerMessage(966));
            }
            else {
                L1Skills skillTemp = SkillsTable.get().getTemplate(67);
                if (attacker.getId() == pc2.getId()) {
                    L1PolyMorph.doPoly(pc2, polyId, skillTemp.getBuffDuration(), 1);
                }
                else {
                    int probability2 = 3 * (attacker.getLevel() - cha.getLevel()) + 100 - cha.getMr();
                    if (RandomArrayList.getInc(100, 1) < probability2) {
                        this.polyAction(pc2, cha);
                    }
                    else {
                        pc2.sendPackets(new S_ServerMessage(79));
                    }
                }
            }
        }
        else if (cha instanceof L1MonsterInstance) {
            L1MonsterInstance mob = (L1MonsterInstance)cha;
            if (mob.getLevel() < 50) {
                int npcId = mob.getNpcTemplate().get_npcId();
                if (npcId != 45338 && npcId != 45370 && npcId != 45456 && npcId != 45464 && npcId != 45473 && npcId != 45488 && npcId != 45497 && npcId != 45516 && npcId != 45529 && npcId != 45458) {
                    L1Skills skillTemp2 = SkillsTable.get().getTemplate(67);
                    L1PolyMorph.doPoly(mob, polyId, skillTemp2.getBuffDuration(), 1);
                }
            }
        }
    }
}
