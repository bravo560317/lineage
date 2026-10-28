package com.lineage.server.serverpackets;

public class S_Unknown_B extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Unknown_B() {
        this._byte = null;
        this.writeC(64);
        this.writeC(10);
        this.writeC(2);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(43);
        this.writeC(127);
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
