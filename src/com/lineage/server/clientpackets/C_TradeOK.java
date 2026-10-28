package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Trade;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_TradeOK extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_TradeOK.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance player = client.getActiveChar();
            L1PcInstance trading_partner = (L1PcInstance)World.get().findObject(player.getTradeID());
            if (trading_partner != null) {
                player.setTradeOk(true);
                if (player.getTradeOk() && trading_partner.getTradeOk()) {
                    if (player.getInventory().getSize() < 170 && trading_partner.getInventory().getSize() < 170) {
                        L1Trade trade = new L1Trade();
                        trade.tradeOK(player);
                    }
                    else {
                        player.sendPackets(new S_ServerMessage(263));
                        trading_partner.sendPackets(new S_ServerMessage(263));
                        L1Trade trade = new L1Trade();
                        trade.tradeCancel(player);
                    }
                }
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
