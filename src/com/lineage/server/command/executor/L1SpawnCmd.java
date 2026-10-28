package com.lineage.server.command.executor;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.util.NoSuchElementException;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.thread.GeneralThreadPool;
import java.util.StringTokenizer;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SpawnCmd implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1SpawnCmd.class);
    }
    
    private L1SpawnCmd() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1SpawnCmd();
    }
    
    private void sendErrorMessage(L1PcInstance pc, String cmdName) {
        String errorMsg = cmdName + " npcid|name [數量] [範圍]。";
        pc.sendPackets(new S_SystemMessage(errorMsg));
    }
    
    private int parseNpcId(String nameId) {
        int npcid = 0;
        try {
            npcid = Integer.parseInt(nameId);
        }
        catch (NumberFormatException e) {
            npcid = NpcTable.get().findNpcIdByNameWithoutSpace(nameId);
        }
        return npcid;
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            String nameId = tok.nextToken();
            int count = 1;
            if (tok.hasMoreTokens()) {
                count = Integer.parseInt(tok.nextToken());
            }
            int randomrange = 0;
            if (tok.hasMoreTokens()) {
                randomrange = Integer.parseInt(tok.nextToken(), 10);
            }
            int npcid = this.parseNpcId(nameId);
            L1Npc npc = NpcTable.get().getTemplate(npcid);
            if (npc == null) {
                pc.sendPackets(new S_SystemMessage("找不到該npc。"));
                return;
            }
            else if (count > 1000) {
                pc.sendPackets(new S_SystemMessage("一次召喚數量不能超過1000。"));
                return;
            }
            else {
                if (count > 100) {
                    L1SpawnCmd.SpawnRunnable spawnRunnable = new L1SpawnCmd.SpawnRunnable(pc, npcid, randomrange, count);
                    GeneralThreadPool.get().execute(spawnRunnable);
                }
                else {
                    for (int i = 0; i < count; ++i) {
                        L1SpawnUtil.spawn(pc, npcid, randomrange, 0);
                    }
                }
                String msg = String.format("%s(%d) (%d) 召喚。 (範圍:%d)", npc.get_name(), (Integer)npcid, (Integer)count, (Integer)randomrange);
                pc.sendPackets(new S_SystemMessage(msg));
            }
        }
        catch (NoSuchElementException e) {
            this.sendErrorMessage(pc, cmdName);
        }
        catch (NumberFormatException e2) {
            this.sendErrorMessage(pc, cmdName);
        }
        catch (Exception e3) {
            L1SpawnCmd._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
    
    private class SpawnRunnable implements Runnable
    {
        private L1PcInstance _pc;
        private int _npcid;
        private int _randomrange;
        private int _count;
        
        private SpawnRunnable(L1PcInstance pc, int npcid, int randomrange, int count) {
            this._pc = pc;
            this._npcid = npcid;
            this._randomrange = randomrange;
            this._count = count;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < this._count; ++i) {
                    L1SpawnUtil.spawn(this._pc, this._npcid, this._randomrange, 0);
                    Thread.sleep(1L);
                }
            }
            catch (InterruptedException e) {
                L1SpawnCmd._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
