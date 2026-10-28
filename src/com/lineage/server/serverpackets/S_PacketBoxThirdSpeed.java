package com.lineage.server.serverpackets;

public class S_PacketBoxThirdSpeed extends ServerBasePacket
{
    private byte[] _byte;
    public static final int CAKE = 60;
    
    public S_PacketBoxThirdSpeed(int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(60);
        this.writeC(time >> 2);
        this.writeC(8);
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
