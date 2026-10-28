package com.lineage.server.serverpackets;

public class S_SkillIconShield extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SkillIconShield(int type, int time) {
        this._byte = null;
        this.writeC(216);
        this.writeH(time);
        this.writeC(type);
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
