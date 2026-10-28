package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;

public class S_HierarchPack extends ServerBasePacket
{
    private static final String _S__1F_HIERARCHPACK = "[S] S_HierarchPack";
    private byte[] _byte;
    
    public S_HierarchPack(L1HierarchInstance pet, L1PcInstance player) {
        this._byte = null;
        this.writeC(87);
        this.writeH(pet.getX());
        this.writeH(pet.getY());
        this.writeD(pet.getId());
        this.writeH(pet.getGfxId());
        this.writeC(pet.getStatus());
        this.writeC(pet.getHeading());
        this.writeC(pet.getLightSize());
        this.writeC(1);
        this.writeD(0);
        this.writeH(0);
        this.writeS(pet.getNameId());
        this.writeS(pet.getTitle());
        this.writeC(0);
        this.writeD(0);
        this.writeS(null);
        this.writeS((pet.getMaster() != null) ? pet.getMaster().getName() : "");
        this.writeC(0);
        this.writeC(255);
        this.writeC(0);
        this.writeC(pet.getLevel());
        this.writeC(0);
        this.writeC(255);
        this.writeC(255);
    }
    
    @Override
    public byte[] getContent() {
        if (this._byte == null) {
            this._byte = this._bao.toByteArray();
        }
        return this._byte;
    }
    
    @Override
    public String getType() {
        return "[S] S_HierarchPack";
    }
}
