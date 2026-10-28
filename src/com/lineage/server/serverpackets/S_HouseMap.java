package com.lineage.server.serverpackets;

public class S_HouseMap extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_HouseMap(int objectId, String house_number) {
        this._byte = null;
        this.buildPacket(objectId, house_number);
    }
    
    private void buildPacket(int objectId, String house_number) {
        int number = Integer.valueOf(house_number);
        this.writeC(187);
        this.writeD(objectId);
        this.writeD(number);
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
