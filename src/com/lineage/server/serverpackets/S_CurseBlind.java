package com.lineage.server.serverpackets;

public class S_CurseBlind extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CurseBlind(int type) {
        this._byte = null;
        this.buildPacket(type);
    }
    
    private void buildPacket(int type) {
        this.writeC(47);
        this.writeH(type);
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
