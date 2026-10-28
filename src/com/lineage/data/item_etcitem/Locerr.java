package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class Locerr extends ItemExecutor
{
    private static Random _random;
    
    static {
        Locerr._random = new Random();
    }
    
    private Locerr() {
    }
    
    public static ItemExecutor get() {
        return new Locerr();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc.isParalyzed() || pc.isSleeped() || pc.isParalyzedX()) {
            return;
        }
        else if (pc.isDead()) {
            return;
        }
        else if (pc.isInvisble()) {
            return;
        }
        else if (pc.hasSkillEffect(55889)) {
            pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
            return;
        }
        else {
            pc.setSkillEffect(55889, 3000);
            pc.set_misslocTime(1);
            return;
        }
    }
}
