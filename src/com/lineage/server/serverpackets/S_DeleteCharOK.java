package com.lineage.server.serverpackets;

public class S_DeleteCharOK extends ServerBasePacket
{
    private byte[] _byte;
    public static final int DELETE_CHAR_NOW = 5;
    public static final int DELETE_CHAR_AFTER_7DAYS = 81;
    
    public S_DeleteCharOK(int type) {
        this._byte = null;
        this.writeC(6);
        this.writeC(type);
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
