package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_EnchantBuff extends NpcExecutor
{
    private Npc_EnchantBuff() {
    }
    
    public static NpcExecutor get() {
        return new Npc_EnchantBuff();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        int mapId = npc.getMapId();
        if (mapId == 0 || mapId == 4) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_04"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_08"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equals("a")) {
            if (pc.getInventory().consumeItem(40308, 3000L)) {
                int[] skillId = { 26, 42, 48 };
                int[] skillTime = { 1800, 1800, 960, 960, 960, 1000, 1000 };
                for (int i = 0; i < skillId.length; ++i) {
                    L1SkillUse skillUse = new L1SkillUse();
                    skillUse.handleCommands(pc, skillId[i], pc.getId(), pc.getX(), pc.getY(), skillTime[i], 4);
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(189));
            }
        }
        else if (cmd.equalsIgnoreCase("1")) {
            pc.setAmount(1);
            String[] data = { String.valueOf(500), String.valueOf(1000), String.valueOf(1000), String.valueOf(2000), String.valueOf(2000), String.valueOf(1) };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m4", data));
            return;
        }
        else if (cmd.equalsIgnoreCase("2")) {
            pc.setAmount(5);
            String[] data = { String.valueOf(2500), String.valueOf(5000), String.valueOf(5000), String.valueOf(10000), String.valueOf(10000), String.valueOf(5) };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m4", data));
            return;
        }
        else if (cmd.equalsIgnoreCase("3")) {
            pc.setAmount(10);
            String[] data = { String.valueOf(5000), String.valueOf(10000), String.valueOf(10000), String.valueOf(20000), String.valueOf(20000), String.valueOf(10) };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m4", data));
            return;
        }
        else if (cmd.equalsIgnoreCase("4")) {
            pc.setAmount(100);
            String[] data = { String.valueOf(50000), String.valueOf(100000), String.valueOf(100000), String.valueOf(200000), String.valueOf(200000), String.valueOf(100) };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m4", data));
            return;
        }
        else if (cmd.equalsIgnoreCase("5")) {
            pc.setAmount(500);
            String[] data = { String.valueOf(250000), String.valueOf(500000), String.valueOf(500000), String.valueOf(1000000), String.valueOf(1000000), String.valueOf(500) };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m4", data));
            return;
        }
        else if (cmd.equals("A")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 8) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 500 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40090, (long)count)) {
                    pc.getInventory().storeItem(40860, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equals("B")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 8) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 500 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40090, (long)count)) {
                    pc.getInventory().storeItem(40861, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equals("C")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 8) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 500 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40090, (long)count)) {
                    pc.getInventory().storeItem(40862, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 8) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 500 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40090, (long)count)) {
                    pc.getInventory().storeItem(40866, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 8) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 500 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40090, (long)count)) {
                    pc.getInventory().storeItem(40859, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("F")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 16) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40091, (long)count)) {
                    pc.getInventory().storeItem(40872, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("G")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 16) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40091, (long)count)) {
                    pc.getInventory().storeItem(40871, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("H")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 16) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40091, (long)count)) {
                    pc.getInventory().storeItem(40870, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("I")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 16) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40091, (long)count)) {
                    pc.getInventory().storeItem(40867, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("J")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 16) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40091, (long)count)) {
                    pc.getInventory().storeItem(40873, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("K")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 24) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40092, (long)count)) {
                    pc.getInventory().storeItem(40875, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("L")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 24) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40092, (long)count)) {
                    pc.getInventory().storeItem(40879, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("M")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 24) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40092, (long)count)) {
                    pc.getInventory().storeItem(40877, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("N")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 24) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40092, (long)count)) {
                    pc.getInventory().storeItem(40880, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("O")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 24) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 1000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40092, (long)count)) {
                    pc.getInventory().storeItem(40876, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("P")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 32) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40093, (long)count)) {
                    pc.getInventory().storeItem(40890, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("Q")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 32) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40093, (long)count)) {
                    pc.getInventory().storeItem(40883, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("R")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 32) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40093, (long)count)) {
                    pc.getInventory().storeItem(40884, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("S")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 32) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40093, (long)count) && pc.getInventory().consumeItem(40318, (long)count)) {
                    pc.getInventory().storeItem(40889, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("T")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 32) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40093, (long)count) && pc.getInventory().consumeItem(40318, (long)count)) {
                    pc.getInventory().storeItem(40887, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("U")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 40) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40094, (long)count)) {
                    pc.getInventory().storeItem(40893, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("V")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 40) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40094, (long)count)) {
                    pc.getInventory().storeItem(40895, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("W")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 40) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40094, (long)count)) {
                    pc.getInventory().storeItem(40897, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("X")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 40) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40094, (long)count)) {
                    pc.getInventory().storeItem(40896, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        else if (cmd.equalsIgnoreCase("Y")) {
            int count = pc.getAmount();
            if (count <= 0) {
                pc.setAmount(0);
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else if (pc.getHighLevel() < 40) {
                pc.setAmount(0);
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m3"));
                return;
            }
            else {
                int money = 2000 * count;
                if (pc.getInventory().consumeItem(40308, (long)money) && pc.getInventory().consumeItem(40094, (long)count)) {
                    pc.getInventory().storeItem(40892, (long)count);
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m1"));
                }
                else {
                    pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "bs_m2"));
                }
                pc.setAmount(0);
                return;
            }
        }
        pc.sendPackets(new S_CloseList(pc.getId()));
    }
}
