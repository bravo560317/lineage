package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.quest.DarkElfLv30_1;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Assassination_NameList extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Assassination_NameList();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.getQuest().isEnd(DarkElfLv30_1.QUEST.get_id())) {
            return;
        }
        else if (!pc.hasSkillEffect(4003)) {
            pc.sendPackets(new S_ServerMessage(79));
            return;
        }
        else {
            int itemId = item.getItemId();
            switch (itemId) {
                case 40557: {
                    if (pc.getX() == 32620 && pc.getY() == 32641 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45883) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45883, 2, 300);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                case 40558: {
                    if (pc.getX() == 33513 && pc.getY() == 32890 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45889) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45889, 2, 300000);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                case 40559: {
                    if (pc.getX() == 34215 && pc.getY() == 33195 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45888) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45888, 2, 300000);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                case 40560: {
                    if (pc.getX() == 32580 && pc.getY() == 33260 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45886) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45886, 2, 300000);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                case 40561: {
                    if (pc.getX() == 33046 && pc.getY() == 32806 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45885) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45885, 2, 300000);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                case 40562: {
                    if (pc.getX() == 33447 && pc.getY() == 33476 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45887) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45887, 2, 300000);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
                case 40563: {
                    if (pc.getX() == 32730 && pc.getY() == 32426 && pc.getMapId() == 4) {
                        for (L1NpcInstance npc : WorldNpc.get().all()) {
                            if (npc.getNpcTemplate().get_npcId() == 45884) {
                                pc.sendPackets(new S_ServerMessage(79));
                                return;
                            }
                        }
                        L1SpawnUtil.spawn(pc, 45884, 2, 300000);
                        break;
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage(79));
                        break;
                    }
                }
            }
            return;
        }
    }
}
