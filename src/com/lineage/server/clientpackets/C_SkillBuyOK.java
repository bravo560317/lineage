package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Skills;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.serverpackets.S_SkillBuyCN;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.list.PcLvSkillList;
import com.add.AutoAttackUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_SkillBuyOK extends ClientBasePacket
{
    private static final Log _log;
    private static final int[] PRICE;
    
    static {
        _log = LogFactory.getLog(C_SkillBuyOK.class);
        PRICE = new int[] { 1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 0, 289, 324, 361, 400, 441, 484, 529, 576, 625, 676, 729, 784 };
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isPrivateShop()) {
                            int count = this.readH();
                            ArrayList<Integer> skillList = null;
                            if (pc.getAu_AutoSkill(0) > 0) {
                                if (count == 1) {
                                    int skillIds = this.readD() + 1;
                                    pc.setAu_AutoSkill(4, skillIds);
                                    pc.get_other1().set_type22(skillIds);
                                    pc.sendPackets(new S_ServerMessage("\\fU成功設定攻擊技能。"));
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage("\\fU只能有一種技能。"));
                                }
                                pc.setAu_AutoSkill(0, 0);
                                AutoAttackUpdate.getInstance().Au_MagicSet(pc);
                                return;
                            }
                            else if (pc.getAu_AutoSkill(1) > 0) {
                                if (count == 1) {
                                    int skillIds = this.readD() + 1;
                                    pc.setAu_AutoSkill(6, skillIds);
                                    pc.get_other1().set_type24(skillIds);
                                    pc.sendPackets(new S_ServerMessage("\\fU成功設定攻擊技能。"));
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage("\\fU只能有一種技能。"));
                                }
                                pc.setAu_AutoSkill(1, 0);
                                AutoAttackUpdate.getInstance().Au_MagicSet(pc);
                                return;
                            }
                            else {
                                if (pc.isCrown()) {
                                    skillList = PcLvSkillList.isCrown(pc);
                                }
                                else if (pc.isKnight()) {
                                    skillList = PcLvSkillList.isKnight(pc);
                                }
                                else if (pc.isElf()) {
                                    skillList = PcLvSkillList.isElf(pc);
                                }
                                else if (pc.isWizard()) {
                                    skillList = PcLvSkillList.isWizard(pc);
                                }
                                else if (pc.isDarkelf()) {
                                    skillList = PcLvSkillList.isDarkelf(pc);
                                }
                                else if (pc.isDragonKnight()) {
                                    skillList = PcLvSkillList.isDragonKnight(pc);
                                }
                                else if (pc.isIllusionist()) {
                                    skillList = PcLvSkillList.isIllusionist(pc);
                                }
                                if (skillList == null) {
                                    return;
                                }
                                else {
                                    boolean isGfx = false;
                                    boolean shopSkill = false;
                                    if (pc.get_other().get_shopSkill()) {
                                        shopSkill = true;
                                    }
                                    for (int i = 0; i < count; ++i) {
                                        int skillId = this.readD() + 1;
                                        if (!CharSkillReading.get().spellCheck(pc.getId(), skillId) && skillList.contains(new Integer(skillId - 1))) {
                                            L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
                                            int skillLvPrice = C_SkillBuyOK.PRICE[l1skills.getSkillLevel() - 1];
                                            int price = (shopSkill ? S_SkillBuyCN.PCTYPE[pc.getType()] : 6000) * skillLvPrice;
                                            if (pc.getInventory().checkItem(40308, (long)price)) {
                                                pc.getInventory().consumeItem(40308, (long)price);
                                                CharSkillReading.get().spellMastery(pc.getId(), l1skills.getSkillId(), l1skills.getName(), 0, 0);
                                                pc.sendPackets(new S_AddSkill(pc, skillId));
                                                isGfx = true;
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(189));
                                            }
                                        }
                                    }
                                    if (isGfx) {
                                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 224));
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
