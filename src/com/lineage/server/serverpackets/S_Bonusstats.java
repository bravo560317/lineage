package com.lineage.server.serverpackets;

public class S_Bonusstats extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Bonusstats(int objid) {
        this._byte = null;
        this.buildPacket(objid);
    }
    
    private void buildPacket(int objid) {
        this.writeC(39);
        this.writeD(objid);
        this.writeS("RaiseAttr");
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
