package com.lineage.server.serverpackets;

public class S_PacketBoxIcon1 extends ServerBasePacket
{
    private static final int ICONS1 = 20;
    private static final int _dodge_up = 88;
    private static final int _dodge_down = 101;
    private byte[] _byte;
    
    public S_PacketBoxIcon1(int type, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(20);
        this.writeH(time);
        this.writeC(type);
    }
    
    public S_PacketBoxIcon1(boolean type, int i) {
        this._byte = null;
        this.writeC(250);
        if (type) {
            this.writeC(88);
            this.writeC(i);
        }
        else {
            this.writeC(101);
            this.writeC(i);
        }
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
