package com.lineage.data.item_etcitem;

import com.lineage.server.templates.L1Account;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Ancient_Jade extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Ancient_Jade();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc != null && item != null) {
            L1Account account = pc.getNetConnection().getAccount();
            if (account == null) {
                pc.sendPackets(new S_ServerMessage(79));
                return;
            }
            else {
                int characterSlot = account.get_character_slot();
                int maxAmount = ConfigAlt.DEFAULT_CHARACTER_SLOT + characterSlot;
                if (maxAmount >= 8) {
                    pc.sendPackets(new S_ServerMessage(79));
                    return;
                }
                else {
                    pc.getInventory().removeItem(item, 1L);
                    if (characterSlot < 0) {
                        characterSlot = 0;
                    }
                    else {
                        ++characterSlot;
                    }
                    account.set_character_slot(characterSlot);
                    AccountReading.get().updateCharacterSlot(pc.getAccountName(), characterSlot);
                }
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
