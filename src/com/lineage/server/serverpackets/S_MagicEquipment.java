package com.lineage.server.serverpackets;

public class S_MagicEquipment extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_MagicEquipment(int time, int type) {
        this._byte = null;
        this.buildPacket(time, type);
    }
    
    public void buildPacket(int time, int type) {
        this.writeC(250);
        this.writeC(154);
        this.writeH(time);
        this.writeD(type);
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
