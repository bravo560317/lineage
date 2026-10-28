package com.lineage.data.npc.other;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Anton extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Anton.class);
    }
    
    private Npc_Anton() {
    }
    
    public static NpcExecutor get() {
        return new Npc_Anton();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "anton3"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        int mdefarmor = 0;
        int oldarmor = 0;
        int material = 0;
        int newarmor = 0;
        boolean success = false;
        if (cmd.equalsIgnoreCase("A")) {
            mdefarmor = 20110;
            oldarmor = 20095;
            material = 41246;
            newarmor = 401031;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 7, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 7, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(0);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("B")) {
            mdefarmor = 20110;
            oldarmor = 20095;
            material = 41246;
            newarmor = 401031;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 8, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 8, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(1);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("C")) {
            mdefarmor = 20110;
            oldarmor = 20095;
            material = 41246;
            newarmor = 401031;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 9, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 9, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(2);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("D")) {
            mdefarmor = 20110;
            oldarmor = 20095;
            material = 41246;
            newarmor = 401031;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 10, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 10, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(3);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("E")) {
            mdefarmor = 20110;
            oldarmor = 20094;
            material = 41246;
            newarmor = 401030;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 7, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 7, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(0);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("F")) {
            mdefarmor = 20110;
            oldarmor = 20094;
            material = 41246;
            newarmor = 401030;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 8, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 8, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(1);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("G")) {
            mdefarmor = 20110;
            oldarmor = 20094;
            material = 41246;
            newarmor = 401030;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 9, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 9, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(2);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("H")) {
            mdefarmor = 20110;
            oldarmor = 20094;
            material = 41246;
            newarmor = 401030;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 10, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 10, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(3);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("I")) {
            mdefarmor = 20110;
            oldarmor = 20092;
            material = 41246;
            newarmor = 401028;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 7, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 7, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(0);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("J")) {
            mdefarmor = 20110;
            oldarmor = 20092;
            material = 41246;
            newarmor = 401028;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 8, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 8, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(1);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("K")) {
            mdefarmor = 20110;
            oldarmor = 20092;
            material = 41246;
            newarmor = 401028;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 9, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 9, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(2);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("L")) {
            mdefarmor = 20110;
            oldarmor = 20092;
            material = 41246;
            newarmor = 401028;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 10, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 10, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(3);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("M")) {
            mdefarmor = 20110;
            oldarmor = 20093;
            material = 41246;
            newarmor = 401029;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 7, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 7, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(0);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("N")) {
            mdefarmor = 20110;
            oldarmor = 20093;
            material = 41246;
            newarmor = 401029;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 8, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 8, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(1);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("O")) {
            mdefarmor = 20110;
            oldarmor = 20093;
            material = 41246;
            newarmor = 401029;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 9, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 9, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(2);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        else if (cmd.equalsIgnoreCase("P")) {
            mdefarmor = 20110;
            oldarmor = 20093;
            material = 41246;
            newarmor = 401029;
            if (pc.getInventory().checkEnchantItem(mdefarmor, 10, 1L) && pc.getInventory().checkItem(oldarmor, 1L) && pc.getInventory().checkItem(material, 100000L)) {
                pc.getInventory().consumeEnchantItem(mdefarmor, 10, 1L);
                pc.getInventory().consumeItem(oldarmor, 1L);
                pc.getInventory().consumeItem(material, 100000L);
                L1ItemInstance item = ItemTable.get().createItem(newarmor);
                item.setEnchantLevel(3);
                item.setIdentified(true);
                pc.getInventory().storeItem(item);
                String npcName = npc.getNpcTemplate().get_name();
                String itemName = item.getLogName();
                pc.sendPackets(new S_ServerMessage(143, npcName, itemName));
                success = true;
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        if (!success) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "anton9"));
        }
    }
}
