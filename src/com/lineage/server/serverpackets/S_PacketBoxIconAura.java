package com.lineage.server.serverpackets;

public class S_PacketBoxIconAura extends ServerBasePacket
{
    private byte[] _byte;
    public static final int ICON_AURA = 22;
    public static final int ICON_OS = 125;
    public static final int ICON_E3 = 227;
    
    public S_PacketBoxIconAura(int iconid, int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(22);
        this.writeC(iconid);
        this.writeH(time);
    }
    
    public S_PacketBoxIconAura(int type, int time, int value, int test) {
        this._byte = null;
        this.writeC(250);
        this.writeC(22);
        this.writeC(type);
        this.writeH(time);
        this.writeH(test);
        this.writeH(value);
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
