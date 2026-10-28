package com.lineage.server.serverpackets;

public class S_SkillHaste extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SkillHaste(int objid, int mode, int time) {
        this._byte = null;
        this.writeC(255);
        this.writeD(objid);
        this.writeC(mode);
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
