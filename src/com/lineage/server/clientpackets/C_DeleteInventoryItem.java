package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigRate;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_DeleteInventoryItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(C_DeleteInventoryItem.class);
    }
    
    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            int itemObjectId = this.readD();
            L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
            if (item != null && item.getCount() > 0L) {
                int count = this.readD();
                if (count == 0) {
                    count = (int)item.getCount();
                }
                if (!pc.isGm() && item.getItem().isCantDelete()) {
                    pc.sendPackets(new S_ServerMessage(125));
                    return;
                }
                else {
                    Object[] petlist = pc.getPetList().values().toArray();
                    Object[] array;
                    for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                        Object petObject = array[i];
                        if (petObject instanceof L1PetInstance) {
                            L1PetInstance pet = (L1PetInstance)petObject;
                            if (item.getId() == pet.getItemObjId()) {
                                pc.sendPackets(new S_ServerMessage(125));
                                return;
                            }
                        }
                    }
                    if (item.getId() == pc.getQuest().get_step(74)) {
                        pc.sendPackets(new S_ServerMessage(125));
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
                        C_DeleteInventoryItem._log.info("人物:" + pc.getName() + "刪除物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
                     // 若未開啟 DeleItemlog，才記錄刪除物品 log
                        if (!ConfigRate.DeleItemlog) {
                            RecordTable.get().recordDeleItem(pc.getName(), item.getAllName(), (int)item.getCount(), item.getId(), pc.getIp());
                        }
                        pc.getInventory().removeItem(item, (long)count);
                        World.get().removeObject(item);
                    }
                }
            }
            else {
                return;
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
