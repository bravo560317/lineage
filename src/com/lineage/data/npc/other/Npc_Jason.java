package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Jason extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Jason.class);
        Npc_Jason._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Jason();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jason2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "jason1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("request timber")) {
            int[] items = { 42502 };
            int[] counts = { 4 };
            int[] gitems = { 42503 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "a1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equals("a1")) {
            int[] items = { 42502 };
            int[] counts = { 4 };
            int[] gitems = { 42503 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("request blank box")) {
            int[] items = { 42502, 40308 };
            int[] counts = { 5, 500 };
            int[] gitems = { 42504 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "a2"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equals("a2")) {
            int[] items = { 42502, 40308 };
            int[] counts = { 5, 500 };
            int[] gitems = { 42504 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
