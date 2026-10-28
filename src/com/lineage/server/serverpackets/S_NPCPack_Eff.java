package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1EffectInstance;

public class S_NPCPack_Eff extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_NPCPack_Eff(L1EffectInstance npc) {
        this._byte = null;
        this.writeC(87);
        this.writeH(npc.getX());
        this.writeH(npc.getY());
        this.writeD(npc.getId());
        this.writeH(npc.getGfxId());
        this.writeC(npc.getStatus());
        this.writeC(npc.getHeading());
        this.writeC(npc.getChaLightSize());
        this.writeC(npc.getMoveSpeed());
        this.writeD((int)npc.getExp());
        this.writeH(npc.getTempLawful());
        this.writeS(npc.getNameId());
        this.writeS(npc.getTitle());
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
