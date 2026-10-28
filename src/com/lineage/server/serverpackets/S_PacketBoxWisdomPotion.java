package com.lineage.server.serverpackets;

public class S_PacketBoxWisdomPotion extends ServerBasePacket
{
    private byte[] _byte;
    public static final int WISDOM_POTION = 57;
    
    public S_PacketBoxWisdomPotion(int time) {
        this._byte = null;
        this.writeC(250);
        this.writeC(57);
        this.writeC(44);
        this.writeH(time);
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
