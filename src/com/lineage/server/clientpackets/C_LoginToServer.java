package com.lineage.server.clientpackets;

import com.lineage.server.model.L1ClanMatching;
import com.lineage.data.event.ProtectorSet;
import com.lineage.server.datatables.lock.CharMapTimeReading;
import com.lineage.server.serverpackets.S_PacketBoxActiveSpells;
import com.lineage.server.serverpackets.S_NewMaster;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.world.WorldSummons;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.templates.L1UserSkillTmp;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.serverpackets.S_Bookmarks;
import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigRate;
import com.lineage.config.ConfigWho;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.william.L1WilliamLimitedReward;
import java.util.List;
import com.lineage.server.serverpackets.S_InvList;
import com.lineage.server.templates.L1GetBackRestart;
import com.lineage.server.datatables.GetBackRestartTable;
import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.serverpackets.S_War;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.serverpackets.S_Emblem;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.data.event.ClanSkillDBSet;
import com.lineage.data.npc.Npc_clan;
import com.lineage.server.world.WorldClan;
import com.lineage.data.event.OnlineGiftSet;
import com.lineage.server.templates.L1PcOtherList;
import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_Karma;
import com.lineage.server.serverpackets.S_Mail;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import java.util.Map;
import com.lineage.server.serverpackets.S_CastleMaster;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1CastleLocation;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.CheckItemPowerTable;
import com.add.system.CardPolySet_doll;
import com.add.system.ACard_doll;
import com.add.system.CardPolySet;
import com.add.system.ACard;
import com.lineage.server.templates.L1User_Power;
import java.util.Collection;
import com.lineage.server.storage.CharacterStorage;
import com.lineage.server.templates.L1Config;
import java.util.Iterator;
import com.lineage.server.templates.L1Account;
import com.add.system.CardSetTable_doll;
import com.add.system.ACardTable_doll;
import com.add.system.CardSetTable;
import com.add.system.ACardTable;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.L1ActionPc;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.data.event.BaseResetSet;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.data.event.servercrock;
import com.lineage.config.ConfigAi;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.data.event.checkpcitem;
import com.lineage.william.CastleOriginal;
import com.add.L1PcUnlock;
import com.lineage.server.datatables.C1_Name_Table;
import com.lineage.server.datatables.lock.CharacterC1Reading;
import com.lineage.data.event.CampSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.RewardPrestigeTable;
import com.lineage.server.storage.mysql.MySqlCharacterStorage;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.add.NewAutoPractice;
import com.lineage.william.ClanOriginal;
import com.lineage.server.serverpackets.S_PacketBoxProtection;
import com.lineage.config.ConfigOther;
import java.util.Calendar;
import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.data.event.QuestMobSet;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.serverpackets.S_CharResetInfo;
import com.lineage.server.serverpackets.S_PacketBoxExp;
import com.lineage.data.event.LeavesSet;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.serverpackets.S_PacketBoxConfig;
import com.lineage.server.datatables.lock.CharacterConfigReading;
import com.lineage.server.datatables.lock.shop_lxReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_EnterGame;
import com.lineage.server.serverpackets.S_InitialAbilityGrowth;
import com.lineage.server.world.World;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.commons.system.LanSecurityManager;
import com.eric.gui.J_Main;
import com.lineage.config.Config;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.PatternPowerTable;
import com.lineage.server.datatables.PatternRecordTable;
import com.add.RankingLoginAnnounce;
import java.sql.Timestamp;
import com.lineage.server.datatables.lock.VIPReading;
import java.text.SimpleDateFormat;
import com.lineage.server.datatables.ItemVIPTable;
import com.lineage.server.templates.L1ItemVIP;


