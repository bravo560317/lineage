package com.lineage.data.npc;

import java.util.Map;
import com.add.system.L1BlendTable;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.NpcExecutor;

public class Npc_FireDis extends NpcExecutor
{
    public static NpcExecutor get() {
        return new Npc_FireDis();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "firedis1"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.equalsIgnoreCase("request craft")) {
            this.ShowCraftList(pc, npc);
        }
    }
    
    private void ShowCraftList(L1PcInstance pc, L1NpcInstance npc) {
        String msg0 = "";
        String msg2 = "";
        String msg3 = "";
        String msg4 = "";
        String msg5 = "";
        String msg6 = "";
        String msg7 = "";
        String msg8 = "";
        String msg9 = "";
        String msg10 = "";
        String msg11 = "";
        String msg12 = "";
        String msg13 = "";
        String msg14 = "";
        String msg15 = "";
        String msg16 = "";
        String msg17 = "";
        String msg18 = "";
        String msg19 = "";
        String msg20 = "";
        String msg21 = "";
        String msg22 = "";
        String msg23 = "";
        String msg24 = "";
        String msg25 = "";
        String msg26 = "";
        int npcid = npc.getNpcId();
        Map<String, String> craftlist = L1BlendTable.getInstance().get_craftlist();
        if (!craftlist.isEmpty()) {
            msg0 = craftlist.get(String.valueOf(npcid) + "A");
            msg2 = craftlist.get(String.valueOf(npcid) + "B");
            msg3 = craftlist.get(String.valueOf(npcid) + "C");
            msg4 = craftlist.get(String.valueOf(npcid) + "D");
            msg5 = craftlist.get(String.valueOf(npcid) + "E");
            msg6 = craftlist.get(String.valueOf(npcid) + "F");
            msg7 = craftlist.get(String.valueOf(npcid) + "G");
            msg8 = craftlist.get(String.valueOf(npcid) + "H");
            msg9 = craftlist.get(String.valueOf(npcid) + "I");
            msg10 = craftlist.get(String.valueOf(npcid) + "J");
            msg11 = craftlist.get(String.valueOf(npcid) + "K");
            msg12 = craftlist.get(String.valueOf(npcid) + "L");
            msg13 = craftlist.get(String.valueOf(npcid) + "M");
            msg14 = craftlist.get(String.valueOf(npcid) + "N");
            msg15 = craftlist.get(String.valueOf(npcid) + "O");
            msg16 = craftlist.get(String.valueOf(npcid) + "P");
            msg17 = craftlist.get(String.valueOf(npcid) + "Q");
            msg18 = craftlist.get(String.valueOf(npcid) + "R");
            msg19 = craftlist.get(String.valueOf(npcid) + "S");
            msg20 = craftlist.get(String.valueOf(npcid) + "T");
            msg21 = craftlist.get(String.valueOf(npcid) + "U");
            msg22 = craftlist.get(String.valueOf(npcid) + "V");
            msg23 = craftlist.get(String.valueOf(npcid) + "W");
            msg24 = craftlist.get(String.valueOf(npcid) + "X");
            msg25 = craftlist.get(String.valueOf(npcid) + "Y");
            msg26 = craftlist.get(String.valueOf(npcid) + "Z");
        }
        String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23, msg24, msg25, msg26 };
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "smithitem1", msgs));
    }
}
