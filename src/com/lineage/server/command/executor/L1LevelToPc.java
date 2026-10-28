package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.RangeInt;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1LevelToPc implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1LevelToPc.class);
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1LevelToPc();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            String char_name = tok.nextToken();
            int level = Integer.parseInt(tok.nextToken());
            L1PcInstance target = World.get().getPlayer(char_name);
            if (target == null) {
                if (pc == null) {
                    return;
                }
                else {
                    pc.sendPackets(new S_ServerMessage(73, char_name));
                    return;
                }
            }
            else if (level == pc.getLevel()) {
                pc.sendPackets(new S_ServerMessage(166, char_name + "當前已經是" + level + "級"));
                return;
            }
            else if (!RangeInt.includes(level, 1, ExpTable.MAX_LEVEL)) {
                pc.sendPackets(new S_SystemMessage("範圍限制 1~" + ExpTable.MAX_LEVEL));
                return;
            }
            else {
                long nowexp = target.getExp();
                long levelexp = ExpTable.getExpByLevel(level);
                long add = levelexp - nowexp;
                target.setExp(ExpTable.getExpByLevel(level));
                target.onChangeExp();
                pc.sendPackets(new S_ServerMessage(166, char_name + "的等級已經變更為:" + level + "級"));
            }
        }
        catch (Exception e) {
            L1LevelToPc._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
