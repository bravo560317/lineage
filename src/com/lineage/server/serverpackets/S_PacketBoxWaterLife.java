package com.lineage.server.serverpackets;

public class S_PacketBoxWaterLife extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PacketBoxWaterLife() {
        this._byte = null;
        this.writeC(250);
        this.writeC(59);
        this.writeH(0);
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
