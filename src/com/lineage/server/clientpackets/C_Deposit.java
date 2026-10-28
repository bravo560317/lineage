package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Castle;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Deposit extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Deposit.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int objid = this.readD();
            long count = (long)this.readD();
            if (count > 2147483647L) {
                count = 2147483647L;
            }
            count = Math.max(0L, count);
            L1PcInstance player = client.getActiveChar();
            if (objid == player.getId()) {
                L1Clan clan = WorldClan.get().getClan(player.getClanname());
                if (clan != null) {
                    int castle_id = clan.getCastleId();
                    if (castle_id != 0) {
                        L1Castle l1castle = CastleReading.get().getCastleTable(castle_id);
                        synchronized (l1castle) {
                            long money = l1castle.getPublicMoney();
                            if (player.getInventory().consumeItem(40308, count)) {
                                money += count;
                                l1castle.setPublicMoney(money);
                                CastleReading.get().updateCastle(l1castle);
                            }
                        }
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
