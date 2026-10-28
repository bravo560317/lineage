package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Trade;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_TradeCancel extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_TradeCancel.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance player = client.getActiveChar();
            L1Trade trade = new L1Trade();
            trade.tradeCancel(player);
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
