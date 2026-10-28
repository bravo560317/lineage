package com.lineage.server.serverpackets;

public class S_CastleMaster extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CastleMaster(int type, int objecId) {
        this._byte = null;
        this.buildPacket(type, objecId);
    }
    
    private void buildPacket(int type, int objecId) {
        this.writeC(69);
        this.writeC(type);
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
