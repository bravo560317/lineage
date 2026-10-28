package com.lineage.server.model;

import com.lineage.server.clientpackets.C_War;
import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import java.util.Iterator;
import com.lineage.server.datatables.sql.ClanTable;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.utils.CalcStat;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.config.ConfigAlt;
import com.lineage.server.clientpackets.C_CreateChar;
import java.io.IOException;
import java.sql.Timestamp;
import java.io.FileWriter;
import java.io.File;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.william.PayBonus;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.lock.CharacterQuestReading;
import com.lineage.server.templates.L1Quest;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.QuestClass;
import com.lineage.server.datatables.QuestTable;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.config.ConfigClan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.text.SimpleDateFormat;
import java.sql.Date;
import com.lineage.server.datatables.lock.SpawnBossReading;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.command.executor.L1ToPC2;
import com.lineage.william.ItemShop;
import com.lineage.william.ItemAction;
import com.lineage.william.ItemActionTele;
import com.lineage.william.ItemActionPolyBS;
import com.lineage.william.ItemActionDoll;
import com.lineage.william.ItemActionPoly;
import com.lineage.FollowAutoAttackUpdate;
import com.lineage.data.item_etcitem.extra.Show_pcitem;
import com.add.system.CardBookCmd_doll;
import com.add.system.CardBookCmd;
import com.add.TFUpdate;
import com.add.AutoAttackUpdate;
import com.lineage.server.datatables.PolyTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import org.apache.commons.logging.LogFactory;
import java.io.BufferedWriter;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.item_etcitem.PatternSystem;

public class L1ActionPc
{
    private static final Log _log;
    public static final Random _random;
    private final L1PcInstance _pc;
    boolean start;
    private static BufferedWriter out;
    
    static {
        _log = LogFactory.getLog(L1ActionPc.class);
        _random = new Random();
    }
    
    public L1ActionPc(L1PcInstance pc) {
        this.start = false;
        this._pc = pc;
    }
    
    public L1PcInstance get_pc() {
        return this._pc;
    }
    
