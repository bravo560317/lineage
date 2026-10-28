package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1SkinInstance;

public class S_NPCPack_Skin extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Skin(L1SkinInstance pet) {
        this._byte = null;
        this.writeC(87);
        this.writeH(pet.getX());
        this.writeH(pet.getY());
        this.writeD(pet.getId());
        this.writeH(pet.getGfxId());
        this.writeC(pet.getStatus());
        this.writeC(pet.getHeading());
        this.writeC(0);
        this.writeC(pet.getMoveSpeed());
        this.writeD(0);
        this.writeH(0);
        this.writeS(pet.getNameId());
        this.writeS(pet.getTitle());
        this.writeC(0);
        this.writeD(0);
        this.writeS(null);
        this.writeS(null);
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
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
