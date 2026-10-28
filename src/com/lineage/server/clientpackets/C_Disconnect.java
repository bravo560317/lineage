package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Disconnect extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Disconnect.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        this.over();
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
