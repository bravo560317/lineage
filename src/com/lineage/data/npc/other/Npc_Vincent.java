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

public class Npc_Vincent extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Vincent.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Vincent();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "vincent2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "vincent1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        long xcount = -1L;
        if (cmd.equalsIgnoreCase("sell")) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "ladar2"));
        }
        else if (cmd.equalsIgnoreCase("request adena2")) {
            int[] items = { 40405 };
            int[] counts = { 1 };
            xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount > 0L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "A1"));
            }
            else {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("A1")) {
            int[] items = { 40405 };
            int[] counts = { 1 };
            int[] gitems = { 40308 };
            int[] gcounts = { 2 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request adena30")) {
            int[] items = { 40406 };
            int[] counts = { 1 };
            xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount > 0L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "A2"));
            }
            else {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("A2")) {
            int[] items = { 40406 };
            int[] counts = { 1 };
            int[] gitems = { 40308 };
            int[] gcounts = { 30 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request hard leather")) {
            int[] items = { 40405 };
            int[] counts = { 20 };
            xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount > 0L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "A3"));
            }
            else {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("A3")) {
            int[] items = { 40405 };
            int[] counts = { 20 };
            int[] gitems = { 40406 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request leather cap")) {
            int[] items = { 40405, 40408 };
            int[] counts = { 5, 1 };
            int[] gitems = { 20001 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request leather sandal")) {
            int[] items = { 40405, 40408 };
            int[] counts = { 6, 2 };
            int[] gitems = { 20193 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request leather vest")) {
            int[] items = { 40405 };
            int[] counts = { 10 };
            int[] gitems = { 20090 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request leather shield")) {
            int[] items = { 40405 };
            int[] counts = { 7 };
            int[] gitems = { 20219 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request leather boots")) {
            int[] items = { 20212, 40406, 40408, 40308 };
            int[] counts = { 1, 10, 10, 300 };
            int[] gitems = { 20192 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request leather helmet")) {
            int[] items = { 20043, 20001, 40406, 40408 };
            int[] counts = { 1, 1, 5, 5 };
            int[] gitems = { 20002 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request hard leather vest")) {
            int[] items = { 20148, 40406, 40408 };
            int[] counts = { 1, 15, 15 };
            int[] gitems = { 20145 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request leather vest with belt")) {
            int[] items = { 20090, 40778 };
            int[] counts = { 1, 1 };
            int[] gitems = { 20120 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request belt")) {
            int[] items = { 40406, 40408 };
            int[] counts = { 5, 2 };
            xcount = CreateNewItem.checkNewItem(pc, items, counts);
            if (xcount > 0L) {
                pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, "A4"));
            }
            else {
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("A4")) {
            int[] items = { 40406, 40408 };
            int[] counts = { 5, 2 };
            int[] gitems = { 40778 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts, amount);
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private boolean getItem(L1PcInstance pc, int[] items, int[] counts, int[] gitems, int[] gcounts, long amount) {
        try {
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                return true;
            }
            else {
                CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
                return true;
            }
        }
        catch (Exception e) {
            Npc_Vincent._log.error(e.getLocalizedMessage(), e);
            return true;
        }
    }
}
