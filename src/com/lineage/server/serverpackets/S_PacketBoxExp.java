package com.lineage.server.serverpackets;

public class S_PacketBoxExp extends ServerBasePacket
{
    private byte[] _byte;
    public static final int LEAVES = 82;
    
    public S_PacketBoxExp(int exp) {
        this._byte = null;
        this.writeC(250);
        this.writeC(82);
        this.writeC(exp);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
    }
    
    public S_PacketBoxExp() {
        this._byte = null;
        this.writeC(250);
        this.writeC(82);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
        this.writeC(0);
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
