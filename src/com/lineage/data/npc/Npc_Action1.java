package com.lineage.data.npc;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_Action1 extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_Action1.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_Action1();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
            String nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            String[] info = { nowDate };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_action_1", info));
        }
        catch (Exception e) {
            Npc_Action1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String s, long amount) {
        if (s.equalsIgnoreCase("y_action0")) {
            this.getScore(pc, 40308, 100000, 83054, 10);
        }
        else if (s.equalsIgnoreCase("y_action1")) {
            this.getScore(pc, 49138, 10, 83054, 10);
        }
        else if (s.equalsIgnoreCase("y_action2")) {
            this.getScore(pc, 44102, 10, 83054, 100);
        }
        else if (s.equalsIgnoreCase("y_action3")) {
            this.getScore(pc, 80280, 1, 83054, 1000);
        }
    }
    
    private void getScore(L1PcInstance pc, int toitemid, int toitemcount, int needitemid, int needcount) {
        try {
            L1ItemInstance toitem = ItemTable.get().createItem(toitemid);
            L1ItemInstance needitem = pc.getInventory().checkItemX(needitemid, (long)needcount);
            if (needitem == null) {
                pc.sendPackets(new S_ServerMessage(" \\fR材料不足!"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                if (toitem != null) {
                    toitem.setCount((long)toitemcount);
                    if (pc.getInventory().checkAddItem(toitem, (long)toitemcount) == 0) {
                        pc.getInventory().storeItem(toitem);
                        pc.sendPackets(new S_ServerMessage(403, toitem.getLogName()));
                        pc.getInventory().removeItem(needitem, (long)needcount);
                        this.toGmMsg2(pc, toitem);
                    }
                }
                pc.sendPackets(new S_CloseList(pc.getId()));
            }
        }
        catch (Exception e) {
            Npc_Action1._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void toGmMsg2(L1PcInstance pc, L1ItemInstance element) {
        try {
            Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            for (L1PcInstance tgpc : allPc) {
                if (tgpc.isGm()) {
                    StringBuilder topc = new StringBuilder();
                    topc.append("人物:" + pc.getName() + " 兌換道具:" + element.getLogName());
                    tgpc.sendPackets(new S_ServerMessage(166, topc.toString()));
                }
            }
        }
        catch (Exception e) {
            Npc_Action1._log.error(e.getLocalizedMessage(), e);
        }
    }
}
