package com.lineage.server.serverpackets;

public class S_Ability extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Ability(int type, boolean equipped) {
        this._byte = null;
        this.buildPacket(type, equipped);
    }
    
    private void buildPacket(int type, boolean equipped) {
        this.writeC(36);
        this.writeC(type);
        this.writeC(equipped ? 1 : 0);
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
