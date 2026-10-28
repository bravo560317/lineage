package com.lineage.server.command.executor;

import com.lineage.server.templates.L1CharName;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ResetTime implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ResetTime.class);
    }
    
    private L1ResetTime() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ResetTime();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1CharName[] charNameList;
            for (int length = (charNameList = CharacterTable.get().getCharNameList()).length, i = 0; i < length; ++i) {
                L1CharName charName = charNameList[i];
                L1PcInstance tgpc = World.get().getPlayer(charName.getName());
                if (tgpc != null) {
                    tgpc.resetAllMapTime();
                    tgpc.save();
                    tgpc.sendPackets(new S_ServerMessage("\\fT限時地監的使用時間已重置。"));
                }
                else {
                    L1PcInstance offlinepc = CharacterTable.get().restoreCharacter(charName.getName());
                    offlinepc.resetAllMapTime();
                    offlinepc.save();
                }
            }
            if (pc == null) {
                L1ResetTime._log.warn("系統命令執行: " + cmdName + "重置所有玩家限時地監時間。");
            }
            else {
                pc.sendPackets(new S_SystemMessage("\\aH重置所有玩家限時地監時間。"));
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
