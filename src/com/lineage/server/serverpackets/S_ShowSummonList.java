package com.lineage.server.serverpackets;

public class S_ShowSummonList extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ShowSummonList(int objid) {
        this._byte = null;
        this.writeC(39);
        this.writeD(objid);
        this.writeS("summonlist");
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
