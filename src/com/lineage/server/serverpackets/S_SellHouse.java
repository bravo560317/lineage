package com.lineage.server.serverpackets;

public class S_SellHouse extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SellHouse(int objectId, String houseNumber) {
        this._byte = null;
        this.buildPacket(objectId, houseNumber);
    }
    
    private void buildPacket(int objectId, String houseNumber) {
        this.writeC(136);
        this.writeD(objectId);
        this.writeD(0);
        this.writeD(100000);
        this.writeD(100000);
        this.writeD(2000000000);
        this.writeH(0);
        this.writeS("agsell");
        this.writeS("agsell " + houseNumber);
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
