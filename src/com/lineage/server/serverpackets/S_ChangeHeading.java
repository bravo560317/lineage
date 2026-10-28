package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;

public class S_ChangeHeading extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChangeHeading(L1Character cha) {
        this._byte = null;
        this.buildPacket(cha);
    }
    
    private void buildPacket(L1Character cha) {
        this.writeC(122);
        this.writeD(cha.getId());
        this.writeC(cha.getHeading());
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
