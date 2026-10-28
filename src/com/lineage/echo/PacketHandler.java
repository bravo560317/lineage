package com.lineage.echo;

import com.lineage.server.clientpackets.C_PledgeContent;
import com.lineage.server.clientpackets.C_ClanMatching;
import com.lineage.server.clientpackets.C_HireSoldier;
import com.lineage.server.clientpackets.C_FishClick;
import com.lineage.server.clientpackets.C_AutoLogin;
import com.lineage.server.clientpackets.C_Windows;
import com.lineage.server.clientpackets.C_KeepALIVE;
import com.lineage.server.clientpackets.C_Password;
import com.lineage.server.clientpackets.C_Teleport;
import com.lineage.server.clientpackets.C_EmblemDownload;
import com.lineage.server.clientpackets.C_Ship;
import com.lineage.server.clientpackets.C_Mail;
import com.lineage.server.clientpackets.C_Fight;
import com.lineage.server.clientpackets.C_ChatParty;
import com.lineage.server.clientpackets.C_Rank;
import com.lineage.server.clientpackets.C_UsePetItem;
import com.lineage.server.clientpackets.C_PetMenu;
import com.lineage.server.clientpackets.C_SelectTarget;
import com.lineage.server.clientpackets.C_CallPlayer;
import com.lineage.server.clientpackets.C_ExitGhost;
import com.lineage.server.clientpackets.C_SelectList;
import com.lineage.server.clientpackets.C_FixWeaponList;
import com.lineage.server.clientpackets.C_Amount;
import com.lineage.server.clientpackets.C_EnterPortal;
import com.lineage.server.clientpackets.C_CreateParty;
import com.lineage.server.clientpackets.C_ItemUSe;
import com.lineage.server.clientpackets.C_BoardWrite;
import com.lineage.server.clientpackets.C_ExtraCommand;
import com.lineage.server.clientpackets.C_CreateChar;
import com.lineage.server.clientpackets.C_CommonClick;
import com.lineage.server.clientpackets.C_JoinClan;
import com.lineage.server.clientpackets.C_ChatGlobal;
import com.lineage.server.clientpackets.C_ShopList;
import com.lineage.server.clientpackets.C_LoginToServer;
import com.lineage.server.clientpackets.C_War;
import com.lineage.server.clientpackets.C_DelBuddy;
import com.lineage.server.clientpackets.C_BanParty;
import com.lineage.server.clientpackets.C_NPCTalk;
import com.lineage.server.clientpackets.C_LeaveClan;
import com.lineage.server.clientpackets.C_Restart;
import com.lineage.server.clientpackets.C_DeleteBookmark;
import com.lineage.server.clientpackets.C_MoveChar;
import com.lineage.server.clientpackets.C_GiveItem;
import com.lineage.server.clientpackets.C_Who;
import com.lineage.server.clientpackets.C_PickUpItem;
import com.lineage.server.clientpackets.C_Party;
import com.lineage.server.clientpackets.C_ChatWhisper;
import com.lineage.server.clientpackets.C_DeleteInventoryItem;
import com.lineage.server.clientpackets.C_Board;
import com.lineage.server.clientpackets.C_BanClan;
import com.lineage.server.clientpackets.C_AttackBow;
import com.lineage.server.clientpackets.C_Attack;
import com.lineage.server.clientpackets.C_LeaveParty;
import com.lineage.server.clientpackets.C_DropItem;
import com.lineage.server.clientpackets.C_Buddy;
import com.lineage.server.clientpackets.C_NewCharSelect;
import com.lineage.server.clientpackets.C_TaxRate;
import com.lineage.server.clientpackets.C_CheckPK;
import com.lineage.server.clientpackets.C_TradeOK;
import com.lineage.server.clientpackets.C_Chat;
import com.lineage.server.clientpackets.C_AddBuddy;
import com.lineage.server.clientpackets.C_TradeAddItem;
import com.lineage.server.clientpackets.C_SkillBuyItem;
import com.lineage.server.clientpackets.C_SkillBuyOK;
import com.lineage.server.clientpackets.C_SkillBuy;
import com.lineage.server.clientpackets.C_LoginToServerOK;
import com.lineage.server.clientpackets.C_Drawal;
import com.lineage.server.clientpackets.C_Deposit;
import com.lineage.server.clientpackets.C_Result;
import com.lineage.server.clientpackets.C_AuthLogin;
import com.lineage.server.clientpackets.C_Attr;
import com.lineage.server.clientpackets.C_DeleteChar;
import com.lineage.server.clientpackets.C_Trade;
import com.lineage.server.clientpackets.C_BoardRead;
import com.lineage.server.clientpackets.C_Shop;
import com.lineage.server.clientpackets.C_BoardBack;
import com.lineage.server.clientpackets.C_Propose;
import com.lineage.server.clientpackets.C_ServerVersion;
import com.lineage.server.clientpackets.C_CreateClan;
import com.lineage.server.clientpackets.C_AddBookmark;
import com.lineage.server.clientpackets.C_ChangeWarTime;
import com.lineage.server.clientpackets.C_TradeCancel;
import com.lineage.server.clientpackets.C_EmblemUpload;
import com.lineage.server.clientpackets.C_UseSkill;
import com.lineage.server.clientpackets.C_NPCAction;
import com.lineage.server.clientpackets.C_ChangeHeading;
import com.lineage.server.clientpackets.C_Pledge;
import com.lineage.server.clientpackets.C_BoardDelete;
import com.lineage.server.clientpackets.C_Title;
import com.lineage.server.clientpackets.C_Door;
import com.lineage.server.clientpackets.C_CharcterConfig;
import com.lineage.server.clientpackets.C_Exclude;
import com.lineage.server.clientpackets.C_Disconnect;
import com.lineage.server.clientpackets.C_CharReset;
import com.lineage.server.clientpackets.C_ExtraPacket;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.lineage.server.datatables.RecordTable;
import com.lineage.echo.encryptions.PacketPrint;
import com.lineage.config.Config;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.clientpackets.ClientBasePacket;
import java.util.Map;
import org.apache.commons.logging.Log;

