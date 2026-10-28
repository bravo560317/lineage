package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

public class S_Letter extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Letter(L1ItemInstance item) {
        this._byte = null;
    }
    
    public S_Letter() {
        this._byte = null;
        this.writeD(0);
        this.writeH(615);
        this.writeH(0);
        this.writeS("123");
        this.writeS("456");
        this.writeByte((byte[])null);
        this.writeByte((byte[])null);
        this.writeC(0);
        this.writeS("info");
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
