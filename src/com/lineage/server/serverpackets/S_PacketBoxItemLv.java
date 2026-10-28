package com.lineage.server.serverpackets;

public class S_PacketBoxItemLv extends ServerBasePacket
{
    private byte[] _byte;
    public static final int MSG_LEVEL_OVER = 12;
    
    public S_PacketBoxItemLv(int minLv, int maxLv) {
        this._byte = null;
        this.writeC(250);
        this.writeC(12);
        this.writeC(minLv);
        this.writeC(maxLv);
    }
    
    public S_PacketBoxItemLv(int opid) {
        this._byte = null;
        this.writeC(opid);
        this.writeC(12);
        this.writeC(10);
        this.writeC(1249);
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
