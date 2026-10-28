package com.lineage.server.clientpackets;

import java.util.Calendar;
import com.lineage.server.templates.L1Castle;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_WarTime;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ChangeWarTime extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Buddy.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                int castle_id = clan.getCastleId();
                if (castle_id != 0) {
                    L1Castle l1castle = CastleReading.get().getCastleTable(castle_id);
                    Calendar cal = l1castle.getWarTime();
                    pc.sendPackets(new S_WarTime(cal));
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
