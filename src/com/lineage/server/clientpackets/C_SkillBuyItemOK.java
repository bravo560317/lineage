package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1SkillItem;
import com.lineage.server.templates.L1Skills;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ItemError;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.datatables.SkillsItemTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.list.PcLvSkillList;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_SkillBuyItemOK extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_EnterPortal.class);
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
                            ArrayList<Integer> skillList = null;
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
                                for (int count = this.readH(), i = 0; i < count; ++i) {
                                    int skillId = this.readD() + 1;
                                    if (!CharSkillReading.get().spellCheck(pc.getId(), skillId) && skillList.contains(new Integer(skillId - 1))) {
                                        L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
                                        L1SkillItem priceItem = SkillsItemTable.get().getTemplate(skillId);
                                        if (priceItem != null && priceItem.get_items() != null) {
                                            int length = priceItem.get_items().length;
                                            boolean[] isOks = new boolean[length];
                                            for (int x = 0; x < length; ++x) {
                                                int itemId = priceItem.get_items()[x];
                                                int itemCount = priceItem.get_counts()[x];
                                                if (!pc.getInventory().checkItem(itemId, (long)itemCount)) {
                                                    isOks[x] = false;
                                                }
                                                else {
                                                    isOks[x] = true;
                                                }
                                            }
                                            boolean isShopOk = true;
                                            boolean[] array;
                                            for (int length2 = (array = isOks).length, j = 0; j < length2; ++j) {
                                                boolean isOk = array[j];
                                                if (!isOk) {
                                                    isShopOk = false;
                                                }
                                            }
                                            if (isShopOk) {
                                                for (int x2 = 0; x2 < priceItem.get_items().length; ++x2) {
                                                    int itemId2 = priceItem.get_items()[x2];
                                                    int itemCount2 = priceItem.get_counts()[x2];
                                                    if (pc.getInventory().checkItem(itemId2, (long)itemCount2)) {
                                                        pc.getInventory().consumeItem(itemId2, (long)itemCount2);
                                                    }
                                                }
                                                CharSkillReading.get().spellMastery(pc.getId(), l1skills.getSkillId(), l1skills.getName(), 0, 0);
                                                pc.sendPackets(new S_AddSkill(pc, skillId));
                                                isGfx = true;
                                            }
                                            else {
                                                pc.sendPackets(new S_ItemError(skillId - 1));
                                            }
                                        }
                                        else {
                                            pc.sendPackets(new S_ServerMessage(939));
                                            C_SkillBuyItemOK._log.error("購買技能 材料 設置資料異常 材料未設置: " + skillId);
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
