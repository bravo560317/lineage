package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Castle;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Drawal extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Drawal.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        int objid = this.readD();
                        long count = (long)this.readD();
                        if (count > 2147483647L) {
                            count = 2147483647L;
                        }
                        count = Math.max(0L, count);
                        L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                        if (clan != null) {
                            int castle_id = clan.getCastleId();
                            if (castle_id != 0) {
                                if (!pc.isCrown()) {
                                    pc.sendPackets(new S_ServerMessage(518));
                                    return;
                                }
                                else if (pc.getId() != clan.getLeaderId()) {
                                    return;
                                }
                                else {
                                    L1Castle l1castle = CastleReading.get().getCastleTable(castle_id);
                                    L1ItemInstance item = ItemTable.get().createItem(40308);
                                    long money = l1castle.getPublicMoney() - count;
                                    if (item != null && money >= 0L) {
                                        l1castle.setPublicMoney(money);
                                        CastleReading.get().updateCastle(l1castle);
                                        if (pc.getInventory().checkAddItem(item, count) == 0) {
                                            pc.getInventory().storeItem(40308, count);
                                        }
                                        pc.sendPackets(new S_ServerMessage(143, "$457", "$4 (" + count + ")"));
                                        return;
                                    }
                                    else {
                                        return;
                                    }
                                }
                            }
                            else {
                                return;
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
            C_Drawal._log.error(e.getLocalizedMessage(), e);
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
