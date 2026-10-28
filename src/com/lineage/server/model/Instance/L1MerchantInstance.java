package com.lineage.server.model.Instance;

import java.util.Iterator;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.lock.TownReading;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.L1NpcTalkData;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.datatables.NPCTalkDataTable;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_NPCPack_M;
import com.lineage.server.serverpackets.S_NPCPack_GM;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1MerchantInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1MerchantInstance.class);
    }
    
    public L1MerchantInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            perceivedFrom.addKnownObject(this);
            if (perceivedFrom.isGm()) {
                perceivedFrom.sendPackets(new S_NPCPack_GM(this));
            }
            else {
                perceivedFrom.sendPackets(new S_NPCPack_M(this));
            }
        }
        catch (Exception e) {
            L1MerchantInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onAction(L1PcInstance pc) {
        try {
            L1AttackMode attack = new L1AttackPc(pc, this);
            attack.action();
        }
        catch (Exception e) {
            L1MerchantInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        else {
            this.setActived(false);
            this.startAI();
            return;
        }
    }
    
    @Override
    public void onTalkAction(L1PcInstance player) {
        int objid = this.getId();
        L1NpcTalkData talking = NPCTalkDataTable.get().getTemplate(this.getNpcTemplate().get_npcId());
        int npcid = this.getNpcTemplate().get_npcId();
        L1PcQuest quest = player.getQuest();
        String htmlid = null;
        String[] htmldata = (String[])null;
        int pcX = player.getX();
        int pcY = player.getY();
        int npcX = this.getX();
        int npcY = this.getY();
        if (this.WORK == null && this.getNpcTemplate().getChangeHead()) {
            if (pcX == npcX && pcY < npcY) {
                this.setHeading(0);
            }
            else if (pcX > npcX && pcY < npcY) {
                this.setHeading(1);
            }
            else if (pcX > npcX && pcY == npcY) {
                this.setHeading(2);
            }
            else if (pcX > npcX && pcY > npcY) {
                this.setHeading(3);
            }
            else if (pcX == npcX && pcY > npcY) {
                this.setHeading(4);
            }
            else if (pcX < npcX && pcY > npcY) {
                this.setHeading(5);
            }
            else if (pcX < npcX && pcY == npcY) {
                this.setHeading(6);
            }
            else if (pcX < npcX && pcY < npcY) {
                this.setHeading(7);
            }
            this.broadcastPacketAll(new S_ChangeHeading(this));
        }
        if (talking != null) {
            if (npcid == 70841) {
                if (player.isElf()) {
                    htmlid = "luudielE1";
                }
                else if (player.isDarkelf()) {
                    htmlid = "luudielCE1";
                }
                else {
                    htmlid = "luudiel1";
                }
            }
            else if (npcid == 70087) {
                if (player.isDarkelf()) {
                    htmlid = "sedia";
                }
            }
            else if (npcid == 70099) {
                if (!quest.isEnd(11) && player.getLevel() > 13) {
                    htmlid = "kuper1";
                }
            }
            else if (npcid == 70796) {
                if (!quest.isEnd(11) && player.getLevel() > 13) {
                    htmlid = "dunham1";
                }
            }
            else if (npcid == 70011) {
                int time = L1GameTimeClock.getInstance().currentTime().getSeconds() % 86400;
                if (time < 21600 || time > 72000) {
                    htmlid = "shipEvI6";
                }
            }
            else if (npcid == 70553) {
                boolean hascastle = this.checkHasCastle(player, 1);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "ishmael1";
                    }
                    else {
                        htmlid = "ishmael6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "ishmael7";
                }
            }
            else if (npcid == 70822) {
                boolean hascastle = this.checkHasCastle(player, 2);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "seghem1";
                    }
                    else {
                        htmlid = "seghem6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "seghem7";
                }
            }
            else if (npcid == 70784) {
                boolean hascastle = this.checkHasCastle(player, 3);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "othmond1";
                    }
                    else {
                        htmlid = "othmond6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "othmond7";
                }
            }
            else if (npcid == 70623) {
                boolean hascastle = this.checkHasCastle(player, 4);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "orville1";
                    }
                    else {
                        htmlid = "orville6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "orville7";
                }
            }
            else if (npcid == 70880) {
                boolean hascastle = this.checkHasCastle(player, 5);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "fisher1";
                    }
                    else {
                        htmlid = "fisher6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "fisher7";
                }
            }
            else if (npcid == 70665) {
                boolean hascastle = this.checkHasCastle(player, 6);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "potempin1";
                    }
                    else {
                        htmlid = "potempin6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "potempin7";
                }
            }
            else if (npcid == 70721) {
                boolean hascastle = this.checkHasCastle(player, 7);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "timon1";
                    }
                    else {
                        htmlid = "timon6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "timon7";
                }
            }
            else if (npcid == 81155) {
                boolean hascastle = this.checkHasCastle(player, 8);
                if (hascastle) {
                    if (this.checkClanLeader(player)) {
                        htmlid = "olle1";
                    }
                    else {
                        htmlid = "olle6";
                        htmldata = new String[] { player.getName() };
                    }
                }
                else {
                    htmlid = "olle7";
                }
            }
            else if (npcid == 80057) {
                switch (player.getKarmaLevel()) {
                    case -8: {
                        htmlid = "cyk8";
                        break;
                    }
                    case -7: {
                        htmlid = "cyk7";
                        break;
                    }
                    case -6: {
                        htmlid = "cyk6";
                        break;
                    }
                    case -5: {
                        htmlid = "cyk5";
                        break;
                    }
                    case -4: {
                        htmlid = "cyk4";
                        break;
                    }
                    case -3: {
                        htmlid = "cyk3";
                        break;
                    }
                    case -2: {
                        htmlid = "cyk2";
                        break;
                    }
                    case -1: {
                        htmlid = "cyk1";
                        break;
                    }
                    case 0: {
                        htmlid = "alfons1";
                        break;
                    }
                    case 1: {
                        htmlid = "cbk1";
                        break;
                    }
                    case 2: {
                        htmlid = "cbk2";
                        break;
                    }
                    case 3: {
                        htmlid = "cbk3";
                        break;
                    }
                    case 4: {
                        htmlid = "cbk4";
                        break;
                    }
                    case 5: {
                        htmlid = "cbk5";
                        break;
                    }
                    case 6: {
                        htmlid = "cbk6";
                        break;
                    }
                    case 7: {
                        htmlid = "cbk7";
                        break;
                    }
                    case 8: {
                        htmlid = "cbk8";
                        break;
                    }
                    default: {
                        htmlid = "alfons1";
                        break;
                    }
                }
            }
            else if (npcid == 80058) {
                int level = player.getLevel();
                if (level <= 44) {
                    htmlid = "cpass03";
                }
                else if (level <= 51 && 45 <= level) {
                    htmlid = "cpass02";
                }
                else {
                    htmlid = "cpass01";
                }
            }
            else if (npcid == 80059) {
                if (player.getKarmaLevel() > 0) {
                    htmlid = "cpass03";
                }
                else if (player.getInventory().checkItem(40921)) {
                    htmlid = "wpass02";
                }
                else if (player.getInventory().checkItem(40917)) {
                    htmlid = "wpass14";
                }
                else if (player.getInventory().checkItem(40912) || player.getInventory().checkItem(40910) || player.getInventory().checkItem(40911)) {
                    htmlid = "wpass04";
                }
                else if (player.getInventory().checkItem(40909)) {
                    int count = this.getNecessarySealCount(player);
                    if (player.getInventory().checkItem(40913, (long)count)) {
                        this.createRuler(player, 1, count);
                        htmlid = "wpass06";
                    }
                    else {
                        htmlid = "wpass03";
                    }
                }
                else if (player.getInventory().checkItem(40913)) {
                    htmlid = "wpass08";
                }
                else {
                    htmlid = "wpass05";
                }
            }
            else if (npcid == 80060) {
                if (player.getKarmaLevel() > 0) {
                    htmlid = "cpass03";
                }
                else if (player.getInventory().checkItem(40921)) {
                    htmlid = "wpass02";
                }
                else if (player.getInventory().checkItem(40920)) {
                    htmlid = "wpass13";
                }
                else if (player.getInventory().checkItem(40909) || player.getInventory().checkItem(40910) || player.getInventory().checkItem(40911)) {
                    htmlid = "wpass04";
                }
                else if (player.getInventory().checkItem(40912)) {
                    int count = this.getNecessarySealCount(player);
                    if (player.getInventory().checkItem(40916, (long)count)) {
                        this.createRuler(player, 8, count);
                        htmlid = "wpass06";
                    }
                    else {
                        htmlid = "wpass03";
                    }
                }
                else if (player.getInventory().checkItem(40916)) {
                    htmlid = "wpass08";
                }
                else {
                    htmlid = "wpass05";
                }
            }
            else if (npcid == 80061) {
                if (player.getKarmaLevel() > 0) {
                    htmlid = "cpass03";
                }
                else if (player.getInventory().checkItem(40921)) {
                    htmlid = "wpass02";
                }
                else if (player.getInventory().checkItem(40918)) {
                    htmlid = "wpass11";
                }
                else if (player.getInventory().checkItem(40909) || player.getInventory().checkItem(40912) || player.getInventory().checkItem(40911)) {
                    htmlid = "wpass04";
                }
                else if (player.getInventory().checkItem(40910)) {
                    int count = this.getNecessarySealCount(player);
                    if (player.getInventory().checkItem(40914, (long)count)) {
                        this.createRuler(player, 4, count);
                        htmlid = "wpass06";
                    }
                    else {
                        htmlid = "wpass03";
                    }
                }
                else if (player.getInventory().checkItem(40914)) {
                    htmlid = "wpass08";
                }
                else {
                    htmlid = "wpass05";
                }
            }
            else if (npcid == 80062) {
                if (player.getKarmaLevel() > 0) {
                    htmlid = "cpass03";
                }
                else if (player.getInventory().checkItem(40921)) {
                    htmlid = "wpass02";
                }
                else if (player.getInventory().checkItem(40919)) {
                    htmlid = "wpass12";
                }
                else if (player.getInventory().checkItem(40909) || player.getInventory().checkItem(40912) || player.getInventory().checkItem(40910)) {
                    htmlid = "wpass04";
                }
                else if (player.getInventory().checkItem(40911)) {
                    int count = this.getNecessarySealCount(player);
                    if (player.getInventory().checkItem(40915, (long)count)) {
                        this.createRuler(player, 2, count);
                        htmlid = "wpass06";
                    }
                    else {
                        htmlid = "wpass03";
                    }
                }
                else if (player.getInventory().checkItem(40915)) {
                    htmlid = "wpass08";
                }
                else {
                    htmlid = "wpass05";
                }
            }
            else if (npcid == 80065) {
                if (player.getKarmaLevel() < 3) {
                    htmlid = "uturn0";
                }
                else {
                    htmlid = "uturn1";
                }
            }
            else if (npcid == 80047) {
                if (player.getKarmaLevel() > -3) {
                    htmlid = "uhelp1";
                }
                else {
                    htmlid = "uhelp2";
                }
            }
            else if (npcid == 80049) {
                if (player.getKarma() <= -10000000) {
                    htmlid = "betray11";
                }
                else {
                    htmlid = "betray12";
                }
            }
            else if (npcid == 80050) {
                if (player.getKarmaLevel() > -1) {
                    htmlid = "meet103";
                }
                else {
                    htmlid = "meet101";
                }
            }
            else if (npcid == 80053) {
                int karmaLevel = player.getKarmaLevel();
                if (karmaLevel == 0) {
                    htmlid = "aliceyet";
                }
                else if (karmaLevel >= 1) {
                    if (player.getInventory().checkItem(196) || player.getInventory().checkItem(197) || player.getInventory().checkItem(198) || player.getInventory().checkItem(199) || player.getInventory().checkItem(200) || player.getInventory().checkItem(201) || player.getInventory().checkItem(202) || player.getInventory().checkItem(203)) {
                        htmlid = "alice_gd";
                    }
                    else {
                        htmlid = "gd";
                    }
                }
                else if (karmaLevel <= -1) {
                    if (player.getInventory().checkItem(40991)) {
                        if (karmaLevel <= -1) {
                            htmlid = "Mate_1";
                        }
                    }
                    else if (player.getInventory().checkItem(196)) {
                        if (karmaLevel <= -2) {
                            htmlid = "Mate_2";
                        }
                        else {
                            htmlid = "alice_1";
                        }
                    }
                    else if (player.getInventory().checkItem(197)) {
                        if (karmaLevel <= -3) {
                            htmlid = "Mate_3";
                        }
                        else {
                            htmlid = "alice_2";
                        }
                    }
                    else if (player.getInventory().checkItem(198)) {
                        if (karmaLevel <= -4) {
                            htmlid = "Mate_4";
                        }
                        else {
                            htmlid = "alice_3";
                        }
                    }
                    else if (player.getInventory().checkItem(199)) {
                        if (karmaLevel <= -5) {
                            htmlid = "Mate_5";
                        }
                        else {
                            htmlid = "alice_4";
                        }
                    }
                    else if (player.getInventory().checkItem(200)) {
                        if (karmaLevel <= -6) {
                            htmlid = "Mate_6";
                        }
                        else {
                            htmlid = "alice_5";
                        }
                    }
                    else if (player.getInventory().checkItem(201)) {
                        if (karmaLevel <= -7) {
                            htmlid = "Mate_7";
                        }
                        else {
                            htmlid = "alice_6";
                        }
                    }
                    else if (player.getInventory().checkItem(202)) {
                        if (karmaLevel <= -8) {
                            htmlid = "Mate_8";
                        }
                        else {
                            htmlid = "alice_7";
                        }
                    }
                    else if (player.getInventory().checkItem(203)) {
                        htmlid = "alice_8";
                    }
                    else {
                        htmlid = "alice_no";
                    }
                }
            }
            else if (npcid == 80055) {
                int amuletLevel = 0;
                if (player.getInventory().checkItem(20358)) {
                    amuletLevel = 1;
                }
                else if (player.getInventory().checkItem(20359)) {
                    amuletLevel = 2;
                }
                else if (player.getInventory().checkItem(20360)) {
                    amuletLevel = 3;
                }
                else if (player.getInventory().checkItem(20361)) {
                    amuletLevel = 4;
                }
                else if (player.getInventory().checkItem(20362)) {
                    amuletLevel = 5;
                }
                else if (player.getInventory().checkItem(20363)) {
                    amuletLevel = 6;
                }
                else if (player.getInventory().checkItem(20364)) {
                    amuletLevel = 7;
                }
                else if (player.getInventory().checkItem(20365)) {
                    amuletLevel = 8;
                }
                if (player.getKarmaLevel() == -1) {
                    if (amuletLevel >= 1) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet1";
                    }
                }
                else if (player.getKarmaLevel() == -2) {
                    if (amuletLevel >= 2) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet2";
                    }
                }
                else if (player.getKarmaLevel() == -3) {
                    if (amuletLevel >= 3) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet3";
                    }
                }
                else if (player.getKarmaLevel() == -4) {
                    if (amuletLevel >= 4) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet4";
                    }
                }
                else if (player.getKarmaLevel() == -5) {
                    if (amuletLevel >= 5) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet5";
                    }
                }
                else if (player.getKarmaLevel() == -6) {
                    if (amuletLevel >= 6) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet6";
                    }
                }
                else if (player.getKarmaLevel() == -7) {
                    if (amuletLevel >= 7) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet7";
                    }
                }
                else if (player.getKarmaLevel() == -8) {
                    if (amuletLevel >= 8) {
                        htmlid = "uamuletd";
                    }
                    else {
                        htmlid = "uamulet8";
                    }
                }
                else {
                    htmlid = "uamulet0";
                }
            }
            else if (npcid == 80056) {
                if (player.getKarma() <= -10000000) {
                    htmlid = "infamous11";
                }
                else {
                    htmlid = "infamous12";
                }
            }
            else if (npcid == 80064) {
                if (player.getKarmaLevel() < 1) {
                    htmlid = "meet003";
                }
                else {
                    htmlid = "meet001";
                }
            }
            else if (npcid == 80066) {
                if (player.getKarma() >= 10000000) {
                    htmlid = "betray01";
                }
                else {
                    htmlid = "betray02";
                }
            }
            else if (npcid == 80071) {
                int earringLevel = 0;
                if (player.getInventory().checkItem(21020)) {
                    earringLevel = 1;
                }
                else if (player.getInventory().checkItem(21021)) {
                    earringLevel = 2;
                }
                else if (player.getInventory().checkItem(21022)) {
                    earringLevel = 3;
                }
                else if (player.getInventory().checkItem(21023)) {
                    earringLevel = 4;
                }
                else if (player.getInventory().checkItem(21024)) {
                    earringLevel = 5;
                }
                else if (player.getInventory().checkItem(21025)) {
                    earringLevel = 6;
                }
                else if (player.getInventory().checkItem(21026)) {
                    earringLevel = 7;
                }
                else if (player.getInventory().checkItem(21027)) {
                    earringLevel = 8;
                }
                if (player.getKarmaLevel() == 1) {
                    if (earringLevel >= 1) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring1";
                    }
                }
                else if (player.getKarmaLevel() == 2) {
                    if (earringLevel >= 2) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring2";
                    }
                }
                else if (player.getKarmaLevel() == 3) {
                    if (earringLevel >= 3) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring3";
                    }
                }
                else if (player.getKarmaLevel() == 4) {
                    if (earringLevel >= 4) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring4";
                    }
                }
                else if (player.getKarmaLevel() == 5) {
                    if (earringLevel >= 5) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring5";
                    }
                }
                else if (player.getKarmaLevel() == 6) {
                    if (earringLevel >= 6) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring6";
                    }
                }
                else if (player.getKarmaLevel() == 7) {
                    if (earringLevel >= 7) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring7";
                    }
                }
                else if (player.getKarmaLevel() == 8) {
                    if (earringLevel >= 8) {
                        htmlid = "lringd";
                    }
                    else {
                        htmlid = "lring8";
                    }
                }
                else {
                    htmlid = "lring0";
                }
            }
            else if (npcid == 80072) {
                int karmaLevel = player.getKarmaLevel();
                if (karmaLevel == 1) {
                    htmlid = "lsmith0";
                }
                else if (karmaLevel == 2) {
                    htmlid = "lsmith1";
                }
                else if (karmaLevel == 3) {
                    htmlid = "lsmith2";
                }
                else if (karmaLevel == 4) {
                    htmlid = "lsmith3";
                }
                else if (karmaLevel == 5) {
                    htmlid = "lsmith4";
                }
                else if (karmaLevel == 6) {
                    htmlid = "lsmith5";
                }
                else if (karmaLevel == 7) {
                    htmlid = "lsmith7";
                }
                else if (karmaLevel == 8) {
                    htmlid = "lsmith8";
                }
                else {
                    htmlid = "";
                }
            }
            else if (npcid == 80074) {
                if (player.getKarma() >= 10000000) {
                    htmlid = "infamous01";
                }
                else {
                    htmlid = "infamous02";
                }
            }
            else if (npcid == 80104) {
                if (!player.isCrown()) {
                    htmlid = "horseseller4";
                }
            }
            else if (npcid == 70528) {
                htmlid = talkToTownmaster(player, 1);
            }
            else if (npcid == 70546) {
                htmlid = talkToTownmaster(player, 6);
            }
            else if (npcid == 70567) {
                htmlid = talkToTownmaster(player, 3);
            }
            else if (npcid == 70815) {
                htmlid = talkToTownmaster(player, 4);
            }
            else if (npcid == 70774) {
                htmlid = talkToTownmaster(player, 5);
            }
            else if (npcid == 70799) {
                htmlid = talkToTownmaster(player, 2);
            }
            else if (npcid == 70594) {
                htmlid = talkToTownmaster(player, 7);
            }
            else if (npcid == 70860) {
                htmlid = talkToTownmaster(player, 8);
            }
            else if (npcid == 70654) {
                htmlid = talkToTownmaster(player, 9);
            }
            else if (npcid == 70748) {
                htmlid = talkToTownmaster(player, 10);
            }
            else if (npcid == 70534) {
                htmlid = talkToTownadviser(player, 1);
            }
            else if (npcid == 70556) {
                htmlid = talkToTownadviser(player, 6);
            }
            else if (npcid == 70572) {
                htmlid = talkToTownadviser(player, 3);
            }
            else if (npcid == 70830) {
                htmlid = talkToTownadviser(player, 4);
            }
            else if (npcid == 70788) {
                htmlid = talkToTownadviser(player, 5);
            }
            else if (npcid == 70806) {
                htmlid = talkToTownadviser(player, 2);
            }
            else if (npcid == 70631) {
                htmlid = talkToTownadviser(player, 7);
            }
            else if (npcid == 70876) {
                htmlid = talkToTownadviser(player, 8);
            }
            else if (npcid == 70663) {
                htmlid = talkToTownadviser(player, 9);
            }
            else if (npcid == 70761) {
                htmlid = talkToTownadviser(player, 10);
            }
            else if (npcid == 70506) {
                htmlid = this.talkToRuba(player);
            }
            else if (npcid == 71026) {
                if (player.getLevel() < 10) {
                    htmlid = "en0113";
                }
                else if (player.getLevel() >= 10 && player.getLevel() < 25) {
                    htmlid = "en0111";
                }
                else if (player.getLevel() > 25) {
                    htmlid = "en0112";
                }
            }
            else if (npcid == 71027) {
                if (player.getLevel() < 10) {
                    htmlid = "en0283";
                }
                else if (player.getLevel() >= 10 && player.getLevel() < 25) {
                    htmlid = "en0281";
                }
                else if (player.getLevel() > 25) {
                    htmlid = "en0282";
                }
            }
            else if (npcid == 70512) {
                if (player.getLevel() >= 25) {
                    htmlid = "jpe0102";
                }
            }
            else if (npcid == 70514) {
                if (player.getLevel() >= 25) {
                    htmlid = "jpe0092";
                }
            }
            else if (npcid == 71038) {
                if (player.getInventory().checkItem(41060)) {
                    if (player.getInventory().checkItem(41090) || player.getInventory().checkItem(41091) || player.getInventory().checkItem(41092)) {
                        htmlid = "orcfnoname7";
                    }
                    else {
                        htmlid = "orcfnoname8";
                    }
                }
                else {
                    htmlid = "orcfnoname1";
                }
            }
            else if (npcid == 71040) {
                if (player.getInventory().checkItem(41060)) {
                    if (player.getInventory().checkItem(41065)) {
                        if (player.getInventory().checkItem(41086) || player.getInventory().checkItem(41087) || player.getInventory().checkItem(41088) || player.getInventory().checkItem(41089)) {
                            htmlid = "orcfnoa6";
                        }
                        else {
                            htmlid = "orcfnoa5";
                        }
                    }
                    else {
                        htmlid = "orcfnoa2";
                    }
                }
                else {
                    htmlid = "orcfnoa1";
                }
            }
            else if (npcid == 71041) {
                if (player.getInventory().checkItem(41060)) {
                    if (player.getInventory().checkItem(41064)) {
                        if (player.getInventory().checkItem(41081) || player.getInventory().checkItem(41082) || player.getInventory().checkItem(41083) || player.getInventory().checkItem(41084) || player.getInventory().checkItem(41085)) {
                            htmlid = "orcfhuwoomo2";
                        }
                        else {
                            htmlid = "orcfhuwoomo8";
                        }
                    }
                    else {
                        htmlid = "orcfhuwoomo1";
                    }
                }
                else {
                    htmlid = "orcfhuwoomo5";
                }
            }
            else if (npcid == 71042) {
                if (player.getInventory().checkItem(41060)) {
                    if (player.getInventory().checkItem(41062)) {
                        if (player.getInventory().checkItem(41071) || player.getInventory().checkItem(41072) || player.getInventory().checkItem(41073) || player.getInventory().checkItem(41074) || player.getInventory().checkItem(41075)) {
                            htmlid = "orcfbakumo2";
                        }
                        else {
                            htmlid = "orcfbakumo8";
                        }
                    }
                    else {
                        htmlid = "orcfbakumo1";
                    }
                }
                else {
                    htmlid = "orcfbakumo5";
                }
            }
            else if (npcid == 71043) {
                if (player.getInventory().checkItem(41060)) {
                    if (player.getInventory().checkItem(41063)) {
                        if (player.getInventory().checkItem(41076) || player.getInventory().checkItem(41077) || player.getInventory().checkItem(41078) || player.getInventory().checkItem(41079) || player.getInventory().checkItem(41080)) {
                            htmlid = "orcfbuka2";
                        }
                        else {
                            htmlid = "orcfbuka8";
                        }
                    }
                    else {
                        htmlid = "orcfbuka1";
                    }
                }
                else {
                    htmlid = "orcfbuka5";
                }
            }
            else if (npcid == 71044) {
                if (player.getInventory().checkItem(41060)) {
                    if (player.getInventory().checkItem(41061)) {
                        if (player.getInventory().checkItem(41066) || player.getInventory().checkItem(41067) || player.getInventory().checkItem(41068) || player.getInventory().checkItem(41069) || player.getInventory().checkItem(41070)) {
                            htmlid = "orcfkame2";
                        }
                        else {
                            htmlid = "orcfkame8";
                        }
                    }
                    else {
                        htmlid = "orcfkame1";
                    }
                }
                else {
                    htmlid = "orcfkame5";
                }
            }
            else if (npcid == 71055) {
                if (player.getQuest().get_step(30) == 3) {
                    htmlid = "lukein13";
                }
                else if (player.getQuest().get_step(23) == 255 && player.getQuest().get_step(30) == 2 && player.getInventory().checkItem(40631)) {
                    htmlid = "lukein10";
                }
                else if (player.getQuest().get_step(23) == 255) {
                    htmlid = "lukein0";
                }
                else if (player.getQuest().get_step(23) == 11) {
                    if (player.getInventory().checkItem(40716)) {
                        htmlid = "lukein9";
                    }
                }
                else if (player.getQuest().get_step(23) >= 1 && player.getQuest().get_step(23) <= 10) {
                    htmlid = "lukein8";
                }
            }
            else if (npcid == 71063) {
                if (player.getQuest().get_step(24) != 255 && player.getQuest().get_step(23) == 1) {
                    htmlid = "maptbox";
                }
            }
            else if (npcid == 71064) {
                if (player.getQuest().get_step(23) == 2) {
                    htmlid = this.talkToSecondtbox(player);
                }
            }
            else if (npcid == 71065) {
                if (player.getQuest().get_step(23) == 3) {
                    htmlid = this.talkToSecondtbox(player);
                }
            }
            else if (npcid == 71066) {
                if (player.getQuest().get_step(23) == 4) {
                    htmlid = this.talkToSecondtbox(player);
                }
            }
            else if (npcid == 71067) {
                if (player.getQuest().get_step(23) == 5) {
                    htmlid = this.talkToThirdtbox(player);
                }
            }
            else if (npcid == 71068) {
                if (player.getQuest().get_step(23) == 6) {
                    htmlid = this.talkToThirdtbox(player);
                }
            }
            else if (npcid == 71069) {
                if (player.getQuest().get_step(23) == 7) {
                    htmlid = this.talkToThirdtbox(player);
                }
            }
            else if (npcid == 71070) {
                if (player.getQuest().get_step(23) == 8) {
                    htmlid = this.talkToThirdtbox(player);
                }
            }
            else if (npcid == 71071) {
                if (player.getQuest().get_step(23) == 9) {
                    htmlid = this.talkToThirdtbox(player);
                }
            }
            else if (npcid == 71072) {
                if (player.getQuest().get_step(23) == 10) {
                    htmlid = this.talkToThirdtbox(player);
                }
            }
            else if (npcid == 71056) {
                if (player.getQuest().get_step(30) == 4) {
                    if (player.getInventory().checkItem(40631)) {
                        htmlid = "SIMIZZ11";
                    }
                    else {
                        htmlid = "SIMIZZ0";
                    }
                }
                else if (player.getQuest().get_step(27) == 2) {
                    htmlid = "SIMIZZ0";
                }
                else if (player.getQuest().get_step(27) == 255) {
                    htmlid = "SIMIZZ15";
                }
                else if (player.getQuest().get_step(27) == 1) {
                    htmlid = "SIMIZZ6";
                }
            }
            else if (npcid == 71057) {
                if (player.getQuest().get_step(28) == 255) {
                    htmlid = "doil4b";
                }
            }
            else if (npcid == 71059) {
                if (player.getQuest().get_step(29) == 255) {
                    htmlid = "rudian1c";
                }
                else if (player.getQuest().get_step(29) == 1) {
                    htmlid = "rudian7";
                }
                else if (player.getQuest().get_step(28) == 255) {
                    htmlid = "rudian1b";
                }
                else {
                    htmlid = "rudian1a";
                }
            }
            else if (npcid == 71060) {
                if (player.getQuest().get_step(30) == 255) {
                    htmlid = "resta1e";
                }
                else if (player.getQuest().get_step(27) == 255) {
                    htmlid = "resta14";
                }
                else if (player.getQuest().get_step(30) == 4) {
                    htmlid = "resta13";
                }
                else if (player.getQuest().get_step(30) == 3) {
                    htmlid = "resta11";
                    player.getQuest().set_step(30, 4);
                }
                else if (player.getQuest().get_step(30) == 2) {
                    htmlid = "resta16";
                }
                else if ((player.getQuest().get_step(27) == 2 && player.getQuest().get_step(31) == 1) || player.getInventory().checkItem(40647)) {
                    htmlid = "resta1a";
                }
                else if (player.getQuest().get_step(31) == 1 || player.getInventory().checkItem(40647)) {
                    htmlid = "resta1c";
                }
                else if (player.getQuest().get_step(27) == 2) {
                    htmlid = "resta1b";
                }
            }
            else if (npcid == 71061) {
                if (player.getQuest().get_step(31) == 255) {
                    htmlid = "cadmus1c";
                }
                else if (player.getQuest().get_step(31) == 3) {
                    htmlid = "cadmus8";
                }
                else if (player.getQuest().get_step(31) == 2) {
                    htmlid = "cadmus1a";
                }
                else if (player.getQuest().get_step(28) == 255) {
                    htmlid = "cadmus1b";
                }
            }
            else if (npcid == 71036) {
                if (player.getQuest().get_step(32) == 255) {
                    htmlid = "kamyla26";
                }
                else if (player.getQuest().get_step(32) == 4 && player.getInventory().checkItem(40717)) {
                    htmlid = "kamyla15";
                }
                else if (player.getQuest().get_step(32) == 4) {
                    htmlid = "kamyla14";
                }
                else if (player.getQuest().get_step(32) == 3 && player.getInventory().checkItem(40630)) {
                    htmlid = "kamyla12";
                }
                else if (player.getQuest().get_step(32) == 3) {
                    htmlid = "kamyla11";
                }
                else if (player.getQuest().get_step(32) == 2 && player.getInventory().checkItem(40644)) {
                    htmlid = "kamyla9";
                }
                else if (player.getQuest().get_step(32) == 1) {
                    htmlid = "kamyla8";
                }
                else if (player.getQuest().get_step(31) == 255 && player.getInventory().checkItem(40621)) {
                    htmlid = "kamyla1";
                }
            }
            else if (npcid == 71089) {
                if (player.getQuest().get_step(32) == 2) {
                    htmlid = "francu12";
                }
            }
            else if (npcid == 71090) {
                if (player.getQuest().get_step(33) == 1 && player.getInventory().checkItem(40620)) {
                    htmlid = "jcrystal2";
                }
                else if (player.getQuest().get_step(33) == 1) {
                    htmlid = "jcrystal3";
                }
            }
            else if (npcid == 71091) {
                if (player.getQuest().get_step(33) == 2 && player.getInventory().checkItem(40654)) {
                    htmlid = "jcrystall2";
                }
            }
            else if (npcid == 71074) {
                if (player.getQuest().get_step(34) == 255) {
                    htmlid = "lelder0";
                }
                else if (player.getQuest().get_step(34) == 3 && player.getInventory().checkItem(40634)) {
                    htmlid = "lelder12";
                }
                else if (player.getQuest().get_step(34) == 3) {
                    htmlid = "lelder11";
                }
                else if (player.getQuest().get_step(34) == 2 && player.getInventory().checkItem(40633)) {
                    htmlid = "lelder7";
                }
                else if (player.getQuest().get_step(34) == 2) {
                    htmlid = "lelder7b";
                }
                else if (player.getQuest().get_step(34) == 1) {
                    htmlid = "lelder7b";
                }
                else if (player.getLevel() >= 40) {
                    htmlid = "lelder1";
                }
            }
            else if (npcid == 71076) {
                if (player.getQuest().get_step(34) == 255) {
                    htmlid = "ylizardb";
                }
            }
            else if (npcid == 80079) {
                if (player.getQuest().get_step(35) == 255 && !player.getInventory().checkItem(41312)) {
                    htmlid = "keplisha6";
                }
                else if (player.getInventory().checkItem(41314)) {
                    htmlid = "keplisha3";
                }
                else if (player.getInventory().checkItem(41313)) {
                    htmlid = "keplisha2";
                }
                else if (player.getInventory().checkItem(41312)) {
                    htmlid = "keplisha4";
                }
            }
            else if (npcid == 80102) {
                if (player.getInventory().checkItem(41329)) {
                    htmlid = "fillis3";
                }
            }
            else if (npcid == 71167) {
                if (player.getTempCharGfx() == 3887) {
                    htmlid = "frim1";
                }
            }
            else if (npcid == 71141) {
                if (player.getTempCharGfx() == 3887) {
                    htmlid = "moumthree1";
                }
            }
            else if (npcid == 71142) {
                if (player.getTempCharGfx() == 3887) {
                    htmlid = "moumtwo1";
                }
            }
            else if (npcid == 71145) {
                if (player.getTempCharGfx() == 3887) {
                    htmlid = "moumone1";
                }
            }
            else if (npcid == 81200) {
                if (player.getInventory().checkItem(21069) || player.getInventory().checkItem(21074)) {
                    htmlid = "c_belt";
                }
            }
            else if (npcid == 80076) {
                if (player.getInventory().checkItem(41058)) {
                    htmlid = "voyager8";
                }
                else if (player.getInventory().checkItem(49082) || player.getInventory().checkItem(49083)) {
                    if (player.getInventory().checkItem(41038) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039) || player.getInventory().checkItem(41039)) {
                        htmlid = "voyager9";
                    }
                    else {
                        htmlid = "voyager7";
                    }
                }
                else if (player.getInventory().checkItem(49082) || player.getInventory().checkItem(49083) || player.getInventory().checkItem(49084) || player.getInventory().checkItem(49085) || player.getInventory().checkItem(49086) || player.getInventory().checkItem(49087) || player.getInventory().checkItem(49088) || player.getInventory().checkItem(49089) || player.getInventory().checkItem(49090) || player.getInventory().checkItem(49091)) {
                    htmlid = "voyager7";
                }
            }
            else if (npcid == 80048) {
                int level = player.getLevel();
                if (level <= 44) {
                    htmlid = "entgate3";
                }
                else if (level >= 45 && level <= 51) {
                    htmlid = "entgate2";
                }
                else {
                    htmlid = "entgate";
                }
            }
            else if (npcid == 71168) {
                if (player.getInventory().checkItem(41028)) {
                    htmlid = "dantes1";
                }
            }
            else if (npcid == 80067) {
                if (player.getQuest().get_step(36) == 255) {
                    htmlid = "minicod10";
                }
                else if (player.getKarmaLevel() >= 1) {
                    htmlid = "minicod07";
                }
                else if (player.getQuest().get_step(36) == 1 && player.getTempCharGfx() == 6034) {
                    htmlid = "minicod03";
                }
                else if (player.getQuest().get_step(36) == 1 && player.getTempCharGfx() != 6034) {
                    htmlid = "minicod05";
                }
                else if (player.getQuest().get_step(37) == 255 || player.getInventory().checkItem(41121) || player.getInventory().checkItem(41122)) {
                    htmlid = "minicod01";
                }
                else if (player.getInventory().checkItem(41130) && player.getInventory().checkItem(41131)) {
                    htmlid = "minicod06";
                }
                else if (player.getInventory().checkItem(41130)) {
                    htmlid = "minicod02";
                }
            }
            else if (npcid == 81202) {
                if (player.getQuest().get_step(37) == 255) {
                    htmlid = "minitos10";
                }
                else if (player.getKarmaLevel() <= -1) {
                    htmlid = "minitos07";
                }
                else if (player.getQuest().get_step(37) == 1 && player.getTempCharGfx() == 6035) {
                    htmlid = "minitos03";
                }
                else if (player.getQuest().get_step(37) == 1 && player.getTempCharGfx() != 6035) {
                    htmlid = "minitos05";
                }
                else if (player.getQuest().get_step(36) == 255 || player.getInventory().checkItem(41130) || player.getInventory().checkItem(41131)) {
                    htmlid = "minitos01";
                }
                else if (player.getInventory().checkItem(41121) && player.getInventory().checkItem(41122)) {
                    htmlid = "minitos06";
                }
                else if (player.getInventory().checkItem(41121)) {
                    htmlid = "minitos02";
                }
            }
            else if (npcid == 81208) {
                if (player.getInventory().checkItem(41129) || player.getInventory().checkItem(41138)) {
                    htmlid = "minibrob04";
                }
                else if ((player.getInventory().checkItem(41126) && player.getInventory().checkItem(41127) && player.getInventory().checkItem(41128)) || (player.getInventory().checkItem(41135) && player.getInventory().checkItem(41136) && player.getInventory().checkItem(41137))) {
                    htmlid = "minibrob02";
                }
            }
            else if (npcid == 50113) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orena14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orena0";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orena2";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orena3";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orena4";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orena5";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orena6";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orena7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orena8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orena9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orena10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orena11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orena12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orena13";
                }
            }
            else if (npcid == 50112) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenb14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenb0";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenb2";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenb3";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenb4";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenb5";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenb6";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenb7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenb8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenb9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenb10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenb11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenb12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenb13";
                }
            }
            else if (npcid == 50111) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenc14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenc1";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenc0";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenc3";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenc4";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenc5";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenc6";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenc7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenc8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenc9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenc10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenc11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenc12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenc13";
                }
            }
            else if (npcid == 50116) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orend14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orend3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orend1";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orend0";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orend4";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orend5";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orend6";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orend7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orend8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orend9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orend10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orend11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orend12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orend13";
                }
            }
            else if (npcid == 50117) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orene14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orene3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orene4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orene1";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orene0";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orene5";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orene6";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orene7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orene8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orene9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orene10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orene11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orene12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orene13";
                }
            }
            else if (npcid == 50119) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenf14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenf3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenf4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenf5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenf1";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenf0";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenf6";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenf7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenf8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenf9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenf10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenf11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenf12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenf13";
                }
            }
            else if (npcid == 50121) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "oreng14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "oreng3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "oreng4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "oreng5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "oreng6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "oreng1";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "oreng0";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "oreng7";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "oreng8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "oreng9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "oreng10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "oreng11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "oreng12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "oreng13";
                }
            }
            else if (npcid == 50114) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenh14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenh3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenh4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenh5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenh6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenh7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenh1";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenh0";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenh8";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenh9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenh10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenh11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenh12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenh13";
                }
            }
            else if (npcid == 50120) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "oreni14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "oreni3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "oreni4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "oreni5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "oreni6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "oreni7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "oreni8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "oreni1";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "oreni0";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "oreni9";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "oreni10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "oreni11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "oreni12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "oreni13";
                }
            }
            else if (npcid == 50122) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenj14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenj3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenj4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenj5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenj6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenj7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenj8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenj9";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenj1";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenj0";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenj10";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenj11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenj12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenj13";
                }
            }
            else if (npcid == 50123) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenk14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenk3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenk4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenk5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenk6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenk7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenk8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenk9";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenk10";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenk1";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenk0";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenk11";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenk12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenk13";
                }
            }
            else if (npcid == 50125) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenl14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenl3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenl4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenl5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenl6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenl7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenl8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenl9";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenl10";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenl11";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenl1";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenl0";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenl12";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenl13";
                }
            }
            else if (npcid == 50124) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenm14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenm3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenm4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenm5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenm6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenm7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenm8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenm9";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenm10";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenm11";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenm12";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenm1";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenm0";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenm13";
                }
            }
            else if (npcid == 50126) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "orenn14";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "orenn3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "orenn4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "orenn5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "orenn6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "orenn7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "orenn8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "orenn9";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "orenn10";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "orenn11";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "orenn12";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "orenn13";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "orenn1";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "orenn0";
                }
            }
            else if (npcid == 50115) {
                if (player.getQuest().get_step(39) == 255) {
                    htmlid = "oreno0";
                }
                else if (player.getQuest().get_step(39) == 1) {
                    htmlid = "oreno3";
                }
                else if (player.getQuest().get_step(39) == 2) {
                    htmlid = "oreno4";
                }
                else if (player.getQuest().get_step(39) == 3) {
                    htmlid = "oreno5";
                }
                else if (player.getQuest().get_step(39) == 4) {
                    htmlid = "oreno6";
                }
                else if (player.getQuest().get_step(39) == 5) {
                    htmlid = "oreno7";
                }
                else if (player.getQuest().get_step(39) == 6) {
                    htmlid = "oreno8";
                }
                else if (player.getQuest().get_step(39) == 7) {
                    htmlid = "oreno9";
                }
                else if (player.getQuest().get_step(39) == 8) {
                    htmlid = "oreno10";
                }
                else if (player.getQuest().get_step(39) == 9) {
                    htmlid = "oreno11";
                }
                else if (player.getQuest().get_step(39) == 10) {
                    htmlid = "oreno12";
                }
                else if (player.getQuest().get_step(39) == 11) {
                    htmlid = "oreno13";
                }
                else if (player.getQuest().get_step(39) == 12) {
                    htmlid = "oreno14";
                }
                else if (player.getQuest().get_step(39) == 13) {
                    htmlid = "oreno1";
                }
            }
            else if (npcid == 70838) {
                if (player.isCrown() || player.isKnight() || player.isWizard() || player.isDragonKnight() || player.isIllusionist()) {
                    htmlid = "nerupam1";
                }
                else if (player.isDarkelf() && player.getLawful() <= -1) {
                    htmlid = "nerupaM2";
                }
                else if (player.isDarkelf()) {
                    htmlid = "nerupace1";
                }
                else if (player.isElf()) {
                    htmlid = "nerupae1";
                }
            }
            else if (npcid == 80099) {
                if (player.getQuest().get_step(41) == 1) {
                    if (player.getInventory().checkItem(41325, 1L)) {
                        htmlid = "rarson8";
                    }
                    else {
                        htmlid = "rarson10";
                    }
                }
                else if (player.getQuest().get_step(41) == 2) {
                    if (player.getInventory().checkItem(41317, 1L) && player.getInventory().checkItem(41315, 1L)) {
                        htmlid = "rarson13";
                    }
                    else {
                        htmlid = "rarson19";
                    }
                }
                else if (player.getQuest().get_step(41) == 3) {
                    htmlid = "rarson14";
                }
                else if (player.getQuest().get_step(41) == 4) {
                    if (!player.getInventory().checkItem(41326, 1L)) {
                        htmlid = "rarson18";
                    }
                    else if (player.getInventory().checkItem(41326, 1L)) {
                        htmlid = "rarson11";
                    }
                    else {
                        htmlid = "rarson17";
                    }
                }
                else if (player.getQuest().get_step(41) >= 5) {
                    htmlid = "rarson1";
                }
            }
            else if (npcid == 80101) {
                if (player.getQuest().get_step(41) == 4) {
                    if (player.getInventory().checkItem(41315, 1L) && player.getInventory().checkItem(40494, 30L) && player.getInventory().checkItem(41317, 1L)) {
                        htmlid = "kuen4";
                    }
                    else if (player.getInventory().checkItem(41316, 1L)) {
                        htmlid = "kuen1";
                    }
                    else if (!player.getInventory().checkItem(41316)) {
                        player.getQuest().set_step(41, 1);
                    }
                }
                else {
                    if (player.getQuest().get_step(41) == 2 && player.getInventory().checkItem(41317, 1L)) {
                        htmlid = "kuen3";
                    }
                    htmlid = "kuen1";
                }
            }
            else if (npcid == 81255) {
                int quest_step = quest.get_step(300);
                int playerLv = player.getLevel();
                if (playerLv < 13) {
                    this.newUserHelp(player, 1);
                }
                if (playerLv < 2 && quest_step == 0) {
                    player.addExp(125L);
                    quest.set_step(300, 1);
                    htmlid = "";
                }
                else if (playerLv > 1 && playerLv < 13 && quest_step == 0) {
                    quest.set_step(300, 1);
                    htmlid = "";
                }
                else if (playerLv < 13 && player.isDarkelf()) {
                    htmlid = "tutord";
                }
                else if (playerLv < 13 && player.isDragonKnight()) {
                    htmlid = "tutordk";
                }
                else if (playerLv < 13 && player.isElf()) {
                    htmlid = "tutore";
                }
                else if (playerLv < 13 && player.isIllusionist()) {
                    htmlid = "tutori";
                }
                else if (playerLv < 13 && player.isKnight()) {
                    htmlid = "tutork";
                }
                else if (playerLv < 13 && player.isWizard()) {
                    htmlid = "tutorm";
                }
                else if (playerLv < 13 && player.isCrown()) {
                    htmlid = "tutorp";
                }
                else {
                    htmlid = "tutorend";
                }
            }
            else if (npcid == 81256) {
                int quest_step = quest.get_step(304);
                int playerLv = player.getLevel();
                if (playerLv < 13 && quest_step >= 2) {
                    this.newUserHelp(player, 2);
                    htmlid = "admin1";
                }
                else if (playerLv < 13 && quest_step == 0) {
                    player.addExp(ExpTable.getNeedExpNextLevel(playerLv));
                    this.newUserHelp(player, 3);
                    quest.set_step(304, 1);
                    htmlid = "admin2";
                }
                else if (playerLv < 13 && quest_step == 1) {
                    player.addExp(ExpTable.getNeedExpNextLevel(playerLv));
                    this.newUserHelp(player, 2);
                    quest.set_step(304, 2);
                    htmlid = "admin3";
                }
            }
            else if (npcid == 81257) {
                int playerLv2 = player.getLevel();
                if (playerLv2 < 13) {
                    htmlid = "lowlvS1";
                }
                else if (playerLv2 > 12 && playerLv2 < 47) {
                    htmlid = "lowlvS2";
                }
                else {
                    htmlid = "lowlvno";
                }
            }
            else if (npcid == 70936) {
                if (player.getLevel() >= 52) {
                    htmlid = "dsecret2";
                }
                else {
                    htmlid = "dsecret3";
                }
            }
            else if (npcid == 81445 && player.getQuest().isEnd(79) && player.getQuest().isEnd(80) && player.getQuest().isEnd(81)) {
                htmlid = "slot5";
            }
            if (htmlid != null) {
                if (htmldata != null) {
                    player.sendPackets(new S_NPCTalkReturn(objid, htmlid, htmldata));
                }
                else {
                    player.sendPackets(new S_NPCTalkReturn(objid, htmlid));
                }
            }
            else if (player.getLawful() < -1000) {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 2));
            }
            else {
                player.sendPackets(new S_NPCTalkReturn(talking, objid, 1));
            }
            this.set_stop_time(10);
            this.setRest(true);
        }
    }
    
    private static String talkToTownadviser(L1PcInstance pc, int town_id) {
        String htmlid;
        if (pc.getHomeTownId() == town_id && TownReading.get().isLeader(pc, town_id)) {
            htmlid = "secretary1";
        }
        else {
            htmlid = "secretary2";
        }
        return htmlid;
    }
    
    private static String talkToTownmaster(L1PcInstance pc, int town_id) {
        String htmlid;
        if (pc.getHomeTownId() == town_id) {
            htmlid = "hometown";
        }
        else {
            htmlid = "othertown";
        }
        return htmlid;
    }
    
    @Override
    public void onFinalAction(L1PcInstance player, String action) {
    }
    
    public void doFinalAction(L1PcInstance player) {
    }
    
    private boolean checkHasCastle(L1PcInstance player, int castle_id) {
        if (player.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(player.getClanname());
            if (clan != null && clan.getCastleId() == castle_id) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkClanLeader(L1PcInstance player) {
        if (player.isCrown()) {
            L1Clan clan = WorldClan.get().getClan(player.getClanname());
            if (clan != null && player.getId() == clan.getLeaderId()) {
                return true;
            }
        }
        return false;
    }
    
    private int getNecessarySealCount(L1PcInstance pc) {
        int rulerCount = 0;
        int necessarySealCount = 10;
        if (pc.getInventory().checkItem(40917)) {
            ++rulerCount;
        }
        if (pc.getInventory().checkItem(40920)) {
            ++rulerCount;
        }
        if (pc.getInventory().checkItem(40918)) {
            ++rulerCount;
        }
        if (pc.getInventory().checkItem(40919)) {
            ++rulerCount;
        }
        if (rulerCount == 0) {
            necessarySealCount = 10;
        }
        else if (rulerCount == 1) {
            necessarySealCount = 100;
        }
        else if (rulerCount == 2) {
            necessarySealCount = 200;
        }
        else if (rulerCount == 3) {
            necessarySealCount = 500;
        }
        return necessarySealCount;
    }
    
    private void createRuler(L1PcInstance pc, int attr, int sealCount) {
        int rulerId = 0;
        int protectionId = 0;
        int sealId = 0;
        if (attr == 1) {
            rulerId = 40917;
            protectionId = 40909;
            sealId = 40913;
        }
        else if (attr == 2) {
            rulerId = 40919;
            protectionId = 40911;
            sealId = 40915;
        }
        else if (attr == 4) {
            rulerId = 40918;
            protectionId = 40910;
            sealId = 40914;
        }
        else if (attr == 8) {
            rulerId = 40920;
            protectionId = 40912;
            sealId = 40916;
        }
        pc.getInventory().consumeItem(protectionId, 1L);
        pc.getInventory().consumeItem(sealId, (long)sealCount);
        L1ItemInstance item = pc.getInventory().storeItem(rulerId, 1L);
        if (item != null) {
            pc.sendPackets(new S_ServerMessage(143, this.getNpcTemplate().get_name(), item.getLogName()));
        }
    }
    
    private String talkToRuba(L1PcInstance pc) {
        String htmlid = "";
        if (pc.isCrown() || pc.isWizard()) {
            htmlid = "en0101";
        }
        else if (pc.isKnight() || pc.isElf() || pc.isDarkelf()) {
            htmlid = "en0102";
        }
        return htmlid;
    }
    
    private String talkToSecondtbox(L1PcInstance pc) {
        String htmlid = "";
        if (pc.getQuest().get_step(24) == 255) {
            if (pc.getInventory().checkItem(40701)) {
                htmlid = "maptboxa";
            }
            else {
                htmlid = "maptbox0";
            }
        }
        else {
            htmlid = "maptbox0";
        }
        return htmlid;
    }
    
    private String talkToThirdtbox(L1PcInstance pc) {
        String htmlid = "";
        if (pc.getQuest().get_step(25) == 255) {
            if (pc.getInventory().checkItem(40701)) {
                htmlid = "maptboxd";
            }
            else {
                htmlid = "maptbox0";
            }
        }
        else {
            htmlid = "maptbox0";
        }
        return htmlid;
    }
    
    private void newUserHelp(L1PcInstance pc, int helpNo) {
        switch (helpNo) {
            case 1: {
                pc.sendPackets(new S_ServerMessage(183));
                pc.sendPackets(new S_SkillHaste(pc.getId(), 1, 1600));
                pc.broadcastPacketX8(new S_SkillHaste(pc.getId(), 1, 0));
                pc.sendPackets(new S_SkillSound(pc.getId(), 755));
                pc.broadcastPacketX8(new S_SkillSound(pc.getId(), 755));
                pc.setMoveSpeed(1);
                pc.setSkillEffect(1001, 1600000);
                pc.setCurrentHp(pc.getMaxHp());
                if (pc.getLevel() < 13) {
                    pc.setCurrentMp(pc.getMaxMp());
                }
                pc.sendPackets(new S_ServerMessage(77));
                pc.sendPackets(new S_SkillSound(pc.getId(), 830));
                break;
            }
            case 2: {
                pc.sendPackets(new S_ServerMessage(183));
                pc.sendPackets(new S_SkillHaste(pc.getId(), 1, 1600));
                pc.broadcastPacketX8(new S_SkillHaste(pc.getId(), 1, 0));
                pc.sendPackets(new S_SkillSound(pc.getId(), 755));
                pc.broadcastPacketX8(new S_SkillSound(pc.getId(), 755));
                pc.setMoveSpeed(1);
                pc.setSkillEffect(1001, 1600000);
                break;
            }
            case 3: {
                if (pc.getWeapon() == null) {
                    pc.sendPackets(new S_ServerMessage(79));
                    break;
                }
                else {
                    for (L1ItemInstance item : pc.getInventory().getItems()) {
                        if (pc.getWeapon().equals(item)) {
                            L1SkillUse l1skilluse = new L1SkillUse();
                            l1skilluse.handleCommands(pc, 8, pc.getId(), pc.getX(), pc.getY(), 0, 2);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
