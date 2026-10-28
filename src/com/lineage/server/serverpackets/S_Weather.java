package com.lineage.server.serverpackets;

public class S_Weather extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Weather(int weather) {
        this._byte = null;
        this.buildPacket(weather);
    }
    
    private void buildPacket(int weather) {
        this.writeC(115);
        this.writeC(weather);
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
