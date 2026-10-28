package com.lineage.server.serverpackets;

public class S_War extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_War(int type, String clan_name1, String clan_name2) {
        this._byte = null;
        this.buildPacket(type, clan_name1, clan_name2);
    }
    
    private void buildPacket(int type, String clan_name1, String clan_name2) {
        this.writeC(84);
        this.writeC(type);
        this.writeS(clan_name1);
        this.writeS(clan_name2);
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
