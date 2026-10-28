package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1EmblemIcon;

public class S_Emblem extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Emblem(L1EmblemIcon emblemIcon) {
        this._byte = null;
        this.writeC(118);
        this.writeD(emblemIcon.get_emblemid());
        this.writeByte(emblemIcon.get_clanIcon());
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
