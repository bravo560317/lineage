package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_SystemMessage;
import java.util.logging.Level;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.datatables.NpcSpawnTable;
import com.lineage.server.datatables.SpawnTable;
import com.lineage.server.datatables.NpcTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.logging.Logger;

public class L1InsertSpawn implements L1CommandExecutor
{
    private static Logger _log;
    
    static {
        L1InsertSpawn._log = Logger.getLogger(L1InsertSpawn.class.getName());
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1InsertSpawn();
    }
    
    private L1InsertSpawn() {
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        String msg = null;
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            String type = tok.nextToken();
            int npcId = Integer.parseInt(tok.nextToken().trim());
            L1Npc template = NpcTable.get().getTemplate(npcId);
            if (template == null) {
                msg = "找不到符合條件的NPC。";
            }
            else {
                if (type.equalsIgnoreCase("mob")) {
                    if (!template.getImpl().equals("L1Monster")) {
                        msg = "指定的NPC不是L1Monster類型。";
                        return;
                    }
                    else {
                        SpawnTable.storeSpawn(pc, template);
                    }
                }
                else if (type.equalsIgnoreCase("npc")) {
                    NpcSpawnTable.get().storeSpawn(pc, template);
                }
                L1SpawnUtil.spawn(pc, npcId, 0, 0);
                msg = template.get_name() + (" (" + npcId + ") ") + "新增到資料庫中。";
                return;
            }
            return;
        }
        catch (Exception e) {
            L1InsertSpawn._log.log(Level.SEVERE, "", e);
            msg = "請輸入 : " + cmdName + " mob|npc NPCID 。";
        }
        finally {
            if (msg != null) {
                pc.sendPackets(new S_SystemMessage(msg));
            }
        }
    }
}
