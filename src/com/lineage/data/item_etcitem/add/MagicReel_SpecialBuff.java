package com.lineage.data.item_etcitem.add;

import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import java.util.Iterator;
import com.lineage.server.model.L1Party;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class MagicReel_SpecialBuff extends ItemExecutor
{
    private int _type;
    
    private MagicReel_SpecialBuff() {
    }
    
    public static ItemExecutor get() {
        return new MagicReel_SpecialBuff();
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
            pc.getInventory().removeItem(item, 1L);
            switch (this._type) {
                case 1: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 57, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 2: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 79, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 3: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 48, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 4: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 26, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 5: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 42, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 6: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 68, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 7: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 55, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 8: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 148, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
                case 9: {
                    L1Party party = pc.getParty();
                    if (party == null) {
                        pc.sendPackets(new S_SystemMessage("沒有組隊無法使用此道具。"));
                        return;
                    }
                    else {
                        L1SkillUse l1skilluse = new L1SkillUse();
                        for (L1PcInstance member : party.partyUsers().values()) {
                            if (member.getLocation().isInScreen(pc.getLocation())) {
                                l1skilluse.handleCommands(pc, 149, member.getId(), member.getX(), member.getY(), 0, 2);
                            }
                        }
                        break;
                    }
                }
            }
            pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 19));
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
