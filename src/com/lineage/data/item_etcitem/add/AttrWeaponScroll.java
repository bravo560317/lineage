package com.lineage.data.item_etcitem.add;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class AttrWeaponScroll extends ItemExecutor
{
    private int _attr_id;
    private int _stage;
    
    public static ItemExecutor get() {
        return new AttrWeaponScroll();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
        if (tgItem == null) {
            return;
        }
        else if (tgItem.getItem().getType2() != 1) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else if (tgItem.getBless() >= 128) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            int attrEnchantKind = tgItem.getAttrEnchantKind();
            int attrEnchantLevel = tgItem.getAttrEnchantLevel();
            if (attrEnchantKind == this._attr_id && attrEnchantLevel >= this._stage) {
                pc.sendPackets(new S_SystemMessage("武器本身階級已超過卷軸階級。"));
                return;
            }
            else {
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_ServerMessage(1410, tgItem.getLogName()));
                tgItem.setAttrEnchantKind(this._attr_id);
                tgItem.setAttrEnchantLevel(this._stage);
                int column = 3072;
                pc.getInventory().updateItem(tgItem, column);
                pc.getInventory().saveItem(tgItem, column);
                return;
            }
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._attr_id = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._stage = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
    }
}
