package com.lineage.data.item_etcitem;

import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.L1Location;
import java.util.Iterator;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_NPCPack_Pet;
import com.lineage.server.serverpackets.S_NPCPack_Summon;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_Sound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import com.lineage.data.executor.ItemExecutor;

public class New_Whistle extends ItemExecutor
{
    private static Random _random;
    
    static {
        New_Whistle._random = new Random();
    }
    
    private New_Whistle() {
    }
    
    public static ItemExecutor get() {
        return new New_Whistle();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        short mapId = pc.getTeleportMapId();
        int head = pc.getTeleportHeading();
        pc.sendPacketsX8(new S_Sound(437));
        if (!pc.isGhost() && pc.getMap().isTakePets()) {
            Object[] petList = pc.getPetList().values().toArray();
            Object[] array;
            for (int length = (array = petList).length, i = 0; i < length; ++i) {
                Object petObject = array[i];
                if (petObject instanceof L1PetInstance) {
                    L1PetInstance pet = (L1PetInstance)petObject;
                    for (L1NpcInstance petNpc : pc.getPetList().values()) {
                        L1Location loc = pc.getLocation().randomLocation(3, false);
                        int nx = loc.getX();
                        int ny = loc.getY();
                        if (pc.getMapId() == 5125 || pc.getMapId() == 5131 || pc.getMapId() == 5132 || pc.getMapId() == 5133 || pc.getMapId() == 5134) {
                            nx = 32799 + New_Whistle._random.nextInt(5) - 3;
                            ny = 32864 + New_Whistle._random.nextInt(5) - 3;
                        }
                        teleport(petNpc, nx, ny, mapId, head);
                        pet.call_New();
                        if (petNpc instanceof L1SummonInstance) {
                            L1SummonInstance summon = (L1SummonInstance)petNpc;
                            pc.sendPackets(new S_NPCPack_Summon(summon, pc));
                        }
                        else if (petNpc instanceof L1PetInstance) {
                            L1PetInstance pet2 = (L1PetInstance)petNpc;
                            pc.sendPackets(new S_NPCPack_Pet(pet2, pc));
                        }
                        for (L1PcInstance visiblePc : World.get().getVisiblePlayer(petNpc)) {
                            visiblePc.removeKnownObject(petNpc);
                        }
                    }
                    pet.call_New();
                }
            }
        }
    }
    
    private static void teleport(L1NpcInstance npc, int x, int y, short map, int head) {
        try {
            World.get().moveVisibleObject(npc, (int)map);
            L1WorldMap.get().getMap(npc.getMapId()).setPassable(npc.getX(), npc.getY(), true);
            npc.setX(x);
            npc.setY(y);
            npc.setMap(map);
            npc.setHeading(head);
            L1WorldMap.get().getMap(npc.getMapId()).setPassable(npc.getX(), npc.getY(), false);
        }
        catch (Exception ex) {}
    }
}
