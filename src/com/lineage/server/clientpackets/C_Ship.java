package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Ship extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_Ship.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            int mapId = pc.getMapId();
            int item_id = 0;
            switch (mapId) {
                case 5: {
                    item_id = 40299;
                    break;
                }
                case 6: {
                    item_id = 40298;
                    break;
                }
                case 83: {
                    item_id = 40300;
                    break;
                }
                case 84: {
                    item_id = 40301;
                    break;
                }
                case 446: {
                    item_id = 40303;
                    break;
                }
                case 447: {
                    item_id = 40302;
                    break;
                }
            }
            if (item_id != 0) {
                pc.getInventory().consumeItem(item_id, 1L);
                int shipMapId = this.readH();
                int locX = this.readH();
                int locY = this.readH();
                pc.sendPackets(new S_OwnCharPack(pc));
                L1Teleport.teleport(pc, locX, locY, (short)shipMapId, 0, false);
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
