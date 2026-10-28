package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PrivateShop;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_ShopList extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_ShopList.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport() || pc.isPrivateShop()) {
                return;
            }
            else {
                int mapId = pc.getMapId();
                boolean isShopMap = false;
				                if (mapId == 0) {
                    isShopMap = true;
                }
				                if (mapId == 4) {
                    isShopMap = true;
                }
                if (mapId == 340) {
                    isShopMap = true;
                }
                if (mapId == 350) {
                    isShopMap = true;
                }
                if (mapId == 360) {
                    isShopMap = true;
                }
                if (mapId == 370) {
                    isShopMap = true;
                }
                if (mapId == 800) {
                    isShopMap = true;
                }
                if (isShopMap) {
                    int type = this.readC();
                    int objectId = this.readD();
                    pc.sendPackets(new S_PrivateShop(pc, objectId, type));
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