    public void action(String cmd, long amount) {
        try {
            if (this._pc.isInCharReset()) {
                switch (cmd.hashCode()) {
                    case 49: {
                        if (!cmd.equals("1")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this.setLevelUp(this._pc, 1);
                            }
                            checkLevelUp(this._pc);
                            break;
                        }
                    }
                    case 1567: {
                        if (!cmd.equals("10")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this.setLevelUp(this._pc, 10);
                            }
                            checkLevelUp(this._pc);
                            break;
                        }
                    }
                    case 2524: {
                        if (!cmd.equals("OK")) {
                            break;
                        }
                        else {
                            saveNewCharStatus(this._pc);
                            break;
                        }
                    }
                    case 67708: {
                        if (!cmd.equals("Cha")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.addBaseCha(1);
                                this.setLevelUp(this._pc, 1);
                            }
                            checkBonusPower(this._pc);
                            break;
                        }
                    }
                    case 67938: {
                        if (!cmd.equals("Con")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.addBaseCon(1);
                                this.setLevelUp(this._pc, 1);
                            }
                            checkBonusPower(this._pc);
                            break;
                        }
                    }
                    case 68599: {
                        if (!cmd.equals("Dex")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.addBaseDex(1);
                                this.setLevelUp(this._pc, 1);
                            }
                            checkBonusPower(this._pc);
                            break;
                        }
                    }
                    case 73679: {
                        if (!cmd.equals("Int")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.addBaseInt(1);
                                this.setLevelUp(this._pc, 1);
                            }
                            checkBonusPower(this._pc);
                            break;
                        }
                    }
                    case 83473: {
                        if (!cmd.equals("Str")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.addBaseStr(1);
                                this.setLevelUp(this._pc, 1);
                            }
                            checkBonusPower(this._pc);
                            break;
                        }
                    }
                    case 86977: {
                        if (!cmd.equals("Wis")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.addBaseWis(1);
                                this.setLevelUp(this._pc, 1);
                            }
                            checkBonusPower(this._pc);
                            break;
                        }
                    }
                    case 96918249: {
                        if (!cmd.equals("exCha")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1);
                                this._pc.addBaseCha(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkElixirPower(this._pc);
                            break;
                        }
                    }
                    case 96918479: {
                        if (!cmd.equals("exCon")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1);
                                this._pc.addBaseCon(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkElixirPower(this._pc);
                            break;
                        }
                    }
                    case 96919140: {
                        if (!cmd.equals("exDex")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1);
                                this._pc.addBaseDex(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkElixirPower(this._pc);
                            break;
                        }
                    }
                    case 96924220: {
                        if (!cmd.equals("exInt")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1);
                                this._pc.addBaseInt(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkElixirPower(this._pc);
                            break;
                        }
                    }
                    case 96934014: {
                        if (!cmd.equals("exStr")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1);
                                this._pc.addBaseStr(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkElixirPower(this._pc);
                            break;
                        }
                    }
                    case 96937518: {
                        if (!cmd.equals("exWis")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1);
                                this._pc.addBaseWis(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkElixirPower(this._pc);
                            break;
                        }
                    }
                    case 1948305772: {
                        if (!cmd.equals("initCha")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1);
                                this._pc.addBaseCha(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkInitPower(this._pc);
                            break;
                        }
                    }
                    case 1948306002: {
                        if (!cmd.equals("initCon")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1);
                                this._pc.addBaseCon(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkInitPower(this._pc);
                            break;
                        }
                    }
                    case 1948306663: {
                        if (!cmd.equals("initDex")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1);
                                this._pc.addBaseDex(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkInitPower(this._pc);
                            break;
                        }
                    }
                    case 1948311743: {
                        if (!cmd.equals("initInt")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1);
                                this._pc.addBaseInt(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkInitPower(this._pc);
                            break;
                        }
                    }
                    case 1948321537: {
                        if (!cmd.equals("initStr")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1);
                                this._pc.addBaseStr(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkInitPower(this._pc);
                            break;
                        }
                    }
                    case 1948325041: {
                        if (!cmd.equals("initWis")) {
                            break;
                        }
                        else {
                            if (this.checkvalid(this._pc, cmd)) {
                                this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1);
                                this._pc.addBaseWis(1);
                                this._pc.sendPackets(new S_OwnCharStatus2(this._pc));
                            }
                            checkInitPower(this._pc);
                            break;
                        }
                    }
                }
                return;
            }
            else if (this._pc.isShapeChange()) {
                this._pc.get_other().set_gmHtml(null);
                int awakeSkillId = this._pc.getAwakeSkillId();
                if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
                    this._pc.sendPackets(new S_ServerMessage(1384));
                    return;
                }
                else {
                    L1PolyMorph.handleCommands(this._pc, cmd);
                    this._pc.setShapeChange(false);
                    this._pc.setSummonMonster(false);
                    return;
                }
            }
            else {
                if (this._pc.isItemPoly()) {
                    L1ItemInstance item = this._pc.getPolyScroll();
                    L1PolyMorph poly = PolyTable.get().getTemplate(cmd);
                    if (poly != null || cmd.equals("none")) {
                        if (item.getItemId() == 44212 || (item.getItemId() >= 80057 && item.getItemId() <= 80066)) {
                            this.usePolyBook(this._pc, item, cmd);
                            return;
                        }
                        else if (item.getItemId() >= 80067 && item.getItemId() <= 80070) {
                            this.usePolyBookcouitem(this._pc, item, cmd);
                            return;
                        }
                        else {
                            this.usePolyScroll(this._pc, item, cmd);
                            return;
                        }
                    }
                }
                if (this._pc.isPhantomTeleport()) {
                    this.usePhantomTeleport(this._pc, cmd);
                    return;
                }
                else if (this._pc.get_other().get_gmHtml() != null) {
                    this._pc.get_other().get_gmHtml().action(cmd);
                    return;
                }
                else if (AutoAttackUpdate.getInstance().PcCommand(this._pc, cmd)) {
                    return;
                }
                else if (TFUpdate.getInstance().PcCommand(this._pc, cmd)) {
                    return;
                }
                else if (CardBookCmd.get().Cmd(this._pc, cmd)) {
                    return;
                }
                else if (CardBookCmd.get().PolyCmd(this._pc, cmd)) {
                    return;
                }
                else if (CardBookCmd_doll.get().Cmd(this._pc, cmd)) {
                    return;
                }
                else if (CardBookCmd_doll.get().PolyCmd(this._pc, cmd)) {
                    return;
                }
                else if (Show_pcitem.get().PcCommand(this._pc, cmd)) {
                    return;
                }
                else if (FollowAutoAttackUpdate.getInstance().PcCommand(this._pc, cmd)) {
                    return;
                }
                else if (ItemActionPoly.forNpcQuest(cmd, this._pc)) {
                    return;
                }
                else if (ItemActionDoll.forNpcQuest(cmd, this._pc)) {
                    return;
                }
                else if (ItemActionPolyBS.forNpcQuest(cmd, this._pc)) {
                    return;
                }
                else if (ItemActionTele.forNpcQuest(cmd, this._pc)) {
                    return;
                }
                else {
                    L1ItemInstance item2 = this._pc.getPolyScrol2();
                    if (ItemAction.forNpcQuest(cmd, this._pc, item2)) {
                        return;
                    }
                    else if (ItemShop.forNpcQuest(cmd, this._pc)) {
                        return;
                    }
                    else {
                        this._pc.get_other().set_gmHtml(null);
                        if (this._pc.isGm()) {
                            if (cmd.equals("tp_refresh")) {
                                L1ToPC2.checkTPhtmlPredicate(this._pc, 0, true);
                            }
                            else if (cmd.equals("tp_refresh_map")) {
                                L1ToPC2.checkTPhtmlPredicate(this._pc, 0, false);
                            }
                            else if (cmd.equals("tp_page_up")) {
                                L1ToPC2.checkTPhtml(this._pc, this._pc.get_other().get_page() - 1);
                            }
                            else if (cmd.equals("tp_page_down")) {
                                L1ToPC2.checkTPhtml(this._pc, this._pc.get_other().get_page() + 1);
                            }
                            else if (cmd.matches("tp_[0-9]+")) {
                                int index = Integer.parseInt(cmd.substring(3));
                                L1ToPC2.teleport2Player(this._pc, index);
                            }
                        }
                        if (cmd.equalsIgnoreCase("power")) {
                            if (this._pc.power()) {
                                this._pc.sendPackets(new S_Bonusstats(this._pc.getId()));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("bosspage2")) {
                            int pag1 = SpawnBossReading.get().bossreid().size() / 15;
                            int pag2 = (SpawnBossReading.get().bossreid().size() % 15 != 0) ? 1 : 0;
                            if (pag1 + pag2 > this._pc.get_bosspage()) {
                                this._pc.set_bosspage(this._pc.get_bosspage() + 1);
                                String[] info = new String[17];
                                int num = 0;
                                int i = 15 * (this._pc.get_bosspage() - 1);
                                while (i < SpawnBossReading.get().bossreid().size()) {
                                    int now = SpawnBossReading.get().bossreid().get(i);
                                    if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime() != null) {
                                        Date localDate = new Date(SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis());
                                        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
                                        String time = localSimpleDateFormat.format(localDate);
                                        if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis() < System.currentTimeMillis()) {
                                            info[num] = "【已降臨】" + SpawnBossReading.get().getTemplate(now).getName();
                                        }
                                        if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis() >= System.currentTimeMillis()) {
                                            info[num] = "【下次重生:" + time + "】" + SpawnBossReading.get().getTemplate(now).getName();
                                        }
                                    }
                                    else {
                                        info[num] = "【已降臨】" + SpawnBossReading.get().getTemplate(now).getName();
                                    }
                                    if (++num > 14) {
                                        break;
                                    }
                                    else {
                                        ++i;
                                    }
                                }
                                info[15] = "上一頁";
                                info[16] = "下一頁";
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "bosslist", info));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("bosspage1")) {
                            if (this._pc.get_bosspage() > 1) {
                                this._pc.set_bosspage(this._pc.get_bosspage() - 1);
                                String[] info2 = new String[17];
                                int num2 = 0;
                                int j = 15 * (this._pc.get_bosspage() - 1);
                                while (j < SpawnBossReading.get().bossreid().size()) {
                                    int now2 = SpawnBossReading.get().bossreid().get(j);
                                    if (SpawnBossReading.get().getTemplate(now2).get_nextSpawnTime() != null) {
                                        Date localDate2 = new Date(SpawnBossReading.get().getTemplate(now2).get_nextSpawnTime().getTimeInMillis());
                                        SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("HH:mm");
                                        String time2 = localSimpleDateFormat2.format(localDate2);
                                        if (SpawnBossReading.get().getTemplate(now2).get_nextSpawnTime().getTimeInMillis() < System.currentTimeMillis()) {
                                            info2[num2] = "【已降臨】" + SpawnBossReading.get().getTemplate(now2).getName();
                                        }
                                        if (SpawnBossReading.get().getTemplate(now2).get_nextSpawnTime().getTimeInMillis() >= System.currentTimeMillis()) {
                                            info2[num2] = "【★下次重生:" + time2 + "】" + SpawnBossReading.get().getTemplate(now2).getName();
                                        }
                                    }
                                    else {
                                        info2[num2] = "【已降臨】" + SpawnBossReading.get().getTemplate(now2).getName();
                                    }
                                    if (++num2 > 14) {
                                        break;
                                    }
                                    else {
                                        ++j;
                                    }
                                }
                                info2[15] = "上一頁";
                                info2[16] = "下一頁";
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "bosslist", info2));
                            }
                        }
        			 else if (cmd.equalsIgnoreCase("pasea")) {
        				final String[] data = new String[6];
        				if (_pc.getPaCha() > 0) {
        					final String pacha = String.valueOf((int) (_pc.getPaCha()));
        					data[0] = pacha;
        				}
        				if (_pc.getPaCon() > 0) {
        					final String pacon = String.valueOf((int) (_pc.getPaCon()));
        					data[1] = pacon;
        				}
        				if (_pc.getPaDex() > 0) {
        					final String padex = String.valueOf((int) (_pc.getPaDex()));
        					data[2] = padex;
        				}
        				if (_pc.getPaInt() > 0) {
        					final String paint = String.valueOf((int) (_pc.getPaInt()));
        					data[3] = paint;
        				}
        				if (_pc.getPaStr() > 0) {
        					final String pastr = String.valueOf((int) (_pc.getPaStr()));
        					data[4] = pastr;
        				}
        				if (_pc.getPaWis() > 0) {
        					final String pawis = String.valueOf((int) (_pc.getPaWis()));
        					data[5] = pawis;
        				}
        				_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "wnchaxun", data));      				
        			 }	
                        else if (cmd.equalsIgnoreCase("bad_3")) {
                            this._pc.setBadKeyInEnemy(true);
                            this._pc.sendPackets(new S_ServerMessage("\\fU請輸入要加入黑名單的[玩家名稱]。"));
                        }
                        else if (cmd.equalsIgnoreCase("bad_4")) {
                            this._pc.setBadKeyOutEnemy(true);
                            this._pc.sendPackets(new S_ServerMessage("\\fU請輸入要移除黑名單的[玩家名稱]。"));
                        }
                        else if (cmd.equalsIgnoreCase("bad_5")) {
                            this.BadEnemyList(this._pc);
                        }
                        else if (cmd.equalsIgnoreCase("clanlv1")) {
                            if (this._pc.isCrown()) {
                                if (this._pc.getClan() == null) {
                                    this._pc.sendPackets(new S_SystemMessage("血盟等級升級必須先擁有血盟喔。"));
                                }
                                else {
                                    clanLevel(this._pc, 1);
                                    L1Clan clan = WorldClan.get().getClan(this._pc.getClanname());
                                    String[] info3 = new String[7];
                                    if (this._pc.getClanid() > 0) {
                                        info3[0] = this._pc.getClanname();
                                    }
                                    else {
                                        info3[0] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[1] = String.valueOf(clan.getClanLevel());
                                    }
                                    else {
                                        info3[1] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[2] = String.valueOf(clan.getClanContribution());
                                    }
                                    else {
                                        info3[2] = "無";
                                    }
                                    info3[3] = String.valueOf(this._pc.getPcContribution());
                                    info3[4] = String.valueOf(this._pc.getClanContribution());
                                    info3[5] = String.valueOf(this._pc.getclanadena());
                                    info3[6] = String.valueOf(clan.getclanadena());
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "clanlvadd", info3));
                                }
                            }
                            else {
                                this._pc.sendPackets(new S_SystemMessage("你的職業無法強化血盟等級。"));
                            }
                        }
                        else if (cmd.equals("deletebook")) {
                            this._pc.sendPackets(new S_SystemMessage("請輸入刪除座標名稱(60秒內)"));
                            this._pc.setSkillEffect(95133, 60000);
                        }
                        else if (cmd.equals("clanlv3")) {
                            if (this._pc.getClan() == null) {
                                this._pc.sendPackets(new S_SystemMessage("想要貢獻必須先擁有血盟喔。"));
                            }
                            else if (this._pc.getInventory().checkItem(40308, (long)ConfigClan.PcClanAdena)) {
                                if (this._pc.getLevel() >= ConfigClan.clanlevel) {
                                    clanLevel(this._pc, 3);
                                    L1Clan clan = WorldClan.get().getClan(this._pc.getClanname());
                                    String[] info3 = new String[7];
                                    if (this._pc.getClanid() > 0) {
                                        info3[0] = this._pc.getClanname();
                                    }
                                    else {
                                        info3[0] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[1] = String.valueOf(clan.getClanLevel());
                                    }
                                    else {
                                        info3[1] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[2] = String.valueOf(clan.getClanContribution());
                                    }
                                    else {
                                        info3[2] = "無";
                                    }
                                    info3[3] = String.valueOf(this._pc.getPcContribution());
                                    info3[4] = String.valueOf(this._pc.getClanContribution());
                                    info3[5] = String.valueOf(this._pc.getclanadena());
                                    info3[6] = String.valueOf(clan.getclanadena());
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "clanlvadd", info3));
                                }
                                else {
                                    this._pc.sendPackets(new S_SystemMessage(ConfigClan.clanmsg1));
                                }
                            }
                            else {
                                this._pc.sendPackets(new S_SystemMessage("金幣不足無法幫助你的血盟喔。"));
                            }
                        }
                        else if (cmd.equals("clanlv5")) {
                            if (this._pc.getClan() == null) {
                                this._pc.sendPackets(new S_SystemMessage("想要貢獻必須先擁有血盟喔。"));
                            }
                            else if (this._pc.getInventory().checkItem(44070, (long)ConfigClan.ClanItem44070)) {
                                if (this._pc.getLevel() >= ConfigClan.clanlevel) {
                                    clanLevel(this._pc, 5);
                                    L1Clan clan = WorldClan.get().getClan(this._pc.getClanname());
                                    String[] info3 = new String[7];
                                    if (this._pc.getClanid() > 0) {
                                        info3[0] = this._pc.getClanname();
                                    }
                                    else {
                                        info3[0] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[1] = String.valueOf(clan.getClanLevel());
                                    }
                                    else {
                                        info3[1] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[2] = String.valueOf(clan.getClanContribution());
                                    }
                                    else {
                                        info3[2] = "無";
                                    }
                                    info3[3] = String.valueOf(this._pc.getPcContribution());
                                    info3[4] = String.valueOf(this._pc.getClanContribution());
                                    info3[5] = String.valueOf(this._pc.getclanadena());
                                    info3[6] = String.valueOf(clan.getclanadena());
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "clanlvadd", info3));
                                }
                                else {
                                    this._pc.sendPackets(new S_SystemMessage(ConfigClan.clanmsg1));
                                }
                            }
                            else {
                                this._pc.sendPackets(new S_SystemMessage("元寶不足無法幫助你的血盟喔。"));
                            }
                        }
                        else if (cmd.equals("clanlv6")) {
                            if (this._pc.getClan() == null) {
                                this._pc.sendPackets(new S_SystemMessage("想要貢獻必須先擁有血盟喔。"));
                            }
                            else if (this._pc.getInventory().checkItem(44070, (long)ConfigClan.ClanItem44070_1)) {
                                if (this._pc.getLevel() >= ConfigClan.clanlevel) {
                                    clanLevel(this._pc, 6);
                                    L1Clan clan = WorldClan.get().getClan(this._pc.getClanname());
                                    String[] info3 = new String[7];
                                    if (this._pc.getClanid() > 0) {
                                        info3[0] = this._pc.getClanname();
                                    }
                                    else {
                                        info3[0] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[1] = String.valueOf(clan.getClanLevel());
                                    }
                                    else {
                                        info3[1] = "無";
                                    }
                                    if (this._pc.getClanid() > 0) {
                                        info3[2] = String.valueOf(clan.getClanContribution());
                                    }
                                    else {
                                        info3[2] = "無";
                                    }
                                    info3[3] = String.valueOf(this._pc.getPcContribution());
                                    info3[4] = String.valueOf(this._pc.getClanContribution());
                                    info3[5] = String.valueOf(this._pc.getclanadena());
                                    info3[6] = String.valueOf(clan.getclanadena());
                                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "clanlvadd", info3));
                                }
                                else {
                                    this._pc.sendPackets(new S_SystemMessage(ConfigClan.clanmsg1));
                                }
                            }
                            else {
                                this._pc.sendPackets(new S_SystemMessage("元寶不足無法幫助你的血盟喔。"));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("cleanaiq")) {
                            this._pc.setnewai4(0);
                            this._pc.setnewai5(0);
                            this._pc.setnewai6(0);
                            newai(this._pc);
                        }
                        else if (cmd.equalsIgnoreCase("1q")) {
                            this._pc.setnewaiq1(1);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq1());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq1());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq1());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("2q")) {
                            this._pc.setnewaiq2(2);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq2());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq2());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq2());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("3q")) {
                            this._pc.setnewaiq3(3);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq3());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq3());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq3());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("4q")) {
                            this._pc.setnewaiq4(4);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq4());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq4());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq4());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.setSkillEffect(6930, 1200000);
                                this._pc.killSkillEffectTimer(6933);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("5q")) {
                            this._pc.setnewaiq5(5);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq5());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq5());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq5());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("6q")) {
                            this._pc.setnewaiq6(6);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq6());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq6());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq6());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("7q")) {
                            this._pc.setnewaiq7(7);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq7());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq7());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq7());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("8q")) {
                            this._pc.setnewaiq8(8);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq8());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq8());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq8());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("9q")) {
                            this._pc.setnewaiq9(9);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq9());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq9());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq9());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("0q")) {
                            this._pc.setnewaiq0(0);
                            if (this._pc.getnewai4() == 0) {
                                this._pc.setnewai4(this._pc.getnewaiq1());
                            }
                            else if (this._pc.getnewai5() == 0) {
                                this._pc.setnewai5(this._pc.getnewaiq1());
                            }
                            else if (this._pc.getnewai6() == 0) {
                                this._pc.setnewai6(this._pc.getnewaiq1());
                            }
                            newai(this._pc);
                            if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
                                this._pc.sendPackets(new S_SystemMessage("恭喜您驗證成功"));
                                this._pc.setnewai4(0);
                                this._pc.setnewai5(0);
                                this._pc.setnewai6(0);
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                                this._pc.killSkillEffectTimer(6933);
                                this._pc.setSkillEffect(6930, 1200000);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("locerr1")) {
                            if (this._pc.isParalyzed() || this._pc.isSleeped() || this._pc.isParalyzedX()) {
                                return;
                            }
                            else if (this._pc.isDead()) {
                                return;
                            }
                            else if (this._pc.isInvisble()) {
                                return;
                            }
                            else if (this._pc.hasSkillEffect(55889)) {
                                this._pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
                                return;
                            }
                            else {
                                this._pc.setSkillEffect(55889, 30000);
                                this._pc.set_unfreezingTime(5);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("locerr2")) {
                            if (this._pc.isParalyzed() || this._pc.isSleeped() || this._pc.isParalyzedX()) {
                                return;
                            }
                            else if (this._pc.isDead()) {
                                return;
                            }
                            else if (this._pc.isInvisble()) {
                                return;
                            }
                            else if (this._pc.hasSkillEffect(55889)) {
                                this._pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
                                return;
                            }
                            else {
                                this._pc.setSkillEffect(55889, 30000);
                                this._pc.set_misslocTime(3);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("index")) {
                            this._pc.isWindows();
                        }
                        else if (cmd.equalsIgnoreCase("gfxid")) {
                            if (this._pc.getopengfxid()) {
                                this._pc.setopengfxid(false);
                                this._pc.sendPackets(new S_ServerMessage("關閉-畫面中玩家特效"));
                            }
                            else {
                                this._pc.setopengfxid(true);
                                this._pc.sendPackets(new S_ServerMessage("開啟-畫面中玩家特效"));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("npcdropmenu")) {
                            this._pc.setItemName(true);
                            this._pc.sendPackets(new S_SystemMessage("\\fY請輸入要查詢道具名稱後，按(Enter)。"));
                        }
                        else if (cmd.equalsIgnoreCase("pc_score")) {
                            if (this._pc.get_other().get_score() >= 0) {
                                this._pc.sendPackets(new S_ServerMessage("\\aD目前陣營積分:" + this._pc.get_other().get_score()));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("kor_meau")) {
                            if (this._pc.isActived()) {
                                this._pc.sendPackets(new S_ServerMessage("掛機中無法使用開關"));
                                return;
                            }
                            else {
                                String type1 = "";
                                String type2 = "";
                                String type3 = "";
                                String type4 = "";
                                String type5 = "";
                                if (this._pc.hasSkillEffect(1688)) {
                                    type1 = "開啟";
                                }
                                else {
                                    type1 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1689)) {
                                    type2 = "開啟";
                                }
                                else {
                                    type2 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1690)) {
                                    type3 = "開啟";
                                }
                                else {
                                    type3 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1691)) {
                                    type4 = "開啟";
                                }
                                else {
                                    type4 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1692)) {
                                    type5 = "開啟";
                                }
                                else {
                                    type5 = "關閉";
                                }
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("aa")) {
                            boolean isTalent = false;
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                isTalent = true;
                            }
                            if (isTalent) {
                                if (this._pc.getlogpcpower_SkillFor1() >= 10) {
                                    this._pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                                    isTalent = false;
                                }
                                else {
                                    this._pc.setlogpcpower_SkillFor1(this._pc.getlogpcpower_SkillFor1() + 1);
                                    this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
                                }
                            }
                            String[] info3 = new String[6];
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                info3[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
                            }
                            else {
                                info3[0] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor1() > 0) {
                                info3[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
                            }
                            else {
                                info3[1] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor2() > 0) {
                                info3[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
                            }
                            else {
                                info3[2] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor3() > 0) {
                                info3[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
                            }
                            else {
                                info3[3] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor4() > 0) {
                                info3[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
                            }
                            else {
                                info3[4] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor5() > 0) {
                                info3[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
                            }
                            else {
                                info3[5] = "0";
                            }
                            if (this._pc.isCrown()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talCrown", info3));
                            }
                            else if (this._pc.isKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talKnight", info3));
                            }
                            else if (this._pc.isWizard()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talWizard", info3));
                            }
                            else if (this._pc.isElf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talElf", info3));
                            }
                            else if (this._pc.isDarkelf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info3));
                            }
                            else if (this._pc.isDragonKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info3));
                            }
                            else if (this._pc.isIllusionist()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info3));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("bb")) {
                            boolean isTalent = false;
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                isTalent = true;
                            }
                            if (isTalent) {
                                if (this._pc.getlogpcpower_SkillFor2() >= 10) {
                                    this._pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                                    isTalent = false;
                                }
                                else {
                                    this._pc.setlogpcpower_SkillFor2(this._pc.getlogpcpower_SkillFor2() + 1);
                                    this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
                                }
                            }
                            String[] info3 = new String[6];
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                info3[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
                            }
                            else {
                                info3[0] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor1() > 0) {
                                info3[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
                            }
                            else {
                                info3[1] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor2() > 0) {
                                info3[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
                            }
                            else {
                                info3[2] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor3() > 0) {
                                info3[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
                            }
                            else {
                                info3[3] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor4() > 0) {
                                info3[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
                            }
                            else {
                                info3[4] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor5() > 0) {
                                info3[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
                            }
                            else {
                                info3[5] = "0";
                            }
                            if (this._pc.isCrown()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talCrown", info3));
                            }
                            else if (this._pc.isKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talKnight", info3));
                            }
                            else if (this._pc.isWizard()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talWizard", info3));
                            }
                            else if (this._pc.isElf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talElf", info3));
                            }
                            else if (this._pc.isDarkelf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info3));
                            }
                            else if (this._pc.isDragonKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info3));
                            }
                            else if (this._pc.isIllusionist()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info3));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("cc")) {
                            boolean isTalent = false;
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                isTalent = true;
                            }
                            if (isTalent) {
                                if (this._pc.getlogpcpower_SkillFor3() >= 10) {
                                    this._pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                                    isTalent = false;
                                }
                                else {
                                    this._pc.setlogpcpower_SkillFor3(this._pc.getlogpcpower_SkillFor3() + 1);
                                    this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
                                }
                            }
                            String[] info3 = new String[6];
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                info3[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
                            }
                            else {
                                info3[0] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor1() > 0) {
                                info3[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
                            }
                            else {
                                info3[1] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor2() > 0) {
                                info3[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
                            }
                            else {
                                info3[2] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor3() > 0) {
                                info3[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
                            }
                            else {
                                info3[3] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor4() > 0) {
                                info3[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
                            }
                            else {
                                info3[4] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor5() > 0) {
                                info3[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
                            }
                            else {
                                info3[5] = "0";
                            }
                            if (this._pc.isCrown()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talCrown", info3));
                            }
                            else if (this._pc.isKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talKnight", info3));
                            }
                            else if (this._pc.isWizard()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talWizard", info3));
                            }
                            else if (this._pc.isElf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talElf", info3));
                            }
                            else if (this._pc.isDarkelf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info3));
                            }
                            else if (this._pc.isDragonKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info3));
                            }
                            else if (this._pc.isIllusionist()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info3));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("dd")) {
                            boolean isTalent = false;
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                isTalent = true;
                            }
                            if (isTalent) {
                                if (this._pc.getlogpcpower_SkillFor4() >= 10) {
                                    this._pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                                    isTalent = false;
                                }
                                else {
                                    this._pc.setlogpcpower_SkillFor4(this._pc.getlogpcpower_SkillFor4() + 1);
                                    this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
                                }
                            }
                            String[] info3 = new String[6];
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                info3[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
                            }
                            else {
                                info3[0] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor1() > 0) {
                                info3[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
                            }
                            else {
                                info3[1] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor2() > 0) {
                                info3[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
                            }
                            else {
                                info3[2] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor3() > 0) {
                                info3[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
                            }
                            else {
                                info3[3] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor4() > 0) {
                                info3[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
                            }
                            else {
                                info3[4] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor5() > 0) {
                                info3[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
                            }
                            else {
                                info3[5] = "0";
                            }
                            if (this._pc.isCrown()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talCrown", info3));
                            }
                            else if (this._pc.isKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talKnight", info3));
                            }
                            else if (this._pc.isWizard()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talWizard", info3));
                            }
                            else if (this._pc.isElf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talElf", info3));
                            }
                            else if (this._pc.isDarkelf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info3));
                            }
                            else if (this._pc.isDragonKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info3));
                            }
                            else if (this._pc.isIllusionist()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info3));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("ee")) {
                            boolean isTalent = false;
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                isTalent = true;
                            }
                            if (isTalent) {
                                if (this._pc.getlogpcpower_SkillFor5() >= 10) {
                                    this._pc.sendPackets(new S_SystemMessage("該天賦技能已滿"));
                                    isTalent = false;
                                }
                                else {
                                    this._pc.setlogpcpower_SkillFor5(this._pc.getlogpcpower_SkillFor5() + 1);
                                    this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
                                    if (this._pc.isDragonKnight()) {
                                        if (this._pc.getlogpcpower_SkillFor5() == 1) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 2) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 3) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 4) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 5) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 6) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 7) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 8) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 9) {
                                            this._pc.addMaxHp(400);
                                        }
                                        else if (this._pc.getlogpcpower_SkillFor5() == 10) {
                                            this._pc.addMaxHp(400);
                                            this._pc.sendPackets(new S_OwnCharStatus(this._pc));
                                        }
                                    }
                                }
                            }
                            String[] info3 = new String[6];
                            if (this._pc.getlogpcpower_SkillCount() > 0) {
                                info3[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
                            }
                            else {
                                info3[0] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor1() > 0) {
                                info3[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
                            }
                            else {
                                info3[1] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor2() > 0) {
                                info3[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
                            }
                            else {
                                info3[2] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor3() > 0) {
                                info3[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
                            }
                            else {
                                info3[3] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor4() > 0) {
                                info3[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
                            }
                            else {
                                info3[4] = "0";
                            }
                            if (this._pc.getlogpcpower_SkillFor5() > 0) {
                                info3[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
                            }
                            else {
                                info3[5] = "0";
                            }
                            if (this._pc.isCrown()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talCrown", info3));
                            }
                            else if (this._pc.isKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talKnight", info3));
                            }
                            else if (this._pc.isWizard()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talWizard", info3));
                            }
                            else if (this._pc.isElf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talElf", info3));
                            }
                            else if (this._pc.isDarkelf()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info3));
                            }
                            else if (this._pc.isDragonKnight()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info3));
                            }
                            else if (this._pc.isIllusionist()) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info3));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("ff")) {
                            L1Item temp = ItemTable.get().getTemplate(ConfigOther.recaseff);
                            if (!this._pc.getInventory().checkItem(ConfigOther.recaseff, (long)ConfigOther.recaseffcount)) {
                                this._pc.sendPackets(new S_SystemMessage(temp.getName() + "不足" + ConfigOther.recaseffcount + "個"));
                                return;
                            }
                            else if (this._pc.getMeteLevel() == 0) {
                                this._pc.sendPackets(new S_SystemMessage("您尚未轉身無法重置"));
                                return;
                            }
                            else if (this._pc.getlogpcpower_SkillCount() > 0) {
                                this._pc.sendPackets(new S_SystemMessage("請先將天賦點數點完"));
                                return;
                            }
                            else {
                                this._pc.getInventory().consumeItem(40308, 100L);
                                int SkillCount = this._pc.getlogpcpower_SkillFor1() + this._pc.getlogpcpower_SkillFor2() + this._pc.getlogpcpower_SkillFor3() + this._pc.getlogpcpower_SkillFor4() + this._pc.getlogpcpower_SkillFor5();
                                this._pc.setlogpcpower_SkillCount(SkillCount);
                                if (this._pc.isDragonKnight()) {
                                    this._pc.addMaxHp(-400 * this._pc.getlogpcpower_SkillFor5());
                                    this._pc.sendPackets(new S_OwnCharStatus(this._pc));
                                }
                                this._pc.setlogpcpower_SkillFor1(0);
                                this._pc.setlogpcpower_SkillFor2(0);
                                this._pc.setlogpcpower_SkillFor3(0);
                                this._pc.setlogpcpower_SkillFor4(0);
                                this._pc.setlogpcpower_SkillFor5(0);
                                this._pc.sendPackets(new S_SystemMessage("天賦能力已還原"));
                                this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("atkmessage")) {
                            if (this._pc.isActived()) {
                                this._pc.sendPackets(new S_ServerMessage("掛機中無法使用開關"));
                                return;
                            }
                            else {
                                if (this._pc.hasSkillEffect(1688)) {
                                    this._pc.killSkillEffectTimer(1688);
                                }
                                else if (!this._pc.hasSkillEffect(1688)) {
                                    this._pc.setSkillEffect(1688, 0);
                                }
                                String type1 = "";
                                String type2 = "";
                                String type3 = "";
                                String type4 = "";
                                String type5 = "";
                                if (this._pc.hasSkillEffect(1688)) {
                                    type1 = "開啟";
                                }
                                else {
                                    type1 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1689)) {
                                    type2 = "開啟";
                                }
                                else {
                                    type2 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1690)) {
                                    type3 = "開啟";
                                }
                                else {
                                    type3 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1691)) {
                                    type4 = "開啟";
                                }
                                else {
                                    type4 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1692)) {
                                    type5 = "開啟";
                                }
                                else {
                                    type5 = "關閉";
                                }
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("partymessage")) {
                            if (this._pc.isActived()) {
                                this._pc.sendPackets(new S_ServerMessage("掛機中無法使用開關"));
                                return;
                            }
                            else {
                                if (this._pc.hasSkillEffect(1689)) {
                                    this._pc.killSkillEffectTimer(1689);
                                }
                                else if (!this._pc.hasSkillEffect(1689)) {
                                    this._pc.setSkillEffect(1689, 0);
                                }
                                String type1 = "";
                                String type2 = "";
                                String type3 = "";
                                String type4 = "";
                                String type5 = "";
                                if (this._pc.hasSkillEffect(1688)) {
                                    type1 = "開啟";
                                }
                                else {
                                    type1 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1689)) {
                                    type2 = "開啟";
                                }
                                else {
                                    type2 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1690)) {
                                    type3 = "開啟";
                                }
                                else {
                                    type3 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1691)) {
                                    type4 = "開啟";
                                }
                                else {
                                    type4 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1692)) {
                                    type5 = "開啟";
                                }
                                else {
                                    type5 = "關閉";
                                }
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("pcdrop")) {
                            if (this._pc.isActived()) {
                                this._pc.sendPackets(new S_ServerMessage("掛機中無法使用開關"));
                                return;
                            }
                            else {
                                if (this._pc.hasSkillEffect(1690)) {
                                    this._pc.killSkillEffectTimer(1690);
                                }
                                else if (!this._pc.hasSkillEffect(1690)) {
                                    this._pc.setSkillEffect(1690, 0);
                                }
                                String type1 = "";
                                String type2 = "";
                                String type3 = "";
                                String type4 = "";
                                String type5 = "";
                                if (this._pc.hasSkillEffect(1688)) {
                                    type1 = "開啟";
                                }
                                else {
                                    type1 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1689)) {
                                    type2 = "開啟";
                                }
                                else {
                                    type2 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1690)) {
                                    type3 = "開啟";
                                }
                                else {
                                    type3 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1691)) {
                                    type4 = "開啟";
                                }
                                else {
                                    type4 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1692)) {
                                    type5 = "開啟";
                                }
                                else {
                                    type5 = "關閉";
                                }
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("alldrop")) {
                            if (this._pc.isActived()) {
                                this._pc.sendPackets(new S_ServerMessage("掛機中無法使用開關"));
                                return;
                            }
                            else {
                                if (this._pc.hasSkillEffect(1691)) {
                                    this._pc.killSkillEffectTimer(1691);
                                }
                                else if (!this._pc.hasSkillEffect(1691)) {
                                    this._pc.setSkillEffect(1691, 0);
                                }
                                String type1 = "";
                                String type2 = "";
                                String type3 = "";
                                String type4 = "";
                                String type5 = "";
                                if (this._pc.hasSkillEffect(1688)) {
                                    type1 = "開啟";
                                }
                                else {
                                    type1 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1689)) {
                                    type2 = "開啟";
                                }
                                else {
                                    type2 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1690)) {
                                    type3 = "開啟";
                                }
                                else {
                                    type3 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1691)) {
                                    type4 = "開啟";
                                }
                                else {
                                    type4 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1692)) {
                                    type5 = "開啟";
                                }
                                else {
                                    type5 = "關閉";
                                }
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("yncheck")) {
                            if (this._pc.isActived()) {
                                this._pc.sendPackets(new S_ServerMessage("掛機中無法使用開關"));
                                return;
                            }
                            else {
                                if (this._pc.hasSkillEffect(1692)) {
                                    this._pc.killSkillEffectTimer(1692);
                                }
                                else if (!this._pc.hasSkillEffect(1692)) {
                                    this._pc.setSkillEffect(1692, 0);
                                }
                                String type1 = "";
                                String type2 = "";
                                String type3 = "";
                                String type4 = "";
                                String type5 = "";
                                if (this._pc.hasSkillEffect(1688)) {
                                    type1 = "開啟";
                                }
                                else {
                                    type1 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1689)) {
                                    type2 = "開啟";
                                }
                                else {
                                    type2 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1690)) {
                                    type3 = "開啟";
                                }
                                else {
                                    type3 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1691)) {
                                    type4 = "開啟";
                                }
                                else {
                                    type4 = "關閉";
                                }
                                if (this._pc.hasSkillEffect(1692)) {
                                    type5 = "開啟";
                                }
                                else {
                                    type5 = "關閉";
                                }
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("newcharpra")) {
                            if (this._pc.hasSkillEffect(55899)) {
                                this._pc.sendPackets(new S_ServerMessage("此功能無法連續使用"));
                                return;
                            }
                            else if (!L1CastleLocation.checkInAllWarArea(this._pc.getX(), this._pc.getY(), this._pc.getMapId())) {
                                if (this._pc.getLevel() <= ConfigOther.newcharpralv) {
                                    if (this._pc.getnewcharpra()) {
                                        this._pc.setnewcharpra(false);
                                        this._pc.sendPackets(new S_ServerMessage("關閉-新手保護"));
                                        this._pc.setSkillEffect(55899, 60000);
                                    }
                                    else if (!this._pc.getnewcharpra()) {
                                        this._pc.setnewcharpra(true);
                                        this._pc.setSkillEffect(55899, 60000);
                                        this._pc.sendPackets(new S_ServerMessage("開啟-新手保護"));
                                    }
                                }
                                else {
                                    this._pc.sendPackets(new S_ServerMessage("等級大於" + ConfigOther.newcharpralv + "無法對您進行保護了"));
                                }
                            }
                            else {
                                this._pc.sendPackets(new S_ServerMessage("旗子內禁止使用此功能"));
                            }
                        }
                        else if (cmd.equalsIgnoreCase("qt")) {
                            showStartQuest(this._pc, this._pc.getId());
                        }
                        else if (cmd.equalsIgnoreCase("quest")) {
                            showQuest(this._pc, this._pc.getId());
                        }
                        else if (cmd.equalsIgnoreCase("questa")) {
                            showQuestAll(this._pc, this._pc.getId());
                        }
                        else if (cmd.equalsIgnoreCase("i")) {
                            L1Quest quest = QuestTable.get().getTemplate(this._pc.getTempID());
                            this._pc.setTempID(0);
                            if (quest == null) {
                                return;
                            }
                            else {
                                QuestClass.get().showQuest(this._pc, quest.get_id());
                            }
                        }
                        else if (cmd.equalsIgnoreCase("d")) {
                            L1Quest quest = QuestTable.get().getTemplate(this._pc.getTempID());
                            this._pc.setTempID(0);
                            if (quest == null) {
                                return;
                            }
                            else if (this._pc.getQuest().isEnd(quest.get_id())) {
                                this.questDel(quest);
                                return;
                            }
                            else if (!this._pc.getQuest().isStart(quest.get_id())) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_q_not6"));
                                return;
                            }
                            else {
                                this.questDel(quest);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("dy")) {
                            L1Quest quest = QuestTable.get().getTemplate(this._pc.getTempID());
                            this._pc.setTempID(0);
                            if (quest == null) {
                                return;
                            }
                            else if (this._pc.getQuest().isEnd(quest.get_id())) {
                                this.isDel(quest);
                                return;
                            }
                            else if (!this._pc.getQuest().isStart(quest.get_id())) {
                                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_q_not6"));
                                return;
                            }
                            else {
                                this.isDel(quest);
                            }
                        }
                        else if (cmd.equalsIgnoreCase("up")) {
                            int page = this._pc.get_other().get_page() - 1;
                            L1ActionShowHtml show = new L1ActionShowHtml(this._pc);
                            show.showQuestMap(page);
                        }
                        else if (cmd.equalsIgnoreCase("dn")) {
                            int page = this._pc.get_other().get_page() + 1;
                            L1ActionShowHtml show = new L1ActionShowHtml(this._pc);
                            show.showQuestMap(page);
                        }
                        else if (cmd.equalsIgnoreCase("q0")) {
                            int key = this._pc.get_other().get_page() * 10 + 0;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q1")) {
                            int key = this._pc.get_other().get_page() * 10 + 1;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q2")) {
                            int key = this._pc.get_other().get_page() * 10 + 2;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q3")) {
                            int key = this._pc.get_other().get_page() * 10 + 3;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q4")) {
                            int key = this._pc.get_other().get_page() * 10 + 4;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q5")) {
                            int key = this._pc.get_other().get_page() * 10 + 5;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q6")) {
                            int key = this._pc.get_other().get_page() * 10 + 6;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q7")) {
                            int key = this._pc.get_other().get_page() * 10 + 7;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q8")) {
                            int key = this._pc.get_other().get_page() * 10 + 8;
                            this.showPage(key);
                        }
                        else if (cmd.equalsIgnoreCase("q9")) {
                            int key = this._pc.get_other().get_page() * 10 + 9;
                            this.showPage(key);
                        }
                        if (cmd.startsWith("cmpn_")) {
                            String typeString = cmd.replace("cmpn_", "");
                            PatternSystem.showHtml(this._pc, typeString);
                        } 
                        if (cmd.startsWith("p_pattern")) {
                            String powerString = cmd.replace("p_pattern", "");
                            PatternSystem.showPower(this._pc, powerString);
                        } 
                    }
                }
            }
         }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void usePolyScroll(L1PcInstance pc, L1ItemInstance item, String s) {
        try {
            L1PolyMorph poly = PolyTable.get().getTemplate(s);
            int time = 1800;
            if (item.getBless() == 0) {
                time = 2100;
            }
            if (item.getBless() == 128) {
                time = 2100;
            }
            boolean isUseItem = false;
            if (s.equals("none")) {
                if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
                    isUseItem = true;
                }
                else {
                    L1PolyMorph.undoPoly(pc);
                    isUseItem = true;
                }
            }
            else if (poly.getMinLevel() <= pc.getLevel() || pc.isGm()) {
                if (poly.getPolyId() == 13715 && (pc.get_sex() != 0 || !pc.isCrown())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13717 && (pc.get_sex() != 1 || !pc.isCrown())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13719 && (pc.get_sex() != 0 || !pc.isKnight())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13721 && (pc.get_sex() != 1 || !pc.isKnight())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13723 && (pc.get_sex() != 0 || !pc.isElf())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13725 && (pc.get_sex() != 1 || !pc.isElf())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13727 && (pc.get_sex() != 0 || !pc.isWizard())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13729 && (pc.get_sex() != 1 || !pc.isWizard())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13731 && (pc.get_sex() != 0 || !pc.isDarkelf())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13733 && (pc.get_sex() != 1 || !pc.isDarkelf())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13735 && (pc.get_sex() != 0 || !pc.isDragonKnight())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13737 && (pc.get_sex() != 1 || !pc.isDragonKnight())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13739 && (pc.get_sex() != 0 || !pc.isIllusionist())) {
                    isUseItem = false;
                }
                else if (poly.getPolyId() == 13741 && (pc.get_sex() != 1 || !pc.isIllusionist())) {
                    isUseItem = false;
                }
                else {
                    L1PolyMorph.doPoly(pc, poly.getPolyId(), time, 1);
                    isUseItem = true;
                }
            }
            if (isUseItem) {
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            else {
                pc.sendPackets(new S_ServerMessage(181));
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            pc.setItemPoly(false);
            pc.setPolyScroll(null);
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void usePolyBook(L1PcInstance pc, L1ItemInstance item, String s) {
        try {
            L1PolyMorph poly = PolyTable.get().getTemplate(s);
            int time = 1800;
            if (item.getBless() == 0) {
                time = 2100;
            }
            if (item.getBless() == 128) {
                time = 2100;
            }
            boolean isUseItem = false;
            if (s.equals("none")) {
                if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
                    isUseItem = true;
                }
                else {
                    L1PolyMorph.undoPoly(pc);
                    isUseItem = true;
                }
            }
            else {
                L1PolyMorph.doPoly(pc, poly.getPolyId(), time, 1);
                isUseItem = true;
            }
            if (isUseItem) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            else {
                pc.sendPackets(new S_ServerMessage(181));
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            pc.setItemPoly(false);
            pc.setPolyScroll(null);
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void usePolyBookcouitem(L1PcInstance pc, L1ItemInstance item, String s) {
        try {
            L1PolyMorph poly = PolyTable.get().getTemplate(s);
            int time = 1800;
            if (item.getBless() == 0) {
                time = 2100;
            }
            if (item.getBless() == 128) {
                time = 2100;
            }
            boolean isUseItem = false;
            if (s.equals("none")) {
                if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
                    isUseItem = true;
                }
                else {
                    L1PolyMorph.undoPoly(pc);
                    isUseItem = true;
                }
            }
            else {
                L1PolyMorph.doPoly(pc, poly.getPolyId(), time, 1);
                isUseItem = true;
            }
            if (isUseItem) {
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            else {
                pc.sendPackets(new S_ServerMessage(181));
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
            pc.setItemPoly(false);
            pc.setPolyScroll(null);
            pc.getInventory().removeItem(item, 1L);
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void usePhantomTeleport(L1PcInstance pc, String cmd) {
        try {
            int x = 0;
            int y = 0;
            short mapid = 0;
            switch (cmd.hashCode()) {
                case 97: {
                    if (!cmd.equals("a")) {
                        break;
                    }
                    else {
                        x = 32797;
                        y = 32799;
                        mapid = 3301;
                        break;
                    }
                }
                case 98: {
                    if (!cmd.equals("b")) {
                        break;
                    }
                    else {
                        x = 32797;
                        y = 32799;
                        mapid = 3302;
                        break;
                    }
                }
                case 99: {
                    if (!cmd.equals("c")) {
                        break;
                    }
                    else {
                        x = 32797;
                        y = 32799;
                        mapid = 3303;
                        break;
                    }
                }
                case 100: {
                    if (!cmd.equals("d")) {
                        break;
                    }
                    else {
                        x = 32668;
                        y = 32864;
                        mapid = 3304;
                        break;
                    }
                }
                case 101: {
                    if (!cmd.equals("e")) {
                        break;
                    }
                    else {
                        x = 32668;
                        y = 32864;
                        mapid = 3305;
                        break;
                    }
                }
                case 102: {
                    if (!cmd.equals("f")) {
                        break;
                    }
                    else {
                        x = 32717;
                        y = 32871;
                        mapid = 3306;
                        break;
                    }
                }
                case 103: {
                    if (!cmd.equals("g")) {
                        break;
                    }
                    else {
                        x = 32668;
                        y = 32864;
                        mapid = 3307;
                        break;
                    }
                }
                case 104: {
                    if (!cmd.equals("h")) {
                        break;
                    }
                    else {
                        x = 32668;
                        y = 32864;
                        mapid = 3308;
                        break;
                    }
                }
                case 105: {
                    if (!cmd.equals("i")) {
                        break;
                    }
                    else {
                        x = 32668;
                        y = 32864;
                        mapid = 3309;
                        break;
                    }
                }
                case 106: {
                    if (!cmd.equals("j")) {
                        break;
                    }
                    else {
                        x = 32797;
                        y = 32799;
                        mapid = 3310;
                        break;
                    }
                }
                case 107: {
                    if (!cmd.equals("k")) {
                        break;
                    }
                    else {
                        x = 32760;
                        y = 32894;
                        mapid = 7100;
                        break;
                    }
                }
                case 108: {
                    if (!cmd.equals("l")) {
                        break;
                    }
                    else {
                        x = 32692;
                        y = 32903;
                        mapid = 7100;
                        break;
                    }
                }
            }
            L1Teleport.teleport(pc, x, y, mapid, pc.getHeading(), true);
            pc.setPhantomTeleport(false);
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void questDel(L1Quest quest) {
        try {
            if (quest.is_del()) {
                this._pc.setTempID(quest.get_id());
                String over = null;
                if (this._pc.getQuest().isEnd(quest.get_id())) {
                    over = "完成任務";
                }
                else {
                    over = String.valueOf(this._pc.getQuest().get_step(quest.get_id())) + " / " + quest.get_difficulty();
                }
                String[] info = { quest.get_questname(), Integer.toString(quest.get_questlevel()), over };
                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_qi2", info));
            }
            else {
                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_q_not5"));
            }
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void isDel(L1Quest quest) {
        try {
            if (quest.is_del()) {
                QuestClass.get().stopQuest(this._pc, quest.get_id());
                CharacterQuestReading.get().delQuest(this._pc.getId(), quest.get_id());
                String[] info = { quest.get_questname(), Integer.toString(quest.get_questlevel()) };
                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_qi3", info));
            }
            else {
                this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_q_not5"));
            }
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void showStartQuest(L1PcInstance pc, int objid) {
        try {
            pc.get_otherList().QUESTMAP.clear();
            int key = 0;
            for (int i = QuestTable.MINQID; i <= QuestTable.MAXQID; ++i) {
                L1Quest value = QuestTable.get().getTemplate(i);
                if (value != null) {
                    if (!pc.getQuest().isEnd(value.get_id())) {
                        if (pc.getQuest().isStart(value.get_id())) {
                            pc.get_otherList().QUESTMAP.put((Integer)(key++), value);
                        }
                    }
                }
            }
            if (pc.get_otherList().QUESTMAP.size() <= 0) {
                pc.sendPackets(new S_NPCTalkReturn(objid, "y_q_not7"));
            }
            else {
                L1ActionShowHtml show = new L1ActionShowHtml(pc);
                show.showQuestMap(0);
            }
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void showQuest(L1PcInstance pc, int objid) {
        try {
            pc.get_otherList().QUESTMAP.clear();
            int key = 0;
            for (int i = QuestTable.MINQID; i <= QuestTable.MAXQID; ++i) {
                L1Quest value = QuestTable.get().getTemplate(i);
                if (value != null && pc.getLevel() >= value.get_questlevel()) {
                    if (!pc.getQuest().isEnd(value.get_id())) {
                        if (!pc.getQuest().isStart(value.get_id())) {
                            if (value.check(pc)) {
                                pc.get_otherList().QUESTMAP.put((Integer)(key++), value);
                            }
                        }
                    }
                }
            }
            if (pc.get_otherList().QUESTMAP.size() <= 0) {
                pc.sendPackets(new S_NPCTalkReturn(objid, "y_q_not4"));
            }
            else {
                L1ActionShowHtml show = new L1ActionShowHtml(pc);
                show.showQuestMap(0);
            }
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void showQuestAll(L1PcInstance pc, int objid) {
        try {
            pc.get_otherList().QUESTMAP.clear();
            int key = 0;
            for (int i = QuestTable.MINQID; i <= QuestTable.MAXQID; ++i) {
                L1Quest value = QuestTable.get().getTemplate(i);
                if (value != null && value.check(pc)) {
                    pc.get_otherList().QUESTMAP.put((Integer)(key++), value);
                }
            }
            L1ActionShowHtml show = new L1ActionShowHtml(pc);
            show.showQuestMap(0);
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private static synchronized void checkSponsor(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        try {
            String AccountName = pc.getAccountName();
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select ordernumber,amount,payname,state from ezpay where state = 1 and payname ='" + AccountName + "'");
            rs = pstm.executeQuery();
            boolean isfind = false;
            while (rs.next() && rs != null) {
                int serial = rs.getInt("ordernumber");
                if (pc.getAccountName().equalsIgnoreCase(rs.getString("payname"))) {
                    isfind = true;
                    pstm2 = con.prepareStatement("update ezpay set state = 2 where ordernumber = ?");
                    pstm2.setInt(1, serial);
                    pstm2.execute();
                    int count = rs.getInt("amount");
                    GiveItem(pc, 44070, count);
                    PayBonus.getItem(pc, count);
                    pc.setpaycount(pc.getpaycount() + count);
                    writeSponsorlog(pc, count);
                }
            }
            if (!isfind) {
                pc.sendPackets(new S_ServerMessage("尚未偵測到關於您的贊助。"));
            }
        }
        catch (SQLException e) {
            L1ActionPc._log.error(e.getLocalizedMessage());
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
    }
    
    public static void GiveItem(L1PcInstance pc, int itemId, int count) {
        L1ItemInstance item = ItemTable.get().createItem(itemId);
        item.setCount((long)count);
        if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
            pc.getInventory().storeItem(item);
            pc.sendPackets(new S_ServerMessage("本次贊助共獲得： " + item.getLogName() + "。"));
        }
    }
    
    public static void writeSponsorlog(L1PcInstance player, int count) {
        try {
            File DeleteLog = new File("自動贊助\\贊助領取資料.log");
            if (DeleteLog.createNewFile()) {
                (L1ActionPc.out = new BufferedWriter(new FileWriter("自動贊助\\贊助領取資料.log", false))).write("※以下是玩家[領取贊助]的所有紀錄※\r\n");
                L1ActionPc.out.close();
            }
            (L1ActionPc.out = new BufferedWriter(new FileWriter("自動贊助\\贊助領取資料.log", true))).write("\r\n");
            L1ActionPc.out.write("來自帳號: " + player.getAccountName() + "來自ip: " + player.getNetConnection().getIp() + ",來自玩家: " + player.getName() + ",領取了: " + count + " 個 " + ",<領取時間:" + new Timestamp(System.currentTimeMillis()) + ">" + "\r\n");
            L1ActionPc.out.close();
        }
        catch (IOException e) {
            System.out.println("以下是錯誤訊息: " + e.getMessage());
        }
    }
    
    public static void initCharStatus(L1PcInstance pc, int inithp, int initmp, int str, int intel, int wis, int dex, int con, int cha) {
        pc.addBaseMaxHp((short)(inithp - pc.getBaseMaxHp()));
        pc.addBaseMaxMp((short)(initmp - pc.getBaseMaxMp()));
        pc.addBaseStr((int)(byte)(str - pc.getBaseStr()));
        pc.addBaseInt((int)(byte)(intel - pc.getBaseInt()));
        pc.addBaseWis((int)(byte)(wis - pc.getBaseWis()));
        pc.addBaseDex((int)(byte)(dex - pc.getBaseDex()));
        pc.addBaseCon((int)(byte)(con - pc.getBaseCon()));
        pc.addBaseCha((int)(byte)(cha - pc.getBaseCha()));
        pc.getQuest().set_step(85, 0);
        pc.getQuest().set_step(86, 0);
        pc.getQuest().set_step(87, 0);
        pc.getQuest().set_step(88, 0);
        pc.getQuest().set_step(89, 0);
        pc.getQuest().set_step(90, 0);
    }
    
    private boolean checkvalid(L1PcInstance pc, String cmd) {
        int initpoint = C_CreateChar.ORIGINAL_AMOUNT[pc.getType()];
        if (pc.isInCharReset()) {
            if (cmd.equalsIgnoreCase("initStr")) {
                if (pc.getBaseStr() >= C_CreateChar.MAX_STR[pc.getType()]) {
                    pc.sendPackets(new S_ServerMessage("該能力初始已達上限"));
                    return false;
                }
                else if (pc.getBaseStr() < 20) {
                    if (initpoint - pc.getTempInitPoint() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("initInt")) {
                if (pc.getBaseInt() >= C_CreateChar.MAX_INT[pc.getType()]) {
                    pc.sendPackets(new S_ServerMessage("該能力初始已達上限"));
                    return false;
                }
                else if (pc.getBaseInt() < 20) {
                    if (initpoint - pc.getTempInitPoint() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("initWis")) {
                if (pc.getBaseWis() >= C_CreateChar.MAX_WIS[pc.getType()]) {
                    pc.sendPackets(new S_ServerMessage("該能力初始已達上限"));
                    return false;
                }
                else if (pc.getBaseWis() < 20) {
                    if (initpoint - pc.getTempInitPoint() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("initDex")) {
                if (pc.getBaseDex() >= C_CreateChar.MAX_DEX[pc.getType()]) {
                    pc.sendPackets(new S_ServerMessage("該能力初始已達上限"));
                    return false;
                }
                else if (pc.getBaseDex() < 20) {
                    if (initpoint - pc.getTempInitPoint() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("initCon")) {
                if (pc.getBaseCon() >= C_CreateChar.MAX_CON[pc.getType()]) {
                    pc.sendPackets(new S_ServerMessage("該能力初始已達上限"));
                    return false;
                }
                else if (pc.getBaseCon() < 20) {
                    if (initpoint - pc.getTempInitPoint() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("initCha")) {
                if (pc.getBaseCha() >= C_CreateChar.MAX_CHA[pc.getType()]) {
                    pc.sendPackets(new S_ServerMessage("該能力初始已達上限"));
                    return false;
                }
                else if (pc.getBaseCha() < 20) {
                    if (initpoint - pc.getTempInitPoint() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("Str")) {
                if (pc.getBaseStr() < ConfigAlt.BaseResetPOWER) {
                    if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("Int")) {
                if (pc.getBaseInt() < ConfigAlt.BaseResetPOWER) {
                    if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("Wis")) {
                if (pc.getBaseWis() < ConfigAlt.BaseResetPOWER) {
                    if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("Dex")) {
                if (pc.getBaseDex() < ConfigAlt.BaseResetPOWER) {
                    if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("Con")) {
                if (pc.getBaseCon() < ConfigAlt.BaseResetPOWER) {
                    if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("Cha")) {
                if (pc.getBaseCha() < ConfigAlt.BaseResetPOWER) {
                    if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("exStr")) {
                if (pc.getBaseStr() < ConfigAlt.POWERMEDICINE) {
                    if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("exInt")) {
                if (pc.getBaseInt() < ConfigAlt.POWERMEDICINE) {
                    if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("exWis")) {
                if (pc.getBaseWis() < ConfigAlt.POWERMEDICINE) {
                    if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("exDex")) {
                if (pc.getBaseDex() < ConfigAlt.POWERMEDICINE) {
                    if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("exCon")) {
                if (pc.getBaseCon() < ConfigAlt.POWERMEDICINE) {
                    if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
                }
            }
            else if (cmd.equalsIgnoreCase("exCha")) {
                if (pc.getBaseCha() < ConfigAlt.POWERMEDICINE) {
                    if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
                        return true;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("已經沒有多餘的點數了。"));
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
                }
            }

            else if (cmd.equalsIgnoreCase("1")) {
                if (pc.getTempMaxLevel() - pc.getTempLevel() >= 1) {
                    return true;
                }
                else {
                    pc.sendPackets(new S_ServerMessage("無法再繼續升級了。"));
                }
            }
            else if (cmd.equalsIgnoreCase("10")) {
                if (pc.getTempMaxLevel() - pc.getTempLevel() >= 10) {
                    return true;
                }
                else {
                    pc.sendPackets(new S_ServerMessage("無法再繼續升級了。"));
                }
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage("必須在人物重置模式下才能使用！"));
        }
        return false;
    }
    
    public static void checkInitPower(L1PcInstance pc) {
        int initpoint = C_CreateChar.ORIGINAL_AMOUNT[pc.getType()];
        String msg0 = "初始能力點數";
        String msg2 = String.valueOf(initpoint - pc.getTempInitPoint());
        String msg3 = "請選擇想要提升的屬性：";
        if (initpoint - pc.getTempInitPoint() > 0) {
            String[] msgs = { msg0, msg2, msg3 };
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "charreset1", msgs));
        }
        else {
            if (pc.getLevel() == 1) {
                CharacterTable.saveCharStatus(pc);
            }
            checkLevelUp(pc);
        }
    }
    
    private static void checkLevelUp(L1PcInstance pc) {
        String msg0 = "提升等級";
        String msg2 = "0";
        String msg3 = "要提升多少等級呢?";
        String msg4 = "";
        String msg5 = "";
        if (pc.getTempMaxLevel() - pc.getTempLevel() > 10 && pc.getTempLevel() + 10 < 51) {
            msg4 = "提升 1 級";
            msg5 = "提升 10 級";
        }
        else {
            msg4 = "提升 1 級";
            msg5 = "";
        }
        if (pc.getTempMaxLevel() - pc.getTempLevel() > 0 && pc.getTempLevel() < 50) {
            String[] msgs = { msg0, msg2, msg3, msg4, msg5 };
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "charreset2", msgs));
        }
        else {
            checkBonusPower(pc);
        }
    }
    
    private static void checkBonusPower(L1PcInstance pc) {
        String msg0 = "等級獎勵點數";
        String msg2 = String.valueOf(pc.getTempMaxLevel() - pc.getTempLevel());
        String msg3 = "請選擇想要提升的屬性：";
        if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
            String[] msgs = { msg0, msg2, msg3 };
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "charreset3", msgs));
        }
        else {
            checkElixirPower(pc);
        }
    }
    
    private static void checkElixirPower(L1PcInstance pc) {
// ★ 六屬性全滿 → 自動刪除「剩餘」萬能藥點數 ★
if (pc.getBaseStr() >= ConfigAlt.POWERMEDICINE
 && pc.getBaseInt() >= ConfigAlt.POWERMEDICINE
 && pc.getBaseWis() >= ConfigAlt.POWERMEDICINE
 && pc.getBaseDex() >= ConfigAlt.POWERMEDICINE
 && pc.getBaseCon() >= ConfigAlt.POWERMEDICINE
 && pc.getBaseCha() >= ConfigAlt.POWERMEDICINE) {

    // 只刪「剩餘沒用完的萬能藥點數」
    pc.setElixirStats(pc.getTempElixirstats());
    pc.setTempElixirstats(pc.getTempElixirstats());

    // 清掉其他暫存點數，避免殘留
    pc.setTempInitPoint(0);
    pc.setTempLevel(pc.getLevel());
    pc.setTempMaxLevel(pc.getLevel());

    String[] msgs = {
        "重置完成。",
        "0",
        "六項屬性已達上限，已自動刪除剩餘萬能藥點數。"
    };
    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "charreset5", msgs));
    return;
}


    // ===== 原本的流程完全不動 =====
        String msg0 = "萬能藥點數";
        String msg2 = String.valueOf(pc.getElixirStats() - pc.getTempElixirstats());
        String msg3 = "請選擇想要提升的屬性：";
        String msg4 = "力量";
        String msg5 = "智力";
        String msg6 = "精神";
        String msg7 = "敏捷";
        String msg8 = "體質";
        String msg9 = "魅力";
        if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
            String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9 };
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "charreset4", msgs));
        }
        else {
            msg0 = "重置完成。";
            msg2 = String.valueOf(pc.getElixirStats() - pc.getTempElixirstats());
            msg3 = "已完成所有點數的分配！";
            String[] msgs = { msg0, msg2, msg3 };
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "charreset5", msgs));
        }
    }
    
    private void setLevelUp(L1PcInstance pc, int addLv) {
        pc.setTempLevel(pc.getTempLevel() + addLv);
        for (int i = 0; i < addLv; ++i) {
            short randomHp = CalcStat.calcStatHp(pc.getType(), (int)pc.getBaseMaxHp(), pc.getBaseCon(), pc.getOriginalHpup());
            short randomMp = CalcStat.calcStatMp(pc.getType(), (int)pc.getBaseMaxMp(), pc.getBaseWis(), pc.getOriginalMpup());
            pc.addBaseMaxHp(randomHp);
            pc.addBaseMaxMp(randomMp);
        }
        pc.setExp(ExpTable.getExpByLevel(pc.getTempLevel()));
        pc.resetLevel();
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_OwnCharStatus(pc));
        pc.sendPackets(new S_PacketBox(132, pc.getEr()));
    }
    
    private static void saveNewCharStatus(L1PcInstance pc) {
        pc.getInventory().consumeItem(49142, 1L);
        pc.setInCharReset(false);
        pc.refresh();
        pc.setExp(pc.getoldexp());
        pc.setCurrentHp(pc.getMaxHp());
        pc.setCurrentMp(pc.getMaxMp());
        if (pc.getTempMaxLevel() != pc.getLevel()) {
            pc.setLevel(pc.getTempMaxLevel());
            pc.setExp(ExpTable.getExpByLevel(pc.getTempMaxLevel()));
        }
        pc.setTempMaxLevel(0);
        if (pc.getLevel() > 50) {
            pc.setBonusStats(pc.getLevel() - 50);
        }
        else {
            pc.setBonusStats(0);
        }
        pc.sendPackets(new S_Paralysis(4, false));
        try {
            pc.save();
        }
        catch (Exception e) {
            L1ActionPc._log.error(e.getLocalizedMessage(), e);
        }
        L1Teleport.teleport(pc, 32628, 32772, (short)4, 4, false);
        RecordTable.get().reshp1(pc.getName());
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_OwnCharStatus(pc));
        pc.sendPackets(new S_PacketBox(132, pc.getEr()));
        L1PolyMorph.undoPoly(pc);
        RecordTable.get().reshp1(pc.getName());
        if (ConfigOther.restsavepclog) {
            pc.setExp(pc.getoldexp());
            pc.onChangeExp();
        }
        if (pc.getQuest().get_step(30677) == 1) {
            pc.getQuest().set_step(30677, 2);
            try {
                Thread.sleep(1000L);
            }
            catch (Exception ex) {}
            pc.setExp(pc.getoldexp());
            pc.onChangeExp();
        }
    }
    
private void showPage(int key) {
    try {
        // 取得任務資料
        L1Quest quest = this._pc.get_otherList().QUESTMAP.get((Integer) key);

        // 若 quest 為 null → 是真正的錯誤來源
        if (quest == null) {
            _log.error("showPage error: quest is null, key=" + key + ", pc=" + this._pc.getName());
            // 給玩家提示，不會卡死
            this._pc.sendPackets(new S_SystemMessage("任務資料錯誤或任務不存在。"));
            return;
        }

        this._pc.setTempID(quest.get_id());

        String over;
        if (this._pc.getQuest().isEnd(quest.get_id())) {
            over = "完成任務";
        } else {
            over = this._pc.getQuest().get_step(quest.get_id()) + " / " + quest.get_difficulty();
        }

        // 避免 quest 欄位為 null
        String questName = quest.get_questname() != null ? quest.get_questname() : "未知任務";
        String questLevel = Integer.toString(quest.get_questlevel());

        String[] info = { questName, questLevel, over, "" };

        this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_qi1", info));
    }
    catch (Exception e) {
        L1ActionPc._log.error("showPage exception: key=" + key + ", pc=" 
                              + this._pc.getName() + " " + e.getMessage(), e);
    }
}

    
    public static void clanLevel(L1PcInstance pc, int yy) {
        if (yy == 1) {
            int clan_level = 0;
            int clan_contribution = 0;
            int clan_adena = 0;
            int clanadena = 0;
            int clanadenacount = 0;
            int contribution = 0;
            if (pc.getClanid() != 0) {
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    clan_level = clan.getClanLevel();
                    clan_contribution = clan.getClanContribution();
                    clan_adena = clan.getclanadena();
                }
            }
            if (clan_level >= 10) {
                pc.sendPackets(new S_SystemMessage("你所屬的血盟已強化至最高等級。"));
                return;
            }
            else {
                if (clan_level == 0) {
                    clanadena = ConfigClan.clanlv1;
                    clanadenacount = ConfigClan.clanlvcount1;
                    contribution = ConfigClan.clanenergy1;
                }
                else if (clan_level == 1) {
                    clanadena = ConfigClan.clanlv2;
                    clanadenacount = ConfigClan.clanlvcount2;
                    contribution = ConfigClan.clanenergy2;
                }
                else if (clan_level == 2) {
                    clanadena = ConfigClan.clanlv3;
                    clanadenacount = ConfigClan.clanlvcount3;
                    contribution = ConfigClan.clanenergy3;
                }
                else if (clan_level == 3) {
                    clanadena = ConfigClan.clanlv4;
                    clanadenacount = ConfigClan.clanlvcount4;
                    contribution = ConfigClan.clanenergy4;
                }
                else if (clan_level == 4) {
                    clanadena = ConfigClan.clanlv5;
                    clanadenacount = ConfigClan.clanlvcount5;
                    contribution = ConfigClan.clanenergy5;
                }
                else if (clan_level == 5) {
                    clanadena = ConfigClan.clanlv6;
                    clanadenacount = ConfigClan.clanlvcount6;
                    contribution = ConfigClan.clanenergy6;
                }
                else if (clan_level == 6) {
                    clanadena = ConfigClan.clanlv7;
                    clanadenacount = ConfigClan.clanlvcount7;
                    contribution = ConfigClan.clanenergy7;
                }
                else if (clan_level == 7) {
                    clanadena = ConfigClan.clanlv8;
                    clanadenacount = ConfigClan.clanlvcount8;
                    contribution = ConfigClan.clanenergy8;
                }
                else if (clan_level == 8) {
                    clanadena = ConfigClan.clanlv9;
                    clanadenacount = ConfigClan.clanlvcount9;
                    contribution = ConfigClan.clanenergy9;
                }
                else if (clan_level == 9) {
                    clanadena = ConfigClan.clanlv10;
                    clanadenacount = ConfigClan.clanlvcount10;
                    contribution = ConfigClan.clanenergy10;
                }
                boolean isCreate = true;
                int newClanLevel = clan_level + 1;
                if (clan_adena < clanadenacount) {
                    pc.sendPackets(new S_ServerMessage("血盟資金不足" + clanadenacount));
                    isCreate = false;
                    return;
                }
                else if (contribution > clan_contribution) {
                    pc.sendPackets(new S_SystemMessage("血盟目前貢獻度不足【" + contribution + "】點，請再提升血盟貢獻度吧。"));
                    isCreate = false;
                    return;
                }
                else if (isCreate) {
                    pc.getInventory().consumeItem(clanadena, (long)clanadenacount);
                    L1Clan clan2 = WorldClan.get().getClan(pc.getClanname());
                    clan2.setClanLevel(newClanLevel);
                    clan2.setClanContribution(clan2.getClanContribution() - contribution);
                    clan2.setclanadena(clan2.getclanadena() - clanadenacount);
                    ClanTable.getInstance().updateClan(clan2);
                    pc.sendPackets(new S_SystemMessage("你的血盟等級已強化為 " + newClanLevel + " 級。"));
                    L1PcInstance[] clanMember = clan2.getOnlineClanMember();
                    if (clanMember.length > 0) {
                        pc.sendPackets(new S_SystemMessage("你所屬的血盟(" + pc.getClanname() + ")等級已強化為 " + newClanLevel + " 級"));
                    }
                }
            }
        }
        else if (yy == 3) {
            if (pc.getQuest().get_step(8541) == 10) {
                pc.sendPackets(new S_SystemMessage("今日貢獻金幣已達上限"));
                return;
            }
            else {
                if (!pc.getClanname().equals(pc.getClanNameContribution())) {
                    pc.setClanNameContribution(pc.getClanname());
                }
                if (pc.getQuest().get_step(8541) == 0) {
                    pc.getQuest().set_step(8541, 1);
                }
                else if (pc.getQuest().get_step(8541) == 1) {
                    pc.getQuest().set_step(8541, 2);
                }
                else if (pc.getQuest().get_step(8541) == 2) {
                    pc.getQuest().set_step(8541, 3);
                }
                else if (pc.getQuest().get_step(8541) == 3) {
                    pc.getQuest().set_step(8541, 4);
                }
                else if (pc.getQuest().get_step(8541) == 4) {
                    pc.getQuest().set_step(8541, 5);
                }
                else if (pc.getQuest().get_step(8541) == 5) {
                    pc.getQuest().set_step(8541, 6);
                }
                else if (pc.getQuest().get_step(8541) == 6) {
                    pc.getQuest().set_step(8541, 7);
                }
                else if (pc.getQuest().get_step(8541) == 7) {
                    pc.getQuest().set_step(8541, 8);
                }
                else if (pc.getQuest().get_step(8541) == 8) {
                    pc.getQuest().set_step(8541, 9);
                }
                else if (pc.getQuest().get_step(8541) == 9) {
                    pc.getQuest().set_step(8541, 10);
                }
                L1Clan clan3 = WorldClan.get().getClan(pc.getClanname());
                pc.setclanadena(pc.getclanadena() + ConfigClan.PcClanAdena);
                clan3.setclanadena(clan3.getclanadena() + ConfigClan.PcClanAdena);
                pc.getInventory().consumeItem(40308, (long)ConfigClan.PcClanAdena);
                ClanTable.getInstance().updateClan(clan3);
                pc.sendPackets(new S_SystemMessage("你給予目前的血盟資金有【" + pc.getclanadena() + "】。"));
                pc.sendPackets(new S_SystemMessage("你的血盟目前所獲得的資金總合:【" + clan3.getclanadena() + "】。"));
            }
        }
        else if (yy == 5) {
            if (pc.getQuest().get_step(8545) == 1) {
                pc.sendPackets(new S_SystemMessage("元寶貢獻能量今日已達上限"));
                return;
            }
            else {
                if (!pc.getClanname().equals(pc.getClanNameContribution())) {
                    pc.setClanNameContribution(pc.getClanname());
                }
                L1Clan clan3 = WorldClan.get().getClan(pc.getClanname());
                clan3.setClanContribution(clan3.getClanContribution() + ConfigClan.ClanContribution);
                ClanTable.getInstance().updateClan(clan3);
                pc.setClanContribution(pc.getClanContribution() + ConfigClan.ClanContribution);
                pc.getInventory().storeItem(92164, (long)ConfigClan.ClanContribution);
                pc.sendPackets(new S_SystemMessage("獲得血盟貢獻幣:【" + ConfigClan.ClanContribution + "】個。"));
                pc.sendPackets(new S_SystemMessage("你給予目前的血盟貢獻度有【" + pc.getClanContribution() + "】點。"));
                pc.sendPackets(new S_SystemMessage("你的血盟目前所獲得的貢獻度總合有【" + clan3.getClanContribution() + "】點。"));
                pc.getQuest().set_step(8545, 1);
                pc.getInventory().consumeItem(44070, (long)ConfigClan.ClanItem44070);
            }
        }
        else if (yy == 6) {
            if (pc.getQuest().get_step(8546) == 1) {
                pc.sendPackets(new S_SystemMessage("元寶貢獻金幣今日已達上限"));
                return;
            }
            else {
                if (!pc.getClanname().equals(pc.getClanNameContribution())) {
                    pc.setClanNameContribution(pc.getClanname());
                }
                L1Clan clan3 = WorldClan.get().getClan(pc.getClanname());
                clan3.setclanadena(clan3.getclanadena() + ConfigClan.ClanContribution_1);
                ClanTable.getInstance().updateClan(clan3);
                pc.setclanadena(pc.getclanadena() + ConfigClan.ClanContribution_1);
                pc.sendPackets(new S_SystemMessage("你給予目前的血盟資金有:【" + pc.getclanadena() + "】。"));
                pc.sendPackets(new S_SystemMessage("你的血盟目前所獲得的資金總合有【" + clan3.getclanadena() + "】。"));
                pc.getQuest().set_step(8546, 1);
                pc.getInventory().consumeItem(44070, (long)ConfigClan.ClanItem44070_1);
            }
        }
    }
    
    private static void newai(L1PcInstance pc) {
        String[] info = new String[34];
        info[1] = String.valueOf(pc.getnewai1());
        info[2] = String.valueOf(pc.getnewai2());
        info[3] = String.valueOf(pc.getnewai3());
        info[4] = String.valueOf(pc.getnewai4());
        info[5] = String.valueOf(pc.getnewai5());
        info[6] = String.valueOf(pc.getnewai6());
        switch (L1ActionPc._random.nextInt(10) + 1) {
            case 1: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai", info));
                break;
            }
            case 2: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai1", info));
                break;
            }
            case 3: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai2", info));
                break;
            }
            case 4: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai3", info));
                break;
            }
            case 5: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai4", info));
                break;
            }
            case 6: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai5", info));
                break;
            }
            case 7: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai6", info));
                break;
            }
            case 8: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai7", info));
                break;
            }
            case 9: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai8", info));
                break;
            }
            case 10: {
                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "newai9", info));
                break;
            }
        }
    }
    
    public void BadEnemyList(L1PcInstance pc) {
        StringBuilder msg = new StringBuilder();
        for (String name : pc.InBadEnemyList()) {
            msg.append(name + ",");
        }
        String[] clientStrAry = msg.toString().split(",");
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "x_autolist4", clientStrAry));
    }
    
    private void declareWar(L1PcInstance pc, String value) {
        if (pc == null || pc.isDead() || pc.isGhost()) {
            return;
        }
        else {
            int id = -1;
            try {
                id = Integer.parseInt(value);
            }
            catch (Exception ex) {}
            if (id == -1) {
                return;
            }
            else if (!ServerWarExecutor.get().isNowWar(id)) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.sendPackets(new S_SystemMessage("\\aG攻城戰時尚未開始"));
                return;
            }
            else if (!pc.isCrown()) {
                pc.sendPackets(new S_ServerMessage(478));
                return;
            }
            else if (pc.getClan() == null) {
                return;
            }
            else {
                try {
                    pc.setDeclareId(id);
                    BinaryOutputStream os = new BinaryOutputStream();
                    os.writeC(227);
                    os.writeC(0);
                    os.writeS(WorldClan.get().getCastleClanName(id));
                    new C_War().start(os.getBytes(), pc.getNetConnection());
                    os.close();
                }
                catch (Exception ex2) {}
                return;
            }
        }
    }
}
