package com.lineage.data.npc.other;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Herbert extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Herbert.class);
        Npc_Herbert._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Herbert();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "herbert2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "herbert1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("request t-shirt")) {
            int[] items = { 40456, 40455, 40457, 40308 };
            int[] counts = { 3, 2, 10, 30000 };
            int[] gitems = { 20085 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
        }
        else if (cmd.equalsIgnoreCase("request cloak of magic resistance")) {
            int[] items = { 40456, 40455, 40457, 40308 };
            int[] counts = { 10, 2, 1, 1000 };
            int[] gitems = { 20056 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
        }
        else if (cmd.equalsIgnoreCase("request cloak of protection")) {
            int[] items = { 40456, 40455, 40457, 40308 };
            int[] counts = { 5, 5, 10, 20000 };
            int[] gitems = { 20063 };
            int[] gcounts = { 1 };
            isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
        }
        else {
            cmd.equalsIgnoreCase("request sixth goods of war");
        }
        if (isCloseList) {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private boolean getItem(L1PcInstance pc, int[] items, int[] counts, int[] gitems, int[] gcounts) {
        if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
            return true;
        }
        else {
            CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
            return true;
        }
    }
}
