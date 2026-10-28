package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PetInstance;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1PcInstance;

public class S_PetList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PetList(int npcObjId, L1PcInstance pc) {
        this._byte = null;
        this.buildPacket(npcObjId, pc);
    }
    
    private void buildPacket(int npcObjId, L1PcInstance pc) {
        List<L1ItemInstance> amuletList = new ArrayList();
        for (L1ItemInstance object47 : pc.getInventory().getItems()) {
            Object itemObject = object47;
            L1ItemInstance item = (L1ItemInstance)itemObject;
            switch (item.getItem().getItemId()) {
                case 40314:
                case 40316: {
                    if (!this.isWithdraw(pc, item)) {
                        amuletList.add(item);
                        continue;
                    }
                    else {
                        continue;
                    }
                }
                default: {
                    continue;
                }
            }
        }
        if (amuletList.size() != 0) {
            this.writeC(83);
            this.writeD(70);
            this.writeH(amuletList.size());
            for (L1ItemInstance item2 : amuletList) {
                this.writeD(item2.getId());
                this.writeC((int)Math.min(item2.getCount(), 2000000000L));
            }
        }
    }
    
    private boolean isWithdraw(L1PcInstance pc, L1ItemInstance item) {
        Object[] petlist = pc.getPetList().values().toArray();
        Object[] array;
        for (int length = (array = petlist).length, i = 0; i < length; ++i) {
            Object petObject = array[i];
            if (petObject instanceof L1PetInstance) {
                L1PetInstance pet = (L1PetInstance)petObject;
                if (item.getId() == pet.getItemObjId()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this.getBytes();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
