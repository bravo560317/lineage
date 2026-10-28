package com.lineage.data.npc.shop;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.serverpackets.S_PowerItemList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.templates.L1Item;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.datatables.lock.CharShiftingReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.datatables.ItemTable;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.templates.L1ItemUpdate;
import com.lineage.server.datatables.ItemUpdateTable;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

public class Npc_ItemUpdate extends NpcExecutor
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(Npc_ItemUpdate.class);
    }
    
    private Npc_ItemUpdate() {
    }
    
    public static NpcExecutor get() {
        return new Npc_ItemUpdate();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
    @Override
    public void talk(L1PcInstance pc, L1NpcInstance npc) {
        pc.set_mode_id(0);
        pc.set_check_item(false);
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_up_i0"));
    }
    
    @Override
    public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
        if (cmd.matches("[0-9]+")) {
            if (pc.get_mode_id() != 0 && !pc.get_check_item()) {
                this.check_item(pc, npc, cmd);
            }
        }
        else if (cmd.equalsIgnoreCase("up")) {
            pc.set_mode_id(0);
            this.show_item(pc, npc);
        }
        else if (cmd.startsWith("ut") && pc.get_check_item()) {
            this.get_item(pc, npc, cmd);
        }
    }
    
    private void get_item(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        try {
            int index = Integer.parseInt(cmd.substring(2));
            L1ItemInstance tgitem = pc.getInventory().getItem(pc.get_mode_id());
            if (tgitem == null) {
                Npc_ItemUpdate._log.error("升級道具 對象 OBJID異常 背包中無該道具:" + pc.get_mode_id());
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                ArrayList<L1ItemUpdate> list = ItemUpdateTable.get().get(tgitem.getItemId());
                L1ItemUpdate newitem = list.get(index);
                if (newitem == null) {
                    Npc_ItemUpdate._log.error("升級道具 對象 ITEMID 找不到升級模組:" + tgitem.getItemId());
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    int toid = newitem.get_toid();
                    int[] needids = newitem.get_needids();
                    int[] needcounts = newitem.get_needcounts();
                    if (CreateNewItem.checkNewItem(pc, needids, needcounts) >= 1L) {
                        if (CreateNewItem.delItems(pc, needids, needcounts, 1L)) {
                            L1Item l1item = ItemTable.get().getTemplate(toid);
                            String src_name = tgitem.getItem().getName();
                            pc.sendPackets(new S_DeleteInventoryItem(tgitem.getId()));
                            tgitem.setItemId(toid);
                            tgitem.setItem(l1item);
                            tgitem.setBless(tgitem.getBless());
                            CharItemsReading.get().updateItemId_Name(tgitem);
                            pc.sendPackets(new S_AddItem(tgitem));
                            pc.sendPackets(new S_ServerMessage(403, tgitem.getLogName()));
                            CharShiftingReading.get().newShifting(pc, 0, src_name, tgitem.getId(), tgitem.getItem(), tgitem, 0);
                        }
                    }
                    else {
                        pc.sendPackets(new S_CloseList(pc.getId()));
                    }
                }
            }
        }
        catch (Exception e) {
            Npc_ItemUpdate._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void check_item(L1PcInstance pc, L1NpcInstance npc, String cmd) {
        try {
            int index = Integer.parseInt(cmd);
            L1ItemInstance tgitem = pc.getInventory().getItem(pc.get_mode_id());
            if (tgitem == null) {
                Npc_ItemUpdate._log.error("升級道具 對象 OBJID異常 背包中無該道具:" + pc.get_mode_id());
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                ArrayList<L1ItemUpdate> list = ItemUpdateTable.get().get(tgitem.getItemId());
                L1ItemUpdate newitem = list.get(index);
                if (newitem == null) {
                    Npc_ItemUpdate._log.error("升級道具 對象 ITEMID 找不到升級模組:" + tgitem.getItemId());
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    return;
                }
                else {
                    int toids = newitem.get_toid();
                    int[] needids = newitem.get_needids();
                    int[] needcounts = newitem.get_needcounts();
                    L1Item getitem = ItemTable.get().getTemplate(toids);
                    String need = "";
                    for (int i = 0; i < needids.length; ++i) {
                        L1Item l1item = ItemTable.get().getTemplate(needids[i]);
                        need = need + l1item.getName() + "(" + needcounts[i] + ")  ";
                    }
                    String[] date = { tgitem.getItem().getName(), getitem.getName(), need };
                    pc.set_check_item(true);
                    pc.sendPackets(new S_ItemCount(npc.getId(), 1, 1, "y_up_i2", "ut" + index, date));
                }
            }
        }
        catch (Exception e) {
            Npc_ItemUpdate._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void show_item(L1PcInstance pc, L1NpcInstance npc) {
        try {
            ArrayList<L1ItemInstance> list = new ArrayList();
            List<L1ItemInstance> items = pc.getInventory().getItems();
            for (L1ItemInstance item : items) {
                if (item.getItem().isStackable()) {
                    continue;
                }
                else if (item.isEquipped()) {
                    continue;
                }
                else if (ItemUpdateTable.get().get(item.getItemId()) == null) {
                    continue;
                }
                else {
                    list.add(item);
                }
            }
            if (list.size() <= 0) {
                pc.sendPackets(new S_SystemMessage("找不到可以升級的裝備。"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            else {
                pc.sendPackets(new S_PowerItemList(pc, npc.getId(), list));
            }
        }
        catch (Exception e) {
            Npc_ItemUpdate._log.error(e.getLocalizedMessage(), e);
        }
    }
}
