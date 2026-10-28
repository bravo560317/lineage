package com.lineage.server.serverpackets;

public class S_SkillBrave extends ServerBasePacket
{
    public S_SkillBrave(int objid, int mode, int time) {
        this.writeC(67);
        this.writeD(objid);
        this.writeC(mode);
        this.writeH(time);
    }
    
    public S_SkillBrave(int objid, int mode) {
        this.writeC(67);
        this.writeD(objid);
        this.writeC(mode);
        this.writeH(300);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}
