package com.lineage.server.serverpackets;

public class S_PacketBoxLoc extends ServerBasePacket
{
    private byte[] _byte;
    public static final int SEND_LOC = 111;
    
    public S_PacketBoxLoc(String name, int map, int x, int y, int zone) {
        this._byte = null;
        this.writeC(250);
        this.writeC(111);
        this.writeS(name);
        this.writeH(map);
        this.writeH(x);
        this.writeH(y);
        this.writeD(zone);
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
