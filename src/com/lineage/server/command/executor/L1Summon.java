package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.NpcTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Summon implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Summon.class);
    }
    
    private L1Summon() {
    }
    
    public static L1Summon getInstance() {
        return new L1Summon();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            String nameid = tok.nextToken();
            int npcid = 0;
            try {
                npcid = Integer.parseInt(nameid);
            }
            catch (NumberFormatException e) {
                npcid = NpcTable.get().findNpcIdByNameWithoutSpace(nameid);
                if (npcid <= 0) {
                    pc.sendPackets(new S_ServerMessage(166, "錯誤的NPCID: " + npcid));
                    return;
                }
            }
            int count = 1;
            if (tok.hasMoreTokens()) {
                count = Integer.parseInt(tok.nextToken());
            }
            if (count > 5) {
                pc.sendPackets(new S_SystemMessage("寵物召喚數量一次禁止超過5隻。"));
                return;
            }
            else {
                L1Npc npc = NpcTable.get().getTemplate(npcid);
                for (int i = 0; i < count; ++i) {
                    L1PetInstance pet = new L1PetInstance(npc, pc);
                    pet.setPetcost(0);
                }
                nameid = NpcTable.get().getTemplate(npcid).get_name();
                pc.sendPackets(new S_ServerMessage(166, nameid + "(ID:" + npcid + ") 數量:" + count + " 完成召喚"));
            }
        }
        catch (Exception e2) {
            L1Summon._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
