package com.lineage.server.serverpackets;

public class S_CommonInfo extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_CommonInfo(int type, String[] info) {
        this._byte = null;
        this.buildPacket(type, info);
    }
    
    private void buildPacket(int type, String[] info) {
        this.writeH(type);
        if (info == null) {
            this.writeC(0);
        }
        else {
            this.writeC(info.length);
            for (int i = 0; i < info.length; ++i) {
                this.writeS(info[i]);
            }
        }
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
