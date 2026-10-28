package com.lineage.server.command.executor;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_PacketBoxGm;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1AllAINPC2 implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1AllAINPC2.class);
    }
    
    private L1AllAINPC2() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1AllAINPC2();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            if (name.equalsIgnoreCase("all")) {
                L1AllAINPC2.AllBuffRunnable allBuffRunnable = new L1AllAINPC2.AllBuffRunnable();
                GeneralThreadPool.get().execute(allBuffRunnable);
                pc.sendPackets(new S_ServerMessage("已送出"));
                return;
            }
            else {
                L1PcInstance target = World.get().getPlayer(name);
                if (target == null) {
                    pc.sendPackets(new S_ServerMessage(73, name));
                    return;
                }
                else {
                    startPc(target);
                }
            }
        }
        catch (Exception e) {
            int mode = 4;
            pc.sendPackets(new S_PacketBoxGm(pc, 4));
        }
    }
    
    public static void startPc(L1PcInstance target) {
        if (!target.hasSkillEffect(98765)) {
            target.setSkillEffect(98765, 120000);
        }
    }
    
    private class AllBuffRunnable implements Runnable
    {
        @Override
        public void run() {
            try {
                for (L1PcInstance tgpc : World.get().getAllPlayers()) {
                    L1AllAINPC2.startPc(tgpc);
                    Thread.sleep(1L);
                }
            }
            catch (Exception e) {
                L1AllAINPC2._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
