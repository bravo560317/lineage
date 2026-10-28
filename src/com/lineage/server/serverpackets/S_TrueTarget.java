package com.lineage.server.serverpackets;

public class S_TrueTarget extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_TrueTarget(int targetId, int objectId, String message) {
        this._byte = null;
        this.buildPacket(targetId, objectId, message);
    }
    
    private void buildPacket(int targetId, int objectId, String message) {
        this.writeC(11);
        this.writeD(targetId);
        this.writeD(objectId);
        this.writeS(message);
    }
    
    public S_TrueTarget(int targetId, int gfxid) {
        this._byte = null;
        this.writeC(11);
        this.writeD(targetId);
        this.writeD(targetId);
        this.writeS(null);
        this.writeH(gfxid);
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
