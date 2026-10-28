package com.lineage.server.serverpackets;

public class S_Lock extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Lock() {
        this._byte = null;
        this.buildPacket();
    }
    
    private void buildPacket() {
        this.writeC(241);
        this.writeC(0);
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
