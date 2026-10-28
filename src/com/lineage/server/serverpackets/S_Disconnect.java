package com.lineage.server.serverpackets;

public class S_Disconnect extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Disconnect() {
        this._byte = null;
        this.writeC(227);
        this.writeH(500);
        this.writeD(0);
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
