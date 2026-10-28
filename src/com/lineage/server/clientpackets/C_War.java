package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_War;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_HelpMessage;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_War extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_War.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance player = client.getActiveChar();
            String playerName = player.getName();
            String clanName = player.getClanname();
            int clanId = player.getClanid();
            if (!player.isCrown()) {
                player.sendPackets(new S_ServerMessage(478));
            }
            else if (clanId == 0) {
                player.sendPackets(new S_ServerMessage(272));
            }
            else {
                L1Clan clan = WorldClan.get().getClan(clanName);
                if (clan == null) {
                    return;
                }
                else {
                    L1EmblemIcon emblemIcon = ClanEmblemReading.get().get(clan.getClanId());
                    if (emblemIcon == null && !player.isGm()) {
                        player.sendPackets(new S_ServerMessage(812));
                        return;
                    }
                    else if (player.getId() != clan.getLeaderId()) {
                        player.sendPackets(new S_ServerMessage(478));
                        return;
                    }
                    else {
                        int type = this.readC();
                        String tgname = this.readS();
                        if (clanName.toLowerCase().equals(tgname.toLowerCase())) {
                            return;
                        }
                        else {
                            L1Clan enemyClan = null;
                            String enemyClanName = null;
                            Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                            for (L1Clan checkClan : allClans) {
                                if (checkClan.getClanName().toLowerCase().equals(tgname.toLowerCase())) {
                                    enemyClan = checkClan;
                                    enemyClanName = checkClan.getClanName();
                                    break;
                                }
                            }
                            if (enemyClan == null) {
                                if (tgname.equals(" ")) {
                                    this.war_castle(player, clan, null, type);
                                }
                                return;
                            }
                            else if (clan.getAlliance(enemyClan.getClanId()) == enemyClan) {
                                S_ServerMessage sm = new S_ServerMessage(1205);
                                player.sendPackets(sm);
                                return;
                            }
                            else {
                                boolean inWar = false;
                                List<L1War> warList = WorldWar.get().getWarList();
                                if (enemyClan.getCastleId() == 0) {
                                    for (L1War war : warList) {
                                        if (war.checkClanInWar(clanName)) {
                                            if (type == 0) {
                                                player.sendPackets(new S_ServerMessage(234));
                                                return;
                                            }
                                            else {
                                                inWar = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!inWar && (type == 2 || type == 3)) {
                                    return;
                                }
                                else {
                                    if (clan.getCastleId() != 0) {
                                        if (type == 0) {
                                            player.sendPackets(new S_ServerMessage(474));
                                            return;
                                        }
                                        else if (type == 2 || type == 3) {
                                            return;
                                        }
                                    }
                                    if (enemyClan.getCastleId() == 0 && player.getLevel() <= ConfigOther.clanLeaderlv) {
                                        player.sendPackets(new S_ServerMessage("等級" + ConfigOther.clanLeaderlv + "以下的君主無法宣戰"));
                                        return;
                                    }
                                    else if (enemyClan.getCastleId() != 0) {
                                        this.war_castle(player, clan, enemyClan, type);
                                        return;
                                    }
                                    else if (ServerWarExecutor.get().checkCastleWar() > 0) {
                                        player.sendPackets(new S_HelpMessage("\\fS攻城戰期間，暫停血盟宣戰。"));
                                        return;
                                    }
                                    else {
                                        boolean enemyInWar = false;
                                        for (L1War war2 : warList) {
                                            if (war2.checkClanInWar(enemyClanName)) {
                                                switch (type) {
                                                    case 0: {
                                                        player.sendPackets(new S_ServerMessage(236, enemyClanName));
                                                        return;
                                                    }
                                                    case 2:
                                                    case 3: {
                                                        if (!war2.checkClanInSameWar(clanName, enemyClanName)) {
                                                            return;
                                                        }
                                                        else {
                                                            break;
                                                        }
                                                    }
                                                }
                                                enemyInWar = true;
                                                break;
                                            }
                                        }
                                        if (!enemyInWar && (type == 2 || type == 3)) {
                                            return;
                                        }
                                        else {
                                            L1PcInstance enemyLeader = World.get().getPlayer(enemyClan.getLeaderName());
                                            if (enemyLeader == null) {
                                                player.sendPackets(new S_ServerMessage(218, enemyClanName));
                                                return;
                                            }
                                            else {
                                                switch (type) {
                                                    case 0: {
                                                        enemyLeader.setTempID(player.getId());
                                                        enemyLeader.sendPackets(new S_Message_YN(217, clanName, playerName));
                                                        break;
                                                    }
                                                    case 2: {
                                                        enemyLeader.setTempID(player.getId());
                                                        enemyLeader.sendPackets(new S_Message_YN(221, clanName));
                                                        break;
                                                    }
                                                    case 3: {
                                                        enemyLeader.setTempID(player.getId());
                                                        enemyLeader.sendPackets(new S_Message_YN(222, clanName));
                                                        break;
                                                    }
                                                }
                                                return;
                                            }
                                        }
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
    
    private void war_castle(L1PcInstance player, L1Clan clan, L1Clan enemyClan, int type) {
        try {
            if (player.getLevel() < ConfigOther.clanLeaderlv) {
                player.sendPackets(new S_ServerMessage("等級" + ConfigOther.clanLeaderlv + "以下的君主無法宣戰"));
                return;
            }
            else {
                L1EmblemIcon emblemIcon = ClanEmblemReading.get().get(clan.getClanId());
                if (emblemIcon == null && !player.isGm()) {
                    player.sendPackets(new S_ServerMessage(812));
                    return;
                }
                else {
                    String clanName = player.getClanname();
                    if (enemyClan == null) {
                        L1Object object = World.get().findObject(player.getTempID());
                        if (object instanceof L1NpcInstance) {
                            int id = L1CastleLocation.getCastleIdByArea((L1NpcInstance)object);
                            if (player.getDeclareId() > 0) {
                                id = player.getDeclareId();
                            }
                            if (ServerWarExecutor.get().isNowWar(id)) {
                                L1PcInstance[] clanMember = clan.getOnlineClanMember();
                                for (int k = 0; k < clanMember.length; ++k) {
                                    clanMember[k].sendPackets(new S_War(1, clanName, "NPC"));
                                }
                                player.setDeclareId(0);
                                player.sendPackets(new S_CloseList(player.getId()));
                                for (int k = 0; k < clanMember.length; ++k) {
                                    if (L1CastleLocation.checkInWarArea(id, clanMember[k])) {
                                        L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                                        L1PcInstance[] array;
                                        for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                                            L1PcInstance clanMember2 = array[i];
                                            if (clanMember2.getId() != player.getId() && !clanMember2.isFishing() && !clanMember2.isPrivateShop()) {
                                                int castle_id = L1CastleLocation.getCastleIdByArea(clanMember2);
                                                int[] loc = new int[3];
                                                loc = L1CastleLocation.getGetBackLoc(castle_id);
                                                L1Teleport.teleport(clanMember2, loc[0], loc[1], (short)loc[2], 5, true);
                                                clan.sendPacketsAll(new S_ServerMessage("\\aD宣戰:系統強制驅離旗子範圍內本血盟玩家"));
                                            }
                                            if (clanMember2.isDead()) {
                                                this.restartPlayer(clanMember2, 33064, 32772, (short)4);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                if (type == 0) {
                                    player.sendPackets(new S_ServerMessage(476));
                                }
                                player.sendPackets(new S_CloseList(player.getId()));
                            }
                        }
                        return;
                    }
                    else {
                        String enemyClanName = enemyClan.getClanName();
                        if (WorldWar.get().isWar(clan.getClanName(), enemyClanName)) {
                            player.sendPackets(new S_ServerMessage(234));
                            return;
                        }
                        else {
                            int castle_id2 = enemyClan.getCastleId();
                            if (ServerWarExecutor.get().isNowWar(castle_id2)) {
                                L1PcInstance[] clanMember = clan.getOnlineClanMember();
                                for (int k = 0; k < clanMember.length; ++k) {
                                    if (L1CastleLocation.checkInWarArea(castle_id2, clanMember[k])) {
                                        L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                                        L1PcInstance[] array2;
                                        for (int length2 = (array2 = clanMembers).length, j = 0; j < length2; ++j) {
                                            L1PcInstance clanMember2 = array2[j];
                                            if (clanMember2.getId() != player.getId() && !clanMember2.isFishing() && !clanMember2.isPrivateShop()) {
                                                int[] loc2 = new int[3];
                                                loc2 = L1CastleLocation.getGetBackLoc(castle_id2);
                                                L1Teleport.teleport(clanMember2, loc2[0], loc2[1], (short)loc2[2], 5, true);
                                                clan.sendPacketsAll(new S_ServerMessage("\\aD宣戰:系統強制驅離旗子範圍內本血盟玩家"));
                                            }
                                            if (clanMember2.isDead()) {
                                                this.restartPlayer(clanMember2, 33064, 32772, (short)4);
                                            }
                                        }
                                    }
                                }
                                List<L1War> warList = WorldWar.get().getWarList();
                                boolean enemyInWar = false;
                                for (L1War war : warList) {
                                    if (war.checkClanInWar(enemyClanName)) {
                                        if (type == 0) {
                                            war.declareWar(clanName, enemyClanName);
                                            war.addAttackClan(clanName);
                                            player.sendPackets(new S_CloseList(player.getId()));
                                        }
                                        else if (type == 2 || type == 3) {
                                            if (!war.checkClanInSameWar(clanName, enemyClanName)) {
                                                return;
                                            }
                                            else if (type == 2) {
                                                war.surrenderWar(clanName, enemyClanName);
                                            }
                                            else if (type == 3) {
                                                war.ceaseWar(clanName, enemyClanName);
                                            }
                                        }
                                        enemyInWar = true;
                                        break;
                                    }
                                }
                                if (!enemyInWar && type == 0) {
                                    L1War war = new L1War();
                                    war.handleCommands(1, clanName, enemyClanName);
                                    player.sendPackets(new S_CloseList(player.getId()));
                                }
                            }
                            else {
                                if (type == 0) {
                                    player.sendPackets(new S_ServerMessage(476));
                                }
                                player.sendPackets(new S_CloseList(player.getId()));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            C_War._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void restartPlayer(L1PcInstance pc, int locx, int locy, short mapid) {
        pc.removeAllKnownObjects();
        pc.broadcastPacketAll(new S_RemoveObject(pc));
        pc.setCurrentHp(pc.getLevel());
        pc.set_food(40);
        pc.setDead(false);
        pc.setStatus(0);
        World.get().moveVisibleObject(pc, (int)mapid);
        pc.setX(locx);
        pc.setY(locy);
        pc.setMap(mapid);
        pc.sendPackets(new S_MapID(pc, (int)pc.getMapId(), pc.getMap().isUnderwater()));
        pc.broadcastPacketAll(new S_OtherCharPacks(pc));
        pc.sendPackets(new S_OwnCharPack(pc));
        pc.sendPackets(new S_CharVisualUpdate(pc));
        pc.startHpRegeneration();
        pc.startMpRegeneration();
        pc.sendPackets(new S_Weather(World.get().getWeather()));
        pc.stopPcDeleteTimer();
        if (pc.getHellTime() > 0) {
            pc.beginHell(false);
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
