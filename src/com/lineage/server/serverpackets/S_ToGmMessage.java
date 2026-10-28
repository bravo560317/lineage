package com.lineage.server.serverpackets;

public class S_ToGmMessage extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ToGmMessage(String info) {
        this._byte = null;
        this.writeC(161);
        this.writeC(0);
        this.writeD(0);
        this.writeS("\\fY" + info);
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
