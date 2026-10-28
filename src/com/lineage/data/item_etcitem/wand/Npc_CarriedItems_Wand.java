package com.lineage.data.item_etcitem.wand;

import com.lineage.server.templates.L1Item;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.text.NumberFormat;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1Drop;
import com.lineage.server.datatables.DropTable;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Npc_CarriedItems_Wand extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Npc_CarriedItems_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        this.useNpcCarriedItemsWand(pc, spellsc_objid, item);
    }
    
    private void useNpcCarriedItemsWand(L1PcInstance pc, int targetId, L1ItemInstance item) {
        L1Object target = World.get().findObject(targetId);
        if (target == null) {
            return;
        }
        else {
            pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 17));
            if (target instanceof L1MonsterInstance) {
                L1NpcInstance tgnpc = (L1MonsterInstance)target;
                ArrayList<L1Drop> dropList = DropTable.get().getdroplist(tgnpc.getNpcId());
                String[] itemdatas = null;
                if (dropList != null) {
                    itemdatas = new String[dropList.size()];
                    for (int i = 0; i < dropList.size(); ++i) {
                        L1Drop drop = dropList.get(i);
                        L1Item dropitem = ItemTable.get().getTemplate(drop.getItemid());
                        double dropchance = (double)drop.getChance() / 10000.0;
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMaximumFractionDigits(6);
                        itemdatas[i] = dropitem.getName() + " 機率：" + nf.format(dropchance) + "%";
                    }
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "mobdroplist", itemdatas));
                }
                else {
                    pc.sendPackets(new S_SystemMessage("此怪物沒有掉寶資料。"));
                }
                int newchargecount = item.getChargeCount() - 1;
                item.setChargeCount(newchargecount);
                pc.getInventory().updateItem(item, 128);
                if (newchargecount <= 0) {
                    pc.getInventory().deleteItem(item);
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("只能對怪物使用。"));
            }
            return;
        }
    }
}
