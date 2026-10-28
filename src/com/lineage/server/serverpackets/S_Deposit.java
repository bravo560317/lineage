package com.lineage.server.serverpackets;

public class S_Deposit extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Deposit(int objecId) {
        this._byte = null;
        this.writeC(4);
        this.writeD(objecId);
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
