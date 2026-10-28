package com.lineage.server.serverpackets;

public class S_PacketBoxDk extends ServerBasePacket
{
    public static final int LV1 = 1;
    public static final int LV2 = 2;
    public static final int LV3 = 3;
    private byte[] _byte;
    
    public S_PacketBoxDk(int lv) {
        this._byte = null;
        this.writeC(250);
        this.writeC(75);
        this.writeC(lv);
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
