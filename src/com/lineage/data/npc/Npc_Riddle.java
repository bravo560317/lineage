package com.lineage.data.npc;

import java.util.Iterator;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Riddle extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_Riddle();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "riddle1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        String htmlid = null;
        if (cmd.equalsIgnoreCase("A")) {
            for (L1ItemInstance Item : pc.getInventory().getItems()) {
                if (Item.getItemId() == 49477) {
                    pc.sendPackets(new S_ServerMessage("請先找尋蜜拉瓦通過任務後，才可以找我兌換別種符石"));
                    return;
                }
            }
            if (pc.getInventory().checkItem(49928, 1L)) {
                if (pc.isCrown()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600000, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600041, 1L);
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600001, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600042, 1L);
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600002, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600043, 1L);
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600003, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600044, 1L);
                    }
                }
                else if (pc.isDarkelf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600004, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600045, 1L);
                    }
                }
                else if (pc.isDragonKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600005, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600046, 1L);
                    }
                }
                else if (pc.isIllusionist()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600006, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600047, 1L);
                    }
                }
                pc.getInventory().consumeItem(49928, 1L);
            }
            else {
                htmlid = "riddle2";
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            for (L1ItemInstance Item : pc.getInventory().getItems()) {
                if (Item.getItemId() == 49477) {
                    pc.sendPackets(new S_ServerMessage("請先找尋蜜拉瓦通過任務後，才可以找我兌換別種符石"));
                    return;
                }
            }
            if (pc.getInventory().checkItem(49928, 1L)) {
                if (pc.isCrown()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600008, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600049, 1L);
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600009, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600050, 1L);
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600010, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600051, 1L);
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600011, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600052, 1L);
                    }
                }
                else if (pc.isDarkelf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600012, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600053, 1L);
                    }
                }
                else if (pc.isDragonKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600013, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600054, 1L);
                    }
                }
                else if (pc.isIllusionist()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600014, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600055, 1L);
                    }
                }
                pc.getInventory().consumeItem(49928, 1L);
            }
            else {
                htmlid = "riddle2";
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            for (L1ItemInstance Item : pc.getInventory().getItems()) {
                if (Item.getItemId() == 49477) {
                    pc.sendPackets(new S_ServerMessage("請先找尋蜜拉瓦通過任務後，才可以找我兌換別種符石"));
                    return;
                }
            }
            if (pc.getInventory().checkItem(49928, 1L)) {
                if (pc.isCrown()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600016, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600057, 1L);
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600017, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600058, 1L);
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600018, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600059, 1L);
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600019, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600060, 1L);
                    }
                }
                else if (pc.isDarkelf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600020, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600061, 1L);
                    }
                }
                else if (pc.isDragonKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600021, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600062, 1L);
                    }
                }
                else if (pc.isIllusionist()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600022, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600063, 1L);
                    }
                }
                pc.getInventory().consumeItem(49928, 1L);
            }
            else {
                htmlid = "riddle2";
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            for (L1ItemInstance Item : pc.getInventory().getItems()) {
                if (Item.getItemId() == 49477) {
                    pc.sendPackets(new S_ServerMessage("請先找尋蜜拉瓦通過任務後，才可以找我兌換別種符石"));
                    return;
                }
            }
            if (pc.getInventory().checkItem(49928, 1L)) {
                if (pc.isCrown()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600024, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600065, 1L);
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600025, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600066, 1L);
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600026, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600067, 1L);
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600027, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600068, 1L);
                    }
                }
                else if (pc.isDarkelf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600028, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600069, 1L);
                    }
                }
                else if (pc.isDragonKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600029, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600070, 1L);
                    }
                }
                else if (pc.isIllusionist()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600030, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600071, 1L);
                    }
                }
                pc.getInventory().consumeItem(49928, 1L);
            }
            else {
                htmlid = "riddle2";
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            for (L1ItemInstance Item : pc.getInventory().getItems()) {
                if (Item.getItemId() == 49477) {
                    pc.sendPackets(new S_ServerMessage("請先找尋蜜拉瓦通過任務後，才可以找我兌換別種符石"));
                    return;
                }
            }
            if (pc.getInventory().checkItem(49928, 1L)) {
                if (pc.isCrown()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600032, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600073, 1L);
                    }
                }
                else if (pc.isKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600033, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600074, 1L);
                    }
                }
                else if (pc.isElf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600034, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600075, 1L);
                    }
                }
                else if (pc.isWizard()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600035, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600076, 1L);
                    }
                }
                else if (pc.isDarkelf()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600036, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600077, 1L);
                    }
                }
                else if (pc.isDragonKnight()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600037, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600078, 1L);
                    }
                }
                else if (pc.isIllusionist()) {
                    if (pc.getLevel() < 70) {
                        CreateNewItem.createNewItem(pc, 600038, 1L);
                    }
                    else {
                        CreateNewItem.createNewItem(pc, 600079, 1L);
                    }
                }
                pc.getInventory().consumeItem(49928, 1L);
            }
            else {
                htmlid = "riddle2";
            }
        }
        if (htmlid != null) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), htmlid));
        }
    }
}
