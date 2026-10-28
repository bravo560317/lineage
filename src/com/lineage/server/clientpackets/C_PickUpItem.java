package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.config.ConfigTakeitem;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_PickUpItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_PickUpItem.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {}
            if (pc.isDead() || pc.isTeleport() || pc.isPrivateShop() || pc.isInvisble() || pc.isInvisDelay()) {
                return;
            }
            else {
                int x = this.readH();
                int y = this.readH();
                int objectId = this.readD();
                long pickupCount = (long)this.readD();
                if (pickupCount > 2147483647L) {
                    pickupCount = 2147483647L;
                }
                pickupCount = Math.max(0L, pickupCount);
                L1Inventory groundInventory = World.get().getInventory(x, y, pc.getMapId());
                L1Object object = groundInventory.getItem(objectId);
                if (object != null && !pc.isDead()) {
                    L1ItemInstance item = (L1ItemInstance)object;
                    if (item.getCount() <= 0L) {
                        return;
                    }
                    else if (item.getItemOwnerId() != 0 && pc.getId() != item.getItemOwnerId()) {
                        pc.sendPackets(new S_ServerMessage(623));
                        return;
                    }
                    else if (pc.getLocation().getTileLineDistance(item.getLocation()) > 3) {
                        return;
                    }
                    else {
                        item.set_showId(-1);
                        if (pc.getInventory().checkAddItem(item, pickupCount) == 0 && item.getX() != 0 && item.getY() != 0) {
                            if (ConfigOther.Pickupitemtoall && item.getItem().getItemId() != 40515 && item.getdropitemcheck() == 1) {
                                if (item.getItem().getType2() == 2 && item.getEnchantLevel() > 0) {
                                    ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), "+" + item.getEnchantLevel() + item.getItem().getName());
                                }
                                else if (item.getItem().getType2() == 1 && item.getEnchantLevel() > 0) {
                                    ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), "+" + item.getEnchantLevel() + item.getItem().getName());
                                }
                                else if (item.getItem().getType2() == 2 && item.getEnchantLevel() == 0) {
                                    ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), item.getItem().getName());
                                }
                                else if (item.getItem().getType2() == 1 && item.getEnchantLevel() == 0) {
                                    ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), item.getItem().getName());
                                }
                                else {
                                    ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), item.getItem().getName() + "(" + pickupCount + ")");
                                }
                            }
if (item.getItemId() != 40308) {
    C_PickUpItem._log.info("人物:" + pc.getName() + " 撿起物品 " + item.getItem().getName() + " 物品OBJID:" + item.getId());
}
                            groundInventory.tradeItem(item, pickupCount, pc.getInventory());
                            pc.turnOnOffLight();
                            pc.setHeading(pc.targetDirection(item.getX(), item.getY()));
                            if (!pc.isGmInvis()) {
                                pc.broadcastPacketAll(new S_ChangeHeading(pc));
                                pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 15));
                            }
                            RecordTable.get().recordtakeitem(pc.getName(), item.getAllName(), (int)pickupCount, item.getId(), pc.getIp());
                        }
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
