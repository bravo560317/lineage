package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.Shutdown;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.datatables.ServerItemDropTable;
import com.lineage.data.event.DropItem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_DropItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_DropItem.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            int x = this.readH();
            int y = this.readH();
            int objectId = this.readD();
            int count = this.readD();
            if (count > Integer.MAX_VALUE) {
                count = Integer.MAX_VALUE;
            }
            count = Math.max(0, count);
            L1PcInstance pc = client.getActiveChar();
            if (!pc.isGhost()) {
                if (!ConfigAlt.DORP_ITEM && !pc.isGm()) {
                    pc.sendPackets(new S_ServerMessage(125));
                }
                else {
                    L1ItemInstance item = pc.getInventory().getItem(objectId);
                    if (item == null) {
                        return;
                    }
                    else if (item.getCount() <= 0L) {
                        return;
                    }
                    else {
                        if (!pc.isGm()) {
                            if (!item.getItem().isTradable()) {
                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                return;
                            }
                            else if (item.getItemId() == 44070 && !pc.isGm()) {
                                pc.sendPackets(new S_ServerMessage("該物品貴重.無法捨棄"));
                                return;
                            }
                            else {
                                if (DropItem.START) {
                                    int maxCount = ServerItemDropTable.get().getMaxCount(item.getItemId());
                                    if (maxCount < 0) {
                                        pc.sendPackets(new S_ServerMessage(125));
                                        return;
                                    }
                                    else if (count > maxCount) {
                                        pc.sendPackets(new S_ServerMessage(166, item.getName() + "最多只能丟棄" + maxCount + "個"));
                                        return;
                                    }
                                }
                                if (item.get_time() != null) {
                                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                    return;
                                }
                                else if (ItemRestrictionsTable.RESTRICTIONS.contains((Integer)item.getItemId())) {
                                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                    return;
                                }
                                else if (pc.get_other().get_item() != null) {
                                    pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中禁止丟棄"));
                                    pc.sendPackets(new S_CloseList(pc.getId()));
                                    pc.get_other().set_item(null);
                                    return;
                                }
                                else if (Shutdown.isSHUTDOWN) {
                                    pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                                    return;
                                }
                            }
                        }
                        RecordTable.get().recorddropitem(pc.getName(), item.getAllName(), count, item.getId(), pc.getIp());
                        Object[] petlist = pc.getPetList().values().toArray();
                        Object[] array;
                        for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                            Object petObject = array[i];
                            if (petObject instanceof L1PetInstance) {
                                L1PetInstance pet = (L1PetInstance)petObject;
                                if (item.getId() == pet.getItemObjId()) {
                                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                    return;
                                }
                            }
                        }
                        if (item.getId() == pc.getQuest().get_step(74)) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
                            return;
                        }
                        else if (pc.getDoll(item.getId()) != null) {
                            pc.sendPackets(new S_ServerMessage(1181));
                            return;
                        }
                        else if (item.isEquipped()) {
                            pc.sendPackets(new S_ServerMessage(125));
                            return;
                        }
                        else if (item.getBless() >= 128) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        else {
                            pc.getInventory().tradeItem(item, count, pc.get_showId(), World.get().getInventory(x, y, pc.getMapId()));
                            item.setdropitemcheck(2);
                            item.setdropitemname(item.getName());
                            pc.turnOnOffLight();
                            return;
                        }
                    }
                }
            }
            return;
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
