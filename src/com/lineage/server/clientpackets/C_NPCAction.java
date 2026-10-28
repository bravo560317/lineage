package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Account;
import com.lineage.william.ezpay;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.william.ezpayfirst;
import com.lineage.william.Ezpay_bz;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import com.lineage.william.PayBonus;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_EffectLocation;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.TimeZone;
import com.lineage.config.Config;
import com.lineage.config.ConfigAlt;
import java.util.Collection;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.datatables.DoorSpawnTable;
import com.lineage.server.serverpackets.S_SellHouse;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.L1PetMatch;
import com.lineage.server.model.L1HauntedHouse;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1UltimateBattle;
import com.lineage.server.datatables.UBTable;
import java.util.Map;
import com.lineage.server.templates.L1Item;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.templates.L1House;
import com.lineage.server.templates.L1Castle;
import java.util.Iterator;
import com.lineage.server.templates.L1Inn;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.npc.action.L1NpcAction;
import com.add.system.L1Blend1;
import com.add.system.L1Blend;
import com.lineage.server.model.L1Object;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_SkillIconAura;
import com.lineage.config.ConfigRate;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.Instance.L1HousekeeperInstance;
import com.lineage.server.model.L1HouseLocation;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.serverpackets.S_PetList;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_Deposit;
import com.lineage.server.serverpackets.S_Drawal;
import com.lineage.server.serverpackets.S_TaxRate;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.sql.Timestamp;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.serverpackets.S_HowManyKey;
import java.util.Calendar;
import com.lineage.server.datatables.InnTable;
import com.lineage.server.serverpackets.S_PledgeWarehouseHistory;
import com.lineage.server.serverpackets.S_RetrievePledgeList;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.S_RetrieveElfList;
import com.lineage.server.serverpackets.S_RetrieveChaList;
import com.lineage.server.serverpackets.S_RetrieveList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ShopBuyList;
import com.lineage.server.serverpackets.S_ShopSellList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.NpcActionTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.ServerQuestMaPTable;
import com.lineage.server.datatables.LaBarGameTable;
import com.lineage.william.castleid_npc;
import com.lineage.william.Npc_Rush;
import com.add.system.ItemActionTele1;
import com.lineage.william.Npc_Combind;
import com.lineage.william.Npc_BuyPet;
import com.lineage.william.Npc_ins;
import com.lineage.server.datatables.LaBarGameTable2;
import com.lineage.server.datatables.LaBarGameTable1;
import com.add.system.L1BlendTable_1;
import com.lineage.server.serverpackets.S_CloseList;
import com.add.system.L1BlendTable;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.william.WilliamBuff;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import java.io.BufferedWriter;
import java.util.Random;
import org.apache.commons.logging.Log;

public class C_NPCAction extends ClientBasePacket
{
    private static final Log _log;
    private static Random _random;
    private static BufferedWriter out;
    
