package com.lineage.server.serverpackets;

public class S_PacketBoxWindShackle extends ServerBasePacket
{
    private byte[] _byte;
    public static final int WIND_SHACKLE = 44;
    
    public S_PacketBoxWindShackle(int objectId, int time) {
        this._byte = null;
        int buffTime = time >> 2;
        this.writeC(250);
        this.writeC(44);
        this.writeD(objectId);
        this.writeH(buffTime);
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
