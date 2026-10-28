package com.lineage.server.serverpackets;

public class S_ItemError extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ItemError(int skillid) {
        this._byte = null;
        this.buildPacket(skillid);
    }
    
    private void buildPacket(int skillid) {
        this.writeC(197);
        this.writeD(skillid);
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
