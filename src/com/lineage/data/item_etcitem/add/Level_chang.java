package com.lineage.data.item_etcitem.add;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;

public class Level_chang extends ItemExecutor {

    private boolean type = false;

    public static ItemExecutor get() {
        return new Level_chang();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.getInventory().removeItem(item, 1L);

        // ⚡ 修正重點：不轉 int
        pc.setsavepclog(pc.getExp());

        pc.setExp(1L);
        pc.onChangeExp();

        try {
            Thread.sleep(1000L);
        } catch (Exception ignored) {}

        pc.setExp(pc.gesavepclog());
        pc.onChangeExp();

        if (this.type) {
            pc.setCurrentHp(1);
            pc.setCurrentMp(1);
        }

        pc.sendPackets((ServerBasePacket) new S_ServerMessage(822));
        RecordTable.get().reshp(pc.getName());
    }

    @Override
    public void set_set(String[] set) {
        try {
            this.type = Boolean.parseBoolean(set[1]);
        } catch (Exception ignored) {}
    }
}
