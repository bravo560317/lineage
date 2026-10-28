package com.lineage.server.serverpackets;

public class S_Game_PetRacing extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_Game_PetRacing(int maxLab, int nowLab) {
        this._byte = null;
        this.writeC(250);
        this.writeC(67);
        this.writeH(maxLab);
        this.writeH(nowLab);
    }
    
    public S_Game_PetRacing(int type, int s, boolean i) {
        this._byte = null;
        this.writeC(250);
        this.writeC(type);
        this.writeC(s);
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
