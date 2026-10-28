package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.datatables.sql.ClanTable;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_PledgeContent extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_PledgeContent.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                int clan_id = pc.getClanid();
                if (clan_id == 0) {
                    return;
                }
                else {
                    int data = this.readC();
                    if (data == 15) {
                        String announce = this.readS();
                        L1Clan clan = ClanReading.get().getTemplate(clan_id);
                        clan.setAnnouncement(announce);
                        ClanTable.getInstance().updateClan(clan);
                        pc.sendPackets(new S_PacketBox(168, announce));
                    }
                    else if (data == 16) {
                        pc.sendPackets(new S_ServerMessage("目前不開放玩家修改備註!!"));
                    }
                }
            }
        }
        catch (Exception e) {
            C_PledgeContent._log.error(e.getLocalizedMessage(), e);
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
