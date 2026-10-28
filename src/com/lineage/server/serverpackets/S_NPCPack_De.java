package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1DeInstance;

public class S_NPCPack_De extends ServerBasePacket
{
    private static final int STATUS_POISON = 1;
    private static final int STATUS_INVISIBLE = 2;
    private static final int STATUS_PC = 4;
    private static final int STATUS_BRAVE = 16;
    private byte[] _byte;
    
    public S_NPCPack_De(L1DeInstance de) {
        this._byte = null;
        int status = 4;
        if (de.getPoison() != null && de.getPoison().getEffectId() == 1) {
            status |= 0x1;
        }
        if (de.isInvisble()) {
            status |= 0x2;
        }
        if (de.getBraveSpeed() == 1) {
            status |= 0x10;
        }
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
        this.writeC(status);
        if (de.getClanid() > 0) {
            this.writeD(de.getEmblem().get_emblemid());
        }
        else {
            this.writeD(0);
        }
        this.writeS(de.getClanname());
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