public class PacketHandler extends PacketHandlerExecutor
{
    private static final Log _log;
    private static final Map<Integer, ClientBasePacket> _opListClient;
    private ClientExecutor _client;
    
    static {
        _log = LogFactory.getLog(PacketHandler.class);
        _opListClient = new HashMap();
    }
    
    @Override
    public void handlePacket(byte[] decrypt) {
        ClientBasePacket basePacket = null;
        if (decrypt == null) {
            return;
        }
        else if (decrypt.length <= 0) {
            return;
        }
        else {
            try {
                int key = decrypt[0] & 0xFF;
                basePacket = PacketHandler._opListClient.get((Integer)key);
                if (Config.DEBUG && basePacket != null) {
                    PacketHandler._log.info("客戶端: " + basePacket.getType() + "\nOP ID: " + key + " length:" + decrypt.length + "\nInfo:\n" + PacketPrint.get().printData(decrypt, decrypt.length));
                }
                if (basePacket == null) {
                    PacketHandler._log.info("\nClient: " + key + "\n" + PacketPrint.get().printData(decrypt, decrypt.length) + this.getNow_YMDHMS());
                    RecordTable.get().r_bug("未處理C包:" + key);
                }
                else {
                    basePacket.start(decrypt, this._client);
                }
            }
            catch (Exception e) {
                if (Config.DEBUG) {
                    String name = "Not Login Pc";
                    if (this._client.getActiveChar() != null) {
                        name = this._client.getActiveChar().getName();
                    }
                    PacketHandler._log.error("OP ID: " + (decrypt[0] & 0xFF) + " Pc Name: " + name + "\n" + basePacket.getType() + "\n" + PacketPrint.get().printData(decrypt, decrypt.length), e);
                }
            }
            finally {
                basePacket = null;
            }
            return;
        }
    }
    
    private final String getNow_YMDHMS() {
        String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        return nowDate;
    }
    
    public PacketHandler(ClientExecutor client) {
        this._client = client;
    }
    
