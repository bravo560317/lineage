package com.lineage.server.serverpackets;

public class S_Fishing extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Fishing(int objectId, int motionNum, int x, int y) {
        this._byte = null;
        this.buildPacket(objectId, motionNum, x, y);
    }
    
    private void buildPacket(int objectId, int motionNum, int x, int y) {
        this.writeC(158);
        this.writeD(objectId);
        this.writeC(motionNum);
        this.writeH(x);
        this.writeH(y);
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
