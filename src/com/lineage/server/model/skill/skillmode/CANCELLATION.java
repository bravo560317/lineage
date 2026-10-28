package com.lineage.server.model.skill.skillmode;

import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.model.L1PinkName;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.william.L1WilliamGfxIdOrginal;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.datatables.Skill_type;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

public class CANCELLATION extends SkillMode
{
    @Override
    public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        if (cha instanceof L1NpcInstance) {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            int npcId = tgnpc.getNpcTemplate().get_npcId();
            switch (npcId) {
                case 71092: {
                    if (tgnpc.getGfxId() == tgnpc.getTempCharGfx()) {
                        tgnpc.setTempCharGfx(1314);
                        tgnpc.broadcastPacketAll(new S_ChangeShape(tgnpc, 1314));
                        return 0;
                    }
                    else {
                        break;
                    }
                }
                case 80013: {
                    if (tgnpc.getGfxId() == tgnpc.getTempCharGfx()) {
                        tgnpc.setTempCharGfx(5217);
                        tgnpc.setPassispeed(480);
                        tgnpc.broadcastPacketAll(new S_ChangeShape(tgnpc, 5217));
                        tgnpc.setNameId("$6068");
                        tgnpc.broadcastPacketAll(new S_ChangeName(tgnpc.getId(), "$6068"));
                        return 0;
                    }
                    else {
                        break;
                    }
                }
                case 81209: {
                    if (tgnpc.getGfxId() == tgnpc.getTempCharGfx()) {
                        tgnpc.setTempCharGfx(4310);
                        tgnpc.broadcastPacketAll(new S_ChangeShape(tgnpc, 4310));
                        return 0;
                    }
                    else {
                        break;
                    }
                }
                case 91123: {
                    if (tgnpc.getGfxId() == tgnpc.getTempCharGfx()) {
                        int x = tgnpc.getX();
                        int y = tgnpc.getY();
                        short m = tgnpc.getMapId();
                        int h = tgnpc.getHeading();
                        tgnpc.deleteMe();
                        L1SpawnUtil.spawnT(45641, x, y, m, h, 300);
                        return 0;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        if (srcpc != null && srcpc.isInvisble()) {
            srcpc.delInvis();
        }
        for (int skillNum = 1; skillNum <= 220; ++skillNum) {
            if (!Skill_type.RESTRICTIONS.contains((Integer)skillNum) || cha.isDead()) {
                if (cha.hasSkillEffect(42) && cha instanceof L1PcInstance) {
                    L1PcInstance tgpc = (L1PcInstance)cha;
                    tgpc.sendPackets(new S_Strup(tgpc, -5, 1));
                    tgpc.removeSkillEffect(42);
                }
                if (cha.hasSkillEffect(26) && cha instanceof L1PcInstance) {
                    L1PcInstance tgpc = (L1PcInstance)cha;
                    tgpc.sendPackets(new S_Dexup(tgpc, -5, 1));
                    tgpc.removeSkillEffect(26);
                }
                if (!L1WilliamGfxIdOrginal.Cancellation(cha.getTempCharGfx()) || skillNum != 67) {
                    cha.removeSkillEffect(skillNum);
                }
            }
        }
        cha.curePoison();
        cha.cureParalaysis();
        for (int skillNum = 998; skillNum <= 1027; ++skillNum) {
            if (!Skill_type.RESTRICTIONS.contains((Integer)skillNum) || cha.isDead()) {
                cha.removeSkillEffect(skillNum);
            }
        }
        cha.removeSkillEffect(4000);
        if (cha instanceof L1PcInstance) {
            L1PcInstance tgpc2 = (L1PcInstance)cha;
            if (!L1WilliamGfxIdOrginal.Cancellation(tgpc2.getTempCharGfx())) {
                L1PolyMorph.undoPoly_new(tgpc2);
            }
            if (tgpc2.getHasteItemEquipped() > 0) {
                tgpc2.setMoveSpeed(0);
                tgpc2.sendPacketsAll(new S_SkillHaste(tgpc2.getId(), 0, 0));
            }
            tgpc2.sendPacketsAll(new S_CharVisualUpdate(tgpc2));
            if (tgpc2.isPrivateShop()) {
                tgpc2.sendPacketsAll(new S_DoActionShop(tgpc2.getId(), tgpc2.getShopChat()));
            }
            L1PinkName.onAction(tgpc2, srcpc);
        }
        else {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            tgnpc.setMoveSpeed(0);
            tgnpc.setBraveSpeed(0);
            tgnpc.broadcastPacketAll(new S_SkillHaste(cha.getId(), 0, 0));
            tgnpc.broadcastPacketAll(new S_SkillBrave(cha.getId(), 0, 0));
            tgnpc.setWeaponBreaked(false);
            tgnpc.setParalyzed(false);
        }
        return 0;
    }
    
    @Override
    public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
        int dmg = 0;
        for (int skillNum = 1; skillNum <= 220; ++skillNum) {
            if (!Skill_type.RESTRICTIONS.contains((Integer)skillNum) || cha.isDead()) {
                cha.removeSkillEffect(skillNum);
            }
        }
        cha.curePoison();
        cha.cureParalaysis();
        for (int skillNum = 998; skillNum <= 1027; ++skillNum) {
            if (!Skill_type.RESTRICTIONS.contains((Integer)skillNum) || cha.isDead()) {
                cha.removeSkillEffect(skillNum);
            }
        }
        cha.removeSkillEffect(4000);
        if (cha instanceof L1PcInstance) {
            L1PcInstance tgpc = (L1PcInstance)cha;
            if (tgpc.getHasteItemEquipped() > 0) {
                tgpc.setMoveSpeed(0);
                tgpc.sendPacketsAll(new S_SkillHaste(tgpc.getId(), 0, 0));
            }
            tgpc.sendPacketsAll(new S_CharVisualUpdate(tgpc));
            if (tgpc.isPrivateShop()) {
                tgpc.sendPacketsAll(new S_DoActionShop(tgpc.getId(), tgpc.getShopChat()));
            }
        }
        else {
            L1NpcInstance tgnpc = (L1NpcInstance)cha;
            tgnpc.setMoveSpeed(0);
            tgnpc.setBraveSpeed(0);
            tgnpc.broadcastPacketAll(new S_SkillHaste(cha.getId(), 0, 0));
            tgnpc.broadcastPacketAll(new S_SkillBrave(cha.getId(), 0, 0));
            tgnpc.setWeaponBreaked(false);
            tgnpc.setParalyzed(false);
        }
        return 0;
    }
    
    @Override
    public void start(L1PcInstance srcpc, Object obj) throws Exception {
    }
    
    @Override
    public void stop(L1Character cha) throws Exception {
    }
}
