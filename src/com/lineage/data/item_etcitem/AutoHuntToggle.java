package com.lineage.data.item_etcitem;

import java.sql.Timestamp;
import com.add.AutoAttackUpdate;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.lock.VIPReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public class AutoHuntToggle extends ItemExecutor {

    private AutoHuntToggle() {
    }

    public static ItemExecutor get() {
        return new AutoHuntToggle();
    }

    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc == null || item == null) {
            return;
        }

        boolean start = !pc.isActived();
        boolean simpleAutoHunt = !hasActiveVip(pc);
        AutoAttackUpdate.getInstance().PcCommand(pc, start ? "guaji_start" : "guaji_stop", start && simpleAutoHunt);
    }

    private boolean hasActiveVip(L1PcInstance pc) {
        Timestamp vipTime = VIPReading.get().getOther(pc);
        return vipTime != null && vipTime.getTime() > System.currentTimeMillis();
    }
}
