package com.lineage.server.model;

import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.world.World;
import com.lineage.config.ConfigAlt;
import com.add.L1PcUnlock;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ClanJoin
{
    private static final Log _log;
    private static L1ClanJoin _instance;
    
    static {
        _log = LogFactory.getLog(L1ClanJoin.class);
    }
    
    public static L1ClanJoin getInstance() {
        if (L1ClanJoin._instance == null) {
            L1ClanJoin._instance = new L1ClanJoin();
        }
        return L1ClanJoin._instance;
    }
    
    public boolean ClanJoin(L1PcInstance pc, L1PcInstance joinPc) {
        try {
            int clan_id = pc.getClanid();
            String clanName = pc.getClanname();
            L1Clan clan = WorldClan.get().getClan(clanName);
            if (clan != null) {
                int maxMember = 0;
                int charisma = 0;
                L1PcInstance clanleader = CharacterTable.get().restoreCharacter(clan.getLeaderName());
                if (clanleader == null) {
                    System.out.println("取回盟主資料時發生錯誤。");
                    return false;
                }
                else {
                    charisma = clanleader.getCha();
                    clanleader.getQuest().load();
                    boolean lv45quest = false;
                    if (clanleader.getLevel() >= 50) {
                        if (lv45quest) {
                            maxMember = charisma * 9;
                        }
                        else {
                            maxMember = charisma * 3;
                        }
                    }
                    else if (lv45quest) {
                        maxMember = charisma * 6;
                    }
                    else {
                        maxMember = charisma * 2;
                    }
                    if (ConfigOther.SCLANCOUNT > 0) {
                        maxMember = ConfigOther.SCLANCOUNT;
                    }
                    if (joinPc.getRejoinClanTime() != null && joinPc.getRejoinClanTime().getTime() > System.currentTimeMillis()) {
                        int time = (int)(joinPc.getRejoinClanTime().getTime() - System.currentTimeMillis()) / 3600000;
                        joinPc.sendPackets(new S_ServerMessage(1925, new StringBuilder(String.valueOf(time)).toString()));
                        return false;
                    }
                    else if (joinPc.getClanid() == 0) {
                        String[] clanMembersName = clan.getAllMembers();
                        if (maxMember <= clanMembersName.length) {
                            joinPc.sendPackets(new S_ServerMessage(188, pc.getName()));
                            return false;
                        }
                        else {
                            L1PcInstance[] onlineClanMember;
                            for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                                L1PcInstance clanMembers = onlineClanMember[i];
                                clanMembers.sendPackets(new S_ServerMessage(94, joinPc.getName()));
                            }
                            joinPc.setClanid(clan_id);
                            joinPc.setClanname(clanName);
                            joinPc.setClanRank(7);
                            joinPc.setClanMemberNotes("");
                            joinPc.setPcContribution(0);
                            joinPc.save();
                            joinPc.sendPacketsAll(new S_CharReset(joinPc.getId(), clan.getEmblemId()));
                            joinPc.sendPackets(new S_PacketBox(173, pc.getClan().getEmblemStatus()));
                            clan.addMemberName(joinPc.getName());
                            ClanMembersTable.getInstance().newMember(joinPc);
                            joinPc.sendPackets(new S_PacketBox(27, 7, joinPc.getName()));
                            joinPc.sendPackets(new S_ServerMessage(95, clanName));
                            L1PcUnlock.Pc_Unlock(joinPc);
                            joinPc.setPcContribution(0);
                            joinPc.setClanContribution(0);
                            joinPc.setclanadena(0);
                            joinPc.setClanNameContribution("");
                        }
                    }
                    else if (ConfigAlt.CLAN_ALLIANCE && lv45quest) {
                        this.changeClan(pc, joinPc, maxMember);
                    }
                    else {
                        joinPc.sendPackets(new S_ServerMessage(89));
                    }
                }
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    private void changeClan(L1PcInstance pc, L1PcInstance joinPc, int maxMember) {
        int clanId = pc.getClanid();
        String clanName = pc.getClanname();
        L1Clan clan = WorldClan.get().getClan(clanName);
        String oldClanName = joinPc.getClanname();
        L1Clan oldClan = WorldClan.get().getClan(oldClanName);
        int oldClanId = joinPc.getClanid();
        if (clan != null && oldClan != null && joinPc.isCrown() && joinPc.getId() == oldClan.getLeaderId()) {
            if (maxMember < clan.getAllMembers().length + oldClan.getAllMembers().length) {
                joinPc.sendPackets(new S_ServerMessage(188, pc.getName()));
                return;
            }
            else {
                L1PcInstance[] onlineClanMember;
                for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                    L1PcInstance element = onlineClanMember[i];
                    element.sendPackets(new S_ServerMessage(94, joinPc.getName()));
                }
                try {
                    pc.setClanRank(4);
                    pc.save();
                    L1PcUnlock.Pc_Unlock(pc);
                    pc.sendPackets(new S_PacketBox(27, 4, pc.getName()));
                }
                catch (Exception e) {
                    L1ClanJoin._log.error(e.getLocalizedMessage(), e);
                }
                String[] allMembers;
                for (int length2 = (allMembers = clan.getAllMembers()).length, j = 0; j < length2; ++j) {
                    String membername = allMembers[j];
                    try {
                        L1PcInstance clanuser = World.get().getPlayer(membername);
                        if (clanuser == null) {
                            clanuser = CharacterTable.get().restoreCharacter(membername);
                            if (clanuser == null) {
                                continue;
                            }
                        }
                        int memberrank = clanuser.getClanRank();
                        switch (memberrank) {
                            case 7: {
                                memberrank = 2;
                                break;
                            }
                            case 8: {
                                memberrank = 5;
                                break;
                            }
                            case 9: {
                                memberrank = 6;
                                break;
                            }
                        }
                        clanuser.setClanRank(memberrank);
                        clanuser.save();
                        if (clanuser.getOnlineStatus() == 1) {
                            clanuser.sendPackets(new S_PacketBox(27, memberrank, membername));
                        }
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                String[] allMembers2;
                for (int length3 = (allMembers2 = oldClan.getAllMembers()).length, k = 0; k < length3; ++k) {
                    String element2 = allMembers2[k];
                    L1PcInstance oldClanMember = World.get().getPlayer(element2);
                    if (oldClanMember != null) {
                        ClanMembersTable.getInstance().deleteMember(oldClanMember.getId());
                        oldClanMember.setClanid(clanId);
                        oldClanMember.setClanname(clanName);
                        if (oldClan.getLeaderId() == joinPc.getId()) {
                            oldClanMember.setClanRank(3);
                            oldClanMember.sendPackets(new S_PacketBox(27, 3, oldClanMember.getName()));
                        }
                        else {
                            oldClanMember.setClanRank(2);
                            oldClanMember.sendPackets(new S_PacketBox(27, 2, oldClanMember.getName()));
                        }
                        try {
                            oldClanMember.save();
                        }
                        catch (Exception e3) {
                            L1ClanJoin._log.error(e3.getLocalizedMessage(), e3);
                        }
                        clan.addMemberName(oldClanMember.getName());
                        ClanMembersTable.getInstance().newMember(oldClanMember);
                        oldClanMember.sendPackets(new S_ServerMessage(95, clanName));
                        oldClanMember.sendPacketsAll(new S_CharReset(oldClanMember.getId(), clan.getEmblemId()));
                        oldClanMember.sendPackets(new S_PacketBox(173, pc.getClan().getEmblemStatus()));
                    }
                    else {
                        try {
                            L1PcInstance offClanMember = CharacterTable.get().restoreCharacter(element2);
                            ClanMembersTable.getInstance().deleteMember(offClanMember.getId());
                            offClanMember.setClanid(clanId);
                            offClanMember.setClanname(clanName);
                            offClanMember.setClanRank(2);
                            offClanMember.save();
                            clan.addMemberName(offClanMember.getName());
                            ClanMembersTable.getInstance().newMember(offClanMember);
                        }
                        catch (Exception e3) {
                            L1ClanJoin._log.error(e3.getLocalizedMessage(), e3);
                        }
                    }
                }
                ClanEmblemReading.get().deleteIcon(oldClanId);
                ClanReading.get().deleteClan(oldClanName);
                L1ClanMatching Clan = L1ClanMatching.getInstance();
                Clan.deleteClanMatching(oldClan);
            }
        }
    }
}
