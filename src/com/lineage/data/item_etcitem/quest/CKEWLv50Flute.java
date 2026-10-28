package com.lineage.data.item_etcitem.quest;

import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.model.L1Location;
import java.util.Iterator;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import java.util.HashMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class CKEWLv50Flute extends ItemExecutor
{
    private CKEWLv50Flute() {
    }
    
    public static ItemExecutor get() {
        return new CKEWLv50Flute();
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
            int itemid = 49167;
            int i = 0;
            for (L1Object tgobj : mapList.values()) {
                if (tgobj instanceof L1MonsterInstance) {
                    L1MonsterInstance tgnpc = (L1MonsterInstance)tgobj;
                    if (pc.get_showId() != tgnpc.get_showId()) {
                        continue;
                    }
                    else {
                        switch (tgnpc.getNpcId()) {
                            case 87015:
                            case 87016:
                            case 87017:
                            case 87018: {
                                i += 16;
                                break;
                            }
                        }
                    }
                }
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
                            L1Location loc = tgpc.getLocation().randomLocation(2, false);
                            pc.sendPacketsXR(new S_EffectLocation(loc, 3992), 8);
                            if (tgpc.isCrown()) {
                                L1SpawnUtil.spawnX(87017, loc, tgpc.get_showId());
                            }
                            else if (tgpc.isKnight()) {
                                L1SpawnUtil.spawnX(87018, loc, tgpc.get_showId());
                            }
                            else if (tgpc.isElf()) {
                                L1SpawnUtil.spawnX(87015, loc, tgpc.get_showId());
                            }
                            else if (tgpc.isWizard()) {
                                L1SpawnUtil.spawnX(87016, loc, tgpc.get_showId());
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
