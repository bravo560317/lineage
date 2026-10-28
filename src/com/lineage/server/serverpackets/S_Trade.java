package com.lineage.server.serverpackets;

public class S_Trade extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Trade(String name) {
        this._byte = null;
        this.writeC(52);
        this.writeS(name);
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
