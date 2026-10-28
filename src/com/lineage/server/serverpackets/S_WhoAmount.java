package com.lineage.server.serverpackets;

public class S_WhoAmount extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_WhoAmount(String amount) {
        this._byte = null;
        this.writeC(71);
        this.writeH(81);
        this.writeC(1);
        this.writeS(amount);
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
