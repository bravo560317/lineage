package com.lineage.server.serverpackets;

public class S_TradeStatus extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_TradeStatus(int type) {
        this._byte = null;
        this.writeC(112);
        this.writeC(type);
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
