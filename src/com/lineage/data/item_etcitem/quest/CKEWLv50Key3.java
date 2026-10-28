package com.lineage.data.item_etcitem.quest;

import com.lineage.server.templates.L1QuestUser;
import java.util.Iterator;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class CKEWLv50Key3 extends ItemExecutor
{
    private CKEWLv50Key3() {
    }
    
    public static ItemExecutor get() {
        return new CKEWLv50Key3();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getMapId() != 2000) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            HashMap<Integer, L1Object> mapList = new HashMap();
            mapList.putAll(World.get().getVisibleObjects(2000));
            int itemid = 49166;
            int i = 0;
            for (L1Object tgobj : mapList.values()) {
                if (tgobj instanceof L1PcInstance) {
                    L1PcInstance tgpc = (L1PcInstance)tgobj;
                    if (tgpc.get_showId() == pc.get_showId()) {
                        if (tgpc.isCrown()) {
                            ++i;
                        }
                        else if (tgpc.isKnight()) {
                            i += 2;
                        }
                        else if (tgpc.isElf()) {
                            i += 4;
                        }
                        else if (tgpc.isWizard()) {
                            i += 8;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
            if (i == 15) {
                for (L1Object tgobj : mapList.values()) {
                    if (tgobj instanceof L1PcInstance) {
                        L1PcInstance tgpc = (L1PcInstance)tgobj;
                        if (tgpc.get_showId() == pc.get_showId()) {
                            L1ItemInstance reitem = tgpc.getInventory().findItemId(itemid);
                            if (reitem != null) {
                                tgpc.sendPackets(new S_ServerMessage(165, reitem.getName()));
                                tgpc.getInventory().removeItem(reitem);
                            }
                            if (tgpc.isCrown()) {
                                L1Teleport.teleport(tgpc, 32741, 32776, (short)2000, 2, true);
                            }
                            else if (tgpc.isKnight()) {
                                L1Teleport.teleport(tgpc, 32741, 32771, (short)2000, 2, true);
                            }
                            else if (tgpc.isElf()) {
                                L1Teleport.teleport(tgpc, 32735, 32771, (short)2000, 2, true);
                            }
                            else if (tgpc.isWizard()) {
                                L1Teleport.teleport(tgpc, 32735, 32776, (short)2000, 2, true);
                            }
                            else {
                                continue;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(79));
                L1QuestUser quest = WorldQuest.get().get(pc.get_showId());
                quest.endQuest();
            }
            mapList.clear();
            return;
        }
    }
}
