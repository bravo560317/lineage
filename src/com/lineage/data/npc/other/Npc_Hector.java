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

public class Npc_Hector extends NpcExecutor
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog(Npc_Hector.class);
        Npc_Hector._random = new Random();
    }
    
    public static NpcExecutor get() {
        return new Npc_Hector();
    }
    
    @Override
    public int type() {
        return 19;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        if (pc.getLawful() < 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hector2"));
        }
        else {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "hector1"));
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        boolean isCloseList = false;
        if (cmd.equalsIgnoreCase("request arctic of helm")) {
            int[] items = { 20006, 80021, 41246 };
            int[] counts = { 1, 1, 50000 };
            int[] gitems = { 400047 };
            int[] gcounts = { 1 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                isCloseList = true;
            }
            else {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("request arctic of armor")) {
            int[] items = { 20154, 80023, 41246 };
            int[] counts = { 1, 1, 50000 };
            int[] gitems = { 400045 };
            int[] gcounts = { 1 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                isCloseList = true;
            }
            else {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
        }
        else if (cmd.equalsIgnoreCase("request arctic of boots")) {
            int[] items = { 20205, 80022, 41246 };
            int[] counts = { 1, 1, 50000 };
            int[] gitems = { 400046 };
            int[] gcounts = { 1 };
            if (CreateNewItem.checkNewItem(pc, items, counts) < 1L) {
                isCloseList = true;
            }
            else {
                CreateNewItem.createNewItem(pc, items, counts, gitems, 1L, gcounts);
                isCloseList = true;
            }
        }
        if (!cmd.equalsIgnoreCase("request fifth goods of war")) {
            if (cmd.equalsIgnoreCase("request iron gloves")) {
                int[] items = { 20182, 40408, 40308 };
                int[] counts = { 1, 150, 25000 };
                int[] gitems = { 20163 };
                int[] gcounts = { 1 };
                isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
            }
            else if (cmd.equalsIgnoreCase("request iron visor")) {
                int[] items = { 20006, 40408, 40308 };
                int[] counts = { 1, 120, 16500 };
                int[] gitems = { 20003 };
                int[] gcounts = { 1 };
                isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
            }
            else if (cmd.equalsIgnoreCase("request iron shield")) {
                int[] items = { 20231, 40408, 40308 };
                int[] counts = { 1, 200, 16000 };
                int[] gitems = { 20220 };
                int[] gcounts = { 1 };
                isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
            }
            else if (cmd.equalsIgnoreCase("request iron boots")) {
                int[] items = { 20205, 40408, 40308 };
                int[] counts = { 1, 160, 8000 };
                int[] gitems = { 20194 };
                int[] gcounts = { 1 };
                isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
            }
            else if (cmd.equalsIgnoreCase("request iron plate mail")) {
                int[] items = { 20154, 40408, 40308 };
                int[] counts = { 1, 450, 30000 };
                int[] gitems = { 20091 };
                int[] gcounts = { 1 };
                isCloseList = this.getItem(pc, items, counts, gitems, gcounts);
            }
            else if (cmd.equalsIgnoreCase("request slim plate")) {
                int[] items = { 40408, 40308 };
                int[] counts = { 10, 500 };
                int[] gitems = { 40526 };
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
                int[] items = { 40408, 40308 };
                int[] counts = { 10, 500 };
                int[] gitems = { 40526 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= amount) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
                }
                isCloseList = true;
            }
            else if (cmd.equalsIgnoreCase("request lump of steel")) {
                int[] items = { 40899, 40408, 40308 };
                int[] counts = { 5, 5, 500 };
                int[] gitems = { 40779 };
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
            else if (cmd.equalsIgnoreCase("a2")) {
                int[] items = { 40899, 40408, 40308 };
                int[] counts = { 5, 5, 500 };
                int[] gitems = { 40779 };
                int[] gcounts = { 1 };
                long xcount = CreateNewItem.checkNewItem(pc, items, counts);
                if (xcount >= amount) {
                    CreateNewItem.createNewItem(pc, items, counts, gitems, amount, gcounts);
                }
                isCloseList = true;
            }
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
