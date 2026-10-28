package com.lineage.server.clientpackets;

import com.lineage.server.templates.DeClan;
import com.lineage.server.model.L1Clan;
import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.DeClanTable;
import com.lineage.server.serverpackets.S_Emblem;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_EmblemDownload extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_EmblemDownload.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        int emblemid = this.readD();
                        L1EmblemIcon emblemIcon = ClanEmblemReading.get().getEmblem(emblemid);
                        L1Clan clan = ClanReading.get().getTemplate(emblemIcon.get_clanid());
                        if (clan != null && emblemIcon != null) {
                            pc.sendPackets(new S_Emblem(emblemIcon));
                        }
                        L1EmblemIcon emblemIcon2 = ClanEmblemReading.get().getEmblem(emblemid);
                        DeClan deClan = DeClanTable.get().get(emblemIcon2.get_clanid());
                        if (deClan != null && emblemIcon2 != null) {
                            pc.sendPackets(new S_Emblem(emblemIcon2));
                            return;
                        }
                        else {
                            return;
                        }
                    }
                }
            }
            return;
        }
        catch (Exception e) {
            C_EmblemDownload._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
