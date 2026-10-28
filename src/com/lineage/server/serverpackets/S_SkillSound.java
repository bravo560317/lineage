package com.lineage.server.serverpackets;

public class S_SkillSound extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_SkillSound(int objid, int gfxid) {
        this._byte = null;
        this.buildPacket(objid, gfxid);
    }
    
    private void buildPacket(int objid, int gfxid) {
        this.writeC(55);
        this.writeD(objid);
        this.writeH(gfxid);
    }
    
    public S_SkillSound(int objid, int gfxid, int time) {
        this._byte = null;
        this.writeC(55);
        this.writeD(objid);
        this.writeH(gfxid);
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
