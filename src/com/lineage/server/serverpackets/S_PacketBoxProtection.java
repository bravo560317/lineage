package com.lineage.server.serverpackets;

public class S_PacketBoxProtection extends ServerBasePacket
{
    private byte[] _byte;
    public static final int JUSTICE_L1 = 0;
    public static final int JUSTICE_L2 = 1;
    public static final int JUSTICE_L3 = 2;
    public static final int EVIL_L1 = 3;
    public static final int EVIL_L2 = 4;
    public static final int EVIL_L3 = 5;
    public static final int ENCOUNTER = 6;
    
    public S_PacketBoxProtection(int model, int type) {
        this._byte = null;
        this.writeC(250);
        this.writeC(114);
        this.writeD(model);
        this.writeD(type);
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