    static {
        _log = LogFactory.getLog(C_NPCAction.class);
        C_NPCAction._random = new Random();
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isPrivateShop()) {
                            int objid = this.readD();
                            String cmd = this.readS();
                            int[] materials = null;
                            int[] counts = null;
                            int[] createitem = null;
                            int[] createcount = null;
                            String htmlid = null;
                            String success_htmlid = null;
                            String failure_htmlid = null;
                            String[] htmldata = null;
                            L1Npc npctemp = null;
                            L1Object obj = World.get().findObject(objid);
                            if (obj == null) {
                                C_NPCAction._log.error("該OBJID編號的 NPC已經不存在世界中: " + objid);
                                return;
                            }
                            else {

                                if (obj instanceof L1NpcInstance) {
                                    L1NpcInstance npc = (L1NpcInstance)obj;
                                    npctemp = npc.getNpcTemplate();
                                    if (WilliamBuff.giveBuff(pc, npc, cmd)) {
                                        return;
                                    }
                                    else {
                                        String s2 = null;
                                        try {
                                            if (npctemp.get_classname().equalsIgnoreCase("other.Npc_AuctionBoard")) {
                                                s2 = this.readS();
                                            }
                                            else if (npctemp.get_classname().equalsIgnoreCase("other.Npc_Board")) {
                                                s2 = this.readS();
                                            }
                                        }
                                        catch (Exception ex) {}
                                        if (obj instanceof L1PetInstance) {
                                            L1PetInstance pet = (L1PetInstance)obj;
                                            pc.getActionPet().action(pet, cmd);
                                            return;
                                        }
                                        else if (obj instanceof L1SummonInstance) {
                                            L1SummonInstance summon = (L1SummonInstance)obj;
                                            pc.getActionSummon().action(summon, cmd);
                                            return;
                                        }
                                        else {
                                            int difflocx = Math.abs(pc.getX() - npc.getX());
                                            int difflocy = Math.abs(pc.getY() - npc.getY());
                                            if (difflocx > 5 || difflocy > 5) {
                                                return;
                                            }
                                            else if (cmd.equalsIgnoreCase("request craft")) {
                                                this.ShowCraftList(pc, npc);
                                                return;
                                            }
                                            else {
                                                String craftkey = String.valueOf(npctemp.get_npcId()) + cmd;
                                                L1Blend ItemBlend = L1BlendTable.getInstance().getTemplate(craftkey);
                                                if (ItemBlend != null) {
                                                    ItemBlend.ShowCraftHtml(pc, npc, ItemBlend);
                                                    npc.set_craftkey(craftkey);
                                                    return;
                                                }
                                                else {
                                                    String craftkey2 = npc.get_craftkey();
                                                    L1Blend ItemBlend2 = L1BlendTable.getInstance().getTemplate(craftkey2);
                                                    if (ItemBlend2 != null) {
                                                        if (cmd.equalsIgnoreCase("confirm craft")) {
                                                            ItemBlend2.CheckCraftItem(pc, npc, ItemBlend2, 1, false);
                                                            return;
                                                        }
                                                        else if (cmd.equalsIgnoreCase("cancel craft")) {
                                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                                            npc.set_craftkey(null);
                                                            return;
                                                        }
                                                    }
                                                    if (cmd.equalsIgnoreCase("request craft")) {
                                                        this.ShowCraftList1(pc, npc);
                                                        return;
                                                    }
                                                    else {
                                                        String craftkey3 = String.valueOf(npctemp.get_npcId()) + cmd;
                                                        L1Blend1 ItemBlend3 = L1BlendTable_1.getInstance().getTemplate(craftkey3);
                                                        if (ItemBlend3 != null) {
                                                            ItemBlend3.ShowCraftHtml(pc, npc, ItemBlend3);
                                                            npc.set_craftkey(craftkey3);
                                                            return;
                                                        }
                                                        else {
                                                            String craftkey4 = npc.get_craftkey();
                                                            L1Blend1 ItemBlend4 = L1BlendTable_1.getInstance().getTemplate(craftkey4);
                                                            if (ItemBlend4 != null) {
                                                                if (cmd.equalsIgnoreCase("confirm craft")) {
                                                                    ItemBlend4.CheckCraftItem(pc, npc, ItemBlend4, 1, false);
                                                                    return;
                                                                }
                                                                else if (cmd.equalsIgnoreCase("cancel craft")) {
                                                                    pc.sendPackets(new S_CloseList(pc.getId()));
                                                                    npc.set_craftkey(null);
                                                                    return;
                                                                }
                                                            }
                                                            if (cmd.equalsIgnoreCase("collect")) {
                                                                pc.setpag(0);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (cmd.equalsIgnoreCase("collect1")) {
                                                                pc.setpag(1);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (cmd.equalsIgnoreCase("collect2")) {
                                                                pc.setpag(2);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (cmd.equalsIgnoreCase("collect3")) {
                                                                pc.setpag(3);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (cmd.equalsIgnoreCase("collect4")) {
                                                                pc.setpag(4);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (cmd.equalsIgnoreCase("collect5")) {
                                                                pc.setpag(5);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (cmd.equalsIgnoreCase("collect6")) {
                                                                pc.setpag(6);
                                                                this.Gqpc(pc, npc);
                                                                return;
                                                            }
                                                            else if (npc.ACTION != null) {
                                                                if (s2 != null && s2.length() > 0) {
                                                                    npc.ACTION.action(pc, npc, cmd + "," + s2, 0L);
                                                                    return;
                                                                }
                                                                else {
                                                                    npc.ACTION.action(pc, npc, cmd, 0L);
                                                                    return;
                                                                }
                                                            }
                                                            else if (LaBarGameTable1.forLaBarGame(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (LaBarGameTable2.forLaBarGame(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (Npc_ins.forNpcQuest(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (Npc_BuyPet.forNpcQuest(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (Npc_Combind.forNpcQuest(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (ItemActionTele1.forNpcQuest(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (Npc_Rush.forNpcQuest(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (castleid_npc.forNpcQuest(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (LaBarGameTable.forLaBarGame(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else if (ServerQuestMaPTable.checkMobMap(cmd, pc, (L1NpcInstance)obj, ((L1NpcInstance)obj).getNpcTemplate().get_npcId(), objid)) {
                                                                htmlid = "";
                                                                return;
                                                            }
                                                            else {
                                                                npc.onFinalAction(pc, cmd);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                else if (obj instanceof L1PcInstance) {
                                    L1PcInstance target = (L1PcInstance)obj;
                                    target.getAction().action(cmd, 0L);
                                    return;
                                }
                                L1NpcAction action = NpcActionTable.getInstance().get(cmd, pc, obj);
                                if (action != null) {
                                    L1NpcHtml result = action.execute(cmd, pc, obj, this.readByte());
                                    if (result != null) {
                                        pc.sendPackets(new S_NPCTalkReturn(obj.getId(), result));
                                    }
                                    return;
                                }
                                else {
                                    if (cmd.equalsIgnoreCase("buy")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        if (this.isNpcSellOnly(npc2)) {
                                            return;
                                        }
                                        else {
                                            pc.sendPackets(new S_ShopSellList(objid));
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("sell")) {
                                        int npcid = npctemp.get_npcId();
                                        if (npcid == 70523 || npcid == 70805) {
                                            htmlid = "ladar2";
                                        }
                                        else if (npcid == 70537 || npcid == 70807) {
                                            htmlid = "farlin2";
                                        }
                                        else if (npcid == 70525 || npcid == 70804) {
                                            htmlid = "lien2";
                                        }
                                        else if (npcid == 50527 || npcid == 50505 || npcid == 50519 || npcid == 50545 || npcid == 50531 || npcid == 50529 || npcid == 50516 || npcid == 50538 || npcid == 50518 || npcid == 50509 || npcid == 50536 || npcid == 50520 || npcid == 50543 || npcid == 50526 || npcid == 50512 || npcid == 50510 || npcid == 50504 || npcid == 50525 || npcid == 50534 || npcid == 50540 || npcid == 50515 || npcid == 50513 || npcid == 50528 || npcid == 50533 || npcid == 50542 || npcid == 50511 || npcid == 50501 || npcid == 50503 || npcid == 50508 || npcid == 50514 || npcid == 50532 || npcid == 50544 || npcid == 50524 || npcid == 50535 || npcid == 50521 || npcid == 50517 || npcid == 50537 || npcid == 50539 || npcid == 50507 || npcid == 50530 || npcid == 50502 || npcid == 50506 || npcid == 50522 || npcid == 50541 || npcid == 50523 || npcid == 50620 || npcid == 50623 || npcid == 50619 || npcid == 50621 || npcid == 50622 || npcid == 50624 || npcid == 50617 || npcid == 50614 || npcid == 50618 || npcid == 50616 || npcid == 50615 || npcid == 50626 || npcid == 50627 || npcid == 50628 || npcid == 50629 || npcid == 50630 || npcid == 50631) {
                                            String sellHouseMessage = this.sellHouse(pc, objid, npcid);
                                            if (sellHouseMessage != null) {
                                                htmlid = sellHouseMessage;
                                            }
                                        }
                                        else {
                                            pc.sendPackets(new S_ShopBuyList(objid, pc));
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("Hierarch_1")) {
                                        if (pc.getHierarch() > 0) {
                                            pc.setHierarch(pc.getHierarch() - 1);
                                        }
                                        String msg0 = "休息";
                                        String msg2 = String.valueOf(pc.getHierarch() * 10);
                                        if (((L1NpcInstance)obj).getHierarch() == 1) {
                                            msg0 = "輔助";
                                        }
                                        htmldata = new String[] { ((L1NpcInstance)obj).getName(), String.valueOf(((L1NpcInstance)obj).getCurrentMp()), String.valueOf(((L1NpcInstance)obj).getMaxMp()), msg0, msg2 };
                                        htmlid = "Hierarch";
                                    }
                                    else if (cmd.equalsIgnoreCase("Hierarch_2")) {
                                        if (pc.getHierarch() < 10) {
                                            pc.setHierarch(pc.getHierarch() + 1);
                                        }
                                        String msg0 = "休息";
                                        String msg2 = String.valueOf(pc.getHierarch() * 10);
                                        if (((L1NpcInstance)obj).getHierarch() == 1) {
                                            msg0 = "輔助";
                                        }
                                        htmldata = new String[] { ((L1NpcInstance)obj).getName(), String.valueOf(((L1NpcInstance)obj).getCurrentMp()), String.valueOf(((L1NpcInstance)obj).getMaxMp()), msg0, msg2 };
                                        htmlid = "Hierarch";
                                    }
                                    else if (cmd.equalsIgnoreCase("Hierarch_3")) {
                                        ((L1NpcInstance)obj).setHierarch(1);
                                        String msg0 = "跟隨";
                                        String msg2 = String.valueOf(pc.getHierarch() * 10);
                                        if (((L1NpcInstance)obj).getHierarch() == 1) {
                                            msg0 = "輔助";
                                        }
                                        htmldata = new String[] { ((L1NpcInstance)obj).getName(), String.valueOf(((L1NpcInstance)obj).getCurrentMp()), String.valueOf(((L1NpcInstance)obj).getMaxMp()), msg0, msg2 };
                                        htmlid = "Hierarch";
                                    }
                                    else if (cmd.equalsIgnoreCase("Hierarch_4")) {
                                        ((L1NpcInstance)obj).setHierarch(0);
                                        String msg0 = "跟隨";
                                        String msg2 = String.valueOf(pc.getHierarch() * 10);
                                        if (((L1NpcInstance)obj).getHierarch() == 1) {
                                            msg0 = "輔助";
                                        }
                                        htmldata = new String[] { ((L1NpcInstance)obj).getName(), String.valueOf(((L1NpcInstance)obj).getCurrentMp()), String.valueOf(((L1NpcInstance)obj).getMaxMp()), msg0, msg2 };
                                        htmlid = "Hierarch";
                                    }
                                    else if (cmd.equalsIgnoreCase("retrieve")) {
                                        if (pc.getLevel() >= ConfigOther.warehouselevel) {
                                            int size = pc.getDwarfInventory().getItems().size();
                                            if (size > 0) {
                                                if (this.isTwoLogin(pc)) {
                                                    return;
                                                }
                                                else {
                                                    int srcpwd = client.getAccount().get_warehouse();
                                                    if (srcpwd > 0) {
                                                        pc.sendPackets(new S_ServerMessage(834));
                                                        return;
                                                    }
                                                    else {
                                                        if (!pc.isGm()) {
                                                            Thread.sleep((long)(C_NPCAction._random.nextInt(5) * 1000));
                                                        }
                                                        pc.sendPackets(new S_RetrieveList(objid, pc));
                                                    }
                                                }
                                            }
                                            else {
                                                pc.sendPackets(new S_NPCTalkReturn(objid, "noitemret"));
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("retrieve-char")) {
                                        if (pc.getLevel() >= ConfigOther.warehouselevel) {
                                            int size = pc.getDwarfForChaInventory().getItems().size();
                                            if (size > 0) {
                                                if (this.isTwoLogin(pc)) {
                                                    return;
                                                }
                                                else {
                                                    int srcpwd = client.getAccount().get_warehouse();
                                                    if (srcpwd > 0) {
                                                        pc.sendPackets(new S_ServerMessage(834));
                                                        return;
                                                    }
                                                    else {
                                                        if (!pc.isGm()) {
                                                            Thread.sleep((long)(C_NPCAction._random.nextInt(5) * 1000));
                                                        }
                                                        pc.sendPackets(new S_RetrieveChaList(objid, pc));
                                                    }
                                                }
                                            }
                                            else {
                                                pc.sendPackets(new S_NPCTalkReturn(objid, "noitemret"));
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("retrieve-elven")) {
                                        if (pc.getLevel() >= ConfigOther.warehouselevel && pc.isElf()) {
                                            int size = pc.getDwarfForElfInventory().getSize();
                                            if (size > 0) {
                                                if (this.isTwoLogin(pc)) {
                                                    return;
                                                }
                                                else {
                                                    int srcpwd = client.getAccount().get_warehouse();
                                                    if (srcpwd > 0) {
                                                        pc.sendPackets(new S_ServerMessage(834));
                                                        return;
                                                    }
                                                    else {
                                                        if (!pc.isGm()) {
                                                            Thread.sleep((long)(C_NPCAction._random.nextInt(5) * 1000));
                                                        }
                                                        pc.sendPackets(new S_RetrieveElfList(objid, pc));
                                                    }
                                                }
                                            }
                                            else {
                                                pc.sendPackets(new S_NPCTalkReturn(objid, "noitemret"));
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("retrieve-pledge")) {
                                        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                        if (pc.getLevel() >= ConfigOther.warehouselevel) {
                                            if (this.isTwoLogin(pc)) {
                                                return;
                                            }
                                            else if (pc.getClanid() == 0) {
                                                pc.sendPackets(new S_ServerMessage(208));
                                                return;
                                            }
                                            else if (clan.getWarehouseUsingChar() > 0) {
                                                pc.sendPackets(new S_ServerMessage("血盟倉庫有人正在使用中"));
                                                return;
                                            }
                                            else {
                                                int rank = pc.getClanRank();
                                                if (rank != 8 && rank != 9 && rank != 10 && rank != 5 && rank != 6 && rank != 3 && rank != 4) {
                                                    pc.sendPackets(new S_ServerMessage(728));
                                                    return;
                                                }
                                                else {
                                                    if (!pc.isGm()) {
                                                        Thread.sleep((long)(C_NPCAction._random.nextInt(5) * 1000));
                                                    }
                                                    if (client.getAccount().get_warehouse() > 0) {
                                                        pc.sendPackets(new S_ServerMessage(834));
                                                    }
                                                    else {
                                                        pc.sendPackets(new S_RetrievePledgeList(objid, pc));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("history")) {
                                        int clan_id = pc.getClanid();
                                        if (clan_id != 0) {
                                            pc.sendPackets(new S_PledgeWarehouseHistory(clan_id));
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("get")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        int npcId = npc2.getNpcTemplate().get_npcId();
                                        if (npcId == 70099 || npcId == 70796) {
                                            L1ItemInstance item = pc.getInventory().storeItem(20081, 1L);
                                            String npcName = npc2.getNpcTemplate().get_name();
                                            String itemName = item.getItem().getName();
                                            pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                                            pc.getQuest().set_end(11);
                                            htmlid = "";
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("room")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        int npcId = npc2.getNpcTemplate().get_npcId();
                                        boolean canRent = false;
                                        boolean findRoom = false;
                                        boolean isRented = false;
                                        boolean isHall = false;
                                        int roomNumber = 0;
                                        byte roomCount = 0;
                                        for (int i = 0; i < 16; ++i) {
                                            L1Inn inn = InnTable.getInstance().getTemplate(npcId, i);
                                            if (inn != null) {
                                                if (inn.isHall()) {
                                                    isHall = true;
                                                }
                                                Timestamp dueTime = inn.getDueTime();
                                                Calendar cal = Calendar.getInstance();
                                                long checkDueTime = (cal.getTimeInMillis() - dueTime.getTime()) / 1000L;
                                                if (inn.getLodgerId() == pc.getId() && checkDueTime < 0L) {
                                                    isRented = true;
                                                    break;
                                                }
                                                else if (pc.getInventory().checkItem(40312, 1L)) {
                                                    isRented = true;
                                                    break;
                                                }
                                                else if (!findRoom && !isRented) {
                                                    if (checkDueTime >= 0L) {
                                                        canRent = true;
                                                        findRoom = true;
                                                        roomNumber = inn.getRoomNumber();
                                                    }
                                                    else if (!inn.isHall()) {
                                                        ++roomCount;
                                                    }
                                                }
                                            }
                                        }
                                        if (isRented) {
                                            if (isHall) {
                                                htmlid = "inn15";
                                            }
                                            else {
                                                htmlid = "inn5";
                                            }
                                        }
                                        else if (roomCount >= 12) {
                                            htmlid = "inn6";
                                        }
                                        else if (canRent) {
                                            pc.setInnRoomNumber(roomNumber);
                                            pc.setHall(false);
                                            pc.sendPackets(new S_HowManyKey(npc2, 300, 1, 8, "inn2"));
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("hall") && obj instanceof L1MerchantInstance) {
                                        if (pc.isCrown()) {
                                            L1NpcInstance npc2 = (L1NpcInstance)obj;
                                            int npcId = npc2.getNpcTemplate().get_npcId();
                                            boolean canRent = false;
                                            boolean findRoom = false;
                                            boolean isRented = false;
                                            boolean isHall = false;
                                            int roomNumber = 0;
                                            byte roomCount = 0;
                                            for (int i = 0; i < 16; ++i) {
                                                L1Inn inn = InnTable.getInstance().getTemplate(npcId, i);
                                                if (inn != null) {
                                                    if (inn.isHall()) {
                                                        isHall = true;
                                                    }
                                                    Timestamp dueTime = inn.getDueTime();
                                                    Calendar cal = Calendar.getInstance();
                                                    long checkDueTime = (cal.getTimeInMillis() - dueTime.getTime()) / 1000L;
                                                    if (inn.getLodgerId() == pc.getId() && checkDueTime < 0L) {
                                                        isRented = true;
                                                        break;
                                                    }
                                                    else if (pc.getInventory().checkItem(40312, 1L)) {
                                                        isRented = true;
                                                        break;
                                                    }
                                                    else if (!findRoom && !isRented) {
                                                        if (checkDueTime >= 0L) {
                                                            canRent = true;
                                                            findRoom = true;
                                                            roomNumber = inn.getRoomNumber();
                                                        }
                                                        else if (inn.isHall()) {
                                                            ++roomCount;
                                                        }
                                                    }
                                                }
                                            }
                                            if (isRented) {
                                                if (isHall) {
                                                    htmlid = "inn15";
                                                }
                                                else {
                                                    htmlid = "inn5";
                                                }
                                            }
                                            else if (roomCount >= 4) {
                                                htmlid = "inn16";
                                            }
                                            else if (canRent) {
                                                pc.setInnRoomNumber(roomNumber);
                                                pc.setHall(true);
                                                pc.sendPackets(new S_HowManyKey(npc2, 300, 1, 16, "inn12"));
                                            }
                                        }
                                        else {
                                            htmlid = "inn10";
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("return")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        int npcId = npc2.getNpcTemplate().get_npcId();
                                        int price = 0;
                                        boolean isBreak = false;
                                        int j = 0;
                                        while (j < 16) {
                                            L1Inn inn2 = InnTable.getInstance().getTemplate(npcId, j);
                                            if (inn2 != null && inn2.getLodgerId() == pc.getId()) {
                                                Timestamp dueTime2 = inn2.getDueTime();
                                                if (dueTime2 != null) {
                                                    Calendar cal2 = Calendar.getInstance();
                                                    if ((cal2.getTimeInMillis() - dueTime2.getTime()) / 1000L < 0L) {
                                                        isBreak = true;
                                                        price += 60;
                                                    }
                                                }
                                                Timestamp ts = new Timestamp(System.currentTimeMillis());
                                                inn2.setDueTime(ts);
                                                inn2.setLodgerId(0);
                                                inn2.setKeyId(0);
                                                inn2.setHall(false);
                                                InnTable.getInstance().updateInn(inn2);
                                                break;
                                            }
                                            else {
                                                ++j;
                                            }
                                        }
                                        for (L1ItemInstance item2 : pc.getInventory().getItems()) {
                                            if (item2.getInnNpcId() == npcId) {
                                                price = (int)((long)price + 20L * item2.getCount());
                                                InnKeyTable.DeleteKey(item2);
                                                pc.getInventory().removeItem(item2);
                                                isBreak = true;
                                            }
                                        }
                                        if (isBreak) {
                                            htmldata = new String[] { npc2.getName(), String.valueOf(price) };
                                            htmlid = "inn20";
                                            pc.getInventory().storeItem(40308, (long)price);
                                        }
                                        else {
                                            htmlid = "";
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("enter")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        int npcId = npc2.getNpcTemplate().get_npcId();
                                        for (L1ItemInstance item : pc.getInventory().getItems()) {
                                            if (item.getInnNpcId() == npcId) {
                                                for (int k = 0; k < 16; ++k) {
                                                    L1Inn inn3 = InnTable.getInstance().getTemplate(npcId, k);
                                                    if (inn3.getKeyId() == item.getKeyId()) {
                                                        Timestamp dueTime2 = item.getDueTime();
                                                        if (dueTime2 != null) {
                                                            Calendar cal2 = Calendar.getInstance();
                                                            if ((cal2.getTimeInMillis() - dueTime2.getTime()) / 1000L < 0L) {
                                                                int[] data = (int[])null;
                                                                switch (npcId) {
                                                                    case 70012: {
                                                                        data = new int[] { 32745, 32803, 16384, 32743, 32808, 16896 };
                                                                        break;
                                                                    }
                                                                    case 70019: {
                                                                        data = new int[] { 32743, 32803, 17408, 32744, 32807, 17920 };
                                                                        break;
                                                                    }
                                                                    case 70031: {
                                                                        data = new int[] { 32744, 32803, 18432, 32744, 32807, 18944 };
                                                                        break;
                                                                    }
                                                                    case 70054: {
                                                                        data = new int[] { 32744, 32803, 23552, 32744, 32807, 24064 };
                                                                        break;
                                                                    }
                                                                    case 70065: {
                                                                        data = new int[] { 32744, 32803, 19456, 32744, 32807, 19968 };
                                                                        break;
                                                                    }
                                                                    case 70070: {
                                                                        data = new int[] { 32744, 32803, 20480, 32744, 32807, 20992 };
                                                                        break;
                                                                    }
                                                                    case 70075: {
                                                                        data = new int[] { 32744, 32803, 21504, 32744, 32807, 22016 };
                                                                        break;
                                                                    }
                                                                    case 70084: {
                                                                        data = new int[] { 32744, 32803, 22528, 32744, 32807, 23040 };
                                                                        break;
                                                                    }
                                                                    case 70096: {
                                                                        data = new int[] { 32744, 32803, 24576, 32744, 32807, 25088 };
                                                                        break;
                                                                    }
                                                                }
                                                                if (!item.checkRoomOrHall()) {
                                                                    pc.set_showId(item.getKeyId());
                                                                    L1Teleport.teleport(pc, data[0], data[1], (short)data[2], 6, false);
                                                                    break;
                                                                }
                                                                else {
                                                                    pc.set_showId(item.getKeyId());
                                                                    L1Teleport.teleport(pc, data[3], data[4], (short)data[5], 6, false);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("openigate")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        this.openCloseGate(pc, npc2.getNpcTemplate().get_npcId(), true);
                                        htmlid = "";
                                    }
                                    else if (cmd.equalsIgnoreCase("closeigate")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        this.openCloseGate(pc, npc2.getNpcTemplate().get_npcId(), false);
                                        htmlid = "";
                                    }
                                    else if (cmd.equalsIgnoreCase("askwartime")) {
                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                        if (npc2.getNpcTemplate().get_npcId() == 60514) {
                                            htmldata = this.makeWarTimeStrings(1);
                                            htmlid = "ktguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 60560) {
                                            htmldata = this.makeWarTimeStrings(2);
                                            htmlid = "orcguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 60552) {
                                            htmldata = this.makeWarTimeStrings(3);
                                            htmlid = "wdguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 60524 || npc2.getNpcTemplate().get_npcId() == 60525 || npc2.getNpcTemplate().get_npcId() == 60529) {
                                            htmldata = this.makeWarTimeStrings(4);
                                            htmlid = "grguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 70857) {
                                            htmldata = this.makeWarTimeStrings(5);
                                            htmlid = "heguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 60530 || npc2.getNpcTemplate().get_npcId() == 60531) {
                                            htmldata = this.makeWarTimeStrings(6);
                                            htmlid = "dcguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 60533 || npc2.getNpcTemplate().get_npcId() == 60534) {
                                            htmldata = this.makeWarTimeStrings(7);
                                            htmlid = "adguard7";
                                        }
                                        else if (npc2.getNpcTemplate().get_npcId() == 81156) {
                                            htmldata = this.makeWarTimeStrings(8);
                                            htmlid = "dfguard3";
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("inex")) {
                                        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                        if (clan != null) {
                                            int castle_id = clan.getCastleId();
                                            if (castle_id != 0) {
                                                L1Castle l1castle = CastleReading.get().getCastleTable(castle_id);
                                                pc.sendPackets(new S_ServerMessage(309, l1castle.getName(), String.valueOf(l1castle.getPublicMoney())));
                                                htmlid = "";
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("tax")) {
                                        pc.sendPackets(new S_TaxRate(pc.getId()));
                                    }
                                    else if (cmd.equalsIgnoreCase("withdrawal")) {
                                        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                        if (clan != null) {
                                            int castle_id = clan.getCastleId();
                                            if (castle_id != 0) {
                                                L1Castle l1castle = CastleReading.get().getCastleTable(castle_id);
                                                pc.sendPackets(new S_Drawal(pc.getId(), l1castle.getPublicMoney()));
                                            }
                                        }
                                    }
                                    else if (cmd.equalsIgnoreCase("cdeposit")) {
                                        pc.sendPackets(new S_Deposit(pc.getId()));
                                    }
                                    else if (!cmd.equalsIgnoreCase("employ") && !cmd.equalsIgnoreCase("arrange")) {
                                        if (cmd.equalsIgnoreCase("castlegate")) {
                                            this.repairGate(pc);
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("encw")) {
                                            if (pc.getWeapon() == null) {
                                                pc.sendPackets(new S_ServerMessage(79));
                                            }
                                            else {
                                                for (L1ItemInstance item3 : pc.getInventory().getItems()) {
                                                    if (pc.getWeapon().equals(item3)) {
                                                        L1SkillUse l1skilluse = new L1SkillUse();
                                                        l1skilluse.handleCommands(pc, 12, item3.getId(), 0, 0, 0, 2);
                                                        break;
                                                    }
                                                }
                                            }
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("enca")) {
                                            L1ItemInstance item3 = pc.getInventory().getItemEquipped(2, 2);
                                            if (item3 != null) {
                                                L1SkillUse l1skilluse2 = new L1SkillUse();
                                                l1skilluse2.handleCommands(pc, 21, item3.getId(), 0, 0, 0, 2);
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(79));
                                            }
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("depositnpc")) {
                                            Object[] petList = pc.getPetList().values().toArray();
                                            Object[] array;
                                            for (int length = (array = petList).length, n = 0; n < length; ++n) {
                                                Object petObject = array[n];
                                                if (petObject instanceof L1PetInstance) {
                                                    L1PetInstance pet2 = (L1PetInstance)petObject;
                                                    pet2.collect(true);
                                                    pc.removePet(pet2);
                                                    pet2.deleteMe();
                                                }
                                            }
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("withdrawnpc")) {
                                            pc.sendPackets(new S_PetList(objid, pc));
                                        }
                                        else if (cmd.equalsIgnoreCase("open") || cmd.equalsIgnoreCase("close")) {
                                            L1NpcInstance npc2 = (L1NpcInstance)obj;
                                            this.openCloseDoor(pc, npc2, cmd);
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("expel")) {
                                            L1NpcInstance npc2 = (L1NpcInstance)obj;
                                            this.expelOtherClan(pc, npc2.getNpcTemplate().get_npcId());
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("pay")) {
                                            L1NpcInstance npc2 = (L1NpcInstance)obj;
                                            htmldata = this.makeHouseTaxStrings(pc, npc2);
                                            htmlid = "agpay";
                                        }
                                        else if (cmd.equalsIgnoreCase("payfee")) {
                                            L1NpcInstance npc2 = (L1NpcInstance)obj;
                                            this.payFee(pc, npc2);
                                            htmlid = "";
                                        }
                                        else if (cmd.equalsIgnoreCase("name")) {
                                            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                            if (clan != null) {
                                                int houseId = clan.getHouseId();
                                                if (houseId != 0) {
                                                    L1House house = HouseReading.get().getHouseTable(houseId);
                                                    int keeperId = house.getKeeperId();
                                                    L1NpcInstance npc3 = (L1NpcInstance)obj;
                                                    if (npc3.getNpcTemplate().get_npcId() == keeperId) {
                                                        pc.setTempID(houseId);
                                                        pc.sendPackets(new S_Message_YN(512));
                                                    }
                                                }
                                            }
                                            htmlid = "";
                                        }
                                        else if (!cmd.equalsIgnoreCase("rem")) {
                                            if (cmd.equalsIgnoreCase("tel0") || cmd.equalsIgnoreCase("tel1") || cmd.equalsIgnoreCase("tel2") || cmd.equalsIgnoreCase("tel3")) {
                                                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                                if (clan != null) {
                                                    int houseId = clan.getHouseId();
                                                    if (houseId != 0) {
                                                        L1House house = HouseReading.get().getHouseTable(houseId);
                                                        int keeperId = house.getKeeperId();
                                                        L1NpcInstance npc3 = (L1NpcInstance)obj;
                                                        if (npc3.getNpcTemplate().get_npcId() == keeperId) {
                                                            int[] loc = new int[3];
                                                            if (cmd.equalsIgnoreCase("tel0")) {
                                                                loc = L1HouseLocation.getHouseTeleportLoc(houseId, 0);
                                                            }
                                                            else if (cmd.equalsIgnoreCase("tel1")) {
                                                                loc = L1HouseLocation.getHouseTeleportLoc(houseId, 1);
                                                            }
                                                            else if (cmd.equalsIgnoreCase("tel2")) {
                                                                loc = L1HouseLocation.getHouseTeleportLoc(houseId, 2);
                                                            }
                                                            else if (cmd.equalsIgnoreCase("tel3")) {
                                                                loc = L1HouseLocation.getHouseTeleportLoc(houseId, 3);
                                                            }
                                                            L1Teleport.teleport(pc, loc[0], loc[1], (short)loc[2], 5, true);
                                                        }
                                                    }
                                                }
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("upgrade")) {
                                                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                                if (clan != null) {
                                                    int houseId = clan.getHouseId();
                                                    if (houseId != 0) {
                                                        L1House house = HouseReading.get().getHouseTable(houseId);
                                                        int keeperId = house.getKeeperId();
                                                        if (keeperId >= 50626 && keeperId <= 50631) {
                                                            pc.sendPackets(new S_ServerMessage("此小屋無法創建地下盟屋"));
                                                            return;
                                                        }
                                                        else {
                                                            L1NpcInstance npc3 = (L1NpcInstance)obj;
                                                            if (npc3.getNpcTemplate().get_npcId() == keeperId) {
                                                                if (pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                                                                    if (house.isPurchaseBasement()) {
                                                                        pc.sendPackets(new S_ServerMessage(1135));
                                                                    }
                                                                    else if (pc.getInventory().consumeItem(40308, 5000000L)) {
                                                                        house.setPurchaseBasement(true);
                                                                        HouseReading.get().updateHouse(house);
                                                                        pc.sendPackets(new S_ServerMessage(1099));
                                                                    }
                                                                    else {
                                                                        pc.sendPackets(new S_ServerMessage(189));
                                                                    }
                                                                }
                                                                else {
                                                                    pc.sendPackets(new S_ServerMessage(518));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("hall") && obj instanceof L1HousekeeperInstance) {
                                                L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                                                if (clan != null) {
                                                    int houseId = clan.getHouseId();
                                                    if (houseId != 0) {
                                                        L1House house = HouseReading.get().getHouseTable(houseId);
                                                        int keeperId = house.getKeeperId();
                                                        L1NpcInstance npc3 = (L1NpcInstance)obj;
                                                        if (npc3.getNpcTemplate().get_npcId() == keeperId) {
                                                            if (house.isPurchaseBasement()) {
                                                                int[] loc = new int[3];
                                                                loc = L1HouseLocation.getBasementLoc(houseId);
                                                                L1Teleport.teleport(pc, loc[0], loc[1], (short)loc[2], 5, true);
                                                            }
                                                            else {
                                                                pc.sendPackets(new S_ServerMessage(1098));
                                                            }
                                                        }
                                                    }
                                                }
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("fire")) {
                                                if (pc.isElf()) {
                                                    if (pc.getElfAttr() != 0) {
                                                        return;
                                                    }
                                                    else {
                                                        pc.setElfAttr(2);
                                                        pc.save();
                                                        pc.sendPackets(new S_PacketBox(15, 1));
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("water")) {
                                                if (pc.isElf()) {
                                                    if (pc.getElfAttr() != 0) {
                                                        return;
                                                    }
                                                    else {
                                                        pc.setElfAttr(4);
                                                        pc.save();
                                                        pc.sendPackets(new S_PacketBox(15, 2));
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("air")) {
                                                if (pc.isElf()) {
                                                    if (pc.getElfAttr() != 0) {
                                                        return;
                                                    }
                                                    else {
                                                        pc.setElfAttr(8);
                                                        pc.save();
                                                        pc.sendPackets(new S_PacketBox(15, 3));
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("earth")) {
                                                if (pc.isElf()) {
                                                    if (pc.getElfAttr() != 0) {
                                                        return;
                                                    }
                                                    else {
                                                        pc.setElfAttr(1);
                                                        pc.save();
                                                        pc.sendPackets(new S_PacketBox(15, 4));
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("init")) {
                                                if (pc.isElf()) {
                                                    if (pc.getElfAttr() == 0) {
                                                        return;
                                                    }
                                                    else {
                                                        for (int cnt = 129; cnt <= 176; ++cnt) {
                                                            L1Skills l1skills1 = SkillsTable.get().getTemplate(cnt);
                                                            int skill_attr = l1skills1.getAttr();
                                                            if (skill_attr != 0) {
                                                                CharSkillReading.get().spellLost(pc.getId(), l1skills1.getSkillId());
                                                            }
                                                        }
                                                        if (pc.hasSkillEffect(147)) {
                                                            pc.removeSkillEffect(147);
                                                        }
                                                        pc.sendPackets(new S_DelSkill(pc, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 248, 252, 252, 255, 0, 0, 0, 0, 0, 0));
                                                        pc.setElfAttr(0);
                                                        pc.save();
                                                        pc.sendPackets(new S_ServerMessage(678));
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("exp")) {
                                                if (pc.getExpRes() == 1) {
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
                                                    pc.sendPackets(new S_Message_YN(738, String.valueOf(cost)));
                                                }
                                                else {
                                                    pc.sendPackets(new S_ServerMessage(739));
                                                    htmlid = "";
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("ent") || cmd.toLowerCase().startsWith("ent,")) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                int npcId2 = npc2.getNpcId();
                                                if (npcId2 == 80085) {
                                                    htmlid = this.enterHauntedHouse(pc);
                                                }
                                                else if (npcId2 == 80088) {
                                                    String actionCmd = cmd;
                                                    if (cmd.equalsIgnoreCase("ent")) {
                                                        String s3 = this.readS();
                                                        if (s3 != null && !s3.isEmpty()) {
                                                            actionCmd = cmd + "," + s3;
                                                        }
                                                    }
                                                    if (npc2.ACTION != null) {
                                                        npc2.ACTION.action(pc, npc2, actionCmd, 0L);
                                                        return;
                                                    }
                                                    try {
                                                        String[] temp = actionCmd.split(",");
                                                        int amuletId = 0;
                                                        if (temp.length >= 3) {
                                                            amuletId = Integer.parseInt(temp[2]);
                                                        } else if (temp.length >= 2) {
                                                            amuletId = Integer.parseInt(temp[1]);
                                                        }
                                                        if (amuletId > 0 && PetReading.get().getTemplate(amuletId) == null) {
                                                            L1ItemInstance item = pc.get_otherList().get_sitemList().get(Integer.valueOf(amuletId));
                                                            if (item != null) {
                                                                amuletId = item.getItemId();
                                                            } else {
                                                                Integer mappedId = pc.get_otherList().get_sitemList2().get(Integer.valueOf(amuletId));
                                                                if (mappedId != null) {
                                                                    amuletId = mappedId.intValue();
                                                                }
                                                            }
                                                        }
                                                        if (amuletId > 0) {
                                                            htmlid = this.enterPetMatch(pc, amuletId);
                                                        } else {
                                                            htmlid = null; // 如果只收到 "ent"，不中斷現有對話框狀態
                                                        }
                                                    } catch (Exception ex) {
                                                        pc.sendPackets(new S_ServerMessage(1182));
                                                        htmlid = "";
                                                    }
                                                }
                                                else if (npcId2 == 50038 || npcId2 == 50042 || npcId2 == 50029 || npcId2 == 50019 || npcId2 == 50062) {
                                                    htmlid = this.watchUb(pc, npcId2);
                                                }
                                                else if (npcId2 == 80086) {
                                                    if (pc.getLevel() <= 29 || pc.getLevel() >= 52) {
                                                        pc.sendPackets(new S_ServerMessage(1273, "30", "51"));
                                                    }
                                                }
                                                else if (npcId2 == 80087) {
                                                    if (pc.getLevel() <= 51) {
                                                        pc.sendPackets(new S_ServerMessage(1273, "52", "99"));
                                                    }
                                                }
                                                else {
                                                    htmlid = this.enterUb(pc, npcId2);
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("par")) {
                                                htmlid = this.enterUb(pc, ((L1NpcInstance)obj).getNpcId());
                                            }
                                            else if (cmd.equalsIgnoreCase("info")) {
                                                htmlid = "colos2";
                                            }
                                            else if (cmd.equalsIgnoreCase("sco")) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                this.UbRank(pc, npc2);
                                            }
                                            else if (cmd.equalsIgnoreCase("haste")) {
                                                L1NpcInstance l1npcinstance = (L1NpcInstance)obj;
                                                int npcid2 = l1npcinstance.getNpcTemplate().get_npcId();
                                                if (npcid2 == 70514) {
                                                    pc.sendPackets(new S_ServerMessage(183));
                                                    pc.sendPackets(new S_SkillHaste(pc.getId(), 1, 1600));
                                                    pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
                                                    pc.sendPacketsX8(new S_SkillSound(pc.getId(), 755));
                                                    pc.setMoveSpeed(1);
                                                    pc.setSkillEffect(1001, 1600000);
                                                    htmlid = "";
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("skeleton nbmorph")) {
                                                this.poly(client, 2374);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("lycanthrope nbmorph")) {
                                                this.poly(client, 3874);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("shelob nbmorph")) {
                                                this.poly(client, 95);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("ghoul nbmorph")) {
                                                this.poly(client, 3873);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("ghast nbmorph")) {
                                                this.poly(client, 3875);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("atuba orc nbmorph")) {
                                                this.poly(client, 3868);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("skeleton axeman nbmorph")) {
                                                this.poly(client, 2376);
                                                htmlid = "";
                                            }
                                            else if (cmd.equalsIgnoreCase("troll nbmorph")) {
                                                this.poly(client, 3878);
                                                htmlid = "";
                                            }
                                            else if (npctemp.get_npcId() == 95199) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().checkItem(92122, 1L)) {
                                                        htmlid = "icqwand4";
                                                    }
                                                    else {
                                                        CreateNewItem.createNewItem_NPC(pc, npctemp.get_name(), 92122, 1);
                                                        htmlid = "icqwand2";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().checkItem(92123, 1L)) {
                                                        htmlid = "icqwand3";
                                                    }
                                                    else {
                                                        CreateNewItem.createNewItem_NPC(pc, npctemp.get_name(), 92123, 80);
                                                        htmlid = "icqwand2";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71038) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                    L1ItemInstance item4 = pc.getInventory().storeItem(41060, 1L);
                                                    String npcName2 = npc2.getNpcTemplate().get_name();
                                                    String itemName2 = item4.getItem().getNameId();
                                                    pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                    htmlid = "orcfnoname9";
                                                }
                                                else if (cmd.equalsIgnoreCase("Z") && pc.getInventory().consumeItem(41060, 1L)) {
                                                    htmlid = "orcfnoname11";
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 93229 && cmd.equalsIgnoreCase("long_pay")) {
                                                giveitemnew(pc);
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 98007 && cmd.equalsIgnoreCase("long_pay")) {
                                                if (pc.hasSkillEffect(90007)) {
                                                    pc.sendPackets(new S_SystemMessage("系統正再讀取贊助金額中.請稍候"));
                                                    return;
                                                }
                                                else if (!pc.hasSkillEffect(90007)) {
                                                    pc.setSkillEffect(90007, 10000);
                                                    checkSponsor3(pc);
                                                }
                                                else {
                                                    pc.sendPackets(new S_SystemMessage("系統正在作業中，請稍後。"));
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 93053 && cmd.equalsIgnoreCase("long_pay")) {
                                                if (pc.hasSkillEffect(60007)) {
                                                    pc.sendPackets(new S_SystemMessage("系統正再讀取贊助金額中.請稍候"));
                                                    return;
                                                }
                                                else if (pc.hasSkillEffect(90007)) {
                                                    pc.sendPackets(new S_SystemMessage("系統正在作業中，請稍後。"));
                                                    return;
                                                }
                                                else {
                                                    pc.setSkillEffect(90007, 10000);
                                                    try {
                                                        pc.sendPackets(new S_SystemMessage("系統正再讀取贊助金額中.請稍候"));
                                                        Thread.sleep(3000L);
                                                        pc.setSkillEffect(60007, 3000);
                                                    }
                                                    catch (InterruptedException ex2) {}
                                                    checkSponsor1(pc);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71039) {
                                                if (cmd.equalsIgnoreCase("teleportURL")) {
                                                    htmlid = "orcfbuwoo2";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71040) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                    L1ItemInstance item4 = pc.getInventory().storeItem(41065, 1L);
                                                    String npcName2 = npc2.getNpcTemplate().get_name();
                                                    String itemName2 = item4.getItem().getNameId();
                                                    pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                    htmlid = "orcfnoa4";
                                                }
                                                else if (cmd.equalsIgnoreCase("Z") && pc.getInventory().consumeItem(41065, 1L)) {
                                                    htmlid = "orcfnoa7";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71041) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                    L1ItemInstance item4 = pc.getInventory().storeItem(41064, 1L);
                                                    String npcName2 = npc2.getNpcTemplate().get_name();
                                                    String itemName2 = item4.getItem().getNameId();
                                                    pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                    htmlid = "orcfhuwoomo4";
                                                }
                                                else if (cmd.equalsIgnoreCase("Z") && pc.getInventory().consumeItem(41064, 1L)) {
                                                    htmlid = "orcfhuwoomo6";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71042) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                    L1ItemInstance item4 = pc.getInventory().storeItem(41062, 1L);
                                                    String npcName2 = npc2.getNpcTemplate().get_name();
                                                    String itemName2 = item4.getItem().getNameId();
                                                    pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                    htmlid = "orcfbakumo4";
                                                }
                                                else if (cmd.equalsIgnoreCase("Z") && pc.getInventory().consumeItem(41062, 1L)) {
                                                    htmlid = "orcfbakumo6";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71043) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                    L1ItemInstance item4 = pc.getInventory().storeItem(41063, 1L);
                                                    String npcName2 = npc2.getNpcTemplate().get_name();
                                                    String itemName2 = item4.getItem().getNameId();
                                                    pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                    htmlid = "orcfbuka4";
                                                }
                                                else if (cmd.equalsIgnoreCase("Z") && pc.getInventory().consumeItem(41063, 1L)) {
                                                    htmlid = "orcfbuka6";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71044) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                    L1ItemInstance item4 = pc.getInventory().storeItem(41061, 1L);
                                                    String npcName2 = npc2.getNpcTemplate().get_name();
                                                    String itemName2 = item4.getItem().getNameId();
                                                    pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                    htmlid = "orcfkame4";
                                                }
                                                else if (cmd.equalsIgnoreCase("Z") && pc.getInventory().consumeItem(41061, 1L)) {
                                                    htmlid = "orcfkame6";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71078) {
                                                if (cmd.equalsIgnoreCase("teleportURL")) {
                                                    htmlid = "usender2";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71080) {
                                                if (cmd.equalsIgnoreCase("teleportURL")) {
                                                    htmlid = "amisoo2";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80048) {
                                                if (cmd.equalsIgnoreCase("2")) {
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80049) {
                                                if (cmd.equalsIgnoreCase("1") && pc.getKarma() <= -10000000) {
                                                    pc.setKarma(1000000);
                                                    pc.sendPackets(new S_ServerMessage(1078));
                                                    htmlid = "betray13";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80050) {
                                                if (cmd.equalsIgnoreCase("1")) {
                                                    htmlid = "meet105";
                                                }
                                                else if (cmd.equalsIgnoreCase("2")) {
                                                    if (pc.getInventory().checkItem(40718)) {
                                                        htmlid = "meet106";
                                                    }
                                                    else {
                                                        htmlid = "meet110";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().consumeItem(40718, 1L)) {
                                                        pc.addKarma((int)(-100.0 * ConfigRate.RATE_KARMA));
                                                        pc.sendPackets(new S_ServerMessage(1079));
                                                        htmlid = "meet107";
                                                    }
                                                    else {
                                                        htmlid = "meet104";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().consumeItem(40718, 10L)) {
                                                        pc.addKarma((int)(-1000.0 * ConfigRate.RATE_KARMA));
                                                        pc.sendPackets(new S_ServerMessage(1079));
                                                        htmlid = "meet108";
                                                    }
                                                    else {
                                                        htmlid = "meet104";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("c")) {
                                                    if (pc.getInventory().consumeItem(40718, 100L)) {
                                                        pc.addKarma((int)(-10000.0 * ConfigRate.RATE_KARMA));
                                                        pc.sendPackets(new S_ServerMessage(1079));
                                                        htmlid = "meet109";
                                                    }
                                                    else {
                                                        htmlid = "meet104";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("d")) {
                                                    if (pc.getInventory().checkItem(40615) || pc.getInventory().checkItem(40616)) {
                                                        htmlid = "";
                                                    }
                                                    else {
                                                        L1Teleport.teleport(pc, 32683, 32895, (short)608, 5, true);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80052) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.hasSkillEffect(4003)) {
                                                        pc.removeSkillEffect(4003);
                                                    }
                                                    if (pc.hasSkillEffect(4007)) {
                                                        pc.removeSkillEffect(4007);
                                                    }
                                                    if (pc.hasSkillEffect(4006)) {
                                                        pc.sendPackets(new S_ServerMessage(79));
                                                    }
                                                    else {
                                                        pc.setSkillEffect(4005, 1020000);
                                                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7246));
                                                        pc.sendPackets(new S_ServerMessage(1127));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80053) {
                                                int karmaLevel = pc.getKarmaLevel();
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    int aliceMaterialId = 0;
                                                    int[] aliceMaterialIdList = { 40991, 196, 197, 198, 199, 200, 201, 202, 203 };
                                                    int[] array2;
                                                    int length2 = (array2 = aliceMaterialIdList).length;
                                                    int n2 = 0;
                                                    while (n2 < length2) {
                                                        int id = array2[n2];
                                                        if (pc.getInventory().checkItem(id)) {
                                                            aliceMaterialId = id;
                                                            break;
                                                        }
                                                        else {
                                                            ++n2;
                                                        }
                                                    }
                                                    if (aliceMaterialId == 0) {
                                                        htmlid = "alice_no";
                                                    }
                                                    else if (aliceMaterialId == 40991) {
                                                        if (karmaLevel <= -1) {
                                                            materials = new int[] { 40995, 40718, 40991 };
                                                            counts = new int[] { 100, 100, 1 };
                                                            createitem = new int[] { 196 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_1";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "aliceyet";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 196) {
                                                        if (karmaLevel <= -2) {
                                                            materials = new int[] { 40997, 40718, 196 };
                                                            counts = new int[] { 100, 100, 1 };
                                                            createitem = new int[] { 197 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_2";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_1";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 197) {
                                                        if (karmaLevel <= -3) {
                                                            materials = new int[] { 40990, 40718, 197 };
                                                            counts = new int[] { 100, 100, 1 };
                                                            createitem = new int[] { 198 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_3";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_2";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 198) {
                                                        if (karmaLevel <= -4) {
                                                            materials = new int[] { 40994, 40718, 198 };
                                                            counts = new int[] { 50, 100, 1 };
                                                            createitem = new int[] { 199 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_4";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_3";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 199) {
                                                        if (karmaLevel <= -5) {
                                                            materials = new int[] { 40993, 40718, 199 };
                                                            counts = new int[] { 50, 100, 1 };
                                                            createitem = new int[] { 200 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_5";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_4";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 200) {
                                                        if (karmaLevel <= -6) {
                                                            materials = new int[] { 40998, 40718, 200 };
                                                            counts = new int[] { 50, 100, 1 };
                                                            createitem = new int[] { 201 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_6";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_5";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 201) {
                                                        if (karmaLevel <= -7) {
                                                            materials = new int[] { 40996, 40718, 201 };
                                                            counts = new int[] { 10, 100, 1 };
                                                            createitem = new int[] { 202 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_7";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_6";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 202) {
                                                        if (karmaLevel <= -8) {
                                                            materials = new int[] { 40992, 40718, 202 };
                                                            counts = new int[] { 10, 100, 1 };
                                                            createitem = new int[] { 203 };
                                                            createcount = new int[] { 1 };
                                                            success_htmlid = "alice_8";
                                                            failure_htmlid = "alice_no";
                                                        }
                                                        else {
                                                            htmlid = "alice_7";
                                                        }
                                                    }
                                                    else if (aliceMaterialId == 203) {
                                                        htmlid = "alice_8";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80055) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                htmlid = this.getYaheeAmulet(pc, npc2, cmd);
                                            }
                                            else if (npctemp.get_npcId() == 80056) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                if (pc.getKarma() <= -10000000) {
                                                    this.getBloodCrystalByKarma(pc, npc2, cmd);
                                                }
                                                htmlid = "";
                                            }
                                            else if (npctemp.get_npcId() == 80063) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().checkItem(40921)) {
                                                        L1Teleport.teleport(pc, 32674, 32832, (short)603, 2, true);
                                                    }
                                                    else {
                                                        htmlid = "gpass02";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80064) {
                                                if (cmd.equalsIgnoreCase("1")) {
                                                    htmlid = "meet005";
                                                }
                                                else if (cmd.equalsIgnoreCase("2")) {
                                                    if (pc.getInventory().checkItem(40678)) {
                                                        htmlid = "meet006";
                                                    }
                                                    else {
                                                        htmlid = "meet010";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().consumeItem(40678, 1L)) {
                                                        pc.addKarma((int)(100.0 * ConfigRate.RATE_KARMA));
                                                        pc.sendPackets(new S_ServerMessage(1078));
                                                        htmlid = "meet007";
                                                    }
                                                    else {
                                                        htmlid = "meet004";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().consumeItem(40678, 10L)) {
                                                        pc.addKarma((int)(1000.0 * ConfigRate.RATE_KARMA));
                                                        pc.sendPackets(new S_ServerMessage(1078));
                                                        htmlid = "meet008";
                                                    }
                                                    else {
                                                        htmlid = "meet004";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("c")) {
                                                    if (pc.getInventory().consumeItem(40678, 100L)) {
                                                        pc.addKarma((int)(10000.0 * ConfigRate.RATE_KARMA));
                                                        pc.sendPackets(new S_ServerMessage(1078));
                                                        htmlid = "meet009";
                                                    }
                                                    else {
                                                        htmlid = "meet004";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("d")) {
                                                    if (pc.getInventory().checkItem(40909) || pc.getInventory().checkItem(40910) || pc.getInventory().checkItem(40911) || pc.getInventory().checkItem(40912) || pc.getInventory().checkItem(40913) || pc.getInventory().checkItem(40914) || pc.getInventory().checkItem(40915) || pc.getInventory().checkItem(40916) || pc.getInventory().checkItem(40917) || pc.getInventory().checkItem(40918) || pc.getInventory().checkItem(40919) || pc.getInventory().checkItem(40920) || pc.getInventory().checkItem(40921)) {
                                                        htmlid = "";
                                                    }
                                                    else {
                                                        L1Teleport.teleport(pc, 32674, 32832, (short)602, 2, true);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80066) {
                                                if (cmd.equalsIgnoreCase("1") && pc.getKarma() >= 10000000) {
                                                    pc.setKarma(-1000000);
                                                    pc.sendPackets(new S_ServerMessage(1079));
                                                    htmlid = "betray03";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80071) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                htmlid = this.getBarlogEarring(pc, npc2, cmd);
                                            }
                                            else if (npctemp.get_npcId() == 80073) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.hasSkillEffect(34003)) {
                                                        pc.removeSkillEffect(34003);
                                                    }
                                                    if (pc.hasSkillEffect(34007)) {
                                                        pc.removeSkillEffect(34007);
                                                    }
                                                    if (pc.hasSkillEffect(34005)) {
                                                        pc.sendPackets(new S_ServerMessage(79));
                                                    }
                                                    else {
                                                        pc.setSkillEffect(4006, 1020000);
                                                        pc.sendPackets(new S_SkillIconAura(221, 1020, 1));
                                                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7247));
                                                        pc.sendPackets(new S_ServerMessage(1127));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80072) {
                                                int karmaLevel = pc.getKarmaLevel();
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    htmlid = "lsmitha";
                                                }
                                                else if (cmd.equalsIgnoreCase("1")) {
                                                    htmlid = "lsmithb";
                                                }
                                                else if (cmd.equalsIgnoreCase("2")) {
                                                    htmlid = "lsmithc";
                                                }
                                                else if (cmd.equalsIgnoreCase("3")) {
                                                    htmlid = "lsmithd";
                                                }
                                                else if (cmd.equalsIgnoreCase("4")) {
                                                    htmlid = "lsmithe";
                                                }
                                                else if (cmd.equalsIgnoreCase("5")) {
                                                    htmlid = "lsmithf";
                                                }
                                                else if (cmd.equalsIgnoreCase("6")) {
                                                    htmlid = "";
                                                }
                                                else if (cmd.equalsIgnoreCase("7")) {
                                                    htmlid = "lsmithg";
                                                }
                                                else if (cmd.equalsIgnoreCase("8")) {
                                                    htmlid = "lsmithh";
                                                }
                                                else if (cmd.equalsIgnoreCase("a") && karmaLevel >= 1) {
                                                    materials = new int[] { 20158, 40669, 40678 };
                                                    counts = new int[] { 1, 50, 100 };
                                                    createitem = new int[] { 20083 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithaa";
                                                }
                                                else if (cmd.equalsIgnoreCase("b") && karmaLevel >= 2) {
                                                    materials = new int[] { 20144, 40672, 40678 };
                                                    counts = new int[] { 1, 50, 100 };
                                                    createitem = new int[] { 20131 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithbb";
                                                }
                                                else if (cmd.equalsIgnoreCase("c") && karmaLevel >= 3) {
                                                    materials = new int[] { 20075, 40671, 40678 };
                                                    counts = new int[] { 1, 50, 100 };
                                                    createitem = new int[] { 20069 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithcc";
                                                }
                                                else if (cmd.equalsIgnoreCase("d") && karmaLevel >= 4) {
                                                    materials = new int[] { 20183, 40674, 40678 };
                                                    counts = new int[] { 1, 20, 100 };
                                                    createitem = new int[] { 20179 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithdd";
                                                }
                                                else if (cmd.equalsIgnoreCase("e") && karmaLevel >= 5) {
                                                    materials = new int[] { 20190, 40674, 40678 };
                                                    counts = new int[] { 1, 40, 100 };
                                                    createitem = new int[] { 20209 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithee";
                                                }
                                                else if (cmd.equalsIgnoreCase("f") && karmaLevel >= 6) {
                                                    materials = new int[] { 20078, 40674, 40678 };
                                                    counts = new int[] { 1, 5, 100 };
                                                    createitem = new int[] { 20290 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithff";
                                                }
                                                else if (cmd.equalsIgnoreCase("g") && karmaLevel >= 7) {
                                                    materials = new int[] { 20078, 40670, 40678 };
                                                    counts = new int[] { 1, 1, 100 };
                                                    createitem = new int[] { 20261 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithgg";
                                                }
                                                else if (cmd.equalsIgnoreCase("h") && karmaLevel >= 8) {
                                                    materials = new int[] { 40719, 40673, 40678 };
                                                    counts = new int[] { 1, 1, 100 };
                                                    createitem = new int[] { 20031 };
                                                    createcount = new int[] { 1 };
                                                    success_htmlid = "";
                                                    failure_htmlid = "lsmithhh";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80074) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                if (pc.getKarma() >= 10000000) {
                                                    this.getSoulCrystalByKarma(pc, npc2, cmd);
                                                }
                                                htmlid = "";
                                            }
                                            else if (npctemp.get_npcId() == 80057) {
                                                htmlid = this.karmaLevelToHtmlId(pc.getKarmaLevel());
                                                htmldata = new String[] { String.valueOf(pc.getKarmaPercent()) };
                                            }
                                            else if (npctemp.get_npcId() == 80059 || npctemp.get_npcId() == 80060 || npctemp.get_npcId() == 80061 || npctemp.get_npcId() == 80062) {
                                                htmlid = this.talkToDimensionDoor(pc, (L1NpcInstance)obj, cmd);
                                            }
                                            else if (cmd.equalsIgnoreCase("pandora6") || cmd.equalsIgnoreCase("cold6") || cmd.equalsIgnoreCase("balsim3") || cmd.equalsIgnoreCase("mellin3") || cmd.equalsIgnoreCase("glen3")) {
                                                htmlid = cmd;
                                                int npcid = npctemp.get_npcId();
                                                int taxRatesCastle = L1CastleLocation.getCastleTaxRateByNpcId(npcid);
                                                htmldata = new String[] { String.valueOf(taxRatesCastle) };
                                            }
                                            else if (npctemp.get_npcId() == 70512) {
                                                if (cmd.equalsIgnoreCase("0") || cmd.equalsIgnoreCase("fullheal")) {
                                                    int hp = C_NPCAction._random.nextInt(21) + 70;
                                                    pc.setCurrentHp(pc.getCurrentHp() + hp);
                                                    pc.sendPackets(new S_ServerMessage(77));
                                                    pc.sendPackets(new S_SkillSound(pc.getId(), 830));
                                                    pc.sendPackets(new S_HPUpdate(pc));
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71037) {
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    pc.setCurrentHp(pc.getMaxHp());
                                                    pc.setCurrentMp(pc.getMaxMp());
                                                    pc.sendPackets(new S_ServerMessage(77));
                                                    pc.sendPackets(new S_SkillSound(pc.getId(), 830));
                                                    pc.sendPackets(new S_HPUpdate(pc));
                                                    pc.sendPackets(new S_MPUpdate(pc));
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71030) {
                                                if (cmd.equalsIgnoreCase("fullheal")) {
                                                    if (pc.getInventory().checkItem(40308, 5L)) {
                                                        pc.getInventory().consumeItem(40308, 5L);
                                                        pc.setCurrentHp(pc.getMaxHp());
                                                        pc.setCurrentMp(pc.getMaxMp());
                                                        pc.sendPackets(new S_ServerMessage(77));
                                                        pc.sendPackets(new S_SkillSound(pc.getId(), 830));
                                                        pc.sendPackets(new S_HPUpdate(pc));
                                                        pc.sendPackets(new S_MPUpdate(pc));
                                                        if (pc.isInParty()) {
                                                            pc.getParty().updateMiniHP(pc);
                                                        }
                                                    }
                                                    else {
                                                        pc.sendPackets(new S_ServerMessage(337, "$4"));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71002) {
                                                if (cmd.equalsIgnoreCase("0") && pc.getLevel() <= 13) {
                                                    L1SkillUse skillUse = new L1SkillUse();
                                                    skillUse.handleCommands(pc, 44, pc.getId(), pc.getX(), pc.getY(), 0, 3, (L1NpcInstance)obj);
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71025) {
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    int[] item_ids = { 41225 };
                                                    int[] item_amounts = { 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                    }
                                                    htmlid = "jpe0083";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71055) {
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    int[] item_ids = { 40701 };
                                                    int[] item_amounts = { 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                    }
                                                    pc.getQuest().set_step(23, 1);
                                                    htmlid = "lukein8";
                                                }
                                                else if (cmd.equalsIgnoreCase("2")) {
                                                    htmlid = "lukein12";
                                                    pc.getQuest().set_step(30, 3);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71063) {
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    materials = new int[] { 40701 };
                                                    counts = new int[] { 1 };
                                                    createitem = new int[] { 40702 };
                                                    createcount = new int[] { 1 };
                                                    htmlid = "maptbox1";
                                                    pc.getQuest().set_end(24);
                                                    int[] nextbox = { 1, 2, 3 };
                                                    int pid = C_NPCAction._random.nextInt(nextbox.length);
                                                    int nb = nextbox[pid];
                                                    if (nb == 1) {
                                                        pc.getQuest().set_step(23, 2);
                                                    }
                                                    else if (nb == 2) {
                                                        pc.getQuest().set_step(23, 3);
                                                    }
                                                    else if (nb == 3) {
                                                        pc.getQuest().set_step(23, 4);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71064 || npctemp.get_npcId() == 71065 || npctemp.get_npcId() == 71066) {
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    materials = new int[] { 40701 };
                                                    counts = new int[] { 1 };
                                                    createitem = new int[] { 40702 };
                                                    createcount = new int[] { 1 };
                                                    htmlid = "maptbox1";
                                                    pc.getQuest().set_end(25);
                                                    int[] nextbox2 = { 1, 2, 3, 4, 5, 6 };
                                                    int pid = C_NPCAction._random.nextInt(nextbox2.length);
                                                    int nb2 = nextbox2[pid];
                                                    if (nb2 == 1) {
                                                        pc.getQuest().set_step(23, 5);
                                                    }
                                                    else if (nb2 == 2) {
                                                        pc.getQuest().set_step(23, 6);
                                                    }
                                                    else if (nb2 == 3) {
                                                        pc.getQuest().set_step(23, 7);
                                                    }
                                                    else if (nb2 == 4) {
                                                        pc.getQuest().set_step(23, 8);
                                                    }
                                                    else if (nb2 == 5) {
                                                        pc.getQuest().set_step(23, 9);
                                                    }
                                                    else if (nb2 == 6) {
                                                        pc.getQuest().set_step(23, 10);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71056) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    pc.getQuest().set_step(27, 1);
                                                    htmlid = "SIMIZZ7";
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().checkItem(40661) && pc.getInventory().checkItem(40662) && pc.getInventory().checkItem(40663)) {
                                                        htmlid = "SIMIZZ8";
                                                        pc.getQuest().set_step(27, 2);
                                                        materials = new int[] { 40661, 40662, 40663 };
                                                        counts = new int[] { 1, 1, 1 };
                                                        createitem = new int[] { 20044 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "SIMIZZ9";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("d")) {
                                                    htmlid = "SIMIZZ12";
                                                    pc.getQuest().set_step(27, 255);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71057) {
                                                if (cmd.equalsIgnoreCase("3")) {
                                                    htmlid = "doil4";
                                                }
                                                else if (cmd.equalsIgnoreCase("6")) {
                                                    htmlid = "doil6";
                                                }
                                                else if (cmd.equalsIgnoreCase("1")) {
                                                    if (pc.getInventory().checkItem(40714)) {
                                                        htmlid = "doil8";
                                                        materials = new int[] { 40714 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 40647 };
                                                        createcount = new int[] { 1 };
                                                        pc.getQuest().set_step(28, 255);
                                                    }
                                                    else {
                                                        htmlid = "doil7";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71059) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    htmlid = "rudian6";
                                                    int[] item_ids = { 40700 };
                                                    int[] item_amounts = { 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                    }
                                                    pc.getQuest().set_step(29, 1);
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    if (pc.getInventory().checkItem(40710)) {
                                                        htmlid = "rudian8";
                                                        materials = new int[] { 40700, 40710 };
                                                        counts = new int[] { 1, 1 };
                                                        createitem = new int[] { 40647 };
                                                        createcount = new int[] { 1 };
                                                        pc.getQuest().set_step(29, 255);
                                                    }
                                                    else {
                                                        htmlid = "rudian9";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71060) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    if (pc.getQuest().get_step(29) == 255) {
                                                        htmlid = "resta6";
                                                    }
                                                    else {
                                                        htmlid = "resta4";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    htmlid = "resta10";
                                                    pc.getQuest().set_step(30, 2);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71061) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    if (pc.getInventory().checkItem(40647, 3L)) {
                                                        htmlid = "cadmus6";
                                                        pc.getInventory().consumeItem(40647, 3L);
                                                        pc.getQuest().set_step(31, 2);
                                                    }
                                                    else {
                                                        htmlid = "cadmus5";
                                                        pc.getQuest().set_step(31, 1);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71036) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    htmlid = "kamyla7";
                                                    pc.getQuest().set_step(32, 1);
                                                }
                                                else if (cmd.equalsIgnoreCase("c")) {
                                                    htmlid = "kamyla10";
                                                    pc.getInventory().consumeItem(40644, 1L);
                                                    pc.getQuest().set_step(32, 3);
                                                }
                                                else if (cmd.equalsIgnoreCase("e")) {
                                                    htmlid = "kamyla13";
                                                    pc.getInventory().consumeItem(40630, 1L);
                                                    pc.getQuest().set_step(32, 4);
                                                }
                                                else if (cmd.equalsIgnoreCase("i")) {
                                                    htmlid = "kamyla25";
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getQuest().get_step(32) == 1) {
                                                        L1Teleport.teleport(pc, 32679, 32742, (short)482, 5, true);
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("d")) {
                                                    if (pc.getQuest().get_step(32) == 3) {
                                                        L1Teleport.teleport(pc, 32736, 32800, (short)483, 5, true);
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("f") && pc.getQuest().get_step(32) == 4) {
                                                    L1Teleport.teleport(pc, 32746, 32807, (short)484, 5, true);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71089) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    htmlid = "francu10";
                                                    int[] item_ids = { 40644 };
                                                    int[] item_amounts = { 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                        pc.getQuest().set_step(32, 2);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71090) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    htmlid = "";
                                                    int[] item_ids = { 246, 247, 248, 249, 40660 };
                                                    int[] item_amounts = { 1, 1, 1, 1, 5 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                        pc.getQuest().set_step(33, 1);
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().checkEquipped(246) || pc.getInventory().checkEquipped(247) || pc.getInventory().checkEquipped(248) || pc.getInventory().checkEquipped(249)) {
                                                        htmlid = "jcrystal5";
                                                    }
                                                    else if (pc.getInventory().checkItem(40660)) {
                                                        htmlid = "jcrystal4";
                                                    }
                                                    else {
                                                        pc.getInventory().consumeItem(246, 1L);
                                                        pc.getInventory().consumeItem(247, 1L);
                                                        pc.getInventory().consumeItem(248, 1L);
                                                        pc.getInventory().consumeItem(249, 1L);
                                                        pc.getInventory().consumeItem(40620, 1L);
                                                        pc.getQuest().set_step(33, 2);
                                                        L1Teleport.teleport(pc, 32801, 32895, (short)483, 4, true);
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("c")) {
                                                    if (pc.getInventory().checkEquipped(246) || pc.getInventory().checkEquipped(247) || pc.getInventory().checkEquipped(248) || pc.getInventory().checkEquipped(249)) {
                                                        htmlid = "jcrystal5";
                                                    }
                                                    else {
                                                        pc.getInventory().checkItem(40660);
                                                        L1ItemInstance l1iteminstance = pc.getInventory().findItemId(40660);
                                                        long sc = l1iteminstance.getCount();
                                                        if (sc > 0L) {
                                                            pc.getInventory().consumeItem(40660, sc);
                                                        }
                                                        pc.getInventory().consumeItem(246, 1L);
                                                        pc.getInventory().consumeItem(247, 1L);
                                                        pc.getInventory().consumeItem(248, 1L);
                                                        pc.getInventory().consumeItem(249, 1L);
                                                        pc.getInventory().consumeItem(40620, 1L);
                                                        pc.getQuest().set_step(33, 0);
                                                        L1Teleport.teleport(pc, 32736, 32800, (short)483, 4, true);
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71091) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    htmlid = "";
                                                    pc.getInventory().consumeItem(40654, 1L);
                                                    pc.getQuest().set_step(33, 255);
                                                    L1Teleport.teleport(pc, 32744, 32927, (short)483, 4, true);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71074) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    htmlid = "lelder5";
                                                    pc.getQuest().set_step(34, 1);
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    htmlid = "lelder10";
                                                    pc.getInventory().consumeItem(40633, 1L);
                                                    pc.getQuest().set_step(34, 3);
                                                }
                                                else if (cmd.equalsIgnoreCase("C")) {
                                                    htmlid = "lelder13";
                                                    pc.getQuest().get_step(34);
                                                    materials = new int[] { 40634 };
                                                    counts = new int[] { 1 };
                                                    createitem = new int[] { 20167 };
                                                    createcount = new int[] { 1 };
                                                    pc.getQuest().set_step(34, 255);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80079) {
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    if (!pc.getInventory().checkItem(41312)) {
                                                        L1ItemInstance item3 = pc.getInventory().storeItem(41312, 1L);
                                                        if (item3 != null) {
                                                            pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item3.getItem().getNameId()));
                                                            pc.getQuest().set_end(35);
                                                        }
                                                        htmlid = "keplisha7";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("1")) {
                                                    if (!pc.getInventory().checkItem(41314)) {
                                                        if (pc.getInventory().checkItem(40308, 1000L)) {
                                                            materials = new int[] { 40308, 41313 };
                                                            counts = new int[] { 1000, 1 };
                                                            createitem = new int[] { 41314 };
                                                            createcount = new int[] { 1 };
                                                            int htmlA = C_NPCAction._random.nextInt(3) + 1;
                                                            int htmlB = C_NPCAction._random.nextInt(100) + 1;
                                                            switch (htmlA) {
                                                                case 1: {
                                                                    htmlid = "horosa" + htmlB;
                                                                    break;
                                                                }
                                                                case 2: {
                                                                    htmlid = "horosb" + htmlB;
                                                                    break;
                                                                }
                                                                case 3: {
                                                                    htmlid = "horosc" + htmlB;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        else {
                                                            htmlid = "keplisha8";
                                                        }
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("2")) {
                                                    if (pc.getTempCharGfx() != pc.getClassId()) {
                                                        htmlid = "keplisha9";
                                                    }
                                                    else if (pc.getInventory().checkItem(41314)) {
                                                        pc.getInventory().consumeItem(41314, 1L);
                                                        int html = C_NPCAction._random.nextInt(9) + 1;
                                                        int PolyId = 6180 + C_NPCAction._random.nextInt(64);
                                                        this.polyByKeplisha(client, PolyId);
                                                        switch (html) {
                                                            case 1: {
                                                                htmlid = "horomon11";
                                                                break;
                                                            }
                                                            case 2: {
                                                                htmlid = "horomon12";
                                                                break;
                                                            }
                                                            case 3: {
                                                                htmlid = "horomon13";
                                                                break;
                                                            }
                                                            case 4: {
                                                                htmlid = "horomon21";
                                                                break;
                                                            }
                                                            case 5: {
                                                                htmlid = "horomon22";
                                                                break;
                                                            }
                                                            case 6: {
                                                                htmlid = "horomon23";
                                                                break;
                                                            }
                                                            case 7: {
                                                                htmlid = "horomon31";
                                                                break;
                                                            }
                                                            case 8: {
                                                                htmlid = "horomon32";
                                                                break;
                                                            }
                                                            case 9: {
                                                                htmlid = "horomon33";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("3")) {
                                                    if (pc.getInventory().checkItem(41312)) {
                                                        pc.getInventory().consumeItem(41312, 1L);
                                                        htmlid = "";
                                                    }
                                                    if (pc.getInventory().checkItem(41313)) {
                                                        pc.getInventory().consumeItem(41313, 1L);
                                                        htmlid = "";
                                                    }
                                                    if (pc.getInventory().checkItem(41314)) {
                                                        pc.getInventory().consumeItem(41314, 1L);
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80084) {
                                                if (cmd.equalsIgnoreCase("q")) {
                                                    if (pc.getInventory().checkItem(41356, 1L)) {
                                                        htmlid = "rparum4";
                                                    }
                                                    else {
                                                        L1ItemInstance item3 = pc.getInventory().storeItem(41356, 1L);
                                                        if (item3 != null) {
                                                            pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item3.getItem().getNameId()));
                                                        }
                                                        htmlid = "rparum3";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80105) {
                                                if (cmd.equalsIgnoreCase("c") && pc.isCrown() && pc.getInventory().checkItem(20383, 1L)) {
                                                    if (pc.getInventory().checkItem(40308, 100000L)) {
                                                        L1ItemInstance item3 = pc.getInventory().findItemId(20383);
                                                        if (item3 != null && item3.getChargeCount() != 50) {
                                                            item3.setChargeCount(50);
                                                            pc.getInventory().updateItem(item3, 128);
                                                            pc.getInventory().consumeItem(40308, 100000L);
                                                            htmlid = "";
                                                        }
                                                    }
                                                    else {
                                                        pc.sendPackets(new S_ServerMessage(337, "$4"));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71126) {
                                                if (cmd.equalsIgnoreCase("B")) {
                                                    if (pc.getInventory().checkItem(41007, 1L)) {
                                                        htmlid = "eris10";
                                                    }
                                                    else {
                                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                        L1ItemInstance item4 = pc.getInventory().storeItem(41007, 1L);
                                                        String npcName2 = npc2.getNpcTemplate().get_name();
                                                        String itemName2 = item4.getItem().getNameId();
                                                        pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                        htmlid = "eris6";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("C")) {
                                                    if (pc.getInventory().checkItem(41009, 1L)) {
                                                        htmlid = "eris10";
                                                    }
                                                    else {
                                                        L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                        L1ItemInstance item4 = pc.getInventory().storeItem(41009, 1L);
                                                        String npcName2 = npc2.getNpcTemplate().get_name();
                                                        String itemName2 = item4.getItem().getNameId();
                                                        pc.sendPackets(new S_ServerMessage(143, npcName2, itemName2));
                                                        htmlid = "eris8";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("A")) {
                                                    if (pc.getInventory().checkItem(41007, 1L)) {
                                                        if (pc.getInventory().checkItem(40969, 20L)) {
                                                            htmlid = "eris18";
                                                            materials = new int[] { 40969, 41007 };
                                                            counts = new int[] { 20, 1 };
                                                            createitem = new int[] { 41008 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else {
                                                            htmlid = "eris5";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "eris2";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("E")) {
                                                    if (pc.getInventory().checkItem(41010, 1L)) {
                                                        htmlid = "eris19";
                                                    }
                                                    else {
                                                        htmlid = "eris7";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("D")) {
                                                    if (pc.getInventory().checkItem(41010, 1L)) {
                                                        htmlid = "eris19";
                                                    }
                                                    else if (pc.getInventory().checkItem(41009, 1L)) {
                                                        if (pc.getInventory().checkItem(40959, 1L)) {
                                                            htmlid = "eris17";
                                                            materials = new int[] { 40959, 41009 };
                                                            counts = new int[] { 1, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40960, 1L)) {
                                                            htmlid = "eris16";
                                                            materials = new int[] { 40960, 41009 };
                                                            counts = new int[] { 1, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40961, 1L)) {
                                                            htmlid = "eris15";
                                                            materials = new int[] { 40961, 41009 };
                                                            counts = new int[] { 1, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40962, 1L)) {
                                                            htmlid = "eris14";
                                                            materials = new int[] { 40962, 41009 };
                                                            counts = new int[] { 1, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40635, 10L)) {
                                                            htmlid = "eris12";
                                                            materials = new int[] { 40635, 41009 };
                                                            counts = new int[] { 10, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40638, 10L)) {
                                                            htmlid = "eris11";
                                                            materials = new int[] { 40638, 41009 };
                                                            counts = new int[] { 10, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40642, 10L)) {
                                                            htmlid = "eris13";
                                                            materials = new int[] { 40642, 41009 };
                                                            counts = new int[] { 10, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else if (pc.getInventory().checkItem(40667, 10L)) {
                                                            htmlid = "eris13";
                                                            materials = new int[] { 40667, 41009 };
                                                            counts = new int[] { 10, 1 };
                                                            createitem = new int[] { 41010 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else {
                                                            htmlid = "eris8";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "eris7";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80076) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    int[] diaryno = { 49082, 49083 };
                                                    int pid = C_NPCAction._random.nextInt(diaryno.length);
                                                    int di = diaryno[pid];
                                                    if (di == 49082) {
                                                        htmlid = "voyager6a";
                                                        L1NpcInstance npc4 = (L1NpcInstance)obj;
                                                        L1ItemInstance item6 = pc.getInventory().storeItem(di, 1L);
                                                        String npcName3 = npc4.getNpcTemplate().get_name();
                                                        String itemName3 = item6.getItem().getNameId();
                                                        pc.sendPackets(new S_ServerMessage(143, npcName3, itemName3));
                                                    }
                                                    else if (di == 49083) {
                                                        htmlid = "voyager6b";
                                                        L1NpcInstance npc4 = (L1NpcInstance)obj;
                                                        L1ItemInstance item6 = pc.getInventory().storeItem(di, 1L);
                                                        String npcName3 = npc4.getNpcTemplate().get_name();
                                                        String itemName3 = item6.getItem().getNameId();
                                                        pc.sendPackets(new S_ServerMessage(143, npcName3, itemName3));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71128) {
                                                if (cmd.equals("A")) {
                                                    if (pc.getInventory().checkItem(41010, 1L)) {
                                                        htmlid = "perita2";
                                                    }
                                                    else {
                                                        htmlid = "perita3";
                                                    }
                                                }
                                                else if (cmd.equals("p")) {
                                                    if (pc.getInventory().checkItem(40987, 1L) && pc.getInventory().checkItem(40988, 1L) && pc.getInventory().checkItem(40989, 1L)) {
                                                        htmlid = "perita43";
                                                    }
                                                    else if (pc.getInventory().checkItem(40987, 1L) && pc.getInventory().checkItem(40989, 1L)) {
                                                        htmlid = "perita44";
                                                    }
                                                    else if (pc.getInventory().checkItem(40987, 1L) && pc.getInventory().checkItem(40988, 1L)) {
                                                        htmlid = "perita45";
                                                    }
                                                    else if (pc.getInventory().checkItem(40988, 1L) && pc.getInventory().checkItem(40989, 1L)) {
                                                        htmlid = "perita47";
                                                    }
                                                    else if (pc.getInventory().checkItem(40987, 1L)) {
                                                        htmlid = "perita46";
                                                    }
                                                    else if (pc.getInventory().checkItem(40988, 1L)) {
                                                        htmlid = "perita49";
                                                    }
                                                    else if (pc.getInventory().checkItem(40987, 1L)) {
                                                        htmlid = "perita48";
                                                    }
                                                    else {
                                                        htmlid = "perita50";
                                                    }
                                                }
                                                else if (cmd.equals("q")) {
                                                    if (pc.getInventory().checkItem(41173, 1L) && pc.getInventory().checkItem(41174, 1L) && pc.getInventory().checkItem(41175, 1L)) {
                                                        htmlid = "perita54";
                                                    }
                                                    else if (pc.getInventory().checkItem(41173, 1L) && pc.getInventory().checkItem(41175, 1L)) {
                                                        htmlid = "perita55";
                                                    }
                                                    else if (pc.getInventory().checkItem(41173, 1L) && pc.getInventory().checkItem(41174, 1L)) {
                                                        htmlid = "perita56";
                                                    }
                                                    else if (pc.getInventory().checkItem(41174, 1L) && pc.getInventory().checkItem(41175, 1L)) {
                                                        htmlid = "perita58";
                                                    }
                                                    else if (pc.getInventory().checkItem(41174, 1L)) {
                                                        htmlid = "perita57";
                                                    }
                                                    else if (pc.getInventory().checkItem(41175, 1L)) {
                                                        htmlid = "perita60";
                                                    }
                                                    else if (pc.getInventory().checkItem(41176, 1L)) {
                                                        htmlid = "perita59";
                                                    }
                                                    else {
                                                        htmlid = "perita61";
                                                    }
                                                }
                                                else if (cmd.equals("s")) {
                                                    if (pc.getInventory().checkItem(41161, 1L) && pc.getInventory().checkItem(41162, 1L) && pc.getInventory().checkItem(41163, 1L)) {
                                                        htmlid = "perita62";
                                                    }
                                                    else if (pc.getInventory().checkItem(41161, 1L) && pc.getInventory().checkItem(41163, 1L)) {
                                                        htmlid = "perita63";
                                                    }
                                                    else if (pc.getInventory().checkItem(41161, 1L) && pc.getInventory().checkItem(41162, 1L)) {
                                                        htmlid = "perita64";
                                                    }
                                                    else if (pc.getInventory().checkItem(41162, 1L) && pc.getInventory().checkItem(41163, 1L)) {
                                                        htmlid = "perita66";
                                                    }
                                                    else if (pc.getInventory().checkItem(41161, 1L)) {
                                                        htmlid = "perita65";
                                                    }
                                                    else if (pc.getInventory().checkItem(41162, 1L)) {
                                                        htmlid = "perita68";
                                                    }
                                                    else if (pc.getInventory().checkItem(41163, 1L)) {
                                                        htmlid = "perita67";
                                                    }
                                                    else {
                                                        htmlid = "perita69";
                                                    }
                                                }
                                                else if (cmd.equals("B")) {
                                                    if (pc.getInventory().checkItem(40651, 10L) && pc.getInventory().checkItem(40643, 10L) && pc.getInventory().checkItem(40618, 10L) && pc.getInventory().checkItem(40645, 10L) && pc.getInventory().checkItem(40676, 10L) && pc.getInventory().checkItem(40442, 5L) && pc.getInventory().checkItem(340051, 1L)) {
                                                        htmlid = "perita7";
                                                        materials = new int[] { 40651, 40643, 40618, 40645, 40676, 40442, 340051 };
                                                        counts = new int[] { 10, 10, 10, 10, 20, 5, 1 };
                                                        createitem = new int[] { 40925 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita8";
                                                    }
                                                }
                                                else if (cmd.equals("G") || cmd.equals("h") || cmd.equals("i")) {
                                                    if (pc.getInventory().checkItem(40651, 5L) && pc.getInventory().checkItem(40643, 5L) && pc.getInventory().checkItem(40618, 5L) && pc.getInventory().checkItem(40645, 5L) && pc.getInventory().checkItem(40676, 5L) && pc.getInventory().checkItem(40675, 5L) && pc.getInventory().checkItem(340049, 3L) && pc.getInventory().checkItem(340051, 1L)) {
                                                        htmlid = "perita27";
                                                        materials = new int[] { 40651, 40643, 40618, 40645, 40676, 40675, 340049, 340051 };
                                                        counts = new int[] { 5, 5, 5, 5, 10, 10, 3, 1 };
                                                        createitem = new int[] { 40926 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita28";
                                                    }
                                                }
                                                else if (cmd.equals("H") || cmd.equals("j") || cmd.equals("k")) {
                                                    if (pc.getInventory().checkItem(40651, 10L) && pc.getInventory().checkItem(40643, 10L) && pc.getInventory().checkItem(40618, 10L) && pc.getInventory().checkItem(40645, 10L) && pc.getInventory().checkItem(40676, 20L) && pc.getInventory().checkItem(40675, 10L) && pc.getInventory().checkItem(340048, 3L) && pc.getInventory().checkItem(340051, 1L)) {
                                                        htmlid = "perita29";
                                                        materials = new int[] { 40651, 40643, 40618, 40645, 40676, 40675, 340048, 340051 };
                                                        counts = new int[] { 10, 10, 10, 10, 20, 10, 3, 1 };
                                                        createitem = new int[] { 40927 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita30";
                                                    }
                                                }
                                                else if (cmd.equals("I") || cmd.equals("l") || cmd.equals("m")) {
                                                    if (pc.getInventory().checkItem(40651, 20L) && pc.getInventory().checkItem(40643, 20L) && pc.getInventory().checkItem(40618, 20L) && pc.getInventory().checkItem(40645, 20L) && pc.getInventory().checkItem(40676, 30L) && pc.getInventory().checkItem(40675, 10L) && pc.getInventory().checkItem(340050, 3L) && pc.getInventory().checkItem(340051, 1L)) {
                                                        htmlid = "perita31";
                                                        materials = new int[] { 40651, 40643, 40618, 40645, 40676, 40675, 340050, 340051 };
                                                        counts = new int[] { 20, 20, 20, 20, 30, 10, 3, 1 };
                                                        createitem = new int[] { 40928 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita32";
                                                    }
                                                }
                                                else if (cmd.equals("J") || cmd.equals("n") || cmd.equals("o")) {
                                                    if (pc.getInventory().checkItem(40651, 30L) && pc.getInventory().checkItem(40643, 30L) && pc.getInventory().checkItem(40618, 30L) && pc.getInventory().checkItem(40645, 30L) && pc.getInventory().checkItem(40676, 30L) && pc.getInventory().checkItem(40675, 20L) && pc.getInventory().checkItem(340052, 1L) && pc.getInventory().checkItem(340051, 1L)) {
                                                        htmlid = "perita33";
                                                        materials = new int[] { 40651, 40643, 40618, 40645, 40676, 40675, 340052, 340051 };
                                                        counts = new int[] { 30, 30, 30, 30, 30, 20, 1, 1 };
                                                        createitem = new int[] { 40928 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita34";
                                                    }
                                                }
                                                else if (cmd.equals("K")) {
                                                    int earinga = 0;
                                                    int earingb = 0;
                                                    if (pc.getInventory().checkEquipped(21014) || pc.getInventory().checkEquipped(21006) || pc.getInventory().checkEquipped(21007)) {
                                                        htmlid = "perita36";
                                                    }
                                                    else if (pc.getInventory().checkItem(21014, 1L)) {
                                                        earinga = 21014;
                                                        earingb = 41176;
                                                    }
                                                    else if (pc.getInventory().checkItem(21006, 1L)) {
                                                        earinga = 21006;
                                                        earingb = 41177;
                                                    }
                                                    else if (pc.getInventory().checkItem(21007, 1L)) {
                                                        earinga = 21007;
                                                        earingb = 41178;
                                                    }
                                                    else {
                                                        htmlid = "perita36";
                                                    }
                                                    if (earinga > 0) {
                                                        materials = new int[] { earinga };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { earingb };
                                                        createcount = new int[] { 1 };
                                                    }
                                                }
                                                else if (cmd.equals("L")) {
                                                    if (pc.getInventory().checkEquipped(21015)) {
                                                        htmlid = "perita22";
                                                    }
                                                    else if (pc.getInventory().checkItem(21015, 1L)) {
                                                        materials = new int[] { 21015 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 41179 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita22";
                                                    }
                                                }
                                                else if (cmd.equals("M")) {
                                                    if (pc.getInventory().checkEquipped(21016)) {
                                                        htmlid = "perita26";
                                                    }
                                                    else if (pc.getInventory().checkItem(21016, 1L)) {
                                                        materials = new int[] { 21016 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 41182 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita26";
                                                    }
                                                }
                                                else if (cmd.equals("b")) {
                                                    if (pc.getInventory().checkEquipped(21009)) {
                                                        htmlid = "perita39";
                                                    }
                                                    else if (pc.getInventory().checkItem(21009, 1L)) {
                                                        materials = new int[] { 21009 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 41180 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita39";
                                                    }
                                                }
                                                else if (cmd.equals("d")) {
                                                    if (pc.getInventory().checkEquipped(21012)) {
                                                        htmlid = "perita41";
                                                    }
                                                    else if (pc.getInventory().checkItem(21012, 1L)) {
                                                        materials = new int[] { 21012 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 41183 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita41";
                                                    }
                                                }
                                                else if (cmd.equals("a")) {
                                                    if (pc.getInventory().checkEquipped(21008)) {
                                                        htmlid = "perita38";
                                                    }
                                                    else if (pc.getInventory().checkItem(21008, 1L)) {
                                                        materials = new int[] { 21008 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 41181 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita38";
                                                    }
                                                }
                                                else if (cmd.equals("c")) {
                                                    if (pc.getInventory().checkEquipped(21010)) {
                                                        htmlid = "perita40";
                                                    }
                                                    else if (pc.getInventory().checkItem(21010, 1L)) {
                                                        materials = new int[] { 21010 };
                                                        counts = new int[] { 1 };
                                                        createitem = new int[] { 41184 };
                                                        createcount = new int[] { 1 };
                                                    }
                                                    else {
                                                        htmlid = "perita40";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71129) {
                                                if (cmd.equals("Z")) {
                                                    htmlid = "rumtis2";
                                                }
                                                else if (cmd.equals("Y")) {
                                                    if (pc.getInventory().checkItem(41010, 1L)) {
                                                        htmlid = "rumtis3";
                                                    }
                                                    else {
                                                        htmlid = "rumtis4";
                                                    }
                                                }
                                                else if (cmd.equals("q")) {
                                                    htmlid = "rumtis92";
                                                }
                                                else if (cmd.equals("A")) {
                                                    if (pc.getInventory().checkItem(41161, 1L)) {
                                                        htmlid = "rumtis6";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("B")) {
                                                    if (pc.getInventory().checkItem(41164, 1L)) {
                                                        htmlid = "rumtis7";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("C")) {
                                                    if (pc.getInventory().checkItem(41167, 1L)) {
                                                        htmlid = "rumtis8";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("T")) {
                                                    if (pc.getInventory().checkItem(41167, 1L)) {
                                                        htmlid = "rumtis9";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("w")) {
                                                    if (pc.getInventory().checkItem(41162, 1L)) {
                                                        htmlid = "rumtis14";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("x")) {
                                                    if (pc.getInventory().checkItem(41165, 1L)) {
                                                        htmlid = "rumtis15";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("y")) {
                                                    if (pc.getInventory().checkItem(41168, 1L)) {
                                                        htmlid = "rumtis16";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("z")) {
                                                    if (pc.getInventory().checkItem(41171, 1L)) {
                                                        htmlid = "rumtis17";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("U")) {
                                                    if (pc.getInventory().checkItem(41163, 1L)) {
                                                        htmlid = "rumtis10";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("V")) {
                                                    if (pc.getInventory().checkItem(41166, 1L)) {
                                                        htmlid = "rumtis11";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("W")) {
                                                    if (pc.getInventory().checkItem(41169, 1L)) {
                                                        htmlid = "rumtis12";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("X")) {
                                                    if (pc.getInventory().checkItem(41172, 1L)) {
                                                        htmlid = "rumtis13";
                                                    }
                                                    else {
                                                        htmlid = "rumtis101";
                                                    }
                                                }
                                                else if (cmd.equals("D") || cmd.equals("E") || cmd.equals("F") || cmd.equals("G")) {
                                                    int insn = 0;
                                                    int bacn = 0;
                                                    int me = 0;
                                                    int mr = 0;
                                                    int mj = 0;
                                                    int an = 0;
                                                    int men = 0;
                                                    int mrn = 0;
                                                    int mjn = 0;
                                                    int ann = 0;
                                                    if (pc.getInventory().checkItem(40959, 1L) && pc.getInventory().checkItem(40960, 1L) && pc.getInventory().checkItem(40961, 1L) && pc.getInventory().checkItem(40962, 1L)) {
                                                        insn = 1;
                                                        me = 40959;
                                                        mr = 40960;
                                                        mj = 40961;
                                                        an = 40962;
                                                        men = 1;
                                                        mrn = 1;
                                                        mjn = 1;
                                                        ann = 1;
                                                    }
                                                    else if (pc.getInventory().checkItem(40642, 10L) && pc.getInventory().checkItem(40635, 10L) && pc.getInventory().checkItem(40638, 10L) && pc.getInventory().checkItem(40667, 10L)) {
                                                        bacn = 1;
                                                        me = 40642;
                                                        mr = 40635;
                                                        mj = 40638;
                                                        an = 40667;
                                                        men = 10;
                                                        mrn = 10;
                                                        mjn = 10;
                                                        ann = 10;
                                                    }
                                                    if (pc.getInventory().checkItem(340046, 1L) && pc.getInventory().checkItem(40618, 5L) && pc.getInventory().checkItem(40643, 5L) && pc.getInventory().checkItem(40645, 5L) && pc.getInventory().checkItem(40651, 5L) && pc.getInventory().checkItem(40676, 5L)) {
                                                        if (insn == 1 || bacn == 1) {
                                                            htmlid = "rumtis60";
                                                            materials = new int[] { me, mr, mj, an, 340046, 40618, 40643, 40651, 40676 };
                                                            counts = new int[] { men, mrn, mjn, ann, 1, 5, 5, 5, 5, 5 };
                                                            createitem = new int[] { 40926 };
                                                            createcount = new int[] { 1 };
                                                        }
                                                        else {
                                                            htmlid = "rumtis18";
                                                        }
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71119) {
                                                if (cmd.equalsIgnoreCase("request las history book")) {
                                                    materials = new int[] { 41019, 41020, 41021, 41022, 41023, 41024, 41025, 41026 };
                                                    counts = new int[] { 1, 1, 1, 1, 1, 1, 1, 1 };
                                                    createitem = new int[] { 41027 };
                                                    createcount = new int[] { 1 };
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71170) {
                                                if (cmd.equalsIgnoreCase("request las weapon manual")) {
                                                    materials = new int[] { 41027 };
                                                    counts = new int[] { 1 };
                                                    createitem = new int[] { 40965 };
                                                    createcount = new int[] { 1 };
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 71168) {
                                                if (cmd.equalsIgnoreCase("a") && pc.getInventory().checkItem(41028, 1L)) {
                                                    L1Teleport.teleport(pc, 32648, 32921, (short)535, 6, true);
                                                    pc.getInventory().consumeItem(41028, 1L);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80067) {
                                                if (cmd.equalsIgnoreCase("n")) {
                                                    htmlid = "";
                                                    this.poly(client, 6034);
                                                    int[] item_ids = { 41132, 41133, 41134 };
                                                    int[] item_amounts = { 1, 1, 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                        pc.getQuest().set_step(36, 1);
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("d")) {
                                                    htmlid = "minicod09";
                                                    pc.getInventory().consumeItem(41130, 1L);
                                                    pc.getInventory().consumeItem(41131, 1L);
                                                }
                                                else if (cmd.equalsIgnoreCase("k")) {
                                                    htmlid = "";
                                                    pc.getInventory().consumeItem(41132, 1L);
                                                    pc.getInventory().consumeItem(41133, 1L);
                                                    pc.getInventory().consumeItem(41134, 1L);
                                                    pc.getInventory().consumeItem(41135, 1L);
                                                    pc.getInventory().consumeItem(41136, 1L);
                                                    pc.getInventory().consumeItem(41137, 1L);
                                                    pc.getInventory().consumeItem(41138, 1L);
                                                    pc.getQuest().set_step(36, 0);
                                                }
                                                else if (cmd.equalsIgnoreCase("e")) {
                                                    if (pc.getQuest().get_step(36) == 255 || pc.getKarmaLevel() >= 1) {
                                                        htmlid = "";
                                                    }
                                                    else if (pc.getInventory().checkItem(41138)) {
                                                        htmlid = "";
                                                        pc.addKarma((int)(1600.0 * ConfigRate.RATE_KARMA));
                                                        pc.getInventory().consumeItem(41130, 1L);
                                                        pc.getInventory().consumeItem(41131, 1L);
                                                        pc.getInventory().consumeItem(41138, 1L);
                                                        pc.getQuest().set_step(36, 255);
                                                    }
                                                    else {
                                                        htmlid = "minicod04";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("g")) {
                                                    htmlid = "";
                                                    int[] item_ids = { 41130 };
                                                    int[] item_amounts = { 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 81202) {
                                                if (cmd.equalsIgnoreCase("n")) {
                                                    htmlid = "";
                                                    this.poly(client, 6035);
                                                    int[] item_ids = { 41123, 41124, 41125 };
                                                    int[] item_amounts = { 1, 1, 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                        pc.getQuest().set_step(37, 1);
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("d")) {
                                                    htmlid = "minitos09";
                                                    pc.getInventory().consumeItem(41121, 1L);
                                                    pc.getInventory().consumeItem(41122, 1L);
                                                }
                                                else if (cmd.equalsIgnoreCase("k")) {
                                                    htmlid = "";
                                                    pc.getInventory().consumeItem(41123, 1L);
                                                    pc.getInventory().consumeItem(41124, 1L);
                                                    pc.getInventory().consumeItem(41125, 1L);
                                                    pc.getInventory().consumeItem(41126, 1L);
                                                    pc.getInventory().consumeItem(41127, 1L);
                                                    pc.getInventory().consumeItem(41128, 1L);
                                                    pc.getInventory().consumeItem(41129, 1L);
                                                    pc.getQuest().set_step(37, 0);
                                                }
                                                else if (cmd.equalsIgnoreCase("e")) {
                                                    if (pc.getQuest().get_step(37) == 255 || pc.getKarmaLevel() >= 1) {
                                                        htmlid = "";
                                                    }
                                                    else if (pc.getInventory().checkItem(41129)) {
                                                        htmlid = "";
                                                        pc.addKarma((int)(-1600.0 * ConfigRate.RATE_KARMA));
                                                        pc.getInventory().consumeItem(41121, 1L);
                                                        pc.getInventory().consumeItem(41122, 1L);
                                                        pc.getInventory().consumeItem(41129, 1L);
                                                        pc.getQuest().set_step(37, 255);
                                                    }
                                                    else {
                                                        htmlid = "minitos04";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("g")) {
                                                    htmlid = "";
                                                    int[] item_ids = { 41121 };
                                                    int[] item_amounts = { 1 };
                                                    for (int l = 0; l < item_ids.length; ++l) {
                                                        L1ItemInstance item5 = pc.getInventory().storeItem(item_ids[l], (long)item_amounts[l]);
                                                        pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item5.getItem().getNameId()));
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 70908) {
                                                int[] oldweapon = { 81, 162, 177, 194, 13 };
                                                int newWeapon = 0;
                                                boolean success = false;
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    newWeapon = 410162;
                                                    int m = 0;
                                                    while (m < oldweapon.length) {
                                                        if (pc.getInventory().checkEnchantItem(oldweapon[m], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                                                            pc.getInventory().consumeEnchantItem(oldweapon[m], 8, 1L);
                                                            pc.getInventory().consumeItem(40308, 5000000L);
                                                            L1ItemInstance item6 = ItemTable.get().createItem(newWeapon);
                                                            item6.setEnchantLevel(7);
                                                            item6.setIdentified(true);
                                                            pc.getInventory().storeItem(item6);
                                                            pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item6.getLogName()));
                                                            success = true;
                                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                                            break;
                                                        }
                                                        else {
                                                            ++m;
                                                        }
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    newWeapon = 410161;
                                                    int m = 0;
                                                    while (m < oldweapon.length) {
                                                        if (pc.getInventory().checkEnchantItem(oldweapon[m], 8, 1L) && pc.getInventory().checkItem(40308, 5000000L)) {
                                                            pc.getInventory().consumeEnchantItem(oldweapon[m], 8, 1L);
                                                            pc.getInventory().consumeItem(40308, 5000000L);
                                                            L1ItemInstance item6 = ItemTable.get().createItem(newWeapon);
                                                            item6.setEnchantLevel(7);
                                                            item6.setIdentified(true);
                                                            pc.getInventory().storeItem(item6);
                                                            pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item6.getLogName()));
                                                            success = true;
                                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                                            break;
                                                        }
                                                        else {
                                                            ++m;
                                                        }
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("C")) {
                                                    newWeapon = 410162;
                                                    int m = 0;
                                                    while (m < oldweapon.length) {
                                                        if (pc.getInventory().checkEnchantItem(oldweapon[m], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                                                            pc.getInventory().consumeEnchantItem(oldweapon[m], 9, 1L);
                                                            pc.getInventory().consumeItem(40308, 10000000L);
                                                            L1ItemInstance item6 = ItemTable.get().createItem(newWeapon);
                                                            item6.setEnchantLevel(8);
                                                            item6.setIdentified(true);
                                                            pc.getInventory().storeItem(item6);
                                                            pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item6.getLogName()));
                                                            success = true;
                                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                                            break;
                                                        }
                                                        else {
                                                            ++m;
                                                        }
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("D")) {
                                                    newWeapon = 410161;
                                                    int m = 0;
                                                    while (m < oldweapon.length) {
                                                        if (pc.getInventory().checkEnchantItem(oldweapon[m], 9, 1L) && pc.getInventory().checkItem(40308, 10000000L)) {
                                                            pc.getInventory().consumeEnchantItem(oldweapon[m], 9, 1L);
                                                            pc.getInventory().consumeItem(40308, 10000000L);
                                                            L1ItemInstance item6 = ItemTable.get().createItem(newWeapon);
                                                            item6.setEnchantLevel(8);
                                                            item6.setIdentified(true);
                                                            pc.getInventory().storeItem(item6);
                                                            pc.sendPackets(new S_ServerMessage(143, npctemp.get_name(), item6.getLogName()));
                                                            success = true;
                                                            pc.sendPackets(new S_CloseList(pc.getId()));
                                                            break;
                                                        }
                                                        else {
                                                            ++m;
                                                        }
                                                    }
                                                }
                                                if (!success) {
                                                    htmlid = "piers04";
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcId() == 81246) {
                                                if (pc.getLevel() < 30) {
                                                    htmlid = "sharna4";
                                                }
                                                else if (pc.getLevel() >= 30 && pc.getLevel() <= 39) {
                                                    createitem = new int[] { 82225 };
                                                }
                                                else if (pc.getLevel() >= 40 && pc.getLevel() <= 51) {
                                                    createitem = new int[] { 82226 };
                                                }
                                                else if (pc.getLevel() >= 52 && pc.getLevel() <= 54) {
                                                    createitem = new int[] { 82227 };
                                                }
                                                else if (pc.getLevel() >= 55 && pc.getLevel() <= 59) {
                                                    createitem = new int[] { 82228 };
                                                }
                                                else if (pc.getLevel() >= 60 && pc.getLevel() <= 64) {
                                                    createitem = new int[] { 82229 };
                                                }
                                                else if (pc.getLevel() >= 65 && pc.getLevel() <= 69) {
                                                    createitem = new int[] { 82230 };
                                                }
                                                else if (pc.getLevel() >= 70 && pc.getLevel() <= 74) {
                                                    createitem = new int[] { 82231 };
                                                }
                                                else if (pc.getLevel() >= 75 && pc.getLevel() <= 79) {
                                                    createitem = new int[] { 82232 };
                                                }
                                                else if (pc.getLevel() >= 80) {
                                                    createitem = new int[] { 82233 };
                                                }
                                                int createCount = 0;
                                                if (cmd.equalsIgnoreCase(":")) {
                                                    createCount = 11;
                                                }
                                                else if (cmd.equalsIgnoreCase(";")) {
                                                    createCount = 12;
                                                }
                                                else if (cmd.equalsIgnoreCase("<")) {
                                                    createCount = 13;
                                                }
                                                else if (cmd.equalsIgnoreCase("=")) {
                                                    createCount = 14;
                                                }
                                                else if (cmd.equalsIgnoreCase(">")) {
                                                    createCount = 15;
                                                }
                                                else if (cmd.equalsIgnoreCase("?")) {
                                                    createCount = 16;
                                                }
                                                else if (cmd.equalsIgnoreCase("@")) {
                                                    createCount = 17;
                                                }
                                                else if (cmd.equalsIgnoreCase("A")) {
                                                    createCount = 18;
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    createCount = 19;
                                                }
                                                else if (cmd.equalsIgnoreCase("C")) {
                                                    createCount = 20;
                                                }
                                                else {
                                                    createCount = Integer.parseInt(cmd) + 1;
                                                }
                                                if (createitem != null) {
                                                    materials = new int[] { 40308 };
                                                    createcount = new int[] { createCount };
                                                    counts = new int[] { 2500 * createCount };
                                                }
                                                success_htmlid = "sharna3";
                                                failure_htmlid = "sharna5";
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 71253) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    if (pc.getInventory().checkItem(49101, 100L)) {
                                                        materials = new int[] { 49101 };
                                                        counts = new int[] { 100 };
                                                        createitem = new int[] { 49092 };
                                                        createcount = new int[] { 1 };
                                                        htmlid = "joegolem18";
                                                    }
                                                    else {
                                                        htmlid = "joegolem19";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    if (pc.getInventory().checkItem(49101, 1L)) {
                                                        pc.getInventory().consumeItem(49101, 1L);
                                                        L1Teleport.teleport(pc, 33966, 33253, (short)4, 5, true);
                                                        htmlid = "";
                                                    }
                                                    else {
                                                        htmlid = "joegolem20";
                                                    }
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 71255) {
                                                if (cmd.equalsIgnoreCase("e")) {
                                                    if (ServerCrockTable.get().checkBoss()) {
                                                        if (pc.getInventory().checkItem(49242, 1L)) {
                                                            pc.getInventory().consumeItem(49242, 1L);
                                                            L1Teleport.teleport(pc, 32735, 32831, (short)782, 2, true);
                                                            htmlid = "";
                                                        }
                                                        else {
                                                            htmlid = "tebegate3";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "tebegate2";
                                                    }
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 70702) {
                                                if (cmd.equalsIgnoreCase("chg")) {
                                                    if (!pc.getInventory().checkItem(40308, 1000L)) {
                                                        pc.sendPackets(new S_ServerMessage(189));
                                                        return;
                                                    }
                                                    else {
                                                        for (L1ItemInstance item3 : pc.getInventory().getItems()) {
                                                            if (item3.getItemId() >= 40901 && item3.getItemId() <= 40908) {
                                                                int AddCount = 0;
                                                                if (item3.getItemId() == 40903) {
                                                                    AddCount = 1;
                                                                }
                                                                else if (item3.getItemId() == 40904) {
                                                                    AddCount = 2;
                                                                }
                                                                else if (item3.getItemId() == 40905) {
                                                                    AddCount = 3;
                                                                }
                                                                else if (item3.getItemId() == 40906) {
                                                                    AddCount = 5;
                                                                }
                                                                else if (item3.getItemId() == 40907 || item3.getItemId() == 40908) {
                                                                    AddCount = 20;
                                                                }
                                                                if (item3.getChargeCount() < AddCount) {
                                                                    item3.setChargeCount(AddCount);
                                                                    pc.getInventory().updateItem(item3, 128);
                                                                    pc.getInventory().consumeItem(40308, 1000L);
                                                                }
                                                                else {
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                        htmlid = "";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80099) {
                                                if (cmd.equalsIgnoreCase("A")) {
                                                    if (pc.getInventory().checkItem(40308, 300L)) {
                                                        pc.getInventory().consumeItem(40308, 300L);
                                                        pc.getInventory().storeItem(41315, 1L);
                                                        pc.getQuest().set_step(41, 1);
                                                        htmlid = "rarson16";
                                                    }
                                                    else if (!pc.getInventory().checkItem(40308, 300L)) {
                                                        htmlid = "rarson7";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("B")) {
                                                    if (pc.getQuest().get_step(41) == 1 && pc.getInventory().checkItem(41325, 1L)) {
                                                        pc.getInventory().consumeItem(41325, 1L);
                                                        pc.getInventory().storeItem(40308, 2000L);
                                                        pc.getInventory().storeItem(41317, 1L);
                                                        pc.getQuest().set_step(41, 2);
                                                        htmlid = "rarson9";
                                                    }
                                                    htmlid = "rarson10";
                                                }
                                                else if (cmd.equalsIgnoreCase("C")) {
                                                    if (pc.getQuest().get_step(41) == 4 && pc.getInventory().checkItem(41326, 1L)) {
                                                        pc.getInventory().storeItem(40308, 30000L);
                                                        pc.getInventory().consumeItem(41326, 1L);
                                                        htmlid = "rarson12";
                                                        pc.getQuest().set_step(41, 5);
                                                    }
                                                    htmlid = "rarson17";
                                                }
                                                else if (cmd.equalsIgnoreCase("D")) {
                                                    if (pc.getQuest().get_step(41) <= 1 || pc.getQuest().get_step(41) == 5) {
                                                        if (pc.getInventory().checkItem(40308, 300L)) {
                                                            pc.getInventory().consumeItem(40308, 300L);
                                                            pc.getInventory().storeItem(41315, 1L);
                                                            pc.getQuest().set_step(41, 1);
                                                            htmlid = "rarson16";
                                                        }
                                                        else if (!pc.getInventory().checkItem(40308, 300L)) {
                                                            htmlid = "rarson7";
                                                        }
                                                    }
                                                    else if (pc.getQuest().get_step(41) >= 2 && pc.getQuest().get_step(41) <= 4) {
                                                        if (pc.getInventory().checkItem(40308, 300L)) {
                                                            pc.getInventory().consumeItem(40308, 300L);
                                                            pc.getInventory().storeItem(41315, 1L);
                                                            htmlid = "rarson16";
                                                        }
                                                        else if (!pc.getInventory().checkItem(40308, 300L)) {
                                                            htmlid = "rarson7";
                                                        }
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 80101) {
                                                if (cmd.equalsIgnoreCase("request letter of kuen")) {
                                                    if (pc.getQuest().get_step(41) == 2 && pc.getInventory().checkItem(41317, 1L)) {
                                                        pc.getInventory().consumeItem(41317, 1L);
                                                        pc.getInventory().storeItem(41318, 1L);
                                                        pc.getQuest().set_step(41, 3);
                                                        htmlid = "";
                                                    }
                                                    htmlid = "";
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 93197) {
                                                if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().getSize() > 170) {
                                                        pc.sendPackets(new S_SystemMessage("你身上太多東西無法領取"));
                                                        return;
                                                    }
                                                    else if (!pc.getInventory().checkItem(95309, 1L)) {
                                                        if (pc.getInventory().consumeItem(40308, 150000L) && pc.getInventory().consumeItem(95308, 1L)) {
                                                            CreateNewItem.createNewItem(pc, 95309, 1L);
                                                            L1Teleport.teleport(pc, 32808, 32800, (short)4000, 4, true);
                                                            htmlid = "";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "shhon1";
                                                    }
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 93222) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    L1Teleport.teleport(pc, 33448, 32793, (short)4, 4, true);
                                                }
                                            }
                                            else if (((L1NpcInstance)obj).getNpcTemplate().get_npcId() == 93211) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    L1Teleport.teleport(pc, 32757, 32845, (short)2400, 4, true);
                                                }
                                            }
                                            else if (cmd.equalsIgnoreCase("request holy mithril dust")) {
                                                if (pc.getQuest().get_step(41) == 3 && pc.getInventory().checkItem(41315, 1L) && pc.getInventory().checkItem(40494, 30L) && pc.getInventory().checkItem(41318, 1L)) {
                                                    pc.getInventory().consumeItem(41315, 1L);
                                                    pc.getInventory().consumeItem(41318, 1L);
                                                    pc.getInventory().consumeItem(40494, 30L);
                                                    pc.getInventory().storeItem(41316, 1L);
                                                    pc.getQuest().set_step(41, 4);
                                                    htmlid = "";
                                                }
                                                htmlid = "";
                                            }
                                            else if (npctemp.get_npcId() == 81255) {
                                                int level2 = pc.getLevel();
                                                char s4 = cmd.charAt(0);
                                                if (level2 < 13) {
                                                    switch (s4) {
                                                        case 'A':
                                                        case 'a': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutorp1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutorp2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutorp3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 12) {
                                                                htmlid = "tutorp4";
                                                                break;
                                                            }
                                                            else if (level2 > 11 && level2 < 13) {
                                                                htmlid = "tutorp5";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutorp6";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'B':
                                                        case 'b': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutork1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutork2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutork3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 13) {
                                                                htmlid = "tutork4";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutork5";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'C':
                                                        case 'c': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutore1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutore2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutore3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 12) {
                                                                htmlid = "tutore4";
                                                                break;
                                                            }
                                                            else if (level2 > 11 && level2 < 13) {
                                                                htmlid = "tutore5";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutore6";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'D':
                                                        case 'd': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutorm1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutorm2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutorm3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 12) {
                                                                htmlid = "tutorm4";
                                                                break;
                                                            }
                                                            else if (level2 > 11 && level2 < 13) {
                                                                htmlid = "tutorm5";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutorm6";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'E':
                                                        case 'e': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutord1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutord2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutord3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 12) {
                                                                htmlid = "tutord4";
                                                                break;
                                                            }
                                                            else if (level2 > 11 && level2 < 13) {
                                                                htmlid = "tutord5";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutord6";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'F':
                                                        case 'f': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutordk1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutordk2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutordk3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 13) {
                                                                htmlid = "tutordk4";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutordk5";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'G':
                                                        case 'g': {
                                                            if (level2 > 1 && level2 < 5) {
                                                                htmlid = "tutori1";
                                                                break;
                                                            }
                                                            else if (level2 > 4 && level2 < 8) {
                                                                htmlid = "tutori2";
                                                                break;
                                                            }
                                                            else if (level2 > 7 && level2 < 10) {
                                                                htmlid = "tutori3";
                                                                break;
                                                            }
                                                            else if (level2 > 9 && level2 < 13) {
                                                                htmlid = "tutori4";
                                                                break;
                                                            }
                                                            else if (level2 > 12) {
                                                                htmlid = "tutori5";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "tutorend";
                                                                break;
                                                            }
                                                        }
                                                        case 'H':
                                                        case 'h': {
                                                            L1Teleport.teleport(pc, 32575, 32945, (short)0, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'I':
                                                        case 'i': {
                                                            L1Teleport.teleport(pc, 32579, 32923, (short)0, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'J':
                                                        case 'j': {
                                                            createitem = new int[] { 42099 };
                                                            createcount = new int[] { 1 };
                                                            L1Teleport.teleport(pc, 32676, 32813, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'K':
                                                        case 'k': {
                                                            L1Teleport.teleport(pc, 32562, 33082, (short)0, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'L':
                                                        case 'l': {
                                                            L1Teleport.teleport(pc, 32792, 32820, (short)75, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'M':
                                                        case 'm': {
                                                            L1Teleport.teleport(pc, 32877, 32904, (short)304, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'N':
                                                        case 'n': {
                                                            L1Teleport.teleport(pc, 32759, 32884, (short)1000, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'O':
                                                        case 'o': {
                                                            L1Teleport.teleport(pc, 32605, 32837, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'P':
                                                        case 'p': {
                                                            L1Teleport.teleport(pc, 32733, 32902, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'Q':
                                                        case 'q': {
                                                            L1Teleport.teleport(pc, 32559, 32843, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'R':
                                                        case 'r': {
                                                            L1Teleport.teleport(pc, 32677, 32982, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'S':
                                                        case 's': {
                                                            L1Teleport.teleport(pc, 32781, 32854, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'T':
                                                        case 't': {
                                                            L1Teleport.teleport(pc, 32674, 32739, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'U':
                                                        case 'u': {
                                                            L1Teleport.teleport(pc, 32578, 32737, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'V':
                                                        case 'v': {
                                                            L1Teleport.teleport(pc, 32542, 32996, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'W':
                                                        case 'w': {
                                                            L1Teleport.teleport(pc, 32794, 32973, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'X':
                                                        case 'x': {
                                                            L1Teleport.teleport(pc, 32803, 32789, (short)2005, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 81256) {
                                                int quest_step = pc.getQuest().get_step(304);
                                                int level = pc.getLevel();
                                                if (cmd.equalsIgnoreCase("A") && level > 4 && quest_step == 2) {
                                                    createitem = new int[] { 20028, 20126, 20173, 20206, 20232, 340029, 340030, 340098, 340099, 42099 };
                                                    createcount = new int[] { 1, 1, 1, 1, 1, 50, 5, 20, 30, 5 };
                                                }
                                                htmlid = "";
                                            }
                                            else if (npctemp.get_npcId() == 81257) {
                                                int level2 = pc.getLevel();
                                                char s4 = cmd.charAt(0);
                                                if (level2 < 46) {
                                                    switch (s4) {
                                                        case '0': {
                                                            if (level2 < 13) {
                                                                htmlid = "lowlvS1";
                                                                break;
                                                            }
                                                            else if (level2 > 12 && level2 < 46) {
                                                                htmlid = "lowlvS2";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlvno";
                                                                break;
                                                            }
                                                        }
                                                        case '1': {
                                                            if (level2 < 13) {
                                                                htmlid = "lowlv14";
                                                                break;
                                                            }
                                                            else if (level2 > 12 && level2 < 46) {
                                                                htmlid = "lowlv15";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlvno";
                                                                break;
                                                            }
                                                        }
                                                        case '2': {
                                                            createitem = new int[] { 20028, 20126, 20173, 20206, 20232, 21138, 42007 };
                                                            createcount = new int[7];
                                                            boolean isOK = false;
                                                            for (int m = 0; m < createitem.length; ++m) {
                                                                if (createitem[m] == 42007) {
                                                                    L1ItemInstance item6 = pc.getInventory().findItemId(createitem[m]);
                                                                    if (item6 != null) {
                                                                        if (item6.getCount() < 1000L) {
                                                                            createcount[m] = (int)(1000L - item6.getCount());
                                                                            isOK = true;
                                                                        }
                                                                    }
                                                                    else {
                                                                        createcount[m] = 1000;
                                                                        isOK = true;
                                                                    }
                                                                }
                                                                else if (!pc.getInventory().checkItem(createitem[m], 1L)) {
                                                                    createcount[m] = 1;
                                                                    isOK = true;
                                                                }
                                                            }
                                                            if (isOK) {
                                                                success_htmlid = "lowlv16";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlv17";
                                                                break;
                                                            }
                                                        }
                                                        case '6': {
                                                            if (!pc.getInventory().checkItem(42010, 1L) && !pc.getInventory().checkItem(42011, 1L)) {
                                                                createitem = new int[] { 42010 };
                                                                createcount = new int[] { 2 };
                                                                materials = new int[] { 40308 };
                                                                counts = new int[] { 2000 };
                                                                success_htmlid = "lowlv22";
                                                                failure_htmlid = "lowlv20";
                                                                break;
                                                            }
                                                            else if (pc.getInventory().checkItem(42010, 1L) || pc.getInventory().checkItem(42011, 1L)) {
                                                                htmlid = "lowlv23";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlvno";
                                                                break;
                                                            }
                                                        }
                                                        case 'A':
                                                        case 'a': {
                                                            L1Teleport.teleport(pc, 32562, 33082, (short)0, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'B':
                                                        case 'b': {
                                                            L1Teleport.teleport(pc, 33119, 32933, (short)4, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'C':
                                                        case 'c': {
                                                            L1Teleport.teleport(pc, 32887, 32652, (short)4, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'D':
                                                        case 'd': {
                                                            L1Teleport.teleport(pc, 32792, 32820, (short)75, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'E':
                                                        case 'e': {
                                                            L1Teleport.teleport(pc, 32789, 32851, (short)76, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'F':
                                                        case 'f': {
                                                            L1Teleport.teleport(pc, 32750, 32847, (short)76, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'G':
                                                        case 'g': {
                                                            if (pc.isDarkelf()) {
                                                                L1Teleport.teleport(pc, 32877, 32904, (short)304, 5, true);
                                                                htmlid = "";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlv40";
                                                                break;
                                                            }
                                                        }
                                                        case 'H':
                                                        case 'h': {
                                                            if (pc.isDragonKnight()) {
                                                                L1Teleport.teleport(pc, 32811, 32873, (short)1001, 5, true);
                                                                htmlid = "";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlv41";
                                                                break;
                                                            }
                                                        }
                                                        case 'I':
                                                        case 'i': {
                                                            if (pc.isIllusionist()) {
                                                                L1Teleport.teleport(pc, 32759, 32884, (short)1000, 5, true);
                                                                htmlid = "";
                                                                break;
                                                            }
                                                            else {
                                                                htmlid = "lowlv42";
                                                                break;
                                                            }
                                                        }
                                                        case 'J':
                                                        case 'j': {
                                                            L1Teleport.teleport(pc, 32509, 32867, (short)0, 5, true);
                                                            htmlid = "";
                                                            break;
                                                        }
                                                        case 'K':
                                                        case 'k': {
                                                            if (level2 > 34) {
                                                                createitem = new int[] { 20282, 21139 };
                                                                createcount = new int[2];
                                                                boolean isOK = false;
                                                                for (int m = 0; m < createitem.length; ++m) {
                                                                    if (!pc.getInventory().checkItem(createitem[m], 1L)) {
                                                                        createcount[m] = 1;
                                                                        isOK = true;
                                                                    }
                                                                }
                                                                if (isOK) {
                                                                    success_htmlid = "lowlv43";
                                                                    break;
                                                                }
                                                                else {
                                                                    htmlid = "lowlv45";
                                                                    break;
                                                                }
                                                            }
                                                            else {
                                                                htmlid = "lowlv44";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 81278) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().checkItem(40308, 10000000L) && !pc.getInventory().checkItem(47010)) {
                                                        pc.getInventory().consumeItem(40308, 10000000L);
                                                        pc.getInventory().storeItem(47010, 1L);
                                                        htmlid = "okveil";
                                                    }
                                                    else {
                                                        htmlid = "noveil";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 70936) {
                                                int level2 = pc.getLevel();
                                                char s4 = cmd.charAt(0);
                                                if (cmd.equalsIgnoreCase("0")) {
                                                    if (level2 >= 30 && level2 <= 51) {
                                                        L1Teleport.teleport(pc, 32820, 32904, (short)1002, 5, true);
                                                        htmlid = "";
                                                    }
                                                    else {
                                                        htmlid = "dsecret3";
                                                    }
                                                }
                                                else if (level2 >= 52) {
                                                    switch (s4) {
                                                        case '1': {
                                                            L1Teleport.teleport(pc, 32904, 32627, (short)1002, 5, true);
                                                            break;
                                                        }
                                                        case '2': {
                                                            L1Teleport.teleport(pc, 32793, 32593, (short)1002, 5, true);
                                                            break;
                                                        }
                                                        case '3': {
                                                            L1Teleport.teleport(pc, 32874, 32785, (short)1002, 5, true);
                                                            break;
                                                        }
                                                        case '4': {
                                                            L1Teleport.teleport(pc, 32993, 32716, (short)1002, 4, true);
                                                            break;
                                                        }
                                                        case '5': {
                                                            L1Teleport.teleport(pc, 32698, 32664, (short)1002, 6, true);
                                                            break;
                                                        }
                                                        case '6': {
                                                            L1Teleport.teleport(pc, 32710, 32759, (short)1002, 6, true);
                                                            break;
                                                        }
                                                        case '7': {
                                                            L1Teleport.teleport(pc, 32986, 32630, (short)1002, 4, true);
                                                            break;
                                                        }
                                                    }
                                                    htmlid = "";
                                                }
                                                else {
                                                    htmlid = "dsecret3";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 70077 || npctemp.get_npcId() == 81290) {
                                                int consumeItem = 0;
                                                int consumeItemCount = 0;
                                                int petNpcId = 0;
                                                int petItemId = 0;
                                                int upLv = 0;
                                                long lvExp = 0L;
                                                String msg3 = "";
                                                if (cmd.equalsIgnoreCase("buy 1")) {
                                                    petNpcId = 45042;
                                                    consumeItem = 40308;
                                                    consumeItemCount = 50000;
                                                    petItemId = 40314;
                                                    upLv = 5;
                                                    lvExp = ExpTable.getExpByLevel(upLv);
                                                    msg3 = "金幣";
                                                }
                                                else if (cmd.equalsIgnoreCase("buy 2")) {
                                                    petNpcId = 45034;
                                                    consumeItem = 40308;
                                                    consumeItemCount = 50000;
                                                    petItemId = 40314;
                                                    upLv = 5;
                                                    lvExp = ExpTable.getExpByLevel(upLv);
                                                    msg3 = "金幣";
                                                }
                                                else if (cmd.equalsIgnoreCase("buy 3")) {
                                                    petNpcId = 45046;
                                                    consumeItem = 40308;
                                                    consumeItemCount = 50000;
                                                    petItemId = 40314;
                                                    upLv = 5;
                                                    lvExp = ExpTable.getExpByLevel(upLv);
                                                    msg3 = "金幣";
                                                }
                                                else if (cmd.equalsIgnoreCase("buy 4")) {
                                                    petNpcId = 45047;
                                                    consumeItem = 40308;
                                                    consumeItemCount = 50000;
                                                    petItemId = 40314;
                                                    upLv = 5;
                                                    lvExp = ExpTable.getExpByLevel(upLv);
                                                    msg3 = "金幣";
                                                }
                                                else if (cmd.equalsIgnoreCase("buy 7")) {
                                                    petNpcId = 97023;
                                                    consumeItem = 42531;
                                                    consumeItemCount = 1;
                                                    petItemId = 40314;
                                                    upLv = 5;
                                                    lvExp = ExpTable.getExpByLevel(upLv);
                                                    msg3 = "淘氣幼龍蛋";
                                                }
                                                else if (cmd.equalsIgnoreCase("buy 8")) {
                                                    petNpcId = 97022;
                                                    consumeItem = 42513;
                                                    consumeItemCount = 1;
                                                    petItemId = 40314;
                                                    upLv = 5;
                                                    lvExp = ExpTable.getExpByLevel(upLv);
                                                    msg3 = "頑皮幼龍蛋";
                                                }
                                                if (petNpcId > 0) {
                                                    if (!pc.getInventory().checkItem(consumeItem, (long)consumeItemCount)) {
                                                        pc.sendPackets(new S_ServerMessage(337, msg3));
                                                    }
                                                    else if (pc.getInventory().getSize() > 170) {
                                                        pc.sendPackets(new S_ServerMessage(337, "身上空間"));
                                                    }
                                                    else if (pc.getInventory().checkItem(consumeItem, (long)consumeItemCount)) {
                                                        pc.getInventory().consumeItem(consumeItem, (long)consumeItemCount);
                                                        L1PcInventory inv = pc.getInventory();
                                                        L1ItemInstance petamu = inv.storeItem(petItemId, 1L);
                                                        if (petamu != null) {
                                                            PetReading.get().buyNewPet(petNpcId, petamu.getId() + 1, petamu.getId(), upLv, lvExp);
                                                            pc.sendPackets(new S_ItemName(petamu));
                                                            pc.sendPackets(new S_ServerMessage(403, petamu.getName()));
                                                        }
                                                    }
                                                }
                                                else {
                                                    pc.sendPackets(new S_SystemMessage("對話檔版本不符，請下載更新"));
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() >= 90327 && npctemp.get_npcId() <= 90337) {
                                                L1NpcInstance npc2 = (L1NpcInstance)obj;
                                                int pcX = 0;
                                                int pcY = 0;
                                                if (pc.getClanRank() != 10 && pc.getClanRank() != 4) {
                                                    pc.sendPackets(new S_ServerMessage(2498));
                                                    return;
                                                }
                                                else if (cmd.equals("0-5")) {
                                                    pcX = C_NPCAction._random.nextInt(6) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32730;
                                                    this.ShellDamage(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-6")) {
                                                    pcX = C_NPCAction._random.nextInt(8) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32698;
                                                    this.ShellDamage(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-7")) {
                                                    pcX = C_NPCAction._random.nextInt(6) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(6) + 32675;
                                                    this.ShellDamage(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-16")) {
                                                    pcX = C_NPCAction._random.nextInt(6) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32730;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-17")) {
                                                    pcX = C_NPCAction._random.nextInt(8) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32698;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-18")) {
                                                    pcX = C_NPCAction._random.nextInt(7) + 33626;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32704;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-19")) {
                                                    pcX = C_NPCAction._random.nextInt(7) + 33632;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32704;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-20")) {
                                                    pcX = C_NPCAction._random.nextInt(6) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(6) + 32675;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-10")) {
                                                    pcX = C_NPCAction._random.nextInt(6) + 33629;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32735;
                                                    this.ShellDamage(npc2, 12193, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-1")) {
                                                    pcX = C_NPCAction._random.nextInt(5) + 33106;
                                                    pcY = C_NPCAction._random.nextInt(5) + 32768;
                                                    this.ShellDamage(npc2, 12201, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-2")) {
                                                    pcX = C_NPCAction._random.nextInt(8) + 33164;
                                                    pcY = C_NPCAction._random.nextInt(9) + 32776;
                                                    this.ShellDamage(npc2, 12201, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-11")) {
                                                    pcX = C_NPCAction._random.nextInt(5) + 33106;
                                                    pcY = C_NPCAction._random.nextInt(5) + 32768;
                                                    this.ShellsSilence(npc2, 12201, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-12")) {
                                                    pcX = C_NPCAction._random.nextInt(5) + 33112;
                                                    pcY = C_NPCAction._random.nextInt(5) + 32768;
                                                    this.ShellsSilence(npc2, 12201, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-13")) {
                                                    pcX = C_NPCAction._random.nextInt(8) + 33164;
                                                    pcY = C_NPCAction._random.nextInt(9) + 32785;
                                                    this.ShellsSilence(npc2, 12201, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-8")) {
                                                    pcX = C_NPCAction._random.nextInt(5) + 33106;
                                                    pcY = C_NPCAction._random.nextInt(5) + 32768;
                                                    this.ShellDamage(npc2, 12197, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-3")) {
                                                    pcX = C_NPCAction._random.nextInt(7) + 32792;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32321;
                                                    this.ShellDamage(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-4")) {
                                                    pcX = C_NPCAction._random.nextInt(8) + 32794;
                                                    pcY = C_NPCAction._random.nextInt(8) + 32281;
                                                    this.ShellDamage(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-14")) {
                                                    pcX = C_NPCAction._random.nextInt(7) + 32792;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32321;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("1-15")) {
                                                    pcX = C_NPCAction._random.nextInt(8) + 32794;
                                                    pcY = C_NPCAction._random.nextInt(8) + 32281;
                                                    this.ShellsSilence(npc2, 12205, pcX, pcY, pc);
                                                }
                                                else if (cmd.equals("0-9")) {
                                                    pcX = C_NPCAction._random.nextInt(7) + 32792;
                                                    pcY = C_NPCAction._random.nextInt(4) + 32321;
                                                    this.ShellDamage(npc2, 12193, pcX, pcY, pc);
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 99021) {
                                                String npcName4 = npctemp.get_name();
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    long xcount = CreateNewItem.checkNewItem(pc, 85000, 108);
                                                    if (xcount == 1L) {
                                                        pc.getInventory().consumeItem(85000, 108L);
                                                        CreateNewItem.createNewItem_NPC(pc, npcName4, 85008, 1);
                                                        htmlid = "tw_takesi2";
                                                    }
                                                    else if (xcount > 1L) {
                                                        pc.sendPackets(new S_ItemCount(objid, (int)xcount, "a1"));
                                                    }
                                                    else if (xcount < 1L) {
                                                        htmlid = "tw_takesi3";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("e")) {
                                                    if (pc.getInventory().consumeItem(85000, 108L)) {
                                                        CreateNewItem.createNewItem_NPC(pc, npcName4, 85011, 1);
                                                        htmlid = "tw_takesi2";
                                                    }
                                                    else {
                                                        htmlid = "tw_takesi3";
                                                    }
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 99023) {
                                                String npcName4 = npctemp.get_name();
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().consumeItem(85001, 108L)) {
                                                        CreateNewItem.createNewItem_NPC(pc, npcName4, 85002, 1);
                                                        htmlid = "tw_sanojos1";
                                                    }
                                                    else {
                                                        htmlid = "tw_sanojof1";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().consumeItem(40308, 500000L)) {
                                                        CreateNewItem.createNewItem_NPC(pc, npcName4, 85003, 1);
                                                        htmlid = "tw_sanojos2";
                                                    }
                                                    else {
                                                        htmlid = "tw_sanojof2";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("c")) {
                                                    L1Teleport.teleport(pc, 32935, 32867, (short)8000, 5, true);
                                                    htmlid = "";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 99022) {
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().consumeItem(85007, 1L)) {
                                                        double addExp = ExpTable.calcPercentageExp(49, 5);
                                                        double penaltyrate = ExpTable.getPenaltyRate(pc.getLevel(), pc.getMeteLevel());
                                                        pc.addExp((long)(addExp * penaltyrate));
                                                        htmlid = "jp_noname1a";
                                                    }
                                                    else {
                                                        htmlid = "jp_noname1b";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    htmlid = "jp_noname1c";
                                                }
                                            }
                                            else if (npctemp.get_npcId() == 99026) {
                                                String npcName4 = npctemp.get_name();
                                                if (cmd.equalsIgnoreCase("a")) {
                                                    if (pc.getInventory().checkItem(85004)) {
                                                        L1ItemInstance item4 = pc.getInventory().findItemId(85004);
                                                        if (item4 != null) {
                                                            int adena = (int)(item4.getCount() * 1000L);
                                                            pc.getInventory().removeItem(item4);
                                                            CreateNewItem.createNewItem_NPC(pc, npcName4, 40308, adena);
                                                            htmlid = "jp_yakuro6";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "jp_yakuro5";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("b")) {
                                                    if (pc.getInventory().checkItem(85005)) {
                                                        L1ItemInstance item4 = pc.getInventory().findItemId(85005);
                                                        if (item4 != null) {
                                                            int adena = (int)(item4.getCount() * 10000L);
                                                            pc.getInventory().removeItem(item4);
                                                            CreateNewItem.createNewItem_NPC(pc, npcName4, 40308, adena);
                                                            htmlid = "jp_yakuro6";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "jp_yakuro5";
                                                    }
                                                }
                                                else if (cmd.equalsIgnoreCase("c")) {
                                                    if (pc.getInventory().checkItem(85006)) {
                                                        L1ItemInstance item4 = pc.getInventory().findItemId(85006);
                                                        if (item4 != null) {
                                                            int adena = (int)(item4.getCount() * 150000L);
                                                            pc.getInventory().removeItem(item4);
                                                            pc.getInventory().storeItem(40308, (long)adena);
                                                            CreateNewItem.createNewItem_NPC(pc, npcName4, 40308, adena);
                                                            htmlid = "jp_yakuro6";
                                                        }
                                                    }
                                                    else {
                                                        htmlid = "jp_yakuro5";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (htmlid != null && htmlid.equalsIgnoreCase("colos2")) {
                                        htmldata = this.makeUbInfoStrings(npctemp.get_npcId());
                                    }
                                    if (htmlid != null && htmlid.equalsIgnoreCase("colos2")) {
                                        htmldata = this.makeUbInfoStrings(npctemp.get_npcId());
                                    }
                                    if (createitem != null) {
                                        boolean isCreate = true;
                                        for (int j2 = 0; j2 < materials.length; ++j2) {
                                            if (!pc.getInventory().checkItemNotEquipped(materials[j2], (long)counts[j2])) {
                                                L1Item temp = ItemTable.get().getTemplate(materials[j2]);
                                                pc.sendPackets(new S_ServerMessage(337, temp.getNameId()));
                                                isCreate = false;
                                            }
                                        }
                                        if (isCreate) {
                                            int create_count = 0;
                                            int create_weight = 0;
                                            for (int k2 = 0; k2 < createitem.length; ++k2) {
                                                L1Item temp2 = ItemTable.get().getTemplate(createitem[k2]);
                                                if (temp2.isStackable()) {
                                                    if (!pc.getInventory().checkItem(createitem[k2])) {
                                                        ++create_count;
                                                    }
                                                }
                                                else {
                                                    create_count += createcount[k2];
                                                }
                                                create_weight += temp2.getWeight() * createcount[k2] / 1000;
                                            }
                                            if (pc.getInventory().getSize() + create_count > 170) {
                                                pc.sendPackets(new S_ServerMessage(263));
                                                return;
                                            }
                                            else if (pc.getMaxWeight() < (double)(pc.getInventory().getWeight() + create_weight)) {
                                                pc.sendPackets(new S_ServerMessage(82));
                                                return;
                                            }
                                            else {
                                                for (int j3 = 0; j3 < materials.length; ++j3) {
                                                    pc.getInventory().consumeItem(materials[j3], (long)counts[j3]);
                                                }
                                                for (int k2 = 0; k2 < createitem.length; ++k2) {
                                                    L1ItemInstance item6 = pc.getInventory().storeItem(createitem[k2], (long)createcount[k2]);
                                                    if (item6 != null) {
                                                        String itemName4 = ItemTable.get().getTemplate(createitem[k2]).getNameId();
                                                        String createrName = "";
                                                        if (obj instanceof L1NpcInstance) {
                                                            createrName = ((L1NpcInstance)obj).getNpcTemplate().get_name();
                                                        }
                                                        if (createcount[k2] > 1) {
                                                            pc.sendPackets(new S_ServerMessage(143, createrName, itemName4 + " (" + createcount[k2] + ")"));
                                                        }
                                                        else {
                                                            pc.sendPackets(new S_ServerMessage(143, createrName, itemName4));
                                                        }
                                                    }
                                                }
                                                if (success_htmlid != null) {
                                                    pc.sendPackets(new S_NPCTalkReturn(objid, success_htmlid, htmldata));
                                                }
                                            }
                                        }
                                        else if (failure_htmlid != null) {
                                            pc.sendPackets(new S_NPCTalkReturn(objid, failure_htmlid, htmldata));
                                        }
                                    }
                                    if (htmlid != null) {
                                        pc.sendPackets(new S_NPCTalkReturn(objid, htmlid, htmldata));
                                        return;
                                    }
                                    else {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        catch (Exception ex3) {}
        finally {
            this.over();
        }
    }
    
    private void ShowCraftList(L1PcInstance pc, L1NpcInstance npc) {
        String msg0 = "";
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
        String msg12 = "";
        String msg13 = "";
        String msg14 = "";
        String msg15 = "";
        String msg16 = "";
        String msg17 = "";
        String msg18 = "";
        String msg19 = "";
        String msg20 = "";
        String msg21 = "";
        String msg22 = "";
        String msg23 = "";
        String msg24 = "";
        String msg25 = "";
        String msg26 = "";
        String msg27 = "";
        String msg28 = "";
        String msg29 = "";
        String msg30 = "";
        String msg31 = "";
        String msg32 = "";
        String msg33 = "";
        String msg34 = "";
        String msg35 = "";
        String msg36 = "";
        String msg37 = "";
        String msg38 = "";
        String msg39 = "";
        String msg40 = "";
        String msg41 = "";
        int npcid = npc.getNpcId();
        Map<String, String> craftlist = L1BlendTable.getInstance().get_craftlist();
        if (!craftlist.isEmpty()) {
            msg0 = craftlist.get(String.valueOf(npcid) + "A");
            msg2 = craftlist.get(String.valueOf(npcid) + "B");
            msg3 = craftlist.get(String.valueOf(npcid) + "C");
            msg4 = craftlist.get(String.valueOf(npcid) + "D");
            msg5 = craftlist.get(String.valueOf(npcid) + "E");
            msg6 = craftlist.get(String.valueOf(npcid) + "F");
            msg7 = craftlist.get(String.valueOf(npcid) + "G");
            msg8 = craftlist.get(String.valueOf(npcid) + "H");
            msg9 = craftlist.get(String.valueOf(npcid) + "I");
            msg10 = craftlist.get(String.valueOf(npcid) + "J");
            msg11 = craftlist.get(String.valueOf(npcid) + "K");
            msg12 = craftlist.get(String.valueOf(npcid) + "L");
            msg13 = craftlist.get(String.valueOf(npcid) + "M");
            msg14 = craftlist.get(String.valueOf(npcid) + "N");
            msg15 = craftlist.get(String.valueOf(npcid) + "O");
            msg16 = craftlist.get(String.valueOf(npcid) + "P");
            msg17 = craftlist.get(String.valueOf(npcid) + "Q");
            msg18 = craftlist.get(String.valueOf(npcid) + "R");
            msg19 = craftlist.get(String.valueOf(npcid) + "S");
            msg20 = craftlist.get(String.valueOf(npcid) + "T");
            msg21 = craftlist.get(String.valueOf(npcid) + "U");
            msg22 = craftlist.get(String.valueOf(npcid) + "V");
            msg23 = craftlist.get(String.valueOf(npcid) + "W");
            msg24 = craftlist.get(String.valueOf(npcid) + "X");
            msg25 = craftlist.get(String.valueOf(npcid) + "Y");
            msg26 = craftlist.get(String.valueOf(npcid) + "Z");
            msg27 = craftlist.get(String.valueOf(npcid) + "a1");
            msg28 = craftlist.get(String.valueOf(npcid) + "a2");
            msg29 = craftlist.get(String.valueOf(npcid) + "a3");
            msg30 = craftlist.get(String.valueOf(npcid) + "a4");
            msg31 = craftlist.get(String.valueOf(npcid) + "a5");
            msg32 = craftlist.get(String.valueOf(npcid) + "a6");
            msg33 = craftlist.get(String.valueOf(npcid) + "a7");
            msg34 = craftlist.get(String.valueOf(npcid) + "a8");
            msg35 = craftlist.get(String.valueOf(npcid) + "a9");
            msg36 = craftlist.get(String.valueOf(npcid) + "a10");
            msg37 = craftlist.get(String.valueOf(npcid) + "a11");
            msg38 = craftlist.get(String.valueOf(npcid) + "a12");
            msg39 = craftlist.get(String.valueOf(npcid) + "a13");
            msg40 = craftlist.get(String.valueOf(npcid) + "a14");
            msg41 = craftlist.get(String.valueOf(npcid) + "a15");
        }
        String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23, msg24, msg25, msg26, msg27, msg28, msg29, msg30, msg31, msg32, msg33, msg34, msg35, msg36, msg37, msg38, msg39, msg40, msg41 };
        if (msg0 != null) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "smithitem1", msgs));
            return;
        }
        else {
            pc.sendPackets(new S_SystemMessage("沒有可以製作的道具。"));
            return;
        }
    }
    
    private String karmaLevelToHtmlId(int level) {
        if (level == 0 || level < -7 || 7 < level) {
            return "";
        }
        else {
            String htmlid = "";
            if (level > 0) {
                htmlid = "vbk" + level;
            }
            else if (level < 0) {
                htmlid = "vyk" + Math.abs(level);
            }
            return htmlid;
        }
    }
    
    private String watchUb(L1PcInstance pc, int npcId) {
        L1UltimateBattle ub = UBTable.getInstance().getUbForNpcId(npcId);
        L1Location loc = ub.getLocation();
        if (pc.getInventory().consumeItem(40308, 100L)) {
            try {
                pc.save();
                pc.beginGhost(loc.getX(), loc.getY(), (short)loc.getMapId(), true, 600);
            }
            catch (Exception e) {
                C_NPCAction._log.error(e.getLocalizedMessage(), e);
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(189));
        }
        return "";
    }
    
    private boolean isNpcSellOnly(L1NpcInstance npc) {
        int npcId = npc.getNpcTemplate().get_npcId();
        return npcId == 70027 || npcId == 70023;
    }
    
    private String enterUb(L1PcInstance pc, int npcId) {
        L1UltimateBattle ub = UBTable.getInstance().getUbForNpcId(npcId);
        if (!ub.isActive() || !ub.canPcEnter(pc)) {
            return "colos2";
        }
        else if (ub.isNowUb()) {
            return "colos1";
        }
        else if (ub.getMembersCount() >= ub.getMaxPlayer()) {
            return "colos4";
        }
        else {
            ub.addMember(pc);
            L1Location loc = ub.getLocation().randomLocation(10, false);
            L1Teleport.teleport(pc, loc.getX(), loc.getY(), ub.getMapId(), 5, true);
            return "";
        }
    }
    
    private String enterHauntedHouse(L1PcInstance pc) {
        if (L1HauntedHouse.getInstance().getHauntedHouseStatus() == 2) {
            pc.sendPackets(new S_ServerMessage(1182));
            return "";
        }
        else if (L1HauntedHouse.getInstance().getMembersCount() >= 10) {
            pc.sendPackets(new S_ServerMessage(1184));
            return "";
        }
        else {
            L1HauntedHouse.getInstance().addMember(pc);
            L1Teleport.teleport(pc, 32722, 32830, (short)5140, 2, true);
            return "";
        }
    }
    
    private String enterPetMatch(L1PcInstance pc, int objid2) {
        if (pc.getPetList().values().size() > 0) {
            pc.sendPackets(new S_ServerMessage(1187));
            return "";
        }
        else {
            if (!L1PetMatch.getInstance().enterPetMatch(pc, objid2)) {
                pc.sendPackets(new S_ServerMessage(1182));
            }
            return "";
        }
    }
    
    private void poly(ClientExecutor clientthread, int polyId) {
        L1PcInstance pc = clientthread.getActiveChar();
        int awakeSkillId = pc.getAwakeSkillId();
        if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
            pc.sendPackets(new S_ServerMessage(1384));
            return;
        }
        else {
            if (pc.getInventory().checkItem(40308, 100L)) {
                pc.getInventory().consumeItem(40308, 100L);
                L1PolyMorph.doPoly(pc, polyId, 1800, 4);
            }
            else {
                pc.sendPackets(new S_ServerMessage(337, "$4"));
            }
            return;
        }
    }
    
    private void polyByKeplisha(ClientExecutor clientthread, int polyId) {
        L1PcInstance pc = clientthread.getActiveChar();
        int awakeSkillId = pc.getAwakeSkillId();
        if (awakeSkillId == 185 || awakeSkillId == 190 || awakeSkillId == 195) {
            pc.sendPackets(new S_ServerMessage(1384));
            return;
        }
        else {
            if (pc.getInventory().checkItem(40308, 100L)) {
                pc.getInventory().consumeItem(40308, 100L);
                L1PolyMorph.doPoly(pc, polyId, 1800, 8);
            }
            else {
                pc.sendPackets(new S_ServerMessage(337, "$4"));
            }
            return;
        }
    }
    
    private String sellHouse(L1PcInstance pc, int objectId, int npcId) {
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        if (clan == null) {
            return "";
        }
        else {
            int houseId = clan.getHouseId();
            if (houseId == 0) {
                return "";
            }
            else {
                L1House house = HouseReading.get().getHouseTable(houseId);
                int keeperId = house.getKeeperId();
                if (npcId != keeperId) {
                    return "";
                }
                else if (!pc.isCrown()) {
                    pc.sendPackets(new S_ServerMessage(518));
                    return "";
                }
                else if (pc.getId() != clan.getLeaderId()) {
                    pc.sendPackets(new S_ServerMessage(518));
                    return "";
                }
                else if (house.isOnSale()) {
                    return "agonsale";
                }
                else {
                    pc.sendPackets(new S_SellHouse(objectId, String.valueOf(houseId)));
                    return null;
                }
            }
        }
    }
    
    private void openCloseDoor(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        if (clan != null) {
            int houseId = clan.getHouseId();
            if (houseId != 0) {
                L1House house = HouseReading.get().getHouseTable(houseId);
                int keeperId = house.getKeeperId();
                if (npc.getNpcTemplate().get_npcId() == keeperId) {
                    L1DoorInstance door1 = null;
                    L1DoorInstance door2 = null;
                    L1DoorInstance door3 = null;
                    L1DoorInstance door4 = null;
                    L1DoorInstance[] doorList;
                    for (int length = (doorList = DoorSpawnTable.get().getDoorList()).length, i = 0; i < length; ++i) {
                        L1DoorInstance door5 = doorList[i];
                        if (door5.getKeeperId() == keeperId) {
                            if (door1 == null) {
                                door1 = door5;
                            }
                            else if (door2 == null) {
                                door2 = door5;
                            }
                            else if (door3 == null) {
                                door3 = door5;
                            }
                            else if (door4 == null) {
                                door4 = door5;
                                break;
                            }
                        }
                    }
                    if (door1 != null) {
                        if (cmd.equalsIgnoreCase("open")) {
                            door1.open();
                        }
                        else if (cmd.equalsIgnoreCase("close")) {
                            door1.close();
                        }
                    }
                    if (door2 != null) {
                        if (cmd.equalsIgnoreCase("open")) {
                            door2.open();
                        }
                        else if (cmd.equalsIgnoreCase("close")) {
                            door2.close();
                        }
                    }
                    if (door3 != null) {
                        if (cmd.equalsIgnoreCase("open")) {
                            door3.open();
                        }
                        else if (cmd.equalsIgnoreCase("close")) {
                            door3.close();
                        }
                    }
                    if (door4 != null) {
                        if (cmd.equalsIgnoreCase("open")) {
                            door4.open();
                        }
                        else if (cmd.equalsIgnoreCase("close")) {
                            door4.close();
                        }
                    }
                }
            }
        }
    }
    
    private void openCloseGate(L1PcInstance pc, int keeperId, boolean isOpen) {
        boolean isNowWar = false;
        int pcCastleId = 0;
        if (pc.getClanid() != 0) {
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                pcCastleId = clan.getCastleId();
            }
        }
        if (keeperId == 70656 || keeperId == 70549 || keeperId == 70985) {
            if (this.isExistDefenseClan(1) && pcCastleId != 1) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(1);
            }
        }
        else if (keeperId == 70600) {
            if (this.isExistDefenseClan(2) && pcCastleId != 2) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(2);
            }
        }
        else if (keeperId == 70778 || keeperId == 70987 || keeperId == 70687) {
            if (this.isExistDefenseClan(3) && pcCastleId != 3) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(3);
            }
        }
        else if (keeperId == 70817 || keeperId == 70800 || keeperId == 70988 || keeperId == 70990 || keeperId == 70989 || keeperId == 70991) {
            if (this.isExistDefenseClan(4) && pcCastleId != 4) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(4);
            }
        }
        else if (keeperId == 70863 || keeperId == 70992 || keeperId == 70862) {
            if (this.isExistDefenseClan(5) && pcCastleId != 5) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(5);
            }
        }
        else if (keeperId == 70995 || keeperId == 70994 || keeperId == 70993) {
            if (this.isExistDefenseClan(6) && pcCastleId != 6) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(6);
            }
        }
        else if (keeperId == 70996) {
            if (this.isExistDefenseClan(7) && pcCastleId != 7) {
                return;
            }
            else {
                isNowWar = ServerWarExecutor.get().isNowWar(7);
            }
        }
        L1DoorInstance[] doorList;
        for (int length = (doorList = DoorSpawnTable.get().getDoorList()).length, i = 0; i < length; ++i) {
            L1DoorInstance door = doorList[i];
            if (door.getKeeperId() == keeperId && (!isNowWar || door.getMaxHp() <= 1)) {
                if (isOpen) {
                    door.open();
                }
                else {
                    door.close();
                }
            }
        }
    }
    
    private boolean isExistDefenseClan(int castleId) {
        boolean isExistDefenseClan = false;
        Collection<L1Clan> allClans = WorldClan.get().getAllClans();
        for (L1Clan clan : allClans) {
            if (castleId == clan.getCastleId()) {
                isExistDefenseClan = true;
                break;
            }
        }
        return isExistDefenseClan;
    }
    
    private void expelOtherClan(L1PcInstance clanPc, int keeperId) {
        int houseId = 0;
        Collection<L1House> houseList = (Collection)HouseReading.get().getHouseTableList().values();
        for (L1House house : houseList) {
            if (house.getKeeperId() == keeperId) {
                houseId = house.getHouseId();
            }
        }
        if (houseId == 0) {
            return;
        }
        else {
            int[] loc = new int[3];
            for (L1PcInstance pc : World.get().getAllPlayers()) {
                if (L1HouseLocation.isInHouseLoc(houseId, pc.getX(), pc.getY(), pc.getMapId()) && clanPc.getClanid() != pc.getClanid()) {
                    loc = L1HouseLocation.getHouseTeleportLoc(houseId, 0);
                    if (pc != null) {
                        L1Teleport.teleport(pc, loc[0], loc[1], (short)loc[2], 5, true);
                    }
                    else {
                        continue;
                    }
                }
            }
            return;
        }
    }
    
    private void repairGate(L1PcInstance pc) {
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        if (clan != null) {
            int castleId = clan.getCastleId();
            if (castleId != 0) {
                if (!ServerWarExecutor.get().isNowWar(castleId)) {
                    L1DoorInstance[] arrayOfL1DoorInstance;
                    for (int j = (arrayOfL1DoorInstance = DoorSpawnTable.get().getDoorList()).length, i = 0; i < j; ++i) {
                        L1DoorInstance door = arrayOfL1DoorInstance[i];
                        if (L1CastleLocation.checkInWarArea(castleId, door)) {
                            door.repairGate();
                        }
                    }
                    pc.sendPackets(new S_ServerMessage(990));
                }
                else {
                    pc.sendPackets(new S_ServerMessage(991));
                }
            }
        }
    }
    
    private void payFee(L1PcInstance pc, L1NpcInstance npc) {
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        if (clan != null) {
            int houseId = clan.getHouseId();
            if (houseId != 0) {
                L1House house = HouseReading.get().getHouseTable(houseId);
                int keeperId = house.getKeeperId();
                if (npc.getNpcTemplate().get_npcId() == keeperId) {
                    if (pc.getInventory().checkItem(40308, (long)ConfigAlt.HOUSE_TAX_ADENA)) {
                        pc.getInventory().consumeItem(40308, (long)ConfigAlt.HOUSE_TAX_ADENA);
                        TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
                        Calendar cal = Calendar.getInstance(tz);
                        cal.add(5, ConfigAlt.HOUSE_TAX_INTERVAL);
                        cal.set(12, 0);
                        cal.set(13, 0);
                        house.setTaxDeadline(cal);
                        HouseReading.get().updateHouse(house);
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(189));
                    }
                }
            }
        }
    }
    
    private String[] makeHouseTaxStrings(L1PcInstance pc, L1NpcInstance npc) {
        String name = npc.getNpcTemplate().get_name();
        String[] result = { name, "2000", "1", "1", "00" };
        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        if (clan != null) {
            int houseId = clan.getHouseId();
            if (houseId != 0) {
                L1House house = HouseReading.get().getHouseTable(houseId);
                int keeperId = house.getKeeperId();
                if (npc.getNpcTemplate().get_npcId() == keeperId) {
                    Calendar cal = house.getTaxDeadline();
                    int month = cal.get(2) + 1;
                    int day = cal.get(5);
                    int hour = cal.get(11);
                    result = new String[] { name, "2000", String.valueOf(month), String.valueOf(day), String.valueOf(hour) };
                }
            }
        }
        return result;
    }
    
    private String[] makeWarTimeStrings(int castleId) {
        L1Castle castle = CastleReading.get().getCastleTable(castleId);
        if (castle == null) {
            return null;
        }
        else {
            Calendar warTime = castle.getWarTime();
            int year = warTime.get(1);
            int month = warTime.get(2) + 1;
            int day = warTime.get(5);
            int hour = warTime.get(11);
            int minute = warTime.get(12);
            String[] result;
            if (castleId == 2) {
                result = new String[] { String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), String.valueOf(minute) };
            }
            else {
                result = new String[] { "", String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), String.valueOf(minute) };
            }
            return result;
        }
    }
    
    private String getYaheeAmulet(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        int[] amuletIdList = { 20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365 };
        int amuletId = 0;
        L1ItemInstance item = null;
        String htmlid = null;
        if (cmd.equalsIgnoreCase("1")) {
            if (pc.getKarma() >= -10000) {
                pc.sendPackets(new S_ServerMessage("好友度不足"));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20358)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[0];
            }
        }
        else if (cmd.equalsIgnoreCase("2")) {
            if (pc.getKarma() >= -20000) {
                pc.sendPackets(new S_ServerMessage("好友度不足20000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20359)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[1];
            }
        }
        else if (cmd.equalsIgnoreCase("3")) {
            if (pc.getKarma() >= -100000) {
                pc.sendPackets(new S_ServerMessage("好友度不足100000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20360)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[2];
            }
        }
        else if (cmd.equalsIgnoreCase("4")) {
            if (pc.getKarma() >= -500000) {
                pc.sendPackets(new S_ServerMessage("好友度不足500000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20361)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[3];
            }
        }
        else if (cmd.equalsIgnoreCase("5")) {
            if (pc.getKarma() >= -1500000) {
                pc.sendPackets(new S_ServerMessage("好友度不足1500000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20362)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[4];
            }
        }
        else if (cmd.equalsIgnoreCase("6")) {
            if (pc.getKarma() >= -3000000) {
                pc.sendPackets(new S_ServerMessage("好友度不足3000000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20363)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[5];
            }
        }
        else if (cmd.equalsIgnoreCase("7")) {
            if (pc.getKarma() >= -5000000) {
                pc.sendPackets(new S_ServerMessage("好友度不足5000000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20364)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[6];
            }
        }
        else if (cmd.equalsIgnoreCase("8")) {
            if (pc.getKarma() >= -10000000) {
                pc.sendPackets(new S_ServerMessage("好友度不足10000000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(20365)) {
                pc.sendPackets(new S_ServerMessage("身上已有該項鍊,無法再領取"));
                return htmlid;
            }
            else {
                amuletId = amuletIdList[7];
            }
        }
        if (amuletId != 0) {
            item = pc.getInventory().storeItem(amuletId, 1L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            int[] array;
            int length = (array = amuletIdList).length;
            int i = 0;
            while (i < length) {
                int id = array[i];
                if (id == amuletId) {
                    break;
                }
                else {
                    if (pc.getInventory().checkItem(id)) {
                        pc.getInventory().consumeItem(id, 1L);
                    }
                    ++i;
                }
            }
            htmlid = "";
        }
        return htmlid;
    }
    
    private String getBarlogEarring(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        int[] earringIdList = { 21020, 21021, 21022, 21023, 21024, 21025, 21026, 21027 };
        int earringId = 0;
        L1ItemInstance item = null;
        String htmlid = null;
        if (cmd.equalsIgnoreCase("1")) {
            if (pc.getKarma() < 10000) {
                pc.sendPackets(new S_ServerMessage("好友度不足10000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21020)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[0];
            }
        }
        else if (cmd.equalsIgnoreCase("2")) {
            if (pc.getKarma() < 20000) {
                pc.sendPackets(new S_ServerMessage("好友度不足20000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21021)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[1];
            }
        }
        else if (cmd.equalsIgnoreCase("3")) {
            if (pc.getKarma() < 100000) {
                pc.sendPackets(new S_ServerMessage("好友度不足100000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21022)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[2];
            }
        }
        else if (cmd.equalsIgnoreCase("4")) {
            if (pc.getKarma() < 500000) {
                pc.sendPackets(new S_ServerMessage("好友度不足500000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21023)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[3];
            }
        }
        else if (cmd.equalsIgnoreCase("5")) {
            if (pc.getKarma() < 1500000) {
                pc.sendPackets(new S_ServerMessage("好友度不足1500000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21024)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[4];
            }
        }
        else if (cmd.equalsIgnoreCase("6")) {
            if (pc.getKarma() < 3000000) {
                pc.sendPackets(new S_ServerMessage("好友度不足3000000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21025)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[5];
            }
        }
        else if (cmd.equalsIgnoreCase("7")) {
            if (pc.getKarma() < 5000000) {
                pc.sendPackets(new S_ServerMessage("好友度不足5000000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21026)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[6];
            }
        }
        else if (cmd.equalsIgnoreCase("8")) {
            if (pc.getKarma() < 10000000) {
                pc.sendPackets(new S_ServerMessage("好友度不足10000000."));
                return htmlid;
            }
            else if (pc.getInventory().checkItem(21027)) {
                pc.sendPackets(new S_ServerMessage("身上已有該耳環,無法再領取"));
                return htmlid;
            }
            else {
                earringId = earringIdList[7];
            }
        }
        if (earringId != 0) {
            item = pc.getInventory().storeItem(earringId, 1L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            int[] array;
            int length = (array = earringIdList).length;
            int i = 0;
            while (i < length) {
                int id = array[i];
                if (id == earringId) {
                    break;
                }
                else {
                    if (pc.getInventory().checkItem(id)) {
                        pc.getInventory().consumeItem(id, 1L);
                    }
                    ++i;
                }
            }
            htmlid = "";
        }
        return htmlid;
    }
    
    private String[] makeUbInfoStrings(int npcId) {
        L1UltimateBattle ub = UBTable.getInstance().getUbForNpcId(npcId);
        return ub.makeUbInfoStrings();
    }
    
    private String talkToDimensionDoor(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        String htmlid = "";
        int protectionId = 0;
        int sealId = 0;
        int locX = 0;
        int locY = 0;
        short mapId = 0;
        if (npc.getNpcTemplate().get_npcId() == 80059) {
            protectionId = 40909;
            sealId = 40913;
            locX = 32773;
            locY = 32835;
            mapId = 607;
        }
        else if (npc.getNpcTemplate().get_npcId() == 80060) {
            protectionId = 40912;
            sealId = 40916;
            locX = 32757;
            locY = 32842;
            mapId = 606;
        }
        else if (npc.getNpcTemplate().get_npcId() == 80061) {
            protectionId = 40910;
            sealId = 40914;
            locX = 32830;
            locY = 32822;
            mapId = 604;
        }
        else if (npc.getNpcTemplate().get_npcId() == 80062) {
            protectionId = 40911;
            sealId = 40915;
            locX = 32835;
            locY = 32822;
            mapId = 605;
        }
        if (cmd.equalsIgnoreCase("a")) {
            L1Teleport.teleport(pc, locX, locY, mapId, 5, true);
            htmlid = "";
        }
        else if (cmd.equalsIgnoreCase("b")) {
            L1ItemInstance item = pc.getInventory().storeItem(protectionId, 1L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            htmlid = "";
        }
        else if (cmd.equalsIgnoreCase("c")) {
            htmlid = "wpass07";
        }
        else if (cmd.equalsIgnoreCase("d")) {
            if (pc.getInventory().checkItem(sealId)) {
                L1ItemInstance item = pc.getInventory().findItemId(sealId);
                pc.getInventory().consumeItem(sealId, item.getCount());
            }
        }
        else if (cmd.equalsIgnoreCase("e")) {
            htmlid = "";
        }
        else if (cmd.equalsIgnoreCase("f")) {
            if (pc.getInventory().checkItem(protectionId)) {
                pc.getInventory().consumeItem(protectionId, 1L);
            }
            if (pc.getInventory().checkItem(sealId)) {
                L1ItemInstance item = pc.getInventory().findItemId(sealId);
                pc.getInventory().consumeItem(sealId, item.getCount());
            }
            htmlid = "";
        }
        return htmlid;
    }
    
    private void getBloodCrystalByKarma(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        L1ItemInstance item = null;
        if (cmd.equalsIgnoreCase("1")) {
            pc.addKarma((int)(500.0 * ConfigRate.RATE_KARMA));
            item = pc.getInventory().storeItem(40718, 1L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            pc.sendPackets(new S_ServerMessage(1081));
        }
        else if (cmd.equalsIgnoreCase("2")) {
            pc.addKarma((int)(5000.0 * ConfigRate.RATE_KARMA));
            item = pc.getInventory().storeItem(40718, 10L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            pc.sendPackets(new S_ServerMessage(1081));
        }
        else if (cmd.equalsIgnoreCase("3")) {
            pc.addKarma((int)(50000.0 * ConfigRate.RATE_KARMA));
            item = pc.getInventory().storeItem(40718, 100L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            pc.sendPackets(new S_ServerMessage(1081));
        }
    }
    
    private void getSoulCrystalByKarma(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        L1ItemInstance item = null;
        if (cmd.equalsIgnoreCase("1")) {
            pc.addKarma((int)(-500.0 * ConfigRate.RATE_KARMA));
            item = pc.getInventory().storeItem(40678, 1L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            pc.sendPackets(new S_ServerMessage(1080));
        }
        else if (cmd.equalsIgnoreCase("2")) {
            pc.addKarma((int)(-5000.0 * ConfigRate.RATE_KARMA));
            item = pc.getInventory().storeItem(40678, 10L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            pc.sendPackets(new S_ServerMessage(1080));
        }
        else if (cmd.equalsIgnoreCase("3")) {
            pc.addKarma((int)(-50000.0 * ConfigRate.RATE_KARMA));
            item = pc.getInventory().storeItem(40678, 100L);
            if (item != null) {
                pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
            }
            pc.sendPackets(new S_ServerMessage(1080));
        }
    }
    
    private void UbRank(L1PcInstance pc, L1NpcInstance npc) {
        L1UltimateBattle ub = UBTable.getInstance().getUbForNpcId(npc.getNpcTemplate().get_npcId());
        String[] htmldata = null;
        htmldata = new String[11];
        htmldata[0] = npc.getNpcTemplate().get_name();
        String htmlid = "colos3";
        int i = 1;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM ub_rank WHERE ub_id=? order by score desc limit 10");
            pstm.setInt(1, ub.getUbId());
            rs = pstm.executeQuery();
            while (rs.next()) {
                htmldata[i] = rs.getString(2) + " : " + String.valueOf(rs.getInt(3));
                ++i;
            }
        }
        catch (SQLException e) {
            C_NPCAction._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), htmlid, htmldata));
    }
    
    private void ShellDamage(L1NpcInstance npc, int effect, int x, int y, L1PcInstance pc) {
        long curtime = System.currentTimeMillis() / 1000L;
        if (npc.getShellsDamageTime() + 10L > curtime || npc.getShellsSilenceTime() + 10L > curtime) {
            pc.sendPackets(new S_ServerMessage(3680));
            return;
        }
        else if (!pc.getInventory().checkItem(82500, 1L)) {
            pc.sendPackets(new S_SystemMessage("投石器需要炸彈才能發射。 "));
            return;
        }
        else {
            pc.getInventory().consumeItem(82500, 1L);
            npc.setShellsDamageTime(curtime);
            Collection<L1PcInstance> list = null;
            list = World.get().getAllPlayers();
            for (L1PcInstance player : list) {
                if (L1CastleLocation.checkInAllWarArea(player.getX(), player.getY(), player.getMapId())) {
                    player.sendPackets(new S_EffectLocation(x, y, effect));
                    player.sendPackets(new S_DoActionGFX(npc.getId(), 1));
                    if (player.getX() >= x - 2 && player.getX() <= x + 2 && player.getY() >= y - 2 && player.getY() <= y + 2) {
                        player.receiveDamage(npc, 300.0, false, true);
                        player.sendPackets(new S_DoActionGFX(player.getId(), 2));
                        npc.broadcastPacketAll(new S_DoActionGFX(player.getId(), 2));
                    }
                    else {
                        continue;
                    }
                }
            }
            return;
        }
    }
    
    private static synchronized void giveitemnew(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        try {
            String AccountName = pc.getName();
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select id,道具編號,數量,玩家名稱,時間後領取,時間前領取,是否領取 from w_npc玩家時間領取 where 是否領取 = 0 and 玩家名稱 ='" + AccountName + "'");
            rs = pstm.executeQuery();
            boolean isfind = false;
            while (rs.next() && rs != null) {
                Timestamp now_time = new Timestamp(System.currentTimeMillis());
                int serial = rs.getInt("id");
                Timestamp end_time = rs.getTimestamp("時間後領取");
                Timestamp end_time2 = rs.getTimestamp("時間前領取");
                if (pc.getName().equalsIgnoreCase(rs.getString("玩家名稱")) && end_time != null && now_time.after(end_time)) {
                    isfind = true;
                    pstm2 = con.prepareStatement("update w_npc玩家時間領取 set 是否領取 = 1 where id = ?");
                    pstm2.setInt(1, serial);
                    pstm2.execute();
                    int itemid = rs.getInt("道具編號");
                    int count = rs.getInt("數量");
                    pc.getInventory().storeItem(itemid, (long)count);
                    L1ItemInstance item = ItemTable.get().createItem(itemid);
                    pc.sendPackets(new S_ServerMessage("\\fW獲得:" + item.getName() + "(" + count + ")"));
                }
                else if (pc.getName().equalsIgnoreCase(rs.getString("玩家名稱")) && end_time2 != null && now_time.before(end_time2)) {
                    isfind = true;
                    pstm2 = con.prepareStatement("update w_npc玩家時間領取 set 是否領取 = 1 where id = ?");
                    pstm2.setInt(1, serial);
                    pstm2.execute();
                    int itemid = rs.getInt("道具編號");
                    int count = rs.getInt("數量");
                    pc.getInventory().storeItem(itemid, (long)count);
                    L1ItemInstance item = ItemTable.get().createItem(itemid);
                    pc.sendPackets(new S_ServerMessage("\\fW獲得:" + item.getName() + "(" + count + ")"));
                }
                else {
                    continue;
                }
            }
            if (!isfind) {
                pc.sendPackets(new S_ServerMessage("\\aD「系統」尚未偵測到您有物品未領"));
            }
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
    }
    
    private static synchronized void checkSponsor1(L1PcInstance pc) {
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
                    PayBonus.getItem(pc, count);
                    GiveItem1(pc, count, count);
                }
            }
            if (!isfind) {
                pc.sendPackets(new S_ServerMessage("\\aD「系統」尚未偵測到您有贊助"));
            }
        }
        catch (SQLException e) {
            C_NPCAction._log.error(e.getLocalizedMessage());
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
    }
    
    private static synchronized void checkSponsor2(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        try {
            String AccountName = pc.getAccountName();
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select ID,amount,RtnCode,payname,state from ezpay where state = 0 and payname ='" + AccountName + "'");
            rs = pstm.executeQuery();
            boolean isfind = false;
            while (rs.next() && rs != null) {
                int serial = rs.getInt("ID");
                int count = rs.getInt("amount");
                int RtnCode = rs.getInt("RtnCode");
                if (count != 0 && RtnCode == 1 && pc.getAccountName().equalsIgnoreCase(rs.getString("payname"))) {
                    isfind = true;
                    pstm2 = con.prepareStatement("update ezpay set state = 1 where ID = ?");
                    pstm2.setInt(1, serial);
                    pstm2.execute();
                    GiveItem1(pc, count, count);
                    PayBonus.getItem(pc, count);
                }
            }
            if (!isfind) {
                pc.sendPackets(new S_ServerMessage("\\aD「系統」尚未偵測到您有贊助"));
            }
        }
        catch (SQLException ex) {}
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
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
            pstm = con.prepareStatement("select ID,amount,RtnCode,payname,state from ezpay where state = 0 and payname ='" + AccountName + "'");
            rs = pstm.executeQuery();
            boolean isfind = false;
            while (rs.next() && rs != null) {
                int serial = rs.getInt("ID");
                int count = rs.getInt("amount");
                int RtnCode = rs.getInt("RtnCode");
                if (count != 0 && RtnCode == 1 && pc.getAccountName().equalsIgnoreCase(rs.getString("payname"))) {
                    isfind = true;
                    pstm2 = con.prepareStatement("update ezpay set state = 1 where ID = ?");
                    pstm2.setInt(1, serial);
                    pstm2.execute();
                    GiveItem1(pc, count, count);
                    PayBonus.getItem(pc, count);
                }
            }
            if (!isfind) {
                pc.sendPackets(new S_ServerMessage("\\aD「系統」尚未偵測到您有贊助"));
            }
        }
        catch (SQLException ex) {}
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
            pc.sendPackets(new S_ServerMessage("已獲得 " + item.getLogName() + "。"));
        }
    }
    
    public static void writeSponsorlog(L1PcInstance player, int count) {
        try {
            File DeleteLog = new File("全紀錄\\領取贊助幣紀錄.log");
            if (DeleteLog.createNewFile()) {
                (C_NPCAction.out = new BufferedWriter(new FileWriter("全紀錄\\領取贊助幣紀錄.log", false))).write("※以下是玩家[領取贊助]的所有紀錄※\r\n");
                C_NPCAction.out.close();
            }
            (C_NPCAction.out = new BufferedWriter(new FileWriter("全紀錄\\領取贊助幣紀錄.log", true))).write("\r\n");
            C_NPCAction.out.write("來自帳號: " + player.getAccountName() + "來自ip: " + player.getNetConnection().getIp() + ",來自玩家: " + player.getName() + ",領取了: " + count + " 個 " + ",<領取時間:" + new Timestamp(System.currentTimeMillis()) + ">" + "\r\n");
            C_NPCAction.out.close();
        }
        catch (IOException e) {
            System.out.println("以下是錯誤訊息: " + e.getMessage());
        }
    }
    
    public static void GiveItem1(L1PcInstance pc, int count, int money) {
        L1Account account = pc.getNetConnection().getAccount();
        Ezpay_bz.getItem(pc, count);
        pc.setfirst_pay(account.get_first_pay());
        if (account.get_first_pay() == 0) {
            ezpayfirst.getItem(pc, (long)money);
        }
        account.set_first_pay(account.get_first_pay() + money);
        AccountReading.get().updatefp(pc.getAccountName(), account.get_first_pay());
        pc.setpcezpay(account.get_first_pay() + money);
        ezpay.getItem(pc, (long)account.get_first_pay());
    }
    
    private void ShellsSilence(L1NpcInstance npc, int effect, int x, int y, L1PcInstance pc) {
        long curtime = System.currentTimeMillis() / 1000L;
        if (npc.getShellsDamageTime() + 10L > curtime || npc.getShellsSilenceTime() + 10L > curtime) {
            pc.sendPackets(new S_ServerMessage(3680));
            return;
        }
        else if (!pc.getInventory().checkItem(82500, 1L)) {
            pc.sendPackets(new S_SystemMessage("投石器需要炸彈才能發射。 "));
            return;
        }
        else {
            pc.getInventory().consumeItem(82500, 1L);
            npc.setShellsSilenceTime(curtime);
            Collection<L1PcInstance> list = null;
            list = World.get().getAllPlayers();
            for (L1PcInstance player : list) {
                if (L1CastleLocation.checkInAllWarArea(player.getX(), player.getY(), player.getMapId())) {
                    player.sendPackets(new S_EffectLocation(x, y, effect));
                    player.sendPackets(new S_DoActionGFX(npc.getId(), 1));
                    if (player.getX() >= x - 2 && player.getX() <= x + 2 && player.getY() >= y - 2 && player.getY() <= y + 2) {
                        player.killSkillEffectTimer(64);
                        player.setSkillEffect(64, 15000);
                        player.sendPacketsAll(new S_SkillSound(player.getId(), 2177));
                        npc.broadcastPacketAll(new S_SkillSound(player.getId(), 2177));
                    }
                    else {
                        continue;
                    }
                }
            }
            return;
        }
    }
    
    private static synchronized void checkSponsor3(L1PcInstance pc) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PreparedStatement pstm2 = null;
        try {
            String AccountName = pc.getAccountName();
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select ordernumber,amount,r_count,p_id,payname,state from ezpay where state = 1 and payname ='" + AccountName + "'");
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
                    int money = rs.getInt("r_count");
                    int itemid = rs.getInt("p_id");
                    GiveItem3(pc, itemid, count, money);
                    PayBonus.getItem(pc, count);
                }
            }
            if (!isfind) {
                pc.sendPackets(new S_ServerMessage("\\fT「系統」尚未偵測到您有贊助"));
            }
        }
        catch (SQLException e) {
            C_NPCAction._log.error(e.getLocalizedMessage());
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(pstm2);
            SQLUtil.close(con);
        }
    }
    
    public static void GiveItem3(L1PcInstance pc, int itemId, int count, int money) {
        L1Account account = pc.getNetConnection().getAccount();
        L1ItemInstance item = ItemTable.get().createItem(itemId);
        item.setCount((long)count);
        if (pc.getInventory().checkAddItem(item, (long)count) == 0) {
            pc.setfirst_pay(account.get_first_pay());
            pc.getInventory().storeItem(item);
            if (account.get_first_pay() == 0) {
                ezpayfirst.getItem(pc, (long)money);
            }
            account.set_first_pay(account.get_first_pay() + money);
            AccountReading.get().updatefp(pc.getAccountName(), account.get_first_pay());
            pc.setpcezpay(account.get_first_pay() + money);
            ezpay.getItem(pc, (long)account.get_first_pay());
            pc.sendPackets(new S_ServerMessage("感謝贊助.獲得 " + item.getLogName()));
        }
    }
    
    private void ShowCraftList1(L1PcInstance pc, L1NpcInstance npc) {
        String msg0 = "";
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
        String msg12 = "";
        String msg13 = "";
        String msg14 = "";
        String msg15 = "";
        String msg16 = "";
        String msg17 = "";
        String msg18 = "";
        String msg19 = "";
        String msg20 = "";
        String msg21 = "";
        String msg22 = "";
        String msg23 = "";
        String msg24 = "";
        String msg25 = "";
        String msg26 = "";
        String msg27 = "";
        String msg28 = "";
        String msg29 = "";
        String msg30 = "";
        String msg31 = "";
        String msg32 = "";
        String msg33 = "";
        String msg34 = "";
        String msg35 = "";
        String msg36 = "";
        String msg37 = "";
        String msg38 = "";
        String msg39 = "";
        String msg40 = "";
        String msg41 = "";
        int npcid = npc.getNpcId();
        Map<String, String> craftlist = L1BlendTable_1.getInstance().get_craftlist();
        if (!craftlist.isEmpty()) {
            msg0 = craftlist.get(String.valueOf(npcid) + "A");
            msg2 = craftlist.get(String.valueOf(npcid) + "B");
            msg3 = craftlist.get(String.valueOf(npcid) + "C");
            msg4 = craftlist.get(String.valueOf(npcid) + "D");
            msg5 = craftlist.get(String.valueOf(npcid) + "E");
            msg6 = craftlist.get(String.valueOf(npcid) + "F");
            msg7 = craftlist.get(String.valueOf(npcid) + "G");
            msg8 = craftlist.get(String.valueOf(npcid) + "H");
            msg9 = craftlist.get(String.valueOf(npcid) + "I");
            msg10 = craftlist.get(String.valueOf(npcid) + "J");
            msg11 = craftlist.get(String.valueOf(npcid) + "K");
            msg12 = craftlist.get(String.valueOf(npcid) + "L");
            msg13 = craftlist.get(String.valueOf(npcid) + "M");
            msg14 = craftlist.get(String.valueOf(npcid) + "N");
            msg15 = craftlist.get(String.valueOf(npcid) + "O");
            msg16 = craftlist.get(String.valueOf(npcid) + "P");
            msg17 = craftlist.get(String.valueOf(npcid) + "Q");
            msg18 = craftlist.get(String.valueOf(npcid) + "R");
            msg19 = craftlist.get(String.valueOf(npcid) + "S");
            msg20 = craftlist.get(String.valueOf(npcid) + "T");
            msg21 = craftlist.get(String.valueOf(npcid) + "U");
            msg22 = craftlist.get(String.valueOf(npcid) + "V");
            msg23 = craftlist.get(String.valueOf(npcid) + "W");
            msg24 = craftlist.get(String.valueOf(npcid) + "X");
            msg25 = craftlist.get(String.valueOf(npcid) + "Y");
            msg26 = craftlist.get(String.valueOf(npcid) + "Z");
            msg27 = craftlist.get(String.valueOf(npcid) + "a1");
            msg28 = craftlist.get(String.valueOf(npcid) + "a2");
            msg29 = craftlist.get(String.valueOf(npcid) + "a3");
            msg30 = craftlist.get(String.valueOf(npcid) + "a4");
            msg31 = craftlist.get(String.valueOf(npcid) + "a5");
            msg32 = craftlist.get(String.valueOf(npcid) + "a6");
            msg33 = craftlist.get(String.valueOf(npcid) + "a7");
            msg34 = craftlist.get(String.valueOf(npcid) + "a8");
            msg35 = craftlist.get(String.valueOf(npcid) + "a9");
            msg36 = craftlist.get(String.valueOf(npcid) + "a10");
            msg37 = craftlist.get(String.valueOf(npcid) + "a11");
            msg38 = craftlist.get(String.valueOf(npcid) + "a12");
            msg39 = craftlist.get(String.valueOf(npcid) + "a13");
            msg40 = craftlist.get(String.valueOf(npcid) + "a14");
            msg41 = craftlist.get(String.valueOf(npcid) + "a15");
        }
        String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23, msg24, msg25, msg26, msg27, msg28, msg29, msg30, msg31, msg32, msg33, msg34, msg35, msg36, msg37, msg38, msg39, msg40, msg41 };
        if (msg0 != null) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "smithitem2", msgs));
            return;
        }
        else {
            pc.sendPackets(new S_SystemMessage("沒有可以製作的道具。"));
            return;
        }
    }
    
    private void Gqpc(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String type1 = "[未解]";
            String type2 = "[未解]";
            String type3 = "[未解]";
            String type4 = "[未解]";
            String type5 = "[未解]";
            String type6 = "[未解]";
            String type7 = "[未解]";
            String type8 = "[未解]";
            String type9 = "[未解]";
            String type10 = "[未解]";
            String type11 = "[未解]";
            String type12 = "[未解]";
            String type13 = "[未解]";
            String type14 = "[未解]";
            String type15 = "[未解]";
            String type16 = "[未解]";
            String type17 = "[未解]";
            String type18 = "[未解]";
            String type19 = "[未解]";
            String type20 = "[未解]";
            String type21 = "[未解]";
            String type22 = "[未解]";
            String type23 = "[未解]";
            String type24 = "[未解]";
            String type25 = "[未解]";
            String type26 = "[未解]";
            String type27 = "[未解]";
            String type28 = "[未解]";
            String type29 = "[未解]";
            String type30 = "[未解]";
            String type31 = "[未解]";
            String type32 = "[未解]";
            String type33 = "[未解]";
            String type34 = "[未解]";
            String type35 = "[未解]";
            String type36 = "[未解]";
            String type37 = "[未解]";
            String type38 = "[未解]";
            String type39 = "[未解]";
            String type40 = "[未解]";
            String type41 = "[未解]";
            String type42 = "[未解]";
            String type43 = "[未解]";
            String type44 = "[未解]";
            String type45 = "[未解]";
            String type46 = "[未解]";
            String type47 = "[未解]";
            String type48 = "[未解]";
            String type49 = "[未解]";
            String type50 = "[未解]";
            String type51 = "[未解]";
            String type52 = "[未解]";
            String type53 = "[未解]";
            String type54 = "[未解]";
            String type55 = "[未解]";
            String type56 = "[未解]";
            String type57 = "[未解]";
            String type58 = "[未解]";
            String type59 = "[未解]";
            String type60 = "[未解]";
            String type61 = "[未解]";
            String type62 = "[未解]";
            String type63 = "[未解]";
            String type64 = "[未解]";
            String type65 = "[未解]";
            String type66 = "[未解]";
            String type67 = "[未解]";
            String type68 = "[未解]";
            String type69 = "[未解]";
            String type70 = "[未解]";
            String type71 = "[未解]";
            String type72 = "[未解]";
            String type73 = "[未解]";
            String type74 = "[未解]";
            String type75 = "[未解]";
            String type76 = "[未解]";
            String type77 = "[未解]";
            String type78 = "[未解]";
            String type79 = "[未解]";
            String type80 = "[未解]";
            String type81 = "[未解]";
            String type82 = "[未解]";
            String type83 = "[未解]";
            String type84 = "[未解]";
            String type85 = "[未解]";
            String type86 = "[未解]";
            String type87 = "[未解]";
            String type88 = "[未解]";
            String type89 = "[未解]";
            String type90 = "[未解]";
            String type91 = "[未解]";
            String type92 = "[未解]";
            String type93 = "[未解]";
            String type94 = "[未解]";
            String type95 = "[未解]";
            String type96 = "[未解]";
            String type97 = "[未解]";
            String type98 = "[未解]";
            String type99 = "[未解]";
            String type100 = "[未解]";
            String type101 = "[未解]";
            String type102 = "[未解]";
            String type103 = "[未解]";
            String type104 = "[未解]";
            String type105 = "[未解]";
            String type106 = "[未解]";
            String type107 = "[未解]";
            String type108 = "[未解]";
            String type109 = "[未解]";
            String type110 = "[未解]";
            String type111 = "[未解]";
            String type112 = "[未解]";
            String type113 = "[未解]";
            String type114 = "[未解]";
            String type115 = "[未解]";
            String type116 = "[未解]";
            String type117 = "[未解]";
            String type118 = "[未解]";
            String type119 = "[未解]";
            String type120 = "[未解]";
            String type121 = "[未解]";
            String type122 = "[未解]";
            String type123 = "[未解]";
            String type124 = "[未解]";
            String type125 = "[未解]";
            String type126 = "[未解]";
            String type127 = "[未解]";
            String type128 = "[未解]";
            String type129 = "[未解]";
            String type130 = "[未解]";
            String type131 = "[未解]";
            String type132 = "[未解]";
            String type133 = "[未解]";
            String type134 = "[未解]";
            String type135 = "[未解]";
            String type136 = "[未解]";
            String type137 = "[未解]";
            String type138 = "[未解]";
            String type139 = "[未解]";
            String type140 = "[未解]";
            String type141 = "[未解]";
            String type142 = "[未解]";
            String type143 = "[未解]";
            String type144 = "[未解]";
            String type145 = "[未解]";
            String type146 = "[未解]";
            String type147 = "[未解]";
            String type148 = "[未解]";
            String type149 = "[未解]";
            String type150 = "[未解]";
            String type151 = "[未解]";
            String type152 = "[未解]";
            String type153 = "[未解]";
            String type154 = "[未解]";
            String type155 = "[未解]";
            String type156 = "[未解]";
            String type157 = "[未解]";
            String type158 = "[未解]";
            String type159 = "[未解]";
            String type160 = "[未解]";
            String type161 = "[未解]";
            String type162 = "[未解]";
            String type163 = "[未解]";
            String type164 = "[未解]";
            String type165 = "[未解]";
            String type166 = "[未解]";
            String type167 = "[未解]";
            String type168 = "[未解]";
            String type169 = "[未解]";
            String type170 = "[未解]";
            String type171 = "[未解]";
            String type172 = "[未解]";
            String type173 = "[未解]";
            String type174 = "[未解]";
            String type175 = "[未解]";
            String type176 = "[未解]";
            String type177 = "[未解]";
            String type178 = "[未解]";
            String type179 = "[未解]";
            String type180 = "[未解]";
            String type181 = "[未解]";
            String type182 = "[未解]";
            String type183 = "[未解]";
            String type184 = "[未解]";
            String type185 = "[未解]";
            String type186 = "[未解]";
            String type187 = "[未解]";
            String type188 = "[未解]";
            String type189 = "[未解]";
            String type190 = "[未解]";
            String type191 = "[未解]";
            String type192 = "[未解]";
            String type193 = "[未解]";
            String type194 = "[未解]";
            String type195 = "[未解]";
            String type196 = "[未解]";
            String type197 = "[未解]";
            String type198 = "[未解]";
            String type199 = "[未解]";
            String type200 = "[未解]";
            if (pc.getQuest().get_step(340000) == 2) {
                type1 = "[已完成]";
            }
            if (pc.getQuest().get_step(340001) == 2) {
                type2 = "[已完成]";
            }
            if (pc.getQuest().get_step(340002) == 2) {
                type3 = "[已完成]";
            }
            if (pc.getQuest().get_step(340003) == 2) {
                type4 = "[已完成]";
            }
            if (pc.getQuest().get_step(340004) == 2) {
                type5 = "[已完成]";
            }
            if (pc.getQuest().get_step(340005) == 2) {
                type6 = "[已完成]";
            }
            if (pc.getQuest().get_step(340006) == 2) {
                type7 = "[已完成]";
            }
            if (pc.getQuest().get_step(340007) == 2) {
                type8 = "[已完成]";
            }
            if (pc.getQuest().get_step(340008) == 2) {
                type9 = "[已完成]";
            }
            if (pc.getQuest().get_step(340009) == 2) {
                type10 = "[已完成]";
            }
            if (pc.getQuest().get_step(340010) == 2) {
                type11 = "[已完成]";
            }
            if (pc.getQuest().get_step(340011) == 2) {
                type12 = "[已完成]";
            }
            if (pc.getQuest().get_step(340012) == 2) {
                type13 = "[已完成]";
            }
            if (pc.getQuest().get_step(340013) == 2) {
                type14 = "[已完成]";
            }
            if (pc.getQuest().get_step(340014) == 2) {
                type15 = "[已完成]";
            }
            if (pc.getQuest().get_step(340015) == 2) {
                type16 = "[已完成]";
            }
            if (pc.getQuest().get_step(340016) == 2) {
                type17 = "[已完成]";
            }
            if (pc.getQuest().get_step(340017) == 2) {
                type18 = "[已完成]";
            }
            if (pc.getQuest().get_step(340018) == 2) {
                type19 = "[已完成]";
            }
            if (pc.getQuest().get_step(340019) == 2) {
                type20 = "[已完成]";
            }
            if (pc.getQuest().get_step(340020) == 2) {
                type21 = "[已完成]";
            }
            if (pc.getQuest().get_step(340021) == 2) {
                type22 = "[已完成]";
            }
            if (pc.getQuest().get_step(340022) == 2) {
                type23 = "[已完成]";
            }
            if (pc.getQuest().get_step(340023) == 2) {
                type24 = "[已完成]";
            }
            if (pc.getQuest().get_step(340024) == 2) {
                type25 = "[已完成]";
            }
            if (pc.getQuest().get_step(340025) == 2) {
                type26 = "[已完成]";
            }
            if (pc.getQuest().get_step(340026) == 2) {
                type27 = "[已完成]";
            }
            if (pc.getQuest().get_step(340027) == 2) {
                type28 = "[已完成]";
            }
            if (pc.getQuest().get_step(340028) == 2) {
                type29 = "[已完成]";
            }
            if (pc.getQuest().get_step(340029) == 2) {
                type30 = "[已完成]";
            }
            if (pc.getQuest().get_step(340030) == 2) {
                type31 = "[已完成]";
            }
            if (pc.getQuest().get_step(340031) == 2) {
                type32 = "[已完成]";
            }
            if (pc.getQuest().get_step(340032) == 2) {
                type33 = "[已完成]";
            }
            if (pc.getQuest().get_step(340033) == 2) {
                type34 = "[已完成]";
            }
            if (pc.getQuest().get_step(340034) == 2) {
                type35 = "[已完成]";
            }
            if (pc.getQuest().get_step(340035) == 2) {
                type36 = "[已完成]";
            }
            if (pc.getQuest().get_step(340036) == 2) {
                type37 = "[已完成]";
            }
            if (pc.getQuest().get_step(340037) == 2) {
                type38 = "[已完成]";
            }
            if (pc.getQuest().get_step(340038) == 2) {
                type39 = "[已完成]";
            }
            if (pc.getQuest().get_step(340039) == 2) {
                type40 = "[已完成]";
            }
            if (pc.getQuest().get_step(340040) == 2) {
                type41 = "[已完成]";
            }
            if (pc.getQuest().get_step(340041) == 2) {
                type42 = "[已完成]";
            }
            if (pc.getQuest().get_step(340042) == 2) {
                type43 = "[已完成]";
            }
            if (pc.getQuest().get_step(340043) == 2) {
                type44 = "[已完成]";
            }
            if (pc.getQuest().get_step(340044) == 2) {
                type45 = "[已完成]";
            }
            if (pc.getQuest().get_step(340045) == 2) {
                type46 = "[已完成]";
            }
            if (pc.getQuest().get_step(340046) == 2) {
                type47 = "[已完成]";
            }
            if (pc.getQuest().get_step(340047) == 2) {
                type48 = "[已完成]";
            }
            if (pc.getQuest().get_step(340048) == 2) {
                type49 = "[已完成]";
            }
            if (pc.getQuest().get_step(340049) == 2) {
                type50 = "[已完成]";
            }
            if (pc.getQuest().get_step(340050) == 2) {
                type51 = "[已完成]";
            }
            if (pc.getQuest().get_step(340051) == 2) {
                type52 = "[已完成]";
            }
            if (pc.getQuest().get_step(340052) == 2) {
                type53 = "[已完成]";
            }
            if (pc.getQuest().get_step(340053) == 2) {
                type54 = "[已完成]";
            }
            if (pc.getQuest().get_step(340054) == 2) {
                type55 = "[已完成]";
            }
            if (pc.getQuest().get_step(340055) == 2) {
                type56 = "[已完成]";
            }
            if (pc.getQuest().get_step(340056) == 2) {
                type57 = "[已完成]";
            }
            if (pc.getQuest().get_step(340057) == 2) {
                type58 = "[已完成]";
            }
            if (pc.getQuest().get_step(340058) == 2) {
                type59 = "[已完成]";
            }
            if (pc.getQuest().get_step(340059) == 2) {
                type60 = "[已完成]";
            }
            if (pc.getQuest().get_step(340060) == 2) {
                type61 = "[已完成]";
            }
            if (pc.getQuest().get_step(340061) == 2) {
                type62 = "[已完成]";
            }
            if (pc.getQuest().get_step(340062) == 2) {
                type63 = "[已完成]";
            }
            if (pc.getQuest().get_step(340063) == 2) {
                type64 = "[已完成]";
            }
            if (pc.getQuest().get_step(340064) == 2) {
                type65 = "[已完成]";
            }
            if (pc.getQuest().get_step(340065) == 2) {
                type66 = "[已完成]";
            }
            if (pc.getQuest().get_step(340066) == 2) {
                type67 = "[已完成]";
            }
            if (pc.getQuest().get_step(340067) == 2) {
                type68 = "[已完成]";
            }
            if (pc.getQuest().get_step(340068) == 2) {
                type69 = "[已完成]";
            }
            if (pc.getQuest().get_step(340069) == 2) {
                type70 = "[已完成]";
            }
            if (pc.getQuest().get_step(340070) == 2) {
                type71 = "[已完成]";
            }
            if (pc.getQuest().get_step(340071) == 2) {
                type72 = "[已完成]";
            }
            if (pc.getQuest().get_step(340072) == 2) {
                type73 = "[已完成]";
            }
            if (pc.getQuest().get_step(340073) == 2) {
                type74 = "[已完成]";
            }
            if (pc.getQuest().get_step(340074) == 2) {
                type75 = "[已完成]";
            }
            if (pc.getQuest().get_step(340075) == 2) {
                type76 = "[已完成]";
            }
            if (pc.getQuest().get_step(340076) == 2) {
                type77 = "[已完成]";
            }
            if (pc.getQuest().get_step(340077) == 2) {
                type78 = "[已完成]";
            }
            if (pc.getQuest().get_step(340078) == 2) {
                type79 = "[已完成]";
            }
            if (pc.getQuest().get_step(340079) == 2) {
                type80 = "[已完成]";
            }
            if (pc.getQuest().get_step(340080) == 2) {
                type81 = "[已完成]";
            }
            if (pc.getQuest().get_step(340081) == 2) {
                type82 = "[已完成]";
            }
            if (pc.getQuest().get_step(340082) == 2) {
                type83 = "[已完成]";
            }
            if (pc.getQuest().get_step(340083) == 2) {
                type84 = "[已完成]";
            }
            if (pc.getQuest().get_step(340084) == 2) {
                type85 = "[已完成]";
            }
            if (pc.getQuest().get_step(340085) == 2) {
                type86 = "[已完成]";
            }
            if (pc.getQuest().get_step(340086) == 2) {
                type87 = "[已完成]";
            }
            if (pc.getQuest().get_step(340087) == 2) {
                type88 = "[已完成]";
            }
            if (pc.getQuest().get_step(340088) == 2) {
                type89 = "[已完成]";
            }
            if (pc.getQuest().get_step(340089) == 2) {
                type90 = "[已完成]";
            }
            if (pc.getQuest().get_step(340090) == 2) {
                type91 = "[已完成]";
            }
            if (pc.getQuest().get_step(340091) == 2) {
                type92 = "[已完成]";
            }
            if (pc.getQuest().get_step(340092) == 2) {
                type93 = "[已完成]";
            }
            if (pc.getQuest().get_step(340093) == 2) {
                type94 = "[已完成]";
            }
            if (pc.getQuest().get_step(340094) == 2) {
                type95 = "[已完成]";
            }
            if (pc.getQuest().get_step(340095) == 2) {
                type96 = "[已完成]";
            }
            if (pc.getQuest().get_step(340096) == 2) {
                type97 = "[已完成]";
            }
            if (pc.getQuest().get_step(340097) == 2) {
                type98 = "[已完成]";
            }
            if (pc.getQuest().get_step(340098) == 2) {
                type99 = "[已完成]";
            }
            if (pc.getQuest().get_step(340099) == 2) {
                type100 = "[已完成]";
            }
            if (pc.getQuest().get_step(340100) == 2) {
                type101 = "[已完成]";
            }
            if (pc.getQuest().get_step(340101) == 2) {
                type102 = "[已完成]";
            }
            if (pc.getQuest().get_step(340102) == 2) {
                type103 = "[已完成]";
            }
            if (pc.getQuest().get_step(340103) == 2) {
                type104 = "[已完成]";
            }
            if (pc.getQuest().get_step(340104) == 2) {
                type105 = "[已完成]";
            }
            if (pc.getQuest().get_step(340105) == 2) {
                type106 = "[已完成]";
            }
            if (pc.getQuest().get_step(340106) == 2) {
                type107 = "[已完成]";
            }
            if (pc.getQuest().get_step(340107) == 2) {
                type108 = "[已完成]";
            }
            if (pc.getQuest().get_step(340108) == 2) {
                type109 = "[已完成]";
            }
            if (pc.getQuest().get_step(340109) == 2) {
                type110 = "[已完成]";
            }
            if (pc.getQuest().get_step(340110) == 2) {
                type111 = "[已完成]";
            }
            if (pc.getQuest().get_step(340111) == 2) {
                type112 = "[已完成]";
            }
            if (pc.getQuest().get_step(340112) == 2) {
                type113 = "[已完成]";
            }
            if (pc.getQuest().get_step(340113) == 2) {
                type114 = "[已完成]";
            }
            if (pc.getQuest().get_step(340114) == 2) {
                type115 = "[已完成]";
            }
            if (pc.getQuest().get_step(340115) == 2) {
                type116 = "[已完成]";
            }
            if (pc.getQuest().get_step(340116) == 2) {
                type117 = "[已完成]";
            }
            if (pc.getQuest().get_step(340117) == 2) {
                type118 = "[已完成]";
            }
            if (pc.getQuest().get_step(340118) == 2) {
                type119 = "[已完成]";
            }
            if (pc.getQuest().get_step(340119) == 2) {
                type120 = "[已完成]";
            }
            if (pc.getQuest().get_step(340120) == 2) {
                type121 = "[已完成]";
            }
            if (pc.getQuest().get_step(340121) == 2) {
                type122 = "[已完成]";
            }
            if (pc.getQuest().get_step(340122) == 2) {
                type123 = "[已完成]";
            }
            if (pc.getQuest().get_step(340123) == 2) {
                type124 = "[已完成]";
            }
            if (pc.getQuest().get_step(340124) == 2) {
                type125 = "[已完成]";
            }
            if (pc.getQuest().get_step(340125) == 2) {
                type126 = "[已完成]";
            }
            if (pc.getQuest().get_step(340126) == 2) {
                type127 = "[已完成]";
            }
            if (pc.getQuest().get_step(340127) == 2) {
                type128 = "[已完成]";
            }
            if (pc.getQuest().get_step(340128) == 2) {
                type129 = "[已完成]";
            }
            if (pc.getQuest().get_step(340129) == 2) {
                type130 = "[已完成]";
            }
            if (pc.getQuest().get_step(340130) == 2) {
                type131 = "[已完成]";
            }
            if (pc.getQuest().get_step(340131) == 2) {
                type132 = "[已完成]";
            }
            if (pc.getQuest().get_step(340132) == 2) {
                type133 = "[已完成]";
            }
            if (pc.getQuest().get_step(340133) == 2) {
                type134 = "[已完成]";
            }
            if (pc.getQuest().get_step(340134) == 2) {
                type135 = "[已完成]";
            }
            if (pc.getQuest().get_step(340135) == 2) {
                type136 = "[已完成]";
            }
            if (pc.getQuest().get_step(340136) == 2) {
                type137 = "[已完成]";
            }
            if (pc.getQuest().get_step(340137) == 2) {
                type138 = "[已完成]";
            }
            if (pc.getQuest().get_step(340138) == 2) {
                type139 = "[已完成]";
            }
            if (pc.getQuest().get_step(340139) == 2) {
                type140 = "[已完成]";
            }
            if (pc.getQuest().get_step(340140) == 2) {
                type141 = "[已完成]";
            }
            if (pc.getQuest().get_step(340141) == 2) {
                type142 = "[已完成]";
            }
            if (pc.getQuest().get_step(340142) == 2) {
                type143 = "[已完成]";
            }
            if (pc.getQuest().get_step(340143) == 2) {
                type144 = "[已完成]";
            }
            if (pc.getQuest().get_step(340144) == 2) {
                type145 = "[已完成]";
            }
            if (pc.getQuest().get_step(340145) == 2) {
                type146 = "[已完成]";
            }
            if (pc.getQuest().get_step(340146) == 2) {
                type147 = "[已完成]";
            }
            if (pc.getQuest().get_step(340147) == 2) {
                type148 = "[已完成]";
            }
            if (pc.getQuest().get_step(340148) == 2) {
                type149 = "[已完成]";
            }
            if (pc.getQuest().get_step(340149) == 2) {
                type150 = "[已完成]";
            }
            if (pc.getQuest().get_step(340150) == 2) {
                type151 = "[已完成]";
            }
            if (pc.getQuest().get_step(340151) == 2) {
                type152 = "[已完成]";
            }
            if (pc.getQuest().get_step(340152) == 2) {
                type153 = "[已完成]";
            }
            if (pc.getQuest().get_step(340153) == 2) {
                type154 = "[已完成]";
            }
            if (pc.getQuest().get_step(340154) == 2) {
                type155 = "[已完成]";
            }
            if (pc.getQuest().get_step(340155) == 2) {
                type156 = "[已完成]";
            }
            if (pc.getQuest().get_step(340156) == 2) {
                type157 = "[已完成]";
            }
            if (pc.getQuest().get_step(340157) == 2) {
                type158 = "[已完成]";
            }
            if (pc.getQuest().get_step(340158) == 2) {
                type159 = "[已完成]";
            }
            if (pc.getQuest().get_step(340159) == 2) {
                type160 = "[已完成]";
            }
            if (pc.getQuest().get_step(340160) == 2) {
                type161 = "[已完成]";
            }
            if (pc.getQuest().get_step(340161) == 2) {
                type162 = "[已完成]";
            }
            if (pc.getQuest().get_step(340162) == 2) {
                type163 = "[已完成]";
            }
            if (pc.getQuest().get_step(340163) == 2) {
                type164 = "[已完成]";
            }
            if (pc.getQuest().get_step(340164) == 2) {
                type165 = "[已完成]";
            }
            if (pc.getQuest().get_step(340165) == 2) {
                type166 = "[已完成]";
            }
            if (pc.getQuest().get_step(340166) == 2) {
                type167 = "[已完成]";
            }
            if (pc.getQuest().get_step(340167) == 2) {
                type168 = "[已完成]";
            }
            if (pc.getQuest().get_step(340168) == 2) {
                type169 = "[已完成]";
            }
            if (pc.getQuest().get_step(340169) == 2) {
                type170 = "[已完成]";
            }
            if (pc.getQuest().get_step(340170) == 2) {
                type171 = "[已完成]";
            }
            if (pc.getQuest().get_step(340171) == 2) {
                type172 = "[已完成]";
            }
            if (pc.getQuest().get_step(340172) == 2) {
                type173 = "[已完成]";
            }
            if (pc.getQuest().get_step(340173) == 2) {
                type174 = "[已完成]";
            }
            if (pc.getQuest().get_step(340174) == 2) {
                type175 = "[已完成]";
            }
            if (pc.getQuest().get_step(340175) == 2) {
                type176 = "[已完成]";
            }
            if (pc.getQuest().get_step(340176) == 2) {
                type177 = "[已完成]";
            }
            if (pc.getQuest().get_step(340177) == 2) {
                type178 = "[已完成]";
            }
            if (pc.getQuest().get_step(340178) == 2) {
                type179 = "[已完成]";
            }
            if (pc.getQuest().get_step(340179) == 2) {
                type180 = "[已完成]";
            }
            if (pc.getQuest().get_step(340180) == 2) {
                type181 = "[已完成]";
            }
            if (pc.getQuest().get_step(340181) == 2) {
                type182 = "[已完成]";
            }
            if (pc.getQuest().get_step(340182) == 2) {
                type183 = "[已完成]";
            }
            if (pc.getQuest().get_step(340183) == 2) {
                type184 = "[已完成]";
            }
            if (pc.getQuest().get_step(340184) == 2) {
                type185 = "[已完成]";
            }
            if (pc.getQuest().get_step(340185) == 2) {
                type186 = "[已完成]";
            }
            if (pc.getQuest().get_step(340186) == 2) {
                type187 = "[已完成]";
            }
            if (pc.getQuest().get_step(340187) == 2) {
                type188 = "[已完成]";
            }
            if (pc.getQuest().get_step(340188) == 2) {
                type189 = "[已完成]";
            }
            if (pc.getQuest().get_step(340189) == 2) {
                type190 = "[已完成]";
            }
            if (pc.getQuest().get_step(340190) == 2) {
                type191 = "[已完成]";
            }
            if (pc.getQuest().get_step(340191) == 2) {
                type192 = "[已完成]";
            }
            if (pc.getQuest().get_step(340192) == 2) {
                type193 = "[已完成]";
            }
            if (pc.getQuest().get_step(340193) == 2) {
                type194 = "[已完成]";
            }
            if (pc.getQuest().get_step(340194) == 2) {
                type195 = "[已完成]";
            }
            if (pc.getQuest().get_step(340195) == 2) {
                type196 = "[已完成]";
            }
            if (pc.getQuest().get_step(340196) == 2) {
                type197 = "[已完成]";
            }
            if (pc.getQuest().get_step(340197) == 2) {
                type198 = "[已完成]";
            }
            if (pc.getQuest().get_step(340198) == 2) {
                type199 = "[已完成]";
            }
            if (pc.getQuest().get_step(340199) == 2) {
                type200 = "[已完成]";
            }
            if (pc.getpag() == 0) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
            else if (pc.getpag() == 1) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect1", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
            else if (pc.getpag() == 2) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect2", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
            else if (pc.getpag() == 3) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect3", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
            else if (pc.getpag() == 4) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect4", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
            else if (pc.getpag() == 5) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect5", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
            else if (pc.getpag() == 6) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "collect6", new String[] { type1, type2, type3, type4, type5, type6, type7, type8, type9, type10, type11, type12, type13, type14, type15, type16, type17, type18, type19, type20, type21, type22, type23, type24, type25, type26, type27, type28, type29, type30, type31, type32, type33, type34, type35, type36, type37, type38, type39, type40, type41, type42, type43, type44, type45, type46, type47, type48, type49, type50, type51, type52, type53, type54, type55, type56, type57, type58, type59, type60, type61, type62, type63, type64, type65, type66, type67, type68, type69, type70, type71, type72, type73, type74, type75, type76, type77, type78, type79, type80, type81, type82, type83, type84, type85, type86, type87, type88, type89, type90, type91, type92, type93, type94, type95, type96, type97, type98, type99, type100, type101, type102, type103, type104, type105, type106, type107, type108, type109, type110, type111, type112, type113, type114, type115, type116, type117, type118, type119, type120, type121, type122, type123, type124, type125, type126, type127, type128, type129, type130, type131, type132, type133, type134, type135, type136, type137, type138, type139, type140, type141, type142, type143, type144, type145, type146, type147, type148, type149, type150, type151, type152, type153, type154, type155, type156, type157, type158, type159, type160, type161, type162, type163, type164, type165, type166, type167, type168, type169, type170, type171, type172, type173, type174, type175, type176, type177, type178, type179, type180, type181, type182, type183, type184, type185, type186, type187, type188, type189, type190, type191, type192, type193, type194, type195, type196, type197, type198, type199, type200 }));
            }
        }
        catch (Exception e) {
            C_NPCAction._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    private boolean isTwoLogin(L1PcInstance c) {
        boolean bool = false;
        L1PcInstance[] allPlayersToArray;
        int length = (allPlayersToArray = World.get().getAllPlayersToArray()).length;
        int i = 0;
        while (i < length) {
            L1PcInstance target = allPlayersToArray[i];
            if (c.getId() != target.getId() && !target.isPrivateShop() && c.getNetConnection().getAccountName().equalsIgnoreCase(target.getNetConnection().getAccountName())) {
                bool = true;
                break;
            }
            else {
                ++i;
            }
        }
        return bool;
    }
}