    public static void load() {
        put(1, new C_ExtraPacket());
        put(98, new C_CharReset());
        put(122, new C_Disconnect());
        put(171, new C_Exclude());
        put(244, new C_CharcterConfig());
        put(41, new C_Door());
        put(90, new C_Title());
        put(153, new C_BoardDelete());
        put(68, new C_Pledge());
        put(225, new C_ChangeHeading());
        put(125, new C_NPCAction());
        put(6, new C_UseSkill());
        put(18, new C_EmblemUpload());
        put(86, new C_TradeCancel());
        put(1443, new C_ChangeWarTime());
        put(165, new C_AddBookmark());
        put(222, new C_CreateClan());
        put(14, new C_ServerVersion());
        put(50, new C_Propose());
        put(23, new C_BoardBack());
        put(38, new C_Shop());
        put(114, new C_BoardRead());
        put(2, new C_Trade());
        put(162, new C_DeleteChar());
        put(121, new C_Attr());
        put(119, new C_AuthLogin());
        put(161, new C_Result());
        put(56, new C_Deposit());
        put(44, new C_Drawal());
        put(26, new C_LoginToServerOK());
        put(145, new C_SkillBuy());
        put(39, new C_SkillBuyOK());
        put(245, new C_SkillBuyItem());
        put(37, new C_TradeAddItem());
        put(207, new C_AddBuddy());
        put(136, new C_Chat());
        put(71, new C_TradeOK());
        put(51, new C_CheckPK());
        put(19, new C_TaxRate());
        put(7, new C_NewCharSelect());
        put(4, new C_Buddy());
        put(25, new C_DropItem());
        put(33, new C_LeaveParty());
        put(229, new C_Attack());
        put(123, new C_AttackBow());
        put(69, new C_BanClan());
        put(10, new C_Board());
        put(138, new C_DeleteInventoryItem());
        put(184, new C_ChatWhisper());
        put(43, new C_Party());
        put(112, new C_PickUpItem());
        put(206, new C_Who());
        put(45, new C_GiveItem());
        put(29, new C_MoveChar());
        put(3, new C_DeleteBookmark());
        put(177, new C_Restart());
        put(61, new C_LeaveClan());
        put(34, new C_NPCTalk());
        put(255, new C_BanParty());
        put(202, new C_DelBuddy());
        put(227, new C_War());
        put(137, new C_LoginToServer());
        put(47, new C_ShopList());
        put(40, new C_ChatGlobal());
        put(194, new C_JoinClan());
        put(16, new C_CommonClick());
        put(84, new C_CreateChar());
        put(120, new C_ExtraCommand());
        put(141, new C_BoardWrite());
        put(164, new C_ItemUSe());
        put(230, new C_CreateParty());
        put(219, new C_EnterPortal());
        put(11, new C_Amount());
        put(118, new C_FixWeaponList());
        put(20, new C_SelectList());
        put(173, new C_ExitGhost());
        put(185, new C_CallPlayer());
        put(223, new C_SelectTarget());
        put(103, new C_PetMenu());
        put(104, new C_UsePetItem());
        put(63, new C_Rank());
        put(199, new C_ChatParty());
        put(5, new C_Fight());
        put(87, new C_Mail());
        put(231, new C_Ship());
        put(72, new C_EmblemDownload());
        put(52, new C_Teleport());
        put(246, new C_Teleport());
        put(13, new C_Password());
        put(95, new C_KeepALIVE());
        put(254, new C_Windows());
        put(210, new C_AutoLogin());
        put(62, new C_FishClick());
        put(1411, new C_HireSoldier());
        put(76, new C_ClanMatching());
        put(78, new C_PledgeContent());
        put(99, new C_Mail());
    }
    
    public static void put(Integer key, ClientBasePacket value) {
        if (PacketHandler._opListClient.get(key) == null) {
            PacketHandler._opListClient.put(key, value);
        }
        else if (!key.equals((Integer)(-1))) {
            PacketHandler._log.error("重複標記的OPID: " + key + " " + value.getType());
        }
    }
}
