package com.lineage.server.serverpackets;

public class S_TaxRate extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_TaxRate(int objecId) {
        this._byte = null;
        this.writeC(185);
        this.writeD(objecId);
        this.writeC(10);
        this.writeC(50);
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
