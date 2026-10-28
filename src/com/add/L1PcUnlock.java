package com.add;

import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1PcUnlock
{
    public static void Pc_Unlock(L1PcInstance pc) {
        if (pc == null) {
            return;
        }
        else {
            pc.sendPackets(new S_OwnCharPack(pc));
            pc.removeAllKnownObjects();
            pc.updateObject();
            pc.sendVisualEffectAtTeleport();
            pc.sendPackets(new S_CharVisualUpdate(pc));
            return;
        }
    }
}
