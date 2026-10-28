package com.lineage.data.npc;

import java.util.Iterator;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.templates.L1Inn;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.world.World;
import com.lineage.server.datatables.ItemTable;
import java.sql.Timestamp;
import java.util.Calendar;
import com.lineage.server.datatables.InnTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Bosskey extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Bosskey();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bosskey1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("1")) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bosskey4"));
            return;
        }
        else {
            if (cmd.equalsIgnoreCase("2") || cmd.equalsIgnoreCase("3") || cmd.equalsIgnoreCase("4")) {
                switch (cmd.hashCode()) {
                    case 50: {
                        if (!cmd.equals("2")) {
                            break;
                        }
                        else {
                            amount = 4L;
                            break;
                        }
                    }
                    case 51: {
                        if (!cmd.equals("3")) {
                            break;
                        }
                        else {
                            amount = 8L;
                            break;
                        }
                    }
                    case 52: {
                        if (!cmd.equals("4")) {
                            break;
                        }
                        else {
                            amount = 16L;
                            break;
                        }
                    }
                }
                int npcId = npc.getNpcTemplate().get_npcId();
                boolean canRent = false;
                boolean findRoom = false;
                boolean isRented = false;
                int roomNumber = 0;
                byte roomCount = 0;
                for (int i = 0; i < 16; ++i) {
                    L1Inn inn = InnTable.getInstance().getTemplate(npcId, i);
                    if (inn != null) {
                        Timestamp dueTime = inn.getDueTime();
                        Calendar cal = Calendar.getInstance();
                        long checkDueTime = (cal.getTimeInMillis() - dueTime.getTime()) / 1000L;
                        if (pc.getInventory().checkItem(82503, 1L)) {
                            isRented = true;
                            break;
                        }
                        else if (!findRoom && !isRented) {
                            if (checkDueTime >= 0L) {
                                canRent = true;
                                findRoom = true;
                                roomNumber = inn.getRoomNumber();
                            }
                            else if (inn.getLodgerId() == pc.getId()) {
                                canRent = true;
                                findRoom = true;
                                roomNumber = inn.getRoomNumber();
                            }
                            else {
                                ++roomCount;
                            }
                        }
                    }
                }
                if (isRented) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bosskey6"));
                }
                else if (roomCount >= 16) {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bosskey3"));
                }
                else if (canRent) {
                    if (pc.getInventory().checkItem(40308, 300L * amount)) {
                        L1Inn inn2 = InnTable.getInstance().getTemplate(npcId, roomNumber);
                        if (inn2 != null) {
                            Timestamp ts = new Timestamp(System.currentTimeMillis() + 14400000L);
                            L1ItemInstance item = ItemTable.get().createItem(82503);
                            if (item != null) {
                                item.setKeyId(item.getId());
                                item.setInnNpcId(npcId);
                                item.setHall(false);
                                item.setDueTime(ts);
                                item.setCount(amount);
                                inn2.setKeyId(item.getKeyId());
                                inn2.setLodgerId(pc.getId());
                                inn2.setHall(false);
                                inn2.setDueTime(ts);
                                InnTable.getInstance().updateInn(inn2);
                                pc.getInventory().consumeItem(40308, 300L * amount);
                                L1Inventory inventory;
                                if (pc.getInventory().checkAddItem(item, amount) == 0) {
                                    inventory = pc.getInventory();
                                }
                                else {
                                    inventory = World.get().getInventory(pc.getLocation());
                                }
                                inventory.storeItem(item);
                                if (InnKeyTable.checkey(item)) {
                                    InnKeyTable.DeleteKey(item);
                                    InnKeyTable.StoreKey(item);
                                }
                                else {
                                    InnKeyTable.StoreKey(item);
                                }
                                pc.sendPackets(new S_ServerMessage(143, npc.getName(), item.getLogName()));
                                String[] msg = { npc.getName() };
                                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bosskey7", msg));
                            }
                        }
                    }
                    else {
                        String[] msg2 = { npc.getName() };
                        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bosskey5", msg2));
                    }
                }
            }
            else if (cmd.equalsIgnoreCase("6")) {
                int npcId2 = npc.getNpcTemplate().get_npcId();
                for (L1ItemInstance item2 : pc.getInventory().getItems()) {
                    if (item2.getInnNpcId() == npcId2) {
                        for (int j = 0; j < 16; ++j) {
                            L1Inn inn3 = InnTable.getInstance().getTemplate(npcId2, j);
                            if (inn3.getKeyId() == item2.getKeyId()) {
                                Timestamp dueTime2 = item2.getDueTime();
                                if (dueTime2 != null) {
                                    Calendar cal2 = Calendar.getInstance();
                                    if ((cal2.getTimeInMillis() - dueTime2.getTime()) / 1000L < 0L) {
                                        pc.set_showId(item2.getKeyId());
                                        L1Teleport.teleport(pc, 32899, 32818, (short)1400, pc.getHeading(), true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
    }
}
