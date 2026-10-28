package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1IllusoryInstance;

public class S_NPCPack_Ill extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Ill(L1IllusoryInstance de) {
        this._byte = null;
        this.writeC(87);
        this.writeH(de.getX());
        this.writeH(de.getY());
        this.writeD(de.getId());
        this.writeH(de.getTempCharGfx());
        this.writeC(de.getStatus());
        this.writeC(de.getHeading());
        this.writeC(de.getChaLightSize());
        this.writeC(de.getMoveSpeed());
        this.writeD(0);
        this.writeH(de.getLawful());
        this.writeS(de.getNameId());
        this.writeS(de.getTitle());
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
