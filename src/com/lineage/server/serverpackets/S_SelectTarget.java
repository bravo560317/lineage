package com.lineage.server.serverpackets;

public class S_SelectTarget extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SelectTarget(int ObjectId) {
        this._byte = null;
        this.writeC(236);
        this.writeD(ObjectId);
        this.writeC(0);
        this.writeC(0);
        this.writeC(2);
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
