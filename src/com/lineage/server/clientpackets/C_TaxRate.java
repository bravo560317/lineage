package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1Castle;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_TaxRate extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_TaxRate.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int i = this.readD();
            int j = this.readC();
            L1PcInstance player = client.getActiveChar();
            if (i == player.getId()) {
                L1Clan clan = WorldClan.get().getClan(player.getClanname());
                if (clan != null) {
                    int castle_id = clan.getCastleId();
                    if (castle_id != 0) {
                        L1Castle l1castle = CastleReading.get().getCastleTable(castle_id);
                        if (j >= 10 && j <= 50) {
                            l1castle.setTaxRate(j);
                            CastleReading.get().updateCastle(l1castle);
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
