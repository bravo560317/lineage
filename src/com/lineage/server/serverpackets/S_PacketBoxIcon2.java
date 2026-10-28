package com.lineage.server.serverpackets;

public class S_PacketBoxIcon2 extends ServerBasePacket
{
    private static final int ICONS2 = 21;
    private byte[] _byte;
    
    public S_PacketBoxIcon2(int type, int time, int value, int test) {
        this._byte = null;
        this.writeC(250);
        this.writeC(21);
        this.writeC(type);
        this.writeC(time);
        this.writeC(test);
        this.writeC(value);
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
