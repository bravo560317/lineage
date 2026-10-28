package com.lineage.server.serverpackets;

public class S_PacketBoxIcon3 extends ServerBasePacket
{
    private static final int ICON_OTHER3 = 154;
    private byte[] _byte;
    
    public S_PacketBoxIcon3(int time, int type) {
        this._byte = null;
        this.writeC(250);
        this.writeC(154);
        this.writeH(time);
        this.writeC(type);
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
