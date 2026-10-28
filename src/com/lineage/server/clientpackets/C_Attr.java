package com.lineage.server.clientpackets;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;
import java.sql.Timestamp;
import com.lineage.server.datatables.ClanMembersTable;
import com.add.L1PcUnlock;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.config.ConfigClan;
import java.util.Collection;
import com.lineage.server.templates.L1House;
import com.lineage.server.model.Instance.L1EffectInstance;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.L1ChatParty;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.william.ConDmg;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.CharObjidTable;
import java.util.regex.Matcher;
import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_Resurrection;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_Trade;
import com.lineage.server.utils.FaceToFace;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.L1Alliance;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1War;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.L1ClanJoin;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Attr extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Attr.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost() && !pc.isTeleport()) {
                int mode = this.readH();
                int tgobjid = 0;
                if (mode == 0) {
                    tgobjid = this.readD();
                    mode = this.readH();
                }
                switch (mode) {
                    case 97: {
                        int c = this.readC();
                        L1PcInstance joinPc = (L1PcInstance)World.get().findObject(pc.getTempID());
                        pc.setTempID(0);
                        if (joinPc != null) {
                            if (c == 0) {
                                joinPc.sendPackets(new S_ServerMessage(96, pc.getName()));
                                break;
                            }
                            else if (c == 1) {
                                L1ClanJoin.getInstance().ClanJoin(pc, joinPc);
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
                    case 217:
                    case 221:
                    case 222: {
                        int c = this.readC();
                        L1PcInstance enemyLeader = (L1PcInstance)World.get().findObject(pc.getTempID());
                        if (enemyLeader == null) {
                            return;
                        }
                        else {
                            pc.setTempID(0);
                            String clanName = pc.getClanname();
                            String enemyClanName = enemyLeader.getClanname();
                            if (c == 0) {
                                if (mode == 217) {
                                    enemyLeader.sendPackets(new S_ServerMessage(236, clanName));
                                    break;
                                }
                                else if (mode == 221 || mode == 222) {
                                    enemyLeader.sendPackets(new S_ServerMessage(237, clanName));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            else if (c == 1) {
                                int castle_id = 1;
                                while (castle_id < 8) {
                                    if (ServerWarExecutor.get().isNowWar(castle_id)) {
                                        enemyLeader.sendPackets(new S_ServerMessage("城戰期間無法血盟宣戰"));
                                        return;
                                    }
                                    else {
                                        ++castle_id;
                                    }
                                }
                                if (mode == 217) {
                                    L1War war = new L1War();
                                    war.handleCommands(2, enemyClanName, clanName);
                                    break;
                                }
                                else if (mode == 221 || mode == 222) {
                                    for (L1War war : WorldWar.get().getWarList()) {
                                        if (war.checkClanInWar(clanName)) {
                                            if (mode == 221) {
                                                war.surrenderWar(enemyClanName, clanName);
                                                break;
                                            }
                                            else if (mode == 222) {
                                                war.ceaseWar(enemyClanName, clanName);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                    }
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
                    }
                    case 223: {
                        L1PcInstance alliancePc = (L1PcInstance)World.get().findObject(pc.getTempID());
                        pc.setTempID(0);
                        if (alliancePc == null) {
                            return;
                        }
                        else {
                            int c = this.readC();
                            if (c == 1) {
                                L1Clan clan1 = pc.getClan();
                                int PcClanId = pc.getClanid();
                                int TargetClanId = alliancePc.getClanid();
                                if (clan1 == null || pc.getId() != clan1.getLeaderId()) {
                                    pc.sendPackets(new S_ServerMessage(518));
                                    return;
                                }
                                else {
                                    L1Clan target_clan = alliancePc.getClan();
                                    if (target_clan == null) {
                                        return;
                                    }
                                    else {
                                        L1Alliance alliance = ClanAllianceReading.get().getAlliance(clan1.getClanId());
                                        if (alliance == null) {
                                            alliance = new L1Alliance(clan1.getClanId(), new L1Clan[] { clan1, target_clan });
                                            ClanAllianceReading.get().insertAlliance(alliance);
                                            String TargetClanName = alliancePc.getClanname();
                                            L1Clan PcClan = WorldClan.get().getClan(pc.getClanname());
                                            L1Clan TargegClan = WorldClan.get().getClan(TargetClanName);
                                            PcClan.addAlliance(TargetClanId);
                                            TargegClan.addAlliance(PcClanId);
                                            ClanReading.get().updateClan(PcClan);
                                            ClanReading.get().updateClan(TargegClan);
                                        }
                                        else if (!alliance.checkSize()) {
                                            alliancePc.sendPackets(new S_ServerMessage(1201));
                                            return;
                                        }
                                        else {
                                            alliance.addAlliance(target_clan);
                                            ClanAllianceReading.get().updateAlliance(alliance.getOrderId(), alliance.getTotalList());
                                        }
                                        ClanReading.get().updateClan(pc.getClan());
                                        World.get().broadcastPacketToAll(new S_ServerMessage(224, clan1.getClanName(), target_clan.getClanName()));
                                        alliance.sendPacketsAll("", new S_ServerMessage(1200, target_clan.getClanName()));
                                        String TargetClanName = alliancePc.getClanname();
                                        L1Clan PcClan = WorldClan.get().getClan(pc.getClanname());
                                        L1Clan TargegClan = WorldClan.get().getClan(TargetClanName);
                                        PcClan.addAlliance(TargetClanId);
                                        TargegClan.addAlliance(PcClanId);
                                        ClanReading.get().updateClan(PcClan);
                                        ClanReading.get().updateClan(TargegClan);
                                        break;
                                    }
                                }
                            }
                            else if (c == 0) {
                                alliancePc.sendPackets(new S_ServerMessage(1198));
                                break;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    case 252: {
                        int c = this.readC();
                        L1PcInstance trading_partner = (L1PcInstance)World.get().findObject(pc.getTradeID());
                        L1PcInstance target_partner = FaceToFace.faceToFace(pc);
                        if (trading_partner != null && target_partner != null && target_partner.getId() == trading_partner.getId()) {
                            if (c == 0) {
                                trading_partner.sendPackets(new S_ServerMessage(253, pc.getName()));
                                pc.setTradeID(0);
                                trading_partner.setTradeID(0);
                                break;
                            }
                            else if (c == 1) {
                                pc.sendPackets(new S_Trade(trading_partner.getName()));
                                trading_partner.sendPackets(new S_Trade(pc.getName()));
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            pc.setTradeID(0);
                            if (trading_partner != null) {
                                trading_partner.setTradeID(0);
                                break;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    case 321: {
                        int c = this.readC();
                        L1PcInstance resusepc1 = (L1PcInstance)World.get().findObject(pc.getTempID());
                        pc.setTempID(0);
                        if (resusepc1 != null) {
                            if (c == 0) {
                                break;
                            }
                            else if (c == 1) {
                                L1EffectInstance tomb = pc.get_tomb();
                                if (tomb != null) {
                                    tomb.broadcastPacketAll(new S_DoActionGFX(tomb.getId(), 8));
                                    tomb.deleteMe();
                                }
                                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 230));
                                pc.resurrect(pc.getMaxHp() / 2);
                                pc.setCurrentHp(pc.getMaxHp() / 2);
                                pc.startHpRegeneration();
                                pc.startMpRegeneration();
                                pc.stopPcDeleteTimer();
                                pc.sendPacketsAll(new S_Resurrection(pc, resusepc1, 0));
                                pc.sendPacketsAll(new S_CharVisualUpdate(pc));
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
                    case 322: {
                        int c = this.readC();
                        L1PcInstance resusepc2 = (L1PcInstance)World.get().findObject(pc.getTempID());
                        pc.setTempID(0);
                        if (resusepc2 != null) {
                            if (c == 0) {
                                break;
                            }
                            else if (c == 1) {
                                L1EffectInstance tomb2 = pc.get_tomb();
                                if (tomb2 != null) {
                                    tomb2.broadcastPacketAll(new S_DoActionGFX(tomb2.getId(), 8));
                                    tomb2.deleteMe();
                                }
                                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 230));
                                pc.resurrect(pc.getMaxHp());
                                pc.setCurrentHp(pc.getMaxHp());
                                pc.startHpRegeneration();
                                pc.startMpRegeneration();
                                pc.stopPcDeleteTimer();
                                pc.sendPacketsAll(new S_Resurrection(pc, resusepc2, 0));
                                pc.sendPacketsAll(new S_CharVisualUpdate(pc));
                                if (pc.getExpRes() == 1 && pc.isGres() && pc.isGresValid()) {
                                    pc.resExp();
                                    pc.setExpRes(0);
                                    pc.setGres(false);
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
                    case 325: {
                        int c = this.readC();
                        String petName = this.readS();
                        if (pc.is_rname()) {
                            String name = Matcher.quoteReplacement(petName);
                            name = name.replaceAll("\\s", "");
                            name = name.replaceAll("\u3000", "");
                            name = name.substring(0, 1).toUpperCase() + name.substring(1);
                            String[] banlist;
                            for (int length = (banlist = C_CreateChar.BANLIST).length, k = 0; k < length; ++k) {
                                String ban = banlist[k];
                                if (name.indexOf(ban) != -1) {
                                    name = "";
                                }
                            }
                            if (name.length() == 0) {
                                pc.sendPackets(new S_ServerMessage(53));
                                return;
                            }
                            else if (!C_CreateChar.isInvalidName(name)) {
                                pc.sendPackets(new S_ServerMessage(53));
                                return;
                            }
                            else if (CharObjidTable.get().charObjid(name) != 0) {
                                pc.sendPackets(new S_ServerMessage(58));
                                return;
                            }
                            else {
                                World.get().removeObject(pc);
                                pc.getInventory().consumeItem(41227, 1L);
                                RecordTable.get().recordPcChangeName(pc.getId(), pc.getAccountName(), pc.getName(), name, pc.getIp());
                                pc.setName(name);
                                CharObjidTable.get().reChar(pc.getId(), name);
                                CharacterTable.get().newCharName(pc.getId(), name);
                                World.get().storeObject(pc);
                                pc.sendPacketsAll(new S_ChangeName(pc, true));
                                pc.sendPackets(new S_ServerMessage(166, "由於人物名稱異動!請重新登入遊戲!將於5秒後強制斷線!"));
                                C_Attr.KickPc kickPc = new C_Attr.KickPc(pc);
                                kickPc.start_cmd();
                            }
                        }
                        else {
                            L1PetInstance pet = (L1PetInstance)World.get().findObject(pc.getTempID());
                            pc.setTempID(0);
                            renamePet(pet, petName);
                        }
                        pc.rename(false);
                        break;
                    }
                    case 479: {
                        int c = this.readC();
                        if (c == 1) {
                            String s = this.readS();
                            if (pc.getLevel() - 50 <= pc.getBonusStats()) {
                                return;
                            }
                            else {
                                if (s.equalsIgnoreCase("str")) {
                                    if (pc.getBaseStr() < ConfigAlt.POWER) {
                                        pc.addBaseStr(1);
                                        pc.setBonusStats(pc.getBonusStats() + 1);
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_CharVisualUpdate(pc));
                                        pc.save();
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER + "。 請重試一次。"));
                                    }
                                }
                                else if (s.equalsIgnoreCase("dex")) {
                                    if (pc.getBaseDex() < ConfigAlt.POWER) {
                                        pc.addBaseDex(1);
                                        pc.resetBaseAc();
                                        pc.setBonusStats(pc.getBonusStats() + 1);
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_CharVisualUpdate(pc));
                                        pc.save();
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER + "。 請重試一次。"));
                                    }
                                }
                                else if (s.equalsIgnoreCase("con")) {
                                    if (pc.getBaseCon() < ConfigAlt.POWER) {
                                        pc.addBaseCon(1);
                                        pc.setBonusStats(pc.getBonusStats() + 1);
                                        int condmg = ConDmg.getConHitSkill(pc, (int)pc.getCon());
                                        if (condmg != 0) {
                                            pc.addMaxHp(condmg);
                                            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                                        }
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_CharVisualUpdate(pc));
                                        pc.save();
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER + "。 請重試一次。"));
                                    }
                                }
                                else if (s.equalsIgnoreCase("int")) {
                                    if (pc.getBaseInt() < ConfigAlt.POWER) {
                                        pc.addBaseInt(1);
                                        pc.setBonusStats(pc.getBonusStats() + 1);
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_CharVisualUpdate(pc));
                                        pc.save();
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER + "。 請重試一次。"));
                                    }
                                }
                                else if (s.equalsIgnoreCase("wis")) {
                                    if (pc.getBaseWis() < ConfigAlt.POWER) {
                                        pc.addBaseWis(1);
                                        pc.resetBaseMr();
                                        pc.setBonusStats(pc.getBonusStats() + 1);
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_CharVisualUpdate(pc));
                                        pc.save();
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER + "。 請重試一次。"));
                                    }
                                }
                                else if (s.equalsIgnoreCase("cha")) {
                                    if (pc.getBaseCha() < ConfigAlt.POWER) {
                                        pc.addBaseCha(1);
                                        pc.setBonusStats(pc.getBonusStats() + 1);
                                        pc.sendPackets(new S_OwnCharStatus2(pc));
                                        pc.sendPackets(new S_CharVisualUpdate(pc));
                                        pc.save();
                                    }
                                    else {
                                        pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER + "。 請重試一次。"));
                                    }
                                }
                                if (pc.power()) {
                                    pc.sendPackets(new S_Bonusstats(pc.getId()));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 512: {
                        int c = this.readC();
                        String houseName = this.readS();
                        int houseId = pc.getTempID();
                        pc.setTempID(0);
                        if (houseName.length() <= 16) {
                            L1House house = HouseReading.get().getHouseTable(houseId);
                            house.setHouseName(houseName);
                            HouseReading.get().updateHouse(house);
                            break;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(513));
                            break;
                        }
                    }
                    case 630: {
                        int c = this.readC();
                        L1PcInstance fightPc = (L1PcInstance)World.get().findObject(pc.getFightId());
                        if (c == 0) {
                            pc.setFightId(0);
                            fightPc.setFightId(0);
                            fightPc.sendPackets(new S_ServerMessage(631, pc.getName()));
                            break;
                        }
                        else if (c == 1) {
                            fightPc.sendPackets(new S_PacketBox(5, fightPc.getFightId(), fightPc.getId()));
                            pc.sendPackets(new S_PacketBox(5, pc.getFightId(), pc.getId()));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 653: {
                        int c = this.readC();
                        L1PcInstance target653 = (L1PcInstance)World.get().findObject(pc.getPartnerId());
                        if (c == 0) {
                            return;
                        }
                        else {
                            if (c == 1) {
                                if (target653 != null) {
                                    target653.setPartnerId(0);
                                    target653.save();
                                    target653.sendPackets(new S_ServerMessage(662));
                                }
                                else {
                                    CharacterTable.get();
                                    CharacterTable.updatePartnerId(pc.getPartnerId());
                                }
                            }
                            pc.setPartnerId(0);
                            pc.save();
                            pc.sendPackets(new S_ServerMessage(662));
                            break;
                        }
                    }
                    case 654: {
                        int c = this.readC();
                        L1PcInstance partner = (L1PcInstance)World.get().findObject(pc.getTempID());
                        pc.setTempID(0);
                        if (partner != null) {
                            if (c == 0) {
                                partner.sendPackets(new S_ServerMessage(656, pc.getName()));
                                break;
                            }
                            else if (c == 1) {
                                pc.setPartnerId(partner.getId());
                                pc.save();
                                pc.sendPackets(new S_ServerMessage(790));
                                pc.sendPackets(new S_ServerMessage(655, partner.getName()));
                                partner.setPartnerId(pc.getId());
                                partner.save();
                                partner.sendPackets(new S_ServerMessage(790));
                                partner.sendPackets(new S_ServerMessage(655, pc.getName()));
                                for (L1PcInstance allpc : World.get().getAllPlayers()) {
                                    if (allpc != null) {
                                        allpc.sendPackets(new S_SystemMessage("恭喜 " + pc.getName() + " 與 " + partner.getName() + " 結為夫妻。"));
                                        allpc.sendPackets(new S_SystemMessage("讓我們一起為這對新人送上最真摯的祝福。"));
                                    }
                                }
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
                    case 729: {
                        int c = this.readC();
                        if (c == 0) {
                            int objId = pc.getTempID();
                            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                            for (L1PcInstance each : allPc) {
                                if (each.getId() == objId) {
                                    each.sendPackets(new S_ServerMessage("對方拒絕了你的呼喚。"));
                                    break;
                                }
                            }
                            if (pc.getcallclanal() > 0) {
                                pc.setcallclanal(0);
                            }
                            pc.setTempID(0);
                            break;
                        }
                        else if (c == 1) {
                            if (pc.getcallclanal() > 0) {
                                if (!pc.getInventory().checkItem(ConfigOther.target_clan_itemid, (long)ConfigOther.target_clan_count)) {
                                    pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg2));
                                    return;
                                }
                                else if (!pc.getMap().isClanPc()) {
                                    pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                                    return;
                                }
                                else if (isInWarAreaAndWarTime(pc)) {
                                    pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
                                    return;
                                }
                                else {
                                    this.callClan1(pc);
                                    break;
                                }
                            }
                            else {
                                this.callClan(pc);
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 738: {
                        int c = this.readC();
                        if (c == 0) {
                            break;
                        }
                        else if (c == 1 && pc.getExpRes() == 1) {
                            int cost = 0;
                            int level = pc.getLevel();
                            int lawful = pc.getLawful();
                            if (level < 45) {
                                cost = level * level * 100;
                            }
                            else {
                                cost = level * level * 200;
                            }
                            if (lawful >= 0) {
                                cost /= 2;
                            }
                            if (pc.getInventory().consumeItem(40308, (long)cost)) {
                                pc.resExp();
                                pc.setExpRes(0);
                                break;
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(189));
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 748: {
                        if (pc.hasSkillEffect(1692)) {
                            int c = this.readC();
                            if (c == 0) {
                                break;
                            }
                            else if (c == 1) {
                                if (pc.isParalyzed_guaji()) {
                                    return;
                                }
                                else {
                                    int newX = pc.getTeleportX();
                                    int newY = pc.getTeleportY();
                                    short mapId = pc.getTeleportMapId();
                                    L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        else if (pc.isParalyzed_guaji()) {
                            pc.sendPackets(new S_Paralysis(7, false));
                            return;
                        }
                        else {
                            int newX = pc.getTeleportX();
                            int newY = pc.getTeleportY();
                            short mapId = pc.getTeleportMapId();
                            L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
                            break;
                        }
                    }
                    case 951: {
                        int c = this.readC();
                        L1PcInstance chatPc = (L1PcInstance)World.get().findObject(pc.getPartyID());
                        if (chatPc != null) {
                            if (c == 0) {
                                chatPc.sendPackets(new S_ServerMessage(423, pc.getName()));
                                pc.setPartyID(0);
                                break;
                            }
                            else if (c == 1) {
                                if (chatPc.isInChatParty()) {
                                    if (chatPc.getChatParty().isVacancy() || chatPc.isGm()) {
                                        chatPc.getChatParty().addMember(pc);
                                        break;
                                    }
                                    else {
                                        chatPc.sendPackets(new S_ServerMessage(417));
                                        break;
                                    }
                                }
                                else {
                                    L1ChatParty chatParty = new L1ChatParty();
                                    chatParty.addMember(chatPc);
                                    chatParty.addMember(pc);
                                    chatPc.sendPackets(new S_ServerMessage(424, pc.getName()));
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
                    case 953: {
                        int c = this.readC();
                        L1PcInstance target654 = (L1PcInstance)World.get().findObject(pc.getPartyID());
                        if (target654 != null) {
                            if (c == 0) {
                                target654.sendPackets(new S_ServerMessage(423, pc.getName()));
                                pc.setPartyID(0);
                                break;
                            }
                            else if (c == 1) {
                                if (target654.isInParty()) {
                                    if (target654.getParty().isVacancy()) {
                                        target654.getParty().addMember(pc);
                                        break;
                                    }
                                    else {
                                        target654.sendPackets(new S_ServerMessage(417));
                                        break;
                                    }
                                }
                                else {
                                    L1Party party = new L1Party();
                                    party.addMember(target654);
                                    party.addMember(pc);
                                    target654.sendPackets(new S_ServerMessage(424, pc.getName()));
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
                    case 1210: {
                        if (this.readC() == 1) {
                            L1Clan clan1 = pc.getClan();
                            if (clan1 == null || pc.getId() != clan1.getLeaderId()) {
                                pc.sendPackets(new S_ServerMessage(518));
                                return;
                            }
                            else {
                                L1Alliance alliance2 = ClanAllianceReading.get().getAlliance(clan1.getClanId());
                                if (alliance2 == null) {
                                    return;
                                }
                                else {
                                    for (L1Clan l1clan : alliance2.getTotalList()) {
                                        if (l1clan.getClanId() == clan1.getClanId()) {
                                            alliance2.getTotalList().remove(l1clan);
                                            break;
                                        }
                                    }
                                    if (alliance2.getTotalList().size() < 2) {
                                        ClanAllianceReading.get().deleteAlliance(alliance2.getOrderId());
                                    }
                                    else {
                                        ClanAllianceReading.get().updateAlliance(alliance2.getOrderId(), alliance2.getTotalList());
                                    }
                                    World.get().broadcastPacketToAll(new S_ServerMessage(225, (alliance2.getTotalList().get(0)).getClanName(), clan1.getClanName()));
                                    alliance2.sendPacketsAll("", new S_ServerMessage(1204, clan1.getClanName()));
                                    pc.getClan().AllianceDelete();
                                    ClanReading.get().updateClan(pc.getClan());
                                    break;
                                }
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 1268: {
                        if (pc.getLevel() <= 29 || pc.getLevel() >= 52) {
                            pc.getLevel();
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 1906: {
                        int c = this.readC();
                        if (c == 0) {
                            this.leaveClan(pc, false);
                            break;
                        }
                        else {
                            boolean loginLeader = false;
                            String clan_name = pc.getClanname();
                            L1Clan clan2 = WorldClan.get().getClan(clan_name);
                            L1PcInstance[] clanMember = clan2.getOnlineClanMember();
                            for (int i = 0; i < clanMember.length; ++i) {
                                if (clanMember[i].getClanRank() == 4 || clanMember[i].getClanRank() == 3 || clanMember[i].getClanRank() == 10) {
                                    clanMember[i].setTempID(pc.getId());
                                    clanMember[i].sendPackets(new S_Message_YN(1908, pc.getName()));
                                    loginLeader = true;
                                }
                            }
                            if (loginLeader) {
                                pc.sendPackets(new S_ServerMessage(1907));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_Message_YN(1914));
                                break;
                            }
                        }
                    }
                    case 1908: {
                        L1PcInstance leavePc = (L1PcInstance)World.get().findObject(pc.getTempID());
                        int c = this.readC();
                        if (c == 0) {
                            String clan_name = pc.getClanname();
                            L1Clan clan2 = WorldClan.get().getClan(clan_name);
                            L1PcInstance[] clanMember = clan2.getOnlineClanMember();
                            for (int j = 0; j < clanMember.length; ++j) {
                                clanMember[j].sendPackets(new S_ServerMessage(1917, pc.getName(), leavePc.getName()));
                            }
                            leavePc.sendPackets(new S_Message_YN(1912));
                            break;
                        }
                        else {
                            String clan_name = pc.getClanname();
                            L1Clan clan2 = WorldClan.get().getClan(clan_name);
                            L1PcInstance[] clanMember = clan2.getOnlineClanMember();
                            for (int j = 0; j < clanMember.length; ++j) {
                                clanMember[j].sendPackets(new S_ServerMessage(1916, pc.getName(), leavePc.getName()));
                            }
                            this.leaveClan(leavePc, true);
                            break;
                        }
                    }
                    case 1912:
                    case 1914: {
                        int c = this.readC();
                        if (c == 0) {
                            return;
                        }
                        else {
                            this.leaveClan(pc, false);
                            break;
                        }
                    }
                    case 3312: {
                        int c = this.readC();
                        if (c == 1) {
                            pc.getInventory().consumeItem(ConfigOther.checkitem76, (long)ConfigOther.checkitemcount76);
                            pc.getQuest().set_step(58003, 1);
                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3313: {
                        int c = this.readC();
                        if (c == 1) {
                            pc.getInventory().consumeItem(ConfigOther.checkitem81, (long)ConfigOther.checkitemcount81);
                            pc.getQuest().set_step(58002, 1);
                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 3589: {
                        int c = this.readC();
                        if (c == 1) {
                            pc.getInventory().consumeItem(ConfigOther.checkitem59, (long)ConfigOther.checkitemcount59);
                            pc.getQuest().set_step(58001, 1);
                            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case 4000: {
                        int c = this.readC();
                        if (c == 0) {
                            int objId = pc.getTempID();
                            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                            for (L1PcInstance each : allPc) {
                                if (each.getId() == objId) {
                                    each.sendPackets(new S_ServerMessage("對方拒絕了你的呼喚。"));
                                    break;
                                }
                            }
                            if (pc.getcallclanal() > 0) {
                                pc.setcallclanal(0);
                            }
                            pc.setTempID(0);
                            break;
                        }
                        else if (c == 1 && pc.getcallclanal() > 0) {
                            if (!pc.getInventory().checkItem(ConfigOther.target_party_itemid, (long)ConfigOther.target_party_count)) {
                                pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg6));
                                return;
                            }
                            else if (!pc.getMap().isPartyPc()) {
                                pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                                return;
                            }
                            else if (isInWarAreaAndWarTime(pc)) {
                                pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
                                return;
                            }
                            else {
                                this.callpartyall(pc);
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    case 4001: {
                        int c = this.readC();
                        if (c == 0) {
                            int objId = pc.getTempID();
                            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                            for (L1PcInstance each : allPc) {
                                if (each.getId() == objId) {
                                    each.sendPackets(new S_ServerMessage("對方拒絕了你的呼喚。"));
                                    break;
                                }
                            }
                            if (pc.getcallclana2() > 0) {
                                pc.setcallclana2(0);
                            }
                            pc.setTempID(0);
                            break;
                        }
                        else if (c == 1 && pc.getcallclana2() > 0) {
                            if (!pc.getInventory().checkItem(ConfigOther.target_clan_itemid2, (long)ConfigOther.target_clan_count2)) {
                                pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg16));
                                return;
                            }
                            else if (!pc.getMap().isClanPc()) {
                                pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                                return;
                            }
                            else if (isInWarAreaAndWarTime(pc)) {
                                pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
                                return;
                            }
                            else {
                                this.callClan2(pc);
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            else {
                return;
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    private void leaveClan(L1PcInstance leavePc, boolean isApproved) {
        String clan_name = leavePc.getClanname();
        L1Clan clan = WorldClan.get().getClan(clan_name);
        L1PcInstance[] clanMember = clan.getOnlineClanMember();
        for (int i = 0; i < clanMember.length; ++i) {
            clanMember[i].sendPackets(new S_ServerMessage(178, leavePc.getName(), clan_name));
        }
        if (clan.getWarehouseUsingChar() == leavePc.getId()) {
            clan.setWarehouseUsingChar(0);
        }
        try {
            long time = 0L;
            if (isApproved) {
                time = 0L;
            }
            else {
                time = 10800000L;
            }
            leavePc.setClanid(0);
            leavePc.setClanname("");
            leavePc.setClanRank(0);
            leavePc.setClanMemberId(0);
            leavePc.setClanMemberNotes("");
            if (ConfigClan.clandelt) {
                leavePc.setPcContribution(0);
            }
            leavePc.setClanContribution(0);
            leavePc.setClanNameContribution("");
            leavePc.sendPacketsAll(new S_CharTitle(leavePc.getId(), ""));
            leavePc.sendPacketsAll(new S_CharReset(leavePc.getId(), 0));
            leavePc.save();
            L1PcUnlock.Pc_Unlock(leavePc);
            clan.delMemberName(leavePc.getName());
            ClanMembersTable.getInstance().deleteMember(leavePc.getId());
            leavePc.setRejoinClanTime(new Timestamp(System.currentTimeMillis() + time));
        }
        catch (Exception e) {
            C_Attr._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static void renamePet(L1PetInstance pet, String name) {
        if (pet == null || name == null) {
            throw new NullPointerException();
        }
        else {
            int petItemObjId = pet.getItemObjId();
            L1Pet petTemplate = PetReading.get().getTemplate(petItemObjId);
            if (petTemplate == null) {
                throw new NullPointerException();
            }
            else {
                L1PcInstance pc = (L1PcInstance)pet.getMaster();
                if (PetReading.get().isNameExists(name)) {
                    pc.sendPackets(new S_ServerMessage(327));
                    return;
                }
                else {
                    L1Npc l1npc = NpcTable.get().getTemplate(pet.getNpcId());
                    if (!pet.getName().equalsIgnoreCase(l1npc.get_name())) {
                        pc.sendPackets(new S_ServerMessage(326));
                        return;
                    }
                    else {
                        pet.setName(name);
                        petTemplate.set_name(name);
                        PetReading.get().storePet(petTemplate);
                        L1ItemInstance item = pc.getInventory().getItem(pet.getItemObjId());
                        pc.getInventory().updateItem(item);
                        pc.sendPacketsAll(new S_ChangeName(pet.getId(), name));
                        return;
                    }
                }
            }
        }
    }
    
    private void callClan(L1PcInstance pc) {
        L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getTempID());
        pc.setTempID(0);
        if (pc.isParalyzedX()) {
            return;
        }
        else if (callClanPc == null) {
            return;
        }
        else if (!pc.getMap().isEscapable() && !pc.isGm()) {
            pc.sendPackets(new S_ServerMessage(647));
            return;
        }
        else if (pc.getId() != callClanPc.getCallClanId()) {
            return;
        }
        else {
            boolean isInWarArea = false;
            int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
            if (castleId != 0) {
                isInWarArea = true;
                if (ServerWarExecutor.get().isNowWar(castleId)) {
                    isInWarArea = false;
                }
            }
            short mapId = callClanPc.getMapId();
            if ((mapId != 0 && mapId != 4 && mapId != 304) || isInWarArea) {
                pc.sendPackets(new S_ServerMessage(629));
                return;
            }
            else if (QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
                pc.sendPackets(new S_ServerMessage(629));
                return;
            }
            else {
                int[] HEADING_TABLE_X = { 0, 1, 1, 1, 0, -1, -1, -1 };
                int[] HEADING_TABLE_Y = { -1, -1, 0, 1, 1, 1, 0, -1 };
                L1Map map = callClanPc.getMap();
                int locX = callClanPc.getX();
                int locY = callClanPc.getY();
                int heading = callClanPc.getCallClanHeading();
                locX += HEADING_TABLE_X[heading];
                locY += HEADING_TABLE_Y[heading];
                heading = (heading + 4) % 4;
                boolean isExsistCharacter = false;
                for (L1Object object : World.get().getVisibleObjects(callClanPc, 1)) {
                    if (object instanceof L1Character) {
                        L1Character cha = (L1Character)object;
                        if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
                            isExsistCharacter = true;
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                }
                if ((locX == 0 && locY == 0) || !map.isPassable(locX, locY, null) || isExsistCharacter) {
                    pc.sendPackets(new S_ServerMessage(627));
                    return;
                }
                else {
                    L1Teleport.teleport(pc, locX, locY, mapId, heading, true, 3);
                    return;
                }
            }
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    private void callClan1(L1PcInstance pc) {
        L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getcallclanal());
        pc.setcallclanal(0);
        if (pc.isParalyzedX()) {
            return;
        }
        else if (callClanPc == null) {
            return;
        }
        else if (pc.isParalyzedX()) {
            return;
        }
        else {
            boolean isInWarArea = false;
            int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
            if (castleId != 0) {
                isInWarArea = true;
                if (ServerWarExecutor.get().isNowWar(castleId)) {
                    isInWarArea = false;
                }
            }
            if (isInWarArea) {
                pc.sendPackets(new S_ServerMessage(626));
                return;
            }
            else if (isInWarAreaAndWarTime(callClanPc)) {
                pc.sendPackets(new S_ServerMessage("對方旗子內,無法傳送"));
                return;
            }
            else if (!callClanPc.getMap().isClanPc()) {
                pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                return;
            }
            else {
                short mapId = callClanPc.getMapId();
                if (QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
                    pc.sendPackets(new S_ServerMessage(626));
                    return;
                }
                else {
                    pc.getInventory().consumeItem(ConfigOther.target_clan_itemid, (long)ConfigOther.target_clan_count);
                    int[] HEADING_TABLE_X = { 0, 1, 1, 1, 0, -1, -1, -1 };
                    int[] HEADING_TABLE_Y = { -1, -1, 0, 1, 1, 1, 0, -1 };
                    L1Map map = callClanPc.getMap();
                    int locX = callClanPc.getX();
                    int locY = callClanPc.getY();
                    int heading = callClanPc.getCallClanHeading();
                    locX += HEADING_TABLE_X[heading];
                    locY += HEADING_TABLE_Y[heading];
                    heading = (heading + 4) % 4;
                    L1Teleport.teleport(pc, locX, locY, mapId, heading, false);
                    return;
                }
            }
        }
    }
    
    private void callClan2(L1PcInstance pc) {
        L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getcallclana2());
        pc.setcallclana2(0);
        if (pc.isParalyzedX()) {
            return;
        }
        else if (callClanPc == null) {
            return;
        }
        else if (pc.isParalyzedX()) {
            return;
        }
        else {
            boolean isInWarArea = false;
            int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
            if (castleId != 0) {
                isInWarArea = true;
                if (ServerWarExecutor.get().isNowWar(castleId)) {
                    isInWarArea = false;
                }
            }
            if (isInWarArea) {
                pc.sendPackets(new S_ServerMessage(626));
                return;
            }
            else if (isInWarAreaAndWarTime(callClanPc)) {
                pc.sendPackets(new S_ServerMessage("對方旗子內,無法傳送"));
                return;
            }
            else if (!callClanPc.getMap().isClanPc()) {
                pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                return;
            }
            else {
                short mapId = callClanPc.getMapId();
                if (QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
                    pc.sendPackets(new S_ServerMessage(626));
                    return;
                }
                else {
                    pc.getInventory().consumeItem(ConfigOther.target_clan_itemid2, (long)ConfigOther.target_clan_count2);
                    int[] HEADING_TABLE_X = { 0, 1, 1, 1, 0, -1, -1, -1 };
                    int[] HEADING_TABLE_Y = { -1, -1, 0, 1, 1, 1, 0, -1 };
                    L1Map map = callClanPc.getMap();
                    int locX = callClanPc.getX();
                    int locY = callClanPc.getY();
                    int heading = callClanPc.getCallClanHeading();
                    locX += HEADING_TABLE_X[heading];
                    locY += HEADING_TABLE_Y[heading];
                    heading = (heading + 4) % 4;
                    L1Teleport.teleport(pc, locX, locY, mapId, heading, false);
                    return;
                }
            }
        }
    }
    
    private void callpartyall(L1PcInstance pc) {
        L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getcallclanal());
        pc.setcallclanal(0);
        if (pc.isParalyzedX()) {
            return;
        }
        else if (callClanPc == null) {
            return;
        }
        else if (pc.isParalyzedX()) {
            return;
        }
        else {
            boolean isInWarArea = false;
            int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
            if (castleId != 0) {
                isInWarArea = true;
                if (ServerWarExecutor.get().isNowWar(castleId)) {
                    isInWarArea = false;
                }
            }
            short mapId = callClanPc.getMapId();
            if (isInWarArea) {
                pc.sendPackets(new S_ServerMessage(626));
                return;
            }
            else if (QuestMapTable.get().isQuestMap((int)pc.getMapId())) {
                pc.sendPackets(new S_ServerMessage(626));
                return;
            }
            else if (isInWarAreaAndWarTime(callClanPc)) {
                pc.sendPackets(new S_ServerMessage("對方旗子內,無法傳送"));
                return;
            }
            else if (!callClanPc.getMap().isPartyPc()) {
                pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                return;
            }
            else {
                pc.getInventory().consumeItem(ConfigOther.target_party_itemid, (long)ConfigOther.target_party_count);
                int[] HEADING_TABLE_X = { 0, 1, 1, 1, 0, -1, -1, -1 };
                int[] HEADING_TABLE_Y = { -1, -1, 0, 1, 1, 1, 0, -1 };
                L1Map map = callClanPc.getMap();
                int locX = callClanPc.getX();
                int locY = callClanPc.getY();
                int heading = callClanPc.getCallClanHeading();
                locX += HEADING_TABLE_X[heading];
                locY += HEADING_TABLE_Y[heading];
                heading = (heading + 4) % 4;
                L1Teleport.teleport(pc, locX, locY, mapId, heading, false);
                return;
            }
        }
    }
    
    private static boolean isInWarAreaAndWarTime(L1PcInstance pc) {
        int castleId = L1CastleLocation.getCastleIdByArea(pc);
        return castleId != 0 && ServerWarExecutor.get().isNowWar(castleId);
    }
    
    private class KickPc implements Runnable
    {
        private ClientExecutor _client;
        
        private KickPc(L1PcInstance pc) {
            this._client = pc.getNetConnection();
        }
        
        private void start_cmd() {
            GeneralThreadPool.get().execute(this);
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(5000L);
                this._client.kick();
            }
            catch (InterruptedException e) {
                C_Attr._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
