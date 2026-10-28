package com.lineage.server.serverpackets;

public class S_Teleport2 extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Teleport2(int mapid, int id) {
        this._byte = null;
        this.writeC(241);
        this.writeH(mapid);
        this.writeD(id);
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
