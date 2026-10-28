package com.lineage.data.item_etcitem.quest;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class ScoreItem1 extends ItemExecutor
{
    private final Random _random;
    
    public ScoreItem1() {
        this._random = new Random();
    }
    
    public static ItemExecutor get() {
        return new ScoreItem1();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int srcItemId = 44049;
        int count = 100;
        boolean isError = true;
        if (pc.getMapId() == 4) {
            int x = pc.getX();
            int y = pc.getY();
            if (x > 34026 && x < 34080 && y > 32235 && y < 32314) {
                L1ItemInstance itemX = pc.getInventory().checkItemX(44049, 100L);
                if (itemX != null) {
                    pc.sendPacketsAll(new S_SkillSound(pc.getId(), 2944));
                    pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 19));
                    pc.getInventory().removeItem(itemX, 100L);
                    if (this._random.nextInt(100) < 95) {
                        CreateNewItem.createNewItem(pc, 41223, 1L);
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(280));
                    }
                    isError = false;
                }
            }
        }
        if (isError) {
            pc.sendPackets(new S_ServerMessage(79));
        }
    }
}