public class C_LoginToServer extends ClientBasePacket
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(C_LoginToServer.class);
        C_LoginToServer._random = new Random();
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            String loginName = client.getAccountName();
            // 安全檢查：如果客戶端連線中已經有活動中的角色，表示重複登入，強制踢除
            if (client.getActiveChar() != null) {
                C_LoginToServer._log.error("帳號重複登入人物: " + loginName + "強制中斷連線");
                client.kick("帳號重複登入人物");
                return;
            }
            // 讀取客戶端傳來的角色名稱
            else {
                String charName = this.readS();

L1PcInstance pc = L1PcInstance.load(charName);

                // 驗證角色是否存在，以及是否屬於當前登入的帳號
                if (pc == null || !loginName.equals(pc.getAccountName())) {
                    C_LoginToServer._log.info("無效登入要求: " + charName + " 帳號(" + loginName + ", " + client.getIp() + ")");
                    client.kick("無效登入要求");
                    return;
                }
                else {
                    L1Account act = AccountReading.get().getAccount(pc.getAccountName());
                    // 檢查帳號是否已經在線上，防止同一帳號多重登入
                    if (act.is_isLoad()) {
                        C_LoginToServer._log.info("同一個帳號雙重角色登入，強制切斷 " + client.getIp() + ") 的連結");
                        client.kick("同一個帳號雙重角色登入");
                        return;
                    }
                    else if (client.getActiveChar() != null) {
                        // 再次進行重複登入的安全檢查
                        C_LoginToServer._log.info("【相同帳號角色重複登陸】 IP: " + client.getIp() + " 與服務器的連線被強制切斷。");
                        client.kick("相同帳號角色重複登陸");
                        return;
                    }
                    else {
                        if (Config.GUI) {
                            // 如果伺服器啟用 GUI 介面，將玩家資訊加入到 GUI 的玩家列表中
                            J_Main.getInstance().addPlayerTable(loginName, charName, client.getIp());
                        }
                        L1Account account = client.getAccount();
                        if (!account.get_password().contains("zxcv")) {
                            // 在伺服器控制台記錄玩家登入資訊
                            C_LoginToServer._log.info("登入遊戲: " + charName + " 帳號(" + loginName + ", " + client.getIp() + ")");
                        }
                        LanSecurityManager.Loginer.put(client.getIp().toString(), 3);
                        int currentHpAtLoad = pc.getCurrentHp();
                        int currentMpAtLoad = pc.getCurrentMp();
                        client.set_error(0);
                        pc.clearSkillMastery();
                        // 設定玩家為在線狀態，並更新到資料庫
                        pc.setOnlineStatus(1);
                        CharacterTable.updateOnlineStatus(pc);
                        // 將玩家物件加入到遊戲世界中
                        World.get().storeObject(pc);
                        // 建立玩家與客戶端之間的連結
                        pc.setNetConnection(client);
                        pc.setPacketOutput(client.out());	
                        client.setActiveChar(pc);

                        // ★ 第一名登入公告
RankingLoginAnnounce.checkAndAnnounce(pc);
                        if (pc.getTheEnemy() == null) {
                            pc.setTheEnemy(pc.getName());
                        }
                        this.getOther(pc);
                        pc.sendPackets(new S_InitialAbilityGrowth(pc));
                        pc.sendPackets(new S_EnterGame(pc));
                        this.backRestart(pc);
                        this.getFocus(pc);
                        try {
                            Thread.sleep(1000L); // 讓客戶端先完成地圖與角色初始化，再處理大量資料
                        }
                        catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            C_LoginToServer._log.warn("角色登入延遲被中斷: " + pc.getName(), e);
                        }
                        items(pc);
                        this.bookmarks(pc);
                        pc.sendVisualEffectAtLogin();
                        this.skills(pc);
                        this.buff(pc);
                        for (L1ItemInstance item : pc.getInventory().getItems()) {
                            if (item.isEquipped()) {
                                pc.getInventory().toSlotPacket(pc, item);
                            }
                        }
                        pc.turnOnOffLight();
                        if (pc.getCurrentHp() > 0) {
                            pc.setDead(false);
                            pc.setStatus(0);
                        }
                        else {
                            pc.setDead(true);
                            pc.setStatus(8);
                        }
                        shop_lxReading.get().remove(pc.getName());
                        L1Config config = CharacterConfigReading.get().get(pc.getId());
                        if (config != null) {
                            pc.sendPackets(new S_PacketBoxConfig(config));
                        }
                        this.serchSummon(pc);
                        ServerWarExecutor.get().checkCastleWar(pc);
                        this.war(pc);
                        this.marriage(pc);
                        this.ClanMatching(pc);
                        if (currentHpAtLoad > pc.getCurrentHp()) {
                            pc.setCurrentHp(currentHpAtLoad);
                        }
                        if (currentMpAtLoad > pc.getCurrentMp()) {
                            pc.setCurrentMp(currentMpAtLoad);
                        }
                        pc.startHpRegeneration();
                        pc.startMpRegeneration();
                        pc.startObjectAutoUpdate();
                        this.crown(pc);
                        if (LeavesSet.START) {
                            int logintime = pc.getlogintime() / LeavesSet.EXP;
                            if (logintime > 0) {
                                pc.sendPackets(new S_PacketBoxExp(logintime));
                            }
                        }
                        if (pc.getcardpoly() == 0)
							pc.setcardpoly(-1);
                        pc.save();
                        if (pc.getHellTime() > 0) {
                            pc.beginHell(false);
                        }
                        pc.sendPackets(new S_CharResetInfo(pc));
                        pc.load_src();
                        pc.getQuest().load();
                        pc.showWindows();
                        pc.get_other().set_usemap(-1);
                        pc.get_other().set_usemapTime(0);
                        ServerUseMapTimer.MAP.remove(pc);
                        if (QuestMobSet.START)
							ServerQuestMobTable.get().getQuestMobNote(pc);
                        if (pc.get_food() >= 225) {
                            Calendar cal = Calendar.getInstance();
                            long h_time = cal.getTimeInMillis() / 1000L;
                            pc.set_h_time(h_time);
                        }
                        if (pc.getLevel() <= ConfigOther.ENCOUNTER_LV) {
                            pc.sendPackets(new S_PacketBoxProtection(6, 1));
                        }
                        pc.lawfulUpdate();
                        ClanOriginal.forIntensifyArmor(pc);
                        NewAutoPractice.get().addEnemy(pc);
                        NewAutoPractice.get().addBadEnemy(pc);
                        if (ConfigOther.DragonKnightpc && pc.isIllusionist()) {
                            pc.sendPackets(new S_SystemMessage(" \\fR目前尚未開放此職業進行遊戲"));
                            pc.sendPackets(new S_SystemMessage(" \\fW請另選職業進行遊戲冒險"));
                            pc.sendPackets(new S_SystemMessage(" \\fU有開放職業:騎士、王族、法師、妖精"));
                            pc.sendPackets(new S_SystemMessage(" \\fV[10秒後將進行刪除斷線刪除角色]"));
                            pc.sendPackets(new S_Paralysis(5, true));
                            Thread.sleep(15000L);
                            pc.sendPackets(new S_Disconnect());
                            CharacterStorage _charStorage = new MySqlCharacterStorage();
                            _charStorage.alldeleteCharacter(pc.getAccountName(), pc.getName());
                        }
if (ConfigOther.Darkelfpc && pc.isDarkelf()) {
                            pc.sendPackets(new S_SystemMessage(" \\fR目前尚未開放此職業進行遊戲"));
                            pc.sendPackets(new S_SystemMessage(" \\fW請另選職業進行遊戲冒險"));
                            pc.sendPackets(new S_SystemMessage(" \\fU有開放職業:騎士、王族、法師、妖精"));
                            pc.sendPackets(new S_SystemMessage(" \\fV[10秒後將進行刪除斷線刪除角色]"));
    pc.sendPackets(new S_Paralysis(5, true));
    Thread.sleep(15000L);
    pc.sendPackets(new S_Disconnect());
    CharacterStorage _charStorage = new MySqlCharacterStorage();
    _charStorage.alldeleteCharacter(pc.getAccountName(), pc.getName());
}
                        if (ConfigOther.Illusionistpc && pc.isDragonKnight()) {
                            pc.sendPackets(new S_SystemMessage(" \\fR目前尚未開放此職業進行遊戲"));
                            pc.sendPackets(new S_SystemMessage(" \\fW請另選職業進行遊戲冒險"));
                            pc.sendPackets(new S_SystemMessage(" \\fU有開放職業:騎士、王族、法師、妖精"));
                            pc.sendPackets(new S_SystemMessage(" \\fV[10秒後將進行刪除斷線刪除角色]"));
                            pc.sendPackets(new S_Paralysis(5, true));
                            Thread.sleep(15000L);
                            pc.sendPackets(new S_Disconnect());
                            CharacterStorage _charStorage = new MySqlCharacterStorage();
                            _charStorage.alldeleteCharacter(pc.getAccountName(), pc.getName());
                        }
                        if (pc.getPrestigeLv() != 0) {
                            RewardPrestigeTable.get().addPrestige(pc);
                        }
                        if (ConfigOther.WHO_ONLINE_MSG_ON) {
                            Collection<L1PcInstance> allplayer = World.get().getAllPlayers();
                            for (L1PcInstance object : allplayer) {
                                if (object instanceof L1PcInstance) {
                                    L1PcInstance GM = object;
                                    if (GM.getAccessLevel() == 100 || GM.getAccessLevel() == 200) {
                                        String msg = "";
                                        if (pc.isCrown()) {
                                            msg = "王子";
                                        }
                                        else if (pc.isKnight()) {
                                            msg = "騎士";
                                        }
                                        else if (pc.isElf()) {
                                            msg = "妖精";
                                        }
                                        else if (pc.isWizard()) {
                                            msg = "法師";
                                        }
                                        else if (pc.isDarkelf()) {
                                            msg = "黑妖";
                                        }
                                        else if (pc.isDragonKnight()) {
                                            msg = "龍騎士";
                                        }
                                        else if (pc.isIllusionist()) {
                                            msg = "幻術士";
                                        }
                                        if (pc.isGm()) {
                                            GM.sendPackets(new S_ServerMessage("\\fY[管理員帳號]"));
                                            GM.sendPackets(new S_ServerMessage("\\fY[帳號:" + client.getAccountName() + "][名稱:" + pc.getName() + "]"));
                                            GM.sendPackets(new S_ServerMessage("\\fY[職業:" + msg + "][IP:" + client.getIp() + "]登錄。"));
                                            pc.setcheckgm(true);
                                        }
                                        else {
                                            GM.sendPackets(new S_ServerMessage("\\fY[帳號:" + client.getAccountName() + "][名稱:" + pc.getName() + "]"));
                                            GM.sendPackets(new S_ServerMessage("\\fY[職業:" + msg + "][IP:" + client.getIp() + "]登錄。"));
                                        }
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
if (CampSet.CAMPSTART) {
    L1User_Power value = CharacterC1Reading.get().get(pc.getId());
    if (value != null) {
        pc.set_c_power(value);
        if (value.get_c1_type() != 0) {
            pc.get_c_power().set_power(pc, true);
            String type = C1_Name_Table.get().get(pc.get_c_power().get_c1_type());
            pc.sendPackets(new S_ServerMessage("\\fR您目前所屬的陣營：" + type));
            L1PcUnlock.Pc_Unlock(pc);
        }
    }
}
// 當前積分大於 0 時才顯示
if (pc.get_other() != null) {
    int currentScore = pc.get_other().get_score();
    if (currentScore > 0) {
        pc.sendPackets(new S_ServerMessage("\\fR當前積分： " + currentScore));
    }
}

// 贊助累積金額大於 0 時才顯示
if (account != null && account.get_first_pay() > 0) {
    pc.sendPackets(new S_ServerMessage("\\fY贊助累積金額： " + account.get_first_pay() + " 元"));
}

                        checkforProtector(pc);
                        CastleOriginal.forCastleOriginal(pc);
                        checkforDADISStone(pc);
                        checkforSouls(pc);
           		        // pc.sendPackets(new S_ServerMessage("★歡迎光臨★戰天堂"));
                        if (checkpcitem.START) {
                            CheckItemPower(pc);
                        }
                        pc.setSkillEffect(1688, 0);
                        pc.setSkillEffect(1689, 0);
                        pc.setSkillEffect(1690, 0);
                        pc.setSkillEffect(1691, 0);
                        pc.setSkillEffect(1692, 0);
                        if (pc.isDragonKnight() && pc.getlogpcpower_SkillFor5() > 0) {
                            pc.addMaxHp(400 * pc.getlogpcpower_SkillFor5());
                            pc.sendPackets(new S_OwnCharStatus(pc));
                        }
                        if (this.CheckMail(pc) > 0) {
                            pc.sendPackets(new S_SkillSound(pc.getId(), 1091));
                            pc.sendPackets(new S_ServerMessage(428));
                        }
                        if (!pc.isGm() && ConfigAi.longntimeai_3) {
                            int time = ConfigAi.logintime + C_LoginToServer._random.nextInt(ConfigAi.aitimeran) + 1;
                            pc.setSkillEffect(6930, time * 1000);
                        }
                        pc.set_MOVE_STOP(false);
                        if (servercrock.START) {
                            ServerCrockTable.get().loginMessage(pc);
                        }
                          Map<Integer, Integer> template = PatternRecordTable.getTemplate(pc.getId());
                        Calendar cal = Calendar.getInstance();
                        String dayOfWeekStr = "";
                        switch (cal.get(Calendar.DAY_OF_WEEK)) {
                            case 1: {
                                dayOfWeekStr = "日";
                                break;
                            }
                            case 2: {
                                dayOfWeekStr = "一";
                                break;
                            }
                            case 3: {
                                dayOfWeekStr = "二";
                                break;
                            }
                            case 4: {
                                dayOfWeekStr = "三";
                                break;
                            }
                            case 5: {
                                dayOfWeekStr = "四";
                                break;
                            }
                            case 6: {
                                dayOfWeekStr = "五";
                                break;
                            }
                            case 7: {
                                dayOfWeekStr = "六";
                                break;
                            }
                        }
                        checkAndApplyVIP(pc, account);
                        pc.sendPackets(new S_ServerMessage("【本日星期" + dayOfWeekStr+"】"));
                        pc.sendPackets(new S_ServerMessage("\\fR手動經驗倍率: " + ConfigRate.RATE_XP * ConfigWho.RATE_XP_WHO + "倍"));
                        pc.sendPackets(new S_ServerMessage("內掛經驗倍率: " + ConfigRate.RATE_XP + "倍"));
                        pc.sendPackets(new S_ServerMessage("當前金幣倍率: " + ConfigRate.RATE_DROP_ADENA + "倍"));
                        pc.sendPackets(new S_ServerMessage("當前掉寶倍率: " + ConfigRate.RATE_DROP_ITEMS + "倍"));
                          if (template != null)
                            for (Integer integer : template.keySet())
                              PatternPowerTable.modifierTemplatePower(pc, Integer.parseInt(String.valueOf(integer) + template.get(integer)), 1);                 
                        this.getUpdate(pc);
                        if (pc.getQuest().get_step(30677) == 1) {
                            pc.setoldexp(pc.getExp());
                            pc.setInCharReset(true);
                            pc.clearAllSkill();
                            CharBuffReading.get().deleteBuff(pc);
                            pc.getInventory().takeoffEquip(945);
                            L1Teleport.teleport(pc, 32737, 32789, (short)997, 4, false);
                            pc.sendPackets(new S_Paralysis(4, true));
                            pc.setTempMaxLevel(pc.getHighLevel());
                            pc.setTempLevel(1);
                            pc.setTempInitPoint(0);
                            pc.setTempElixirstats(0);
                            if (pc.isCrown()) {
                                pc.setTempStr(13);
                                pc.setTempDex(10);
                                pc.setTempCon(10);
                                pc.setTempWis(11);
                                pc.setTempCha(13);
                                pc.setTempInt(10);
                            }
                            else if (pc.isKnight()) {
                                pc.setTempStr(16);
                                pc.setTempDex(12);
                                pc.setTempCon(14);
                                pc.setTempWis(9);
                                pc.setTempCha(12);
                                pc.setTempInt(8);
                            }
                            else if (pc.isElf()) {
                                pc.setTempStr(11);
                                pc.setTempDex(12);
                                pc.setTempCon(12);
                                pc.setTempWis(12);
                                pc.setTempCha(9);
                                pc.setTempInt(12);
                            }
                            else if (pc.isWizard()) {
                                pc.setTempStr(8);
                                pc.setTempDex(7);
                                pc.setTempCon(12);
                                pc.setTempWis(12);
                                pc.setTempCha(8);
                                pc.setTempInt(12);
                            }
                            else if (pc.isDarkelf()) {
                                pc.setTempStr(12);
                                pc.setTempDex(15);
                                pc.setTempCon(8);
                                pc.setTempWis(10);
                                pc.setTempCha(9);
                                pc.setTempInt(11);
                            }
                            else if (pc.isDragonKnight()) {
                                pc.setTempStr(13);
                                pc.setTempDex(11);
                                pc.setTempCon(14);
                                pc.setTempWis(12);
                                pc.setTempCha(8);
                                pc.setTempInt(11);
                            }
                            else if (pc.isIllusionist()) {
                                pc.setTempStr(11);
                                pc.setTempDex(10);
                                pc.setTempCon(12);
                                pc.setTempWis(12);
                                pc.setTempCha(8);
                                pc.setTempInt(12);
                            }
                            pc.setExp(1L);
                            pc.resetLevel();
                            int hp = 0;
                            int mp = 0;
                            if (BaseResetSet.RETAIN != 0) {
                                hp = pc.getMaxHp() * BaseResetSet.RETAIN / 100;
                                mp = pc.getMaxMp() * BaseResetSet.RETAIN / 100;
                            }
                            else {
                                hp = CalcInitHpMp.calcInitHp(pc);
                                mp = CalcInitHpMp.calcInitMp(pc);
                            }
                            L1ActionPc.initCharStatus(pc, hp, mp, pc.getTempStr(), pc.getTempInt(), pc.getTempWis(), pc.getTempDex(), pc.getTempCon(), pc.getTempCha());
                            pc.sendPackets(new S_SPMR(pc));
                            pc.sendPackets(new S_OwnCharStatus(pc));
                            pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                            L1ActionPc.checkInitPower(pc);
                        }
                        for (int i = 0; i <= ACardTable.get().ACardSize(); ++i) {
                            ACard card = ACardTable.get().getCard(i);
                            if (card != null && pc.getQuest().get_step(card.getQuestId()) != 0) {
                                pc.addStr(card.getAddStr());
                                pc.addDex(card.getAddDex());
                                pc.addCon(card.getAddCon());
                                pc.addInt(card.getAddInt());
                                pc.addWis(card.getAddWis());
                                pc.addCha(card.getAddCha());
                                pc.addAc(card.getAddAc());
                                pc.addMaxHp(card.getAddHp());
                                pc.addMaxMp(card.getAddMp());
                                pc.addHpr(card.getAddHpr());
                                pc.addMpr(card.getAddMpr());
                                pc.addDmgup(card.getAddDmg());
                                pc.addBowDmgup(card.getAddBowDmg());
                                pc.addHitup(card.getAddHit());
                                pc.addBowHitup(card.getAddBowHit());
                                pc.addDamageReductionByArmor(card.getAddDmgR());
                                pc.addMagicDmgReduction(card.getAddMagicDmgR());
                                pc.addSp(card.getAddSp());
                                pc.addMagicDmgModifier(card.getAddMagicHit());
                                pc.addMr(card.getAddMr());
                                pc.addFire(card.getAddFire());
                                pc.addWater(card.getAddWater());
                                pc.addWind(card.getAddWind());
                                pc.addEarth(card.getAddEarth());
                                pc.sendPackets(new S_OwnCharStatus(pc));
                                pc.sendPackets(new S_SPMR(pc));
                                pc.sendPackets(new S_SystemMessage(card.getMsg1()));
                            }
                        }
                        for (int i = 0; i <= CardSetTable.get().CardCardSize(); ++i) {
                            CardPolySet cards = CardSetTable.get().getCard(i);
                            if (cards != null && pc.getQuest().get_step(cards.getQuestId()) != 0) {
                                pc.addStr(cards.getAddStr());
                                pc.addDex(cards.getAddDex());
                                pc.addCon(cards.getAddCon());
                                pc.addInt(cards.getAddInt());
                                pc.addWis(cards.getAddWis());
                                pc.addCha(cards.getAddCha());
                                pc.addAc(cards.getAddAc());
                                pc.addMaxHp(cards.getAddHp());
                                pc.addMaxMp(cards.getAddMp());
                                pc.addHpr(cards.getAddHpr());
                                pc.addMpr(cards.getAddMpr());
                                pc.addDmgup(cards.getAddDmg());
                                pc.addBowDmgup(cards.getAddBowDmg());
                                pc.addHitup(cards.getAddHit());
                                pc.addBowHitup(cards.getAddBowHit());
                                pc.addDamageReductionByArmor(cards.getAddDmgR());
                                pc.addMagicDmgReduction(cards.getAddMagicDmgR());
                                pc.addSp(cards.getAddSp());
                                pc.addMagicDmgModifier(cards.getAddMagicHit());
                                pc.addMr(cards.getAddMr());
                                pc.addFire(cards.getAddFire());
                                pc.addWater(cards.getAddWater());
                                pc.addWind(cards.getAddWind());
                                pc.addEarth(cards.getAddEarth());
                                pc.sendPackets(new S_OwnCharStatus(pc));
                                pc.sendPackets(new S_SPMR(pc));
                            }
                        }
                        for (int i = 0; i <= ACardTable_doll.get().ACardSize(); ++i) {
                            ACard_doll card2 = ACardTable_doll.get().getCard(i);
                            if (card2 != null && pc.getQuest().get_step(card2.getQuestId()) != 0) {
                                pc.addStr(card2.getAddStr());
                                pc.addDex(card2.getAddDex());
                                pc.addCon(card2.getAddCon());
                                pc.addInt(card2.getAddInt());
                                pc.addWis(card2.getAddWis());
                                pc.addCha(card2.getAddCha());
                                pc.addAc(card2.getAddAc());
                                pc.addMaxHp(card2.getAddHp());
                                pc.addMaxMp(card2.getAddMp());
                                pc.addHpr(card2.getAddHpr());
                                pc.addMpr(card2.getAddMpr());
                                pc.addDmgup(card2.getAddDmg());
                                pc.addBowDmgup(card2.getAddBowDmg());
                                pc.addHitup(card2.getAddHit());
                                pc.addBowHitup(card2.getAddBowHit());
                                pc.addDamageReductionByArmor(card2.getAddDmgR());
                                pc.addMagicDmgReduction(card2.getAddMagicDmgR());
                                pc.addSp(card2.getAddSp());
                                pc.addMagicDmgModifier(card2.getAddMagicHit());
                                pc.addMr(card2.getAddMr());
                                pc.addFire(card2.getAddFire());
                                pc.addWater(card2.getAddWater());
                                pc.addWind(card2.getAddWind());
                                pc.addEarth(card2.getAddEarth());
                                pc.sendPackets(new S_OwnCharStatus(pc));
                                pc.sendPackets(new S_SPMR(pc));
                                pc.sendPackets(new S_SystemMessage(card2.getMsg1()));
                            }
                        }
                        for (int i = 0; i <= CardSetTable_doll.get().CardCardSize(); ++i) {
                            CardPolySet_doll cards2 = CardSetTable_doll.get().getCard(i);
                            if (cards2 != null && pc.getQuest().get_step(cards2.getQuestId()) != 0) {
                                pc.addStr(cards2.getAddStr());
                                pc.addDex(cards2.getAddDex());
                                pc.addCon(cards2.getAddCon());
                                pc.addInt(cards2.getAddInt());
                                pc.addWis(cards2.getAddWis());
                                pc.addCha(cards2.getAddCha());
                                pc.addAc(cards2.getAddAc());
                                pc.addMaxHp(cards2.getAddHp());
                                pc.addMaxMp(cards2.getAddMp());
                                pc.addHpr(cards2.getAddHpr());
                                pc.addMpr(cards2.getAddMpr());
                                pc.addDmgup(cards2.getAddDmg());
                                pc.addBowDmgup(cards2.getAddBowDmg());
                                pc.addHitup(cards2.getAddHit());
                                pc.addBowHitup(cards2.getAddBowHit());
                                pc.addDamageReductionByArmor(cards2.getAddDmgR());
                                pc.addMagicDmgReduction(cards2.getAddMagicDmgR());
                                pc.addSp(cards2.getAddSp());
                                pc.addMagicDmgModifier(cards2.getAddMagicHit());
                                pc.addMr(cards2.getAddMr());
                                pc.addFire(cards2.getAddFire());
                                pc.addWater(cards2.getAddWater());
                                pc.addWind(cards2.getAddWind());
                                pc.addEarth(cards2.getAddEarth());
                                pc.sendPackets(new S_OwnCharStatus(pc));
                                pc.sendPackets(new S_SPMR(pc));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            L1PcInstance activeChar = client.getActiveChar();
            C_LoginToServer._log.error("角色登入初始化失敗: " + (activeChar != null ? activeChar.getName() : "unknown"), ex);
        }
        finally {
            this.over();
        }
    }

/**
 * 玩家登入時：1. 時間制 VIP 效果 (72111~72180)  2. 累積贊助 VIP 效果 (71051~71070)
 */
private void checkAndApplyVIP(L1PcInstance pc, L1Account account) {
    try {
        java.util.Set<Integer> activeTypes = new java.util.HashSet<>();
        for (L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.isEquipped()) {
                L1ItemVIP eqVip = ItemVIPTable.get().getVIP(item.getItemId());
                if (eqVip != null) {
                    activeTypes.add(eqVip.get_type());
                }
            }
        }

        // ==================== 第一部分：時間制 VIP (72111 ~ 72180) ====================
        Timestamp vipTime = VIPReading.get().getOther(pc);
        long now = System.currentTimeMillis();

        if (vipTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formatTime = sdf.format(vipTime);

            if (vipTime.getTime() > now) {
                pc.sendPackets(new S_SystemMessage("\\fY您的 VIP月卡 有效時間：" + formatTime));

                long diffMillis = vipTime.getTime() - now;
                long remainingDays = diffMillis / (24L * 60L * 60L * 1000L);
                if (remainingDays < 1) remainingDays = 1;

                int index = (int) ((remainingDays - 1) / 30);
                if (index > 69) index = 69; // 擴展至 72180 (72111 + 69)
                int targetVipId = 72111 + index;

                L1ItemVIP vipTemplate = ItemVIPTable.get().getVIP(targetVipId);
                if (vipTemplate != null) {
                    int vipType = vipTemplate.get_type();
                    if (!activeTypes.contains(vipType)) {
                        ItemVIPTable.get().addItemVIP(pc, targetVipId);
                        activeTypes.add(vipType);
                        int level = index + 1;
                        pc.sendPackets(new S_SystemMessage("\\fY[系統] 已自動為您套用 VIP " + level + " 月卡效果。"));
                    }
                }
            } else {
                VIPReading.get().delOther(pc.getId());
                pc.sendPackets(new S_SystemMessage("\\fR您的 VIP 資格已過期。"));
            }
        }

        // ==================== 第二部分：累積贊助 VIP (71051 ~ 71070) ====================
        if (account != null) {
            int firstPay = account.get_first_pay();
            if (firstPay >= 500) {
                int donateItemId = 71051;

                if (firstPay >= 80000) donateItemId = 71070;
                else if (firstPay >= 65000) donateItemId = 71069;
                else if (firstPay >= 60000) donateItemId = 71068;
                else if (firstPay >= 55000) donateItemId = 71067;
                else if (firstPay >= 50000) donateItemId = 71066;
                else if (firstPay >= 45000) donateItemId = 71065;
                else if (firstPay >= 40000) donateItemId = 71064;
                else if (firstPay >= 35000) donateItemId = 71063;
                else if (firstPay >= 30000) donateItemId = 71062;
                else if (firstPay >= 25000) donateItemId = 71061;
                else if (firstPay >= 20000) donateItemId = 71060;
                else if (firstPay >= 17000) donateItemId = 71059;
                else if (firstPay >= 13500) donateItemId = 71058;
                else if (firstPay >= 10000) donateItemId = 71057;
                else if (firstPay >= 7000)  donateItemId = 71056;
                else if (firstPay >= 5000)  donateItemId = 71055;
                else if (firstPay >= 3500)  donateItemId = 71054;
                else if (firstPay >= 2000)  donateItemId = 71053;
                else if (firstPay >= 1000)  donateItemId = 71052;

                L1ItemVIP donateTemplate = ItemVIPTable.get().getVIP(donateItemId);
                if (donateTemplate != null) {
                    int donateType = donateTemplate.get_type();
                    if (!activeTypes.contains(donateType)) {
                        ItemVIPTable.get().addItemVIP(pc, donateItemId);
                        activeTypes.add(donateType);
                        pc.sendPackets(new S_SystemMessage("\\fY[被動] 已根據累積贊助金額套用專屬加成。"));
                    }
                }
            }
        }

    } catch (Exception e) {
        _log.error(e.getLocalizedMessage(), e);
    }
}
    public static void CheckItemPower(L1PcInstance pc) {
        for (L1ItemInstance item : pc.getInventory().getItems()) {
            if (CheckItemPowerTable.get().checkItem(item.getItemId())) {
                CheckItemPowerTable.get().givepower(pc, item.getItemId());
            }
        }
    }
    private int CheckMail(L1PcInstance pc) {
        int count = 0;
        Connection con = null;
        PreparedStatement pstm1 = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm1 = con.prepareStatement(" SELECT count(*) as cnt FROM mails where receiver =? AND read_status = 0");
            pstm1.setString(1, pc.getName());
            rs = pstm1.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        }
        catch (SQLException e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm1);
            SQLUtil.close(con);
        }
        return count;
    }
    
    private void crown(L1PcInstance pc) {
        try {
            Map<Integer, L1Clan> map = L1CastleLocation.mapCastle();
            for (Integer key : map.keySet()) {
                L1Clan clan = map.get(key);
                if (clan != null) {
                    if (key.equals(2)) {
                        pc.sendPackets(new S_CastleMaster(8, clan.getLeaderId()));
                    }
                    else {
                        pc.sendPackets(new S_CastleMaster(key, clan.getLeaderId()));
                    }
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void getFocus(L1PcInstance pc) {
        try {
            pc.set_showId(-1);
            World.get().addVisibleObject(pc);
            if (pc.getMeteLevel() > 0) {
                pc.resetMeteAbility();
            }
            pc.sendPackets(new S_PacketBox(132, pc.getEr()));
            pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_MapID(pc, (int)pc.getMapId(), pc.getMap().isUnderwater()));
            pc.sendPackets(new S_OwnCharPack(pc));
            ArrayList<L1PcInstance> otherPc = World.get().getVisiblePlayer(pc);
            if (otherPc.size() > 0) {
                for (L1PcInstance tg : otherPc) {
                    tg.sendPackets(new S_OtherCharPacks(pc));
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void getUpdate(L1PcInstance pc) {
        pc.sendPackets(new S_Mail(pc, 0));
        pc.sendPackets(new S_Mail(pc, 1));
        pc.sendPackets(new S_Mail(pc, 2));
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_Karma(pc));
        pc.sendPackets(new S_Weather(World.get().getWeather()));
    }
    
    private void marriage(L1PcInstance pc) {
        try {
            if (pc.getPartnerId() != 0) {
                L1PcInstance partner = (L1PcInstance)World.get().findObject(pc.getPartnerId());
                if (partner != null && partner.getPartnerId() != 0 && pc.getPartnerId() == partner.getId() && partner.getPartnerId() == pc.getId()) {
                    pc.sendPackets(new S_ServerMessage(548));
                    partner.sendPackets(new S_ServerMessage(549));
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void getOther(L1PcInstance pc) throws Exception {
        try {
            pc.set_otherList(new L1PcOtherList(pc));
            pc.addDmgup(pc.get_other().get_Attack());
            pc.addBowDmgup(pc.get_other().get_BowAttack());
            pc.addHitup(pc.get_other().get_Hit());
            pc.addBowHitup(pc.get_other().get_BowHit());
            pc.addSp(pc.get_other().get_Sp());
            pc.addStr(pc.get_other().get_Str());
            pc.addDex(pc.get_other().get_Dex());
            pc.addInt(pc.get_other().get_Int());
            pc.addCon(pc.get_other().get_Con());
            pc.addCha(pc.get_other().get_Cha());
            pc.addWis(pc.get_other().get_Wis());
            pc.addMaxHp(pc.get_other().get_Hp());
            pc.addMaxMp(pc.get_other().get_Mp());
            pc.addMr(pc.get_other().get_Mr());
            pc.addother_ReductionDmg(pc.get_other().get_ReductionDmg());
            pc.addHpr(pc.get_other().get_Hpr());
            pc.addMpr(pc.get_other().get_Mpr());
            pc.add_hppotion(pc.get_other().get_hppotion());
            pc.add_exp(pc.get_other().get_exp());
            pc.addAc(pc.get_other().get_ac());
            pc.addWeightReduction(pc.get_other().get_weight());
            pc.addRegistStun(pc.get_other().get_regist_stun());
            pc.addRegistStone(pc.get_other().get_regist_stone());
            pc.addRegistSleep(pc.get_other().get_regist_sleep());
            pc.add_regist_freeze(pc.get_other().get_regist_freeze());
            pc.addRegistSustain(pc.get_other().get_regist_sustain());
            pc.addRegistBlind(pc.get_other().get_regist_blind());
            pc.addDmgup(pc.get_other2().get_Attack());
            pc.addBowDmgup(pc.get_other2().get_BowAttack());
            pc.addHitup(pc.get_other2().get_Hit());
            pc.addBowHitup(pc.get_other2().get_BowHit());
            pc.addSp(pc.get_other2().get_Sp());
            pc.addStr(pc.get_other2().get_Str());
            pc.addDex(pc.get_other2().get_Dex());
            pc.addInt(pc.get_other2().get_Int());
            pc.addCon(pc.get_other2().get_Con());
            pc.addCha(pc.get_other2().get_Cha());
            pc.addWis(pc.get_other2().get_Wis());
            pc.addMaxHp(pc.get_other2().get_Hp());
            pc.addMaxMp(pc.get_other2().get_Mp());
            pc.addMr(pc.get_other2().get_Mr());
            pc.addother_ReductionDmg(pc.get_other2().get_ReductionDmg());
            pc.addHpr(pc.get_other2().get_Hpr());
            pc.addMpr(pc.get_other2().get_Mpr());
            pc.add_hppotion(pc.get_other2().get_hppotion());
            pc.add_exp(pc.get_other2().get_exp());
            pc.addAc(pc.get_other2().get_ac());
            pc.addWeightReduction(pc.get_other2().get_weight());
            pc.addRegistStun(pc.get_other2().get_regist_stun());
            pc.addRegistStone(pc.get_other2().get_regist_stone());
            pc.addRegistSleep(pc.get_other2().get_regist_sleep());
            pc.add_regist_freeze(pc.get_other2().get_regist_freeze());
            pc.addRegistSustain(pc.get_other2().get_regist_sustain());
            pc.addRegistBlind(pc.get_other2().get_regist_blind());
            pc.add_pvp(pc.get_other2().get_pvp());
            pc.add_bowpvp(pc.get_other2().get_bowpvp());
            if (pc.get_other1().get_type1() != 0) {
                pc.setAu_Shop(true);
            }
            if (pc.get_other1().get_type20() != 0) {
                pc.setAu_AutoSkill(2, pc.get_other1().get_type20());
            }
            if (pc.get_other1().get_type21() != 0) {
                pc.setAu_AutoSkill(3, pc.get_other1().get_type21());
            }
            if (pc.get_other1().get_type22() != 0) {
                pc.setAu_AutoSkill(4, pc.get_other1().get_type22());
            }
            if (pc.get_other1().get_type23() != 0) {
                pc.setAu_AutoSkill(5, pc.get_other1().get_type23());
            }
            if (pc.get_other1().get_type24() != 0) {
                pc.setAu_AutoSkill(6, pc.get_other1().get_type24());
            }
            if (pc.get_other1().get_type25() != 0) {
                pc.setAu_AutoSkill(7, pc.get_other1().get_type25());
            }
            if (pc.get_other1().get_type26() != 0) {
                pc.setAu_AutoSkill(8, 1);
            }
            if (pc.get_other1().get_type27() != 0) {
                pc.setsummon_skillid(pc.get_other1().get_type27());
            }
            if (pc.get_other1().get_type28() != 0) {
                pc.setsummon_skillidmp_1(pc.get_other1().get_type28());
            }
            if (pc.get_other1().get_type30() != null) {
                pc.setSummon_npcid(pc.get_other1().get_type30());
            }
            if (pc.get_other1().get_type41() != 0) {
                pc.setAu_AutoSet(1, pc.get_other1().get_type41());
            }
            if (pc.get_other1().get_type42() != 0) {
                pc.setAu_AutoSet(0, 1);
            }
            if (pc.get_other1().get_type43() != 0) {
                pc.setAu_AutoSet(3, pc.get_other1().get_type43());
            }
            if (pc.get_other1().get_type44() != 0) {
                pc.setAu_AutoSet(5, pc.get_other1().get_type44());
            }
            if (pc.get_other1().get_type45() != 0) {
                pc.setAu_AutoSet(4, pc.get_other1().get_type45());
            }
            if (pc.get_other1().get_type46() != 0) {
                pc.setAu_OtherSet(1, 1);
            }
            if (pc.get_other1().get_type47() != 0) {
                pc.setAu_OtherSet(2, 1);
            }
            if (pc.get_other1().get_type48() != 0) {
                pc.setAu_OtherSet(3, 1);
            }
            if (pc.get_other1().get_type49() != 0) {
                pc.setAu_OtherSet(4, 1);
            }
            if (pc.get_other1().get_type50() != 0) {
                pc.setAu_OtherSet(5, 1);
            }
            if (pc.get_other1().get_type2() != 0 && pc.get_other1().get_type3() != 0) {
                pc.setAu_BuyItemSwitch(0, 1);
                pc.setAu_BuyItemCount(0, pc.get_other1().get_type3());
            }
            if (pc.get_other1().get_type4() != 0 && pc.get_other1().get_type5() != 0) {
                pc.setAu_BuyItemSwitch(1, 1);
                pc.setAu_BuyItemCount(1, pc.get_other1().get_type5());
            }
            if (pc.get_other1().get_type6() != 0 && pc.get_other1().get_type7() != 0) {
                pc.setAu_BuyItemSwitch(2, 1);
                pc.setAu_BuyItemCount(2, pc.get_other1().get_type7());
            }
            if (pc.get_other1().get_type8() != 0 && pc.get_other1().get_type9() != 0) {
                pc.setAu_BuyItemSwitch(3, 1);
                pc.setAu_BuyItemCount(3, pc.get_other1().get_type9());
            }
            if (pc.get_other1().get_type10() != 0 && pc.get_other1().get_type11() != 0) {
                pc.setAu_BuyItemSwitch(4, 1);
                pc.setAu_BuyItemCount(4, pc.get_other1().get_type11());
            }
            if (pc.get_other1().get_type12() != 0 && pc.get_other1().get_type13() != 0) {
                pc.setAu_BuyItemSwitch(5, 1);
                pc.setAu_BuyItemCount(5, pc.get_other1().get_type13());
            }
            if (pc.get_other1().get_type14() != 0 && pc.get_other1().get_type15() != 0) {
                pc.setAu_BuyItemSwitch(6, 1);
                pc.setAu_BuyItemCount(6, pc.get_other1().get_type15());
            }
            if (pc.get_other1().get_type16() != 0 && pc.get_other1().get_type17() != 0) {
                pc.setAu_BuyItemSwitch(7, 1);
                pc.setAu_BuyItemCount(7, pc.get_other1().get_type17());
            }
            if (pc.get_other1().get_type18() != 0 && pc.get_other1().get_type19() != 0) {
                pc.setAu_BuyItemSwitch(8, 1);
                pc.setAu_BuyItemCount(8, pc.get_other1().get_type19());
            }
            if (pc.get_other3().get_type2() != 0) {
                pc.setSave_Quest_Map1(pc.get_other3().get_type2());
            }
            if (pc.get_other3().get_type3() != 0) {
                pc.setSave_Quest_Map2(pc.get_other3().get_type3());
            }
            if (pc.get_other3().get_type4() != 0) {
                pc.setSave_Quest_Map3(pc.get_other3().get_type4());
            }
            if (pc.get_other3().get_type5() != 0) {
                pc.setSave_Quest_Map4(pc.get_other3().get_type5());
            }
            if (pc.get_other3().get_type6() != 0) {
                pc.setSave_Quest_Map5(pc.get_other3().get_type6());
            }
            OnlineGiftSet.add(pc);
            int time = pc.get_other().get_usemapTime();
            if (time > 0) {
                ServerUseMapTimer.put(pc, time);
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void war(L1PcInstance pc) {
        try {
            if (pc.getClanid() != 0) {
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    if (pc.getClanid() == clan.getClanId() && pc.getClanname().toLowerCase().equals(clan.getClanName().toLowerCase())) {
                        L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                        L1PcInstance[] array;
                        for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                            L1PcInstance clanMember = array[i];
                            if (clanMember.getId() != pc.getId()) {
                                clanMember.sendPackets(new S_ServerMessage(843, pc.getName()));
                            }
                        }
                        int clanMan = clan.getOnlineClanMember().length;
                        pc.sendPackets(new S_ServerMessage("\\fU線上血盟成員:" + clanMan));
                        if (clan.getAnnouncement() != null) {
                            pc.sendPackets(new S_SystemMessage("血盟公告:" + clan.getAnnouncement()));
                        }
                        clan.CheckClan_Exp20(null);
                        if (clan.isClanskill()) {
                            switch (pc.get_other().get_clanskill()) {
                                case 1: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[0]));
                                    break;
                                }
                                case 2: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[1]));
                                    break;
                                }
                                case 4: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[2]));
                                    break;
                                }
                                case 8: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[3]));
                                    break;
                                }
                            }
                        }
                        ClanSkillDBSet.add(pc);
                        L1EmblemIcon emblemIcon = ClanEmblemReading.get().get(clan.getClanId());
                        if (emblemIcon != null) {
                            pc.sendPackets(new S_Emblem(emblemIcon));
                        }
                        for (L1War war : WorldWar.get().getWarList()) {
                            boolean ret = war.checkClanInWar(pc.getClanname());
                            if (ret) {
                                String enemy_clan_name = war.getEnemyClanName(pc.getClanname());
                                if (enemy_clan_name != null) {
                                    pc.sendPackets(new S_War(8, pc.getClanname(), enemy_clan_name));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                    }
                }
                else {
                    pc.setClanid(0);
                    pc.setClanname("");
                    pc.setClanRank(0);
                    pc.save();
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    private void backRestart(L1PcInstance pc) {
        try {
            if (pc.getMapId() >= 4001 && pc.getMapId() <= 4050) {
                pc.setX(33448);
                pc.setY(32791);
                pc.setMap((short)4);
            }
            L1GetBackRestart gbr = GetBackRestartTable.get().getGetBackRestart((int)pc.getMapId());
            if (gbr != null) {
                pc.setX(gbr.getLocX());
                pc.setY(gbr.getLocY());
                pc.setMap(gbr.getMapId());
            }
            int castle_id = L1CastleLocation.getCastleIdByArea(pc);
            if (castle_id > 0 && ServerWarExecutor.get().isNowWar(castle_id)) {
                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    if (clan.getCastleId() != castle_id) {
                        int[] loc = new int[3];
                        loc = L1CastleLocation.getGetBackLoc(castle_id);
                        pc.setX(loc[0]);
                        pc.setY(loc[1]);
                        pc.setMap((short)loc[2]);
                    }
                }
                else {
                    int[] loc = new int[3];
                    loc = L1CastleLocation.getGetBackLoc(castle_id);
                    pc.setX(loc[0]);
                    pc.setY(loc[1]);
                    pc.setMap((short)loc[2]);
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void items(L1PcInstance pc) {
        try {
            CharacterTable.restoreInventory(pc);
            List<L1ItemInstance> items = pc.getInventory().getItems();
            if (items.size() > 0) {
                pc.sendPackets(new S_InvList(pc.getInventory().getItems()));
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void bookmarks(L1PcInstance pc) {
        try {
            ArrayList<L1BookMark> bookList = CharBookReading.get().getBookMarks(pc);
            if (bookList != null) {
                L1BookConfig config = CharBookConfigReading.get().get(pc.getId());
                int maxSize = ConfigAlt.CHAR_BOOK_INIT_COUNT + ((config != null) ? config.getMaxSize() : 0);
                pc.sendPackets(new S_Bookmarks((config != null) ? config.getData() : null, maxSize, bookList));
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void skills(L1PcInstance pc) {
        try {
            ArrayList<L1UserSkillTmp> skillList = CharSkillReading.get().skills(pc.getId());
            int[] skills = new int[28];
            if (skillList != null && skillList.size() > 0) {
                for (L1UserSkillTmp userSkillTmp : skillList) {
                    L1Skills skill = SkillsTable.get().getTemplate(userSkillTmp.get_skill_id());
                    int[] array = skills;
                    int n = skill.getSkillLevel() - 1;
                    array[n] += skill.getId();
                }
                pc.sendPackets(new S_AddSkill(pc, skills));
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void serchSummon(L1PcInstance pc) {
        try {
            Collection<L1SummonInstance> summons = WorldSummons.get().all();
            if (summons.size() > 0) {
                for (L1SummonInstance summon : summons) {
                    if (summon.getMaster().getId() == pc.getId()) {
                        summon.setMaster(pc);
                        pc.addPet(summon);
                        S_NewMaster packet = new S_NewMaster(pc.getName(), summon);
                        for (L1PcInstance visiblePc : World.get().getVisiblePlayer(summon)) {
                            visiblePc.sendPackets(packet);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void buff(L1PcInstance pc) {
        try {
            CharBuffReading.get().buff(pc);
            pc.sendPackets(new S_PacketBoxActiveSpells(pc));
            CharMapTimeReading.get().getTime(pc);
        }
        catch (Exception e) {
            C_LoginToServer._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public static void checkforProtector(L1PcInstance pc) {
        for (L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.getItemId() == ProtectorSet.ITEM_ID && !pc.isProtector()) {
                pc.setProtector(true);
                break;
            }
        }
    }
    
    public static void checkforDADISStone(L1PcInstance pc) {
        for (L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.getItemId() == 56147) {
                if (!pc.isEffectDADIS()) {
                    pc.setDADIS(true);
                    break;
                }
                else {
                    continue;
                }
            }
            else if (item.getItemId() == 56148) {
                if (!pc.isEffectGS() && !pc.isEffectDADIS()) {
                    pc.setGS(true);
                    break;
                }
                else {
                    continue;
                }
            }
            else if (item.getItemId() == 56148 && !pc.isEffectGS() && !pc.isEffectDADIS()) {
                pc.setGS(true);
                break;
            }
            else {
                continue;
            }
        }
    }
    
    public static void checkforSouls(L1PcInstance pc) {
        for (L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.getItemId() == 44216) {
                if (!pc._isCraftsmanHeirloom() && !pc._isMarsSoul()) {
                    pc.setCraftsmanHeirloom(true);
                    break;
                }
                else {
                    continue;
                }
            }
            else if (item.getItemId() == 44217 && !pc._isMarsSoul() && !pc._isCraftsmanHeirloom()) {
                pc.setMarsSoul(true);
                break;
            }
            else {
                continue;
            }
        }
    }
    
    private void ClanMatching(L1PcInstance pc) {
        L1ClanMatching cml = L1ClanMatching.getInstance();
        if (pc.getClanid() == 0) {
            if (!pc.isCrown()) {
                if (!cml.getMatchingList().isEmpty()) {
                    pc.sendPackets(new S_ServerMessage(3245));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(3247));
            }
        }
        else {
            switch (pc.getClanRank()) {
                case 3:
                case 4:
                case 6:
                case 9:
                case 10: {
                    if (!pc.getInviteList().isEmpty()) {
                        pc.sendPackets(new S_ServerMessage(3246));
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
            pc.sendPackets(new S_PacketBox(173, pc.getClan().getEmblemStatus()));
        }
    }
    @Override
    public String getType() {
	
        return this.getClass().getSimpleName();
    }

}
