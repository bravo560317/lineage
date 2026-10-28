package com.lineage.server.serverpackets;

public class S_SkillIconBlessOfEva extends ServerBasePacket
{
    public S_SkillIconBlessOfEva(int objectId, int time) {
        this.writeC(126);
        this.writeD(objectId);
        this.writeH(time);
    }
    
    @Override
    public byte[] getContent() {
        return this.getBytes();
    }
}
