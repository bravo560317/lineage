package com.lineage.server.serverpackets;

import com.lineage.server.templates.L1Config;

public class S_PacketBoxConfig extends ServerBasePacket
{
    private byte[] _byte;
    public static final int CHARACTER_CONFIG = 41;
    
    public S_PacketBoxConfig(L1Config config) {
        this._byte = null;
        int length = config.getLength();
        byte[] data = config.getData();
        this.writeC(250);
        this.writeC(41);
        this.writeD(length);
        this.writeByte(data);
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
