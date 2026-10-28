package com.lineage.server.model.skill;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1BuffUtil
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1BuffUtil.class);
    }
    
    public static boolean stopPotion(L1PcInstance pc) {
        if (pc.is_decay_potion()) {
            pc.sendPackets(new S_ServerMessage(698));
            return false;
        }
        else {
            return true;
        }
    }
    
    public static void cancelAbsoluteBarrier(L1PcInstance pc) {
        if (pc.hasSkillEffect(78)) {
            pc.killSkillEffectTimer(78);
            pc.startHpRegeneration();
            pc.startMpRegeneration();
        }
    }
    
    public static int cancelBaphomet(L1PcInstance pc) {
        if (pc.hasSkillEffect(40001)) {
            return pc.getSkillEffectTimeSec(40001);
        }
        else {
            return -1;
        }
    }
    
    public static void hasteStart(L1PcInstance pc) {
        try {
            if (pc.hasSkillEffect(43)) {
                pc.killSkillEffectTimer(43);
                pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                pc.setMoveSpeed(0);
            }
            if (pc.hasSkillEffect(54)) {
                pc.killSkillEffectTimer(54);
                pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                pc.setMoveSpeed(0);
            }
            if (pc.hasSkillEffect(1001)) {
                pc.killSkillEffectTimer(1001);
                pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
                pc.setMoveSpeed(0);
            }
        }
        catch (Exception e) {
            L1BuffUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void haste(L1PcInstance pc, int timeMillis) {
        try {
            hasteStart(pc);
            pc.setSkillEffect(1001, timeMillis);
            int objId = pc.getId();
            pc.sendPackets(new S_SkillHaste(objId, 1, timeMillis / 1000));
            pc.broadcastPacketAll(new S_SkillHaste(objId, 1, 0));
            pc.sendPacketsX8(new S_SkillSound(objId, 191));
            pc.setMoveSpeed(1);
        }
        catch (Exception e) {
            L1BuffUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void braveStart(L1PcInstance pc) {
        try {
            if (pc.hasSkillEffect(52)) {
                pc.killSkillEffectTimer(52);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
            if (pc.hasSkillEffect(101)) {
                pc.killSkillEffectTimer(101);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
            if (pc.hasSkillEffect(150)) {
                pc.killSkillEffectTimer(150);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
            if (pc.hasSkillEffect(1000)) {
                pc.killSkillEffectTimer(1000);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
            if (pc.hasSkillEffect(1016)) {
                pc.killSkillEffectTimer(1016);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
            if (pc.hasSkillEffect(1017)) {
                pc.killSkillEffectTimer(1017);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
            if (pc.hasSkillEffect(186)) {
                pc.killSkillEffectTimer(186);
                pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
                pc.setBraveSpeed(0);
            }
        }
        catch (Exception e) {
            L1BuffUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void brave(L1PcInstance pc, int timeMillis) {
        try {
            braveStart(pc);
            pc.setSkillEffect(1000, timeMillis);
            int objId = pc.getId();
            pc.sendPackets(new S_SkillBrave(objId, 1, timeMillis / 1000));
            pc.broadcastPacketAll(new S_SkillBrave(objId, 1, 0));
            pc.sendPacketsX8(new S_SkillSound(objId, 751));
            pc.setBraveSpeed(1);
        }
        catch (Exception e) {
            L1BuffUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void doPoly(L1PcInstance pc) {
        try {
            int skillId = pc.getAwakeSkillId();
            int polyId = 0;
            switch (skillId) {
                case 185: {
                    polyId = 9362;
                    break;
                }
                case 190: {
                    polyId = 9364;
                    break;
                }
                case 195: {
                    polyId = 9363;
                    break;
                }
            }
            if (pc.hasSkillEffect(67)) {
                pc.killSkillEffectTimer(67);
            }
            pc.setTempCharGfx(polyId);
            pc.sendPacketsAll(new S_ChangeShape(pc, polyId));
            L1ItemInstance weapon = pc.getWeapon();
            if (weapon != null) {
                pc.sendPacketsAll(new S_CharVisualUpdate(pc));
            }
        }
        catch (Exception e) {
            L1BuffUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void undoPoly(L1PcInstance pc) {
        try {
            int classId = pc.getClassId();
            pc.setTempCharGfx(classId);
            pc.sendPacketsAll(new S_ChangeShape(pc, classId));
            L1ItemInstance weapon = pc.getWeapon();
            if (weapon != null) {
                pc.sendPacketsAll(new S_CharVisualUpdate(pc));
            }
        }
        catch (Exception e) {
            L1BuffUtil._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static boolean cancelExpSkill(L1PcInstance pc) {
        if (pc.hasSkillEffect(6666)) {
            int time = pc.getSkillEffectTimeSec(6666);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水130% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6667)) {
            int time = pc.getSkillEffectTimeSec(6667);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水150% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6668)) {
            int time = pc.getSkillEffectTimeSec(6668);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水170% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6669)) {
            int time = pc.getSkillEffectTimeSec(6669);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水200% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6670)) {
            int time = pc.getSkillEffectTimeSec(6670);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水250% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6671)) {
            int time = pc.getSkillEffectTimeSec(6671);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水300% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6672)) {
            int time = pc.getSkillEffectTimeSec(6672);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水350% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6673)) {
            int time = pc.getSkillEffectTimeSec(6673);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水400% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6674)) {
            int time = pc.getSkillEffectTimeSec(6674);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水450% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6675)) {
            int time = pc.getSkillEffectTimeSec(6675);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水500% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6676)) {
            int time = pc.getSkillEffectTimeSec(6676);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水550% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6677)) {
            int time = pc.getSkillEffectTimeSec(6677);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水600% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6678)) {
            int time = pc.getSkillEffectTimeSec(6678);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水650% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6679)) {
            int time = pc.getSkillEffectTimeSec(6679);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水700% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6680)) {
            int time = pc.getSkillEffectTimeSec(6680);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水750% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(6681)) {
            int time = pc.getSkillEffectTimeSec(6681);
            pc.sendPackets(new S_ServerMessage("\\fX第一段經驗藥水800% 剩餘時間(秒):" + time));
            return false;
        }
        else {
            return true;
        }
    }
    
    public static boolean cancelExpSkill_2(L1PcInstance pc) {
        if (pc.hasSkillEffect(5000)) {
            int time = pc.getSkillEffectTimeSec(5000);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水150% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5001)) {
            int time = pc.getSkillEffectTimeSec(5001);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水175% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5002)) {
            int time = pc.getSkillEffectTimeSec(5002);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水200% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5003)) {
            int time = pc.getSkillEffectTimeSec(5003);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水225% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5004)) {
            int time = pc.getSkillEffectTimeSec(5004);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水250% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5005)) {
            int time = pc.getSkillEffectTimeSec(5005);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水275% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5006)) {
            int time = pc.getSkillEffectTimeSec(5006);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水300% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5007)) {
            int time = pc.getSkillEffectTimeSec(5007);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水325% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5008)) {
            int time = pc.getSkillEffectTimeSec(5008);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水350% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5009)) {
            int time = pc.getSkillEffectTimeSec(5009);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水375% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5010)) {
            int time = pc.getSkillEffectTimeSec(5010);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水400% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5011)) {
            int time = pc.getSkillEffectTimeSec(5011);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水450% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5012)) {
            int time = pc.getSkillEffectTimeSec(5012);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水500% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5013)) {
            int time = pc.getSkillEffectTimeSec(5013);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水550% 剩餘時間(秒):" + time));
            return false;
        }
        else if (pc.hasSkillEffect(5014)) {
            int time = pc.getSkillEffectTimeSec(5014);
            pc.sendPackets(new S_ServerMessage("\\fY第二段神力藥水600% 剩餘時間(秒):" + time));
            return false;
        }
        else {
            return true;
        }
    }
    
    public static int cancelDragon(L1PcInstance pc) {
        if (pc.hasSkillEffect(6683)) {
            return pc.getSkillEffectTimeSec(6683);
        }
        else if (pc.hasSkillEffect(6684)) {
            return pc.getSkillEffectTimeSec(6684);
        }
        else if (pc.hasSkillEffect(6685)) {
            return pc.getSkillEffectTimeSec(6685);
        }
        else if (pc.hasSkillEffect(6686)) {
            return pc.getSkillEffectTimeSec(6686);
        }
        else if (pc.hasSkillEffect(6687)) {
            return pc.getSkillEffectTimeSec(6687);
        }
        else if (pc.hasSkillEffect(6688)) {
            return pc.getSkillEffectTimeSec(6688);
        }
        else if (pc.hasSkillEffect(6689)) {
            return pc.getSkillEffectTimeSec(6689);
        }
        else {
            return -1;
        }
    }
    
    public static void cancelBuffStone(L1PcInstance pc) {
        int[] skillids = { 4401, 4402, 4403, 4404, 4405, 4406, 4407, 4408, 4409, 4411, 4412, 4413, 4414, 4415, 4416, 4417, 4418, 4419, 4421, 4422, 4423, 4424, 4425, 4426, 4427, 4428, 4429, 4431, 4432, 4433, 4434, 4435, 4436, 4437, 4438, 4439 };
        for (int i = 0; i < skillids.length; ++i) {
            if (pc.hasSkillEffect(skillids[i])) {
                pc.killSkillEffectTimer(skillids[i]);
            }
        }
    }
    
    public static int cancelDragonSign1(L1PcInstance pc) {
        int[] skillids = { 4401, 4402, 4403, 4404, 4405, 4406, 4407, 4408, 4409, 4411, 4412, 4413, 4414, 4415, 4416, 4417, 4418, 4419, 4421, 4422, 4423, 4424, 4425, 4426, 4427, 4428, 4429, 4431, 4432, 4433, 4434, 4435, 4436, 4437, 4438, 4439 };
        int i = 0;
        while (i < skillids.length) {
            if (pc.hasSkillEffect(skillids[i])) {
                return pc.getSkillEffectTimeSec(skillids[i]);
            }
            else {
                ++i;
            }
        }
        return -1;
    }
    
    public static int cancelDragonSign(L1PcInstance pc) {
        int[] skillids = { 4500, 4501, 4502, 4503, 4504, 4505, 4506, 4507, 4508, 4509, 4510, 4511, 4512, 4513, 4514, 4515, 4516, 4517, 4518, 4519, 4520, 4521, 4522, 4523, 4524, 4525, 4526, 4527, 4528, 4529, 4530, 4531, 4532, 4533, 4534, 4535, 4536, 4537, 4538, 4539 };
        int i = 0;
        while (i < skillids.length) {
            if (pc.hasSkillEffect(skillids[i])) {
                return pc.getSkillEffectTimeSec(skillids[i]);
            }
            else {
                ++i;
            }
        }
        return -1;
    }
    
    public static boolean getUseItemTeleport(L1PcInstance pc) {
        return !pc.hasSkillEffect(157) && !pc.hasSkillEffect(189);
    }
}
