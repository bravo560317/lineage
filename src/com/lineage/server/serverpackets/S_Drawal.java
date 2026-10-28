package com.lineage.server.serverpackets;

public class S_Drawal extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Drawal(int objectId, long count) {
        this._byte = null;
        this.writeC(141);
        this.writeD(objectId);
        this.writeD((int)Math.min(count, 2000000000L));
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
