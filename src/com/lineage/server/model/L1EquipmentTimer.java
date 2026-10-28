package com.lineage.server.model;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.TimerTask;

public class L1EquipmentTimer extends TimerTask
{
    private final L1PcInstance _pc;
    private final L1ItemInstance _item;
    
    public L1EquipmentTimer(L1PcInstance pc, L1ItemInstance item) {
        this._pc = pc;
        this._item = item;
    }
    
    @Override
    public void run() {
        if (this._item.getRemainingTime() - 1 >= 0) {
            this._item.setRemainingTime(this._item.getRemainingTime() - 1);
            this._pc.getInventory().updateItem(this._item, 256);
            if (this._pc.getOnlineStatus() == 0) {
                this.cancel();
            }
        }
        else {
            this._pc.sendPackets(new S_ServerMessage(this._item.getItem().getNameId() + "的使用時間已結束。"));
            if (this._item.isEquipped()) {
                this._pc.getInventory().setEquipped(this._item, false);
            }
            if (this._pc.getDoll(this._item.getId()) != null) {
                this._pc.getDoll(this._item.getId()).deleteDoll();
            }
            this.cancel();
        }
    }
}
