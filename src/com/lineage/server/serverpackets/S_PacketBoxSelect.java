package com.lineage.server.serverpackets;

public class S_PacketBoxSelect extends ServerBasePacket
{
    private byte[] _byte;
    public static final int LOGOUT = 42;
    
    public S_PacketBoxSelect() {
        this._byte = null;
        this.writeC(250);
        this.writeC(42);
        this.writeC(0);
        this.writeC(0);
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
