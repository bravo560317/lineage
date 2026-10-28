package com.lineage.server.serverpackets;

public final class S_MapTimer extends ServerBasePacket
{
    private static final String S_MAP_TIMER = "[S] S_MapTimer";
    private byte[] _byte;
    public static final int MAP_TIMER = 153;
    
    public S_MapTimer(int value) {
        this.writeC(250);
        this.writeC(153);
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
        return "[S] S_MapTimer";
    }
}
