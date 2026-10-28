package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.model.L1TownLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1HouseLocation;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.echo.ClientExecutor;

public class C_AddBookmark extends ClientBasePacket
{
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            else {
                String locName = this.readS();
                if (pc.getMap().isMarkable() || pc.isGm()) {
                    if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId()) || L1HouseLocation.isInHouse(pc.getX(), pc.getY(), pc.getMapId())) {
                        pc.sendPackets(new S_ServerMessage(214));
                    }
                    else if (L1TownLocation.isGambling(pc)) {
                        pc.sendPackets(new S_ServerMessage(214));
                        return;
                    }
                    else {
                        CharBookReading.get().addBookmark(pc, locName);
                    }
                }
                else {
                    pc.sendPackets(new S_ServerMessage(214));
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
