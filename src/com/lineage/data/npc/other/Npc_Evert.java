package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_ShopBuyList;
import com.lineage.server.serverpackets.S_ShopSellList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Evert extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Evert.class);
        Npc_Evert._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Evert();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "evert2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "evert1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("buy")) {
            pc.sendPackets(new S_ShopSellList(npc.getId()));
        }
        else if (cmd.equalsIgnoreCase("sell")) {
            pc.sendPackets(new S_ShopBuyList(npc.getId(), pc));
        }
        if (cmd.equalsIgnoreCase("request arctic fabric")) {
            int[] items = { 20134 };
            int[] counts = { 1 };
            int[] gitems = { 80023 };
            int[] gcounts = { 1 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                isCloseList = true;
            }
            else {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
