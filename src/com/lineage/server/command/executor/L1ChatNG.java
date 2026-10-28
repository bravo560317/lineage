package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1ChatNG implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1ChatNG.class);
    }
    
    private L1ChatNG() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1ChatNG();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            int time = Integer.parseInt(st.nextToken());
            L1PcInstance tg = World.get().getPlayer(name);
            if (tg != null) {
                tg.setSkillEffect(4002, time * 60 * 1000);
                tg.sendPackets(new S_PacketBox(36, time * 60));
                tg.sendPackets(new S_ServerMessage(286, String.valueOf(time)));
                pc.sendPackets(new S_ServerMessage(287, name));
            }
            else {
                pc.sendPackets(new S_ServerMessage(73, name));
            }
        }
        catch (Exception e) {
            L1ChatNG._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
