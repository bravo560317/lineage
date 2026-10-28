package com.lineage.server.command.executor;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1Color implements L1CommandExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1Color.class);
    }
    
    private L1Color() {
    }
    
    public static L1CommandExecutor getInstance() {
        return new L1Color();
    }
    
    @Override
    public void execute(L1PcInstance pc, String cmdName, String arg) {
        try {
            L1Color.ColorTimeController colorTime = new L1Color.ColorTimeController(pc);
            GeneralThreadPool.get().execute(colorTime);
        }
        catch (Exception e) {
            L1Color._log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
            pc.sendPackets(new S_ServerMessage(261));
        }
    }
    
    private class ColorTimeController implements Runnable
    {
        private L1PcInstance _pc;
        int _mode;
        
        public ColorTimeController(L1PcInstance pc) {
            this._mode = 0;
            this._pc = pc;
        }
        
        @Override
        public void run() {
            try {
                while (this._pc.isGm()) {
                    if (this._pc.getOnlineStatus() != 1) {
                        break;
                    }
                    else if (this._pc.getNetConnection() == null) {
                        break;
                    }
                    else if (!this._pc.isGm()) {
                        this._pc.sendPacketsAll(new S_ChangeName(this._pc.getId(), this._pc.getName()));
                        break;
                    }
                    else {
                        ++this._mode;
                        if (this._mode > 10) {
                            this._mode = 0;
                        }
                        this._pc.sendPacketsAll(new S_ChangeName(this._pc.getId(), this._pc.getName(), this._mode));
                        this._pc.sendPacketsX8(new S_SkillSound(this._pc.getId(), 5288));
                        Thread.sleep(5000L);
                    }
                }
            }
            catch (InterruptedException e) {
                L1Color._log.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
