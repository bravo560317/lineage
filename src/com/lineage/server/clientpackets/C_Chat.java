package com.lineage.server.clientpackets;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.map.L1WorldMap;
import com.add.NewAutoPractice;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.templates.L1Item;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.ItemTable;
import java.util.StringTokenizer;
import java.util.List;
import com.lineage.server.serverpackets.S_WhoHero;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.event.BroadcastSet;
import com.lineage.server.BroadcastController;
import com.lineage.server.serverpackets.S_NpcChatShouting;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.command.GMCommands;
import com.lineage.server.serverpackets.S_ChatShouting;
import com.lineage.server.serverpackets.S_ChatClan;
import java.util.concurrent.ConcurrentHashMap;
import com.lineage.server.serverpackets.S_ChatParty;
import com.lineage.server.serverpackets.S_ChatClanUnion;
import com.lineage.server.world.WorldClan;
import com.lineage.config.ConfigRecord;
import com.lineage.server.serverpackets.S_ChatParty2;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1Alliance;
import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_ChatClanAlliance;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.config.ConfigAlt;
import com.lineage.william.Npc_BuyPet1;
import com.lineage.data.event.Chatvip;
import com.eric.gui.J_Main;
import com.lineage.config.Config;
import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.data.event.QuestMobSet;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.config.ConfigAi;
import com.lineage.server.utils.Random;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_CharTitle;
import com.add.AutoAttackUpdate;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Chat extends ClientBasePacket
{
    private static final Log _log;
    private static final String _check_pwd = "abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#";
    private static final String _autype = "0123456789";
    
    static {
        _log = LogFactory.getLog(C_Chat.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            int chatType = this.readC();
            String chatText = this.readS();
            if (chatText != null && chatText.length() > 52) {
                chatText = chatText.substring(0, 52);
            }
            boolean isStop = false;
            boolean errMessage = false;
            if (pc.hasSkillEffect(64) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(161) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(1007) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(4002)) {
                isStop = true;
                errMessage = true;
            }
            if (pc.hasSkillEffect(7004)) {
                isStop = true;
                errMessage = true;
            }
            if (pc.hasSkillEffect(9990)) {
                pc.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法正常說話。"));
            }
            else {
                if ((pc.getMapId() == 5153 || pc.getMapId() == 5154) && !pc.isGm()) {
                    isStop = true;
                }
                if (isStop) {
                    if (errMessage) {
                        pc.sendPackets(new S_ServerMessage(242));
                    }
                }
                else {
                    switch (chatType) {
                        case 0: {
                            if (pc.is_retitle()) {
                                this.re_title(pc, chatText.trim());
                                return;
                            }
                            else if (pc.is_repass() != 0) {
                                this.re_repass(pc, chatText.trim());
                                return;
                            }
                            else if (pc.IsKeyInEnemy()) {
                                this.KeyInEnemyName(pc, chatText.trim());
                                return;
                            }
                            else if (pc.IsKeyOutEnemy()) {
                                this.KeyOutEnemyName(pc, chatText.trim());
                                return;
                            }
                            else if (pc.IsBadKeyInEnemy()) {
                                this.BadKeyInEnemyName(pc, chatText.trim());
                                return;
                            }
                            else if (pc.IsBadKeyOutEnemy()) {
                                this.BadKeyOutEnemyName(pc, chatText.trim());
                                return;
                            }
                            else if (pc.IsAu_SetShop()) {
                                boolean err_1 = false;
                                int i = 0;
                                while (i < chatText.length()) {
                                    String ch = chatText.substring(i, i + 1);
                                    if (!"0123456789".contains(ch.toLowerCase())) {
                                        pc.sendPackets(new S_SystemMessage("無效字元！請重新設定"));
                                        err_1 = true;
                                        break;
                                    }
                                    else {
                                        ++i;
                                    }
                                }
                                if (err_1) {
                                    pc.setAu_SetShop(false);
                                    pc.SetAu_SetShopType(-1);
                                }
                                else {
                                    int count = Integer.valueOf(chatText);
                                    if (count > 10000) {
                                        count = 10000;
                                        pc.sendPackets(new S_SystemMessage("由於設定數量超過最大上限10,000"));
                                        pc.sendPackets(new S_SystemMessage("系統將自動設定10,000上限值"));
                                    }
                                    pc.setAu_BuyItemCount(pc.getAu_SetShopType(), count);
                                    if (pc.getAu_SetShopType() == 0 && pc.getAu_BuyItemSwitch(0) == 1) {
                                        pc.get_other1().set_type3(count);
                                    }
                                    if (pc.getAu_SetShopType() == 1 && pc.getAu_BuyItemSwitch(1) == 1) {
                                        pc.get_other1().set_type5(count);
                                    }
                                    if (pc.getAu_SetShopType() == 2 && pc.getAu_BuyItemSwitch(2) == 1) {
                                        pc.get_other1().set_type7(count);
                                    }
                                    if (pc.getAu_SetShopType() == 3 && pc.getAu_BuyItemSwitch(3) == 1) {
                                        pc.get_other1().set_type9(count);
                                    }
                                    if (pc.getAu_SetShopType() == 4 && pc.getAu_BuyItemSwitch(4) == 1) {
                                        pc.get_other1().set_type11(count);
                                    }
                                    if (pc.getAu_SetShopType() == 5 && pc.getAu_BuyItemSwitch(5) == 1) {
                                        pc.get_other1().set_type13(count);
                                    }
                                    if (pc.getAu_SetShopType() == 6 && pc.getAu_BuyItemSwitch(6) == 1) {
                                        pc.get_other1().set_type15(count);
                                    }
                                    if (pc.getAu_SetShopType() == 7 && pc.getAu_BuyItemSwitch(7) == 1) {
                                        pc.get_other1().set_type17(count);
                                    }
                                    if (pc.getAu_SetShopType() == 8 && pc.getAu_BuyItemSwitch(8) == 1) {
                                        pc.get_other1().set_type19(count);
                                    }
                                    pc.sendPackets(new S_SystemMessage("設置完成"));
                                    pc.setAu_SetShop(false);
                                    pc.SetAu_SetShopType(-1);
                                    AutoAttackUpdate.getInstance().Au_Shop(pc);
                                }
                                return;
                            }
                            else if (pc.hasSkillEffect(98767)) {
                                if (chatText.startsWith(String.valueOf(pc.getrantitle()))) {
                                    pc.killSkillEffectTimer(98767);
                                    pc.sendPackets(new S_SystemMessage("感謝您的驗證,[驗證成功]"));
                                    pc.sendPackets(new S_CharTitle(pc.getId(), pc.get_savetitle()));
                                    pc.setrantitle(0);
                                    pc.set_savetitle(null);
                                    pc.addaierrorcheck(0);
                                    return;
                                }
                                else {
                                    pc.addaierrorcheck(1);
                                    if (pc.getaierrorcheck() == 2) {
                                        pc.sendPackets(new S_Disconnect());
                                        return;
                                    }
                                    else {
                                        pc.sendPackets(new S_SystemMessage("由於您輸入錯誤再給一次機會"));
                                        pc.sendPackets(new S_SystemMessage("請重新輸入新的[封號上的驗證碼]"));
                                        pc.sendPackets(new S_SystemMessage("\\fT由於您輸入錯誤再給一次機會"));
                                        pc.sendPackets(new S_SystemMessage("\\fT請重新輸入新的[封號上的驗證碼]"));
                                        pc.sendPackets(new S_SystemMessage("\\fU由於您輸入錯誤再給一次機會"));
                                        pc.sendPackets(new S_SystemMessage("\\fU請重新輸入新的[封號上的驗證碼]"));
                                        pc.setrantitle(Random.nextInt(9999));
                                        pc.sendPackets(new S_CharTitle(pc.getId(), "驗證碼:" + pc.getrantitle()));
                                        pc.setSkillEffect(98767, 60000);
                                        return;
                                    }
                                }
                            }
                            else if (chatText.startsWith("serverver")) {
                                pc.sendPackets(new S_SystemMessage("pc"));
                                return;
                            }
                            else if (pc.hasSkillEffect(8008)) {
                                pc.killSkillEffectTimer(8008);
                                this.check_broadcast(pc, chatText);
                                return;
                            }
                            else {
                                if (chatText.startsWith(String.valueOf(pc.getnewaicount_2())) && pc.hasSkillEffect(7954)) {
                                    pc.killSkillEffectTimer(7954);
                                    pc.setnewaicount_2(0);
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                                    int time = ConfigAi.aitimelast + Random.nextInt(ConfigAi.aitimeran) + 1;
                                    pc.setSkillEffect(7950, time * 1000);
                                }
                                else if (!chatText.startsWith(String.valueOf(pc.getnewaicount_2())) && pc.hasSkillEffect(7954) && pc.getInputError() == 0) {
                                    pc.setInputError(pc.getInputError() + 1);
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg7));
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                                }
                                else if (!chatText.startsWith(String.valueOf(pc.getnewaicount_2())) && pc.hasSkillEffect(7954) && pc.getInputError() == 1) {
                                    pc.setInputError(pc.getInputError() + 1);
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg7));
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                                    pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                                }
                                else if (pc.getInputError() == 2 && pc.hasSkillEffect(7954)) {
                                    pc.setInputError(0);
                                    pc.saveInventory();
                                    pc.sendPackets(new S_Disconnect());
                                    C_Chat._log.info(String.format("玩家 : %s 因輸入錯誤未通過外掛偵測，已強制切斷其連線", pc.getName()));
                                }
                                if (pc.hasSkillEffect(95133) && chatText.startsWith(chatText)) {
                                    pc.killSkillEffectTimer(95133);
                                    CharBookReading.get().deleteBookmark(pc, chatText);
                                    pc.sendPackets(new S_SystemMessage("已刪除此名稱座標.請重新登入遊戲"));
                                    return;
                                }
                                else if (pc.isItemName()) {
                                    this.ItemName(pc, chatText);
                                    pc.setItemName(false);
                                    return;
                                }
                                else if (chatText.startsWith(ConfigGuaji.guaji)) {
                                    return;
                                }
                                else if (chatText.startsWith("解卡點")) {
                                    if (!pc.isParalyzed() && !pc.isSleeped()) {
                                        if (pc.isParalyzedX()) {
                                            return;
                                        }
                                        else if (pc.isDead()) {
                                            return;
                                        }
                                        else if (pc.isInvisble()) {
                                            return;
                                        }
                                        else if (pc.hasSkillEffect(55889)) {
                                            pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
                                            return;
                                        }
                                        else if (pc.hasSkillEffect(76001)) {
                                            pc.sendPackets(new S_SystemMessage("請不要按太快.."));
                                            return;
                                        }
                                        else if (pc.hasSkillEffect(4000)) {
                                            return;
                                        }
                                        else {
                                            pc.setSkillEffect(55889, 30000);
                                            pc.set_unfreezingTime(5);
                                            return;
                                        }
                                    }
                                    else {
                                        return;
                                    }
                                }
                                else if (chatText.startsWith("解錯位")) {
                                    if (!pc.isParalyzed() && !pc.isSleeped()) {
                                        if (pc.isParalyzedX()) {
                                            return;
                                        }
                                        else if (pc.isDead()) {
                                            return;
                                        }
                                        else if (pc.isInvisble()) {
                                            return;
                                        }
                                        else if (pc.hasSkillEffect(55889)) {
                                            pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
                                            return;
                                        }
                                        else if (pc.hasSkillEffect(76001)) {
                                            pc.sendPackets(new S_SystemMessage("請不要按太快.."));
                                            return;
                                        }
                                        else {
                                            pc.setSkillEffect(55889, 30000);
                                            pc.set_misslocTime(3);
                                            return;
                                        }
                                    }
                                    else {
                                        return;
                                    }
                                }
                                else if (QuestMobSet.START && chatText.equalsIgnoreCase(".任務")) {
                                    ServerQuestMobTable.get().getQuestMobNote(pc);
                                    return;
                                }
                                else {
                                    if (Config.GUI) {
                                        J_Main.getInstance().addNormalChat(pc.getName(), chatText);
                                    }
                                    if (Chatvip.START) {
                                        Npc_BuyPet1.forNpcQuest(chatText, pc);
                                    }
                                    this.chatType_0(pc, chatText);
                                    if (ConfigAlt.GM_OVERHEARD0) {
                                        for (L1PcInstance object40 : World.get().getAllPlayers()) {
                                            L1Object visible = object40;
                                            if (visible instanceof L1PcInstance) {
                                                L1PcInstance GM = (L1PcInstance)visible;
                                                if (GM.getcheckgm() && GM.isGm() && pc.getId() != GM.getId()) {
                                                    GM.sendPackets(new S_ServerMessage(166, "\\fW【一般】", " ( " + pc.getName() + " ) :【 ", chatText + " 】"));
                                                }
                                                else {
                                                    continue;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                        case 2: {
                            if (Config.GUI) {
                                J_Main.getInstance().addNormalChat(pc.getName(), chatText);
                            }
                            this.chatType_2(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD0) {
                                for (L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        L1PcInstance GM = (L1PcInstance)visible;
                                        if (GM.getcheckgm() && GM.isGm() && pc.getId() != GM.getId()) {
                                            GM.sendPackets(new S_ServerMessage(166, "\\fV【大喊】", " ( " + pc.getName() + " ) :【 ", chatText + " 】"));
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 4: {
                            if (Config.GUI) {
                                J_Main.getInstance().addClanChat(pc.getName(), chatText);
                            }
                            this.chatType_4(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD4) {
                                for (L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        L1PcInstance GM = (L1PcInstance)visible;
                                        if (GM.getcheckgm() && GM.isGm() && pc.getId() != GM.getId()) {
                                            GM.sendPackets(new S_ServerMessage(166, "\\fV【血盟】", " ( " + pc.getName() + " ) :【 ", chatText + " 】"));
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 11: {
                            if (Config.GUI) {
                                J_Main.getInstance().addTeamChat(pc.getName(), chatText);
                            }
                            this.chatType_11(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD11) {
                                for (L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        L1PcInstance GM = (L1PcInstance)visible;
                                        if (GM.getcheckgm() && GM.isGm() && pc.getId() != GM.getId()) {
                                            GM.sendPackets(new S_ServerMessage(166, "\\fV【隊伍】", " ( " + pc.getName() + " ) :【 ", chatText + " 】"));
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 13: {
                            if (Config.GUI) {
                                J_Main.getInstance().addClanChat(pc.getName(), chatText);
                            }
                            this.chatType_13(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD4) {
                                for (L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        L1PcInstance GM = (L1PcInstance)visible;
                                        if (GM.getcheckgm() && GM.isGm() && pc.getId() != GM.getId()) {
                                            GM.sendPackets(new S_ServerMessage(166, "\\fV【守護】", " ( " + pc.getName() + " ) :【 ", chatText + " 】"));
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 14: {
                            if (Config.GUI) {
                                J_Main.getInstance().addTeamChat(pc.getName(), chatText);
                            }
                            this.chatType_14(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD0) {
                                for (L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        L1PcInstance GM = (L1PcInstance)visible;
                                        if (GM.getcheckgm() && GM.isGm() && pc.getId() != GM.getId()) {
                                            GM.sendPackets(new S_ServerMessage(166, "\\fV【群聊】", " ( " + pc.getName() + " ) :【 ", chatText + " 】"));
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        case 15: {
                            this.chatType_15(pc, chatText);
                            break;
                        }
                    }
                    if (!pc.isGm()) {
                        pc.checkChatInterval();
                        return;
                    }
                    else {
                        return;
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
    
    private void chatType_15(L1PcInstance pc, String chatText) {
        if (pc.getClanid() != 0) {
            L1Clan clan = pc.getClan();
            if (clan == null) {
                return;
            }
            else {
                L1Alliance alliance = ClanAllianceReading.get().getAlliance(clan.getClanId());
                if (alliance == null) {
                    return;
                }
                else {
                    S_ChatClanAlliance chatpacket = new S_ChatClanAlliance(pc, clan.getClanName(), chatText);
                    alliance.sendPacketsAll(pc.getName(), chatpacket);
                }
            }
        }
    }
    
    private void re_repass(L1PcInstance pc, String password) {
        try {
            switch (pc.is_repass()) {
                case 1: {
                    if (!pc.getNetConnection().getAccount().get_password().equals(password)) {
                        pc.sendPackets(new S_ServerMessage(1744));
                        return;
                    }
                    else {
                        pc.repass(2);
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[] { "請輸入您的新密碼" }));
                        break;
                    }
                }
                case 2: {
                    boolean iserr = false;
                    int i = 0;
                    while (i < password.length()) {
                        String ch = password.substring(i, i + 1);
                        if (!"abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#".contains(ch.toLowerCase())) {
                            pc.sendPackets(new S_ServerMessage(1742));
                            iserr = true;
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    if (password.length() > 13) {
                        pc.sendPackets(new S_ServerMessage(166, "密碼長度過長"));
                        iserr = true;
                    }
                    if (password.length() < 3) {
                        pc.sendPackets(new S_ServerMessage(166, "密碼長度過長"));
                        iserr = true;
                    }
                    if (iserr) {
                        return;
                    }
                    else {
                        pc.setText(password);
                        pc.repass(3);
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[] { "請確認您的新密碼" }));
                        break;
                    }
                }
                case 3: {
                    if (!pc.getText().equals(password)) {
                        pc.sendPackets(new S_ServerMessage(1982));
                        return;
                    }
                    else {
                        pc.sendPackets(new S_CloseList(pc.getId()));
                        pc.getInventory().consumeItem(49538, 1L);
                        pc.sendPackets(new S_ServerMessage(1985));
                        AccountReading.get().updatePwd(pc.getAccountName(), password);
                        pc.setText(null);
                        pc.repass(0);
                        RecordTable.get().recordPcChangePassWord(pc.getId(), pc.getAccountName(), pc.getName(), password, pc.getIp());
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.sendPackets(new S_ServerMessage(45));
            pc.setText(null);
            pc.repass(0);
            C_Chat._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void re_title(L1PcInstance pc, String chatText) {
        try {
            String newchatText = chatText.trim();
            if (newchatText.isEmpty() || newchatText.length() <= 0) {
                pc.sendPackets(new S_ServerMessage("\\fU請輸入封號內容"));
                return;
            }
            else {
                int length = Config.LOGINS_TO_AUTOENTICATION ? 18 : 13;
                if (newchatText.getBytes().length > length) {
                    pc.sendPackets(new S_ServerMessage("\\fU封號長度過長"));
                    return;
                }
                else {
                    StringBuilder title = new StringBuilder();
                    title.append(newchatText);
                    pc.setTitle(title.toString());
                    pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
                    pc.save();
                    pc.retitle(false);
                    pc.sendPackets(new S_ServerMessage("\\fU封號變更完成"));
                    pc.getInventory().removeItem(49537, 1L);
                }
            }
        }
        catch (Exception e) {
            C_Chat._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void chatType_14(L1PcInstance pc, String chatText) {
        if (pc.isInChatParty()) {
            S_ChatParty2 chatpacket = new S_ChatParty2(pc, chatText);
            L1PcInstance[] partyMembers = pc.getChatParty().getMembers();
            L1PcInstance[] array;
            for (int length = (array = partyMembers).length, i = 0; i < length; ++i) {
                L1PcInstance listner = array[i];
                if (!listner.isBadInEnemyList(pc.getName()) && !listner.getExcludingList().contains(pc.getName())) {
                    listner.sendPackets(chatpacket);
                }
            }
            if (ConfigRecord.LOGGING_CHAT_CHAT_PARTY) {
                RecordTable.get().recordeTalk("群聊", pc.getName(), pc.getClanname(), null, chatText);
            }
        }
    }
    
    private void chatType_13(L1PcInstance pc, String chatText) {
        if (pc.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan == null) {
                return;
            }
            else {
                switch (pc.getClanRank()) {
                    case 3:
                    case 4:
                    case 6:
                    case 9:
                    case 10: {
                        S_ChatClanUnion chatpacket = new S_ChatClanUnion(pc, chatText);
                        L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                        L1PcInstance[] array;
                        for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                            L1PcInstance listner = array[i];
                            if (!listner.getExcludingList().contains(pc.getName())) {
                                switch (listner.getClanRank()) {
                                    case 3:
                                    case 4:
                                    case 6:
                                    case 9:
                                    case 10: {
                                        listner.sendPackets(chatpacket);
                                        break;
                                    }
                                }
                            }
                        }
                        if (ConfigRecord.LOGGING_CHAT_COMBINED) {
                            RecordTable.get().recordeTalk("聯盟", pc.getName(), pc.getClanname(), null, chatText);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private void chatType_11(L1PcInstance pc, String chatText) {
        if (pc.isInParty()) {
            S_ChatParty chatpacket = new S_ChatParty(pc, chatText);
            ConcurrentHashMap<Integer, L1PcInstance> pcs = pc.getParty().partyUsers();
            if (pcs.isEmpty()) {
                return;
            }
            else if (pcs.size() <= 0) {
                return;
            }
            else {
                for (L1PcInstance listner : pcs.values()) {
                    if (!listner.isBadInEnemyList(pc.getName()) && !listner.getExcludingList().contains(pc.getName())) {
                        listner.sendPackets(chatpacket);
                    }
                }
                if (ConfigRecord.LOGGING_CHAT_PARTY) {
                    RecordTable.get().recordeTalk("組隊", pc.getName(), pc.getClanname(), null, chatText);
                }
            }
        }
    }
    
    private void chatType_4(L1PcInstance pc, String chatText) {
        if (pc.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                S_ChatClan chatpacket = new S_ChatClan(pc, chatText);
                L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                L1PcInstance[] array;
                for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
                    L1PcInstance listner = array[i];
                    if (!listner.isBadInEnemyList(pc.getName()) && !listner.getExcludingList().contains(pc.getName())) {
                        listner.sendPackets(chatpacket);
                    }
                }
                if (ConfigRecord.LOGGING_CHAT_CLAN) {
                    RecordTable.get().recordeTalk("盟頻", pc.getName(), pc.getClanname(), null, chatText);
                }
            }
        }
    }
    
    private void chatType_2(L1PcInstance pc, String chatText) {
        if (pc.isGhost()) {
            return;
        }
        else {
            S_ChatShouting chatpacket = null;
            String name = pc.getName();
            if (pc.get_outChat() == null) {
                chatpacket = new S_ChatShouting(pc, chatText);
            }
            else {
                chatpacket = new S_ChatShouting(pc.get_outChat(), chatText);
                name = pc.get_outChat().getNameId();
            }
            pc.sendPackets(chatpacket);
            for (L1PcInstance listner : World.get().getVisiblePlayer(pc, 50)) {
                if (!listner.getExcludingList().contains(name) && !listner.isBadInEnemyList(pc.getName()) && pc.get_showId() == listner.get_showId()) {
                    listner.sendPackets(chatpacket);
                }
            }
            if (ConfigRecord.LOGGING_CHAT_SHOUT) {
                RecordTable.get().recordeTalk("大喊", pc.getName(), pc.getClanname(), null, chatText);
            }
            this.doppelShouting(pc, chatText);
            return;
        }
    }
    
    private void chatType_0(L1PcInstance pc, String chatText) {
        if (pc.isGhost() && !pc.isGm() && !pc.isMonitor()) {
            return;
        }
        else if ((pc.getAccessLevel() == 200 || pc.getAccessLevel() == 100) && chatText.startsWith(".")) {
            String cmd = chatText.substring(1);
            GMCommands.getInstance().handleCommands(pc, cmd);
            return;
        }
        else {
            S_Chat chatpacket = null;
            String name = pc.getName();
            if (pc.get_outChat() == null) {
                chatpacket = new S_Chat(pc, chatText);
            }
            else {
                chatpacket = new S_Chat(pc.get_outChat(), chatText);
                name = pc.get_outChat().getNameId();
            }
            pc.sendPackets(chatpacket);
            for (L1PcInstance listner : World.get().getRecognizePlayer(pc)) {
                if (!listner.isBadInEnemyList(pc.getName()) && pc.get_showId() == listner.get_showId()) {
                    listner.sendPackets(chatpacket);
                }
            }
            if (ConfigRecord.LOGGING_CHAT_NORMAL) {
                RecordTable.get().recordeTalk("一般", pc.getName(), pc.getClanname(), null, chatText);
            }
            this.doppelGenerally(pc, chatText);
            return;
        }
    }
    
    private void doppelGenerally(L1PcInstance pc, String chatText) {
        for (L1Object obj : pc.getKnownObjects()) {
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
                    mob.broadcastPacketX8(new S_NpcChat(mob, chatText));
                }
                else {
                    continue;
                }
            }
        }
    }
    
    private void doppelShouting(L1PcInstance pc, String chatText) {
        for (L1Object obj : pc.getKnownObjects()) {
            if (obj instanceof L1MonsterInstance) {
                L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
                    mob.broadcastPacketX8(new S_NpcChatShouting(mob, chatText));
                }
                else {
                    continue;
                }
            }
        }
    }
    
    private void check_broadcast(L1PcInstance pc, String chatText) {
        try {
            if (chatText.isEmpty() || chatText.length() <= 0) {
                pc.sendPackets(new S_SystemMessage("請輸入訊息內容。"));
                return;
            }
            else {
                if (pc.isGm()) {
                    if (chatText.equals("開啟")) {
                        BroadcastController.getInstance().setStop(false);
                        pc.sendPackets(new S_SystemMessage("廣播系統已開啟。"));
                        return;
                    }
                    else if (chatText.equals("關閉")) {
                        BroadcastController.getInstance().setStop(true);
                        pc.sendPackets(new S_SystemMessage("廣播系統已關閉。"));
                        return;
                    }
                }
                if (chatText.getBytes().length > 50) {
                    pc.sendPackets(new S_SystemMessage("廣播訊息長度過長 (不能超過25個中文字)"));
                    return;
                }
                else {
                    StringBuilder message = new StringBuilder();
                    message.append("玩家【").append(pc.getName()).append("】對大家說 : ").append(chatText);
                    L1ItemInstance item = pc.getInventory().checkItemX(BroadcastSet.ITEM_ID, 1L);
                    if (item == null) {
                        pc.sendPackets(new S_SystemMessage("不具有廣播卡，因此無法發送訊息。"));
                        return;
                    }
                    else if (BroadcastController.getInstance().requestWork(message.toString())) {
                        pc.getInventory().removeItem(item, 1L);
                        pc.sendPackets(new S_SystemMessage("已成功發布廣播訊息。"));
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("目前有太多等待訊息，請稍後再嘗試一次。"));
                    }
                }
            }
        }
        catch (Exception e) {
            C_Chat._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void Itemopen(L1PcInstance pc, String arg) {
        L1PcInstance target = World.get().getPlayer(arg);
        if (target != null) {
            List<L1ItemInstance> items = pc.getInventory().getItems();
            List<L1ItemInstance> itemsx = new CopyOnWriteArrayList();
            for (L1ItemInstance item : items) {
                if (item.isEquipped()) {
                    itemsx.add(item);
                }
            }
            target.sendPackets(new S_WhoHero(target, 0, itemsx));
            String msg1 = "";
            String msg2 = "";
            String msg3 = "";
            String msg4 = "";
            String msg5 = "";
            String msg6 = "";
            String msg7 = "";
            String msg8 = "";
            String msg9 = "";
            String msg10 = "";
            String msg11 = "";
            msg1 = new StringBuilder().append(pc.getLevel()).toString();
            msg2 = pc.getCurrentHp() + " / " + pc.getMaxHp();
            msg3 = pc.getCurrentMp() + " / " + pc.getMaxMp();
            msg4 = new StringBuilder().append(pc.getAc()).toString();
            msg5 = new StringBuilder().append((int)pc.getStr()).toString();
            msg6 = new StringBuilder().append((int)pc.getDex()).toString();
            msg7 = new StringBuilder().append((int)pc.getInt()).toString();
            msg8 = new StringBuilder().append((int)pc.getCon()).toString();
            msg9 = new StringBuilder().append((int)pc.getWis()).toString();
            msg10 = new StringBuilder().append((int)pc.getCha()).toString();
            msg11 = pc.getMr() + " %";
            target.sendPackets(new S_SystemMessage("\\fT等級:[" + msg1 + "]"));
            target.sendPackets(new S_SystemMessage("\\fW血量:[" + msg2 + "]  //  魔力:[" + msg3 + "]"));
            target.sendPackets(new S_SystemMessage("\\fU防禦:[" + msg4 + "]  //  魔防:[" + msg11 + "]"));
            target.sendPackets(new S_SystemMessage("\\fR力量:[" + msg5 + "]  //  敏捷:[" + msg6 + "]"));
            target.sendPackets(new S_SystemMessage("\\fR智力:[" + msg7 + "]  //  體質:[" + msg8 + "]"));
            target.sendPackets(new S_SystemMessage("\\fR精神:[" + msg9 + "]  //  魅力:[" + msg10 + "]"));
        }
    }
    
    public void ItemName(L1PcInstance pc, String arg) {
        StringTokenizer st = new StringTokenizer(arg);
        String nameid = st.nextToken();
        int dropID = 0;
        try {
            dropID = Integer.parseInt(nameid);
        }
        catch (NumberFormatException e) {
            dropID = ItemTable.get().findItemIdByNameWithoutSpace1(nameid);
            if (dropID == 0) {
                pc.sendPackets(new S_SystemMessage("找不到您指定的道具名稱"));
                return;
            }
        }
        if (dropID == 40308) {
            pc.sendPackets(new S_SystemMessage("【金幣】幾乎都會掉落"));
        }
        else {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                L1Item item = ItemTable.get().getTemplate(dropID);
                String blessed;
                if (item.getBless() == 1) {
                    blessed = "";
                }
                else if (item.getBless() == 0) {
                    blessed = "\\fR";
                }
                else {
                    blessed = "\\fY";
                }
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("SELECT mobId,min,max,chance FROM droplist WHERE itemId=?");
                pstm.setInt(1, dropID);
                rs = pstm.executeQuery();
                rs.last();
                int rows = rs.getRow();
                int[] mobID = new int[rows];
                double[] chance = new double[rows];
                String[] name = new String[rows];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    mobID[i] = rs.getInt("mobId");
                    chance[i] = (double)rs.getInt("chance") / 10000.0;
                    ++i;
                }
                rs.close();
                pstm.close();
                pc.sendPackets(new S_SystemMessage("\\f=此物品:【" + blessed + item.getName() + "】掉落的怪物如下:"));
                for (int j = 0; j < mobID.length; ++j) {
                    pstm = con.prepareStatement("SELECT name FROM npc WHERE npcid=?");
                    pstm.setInt(1, mobID[j]);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        name[j] = rs.getString("name");
                    }
                    rs.close();
                    pstm.close();
                    if (pc.isGm()) {
                        pc.sendPackets(new S_SystemMessage("\\fS怪物: " + name[j] + " 機率: " + chance[j] + "%"));
                    }
                    else {
                        pc.sendPackets(new S_SystemMessage("\\fS怪物: " + name[j]));
                    }
                }
            }
            catch (Exception ex) {}
            finally {
                SQLUtil.close(rs, pstm, con);
            }
        }
    }
    
    private void KeyInEnemyName(L1PcInstance pc, String name) {
        if (!CharacterTable.doesCharNameExist(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
            pc.setKeyInEnemy(false);
        }
        else if (!pc.isInEnemyList(name)) {
            if (pc.getName().equals(name)) {
                pc.sendPackets(new S_ServerMessage("\\fU不能添加自己為仇人"));
            }
            else {
                pc.sendPackets(new S_ServerMessage("\\fU資料確認成功 !已經成功添加名單"));
                NewAutoPractice.get().AddEnemyList(pc, name);
                pc.setKeyInEnemy(false);
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.setInEnemyList(name);
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\fU名稱重複 請確認"));
            pc.setKeyInEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void KeyOutEnemyName(L1PcInstance pc, String name) {
        if (!CharacterTable.doesCharNameExist(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
            pc.setKeyOutEnemy(false);
        }
        else if (pc.isInEnemyList(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU資料確認成功 !已經成功從名單中移除"));
            NewAutoPractice.get().DeleteEnemyList(pc, name);
            pc.setKeyOutEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.removeInEnemyList(name);
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\fU你的仇人名單內並無此人喔"));
            pc.setKeyOutEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void BadKeyInEnemyName(L1PcInstance pc, String name) {
        if (!CharacterTable.doesCharNameExist(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
            pc.setBadKeyInEnemy(false);
        }
        else if (!pc.isBadInEnemyList(name)) {
            if (pc.getName().equals(name)) {
                pc.sendPackets(new S_ServerMessage("\\fU不能添加自己為黑名單"));
            }
            else {
                pc.sendPackets(new S_ServerMessage("\\fU資料確認成功 !已經成功添加名單"));
                NewAutoPractice.get().AddBadEnemyList(pc, name);
                pc.setBadKeyInEnemy(false);
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.setBadInEnemyList(name);
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\fU名稱重複 請確認"));
            pc.setBadKeyInEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void BadKeyOutEnemyName(L1PcInstance pc, String name) {
        if (!CharacterTable.doesCharNameExist(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
            pc.setBadKeyOutEnemy(false);
        }
        else if (pc.isBadInEnemyList(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU資料確認成功 !已經成功從黑名單中移除"));
            NewAutoPractice.get().DeleteBadEnemyList(pc, name);
            pc.setBadKeyOutEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.removeBadInEnemyList(name);
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\fU你的黑名單內並無此人喔"));
            pc.setBadKeyOutEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    private class TeleportRunnable implements Runnable
    {
        private final L1PcInstance _pc;
        private int _locX;
        private int _locY;
        private int _mapid;
        
        public TeleportRunnable(L1PcInstance pc, int x, int y, int mapid) {
            this._locX = 0;
            this._locY = 0;
            this._mapid = 0;
            this._pc = pc;
            this._locX = x;
            this._locY = y;
            this._mapid = mapid;
        }
        
        @Override
        public void run() {
            try {
                L1Map map = L1WorldMap.get().getMap((short)this._mapid);
                int r = 10;
                int tryCount = 0;
                int newX = this._locX;
                int newY = this._locY;
                do {
                    ++tryCount;
                    newX = this._locX + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                    newY = this._locY + (int)(Math.random() * (double)r) - (int)(Math.random() * (double)r);
                    if (map.isPassable(newX, newY, this._pc)) {
                        break;
                    }
                    else {
                        Thread.sleep(1L);
                    }
                } while (tryCount < 5);
                if (tryCount >= 5) {
                    L1Teleport.teleport(this._pc, this._locX, this._locY, (short)this._mapid, this._pc.getHeading(), true);
                }
                else {
                    L1Teleport.teleport(this._pc, newX, newY, (short)this._mapid, this._pc.getHeading(), true);
                }
            }
            catch (InterruptedException ex) {}
        }
    }
}
