package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Calendar;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_GameTime;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_KeepALIVE extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_KeepALIVE.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
                return;
            }
            else {
                if (World.get().getPlayer(pc.getName()) == null && pc.getOnlineStatus() != 1) {
                    client.kick();
                }
                int serverTime = L1GameTimeClock.getInstance().currentTime().getSeconds();
                pc.sendPackets(new S_GameTime(serverTime));
                if (pc.isPrivateShop()) {
                    int mapId = pc.getMapId();
    if (mapId != 340 && mapId != 350 && mapId != 360 && mapId != 370 && mapId != 800
        && mapId != 0 && mapId != 4) {
                        pc.getSellList().clear();
                        pc.getBuyList().clear();
                        pc.setPrivateShop(false);
                        pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 3));
                    }
                }
                if (pc.get_mazu_time() != 0L && pc.is_mazu()) {
                    Calendar cal = Calendar.getInstance();
                    long h_time = cal.getTimeInMillis() / 1000L;
                    if (h_time - pc.get_mazu_time() >= 1800L) {
                        pc.set_mazu_time(0L);
                        pc.set_mazu(false);
                    }
                }
                if (pc._isCraftsmanHeirloom() && pc.castleWarResult()) {
                    L1ItemInstance item = pc.getInventory().findItemId(44216);
                    if (item != null) {
                        pc.getInventory().removeItem(item, 1L);
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
