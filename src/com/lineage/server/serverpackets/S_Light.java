package com.lineage.server.serverpackets;

public class S_Light extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Light(int objid, int type) {
        this._byte = null;
        this.buildPacket(objid, type);
    }
    
    private void buildPacket(int objid, int type) {
        this.writeC(40);
        this.writeD(objid);
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
