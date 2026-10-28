package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_SelectList extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_SelectList.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!pc.isDead()) {
                    if (!pc.isTeleport()) {
                        if (!pc.isPrivateShop()) {
                            int itemObjectId = this.readD();
                            int npcObjectId = this.readD();
                            if (npcObjectId != 0) {
                                L1Object obj = World.get().findObject(npcObjectId);
                                if (obj != null && obj instanceof L1NpcInstance) {
                                    L1NpcInstance npc = (L1NpcInstance)obj;
                                    int difflocx = Math.abs(pc.getX() - npc.getX());
                                    int difflocy = Math.abs(pc.getY() - npc.getY());
                                    if (difflocx > 5 || difflocy > 5) {
                                        return;
                                    }
                                }
                                L1PcInventory pcInventory = pc.getInventory();
                                L1ItemInstance item = pcInventory.getItem(itemObjectId);
                                int cost = item.get_durability() * 200;
                                if (!pc.getInventory().consumeItem(40308, (long)cost)) {
                                    pc.sendPackets(new S_ServerMessage(189));
                                    return;
                                }
                                else {
                                    item.set_durability(0);
                                    pc.sendPackets(new S_ServerMessage(464, item.getLogName()));
                                    pcInventory.updateItem(item, 1);
                                    return;
                                }
                            }
                            else if (ConfigOther.petcountcha) {
                                pc.petReceive(itemObjectId);
                                return;
                            }
                            else {
                                pc.petReceive1(itemObjectId);
                                return;
                            }
                        }
                    }
                }
            }
            return;
        }
        catch (Exception e) {
            C_SelectList._log.error(e.getLocalizedMessage(), e);
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
