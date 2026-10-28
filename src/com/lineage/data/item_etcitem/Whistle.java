package com.lineage.data.item_etcitem;

import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_Sound;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Whistle extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Whistle();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        pc.sendPacketsX8(new S_Sound(437));
        Object[] petList = pc.getPetList().values().toArray();
        Object[] array;
        for (int length = (array = petList).length, i = 0; i < length; ++i) {
            Object petObject = array[i];
            if (petObject instanceof L1PetInstance) {
                L1PetInstance pet = (L1PetInstance)petObject;
                pet.call();
            }
        }
    }
}
