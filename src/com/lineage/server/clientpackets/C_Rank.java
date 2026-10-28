package com.lineage.server.clientpackets;

import java.util.Collection;
import java.util.Iterator;
import com.lineage.server.model.L1Alliance;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBoxMapTimer;
import com.lineage.server.templates.L1MapsLimitTime;
import com.lineage.server.datatables.MapsGroupTable;
import com.lineage.server.serverpackets.S_SkillSound;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.utils.FaceToFace;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.add.L1PcUnlock;
import com.lineage.server.serverpackets.S_PacketBox;
import java.util.ArrayList;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class C_Rank extends ClientBasePacket
{
    private static final Log _log;
    private static Random _random;
    private int _giverank;
    
    static {
        _log = LogFactory.getLog(C_Rank.class);
        C_Rank._random = new Random();
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int data = 0;
            int rank = 0;
            String name = "";
            try {
                data = this.readC();
                rank = this.readC();
                name = this.readS();
            }
            catch (Exception e) {
                return;
            }
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                switch (data) {
                    case 1: {
                        L1PcInstance targetPc = World.get().getPlayer(name);
                        if (targetPc == null) {
                            targetPc = CharacterTable.get().restoreCharacter(name);
                            if (targetPc == null) {
                                pc.sendPackets(new S_ServerMessage(109, name));
                                return;
                            }
                        }
                        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                        if (clan == null) {
                            return;
                        }
                        else {
                            int userRank = pc.getClanRank();
                            if (userRank == 4) {
                                switch (rank) {
                                    case 7: {
                                        this._giverank = 2;
                                        break;
                                    }
                                    case 8: {
                                        this._giverank = 5;
                                        break;
                                    }
                                    case 9: {
                                        this._giverank = 6;
                                        break;
                                    }
                                }
                            }
                            else {
                                this._giverank = rank;
                            }
                            if (targetPc.getClanRank() == 4 || targetPc.getClanRank() == 10) {
                                pc.sendPackets(new S_ServerMessage(2065));
                                return;
                            }
                            else if (targetPc.getClanRank() == 3) {
                                pc.sendPackets(new S_ServerMessage(2064));
                                return;
                            }
                            else if (targetPc.getClanRank() == pc.getClanRank()) {
                                pc.sendPackets(new S_ServerMessage(2065));
                                return;
                            }
                            else if (userRank != 3 && userRank != 4 && userRank != 6 && userRank != 9 && userRank != 10) {
                                pc.sendPackets(new S_ServerMessage("守護騎士以上階級才能使用此命令。"));
                                return;
                            }
                            else if (name.equalsIgnoreCase(pc.getName())) {
                                pc.sendPackets(new S_ServerMessage(2068));
                                return;
                            }
                            else if (this._giverank == 0 || this._giverank > 10) {
                                System.out.println("階級錯誤");
                                pc.sendPackets(new S_ServerMessage(2149));
                                return;
                            }
                            else {
                                List<Integer> rankList = new ArrayList();
                                switch (userRank) {
                                    case 3: {
                                        if (pc.getLevel() < 25) {
                                            pc.sendPackets(new S_ServerMessage(2471));
                                            return;
                                        }
                                        else {
                                            rankList.add(2);
                                            rankList.add(5);
                                            rankList.add(6);
                                            break;
                                        }
                                    }
                                    case 4: {
                                        if (pc.getLevel() < 25) {
                                            pc.sendPackets(new S_ServerMessage(2471));
                                            return;
                                        }
                                        else {
                                            rankList.add(2);
                                            rankList.add(3);
                                            rankList.add(5);
                                            rankList.add(6);
                                            break;
                                        }
                                    }
                                    case 6: {
                                        if (pc.getLevel() < 40) {
                                            pc.sendPackets(new S_ServerMessage(2472));
                                            return;
                                        }
                                        else {
                                            rankList.add(2);
                                            rankList.add(5);
                                            break;
                                        }
                                    }
                                    case 9: {
                                        if (pc.getLevel() < 40) {
                                            pc.sendPackets(new S_ServerMessage(2472));
                                            return;
                                        }
                                        else {
                                            rankList.add(7);
                                            rankList.add(8);
                                            break;
                                        }
                                    }
                                    case 10: {
                                        if (pc.getLevel() < 25) {
                                            pc.sendPackets(new S_ServerMessage(2471));
                                            return;
                                        }
                                        else {
                                            rankList.add(7);
                                            rankList.add(8);
                                            rankList.add(9);
                                            break;
                                        }
                                    }
                                }
                                switch (this._giverank) {
                                    case 2: {
                                        if (!rankList.contains(2)) {
                                            pc.sendPackets(new S_ServerMessage(2065));
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    case 5: {
                                        if (!rankList.contains(5)) {
                                            pc.sendPackets(new S_ServerMessage(2065));
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    case 6: {
                                        if (targetPc.getLevel() < 40) {
                                            pc.sendPackets(new S_ServerMessage(2473));
                                            return;
                                        }
                                        else if (!rankList.contains(6)) {
                                            pc.sendPackets(new S_ServerMessage(2065));
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    case 7: {
                                        if (!rankList.contains(7)) {
                                            pc.sendPackets(new S_ServerMessage(2065));
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    case 8: {
                                        if (!rankList.contains(8)) {
                                            pc.sendPackets(new S_ServerMessage(2065));
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    case 9: {
                                        if (targetPc.getLevel() < 40) {
                                            pc.sendPackets(new S_ServerMessage(2473));
                                            return;
                                        }
                                        else if (!rankList.contains(9)) {
                                            pc.sendPackets(new S_ServerMessage(2065));
                                            return;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                }
                                if (pc.getClanid() == targetPc.getClanid()) {
                                    try {
                                        targetPc.setClanRank(this._giverank);
                                        targetPc.save();
                                        pc.sendPackets(new S_PacketBox(27, this._giverank, name));
                                        if (targetPc.getOnlineStatus() == 1) {
                                            L1PcUnlock.Pc_Unlock(targetPc);
                                            targetPc.sendPackets(new S_PacketBox(27, this._giverank, name));
                                        }
                                    }
                                    catch (Exception e2) {
                                        C_Rank._log.error(e2.getLocalizedMessage(), e2);
                                    }
                                    break;
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(201, name));
                                    return;
                                }
                            }
                        }
                    }
                    case 2: {
                        L1Clan clan2 = pc.getClan();
                        if (clan2 == null) {
                            pc.sendPackets(new S_ServerMessage(1064));
                            return;
                        }
                        else {
                            L1Alliance alliance1 = ClanAllianceReading.get().getAlliance(clan2.getClanId());
                            if (alliance1 != null) {
                                StringBuffer sbr = new StringBuffer();
                                for (L1Clan l1clan : alliance1.getTotalList()) {
                                    if (clan2.getClanId() != l1clan.getClanId()) {
                                        sbr.append(l1clan.getClanName()).append(" ");
                                    }
                                }
                                pc.sendPackets(new S_PacketBox(62, sbr.toString()));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(1233));
                                break;
                            }
                        }
                    }
                    case 3: {
                        L1Clan clan3 = pc.getClan();
                        if (clan3 == null || pc.getId() != clan3.getLeaderId()) {
                            pc.sendPackets(new S_ServerMessage(518));
                            return;
                        }
                        else if (pc.getLevel() < 25 || !pc.isCrown()) {
                            pc.sendPackets(new S_ServerMessage(1206));
                            return;
                        }
                        else if (ClanAllianceReading.get().getAlliance(clan3.getClanId()) != null) {
                            pc.sendPackets(new S_ServerMessage(1202));
                            return;
                        }
                        else {
                            for (L1War war : WorldWar.get().getWarList()) {
                                if (war.checkClanInWar(clan3.getClanName())) {
                                    pc.sendPackets(new S_ServerMessage(1234));
                                    return;
                                }
                            }
                            L1PcInstance alliancePc = FaceToFace.faceToFace(pc);
                            if (alliancePc != null) {
                                if (!alliancePc.isCrown()) {
                                    pc.sendPackets(new S_ServerMessage(92, alliancePc.getName()));
                                    return;
                                }
                                else if (alliancePc.getClanid() == 0 || alliancePc.getClan() == null) {
                                    pc.sendPackets(new S_ServerMessage(90, alliancePc.getName()));
                                    return;
                                }
                                else {
                                    alliancePc.setTempID(pc.getId());
                                    alliancePc.sendPackets(new S_Message_YN(223, pc.getName()));
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                    }
                    case 4: {
                        L1Clan clan4 = pc.getClan();
                        if (clan4 == null || pc.getId() != clan4.getLeaderId()) {
                            pc.sendPackets(new S_ServerMessage(518));
                            return;
                        }
                        else {
                            for (L1War war2 : WorldWar.get().getWarList()) {
                                if (war2.checkClanInWar(clan4.getClanName())) {
                                    pc.sendPackets(new S_ServerMessage(1203));
                                    return;
                                }
                            }
                            L1Alliance alliance2 = ClanAllianceReading.get().getAlliance(clan4.getClanId());
                            if (alliance2 != null) {
                                pc.sendPackets(new S_Message_YN(1210));
                                break;
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(1233));
                                break;
                            }
                        }
                    }
                    case 5: {
                        if (pc.get_food() >= 225) {
                            if (pc.getWeapon() != null) {
                                Random random = new Random();
                                long time = pc.get_h_time();
                                Calendar cal = Calendar.getInstance();
                                long h_time = cal.getTimeInMillis() / 1000L;
                                int n = (int)((h_time - time) / 60L);
                                int addhp = 0;
                                if (n >= 30) {
                                    int lv = pc.getWeapon().getEnchantLevel();
                                    switch (lv) {
                                        case 0:
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 4:
                                        case 5:
                                        case 6: {
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8907));
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8684));
                                            addhp = (int)((double)pc.getMaxHp() * ((double)(random.nextInt(20) + 20) / 100.0));
                                            break;
                                        }
                                        case 7:
                                        case 8: {
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8909));
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8685));
                                            addhp = (int)((double)pc.getMaxHp() * ((double)(random.nextInt(20) + 30) / 100.0));
                                            break;
                                        }
                                        case 9:
                                        case 10: {
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8910));
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8773));
                                            addhp = (int)((double)pc.getMaxHp() * ((double)(random.nextInt(10) + 50) / 100.0));
                                            break;
                                        }
                                        default: {
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8908));
                                            pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8686));
                                            addhp = (int)((double)pc.getMaxHp() * 0.7);
                                            break;
                                        }
                                    }
                                    if (addhp != 0) {
                                        pc.set_food(0);
                                        pc.sendPackets(new S_PacketBox(11, 0));
                                        pc.setCurrentHp(pc.getCurrentHp() + addhp);
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(1974));
                                    break;
                                }
                            }
                            else {
                                pc.sendPackets(new S_ServerMessage(1973));
                                break;
                            }
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(1974));
                            break;
                        }
                    }
                    case 6: {
                        if (pc.getWeapon() != null) {
                            int lv2 = pc.getWeapon().getEnchantLevel();
                            int gfx = 8684;
                            switch (lv2) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6: {
                                    gfx = 8684;
                                    break;
                                }
                                case 7:
                                case 8: {
                                    gfx = 8685;
                                    break;
                                }
                                case 9:
                                case 10: {
                                    gfx = 8773;
                                    break;
                                }
                                default: {
                                    gfx = 8686;
                                    break;
                                }
                            }
                            pc.sendPacketsAll(new S_SkillSound(pc.getId(), gfx));
                            break;
                        }
                        else {
                            pc.sendPackets(new S_ServerMessage(1973));
                            break;
                        }
                    }
                    case 8: {
                        Collection<L1MapsLimitTime> mapLimitList = (Collection)MapsGroupTable.get().getGroupMaps().values();
                        for (L1MapsLimitTime mapLimit : mapLimitList) {
                            int used_time = pc.getMapsTime(mapLimit.getOrderId());
                            int time_str = (mapLimit.getLimitTime() - used_time) / 60;
                            pc.sendPackets(new S_ServerMessage(2535, mapLimit.getMapName(), String.valueOf(time_str)));
                        }
                        break;
                    }
                    case 9: {
                        pc.sendPackets(new S_PacketBoxMapTimer(pc));
                        break;
                    }
                }
            }
        }
        catch (Exception e3) {
            C_Rank._log.error(e3.getLocalizedMessage(), e3);
        }
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
