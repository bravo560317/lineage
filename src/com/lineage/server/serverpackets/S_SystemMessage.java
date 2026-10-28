package com.lineage.server.serverpackets;

public class S_SystemMessage extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SystemMessage(String msg) {
        this._byte = null;
        this.writeC(243);
        this.writeC(9);
        this.writeS(msg);
    }
    
    public S_SystemMessage(String msg, boolean nameid) {
        this._byte = null;
        this.writeC(161);
        this.writeC(2);
        this.writeD(0);
        this.writeS(msg);
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
