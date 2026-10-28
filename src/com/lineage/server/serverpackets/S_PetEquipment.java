package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PetInstance;

public class S_PetEquipment extends ServerBasePacket
{
    private static final String S_PET_EQUIPMENT = "[S] S_PetEquipment";
    
    public S_PetEquipment(int i, L1PetInstance pet, int j) {
        this.writeC(250);
        this.writeC(37);
        this.writeC(i);
        this.writeD(pet.getId());
        this.writeC(j);
        this.writeC(pet.getAc());
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
    
    @Override
    public String getType() {
        return "[S] S_PetEquipment";
    }
}
