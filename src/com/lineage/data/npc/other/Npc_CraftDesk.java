package com.lineage.data.npc.other;

import java.util.Map;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.add.system.L1BlendTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_CraftDesk extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_CraftDesk.class);
    }
    
    public static NpcExecutor get() {
        return new Npc_CraftDesk();
    }
    
    @Override
    public int type() {
        return 1;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        try {
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
            String msg27 = "";
            String msg28 = "";
            String msg29 = "";
            String msg30 = "";
            String msg31 = "";
            String msg32 = "";
            String msg33 = "";
            String msg34 = "";
            String msg35 = "";
            String msg36 = "";
            String msg37 = "";
            String msg38 = "";
            String msg39 = "";
            String msg40 = "";
            String msg41 = "";
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
                msg27 = craftlist.get(String.valueOf(npcid) + "a1");
                msg28 = craftlist.get(String.valueOf(npcid) + "a2");
                msg29 = craftlist.get(String.valueOf(npcid) + "a3");
                msg30 = craftlist.get(String.valueOf(npcid) + "a4");
                msg31 = craftlist.get(String.valueOf(npcid) + "a5");
                msg32 = craftlist.get(String.valueOf(npcid) + "a6");
                msg33 = craftlist.get(String.valueOf(npcid) + "a7");
                msg34 = craftlist.get(String.valueOf(npcid) + "a8");
                msg35 = craftlist.get(String.valueOf(npcid) + "a9");
                msg36 = craftlist.get(String.valueOf(npcid) + "a10");
                msg37 = craftlist.get(String.valueOf(npcid) + "a11");
                msg38 = craftlist.get(String.valueOf(npcid) + "a12");
                msg39 = craftlist.get(String.valueOf(npcid) + "a13");
                msg40 = craftlist.get(String.valueOf(npcid) + "a14");
                msg41 = craftlist.get(String.valueOf(npcid) + "a15");
            }
            String[] msgs = { msg0, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17, msg18, msg19, msg20, msg21, msg22, msg23, msg24, msg25, msg26, msg27, msg28, msg29, msg30, msg31, msg32, msg33, msg34, msg35, msg36, msg37, msg38, msg39, msg40, msg41 };
            if (msg0 != null) {
                pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "smithitem1", msgs));
            }
            else {
                pc.sendPackets(new S_SystemMessage("沒有可以製作的道具。"));
                return;
            }
        }
        catch (Exception e) {
            Npc_CraftDesk._log.error(e.getLocalizedMessage(), e);
        }
    }
}
