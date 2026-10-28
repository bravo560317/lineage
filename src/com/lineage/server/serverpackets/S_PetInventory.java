package com.lineage.server.serverpackets;

import java.util.Iterator;
import java.util.List;
import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PetInstance;

public class S_PetInventory extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PetInventory(L1PetInstance pet, boolean b) {
        this._byte = null;
        this.isTrue(pet);
    }
    
    private void isTrue(L1PetInstance pet) {
        List<L1ItemInstance> itemList = pet.getInventory().getItems();
        this.writeC(176);
        this.writeD(pet.getId());
        this.writeH(itemList.size());
        this.writeC(11);
        int petAc = 10;
        for (L1ItemInstance item : itemList) {
            if (item != null) {
                this.writeD(item.getId());
                this.writeC(22);
                this.writeH(item.get_gfxid());
                this.writeC(item.getBless());
                this.writeD((int)Math.min(item.getCount(), 2000000000L));
                if (item.getItem().getType2() == 0 && item.getItem().getType() == 11 && item.isEquipped()) {
                    this.writeC(item.isIdentified() ? 3 : 2);
                    petAc += PetItemTable.get().getTemplate(item.getItemId()).getAddAc();
                }
                else {
                    this.writeC(item.isIdentified() ? 1 : 0);
                }
                this.writeS(item.getViewName());
            }
        }
        this.writeC(petAc);
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
