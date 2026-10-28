package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Sherme extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Sherme.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Sherme();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "sherme2"));
        }
        catch (Exception e) {
            Npc_Sherme._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("a")) {
            int[] items = { 42514, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42518 };
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
        else if (cmd.equalsIgnoreCase("a1")) {
            int[] items = { 42514, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42518 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("b")) {
            int[] items = { 42515, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42519 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "b1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("b1")) {
            int[] items = { 42515, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42519 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("c")) {
            int[] items = { 42517, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42521 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "c1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("c1")) {
            int[] items = { 42517, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42521 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("d")) {
            int[] items = { 42516, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42520 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "d1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("d1")) {
            int[] items = { 42516, 40308 };
            int[] counts = { 1, 100000 };
            int[] gitems = { 42520 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("e")) {
            int[] items = { 42525, 42526, 40308 };
            int[] counts = { 1, 1, 200000 };
            int[] gitems = { 42522 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "e1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("e1")) {
            int[] items = { 42525, 42526, 40308 };
            int[] counts = { 1, 1, 200000 };
            int[] gitems = { 42522 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("f")) {
            int[] items = { 42522, 42527, 40308 };
            int[] counts = { 1, 1, 200000 };
            int[] gitems = { 42523 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "f1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("f1")) {
            int[] items = { 42522, 42527, 40308 };
            int[] counts = { 1, 1, 200000 };
            int[] gitems = { 42523 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("g")) {
            int[] items = { 42523, 42528, 40308 };
            int[] counts = { 1, 1, 200000 };
            int[] gitems = { 42524 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "g1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("g1")) {
            int[] items = { 42523, 42528, 40308 };
            int[] counts = { 1, 1, 200000 };
            int[] gitems = { 42524 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("h")) {
            int[] items = { 80015, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42518 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "h1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("h1")) {
            int[] items = { 80015, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42518 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("i")) {
            int[] items = { 80016, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42519 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "i1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("i1")) {
            int[] items = { 80016, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42519 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("j")) {
            int[] items = { 80018, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42521 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "j1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("j1")) {
            int[] items = { 80018, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42521 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount >= amount) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
            }
            isCloseList = true;
        }
        else if (cmd.equalsIgnoreCase("k")) {
            int[] items = { 80017, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42520 };
            int[] gcounts = { 1 };
            long xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount == 1L) {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
            else if (xcount > 1L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "k1"));
            }
            else if (xcount < 1L) {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("k1")) {
            int[] items = { 80017, 40308 };
            int[] counts = { 32, 100000 };
            int[] gitems = { 42520 };
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
