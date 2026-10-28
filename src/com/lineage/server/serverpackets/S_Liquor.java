package com.lineage.server.serverpackets;

public class S_Liquor extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Liquor(int objecId) {
        this._byte = null;
        this.writeC(103);
        this.writeD(objecId);
        this.writeC(1);
    }
    
    public S_Liquor(int objecId, int type) {
        this._byte = null;
        this.writeC(103);
        this.writeD(objecId);
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
