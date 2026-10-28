package com.lineage.server.serverpackets;

public class S_HowManyMake extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_HowManyMake(int objId, int max, String htmlId) {
        this._byte = null;
        this.writeC(136);
        this.writeD(objId);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(max);
        this.writeH(0);
        this.writeS("request");
        this.writeS(htmlId);
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
