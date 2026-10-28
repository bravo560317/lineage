package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

public class S_ChatClanAlliance extends ServerBasePacket
{
    private byte[] _byte;
    
    public S_ChatClanAlliance(L1PcInstance pc, String clanName, String chat) {
        this._byte = null;
        this.buildPacket(pc, clanName, chat);
    }
    
    private void buildPacket(L1PcInstance pc, String clanName, String chat) {
        this.writeC(81);
        this.writeC(15);
        this.writeD(pc.isInvisble() ? 0 : pc.getId());
        this.writeS("[" + clanName + "][" + pc.getName() + "] " + chat);
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
