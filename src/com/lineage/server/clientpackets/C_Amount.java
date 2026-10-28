package com.lineage.server.clientpackets;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.npc.action.L1NpcAction;
import com.add.system.L1Blend1;
import com.add.system.L1Blend;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.templates.L1Inn;
import com.lineage.server.templates.L1House;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.datatables.NpcActionTable;
import com.add.system.L1BlendTable_1;
import com.add.system.L1BlendTable;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.datatables.ItemTable;
import java.sql.Timestamp;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.InnTable;
import java.util.Calendar;
import java.util.TimeZone;
import com.lineage.config.Config;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.datatables.sql.AuctionBoardTable;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1AuctionBoardTmp;
import com.lineage.server.datatables.lock.AuctionBoardReading;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Amount extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Amount.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) throws Exception {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        int objectId = this.readD();
                        int amount = Math.max(0, this.readD());
                        if (amount <= 0) {
                            return;
                        }
                        else {
                            int c = this.readC();
                            String s = this.readS();
                            if (amount > Integer.MAX_VALUE) {
                                amount = Integer.MAX_VALUE;
                            }
                            L1NpcInstance npc = (L1NpcInstance)World.get().findObject(objectId);
                            if (npc == null) {
                                return;
                            }
                            else {
                                String s2 = "";
                                String s3 = "";
                                try {
                                    StringTokenizer stringtokenizer = new StringTokenizer(s);
                                    s2 = stringtokenizer.nextToken();
                                    s3 = stringtokenizer.nextToken();
                                }
                                catch (NoSuchElementException e) {
                                    s2 = "";
                                    s3 = "";
                                }
                                if (s2.equalsIgnoreCase("agapply")) {
                                    String pcName = pc.getName();
                                    Collection<L1AuctionBoardTmp> boardList = (Collection)AuctionBoardReading.get().getAuctionBoardTableList().values();
                                    for (L1AuctionBoardTmp board : boardList) {
                                        if (pcName.equalsIgnoreCase(board.getBidder())) {
                                            pc.sendPackets(new S_ServerMessage(523));
                                            return;
                                        }
                                    }
                                    int houseId = Integer.valueOf(s3);
                                    L1AuctionBoardTmp board2 = AuctionBoardReading.get().getAuctionBoardTable(houseId);
                                    if (board2 != null) {
                                        long nowPrice = board2.getPrice();
                                        int nowBidderId = board2.getBidderId();
                                        L1ItemInstance item = pc.getInventory().checkItemX(40308, (long)amount);
                                        if (item != null) {
                                            if (pc.getInventory().consumeItem(40308, (long)amount)) {
                                                board2.setPrice((long)amount);
                                                board2.setBidder(pcName);
                                                board2.setBidderId(pc.getId());
                                                AuctionBoardReading.get().updateAuctionBoard(board2);
                                                if (nowBidderId != 0) {
                                                    L1PcInstance bidPc = (L1PcInstance)World.get().findObject(nowBidderId);
                                                    if (bidPc != null) {
                                                        bidPc.getInventory().storeItem(40308, nowPrice);
                                                        bidPc.sendPackets(new S_ServerMessage(525, String.valueOf(nowPrice)));
                                                    }
                                                    else {
                                                        CharItemsReading.get().getAdenaCount(nowBidderId, nowPrice);
                                                    }
                                                }
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(189));
                                            }
                                        }
                                        else {
                                            pc.sendPackets(new S_ServerMessage(189));
                                        }
                                    }
                                }
                                else if (s2.equalsIgnoreCase("agsell")) {
                                    if (npc.getNpcId() == 70535 && npc.ACTION != null) {
                                        if (amount <= 0) {
                                            return;
                                        }
                                        else {
                                            npc.ACTION.action(pc, npc, s, (long)amount);
                                            return;
                                        }
                                    }
                                    else {
                                        int houseId2 = Integer.valueOf(s3);
                                        AuctionBoardTable boardTable = new AuctionBoardTable();
                                        L1AuctionBoardTmp board = new L1AuctionBoardTmp();
                                        if (board != null) {
                                            board.setHouseId(houseId2);
                                            L1House house = HouseReading.get().getHouseTable(houseId2);
                                            board.setHouseName(house.getHouseName());
                                            board.setHouseArea(house.getHouseArea());
                                            TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
                                            Calendar cal = Calendar.getInstance(tz);
                                            cal.add(5, 5);
                                            cal.set(12, 0);
                                            cal.set(13, 0);
                                            board.setDeadline(cal);
                                            board.setPrice((long)amount);
                                            board.setLocation(house.getLocation());
                                            board.setOldOwner(pc.getName());
                                            board.setOldOwnerId(pc.getId());
                                            board.setBidder("");
                                            board.setBidderId(0);
                                            boardTable.insertAuctionBoard(board);
                                            house.setOnSale(true);
                                            house.setPurchaseBasement(true);
                                            HouseReading.get().updateHouse(house);
                                        }
                                    }
                                }
                                int npcId = npc.getNpcId();
                                if (npcId == 70070 || npcId == 70019 || npcId == 70075 || npcId == 70012 || npcId == 70031 || npcId == 70084 || npcId == 70065 || npcId == 70054 || npcId == 70096) {
                                    if (pc.getInventory().checkItem(40308, (long)(300 * amount))) {
                                        L1Inn inn = InnTable.getInstance().getTemplate(npcId, pc.getInnRoomNumber());
                                        if (inn != null) {
                                            Timestamp dueTime = inn.getDueTime();
                                            if (dueTime != null) {
                                                Calendar cal2 = Calendar.getInstance();
                                                if ((cal2.getTimeInMillis() - dueTime.getTime()) / 1000L < 0L) {
                                                    pc.sendPackets(new S_NPCTalkReturn(npcId, ""));
                                                    return;
                                                }
                                            }
                                            Timestamp ts = new Timestamp(System.currentTimeMillis() + 14400000L);
                                            L1ItemInstance item2 = ItemTable.get().createItem(40312);
                                            if (item2 != null) {
                                                item2.setKeyId(item2.getId());
                                                item2.setInnNpcId(npcId);
                                                item2.setHall(pc.checkRoomOrHall());
                                                item2.setDueTime(ts);
                                                item2.setCount((long)amount);
                                                inn.setKeyId(item2.getKeyId());
                                                inn.setLodgerId(pc.getId());
                                                inn.setHall(pc.checkRoomOrHall());
                                                inn.setDueTime(ts);
                                                InnTable.getInstance().updateInn(inn);
                                                pc.getInventory().consumeItem(40308, (long)(300 * amount));
                                                L1Inventory inventory;
                                                if (pc.getInventory().checkAddItem(item2, (long)amount) == 0) {
                                                    inventory = pc.getInventory();
                                                }
                                                else {
                                                    inventory = World.get().getInventory(pc.getLocation());
                                                }
                                                inventory.storeItem(item2);
                                                if (InnKeyTable.checkey(item2)) {
                                                    InnKeyTable.DeleteKey(item2);
                                                    InnKeyTable.StoreKey(item2);
                                                }
                                                else {
                                                    InnKeyTable.StoreKey(item2);
                                                }
                                                String itemName = item2.getItem().getName() + item2.getInnKeyName();
                                                if (amount > 1) {
                                                    itemName = itemName + " (" + amount + ")";
                                                }
                                                pc.sendPackets(new S_ServerMessage(143, npc.getName(), itemName));
                                                String[] msg = { npc.getName() };
                                                pc.sendPackets(new S_NPCTalkReturn(npcId, "inn4", msg));
                                            }
                                        }
                                    }
                                    else {
                                        String[] msg2 = { npc.getName() };
                                        pc.sendPackets(new S_NPCTalkReturn(npcId, "inn3", msg2));
                                    }
                                }
                                else if (npcId == 99021 && s.equalsIgnoreCase("a1")) {
                                    int[] items = { 85000 };
                                    int[] counts = { 108 };
                                    int[] gitems = { 85008 };
                                    int[] gcounts = { 1 };
                                    long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                                    if (xcount >= (long)amount) {
                                        CreateNewItem.createNewItem(pc, items, counts, gitems, (long)amount, gcounts);
                                    }
                                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "tw_takesi2"));
                                }
                                String craftkey = npc.get_craftkey();
                                if (craftkey != null) {
                                    L1Blend ItemBlend = L1BlendTable.getInstance().getTemplate(craftkey);
                                    int[] Materials = ItemBlend.getMaterials();
                                    int[] counts2 = ItemBlend.getMaterials_count();
                                    long xcount = CreateNewItem.checkNewItem(pc, Materials, counts2);
                                    if (xcount >= (long)amount) {
                                        ItemBlend.CheckCraftItem(pc, npc, ItemBlend, amount, true);
                                        return;
                                    }
                                }
                                String craftkey2 = npc.get_craftkey();
                                if (craftkey2 != null) {
                                    L1Blend1 ItemBlend2 = L1BlendTable_1.getInstance().getTemplate(craftkey2);
                                    int[] Materials2 = ItemBlend2.getMaterials();
                                    int[] counts3 = ItemBlend2.getMaterials_count();
                                    long xcount2 = CreateNewItem.checkNewItem(pc, Materials2, counts3);
                                    if (xcount2 >= (long)amount) {
                                        ItemBlend2.CheckCraftItem(pc, npc, ItemBlend2, amount, true);
                                        return;
                                    }
                                }
                                if (npc.ACTION != null) {
                                    if (amount <= 0) {
                                        return;
                                    }
                                    else {
                                        npc.ACTION.action(pc, npc, s, (long)amount);
                                        return;
                                    }
                                }
                                else {
                                    L1NpcAction action = NpcActionTable.getInstance().get(s, pc, npc);
                                    if (action != null) {
                                        L1NpcHtml result = action.executeWithAmount(s, pc, npc, (long)amount);
                                        if (result != null) {
                                            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), result));
                                        }
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
        catch (Exception e2) {
            C_Amount._log.error(e2.getLocalizedMessage(), e2);
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
