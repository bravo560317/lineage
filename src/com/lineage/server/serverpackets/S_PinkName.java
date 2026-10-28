package com.lineage.server.serverpackets;

public class S_PinkName extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_PinkName(int objecId, int time) {
        this._byte = null;
        this.writeC(60);
        this.writeD(objecId);
        this.writeC(time);
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
