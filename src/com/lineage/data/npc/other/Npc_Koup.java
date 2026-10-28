package com.lineage.data.npc.other;

import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Koup extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Koup.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Koup();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "koup1"));
        }
        catch (Exception e) {
            Npc_Koup._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("request dark dualblade")) {
            int[] items = { 40321, 40408, 40406 };
            int[] counts = { 100, 10, 20 };
            int[] gitems = { 75 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request dark claw")) {
            int[] items = { 40321, 40322, 40408, 40406 };
            int[] counts = { 100, 5, 10, 10 };
            int[] gitems = { 158 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request dark crossbow")) {
            int[] items = { 40321, 40322, 40408, 40406 };
            int[] counts = { 100, 10, 10, 30 };
            int[] gitems = { 168 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request blind dualblade")) {
            int[] items = { 75, 40408, 40406, 40322, 40323 };
            int[] counts = { 1, 10, 20, 100, 5 };
            int[] gitems = { 81 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request blind claw")) {
            int[] items = { 158, 40408, 40406, 40322, 40323 };
            int[] counts = { 1, 10, 10, 100, 10 };
            int[] gitems = { 162 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request blind crossbow")) {
            int[] items = { 168, 40408, 40406, 40322, 40323 };
            int[] counts = { 1, 10, 30, 100, 20 };
            int[] gitems = { 177 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request silver dualblade")) {
            int[] items = { 75, 40408, 40406, 40321, 40323, 40044, 40467 };
            int[] counts = { 1, 10, 20, 50, 1, 1, 20 };
            int[] gitems = { 74 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request silver claw")) {
            int[] items = { 158, 40408, 40406, 40321, 40323, 40044, 40467 };
            int[] counts = { 1, 10, 10, 40, 1, 1, 30 };
            int[] gitems = { 157 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request black mithril")) {
            int[] items = { 40486, 40490, 40442, 40444, 40308 };
            int[] counts = { 1, 1, 1, 5, 5000 };
            int[] gitems = { 40443 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request black mithril arrow")) {
            int[] items = { 40507, 40443, 40440, 40308 };
            int[] counts = { 10, 1, 1, 1000 };
            int[] gitems = { 40747 };
            int[] gcounts = { 5000 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request lump of steel")) {
            int[] items = { 40899, 40408, 40308 };
            int[] counts = { 5, 5, 500 };
            int[] gitems = { 40779 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request silver bar")) {
            int[] items = { 40468, 40308 };
            int[] counts = { 10, 500 };
            int[] gitems = { 40467 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request gold bar")) {
            int[] items = { 40489, 40308 };
            int[] counts = { 10, 1000 };
            int[] gitems = { 40488 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request platinum bar")) {
            int[] items = { 40441, 40308 };
            int[] counts = { 10, 5000 };
            int[] gitems = { 40440 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request silver plate")) {
            int[] items = { 40467, 40779, 40308 };
            int[] counts = { 5, 3, 1000 };
            int[] gitems = { 40469 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request gold plate")) {
            int[] items = { 40488, 40779, 40308 };
            int[] counts = { 5, 3, 3000 };
            int[] gitems = { 40487 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request platinum plate")) {
            int[] items = { 40440, 40779, 40308 };
            int[] counts = { 5, 3, 10000 };
            int[] gitems = { 40439 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request black mithril plate")) {
            int[] items = { 40443, 40497, 40509, 40469, 40487, 40439, 40308 };
            int[] counts = { 10, 1, 1, 1, 1, 1, 10000 };
            int[] gitems = { 40445 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, amount);
        }
        else if (cmd.equalsIgnoreCase("request ancient blue dragon armor")) {
            int[] items = { 20127, 40445, 40051, 40413, 40308 };
            int[] counts = { 1, 3, 30, 5, 50000 };
            int[] gitems = { 20153 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request ancient green dragon armor")) {
            int[] items = { 20146, 40445, 40048, 40162, 40308 };
            int[] counts = { 1, 3, 30, 5, 50000 };
            int[] gitems = { 20130 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request ancient red dragon armor")) {
            int[] items = { 20159, 40445, 40049, 40409, 40308 };
            int[] counts = { 1, 3, 30, 5, 50000 };
            int[] gitems = { 20119 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        else if (cmd.equalsIgnoreCase("request ancient azure dragon armor")) {
            int[] items = { 20156, 40445, 40050, 40169, 40308 };
            int[] counts = { 1, 3, 30, 5, 50000 };
            int[] gitems = { 20108 };
            int[] gcounts = { 1 };
            isCloseList = CreateNewItem.getItem(pc, npc, cmd, items, counts, gitems, gcounts, 1L);
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
}
