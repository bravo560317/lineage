package com.lineage.server.command.executor;

import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.templates.DeName;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.datatables.DeNameTable;
import com.lineage.server.datatables.NpcTable;
import java.util.StringTokenizer;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SpawnDe implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SpawnDe.class);
    }
    
    private L1SpawnDe() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1SpawnDe();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            if (arg.startsWith("obj")) {
                StringTokenizer tok = new StringTokenizer(arg);
                int objid = Integer.parseInt(tok.nextToken());
                int time = 0;
                try {
                    time = Integer.parseInt(tok.nextToken());
                }
                catch (Exception ex) {}
                int npcid = 81471;
                L1Npc npc = NpcTable.get().getTemplate(81471);
                if (npc != null) {
                    DeName de = DeNameTable.get().getDeName(objid);
                    L1SpawnUtil.spawn(pc, de, time);
                }
                return;
            }
            else if (arg.startsWith("s")) {
                boolean isShopMap = false;
                switch (pc.getMapId()) {
                    case 340:
                    case 350:
                    case 360:
                    case 370:
                    case 800: {
                        isShopMap = true;
                        break;
                    }
                }
                if (isShopMap) {
                    L1DeInstance de2 = L1SpawnUtil.spawn(pc, null, 0);
                    de2.start_shop();
                }
                else {
                    pc.sendPackets(new S_SystemMessage("目前位置不在商店街。"));
                }
                return;
            }
            else {
                StringTokenizer tok = new StringTokenizer(arg);
                int count = 1;
                if (tok.hasMoreTokens()) {
                    count = Integer.parseInt(tok.nextToken());
                }
                int randomrange = 1;
                if (tok.hasMoreTokens()) {
                    randomrange = Integer.parseInt(tok.nextToken(), 10);
                }
                int npcid = 81471;
                L1Npc npc = NpcTable.get().getTemplate(81471);
                if (npc == null) {
                    pc.sendPackets(new S_SystemMessage("找不到該npc。"));
                    return;
                }
                else if (count > 100) {
                    pc.sendPackets(new S_SystemMessage("一次召喚數量不能超過100。"));
                    return;
                }
                else {
                    if (count > 20) {
                        L1SpawnDe.DeRunnable deRunnable = new L1SpawnDe.DeRunnable(pc, 81471, randomrange, count);
                        GeneralThreadPool.get().execute(deRunnable);
                    }
                    else {
                        for (int i = 0; i < count; ++i) {
                            L1SpawnUtil.spawn(pc, 81471, randomrange, 0);
                        }
                    }
                    String msg = String.format("%s(%d) (%d) 召喚。 (範圍:%d)", npc.get_name(), 81471, (Integer)count, (Integer)randomrange);
                    pc.sendPackets(new S_SystemMessage(msg));
                }
            }
        }
        catch (Exception e) {
            L1SpawnDe._log.error(e.getLocalizedMessage(), e);
            L1SpawnDe._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
    
    private class DeRunnable implements Runnable
    {
        private final L1PcInstance _pc;
        private final int _npcid;
        private final int _randomrange;
        private final int _count;
        
        private DeRunnable(L1PcInstance pc, int npcid, int randomrange, int count) {
            this._pc = pc;
            this._npcid = npcid;
            this._randomrange = randomrange;
            this._count = count;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < this._count; ++i) {
                    if (this._pc.getMapId() == 5300) {
                        L1SpawnUtil.spawn(this._pc, this._npcid, this._randomrange, 0);
                    }
                    else {
                        L1SpawnUtil.spawn(this._pc, this._npcid, this._randomrange, 0);
                    }
                    Thread.sleep(1L);
                }
            }
            catch (InterruptedException e) {
                L1SpawnDe._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
