package com.lineage.server.serverpackets;

public class S_Sound extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Sound(int sound) {
        this._byte = null;
        this.buildPacket(sound, 0);
    }
    
    public S_Sound(int sound, int repeat) {
        this._byte = null;
        this.buildPacket(sound, repeat);
    }
    
    private void buildPacket(int sound, int repeat) {
        this.writeC(22);
        this.writeC(repeat);
        this.writeH(sound);
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
