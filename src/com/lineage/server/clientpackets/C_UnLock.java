package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_Lock;
import com.lineage.echo.ClientExecutor;

public class C_UnLock extends ClientBasePacket
{
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int type = this.readC();
            L1PcInstance pc = client.getActiveChar();
            if (type == 127) {
                int oleLocx = pc.getX();
                int oleLocy = pc.getY();
                pc.setOleLocX(oleLocx);
                pc.setOleLocY(oleLocy);
                pc.sendPackets(new S_Lock());
            }
            else {
                pc.sendPackets(new S_Paralysis(7, false));
                pc.setTeleportX(pc.getOleLocX());
                pc.setTeleportY(pc.getOleLocY());
                pc.setTeleportMapId(pc.getMapId());
                pc.setTeleportHeading(pc.getHeading());
                Teleportation.teleportation(pc);
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
