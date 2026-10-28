package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ExitGhost extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ExitGhost.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                return;
            }
            else {
                pc.makeReadyEndGhost();
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
