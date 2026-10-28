package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Poly implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Poly.class);
    }
    
    private L1Poly() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Poly();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            int polyid = Integer.parseInt(st.nextToken());
            L1PcInstance tg = World.get().getPlayer(name);
            if (tg == null) {
                pc.sendPackets(new S_ServerMessage(73, name));
            }
            else {
                try {
                    L1PolyMorph.doPoly(tg, polyid, 7200, 2);
                }
                catch (Exception exception) {
                    pc.sendPackets(new S_SystemMessage(".poly [人物名稱] [外型代號]"));
                }
            }
        }
        catch (Exception e) {
            L1Poly._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
}
