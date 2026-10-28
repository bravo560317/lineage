package com.lineage.server.clientpackets;

import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_Emblem;
import com.lineage.server.world.World;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_EmblemUpload extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_EmblemUpload.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        int clan_id = pc.getClanid();
                        if (clan_id != 0) {
                            if (pc.getClan().getLeaderId() != pc.getId()) {
                                pc.sendPackets(new S_ServerMessage(219));
                                return;
                            }
                            else {
                                L1Clan clan = pc.getClan();
                                if (clan == null) {
                                    return;
                                }
                                else {
                                    byte[] iconByte = this.readByte();
                                    L1EmblemIcon emblemIcon = ClanEmblemReading.get().get(clan_id);
                                    int newemblemid = IdFactory.get().nextId();
                                    clan.setEmblemId(newemblemid);
                                    ClanReading.get().updateClan(pc.getClan());
                                    if (emblemIcon != null) {
                                        emblemIcon.set_emblemid(newemblemid);
                                        emblemIcon.set_clanIcon(iconByte);
                                        emblemIcon.set_update(emblemIcon.get_update() + 1);
                                        ClanEmblemReading.get().updateClanIcon(emblemIcon);
                                    }
                                    else {
                                        emblemIcon = ClanEmblemReading.get().storeClanIcon(clan_id, iconByte, newemblemid);
                                    }
                                    World.get().broadcastPacketToAll(new S_Emblem(emblemIcon));
                                    L1PcInstance[] onlineClanMember;
                                    for (int length = (onlineClanMember = clan.getOnlineClanMember()).length, i = 0; i < length; ++i) {
                                        L1PcInstance clanmember = onlineClanMember[i];
                                        clanmember.sendPacketsAll(new S_CharReset(clanmember.getId(), clan.getEmblemId()));
                                    }
                                    return;
                                }
                            }
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
            C_EmblemUpload._log.error(e.getLocalizedMessage(), e);
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
