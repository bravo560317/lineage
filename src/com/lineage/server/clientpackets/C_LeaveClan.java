package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import java.util.logging.Level;
import com.lineage.server.datatables.sql.CharacterTable;
import com.add.L1PcUnlock;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.config.ConfigClan;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import java.util.logging.Logger;

public class C_LeaveClan extends ClientBasePacket
{
    private static final String C_LEAVE_CLAN = "[C] C_LeaveClan";
    private static Logger _log;
    
    static {
        C_LeaveClan._log = Logger.getLogger(C_LeaveClan.class.getName());
    }
    
    @Override
    public void start(byte[] abyte0, ClientExecutor client) throws Exception {
        L1PcInstance player = client.getActiveChar();
        if (player == null) {
            return;
        }
        else {
            String clan_name = player.getClanname();
            String player_name = player.getName();
            int clan_id = player.getClanid();
            if (clan_id == 0) {
                return;
            }
            else {
                L1Clan clan = WorldClan.get().getClan(clan_name);
                if (clan != null) {
                    String[] clan_member_name = clan.getAllMembers();
                    if (player.isCrown() && player.getId() == clan.getLeaderId()) {
                        if (!ConfigOther.CLANDEL) {
                            player.sendPackets(new S_ServerMessage(302));
                            player.sendPackets(new S_NPCTalkReturn(player.getId(), "y_clanD"));
                            return;
                        }
                        else {
                            int castleId = clan.getCastleId();
                            int houseId = clan.getHouseId();
                            if (castleId != 0 || houseId != 0) {
                                player.sendPackets(new S_ServerMessage(665));
                                return;
                            }
                            else {
                                for (L1War war : WorldWar.get().getWarList()) {
                                    if (war.checkClanInWar(clan_name)) {
                                        player.sendPackets(new S_ServerMessage(302));
                                        return;
                                    }
                                }
                                if (ClanAllianceReading.get().getAlliance(player.getClanid()) != null) {
                                    player.sendPackets(new S_ServerMessage(3109));
                                    return;
                                }
                                else {
                                    for (int i = 0; i < clan_member_name.length; ++i) {
                                        L1PcInstance online_pc = World.get().getPlayer(clan_member_name[i]);
                                        if (online_pc != null) {
                                            online_pc.setClanid(0);
                                            online_pc.setClanname("");
                                            online_pc.setClanRank(0);
                                            online_pc.setClanMemberId(0);
                                            online_pc.setClanMemberNotes("");
                                            online_pc.setTitle("");
                                            if (ConfigClan.clandelt) {
                                                online_pc.setPcContribution(0);
                                            }
                                            online_pc.setClanContribution(0);
                                            online_pc.setclanadena(0);
                                            online_pc.setClanNameContribution("");
                                            L1ItemInstance item = player.getInventory().findItemId(92164);
                                            if (item != null) {
                                                online_pc.getInventory().deleteItem(item);
                                            }
                                            online_pc.sendPacketsAll(new S_CharTitle(online_pc.getId(), ""));
                                            online_pc.sendPacketsAll(new S_CharReset(online_pc.getId(), 0));
                                            online_pc.save();
                                            L1PcUnlock.Pc_Unlock(online_pc);
                                            online_pc.sendPackets(new S_ServerMessage(269, player_name, clan_name));
                                        }
                                        else {
                                            try {
                                                L1PcInstance offline_pc = CharacterTable.get().restoreCharacter(clan_member_name[i]);
                                                L1ItemInstance item2 = offline_pc.getInventory().findItemId(92164);
                                                if (item2 != null) {
                                                    offline_pc.getInventory().deleteItem(item2);
                                                }
                                                offline_pc.setClanid(0);
                                                offline_pc.setClanname("");
                                                offline_pc.setClanRank(0);
                                                if (ConfigClan.clandelt) {
                                                    offline_pc.setPcContribution(0);
                                                }
                                                offline_pc.setClanContribution(0);
                                                offline_pc.setClanNameContribution("");
                                                offline_pc.setTitle("");
                                                offline_pc.save();
                                            }
                                            catch (Exception e) {
                                                C_LeaveClan._log.log(Level.SEVERE, e.getLocalizedMessage(), e);
                                            }
                                        }
                                    }
                                    ClanEmblemReading.get().deleteIcon(clan_id);
                                    ClanReading.get().deleteClan(clan_name);
                                    ClanMembersTable.getInstance().deleteAllMember(clan.getClanId());
                                    L1ClanMatching Clan = L1ClanMatching.getInstance();
                                    Clan.deleteClanMatching(clan_name);
                                    Clan.deleteClanMatchingApcList(clan);
                                }
                            }
                        }
                    }
                    else {
                        player.sendPackets(new S_Message_YN(1906));
                    }
                    return;
                }
                else {
                    return;
                }
            }
        }
    }
    
    @Override
    public String getType() {
        return "[C] C_LeaveClan";
    }
}
