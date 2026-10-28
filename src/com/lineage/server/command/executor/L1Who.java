package com.lineage.server.command.executor;

import java.util.Collection;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.WorldIllusionist;
import com.lineage.server.world.WorldDragonKnight;
import com.lineage.server.world.WorldDarkelf;
import com.lineage.server.world.WorldWizard;
import com.lineage.server.world.WorldElf;
import com.lineage.server.world.WorldKnight;
import com.lineage.server.world.WorldCrown;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Who implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Who.class);
    }
    
    private L1Who() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Who();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            Collection<L1PcInstance> players = World.get().getAllPlayers();
            String amount = String.valueOf(players.size());
            int a = WorldCrown.get().map().size();
            int b = WorldKnight.get().map().size();
            int c = WorldElf.get().map().size();
            int d = WorldWizard.get().map().size();
            int e = WorldDarkelf.get().map().size();
            int f = WorldDragonKnight.get().map().size();
            int g = WorldIllusionist.get().map().size();
            if (pc == null) {
                L1Who._log.warn("系統命令執行: who");
                L1Who._log.info("[王族]:" + a);
                L1Who._log.info("[騎士]:" + b);
                L1Who._log.info("[妖精]:" + c);
                L1Who._log.info("[法師]:" + d);
                L1Who._log.info("[黑妖]:" + e);
                L1Who._log.info("[龍騎]:" + f);
                L1Who._log.info("[幻術]:" + g);
            }
            else {
                pc.sendPackets(new S_ServerMessage("目前線上有: " + amount));
                pc.sendPackets(new S_ServerMessage("王族:" + a));
                pc.sendPackets(new S_ServerMessage("騎士:" + b));
                pc.sendPackets(new S_ServerMessage("妖精:" + c));
                pc.sendPackets(new S_ServerMessage("法師:" + d));
                pc.sendPackets(new S_ServerMessage("黑妖:" + e));
                pc.sendPackets(new S_ServerMessage("龍騎:" + f));
                pc.sendPackets(new S_ServerMessage("幻術:" + g));
            }
        }
        catch (Exception e2) {
            if (pc == null) {
                L1Who._log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
            }
            else {
                L1Who._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
                pc.sendPackets(new S_ServerMessage(261));
            }
        }
    }
}
